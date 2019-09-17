package com.cardif.satelite.suscripcion.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.TstVentas;
import com.cardif.satelite.model.TstVentasExample;
import com.cardif.satelite.suscripcion.bean.ConsultaTstVentas;

public interface TstVentasMapper {

  final String SELECT_TST_VENTAS_DESC = "select TOP 100 tv.pk, tv.correlativoregistro, tv.fechaemision, tv.tipocomprobante, " + "(select nom_valor from parametro where cod_param = '005' "
      + "and tip_param = 'D' and cod_valor = isnull(convert(numeric(18, 0), tv.TipoComprobante), " + "tv.TipoComprobante)) as tipocomprobantedesc, "
      + "tv.nroserie, tv.correlativoserie, tv.tipodoccliente, " + "(select nom_valor from parametro where cod_param = '006' "
      + "and tip_param = 'D' and cod_valor = isnull(convert(varchar(250), tv.TipoDocCliente), " + "tv.TipoDocCliente)) as tipodocclientedesc, "
      + "tv.numerodoccliente, tv.nombresrazonsocial, tv.importetotal, " + "tv.fechaemisionref, tv.tipocomprobanteref, tv.seriecomprobanteref, "
      + "tv.seriecomprobanteref, tv.correlativoserieorig, tv.numeropoliza, " + "tv.producto from tst_ventas tv "
      + "WHERE tv.FechaEmision BETWEEN #{fechaDesde,jdbcType=VARCHAR} AND #{fechaHasta,jdbcType=VARCHAR} "
      + "AND tv.CorrelativoRegistro = isnull(#{correlativo,jdbcType=VARCHAR},tv.CorrelativoRegistro) " + "AND tv.TipoDocCliente = isnull(#{tipDocumento,jdbcType=VARCHAR},tv.TipoDocCliente) "
      + "AND tv.NumeroDocCliente = isnull(#{numDocumento,jdbcType=VARCHAR},tv.NumeroDocCliente) " + "AND tv.NumeroPoliza LIKE isnull(#{poliza,jdbcType=VARCHAR},tv.NumeroPoliza) "
      + "AND UPPER(tv.Producto) LIKE '%' + isnull(UPPER(#{producto,jdbcType=VARCHAR}),'') + '%' " + "AND tv.TipoComprobante = isnull(#{tipComprobante,jdbcType=VARCHAR},tv.TipoComprobante) "
      + "AND tv.NroSerie = isnull(#{nroSerie,jdbcType=VARCHAR},tv.NroSerie) " + "AND tv.CorrelativoSerie = isnull(#{correlativoSerie,jdbcType=VARCHAR},tv.CorrelativoSerie) "
      + "ORDER BY tv.correlativoregistro DESC";

  final String SELECT_TST_VENTAS_NO_REF_DESC = "select tv.pk, tv.correlativoregistro, tv.fechaemision, tv.tipocomprobante, " + "(select nom_valor from parametro where cod_param = '005' "
      + "and tip_param = 'D' and cod_valor = tv.TipoComprobante) as tipocomprobantedesc, " + "tv.nroserie, tv.correlativoserie, tv.tipodoccliente, "
      + "(select nom_valor from parametro where cod_param = '006' " + "and tip_param = 'D' and cod_valor = tv.TipoDocCliente) as tipodocclientedesc, "
      + "tv.numerodoccliente, tv.nombresrazonsocial, tv.importetotal, " + "tv.fechaemisionref, tv.tipocomprobanteref, tv.seriecomprobanteref, "
      + "tv.seriecomprobanteref, tv.correlativoserieorig, tv.numeropoliza, tv.producto " + "from tst_ventas tv "
      + "where tv.FechaEmision BETWEEN #{fechaDesde,jdbcType=VARCHAR} AND #{fechaHasta,jdbcType=VARCHAR} "
      + "AND tv.CorrelativoRegistro = isnull(#{correlativo,jdbcType=VARCHAR},tv.CorrelativoRegistro) " + "AND tv.TipoDocCliente = isnull(#{tipDocumento,jdbcType=VARCHAR},tv.TipoDocCliente) "
      + "AND tv.NumeroDocCliente = isnull(#{numDocumento,jdbcType=VARCHAR},tv.NumeroDocCliente) "
      // +
      // "AND tv.NumeroPoliza LIKE isnull(#{poliza,jdbcType=VARCHAR},tv.NumeroPoliza) "
      + "AND ISNULL(tv.NumeroPoliza, '**') = isnull(#{poliza,jdbcType=VARCHAR}, ISNULL(tv.NumeroPoliza,'**')) "
      + "AND UPPER(tv.Producto) LIKE '%' + isnull(UPPER(#{producto,jdbcType=VARCHAR}),'') + '%' " + "AND tv.TipoComprobante <> 13 "
      + "AND (tv.FechaEmisionRef is null or tv.TipoComprobanteRef is null or tv.SerieComprobanteRef is null or tv.CorrelativoSerieOrig is null) "
      + "AND tv.TipoComprobante = isnull(#{tipComprobante,jdbcType=VARCHAR},tv.TipoComprobante) " + "AND tv.NroSerie = isnull(#{nroSerie,jdbcType=VARCHAR},tv.NroSerie) "
      + "AND tv.CorrelativoSerie = isnull(#{correlativoSerie,jdbcType=VARCHAR},tv.CorrelativoSerie) " + "ORDER BY tv.correlativoregistro DESC";

  final String SELECT_TST_VENTAS_COR = "select tv.pk, tv.correlativoregistro, tv.fechaemision, tv.tipocomprobante, " + "(select nom_valor from parametro where cod_param = '005' "
      + "and tip_param = 'D' and cod_valor = tv.TipoComprobante) as tipocomprobantedesc, " + "tv.nroserie, tv.correlativoserie, tv.tipodoccliente, "
      + "(select nom_valor from parametro where cod_param = '006' " + "and tip_param = 'D' and cod_valor = tv.TipoDocCliente) as tipodocclientedesc, "
      + "tv.numerodoccliente, tv.nombresrazonsocial, tv.importetotal, " + "tv.fechaemisionref, tv.tipocomprobanteref, tv.seriecomprobanteref, "
      + "tv.seriecomprobanteref, tv.correlativoserieorig, tv.numeropoliza, tv.producto " + "from tst_ventas tv " + "where tv.CorrelativoRegistro = #{correlativo,jdbcType=VARCHAR} ";

  @Select(SELECT_TST_VENTAS_COR)
  @ResultMap(value = "TstVentasResultMap")
  ArrayList<ConsultaTstVentas> selectTstVentasCorrela(@Param("correlativo") String correlativo);

  @Select(SELECT_TST_VENTAS_DESC)
  @ResultMap(value = "TstVentasResultMap")
  ArrayList<ConsultaTstVentas> selectTstVentasDesc(@Param("correlativo") String correlativo, @Param("tipComprobante") String tipComprobante, @Param("producto") String producto,
      @Param("poliza") String poliza, @Param("nroSerie") String nroSerie, @Param("correlativoSerie") String correlativoSerie, @Param("tipDocumento") String tipDocumento,
      @Param("numDocumento") String numDocumento, @Param("fechaDesde") String fechaDesde, @Param("fechaHasta") String fechaHasta);

  @Select(SELECT_TST_VENTAS_NO_REF_DESC)
  @ResultMap(value = "TstVentasResultMap")
  ArrayList<ConsultaTstVentas> selectTstVentasNoRefDesc(@Param("correlativo") String correlativo, @Param("tipComprobante") String tipComprobante, @Param("producto") String producto,
      @Param("poliza") String poliza, @Param("nroSerie") String nroSerie, @Param("correlativoSerie") String correlativoSerie, @Param("tipDocumento") String tipDocumento,
      @Param("numDocumento") String numDocumento, @Param("fechaDesde") String fechaDesde, @Param("fechaHasta") String fechaHasta);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.TST_VENTAS
   *
   * @mbggenerated Thu Aug 08 17:52:27 COT 2013
   */
  int countByExample(TstVentasExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.TST_VENTAS
   *
   * @mbggenerated Thu Aug 08 17:52:27 COT 2013
   */
  int deleteByExample(TstVentasExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.TST_VENTAS
   *
   * @mbggenerated Thu Aug 08 17:52:27 COT 2013
   */
  int deleteByPrimaryKey(Long pk);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.TST_VENTAS
   *
   * @mbggenerated Thu Aug 08 17:52:27 COT 2013
   */
  int insert(TstVentas record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.TST_VENTAS
   *
   * @mbggenerated Thu Aug 08 17:52:27 COT 2013
   */
  int insertSelective(TstVentas record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.TST_VENTAS
   *
   * @mbggenerated Thu Aug 08 17:52:27 COT 2013
   */
  List<TstVentas> selectByExample(TstVentasExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.TST_VENTAS
   *
   * @mbggenerated Thu Aug 08 17:52:27 COT 2013
   */
  TstVentas selectByPrimaryKey(Long pk);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.TST_VENTAS
   *
   * @mbggenerated Thu Aug 08 17:52:27 COT 2013
   */
  int updateByExampleSelective(@Param("record") TstVentas record, @Param("example") TstVentasExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.TST_VENTAS
   *
   * @mbggenerated Thu Aug 08 17:52:27 COT 2013
   */
  int updateByExample(@Param("record") TstVentas record, @Param("example") TstVentasExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.TST_VENTAS
   *
   * @mbggenerated Thu Aug 08 17:52:27 COT 2013
   */
  int updateByPrimaryKeySelective(TstVentas record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.TST_VENTAS
   *
   * @mbggenerated Thu Aug 08 17:52:27 COT 2013
   */
  int updateByPrimaryKey(TstVentas record);

  int updateByPrimaryKeySelectiveInt(TstVentas record);
}