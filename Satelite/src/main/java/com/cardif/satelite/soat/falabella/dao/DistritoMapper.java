package com.cardif.satelite.soat.falabella.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.soat.model.Distrito;

public interface DistritoMapper {

  final String SELECT_DIST = "SELECT * FROM DISTRITO " + "WHERE PROVINCIA = #{codProvincia}";

  @Select(SELECT_DIST)
  @ResultMap(value = "BaseResultMap")
  List<Distrito> selectDistrito(@Param("codProvincia") String codProvincia);

  final String SELECT_DIST_NOM = "SELECT * FROM DISTRITO WHERE NOMBRE_DISTRITO = #{nomDistrito}  AND PROVINCIA = #{codProvincia} ";

  @Select(SELECT_DIST_NOM)
  @ResultMap(value = "BaseResultMap")
  Distrito selectDistritoNom(@Param("nomDistrito") String nomDistrito, @Param("codProvincia") String codProvincia);

  int insert(Distrito record);

  Distrito selectByPrimaryKey(String codDistrito);

  int updateByPrimaryKey(Distrito record);
}