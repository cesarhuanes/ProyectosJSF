package com.cardif.satelite.soat.falabella.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cardif.satelite.callcenter.bean.ConsultaRegPropietario;
import com.cardif.satelite.soat.model.Propietario;

public interface PropietarioMapper {

  final String SELECT_PROPIETARIO = "select * from propietario where rut_propietario=#{dni}";
  final String SELECT_PROPIETARIO2 = "select * from propietario where rut_propietario=#{dni}";

  final String UPDATE_BY_RUT = "update PROPIETARIO" + " set EMAIL = #{propietario.email}," + "MATERNO = #{propietario.materno}," + "NOMBRES = #{propietario.nombres},"
      + "RUT_PROPIETARIO = #{propietario.rutPropietario}," + "PATERNO = #{propietario.paterno}," + "VERSION = #{propietario.version}," + "FECHA_NACIMIENTO = #{propietario.fechaNacimiento},"
      + "ID_TIPO_DCTO = #{propietario.idTipoDcto}" + " where RUT_PROPIETARIO = #{dniAntiguo}";

  final String SELECT_MAX_ID = "select max(ID) from propietario";

  @Update(UPDATE_BY_RUT)
  int updateByRut(@Param("propietario") Propietario propietario, @Param("dniAntiguo") String dniAntiguo);

  @Select(SELECT_PROPIETARIO)
  @ResultMap(value = "BaseResultMapRegPropietario")
  ConsultaRegPropietario selectPropietario(@Param("dni") String dni);

  @Select(SELECT_PROPIETARIO2)
  @ResultMap(value = "BaseResultMap")
  Propietario obtener(@Param("dni") String dni);

  int insert(Propietario record);

  int updateByPrimaryKey(Propietario record);
}