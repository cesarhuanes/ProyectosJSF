package com.cardif.satelite.tesoreria.controller;

import java.io.StringReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.richfaces.model.selection.SimpleSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.bnpparibas.cardif.connector.dterecovery.ConsumerSeguros;
import com.bnpparibas.cardif.connector.dterecovery.ConsumerServicios;
import com.bnpparibas.cardif.connector.response.RespuestaPPL;
import com.cardif.framework.controller.BaseController;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.service.ParametroService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.tesoreria.bean.ComprobanteElectronicoBean;
import com.cardif.satelite.tesoreria.bean.ComprobanteRelacionadoBean;
import com.cardif.satelite.tesoreria.bean.UsuarioSunat;
import com.cardif.satelite.tesoreria.service.ComprobanteRetencionService;
import com.cardif.satelite.util.Utilitarios;
import com.cardif.sunsystems.util.ConstantesSun;

@Controller("generacionComprobanteController")
@Scope("request")
public class GeneracionComprobanteController extends BaseController {
  public static final Logger logger = Logger.getLogger(GeneracionComprobanteController.class);

  @Autowired
  private ComprobanteRetencionService comprobanteRetencionService;

  @Autowired
  private ParametroService parametroService;

  private List<SelectItem> comboListaUnidadNegocio;
  private SimpleSelection selection;

  private String idUnidadNegocio;
  private Date fecEmisionDesde;
  private Date fecEmisionHasta;
  private String codProveedorDesde;
  private String codProveedorHasta;
  private String rucProveedor;

  private List<ComprobanteElectronicoBean> listaComprobantesElectronicos;
  private List<ComprobanteRelacionadoBean> listaComprobantesRelacionados;

  private ComprobanteElectronicoBean comprobanteElectronico;
  private String nroCertificado;
  private Integer nroCertificadosValidos;
  private Integer nroCertificadosVencidos;

  private boolean disabledBtnVerDetalle;
  private boolean disabledBtnEnviar;

  @PostConstruct
  @Override
  public String inicio() {
    if (logger.isInfoEnabled()) {
      logger.info("Inicio");
    }
    String respuesta = null;

    if (!tieneAcceso()) {
      if (logger.isDebugEnabled()) {
        logger.debug("No cuenta con los accesos necesarios.");
      }
      return "accesoDenegado";
    }

    try {
      this.comboListaUnidadNegocio = new ArrayList<SelectItem>();
      this.selection = new SimpleSelection();

      this.listaComprobantesElectronicos = new ArrayList<ComprobanteElectronicoBean>();
      this.listaComprobantesRelacionados = new ArrayList<ComprobanteRelacionadoBean>();

      // *****************************************************************************
      // ************************ CARGAS LOS VALORES INCIALES ************************
      // *****************************************************************************
      if (logger.isInfoEnabled()) {
        logger.info("Buscando la lista de UNIDADES DE NEGOCIO.");
      }
      List<Parametro> listaUnidadNegocios = parametroService.buscar(Constantes.COD_PARAM_COMPANIA, Constantes.TIP_PARAM_DETALLE);
      this.comboListaUnidadNegocio.add(new SelectItem("", "- Seleccione -"));

      for (Parametro unidadNegocio : listaUnidadNegocios) {
        if (logger.isDebugEnabled()) {
          logger.debug("UNIDAD_NEGOCIO ==> COD_VALOR: " + unidadNegocio.getCodValor() + " - NOM_VALOR: " + unidadNegocio.getNomValor());
        }
        this.comboListaUnidadNegocio.add(new SelectItem(unidadNegocio.getCodValor(), unidadNegocio.getNomValor()));
      }

      this.idUnidadNegocio = null;
      this.fecEmisionDesde = null;
      this.fecEmisionHasta = null;
      this.codProveedorDesde = null;
      this.codProveedorHasta = null;
      this.rucProveedor = null;

      this.comprobanteElectronico = null;
      this.nroCertificado = null;
      this.nroCertificadosValidos = null;
      this.nroCertificadosVencidos = null;

      /*
       * Deshabilitando los botones - Ver Detalle - Enviar
       */
      this.disabledBtnVerDetalle = true;
      this.disabledBtnEnviar = true;
    } catch (SyncconException e) {
      logger.error("SyncconException() - ERROR: " + ErrorConstants.ERROR_SYNCCON + e.getMessageComplete());
      logger.error("SyncconException() -->" + ExceptionUtils.getStackTrace(e));
      FacesMessage facesMsg = new FacesMessage(e.getSeveridad(), e.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = ErrorConstants.MSJ_ERROR;
    }
    if (logger.isInfoEnabled()) {
      logger.info("Fin");
    }
    return respuesta;
  } // inicio

  public String buscar() {
    if (logger.isInfoEnabled()) {
      logger.info("Inicio");
    }
    String respuesta = null;

    try {
      if (logger.isDebugEnabled()) {
        logger.debug(
            "################ PARAMETROS DE LA BUSQUEDA ################" + "\nUNIDAD_NEGOCIO: " + this.idUnidadNegocio + "\nFEC_EMISION_DESDE: " + this.fecEmisionDesde + "\tFEC_EMISION_HASTA: "
                + this.fecEmisionHasta + "\nCOD_PROVEEDOR_DESDE: " + this.codProveedorDesde + "\tCOD_PROVEEDOR_HASTA: " + this.codProveedorHasta + "\tRUC_PROVEEDOR: " + this.rucProveedor);
      }

      if (!fecEmisionHasta.equals(fecEmisionDesde) && !this.fecEmisionHasta.after(this.fecEmisionDesde)) {
        throw new SyncconException(ErrorConstants.COD_ERROR_COMPROBANTE_FECHA_DESDE_HASTA);
      }

      this.listaComprobantesElectronicos = comprobanteRetencionService.buscarRetenciones(this.idUnidadNegocio, this.fecEmisionDesde, this.fecEmisionHasta, this.codProveedorDesde,
          this.codProveedorHasta, this.rucProveedor, null);
      if (logger.isInfoEnabled()) {
        logger.info("Se encontraron [" + (null != this.listaComprobantesElectronicos ? this.listaComprobantesElectronicos.size() : 0) + "] comprobantes electronicos de RETENCION.");
      }

      /*
       * - Deshabilitando boton 'Ver Detalle' - Habilitando boton 'Enviar'
       */
      this.disabledBtnVerDetalle = true;
      if (null != this.listaComprobantesElectronicos && 0 < this.listaComprobantesElectronicos.size()) {
        this.disabledBtnEnviar = false;
      }
    } catch (SyncconException e) {
      logger.error("SyncconException() - " + ErrorConstants.ERROR_SYNCCON + e.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(e.getSeveridad(), e.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = ErrorConstants.MSJ_ERROR;
    }
    if (logger.isInfoEnabled()) {
      logger.info("Fin");
    }
    return respuesta;
  } // buscar

  public String seleccionarDetalleCertificadoRetencion() {
    if (logger.isInfoEnabled()) {
      logger.info("Inicio");
    }
    String respuesta = null;

    try {
      Iterator<Object> it = getSelection().getKeys();
      if (it.hasNext()) {
        int key = (Integer) it.next();

        ComprobanteElectronicoBean comprobanteElectronico = this.listaComprobantesElectronicos.get(key);
        if (logger.isInfoEnabled()) {
          logger.info("NroComprobanteElec: " + comprobanteElectronico.getEnNroComprobante());
        }

        /* Guardando el objeto */
        setComprobanteElectronico(comprobanteElectronico);
      }

      this.disabledBtnVerDetalle = false;
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));

      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = ErrorConstants.MSJ_ERROR;
    }
    if (logger.isInfoEnabled()) {
      logger.info("Fin");
    }
    return respuesta;
  } // seleccionarDetalleCertificadoRetencion

  public String cargarDetalleComprobante() {
    if (logger.isInfoEnabled()) {
      logger.info("Inicio");
    }
    String respuesta = null;

    try {
      /* Formateando la unidad de negoecio E0{UNIDAD_NEGOCIO} */
      String codUnidadNegocio = MessageFormat.format(ConstantesSun.SSC_BUSINESS_UNIT_PATTERN, this.idUnidadNegocio);

      /*
       * Extrayendo la lista de comprobantes relacionados
       */
      this.listaComprobantesRelacionados = comprobanteRetencionService.buscarDetalleRetenciones(codUnidadNegocio, getComprobanteElectronico().getEnFechaEmision(),
          getComprobanteElectronico().getCodigoProveedor(), getComprobanteElectronico().getEnProNroDocIdent(), getComprobanteElectronico().getEnNroComprobante());

      if (logger.isDebugEnabled()) {
        logger.debug("Nro. de registros en el detalle: " + (null != this.listaComprobantesRelacionados ? this.listaComprobantesRelacionados.size() : 0) + " del NRO_CERTIFICADO: "
            + getComprobanteElectronico().getEnNroComprobante());
      }
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));

      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = ErrorConstants.MSJ_ERROR;
    }
    if (logger.isInfoEnabled()) {
      logger.info("Fin");
    }
    return respuesta;
  } // cargarDetalleComprobante

  public String enviarComprobantesRetencion() {
    if (logger.isInfoEnabled()) {
      logger.info("Inicio");
    }
    String respuesta = null;

    try {
      if (null != this.listaComprobantesElectronicos && !this.listaComprobantesElectronicos.isEmpty()) {
        /*
         * Verificando que exista un registro seleccionado para realizar el ENVIO
         */
        boolean validaSeleccionCheck = checkSeleccionRegistros(this.listaComprobantesElectronicos);
        if (!validaSeleccionCheck) {
          throw new SyncconException(ErrorConstants.COD_ERROR_GEN_COMPROBANTE_NO_CHECK);
        }
        if (logger.isDebugEnabled()) {
          logger.debug("Existe al menos un REGISTRO seleccionado para ser ENVIADO.");
        }

        Map<String, Object> mapCertificadosRetencion = filtrarCertificadosRetencion(this.listaComprobantesElectronicos);
        if (logger.isDebugEnabled()) {
          logger.debug("Retornando el objeto MAP.");
        }

        @SuppressWarnings("unchecked")
        List<ComprobanteElectronicoBean> listaCertificadosValidos = (ArrayList<ComprobanteElectronicoBean>) mapCertificadosRetencion.get("listaCertificadosValidos");
        @SuppressWarnings("unchecked")
        List<ComprobanteElectronicoBean> listaCertificadosVencidos = (ArrayList<ComprobanteElectronicoBean>) mapCertificadosRetencion.get("listaCertificadosVencidos");
        
//        FIXME: Comentar cuando termine el periodo de pruebas en TEST
//        listaCertificadosValidos = listaCertificadosVencidos;

        this.nroCertificadosValidos = (null != listaCertificadosValidos ? listaCertificadosValidos.size() : 0);
        this.nroCertificadosVencidos = (null != listaCertificadosVencidos ? listaCertificadosVencidos.size() : 0);

        if (logger.isDebugEnabled()) {
          logger.debug("Lista de certificados VALIDOS: " + this.nroCertificadosValidos);
        }
        if (logger.isDebugEnabled()) {
          logger.debug("Lista de certificados VENCIDOS: " + this.nroCertificadosVencidos);
        }

        String codUnidadNegocio = MessageFormat.format(ConstantesSun.SSC_BUSINESS_UNIT_PATTERN, this.idUnidadNegocio);

        UsuarioSunat usuarioSunat = null;
        // PPLConsumer pplConsumer = null;
        ConsumerSeguros consumerSeguros = null;
        ConsumerServicios consumerServicios = null;
        if (ConstantesSun.SSC_BusinessUnit_E01.contentEquals(codUnidadNegocio)) {
          if (logger.isDebugEnabled()) {
            logger.debug("Obteniendo parametros de conexion con SUNAT para SEGUROS.");
          }

          List<Parametro> sunatDatosParam = parametroService.buscar(Constantes.COD_PARAM_SUNAT_DATOS, Constantes.TIP_PARAM_DETALLE);

          Parametro paramRUC = Utilitarios.obtenerObjetoParametro(sunatDatosParam, Constantes.USU_SUNAT_SEGUROS_RUC);
          Parametro paramUsuario = Utilitarios.obtenerObjetoParametro(sunatDatosParam, Constantes.USU_SUNAT_SEGUROS_USUARIO);
          Parametro paramClave = Utilitarios.obtenerObjetoParametro(sunatDatosParam, Constantes.USU_SUNAT_SEGUROS_CLAVE);
          Parametro paramRazonSocial = parametroService.obtener(ConstantesSun.COD_PARAM_CARDIF_RAZON_SOCIAL, Constantes.TIP_PARAM_DETALLE, Constantes.COD_CARDIF_SEGUROS);
          Parametro paramNombreComercial = parametroService.obtener(Constantes.COD_PARAM_COMPANIA, Constantes.TIP_PARAM_DETALLE, Constantes.COD_CARDIF_SEGUROS);
          Parametro paramDireccionFiscal = parametroService.obtener(ConstantesSun.COD_PARAM_CARDIF_DIRECCION, Constantes.TIP_PARAM_DETALLE, Constantes.COD_CARDIF_SEGUROS);

          usuarioSunat = new UsuarioSunat(null != paramRUC ? paramRUC.getNomValor() : null, null != paramRazonSocial ? paramRazonSocial.getNomValor() : null,
              null != paramNombreComercial ? paramNombreComercial.getNomValor() : null, null != paramDireccionFiscal ? paramDireccionFiscal.getNomValor() : null,
              null != paramUsuario ? paramUsuario.getNomValor() : null, null != paramClave ? paramClave.getNomValor() : null);
          if (logger.isDebugEnabled()) {
            logger.debug(
                "Informacion del Usuario SEGUROS" + "\nRUC: " + usuarioSunat.getRUC() + "\tRazonSocial: " + usuarioSunat.getRazonSocial() + "\tNombreComercial: " + usuarioSunat.getNombreComercial()
                    + "\nDireccionFiscal: " + usuarioSunat.getDireccionFiscal() + "\tUsuarioSOL: " + usuarioSunat.getUsuarioSOL() + "\tClaveSOL: " + usuarioSunat.getClaveSOL());
          }

          /*
           * Generando objeto consumidor de SEGUROS
           */
          consumerSeguros = new ConsumerSeguros(usuarioSunat.getRUC(), usuarioSunat.getUsuarioSOL(), usuarioSunat.getClaveSOL());
          if (logger.isDebugEnabled()) {
            logger.debug("Se genero el objeto Consumidor de SEGUROS: " + consumerSeguros);
          }
        } else if (ConstantesSun.SSC_BusinessUnit_E02.contentEquals(codUnidadNegocio)) {
          if (logger.isDebugEnabled()) {
            logger.debug("Obteniendo parametros de conexion con SUNAT para SERVICIOS.");
          }

          List<Parametro> sunatDatosParam = parametroService.buscar(Constantes.COD_PARAM_SUNAT_DATOS, Constantes.TIP_PARAM_DETALLE);

          Parametro paramRUC = Utilitarios.obtenerObjetoParametro(sunatDatosParam, Constantes.USU_SUNAT_SERVICIOS_RUC);
          Parametro paramUsuario = Utilitarios.obtenerObjetoParametro(sunatDatosParam, Constantes.USU_SUNAT_SERVICIOS_USUARIO);
          Parametro paramClave = Utilitarios.obtenerObjetoParametro(sunatDatosParam, Constantes.USU_SUNAT_SERVICIOS_CLAVE);
          Parametro paramRazonSocial = parametroService.obtener(ConstantesSun.COD_PARAM_CARDIF_RAZON_SOCIAL, Constantes.TIP_PARAM_DETALLE, Constantes.COD_CARDIF_SERVICIOS);
          Parametro paramNombreComercial = parametroService.obtener(Constantes.COD_PARAM_COMPANIA, Constantes.TIP_PARAM_DETALLE, Constantes.COD_CARDIF_SERVICIOS);
          Parametro paramDireccionFiscal = parametroService.obtener(ConstantesSun.COD_PARAM_CARDIF_DIRECCION, Constantes.TIP_PARAM_DETALLE, Constantes.COD_CARDIF_SERVICIOS);

          usuarioSunat = new UsuarioSunat(null != paramRUC ? paramRUC.getNomValor() : null, null != paramRazonSocial ? paramRazonSocial.getNomValor() : null,
              null != paramNombreComercial ? paramNombreComercial.getNomValor() : null, null != paramDireccionFiscal ? paramDireccionFiscal.getNomValor() : null,
              null != paramUsuario ? paramUsuario.getNomValor() : null, null != paramClave ? paramClave.getNomValor() : null);
          if (logger.isDebugEnabled()) {
            logger.debug(
                "Informacion del Usuario SERVICIOS" + "\nRUC: " + usuarioSunat.getRUC() + "\tRazonSocial: " + usuarioSunat.getRazonSocial() + "\tNombreComercial: " + usuarioSunat.getNombreComercial()
                    + "\nDireccionFiscal: " + usuarioSunat.getDireccionFiscal() + "\tUsuarioSOL: " + usuarioSunat.getUsuarioSOL() + "\tClaveSOL: " + usuarioSunat.getClaveSOL());
          }

          /*
           * Generando objeto consumidor de SERVICIOS
           */
          consumerServicios = new ConsumerServicios(usuarioSunat.getRUC(), usuarioSunat.getUsuarioSOL(), usuarioSunat.getClaveSOL());
          if (logger.isDebugEnabled()) {
            logger.debug("Se genero el objeto Consumidor de SERVICIOS: " + consumerServicios);
          }
        } else {
          throw new SyncconException(ErrorConstants.COD_ERROR_UND_NEGOCIO_NO_EXISTE);
        }

        /*
         * Objeto MAP para guardar la informacion del objeto de respuesta de PPL
         */
        Map<String, RespuestaPPL> mapRespuestaPPL = new HashMap<String, RespuestaPPL>();
//        GeneradorTramaHandler tramaHandler = GeneradorTramaHandler.newInstance();
        String respuestaPPLString = null;

        for (ComprobanteElectronicoBean certRetencionBean : listaCertificadosValidos) {
          String nroCertificadoRet = certRetencionBean.getEnNroComprobante();
          if (logger.isInfoEnabled()) {
            logger.info("Nro. Certificado: " + nroCertificadoRet);
          }

          Date fecEmisionCert = certRetencionBean.getEnFechaEmision();
          String codProveedorCert = certRetencionBean.getCodigoProveedor();
          String rucProveedorCert = certRetencionBean.getEnProNroDocIdent();

          if (logger.isDebugEnabled()) {
            logger.debug("[" + nroCertificadoRet + "] Buscando la lista relacionada. \nUnidNegocio: " + codUnidadNegocio + "\tFecEmision: " + fecEmisionCert + "\tCodProveedor: " + codProveedorCert
                + "\tRUCProveedor: " + rucProveedorCert);
          }
          List<ComprobanteRelacionadoBean> listaComprobantesRelacionados = comprobanteRetencionService.buscarDetalleRetenciones(codUnidadNegocio, fecEmisionCert, codProveedorCert, rucProveedorCert,
              nroCertificadoRet);

          if (logger.isInfoEnabled()) {
            logger.info("[" + nroCertificadoRet + "] Guardando la lista de comprobantes relacionados en el BEAN del Certificado de Retencion.");
          }
          certRetencionBean.setComprobanteRelacionado(listaComprobantesRelacionados);

          // String tramaEnvioPPL = tramaHandler.generaTramaRetencion(certRetencionBean, usuarioSunat);
          String tramaEnvioPPL = comprobanteRetencionService.generaTramaRetencion(codUnidadNegocio, certRetencionBean);
          if (logger.isDebugEnabled()) {
            logger.debug("[" + nroCertificadoRet + "] Se genero la trama de envio a PPL.");
          }

          if (logger.isInfoEnabled()) {
            logger.info("[" + nroCertificadoRet + "] Llamando al metodo onlineGeneration de PPL.");
          }

          // Invocacion al metodo para envio a Paperless
          if (ConstantesSun.SSC_BusinessUnit_E01.contentEquals(codUnidadNegocio)) {
            respuestaPPLString = consumerSeguros.onlineGeneration(tramaEnvioPPL, ConstantesSun.PPL_FOLIACION_MANUAL, ConstantesSun.PPL_RESP_URL_XML);
            
          } else if (ConstantesSun.SSC_BusinessUnit_E02.contentEquals(codUnidadNegocio)) {
            respuestaPPLString = consumerServicios.onlineGeneration(tramaEnvioPPL, ConstantesSun.PPL_FOLIACION_MANUAL, ConstantesSun.PPL_RESP_URL_XML);
            
          }

          if (logger.isInfoEnabled()) {
            logger.info("[" + nroCertificadoRet + "] Respuesta de PPL: " + respuestaPPLString);
          }

          /*
           * Extrayendo la respuesta de PPL
           */
          if (StringUtils.isNotBlank(respuestaPPLString)) {
            if (logger.isDebugEnabled()) {
              logger.debug("[" + nroCertificadoRet + "] Iniciando la transformacion de la resspuesta PPL a Objeto JAXB.");
            }
            JAXBContext jaxbContext = JAXBContext.newInstance(com.bnpparibas.cardif.connector.response.RespuestaPPL.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            /* Formateando la respuesta */
            respuestaPPLString = respuestaPPLString.replaceAll("&([^;]+(?!(?:\\w|;)))", "&amp;$1");
            StringReader reader = new StringReader(respuestaPPLString);

            RespuestaPPL respuestaPPL = (com.bnpparibas.cardif.connector.response.RespuestaPPL) unmarshaller.unmarshal(reader);
            if (logger.isInfoEnabled()) {
              logger.info("[" + nroCertificadoRet + "] Se transformo la respuesta de PPL a Objeto JAXB.");
            }

            /*
             * Guardando la respuesta de PPL en el MAP
             */
            mapRespuestaPPL.put(nroCertificadoRet, respuestaPPL);
            if (logger.isDebugEnabled()) {
              logger.debug("[" + nroCertificadoRet + "] Se guardo la respuesta de PPL en el MAP.");
            }
          } else {
            logger.error("No se pudo extraer la respuesta de PPL. Llego vacia o nula.");
          }
        } // for

        /**
         * Obteniendo el usuario logeado en ese instante.
         */
        String usuario = SecurityContextHolder.getContext().getAuthentication().getName();

        /*
         * Procediendo a actualizar los estados en Sunsystems y Satelite para la RESPUESTA DE PPL estados VALIDADO_FEP y RECHAZADO_FEP
         */
        if (logger.isInfoEnabled()) {
          logger.info("Actualizando los estados en Sunsystems y Satelite con la RESPUESTA DE PPL.");
        }
        for (ComprobanteElectronicoBean certRetencionBean : listaCertificadosValidos) {
          String nroCertificadoRet = certRetencionBean.getEnNroComprobante();
          RespuestaPPL respuestaPPL = mapRespuestaPPL.get(nroCertificadoRet);

          if (Constantes.RESPUESTA_PPL_VALIDADO == respuestaPPL.getCodigo()) {
            if (logger.isInfoEnabled()) {
              logger.info("El CERTIFICADO, Nro: " + nroCertificadoRet + " fue VALIDADO por PPL.");
            }
            
            boolean sunsystemsOK = comprobanteRetencionService.actualizarEstadoSunsystems(nroCertificadoRet, certRetencionBean.getNroDiario(), codUnidadNegocio,
                ConstantesSun.EST_ASIENTO_VALIDADO_FEP);

            if (sunsystemsOK) {
              if (logger.isDebugEnabled()) {
                logger.debug("[" + nroCertificadoRet + "]	Se cambio el estado a " + ConstantesSun.EST_ASIENTO_VALIDADO_FEP + " en SUNSYSTEMS.");
              }

              if (logger.isDebugEnabled()) {
                logger.debug("[" + nroCertificadoRet + "] Actualizando el estado a" + ConstantesSun.EST_ASIENTO_VALIDADO_FEP + " en SATELITE.");
              }
              boolean sateliteOK = comprobanteRetencionService.insertarComprobanteElectronico(certRetencionBean, codUnidadNegocio, ConstantesSun.EST_ASIENTO_VALIDADO_FEP, respuestaPPL.getMensaje(),
                  usuario);

              if (sateliteOK) {
                if (logger.isInfoEnabled()) {
                  logger.info("[" + nroCertificadoRet + "] Se cambio el estado a " + ConstantesSun.EST_ASIENTO_VALIDADO_FEP + " en SATELITE.");
                }
              } else {
                logger.error("[" + nroCertificadoRet + "] No se pudo cambiar el estado a " + ConstantesSun.EST_ASIENTO_VALIDADO_FEP + " en SATELITE.");
              }
            } else {
              logger.error("[" + nroCertificadoRet + "] No se pudo cambiar el estado a " + ConstantesSun.EST_ASIENTO_VALIDADO_FEP + " en SUNSYSTEMS.");
            }
          } else {
            if (logger.isInfoEnabled()) {
              logger.info("El CERTIFICADO, Nro: " + nroCertificadoRet + " fue RECHAZADO por PPL. Codigo: " + respuestaPPL.getCodigo());
            }

            boolean sunsystemsOK = comprobanteRetencionService.actualizarEstadoSunsystems(nroCertificadoRet, certRetencionBean.getNroDiario(), codUnidadNegocio,
                ConstantesSun.EST_ASIENTO_RECHAZADO_FEP);

            if (sunsystemsOK) {
              if (logger.isDebugEnabled()) {
                logger.debug("[" + nroCertificadoRet + "] Se cambio el estado a " + ConstantesSun.EST_ASIENTO_RECHAZADO_FEP + " en SUNSYSTEMS.");
              }

              if (logger.isDebugEnabled()) {
                logger.debug("[" + nroCertificadoRet + "] Actualizando el estado a" + ConstantesSun.EST_ASIENTO_RECHAZADO_FEP + " en SATELITE.");
              }
              boolean sateliteOK = comprobanteRetencionService.insertarComprobanteElectronico(certRetencionBean, codUnidadNegocio, ConstantesSun.EST_ASIENTO_RECHAZADO_FEP, respuestaPPL.getMensaje(),
                  usuario);

              if (sateliteOK) {
                if (logger.isInfoEnabled()) {
                  logger.info("[" + nroCertificadoRet + "] Se cambio el estado a " + ConstantesSun.EST_ASIENTO_RECHAZADO_FEP + " en SATELITE.");
                }
              } else {
                logger.error("[" + nroCertificadoRet + "] No se pudo cambiar el estado a " + ConstantesSun.EST_ASIENTO_RECHAZADO_FEP + " en SATELITE.");
              }
            } else {
              logger.error("[" + nroCertificadoRet + "] No se pudo cambiar el estado a " + ConstantesSun.EST_ASIENTO_RECHAZADO_FEP + " en SUNSYSTEMS.");
            }
          }
        } // for

        /*
         * Procediento a actualizar los estados en Sunsystems y Satelite para los CERTIFICADOS que se encuentran fuera de fecha (Fuera de fecha segun
         * SUNAT)
         */
        if (logger.isInfoEnabled()) {
          logger.info("Actualizando los estados en Sunsystems y Satelite a " + ConstantesSun.EST_ASIENTO_VENCIDO);
        }
        for (ComprobanteElectronicoBean certRetencionBean : listaCertificadosVencidos) {
          String nroCertificadoRet = certRetencionBean.getEnNroComprobante();
          if (logger.isDebugEnabled()) {
            logger.debug("Actualizando la informacion del Nro. Certificado: " + nroCertificadoRet);
          }
          logger.info("NRO_DIARIA: " + certRetencionBean.getNroDiario());

          boolean sunsystemsOK = comprobanteRetencionService.actualizarEstadoSunsystems(nroCertificadoRet, certRetencionBean.getNroDiario(), codUnidadNegocio, ConstantesSun.EST_ASIENTO_VENCIDO);

          if (sunsystemsOK) {
            if (logger.isDebugEnabled()) {
              logger.debug("[" + nroCertificadoRet + "] Se cambio el estado a " + ConstantesSun.EST_ASIENTO_VENCIDO + " en SUNSYSTEMS.");
            }

            if (logger.isDebugEnabled()) {
              logger.debug("[" + nroCertificadoRet + "] Actualizando el estado a " + ConstantesSun.EST_ASIENTO_VENCIDO + " en SATELITE.");
            }
            boolean sateliteOK = comprobanteRetencionService.insertarComprobanteElectronico(certRetencionBean, codUnidadNegocio, ConstantesSun.EST_ASIENTO_VENCIDO, null, usuario);

            if (sateliteOK) {
              if (logger.isInfoEnabled()) {
                logger.info("[" + nroCertificadoRet + "] Se cambio el estado a " + ConstantesSun.EST_ASIENTO_VENCIDO + " en SATELITE.");
              }
            } else {
              logger.error("[" + nroCertificadoRet + "] No se pudo cambiar el estado a " + ConstantesSun.EST_ASIENTO_VENCIDO + " en SATELITE.");
            }
          } else {
            logger.error("[" + nroCertificadoRet + "] No se pudo cambiar el estado a " + ConstantesSun.EST_ASIENTO_VENCIDO + " en SUNSYSTEMS.");
          }
        } // for

        if (logger.isDebugEnabled()) {
          logger.debug("Se culmino el proceso de ENVIO DE CERTIFICADOS DE RETENCION.");
        }

        if (logger.isDebugEnabled()) {
          logger.debug("Buscando nuevamente los registros segun los filtros ingresados.");
        }
        buscar();

        if (logger.isDebugEnabled()) {
          logger.debug("Fin de la busqueda de registros.");
        }
      } else {
        throw new SyncconException(ErrorConstants.COD_ERROR_LISTA_CERT_RETENCION_VACIA);
      }
    } catch (SyncconException e) {
      logger.error("SyncconException() - ERROR: " + ErrorConstants.ERROR_SYNCCON + e.getMessageComplete());
      logger.error("SyncconException() -->" + ExceptionUtils.getStackTrace(e));
      FacesMessage facesMsg = new FacesMessage(e.getSeveridad(), e.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = ErrorConstants.MSJ_ERROR;
    }
    if (logger.isInfoEnabled()) {
      logger.info("Fin");
    }
    return respuesta;
  } // enviarComprobantesRetencion

  /**
   * Este metodo verifica si existe al menos un registro de la lista, seleccionado.
   * 
   * @param listaComprobantesElec
   *          Lista de certificados de retencion.
   * @return Retorna un valor FLAG. (TRUE: Existe un registro seleccionado, FALSE: No existe ningun registros seleccionado)
   * @throws Exception
   */
  private boolean checkSeleccionRegistros(List<ComprobanteElectronicoBean> listaComprobantesElec) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("Inicio");
    }
    boolean flag = false;

    for (ComprobanteElectronicoBean bean : listaComprobantesElec) {
      if (bean.isSeleccion()) {
        flag = true;
        break;
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("Fin");
    }
    return flag;
  } // validandoSeleccionCheckBox

  /**
   * Este metodo genera filtra los certificados seleccionados en la INTERFAZ VISUAL y luego genera dos listas dentro de un MAP. La PRIMERA contiene la
   * lista de certificados VALIDOS (con key 'listaCertificadosValidos') y la SEGUNDA contiene la lista de certificados VENCIDOS POR EL PLAZO DADO POR
   * SUNAT (con key 'listaCertificadosVencidos').
   * 
   * @param listaCertificadosRetencion
   *          Lista de certificados de retencion retornados en la busqueda.
   * @return Retorna un MAP con dos listas de certificados (Certificados VALIDOS y Certificados VENCIDOS)
   * @throws Exception
   */
  private Map<String, Object> filtrarCertificadosRetencion(List<ComprobanteElectronicoBean> listaCertificadosRetencion) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.info("Inicio");
    }
    List<ComprobanteElectronicoBean> listaCertificadosValidos = new ArrayList<ComprobanteElectronicoBean>();
    List<ComprobanteElectronicoBean> listaCertificadosVencidos = new ArrayList<ComprobanteElectronicoBean>();

    /*
     * En este punto ya no es necesario validar si la lista de certificados tiene data o no
     */
    for (ComprobanteElectronicoBean certRetencion : listaCertificadosRetencion) {
      if (certRetencion.isSeleccion()) {
        boolean check = Utilitarios.verificarRangoDeFechas(certRetencion.getEnFechaEmision(), Calendar.getInstance().getTime(), Constantes.PLAZO_DIAS_HABILES_SUNAT);
        if (logger.isDebugEnabled()) {
          logger.debug("Nro. Certificado: " + certRetencion.getEnNroComprobante() + "\tCumple el plazo de SUNAT: " + check);
        }

        if (check) {
          listaCertificadosValidos.add(certRetencion);
        } else {
          listaCertificadosVencidos.add(certRetencion);
        }
      } else {
        if (logger.isDebugEnabled()) {
          logger.debug("Nro. Certificado: " + certRetencion.getEnNroComprobante() + " No se encuentra seleccionado.");
        }
      }
    }

    Map<String, Object> mapCertificadosRetencion = new HashMap<String, Object>();

    if (logger.isDebugEnabled()) {
      logger.debug("Guardando las lista en el MAP.");
    }
    mapCertificadosRetencion.put("listaCertificadosValidos", listaCertificadosValidos);
    mapCertificadosRetencion.put("listaCertificadosVencidos", listaCertificadosVencidos);

    if (logger.isDebugEnabled()) {
      logger.info("Fin");
    }
    return mapCertificadosRetencion;
  } // filtrarCertificadosRetencion

  public List<SelectItem> getComboListaUnidadNegocio() {
    return comboListaUnidadNegocio;
  }

  public void setComboListaUnidadNegocio(List<SelectItem> comboListaUnidadNegocio) {
    this.comboListaUnidadNegocio = comboListaUnidadNegocio;
  }

  public SimpleSelection getSelection() {
    return selection;
  }

  public void setSelection(SimpleSelection selection) {
    this.selection = selection;
  }

  public String getIdUnidadNegocio() {
    return idUnidadNegocio;
  }

  public void setIdUnidadNegocio(String idUnidadNegocio) {
    this.idUnidadNegocio = idUnidadNegocio;
  }

  public Date getFecEmisionDesde() {
    return fecEmisionDesde;
  }

  public void setFecEmisionDesde(Date fecEmisionDesde) {
    this.fecEmisionDesde = fecEmisionDesde;
  }

  public Date getFecEmisionHasta() {
    return fecEmisionHasta;
  }

  public void setFecEmisionHasta(Date fecEmisionHasta) {
    this.fecEmisionHasta = fecEmisionHasta;
  }

  public String getCodProveedorDesde() {
    return codProveedorDesde;
  }

  public void setCodProveedorDesde(String codProveedorDesde) {
    this.codProveedorDesde = codProveedorDesde;
  }

  public String getCodProveedorHasta() {
    return codProveedorHasta;
  }

  public void setCodProveedorHasta(String codProveedorHasta) {
    this.codProveedorHasta = codProveedorHasta;
  }

  public String getRucProveedor() {
    return rucProveedor;
  }

  public void setRucProveedor(String rucProveedor) {
    this.rucProveedor = rucProveedor;
  }

  public List<ComprobanteElectronicoBean> getListaComprobantesElectronicos() {
    return listaComprobantesElectronicos;
  }

  public void setListaComprobantesElectronicos(List<ComprobanteElectronicoBean> listaComprobantesElectronicos) {
    this.listaComprobantesElectronicos = listaComprobantesElectronicos;
  }

  public ComprobanteElectronicoBean getComprobanteElectronico() {
    return comprobanteElectronico;
  }

  public void setComprobanteElectronico(ComprobanteElectronicoBean comprobanteElectronico) {
    this.comprobanteElectronico = comprobanteElectronico;
  }

  public List<ComprobanteRelacionadoBean> getListaComprobantesRelacionados() {
    return listaComprobantesRelacionados;
  }

  public void setListaComprobantesRelacionados(List<ComprobanteRelacionadoBean> listaComprobantesRelacionados) {
    this.listaComprobantesRelacionados = listaComprobantesRelacionados;
  }

  public String getNroCertificado() {
    return nroCertificado;
  }

  public void setNroCertificado(String nroCertificado) {
    this.nroCertificado = nroCertificado;
  }

  public Integer getNroCertificadosValidos() {
    return nroCertificadosValidos;
  }

  public void setNroCertificadosValidos(Integer nroCertificadosValidos) {
    this.nroCertificadosValidos = nroCertificadosValidos;
  }

  public Integer getNroCertificadosVencidos() {
    return nroCertificadosVencidos;
  }

  public void setNroCertificadosVencidos(Integer nroCertificadosVencidos) {
    this.nroCertificadosVencidos = nroCertificadosVencidos;
  }

  public boolean isDisabledBtnVerDetalle() {
    return disabledBtnVerDetalle;
  }

  public void setDisabledBtnVerDetalle(boolean disabledBtnVerDetalle) {
    this.disabledBtnVerDetalle = disabledBtnVerDetalle;
  }

  public boolean isDisabledBtnEnviar() {
    return disabledBtnEnviar;
  }

  public void setDisabledBtnEnviar(boolean disabledBtnEnviar) {
    this.disabledBtnEnviar = disabledBtnEnviar;
  }

} // GeneracionComprobanteController
