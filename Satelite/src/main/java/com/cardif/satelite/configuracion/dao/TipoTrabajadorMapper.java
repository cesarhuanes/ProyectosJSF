package com.cardif.satelite.configuracion.dao;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.satelite.TipoTrabajador;

public interface TipoTrabajadorMapper {

  final String SELECT_TIPO_TRABAJADOR = "SELECT * FROM TIPO_TRABAJADOR ORDER BY DESCRIPCION";

  @Select(SELECT_TIPO_TRABAJADOR)
  @ResultMap(value = "BaseResultMap")
  List<TipoTrabajador> selectTipoTrabajador();

}
