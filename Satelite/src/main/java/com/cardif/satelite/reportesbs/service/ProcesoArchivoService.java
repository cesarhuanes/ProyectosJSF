package com.cardif.satelite.reportesbs.service;

import java.util.Date;
import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.reportesbs.CargaOperaciones;
import com.cardif.satelite.model.reportesbs.LogError;
import com.cardif.satelite.model.reportesbs.ProcesoArchivo;
import com.cardif.satelite.model.reportesbs.ProcesoArchivoTransaccional;

public interface ProcesoArchivoService {
	public List<ProcesoArchivo> listaProcesoArchivo(Long codProceso,
			String codEstado, String nomArchivo, String usuarioProceso,
			Date fechaCargaDesde, Date fechaCargaHasta, Date fechaProcesoDesde,
			Date fechaProcesoHasta) throws SyncconException;

	public List<CargaOperaciones> listaDetalleOperaciones(Long codProceso)
			throws SecurityException;

	public boolean eliminarDetalle(Long codProceso);

	public boolean actualizaProcesoArchivo(ProcesoArchivoTransaccional bean)
			throws SyncconException;;

	public ProcesoArchivoTransaccional buscaProcesoArhivo(Long codProceso)
			throws SyncconException;

	public List<LogError> listaLogError(Long codProceso)
			throws SyncconException;

	public boolean ejecutarPaquete() throws SyncconException;

	List<CargaOperaciones> TraerDetalleCuentas(String BO,String moneda);
	
	List<CargaOperaciones> TraerDetalleCuentasValidar(String BO,String moneda);

	public String getRutaPKG(String codParametro) throws SyncconException;

	public boolean encontroJobRunnable()
			throws SyncconException;

}
