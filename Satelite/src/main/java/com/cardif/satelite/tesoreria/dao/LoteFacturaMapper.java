package com.cardif.satelite.tesoreria.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.LoteFactura;

public interface LoteFacturaMapper {

  int insert(LoteFactura record);

  final String SELECT_LOTES_FACTURA = "SELECT * FROM LOTE_FACTURA WHERE "

      + "nro_lote=ISNULL(#{nroLote,jdbcType=VARCHAR},nro_lote)";

  @Select(SELECT_LOTES_FACTURA)
  @ResultMap("BaseResultMap")
  List<LoteFactura> getLotesFactura(@Param("nroLote") String nroLote);
}