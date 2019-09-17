package com.cardif.satelite.reportes.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
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
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.ParametroAutomat;
import com.cardif.satelite.reportes.bean.RepFalabellaBean;
import com.cardif.satelite.reportes.service.RepFalabellaService;
import com.cardif.satelite.util.Utilitarios;

@Controller("repFalabellaController")
@Scope("request")
public class RepFalabellaController extends BaseController
{
	private final Logger logger = Logger.getLogger(RepFalabellaController.class);
	
	@Autowired
	private RepFalabellaService repFalabellaService; 
	
	@Autowired
	private ParametroAutomatService parametroService;
	
	
	private List<SelectItem> comboListaTipoTrama;
	
	private String idTipoTrama;
	private Date fecVentaDesde;
	private Date fecVentaHasta;
	
	private List<RepFalabellaBean> listaReporteFalabella;
	
	
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
			this.comboListaTipoTrama = new ArrayList<SelectItem>();
			
			this.listaReporteFalabella = new ArrayList<RepFalabellaBean>();
			
			this.idTipoTrama = null;
			this.fecVentaDesde = null;
			this.fecVentaHasta = null;
			
			
			//*****************************************************************************
			//************************ CARGAS LOS VALORES INCIALES ************************
			//*****************************************************************************
			if (logger.isInfoEnabled()) {logger.info("Buscando la lista de TIPOS DE TRAMAS.");}
			List<ParametroAutomat> listaTipoTramas = parametroService.buscarPorCodParam(Constantes.COD_PARAM_FLB_TIPO_TRAMA);
			this.comboListaTipoTrama.add(new SelectItem("", "- Seleccione -"));
			for (ParametroAutomat tipoTrama : listaTipoTramas)
			{
				if (logger.isDebugEnabled()) {logger.debug("TIPO_TRAMA ==> COD_VALOR: " + tipoTrama.getCodValor() + " - NOMBRE_VALOR: " + tipoTrama.getNombreValor());}
				this.comboListaTipoTrama.add(new SelectItem(tipoTrama.getCodValor(), tipoTrama.getNombreValor()));
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
	} //inicio
	
	public String procesoGenerarReporte()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			if (logger.isInfoEnabled())
			{
				logger.info("\n######################## PARAMETROS ########################" +
						"\nTIPO_TRAMA: " + this.idTipoTrama + "\tFEC_VENTA_DESDE: " + this.fecVentaDesde + "\tFEC_VENTA_HASTA: " + this.fecVentaHasta);
			}
			
			//Validar Fechas
			if(!Utilitarios.validarFechasInicioFin(fecVentaDesde, fecVentaHasta)){
				throw new SyncconException(ErrorConstants.COD_ERROR_RANGO_FECHAS);
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			long fechaLong = System.currentTimeMillis();
			String fechaString = sdf.format(new Date(fechaLong));
			
			String rutaTemp = null;
			if (this.idTipoTrama.equalsIgnoreCase(Constantes.FLB_TRAMA_DESPACHO))
			{
				if (logger.isInfoEnabled()) {logger.info("Generar reporte de trama de DESPACHO.");}
				this.listaReporteFalabella = repFalabellaService.buscarPolizasDespacho(this.fecVentaDesde, this.fecVentaHasta);
				
				if (null == this.listaReporteFalabella || 0 == this.listaReporteFalabella.size())
				{
					throw new SyncconException(ErrorConstants.COD_ERROR_REP_FALABELLA_ITEMS_VACIO);
				}
				
				rutaTemp = System.getProperty("java.io.tmpdir") + "Reporte_Falabella_Despacho_" + fechaString + "_" + fechaLong + ".txt";
				logger.info(rutaTemp);
				generarReporteDespacho(this.listaReporteFalabella, rutaTemp);
			}
			else if (this.idTipoTrama.equalsIgnoreCase(Constantes.FLB_TRAMA_ANULACION))
			{
				if (logger.isInfoEnabled()) {logger.info("Generar reporte de trama ANULACION.");}
				this.listaReporteFalabella = repFalabellaService.buscarPolizasAnulacion(this.fecVentaDesde, this.fecVentaHasta);
				
				if (null == this.listaReporteFalabella || 0 == this.listaReporteFalabella.size())
				{
					throw new SyncconException(ErrorConstants.COD_ERROR_REP_FALABELLA_ITEMS_VACIO);
				}
				
				rutaTemp = System.getProperty("java.io.tmpdir") + "Reporte_Falabella_Anulacion_" + fechaString + "_" + fechaLong + ".txt";
				generarReporteAnulacion(this.listaReporteFalabella, rutaTemp);
			}
			else
			{
				throw new SyncconException(ErrorConstants.COD_ERROR_REP_FALABELLA_TIPO_TRAMA);
			}
			
			
			if (logger.isDebugEnabled()) {logger.debug("Generando respuesta del reporte");}
			File archivoResp = new File(rutaTemp);
			FileInputStream fis = new FileInputStream(archivoResp);
			
			ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
			
			HttpServletResponse response = (HttpServletResponse) contexto.getResponse();
			byte[] loader = new byte[(int) archivoResp.length()];
			response.addHeader("Content-Disposition", "attachment;filename=" + archivoResp.getName());
			response.setContentType("text/plain");
			
			ServletOutputStream sos = response.getOutputStream();
		    
		    while ((fis.read(loader)) > 0)
		    {
		    	sos.write(loader, 0, loader.length);
		    }
		    
		    fis.close();
		    sos.close();
			
		    FacesContext.getCurrentInstance().responseComplete();
		    
		    //Limpiar Campos
		    idTipoTrama = "";
		    fecVentaDesde = null;
		    fecVentaHasta = null;
		    
			if (logger.isInfoEnabled()) {logger.info("Se genero el reporte correctamente.");}
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
	} //procesoGenerarReporte
	
	private void generarReporteDespacho(List<RepFalabellaBean> lista, String rutaTemp) throws Exception
	{
		FileOutputStream fos = new FileOutputStream(rutaTemp);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		for (RepFalabellaBean item : lista)
		{
			String propuesta = String.format("%-7s", (StringUtils.isNotBlank(item.getPropuesta()) ? item.getPropuesta() : ""));
			String fecha = String.format("%-8s", (null != item.getFecha() ? sdf.format(item.getFecha()) : ""));
			String motivo = String.format("%-3s", (StringUtils.isNotBlank(item.getMotivo()) ? item.getMotivo() : ""));
			String valor = String.format("%-14s", (item.getValor() != null ) ? item.getValor() : "");
			
//			String linea = propuesta.concat(";").concat(fecha).concat(";").concat(motivo).concat(";").concat(valor);
			
			String linea = Utilitarios.verificarLongitudCampo(propuesta, 7).concat(";")
					.concat(Utilitarios.verificarLongitudCampo(fecha, 8)).concat(";")
					.concat(Utilitarios.verificarLongitudCampo(motivo, 3)).concat(";")
					.concat(Utilitarios.verificarLongitudCampo(valor, 14));
			
			if (logger.isDebugEnabled()) {logger.debug("-->Linea: " + linea);}
			
			bw.write(linea);
			bw.newLine();
		}
		bw.close();
	} //generarReporteDespacho
	
	private void generarReporteAnulacion(List<RepFalabellaBean> lista, String rutaTemp) throws Exception
	{
		FileOutputStream fos = new FileOutputStream(rutaTemp);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		for (RepFalabellaBean item : lista)
		{
			String propuesta = String.format("%-7s", (StringUtils.isNotBlank(item.getPropuesta()) ? item.getPropuesta() : ""));
			String fecha = String.format("%-8s", (null != item.getFecha() ? sdf.format(item.getFecha()) : ""));
			String motivo = String.format("%-3s", (StringUtils.isNotBlank(item.getMotivo()) ? item.getMotivo() : ""));
			
//			String linea = propuesta.concat(fecha).concat(motivo);
			String linea = Utilitarios.verificarLongitudCampo(propuesta, 7)  
					.concat(Utilitarios.verificarLongitudCampo(fecha, 8))
					.concat(Utilitarios.verificarLongitudCampo(motivo, 3));
			
			if (logger.isDebugEnabled()) {logger.debug("-->Linea: " + linea);}
			
			bw.write(linea);
			bw.newLine();
		}
		bw.close();
	} //generarReporteAnulacion
	
	
	public List<SelectItem> getComboListaTipoTrama()
	{
		return comboListaTipoTrama;
	}
	
	public void setComboListaTipoTrama(List<SelectItem> comboListaTipoTrama)
	{
		this.comboListaTipoTrama = comboListaTipoTrama;
	}
	
	public String getIdTipoTrama()
	{
		return idTipoTrama;
	}
	
	public void setIdTipoTrama(String idTipoTrama)
	{
		this.idTipoTrama = idTipoTrama;
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
	
	public List<RepFalabellaBean> getListaReporteFalabella()
	{
		return listaReporteFalabella;
	}
	
	public void setListaReporteFalabella(List<RepFalabellaBean> listaReporteFalabella)
	{
		this.listaReporteFalabella = listaReporteFalabella;
	}
	
} //RepFalabellaController
