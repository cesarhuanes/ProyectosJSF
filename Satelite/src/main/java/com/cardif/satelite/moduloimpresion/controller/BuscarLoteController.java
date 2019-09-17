package com.cardif.satelite.moduloimpresion.controller;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.service.ParametroAutomatService;
import com.cardif.satelite.configuracion.service.ProductoService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.ParametroAutomat;
import com.cardif.satelite.model.moduloimpresion.LoteImpresion;
import com.cardif.satelite.moduloimpresion.bean.ConsultaArmarLote;
import com.cardif.satelite.moduloimpresion.bean.ConsultaConfirmacionImpresion;
import com.cardif.satelite.moduloimpresion.bean.ConsultaDocumentoCurier;
import com.cardif.satelite.moduloimpresion.bean.ConsultaLoteImpresion;
import com.cardif.satelite.moduloimpresion.bean.ConsultaProductoSocio;
import com.cardif.satelite.moduloimpresion.service.BuscarLoteService;
import com.cardif.satelite.util.Base64Util;
import com.cardif.satelite.util.SateliteUtil;

/**
 * Esta clase contiene las funcionalidades para BUSCAR UN LOTE de un determinado SOCIO.
 * Modulo: IMPRESION
 * 
 * @author INFOPARQUEPERU
 * 		   Jose Manuel Lucas Barrera
 */
@Controller("buscarLoteController")
@Scope("request")
public class BuscarLoteController extends BaseController
{
	private static final Logger logger = Logger.getLogger(BuscarLoteController.class);
	
	@Autowired
	private BuscarLoteService buscarLoteService;
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private ParametroAutomatService parametroService;
	
	
	private List<SelectItem> comboListaProductos;
	private List<SelectItem> comboListaEstadosLote;
	
	private Long idProducto;
	private String idEstadoLote;
	private Long numeroLote;
	private Date fecCreacionDesde;
	private Date fecCreacionHasta;
	
	private List<ConsultaLoteImpresion> listaLoteImpresion;
	private int listaLoteImpresionInter;
	private List<ConsultaDocumentoCurier> listaDocumentoCurier;
	
	private List<ConsultaConfirmacionImpresion> listaConfirmacionImpresion;
	private List<ConsultaConfirmacionImpresion> listaConfirmacionImpresionAux;
	
	/*
	 * Navegacion de Paginas
	 */
	private boolean panelStep1Flag;
	private boolean panelStep2Flag;

	private String tituloCabecera;
	
	/* Numero del LOTE */
	private Long nroLoteAux;
	private String nroLoteFormateado;
	
	/*CONTADOR */
	
	private int registrosEncontrados = 0;
	
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
			this.comboListaProductos = new ArrayList<SelectItem>();
			this.comboListaEstadosLote = new ArrayList<SelectItem>();
			
			this.listaLoteImpresion = new ArrayList<ConsultaLoteImpresion>();
			this.listaDocumentoCurier = new ArrayList<ConsultaDocumentoCurier>();
			this.listaConfirmacionImpresion = new ArrayList<ConsultaConfirmacionImpresion>();
			this.listaConfirmacionImpresionAux = new ArrayList<ConsultaConfirmacionImpresion>();
			
			this.idProducto = null;
			this.idEstadoLote = null;
			this.numeroLote = null;
			this.fecCreacionDesde = null;
			this.fecCreacionHasta = null;
			
			
			//*****************************************************************************
			//************************ CARGAS LOS VALORES INCIALES ************************
			//*****************************************************************************
			if (logger.isInfoEnabled()) {logger.info("Buscando la lista de PRODUCTOS.");}
			List<ConsultaProductoSocio> listaProductoSocio = productoService.buscarPorModImpresionNombreCanal(Constantes.PRODUCTO_MOD_IMPRESION_SI, Constantes.CANAL_PROD_NOMBRE_DIGITAL);
			this.comboListaProductos.add(new SelectItem("", "- Seleccione -"));
			for (ConsultaProductoSocio productoSocio : listaProductoSocio)
			{
				if (logger.isDebugEnabled()) {logger.debug("PRODUCTO ==> ID: " + productoSocio.getId() + " - ID_SOCIO: " + productoSocio.getIdSocio() + " - NOMBRE_PRODUCTO: " + productoSocio.getNombreProducto() + " - NOMBRE_SOCIO: " + productoSocio.getNombreSocio());}
				this.comboListaProductos.add(new SelectItem(productoSocio.getId(), productoSocio.getNombreSocio().toUpperCase() ));
				//+ " - " + productoSocio.getNombreProducto().toUpperCase()
			}
			
			
			if (logger.isInfoEnabled()) {logger.info("Buscando la lista de estados del LOTE.");}
			List<ParametroAutomat> listaEstadosLote = parametroService.buscarPorCodParam(Constantes.COD_PARAM_ESTADO_LOTE_IMPRESION);
			this.comboListaEstadosLote.add(new SelectItem("", "- Seleccione -"));
			for (ParametroAutomat estadoLote : listaEstadosLote)
			{
				if (logger.isDebugEnabled()) {logger.debug("ESTADO_LOTE ==> Cod_Valor: " + estadoLote.getCodValor() + " ==> Nom_Valor: " + estadoLote.getNombreValor());}
				this.comboListaEstadosLote.add(new SelectItem(estadoLote.getCodValor(), estadoLote.getNombreValor()));
			}
			
			
			/* Iniciando en la PAGINA 1  */
			this.panelStep1Flag = true;
			this.panelStep2Flag = false;
			
			this.tituloCabecera = null;
			
			/*
			 * Numero del LOTE
			 * Utilizado al momento de cargar la ventana de CONFIRMACION DE IMPRESION
			 */
			this.nroLoteAux = null;
			
			this.nroLoteFormateado = null;
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
	

	
	public String procesarBusqueda()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			if(this.fecCreacionDesde != null && this.fecCreacionHasta!=null){
				if(fecCreacionHasta.before(fecCreacionDesde)){
					throw new SyncconException(ErrorConstants.COD_ERROR_RANGO_FECHAS, FacesMessage.SEVERITY_INFO);
				}
			}
			
			
			
			if (logger.isDebugEnabled())
			{
				logger.debug("################ PARAMETROS DE LA BUSQUEDA ################" +
						"\nPRODUCTO: " + this.idProducto + "\tESTADO_LOTE: " + this.idEstadoLote + "\tNUMERO_LOTE: " + this.numeroLote +
						"\nFEC_CREACION_DESDE: " + this.fecCreacionDesde + "\tFEC_CREACION_HASTA: " + this.fecCreacionHasta);
			}
			
			
			if (logger.isDebugEnabled()) {logger.debug("Se procede a buscar los lotes segun los parametros de entrada.");}
			this.listaLoteImpresion = buscarLoteService.buscarLotesDeImpresion(this.idProducto, this.idEstadoLote, this.numeroLote, this.fecCreacionDesde, this.fecCreacionHasta);
			
			if (logger.isInfoEnabled()) {logger.info("El numero de lotes de impresion encontrados es: [" + (null != this.listaLoteImpresion ? listaLoteImpresion.size() : 0) + "]");}
			 
			 if (null != this.listaLoteImpresion){
				 this.listaLoteImpresionInter=listaLoteImpresion.size();
			 }else{
				 this.listaLoteImpresionInter=0;
			 }
			
			//this.listaLoteImpresionInter= (null != this.listaLoteImpresion ? listaLoteImpresion.size() : 0);
			
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			 
			if (logger.isDebugEnabled())
			{
				if (null != this.listaLoteImpresion && !this.listaLoteImpresion.isEmpty())
				{
					for (ConsultaLoteImpresion loteImp : this.listaLoteImpresion)
					{
						logger.debug("LOTES_IMPRESION 	==> NumLote: " + loteImp.getNumLote() + " Estado: " + loteImp.getEstado() + " FechaCreacion: " + sdf.format(loteImp.getFecCreacion()) + " Usuario: " + loteImp.getUsuario());
					}
					
				this.registrosEncontrados = this.listaLoteImpresion.size();	
				}else{
					this.registrosEncontrados = 0;
				}
			}
			
			if(this.listaLoteImpresion.size() ==0){
				throw new SyncconException(ErrorConstants.COD_ERROR_LISTA_VACIA,FacesMessage.SEVERITY_INFO);
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
	} //procesarBusqueda
	
	public String descargarPDFLoteImpresion()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		
		FacesContext contextParmt = FacesContext.getCurrentInstance();
		String nroLoteAux = contextParmt.getExternalContext().getRequestParameterMap().get("opcionDI");
		log.info("NUMERO DE LOTE AUXILIAR: "+nroLoteAux);
	
		String respuesta = null;
		
		try
		{
			if (null == nroLoteAux)
			{
				throw new SyncconException(ErrorConstants.COD_ERROR_LOTE_IMPRESION_PK_VACIO);
			}
			
			if (logger.isDebugEnabled()) {logger.debug("Iniciando la busqueda del objeto LoteImpresion.");}
			
			LoteImpresion loteImpresionObj = buscarLoteService.buscarLoteImpresionByPk(Long.valueOf(nroLoteAux));
			
			if (logger.isDebugEnabled()) {logger.debug("Se encontro un lote para imprimir: " + loteImpresionObj);}
			
			String pdfCodificadoLote = loteImpresionObj.getPdfCodificadoLote();
			
			if (StringUtils.isNotBlank(pdfCodificadoLote))
			{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				long timeInMillis = Calendar.getInstance().getTimeInMillis();
				String fechaHoy = sdf.format(timeInMillis);
				
				byte[] loteImpresionBytes = Base64Util.getInstance().convertStringToBase64(pdfCodificadoLote);
				if (logger.isDebugEnabled()) {logger.debug("Lote a imprimir en bytes: " + (null != loteImpresionBytes ? loteImpresionBytes.length : 0));}
				
				/*
				 * Guardando el PDF del lote en el DISCO DURO para poder manejarlo
				 */
				
				File fileTemp = Base64Util.getInstance().saveFileToTemp(loteImpresionBytes, "SOAT_Lote_" + fechaHoy + "_" + loteImpresionObj.getNumLote() + "_" + timeInMillis + ".pdf");
				if (logger.isDebugEnabled()) {logger.debug("Se guardo el LOTE en la ruta temporal: " + fileTemp.getAbsolutePath());}
				
				ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
				FileInputStream fis = new FileInputStream(fileTemp);
				
				HttpServletResponse response = (HttpServletResponse) contexto.getResponse();
				byte[] loader = new byte[(int) fileTemp.length()];
				response.reset();
				response.addHeader("Content-Disposition", "attachment;filename=SOAT_Lote_" + fechaHoy + "_" + loteImpresionObj.getNumLote() + "_" + timeInMillis + ".pdf");
				response.setContentType("application/pdf");
				response.setContentLength(loader.length);
				
				ServletOutputStream sos = response.getOutputStream();
				
				while ((fis.read(loader)) > 0)
				{
				
					sos.write(loader, 0, loader.length);				
				
				}
				sos.flush();				
				fis.close();
				sos.close();
				

				
				if (logger.isInfoEnabled()) {logger.info("Se descargo el PDF correctamente.");}
			}
			else
			{
				throw new SyncconException(ErrorConstants.COD_ERROR_DESCARGAR_PDF_VACIO);
			}
			
	
			
		}
		catch (SyncconException e)
		{
			logger.debug(e.getStackTrace());
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

		contextParmt.responseComplete();
		contextParmt.renderResponse();
		
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //descargarPDFLote
	
	public String generarDocumentoCourier()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;

		FacesContext context = FacesContext.getCurrentInstance();
		String numLote = context.getExternalContext().getRequestParameterMap().get("opcionDC");
		String linkDC= context.getExternalContext().getRequestParameterMap().get("opcionCI_L");

		log.info("linkDC: " +linkDC);

		try
		{
			if (null == numLote){
			throw new SyncconException(ErrorConstants.COD_ERROR_LOTE_IMPRESION_PK_VACIO);
			}else if(linkDC.equals("Generar Archivo de Despacho")){
				
				logger.debug("numLote: "+numLote);
			
			this.listaDocumentoCurier = buscarLoteService.buscarPolizasCurierByPkLote(Long.valueOf(numLote));
			if (logger.isInfoEnabled()) {logger.info("Se encontraron [" + (null != this.listaDocumentoCurier ? this.listaDocumentoCurier.size() : 0)+ "] registro para el REPORTE_CURIER del LOTE: " + numLote);}
			
			
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			ServletContext servletContext = (ServletContext) externalContext.getContext();
			
			String rutaTemplate = servletContext.getRealPath(File.separator + "excel" + File.separator + "template_exportar_reporte_despacho_curier.xls");
			if (logger.isDebugEnabled()) {logger.debug("Ruta del TEMPLATE: " + rutaTemplate);}
			
			Map<String, Object> beans = new HashMap<String, Object>();
			beans.put("exportar", this.listaDocumentoCurier);
			
			/*
			 * Generar ruta temporal en donde se guardara el excel generado
			 */
			String rutaTemp = System.getProperty("java.io.tmpdir") + "Reporte_Despacho_CURIER_" + System.currentTimeMillis() + ".xls";
			if (logger.isDebugEnabled()) {logger.debug("Ruta temporal del reporte: " + rutaTemp);}
			
			/*
			 * Colocar la informacion del MAP al archivo temporal (Generando el
			 * reporte)
			 */
			XLSTransformer transformer = new XLSTransformer();
			transformer.transformXLS(rutaTemplate, beans, rutaTemp);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
			
			File archivoResp = new File(rutaTemp);
			FileInputStream fis = new FileInputStream(archivoResp);
			ServletOutputStream sos = response.getOutputStream();

			try {				
			response.addHeader("Content-Disposition", "attachment; filename=Reporte_Despacho_CURIER_" + numLote + "_" + sdf.format(new Date(System.currentTimeMillis())) + ".xls");
			response.setContentType("application/vnd.ms-excel");			
			byte[] loader = new byte[(int) archivoResp.length()];			
			
			
			while ((fis.read(loader)) > 0)
			{
				sos.write(loader, 0, loader.length);
			}
		
			}finally{
				fis.close();
				sos.close();
			}
			FacesContext.getCurrentInstance().responseComplete();
			if (logger.isDebugEnabled()) {logger.debug("Se genero el reporte de DESPACHO CURIER correctamente.");}
		}else if(linkDC.equals("Confirmar Impresión")){
			cargarConfirmarImpresion();
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
	} //generarDocumentoCourier
	
	@SuppressWarnings("unchecked")
	public String cargarConfirmarImpresion()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		FacesContext context = FacesContext.getCurrentInstance();
		String numLote = context.getExternalContext().getRequestParameterMap().get("opcionDC");
		
		log.info("NUMERO DE LOTE: "+numLote);
		
		try
		{
			if (logger.isInfoEnabled()) {logger.info("Buscando la lista de confirmacion del LOTE DE IMPRESION: " + numLote);}
			this.listaConfirmacionImpresion = buscarLoteService.buscarPolizasParaConfirmarImpresion(Long.valueOf(numLote));
			
			/*
			 * Guardar la lista de la confirmacion de impresion en una variable auxiliar
			 * para luego validar, la anterior informacion con la nueva
			 */
			this.listaConfirmacionImpresionAux = (ArrayList<ConsultaConfirmacionImpresion>) SateliteUtil.cloneObject(this.listaConfirmacionImpresion);
			
			this.panelStep2Flag = true;
			this.panelStep1Flag = false;
			
			this.tituloCabecera = Constantes.MOD_IMP_CONFIRMACION_IMPRESION;
			
			/* Guardando el numero de lote para visualizacion */
			setNroLoteAux(Long.valueOf(numLote));
			
			this.nroLoteFormateado = "#" + SateliteUtil.formatNumber(Long.valueOf(numLote), Constantes.NUM_LOTE_FORMAT);
			
			//= numLote;
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
	    	logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
	    	FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
	    	FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	    	respuesta = ErrorConstants.MSJ_ERROR;
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //cargarConfirmarImpresion
	
	public String guardarLoteImpresion()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		for (int i = 0; i < listaConfirmacionImpresion.size(); i++) {
			try {
				
				if (listaConfirmacionImpresion.get(i).getImpresoCorrectamente().contains(Constantes.MOD_IMP_IMPRESO_CORRECTAMENTE_REEMPLAZAR)) {
					validarNroCertificadoConfImpresion(listaConfirmacionImpresion.get(i));
					
				}else{
					this.listaConfirmacionImpresion.get(i).setValidatedCSSStyle(Constantes.MOD_IMP_CERT_VALIDAR_BACKGROUND_COLOR_OK);
				}					
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
		try
		{
			if (!validarExistanNroCertificados(this.listaConfirmacionImpresion))
			{
				throw new SyncconException(ErrorConstants.COD_ERROR_ARMAR_LOTE_NRO_CERTIFICADO_VACIO);				
			}
			if (logger.isDebugEnabled()) {logger.debug("Se valido que existan Nro. de certificados en las polizas.");}
			
			
			if (!validarDuplicidadCertificados(this.listaConfirmacionImpresion))
			{
				throw new SyncconException(ErrorConstants.COD_ERROR_ARMAR_LOTE_DUPLICIDAD_CERTIFICADOS);
			}
			if (logger.isDebugEnabled()) {logger.debug("Se valido la duplicidad de los certificados correctamente.");}
			
			
			for (ConsultaConfirmacionImpresion cConfirmacionImpresion : this.listaConfirmacionImpresion)
			{
				ConsultaConfirmacionImpresion cConfirmacionImpresionAux = getConfirmacionImpresionAuxObj(this.listaConfirmacionImpresionAux, cConfirmacionImpresion.getId());
				if (logger.isDebugEnabled())
				{
					logger.debug("conf_imp_ID: " + cConfirmacionImpresion.getId() + " conf_imp_NRO_POLIZA: " + cConfirmacionImpresion.getNroPolizaPe() + " conf_imp_NRO_CERT: " + cConfirmacionImpresion.getNroCertificado() + 
							"\nconf_imp_AUX_ID: " + cConfirmacionImpresionAux.getId() + " conf_imp_AUX_NRO_POLIZA: " + cConfirmacionImpresionAux.getNroPolizaPe() + " conf_imp_AUX_CER: " + cConfirmacionImpresionAux.getNroCertificado());					
				}
				
				boolean isOK = buscarLoteService.confirmarImpresionPorPoliza(cConfirmacionImpresion, cConfirmacionImpresionAux);
				if (logger.isInfoEnabled()) {logger.info("La poliza(" + cConfirmacionImpresion.getId() + " - " + cConfirmacionImpresion.getNroPolizaPe() + ") fue confirmada correctamente? " + isOK);}
			}
			
			
			boolean isOK = buscarLoteService.actualizarEstadoLoteImpresion(this.listaConfirmacionImpresion.get(0).getNumLote(), Constantes.MOD_IMPRESION_ESTADO_IMPRESO);
			if (isOK)
			{
				if (logger.isInfoEnabled()) {logger.info("Se actualizo el estado del LOTE DE IMPRESION a: " + Constantes.MOD_IMPRESION_ESTADO_IMPRESO + " correctamente.");}
			}
			else
			{
				if (logger.isInfoEnabled()) {logger.info("No se actualizo correctamente el estado del LOTE DE IMPRESION.");}
			}
			
			if (logger.isDebugEnabled()) {logger.debug("Se guardo y confirmo el LOTE DE IMPRESION correctamente.");}
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
	} //guardarLoteImpresion
	
	private boolean validarExistanNroCertificados(List<ConsultaConfirmacionImpresion> listaConfImpresion)
	{
		boolean flag = true;
		
		for (ConsultaConfirmacionImpresion confImpresion : listaConfImpresion)
		{
			String nroCertificado = confImpresion.getNroCertificado();
			
			if (StringUtils.isBlank(nroCertificado) )
			{
				flag = false; break;
			}
		}
		return flag;
	} //validarExistanNroCertificadoStep3
	
	public boolean validarDuplicidadCertificados(List<ConsultaConfirmacionImpresion> listaConfImpresion)
	{
		boolean flag = true;
		
		end :
			for (int i=0; i<listaConfImpresion.size(); i++)
			{
				String nroCertificado = listaConfImpresion.get(i).getNroCertificado();
				
				for (int j=0; j<listaConfImpresion.size(); j++)
				{
					/*
					 * Si cumplen con la igualdad, hay duplicidad
					 */
					if (listaConfImpresion.get(j).getNroCertificado().equalsIgnoreCase(nroCertificado) && (i != j))
					{
						flag = false; 
						break end;
					}
				}
			}
		return flag;
	} //validarDuplicidadCertificados
	
	private ConsultaConfirmacionImpresion getConfirmacionImpresionAuxObj(List<ConsultaConfirmacionImpresion> listaConfirmacionImp, Long pk)
	{
		ConsultaConfirmacionImpresion response = null;
		
		for (ConsultaConfirmacionImpresion cConfirmacionImpresion : listaConfirmacionImp)
		{
			if (cConfirmacionImpresion.getId().compareTo(pk) == 0)
			{
				response = cConfirmacionImpresion; break;
			}
		}
		return response;
	} //getConfirmacionImpresionAuxObj
	
	public String validarNroCertificadoConfImpresion(ConsultaConfirmacionImpresion bean)
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			ConsultaArmarLote consultaArmarLoteBean = new ConsultaArmarLote();
			consultaArmarLoteBean.setId(bean.getId());
			consultaArmarLoteBean.setNumCertificado(bean.getNroCertificado());
			consultaArmarLoteBean.setIdProducto(bean.getIdProducto());
			
			List<ConsultaArmarLote> lista = new ArrayList<ConsultaArmarLote>();
			lista.add(consultaArmarLoteBean);
			
			
			if (!validarFormatoCertificados(lista))
			{
				throw new SyncconException(ErrorConstants.COD_ERROR_ARMAR_LOTE_FORMATO_CERTIFICADOS);
			}
			if (logger.isDebugEnabled()) {logger.debug("Se valido el formato de los certificados correctamente.");}
			
			Map<Long, Boolean> map = buscarLoteService.validarNumeroDocValorados(lista);
			boolean valido = false;
			if (null != map)
			{
				valido = map.get(bean.getId());
				
				if (valido)
				{
					for (int i=0; i<this.listaConfirmacionImpresion.size(); i++)
					{
						if (null != this.listaConfirmacionImpresion.get(i).getId() && null != bean.getId() && this.listaConfirmacionImpresion.get(i).getId().equals(bean.getId()))
						{
							this.listaConfirmacionImpresion.get(i).setValidatedCSSStyle(Constantes.MOD_IMP_CERT_VALIDAR_BACKGROUND_COLOR_OK);
							break;
						}
					}
					/*FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se validó el Nro. de Certificado correctamente!", null);
					FacesContext.getCurrentInstance().addMessage(null, facesMsg);*/
				}
				else
				{
					if (logger.isDebugEnabled()) {logger.debug("La validacion del Nro. Certificado retorno: FALSE");}
					bean.setNroCertificado(null);
					
					for (int i=0; i<this.listaConfirmacionImpresion.size(); i++)
					{
						if (null != this.listaConfirmacionImpresion.get(i).getId() && null != bean.getId() && this.listaConfirmacionImpresion.get(i).getId().equals(bean.getId()))
						{
							this.listaConfirmacionImpresion.get(i).setNroCertificado(null); 
							this.listaConfirmacionImpresion.get(i).setValidatedCSSStyle(Constantes.MOD_IMP_CERT_VALIDAR_BACKGROUND_COLOR_FAIED);

							break;
						}
					}
					
					throw new SyncconException(ErrorConstants.COD_ERROR_ARMAR_LOTE_NRO_CERT_NO_DISPONIBLE);
				}
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
	    	logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
	    	FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
	    	FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	    	respuesta = ErrorConstants.MSJ_ERROR;
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //validarNroCertificadoConfirmarImpresion
	
	public void habilitarTxtNroCertificado(ValueChangeEvent event)
	{
		if (logger.isDebugEnabled()) {logger.debug("Inicio");}
		
		String valueEstado = event.getNewValue().toString();
		String id = event.getNewValue().toString().replace(Constantes.MOD_IMP_IMPRESO_CORRECTAMENTE_SI, "").replace(Constantes.MOD_IMP_IMPRESO_CORRECTAMENTE_REEMPLAZAR, "").replace(" ", "").trim();

		for (int i=0; i<listaConfirmacionImpresion.size(); i++)
		{
			if (listaConfirmacionImpresion.get(i).getId().toString().equals(id.trim()))
			{
				if(valueEstado.contains("SI")){					
					if (logger.isDebugEnabled()) {logger.debug("Opcion IMPRESO CORRECTAMENTE - SI");}					
					listaConfirmacionImpresion.get(i).setImpresoCorrectamente(Constantes.MOD_IMP_IMPRESO_CORRECTAMENTE_SI +" "+ this.listaConfirmacionImpresion.get(i).getId());
					listaConfirmacionImpresion.get(i).setDisabledText(true);
					listaConfirmacionImpresion.get(i).setNroCertificado(listaConfirmacionImpresion.get(i).getTmpNroCertificado());
					
				}else if(valueEstado.contains("REEMPLAZAR")){		
					if (logger.isDebugEnabled()) {logger.debug("Opcion IMPRESO CORRECTAMENTE - REEMPLAZAR");}					

					listaConfirmacionImpresion.get(i).setNroCertificado(null);					
					listaConfirmacionImpresion.get(i).setImpresoCorrectamente(Constantes.MOD_IMP_IMPRESO_CORRECTAMENTE_REEMPLAZAR +" "+ this.listaConfirmacionImpresion.get(i).getId());
					listaConfirmacionImpresion.get(i).setDisabledText(false);					
				}	
				break;
			}
		}
		if (logger.isDebugEnabled()) {logger.debug("Fin");}
	} //habilitarTxtNroCertificado
	
	private boolean validarFormatoCertificados(List<ConsultaArmarLote> listaVCertificadoLote)
	{
		boolean flag = true;
		
		for (ConsultaArmarLote cArmarLote : listaVCertificadoLote)
		{
			String numCertificado = cArmarLote.getNumCertificado();
			
			if (StringUtils.isBlank(numCertificado))
			{
				flag = false; break;
			}
			
			if (12 > numCertificado.length())
			{
				flag = false; break;
			}
		}
		return flag;
	} //validarFormatoCertificados
	
	public String reiniciarPantallaInicial()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			this.listaLoteImpresion = new ArrayList<ConsultaLoteImpresion>();
			this.listaDocumentoCurier = new ArrayList<ConsultaDocumentoCurier>();
			
			this.idProducto = null;
			this.idEstadoLote = null;
			this.numeroLote = null;
			this.fecCreacionDesde = null;
			this.fecCreacionHasta = null;
			
			this.nroLoteFormateado = null;
			
			/*
			 * Iniciando en la PAGINA 1
			 */
			this.panelStep1Flag = true;
			this.panelStep2Flag = false;
			
			this.tituloCabecera = null;
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
	} //reiniciarPantallaInicial
	
	public void Volver(){
		this.panelStep2Flag = false;
		this.panelStep1Flag = true;
		
		
	}
	
	//=========================================================================
	//=========================== GET / SET METHODS ===========================
	//=========================================================================	
	
	public List<SelectItem> getComboListaProductos()
	{
		return comboListaProductos;
	}
	
	public int getRegistrosEncontrados() {
		return registrosEncontrados;
	}

	public void setRegistrosEncontrados(int registrosEncontrados) {
		this.registrosEncontrados = registrosEncontrados;
	}

	public void setComboListaProductos(List<SelectItem> comboListaProductos)
	{
		this.comboListaProductos = comboListaProductos;
	}
	
	public List<SelectItem> getComboListaEstadosLote()
	{
		return comboListaEstadosLote;
	}
	
	public void setComboListaEstadosLote(List<SelectItem> comboListaEstadosLote)
	{
		this.comboListaEstadosLote = comboListaEstadosLote;
	}
	
	public Long getIdProducto()
	{
		return idProducto;
	}
	
	public void setIdProducto(Long idProducto)
	{
		this.idProducto = idProducto;
	}
	
	public String getIdEstadoLote()
	{
		return idEstadoLote;
	}
	
	public void setIdEstadoLote(String idEstadoLote)
	{
		this.idEstadoLote = idEstadoLote;
	}
	
	public Long getNumeroLote()
	{
		return numeroLote;
	}
	
	public void setNumeroLote(Long numeroLote)
	{
		this.numeroLote = numeroLote;
	}
	
	public Date getFecCreacionDesde()
	{
		return fecCreacionDesde;
	}
	
	public void setFecCreacionDesde(Date fecCreacionDesde)
	{
		this.fecCreacionDesde = fecCreacionDesde;
	}
	
	public Date getFecCreacionHasta()
	{
		return fecCreacionHasta;
	}
	
	public void setFecCreacionHasta(Date fecCreacionHasta)
	{
		this.fecCreacionHasta = fecCreacionHasta;
	}
	
	public List<ConsultaLoteImpresion> getListaLoteImpresion()
	{
		return listaLoteImpresion;
	}
	
	public void setListaLoteImpresion(List<ConsultaLoteImpresion> listaLoteImpresion)
	{
		this.listaLoteImpresion = listaLoteImpresion;
	}
	
	public List<ConsultaDocumentoCurier> getListaDocumentoCurier()
	{
		return listaDocumentoCurier;
	}
	
	public void setListaDocumentoCurier(List<ConsultaDocumentoCurier> listaDocumentoCurier)
	{
		this.listaDocumentoCurier = listaDocumentoCurier;
	}
	
	public List<ConsultaConfirmacionImpresion> getListaConfirmacionImpresion()
	{
		return listaConfirmacionImpresion;
	}
	
	public void setListaConfirmacionImpresion(List<ConsultaConfirmacionImpresion> listaConfirmacionImpresion)
	{
		this.listaConfirmacionImpresion = listaConfirmacionImpresion;
	}
	
	public List<ConsultaConfirmacionImpresion> getListaConfirmacionImpresionAux()
	{
		return listaConfirmacionImpresionAux;
	}
	
	public void setListaConfirmacionImpresionAux(List<ConsultaConfirmacionImpresion> listaConfirmacionImpresionAux)
	{
		this.listaConfirmacionImpresionAux = listaConfirmacionImpresionAux;
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
	
	public Long getNroLoteAux()
	{
		return nroLoteAux;
	}
	
	public void setNroLoteAux(Long nroLoteAux)
	{
		this.nroLoteAux = nroLoteAux;
	}
	
	public String getNroLoteFormateado()
	{
		return nroLoteFormateado;
	}
	
	public void setNroLoteFormateado(String nroLoteFormateado)
	{
		this.nroLoteFormateado = nroLoteFormateado;
	}

	public int getListaLoteImpresionInter() {
		return listaLoteImpresionInter;
	}

	public void setListaLoteImpresionInter(int listaLoteImpresionInter) {
		this.listaLoteImpresionInter = listaLoteImpresionInter;
	}

	
	
	
	
	
} //BuscarLoteController
