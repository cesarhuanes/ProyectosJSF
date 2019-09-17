package com.cardif.satelite.soat.directo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.soat.model.UsoVehiculo;

public interface UsoVehiculoDirectoMapper {

  final String SELECT_USO_VEHICULO = "SELECT * FROM USO_VEHICULO";

  final String SELECT_USO_VEHICULO_BY_COD = "SELECT * FROM USO_VEHICULO WHERE COD_USO = #{codUso}";

  @Select(SELECT_USO_VEHICULO)
  @ResultMap(value = "BaseResultMap")
  List<UsoVehiculo> selectUsoVehiculo();

  @Select(SELECT_USO_VEHICULO_BY_COD)
  @ResultMap(value = "BaseResultMap")
  List<UsoVehiculo> selectUsoVehiculoByCodUso(@Param("codUso") String codUso);

}