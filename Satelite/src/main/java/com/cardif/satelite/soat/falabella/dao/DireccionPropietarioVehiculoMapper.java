package com.cardif.satelite.soat.falabella.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cardif.satelite.soat.model.DireccionPropietarioVehiculo;

public interface DireccionPropietarioVehiculoMapper {

  final String SELECT_VEHICULO_PROPIETARIO_VEHICULO = "SELECT * FROM DIRECCION_PROPIETARIO_VEHICULO WHERE placa =#{placa}";

  final String UPDATE_BY_PLACA = "update DIRECCION_PROPIETARIO_VEHICULO " + " set VERSION = #{direccionPropietarioVehiculo.version}, " + "DIRECCION = #{direccionPropietarioVehiculo.direccion}"
      + "where PLACA = #{direccionPropietarioVehiculo.placa}";

  @Update(UPDATE_BY_PLACA)
  int updateByPlaca(@Param("direccionPropietarioVehiculo") DireccionPropietarioVehiculo direccionPropietarioVehiculo);

  @Select(SELECT_VEHICULO_PROPIETARIO_VEHICULO)
  @ResultMap(value = "BaseResultMap")
  DireccionPropietarioVehiculo getDPVbyPlaca(@Param("placa") String placa);

  int insert(DireccionPropietarioVehiculo record);

  int updateByPrimaryKey(DireccionPropietarioVehiculo record);
}