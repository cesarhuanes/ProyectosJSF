package com.cardif.satelite.configuracion.dao;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.satelite.GenPais;

public interface GenPaisMapper {

  final String SELECT_PAIS = "SELECT * FROM GEN_PAIS order by NOMBRE_PAIS";

  @Select(SELECT_PAIS)
  @ResultMap(value = "BaseResultMap")
  List<GenPais> selectPais();

  GenPais selectByPrimaryKey(String codPais);

}
