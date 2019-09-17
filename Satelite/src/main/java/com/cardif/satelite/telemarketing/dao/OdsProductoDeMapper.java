package com.cardif.satelite.telemarketing.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.datamart.OdsProductoDe;
import com.cardif.satelite.model.datamart.OdsProductoDeExample;
import com.cardif.satelite.telemarketing.bean.ReporteTLMK;

/**
 * @class ConsultaTelemarketingService
 * @usuario jhurtado
 * @fecha_creacion 31-01-2014
 * @ticket T022163
 * @descripcion: Obtener Lista de productos y reporte cliente
 * 
 */
public interface OdsProductoDeMapper {

  final String SELECT_ANOMES = "SELECT MAX(ANOMES) FROM ODS_POLIZA_HM";

  @Select(SELECT_ANOMES)
  String selectAnoMes();

  final String SELECT_PRODUCTO = "SELECT COD_PRODUCTO,DES_PRODUCTO FROM ODS_PRODUCTO_DE WHERE COD_PRODUCTO = #{codProducto}";

  @Select(SELECT_PRODUCTO)
  @ResultMap(value = "BaseResultMap")
  List<OdsProductoDe> selectProducto(@Param("codProducto") String codProducto);

  final String SELECT_REPORTE_CLIENTE = "SELECT " + " TRIM(PO.COD_PRODUCTO) COD_PRODUCTO " + " ,TRIM(PO.COD_SOCIO) COD_SOCIO " + " ,PR.DES_PRODUCTO PRODUCTO " + " ,PO.NUM_POLIZA NUM_POLIZA "
      + " ,TRIM(PE.COD_PERSONA) COD_PERSONA " + " ,PE.DOC_IDENTIDAD " + " ,PE.NBR_PERSONA AS NOMBRE_CLIENTE" + " ,PE.APELLIDOPATERNO " + " ,PE.APELLIDOMATERNO "
      + " ,PO.FEC_INIVIGENCIA FEC_INIVIGENCIA " + " , (select max(fec_finvigencia) from ods_poliza_hm where num_poliza=PO.NUM_POLIZA AND ANOMES=#{anomes,jdbcType=VARCHAR}) FEC_FINVIGENCIA "
      // + " ,PO.FEC_FINVIGENCIA FEC_FINVIGENCIA "
      + " ,CASE WHEN (select max(fec_finvigencia) from ods_poliza_hm where num_poliza=PO.NUM_POLIZA AND ANOMES=#{anomes,jdbcType=VARCHAR}) > TRUNC(SYSDATE) THEN "
      + "   ROUND((trunc(SYSDATE) - FEC_INIVIGENCIA),0) " + " ELSE "
      + "   ROUND(((select max(fec_finvigencia) from ods_poliza_hm where num_poliza=PO.NUM_POLIZA AND ANOMES=#{anomes,jdbcType=VARCHAR}) - FEC_INIVIGENCIA),0) " + " END  PERMANENCIA "
      // +
      // " ,CASE WHEN TO_DATE(PO.FEC_FINVIGENCIA,'DD/MM/YY') > SYSDATE THEN "
      // + " ROUND((SYSDATE - TO_DATE(PO.FEC_INIVIGENCIA,'DD/MM/YY')),0) "
      // + " ELSE "
      // +
      // " ROUND((TO_DATE(PO.FEC_FINVIGENCIA,'DD/MM/YY') - TO_DATE(PO.FEC_INIVIGENCIA,'DD/MM/YY')),0) "
      // + " END PERMANENCIA "
      + " ,PE.DIR_PERSONA " + " ,PE.DESC_DISTRITO " + " ,DESC_PROVINCIA " + " ,DESC_DEPARTAMENTO " + " ,PO.IMPORTEPRIMAEMITIDA IMPORTEPRIMAEMITIDA "
      + " ,CASE WHEN LENGTH(PE.TELEFONOCASA) > 5 AND  LENGTH(PE.TELEFONOCASA) < 10  THEN PE.TELEFONOCASA WHEN LENGTH(PE.TELEFONOCASA) < 6 OR LENGTH(PE.TELEFONOCASA) > 9  THEN concat('REGISTRO UN TELEFONO INCORRECTO: ',PE.TELEFONOCASA) END  TELEFONO "
      // + " ,'NINGUNO' OTROS_PRODUCTOS "
      + " , (select count(distinct pr.des_producto) " + "   from ODS_PRODUCTO_DE pr " + "   JOIN ods_poliza_hm hm5 " + "   on pr.cod_producto = hm5.cod_producto "
      + "   JOIN ods_polizarolpersona_mm p1 " + "   on hm5.cod_poliza     = p1.cod_poliza " + "   where hm5.anomes = #{anomes,jdbcType=VARCHAR}  " + "   and p1.cod_persona  = pp.cod_persona "
      + "   and p1.cod_poliza    <> po.cod_poliza " + "   and hm5.cod_producto <> po.cod_producto " + "   ) as OTROS_PRODUCTOS " + " ,TRIM(PO.COD_POLIZA) COD_POLIZA "
      + " ,(SELECT COUNT(1) FROM ODS_PAGO_MM PAG WHERE trim(PAG.COD_ESTADOCUOTA) = '11' AND PAG.NUM_POLIZA = TRIM(PO.NUM_POLIZA)) CUOTAS_IMPAGAS " + " FROM ODS_POLIZA_HM PO  "
      + " JOIN ODS_POLIZAROLPERSONA_MM PP ON PO.COD_POLIZA = PP.COD_POLIZA " + " JOIN ODS_PRODUCTO_DE PR ON PR.COD_PRODUCTO = PO.COD_PRODUCTO "
      + " JOIN ODS_PERSONANATURAL_MM PE ON PE.COD_PERSONA = PP.COD_PERSONA " + " JOIN ods_estadopoliza_de ED ON PO.COD_ESTADO = ED.COD_ESTADO "
      + " JOIN (select min(fec_inivigencia) fec_inivigencia1, NUM_POLIZA NUM_POLIZA1 " + " from ODS_POLIZA_HM "
      // +
      // " where fec_inivigencia BETWEEN TO_DATE(NVL(TO_CHAR(#{fechaIni,jdbcType=VARCHAR}),'01/01/1990'),'dd/mm/yy') AND
      // TO_DATE(NVL(TO_CHAR(#{fechaFin,jdbcType=VARCHAR}),'31/12/2050'),'dd/mm/yy') "
      + "  GROUP BY NUM_POLIZA) MPOL ON PO.NUM_POLIZA = MPOL.NUM_POLIZA1 AND MPOL.fec_inivigencia1=PO.fec_inivigencia " + " WHERE " + " PO.COD_PRODUCTO = #{codProducto,jdbcType=INTEGER} "
      + " AND PO.COD_SOCIO = #{codSocio,jdbcType=DECIMAL} " + " AND PP.COD_ROL = '112' " + " AND ED.DES_ESTADO = 'In force' " + " and ANOMES=#{anomes,jdbcType=VARCHAR} "
      + " AND FEC_INIVIGENCIA BETWEEN TO_DATE(NVL(TO_CHAR(#{fechaIni,jdbcType=VARCHAR}),'01/01/1990'),'dd/mm/yyyy') AND TO_DATE(NVL(TO_CHAR(#{fechaFin,jdbcType=VARCHAR}),'31/12/2050'),'dd/mm/yy') "
      + " AND (SELECT COUNT(1) FROM ODS_PAGO_MM PA WHERE trim(PA.COD_ESTADOCUOTA) = '11' AND PA.NUM_POLIZA = PO.NUM_POLIZA) BETWEEN #{cuotaDe,jdbcType=INTEGER} AND #{cuotaA,jdbcType=INTEGER} "
      + " AND ROWNUM<10000";

  // ORIGINAL
  /*
   * final String SELECT_REPORTE_CLIENTE = "SELECT TRIM(PO.COD_PRODUCTO) COD_PRODUCTO" + " ,TRIM(PO.COD_SOCIO) COD_SOCIO" +
   * " ,(SELECT PR.DES_PRODUCTO FROM ODS_PRODUCTO_DE PR WHERE PR.COD_PRODUCTO = PO.COD_PRODUCTO ) AS PRODUCTO" + " ,PO.NUM_POLIZA NUM_POLIZA" +
   * " ,TRIM((SELECT PE.COD_PERSONA FROM ODS_PERSONANATURAL_MM PE WHERE PE.COD_PERSONA = PP.COD_PERSONA)) COD_PERSONA" +
   * " ,(SELECT PE.DOC_IDENTIDAD FROM ODS_PERSONANATURAL_MM PE WHERE PE.COD_PERSONA = PP.COD_PERSONA) DOC_IDENTIDAD" +
   * " ,(SELECT PE.NBR_PERSONA FROM ODS_PERSONANATURAL_MM PE WHERE PE.COD_PERSONA = PP.COD_PERSONA)AS NOMBRE_CLIENTE" +
   * " ,(SELECT PE.APELLIDOPATERNO FROM ODS_PERSONANATURAL_MM PE WHERE PE.COD_PERSONA = PP.COD_PERSONA)AS APELLIDO_PATERNO" +
   * " ,(SELECT PE.APELLIDOMATERNO FROM ODS_PERSONANATURAL_MM PE WHERE PE.COD_PERSONA = PP.COD_PERSONA) AS APELLIDO_MATERNO" +
   * " ,PO.FEC_INIVIGENCIA FEC_INIVIGENCIA" + " ,PO.FEC_FINVIGENCIA FEC_FINVIGENCIA" +
   * " ,CASE WHEN TO_DATE(PO.FEC_FINVIGENCIA,'DD/MM/YY') > SYSDATE THEN" + " ROUND((SYSDATE - TO_DATE(PO.FEC_INIVIGENCIA,'DD/MM/YY')),0)" + " ELSE" +
   * " ROUND((TO_DATE(PO.FEC_FINVIGENCIA,'DD/MM/YY') - TO_DATE(PO.FEC_INIVIGENCIA,'DD/MM/YY')),0)" + " END  PERMANENCIA" +
   * " ,(SELECT PE.DIR_PERSONA FROM ODS_PERSONANATURAL_MM PE WHERE PE.COD_PERSONA = PP.COD_PERSONA) DIR_PERSONA" +
   * " ,(SELECT PE.DESC_DISTRITO FROM ODS_PERSONANATURAL_MM PE WHERE PE.COD_PERSONA = PP.COD_PERSONA) DESC_DISTRITO" +
   * " ,(SELECT PE.DESC_PROVINCIA FROM ODS_PERSONANATURAL_MM PE WHERE PE.COD_PERSONA = PP.COD_PERSONA) DESC_PROVINCIA" +
   * " ,(SELECT PE.DESC_DEPARTAMENTO FROM ODS_PERSONANATURAL_MM PE WHERE PE.COD_PERSONA = PP.COD_PERSONA) DESC_DEPARTAMENTO" +
   * " ,PO.IMPORTEPRIMAEMITIDA IMPORTEPRIMAEMITIDA" +
   * " ,(SELECT PE.TELEFONOCASA FROM ODS_PERSONANATURAL_MM PE WHERE PE.COD_PERSONA = PP.COD_PERSONA) TELEFONO" + " ,'NINGUNO' OTROS_PRODUCTOS" +
   * " ,TRIM(PO.COD_POLIZA) COD_POLIZA" +
   * " ,(SELECT COUNT(*) FROM ODS_PAGO_MM PAG WHERE trim(PAG.COD_ESTADOCUOTA) = '11' AND PAG.COD_POLIZA = TRIM(PO.COD_POLIZA)) CUOTAS_IMPAGAS" +
   * " FROM ODS_POLIZA_HM PO,ODS_POLIZAROLPERSONA_MM PP"
   * 
   * 
   * + " WHERE " + " PO.COD_POLIZA= PP.COD_POLIZA" + " AND PP.COD_ROL='112'" +
   * " AND (SELECT COUNT(*) FROM ods_estadopoliza_de ed WHERE ED.COD_ESTADO = PO.COD_ESTADO AND ED.DES_ESTADO='In force') > 0" +
   * " AND PO.COD_PRODUCTO = #{codProducto,jdbcType=INTEGER}" + " AND PO.COD_SOCIO = #{codSocio,jdbcType=DECIMAL}" +
   * " AND TO_DATE(FEC_INIVIGENCIA,'dd/mm/yy')" + " BETWEEN TO_DATE(NVL(TO_CHAR(#{fechaIni,jdbcType=VARCHAR}),'01/01/1990'),'dd/mm/yy') AND" +
   * " TO_DATE(NVL(TO_CHAR(#{fechaFin,jdbcType=VARCHAR}),'31/12/2050'),'dd/mm/yy')" +
   * " AND (SELECT COUNT(*) FROM ODS_PAGO_MM PA WHERE trim(PA.COD_ESTADOCUOTA) = '11' AND PA.COD_POLIZA = PO.COD_POLIZA) BETWEEN #{cuotaDe,jdbcType=INTEGER} AND #{cuotaA,jdbcType=INTEGER}"
   * +
   * " AND PO.fec_inivigencia = (select max(POL.fec_inivigencia) from ODS_POLIZA_HM POL where TO_DATE(POL.fec_inivigencia,'dd/mm/yy') BETWEEN TO_DATE(NVL(TO_CHAR(#{fechaIni,jdbcType=VARCHAR}),'01/01/1990'),'dd/mm/yy') AND"
   * + " TO_DATE(NVL(TO_CHAR(#{fechaFin,jdbcType=VARCHAR}),'31/12/2050'),'dd/mm/yy')" + " and POL.NUM_POLIZA= PO.NUM_POLIZA)";
   */

  @Select(SELECT_REPORTE_CLIENTE)
  @ResultMap(value = "ReporteTLMKResultMap")
  ArrayList<ReporteTLMK> selectReporteTLMKAdquisision(@Param("codProducto") Integer codProducto, @Param("codSocio") BigDecimal codSocio, @Param("fechaIni") String fechaIni,
      @Param("fechaFin") String fechaFin, @Param("cuotaDe") Integer cuotaDe, @Param("cuotaA") Integer cuotaA, @Param("anomes") String anomes);

  /*
   * final String SELECT_OTROS_PRODUCTO = "SELECT" + " PR.DES_PRODUCTO OTROS_PRODUCTOS" + " FROM ODS_PRODUCTO_DE PR" + " WHERE" +
   * " PR.COD_PRODUCTO IN (" + " SELECT DISTINCT HM.COD_PRODUCTO" + " FROM ODS_POLIZA_HM HM" + " WHERE HM.COD_POLIZA IN (" +
   * " SELECT DISTINCT P.COD_POLIZA" + " FROM ODS_POLIZAROLPERSONA_MM P" + " WHERE" + " P.COD_ROL = '112'" +
   * " AND P.COD_PERSONA = #{codPersona,jdbcType=VARCHAR}" + " AND P.COD_POLIZA <> #{codPoliza,jdbcType=DECIMAL})" +
   * " AND HM.COD_PRODUCTO <> #{codProducto,jdbcType=INTEGER})";
   */

  final String SELECT_OTROS_PRODUCTO = "SELECT" + " DISTINCT PR.DES_PRODUCTO AS OTROS_PRODUCTOS" + " FROM ODS_PRODUCTO_DE PR" + " JOIN ODS_POLIZA_HM HM ON PR.COD_PRODUCTO = HM.COD_PRODUCTO "
      + " JOIN ODS_POLIZAROLPERSONA_MM P ON HM.COD_POLIZA = P.COD_POLIZA " + " WHERE" + " HM.ANOMES= #{anomes,jdbcType=VARCHAR} " + " AND P.COD_ROL = '112'"
      + " AND P.COD_PERSONA = #{codPersona,jdbcType=VARCHAR}" + " AND P.COD_POLIZA <> #{codPoliza,jdbcType=DECIMAL}" + " AND HM.COD_PRODUCTO <> #{codProducto,jdbcType=INTEGER}";

  @Select(SELECT_OTROS_PRODUCTO)
  @ResultMap(value = "ReporteTLMKResultMap")
  ArrayList<ReporteTLMK> selectReporteTLMKOtrosProductos(@Param("codPersona") String codPersona, @Param("codPoliza") BigDecimal codPoliza, @Param("codProducto") Integer codProducto,
      @Param("anomes") String anomes);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table USER_DM_ODS.ODS_PRODUCTO_DE
   * 
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  int countByExample(OdsProductoDeExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table USER_DM_ODS.ODS_PRODUCTO_DE
   * 
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  int deleteByExample(OdsProductoDeExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table USER_DM_ODS.ODS_PRODUCTO_DE
   * 
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  int insert(OdsProductoDe record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table USER_DM_ODS.ODS_PRODUCTO_DE
   * 
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  int insertSelective(OdsProductoDe record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table USER_DM_ODS.ODS_PRODUCTO_DE
   * 
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  List<OdsProductoDe> selectByExample(OdsProductoDeExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table USER_DM_ODS.ODS_PRODUCTO_DE
   * 
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  int updateByExampleSelective(@Param("record") OdsProductoDe record, @Param("example") OdsProductoDeExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table USER_DM_ODS.ODS_PRODUCTO_DE
   * 
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  int updateByExample(@Param("record") OdsProductoDe record, @Param("example") OdsProductoDeExample example);
}