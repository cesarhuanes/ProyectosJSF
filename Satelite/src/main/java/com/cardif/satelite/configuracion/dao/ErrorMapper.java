package com.cardif.satelite.configuracion.dao;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

public interface ErrorMapper {

  final String SELECT_ERROR = "SELECT * FROM ERROR";

  @Select(SELECT_ERROR)
  @ResultMap(value = "BaseResultMap")
  List<com.cardif.satelite.model.Error> selectError();
}