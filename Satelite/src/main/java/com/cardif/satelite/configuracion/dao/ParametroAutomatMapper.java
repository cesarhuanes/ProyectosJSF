package com.cardif.satelite.configuracion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.ParametroAutomat;

public interface ParametroAutomatMapper
{
	final String SELECT_LIST_PARAMETROS = "SELECT COD_PARAM, COD_VALOR, NOMBRE_VALOR, NUM_ORDEN FROM PARAMETRO_AUTOMAT " + 
				"WHERE (COD_PARAM = #{codParam,jdbcType=VARCHAR}) ORDER BY NUM_ORDEN, NOMBRE_VALOR ASC";
	
	final String SELECT_PARAMETRO = "SELECT COD_PARAM, COD_VALOR, NOMBRE_VALOR, NUM_ORDEN FROM PARAMETRO_AUTOMAT " +
				"WHERE (COD_PARAM = #{codParam,jdbcType=VARCHAR}) AND (COD_VALOR = #{codValor,jdbcType=VARCHAR}) ORDER BY NUM_ORDEN ASC";
	
	
	public int updateByPrimaryKeySelective(ParametroAutomat record);
	
	@Select(SELECT_LIST_PARAMETROS)
	@ResultMap("BaseResultMap")
	public List<ParametroAutomat> selectParametro(@Param("codParam") String codParam);
	
	@Select(SELECT_PARAMETRO)
	@ResultMap("BaseResultMap")
	public ParametroAutomat getParametro(@Param("codParam") String codParam, @Param("codValor") String codValor);
	
} //ParametroAutomatMapper
