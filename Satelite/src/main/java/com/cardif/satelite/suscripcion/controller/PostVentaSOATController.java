package com.cardif.satelite.suscripcion.controller;

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

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import cl.cardif.soap.datos.cliente.generaPoliza.DocumentoPDF;

import com.cardif.framework.controller.BaseController;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.service.CanalProductoService;
import com.cardif.satelite.configuracion.service.CategoriaClaseService;
import com.cardif.satelite.configuracion.service.MarcaVehiculoService;
import com.cardif.satelite.configuracion.service.ModeloVehiculoService;
import com.cardif.satelite.configuracion.service.ParametroAutomatService;
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
import com.cardif.satelite.suscripcion.bean.ReportePostVentaSOAT;
import com.cardif.satelite.suscripcion.handler.PolizaGeneratorHandler;
import com.cardif.satelite.suscripcion.service.PostVentaSOATService;
import com.cardif.satelite.util.Base64Util;

@Scope("request")
@Controller("postVentaSOATController")
public class PostVentaSOATController extends BaseController
{
	private static final Logger logger = Logger.getLogger(PostVentaSOATController.class);
	
	@Autowired
	private PostVentaSOATService postVentaSOATService;
	
	@Autowired
	private CanalProductoService canalProductoService;
	
	@Autowired
	private SocioService socioService;
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private CategoriaClaseService categoriaClaseService;
	
	@Autowired
	private UsoVehiculoService usoVehiculoService;
	
	@Autowired
	private MarcaVehiculoService marcaVehiculoService;
	
	@Autowired
	private ModeloVehiculoService modeloVehiculoService;
	
	@Autowired
	private ParametroAutomatService parametroService;
	
	
	/* Ventana Principal - PAGINA 1 */
	private List<SelectItem> comboListaSocios;
	private List<SelectItem> comboListaCanales;
	
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
	
	/* Ventana Principal - PAGINA 1 */
	private Long idSocio;
	private Long idCanal;
	private String numPlaca;
	private String numCertificado;
	private String numDocumentoID;
	private String nombreContratante;
	private Date fecVentaDesde;
	private Date fecVentaHasta;
	
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
	
	/* Ventana Principal - PAGINA 1 */
	private boolean disabledBtnExportar;
	
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
			
			this.listaPostVentaSOAT = new ArrayList<ConsultaPostVentaSOAT>();
			
			this.idSocio = null;
			this.idCanal = null;
			this.numPlaca = null;
			this.numCertificado = null;
			this.numDocumentoID = null;
			this.nombreContratante = null;
			this.fecVentaDesde = null;
			this.fecVentaHasta = null;
			
			
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
			List<ParametroAutomat> listaTipoDoc = parametroService.buscarPorCodParam(Constantes.COD_PARAM_TIPO_DOCUMENTO_ID);
			this.comboListaTipoDoc.add(new SelectItem("", "- Seleccione -"));
			for (ParametroAutomat tipoDocumento : listaTipoDoc)
			{
				if (logger.isDebugEnabled()) {logger.debug("TIPO_DOCUMENTO_ID ==> COD_VALOR: " + tipoDocumento.getCodValor() + " - NOMBRE_VALOR: " + tipoDocumento.getNombreValor());}
				this.comboListaTipoDoc.add(new SelectItem(tipoDocumento.getCodValor(), tipoDocumento.getNombreValor()));
			}
			
			if (logger.isInfoEnabled()) {logger.info("Buscando la lista de SEXO.");}
			List<ParametroAutomat> listaSexo = parametroService.buscarPorCodParam(Constantes.COD_PARAM_SEXO);
			this.comboListaSexo.add(new SelectItem("", "- Seleccione -"));
			for (ParametroAutomat sexo : listaSexo)
			{
				if (logger.isDebugEnabled()) {logger.debug("SEXO ==> COD_VALOR: " + sexo.getCodValor() + " - NOMBRE_VALOR: " + sexo.getNombreValor());}
				this.comboListaSexo.add(new SelectItem(sexo.getCodValor(), sexo.getNombreValor()));
			}
			
			
			/* Agregar el valor inicial para el Combo SocioProductos */
			this.comboListaCanales.add(new SelectItem("", "- Seleccione -"));
			
			
			/* Deshabilitar boton 'Exportar' */
			this.disabledBtnExportar = true;
			
			
			/* Iniciando en la PAGINA 1 */
			this.panelStep1Flag = true;
			this.panelStep2Flag = false;
			
			
			/* Iniciando sin agregar nada adicional a la cabecera */
			this.tituloCabecera = null;
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
			logger.error("Exception(" + e.getClass().getName()  + ") -->" + ExceptionUtils.getStackTrace(e));
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
				if (logger.isDebugEnabled()) {logger.debug("Buscar canales relacionados al SOCIO: " + (Long) event.getNewValue());}
				List<CanalProducto> listaCanalProducto = canalProductoService.buscarPorCodSocio(Constantes.PRODUCTO_MOD_SUSCRIPCION_SI, (Long) event.getNewValue());
				
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
	
	public String buscarMarcaVehiculo(ValueChangeEvent event)
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			/* Limpiar */
			this.comboListaMarca = new ArrayList<SelectItem>(); 
			this.comboListaMarca.add(new SelectItem("", "- Seleccione -"));
			this.comboListaModelo = new ArrayList<SelectItem>();
			this.comboListaModelo.add(new SelectItem("", "- Seleccione -"));
			
			this.idMarcaVehiculoVD = null;
			this.idModeloVehiculoVD = null;
			
			
			if (null != event.getNewValue())
			{
				cargarComboListaMarca((String) event.getNewValue());
			}
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
	} //buscarMarcaVehiculo
	
	public String buscarModeloVehiculo(ValueChangeEvent event)
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			/* Limpiar */
			this.comboListaModelo = new ArrayList<SelectItem>();
			this.comboListaModelo.add(new SelectItem("", "- Seleccione -"));
			
			if(logger.isDebugEnabled()){
				logger.debug("event.getNewValue()" + event.getNewValue());	
			}
			
			if (null != event.getNewValue())
			{
				cargarComboListaModelo((Long) event.getNewValue());
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
	} //buscarModeloVehiculo
	
	public String nroRegistros = "0";
	
	public String procesarBusqueda()
	{
		if (logger.isInfoEnabled()) {logger.info("Inico");}
		String respuesta = null;
		
		try
		{
			if(this.fecVentaDesde != null &&  this.fecVentaHasta !=null){
				if(fecVentaHasta.before(fecVentaDesde)){
					throw new SyncconException(ErrorConstants.COD_ERROR_RANGO_FECHAS, FacesMessage.SEVERITY_INFO);
				}
			}
			
			if (!validarFiltrosBusqueda())
			{
				throw new SyncconException(ErrorConstants.COD_ERROR_ARMAR_LOTE_VALIDAR_FILTRO_MINIMO);
				
			}
			
			
			if (logger.isDebugEnabled())
			{
				logger.debug("\n##################### PARAMETROS DE LA BUSQUEDA #####################" +
						"\nID_SOCIO: " + this.idSocio + "\tID_CANAL: " + this.idCanal + "\tNUM_PLACA: " + this.numPlaca + "\tNUM_CERTIFICADO: " + this.numCertificado +
						"\nNUM_DOCUMENTO_ID: " + this.numDocumentoID + "\tNOMBRE_CONTRATANTE: " + this.nombreContratante +
						"\nFEC_VENTA_DESDE: " + this.fecVentaDesde + "\tFEC_VENTA_HASTA: " + this.fecVentaHasta);
			}
			
			
			if (logger.isDebugEnabled()) {logger.info("Se obtiene la lista de polizas para la consulta POST VENTA.");}
			this.listaPostVentaSOAT = postVentaSOATService.buscarPolizasPostVentaSOAT(this.idSocio,	this.idCanal, this.numPlaca, this.numCertificado, 
					this.numDocumentoID, this.nombreContratante, this.fecVentaDesde, this.fecVentaHasta);
			
			if (logger.isInfoEnabled()) {logger.info("Se encontraron [" + (null != this.listaPostVentaSOAT ? this.listaPostVentaSOAT.size() : 0) + "] registros en la lista de Post Venta SOAT.");}
			this.disabledBtnExportar = (null != this.listaPostVentaSOAT && 0 < this.listaPostVentaSOAT.size()) ? false : true;
			
			this.nroRegistros = String.valueOf(this.listaPostVentaSOAT.size());
		}
		catch (SyncconException e)
		{
			logger.error("SyncconException() - " + ErrorConstants.ERROR_SYNCCON + e.getMessageComplete());
	    	FacesMessage facesMsg = new FacesMessage(e.getSeveridad(), e.getMessage(), null);
	    	FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
	    	FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
	    	FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	    	respuesta = ErrorConstants.MSJ_ERROR;
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
			if (logger.isDebugEnabled()) {logger.info("Se obtiene la lista de polizas para el reporte de POST VENTA.");}
			List<ReportePostVentaSOAT> listaReportePostVenta = postVentaSOATService.buscarPolizasParaReportePostVentaSOAT(this.idSocio,	this.idCanal, this.numPlaca, this.numCertificado, 
					this.numDocumentoID, this.nombreContratante, this.fecVentaDesde, this.fecVentaHasta);
			
			if (logger.isInfoEnabled()) {logger.info("Se obtuvieron [" + (null != listaReportePostVenta ? listaReportePostVenta.size() : 0) + "] registros para el reporte de POST VENTA.");}
			
			
			if (null != listaReportePostVenta && 0 < listaReportePostVenta.size())
			{
				Map<String, Object> beans = new HashMap<String, Object>();
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				long timeInMillis = System.currentTimeMillis();
				String fechaActual = sdf.format(new Date(timeInMillis));
				
				ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
				
				
				String rutaTemp = System.getProperty("java.io.tmpdir") + "Reporte_Post_Venta_SOAT_" + fechaActual + "_" + timeInMillis + ".xls";
		        if (logger.isDebugEnabled()) {logger.debug("Ruta Archivo: " + rutaTemp);}
		        
		        FacesContext facesContext = FacesContext.getCurrentInstance();
		        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
		        
		        String rutaTemplate = servletContext.getRealPath(File.separator + "excel" + File.separator + "template_exportar_reporte_post_venta_soat.xls");
		        if (logger.isDebugEnabled()) {logger.debug("Ruta del Template: " + rutaTemplate);}
		        
		        beans.put("exportar", listaReportePostVenta);
				
		        XLSTransformer transformer = new XLSTransformer();
		        transformer.transformXLS(rutaTemplate, beans, rutaTemp);
		        
		        File archivoResp = new File(rutaTemp);
		        FileInputStream fis = new FileInputStream(archivoResp);
				
		        HttpServletResponse response = (HttpServletResponse) contexto.getResponse();
		        byte[] loader = new byte[(int) archivoResp.length()];
		        response.addHeader("Content-Disposition", "attachment;filename=" + "Reporte_Post_Venta_SOAT_" + fechaActual + "_" + timeInMillis + ".xls");
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
			
			cleanInformacionVerDetalle();
			
			if (0 == this.comboListaMotivoReimp.size())
			{
				if (logger.isInfoEnabled()) {logger.info("Buscando la lista de MOTIVOS DE REIMPRESION");}
				List<ParametroAutomat> listaMotivoReimp = parametroService.buscarPorCodParam(Constantes.COD_PARAM_MOTIVO_REIMPRESION);
				this.comboListaMotivoReimp.add(new SelectItem("", "- Seleccione -"));
				
				for (ParametroAutomat motivoReimp : listaMotivoReimp)
				{
					if (logger.isDebugEnabled()) {logger.debug("MOTIVO_REIMPRESION ==> COD_VALOR: " + motivoReimp.getCodValor() + " - NOMBRE_VALOR: " + motivoReimp.getNombreValor());}
					this.comboListaMotivoReimp.add(new SelectItem(motivoReimp.getCodValor(), motivoReimp.getNombreValor()));
				}
			}
			
			if (0 == this.comboListaMotivoAnul.size())
			{
				if (logger.isInfoEnabled()) {logger.info("Buscando la lista de MOTIVOS DE ANULACION");}
				List<ParametroAutomat> listaMotivoAnul = parametroService.buscarPorCodParam(Constantes.COD_PARAM_MOTIVO_ANULACION);
				this.comboListaMotivoAnul.add(new SelectItem("", "- Seleccione -"));
				
				for (ParametroAutomat motivoAnul : listaMotivoAnul)
				{
					if (logger.isDebugEnabled()) {logger.debug("MOTIVO_ANULACION ==> COD_VALOR: " + motivoAnul.getCodValor() + " - NOMBRE_VALOR: " + motivoAnul.getNombreValor());}
					this.comboListaMotivoAnul.add(new SelectItem(motivoAnul.getCodValor(), motivoAnul.getNombreValor()));
				}
			}
			
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
				logger.info("ID:::"+getDetalleTramaDiariaAux().getId());
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
	} //volverPantallaPrincipal
	
	public String procesoEditarPoliza()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		
		/* Habilitando todos los componentes visuales editables */
		this.disabledComponentes = false;
		
		
		/*
		 * Deshabilitar
		 * - boton 'Editar'
		 * - boton 'Re-imprimir'
		 * - boton 'Anular'
		 * - boton 'Ver Certificado'
		 * - boton 'Confirmar Certificado'
		 */
		this.disabledBtnEditar = true;
		this.disabledBtnReimprimir = true;
		this.disabledBtnAnular = true;
		this.shownBtnPostReimprimir = false;
        this.disabledprimaBrutaVD=false;
        this.disabledfecVentaVD=false;
        
		
		/*
		 * Habilitar
		 * - boton 'Actualizar'
		 */
		this.disabledBtnActualizar = false;
		
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return null;
	} //procesoEditarPoliza
	
	public String procesoActualizarPoliza()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			boolean obligatoriosOK = validarCamposObligatorios();
			if (!obligatoriosOK)
			{
				throw new SyncconException(ErrorConstants.COD_ERROR_POST_VENTA_ACTUALIZAR_VACIOS);
			}
			
			if (logger.isDebugEnabled()) {logger.debug("Se valido los campos obligatorios.");}
			
			
			/*
			 * Instanciando el HANDLER generador de la POLIZA, para validar
			 * que exista conexion con el WS
			 */
			PolizaGeneratorHandler polizaHandler = PolizaGeneratorHandler.newInstance();
			
			/* Generando el DetalleTramaDiaria bean */
			DetalleTramaDiaria detTramaDiariaBean = createDetalleTramaDiariaForUpdate();
			if (logger.isDebugEnabled()) {logger.debug("Se genero el objeto DetalleTramaDiaria bean.");}
			
			

			if (logger.isDebugEnabled()) {
			logger.debug(
					 "\n getNroPolizaPe"+ detTramaDiariaBean.getNroPolizaPe()
					 +"\n  getPlaca " + detTramaDiariaBean.getPlaca()
					 +"\n  getSerie " + detTramaDiariaBean.getSerie()
					 +"\n  getNroAsientos " + detTramaDiariaBean.getNroAsientos()
					 +"\n  getAnioFab " + detTramaDiariaBean.getAnioFab() 
					 +"\n  getCategoriaClase " + detTramaDiariaBean.getCategoriaClase()
					 +"\n  getUsoVehiculo " + detTramaDiariaBean.getUsoVehiculo() 
					 +"\n  getMarca " + detTramaDiariaBean.getMarca() 
					 +"\n  getModelo " + detTramaDiariaBean.getModelo() 
					 +"\n  getMoneda " + detTramaDiariaBean.getPrimaBruta() 
					 +"\n  getFecEmision " + detTramaDiariaBean.getFecVenta()
					 +"\n  getFecEmision " + detTramaDiariaBean.getHoraVenta()
					 +"\n  getFecIniVigencia " + detTramaDiariaBean.getFecIniVigencia()
					 +"\n  getFecFinVigencia " + detTramaDiariaBean.getFecFinVigencia() 
					 +"\n  getNumDoc " + detTramaDiariaBean.getNumDoc()
					 +"\n  getNomContrat " + detTramaDiariaBean.getNomContrat()
					 +"\n  getApelPateContrat " + detTramaDiariaBean.getApelPateContrat() 
					 +"\n  getApelMateContrat " + detTramaDiariaBean.getApelMateContrat()
					 +"\n  getDepartamento " + detTramaDiariaBean.getDepartamento() 
					 +"\n  getProvincia " + detTramaDiariaBean.getProvincia() 
					 +"\n  getDistrito " + detTramaDiariaBean.getDistrito()
					 +"\n  getDireccion " + detTramaDiariaBean.getDireccion()
					 +"\n  getTelMovil " + detTramaDiariaBean.getTelFijo()								
					);
			}
		
			DocumentoPDF documentoPDF = polizaHandler.generarPolizaPDF(detTramaDiariaBean.getNroPolizaPe(),detTramaDiariaBean.getPlaca(),
					detTramaDiariaBean.getSerie(),detTramaDiariaBean.getNroAsientos(), detTramaDiariaBean.getAnioFab(), 
					detTramaDiariaBean.getCategoriaClase(), detTramaDiariaBean.getUsoVehiculo(), 
					detTramaDiariaBean.getMarca(), detTramaDiariaBean.getModelo(),detTramaDiariaBean.getPrimaBruta(), 
					detTramaDiariaBean.getFecVenta(),detTramaDiariaBean.getHoraVenta().toString(), detTramaDiariaBean.getFecIniVigencia(), detTramaDiariaBean.getFecFinVigencia(), 
					detTramaDiariaBean.getNumDoc(),detTramaDiariaBean.getNomContrat(), detTramaDiariaBean.getApelPateContrat(), 
					detTramaDiariaBean.getApelMateContrat(), detTramaDiariaBean.getDepartamento(), detTramaDiariaBean.getProvincia(), 
					detTramaDiariaBean.getDistrito(), detTramaDiariaBean.getDireccion(),detTramaDiariaBean.getTelFijo());
			
			
			if (logger.isInfoEnabled()) {logger.info("Se genero la Poliza en formato PDF.");}
			logger.debug("documento return documento.getPoliza()" + documentoPDF.getPoliza());

			
			/*
			 * Convertiendo la POLIZA PDF
			 */
			String polizaString = Base64Util.getInstance().convertBase64ToString(documentoPDF.getPoliza());
			if (logger.isDebugEnabled()) {logger.debug("Guardando el PDF Base 64 en el objeto MAP." + polizaString );}

			/* Agregando la nueva Poliza PDF generada */
			detTramaDiariaBean.setPdfCodificadoSinFirmar(polizaString);

			
			DetalleTramaDiaria detTramaDiariaResponse = postVentaSOATService.actualizarPolizaPostVenta(detTramaDiariaBean);
			if (logger.isInfoEnabled()) {logger.info("Se actualizo la informacion de la Poliza: " + detTramaDiariaResponse.getNroPolizaPe() + " con ID: " + detTramaDiariaResponse.getId());}		
			
			
			/* Deshabilitando los objetos editables */
			this.disabledComponentes = true;
			
			/*
			 * Deshabilitar
			 * - boton 'Editar'
			 * - boton 'Actualizar'
			 * - boton 'Confirmar Certificado'
			 */
			this.disabledBtnEditar = true;
			this.disabledBtnActualizar = true;
			
			/*
			 * Habilitar
			 * - boton 'Re-imprimir'
			 * - boton 'Anular'
			 */
			this.disabledBtnReimprimir = false;
			this.disabledBtnAnular = false;
		}
		catch (SyncconException e)
		{
			logger.error("SyncconException() - ERROR: " + ErrorConstants.ERROR_SYNCCON + e.getMessageComplete());
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
	} //procesoActualizarPoliza

	public String cargarReimprimirPoliza()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		
		/* Limpiando parametros */
		setIdMotivoVDR(null);
		setObservacionVDR(null);
		setNroCertificadoVDR(null);		
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return null;
	} //cargarReimprimirPoliza
	
	public String procesoReimprimirPoliza()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("\n##################### PARAMETROS DE RE-IMPRESIÓN #####################" + 
						"\nID_MOTIVO_VDR: " + this.idMotivoVDR + "\tOBVSERVACION_VDR: " + this.observacionVDR + "\tNRO_CERTIFICADO_VDR: " + this.nroCertificadoVDR);
			}
			
			if (logger.isInfoEnabled()) {logger.info("El numero de certificado [" + this.nroCertificadoVDR + "] fue validado.");}
			
			/*
			 * Validando el formato del certificado ingresado
			 */
			if (!validarFormatoCertificado(this.nroCertificadoVDR))
			{
				throw new SyncconException(ErrorConstants.COD_ERROR_POST_VENTA_SOAT_FORMATO_CERT);
			}
			
			/*
			 * Verificando disponibilidad del certificado
			 */
			boolean certificadoDisp = postVentaSOATService.validarNumeroDocValorado(this.nroCertificadoVDR);
			if (certificadoDisp)
			{
				if (logger.isInfoEnabled()) {logger.info("El numero de certificado [" + this.nroCertificadoVDR + "] fue validado.");}
				
				if (logger.isInfoEnabled()) {logger.info("Guardando la informacion en la tabla DETALLE_TRAMA_DIARIA_MOTIVO.");}
				String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
				String motivoDesc = getComboListaMotivoReimpDesc(this.idMotivoVDR);
				
				Long pkNuevoNumDocValorado = postVentaSOATService.nuevoCertificadoPorReimpresion(motivoDesc, this.observacionVDR, Constantes.DET_TRAMA_DIA_MOTIVO_REIMPRESION,
						getPkDetalleTramaDiariaAux(), usuario, this.nroCertificadoVDR ,getCanalVentaVD());
				if (logger.isInfoEnabled()) {logger.info("Se dio de alta al nuevo NRO. CERTIFICADO [" + this.nroCertificadoVDR + "] en la POLIZA: " + getPkDetalleTramaDiariaAux() + ". PK_CERT: " + pkNuevoNumDocValorado);}
				
				
				
				DetalleTramaDiaria detalleTramaDiaria = postVentaSOATService.buscarDetalleTramaDiariaPorPK(getPkDetalleTramaDiariaAux());
				if (logger.isDebugEnabled()) {logger.debug("Se encontro el objeto DetalleTramaDiaria con PK: " + getPkDetalleTramaDiariaAux());}
				
				/* Guardar el objeto DetalleTramaDiaria */
				setDetalleTramaDiariaAux(detalleTramaDiaria);
				
				/*
				 * Actualizando en pantalla la informacion de los certificados
				 * (Actual y Anterior)
				 */
				setNroCertificadoVD(getDetalleTramaDiariaAux().getNroCertificado());
				setNroCertificadoAntVD(getDetalleTramaDiariaAux().getNroCertificadoAnt());
				
				/*
				 * Habilitando los botones
				 * - Ver Certificado
				 * - Confirmar Certificado
				 */
				this.shownBtnPostReimprimir = true;
				this.disabledBtnAnular = true;
				this.disabledBtnReimprimir=true;
				this.disabledBtnEditar=true;
				this.disabledprimaBrutaVD=true;
				
			
			}
			else
			{
				this.nroCertificadoVDR = null;
				throw new SyncconException(ErrorConstants.COD_ERROR_POST_VENTA_SOAT_CERT_NO_DISPONIBLE);
			}
			
		
		}
		catch (SyncconException e)
		{
			logger.error("SyncconException() - " + ErrorConstants.ERROR_SYNCCON + e.getMessageComplete());
	    	FacesMessage facesMsg = new FacesMessage(e.getSeveridad(), e.getMessage(), null);
	    	FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
	    	FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
	    	FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	    	respuesta = ErrorConstants.MSJ_ERROR;
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //procesoReimprimirPoliza
	
	public String descargarPDFPolizaSOAT()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			if (logger.isInfoEnabled()) {logger.info("Extrayendo el SOAT de la POLIZA con DetalleTramaDiaria(" + getPkDetalleTramaDiariaAux() + ")");}
			
			String pdfCodificadoPoliza = postVentaSOATService.buscarPDFByPoliza(getPkDetalleTramaDiariaAux());
			if (StringUtils.isNotBlank(pdfCodificadoPoliza))
			{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String fechaActual = sdf.format(new Date(System.currentTimeMillis()));
				long timeInMillis = System.currentTimeMillis();
				
				byte[] pdfPolizaBytes = Base64Util.getInstance().convertStringToBase64(pdfCodificadoPoliza);
				if (logger.isDebugEnabled()) {logger.debug("PDF Poliza en bytes: " + (null != pdfPolizaBytes ? pdfPolizaBytes.length : 0));}
				 
				/*
				 * Colocando el PDF de la poliza en el DISCO DURO para poder manejarlo
				 */
				File fileTemp = Base64Util.getInstance().saveFileToTemp(pdfPolizaBytes, "SOAT_PDFPoliza_" + fechaActual + "_" + timeInMillis + ".pdf");
				if (logger.isDebugEnabled()) {logger.debug("Se guardo el PDF de la Poliza en la ruta temporal: " + fileTemp.getAbsolutePath());}
				
				ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
				FileInputStream fis = new FileInputStream(fileTemp);
				
				HttpServletResponse response = (HttpServletResponse) context.getResponse();
				byte[] loader = new byte[(int) fileTemp.length()];
				
				response.addHeader("Content-Disposition", "attachment; filename=SOAT_PDFPoliza_" + fechaActual + "_" + timeInMillis + ".pdf");
				response.setContentType("application/pdf");
				
				ServletOutputStream sos = response.getOutputStream();
				while ((fis.read(loader)) > 0)
				{
					sos.write(loader, 0, loader.length);
				}
				fis.close();
				sos.close();
				
				FacesContext.getCurrentInstance().responseComplete();
				if (logger.isInfoEnabled()) {logger.info("Se descargo el PDF de la POLIZA correctamente.");}
			}
			else
			{
				throw new SyncconException(ErrorConstants.COD_ERROR_DESCARGAR_PDF_VACIO);
			}
		}
		catch (SyncconException e)
		{
			logger.error("SyncconException() - " + ErrorConstants.ERROR_SYNCCON + e.getMessageComplete());
			FacesMessage facesMsg = new FacesMessage(e.getSeveridad(), e.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	    	respuesta = ErrorConstants.MSJ_ERROR;
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //descargarPDFPolizaSOAT
	
	public String procesoConfirmarCertificado()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			if (logger.isInfoEnabled()) {logger.info("Confirmar el certificado ingresado para la POLIZA con DetalleTramaDiaria(" + getPkDetalleTramaDiariaAux() + ").");}
			boolean isOK = postVentaSOATService.actualizarEstadoImpresionDePoliza(getPkDetalleTramaDiariaAux(), Constantes.MOD_IMPRESION_ESTADO_REIMPRESO);
			
			if (isOK)
			{
				if (logger.isInfoEnabled()) {logger.info("La poliza fue confirmada correctamente? " + isOK);}
				
				
				DetalleTramaDiaria detalleTramaDiaria = postVentaSOATService.buscarDetalleTramaDiariaPorPK(getPkDetalleTramaDiariaAux());
				if (logger.isDebugEnabled()) {logger.debug("Se encontro el objeto DetalleTramaDiaria con PK: " + getPkDetalleTramaDiariaAux());}
				
				/* Guardar el objeto DetalleTramaDiaria */
				setDetalleTramaDiariaAux(detalleTramaDiaria);
				
				/*
				 * Actualizando en pantalla el estado de impresion de
				 * la poliza
				 */
				setEstadoImpVD(getDetalleTramaDiariaAux().getEstadoImpresion());
				
				
				/*
				 * Habilitando los botones necesarios
				 */
				this.disabledBtnEditar = false;
				this.disabledBtnActualizar = true;
				this.disabledBtnReimprimir = false;
				this.disabledBtnAnular = false;
				
				/*
				 * Ocultando los botones
				 * - Ver Certificado
				 * - Confirmar Certificado
				 */
				this.shownBtnPostReimprimir = false;
			}
			else
			{
				throw new SyncconException(ErrorConstants.COD_ERROR_CONFIRMAR_CERT_POST_VENTA);
			}
		}
		catch (SyncconException e)
		{
			logger.error("SyncconException() - " + ErrorConstants.ERROR_SYNCCON + e.getMessageComplete());
	    	FacesMessage facesMsg = new FacesMessage(e.getSeveridad(), e.getMessage(), null);
	    	FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
	    	FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
	    	FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	    	respuesta = ErrorConstants.MSJ_ERROR;
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //procesoConfirmarCertificado
	
	public String cargarAnularPoliza()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		
		/* Limpiando parametros */
		setIdMotivoVDA(null);
		setObservacionVDA(null);
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return null;
	} //cargarAnularPoliza
	
	public String procesoAnularPoliza()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			//IMPLEMENTACION DE LA LOGICA DE VALIDACION DE PLACA CON SINIESTROS
			
			if(StringUtils.isNotBlank(this.numPlacaVD)){
				
			if(postVentaSOATService.anulacionConsultaSoat(this.numPlacaVD)){
				
				if (logger.isDebugEnabled())
				{
					logger.debug("\n##################### PARAMETROS DE ANULACIÓN #####################" + 
							"\nID_MOTIVO_VDA: " + this.idMotivoVDA + "\tOBVSERVACION_VDA: " + this.observacionVDA + "\tID_DETALLE_TRAMA_DIARIA: " + getPkDetalleTramaDiariaAux());
				}
				//TODO AGREGAR FUNCIONALIDAD DE VERIFICACION DE SINIESTRO - PARAMETRO DE ENTRADA NRO_POLIZA_PE
				
				String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
				String motivoDesc = getComboListaMotivoAnulDesc(this.idMotivoVDA);
				
				if (logger.isInfoEnabled()) {logger.info("Anulando la Poliza POST VENTA. NRO_POLIZA: " + getDetalleTramaDiariaAux().getNroPolizaPe() + " con ID: " + getPkDetalleTramaDiariaAux());}
				DetalleTramaDiaria detalleTramaDiaria = postVentaSOATService.anularPolizaPostVentaSOAT(motivoDesc, this.observacionVDA, 
						Constantes.DET_TRAMA_DIA_MOTIVO_ANULACION, getPkDetalleTramaDiariaAux(), usuario);
				
				
				/* Guardar el objeto DetalleTramaDiaria */
				setDetalleTramaDiariaAux(detalleTramaDiaria);
				
				/* Guardando el PK del objeto DetalleTramaDiaria */
				setPkDetalleTramaDiariaAux(pkDetalleTramaDiariaAux);
				
				/*
				 * Guardar la descripcion del motivo de la anulacion en
				 * la pantalla VER DETALLE
				 */
				setMotivoVD(motivoDesc);
				
				/*
				 * Guardar la observacion del motivo de la anulacion en
				 * la pantalla VER DETALLE
				 */
				setObservacionVD(getObservacionVDA());
				
				
				/*
				 * Actualizando la informacion en PANTALLA
				 */
				{
					setEstadoVD(getDetalleTramaDiariaAux().getEstado());
					
					/* Deshabilitando la edicion de los componentes */
					this.disabledComponentes = true;
					
					/* Deshabilitando todos los botones */
					this.disabledBtnEditar = true;
					this.disabledBtnActualizar = true;
					this.disabledBtnReimprimir = true;
					this.disabledBtnAnular = true;
					
					/* Ocultando los botones de POST REIMPRESION */
					this.shownBtnPostReimprimir = false;
					
					}
				}else{
				throw new SyncconException(ErrorConstants.COD_ERROR_POST_VENTA_VALIDACION_SAS);
				}			
			}else{
				throw new SyncconException(ErrorConstants.COD_ERROR_POST_VENTA_SOAT_NULL);

			}	
		}
		catch (SyncconException e)
		{
			logger.error("SyncconException() - " + ErrorConstants.ERROR_SYNCCON + e.getMessageComplete());
	    	FacesMessage facesMsg = new FacesMessage(e.getSeveridad(), e.getMessage(), null);
	    	FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
	    	FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
	    	FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	    	respuesta = ErrorConstants.MSJ_ERROR;
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //procesoAnularPoliza
	
	private void cleanInformacionVerDetalle()
	{
		if (logger.isDebugEnabled()) {logger.debug("Inicio");}
		
		/* Agregar el valor inicial para el ComboBox de Modelo */
		this.comboListaModelo = new ArrayList<SelectItem>();
		this.comboListaModelo.add(new SelectItem("", "- Seleccione -"));
		
		/* Agregar el valor inicial para el ComboBox de Marca */
		this.comboListaMarca = new ArrayList<SelectItem>();
		this.comboListaMarca.add(new SelectItem("", "- Seleccione -"));
		
		
		setPkDetalleTramaDiariaAux(null);
		setDetalleTramaDiariaAux(null);
		
		/* Cabecera */
		setSocioProductoVD(null);
		setNroPolizaVD(null);
		
		/* Datos del Vehiculo */
		setNumPlacaVD(null);
		setNumSerieVD(null);
		setNumAsientosVD(null);
		setAnioFabVD(null);
		setIdCategoriaClaseVD(null);
		setIdUsoVehiculoVD(null);
		setIdMarcaVehiculoVD(null);
		setIdModeloVehiculoVD(null);
		
		/* Datos del Propietario */
		setIdTipoDocVD(null);
		setNumDocumentoVD(null);
		setFecNacimientoVD(null);
		setIdSexoVD(null);
		setApePaternoVD(null);
		setApeMaternoVD(null);
		setNombresVD(null);
		
		/* Datos de la Poliza */
		setPrimaBrutaVD(null);
		setFecIniVigenciaVD(null);
		setFecFinVigenciaVD(null);
		setFecVentaVD(null);
		setCanalVentaVD(null);
		setNroCertificadoVD(null);
		setNroCertificadoAntVD(null);
		setEstadoVD(null);
		setMotivoVD(null);
		setObservacionVD(null);
		setOpcionApesegVD("NO");
		setFecReporteApesegVD(null);
		setOpcionSbsVD("NO");
		setFecReporteSbsVD(null);
		if (logger.isDebugEnabled()) {logger.debug("Fin");}
	} //cleanInformacionVerDetalle
	
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
	
	private boolean validarFormatoCertificado(String certificado)
	{
		boolean flag = true;
		
		if (StringUtils.isBlank(certificado))
		{
			flag = false;
		}
		else if (12 < numCertificado.length()  &&  numCertificado.length() > 14)
		{
			flag = false;
		}
		return flag;
	} //validarFormatoCertificado
	
	private String getComboListaMotivoReimpDesc(String idMotivoReimp)
	{
		String response = null;
		
		if (null != this.comboListaMotivoReimp && 0 < this.comboListaMotivoReimp.size())
		{
			for (SelectItem itemMotivoReimp : this.comboListaMotivoReimp)
			{
				if (itemMotivoReimp.getValue().toString().equalsIgnoreCase(idMotivoReimp))
				{
					response = itemMotivoReimp.getLabel(); break;
				}
			}
		}
		return response;
	} //getComboListaMotivoReimpDesc
	
	public String getComboListaMotivoAnulDesc(String idMotivoAnul)
	{
		String response = null;
		
		if (null != this.comboListaMotivoAnul && 0 < this.comboListaMotivoAnul.size())
		{
			for (SelectItem itemMotivoAnul : this.comboListaMotivoAnul)
			{
				if (itemMotivoAnul.getValue().toString().equalsIgnoreCase(idMotivoAnul))
				{
					response = itemMotivoAnul.getLabel(); break;
				}
			}
		}
		return response;
	} //getComboListaMotivoAnulDesc
	
	private boolean validarCamposObligatorios()
	{
		/* Datos del Vehiculo */
		if (StringUtils.isBlank(getNumPlacaVD()) || StringUtils.isBlank(getNumSerieVD()) || (null == getNumAsientosVD() || 0 == getNumAsientosVD()) || (null == getAnioFabVD() || 1950 > getAnioFabVD()))
		{
			return false;
		}
		if (StringUtils.isBlank(getIdCategoriaClaseVD()) || StringUtils.isBlank(getIdUsoVehiculoVD()) || (null == getIdMarcaVehiculoVD() || 0 == getIdMarcaVehiculoVD()) || (null == getIdModeloVehiculoVD() || 0 == getIdModeloVehiculoVD()))
		{
			return false;
		}
		
		/* Datos del Propietario */
		if (StringUtils.isBlank(getIdTipoDocVD()) || StringUtils.isBlank(getNumDocumentoVD()) || (null == getFecNacimientoVD()) || StringUtils.isBlank(getIdSexoVD()))
		{
			return false;
		}
		if (StringUtils.isBlank(getApePaternoVD()) || StringUtils.isBlank(getApeMaternoVD()) || StringUtils.isBlank(getNombresVD()))
		{
			return false;
		}
		
		/* Datos de la Poliza */
		if ((null == getPrimaBrutaVD() || 0D == getPrimaBrutaVD()) || (null == getFecIniVigenciaVD()) || (null == getFecFinVigenciaVD()))
		{
			return false;
		}
		if ((null == getFecVentaVD()))
		{
			return false;
		}
		
		return true;
	} //validarCamposObligatorios
	
	private boolean validarFiltrosBusqueda()
	{
		if (logger.isDebugEnabled()) {logger.info("Inicio - Fin");}
		boolean flag = true;
		
		if ((null == idSocio || 0 == idSocio) && (null == idCanal || 0 == idCanal) && StringUtils.isBlank(numPlaca) && StringUtils.isBlank(numCertificado) 
				&& StringUtils.isBlank(numDocumentoID) && StringUtils.isBlank(nombreContratante) && null == fecVentaDesde && null == fecVentaHasta)
		{
			flag = false;
		}
		return flag;
	} //validarFiltrosBusqueda
	
	private DetalleTramaDiaria createDetalleTramaDiariaForUpdate() throws Exception
	{
		if (logger.isDebugEnabled()) {logger.debug("Inicio");}
		DetalleTramaDiaria detalleTramaDiaria = null;
		
		try
		{
			detalleTramaDiaria = new DetalleTramaDiaria();
			
			/* Guardar el PK de la tabla */
			detalleTramaDiaria.setId(getPkDetalleTramaDiariaAux());
			
			detalleTramaDiaria.setNroPolizaPe(getDetalleTramaDiariaAux().getNroPolizaPe());
			detalleTramaDiaria.setImporteCobroDsc(getDetalleTramaDiariaAux().getImporteCobroDsc());
			detalleTramaDiaria.setFecVenta(getDetalleTramaDiariaAux().getFecVenta());
			detalleTramaDiaria.setHoraVenta(getDetalleTramaDiariaAux().getHoraVenta());
			detalleTramaDiaria.setDepartamento(getDetalleTramaDiariaAux().getDepartamento());
			detalleTramaDiaria.setProvincia(getDetalleTramaDiariaAux().getProvincia());
			detalleTramaDiaria.setDistrito(getDetalleTramaDiariaAux().getDistrito());
			detalleTramaDiaria.setDireccion(getDetalleTramaDiariaAux().getDireccion());
			detalleTramaDiaria.setTelFijo(StringUtils.isNotBlank(getDetalleTramaDiariaAux().getTelFijo()) ? getDetalleTramaDiariaAux().getTelFijo() : getDetalleTramaDiariaAux().getTelMovil());
			
			
			/*
			 * Fila 1 de la VENTANA
			 */
			detalleTramaDiaria.setPlaca(getNumPlacaVD());
			detalleTramaDiaria.setSerie(getNumSerieVD());
			detalleTramaDiaria.setNroAsientos(getNumAsientosVD());
			detalleTramaDiaria.setAnioFab(getAnioFabVD());
			
			/*
			 * Fila 2 de la VENTANA
			 */
			CategoriaClase categoriaClase = categoriaClaseService.buscarByPK(getIdCategoriaClaseVD());
			detalleTramaDiaria.setCodCategoriaClase(categoriaClase.getCodCategoriaClase());
			detalleTramaDiaria.setCategoriaClase(categoriaClase.getDescripcionCategoriaClase());
			
			UsoVehiculo usoVehiculo = usoVehiculoService.buscarByPK(getIdUsoVehiculoVD());
			detalleTramaDiaria.setCodUsoVehiculo(usoVehiculo.getCodUso());
			detalleTramaDiaria.setUsoVehiculo(usoVehiculo.getDescripcionUso());
			
			MarcaVehiculo marcaVehiculo = marcaVehiculoService.buscarPorPK(getIdMarcaVehiculoVD());
			detalleTramaDiaria.setCodMarca(marcaVehiculo.getId());
			detalleTramaDiaria.setMarca(marcaVehiculo.getNombreMarcavehiculo());
			
			ModeloVehiculo modeloVehiculo = modeloVehiculoService.buscarPorPK(getIdModeloVehiculoVD());
			detalleTramaDiaria.setCodModelo(modeloVehiculo.getId());
			detalleTramaDiaria.setModelo(modeloVehiculo.getNombreModelovehiculo());
			
			/*
			 * Fila 3 de la VENTANA
			 */
			detalleTramaDiaria.setTipDoc(getIdTipoDocVD());
			detalleTramaDiaria.setNumDoc(getNumDocumentoVD());
			detalleTramaDiaria.setFecNac(getFecNacimientoVD());
			detalleTramaDiaria.setSexo(getIdSexoVD());
			
			/*
			 * Fila 4 de la VENTANA
			 */
			detalleTramaDiaria.setApelPateContrat(getApePaternoVD());
			detalleTramaDiaria.setApelMateContrat(getApeMaternoVD());
			detalleTramaDiaria.setNomContrat(getNombresVD());
			
			/*
			 * Fila 5 de la VENTANA
			 */
			detalleTramaDiaria.setPrimaBruta(getPrimaBrutaVD());
			detalleTramaDiaria.setFecIniVigencia(getFecIniVigenciaVD());
			detalleTramaDiaria.setFecFinVigencia(getFecFinVigenciaVD());
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			throw e;
		}
		if (logger.isDebugEnabled()) {logger.debug("Fin");}
		return detalleTramaDiaria;
	} //createDetalleTramaDiariaForUpdate
	
	
	//=========================================================================
	//=========================== GET / SET METHODS ===========================
	//=========================================================================
	
	public List<SelectItem> getComboListaSocios()
	{
		return comboListaSocios;
	}
	
	public void setComboListaSocios(List<SelectItem> comboListaSocios)
	{
		this.comboListaSocios = comboListaSocios;
	}
	
	public List<SelectItem> getComboListaCanales()
	{
		return comboListaCanales;
	}
	
	public void setComboListaCanales(List<SelectItem> comboListaCanales)
	{
		this.comboListaCanales = comboListaCanales;
	}
	
	public List<SelectItem> getComboListaCategoria()
	{
		return comboListaCategoria;
	}
	
	public void setComboListaCategoria(List<SelectItem> comboListaCategoria)
	{
		this.comboListaCategoria = comboListaCategoria;
	}
	
	public List<SelectItem> getComboListaUsoVehiculo()
	{
		return comboListaUsoVehiculo;
	}
	
	public void setComboListaUsoVehiculo(List<SelectItem> comboListaUsoVehiculo)
	{
		this.comboListaUsoVehiculo = comboListaUsoVehiculo;
	}
	
	public List<SelectItem> getComboListaMarca()
	{
		return comboListaMarca;
	}
	
	public void setComboListaMarca(List<SelectItem> comboListaMarca)
	{
		this.comboListaMarca = comboListaMarca;
	}
	
	public List<SelectItem> getComboListaModelo()
	{
		return comboListaModelo;
	}
	
	public void setComboListaModelo(List<SelectItem> comboListaModelo)
	{
		this.comboListaModelo = comboListaModelo;
	}
	
	public List<SelectItem> getComboListaTipoDoc()
	{
		return comboListaTipoDoc;
	}
	
	public void setComboListaTipoDoc(List<SelectItem> comboListaTipoDoc)
	{
		this.comboListaTipoDoc = comboListaTipoDoc;
	}
	
	public List<SelectItem> getComboListaSexo()
	{
		return comboListaSexo;
	}
	
	public void setComboListaSexo(List<SelectItem> comboListaSexo)
	{
		this.comboListaSexo = comboListaSexo;
	}
	
	public List<SelectItem> getComboListaMotivoReimp()
	{
		return comboListaMotivoReimp;
	}
	
	public void setComboListaMotivoReimp(List<SelectItem> comboListaMotivoReimp)
	{
		this.comboListaMotivoReimp = comboListaMotivoReimp;
	}
	
	public List<SelectItem> getComboListaMotivoAnul()
	{
		return comboListaMotivoAnul;
	}
	
	public void setComboListaMotivoAnul(List<SelectItem> comboListaMotivoAnul)
	{
		this.comboListaMotivoAnul = comboListaMotivoAnul;
	}
	
	public Long getIdSocio()
	{
		return idSocio;
	}
	
	public void setIdSocio(Long idSocio)
	{
		this.idSocio = idSocio;
	}
	
	public Long getIdCanal()
	{
		return idCanal;
	}
	
	public void setIdCanal(Long idCanal)
	{
		this.idCanal = idCanal;
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
	
	public Date getFecVentaDesde()
	{
		return fecVentaDesde;
	}
	
	public void setFecVentaDesde(Date fecVentaDesde)
	{
		this.fecVentaDesde = fecVentaDesde;
	}
	
	public Date getFecVentaHasta()
	{
		return fecVentaHasta;
	}
	
	public void setFecVentaHasta(Date fecVentaHasta)
	{
		this.fecVentaHasta = fecVentaHasta;
	}
	
	public String getSocioProductoVD()
	{
		return socioProductoVD;
	}
	
	public void setSocioProductoVD(String socioProductoVD)
	{
		this.socioProductoVD = socioProductoVD;
	}
	
	public String getNroPolizaVD()
	{
		return nroPolizaVD;
	}
	
	public void setNroPolizaVD(String nroPolizaVD)
	{
		this.nroPolizaVD = nroPolizaVD;
	}
	
	public String getNumPlacaVD()
	{
		return numPlacaVD;
	}
	
	public void setNumPlacaVD(String numPlacaVD)
	{
		this.numPlacaVD = numPlacaVD;
	}
	
	public String getNumSerieVD()
	{
		return numSerieVD;
	}
	
	public void setNumSerieVD(String numSerieVD)
	{
		this.numSerieVD = numSerieVD;
	}
	
	public Integer getNumAsientosVD()
	{
		return numAsientosVD;
	}
	
	public void setNumAsientosVD(Integer numAsientosVD)
	{
		this.numAsientosVD = numAsientosVD;
	}
	
	public Integer getAnioFabVD()
	{
		return anioFabVD;
	}
	
	public void setAnioFabVD(Integer anioFabVD)
	{
		this.anioFabVD = anioFabVD;
	}
	
	public String getIdCategoriaClaseVD()
	{
		return idCategoriaClaseVD;
	}
	
	public void setIdCategoriaClaseVD(String idCategoriaClaseVD)
	{
		this.idCategoriaClaseVD = idCategoriaClaseVD;
	}
	
	public String getIdUsoVehiculoVD()
	{
		return idUsoVehiculoVD;
	}
	
	public void setIdUsoVehiculoVD(String idUsoVehiculoVD)
	{
		this.idUsoVehiculoVD = idUsoVehiculoVD;
	}
	
	public Long getIdMarcaVehiculoVD()
	{
		return idMarcaVehiculoVD;
	}
	
	public void setIdMarcaVehiculoVD(Long idMarcaVehiculoVD)
	{
		this.idMarcaVehiculoVD = idMarcaVehiculoVD;
	}
	
	public Long getIdModeloVehiculoVD()
	{
		return idModeloVehiculoVD;
	}
	
	public void setIdModeloVehiculoVD(Long idModeloVehiculoVD)
	{
		this.idModeloVehiculoVD = idModeloVehiculoVD;
	}
	
	public String getIdTipoDocVD()
	{
		return idTipoDocVD;
	}
	
	public void setIdTipoDocVD(String idTipoDocVD)
	{
		this.idTipoDocVD = idTipoDocVD;
	}
	
	public String getNumDocumentoVD()
	{
		return numDocumentoVD;
	}
	
	public void setNumDocumentoVD(String numDocumentoVD)
	{
		this.numDocumentoVD = numDocumentoVD;
	}
	
	public Date getFecNacimientoVD()
	{
		return fecNacimientoVD;
	}
	
	public void setFecNacimientoVD(Date fecNacimientoVD)
	{
		this.fecNacimientoVD = fecNacimientoVD;
	}
	
	public String getIdSexoVD()
	{
		return idSexoVD;
	}
	
	public void setIdSexoVD(String idSexoVD)
	{
		this.idSexoVD = idSexoVD;
	}
	
	public String getApePaternoVD()
	{
		return apePaternoVD;
	}
	
	public void setApePaternoVD(String apePaternoVD)
	{
		this.apePaternoVD = apePaternoVD;
	}
	
	public String getApeMaternoVD()
	{
		return apeMaternoVD;
	}
	
	public void setApeMaternoVD(String apeMaternoVD)
	{
		this.apeMaternoVD = apeMaternoVD;
	}
	
	public String getNombresVD()
	{
		return nombresVD;
	}
	
	public void setNombresVD(String nombresVD)
	{
		this.nombresVD = nombresVD;
	}
	
	public Double getPrimaBrutaVD()
	{
		return primaBrutaVD;
	}
	
	public void setPrimaBrutaVD(Double primaBrutaVD)
	{
		this.primaBrutaVD = primaBrutaVD;
	}
	
	public Date getFecIniVigenciaVD()
	{
		return fecIniVigenciaVD;
	}
	
	public void setFecIniVigenciaVD(Date fecIniVigenciaVD)
	{
		this.fecIniVigenciaVD = fecIniVigenciaVD;
	}
	
	public Date getFecFinVigenciaVD()
	{
		return fecFinVigenciaVD;
	}
	
	public void setFecFinVigenciaVD(Date fecFinVigenciaVD)
	{
		this.fecFinVigenciaVD = fecFinVigenciaVD;
	}
	
	public Date getFecVentaVD()
	{
		return fecVentaVD;
	}
	
	public void setFecVentaVD(Date fecVentaVD)
	{
		this.fecVentaVD = fecVentaVD;
	}
	
	public String getCanalVentaVD()
	{
		return canalVentaVD;
	}
	
	public void setCanalVentaVD(String canalVentaVD)
	{
		this.canalVentaVD = canalVentaVD;
	}
	
	public String getNroCertificadoVD()
	{
		return nroCertificadoVD;
	}
	
	public void setNroCertificadoVD(String nroCertificadoVD)
	{
		this.nroCertificadoVD = nroCertificadoVD;
	}
	
	public String getNroCertificadoAntVD()
	{
		return nroCertificadoAntVD;
	}
	
	public void setNroCertificadoAntVD(String nroCertificadoAntVD)
	{
		this.nroCertificadoAntVD = nroCertificadoAntVD;
	}
	
	public String getEstadoVD()
	{
		return estadoVD;
	}
	
	public void setEstadoVD(String estadoVD)
	{
		this.estadoVD = estadoVD;
	}
	
	public String getEstadoImpVD()
	{
		return estadoImpVD;
	}
	
	public void setEstadoImpVD(String estadoImpVD)
	{
		this.estadoImpVD = estadoImpVD;
	}
	
	public String getMotivoVD()
	{
		return motivoVD;
	}
	
	public void setMotivoVD(String motivoVD)
	{
		this.motivoVD = motivoVD;
	}
	
	public String getObservacionVD()
	{
		return observacionVD;
	}
	
	public void setObservacionVD(String observacionVD)
	{
		this.observacionVD = observacionVD;
	}
	
	public String getOpcionApesegVD()
	{
		return opcionApesegVD;
	}
	
	public void setOpcionApesegVD(String opcionApesegVD)
	{
		this.opcionApesegVD = opcionApesegVD;
	}
	
	public Date getFecReporteApesegVD()
	{
		return fecReporteApesegVD;
	}
	
	public void setFecReporteApesegVD(Date fecReporteApesegVD)
	{
		this.fecReporteApesegVD = fecReporteApesegVD;
	}
	
	public String getOpcionSbsVD()
	{
		return opcionSbsVD;
	}
	
	public void setOpcionSbsVD(String opcionSbsVD)
	{
		this.opcionSbsVD = opcionSbsVD;
	}
	
	public Date getFecReporteSbsVD()
	{
		return fecReporteSbsVD;
	}
	
	public void setFecReporteSbsVD(Date fecReporteSbsVD)
	{
		this.fecReporteSbsVD = fecReporteSbsVD;
	}
	
	public Long getPkDetalleTramaDiariaAux()
	{
		return pkDetalleTramaDiariaAux;
	}
	
	public void setPkDetalleTramaDiariaAux(Long pkDetalleTramaDiariaAux)
	{
		this.pkDetalleTramaDiariaAux = pkDetalleTramaDiariaAux;
	}
	
	public DetalleTramaDiaria getDetalleTramaDiariaAux()
	{
		return detalleTramaDiariaAux;
	}
	
	public void setDetalleTramaDiariaAux(DetalleTramaDiaria detalleTramaDiariaAux)
	{
		this.detalleTramaDiariaAux = detalleTramaDiariaAux;
	}
	
	public String getIdMotivoVDR()
	{
		return idMotivoVDR;
	}
	
	public void setIdMotivoVDR(String idMotivoVDR)
	{
		this.idMotivoVDR = idMotivoVDR;
	}
	
	public String getObservacionVDR()
	{
		return observacionVDR;
	}
	
	public void setObservacionVDR(String observacionVDR)
	{
		this.observacionVDR = observacionVDR;
	}
	
	public String getNroCertificadoVDR()
	{
		return nroCertificadoVDR;
	}
	
	public void setNroCertificadoVDR(String nroCertificadoVDR)
	{
		this.nroCertificadoVDR = nroCertificadoVDR;
	}
	
	public String getIdMotivoVDA()
	{
		return idMotivoVDA;
	}
	
	public void setIdMotivoVDA(String idMotivoVDA)
	{
		this.idMotivoVDA = idMotivoVDA;
	}
	
	public String getObservacionVDA()
	{
		return observacionVDA;
	}
	
	public void setObservacionVDA(String observacionVDA)
	{
		this.observacionVDA = observacionVDA;
	}
	 
	public List<ConsultaPostVentaSOAT> getListaPostVentaSOAT()
	{
		return listaPostVentaSOAT;
	}
	
	public void setListaPostVentaSOAT(List<ConsultaPostVentaSOAT> listaPostVentaSOAT)
	{
		this.listaPostVentaSOAT = listaPostVentaSOAT;
	}
	
	public boolean isDisabledBtnExportar()
	{
		return disabledBtnExportar;
	}
	
	public void setDisabledBtnExportar(boolean disabledBtnExportar)
	{
		this.disabledBtnExportar = disabledBtnExportar;
	}
	
	public boolean isDisabledComponentes()
	{
		return disabledComponentes;
	}
	
	public void setDisabledComponentes(boolean disabledComponentes)
	{
		this.disabledComponentes = disabledComponentes;
	}
	
	public boolean isDisabledBtnEditar()
	{
		return disabledBtnEditar;
	}
	
	public void setDisabledBtnEditar(boolean disabledBtnEditar)
	{
		this.disabledBtnEditar = disabledBtnEditar;
	}
	
	public boolean isDisabledBtnActualizar()
	{
		return disabledBtnActualizar;
	}
	
	public void setDisabledBtnActualizar(boolean disabledBtnActualizar)
	{
		this.disabledBtnActualizar = disabledBtnActualizar;
	}
	
	public boolean isDisabledBtnReimprimir()
	{
		return disabledBtnReimprimir;
	}
	
	public void setDisabledBtnReimprimir(boolean disabledBtnReimprimir)
	{
		this.disabledBtnReimprimir = disabledBtnReimprimir;
	}
	
	public boolean isDisabledBtnAnular()
	{
		return disabledBtnAnular;
	}
	
	public void setDisabledBtnAnular(boolean disabledBtnAnular)
	{
		this.disabledBtnAnular = disabledBtnAnular;
	}
	
	public boolean isShownBtnPostReimprimir()
	{
		return shownBtnPostReimprimir;
	}
	
	public void setShownBtnPostReimprimir(boolean shownBtnPostReimprimir)
	{
		this.shownBtnPostReimprimir = shownBtnPostReimprimir;
	}
	
	public boolean isPanelStep1Flag()
	{
		return panelStep1Flag;
	}
	
	public void setPanelStep1Flag(boolean panelStep1Flag)
	{
		this.panelStep1Flag = panelStep1Flag;
	}
	
	public boolean isPanelStep2Flag()
	{
		return panelStep2Flag;
	}
	
	public void setPanelStep2Flag(boolean panelStep2Flag)
	{
		this.panelStep2Flag = panelStep2Flag;
	}
	
	public String getTituloCabecera()
	{
		return tituloCabecera;
	}
	
	public void setTituloCabecera(String tituloCabecera)
	{
		this.tituloCabecera = tituloCabecera;
	}

	public String getNroRegistros() {
		return nroRegistros;
	}

	public void setNroRegistros(String nroRegistros) {
		this.nroRegistros = nroRegistros;
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
	
	
	
	
} //PostVentaSOATController
