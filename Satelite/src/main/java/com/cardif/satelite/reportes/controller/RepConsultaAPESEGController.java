package com.cardif.satelite.reportes.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.cardif.satelite.model.reportes.DetalleReporteAPESEG;
import com.cardif.satelite.reportes.bean.RepConsultaAPESEGBean;
import com.cardif.satelite.reportes.service.RepConsultaAPESEGService;
import com.cardif.satelite.util.Utilitarios;

@Scope("request")
@Controller("repConsultaAPESEGController")
public class RepConsultaAPESEGController extends BaseController
{
	private final Logger logger = Logger.getLogger(RepConsultaAPESEGController.class);
	
	@Autowired
	private RepConsultaAPESEGService repConsultaAPESEGService;
	
	@Autowired
	private ParametroAutomatService parametroService;
	
	private Date fecRegistroDesde;
	private Date fecRegistroHasta;
	
	private List<RepConsultaAPESEGBean> listaConsultaAPESEG;
	
	private boolean disabledBtnExportar;
	private boolean disabledBtnConfirmar;
	
	
	@PostConstruct
	@Override
	public String inicio()
	{
		if (logger.isDebugEnabled()) {logger.debug("+inicio()");}
		String respuesta = null;
		
		if (!tieneAcceso())
		{
			if (logger.isDebugEnabled()) {logger.debug("No cuenta con los accesos necesarios.");}
			return "accesoDenegado";
		}
		
		this.listaConsultaAPESEG = new ArrayList<RepConsultaAPESEGBean>();
		
		this.fecRegistroDesde = null;
		this.fecRegistroHasta = null;
		
		
		/*
		 * Deshabilitar botones
		 * - Exportar
		 * - Confirmar APESEG
		 */
		this.disabledBtnExportar = true;
		this.disabledBtnConfirmar = true;
		if (logger.isDebugEnabled()) {logger.debug("-inicio()");}
		return respuesta;
	} //inicio
	
	public String procesarConsulta()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("################ PARAMETROS DE LA BUSQUEDA ################" + 
						"\nFECHA_REGISTRO_Desde: " + this.fecRegistroDesde + "\tFECHA_REGISTRO_Hasta: " + this.fecRegistroHasta);
			}
			
			//Validar Fechas
			if(!Utilitarios.validarFechasInicioFin(fecRegistroDesde, fecRegistroHasta)){
				throw new SyncconException(ErrorConstants.COD_ERROR_RANGO_FECHAS);
			}
			
			this.listaConsultaAPESEG = repConsultaAPESEGService.buscarPolizasParaAPESEG(this.fecRegistroDesde, this.fecRegistroHasta);
			boolean disabled = (null != this.listaConsultaAPESEG && 0 < this.listaConsultaAPESEG.size()) ? false : true;
			
			this.disabledBtnExportar = disabled;
			this.disabledBtnConfirmar = disabled;
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
			if (null == this.listaConsultaAPESEG || 0 == this.listaConsultaAPESEG.size())
			{
				throw new SyncconException(ErrorConstants.COD_ERROR_REP_APESEG_BUSQUEDA_PENDIENTE, FacesMessage.SEVERITY_INFO);
			}
			
			
			ParametroAutomat codCiaParam = parametroService.obtener(Constantes.COD_PARAM_CODIGO_CIA_SEGUROS, Constantes.COD_PARAM_CODIGO_CIA_SEGUROS_VALOR);
			ParametroAutomat apesegCountParam = parametroService.obtener(Constantes.COD_PARAM_APESEG_COUNT, Constantes.COD_PARAM_APESEG_COUNT_VALOR);
			//ParametroAutomat rutaArchivoParam = parametroService.obtener(Constantes.COD_PARAM_RUTA_APESEG, Constantes.COD_PARAM_RUTA_APESEG_VALOR);
			
			String correlativoApeseg = obtenerCorrelativoAPESEG(apesegCountParam.getNombreValor());
			String correlativo = (correlativoApeseg.split("-")[1]).trim();
			if (logger.isDebugEnabled()) {logger.debug("Se genero el correlativo para APESEG: " + correlativo);}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
			String nombreArchivo = Integer.valueOf(codCiaParam.getNombreValor()) + "P" + sdf.format(Calendar.getInstance().getTime()) + correlativo;
			if (logger.isDebugEnabled()) {logger.debug("El nombre del archivo es: " + nombreArchivo);}
			
			
			String rutaTemp = System.getProperty("java.io.tmpdir") +nombreArchivo + ".txt";
			//rutaArchivoParam.getNombreValor() + File.separator + nombreArchivo + ".txt";
			
	        if (logger.isDebugEnabled()) {logger.debug("Ruta Archivo: " + rutaTemp);}
	        
			
	        FileOutputStream fos = new FileOutputStream(rutaTemp);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
			
			/*
			 * Estandar para el formato de FECHA segun documentacion de APESEG.
			 */
			sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			for (RepConsultaAPESEGBean repApesegBean : this.listaConsultaAPESEG)
			{
				String codCia = String.format("%-3s", StringUtils.isNotBlank(repApesegBean.getCodCia()) ? repApesegBean.getCodCia() : "");
				String polCer = String.format("%-30s", StringUtils.isNotBlank(repApesegBean.getPolCer()) ? repApesegBean.getPolCer() : "");
				String tipTra = String.format("%-2s", StringUtils.isNotBlank(repApesegBean.getTipTra()) ? repApesegBean.getTipTra() : "");
				String fchCeF = String.format("%-10s", null != repApesegBean.getFchCeF() ? sdf.format(repApesegBean.getFchCeF()) : "");
				String fchCeI = String.format("%-10s", null != repApesegBean.getFchCeI() ? sdf.format(repApesegBean.getFchCeI()) : "");
				String tipPer = String.format("%-1s", StringUtils.isNotBlank(repApesegBean.getTipPer()) ? repApesegBean.getTipPer() : "");
				String nomCon = String.format("%-60s", StringUtils.isNotBlank(repApesegBean.getNomCon()) ? repApesegBean.getNomCon() : "");
				String tipDoc = String.format("%-2s", StringUtils.isNotBlank(repApesegBean.getTipDoc()) ? repApesegBean.getTipDoc() : "");
				String numDoc = String.format("%-16s", StringUtils.isNotBlank(repApesegBean.getNumDoc()) ? repApesegBean.getNumDoc() : "");
				String placa = String.format("%-12s", StringUtils.isNotBlank(repApesegBean.getPlaca()) ? repApesegBean.getPlaca() : "");
				String uso = String.format("%-2s", StringUtils.isNotBlank(repApesegBean.getUso()) ? repApesegBean.getUso() : "");
				String clase = String.format("%-2s", StringUtils.isNotBlank(repApesegBean.getClase()) ? repApesegBean.getClase() : "");
				String pais = String.format("%-4s", StringUtils.isNotBlank(repApesegBean.getPais()) ? repApesegBean.getPais() : "");
				String tipAnu = String.format("%-1s", StringUtils.isNotBlank(repApesegBean.getTipAnu()) ? repApesegBean.getTipAnu() : "");
				String fecPro = String.format("%-10s", null != repApesegBean.getFecPro() ? sdf.format(repApesegBean.getFecPro()) : "");
				String fecAct = String.format("%-10s", null != repApesegBean.getFecAct() ? sdf.format(repApesegBean.getFecAct()) : "");
				String ubigeo = String.format("%-6s", StringUtils.isNotBlank(repApesegBean.getUbigeo()) ? repApesegBean.getUbigeo() : "");
				String nroMot = String.format("%-20s", StringUtils.isNotBlank(repApesegBean.getNroMot()) ? repApesegBean.getNroMot() : "");
				String nroCha = String.format("%-20s", StringUtils.isNotBlank(repApesegBean.getNroCha()) ? repApesegBean.getNroCha() : "");
				
				
				String linea = Utilitarios.verificarLongitudCampo(codCia, 3).
						concat(Utilitarios.verificarLongitudCampo(polCer, 30)).
						concat(Utilitarios.verificarLongitudCampo(tipTra,2)).
						concat(Utilitarios.verificarLongitudCampo(fchCeF,10)).
						concat(Utilitarios.verificarLongitudCampo(fchCeI,10)).
						concat(Utilitarios.verificarLongitudCampo(tipPer,1)).
						concat(Utilitarios.verificarLongitudCampo(nomCon,60)).
						concat(Utilitarios.verificarLongitudCampo(tipDoc,2)).
						concat(Utilitarios.verificarLongitudCampo(numDoc,16)).
						concat(Utilitarios.verificarLongitudCampo(placa,12)).
						concat(Utilitarios.verificarLongitudCampo(uso,2)).
						concat(Utilitarios.verificarLongitudCampo(clase,2)).
						concat(Utilitarios.verificarLongitudCampo(pais,4)).
						concat(Utilitarios.verificarLongitudCampo(tipAnu,1)).
						concat(Utilitarios.verificarLongitudCampo(fecPro,10)).
						concat(Utilitarios.verificarLongitudCampo(fecAct,10)).
						concat(Utilitarios.verificarLongitudCampo(ubigeo,6)).
						concat(Utilitarios.verificarLongitudCampo(nroMot,20)).
						concat(Utilitarios.verificarLongitudCampo(nroCha,20));
				if (logger.isDebugEnabled()) {logger.debug("-->Linea: " + linea);}
				
				bw.write(linea);
				bw.newLine();
			}
			bw.close();

			
			File archivoResp = new File(rutaTemp);
			FileInputStream fis = new FileInputStream(archivoResp);
			
			ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
			
			HttpServletResponse response = (HttpServletResponse) contexto.getResponse();
			byte[] loader = new byte[(int) archivoResp.length()];
			response.addHeader("Content-Disposition", "attachment;filename=" + nombreArchivo + ".txt");
			response.setContentType("text/plain");
			
			ServletOutputStream sos = response.getOutputStream();
		    
		    while ((fis.read(loader)) > 0)
		    {
		    	sos.write(loader, 0, loader.length);
		    }
		    
		    fis.close();
		    sos.close();
		    
		    
		    if (logger.isDebugEnabled()) {logger.debug("Actualizando el CORRELATIVO en la DB.");}
		    apesegCountParam.setNombreValor(correlativoApeseg);
		    parametroService.actualizar(apesegCountParam);
		    
		    FacesContext.getCurrentInstance().responseComplete();
		    this.disabledBtnConfirmar = false;
		    if (logger.isDebugEnabled()) {logger.debug("Exportacion culminada.");}
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
	} //exportar
	
	public String confirmarApeseg()
	{
		if (logger.isDebugEnabled()) {logger.debug("Inicio");}
		String respuesta = null;
		
		try
		{
			if (null == this.listaConsultaAPESEG || 0 == this.listaConsultaAPESEG.size())
			{
				throw new SyncconException(ErrorConstants.COD_ERROR_REP_APESEG_BUSQUEDA_PENDIENTE, FacesMessage.SEVERITY_INFO);
			}
			
			
			for (RepConsultaAPESEGBean apesegBean : this.listaConsultaAPESEG)
			{
				if (logger.isDebugEnabled()) {logger.debug("Actualizando el DetalleReporteApeseg [" + apesegBean.getIdReporteApeseg() + "] con relacion a DetalleTramaDiaria [" + apesegBean.getId() + "].");}
				DetalleReporteAPESEG reporteAPESEG = repConsultaAPESEGService.actualizarReportadoAPESEG(apesegBean.getIdReporteApeseg(), Constantes.APESEG_REPORTADO_SI);
				
				if (logger.isInfoEnabled()) {logger.info("Se actualizo el DetalleReporteApeseg [" + reporteAPESEG.getId() + "] con relaicon a DetalleTramaDiaria [" + reporteAPESEG.getIdDetalleTramaDiaria() + "].");}
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
		if (logger.isDebugEnabled()) {logger.debug("Fin");}
		return respuesta;
	} //confirmarApeseg
	
	private String obtenerCorrelativoAPESEG(String valorParametro) throws SyncconException, Exception
	{
		String[] params = valorParametro.split("-");
		
		String fechaString = params[0].trim();
		Integer correlativo = Integer.valueOf(params[1].trim());
		if (logger.isDebugEnabled()) {logger.debug("fechaString: " + fechaString + " correlativo: " + correlativo);}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		Calendar fecParamCal = Calendar.getInstance();
		fecParamCal.setTime(sdf.parse(fechaString));
		
		Calendar fecActualCal = Calendar.getInstance();
		
		//nueva conparacion de fechas
		Date dfecParamCal = fecParamCal.getTime();
		Date dfecActualCal = fecActualCal.getTime();
		
		
//		if (fecActualCal.get(Calendar.YEAR) >= fecParamCal.get(Calendar.YEAR) && fecActualCal.get(Calendar.DAY_OF_YEAR) > fecParamCal.get(Calendar.DAY_OF_YEAR))
		if(fechaActualMayorFechaGuardada(dfecActualCal, dfecParamCal))
		{
			/*
			 * En este caso la FECHA_ACTUAL es mayor a la fecha guardada en la tabla PARAMETRO_AUTOMAT,
			 * por lo cual entendemos que es el primer CORRELATIVO del dia.
			 */
			correlativo = 1;
		}
//		else if (fecActualCal.get(Calendar.YEAR) >= fecParamCal.get(Calendar.YEAR) && fecActualCal.get(Calendar.DAY_OF_YEAR) == fecParamCal.get(Calendar.DAY_OF_YEAR))
		else if(fechaActualIgualFechaGuardada(dfecActualCal, dfecParamCal))
		{
			/*
			 * En este caso la FECHA_ACTUAL es igual a la fecha guardada en la tabla PARAMETRO_AUTOMAT,
			 * por lo cual entendemos que ya se ha realizado antes este proceso durante el dia.
			 */
			correlativo++;
		}
		else
		{
			throw new SyncconException(ErrorConstants.COD_ERROR_CORRELATIVO_APESEG);
		}
		
		/*
		 * Formateamos el correlativo con la fecha para actualizar posteriormente
		 * el registro en la tabla PARAMETRO_AUTOMAT
		 */
		return sdf.format(fecActualCal.getTime()) + " - " + correlativo;
	} //obtenerCorrelativo
	
	public static boolean fechaActualMayorFechaGuardada(Date fechaActual, Date fechaGuardada){
		
		boolean rpt = false;
		int resultado = 0;
		
		resultado = fechaActual.compareTo(fechaGuardada);
		if(resultado > 0){
			rpt = true;
		}
		
		return rpt;
		
	}
	
	public static boolean fechaActualIgualFechaGuardada(Date fechaActual, Date fechaGuardada){
		
		boolean rpt = false;
		int resultado = 1;
		
		resultado = fechaActual.compareTo(fechaGuardada);
		if(resultado == 0){
			rpt = true;
		}
		
		return rpt;
		
	}
	
	
	public Date getFecRegistroDesde()
	{
		return fecRegistroDesde;
	}
	
	public void setFecRegistroDesde(Date fecRegistroDesde)
	{
		this.fecRegistroDesde = fecRegistroDesde;
	}
	
	public Date getFecRegistroHasta()
	{
		return fecRegistroHasta;
	}
	
	public void setFecRegistroHasta(Date fecRegistroHasta)
	{
		this.fecRegistroHasta = fecRegistroHasta;
	}
	
	public List<RepConsultaAPESEGBean> getListaConsultaAPESEG()
	{
		return listaConsultaAPESEG;
	}
	
	public void setListaConsultaAPESEG(List<RepConsultaAPESEGBean> listaConsultaAPESEG)
	{
		this.listaConsultaAPESEG = listaConsultaAPESEG;
	}
	
	public boolean isDisabledBtnExportar()
	{
		return disabledBtnExportar;
	}
	
	public void setDisabledBtnExportar(boolean disabledBtnExportar)
	{
		this.disabledBtnExportar = disabledBtnExportar;
	}
	
	public boolean isDisabledBtnConfirmar()
	{
		return disabledBtnConfirmar;
	}
	
	public void setDisabledBtnConfirmar(boolean disabledBtnConfirmar)
	{
		this.disabledBtnConfirmar = disabledBtnConfirmar;
	}
	
} //RepConsultaAPESEGController
