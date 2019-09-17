package com.cardif.satelite.soat.falabella.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.soat.model.Provincia;

public interface ProvinciaMapper {

  final String SELECT_PROV = "SELECT * FROM PROVINCIA WHERE DEPARTAMENTO = #{codDepartamento}";

  @Select(SELECT_PROV)
  @ResultMap(value = "BaseResultMap")
  List<Provincia> selectProvincia(@Param("codDepartamento") String codDepartamento);

  final String SELECT_PROV_NOM = "SELECT * FROM PROVINCIA WHERE NOMBRE_PROVINCIA = #{nomProvincia} AND DEPARTAMENTO = #{codDepartamento}";

  @Select(SELECT_PROV_NOM)
  @ResultMap(value = "BaseResultMap")
  Provincia selectProvinciaNom(@Param("nomProvincia") String nomProvincia, @Param("codDepartamento") String codDepartamento);

  int insert(Provincia record);

  Provincia selectByPrimaryKey(String codProvincia);

}