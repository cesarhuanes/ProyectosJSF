package com.cardif.satelite.soat.directo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.soat.model.Departamento;

public interface DepartamentoDirectoMapper {

  final String SELECT_DEPA = "SELECT * FROM DEPARTAMENTO";

  @Select(SELECT_DEPA)
  @ResultMap(value = "BaseResultMap")
  List<Departamento> selectDepartamento();

  final String SELECT_DEPA_NOM = "SELECT * FROM DEPARTAMENTO WHERE NOMBRE_DEPARTAMENTO = #{nomDepartamento}";

  @Select(SELECT_DEPA_NOM)
  @ResultMap(value = "BaseResultMap")
  Departamento selectDepartamentoNom(@Param("nomDepartamento") String nomDepartamento);

  final String SELECT_DEPA_POR_COD = "SELECT * FROM DEPARTAMENTO  WHERE COD_DEPARTAMENTO = #{codDepartamento,jdbcType=VARCHAR}";

  @Select(SELECT_DEPA_POR_COD)
  @ResultMap(value = "BaseResultMap")
  Departamento selectDepartamentoPorCod(@Param("codDepartamento") String codDepartamento);

}