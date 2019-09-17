package com.cardif.satelite.reportesbs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.reportesbs.LogError;

public interface LogErrorMapper {

	public final String SELECT_LOG_ERROR = "SELECT  COD_LOG_ERROR,"
			+ " COD_PROCESO_ARCHIVO,"
			+ " NVL(SOCIO,'-') AS SOCIO,"
			+ " REPLACE(NVL(ASEGURADO,'-'),',','') AS ASEGURADO,"
			+ " NVL(PRODUCTO,'-') AS PRODUCTO,"
			+ " REPLACE(DESCRIPCION,',','') AS DESCRIPCION,"
			+ " NVL(BO_PLANILLA,'-') AS BO_PLANILLA,"
			+ " NVL(NUM_POLIZA_SINIESTRO,'-') AS NUM_POLIZA_SINIESTRO,"
			+ " USU_CREACION,"
			+ " to_char(FEC_CREACION,'dd-MM-yyyy') AS FEC_CREACION FROM   LOG_ERROR_PROC_ARCHIVO  WHERE COD_PROCESO_ARCHIVO=#{codProceso}"
			+ " order by 5 asc";

	@Select(SELECT_LOG_ERROR)
	@ResultMap(value = "BaseResultMap")
	List<LogError> getlistaError(@Param("codProceso") Long codProceso);

}
