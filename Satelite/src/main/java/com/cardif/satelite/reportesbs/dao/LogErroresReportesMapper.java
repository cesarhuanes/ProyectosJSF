package com.cardif.satelite.reportesbs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.reportesbs.LogErroresReportes;

public interface LogErroresReportesMapper {

	int deleteByPrimaryKey(Short codLogErrores);

	int insert(LogErroresReportes record);

	int insertSelective(LogErroresReportes record);

	LogErroresReportes selectByPrimaryKey(Short codLogErrores);

	int updateByPrimaryKeySelective(LogErroresReportes record);

	int updateByPrimaryKey(LogErroresReportes record);
   
	final String SELECTLISTERRORBYID = "SELECT * FROM LOG_ERRORES_REPORTES WHERE COD_REPORTE = #{codError} ";

	@Select(SELECTLISTERRORBYID) 
	@ResultMap(value = "BaseResultMap")
	List<LogErroresReportes> selectListErroresReporteByPrimaryKey(
			@Param("codError") String codError);

	int deleteByCodReporte(long parseLong);

}