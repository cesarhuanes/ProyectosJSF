package com.cardif.satelite.soat.ripley.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.soat.model.Propietario;

public interface PropietarioRipleyMapper {

  final String SELECT_PROPIETARIO = "select * from propietario where rut_propietario=#{dni}";

  final String SELECT_MAX_ID = "select max(ID) from propietario";

  @Select(SELECT_PROPIETARIO)
  @ResultMap(value = "BaseResultMap")
  Propietario selectPropietario(@Param("dni") String dni);

  @Select(SELECT_MAX_ID)
  @ResultMap(value = "BaseResultMap")
  Propietario SelectMaxIdPropietario();

  int insert(Propietario propietario);

  void updateByPrimaryKey(Propietario propietario);

}