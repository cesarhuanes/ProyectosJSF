package com.cardif.satelite.reportesbs.service;

import java.math.BigDecimal;
import java.util.List;

import javax.faces.model.SelectItem;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.reportesbs.Empresa;
import com.cardif.satelite.model.reportesbs.FirmanteCargo;
import com.cardif.satelite.model.reportesbs.Parametro;
import com.cardif.satelite.model.reportesbs.ParametrosSBSModeloUnoDetalle;
import com.cardif.satelite.model.reportesbs.Producto;
import com.cardif.satelite.model.reportesbs.ReporteSBSModeloUnoDetalle;
import com.cardif.satelite.model.reportesbs.ReportesGeneradosSBSModeloUnoDetalle;
import com.cardif.satelite.model.reportesbs.Socio;
import com.cardif.satelite.reportesbs.bean.JournalBeanReportes;
import com.cardif.satelite.tesoreria.bean.JournalBean;

public interface ReporteSBSModeloUnoDetalleService {

	ReporteSBSModeloUnoDetalle registrarReporteSBSBModeloUno(
			String modeloRepore, String cargoFirmanteUno,
			String cargoFirmanteDos, String nomFirmanteUno,
			String nomFirmanteDos, String periodoAnio, String periodoTrimestre,
			BigDecimal tipoCambio, String estado, String usuario)
			throws SyncconException;

	List<ParametrosSBSModeloUnoDetalle> listaTramaParametros()
			throws SyncconException;

	Parametro selectParametroByPrimaryKey(String codParametro)
			throws SyncconException;

	Socio selectSocioByPrimaryKey(Long codSocio) throws SyncconException;

	Producto selectProductoByPrimaryKey(Long codProducto)
			throws SyncconException;

	Empresa selectEmpresaByPrimaryKey(Long codEmpresa) throws SyncconException;

	List<List<JournalBeanReportes>> consultarRegistros(String idProveedorFrom,
			String idProveedorTo, String fecPeriodoInicio, String fecPeriodoFin)
			throws Exception;

	List<SelectItem> selectListParametroByPrimaryKey(String codParametro)
			throws SyncconException;

	List<ReportesGeneradosSBSModeloUnoDetalle> selectListReporte()
			throws SyncconException;

	List<Parametro> selectParametroListByPrimaryKey(String string);

	List<ReportesGeneradosSBSModeloUnoDetalle> consultarReportes(
			String codigoReporte, String usuarioReporte,
			String tipoReporteParametro, String periodoAnio,
			String periodoTrimestre, String estadoReporte);

	int selectReporteListByParamAnterGuardar(String periodoAnio,
			String periodoTrimestre, String tipoReporte);

	List<FirmanteCargo> listafirmanetCargo(String codReporte);

	public List<Parametro> listarReporteModelo(String codParametro)
			throws SyncconException;

	ReportesGeneradosSBSModeloUnoDetalle consultarReportesParaErrores(
			String codigoReporte, String periodoAnio, String periodoTrimestre);
		
	void actualizarEstadoReporte(long longValue);

}
