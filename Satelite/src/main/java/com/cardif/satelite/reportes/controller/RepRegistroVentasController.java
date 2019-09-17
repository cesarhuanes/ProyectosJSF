package com.cardif.satelite.reportes.controller;

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
import com.cardif.satelite.configuracion.service.ParametroAutomatService;
import com.cardif.satelite.configuracion.service.ProductoService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.ParametroAutomat;
import com.cardif.satelite.model.reportes.DetalleReporteRegVenta;
import com.cardif.satelite.moduloimpresion.bean.ConsultaProductoSocio;
import com.cardif.satelite.reportes.bean.RepRegistroVentaBean;
import com.cardif.satelite.reportes.service.RepRegistroVentasService;
import com.cardif.satelite.util.Utilitarios;

import net.sf.jxls.transformer.XLSTransformer;

@Controller("repRegistroVentasController")
@Scope("request")
public class RepRegistroVentasController extends BaseController
{
	private final Logger logger = Logger.getLogger(RepRegistroVentasController.class);
	
	@Autowired
	private RepRegistroVentasService registroVentasService;
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private ParametroAutomatService parametroService;
	
	
	private List<SelectItem> comboListaSocioProducto;
	private List<SelectItem> comboListaSocioProductoNV;
	private List<SelectItem> comboListaTipoDocumentoNV;
	
	private Long idSocioProducto;
	private Long idSocioProductoNV;
	private Date fecEmisionDesde;
	private Date fecEmisionHasta;
	private Date fecEmisionNV;
	private String tipoDocumentoNV;
	private String nroDocumentoNV;
	private String nomRazonSocialNV;
	private Double importeTotalNV;
	
	private String msjValidarTipoDoc = "";
	private String longitudTipoDoc = "0";
	
	private List<RepRegistroVentaBean> listaRegistroVentas;
	
	private boolean disabledBtnExportar;
	
	private boolean disabledNroDocumento;
	
	
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
			this.comboListaSocioProducto = new ArrayList<SelectItem>();
			this.comboListaSocioProductoNV = new ArrayList<SelectItem>();
			this.comboListaTipoDocumentoNV = new ArrayList<SelectItem>();
			
			this.listaRegistroVentas = new ArrayList<RepRegistroVentaBean>();
			
			this.idSocioProducto = null;
			this.fecEmisionDesde = null;
			this.fecEmisionHasta = null;
			
			
			//*****************************************************************************
			//************************ CARGAS LOS VALORES INCIALES ************************
			//*****************************************************************************
			if (logger.isInfoEnabled()) {logger.info("Buscando la lista de SOCIOS.");}
			List<ConsultaProductoSocio> listaSocioProductos = productoService.buscarPorEstado(Constantes.PRODUCTO_ESTADO_ACTIVO);
			this.comboListaSocioProducto.add(new SelectItem("", "- Seleccione -"));
			for (ConsultaProductoSocio socioProducto : listaSocioProductos)
			{
				if (logger.isDebugEnabled()) {logger.debug("SOCIO_PRODUCTO ==> ID: " + socioProducto.getId() + " - NOMBRE_SOCIO: " + socioProducto.getNombreSocio() + " - NOMBRE_PRODUCTO: " + socioProducto.getNombreProducto());}
				this.comboListaSocioProducto.add(new SelectItem(socioProducto.getId(), socioProducto.getNombreSocio() + " - " + socioProducto.getNombreProducto()));
			}
			
			
			/*
			 * Deshabilitar boton Exportar
			 */
			this.disabledBtnExportar = true;
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
	
	public String procesarConsulta()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			if (logger.isInfoEnabled())
			{
				logger.info("\n###################### PARAMETROS DE BUSQUEDA ######################" +
						"\nID_SOCIO_PRODUCTO: " + this.idSocioProducto + "\tFEC_EMISION_DESDE: " + this.fecEmisionDesde + "\tFEC_EMISION_HASTA: " + this.fecEmisionHasta);
			}
			
			//Validar Fechas
			if(!Utilitarios.validarFechasInicioFin(fecEmisionDesde, fecEmisionHasta)){
				throw new SyncconException(ErrorConstants.COD_ERROR_RANGO_FECHAS);
			}
			
			this.listaRegistroVentas = registroVentasService.buscarPolizasRegistroVentas(this.idSocioProducto, this.fecEmisionDesde, this.fecEmisionHasta);
			
			int size = (null != this.listaRegistroVentas ? listaRegistroVentas.size() : 0);
			if (logger.isInfoEnabled()) {logger.info("Nro. de registros encontrados: " + size);}
			
			if(listaRegistroVentas != null && listaRegistroVentas.size()>0){
				for(int i=0; i<listaRegistroVentas.size(); i++){
					if(listaRegistroVentas.get(i).getTipoComprobante().equals(Constantes.REP_REGISTRO_VENTA_TIPO_ALTA)){
						listaRegistroVentas.get(i).setTipoRegistroVenta(Constantes.DESC_REP_REGISTRO_VENTA_TIPO_ALTA);
					}else{
						listaRegistroVentas.get(i).setTipoRegistroVenta(Constantes.DESC_REP_REGISTRO_VENTA_TIPO_BAJA);
					}
					
					if(listaRegistroVentas.get(i).getNombreRazonSocial() != null){
						listaRegistroVentas.get(i).setNombreRazonSocial(listaRegistroVentas.get(i).getNombreRazonSocial().toUpperCase());
					}else{
						listaRegistroVentas.get(i).setNombreRazonSocial("");
					}
					
					if(listaRegistroVentas.get(i).getNroPoliza() == null){
						listaRegistroVentas.get(i).setNroPoliza("-");
					}
					
					if(listaRegistroVentas.get(i).getObservacion() == null){
						listaRegistroVentas.get(i).setObservacion("-");
					}
					
				}
			}
			
			this.disabledBtnExportar = (size > 0 ? false : true);
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
	} //procesarConsulta
	
	public String cargarNuevaVenta()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			this.idSocioProductoNV = null;
			this.fecEmisionNV = null;
			this.tipoDocumentoNV = null;
			this.nroDocumentoNV = null;
			this.nomRazonSocialNV = null;
			this.importeTotalNV = null;
			
			
			if (null == this.comboListaSocioProductoNV || this.comboListaSocioProductoNV.isEmpty())
			{
				this.comboListaSocioProductoNV = new ArrayList<SelectItem>();
				
				List<ConsultaProductoSocio> listaSocioProductos = productoService.buscarPorEstado(Constantes.PRODUCTO_ESTADO_ACTIVO);
				this.comboListaSocioProductoNV.add(new SelectItem("", "- Seleccione -"));
				for (ConsultaProductoSocio socioProducto : listaSocioProductos)
				{
					if (logger.isDebugEnabled()) {logger.debug("SOCIO_PRODUCTO ==> ID: " + socioProducto.getId() + " - NOMBRE_SOCIO: " + socioProducto.getNombreSocio() + " - NOMBRE_PRODUCTO: " + socioProducto.getNombreProducto());}
					this.comboListaSocioProductoNV.add(new SelectItem(socioProducto.getId(), socioProducto.getNombreSocio() + " - " + socioProducto.getNombreProducto()));
				}
				
				if (logger.isInfoEnabled()) {logger.info("Se cargo la lista de SOCIO-PRODUCTO para el Nuevo Registro de Venta.");}
			}
			
			if (null == this.comboListaTipoDocumentoNV || this.comboListaTipoDocumentoNV.isEmpty())
			{
				this.comboListaTipoDocumentoNV = new ArrayList<SelectItem>();
				
				List<ParametroAutomat> listaTipoDocumentos = parametroService.buscarPorCodParam(Constantes.COD_PARAM_TIPO_DOCUMENTO_ID);
				this.comboListaTipoDocumentoNV.add(new SelectItem("", "- Seleccione -"));
				for (ParametroAutomat tipoDocumento : listaTipoDocumentos)
				{
					if (logger.isDebugEnabled()) {logger.debug("TIPO_DOCUMENTO ==> COD_VALOR: " + tipoDocumento.getCodValor() + " - NOMBRE_VALOR: " + tipoDocumento.getNombreValor());}
					this.comboListaTipoDocumentoNV.add(new SelectItem(tipoDocumento.getCodValor(), tipoDocumento.getNombreValor()));
				}
				
				if (logger.isInfoEnabled()) {logger.info("Se cargo la lista de TIPOS DE DOCUMENTOS.");}
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
			logger.error("Exception(" + e.getClass().getName()  + ") -->" + ExceptionUtils.getStackTrace(e));
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = ErrorConstants.MSJ_ERROR;
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //cargarNuevaVenta
	
	public String registrarNuevaVenta()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			if (logger.isInfoEnabled())
			{
				logger.info("\n###################### PARAMETROS DE REGISTRO ######################" +
					"\nID_SOCIO_PRODUCTO: " + this.idSocioProductoNV + "\tFEC_EMISION: " + this.fecEmisionNV + 
					"\nTIPO_DOC: " + this.tipoDocumentoNV + "\tNRO_DOC: " + this.nroDocumentoNV + 
					"\nNOM_RAZON_SOCIAL: " + this.nomRazonSocialNV + "\tIMPORTE_TOTAL: " + this.importeTotalNV);
			}
			
			DetalleReporteRegVenta detReporteRegVenta = registroVentasService.registrarNuevaVenta(this.idSocioProductoNV, this.fecEmisionNV, 
					this.tipoDocumentoNV, this.nroDocumentoNV, this.nomRazonSocialNV, this.importeTotalNV, Constantes.REP_REGISTRO_VENTA_TIPO_ALTA);
			if (logger.isInfoEnabled()) {logger.info("Se registro la NUEVA Venta, pk: " + detReporteRegVenta.getId());}
		
			
			//Validacion de longitud por tipo de documento
//			tipoDocumentoNV
		
			/*
			 * Verificar si es posible actualizar la TABLA VISUAL
			 */
			if (null != this.idSocioProducto && null != this.fecEmisionDesde && null != this.fecEmisionHasta)
			{
				if (logger.isDebugEnabled()) {logger.debug("Llamando al metodo procesarConsulta()");}
				procesarConsulta();
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
			logger.error("Exception(" + e.getClass().getName()  + ") -->" + ExceptionUtils.getStackTrace(e));
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = ErrorConstants.MSJ_ERROR;
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //registrarNuevaVenta
	
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
			
			if (null != listaRegistroVentas && 0 < listaRegistroVentas.size())
			{
				String rutaTemp = System.getProperty("java.io.tmpdir") + "Reporte_RV_" + fechaActual + ".xls";
		        if (logger.isDebugEnabled()) {logger.debug("Ruta Archivo: " + rutaTemp);}
		        
		        FacesContext facesContext = FacesContext.getCurrentInstance();
		        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
		        
		        String rutaTemplate = servletContext.getRealPath(File.separator + "excel" + File.separator + "template_reporte_registro_vta.xls");
		        if (logger.isDebugEnabled()) {logger.debug("Ruta del Template: " + rutaTemplate);}
		        
		        beans.put("exportar", this.listaRegistroVentas);
		        
		        XLSTransformer transformer = new XLSTransformer();
		        transformer.transformXLS(rutaTemplate, beans, rutaTemp);
				
		        File archivoResp = new File(rutaTemp);
		        FileInputStream fis = new FileInputStream(archivoResp);
		        
		        HttpServletResponse response = (HttpServletResponse) contexto.getResponse();
		        byte[] loader = new byte[(int) archivoResp.length()];
		        response.addHeader("Content-Disposition", "attachment;filename=" + "Reporte_RV_" + fechaActual + ".xls");
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
				throw new SyncconException(ErrorConstants.COD_ERROR_REPORTE_RV_FILA_NULA);
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
	
	
//	 TIPO_DOCUMENTO ==> COD_VALOR: 1 - NOMBRE_VALOR: DNI
//	 TIPO_DOCUMENTO ==> COD_VALOR: 2 - NOMBRE_VALOR: CE
//	 TIPO_DOCUMENTO ==> COD_VALOR: 3 - NOMBRE_VALOR: RUC
	
	public String validarSegunTipoDocumento(ValueChangeEvent event){
	
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		this.nroDocumentoNV = null;
		
		try
		{
			if (null != event.getNewValue())
			{
				if (logger.isDebugEnabled()) {logger.debug("Tipo de documento seleccionado: " + event.getNewValue());}
				String tipDoc = event.getNewValue().toString();
				//Limpiar caja de texto tipo documento
				//this.nroDocumentoNV = null;
				
				//***************************************
				if (logger.isDebugEnabled()) {logger.debug("Inicio");}
				
				if (null != event.getNewValue() && StringUtils.isNotBlank(event.getNewValue().toString()))
				{
					if (logger.isDebugEnabled()) {logger.debug("Habilitando el ingreso del Nro. Documento.");}
					this.disabledNroDocumento = false;
				}
				else
				{
					if (logger.isDebugEnabled()) {logger.debug("Deshabilitando el ingreso del Nro. Documento.");}
					this.disabledNroDocumento = true;
				}
				if (logger.isDebugEnabled()) {logger.debug("Fin");}
				//***************************************
				
				if(tipDoc.equals(Constantes.TIP_DOC_DNI)){					
					msjValidarTipoDoc = "* La longitud para DNI es de 8 digitos, y "							
							+ "  el Importe Total en el formato correcto puede ingresar un nùmero entero o un numero con 2 decimales.";
					longitudTipoDoc = "8";
					
				}else if(tipDoc.equals(Constantes.TIP_DOC_CE)){
					
					msjValidarTipoDoc = "* La longitud para el carnet de extranjeria es de 6 digitos."
							+ "  el Importe Total en el formato correcto puede ingresar un nùmero entero o un numero con 2 decimales. ";
					longitudTipoDoc = "6";
				}else if(tipDoc.equals(Constantes.TIP_DOC_RUC)) {
				
					msjValidarTipoDoc = "* La longitud para el RUC es de 11 digitos."
							+ "  el Importe Total en el formato correcto puede ingresar un nùmero entero o un numero con 2 decimales.";
					longitudTipoDoc = "11";
				}else{ // ""
					msjValidarTipoDoc = "";
					longitudTipoDoc = "0";
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
	}
	
	//--valueChangeListener="#{repRegistroVentasController.validarImporteTotal}"
//	public String validarImporteTotal(ValueChangeEvent event){
//		
//		if (logger.isInfoEnabled()) {logger.info("Inicio");}
//		String respuesta = null;
//		
//		try
//		{
//			
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ingresar Importe Total en el formato correcto"
//					+ ", puede ingresar un nùmero entero o un numero con 2 decimales.", null));	
//			
//		}
//		catch (Exception e)
//		{
//			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
//			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
//			
//			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
//			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
//			respuesta = ErrorConstants.MSJ_ERROR;
//		}
//		if (logger.isInfoEnabled()) {logger.info("Fin");}
//		return respuesta;
//	}	
	//
	
	public List<SelectItem> getComboListaSocioProducto() {
		return comboListaSocioProducto;
	}

	public void setComboListaSocioProducto(List<SelectItem> comboListaSocioProducto) {
		this.comboListaSocioProducto = comboListaSocioProducto;
	}

	public Long getIdSocioProducto() {
		return idSocioProducto;
	}

	public void setIdSocioProducto(Long idSocioProducto) {
		this.idSocioProducto = idSocioProducto;
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
	
	public List<RepRegistroVentaBean> getListaRegistroVentas() {
		return listaRegistroVentas;
	}

	public void setListaRegistroVentas(List<RepRegistroVentaBean> listaRegistroVentas) {
		this.listaRegistroVentas = listaRegistroVentas;
	}

	public boolean isDisabledBtnExportar() {
		return disabledBtnExportar;
	}

	public void setDisabledBtnExportar(boolean disabledBtnExportar) {
		this.disabledBtnExportar = disabledBtnExportar;
	}

	public List<SelectItem> getComboListaSocioProductoNV() {
		return comboListaSocioProductoNV;
	}

	public void setComboListaSocioProductoNV(
			List<SelectItem> comboListaSocioProductoNV) {
		this.comboListaSocioProductoNV = comboListaSocioProductoNV;
	}

	public Date getFecEmisionNV() {
		return fecEmisionNV;
	}

	public void setFecEmisionNV(Date fecEmisionNV) {
		this.fecEmisionNV = fecEmisionNV;
	}

	public String getTipoDocumentoNV() {
		return tipoDocumentoNV;
	}

	public void setTipoDocumentoNV(String tipoDocumentoNV) {
		this.tipoDocumentoNV = tipoDocumentoNV;
	}

	public String getNroDocumentoNV() {
		return nroDocumentoNV;
	}

	public void setNroDocumentoNV(String nroDocumentoNV) {
		this.nroDocumentoNV = nroDocumentoNV;
	}

	public Long getIdSocioProductoNV() {
		return idSocioProductoNV;
	}

	public void setIdSocioProductoNV(Long idSocioProductoNV) {
		this.idSocioProductoNV = idSocioProductoNV;
	}

	public List<SelectItem> getComboListaTipoDocumentoNV() {
		return comboListaTipoDocumentoNV;
	}

	public void setComboListaTipoDocumentoNV(
			List<SelectItem> comboListaTipoDocumentoNV) {
		this.comboListaTipoDocumentoNV = comboListaTipoDocumentoNV;
	}

	public String getNomRazonSocialNV() {
		return nomRazonSocialNV;
	}

	public void setNomRazonSocialNV(String nomRazonSocialNV) {
		this.nomRazonSocialNV = nomRazonSocialNV;
	}

	public Double getImporteTotalNV() {
		return importeTotalNV;
	}

	public void setImporteTotalNV(Double importeTotalNV) {
		this.importeTotalNV = importeTotalNV;
	}
	
	public String getMsjValidarTipoDoc() {
		return msjValidarTipoDoc;
	}

	public void setMsjValidarTipoDoc(String msjValidarTipoDoc) {
		this.msjValidarTipoDoc = msjValidarTipoDoc;
	}
	
	public String getLongitudTipoDoc() {
		return longitudTipoDoc;
	}

	public void setLongitudTipoDoc(String longitudTipoDoc) {
		this.longitudTipoDoc = longitudTipoDoc;
	}

	public boolean isDisabledNroDocumento() {
		return disabledNroDocumento;
	}

	public void setDisabledNroDocumento(boolean disabledNroDocumento) {
		this.disabledNroDocumento = disabledNroDocumento;
	}
	
} //RepRegistroVentasController
