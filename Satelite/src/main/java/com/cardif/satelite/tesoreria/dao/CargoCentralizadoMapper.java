package com.cardif.satelite.tesoreria.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.satelite.CargoCentralizado;
import com.cardif.satelite.model.satelite.CargoCentralizadoExample;

public interface CargoCentralizadoMapper {

  final String OBTENER_CARGO_CENTALIZADO = "SELECT * FROM CARGO_CENTRALIZADO";

  @Select(OBTENER_CARGO_CENTALIZADO)
  @ResultMap(value = "BaseResultMap")
  ArrayList<CargoCentralizado> obtenerListaCargoCentralizado();

  final String SELECT_CARGO_CENTRALIZADO_POR_PK = "SELECT * FROM CARGO_CENTRALIZADO WHERE PK = #{pk}";

  @Select(SELECT_CARGO_CENTRALIZADO_POR_PK)
  @ResultMap(value = "BaseResultMap")
  CargoCentralizado obtenerComprobantesPorPk(@Param("pk") Long pk);

  final String SELECT_CC_COMPROBANTES_APROBADOS1 = "SELECT CC.PK,CC.FEC_EMISION,CC.FEC_RECEPCION_MP,CC.FEC_RECEPCION_CNTBL,CC.FEC_APROBACION_CMPTE,CC.FEC_REGISTRO_CNTBL,CC.FEC_ENTREGA_TSRIA,CC.FEC_VENC_PAGO,CC.FEC_PAGO,CC.RUC,CC.RAZON_SOCIAL,CC.TIPO_COMPROBANTE,CC.NRO_DOCUMENTO,CC.MONEDA,CC.IMPORTE_TOTAL,CC.INDICADOR_DE_IGV,CC.IMPORTE_DETRACCION,CC.CLASE,CC.SISTEMA,CASE WHEN CC.ESTADO = 'NPA' THEN 'No pagado' WHEN CC.ESTADO = 'PAD' THEN 'Pago adelantado' WHEN CC.ESTADO = 'PAG' THEN 'Pagado' WHEN CC.ESTADO = 'REC' THEN 'Rechazado' WHEN CC.ESTADO = 'REV' THEN 'Reversado' END AS ESTADO,CC.OBSERVACIONES"
      + " FROM" + " CARGO_CENTRALIZADO CC" + " WHERE" + " CC.ESTADO = #{filAprobEstado,jdbcType=VARCHAR}"
      + " AND CC.FEC_REGISTRO_CNTBL BETWEEN #{filAprobFechaRegistroContableDesde,jdbcType=DATE} AND #{filAprobFechaRegistroContableHasta,jdbcType=DATE}"
      + " AND CC.SISTEMA = ISNULL(#{filAprobSistema,jdbcType=VARCHAR},CC.SISTEMA)" + " AND CC.NRO_DOCUMENTO = ISNULL(#{filAprobNroComprobante,jdbcType=VARCHAR},CC.NRO_DOCUMENTO)"
      + " AND CC.RUC = ISNULL(#{filAprobRucDni,jdbcType=VARCHAR},CC.RUC)" + " AND CC.RAZON_SOCIAL = ISNULL(#{filAprobRazonSocial,jdbcType=VARCHAR},CC.RAZON_SOCIAL)"
      + " AND CC.MONEDA = ISNULL(#{filAprobMoneda,jdbcType=VARCHAR},CC.MONEDA)" + " AND CC.FEC_VENC_PAGO = #{filAprobFecVencimiento,jdbcType=DATE}"
      + " AND CC.FEC_EMISION = #{filAprobFecEmision,jdbcType=DATE}" + " AND CC.FEC_PAGO = #{filAprobFecPago,jdbcType=DATE}";

  final String SELECT_CC_COMPROBANTES_APROBADOS = "SELECT" + " PK = identity(int, 1, 1)" + " ,CASE WHEN CONVERT(VARCHAR(10),CC.FEC_EMISION,103) != '30/12/1899' THEN CC.FEC_EMISION END AS FEC_EMISION "
      + " ,CASE WHEN CONVERT(VARCHAR(10),CC.FEC_RECEPCION_MP,103) != '30/12/1899' THEN CC.FEC_RECEPCION_MP END AS FEC_RECEPCION_MP"
      + " ,CASE WHEN CONVERT(VARCHAR(10),CC.FEC_RECEPCION_CNTBL,103) != '30/12/1899' THEN CC.FEC_RECEPCION_CNTBL END AS FEC_RECEPCION_CNTBL"
      + " ,CASE WHEN CONVERT(VARCHAR(10),CC.FEC_APROBACION_CMPTE,103) != '30/12/1899' THEN CC.FEC_APROBACION_CMPTE END AS FEC_APROBACION_CMPTE"
      + " ,CASE WHEN CONVERT(VARCHAR(10),CC.FEC_REGISTRO_CNTBL,103) != '30/12/1899' THEN CC.FEC_REGISTRO_CNTBL END AS FEC_REGISTRO_CNTBL"
      + " ,CASE WHEN CONVERT(VARCHAR(10),CC.FEC_ENTREGA_TSRIA,103) != '30/12/1899' THEN CC.FEC_ENTREGA_TSRIA END AS FEC_ENTREGA_TSRIA"
      + " ,CASE WHEN CONVERT(VARCHAR(10),CC.FEC_VENC_PAGO,103) != '30/12/1899' THEN CC.FEC_VENC_PAGO END AS FEC_VENC_PAGO"
      + " ,CASE WHEN CONVERT(VARCHAR(10),CC.FEC_PAGO,103) != '30/12/1899' THEN CC.FEC_PAGO END AS FEC_PAGO" + " ,CC.RUC" + " ,CC.RAZON_SOCIAL" + " ,CC.TIPO_COMPROBANTE" + " ,CC.NRO_DOCUMENTO"
      + " ,CC.MONEDA" + " ,CC.IMPORTE_TOTAL" + " ,CC.INDICADOR_DE_IGV" + " ,CC.IMPORTE_DETRACCION" + " ,CC.CLASE" + " ,CC.SISTEMA"
      + " ,CASE WHEN CC.ESTADO = 'NPA' THEN 'No pagado' WHEN CC.ESTADO = 'PAD' THEN 'Pago adelantado' WHEN CC.ESTADO = 'PAG' THEN 'Pagado' WHEN CC.ESTADO = 'REC' THEN 'Rechazado' WHEN CC.ESTADO = 'REV' THEN 'Reversado' END AS ESTADO"
      + " ,CC.OBSERVACIONES" + " ,CC.FEC_MODIFICACION" + " INTO #TEMP_CARGO_CENTRALIZADO" + " FROM " + " CARGO_CENTRALIZADO CC" + " WHERE "
      + " CC.ESTADO = ISNULL(#{filAprobEstado,jdbcType=VARCHAR},CC.ESTADO)"
      // +" AND CC.FEC_REGISTRO_CNTBL BETWEEN ISNULL(#{filAprobFechaRegistroContableDesde,jdbcType=DATE},CONVERT(DATETIME,'01-01-1900',103)) AND
      // ISNULL(#{filAprobFechaRegistroContableHasta,jdbcType=DATE},CONVERT(DATETIME,'01-01-2050',103))"
      + " AND CC.FEC_REGISTRO_CNTBL BETWEEN ISNULL(#{filAprobFechaRegistroContableDesde,jdbcType=DATE},CC.FEC_REGISTRO_CNTBL) AND ISNULL(#{filAprobFechaRegistroContableHasta,jdbcType=DATE},CC.FEC_REGISTRO_CNTBL)"
      + " AND CC.SISTEMA = ISNULL(#{filAprobSistema,jdbcType=VARCHAR},CC.SISTEMA)" + " AND CC.NRO_DOCUMENTO = ISNULL(#{filAprobNroComprobante,jdbcType=VARCHAR},CC.NRO_DOCUMENTO)"
      + " AND CC.RUC = ISNULL(#{filAprobRucDni,jdbcType=VARCHAR},CC.RUC)" + " AND UPPER(CC.RAZON_SOCIAL) = ISNULL(#{filAprobRazonSocial,jdbcType=VARCHAR},CC.RAZON_SOCIAL)"
      + " AND CC.MONEDA = ISNULL(#{filAprobMoneda,jdbcType=VARCHAR},CC.MONEDA)" + " AND CC.FEC_VENC_PAGO = ISNULL(#{filAprobFecVencimiento,jdbcType=DATE},CC.FEC_VENC_PAGO)"
      + " AND CC.FEC_EMISION = ISNULL(#{filAprobFecEmision,jdbcType=DATE},CC.FEC_EMISION)" + " AND CC.FEC_PAGO = ISNULL(#{filAprobFecPago,jdbcType=DATE},CC.FEC_PAGO)" + " AND CC.ESTADO != 'REC'"
      + " AND CC.ESTADO != 'REV'"
      + " AND CC.FEC_CREACION BETWEEN ISNULL(#{filAprobFechaCargaDesde,jdbcType=TIMESTAMP},CC.FEC_CREACION) AND ISNULL(#{filAprobFechaCargaHasta,jdbcType=TIMESTAMP},CC.FEC_CREACION)"
      + " ORDER BY CC.RAZON_SOCIAL ASC, CC.NRO_DOCUMENTO ASC" + " SELECT * FROM #TEMP_CARGO_CENTRALIZADO" + " DROP TABLE #TEMP_CARGO_CENTRALIZADO";

  @Select(SELECT_CC_COMPROBANTES_APROBADOS)
  @ResultMap(value = "BaseResultMap")
  ArrayList<CargoCentralizado> obtenerComprobantesAprobados(@Param("filAprobFechaRegistroContableDesde") Date filAprobFechaRegistroContableDesde,
      @Param("filAprobFechaRegistroContableHasta") Date filAprobFechaRegistroContableHasta, @Param("filAprobSistema") String filAprobSistema,
      @Param("filAprobNroComprobante") String filAprobNroComprobante, @Param("filAprobRucDni") String filAprobRucDni, @Param("filAprobFecEmision") Date filAprobFecEmision,
      @Param("filAprobRazonSocial") String filAprobRazonSocial, @Param("filAprobMoneda") String filAprobMoneda, @Param("filAprobFecVencimiento") Date filAprobFecVencimiento,
      @Param("filAprobFechaCargaDesde") Date filAprobFechaCargaDesde, @Param("filAprobFechaCargaHasta") Date filAprobFechaCargasHasta, @Param("filAprobFecPago") Date filAprobFecPago,
      @Param("filAprobEstado") String filAprobEstado);

  final String SELECT_CC_COMPROBANTES_RECHAZADOS1 = "SELECT CC.PK,CC.FEC_EMISION,CC.FEC_RECEPCION_MP,CC.FEC_RECEPCION_CNTBL,CC.FEC_APROBACION_CMPTE,CC.FEC_REGISTRO_CNTBL,CC.FEC_ENTREGA_TSRIA,CC.FEC_VENC_PAGO,CC.FEC_PAGO,CC.RUC,CC.RAZON_SOCIAL,CC.TIPO_COMPROBANTE,CC.NRO_DOCUMENTO,CC.MONEDA,CC.IMPORTE_TOTAL,CC.INDICADOR_DE_IGV,CC.IMPORTE_DETRACCION,CC.CLASE,CC.SISTEMA,CASE WHEN CC.ESTADO = 'NPA' THEN 'No pagado' WHEN CC.ESTADO = 'PAD' THEN 'Pago adelantado' WHEN CC.ESTADO = 'PAG' THEN 'Pagado' WHEN CC.ESTADO = 'REC' THEN 'Rechazado' WHEN CC.ESTADO = 'REV' THEN 'Reversado' END AS ESTADO,CC.OBSERVACIONES"
      + " FROM" + " CARGO_CENTRALIZADO CC" + " WHERE" + " CC.ESTADO = 'REC'" + " AND CC.SISTEMA = ISNULL(#{filAprobSistema,jdbcType=VARCHAR},CC.SISTEMA)"
      + " AND CC.NRO_DOCUMENTO = ISNULL(#{filAprobNroComprobante,jdbcType=VARCHAR},CC.NRO_DOCUMENTO)" + " AND CC.RUC = ISNULL(#{filAprobRucDni,jdbcType=VARCHAR},CC.RUC)"
      + " AND CC.RAZON_SOCIAL = ISNULL(#{filAprobRazonSocial,jdbcType=VARCHAR},CC.RAZON_SOCIAL)" + " AND CC.FEC_EMISION = #{filAprobFecEmision,jdbcType=DATE}";

  final String SELECT_CC_COMPROBANTES_RECHAZADOS = "SELECT" + " CC.PK" + " ,CASE WHEN CONVERT(VARCHAR(10),CC.FEC_EMISION,103) != '30/12/1899' THEN CC.FEC_EMISION END AS FEC_EMISION"
      + " ,CASE WHEN CONVERT(VARCHAR(10),CC.FEC_RECEPCION_MP,103) != '30/12/1899' THEN CC.FEC_RECEPCION_MP END AS FEC_RECEPCION_MP"
      + " ,CASE WHEN CONVERT(VARCHAR(10),CC.FEC_RECEPCION_CNTBL,103) != '30/12/1899' THEN CC.FEC_RECEPCION_CNTBL END AS FEC_RECEPCION_CNTBL"
      + " ,CASE WHEN CONVERT(VARCHAR(10),CC.FEC_APROBACION_CMPTE,103) != '30/12/1899' THEN CC.FEC_APROBACION_CMPTE END AS FEC_APROBACION_CMPTE"
      + " ,CASE WHEN CONVERT(VARCHAR(10),CC.FEC_REGISTRO_CNTBL,103) != '30/12/1899' THEN CC.FEC_REGISTRO_CNTBL END AS FEC_REGISTRO_CNTBL"
      + " ,CASE WHEN CONVERT(VARCHAR(10),CC.FEC_ENTREGA_TSRIA,103) != '30/12/1899' THEN CC.FEC_ENTREGA_TSRIA END AS FEC_ENTREGA_TSRIA"
      + " ,CASE WHEN CONVERT(VARCHAR(10),CC.FEC_VENC_PAGO,103) != '30/12/1899' THEN CC.FEC_VENC_PAGO END AS FEC_VENC_PAGO"
      + " ,CASE WHEN CONVERT(VARCHAR(10),CC.FEC_PAGO,103) != '30/12/1899' THEN CC.FEC_PAGO END AS FEC_PAGO" + " ,CC.RUC" + " ,CC.RAZON_SOCIAL" + " ,CC.TIPO_COMPROBANTE" + " ,CC.NRO_DOCUMENTO"
      + " ,CC.MONEDA" + " ,CC.IMPORTE_TOTAL" + " ,CC.INDICADOR_DE_IGV" + " ,CC.IMPORTE_DETRACCION" + " ,CC.CLASE" + " ,CC.SISTEMA"
      + " ,CASE WHEN CC.ESTADO = 'NPA' THEN 'No pagado' WHEN CC.ESTADO = 'PAD' THEN 'Pago adelantado' WHEN CC.ESTADO = 'PAG' THEN 'Pagado' WHEN CC.ESTADO = 'REC' THEN 'Rechazado' WHEN CC.ESTADO = 'REV' THEN 'Reversado' END AS ESTADO"
      + " ,CC.OBSERVACIONES" + " FROM" + " CARGO_CENTRALIZADO CC" + " WHERE" + " CC.ESTADO = 'REC'" + " AND CC.SISTEMA = ISNULL(#{filRechazadoSistema,jdbcType=VARCHAR},CC.SISTEMA)"
      + " AND CC.NRO_DOCUMENTO = ISNULL(#{filRechazadoNroComprobante,jdbcType=VARCHAR},CC.NRO_DOCUMENTO)" + " AND CC.RUC = ISNULL(#{filRechazadoRucDni,jdbcType=VARCHAR},CC.RUC)"
      + " AND UPPER(CC.RAZON_SOCIAL) = ISNULL(#{filRechazadoRazonSocial,jdbcType=VARCHAR},CC.RAZON_SOCIAL)" + " AND CC.FEC_EMISION = ISNULL(#{filRechazadoFecEmision,jdbcType=VARCHAR},CC.FEC_EMISION)"
      + " AND CC.FEC_CREACION BETWEEN ISNULL(#{filRechazadoFechaCargaDesde,jdbcType=TIMESTAMP},CC.FEC_CREACION) AND ISNULL(#{filRechazadoFechaCargaHasta,jdbcType=TIMESTAMP},CC.FEC_CREACION)";

  @Select(SELECT_CC_COMPROBANTES_RECHAZADOS)
  @ResultMap(value = "BaseResultMap")
  ArrayList<CargoCentralizado> obtenerComprobantesRechazados(@Param("filRechazadoSistema") String filRechazadoSistema, @Param("filRechazadoNroComprobante") String filRechazadoNroComprobante,
      @Param("filRechazadoRucDni") String filRechazadoRucDni, @Param("filRechazadoFecEmision") Date filRechazadoFecEmision, @Param("filRechazadoRazonSocial") String filRechazadoRazonSocial,
      @Param("filRechazadoFechaCargaDesde") Date filRechazadoFechaCargaDesde, @Param("filRechazadoFechaCargaHasta") Date filRechazadoFechaCargaHasta);

  final String ACTUALIZA_COMPROBANTES_APROBADOS = "UPDATE CARGO_CENTRALIZADO" + " SET " + " FEC_ENTREGA_TSRIA = #{updateFecEntregaTesoreria,jdbcType=DATE},"
      + " FEC_RECEPCION_CNTBL = #{updateFecRecepcionContable,jdbcType=DATE}," + " FEC_MODIFICACION = #{updateFecModificacion,jdbcType=DATE},"
      + " USU_MODIFICACION = #{usuarioModificacion,jdbcType=VARCHAR}" + " WHERE NRO_DOCUMENTO = #{nroDocumento}";

  @Select(ACTUALIZA_COMPROBANTES_APROBADOS)
  @ResultMap(value = "BaseResultMap")
  CargoCentralizado actualizarComprobantesAprobados(@Param("updateFecEntregaTesoreria") Date updateFecEntregaTesoreria, @Param("updateFecRecepcionContable") Date updateFecRecepcionContable,
      @Param("updateFecModificacion") Date updateFecModificacion, @Param("usuarioModificacion") String usuarioModificacion, @Param("nroDocumento") String nroDocumento);

  final String ACTUALIZA_FECHA_ENTREGA_TESORERIA = "UPDATE CARGO_CENTRALIZADO" + " SET" + " FEC_ENTREGA_TSRIA = #{updateFecEntregaTesoreria,jdbcType=DATE}," + " FEC_RECEPCION_CNTBL = NULL,"
      + " FEC_MODIFICACION = #{updateFecModificacion,jdbcType=DATE}," + " USU_MODIFICACION = #{usuarioModificacion,jdbcType=VARCHAR}" + " WHERE NRO_DOCUMENTO = #{nroDocumento}";

  @Select(ACTUALIZA_FECHA_ENTREGA_TESORERIA)
  @ResultMap(value = "BaseResultMap")
  CargoCentralizado actualizarFecEntregaTesoreria(@Param("updateFecEntregaTesoreria") Date updateFecEntregaTesoreria, @Param("updateFecModificacion") Date updateFecModificacion,
      @Param("usuarioModificacion") String usuarioModificacion, @Param("nroDocumento") String nroDocumento);

  final String ACTUALIZA_FECHA_RECEPCION_CONTABLE = "UPDATE CARGO_CENTRALIZADO" + " SET" + " FEC_ENTREGA_TSRIA = NULL," + " FEC_RECEPCION_CNTBL = #{updateFecRecepcionContable,jdbcType=DATE},"
      + " FEC_MODIFICACION = #{updateFecModificacion,jdbcType=DATE}," + " USU_MODIFICACION = #{usuarioModificacion,jdbcType=VARCHAR}" + " WHERE NRO_DOCUMENTO = #{nroDocumento}";

  @Select(ACTUALIZA_FECHA_RECEPCION_CONTABLE)
  @ResultMap(value = "BaseResultMap")
  CargoCentralizado actualizarFechaRecepcionContable(@Param("updateFecRecepcionContable") Date updateFecRecepcionContable, @Param("updateFecModificacion") Date updateFecModificacion,
      @Param("usuarioModificacion") String usuarioModificacion, @Param("nroDocumento") String nroDocumento);

  final String ACTUALIZA_FECHA_NULL = "UPDATE CARGO_CENTRALIZADO" + " SET" + " FEC_ENTREGA_TSRIA = NULL," + " FEC_RECEPCION_CNTBL = NULL,"
      + " FEC_MODIFICACION = #{updateFecModificacion,jdbcType=DATE}," + " USU_MODIFICACION = #{usuarioModificacion,jdbcType=VARCHAR}" + " WHERE NRO_DOCUMENTO = #{nroDocumento}";

  @Select(ACTUALIZA_FECHA_RECEPCION_CONTABLE)
  @ResultMap(value = "BaseResultMap")
  CargoCentralizado actualizarFechaNull(@Param("updateFecModificacion") Date updateFecModificacion, @Param("usuarioModificacion") String usuarioModificacion,
      @Param("nroDocumento") String nroDocumento);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CARGO_CENTRALIZADO
   *
   * @mbggenerated Thu Jul 24 17:54:12 COT 2014
   */
  int countByExample(CargoCentralizadoExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CARGO_CENTRALIZADO
   *
   * @mbggenerated Thu Jul 24 17:54:12 COT 2014
   */
  int deleteByExample(CargoCentralizadoExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CARGO_CENTRALIZADO
   *
   * @mbggenerated Thu Jul 24 17:54:12 COT 2014
   */
  int deleteByPrimaryKey(Long pk);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CARGO_CENTRALIZADO
   *
   * @mbggenerated Thu Jul 24 17:54:12 COT 2014
   */
  int insert(CargoCentralizado record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CARGO_CENTRALIZADO
   *
   * @mbggenerated Thu Jul 24 17:54:12 COT 2014
   */
  int insertSelective(CargoCentralizado record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CARGO_CENTRALIZADO
   *
   * @mbggenerated Thu Jul 24 17:54:12 COT 2014
   */
  List<CargoCentralizado> selectByExample(CargoCentralizadoExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CARGO_CENTRALIZADO
   *
   * @mbggenerated Thu Jul 24 17:54:12 COT 2014
   */
  CargoCentralizado selectByPrimaryKey(Long pk);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CARGO_CENTRALIZADO
   *
   * @mbggenerated Thu Jul 24 17:54:12 COT 2014
   */
  int updateByExampleSelective(@Param("record") CargoCentralizado record, @Param("example") CargoCentralizadoExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CARGO_CENTRALIZADO
   *
   * @mbggenerated Thu Jul 24 17:54:12 COT 2014
   */
  int updateByExample(@Param("record") CargoCentralizado record, @Param("example") CargoCentralizadoExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CARGO_CENTRALIZADO
   *
   * @mbggenerated Thu Jul 24 17:54:12 COT 2014
   */
  int updateByPrimaryKeySelective(CargoCentralizado record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CARGO_CENTRALIZADO
   *
   * @mbggenerated Thu Jul 24 17:54:12 COT 2014
   */
  int updateByPrimaryKey(CargoCentralizado record);
}