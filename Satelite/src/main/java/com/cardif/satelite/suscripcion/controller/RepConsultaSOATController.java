package com.cardif.satelite.suscripcion.controller;

import static com.cardif.satelite.constantes.ErrorConstants.ERROR_SYNCCON;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR_GENERAL;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.service.CanalProductoService;
import com.cardif.satelite.configuracion.service.CategoriaClaseService;
import com.cardif.satelite.configuracion.service.MarcaVehiculoService;
import com.cardif.satelite.configuracion.service.ModeloVehiculoService;
import com.cardif.satelite.configuracion.service.ParametroAutomatService;
import com.cardif.satelite.configuracion.service.ParametroService;
import com.cardif.satelite.configuracion.service.ProductoService;
import com.cardif.satelite.configuracion.service.SocioService;
import com.cardif.satelite.configuracion.service.UsoVehiculoService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.CanalProducto;
import com.cardif.satelite.model.CategoriaClase;
import com.cardif.satelite.model.MarcaVehiculo;
import com.cardif.satelite.model.ModeloVehiculo;
import com.cardif.satelite.model.ParametroAutomat;
import com.cardif.satelite.model.Socio;
import com.cardif.satelite.model.UsoVehiculo;
import com.cardif.satelite.model.reportes.DetalleReporteAPESEG;
import com.cardif.satelite.model.reportes.DetalleReporteSBS;
import com.cardif.satelite.model.satelite.DetalleTramaDiaria;
import com.cardif.satelite.model.satelite.DetalleTramaDiariaMotivo;
import com.cardif.satelite.moduloimpresion.bean.ConsultaProductoSocio;
import com.cardif.satelite.suscripcion.bean.ConsultaPostVentaSOAT;
import com.cardif.satelite.suscripcion.bean.RepPolizasSOATBean;
import com.cardif.satelite.suscripcion.service.PostVentaSOATService;
import com.cardif.satelite.suscripcion.service.RepConsultaSOATService;

import net.sf.jxls.transformer.XLSTransformer;

/**
 * Esta clase contiene la funcionalidad para el Reporte de los SOAT impresos de los diferentes
 * socios.
 * 
 * @author INFOPARQUEPERU
 *		   Jose Manuel Lucas Barrera
 */
@Scope("request")
@Controller("repConsultaSOATController")
public class RepConsultaSOATController extends BaseController
{
	private static final Logger logger = Logger.getLogger(RepConsultaSOATController.class);
	
	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private ParametroAutomatService parametroService1;
	
	
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private RepConsultaSOATService repConsultaSOATService;
		
	@Autowired
	private SocioService socioService;
	
	@Autowired
	private CategoriaClaseService categoriaClaseService;
	
	@Autowired
	private CanalProductoService canalProductoService;
	
	@Autowired
	private PostVentaSOATService postVentaSOATService;
	
	@Autowired
	private MarcaVehiculoService marcaVehiculoService;
	
	@Autowired
	private ModeloVehiculoService modeloVehiculoService;
	
	
	@Autowired
	private UsoVehiculoService usoVehiculoService;
		

	
	
	
	private List<SelectItem> comboListaSocios;
	private List<SelectItem> comboListaCanales;

	
	
	
	
	private String codSocio;
	private Long codCanal;
	private String numPlaca;
	private String numCertificado;
	private String numDocumentoID;
	private String nombreContratante;
	private Date fechaVentaDesde;
	private Date fechaVentaHasta;
	private String estado;
	
	private List<RepPolizasSOATBean> listaPolizasSOAT;
	

	
	private boolean desabilitarExport;
	
	
	/* Combos para la PAGINA 2 */
	private List<SelectItem> comboListaCategoria;
	private List<SelectItem> comboListaUsoVehiculo;
	private List<SelectItem> comboListaMarca;
	private List<SelectItem> comboListaModelo;
	private List<SelectItem> comboListaTipoDoc;
	private List<SelectItem> comboListaSexo;
	
	/* Combos para la PAGINA 2 - Reimprimir */
	private List<SelectItem> comboListaMotivoReimp;
	private List<SelectItem> comboListaMotivoAnul;
	
	
	/* Ventana Ver Detalle (VD) - PAGINA 2 */
	private String socioProductoVD;
	private String nroPolizaVD;
	private String numPlacaVD;
	private String numSerieVD;
	private Integer numAsientosVD;
	private Integer anioFabVD;
	private String idCategoriaClaseVD;
	private String idUsoVehiculoVD;
	private Long idMarcaVehiculoVD;
	private Long idModeloVehiculoVD;
	private String idTipoDocVD;
	private String numDocumentoVD;
	private Date fecNacimientoVD;
	private String idSexoVD;
	private String apePaternoVD;
	private String apeMaternoVD;
	private String nombresVD;
	private Double primaBrutaVD;
	private Date fecIniVigenciaVD;
	private Date fecFinVigenciaVD;
	private Date fecVentaVD;
	private String canalVentaVD;
	private String nroCertificadoVD;
	private String nroCertificadoAntVD;
	private String estadoVD;
	private String estadoImpVD;
	private String motivoVD;
	private String observacionVD;
	private String opcionApesegVD;
	private Date fecReporteApesegVD;
	private String opcionSbsVD;
	private Date fecReporteSbsVD;
	private Long pkDetalleTramaDiariaAux;
	private DetalleTramaDiaria detalleTramaDiariaAux;
	
	
	/* Ventana Ver Detalle - Reimprimir (VDR) - PAGINA 2 */
	private String idMotivoVDR;
	private String observacionVDR;
	private String nroCertificadoVDR;
	
	/* Ventana Ver Detalle - Anular (VDA) - PAGINA 2 */
	private String idMotivoVDA;
	private String observacionVDA;
	
	private List<ConsultaPostVentaSOAT> listaPostVentaSOAT;
	

	
	/* Ventana Ver Detalle (VD) - PAGINA 2 */
	private boolean disabledComponentes;
	private boolean disabledBtnEditar;
	private boolean disabledBtnActualizar;
	private boolean disabledBtnReimprimir;
	private boolean disabledBtnAnular;
	private boolean shownBtnPostReimprimir;
	private boolean disabledprimaBrutaVD;
	private boolean disabledfecVentaVD;
	/*
	 * Navegacion de Paginas
	 */
	private boolean panelStep1Flag;
	private boolean panelStep2Flag;
	
	private String tituloCabecera;
	
	
	
	@PostConstruct
	@Override
	public String inicio()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		if (!tieneAcceso())
		{
			if (logger.isDebugEnabled()) {logger.debug("No cuenta con los accesos necesarios.");}
			return "accesoDenegado";
		}
		
		try
		{
			this.comboListaSocios = new ArrayList<SelectItem>();
			this.comboListaCanales = new ArrayList<SelectItem>();
			
			
			this.comboListaCategoria = new ArrayList<SelectItem>();
			this.comboListaUsoVehiculo = new ArrayList<SelectItem>();
			this.comboListaMarca = new ArrayList<SelectItem>();
			this.comboListaModelo = new ArrayList<SelectItem>();
			this.comboListaTipoDoc = new ArrayList<SelectItem>();
			this.comboListaSexo = new ArrayList<SelectItem>();
			this.comboListaMotivoReimp = new ArrayList<SelectItem>();
			this.comboListaMotivoAnul = new ArrayList<SelectItem>();
			
			
			
			
			this.listaPolizasSOAT = new ArrayList<RepPolizasSOATBean>();
			this.listaPostVentaSOAT = new ArrayList<ConsultaPostVentaSOAT>();
			
			
			//*****************************************************************************
			//************************ CARGAS LOS VALORES INCIALES ************************
			//*****************************************************************************SE COMENTA
//			if (logger.isInfoEnabled()) {logger.info("Buscando la lista de SOCIOS.");}
//			List<Socio> listaSocio = socioService.buscarPorModSuscripcionEstadoProdEstadoSocio(Constantes.PRODUCTO_MOD_SUSCRIPCION_SI, Constantes.PRODUCTO_ESTADO_ACTIVO, Constantes.SOCIO_ESTADO_ACTIVO);
//			this.comboListaSocios.add(new SelectItem("", "- Seleccione -"));
//			for (Socio socio : listaSocio)
//			{
//				if (logger.isDebugEnabled()) {logger.debug("SOCIO_ID: " + socio.getId() + " SOCIO_NOMBRE: " + socio.getNombreSocio() + " SOCIO_ABBREVIATURA: " + socio.getAbreviatura());}
//				this.comboListaSocios.add(new SelectItem(socio.getId(), socio.getNombreSocio()));
//			}

			
			//*****************************************************************************
			//************************ CARGAS LOS VALORES INCIALES ************************
			//*****************************************************************************
			if (logger.isInfoEnabled()) {logger.info("Buscando la lista de SOCIOS.");}
			List<Socio> listaSocio = socioService.buscarSocio(Constantes.SOCIO_ESTADO_ACTIVO);
			this.comboListaSocios.add(new SelectItem("", "- Seleccione -"));
			for (Socio socio : listaSocio)
			{
				if (logger.isDebugEnabled()) {logger.debug("SOCIO_ID: " + socio.getId() + " SOCIO_NOMBRE: " + socio.getNombreSocio() + " SOCIO_ABBREVIATURA: " + socio.getAbreviatura());}
				this.comboListaSocios.add(new SelectItem(socio.getId(), socio.getNombreSocio()));
			}
			
			if (logger.isInfoEnabled()) {logger.info("Buscando la lista de CATEGORIA / CLASE.");}
			List<CategoriaClase> listaCategoriaClase = categoriaClaseService.buscarTodos();
			this.comboListaCategoria.add(new SelectItem("", "- Seleccione -"));
			for (CategoriaClase categoriaClase : listaCategoriaClase)
			{
				if (logger.isDebugEnabled()) {logger.debug("CATEGORIA_CLASE ==> COD_CATEGORIA_CLASE: " + categoriaClase.getCodCategoriaClase() + " - DESCRIPCION_CATEGORIA_CLASE: " + categoriaClase.getDescripcionCategoriaClase());}
				this.comboListaCategoria.add(new SelectItem(categoriaClase.getCodCategoriaClase(), categoriaClase.getDescripcionCategoriaClase()));
			}
			
			if (logger.isInfoEnabled()) {logger.info("Buscando la lista de USO DE VEHICULO.");}
			List<UsoVehiculo> listaUsoVehiculo = usoVehiculoService.buscarUsoVehiculos();
			this.comboListaUsoVehiculo.add(new SelectItem("", "- Seleccione -"));
			for (UsoVehiculo usoVehiculo : listaUsoVehiculo)
			{
				if (logger.isDebugEnabled()) {logger.debug("USO_VEHICULO ==> COD_USO: " + usoVehiculo.getCodUso() + " - DESCRIPCION_USO: " + usoVehiculo.getDescripcionUso());}
				this.comboListaUsoVehiculo.add(new SelectItem(usoVehiculo.getCodUso(), usoVehiculo.getDescripcionUso()));
			}
			
			if (logger.isInfoEnabled()) {logger.info("Buscando la lista de TIPO DE DOCUMENTO DE IDENTIDAD.");}
			List<ParametroAutomat> listaTipoDoc = parametroService1.buscarPorCodParam(Constantes.COD_PARAM_TIPO_DOCUMENTO_ID);
			this.comboListaTipoDoc.add(new SelectItem("", "- Seleccione -"));
			for (ParametroAutomat tipoDocumento : listaTipoDoc)
			{
				if (logger.isDebugEnabled()) {logger.debug("TIPO_DOCUMENTO_ID ==> COD_VALOR: " + tipoDocumento.getCodValor() + " - NOMBRE_VALOR: " + tipoDocumento.getNombreValor());}
				this.comboListaTipoDoc.add(new SelectItem(tipoDocumento.getCodValor(), tipoDocumento.getNombreValor()));
			}
			
			if (logger.isInfoEnabled()) {logger.info("Buscando la lista de SEXO.");}
			List<ParametroAutomat> listaSexo = parametroService1.buscarPorCodParam(Constantes.COD_PARAM_SEXO);
			this.comboListaSexo.add(new SelectItem("", "- Seleccione -"));
			for (ParametroAutomat sexo : listaSexo)
			{
				if (logger.isDebugEnabled()) {logger.debug("SEXO ==> COD_VALOR: " + sexo.getCodValor() + " - NOMBRE_VALOR: " + sexo.getNombreValor());}
				this.comboListaSexo.add(new SelectItem(sexo.getCodValor(), sexo.getNombreValor()));
			}
			
			
			/* Agregar el valor inicial para el Combo SocioProductos */
			this.comboListaCanales.add(new SelectItem("", "- Seleccione -"));
			
			
			/* Deshabilitar boton 'Exportar' */
			//this.disabledBtnExportar = true;
			
			
			/* Iniciando en la PAGINA 1 */
			this.panelStep1Flag = true;
			this.panelStep2Flag = false;
			
			
			this.desabilitarExport = true;
			
			

			/* Iniciando en la PAGINA 1 */
			this.panelStep1Flag = true;
			this.panelStep2Flag = false;
			
			
			/* Iniciando sin agregar nada adicional a la cabecera */
			this.tituloCabecera = null;
			
		}
		catch (SyncconException e)
		{
			logger.error("SyncconException() - " + ErrorConstants.ERROR_SYNCCON + e.getMessageComplete());
			logger.error("SyncconException() -->" + ExceptionUtils.getStackTrace(e));
			FacesMessage facesMsg = new FacesMessage(e.getSeveridad(), e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = ErrorConstants.MSJ_ERROR;
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //inicio
	
	
	public String buscarCanalesPorSocio(ValueChangeEvent event)
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			this.comboListaCanales = new ArrayList<SelectItem>();
			this.comboListaCanales.add(new SelectItem("", "- Seleccione -"));
			
			if (null != event.getNewValue())
			{
				if (logger.isDebugEnabled()) {logger.debug("Buscar canales relacionados al SOCIO: " + Long.valueOf(event.getNewValue().toString())); }
				List<CanalProducto> listaCanalProducto = canalProductoService.buscarPorCodSocio(Constantes.PRODUCTO_MOD_SUSCRIPCION_SI, Long.valueOf(event.getNewValue().toString()));
				
				if (logger.isDebugEnabled()) {logger.debug("Se encontraron [" + (null != listaCanalProducto ? listaCanalProducto.size() : 0) + "] registros");}
				for (CanalProducto canalProducto : listaCanalProducto)
				{
					this.comboListaCanales.add(new SelectItem(canalProducto.getId(), canalProducto.getNombreCanal().toUpperCase()));
				}
			}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = ErrorConstants.MSJ_ERROR;
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //buscarSocioProducto
	
	public String procesarBusqueda()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			this.desabilitarExport = true;
			
			if(this.fechaVentaDesde != null &&  this.fechaVentaHasta !=null){
				if(fechaVentaHasta.before(fechaVentaDesde)){
					throw new SyncconException(ErrorConstants.COD_ERROR_RANGO_FECHAS, FacesMessage.SEVERITY_INFO);
				}
			}
			
			if (logger.isDebugEnabled())
			{
				logger.debug("################ PARAMETROS DE LA BUSQUEDA ################" + 
						"\nSOCIO: " + this.codSocio + 
						"\nPLACA: " + this.numPlaca + "\tCERTIFICADO: " + this.numCertificado + 
						"\nNRO_DOCUMENTO: " + this.numDocumentoID + "\tCONTRATANTE: " + this.nombreContratante + 
						"\nFECHA_VENTA_DESDE: " + this.fechaVentaDesde + "\tFECHA_VENTA_HASTA: " + this.fechaVentaHasta);
			}
			
			listaPolizasSOAT = postVentaSOATService.buscarPolizasSOAT(Long.valueOf(this.codSocio), this.codCanal, this.numPlaca,this.numCertificado,this.numDocumentoID,this.nombreContratante ,  this.fechaVentaDesde, this.fechaVentaHasta);
			
			int size = (null != listaPolizasSOAT ? listaPolizasSOAT.size() : 0);
			if (logger.isInfoEnabled()) {logger.info("Numero de polizas SOAT encontradas es: [" + size + "]");}
			
			this.desabilitarExport = (0 < size ? false : true);
		}
		catch (SyncconException e)
		{
			logger.error("SyncconException() - " + ERROR_SYNCCON + e.getMessageComplete());
	    	FacesMessage facesMsg = new FacesMessage(e.getSeveridad(), e.getMessage(), null);
	    	FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
	    	FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
	    	FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	    	respuesta = MSJ_ERROR;
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //procesarBusqueda
	
	public String exportar()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			Map<String, Object> beans = new HashMap<String, Object>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
			String fechaActual = sdf.format(new Date(System.currentTimeMillis()));
			
			if (null != listaPolizasSOAT &&	0 < listaPolizasSOAT.size())
			{
				String rutaTemp = System.getProperty("java.io.tmpdir") + "Reporte_Polizas_SOAT_" + System.currentTimeMillis() + ".xls";
		        if (logger.isDebugEnabled()) {logger.debug("Ruta Archivo: " + rutaTemp);}
		        
		        FacesContext facesContext = FacesContext.getCurrentInstance();
		        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
		        
		        String rutaTemplate = servletContext.getRealPath(File.separator + "excel" + File.separator + "template_exportar_reporte_polizas_soat.xls");
		        if (logger.isDebugEnabled()) {logger.debug("Ruta del Template: " + rutaTemplate);}
		        
		        beans.put("exportar", this.listaPolizasSOAT);
		        
		        XLSTransformer transformer = new XLSTransformer();
		        transformer.transformXLS(rutaTemplate, beans, rutaTemp);
		        
		        File archivoResp = new File(rutaTemp);
		        FileInputStream fis = new FileInputStream(archivoResp);
		        
		        HttpServletResponse response = (HttpServletResponse) contexto.getResponse();
		        byte[] loader = new byte[(int) archivoResp.length()];
		        response.addHeader("Content-Disposition", "attachment;filename=" + "Reporte_Polizas_SOAT_" + fechaActual + "_" + System.currentTimeMillis() + ".xls");
		        response.setContentType("application/vnd.ms-excel");
		        
		        ServletOutputStream sos = response.getOutputStream();
		        
		        while ((fis.read(loader)) > 0)
		        {
		        	sos.write(loader, 0, loader.length);
		        }
		        
		        fis.close();
		        sos.close();
		        
		        FacesContext.getCurrentInstance().responseComplete();
		        if (logger.isDebugEnabled()) {logger.debug("Exportacion culminada.");}
			}
			else
			{
				throw new SyncconException(ErrorConstants.COD_ERROR_REP_POLIZAS_SOAT_BUSQUEDA_PENDIENTE, FacesMessage.SEVERITY_INFO);
			}
		}
		catch (SyncconException e)
		{
			logger.error("SyncconException() - " + ErrorConstants.ERROR_SYNCCON + e.getMessageComplete());
			logger.error("SyncconException() -->" + ExceptionUtils.getStackTrace(e));
			FacesMessage facesMsg = new FacesMessage(e.getSeveridad(), e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = ErrorConstants.MSJ_ERROR;
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //exportar
	
	// Ver Detalle
	
	private void cargarComboListaMarca(String codCategoriaClase) throws SyncconException
	{
		if (logger.isDebugEnabled()) {logger.debug("Buscando marcas relacionadas a la CATEGORIA: " + codCategoriaClase);}
		List<MarcaVehiculo> listaMarcaVehiculos = marcaVehiculoService.buscarPorPkCategoriaClase(codCategoriaClase);
		
		if (logger.isDebugEnabled()) {logger.debug("Se encontraron [" + (null != listaMarcaVehiculos ? listaMarcaVehiculos.size() : 0) + "] marcas.");}
		for (MarcaVehiculo marcaVehiculo : listaMarcaVehiculos)
		{
			this.comboListaMarca.add(new SelectItem(marcaVehiculo.getId(), marcaVehiculo.getNombreMarcavehiculo()));
		}
	} //cargarComboListaMarca
	
	private void cargarComboListaModelo(Long idMarcaVehiculo) throws SyncconException
	{
		if (logger.isDebugEnabled()) {logger.debug("Buscando modelos relacionados a la MARCA: " + idMarcaVehiculo);}
		List<ModeloVehiculo> listaModeloVehiculos = modeloVehiculoService.buscarPorMarcaVehiculoCategoriaClase(idMarcaVehiculo);
		
		if (logger.isDebugEnabled()) {logger.debug("Se encontraron [" + (null != listaModeloVehiculos ? listaModeloVehiculos.size() : 0) + "] modelos.");}
		for (ModeloVehiculo modeloVehiculo : listaModeloVehiculos)
		{
			this.comboListaModelo.add(new SelectItem(modeloVehiculo.getId(), modeloVehiculo.getNombreModelovehiculo()));
		}
	} //cargarComboListaModelo
	
	public String cargarPagVerDetalle()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		

		
		FacesContext context = FacesContext.getCurrentInstance();
		String numLote = context.getExternalContext().getRequestParameterMap().get("ventaID");

		Long idDetalleTramaDiaria = Long.valueOf(numLote);
		
		try
		{
			if (logger.isInfoEnabled()) {logger.info("PK_DetalleTramaDiaria: " + idDetalleTramaDiaria);}
			
			//cleanInformacionVerDetalle();
			
			/*if (0 == this.comboListaMotivoReimp.size())
			{
				if (logger.isInfoEnabled()) {logger.info("Buscando la lista de MOTIVOS DE REIMPRESION");}
				List<ParametroAutomat> listaMotivoReimp = parametroService1.buscarPorCodParam(Constantes.COD_PARAM_MOTIVO_REIMPRESION);
				this.comboListaMotivoReimp.add(new SelectItem("", "- Seleccione -"));
				
				for (ParametroAutomat motivoReimp : listaMotivoReimp)
				{
					if (logger.isDebugEnabled()) {logger.debug("MOTIVO_REIMPRESION ==> COD_VALOR: " + motivoReimp.getCodValor() + " - NOMBRE_VALOR: " + motivoReimp.getNombreValor());}
					this.comboListaMotivoReimp.add(new SelectItem(motivoReimp.getCodValor(), motivoReimp.getNombreValor()));
				}
			}*/
			
			/*if (0 == this.comboListaMotivoAnul.size())
			{
				if (logger.isInfoEnabled()) {logger.info("Buscando la lista de MOTIVOS DE ANULACION");}
				List<ParametroAutomat> listaMotivoAnul = parametroService1.buscarPorCodParam(Constantes.COD_PARAM_MOTIVO_ANULACION);
				this.comboListaMotivoAnul.add(new SelectItem("", "- Seleccione -"));
				
				for (ParametroAutomat motivoAnul : listaMotivoAnul)
				{
					if (logger.isDebugEnabled()) {logger.debug("MOTIVO_ANULACION ==> COD_VALOR: " + motivoAnul.getCodValor() + " - NOMBRE_VALOR: " + motivoAnul.getNombreValor());}
					this.comboListaMotivoAnul.add(new SelectItem(motivoAnul.getCodValor(), motivoAnul.getNombreValor()));
				}
			}
			*/
			
			/* Guardar el PK del registro de la tabla DETALLE_TRAMA_DIARIA */
			setPkDetalleTramaDiariaAux(idDetalleTramaDiaria);
			
			
			DetalleTramaDiaria detalleTramaDiaria = postVentaSOATService.buscarDetalleTramaDiariaPorPK(getPkDetalleTramaDiariaAux());
			if (logger.isDebugEnabled()) {logger.debug("Se encontro el objeto DetalleTramaDiaria con PK: " + getPkDetalleTramaDiariaAux());}
			
			/* Guardar el objeto DetalleTramaDiaria */
			setDetalleTramaDiariaAux(detalleTramaDiaria);
			
			
			if (logger.isDebugEnabled()) {logger.debug("Colocando los valores del objeto DetalleTramaDiaria.");}
			
			/*
			 * Cabecera
			 */
			ConsultaProductoSocio socioProducto = productoService.buscarPorPkDetalleTramaDiaria(getPkDetalleTramaDiariaAux());
			setSocioProductoVD(socioProducto.getNombreSocio() + " - " + socioProducto.getNombreProducto());
			setNroPolizaVD(getDetalleTramaDiariaAux().getNroPolizaPe());
			
			
			/*
			 * Datos del Vehiculo
			 */
			setNumPlacaVD(getDetalleTramaDiariaAux().getPlaca());
			setNumSerieVD(getDetalleTramaDiariaAux().getSerie());
			setNumAsientosVD(getDetalleTramaDiariaAux().getNroAsientos());
			setAnioFabVD(getDetalleTramaDiariaAux().getAnioFab());
			setIdCategoriaClaseVD(getDetalleTramaDiariaAux().getCodCategoriaClase());
			setIdUsoVehiculoVD(getDetalleTramaDiariaAux().getCodUsoVehiculo());
			cargarComboListaMarca(getIdCategoriaClaseVD());
			setIdMarcaVehiculoVD(getDetalleTramaDiariaAux().getCodMarca());
			cargarComboListaModelo(getIdMarcaVehiculoVD());
			setIdModeloVehiculoVD(getDetalleTramaDiariaAux().getCodModelo());
			
			
			/*
			 * Datos del Propietario
			 */
			setIdTipoDocVD(StringUtils.isNotBlank(getDetalleTramaDiariaAux().getTipDoc()) ? getDetalleTramaDiariaAux().getTipDoc() : null);
			setNumDocumentoVD(getDetalleTramaDiariaAux().getNumDoc());
			setFecNacimientoVD(getDetalleTramaDiariaAux().getFecNac());
			setIdSexoVD(StringUtils.isNotBlank(getDetalleTramaDiariaAux().getSexo()) ? getDetalleTramaDiariaAux().getSexo().substring(0, 1) : null);
			setApePaternoVD(getDetalleTramaDiariaAux().getApelPateContrat());
			setApeMaternoVD(getDetalleTramaDiariaAux().getApelMateContrat());
			setNombresVD(getDetalleTramaDiariaAux().getNomContrat());
			
			
			/*
			 * Datos de la Póliza
			 */
			setPrimaBrutaVD(getDetalleTramaDiariaAux().getPrimaBruta());
			setFecIniVigenciaVD(getDetalleTramaDiariaAux().getFecIniVigencia());
			setFecFinVigenciaVD(getDetalleTramaDiariaAux().getFecFinVigencia());
			setFecVentaVD(getDetalleTramaDiariaAux().getFecVenta());
			setCanalVentaVD(getDetalleTramaDiariaAux().getCanalVenta());
			setNroCertificadoVD(getDetalleTramaDiariaAux().getNroCertificado());
			setNroCertificadoAntVD(getDetalleTramaDiariaAux().getNroCertificadoAnt());
			setEstadoVD(getDetalleTramaDiariaAux().getEstado());
			setEstadoImpVD(getDetalleTramaDiariaAux().getEstadoImpresion());
			
			if (!getDetalleTramaDiariaAux().getEstado().equalsIgnoreCase(Constantes.POLIZA_ESTADO_VIGENTE))
			{
				if (logger.isDebugEnabled()) {logger.debug("Buscando la ULTIMA informacion sobre el MOTIVO DE ANULACION del DETALLE_TRAMA_DIARIA: " + getDetalleTramaDiariaAux().getId());}
				DetalleTramaDiariaMotivo detTramaDiariaMotivo = postVentaSOATService.buscarUltimoMotivoObservacion(
						getDetalleTramaDiariaAux().getId(), Constantes.DET_TRAMA_DIA_MOTIVO_ANULACION);
				
				if (logger.isDebugEnabled()) {logger.debug("Se encontro el objeto DetalleTramaDiariaMotivo: " + detTramaDiariaMotivo);}
				
				setMotivoVD(detTramaDiariaMotivo.getMotivoDsc());
				setObservacionVD(detTramaDiariaMotivo.getObservacionDsc());
				
				/*
				 * Deshabilitando los botones
				 */
				this.disabledBtnEditar = true;
				this.disabledBtnActualizar = true;
				this.disabledBtnReimprimir = true;
				this.disabledBtnAnular = true;
				
			}
			else
			{
				if (logger.isDebugEnabled()) {logger.debug("No fue necesario buscar informacion sobre el MOTIVO y OBSERVACION.");}
				
				/*
				 * Habilitando los botones necesarios
				 */
				this.disabledBtnEditar = false;
				this.disabledBtnActualizar = true;
				this.disabledBtnReimprimir = false;
				this.disabledBtnAnular = false;
				
			}
			
			
			/*
			 * Buscando la informacion del REPORTE APESEG
			 */
			DetalleReporteAPESEG detReporteAPESEG = postVentaSOATService.buscarUltimoReporteAPESEG(getDetalleTramaDiariaAux().getId());
			if (null != detReporteAPESEG)
			{
				if (logger.isDebugEnabled()) {logger.debug("Se encontro la informacion del REPORTE APESEG.");}
				setOpcionApesegVD(detReporteAPESEG.getReportado().toUpperCase());
				setFecReporteApesegVD(detReporteAPESEG.getFechaReportado());
			}
			else
			{
				if (logger.isDebugEnabled()) {logger.debug("No se encontro informacion del REPORTE APESEG.");}
				setOpcionApesegVD("NO");
				setFecReporteApesegVD(null);
			}
			
			
			/*¨
			 * Buscando la informacion del REPORTE SBS
			 */
			DetalleReporteSBS detReporteSBS = postVentaSOATService.buscarUltimoReporteSBS(getDetalleTramaDiariaAux().getId());
			if (null != detReporteSBS)
			{
				if (logger.isDebugEnabled()) {logger.debug("Se encontro la informacion del REPORTE SBS.");}
				setOpcionSbsVD(detReporteSBS.getReportado().toUpperCase());
				setFecReporteSbsVD(detReporteSBS.getFechaReportado());
			}
			else
			{
				if (logger.isDebugEnabled()) {logger.debug("No se encontro informacion del REPORTE SBS.");}
				setOpcionSbsVD("NO");
				setFecReporteSbsVD(null);
			}
			
			
			/*
			 * Cambiando a la PAGINA 2 'postVentaSOATModSuscripcionStep2.xhtml'
			 */
			this.panelStep2Flag = true;
			this.panelStep1Flag = false;
			
			/* Deshabilitando los componentes visuales */
			this.disabledComponentes = true;
			this.disabledfecVentaVD=true;
			this.disabledprimaBrutaVD=true;
			
			/*
			 * No mostrar los botones 'Ver Certificado', 'Confirmar Certificado'
			 */
			this.shownBtnPostReimprimir = false;
			
			/*
			 * Cambiando el titulo de la PAGINA
			 */
			this.tituloCabecera = Constantes.MOD_SUSC_VER_DETALLE;
		}
		catch (SyncconException e)
		{
			logger.error("SyncconException() - ERROR: " + ErrorConstants.ERROR_SYNCCON + e.getMessageComplete());
			logger.error("SyncconException() -->" + ExceptionUtils.getStackTrace(e));
			FacesMessage facesMsg = new FacesMessage(e.getSeveridad(), e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
	    	logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
	    	FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
	    	FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	    	respuesta = ErrorConstants.MSJ_ERROR;
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //cargarPagVerDetalle

	
	
	public String volverPantallaPrincipal()
	{
		if (logger.isDebugEnabled()) {logger.debug("Inicio - Fin");}
		this.panelStep1Flag = true;
		this.panelStep2Flag = false;
		
		return procesarBusqueda();
	} //
	
	
	public List<SelectItem> getComboListaSocios()
	{
		return comboListaSocios;
	}
	
	public void setComboListaSocios(List<SelectItem> comboListaSocios)
	{
		this.comboListaSocios = comboListaSocios;
	}
	
	public String getCodSocio()
	{
		return codSocio;
	}
	
	public void setCodSocio(String codSocio)
	{
		this.codSocio = codSocio;
	}
	
	public String getNumPlaca()
	{
		return numPlaca;
	}
	
	public void setNumPlaca(String numPlaca)
	{
		this.numPlaca = numPlaca;
	}
	
	public String getNumCertificado()
	{
		return numCertificado;
	}
	
	public void setNumCertificado(String numCertificado)
	{
		this.numCertificado = numCertificado;
	}
	
	public String getNumDocumentoID()
	{
		return numDocumentoID;
	}
	
	public void setNumDocumentoID(String numDocumentoID)
	{
		this.numDocumentoID = numDocumentoID;
	}
	
	public String getNombreContratante()
	{
		return nombreContratante;
	}
	
	public void setNombreContratante(String nombreContratante)
	{
		this.nombreContratante = nombreContratante;
	}
	
	public Date getFechaVentaDesde()
	{
		return fechaVentaDesde;
	}
	
	public void setFechaVentaDesde(Date fechaVentaDesde)
	{
		this.fechaVentaDesde = fechaVentaDesde;
	}
	
	public Date getFechaVentaHasta()
	{
		return fechaVentaHasta;
	}
	
	public void setFechaVentaHasta(Date fechaVentaHasta)
	{
		this.fechaVentaHasta = fechaVentaHasta;
	}
	
	public List<RepPolizasSOATBean> getListaPolizasSOAT()
	{
		return listaPolizasSOAT;
	}
	
	public void setListaPolizasSOAT(List<RepPolizasSOATBean> listaPolizasSOAT)
	{
		this.listaPolizasSOAT = listaPolizasSOAT;
	}
	
	public boolean isDesabilitarExport()
	{
		return desabilitarExport;
	}
	
	public void setDesabilitarExport(boolean desabilitarExport)
	{
		this.desabilitarExport = desabilitarExport;
	}

	

	public List<SelectItem> getComboListaCanales() {
		return comboListaCanales;
	}


	public void setComboListaCanales(List<SelectItem> comboListaCanales) {
		this.comboListaCanales = comboListaCanales;
	}


	public Long getCodCanal() {
		return codCanal;
	}


	public void setCodCanal(Long codCanal) {
		this.codCanal = codCanal;
	}

	public List<ConsultaPostVentaSOAT> getListaPostVentaSOAT() {
		return listaPostVentaSOAT;
	}

	public void setListaPostVentaSOAT(List<ConsultaPostVentaSOAT> listaPostVentaSOAT) {
		this.listaPostVentaSOAT = listaPostVentaSOAT;
	}


	public String getEstado() {
		return estado;
	}


	public void setEstado(String estado) {
		this.estado = estado;
	}


	public ParametroService getParametroService() {
		return parametroService;
	}


	public void setParametroService(ParametroService parametroService) {
		this.parametroService = parametroService;
	}


	public RepConsultaSOATService getRepConsultaSOATService() {
		return repConsultaSOATService;
	}


	public void setRepConsultaSOATService(
			RepConsultaSOATService repConsultaSOATService) {
		this.repConsultaSOATService = repConsultaSOATService;
	}


	public SocioService getSocioService() {
		return socioService;
	}


	public void setSocioService(SocioService socioService) {
		this.socioService = socioService;
	}


	public CategoriaClaseService getCategoriaClaseService() {
		return categoriaClaseService;
	}


	public void setCategoriaClaseService(CategoriaClaseService categoriaClaseService) {
		this.categoriaClaseService = categoriaClaseService;
	}


	public CanalProductoService getCanalProductoService() {
		return canalProductoService;
	}


	public void setCanalProductoService(CanalProductoService canalProductoService) {
		this.canalProductoService = canalProductoService;
	}


	public PostVentaSOATService getPostVentaSOATService() {
		return postVentaSOATService;
	}


	public void setPostVentaSOATService(PostVentaSOATService postVentaSOATService) {
		this.postVentaSOATService = postVentaSOATService;
	}


	public List<SelectItem> getComboListaCategoria() {
		return comboListaCategoria;
	}


	public void setComboListaCategoria(List<SelectItem> comboListaCategoria) {
		this.comboListaCategoria = comboListaCategoria;
	}


	public List<SelectItem> getComboListaUsoVehiculo() {
		return comboListaUsoVehiculo;
	}


	public void setComboListaUsoVehiculo(List<SelectItem> comboListaUsoVehiculo) {
		this.comboListaUsoVehiculo = comboListaUsoVehiculo;
	}


	public List<SelectItem> getComboListaMarca() {
		return comboListaMarca;
	}


	public void setComboListaMarca(List<SelectItem> comboListaMarca) {
		this.comboListaMarca = comboListaMarca;
	}


	public List<SelectItem> getComboListaModelo() {
		return comboListaModelo;
	}


	public void setComboListaModelo(List<SelectItem> comboListaModelo) {
		this.comboListaModelo = comboListaModelo;
	}


	public List<SelectItem> getComboListaTipoDoc() {
		return comboListaTipoDoc;
	}


	public void setComboListaTipoDoc(List<SelectItem> comboListaTipoDoc) {
		this.comboListaTipoDoc = comboListaTipoDoc;
	}


	public List<SelectItem> getComboListaSexo() {
		return comboListaSexo;
	}


	public void setComboListaSexo(List<SelectItem> comboListaSexo) {
		this.comboListaSexo = comboListaSexo;
	}


	public List<SelectItem> getComboListaMotivoReimp() {
		return comboListaMotivoReimp;
	}


	public void setComboListaMotivoReimp(List<SelectItem> comboListaMotivoReimp) {
		this.comboListaMotivoReimp = comboListaMotivoReimp;
	}


	public List<SelectItem> getComboListaMotivoAnul() {
		return comboListaMotivoAnul;
	}


	public void setComboListaMotivoAnul(List<SelectItem> comboListaMotivoAnul) {
		this.comboListaMotivoAnul = comboListaMotivoAnul;
	}


	public String getSocioProductoVD() {
		return socioProductoVD;
	}


	public void setSocioProductoVD(String socioProductoVD) {
		this.socioProductoVD = socioProductoVD;
	}


	public String getNroPolizaVD() {
		return nroPolizaVD;
	}


	public void setNroPolizaVD(String nroPolizaVD) {
		this.nroPolizaVD = nroPolizaVD;
	}


	public String getNumPlacaVD() {
		return numPlacaVD;
	}


	public void setNumPlacaVD(String numPlacaVD) {
		this.numPlacaVD = numPlacaVD;
	}


	public String getNumSerieVD() {
		return numSerieVD;
	}


	public void setNumSerieVD(String numSerieVD) {
		this.numSerieVD = numSerieVD;
	}


	public Integer getNumAsientosVD() {
		return numAsientosVD;
	}


	public void setNumAsientosVD(Integer numAsientosVD) {
		this.numAsientosVD = numAsientosVD;
	}


	public Integer getAnioFabVD() {
		return anioFabVD;
	}


	public void setAnioFabVD(Integer anioFabVD) {
		this.anioFabVD = anioFabVD;
	}


	public String getIdCategoriaClaseVD() {
		return idCategoriaClaseVD;
	}


	public void setIdCategoriaClaseVD(String idCategoriaClaseVD) {
		this.idCategoriaClaseVD = idCategoriaClaseVD;
	}


	public String getIdUsoVehiculoVD() {
		return idUsoVehiculoVD;
	}


	public void setIdUsoVehiculoVD(String idUsoVehiculoVD) {
		this.idUsoVehiculoVD = idUsoVehiculoVD;
	}


	public Long getIdMarcaVehiculoVD() {
		return idMarcaVehiculoVD;
	}


	public void setIdMarcaVehiculoVD(Long idMarcaVehiculoVD) {
		this.idMarcaVehiculoVD = idMarcaVehiculoVD;
	}


	public Long getIdModeloVehiculoVD() {
		return idModeloVehiculoVD;
	}


	public void setIdModeloVehiculoVD(Long idModeloVehiculoVD) {
		this.idModeloVehiculoVD = idModeloVehiculoVD;
	}


	public String getIdTipoDocVD() {
		return idTipoDocVD;
	}


	public void setIdTipoDocVD(String idTipoDocVD) {
		this.idTipoDocVD = idTipoDocVD;
	}


	public String getNumDocumentoVD() {
		return numDocumentoVD;
	}


	public void setNumDocumentoVD(String numDocumentoVD) {
		this.numDocumentoVD = numDocumentoVD;
	}


	public Date getFecNacimientoVD() {
		return fecNacimientoVD;
	}


	public void setFecNacimientoVD(Date fecNacimientoVD) {
		this.fecNacimientoVD = fecNacimientoVD;
	}


	public String getIdSexoVD() {
		return idSexoVD;
	}


	public void setIdSexoVD(String idSexoVD) {
		this.idSexoVD = idSexoVD;
	}


	public String getApePaternoVD() {
		return apePaternoVD;
	}


	public void setApePaternoVD(String apePaternoVD) {
		this.apePaternoVD = apePaternoVD;
	}


	public String getApeMaternoVD() {
		return apeMaternoVD;
	}


	public void setApeMaternoVD(String apeMaternoVD) {
		this.apeMaternoVD = apeMaternoVD;
	}


	public String getNombresVD() {
		return nombresVD;
	}


	public void setNombresVD(String nombresVD) {
		this.nombresVD = nombresVD;
	}


	public Double getPrimaBrutaVD() {
		return primaBrutaVD;
	}


	public void setPrimaBrutaVD(Double primaBrutaVD) {
		this.primaBrutaVD = primaBrutaVD;
	}


	public Date getFecIniVigenciaVD() {
		return fecIniVigenciaVD;
	}


	public void setFecIniVigenciaVD(Date fecIniVigenciaVD) {
		this.fecIniVigenciaVD = fecIniVigenciaVD;
	}


	public Date getFecFinVigenciaVD() {
		return fecFinVigenciaVD;
	}


	public void setFecFinVigenciaVD(Date fecFinVigenciaVD) {
		this.fecFinVigenciaVD = fecFinVigenciaVD;
	}


	public Date getFecVentaVD() {
		return fecVentaVD;
	}


	public void setFecVentaVD(Date fecVentaVD) {
		this.fecVentaVD = fecVentaVD;
	}


	public String getCanalVentaVD() {
		return canalVentaVD;
	}


	public void setCanalVentaVD(String canalVentaVD) {
		this.canalVentaVD = canalVentaVD;
	}


	public String getNroCertificadoVD() {
		return nroCertificadoVD;
	}


	public void setNroCertificadoVD(String nroCertificadoVD) {
		this.nroCertificadoVD = nroCertificadoVD;
	}


	public String getNroCertificadoAntVD() {
		return nroCertificadoAntVD;
	}


	public void setNroCertificadoAntVD(String nroCertificadoAntVD) {
		this.nroCertificadoAntVD = nroCertificadoAntVD;
	}


	public String getEstadoVD() {
		return estadoVD;
	}


	public void setEstadoVD(String estadoVD) {
		this.estadoVD = estadoVD;
	}


	public String getEstadoImpVD() {
		return estadoImpVD;
	}


	public void setEstadoImpVD(String estadoImpVD) {
		this.estadoImpVD = estadoImpVD;
	}


	public String getMotivoVD() {
		return motivoVD;
	}


	public void setMotivoVD(String motivoVD) {
		this.motivoVD = motivoVD;
	}


	public String getObservacionVD() {
		return observacionVD;
	}


	public void setObservacionVD(String observacionVD) {
		this.observacionVD = observacionVD;
	}


	public String getOpcionApesegVD() {
		return opcionApesegVD;
	}


	public void setOpcionApesegVD(String opcionApesegVD) {
		this.opcionApesegVD = opcionApesegVD;
	}


	public Date getFecReporteApesegVD() {
		return fecReporteApesegVD;
	}


	public void setFecReporteApesegVD(Date fecReporteApesegVD) {
		this.fecReporteApesegVD = fecReporteApesegVD;
	}


	public String getOpcionSbsVD() {
		return opcionSbsVD;
	}


	public void setOpcionSbsVD(String opcionSbsVD) {
		this.opcionSbsVD = opcionSbsVD;
	}


	public Date getFecReporteSbsVD() {
		return fecReporteSbsVD;
	}


	public void setFecReporteSbsVD(Date fecReporteSbsVD) {
		this.fecReporteSbsVD = fecReporteSbsVD;
	}


	public Long getPkDetalleTramaDiariaAux() {
		return pkDetalleTramaDiariaAux;
	}


	public void setPkDetalleTramaDiariaAux(Long pkDetalleTramaDiariaAux) {
		this.pkDetalleTramaDiariaAux = pkDetalleTramaDiariaAux;
	}


	public DetalleTramaDiaria getDetalleTramaDiariaAux() {
		return detalleTramaDiariaAux;
	}


	public void setDetalleTramaDiariaAux(DetalleTramaDiaria detalleTramaDiariaAux) {
		this.detalleTramaDiariaAux = detalleTramaDiariaAux;
	}


	public String getIdMotivoVDR() {
		return idMotivoVDR;
	}


	public void setIdMotivoVDR(String idMotivoVDR) {
		this.idMotivoVDR = idMotivoVDR;
	}


	public String getObservacionVDR() {
		return observacionVDR;
	}


	public void setObservacionVDR(String observacionVDR) {
		this.observacionVDR = observacionVDR;
	}


	public String getNroCertificadoVDR() {
		return nroCertificadoVDR;
	}


	public void setNroCertificadoVDR(String nroCertificadoVDR) {
		this.nroCertificadoVDR = nroCertificadoVDR;
	}


	public String getIdMotivoVDA() {
		return idMotivoVDA;
	}


	public void setIdMotivoVDA(String idMotivoVDA) {
		this.idMotivoVDA = idMotivoVDA;
	}


	public String getObservacionVDA() {
		return observacionVDA;
	}


	public void setObservacionVDA(String observacionVDA) {
		this.observacionVDA = observacionVDA;
	}


	public boolean isDisabledComponentes() {
		return disabledComponentes;
	}


	public void setDisabledComponentes(boolean disabledComponentes) {
		this.disabledComponentes = disabledComponentes;
	}


	public boolean isDisabledBtnEditar() {
		return disabledBtnEditar;
	}


	public void setDisabledBtnEditar(boolean disabledBtnEditar) {
		this.disabledBtnEditar = disabledBtnEditar;
	}


	public boolean isDisabledBtnActualizar() {
		return disabledBtnActualizar;
	}


	public void setDisabledBtnActualizar(boolean disabledBtnActualizar) {
		this.disabledBtnActualizar = disabledBtnActualizar;
	}


	public boolean isDisabledBtnReimprimir() {
		return disabledBtnReimprimir;
	}


	public void setDisabledBtnReimprimir(boolean disabledBtnReimprimir) {
		this.disabledBtnReimprimir = disabledBtnReimprimir;
	}


	public boolean isDisabledBtnAnular() {
		return disabledBtnAnular;
	}


	public void setDisabledBtnAnular(boolean disabledBtnAnular) {
		this.disabledBtnAnular = disabledBtnAnular;
	}


	public boolean isShownBtnPostReimprimir() {
		return shownBtnPostReimprimir;
	}


	public void setShownBtnPostReimprimir(boolean shownBtnPostReimprimir) {
		this.shownBtnPostReimprimir = shownBtnPostReimprimir;
	}


	public boolean isDisabledprimaBrutaVD() {
		return disabledprimaBrutaVD;
	}


	public void setDisabledprimaBrutaVD(boolean disabledprimaBrutaVD) {
		this.disabledprimaBrutaVD = disabledprimaBrutaVD;
	}


	public boolean isDisabledfecVentaVD() {
		return disabledfecVentaVD;
	}


	public void setDisabledfecVentaVD(boolean disabledfecVentaVD) {
		this.disabledfecVentaVD = disabledfecVentaVD;
	}


	public boolean isPanelStep1Flag() {
		return panelStep1Flag;
	}


	public void setPanelStep1Flag(boolean panelStep1Flag) {
		this.panelStep1Flag = panelStep1Flag;
	}


	public boolean isPanelStep2Flag() {
		return panelStep2Flag;
	}


	public void setPanelStep2Flag(boolean panelStep2Flag) {
		this.panelStep2Flag = panelStep2Flag;
	}


	public String getTituloCabecera() {
		return tituloCabecera;
	}


	public void setTituloCabecera(String tituloCabecera) {
		this.tituloCabecera = tituloCabecera;
	}
	
	
	
	
	
} //RepConsultaSOATController
