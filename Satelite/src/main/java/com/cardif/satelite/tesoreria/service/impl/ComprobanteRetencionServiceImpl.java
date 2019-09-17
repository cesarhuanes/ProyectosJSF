package com.cardif.satelite.tesoreria.service.impl;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.service.ParametroService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.tesoreria.bean.ComprobanteElectronicoBean;
import com.cardif.satelite.tesoreria.bean.ComprobanteRelacionadoBean;
import com.cardif.satelite.tesoreria.bean.JournalBean;
import com.cardif.satelite.tesoreria.bean.ProveedorBean;
import com.cardif.satelite.tesoreria.bean.TipoCambioBean;
import com.cardif.satelite.tesoreria.dao.AsientoContableSecuencialMapper;
import com.cardif.satelite.tesoreria.dao.ComprobanteElectronicoMapper;
import com.cardif.satelite.tesoreria.model.AsientoContableSecuencial;
import com.cardif.satelite.tesoreria.model.ComprobanteElectronico;
import com.cardif.satelite.tesoreria.service.ComprobanteRetencionService;
import com.cardif.satelite.tesoreria.service.DiarioServices;
import com.cardif.satelite.tesoreria.service.ProveedorService;
import com.cardif.satelite.tesoreria.service.TipoCambioService;
import com.cardif.satelite.util.Utilitarios;
import com.cardif.sunsystems.util.ConstantesSun;
import com.cardif.sunsystems.util.NumberToWords;
import com.cardif.sunsystems.util.Utilidades;

@Service("comprobanteRetencionService")
public class ComprobanteRetencionServiceImpl implements ComprobanteRetencionService {
  public static final Logger logger = Logger.getLogger(ComprobanteRetencionServiceImpl.class);

  @Autowired
  private DiarioServices diarioServices = null;
  @Autowired
  private ProveedorService proveedorService = null;
  @Autowired
  private TipoCambioService tipoCambioService = null;
  @Autowired
  private ParametroService parametroService = null;

  @Autowired
  private ComprobanteElectronicoMapper comprobanteElectronicoMapper = null;

  @Autowired
  private AsientoContableSecuencialMapper asientoMaper = null;

  @Override
  public List<ComprobanteElectronicoBean> buscarRetenciones(String unidadNegocio, Date fechaDesde, Date fechaHasta, String proveedorDesde, String proveedorHasta, String rucProveedor,
      String nroComprobanteRetencion) throws SyncconException {
    if (logger.isInfoEnabled()) {
      logger.info("Inicio");
    }
    List<ComprobanteElectronicoBean> listaComprobanteElectronicos = null;
    List<JournalBean> listaCertificados = null;
    List<JournalBean> listaAsientoRetencion = null;
    Boolean proveedorEncontrado = Boolean.FALSE;
    TipoCambioBean tipoCambioBean = null;
    Parametro parametro = null;

    try {
      SimpleDateFormat sdf = new SimpleDateFormat(ConstantesSun.UTL_FORMATO_FECHA_SUNSYSTEMS);
      String fechaDesdeStr = sdf.format(fechaDesde);
      String fechaHastaStr = sdf.format(fechaHasta);
      if (logger.isDebugEnabled()) {
        logger.debug("fechaDesdeStr: " + fechaDesdeStr + " fechaHastaStr: " + fechaHastaStr);
      }

      unidadNegocio = MessageFormat.format(ConstantesSun.SSC_BUSINESS_UNIT_PATTERN, unidadNegocio);
      if (logger.isDebugEnabled()) {
        logger.debug("unidadNegocio: " + unidadNegocio);
      }

      // Realizamos la busqueda de los asientos de pago de retencion para sacar los datos de las retenciones (LISTA)
      if (unidadNegocio.contentEquals(ConstantesSun.SSC_BusinessUnit_E01)) {
        if (logger.isDebugEnabled()) {
          logger.debug("Buscando la lista de certificados de retencion en SEGUROS, RUC: " + ConstantesSun.FEL_COD_CTA_RETENCION_E01_DOL);
        }
        listaCertificados = diarioServices.buscarRetenciones(unidadNegocio, fechaDesdeStr, fechaHastaStr, ConstantesSun.FEL_COD_CTA_RETENCION_E01_SOL, ConstantesSun.FEL_COD_CTA_RETENCION_E01_DOL,
            rucProveedor, nroComprobanteRetencion);
      } else if (unidadNegocio.contentEquals(ConstantesSun.SSC_BusinessUnit_E02)) {
        if (logger.isDebugEnabled()) {
          logger.debug("Buscando la lista de certificados de retencion en SERVICIOS, RUC: " + ConstantesSun.FEL_COD_CTA_RETENCION_E01_DOL);
        }
        listaCertificados = diarioServices.buscarRetenciones(unidadNegocio, fechaDesdeStr, fechaHastaStr, ConstantesSun.FEL_COD_CTA_RETENCION_E02_BI, ConstantesSun.FEL_COD_CTA_RETENCION_E02_BI,
            rucProveedor, nroComprobanteRetencion);
      }
      if (logger.isInfoEnabled()) {
        logger.info("Nro. de CERTIFICADOS encontrados: " + (null != listaCertificados ? listaCertificados.size() : 0));
      }

      listaComprobanteElectronicos = new ArrayList<ComprobanteElectronicoBean>();

      parametro = parametroService.obtener(ConstantesSun.COD_PARAM_PORCENT_CALCULO, Constantes.TIP_PARAM_DETALLE, ConstantesSun.COD_VALOR_TASA_RETENCION);
      float porcentajeRetencion = Float.parseFloat(parametro.getNomValor());
      parametro = parametroService.obtener(ConstantesSun.COD_PARAM_COPIA_CORREO_DIGITAL, Constantes.TIP_PARAM_DETALLE, ConstantesSun.COD_VALOR_COPIA_CORREO_DIGITAL);
      String ccCorreoCliente = parametro.getNomValor();

      // Hacemos la busqueda de los datos del proveedor desde el asiento de pago, usando el Nro de Certificado de retencion indentificado
      for (JournalBean certificado : listaCertificados) {
        ComprobanteElectronicoBean comprobanteElectronicoBean = new ComprobanteElectronicoBean();

        String nroCertificado = (null != certificado.getDescripcionGeneral2() ? certificado.getDescripcionGeneral2() : certificado.getReferenciaVinculada2());

        // Datos para la cabecera
        comprobanteElectronicoBean.setEnFechaEmision(sdf.parse(certificado.getFechaTransaccion()));
        comprobanteElectronicoBean.setEnNroComprobante(nroCertificado);
        comprobanteElectronicoBean.setEnRetPerMoneda(certificado.getMoneda());
        comprobanteElectronicoBean.setEnRetPerImporteRetenidoPercibido(Double.parseDouble(Float.toString(certificado.getImporteBase())));
        String peMontoLetras = NumberToWords.convert(Double.parseDouble(Float.toString(certificado.getImporteBase()))) + " SOLES";
        comprobanteElectronicoBean.setPeMontoLetras(peMontoLetras);
        comprobanteElectronicoBean.setEnRetPerTasa(Double.parseDouble(Float.toString(porcentajeRetencion)));

        tipoCambioBean = tipoCambioService.buscarTipoCambioDia(unidadNegocio, certificado.getFechaTransaccion(), ConstantesSun.UTL_MONEDA_DOLARES, ConstantesSun.UTL_MONEDA_SOLES);
        comprobanteElectronicoBean.setPeTipoCambio(tipoCambioBean.getTasaConversion());

        logger.info("#################nroCertificado: " + nroCertificado + " diario: " + certificado.getDiario());
        comprobanteElectronicoBean.setNroDiario(certificado.getDiario() + "");

        listaAsientoRetencion = diarioServices.buscarDetalleRetenciones(unidadNegocio, certificado.getFechaTransaccion(), nroCertificado);
        if (logger.isInfoEnabled()) {
          logger.info("Nro. de registro de DETALLE encontrados: " + (null != listaAsientoRetencion ? listaAsientoRetencion.size() : 0) + " del CERTIFICADO: " + nroCertificado);
        }

        proveedorEncontrado = Boolean.FALSE;

        for (JournalBean asientoRetencion : listaAsientoRetencion) {
          // Bloque para la linea de la cuenta de bancos del asiento de pago, sin importar la empresa
          if (asientoRetencion.getCodigoCuenta().substring(0, 2).contentEquals("10")) {
            // El importe pagado sale de la linea de la cuenta 10, importe Base.
            comprobanteElectronicoBean.setEnRetPerImportePagadoCobrado(Double.parseDouble(Float.toString(asientoRetencion.getImporteBase())));
          }

          if (((unidadNegocio.contentEquals(ConstantesSun.SSC_BusinessUnit_E01) && asientoRetencion.getCodigoCuenta().charAt(0) == 'P')
              || (unidadNegocio.contentEquals(ConstantesSun.SSC_BusinessUnit_E02) && asientoRetencion.getCodigoCuenta().substring(0, 2).contentEquals("42"))) && !proveedorEncontrado) {
            comprobanteElectronicoBean.setEnProNroDocIdent(asientoRetencion.getRuc());
            ProveedorBean proveedorBean = proveedorService.buscarProveedor(unidadNegocio, asientoRetencion.getCodigoCuenta(), asientoRetencion.getRuc());

            // SUNAT no esta solicitando de forma obligatoria los datos "Domicilio fiscal del Proveedor (R) o Cliente (P)", por eso vamos a enviar
            // vacio estos datos.
            // comprobanteElectronicoBean.setEnProDomDepartamento(null != proveedorBean ? proveedorBean.getNomProveedor() : "");
            // comprobanteElectronicoBean.setEnProDomDireccionCompleta(null != proveedorBean ? proveedorBean.getDireccion() : "");
            // comprobanteElectronicoBean.setEnProDomDistrito(null != proveedorBean ? proveedorBean.getDistrito() : "");
            // comprobanteElectronicoBean.setEnProDomPais(null != proveedorBean ? proveedorBean.getPais() : "");
            // comprobanteElectronicoBean.setEnProDomProvincia(null != proveedorBean ? proveedorBean.getProvincia() : "");
            // comprobanteElectronicoBean.setEnProDomUbigeo(null != proveedorBean ? proveedorBean.getUbigeo() : "");
            // comprobanteElectronicoBean.setEnProDomUrbanizacion("");

            // Domicilio fiscal del Proveedor (R) o Cliente (P)
            comprobanteElectronicoBean.setEnProDomDepartamento("");
            comprobanteElectronicoBean.setEnProDomDireccionCompleta("");
            comprobanteElectronicoBean.setEnProDomDistrito("");
            comprobanteElectronicoBean.setEnProDomPais("");
            comprobanteElectronicoBean.setEnProDomProvincia("");
            comprobanteElectronicoBean.setEnProDomUbigeo("");
            comprobanteElectronicoBean.setEnProDomUrbanizacion("");

            // Información del Proveedor (R) o Cliente (P)
            comprobanteElectronicoBean.setEnProNombreComercial("");
            comprobanteElectronicoBean.setEnProRazonSocial(null != proveedorBean.getNomProveedor() ? proveedorBean.getNomProveedor() : "");
            comprobanteElectronicoBean.setEnProTipoDocIdent("");
            proveedorEncontrado = Boolean.TRUE;

            // Campos Personalizados
            comprobanteElectronicoBean.setPeCorreoCliente(null != proveedorBean.getCorreoElectronico() ? proveedorBean.getCorreoElectronico() + ", " + ccCorreoCliente : ccCorreoCliente);

          }
        }

        listaComprobanteElectronicos.add(comprobanteElectronicoBean);
      }
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
    }
    if (logger.isInfoEnabled()) {
      logger.info("Fin");
    }
    return listaComprobanteElectronicos;
  } // buscarRetenciones

  @Override
  public List<ComprobanteRelacionadoBean> buscarDetalleRetenciones(String unidadNegocio, Date fechaEmision, String codProveedor, String rucProveedor, String nroComprobanteRetencion)
      throws SyncconException {
    if (logger.isInfoEnabled()) {
      logger.info("Inicio");
    }
    List<ComprobanteRelacionadoBean> listaFacturaRelacionado = new ArrayList<ComprobanteRelacionadoBean>();
    List<ComprobanteRelacionadoBean> listaNotaCreditoRelacionado = new ArrayList<ComprobanteRelacionadoBean>();
    List<JournalBean> listaAsientoRetencion = null;
    TipoCambioBean tipoCambioBean = null;
    Parametro parametro = null;

    try {
      SimpleDateFormat sdf = new SimpleDateFormat(ConstantesSun.UTL_FORMATO_FECHA_SUNSYSTEMS);
      String fechaDesdeStr = sdf.format(fechaEmision);
      if (logger.isDebugEnabled()) {
        logger.debug("fechaEmision: " + fechaEmision);
      }

      listaAsientoRetencion = diarioServices.buscarDetalleRetenciones(unidadNegocio, fechaDesdeStr, nroComprobanteRetencion);
      logger.info("Cantidad de filas para el certificado " + nroComprobanteRetencion + ": " + listaAsientoRetencion.size());

      for (JournalBean asientoRetencion : listaAsientoRetencion) {

        // Bloque para la cuenta de proveedor, datos del proveedor del asiento de pago
        if ((unidadNegocio.contentEquals(ConstantesSun.SSC_BusinessUnit_E01) && asientoRetencion.getCodigoCuenta().charAt(0) == 'P')
            || (unidadNegocio.contentEquals(ConstantesSun.SSC_BusinessUnit_E02) && asientoRetencion.getCodigoCuenta().substring(0, 2).contentEquals("42"))) {
          ComprobanteRelacionadoBean comprobanteRelacionadoBean = new ComprobanteRelacionadoBean();
          ProveedorBean proveedorBean = proveedorService.buscarProveedor(unidadNegocio, asientoRetencion.getCodigoCuenta(), asientoRetencion.getRuc());
          logger.info("Proveedor encontrado: " + proveedorBean.getNomProveedor());

          comprobanteRelacionadoBean.setCrTipoComprobante(asientoRetencion.getComprobanteSunat());
          comprobanteRelacionadoBean.setCrNroComprobante(asientoRetencion.getReferenciaTransaccion());

          JournalBean journalBean = diarioServices.buscarProvision(unidadNegocio, proveedorBean.getCodigoCtaProveedor(), proveedorBean.getRuc(), asientoRetencion.getReferenciaTransaccion());
          // La fecha de emision del comprobante relacionado la sacamos de la fecha de transaccion de la provision
          if (null != journalBean.getFechaTransaccion()) {
            comprobanteRelacionadoBean.setCrFechaEmision(sdf.parse(journalBean.getFechaTransaccion()));
          } else {
            logger.info("No se encontro fecha de emision para el comprobanten relacionado");
          }
          comprobanteRelacionadoBean.setCrTipoMoneda(asientoRetencion.getMoneda());
          comprobanteRelacionadoBean.setCrImporteTotal(Float.toString(asientoRetencion.getImporteTransaccion()));
          comprobanteRelacionadoBean.setImporteBase(Float.toString(asientoRetencion.getImporteBase()));
          comprobanteRelacionadoBean.setPcFechaPagoCobro(sdf.parse(asientoRetencion.getFechaTransaccion()));
          comprobanteRelacionadoBean.setPcNroPagoCobro(asientoRetencion.getDiario() + asientoRetencion.getLineaDiario());
          comprobanteRelacionadoBean.setPcTipoMoneda(asientoRetencion.getMoneda());
          comprobanteRelacionadoBean.setRpMonedaRetPer(ConstantesSun.UTL_MONEDA_SOLES);
          comprobanteRelacionadoBean.setRpFechaRetPer(sdf.parse(asientoRetencion.getFechaTransaccion()));
          comprobanteRelacionadoBean.setRpMonedaNetPagoCobro(ConstantesSun.UTL_MONEDA_SOLES);

          String fechaTransaccion = asientoRetencion.getFechaTransaccion();
          if (asientoRetencion.getMoneda().contentEquals(ConstantesSun.UTL_MONEDA_DOLARES)) {
            tipoCambioBean = tipoCambioService.buscarTipoCambioDia(unidadNegocio, fechaTransaccion, ConstantesSun.UTL_MONEDA_DOLARES, ConstantesSun.UTL_MONEDA_SOLES);
            comprobanteRelacionadoBean.setTcTasaCambio(Double.parseDouble(tipoCambioBean.getTasaConversion()));
          } else {
            comprobanteRelacionadoBean.setTcTasaCambio((double) 1.00);
          }
          comprobanteRelacionadoBean.setTcMonedaOrigen(asientoRetencion.getMoneda());
          comprobanteRelacionadoBean.setTcMonedaDestino(ConstantesSun.UTL_MONEDA_SOLES);
          comprobanteRelacionadoBean.setTcFechaCambio(sdf.parse(fechaTransaccion));

          if (ConstantesSun.FEL_TIP_COMP_FACTURA.contentEquals(comprobanteRelacionadoBean.getCrTipoComprobante())) {
            listaFacturaRelacionado.add(comprobanteRelacionadoBean);
          } else if (ConstantesSun.FEL_TIP_COMP_NOTA_CREDITO.contentEquals(comprobanteRelacionadoBean.getCrTipoComprobante())) {
            comprobanteRelacionadoBean.setRevisado(Boolean.FALSE);
            listaNotaCreditoRelacionado.add(comprobanteRelacionadoBean);
          }

        }
      }

      // Seccion para realizar los calculos con las notas de credito existentes (redondeado a 2 decimales)

      parametro = parametroService.obtener(ConstantesSun.COD_PARAM_PORCENT_CALCULO, Constantes.TIP_PARAM_DETALLE, ConstantesSun.COD_VALOR_TASA_RETENCION);
      float porcentaRetencion = Float.parseFloat(parametro.getNomValor());

      for (ComprobanteRelacionadoBean factura : listaFacturaRelacionado) {
        float importeCalculoTransaccion = Float.parseFloat(factura.getCrImporteTotal());
        float importeCalculoBase = Float.parseFloat(factura.getImporteBase());

        for (ComprobanteRelacionadoBean notaCredito : listaNotaCreditoRelacionado) {
          float importeNotaTransaccion = Float.parseFloat(notaCredito.getCrImporteTotal());
          float importeNotaBase = Float.parseFloat(notaCredito.getImporteBase());

          if (!notaCredito.getRevisado() && (importeCalculoTransaccion > importeNotaTransaccion)) {
            importeCalculoTransaccion = importeCalculoTransaccion - importeNotaTransaccion;
            importeCalculoBase = importeCalculoBase - importeNotaBase;
            // Se aplico la nota de credito
            notaCredito.setRevisado(Boolean.TRUE);
            break;
          }
        }

        //Double importePagoSinRetencion = Double.parseDouble(Float.toString(Utilidades.redondear(2, ((importeCalculoTransaccion) * (100 - porcentaRetencion)) / 100)));
        Double importePagoSinRetencion = Double.parseDouble(Float.toString(Utilidades.redondear(2, importeCalculoTransaccion)));
        factura.setPcImportePagoSinRetCob(importePagoSinRetencion);
        
        float importeRetenidoBase = Utilidades.redondear(2, ((importeCalculoBase) * porcentaRetencion) / 100);
        Double importeRetenido = Double.parseDouble(Float.toString(importeRetenidoBase));
        factura.setRpImporteRetPer(importeRetenido);
        
//        Double importePagoRetencion = Double.parseDouble(Float.toString(Utilidades.redondear(2, ((importeCalculoTransaccion) * (100 - porcentaRetencion)) / 100)));
        Double importePagoRetencion = Double.parseDouble(Float.toString(importeCalculoTransaccion - importeRetenidoBase));
         
        //Double importeNetoPagoCobroCalculo = importePagoSinRetencion * factura.getTcTasaCambio();
        Double importeNetoPagoCobroCalculo = importePagoRetencion * factura.getTcTasaCambio();
        
        float importeNetoPagoCobro = Utilidades.redondear(2, (float) importeNetoPagoCobroCalculo.doubleValue());
        factura.setRpImporteNetPagoCobro(Double.parseDouble(Float.toString(importeNetoPagoCobro)));

      }

      // Colocamos al final todas las notas de credito
      for (ComprobanteRelacionadoBean comprobanteRelacionadoBean : listaNotaCreditoRelacionado) {
        listaFacturaRelacionado.add(comprobanteRelacionadoBean);
      }

    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
    }
    return listaFacturaRelacionado;
  } // buscarDetalleRetenciones

  @Override
  public String generaTramaRetencion(String unidadNegocio, ComprobanteElectronicoBean comprobanteElectronicoBean) throws SyncconException {
    if (logger.isInfoEnabled()) {
      logger.info("Inicio");
    }

    Parametro parametro = null;
    String enEmiNroDocIdent = null;
    String enEmiNombreComercial = null;
    String enEmiRazonSocial = null;
    String enEmiDomDireccionCompleta = null;
    String enEmiDomUrbanizacion = "";
    String observaciones = "";
    String enFechaEmision = null;
    String enProTipoDocIdent = "";
    String enProNombreComercial = "";
    String crFechaEmision = null;
    String pcFechaPago = null;
    String rpFechaRetencion = null;
    String tcFechaTipoCambio = null;
    String peCorreoCliente = "";
    String peTipoCambio = "";
    String pePagWeb = "";
    List<ComprobanteRelacionadoBean> listComprobanteRelacionado;

    String tramaRetencion = "";
    String encabezadoEN = null;
    String comprobanteRelacionadoCR = null;
    String datosPagoCobroPC = null;
    String datosRetencionPercepcionRP = null;
    String datosTipoCambioTC = null;
    String datosPersonalizadosPE = null;

    if (logger.isDebugEnabled()) {
      logger.debug("Unidad de Negocio: " + unidadNegocio);
    }

    if (unidadNegocio.contentEquals(ConstantesSun.SSC_BusinessUnit_E01)) {
      enEmiNroDocIdent = Constantes.RUC_CARDIF_SEGUROS;
      parametro = parametroService.obtener(Constantes.COD_PARAM_COMPANIA, Constantes.TIP_PARAM_DETALLE, Constantes.COD_CARDIF_SEGUROS);
      enEmiNombreComercial = parametro.getNomValor();
      parametro = parametroService.obtener(ConstantesSun.COD_PARAM_CARDIF_RAZON_SOCIAL, Constantes.TIP_PARAM_DETALLE, Constantes.COD_CARDIF_SEGUROS);
      enEmiRazonSocial = parametro.getNomValor();
      parametro = parametroService.obtener(ConstantesSun.COD_PARAM_CARDIF_DIRECCION, Constantes.TIP_PARAM_DETALLE, Constantes.COD_CARDIF_SEGUROS);
      enEmiDomDireccionCompleta = parametro.getNomValor();
      parametro = parametroService.obtener(ConstantesSun.COD_PARAM_URL_COMPRO_DIGITAL, Constantes.TIP_PARAM_DETALLE, ConstantesSun.COD_VALOR_URL_COMPRO_SEGUR);
      pePagWeb = parametro.getNomValor();

    } else if (unidadNegocio.contentEquals(ConstantesSun.SSC_BusinessUnit_E02)) {
      enEmiNroDocIdent = Constantes.RUC_CARDIF_SERVICIOS;
      parametro = parametroService.obtener(Constantes.COD_PARAM_COMPANIA, Constantes.TIP_PARAM_DETALLE, Constantes.COD_CARDIF_SERVICIOS);
      enEmiNombreComercial = parametro.getNomValor();
      parametro = parametroService.obtener(ConstantesSun.COD_PARAM_CARDIF_RAZON_SOCIAL, Constantes.TIP_PARAM_DETALLE, Constantes.COD_CARDIF_SERVICIOS);
      enEmiRazonSocial = parametro.getNomValor();
      parametro = parametroService.obtener(ConstantesSun.COD_PARAM_CARDIF_DIRECCION, Constantes.TIP_PARAM_DETALLE, Constantes.COD_CARDIF_SERVICIOS);
      enEmiDomDireccionCompleta = parametro.getNomValor();
      parametro = parametroService.obtener(ConstantesSun.COD_PARAM_URL_COMPRO_DIGITAL, Constantes.TIP_PARAM_DETALLE, ConstantesSun.COD_VALOR_URL_COMPRO_SERVI);
      pePagWeb = parametro.getNomValor();

    }

    if (logger.isDebugEnabled()) {
      logger.debug("Ruc: " + enEmiNroDocIdent);
      logger.debug("Nombre Comercial: " + enEmiNombreComercial);
      logger.debug("Razon Social: " + enEmiRazonSocial);
      logger.debug("Direccion: " + enEmiDomDireccionCompleta);
      logger.debug("Pagina Web Comprobante Digital: " + pePagWeb);
    }

    enFechaEmision = Utilitarios.convertirFechaACadena(comprobanteElectronicoBean.getEnFechaEmision(), ConstantesSun.UTL_FORMATO_FECHA_PAPERLESS);
    // Preparamos los datos el encabezado "EN"
    if (comprobanteElectronicoBean.getEnProTipoDocIdent() == null || comprobanteElectronicoBean.getEnProTipoDocIdent().equals("")) {
      enProTipoDocIdent = ConstantesSun.FEL_TIP_DOC_IDENT_RUC;
    }

    if (comprobanteElectronicoBean.getEnProNombreComercial() == null || comprobanteElectronicoBean.getEnProNombreComercial().equals("")) {
      enProNombreComercial = comprobanteElectronicoBean.getEnProRazonSocial();
    }

    encabezadoEN = "EN|" + ConstantesSun.FEL_TIP_COMP_RETENCION + "|" + comprobanteElectronicoBean.getEnNroComprobante() + "|" + enFechaEmision + "|" + enEmiNroDocIdent + "|"
        + ConstantesSun.FEL_TIP_DOC_IDENT_RUC + "|" + enEmiNombreComercial + "|" + enEmiRazonSocial + "|" + ConstantesSun.UTL_UBIGEO_CARDIF_COD + "|" + enEmiDomDireccionCompleta + "|"
        + enEmiDomUrbanizacion + "|" + ConstantesSun.UTL_UBIGEO_CARDIF_PROVINCIA + "|" + ConstantesSun.UTL_UBIGEO_CARDIF_DEPARTAMENTO + "|" + ConstantesSun.UTL_UBIGEO_CARDIF_DISTRITO + "|"
        + ConstantesSun.UTL_UBIGEO_CARDIF_PAIS + "|" + comprobanteElectronicoBean.getEnProNroDocIdent() + "|" + enProTipoDocIdent + "|" + enProNombreComercial + "|"
        + comprobanteElectronicoBean.getEnProRazonSocial() + "|" + comprobanteElectronicoBean.getEnProDomUbigeo() + "|" + comprobanteElectronicoBean.getEnProDomDireccionCompleta() + "|"
        + comprobanteElectronicoBean.getEnProDomUrbanizacion() + "|" + comprobanteElectronicoBean.getEnProDomProvincia() + "|" + comprobanteElectronicoBean.getEnProDomDepartamento() + "|"
        + comprobanteElectronicoBean.getEnProDomDistrito() + "|" + comprobanteElectronicoBean.getEnProDomPais() + "|" + ConstantesSun.FEL_COD_REG_RETENCION + "|"
        + comprobanteElectronicoBean.getEnRetPerTasa() + "|" + observaciones + "|" + comprobanteElectronicoBean.getEnRetPerImporteRetenidoPercibido() + "|" + ConstantesSun.UTL_MONEDA_SOLES + "|"
        + comprobanteElectronicoBean.getEnRetPerImportePagadoCobrado() + "|" + ConstantesSun.UTL_MONEDA_SOLES;

    tramaRetencion = encabezadoEN + "\n";

    if (logger.isDebugEnabled()) {
      logger.debug("Encabezado EN: " + encabezadoEN);
    }

    listComprobanteRelacionado = comprobanteElectronicoBean.getComprobanteRelacionado();
    for (ComprobanteRelacionadoBean comprobanteRelacionadoBean : listComprobanteRelacionado) {

      // Preparamos Dato del Comprobante Relacionado "CR"
      crFechaEmision = Utilitarios.convertirFechaACadena(comprobanteRelacionadoBean.getCrFechaEmision(), ConstantesSun.UTL_FORMATO_FECHA_PAPERLESS);
      String[] crNroComprobanteSerie = comprobanteRelacionadoBean.getCrNroComprobante().split("-");
      String crSerieNroComprobanteRelacionado = "";

      if (crNroComprobanteSerie.length > 0) {
        
        //Cesar Prado 2016-07-13
        
        String primeraLetraComprobSerie = crNroComprobanteSerie[0].substring(0,1);
        String TodoelComprobSeriedesde1 = crNroComprobanteSerie[0].substring(1);
        
     // crNroComprobanteSerie[0] = Utilidades.completarCeros(crNroComprobanteSerie[0], "4");      
        crNroComprobanteSerie[0] = primeraLetraComprobSerie + Utilidades.completarCeros(TodoelComprobSeriedesde1, ConstantesSun.FEL_CDP_LONG__SERIE);
       
      //Fin Cesar Prado 2016-07-13
        
        crNroComprobanteSerie[1] = Utilidades.completarCeros(crNroComprobanteSerie[1], ConstantesSun.FEL_CDP_LONG_CORR);
        crSerieNroComprobanteRelacionado = crNroComprobanteSerie[0] + '-' + crNroComprobanteSerie[1];
      } else {
        crSerieNroComprobanteRelacionado = "";
      }

      comprobanteRelacionadoCR = "CR|" + comprobanteRelacionadoBean.getCrTipoComprobante() + "|" + crSerieNroComprobanteRelacionado + "|" + crFechaEmision + "|"
          + comprobanteRelacionadoBean.getCrImporteTotal() + "|" + comprobanteRelacionadoBean.getCrTipoMoneda();

      tramaRetencion = tramaRetencion + comprobanteRelacionadoCR + "\n";

      if (logger.isDebugEnabled()) {
        logger.debug("Comprobante Relacionado CR: " + comprobanteRelacionadoCR);
      }
      
      if (!ConstantesSun.FEL_TIP_COMP_NOTA_CREDITO.contentEquals(comprobanteRelacionadoBean.getCrTipoComprobante())) {
        // Preparamos Datos del Pago (R) o Cobro (P) "PC"
        pcFechaPago = Utilitarios.convertirFechaACadena(comprobanteRelacionadoBean.getPcFechaPagoCobro(), ConstantesSun.UTL_FORMATO_FECHA_PAPERLESS);
        datosPagoCobroPC = "PC|" + pcFechaPago + "|" + comprobanteRelacionadoBean.getPcNroPagoCobro() + "|" + comprobanteRelacionadoBean.getPcImportePagoSinRetCob() + "|"
            + comprobanteRelacionadoBean.getPcTipoMoneda();

        tramaRetencion = tramaRetencion + datosPagoCobroPC + "\n";

        if (logger.isDebugEnabled()) {
          logger.debug("Datos Pago Cobro PC: " + datosPagoCobroPC);
        }

        // Preparamos Datos de la Retención o Percepción "RP"
        rpFechaRetencion = Utilitarios.convertirFechaACadena(comprobanteRelacionadoBean.getRpFechaRetPer(), ConstantesSun.UTL_FORMATO_FECHA_PAPERLESS);
        datosRetencionPercepcionRP = "RP|" + comprobanteRelacionadoBean.getRpImporteRetPer() + "|" + comprobanteRelacionadoBean.getRpMonedaRetPer() + "|" + rpFechaRetencion + "|"
            + comprobanteRelacionadoBean.getRpImporteNetPagoCobro() + "|" + comprobanteRelacionadoBean.getRpMonedaNetPagoCobro();

        tramaRetencion = tramaRetencion + datosRetencionPercepcionRP + "\n";

        if (logger.isDebugEnabled()) {
          logger.debug("Datos Retencion Percepcion RP: " + datosRetencionPercepcionRP);
        }

        
      }

      // Preparamos Tipo de cambio "TC"
      tcFechaTipoCambio = Utilitarios.convertirFechaACadena(comprobanteRelacionadoBean.getTcFechaCambio(), ConstantesSun.UTL_FORMATO_FECHA_PAPERLESS);
      datosTipoCambioTC = "TC|" + comprobanteRelacionadoBean.getTcMonedaOrigen() + "|" + comprobanteRelacionadoBean.getTcMonedaDestino() + "|" + comprobanteRelacionadoBean.getTcTasaCambio() + "|"
          + tcFechaTipoCambio;

      tramaRetencion = tramaRetencion + datosTipoCambioTC + "\n";

      if (logger.isDebugEnabled()) {
        logger.debug("Datos Tipo Cambio TC: " + datosTipoCambioTC);
      }

    }

    // Preparamos Personalizados "PE"
    if (comprobanteElectronicoBean.getPeCorreoCliente() != null && !comprobanteElectronicoBean.getPeCorreoCliente().equals("")) {
      peCorreoCliente = comprobanteElectronicoBean.getPeCorreoCliente();
    }

    if (comprobanteElectronicoBean.getPeTipoCambio() != null && !comprobanteElectronicoBean.getPeTipoCambio().equals("")) {
      peTipoCambio = comprobanteElectronicoBean.getPeTipoCambio();
    }

    datosPersonalizadosPE = "PE|CorreoCliente|" + peCorreoCliente + "\nPE|TipoCambio|" + peTipoCambio + "\nPE|MontoLetras|" + comprobanteElectronicoBean.getPeMontoLetras() + "\nPE|PagWeb|" + pePagWeb;

    tramaRetencion = tramaRetencion + datosPersonalizadosPE + "\n";

    if (logger.isDebugEnabled()) {
      logger.debug("Datos Personalizados PE: " + datosPersonalizadosPE);
      logger.debug("################# INICIO: Trama que se enviara a PAPERLESSS #################: ");
      logger.debug(tramaRetencion);
      logger.debug("################# FIN: Trama que se enviara a PAPERLESSS #################: ");
    }

    return tramaRetencion;
  }

  @Override
  public List<ComprobanteElectronicoBean> buscarRetencionesEmitidas(String unidadNegocio, String nroDiario, String nroComprobanteRetencion, Date fechaDesde, Date fechaHasta, String proveedor,
      String rucProveedor) {
    if (logger.isInfoEnabled()) {
      logger.info("Inicio");
    }
    List<ComprobanteElectronicoBean> listaComprobanteElectronicos = null;
    List<JournalBean> listaCertificados = null;
    List<JournalBean> listaAsientoRetencion = null;

    try {
      SimpleDateFormat sdf = new SimpleDateFormat(ConstantesSun.UTL_FORMATO_FECHA_SUNSYSTEMS);

      String fechaDesdeStr = sdf.format(fechaDesde);
      String fechaHastaStr = sdf.format(fechaHasta);
      if (logger.isDebugEnabled()) {
        logger.debug("fechaDesdeStr: " + fechaDesdeStr + " fechaHastaStr: " + fechaHastaStr);
      }

      unidadNegocio = MessageFormat.format("E0{0}", unidadNegocio);
      if (logger.isDebugEnabled()) {
        logger.debug("unidadNegocio: " + unidadNegocio);
      }

      // Realizamos la busqueda de los asientos de pago de retencion para sacar los datos de las retenciones (LISTA)
      if (unidadNegocio.contentEquals(ConstantesSun.SSC_BusinessUnit_E01)) {
        listaCertificados = diarioServices.buscarRetencionesEmitidas(unidadNegocio, nroDiario, fechaDesdeStr, fechaHastaStr, ConstantesSun.FEL_COD_CTA_RETENCION_E01_SOL,
            ConstantesSun.FEL_COD_CTA_RETENCION_E01_DOL, rucProveedor, nroComprobanteRetencion);
      } else if (unidadNegocio.contentEquals(ConstantesSun.SSC_BusinessUnit_E02)) {
        listaCertificados = diarioServices.buscarRetencionesEmitidas(unidadNegocio, nroDiario, fechaDesdeStr, fechaHastaStr, ConstantesSun.FEL_COD_CTA_RETENCION_E02_BI,
            ConstantesSun.FEL_COD_CTA_RETENCION_E02_BI, rucProveedor, nroComprobanteRetencion);
      }
      logger.info("Cantida de Certificados encontrados: " + listaCertificados.size());
      listaComprobanteElectronicos = new ArrayList<ComprobanteElectronicoBean>();

      // Hacemos la busqueda de los datos del proveedor desde el asiento de pago, usando el Nro de Certificado de retencion indentificado
      for (JournalBean certificado : listaCertificados) {
        String nroCertificado = certificado.getReferenciaVinculada2();
        ComprobanteElectronicoBean comprobanteElectronicoBean = new ComprobanteElectronicoBean();

        // Datos para la cabecera
        comprobanteElectronicoBean.setEnFechaEmision(sdf.parse(certificado.getFechaTransaccion()));
        comprobanteElectronicoBean.setEnNroComprobante(nroCertificado);
        comprobanteElectronicoBean.setEnRetPerMoneda(certificado.getMoneda());
        comprobanteElectronicoBean.setEnRetPerImporteRetenidoPercibido(Double.parseDouble(Float.toString(certificado.getImporteBase())));
        String peMontoLetras = NumberToWords.convert(Double.parseDouble(Float.toString(certificado.getImporteBase()))) + " SOLES";
        comprobanteElectronicoBean.setPeMontoLetras(peMontoLetras);
        comprobanteElectronicoBean.setNroDiario(String.valueOf(certificado.getDiario()));

        listaAsientoRetencion = diarioServices.buscarDetalleRetenciones(unidadNegocio, certificado.getFechaTransaccion(), nroCertificado);
        logger.info("Cantidad de filas para el certificado " + nroCertificado + ": " + listaAsientoRetencion.size());

        for (JournalBean asientoRetencion : listaAsientoRetencion) {
          // Bloque para la linea de la cuenta de bancos del asiento de pago, sin importar la empresa
          if (asientoRetencion.getCodigoCuenta().substring(0, 2).contentEquals("10")) {
            // El importe paagado sale de la linea de la cuenta 10.
            comprobanteElectronicoBean.setEnRetPerImportePagadoCobrado(Double.parseDouble(Float.toString(asientoRetencion.getImporteTransaccion())));

          }
          if ((unidadNegocio.contentEquals(ConstantesSun.SSC_BusinessUnit_E01) && asientoRetencion.getCodigoCuenta().charAt(0) == 'P')
              || (unidadNegocio.contentEquals(ConstantesSun.SSC_BusinessUnit_E02) && asientoRetencion.getCodigoCuenta().substring(0, 2).contentEquals("42"))) {
            comprobanteElectronicoBean.setEnProNroDocIdent(asientoRetencion.getRuc());
            ProveedorBean proveedorBean = proveedorService.buscarProveedor(unidadNegocio, asientoRetencion.getCodigoCuenta(), asientoRetencion.getRuc());
            comprobanteElectronicoBean.setEnProRazonSocial(proveedorBean.getNomProveedor());
            break;
          }
        }

        listaComprobanteElectronicos.add(comprobanteElectronicoBean);
      }
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
    }
    return listaComprobanteElectronicos;
  } // buscarRetencionesEmitidas

  @Override
  public String generaTramaReversion(String unidadNegocio, List<ComprobanteElectronicoBean> listComprobanteElectronicoBean) throws SyncconException {
    // ESTRUCTURA DEL ARCHIVO DE TEXTO
    // RAZONSOCIAL|RUC|TIPODOCID|FECHAEMISIONCRE|TIPODOC|SERIE|CORRELATIVO|MOTIVO|NUMITEM|FECHAGENERACION
    if (logger.isInfoEnabled()) {
      logger.info("Inicio");
    }
    String tramaReversion = "";
    Date fechaGeneracion = new Date();
    String fechaGeneracionStr = "";
    int nroItem = 0;

    unidadNegocio = MessageFormat.format("E0{0}", unidadNegocio);
    if (logger.isDebugEnabled()) {
      logger.debug("Unidad de Negocio: " + unidadNegocio);
    }

    fechaGeneracionStr = Utilitarios.convertirFechaACadena(fechaGeneracion, ConstantesSun.UTL_FORMATO_FECHA_PAPERLESS);

    for (ComprobanteElectronicoBean comprobanteElectronicoBean : listComprobanteElectronicoBean) {
      String[] crNroComprobanteSerie = comprobanteElectronicoBean.getEnNroComprobante().split("-");
      String serieCertificado = "";
      String correlativoCertificado = "";
      nroItem++;
      if (fechaGeneracion.after(comprobanteElectronicoBean.getEnFechaEmision())) {
        if (crNroComprobanteSerie.length > 0) {
          serieCertificado = Utilidades.completarCeros(crNroComprobanteSerie[0], "4");
          correlativoCertificado = Utilidades.completarCeros(crNroComprobanteSerie[1], "8");
        }

        tramaReversion = comprobanteElectronicoBean.getEnProRazonSocial() + "|" + comprobanteElectronicoBean.getEnProNroDocIdent() + "|" + comprobanteElectronicoBean.getEnProTipoDocIdent() + "|"
            + comprobanteElectronicoBean.getEnFechaEmision() + "|" + comprobanteElectronicoBean.getEnTipoComprobante() + "|" + serieCertificado + "|" + correlativoCertificado + "|"
            + comprobanteElectronicoBean.getMotivoAnulacion() + "|" + nroItem + "|" + fechaGeneracionStr + "\n";

      } else {
        // Salimos del bucle ya que la fecha de emision del compobante esta fuera del rango permitido
        tramaReversion = "";
        break;
      }

    }
    if (logger.isDebugEnabled()) {
      logger.debug("TramaReversion: " + tramaReversion);
    }

    return tramaReversion;
  }

  @Override
  public Boolean anularRetencion(String unidadNegocio, ComprobanteElectronico comprobanteElectronico) throws SyncconException {
    Boolean respuestaActualizacion = Boolean.FALSE;
    Boolean respuesta = Boolean.FALSE;
    if (logger.isInfoEnabled()) {
      logger.info("Inicio");
    }

    String nroDiario = comprobanteElectronico.getNroDiario();
    String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
    String cabecera_correlativo = ConstantesSun.FEL_SERIE_RETENCION_DIGITAL;// Correlativo R001- + X
    String nuevoNroCertificado = "";

    unidadNegocio = MessageFormat.format("E0{0}", unidadNegocio);
    if (logger.isInfoEnabled()) {
      logger.debug("Unidad de Negocio: " + unidadNegocio);
      logger.debug("nroDiario: " + nroDiario);
      logger.debug("usuario" + usuario);
    }

    // validar Unidad de negocio Servicio o Seguro

    if (unidadNegocio.equals(ConstantesSun.SSC_BusinessUnit_E01)) {// comparar Servicios
//      nuevoNroCertificado = String.valueOf(incrementarCorrelativoSeguros());
      nuevoNroCertificado = Utilidades.completarCeros(String.valueOf(incrementarCorrelativoSeguros()),ConstantesSun.FEL_CDP_LONG_CORR);
    } else if (unidadNegocio.equals(ConstantesSun.SSC_BusinessUnit_E02)) {// Comparar Seguros
//      nuevoNroCertificado = String.valueOf(incrementarCorrelativoServicios());
      nuevoNroCertificado = Utilidades.completarCeros(String.valueOf(incrementarCorrelativoServicios()),ConstantesSun.FEL_CDP_LONG_CORR);
    }

    // validacion de fecha de emision

    try {
      respuestaActualizacion = Utilitarios.verificarRangoDeFechas(comprobanteElectronico.getFechaEmision(), Calendar.getInstance().getTime(), Constantes.PLAZO_DIAS_HABILES_SUNAT);

      if (respuestaActualizacion == Boolean.FALSE) {
        logger.debug("Mayor de 7 dias");

        if (actualizarEstadoSunsystems(comprobanteElectronico.getNroComprobanteElectronico(), nroDiario, unidadNegocio, ConstantesSun.EST_ASIENTO_PAGADO) == true) {
          comprobanteElectronico.setEstado(ConstantesSun.EST_ASIENTO_ANULADO);
          comprobanteElectronico.setModificadoPor(usuario);
          comprobanteElectronicoMapper.updateByNroComprobanteEstado(comprobanteElectronico);
          respuesta = Boolean.TRUE;
        } else {
          respuesta = Boolean.FALSE;
        }
      } else {
        logger.debug("Menor de 7 dias");
        logger.debug("Nuevo Nro. Certificado:" + nuevoNroCertificado);
        if (actualizarEstadoSunsystems(cabecera_correlativo + nuevoNroCertificado, nroDiario, unidadNegocio, ConstantesSun.EST_ASIENTO_ANULADO) == true) {
          comprobanteElectronico.setEstado(ConstantesSun.EST_ASIENTO_ANULADO);
          comprobanteElectronico.setModificadoPor(usuario);
          comprobanteElectronicoMapper.updateByNroComprobanteEstado(comprobanteElectronico);
          respuesta = Boolean.TRUE;
        } else {
          respuesta = Boolean.FALSE;
        }
      }

    } catch (Exception e) {

      logger.error("Exception(" + e.getClass().getName() + ") - ERROR :" + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") --> " + ExceptionUtils.getStackTrace(e));
      respuesta = Boolean.FALSE;
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_ACTUALIZAR);
    }
    if (logger.isInfoEnabled()) {
      logger.info("Output [Respuesta: " + respuesta + "]");
    }

    if (logger.isInfoEnabled()) {
      logger.info("Fin");
    }

    return respuesta;
  }

  @Override
  public boolean actualizarEstadoSunsystems(String nroCertificado, String nroDiario, String unidadNegocio, String nuevoEstado) throws SyncconException {
    if (logger.isDebugEnabled()) {
      logger.debug("Inicio");
    }
    boolean isOK = false;

    try {
      if (logger.isDebugEnabled()) {
        logger.debug("Input [1.-" + nroCertificado + ", 2.-" + nroDiario + ", 3.-" + unidadNegocio + ", 4.-" + nuevoEstado + "]");
      }
      isOK = diarioServices.actualizaAsientoPagoRetencion(unidadNegocio, nroDiario, nuevoEstado, nroCertificado);

      if (logger.isInfoEnabled()) {
        logger.info("Output [NroCertificado: " + nroCertificado + "\tRespuestaSUN: " + isOK + "]");
      }
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
      // throw new SyncconException(ErrorConstants.COD_ERROR_BD_ACTUALIZAR);
    }
    if (logger.isDebugEnabled()) {
      logger.debug("Fin");
    }
    return isOK;
  } // actualizarEstadoSunsystems

  private String getUltimoCorrelativoSeguros() {
    return asientoMaper.ultimoSecuencialSeguros();
  }

  private int incrementarCorrelativoSeguros() {
    AsientoContableSecuencial asiento = new AsientoContableSecuencial();
    asiento.setFechaAsiento(Calendar.getInstance().getTime());
    asientoMaper.insertar(asiento);
    return Integer.valueOf(getUltimoCorrelativoSeguros());
  }// generacion de Correlativo()

  private String getUltimoCorrelativoServicio() {
    return asientoMaper.ultimoSecuencialServicios();
  }

  private int incrementarCorrelativoServicios() {
    AsientoContableSecuencial asiento = new AsientoContableSecuencial();
    asiento.setFechaAsiento(Calendar.getInstance().getTime());
    asientoMaper.insertServicios(asiento);
    return Integer.valueOf(getUltimoCorrelativoServicio());

  }

  @Override
  public List<ComprobanteElectronico> buscarRetencionesEmitidasAnuladas(String unidadNegocio, String nroDiario, Date fechaDesde, Date fechaHasta, String rucProveedor, String nroComprobanteRetencion) {

    if (logger.isInfoEnabled()) {
      logger.info("Inicio");
    }

    List<ComprobanteElectronico> listaAnulados = new ArrayList<ComprobanteElectronico>();
    ComprobanteElectronico comprobanteElectronico = new ComprobanteElectronico();

    comprobanteElectronico.setUnidadNegocio(unidadNegocio);
    comprobanteElectronico.setNroDiario(nroDiario);
    comprobanteElectronico.setFechaEmision(fechaDesde);// filtro fecha desde
    comprobanteElectronico.setFechaDigitalizacion(fechaHasta); // filtro fecha hasta
    comprobanteElectronico.setProNroDocumento(rucProveedor);
    comprobanteElectronico.setNroComprobanteElectronico(nroComprobanteRetencion);
    comprobanteElectronico.setEstado(ConstantesSun.EST_ASIENTO_VALIDADO_FEP);
    try {
      String fechaEmisiondesde = new SimpleDateFormat("yyyyMMdd").format(fechaDesde);
      String fechaEmisionhasta = new SimpleDateFormat("yyyyMMdd").format(fechaHasta);

      logger.info("unidadNegocio" + unidadNegocio);
      logger.info("nroDiario" + nroDiario);
      logger.info("fechaDesde" + fechaEmisiondesde);
      logger.info("fechaHasta" + fechaEmisionhasta);
      logger.info("nroComprobanteRetencion" + nroComprobanteRetencion);
      logger.info("rucProveedor" + rucProveedor);

      listaAnulados = comprobanteElectronicoMapper.selectCertificadoAnulados(unidadNegocio, nroDiario, nroComprobanteRetencion, fechaEmisiondesde, fechaEmisionhasta, rucProveedor);

      // listaAnulados = comprobanteElectronicoMapper.selectByCertificadosAnulados(comprobanteElectronico);

    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
    }

    if (logger.isInfoEnabled()) {
      logger.info("Fin");
    }

    return listaAnulados;
  }

  public static void main(String[] args) {
    try {
      ComprobanteRetencionService service = new ComprobanteRetencionServiceImpl();
      boolean flag = service.actualizarEstadoSunsystems("001-0068", "7498", "E02", ConstantesSun.EST_ASIENTO_PAGADO);

      System.out.println("flag: " + flag);
    } catch (SyncconException e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
    }

  }

  @Override
  public boolean actualizarEstadoSatelite(String nroCertificado, String nuevoEstado, String mensajeRespuesta) throws SyncconException {
    if (logger.isDebugEnabled()) {
      logger.debug("Inicio");
    }
    boolean isOK = false;

    try {
      if (logger.isDebugEnabled()) {
        logger.debug("Input [1.-" + nroCertificado + ", 2.-" + nuevoEstado + ", 3.-" + mensajeRespuesta + "]");
      }
      ComprobanteElectronico comprobanteElectronico = comprobanteElectronicoMapper.selectByPrimaryKey(nroCertificado);

      comprobanteElectronico.setEstado(nuevoEstado);
      if (StringUtils.isNotBlank(mensajeRespuesta)) {
        comprobanteElectronico.setRespuestaDteRecovery(mensajeRespuesta);
      }

      int rows = comprobanteElectronicoMapper.updateByPrimaryKeySelective(comprobanteElectronico);
      if (logger.isInfoEnabled()) {
        logger.info("Output [NroCertificado: " + nroCertificado + "\tRegistros afectados: " + rows + "]");
      }

      /* La actualizacion culmino correctamente */
      isOK = true;
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_ACTUALIZAR);
    }
    if (logger.isDebugEnabled()) {
      logger.debug("Fin");
    }
    return isOK;
  } // actualizarEstadoSatelite

  @Override
  public boolean insertarComprobanteElectronico(ComprobanteElectronicoBean bean, String unidadNegocio, String estado, String mensajePPL, String usuario) throws SyncconException {
    if (logger.isInfoEnabled()) {
      logger.info("Inicio");
    }
    boolean isOK = false;

    try {
      ComprobanteElectronico comprobanteElectronico = comprobanteElectronicoMapper.selectByPrimaryKey(bean.getEnNroComprobante());

      if (null == comprobanteElectronico) {
        if (logger.isDebugEnabled()) {
          logger.debug("Creando un nuevo objeto ComprobanteElectronico para insertar en la DB Satelite.");
        }
        comprobanteElectronico = new ComprobanteElectronico();

        comprobanteElectronico.setNroComprobanteElectronico(bean.getEnNroComprobante());
        comprobanteElectronico.setTipoComprobante(ConstantesSun.FEL_TIP_COMP_RETENCION);
        comprobanteElectronico.setFechaEmision(bean.getEnFechaEmision());
        comprobanteElectronico.setProTipoDocumento(ConstantesSun.FEL_TIP_DOC_IDENT_RUC);
        comprobanteElectronico.setProNroDocumento(bean.getEnProNroDocIdent());
        comprobanteElectronico.setProRazonSocial(bean.getEnProRazonSocial());
        comprobanteElectronico.setMonedaPago(bean.getEnRetPerMoneda());
        comprobanteElectronico.setTasaRetPer(null != bean.getEnRetPerTasa() ? bean.getEnRetPerTasa().toString() : "");
        comprobanteElectronico.setImporteRetPer(bean.getEnRetPerImporteRetenidoPercibido());
        comprobanteElectronico.setMonedaRetPer(bean.getEnRetPerMoneda());
        comprobanteElectronico.setImportePagCob(bean.getEnRetPerImportePagadoCobrado());
        comprobanteElectronico.setPlantilla(bean.getPePlantilla());
        comprobanteElectronico.setCorreo(bean.getPeCorreoCliente());
        comprobanteElectronico.setTipoCambio(null != bean.getPeTipoCambio() ? Double.valueOf(bean.getPeTipoCambio()) : 0D);
        comprobanteElectronico.setMontoLetras(bean.getPeMontoLetras());
        comprobanteElectronico.setEstado(estado);
        comprobanteElectronico.setFechaCreacion(Calendar.getInstance().getTime());
        comprobanteElectronico.setCreadoPor(usuario);
        comprobanteElectronico.setRespuestaDteRecovery(mensajePPL);
        comprobanteElectronico.setNroDiario(bean.getNroDiario());
        comprobanteElectronico.setFechaDigitalizacion(Calendar.getInstance().getTime());
        comprobanteElectronico.setUnidadNegocio(unidadNegocio);

        comprobanteElectronicoMapper.insertSelective(comprobanteElectronico);
        if (logger.isDebugEnabled()) {
          logger.debug("El ComprobanteElectronico[" + bean.getEnNroComprobante() + "] fue registrado correctamente.");
        }
        isOK = true;
      } else {
        logger.error("No es posible insertar un NUEVO registro. Ya existe un registro con el mismo Nro.Certificado: " + bean.getEnNroComprobante());
        throw new Exception("No es posible insertar un NUEVO registro. Ya existe un registro con el mismo Nro.Certificado: " + bean.getEnNroComprobante());
      }
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
      // throw new SyncconException(ErrorConstants.COD_ERROR_BD_INSERTAR);
    }
    if (logger.isInfoEnabled()) {
      logger.info("Fin");
    }
    return isOK;
  } // insertarComprobanteElectronico

}
