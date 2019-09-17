package com.cardif.satelite.reportesbs.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.SusTramaCabeceraIB;
import com.cardif.satelite.model.reportesbs.Empresa;
import com.cardif.satelite.model.reportesbs.FirmanteCargo;
import com.cardif.satelite.model.reportesbs.Parametro;
import com.cardif.satelite.model.reportesbs.ParametrosSBSModeloUnoDetalle;
import com.cardif.satelite.model.reportesbs.Producto;
import com.cardif.satelite.model.reportesbs.ReporteSBSModeloUnoDetalle;
import com.cardif.satelite.model.reportesbs.ReportesGeneradosSBSModeloUnoDetalle;
import com.cardif.satelite.model.reportesbs.Socio;
import com.cardif.satelite.reportesbs.bean.JournalBeanReportes;
import com.cardif.satelite.reportesbs.dao.RepParametroMapper;
import com.cardif.satelite.reportesbs.dao.ReporteListadoSBSModeloUnoDetalleMapper;
import com.cardif.satelite.reportesbs.dao.ReporteSBSModeloUnoDetalleMapper;
import com.cardif.satelite.reportesbs.service.ReporteSBSModeloUnoDetalleService;
import com.cardif.satelite.tesoreria.bean.JournalBean;
import com.cardif.sunsystems.controller.SunsystemsController;
import com.cardif.sunsystems.util.ConstantesSun;

@Service("reporteSBSModeloUnoDetalleService")
public class ReporteSBSModeloUnoDetalleServiceImpl implements
		ReporteSBSModeloUnoDetalleService {

	public static final Logger log = Logger
			.getLogger(ReporteSBSModeloUnoDetalleServiceImpl.class);

	@Autowired
	private ReporteSBSModeloUnoDetalleMapper reporteSBSModeloUnoDetalleMapper;

	@Autowired
	private ReporteListadoSBSModeloUnoDetalleMapper reporteListadoSBSModeloUnoDetalleMapper;

	@Autowired
	private RepParametroMapper parametrosMapper;

	private SunsystemsController controlSun = SunsystemsController
			.getInstance();

	@Override
	public ReporteSBSModeloUnoDetalle registrarReporteSBSBModeloUno(
			String modeloRepore, String cargoFirmanteUno,
			String cargoFirmanteDos, String nomFirmanteUno,
			String nomFirmanteDos, String periodoAnio, String periodoTrimestre,
			BigDecimal tipoCambio, String estado, String usuario)
			throws SyncconException {

		if (log.isInfoEnabled()) {
			log.info("Inicio");
		}
		ReporteSBSModeloUnoDetalle reporteSBSModeloUnoDetalle = null;

		try {

			reporteSBSModeloUnoDetalle = new ReporteSBSModeloUnoDetalle();

			reporteSBSModeloUnoDetalle.setCargoFirmanteDos(cargoFirmanteDos);
			reporteSBSModeloUnoDetalle.setCargoFirmanteUno(cargoFirmanteUno);
			reporteSBSModeloUnoDetalle.setNomFirmanteUno(nomFirmanteUno);
			reporteSBSModeloUnoDetalle.setNomFirmanteDos(nomFirmanteDos);
			reporteSBSModeloUnoDetalle.setModeloRepore(modeloRepore);
			reporteSBSModeloUnoDetalle.setPeriodoAnio(periodoAnio);
			reporteSBSModeloUnoDetalle.setPeriodoTrimestre(periodoTrimestre);
			reporteSBSModeloUnoDetalle.setTipoCambio(tipoCambio);
			reporteSBSModeloUnoDetalle.setEstado(estado);
			reporteSBSModeloUnoDetalle.setUsuarioCreador(usuario);
			reporteSBSModeloUnoDetalle.setFechaCreado(new Date());

			int rows = reporteSBSModeloUnoDetalleMapper
					.insertSelective(reporteSBSModeloUnoDetalle);
			if (log.isInfoEnabled()) {
				log.info("Output [Se registro el objeto DetalleReporteRegVenta, filas afectadas: "
						+ rows + "]");
			}

		} catch (Exception e) {

			reporteSBSModeloUnoDetalle = null;
			log.error("Exception(" + e.getClass().getName() + ") - ERROR: "
					+ e.getMessage());
			log.error("Exception(" + e.getClass().getName() + ") -->"
					+ ExceptionUtils.getStackTrace(e));
		}

		return reporteSBSModeloUnoDetalle;
	}

	@Override
	public List<ParametrosSBSModeloUnoDetalle> listaTramaParametros()
			throws SyncconException {

		List<ParametrosSBSModeloUnoDetalle> lista = null;

		if (log.isInfoEnabled()) {
			log.info("Inicio");
		}

		try {

			lista = reporteSBSModeloUnoDetalleMapper.selectParametros();

		} catch (Exception e) {

			log.error("Exception(" + e.getClass().getName() + ") - ERROR: "
					+ e.getMessage());
			log.error("Exception(" + e.getClass().getName() + ") -->"
					+ ExceptionUtils.getStackTrace(e));

		}

		if (log.isInfoEnabled()) {
			log.info("Inicio");
		}
		return lista;
	}

	@Override
	public List<SelectItem> selectListParametroByPrimaryKey(String codParametro)
			throws SyncconException {

		log.info("Inicio");
		List<Parametro> parametroSBS = new ArrayList<Parametro>();
		Parametro parametroSBSBean = null;
		List<SelectItem> parametroItem = null;

		try {

			if (log.isDebugEnabled())
				log.debug("Input [" + codParametro + "]");

			parametroSBSBean = reporteSBSModeloUnoDetalleMapper.selectParametroByPrimaryKey(codParametro + "10");

			parametroSBS.add(parametroSBSBean);
			parametroItem = new ArrayList<SelectItem>();
			parametroItem.add(new SelectItem("", "- Seleccionar -"));

			for (Parametro objList : parametroSBS) {

				if (objList.getCodParametro().substring(0, 2)
						.equals(codParametro)
						&& objList.getCodParametro().length() == 4) {

					parametroItem.add(new SelectItem(objList.getCodParametro(),
							objList.getDescripcion()));
				}
			}

			if (log.isDebugEnabled())
				log.debug("Output [" + BeanUtils.describe(parametroSBS) + "]");

		} catch (Exception e) {
			log.error("Exception(" + e.getClass().getName() + ") - ERROR: "
					+ e.getMessage());
			log.error("Exception(" + e.getClass().getName() + ") -->"
					+ ExceptionUtils.getStackTrace(e));
		}
		log.info("Fin");

		return parametroItem;
	}

	@Override
	public Parametro selectParametroByPrimaryKey(String codParametro)
			throws SyncconException {
		log.info("Inicio");
		Parametro parametroSBS = null;
		try {

			if (log.isDebugEnabled())
				log.debug("Input [" + codParametro + "]");
			parametroSBS = reporteSBSModeloUnoDetalleMapper
					.selectParametroByPrimaryKey(codParametro);
			if (log.isDebugEnabled())
				log.debug("Output [" + BeanUtils.describe(parametroSBS) + "]");

		} catch (Exception e) {

			log.error("Exception(" + e.getClass().getName() + ") - ERROR: "
					+ e.getMessage());
			log.error("Exception(" + e.getClass().getName() + ") -->"
					+ ExceptionUtils.getStackTrace(e));

		}
		log.info("Fin");
		return parametroSBS;
	}

	@Override
	public Socio selectSocioByPrimaryKey(Long codSocio) throws SyncconException {
		log.info("Inicio");
		Socio socioReporte = null;
		try {

			if (log.isDebugEnabled())
				log.debug("Input [" + codSocio + "]");
			socioReporte = reporteSBSModeloUnoDetalleMapper
					.selectSocioByPrimaryKey(codSocio);
			if (log.isDebugEnabled())
				log.debug("Output [" + BeanUtils.describe(socioReporte) + "]");

		} catch (Exception e) {

			log.error("Exception(" + e.getClass().getName() + ") - ERROR: "
					+ e.getMessage());
			log.error("Exception(" + e.getClass().getName() + ") -->"
					+ ExceptionUtils.getStackTrace(e));

		}
		log.info("Fin");
		return socioReporte;
	}

	@Override
	public Producto selectProductoByPrimaryKey(Long codProducto)
			throws SyncconException {
		log.info("Inicio");
		Producto productoReporte = null;
		try {

			if (log.isDebugEnabled())
				log.debug("Input [" + codProducto + "]");
			productoReporte = reporteSBSModeloUnoDetalleMapper
					.selectProductoByPrimaryKey(codProducto);
			if (log.isDebugEnabled())
				log.debug("Output [" + BeanUtils.describe(productoReporte)
						+ "]");

		} catch (Exception e) {

			log.error("Exception(" + e.getClass().getName() + ") - ERROR: "
					+ e.getMessage());
			log.error("Exception(" + e.getClass().getName() + ") -->"
					+ ExceptionUtils.getStackTrace(e));

		}
		log.info("Fin");
		return productoReporte;
	}

	@Override
	public Empresa selectEmpresaByPrimaryKey(Long codEmpresa)
			throws SyncconException {
		log.info("Inicio");
		Empresa empresaReporte = null;
		try {

			if (log.isDebugEnabled())
				log.debug("Input [" + codEmpresa + "]");
			empresaReporte = reporteSBSModeloUnoDetalleMapper
					.selectEmpresaByPrimaryKey(codEmpresa);
			if (log.isDebugEnabled())
				log.debug("Output [" + BeanUtils.describe(empresaReporte) + "]");

		} catch (Exception e) {

			log.error("Exception(" + e.getClass().getName() + ") - ERROR: "
					+ e.getMessage());
			log.error("Exception(" + e.getClass().getName() + ") -->"
					+ ExceptionUtils.getStackTrace(e));

		}
		log.info("Fin");
		return empresaReporte;
	}

	@Override
	public List<List<JournalBeanReportes>> consultarRegistros(String idProveedorFrom,
			String idProveedorTo, String fecPeriodoInicio, String fecPeriodoFin)
			throws Exception {
		ArrayList<JournalBeanReportes> lista = null;
		List<List<JournalBeanReportes>> superlistabean = new ArrayList<List<JournalBeanReportes>>();
		JournalBeanReportes element = null;
		if (log.isDebugEnabled()) {
			log.debug("Input Extracción Proveedor [1.- " + idProveedorFrom
					+ ", " + "2.- " + idProveedorTo + ", " + "3.- "
					+ fecPeriodoInicio + ", " + "4.- " + fecPeriodoFin + "] , ");
		}

		List<List<com.cardif.sunsystems.mapeo.reportes.SSC.Payload.Ledger.Line>> superlista = controlSun
				.extraerCuentasModeloUnoReporteSBS(
						ConstantesSun.MDP_BUSCAR_RETENCION, idProveedorFrom,
						idProveedorTo, fecPeriodoInicio, fecPeriodoFin);

		if (superlista == null) {
			superlista = new ArrayList<List<com.cardif.sunsystems.mapeo.reportes.SSC.Payload.Ledger.Line>>();
		}
		for (List<com.cardif.sunsystems.mapeo.reportes.SSC.Payload.Ledger.Line> ab : superlista) {
			lista = new ArrayList<JournalBeanReportes>();
			for (com.cardif.sunsystems.mapeo.reportes.SSC.Payload.Ledger.Line sublista : ab) {
											
				element = new JournalBeanReportes();
				element.setTipoCambio(sublista.getBaseRate());
				element.setJournalType(sublista.getJournalType());
				element.setCodigoCuenta(sublista.getAccountCode());
				element.setNombreProveedor(sublista.getAccounts()
						.getDescription());
				element.setNombreProveedorCompleto(sublista.getAccounts()
						.getLongDescription());
				element.setFechaTransaccion(sublista.getTransactionDate());
				element.setReferencia(sublista.getTransactionReference());
				element.setDebitoCredito(sublista.getDebitCredit());
				element.setMoneda(sublista.getCurrencyCode());
				element.setImpTransaccionAmount(sublista.getTransactionAmount());
				element.setImpPropinas(sublista.getAmountTips());
				element.setImpSoles(sublista.getBaseAmount());
				element.setImpEuros(sublista.getBase2ReportingAmount());
				element.setGlosa(sublista.getDescription());
				element.setPeriodo(sublista.getAccountingPeriod());
				element.setRuc(sublista.getAccounts().getLookupCode() + "");
				element.setCentroCosto(sublista.getAnalysisCode1());
				element.setSocioProducto(sublista.getAnalysisCode2());
				element.setCanal(sublista.getAnalysisCode3());
				element.setProveedorEmpleado(sublista.getAnalysisCode4());
				element.setRucDniCliente(sublista.getAnalysisCode5());
				element.setPolizaCliente(sublista.getAnalysisCode6());
				element.setInversiones(sublista.getAnalysisCode7());
				element.setNroSiniestro(sublista.getAnalysisCode8());
				element.setComprobanteSunat(sublista.getAnalysisCode9());
				element.setTipoMedioPago(sublista.getAnalysisCode10());
				element.setDiario(sublista.getJournalNumber());
				element.setLineaDiario(sublista.getJournalLineNumber());

				lista.add(element);
			}
			superlistabean.add(lista);
		}
		return superlistabean;
	}

	@Override
	public List<ReportesGeneradosSBSModeloUnoDetalle> selectListReporte()
			throws SyncconException {

		List<ReportesGeneradosSBSModeloUnoDetalle> listReporte = null;

		if (log.isInfoEnabled()) {
			log.info("Inicio");
		}

		try {

			listReporte = reporteListadoSBSModeloUnoDetalleMapper
					.selectReporte();

		} catch (Exception e) {

			log.error("Exception(" + e.getClass().getName() + ") - ERROR: "
					+ e.getMessage());
			log.error("Exception(" + e.getClass().getName() + ") -->"
					+ ExceptionUtils.getStackTrace(e));

		}

		if (log.isInfoEnabled()) {
			log.info("Inicio");
		}

		return listReporte;
	}

	@Override
	public List<Parametro> selectParametroListByPrimaryKey(String codParametro) {

		log.info("Inicio");
		List<Parametro> parametroSBSList = null;
		try {

			if (log.isDebugEnabled())
				log.debug("Input [" + codParametro + "]");
			parametroSBSList = reporteListadoSBSModeloUnoDetalleMapper
					.selectListParametroByPrimaryKey(codParametro);

			if (log.isDebugEnabled())
				log.debug("Output [" + BeanUtils.describe(parametroSBSList)
						+ "]");

		} catch (Exception e) {

			log.error("Exception(" + e.getClass().getName() + ") - ERROR: "
					+ e.getMessage());
			log.error("Exception(" + e.getClass().getName() + ") -->"
					+ ExceptionUtils.getStackTrace(e));

		}
		log.info("Fin");

		return parametroSBSList;
	}

	@Override
	public List<ReportesGeneradosSBSModeloUnoDetalle> consultarReportes(
			String codigoReporte, String usuarioReporte,
			String tipoReporteParametro, String periodoAnio,
			String periodoTrimestre, String estadoReporte) {

		log.info("Inicio");
		List<ReportesGeneradosSBSModeloUnoDetalle> objList = null;
		try {
			if (codigoReporte == null || codigoReporte.length() == 0) {
				codigoReporte = "T";
			}
			if (usuarioReporte == null || usuarioReporte.length() == 0) {
				usuarioReporte = "T";
			}
			if (tipoReporteParametro == null
					|| tipoReporteParametro.length() == 0) {
				tipoReporteParametro = "T";
			}
			if (periodoAnio == null || periodoAnio.length() == 0) {
				periodoAnio = "T";
			}
			if (periodoTrimestre == null || periodoTrimestre.length() == 0) {
				periodoTrimestre = "T";
			}
			if (estadoReporte == null || estadoReporte.length() == 0) {
				estadoReporte = "T";
			}

			if (log.isDebugEnabled())
				log.debug("Input [" + codigoReporte + "]");
			objList = reporteListadoSBSModeloUnoDetalleMapper
					.selectListReportesByParam(codigoReporte, usuarioReporte,
							tipoReporteParametro, periodoAnio,
							periodoTrimestre, estadoReporte);

			if (log.isDebugEnabled())
				log.debug("Output [" + BeanUtils.describe(objList) + "]");

		} catch (Exception e) {

			log.error("Exception(" + e.getClass().getName() + ") - ERROR: "
					+ e.getMessage());
			log.error("Exception(" + e.getClass().getName() + ") -->"
					+ ExceptionUtils.getStackTrace(e));

		}
		log.info("Fin");

		return objList;
	}

	@Override
	public int selectReporteListByParamAnterGuardar(String periodoAnio,
			String periodoTrimestre, String tipoReporte) {

		List<ReportesGeneradosSBSModeloUnoDetalle> listReporte = null;
		int retorno = 0;

		if (log.isInfoEnabled()) {
			log.info("Inicio");
		}

		try {

			listReporte = reporteListadoSBSModeloUnoDetalleMapper
					.selectReporteAntesGuardar(periodoAnio, periodoTrimestre,
							tipoReporte);

			if (listReporte.size() > 0) {
				retorno = 1;
			}

		} catch (Exception e) {

			retorno = 2;
			log.error("Exception(" + e.getClass().getName() + ") - ERROR: "
					+ e.getMessage());
			log.error("Exception(" + e.getClass().getName() + ") -->"
					+ ExceptionUtils.getStackTrace(e));

		}

		return retorno;
	}

	@Override
	public List<FirmanteCargo> listafirmanetCargo(String codReporte) {
		List<FirmanteCargo> lista = null;

		lista = reporteSBSModeloUnoDetalleMapper.listaFirmanteCargo(codReporte);
		if (lista != null) {
			log.info("Registros firmantes" + lista.size());
		}
		return lista;
	}

	@Override
	public List<Parametro> listarReporteModelo(String codParametro)
			throws SyncconException {
		log.info("Inicio Tipo Reaseguro");
		List<Parametro> lista = null;
		int size = 0;
		lista = parametrosMapper.getListaParametros(codParametro);
		size = (lista != null) ? lista.size() : 0;
		if (log.isDebugEnabled())
			log.debug("Output [Registros Reaseguros = " + size + "]");
		return lista;
	}

	@Override
	public ReportesGeneradosSBSModeloUnoDetalle consultarReportesParaErrores(
			String codigoReporte, String periodoAnio, String periodoTrimestre) {
		
		log.info("Inicio");
		
		List<ReportesGeneradosSBSModeloUnoDetalle> objList = null;
		ReportesGeneradosSBSModeloUnoDetalle objBean = null;
		
		try {
			

			if (log.isDebugEnabled())
				log.debug("Input [" + codigoReporte + "]");
				log.debug("Input [" + periodoAnio + "]");
				log.debug("Input [" + periodoTrimestre + "]");
		
			objList = reporteListadoSBSModeloUnoDetalleMapper.consultarReportesParaErrores( codigoReporte,periodoAnio,periodoTrimestre); 
							
			if(objList.size() > 0){
			
				for (int i = 0; i < objList.size(); i++) {
					
					objBean = new ReportesGeneradosSBSModeloUnoDetalle();				
					objBean.setCodReporte(objList.get(i).getCodReporte());				
					break;
				}
			
			}
			
			if (log.isDebugEnabled())
				log.debug("Output [" + BeanUtils.describe(objList) + "]");

		} catch (Exception e) {

			objBean = null;
			log.error("Exception(" + e.getClass().getName() + ") - ERROR: "+ e.getMessage());
			log.error("Exception(" + e.getClass().getName() + ") -->"+ ExceptionUtils.getStackTrace(e));

		}
		
		log.info("Fin");

		return objBean;
	}

	@Override
	public void actualizarEstadoReporte(long longValue) {
		
	log.info("Inicio");
		
	/*
	final String PROCESAR_TRAMA = "UPDATE dbo.SUS_TRAMA_CABECERA_IB SET ESTADO='PROCESADO' WHERE  ESTADO='PENDIENTE' AND PERIODO=#{periodo}";
		@Select(PROCESAR_TRAMA)
		@ResultMap(value = "BaseResultMap")
		SusTramaCabeceraIB procesarTrama(@Param("periodo") String periodo);
	*/
					
		try {
			   

			if (log.isDebugEnabled())
				log.debug("Input [" + longValue + "]");				
		
			  reporteListadoSBSModeloUnoDetalleMapper.actualizarEstadoReporte(longValue); 
				
		} catch (Exception e) {
		
			log.error("Exception(" + e.getClass().getName() + ") - ERROR: "+ e.getMessage());
			log.error("Exception(" + e.getClass().getName() + ") -->"+ ExceptionUtils.getStackTrace(e));

		}
		
		log.info("Fin");
		
	}

}
