package com.cardif.satelite.reportes.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.reportes.SBSPeriodo;

public interface SBSPeriodoMapper
{
	final String SELECT_BY_TIPO_ANEXO = "SELECT ID, NOMBRE_PERIODO, TIPO_ANEXO, TRIMESTRE, NUM_ORDEN FROM SBS_PERIODO " +
			"WHERE TIPO_ANEXO=#{tipoAnexo,jdbcType=VARCHAR} ORDER BY NUM_ORDEN ASC";
	
	
	public SBSPeriodo selectByPrimaryKey(Long id);
	
	@Select(SELECT_BY_TIPO_ANEXO)
	@ResultMap("BaseResultMap")
	public List<SBSPeriodo> selectByTipoAnexo(@Param("tipoAnexo") String tipoAnexo);
	
} //SBSPeriodoMapper
