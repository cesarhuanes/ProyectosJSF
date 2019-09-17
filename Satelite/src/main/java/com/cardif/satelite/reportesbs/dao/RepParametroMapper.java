package com.cardif.satelite.reportesbs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.reportesbs.Parametro;

public interface RepParametroMapper {
	final String SELECT_TIPO_SEGURO = "SELECT * FROM  PARAMETRO WHERE SUBSTR(COD_PARAMETRO,1,2)=#{codParametro} AND COD_PARAMETRO <>#{codParametro} AND ESTADO=1 order by DESCRIPCION_VALOR asc";

	@Select(SELECT_TIPO_SEGURO)
	@ResultMap(value = "BaseResultMap")
	List<Parametro> getListaParametros(
			@Param("codParametro") String codParametro);

	final String SELECT_RUTA = "SELECT DESCRIPCION_VALOR FROM  PARAMETRO where COD_PARAMETRO=#{codParametro}  order by DESCRIPCION_VALOR asc";

	@Select(SELECT_RUTA)
	@ResultMap(value = "BaseResultMap")
	List<Parametro> getListaRuta(@Param("codParametro") String codParametro);

}
