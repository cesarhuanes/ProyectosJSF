package com.cardif.satelite.tesoreria.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cardif.satelite.model.LoteCabecera;

public interface LoteCabeceraMapper {

  int insert(LoteCabecera record);

  final String SELECT_MAX_SECUENCIAL = " SELECT ISNULL(MAX(secuencial),0) FROM LOTE_CABECERA WHERE "

      + "tipo_pago=#{tipoPago,jdbcType=VARCHAR} AND banco=#{banco,jdbcType=VARCHAR} "

      + "AND moneda=#{moneda,jdbcType=VARCHAR}";

  @Select(SELECT_MAX_SECUENCIAL)
  int selectMaxSecuencial(@Param("tipoPago") String tipoPago, @Param("banco") String banco, @Param("moneda") String moneda);

  final String SELECT_LOTES_CABECERA = "SELECT * FROM LOTE_CABECERA WHERE "

      + " fec_creacion>=DATEADD(dd, 0, DATEDIFF(dd, 0, ISNULL(#{fec1,jdbcType=DATE},fec_creacion) ))  "

      + "AND fec_creacion< DATEADD(dd, 1, DATEDIFF(dd, 0, ISNULL(#{fec2,jdbcType=DATE},fec_creacion) )) "

      + "AND tipo_pago=ISNULL(#{tipoPago,jdbcType=VARCHAR},tipo_pago) "

      + "AND nro_lote=ISNULL(#{nroLote,jdbcType=VARCHAR},nro_lote)";

  @Select(SELECT_LOTES_CABECERA)
  @ResultMap("BaseResultMap")
  List<LoteCabecera> getLotesCabecera(@Param("nroLote") String nroLote, @Param("tipoPago") String tipoPago, @Param("fec1") Date fec1, @Param("fec2") Date fec2);

  final String UPDATE_FECHA_PROCESO = "UPDATE LOTE_CABECERA SET fec_proceso=#{fechaProceso,jdbcType=DATE} "

      + "WHERE nro_Lote=#{nroLote,jdbcType=VARCHAR}";

  @Update(UPDATE_FECHA_PROCESO)
  void updateFechaProceso(@Param("nroLote") String nroLote, @Param("fechaProceso") Date fechaProceso);

}