package com.cardif.satelite.reportes.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.dao.ParametroAutomatMapper;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.reportes.DetalleReporteSBS;
import com.cardif.satelite.model.reportes.ReporteSBS;
import com.cardif.satelite.model.reportes.ReporteSBSRelacionado;
import com.cardif.satelite.model.reportes.SBSPeriodo;
import com.cardif.satelite.reportes.bean.RepAnulacionPrimaSBSBean;
import com.cardif.satelite.reportes.bean.RepConsultaSBSBean;
import com.cardif.satelite.reportes.bean.RepContratoSoatSBSBean;
import com.cardif.satelite.reportes.bean.RepProduccionPrimaSBSBean;
import com.cardif.satelite.reportes.dao.DetalleReporteLiquidacionMapper;
import com.cardif.satelite.reportes.dao.DetalleReporteSBSMapper;
import com.cardif.satelite.reportes.dao.ReporteSBSMapper;
import com.cardif.satelite.reportes.dao.ReporteSBSRelacionadoMapper;
import com.cardif.satelite.reportes.dao.SBSPeriodoMapper;
import com.cardif.satelite.reportes.service.RepConsultaSBSService;
import com.cardif.satelite.suscripcion.dao.DetalleTramaDiariaMapper;
import com.cardif.satelite.suscripcion.dao.DetalleTramaDiariaMotivoMapper;
import com.cardif.satelite.util.Base64Util;

@Service("repConsultaSBSService")
public class RepConsultaSBSServiceImpl implements RepConsultaSBSService
{
	private final Logger logger = Logger.getLogger(RepConsultaSBSServiceImpl.class);
	
	@Autowired
	private SBSPeriodoMapper sbsPeriodoMapper;
	
	@Autowired
	private ReporteSBSMapper reporteSBSMapper;
	
	@Autowired
	private ReporteSBSRelacionadoMapper repSBSRelacionadoMapper;
	
	@Autowired
	private DetalleReporteSBSMapper detReporteSBSMapper;
	
	@Autowired
	private DetalleReporteLiquidacionMapper detReporteLiquidacionMapper;
	
	@Autowired
	private DetalleTramaDiariaMapper detTramaDiariaMapper;
	
	@Autowired
	private DetalleTramaDiariaMotivoMapper detTramaDiariaMotivoMapper;
	
	@Autowired
	private ParametroAutomatMapper parametroMapper;
	
	
	@Override
	public List<SBSPeriodo> buscarPorTipoAnexo(String tipoAnexo) throws SyncconException
	{
		if (logger.isDebugEnabled()) {logger.debug("Inicio");}
		List<SBSPeriodo> listaSBSPeriodos = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + tipoAnexo + "]");}
			
			listaSBSPeriodos = sbsPeriodoMapper.selectByTipoAnexo(tipoAnexo);
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + (null != listaSBSPeriodos ? listaSBSPeriodos.size() : 0) + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isDebugEnabled()) {logger.debug("Fin");}
		return listaSBSPeriodos;
	} //buscarPorTipoAnexo
	
	@Override
	public List<RepConsultaSBSBean> buscarReporteSBSCabecera(String codTipoAnexo, Integer anio, Long codPeriodo) 
			throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<RepConsultaSBSBean> listaReporteSBSBean = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + codTipoAnexo + ", 2.-" + anio + ", 3.-" + codPeriodo + "]");}
			codTipoAnexo = StringUtils.isNotBlank(codTipoAnexo) ? codTipoAnexo : null;
			anio = (null != anio && 0 != anio) ? anio : null;
			codPeriodo = (null != codPeriodo && 0 != codPeriodo) ? codPeriodo : null;
			
			if (logger.isInfoEnabled()) {logger.info("Input [1.-" + codTipoAnexo + ", 2.-" + anio + ", 3.-" + codPeriodo + "]");}
			
			listaReporteSBSBean = reporteSBSMapper.selectByTipoAnioPeriodo(codTipoAnexo, anio, codPeriodo);
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + (null != listaReporteSBSBean ? listaReporteSBSBean.size() : 0) + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listaReporteSBSBean;
	} //buscarReporteSBSCabecera
	
	@Override
	public String obtenerArchivoAdjuntoSBS(Long pkReporteSBS) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String archivoAdjunto = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + pkReporteSBS + "]");}
			ReporteSBS reporteSBS = reporteSBSMapper.selectByPrimaryKey(pkReporteSBS);
			
			if (null != reporteSBS)
			{
				archivoAdjunto = reporteSBS.getArchivoAdjuntoExportar();
			}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return archivoAdjunto;
	} //obtenerArchivoAdjuntoSBS
	
	@Override
	public boolean verificarExisteReporte(String codTipoAnexo, Integer anio, Long codPeriodo) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		boolean flag = false;
		
		try
		{
			if (logger.isInfoEnabled()) {logger.info("Input [1.-" + codTipoAnexo + ", 2.-" + anio + ", 3.-" + codPeriodo + "]");}
//			ReporteSBS reporteSBS = reporteSBSMapper.selectOneByTipoAnioPeriodo(codTipoAnexo, anio, codPeriodo);
			List<ReporteSBS> lstReporteSBS = reporteSBSMapper.selectOneByTipoAnioPeriodo(codTipoAnexo, anio, codPeriodo);
			
//			if (null != reporteSBS)
			if (lstReporteSBS != null && lstReporteSBS.size() > 0)
			{
//				if (logger.isInfoEnabled()) {logger.info("Se valido la existencia del REPORTE: PK-->" + reporteSBS.getId());}
				if (logger.isInfoEnabled()) {logger.info("Se valido la existencia del REPORTE: Cantidad de registros existentes para este tipo de reporte -->" + 
				lstReporteSBS.size());}
				flag = true;
			}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return flag;
	} //verificarExisteReporte
	
	private Integer obtenerPeriodoAnexoContrato(String codPeriodo, String tipoPeriodo)
	{
		Integer periodoSalida = null;
		if (tipoPeriodo.equalsIgnoreCase("INICIAL"))
		{
			Integer periodo = Integer.valueOf(codPeriodo.substring(1));
			switch(periodo)
			{
				case 1: periodoSalida = 1; break;
				case 2: periodoSalida = 4; break;
				case 3: periodoSalida = 7; break;
				default: periodoSalida = 10;
			}
		}
		else if (tipoPeriodo.equalsIgnoreCase("FINAL"))
		{
			Integer periodo = Integer.valueOf(codPeriodo.substring(1));
			switch(periodo)
			{
				case 1: periodoSalida = 3; break;
				case 2: periodoSalida = 6; break;
				case 3: periodoSalida = 9; break;
				default: periodoSalida = 12;
			}
		}
		return periodoSalida;
	} //obtenerPeriodoAnexoContrato
	
	@Override
	public List<RepProduccionPrimaSBSBean> extraerSBSProduccionPrimas(Integer anio, Long codPeriodo, Double tipoCambioUSD) throws SyncconException
	{
		if (logger.isDebugEnabled()) {logger.debug("Inicio");}
		List<RepProduccionPrimaSBSBean> produccionPrimasLista = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + anio + ", 2.-" + codPeriodo + ", 3.-" + tipoCambioUSD + "]");}
			
			if (logger.isDebugEnabled()) {logger.debug("Extraer informacion del PERIODO, codPeriodo: " + codPeriodo);}
			SBSPeriodo sbsPeriodo = sbsPeriodoMapper.selectByPrimaryKey(codPeriodo);
			
			
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + anio + ", 2.-" + sbsPeriodo.getTrimestre() + ", 3.-" + tipoCambioUSD + "]");}
			
			produccionPrimasLista = detReporteSBSMapper.selectSBSProduccionPrimas(anio, Integer.valueOf(sbsPeriodo.getTrimestre().substring(1)));
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + (null != produccionPrimasLista ? produccionPrimasLista.size() : 0) + "]");}
			
			
			if (null != produccionPrimasLista && 0 < produccionPrimasLista.size())
			{
				for (int i=0; i<produccionPrimasLista.size(); i++)
				{
					/* Definiendo CODIGO DE FILA */
					produccionPrimasLista.get(i).setCodigoFila(i + 1);
					
					
					Double pComercialBruta = produccionPrimasLista.get(i).getPrimaComercialBruta();
					produccionPrimasLista.get(i).setPrimaComercialBruta(null != pComercialBruta ? (pComercialBruta / tipoCambioUSD) : 0D);
					
					Double derechoEmision = produccionPrimasLista.get(i).getDerechoEmision();
					produccionPrimasLista.get(i).setDerechoEmision(null != derechoEmision ? (derechoEmision / tipoCambioUSD) : 0D);
					
					Double pComercial = produccionPrimasLista.get(i).getPrimaComercial();
					produccionPrimasLista.get(i).setPrimaComercial(null != pComercial ? (pComercial / tipoCambioUSD) : 0D);
					
					Double pComercialCobrar = produccionPrimasLista.get(i).getPrimaComercialCobrar();
					produccionPrimasLista.get(i).setPrimaComercialCobrar(null != pComercialCobrar ? (pComercialCobrar / tipoCambioUSD) : 0D);
					
					Double comisiones = produccionPrimasLista.get(i).getComisiones();
					produccionPrimasLista.get(i).setComisiones(null != comisiones ? (comisiones / tipoCambioUSD) : 0D);
					
					Double fondoCompensacion = produccionPrimasLista.get(i).getFondoCompensacion();
					produccionPrimasLista.get(i).setFondoCompensacion(null != fondoCompensacion ? (fondoCompensacion / tipoCambioUSD) : 0D);
				}
			}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return produccionPrimasLista;
	} //extraerSBSProduccionPrimas
	
	@Override
	public List<RepAnulacionPrimaSBSBean> extraerSBSAnulacionPrimas(Integer anio, Long codPeriodo, Double tipoCambioUSD) throws SyncconException
	{
		if (logger.isDebugEnabled()) {logger.debug("Inicio");}
		List<RepAnulacionPrimaSBSBean> anulacionPrimasLista = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + anio + ", 2.-" + codPeriodo + ", 3.-" + tipoCambioUSD + "]");}
			
			if (logger.isDebugEnabled()) {logger.debug("Extraer informacion del PERIODO, codPeriodo: " + codPeriodo);}
			SBSPeriodo sbsPeriodo = sbsPeriodoMapper.selectByPrimaryKey(codPeriodo);
			
			
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + anio + ", 2.-" + sbsPeriodo.getTrimestre() + ", 3.-" + tipoCambioUSD + "]");}
			
			anulacionPrimasLista = detReporteSBSMapper.selectSBSAnulacionPrimas(anio, Integer.valueOf(sbsPeriodo.getTrimestre().substring(1)));
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + (null != anulacionPrimasLista ? anulacionPrimasLista.size() : 0) + "]");}
			
			
			if (null != anulacionPrimasLista && 0 < anulacionPrimasLista.size())
			{
				for (int i=0; i<anulacionPrimasLista.size(); i++)
				{	
					/* Definiendo CODIGO DE FILA */
					anulacionPrimasLista.get(i).setCodigoFila(i + 1);
					
					
					Double pComercialBruta = anulacionPrimasLista.get(i).getPrimaComercialBruta();
					anulacionPrimasLista.get(i).setPrimaComercialBruta(null != pComercialBruta ? (pComercialBruta / tipoCambioUSD) : 0D);
					
					Double pComercial = anulacionPrimasLista.get(i).getPrimaComercial();
					anulacionPrimasLista.get(i).setPrimaComercial(null != pComercial ? (pComercial / tipoCambioUSD) : 0D);
					
					Double fondoCompensacion = anulacionPrimasLista.get(i).getMontoFondoSOAT();
					anulacionPrimasLista.get(i).setMontoFondoSOAT(null != fondoCompensacion ? (fondoCompensacion / tipoCambioUSD) : 0D);
				}
			}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isDebugEnabled()) {logger.debug("Fin");}
		return anulacionPrimasLista;
	} //extraerSBSAnulacionPrimas
	
	@Override
	public List<RepContratoSoatSBSBean> extraerSBSContratosComercioSOAT(Integer anio, Long codPeriodo, Double tipoCambioUSD) throws SyncconException
	{
		if (logger.isDebugEnabled()) {logger.debug("Inicio");}
		List<RepContratoSoatSBSBean> contratoSOATLista = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + anio + ", 2.-" + codPeriodo + ", 3.-" + tipoCambioUSD + "]");}
			
			if (logger.isDebugEnabled()) {logger.debug("Extraer informacion del PERIODO, codPeriodo: " + codPeriodo);}
			SBSPeriodo sbsPeriodo = sbsPeriodoMapper.selectByPrimaryKey(codPeriodo);
			
			Integer periodoIni = obtenerPeriodoAnexoContrato(sbsPeriodo.getTrimestre(), "INICIAL");
			Integer periodoFin = obtenerPeriodoAnexoContrato(sbsPeriodo.getTrimestre(), "FINAL");
			
			
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + anio + ", 2.-" + periodoIni + ", 3.-" + periodoFin + "]");}
			
			contratoSOATLista = detReporteSBSMapper.selectSBSContratosComercio(anio, periodoIni, periodoFin);
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + (null != contratoSOATLista ? contratoSOATLista.size() : 0) + "]");}
			
			
			if (null != contratoSOATLista && 0 < contratoSOATLista.size())
			{
				for (int i=0; i<contratoSOATLista.size(); i++)
				{
					/* Definiendo CODIGO DE FILA */
					contratoSOATLista.get(i).setCodigoFila(i + 1);
					
					Double montoPrimas = contratoSOATLista.get(i).getMontoPrimas();
					contratoSOATLista.get(i).setMontoPrimas(null != montoPrimas ? (montoPrimas / tipoCambioUSD) : 0D);
				}
			}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isDebugEnabled()) {logger.debug("Fin");}
		return contratoSOATLista;
	} //extraerSBSComercializacionSOAT

	@SuppressWarnings("unchecked")
	@Override
	public ReporteSBS guardarDatosReporte(String codTipoAnexo, Integer anio, Long codPeriodo, Double tipoCambioUSD, byte[] reporteBytes, Object listaReporte)
			throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		ReporteSBS reporteSBS = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + codTipoAnexo + ", 2.-" + anio + ", 3.-" + codPeriodo + ", 4.-" + tipoCambioUSD + ", 5.-" + reporteBytes + ", 6.-" + listaReporte + "]");}
			
			String fecActual = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
			
			List<Long> pkDetReporteSBSLista = new ArrayList<Long>();
			String nombreArchivo = null;
			if (codTipoAnexo.equalsIgnoreCase(Constantes.SBS_TIPO_ANEXO_PROD_PRIMAS))
			{
				List<RepProduccionPrimaSBSBean> listaProduccionPrimaSBS = (ArrayList<RepProduccionPrimaSBSBean>) listaReporte;
				
				for (RepProduccionPrimaSBSBean bean : listaProduccionPrimaSBS)
				{
					DetalleReporteSBS detalleReporteSBS = detReporteSBSMapper.selectByPrimaryKey(bean.getId());
					
					detalleReporteSBS.setReportado(Constantes.SBS_REPORTADO_SI);
					detalleReporteSBS.setFechaReportado(Calendar.getInstance().getTime());
					detalleReporteSBS.setEstadoReportado(Constantes.SBS_DET_REPORTE_ESTADO_PROD);
					
					detReporteSBSMapper.updateByPrimaryKeySelective(detalleReporteSBS);
					
					pkDetReporteSBSLista.add(bean.getId());
				}
				nombreArchivo =  Constantes.SBS_TIPO_ANEXO_PROD_PRIMAS + fecActual + ".068";
			}
			else if (codTipoAnexo.equalsIgnoreCase(Constantes.SBS_TIPO_ANEXO_ANUL_PRIMAS))
			{
				List<RepAnulacionPrimaSBSBean> listaAnulacionPrimaSBS = (ArrayList<RepAnulacionPrimaSBSBean>) listaReporte;
				
				for (RepAnulacionPrimaSBSBean bean : listaAnulacionPrimaSBS)
				{
					DetalleReporteSBS detalleReporteSBS = detReporteSBSMapper.selectByPrimaryKey(bean.getId());
					
					detalleReporteSBS.setReportado(Constantes.SBS_REPORTADO_SI);
					detalleReporteSBS.setFechaReportado(Calendar.getInstance().getTime());
					detalleReporteSBS.setEstadoReportado(Constantes.SBS_DET_REPORTE_ESTADO_ANUL);
					
					detReporteSBSMapper.updateByPrimaryKeySelective(detalleReporteSBS);
					
					pkDetReporteSBSLista.add(bean.getId());
				}
				nombreArchivo =  Constantes.SBS_TIPO_ANEXO_ANUL_PRIMAS + fecActual + ".068";
			}
			else if (codTipoAnexo.equalsIgnoreCase(Constantes.SBS_TIPO_ANEXO_CONTRATO_SOAT))
			{
				List<RepContratoSoatSBSBean> listaContratosComercioSBS = (ArrayList<RepContratoSoatSBSBean>) listaReporte;
				
				for (RepContratoSoatSBSBean bean : listaContratosComercioSBS)
				{
					pkDetReporteSBSLista.add(bean.getId());
				}
				nombreArchivo =  Constantes.SBS_TIPO_ANEXO_CONTRATO_SOAT + fecActual + ".077";
			}
			else
			{
				throw new SyncconException(ErrorConstants.COD_ERROR_REP_SBS_TIP_ANEXO_INDEFINIDO);
			}
			
			
			/*
			 * Guardando en la base de datos
			 */
			reporteSBS = new ReporteSBS();
			
			reporteSBS.setNombreArchivo(nombreArchivo);
			reporteSBS.setArchivoAdjuntoExportar(Base64Util.getInstance().convertBase64ToString(reporteBytes));
			reporteSBS.setTipoReporte(codTipoAnexo);
			reporteSBS.setAnioReporte(anio);
			reporteSBS.setPeriodoReporte(codPeriodo);
			reporteSBS.setNroRegistros(pkDetReporteSBSLista.size());
			reporteSBS.setFechaProceso(Calendar.getInstance().getTime());
			reporteSBS.setTipoCambio(tipoCambioUSD);
			reporteSBS.setEstadoProceso(Constantes.ESTADO_REPORTE_SBS_PENDIENTE);
			
			int rows = reporteSBSMapper.insertSelective(reporteSBS);
			if (logger.isInfoEnabled()) {logger.info("Se registro el objeto ReporteSBS, pk: " + reporteSBS.getId() + ", filas afectadas: "+ rows);}
			
			
			for (Long pkDetalleReporte : pkDetReporteSBSLista)
			{
				ReporteSBSRelacionado repSBSRelacionado = new ReporteSBSRelacionado();
				
				repSBSRelacionado.setIdReporte(reporteSBS.getId());
				repSBSRelacionado.setIdDetalleReporte(pkDetalleReporte);
				
				rows = repSBSRelacionadoMapper.insertSelective(repSBSRelacionado);
				if (logger.isInfoEnabled()) {logger.info("Se registro el objeto ReporteSBSRelacionado, pk: " + repSBSRelacionado.getId() + ", filas afectadas: " + rows);}
			}
			
			if (logger.isInfoEnabled()) {logger.info("Se realizo el registro correctamente.");}
		}
		catch (SyncconException e)
		{
			logger.error("SyncconException() - " + ErrorConstants.ERROR_SYNCCON + e.getMessageComplete());
			throw e;
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_REP_SBS_REGISTRAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return reporteSBS;
	} //guardarDatosReporte

	@Override
	public void confirmarReporteSBS(Long pkReporteSBS) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		
		try
		{
			ReporteSBS reporteSBS = reporteSBSMapper.selectByPrimaryKey(pkReporteSBS);
			
			if (null != reporteSBS)
			{
				reporteSBS.setEstadoProceso(Constantes.ESTADO_REPORTE_SBS_CONFIRMADO);
				
				int rows = reporteSBSMapper.updateByPrimaryKeySelective(reporteSBS);
				if (logger.isInfoEnabled()) {logger.info("Se actualizo el ESTADO del Reporte SBS a CONFIRMADO. Filas afectadas: " + rows);}
			}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_ACTUALIZAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
	} //confirmarReporteSBS
	
} //RepConsultaSBSServiceImpl
