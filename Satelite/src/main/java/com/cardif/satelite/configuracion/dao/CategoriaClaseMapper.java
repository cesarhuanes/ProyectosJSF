package com.cardif.satelite.configuracion.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.CategoriaClase;

public interface CategoriaClaseMapper
{
	final String SELECT_BY_NOMBRE = "SELECT COD_CATEGORIA_CLASE, DESCRIPCION_CATEGORIA_CLASE FROM CATEGORIA_CLASE WHERE DESCRIPCION_CATEGORIA_CLASE LIKE '%' + #{descripcionCategoriaClase,jdbcType=VARCHAR} + '%' AND RONUM=1";
	
	
	public List<CategoriaClase> selectAllCategoriaClases();
	
	public CategoriaClase selectByPrimaryKey(String codCategoriaClase);
	
	@Select(SELECT_BY_NOMBRE)
	@ResultMap("BaseResultMap")
	public CategoriaClase selectByDescripcion(@Param("descripcionCategoriaClase") String descripcionCategoriaClase);
	
} //CategoriaClaseMapper
