package com.cardif.satelite.reportes.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.model.reportes.ReporteSBS;
import com.cardif.satelite.reportes.bean.RepConsultaSBSBean;

public interface ReporteSBSMapper
{
	final String SELECT_BY_TIPO_ANIO_PERIODO = "SELECT ID, UPPER(NOMBRE_ARCHIVO) AS NOMBRE_ARCHIVO, (SELECT SUBSTR(NOMBRE_VALOR, INSTR(NOMBRE_VALOR, '(') + 1, LENGTH(NOMBRE_VALOR) - INSTR(NOMBRE_VALOR, '(') - 1) AS TIPO_ANEXO " +
			"FROM PARAMETRO_AUTOMAT WHERE COD_PARAM='" + Constantes.COD_PARAM_SBS_TIPO_ANEXO + "' AND COD_VALOR=TIPO_REPORTE) AS TIPO_ANEXO, NRO_REGISTROS, TRUNC(FECHA_PROCESO) AS FECHA_PROCESO, ESTADO_PROCESO " +
			"FROM REPORTE_SBS WHERE (TIPO_REPORTE = #{codTipoAnexo,jdbcType=VARCHAR}) AND (ANIO_REPORTE = NVL(#{anio,jdbcType=NUMERIC}, ANIO_REPORTE)) AND (PERIODO_REPORTE = NVL(#{codPeriodo,jdbcType=NUMERIC}, PERIODO_REPORTE)) " + 
			"ORDER BY FECHA_PROCESO, ESTADO_PROCESO ASC";
	
	final String SELECT_ONE_BY_TIPO_ANIO_PERIODO = "SELECT ID, NOMBRE_ARCHIVO, ARCHIVO_ADJUNTO_EXPORTAR, TIPO_REPORTE, ANIO_REPORTE, PERIODO_REPORTE, TIPO_CAMBIO,	NRO_REGISTROS, FECHA_PROCESO, ESTADO_PROCESO " +
			"FROM REPORTE_SBS WHERE (TIPO_REPORTE = #{codTipoAnexo,jdbcType=VARCHAR}) AND (ANIO_REPORTE = #{anio,jdbcType=NUMERIC}) AND (PERIODO_REPORTE = #{codPeriodo,jdbcType=NUMERIC})";
	
	final String SELECT_ARCHIVO_ANDJUNTO_BY_PK = "SELECT ARCHIVO_ADJUNTO_EXPORTAR FROM REPORTE_SBS WHERE ID = #{pkReporteSBS,jdbcType=NUMERIC}";
	
	
	public ReporteSBS selectByPrimaryKey(Long id);
	
	public int deleteByPrimaryKey(Long id);
	
	public int insertSelective(ReporteSBS record);
	
	public int updateByPrimaryKeySelective(ReporteSBS record);
	
	
	@Select(SELECT_BY_TIPO_ANIO_PERIODO)
	@ResultMap("RepConsultaSBResultMap")
	public List<RepConsultaSBSBean> selectByTipoAnioPeriodo(@Param("codTipoAnexo") String codTipoAnexo, @Param("anio") Integer anio, @Param("codPeriodo") Long codPeriodo);
	
	@Select(SELECT_ONE_BY_TIPO_ANIO_PERIODO)
	@ResultMap("BaseResultMap")
	public List<ReporteSBS> selectOneByTipoAnioPeriodo(@Param("codTipoAnexo") String codTipoAnexo, @Param("anio") Integer anio, @Param("codPeriodo") Long codPeriodo);
//	public ReporteSBS selectOneByTipoAnioPeriodo(@Param("codTipoAnexo") String codTipoAnexo, @Param("anio") Integer anio, @Param("codPeriodo") Long codPeriodo);
	
	
} //ReporteSBSMapper
