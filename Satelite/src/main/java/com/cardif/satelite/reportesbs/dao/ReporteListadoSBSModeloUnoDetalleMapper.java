package com.cardif.satelite.reportesbs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.reportesbs.Parametro;
import com.cardif.satelite.model.reportesbs.ReportesGeneradosSBSModeloUnoDetalle;

public interface ReporteListadoSBSModeloUnoDetalleMapper {

	final String selectReporte = "SELECT" + " COD_REPORTE," + " ESTADO,"
			+ " COD_TIPO_REPORTE," + " NOM_FIRMANTE_1," + " NOM_FIRMANTE_2,"
			+ " CARGO_FIRMANTE_1," + " CARGO_FIRMANTE_2," + " PERIODO_ANIO,"
			+ " PERIODO_TRIMESTRE," + " TIPO_CAMBIO," + " USUARIO_CREADOR,"
			+ " USUARIO_MODIFICADO," + " FECHA_CREADO" + " FROM REPORTES";
	final String SELECTREPORTEBYPARAMETRO = "SELECT"
			+ " R.COD_REPORTE,"
			+ " R.ESTADO,"
			+ " R.COD_TIPO_REPORTE,"
			+ " R.NOM_FIRMANTE_1,"
			+ " R.NOM_FIRMANTE_2,"
			+ " R.CARGO_FIRMANTE_1,"
			+ " R.CARGO_FIRMANTE_2,"
			+ " R.PERIODO_ANIO,"
			+ " R.PERIODO_TRIMESTRE,"
			+ " R.TIPO_CAMBIO,"
			+ " R.USUARIO_CREADOR,"
			+ " R.USUARIO_MODIFICADO,"
			+ " R.FECHA_CREADO"
			+ " FROM REPORTES R"
			+ " WHERE "
			+ " R.COD_REPORTE = DECODE(#{codigoReporte},'T',R.COD_REPORTE,#{codigoReporte})"
			+ " AND R.USUARIO_CREADOR = DECODE(#{usuarioReporte},'T',R.USUARIO_CREADOR,#{usuarioReporte})"
			+ " AND R.COD_TIPO_REPORTE = DECODE(#{tipoReporteParametro},'T',R.COD_TIPO_REPORTE,#{tipoReporteParametro})"
			+ " AND R.PERIODO_ANIO = DECODE(#{periodoAnio},'T',R.PERIODO_ANIO,#{periodoAnio})"
			+ " AND R.PERIODO_TRIMESTRE = DECODE(#{periodoTrimestre},'T',R.PERIODO_TRIMESTRE,#{periodoTrimestre})"
			+ " AND R.ESTADO = DECODE(#{estadoReporte},'T',R.ESTADO,#{estadoReporte})";

	final String SELECTREPORTEANTESGUARDAR = "SELECT"
			+ " R.COD_REPORTE"
			+ " FROM REPORTES R"
			+ " WHERE "
			+ " R.COD_TIPO_REPORTE = DECODE(#{tipoReporteParametro},'T',R.COD_TIPO_REPORTE,#{tipoReporteParametro})"
			+ " AND R.PERIODO_ANIO = DECODE(#{periodoAnio},'T',R.PERIODO_ANIO,#{periodoAnio})"
			+ " AND R.PERIODO_TRIMESTRE = DECODE(#{periodoTrimestre},'T',R.PERIODO_TRIMESTRE,#{periodoTrimestre})";

	final String SELECTLISTPARAMETRO = "SELECT * FROM PARAMETRO WHERE substr(cod_parametro,0,2) = #{codParametro} AND cod_valor NOT IN('X') ORDER BY NUM_ORDEN";

	@Select(selectReporte)
	@ResultMap(value = "BaseResultMap")
	List<ReportesGeneradosSBSModeloUnoDetalle> selectReporte();

	@Select(SELECTLISTPARAMETRO)
	@ResultMap(value = "BaseResultMapParametro")
	List<Parametro> selectListParametroByPrimaryKey(
			@Param("codParametro") String codParametro);

	@Select(SELECTREPORTEBYPARAMETRO)
	@ResultMap(value = "BaseResultMap")
	List<ReportesGeneradosSBSModeloUnoDetalle> selectListReportesByParam(
			@Param("codigoReporte") String codigoReporte,
			@Param("usuarioReporte") String usuarioReporte,
			@Param("tipoReporteParametro") String tipoReporteParametro,
			@Param("periodoAnio") String periodoAnio,
			@Param("periodoTrimestre") String periodoTrimestre,
			@Param("estadoReporte") String estadoReporte);

	@Select(SELECTREPORTEANTESGUARDAR)
	@ResultMap(value = "BaseResultMap")
	List<ReportesGeneradosSBSModeloUnoDetalle> selectReporteAntesGuardar(
			@Param("periodoAnio") String periodoAnio,
			@Param("periodoTrimestre") String periodoTrimestre,
			@Param("tipoReporteParametro") String tipoReporteParametro);

	@Select(SELECTREPORTEANTESGUARDAR)
	@ResultMap(value = "BaseResultMap")
	List<ReportesGeneradosSBSModeloUnoDetalle> consultarReportesParaErrores(
			@Param("tipoReporteParametro") String tipoReporteParametro,   
			@Param("periodoAnio") String periodoAnio, 
			@Param("periodoTrimestre") String periodoTrimestre);

	
	final String PROCESAR_TRAMA = "UPDATE REPORTES SET ESTADO=1502 WHERE  COD_REPORTE=#{codigoReporte}";
	
		
	@Select(PROCESAR_TRAMA)
	@ResultMap(value = "BaseResultMap")
	void actualizarEstadoReporte(@Param("codigoReporte")long codigoReporte);

}
