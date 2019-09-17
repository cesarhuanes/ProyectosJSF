package com.cardif.satelite.reportesbs.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_INSERTAR;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.model.reportesbs.CargaOperaciones;
import com.cardif.satelite.model.reportesbs.LogError;
import com.cardif.satelite.model.reportesbs.Parametro;
import com.cardif.satelite.model.reportesbs.ProcesoArchivo;
import com.cardif.satelite.model.reportesbs.ProcesoArchivoTransaccional;
import com.cardif.satelite.reportesbs.dao.CargaOperacionesMapper;
import com.cardif.satelite.reportesbs.dao.EjecutaArchivoMapper;
import com.cardif.satelite.reportesbs.dao.LogErrorMapper;
import com.cardif.satelite.reportesbs.dao.ProcesoArchivoMapper;
import com.cardif.satelite.reportesbs.dao.RepParametroMapper;
import com.cardif.satelite.reportesbs.service.ProcesoArchivoService;

@Service("procesoArchivoService")
public class ProcesoArchivoServiceImpl implements ProcesoArchivoService {
	public static final Logger log = Logger
			.getLogger(ProcesoArchivoServiceImpl.class);

	@Autowired
	private ProcesoArchivoMapper procesoArchivoMapper;
	@Autowired
	private CargaOperacionesMapper cargaOperacionesMapper;
	@Autowired
	private LogErrorMapper logErroMapper;
	@Autowired
	private RepParametroMapper parametrosMapper;
	@Autowired
	private EjecutaArchivoMapper ejecutaArchivoMapper;

	@Override
	public List<ProcesoArchivo> listaProcesoArchivo(Long codProceso,
			String codEstado, String nomArchivo, String usuarioProceso,
			java.util.Date fechaCargaDesde, java.util.Date fechaCargaHasta,
			java.util.Date fechaProcesoDesde, java.util.Date fechaProcesoHasta)
			throws SyncconException {
		List<ProcesoArchivo> lista = null;
		int size = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		String cargaDesde = ((fechaCargaDesde != null) ? sdf
				.format(fechaCargaDesde) : "1900-01-01");
		String cargaHasta = ((fechaCargaHasta != null) ? sdf
				.format(fechaCargaHasta) : "2050-12-31");
		String procesoDesde = ((fechaProcesoDesde != null) ? sdf
				.format(fechaProcesoDesde) : "1900-01-01");
		String procesoHasta = ((fechaProcesoHasta != null) ? sdf
				.format(fechaProcesoHasta) : "2050-12-31");

		if (codProceso == null) {
			codProceso = (long) 0;
		}

		if (nomArchivo == null || nomArchivo.trim().equals("")) {
			nomArchivo = "T";
		}
		if (usuarioProceso == null || usuarioProceso.trim().equals("")) {
			usuarioProceso = "T";
		}

		log.info("Codigo Proceso:" + codProceso);
		log.info("Nombre de Archivo:" + nomArchivo);
		log.info("usuario Proceso:" + usuarioProceso);
		log.info("Fecha de Carga Desde:" + cargaDesde);
		log.info("Fecha de Carga Hasta:" + cargaHasta);
		log.info("Fecha de Proceso Desde:" + procesoDesde);
		log.info("Fecha de Proceso Hasta:" + procesoHasta);

		lista = procesoArchivoMapper.getListaProcesoArchivo(codProceso,
				codEstado, nomArchivo, usuarioProceso, cargaDesde, cargaHasta,
				procesoDesde, procesoHasta);
		size = (lista != null) ? lista.size() : 0;
		if (log.isDebugEnabled())
			log.debug("Output [Registros Proceso Archivo = " + size + "]");
		return lista;
	}

	@Override
	public List<CargaOperaciones> listaDetalleOperaciones(Long codProceso)
			throws SecurityException {
		List<CargaOperaciones> lista = null;
		int size = 0;
		lista = cargaOperacionesMapper.getListaDetalle(codProceso);
		size = (lista != null) ? lista.size() : 0;
		if (log.isDebugEnabled())
			log.debug("Output [Registros Detalles = " + size + "]");
		return lista;
	}

	@Override
	public boolean eliminarDetalle(Long codProceso) {

		log.info("Inicio Eliminar");
		boolean flagEliminar = false;
		try {
			if (log.isDebugEnabled())
				log.debug("Input [" + codProceso + "]");
			cargaOperacionesMapper.deleteByPrimaryKey(codProceso);
			flagEliminar = true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);

		}
		log.info("Fin");
		return flagEliminar;

	}

	@Override
	public boolean actualizaProcesoArchivo(ProcesoArchivoTransaccional bean)
			throws SecurityException {
		boolean flagActualizar = false;
		try {

			procesoArchivoMapper.updateByPrimaryKey(bean);
			flagActualizar = true;
		} catch (Exception e) {
			log.error("Exception(" + e.getClass().getName() + ") - ERROR: "
					+ e.getMessage());
			log.error("Exception(" + e.getClass().getName() + ") -->"
					+ ExceptionUtils.getStackTrace(e));

		}
		return flagActualizar;
	}

	@Override
	public ProcesoArchivoTransaccional buscaProcesoArhivo(Long codProceso)
			throws SyncconException {
		log.info("Inicio");

		ProcesoArchivoTransaccional procesoArchivoTransaccional = null;
		try {
			if (log.isDebugEnabled())
				log.debug("Input [ " + codProceso + " ]");

			procesoArchivoTransaccional = procesoArchivoMapper
					.selectByPrimaryKey(codProceso);

			if (log.isDebugEnabled())
				log.debug("Output [ " + BeanUtils.describe(codProceso) + " ]");

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new SyncconException(COD_ERROR_BD_INSERTAR);

		}
		log.info("Fin");
		return procesoArchivoTransaccional;
	}

	@Override
	public List<LogError> listaLogError(Long codProceso)
			throws SyncconException {
		List<LogError> lista = null;
		int size = 0;
		lista = logErroMapper.getlistaError(codProceso);
		size = (lista != null) ? lista.size() : 0;
		if (log.isDebugEnabled())
			log.debug("Output [Registros Log Error = " + size + "]");
		return lista;
	}

	@Override
	public boolean ejecutarPaquete() throws SyncconException {
		boolean flagEjecuccion = false;
		try {
			ejecutaArchivoMapper.ejecutarJob(Constantes.JOB_EJECUTAR_ARCHIVO);
			flagEjecuccion = true;
		} catch (Exception e) {
			log.info("Error al ejecutar el paquete" + e.getMessage());
		}
        log.info("Resultado de ejecuccion del paquete: "+flagEjecuccion);
		return flagEjecuccion;
	}



	@Override
	public List<CargaOperaciones> TraerDetalleCuentas(String BO, String moneda) {

		List<CargaOperaciones> lista = null;

		if (log.isInfoEnabled()) {
			log.info("Inicio");
		}

		try {

			lista = cargaOperacionesMapper.TraerDetalleCuentas(BO);

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
	public String getRutaPKG(String codParametro) throws SyncconException {
		String rutaPKG=null;
		List<Parametro> listaRutaPKG=null;
		listaRutaPKG=parametrosMapper.getListaRuta(codParametro);
		if(listaRutaPKG!=null){
			rutaPKG=listaRutaPKG.get(0).getDescripcion().toString();
			log.info("Ruta PKG :"+rutaPKG);
		}
		return rutaPKG;
	}

	@Override
	public boolean encontroJobRunnable() throws SyncconException {
		boolean isRunnable = false;
		String runJob = ejecutaArchivoMapper
				.resultadoJobRunnable(Constantes.JOB_EJECUTAR_ARCHIVO);
		log.info("Resultado Runn:"+runJob);
		if (runJob != null) {
			isRunnable = true;
		}
		return isRunnable;
	}

	@Override
	public List<CargaOperaciones> TraerDetalleCuentasValidar(String BO,
			String moneda) {
		
		List<CargaOperaciones> lista = null;

		if (log.isInfoEnabled()) {
			log.info("Inicio");
		}

		try {

			lista = cargaOperacionesMapper.TraerDetalleCuentasValidar(BO);

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

}
