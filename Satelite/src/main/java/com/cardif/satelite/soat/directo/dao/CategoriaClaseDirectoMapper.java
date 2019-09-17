package com.cardif.satelite.soat.directo.dao;

import java.util.List;

import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.soat.model.CategoriaClase;

public interface CategoriaClaseDirectoMapper {

  final String SELECT_CATEGORIA = "SELECT * FROM CATEGORIA_CLASE ORDER BY DESCRIPCION_CATEGORIA_CLASE";

  @Select(SELECT_CATEGORIA)
  @ResultMap(value = "BaseResultMap")
  List<CategoriaClase> selectCategoria();

}