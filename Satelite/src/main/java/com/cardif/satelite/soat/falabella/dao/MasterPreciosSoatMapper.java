package com.cardif.satelite.soat.falabella.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.MasterPreciosSoat;

public interface MasterPreciosSoatMapper {
  final String SELECT_PRECIO = "SELECT TOP 1 * FROM MASTER_PRECIOS_SOAT " + "WHERE COD_SOCIO = #{codSocio} " + "AND DEPARTAMENTO = #{departamento} " + "AND USO = #{uso} "
      + "AND CATEGORIA = #{categoria} " + "AND NRO_ASIENTOS IN (#{nroAsientos}, 999) " + "AND MARCA IN (#{marca}, '.TODOS') " + "AND MODELO IN (#{modelo}, '.TODOS') "
      + "ORDER BY MARCA DESC, MODELO DESC ";

  @Select(SELECT_PRECIO)
  @ResultMap(value = "BaseResultMap")
  MasterPreciosSoat getPrecio(@Param("codSocio") String codSocio, @Param("departamento") String departamento, @Param("uso") String uso, @Param("categoria") String categoria,
      @Param("nroAsientos") int nroAsientos, @Param("marca") String marca, @Param("modelo") String modelo);

  int insert(MasterPreciosSoat record);

}