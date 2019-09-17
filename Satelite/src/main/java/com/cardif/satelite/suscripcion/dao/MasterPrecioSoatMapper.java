package com.cardif.satelite.suscripcion.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.satelite.MasterPrecioSoat;
import com.cardif.satelite.model.satelite.MasterPrecioSoatExample;
import com.cardif.satelite.suscripcion.bean.ConsultaFechaCargaMaster;
import com.cardif.satelite.suscripcion.bean.ConsultaMasterPrecio;

/**
 * @author maronlu
 */
public interface MasterPrecioSoatMapper {

  final String CONSULTA_MASTER =" SELECT COD_SOCIO,COD_MASTER,CANAL,MEDIO_PAGO, USO,CATEGORIA,DEPARTAMENTO,MARCA,MODELO,CASO,NRO_ASIENTOS, "
  		+ " PRIMA_TECNICA,PRIMA_PUBLICA,IGV,DE,FND_COMP,  COM_COBRANZA,COM_SOCIO,COM_BROKER_PORCENTAJE,FDO_TLMK,FDO_TLMK_PORCENTAJE,COM_BROKER,PRIMA_IGV FROM CONCI_MASTER_PRECIOS_SOAT "
  		+ " WHERE COD_SOCIO = NVL(#{socio,jdbcType=NVARCHAR}, COD_SOCIO) "
  		+ " AND DEPARTAMENTO =  NVL(#{departamento,jdbcType=NVARCHAR}, DEPARTAMENTO) "
  		+ " AND CATEGORIA  = NVL(#{categoria,jdbcType=NVARCHAR}, CATEGORIA) "
  		+ " AND USO  = NVL(#{uso,jdbcType=NVARCHAR}, USO) "
  		+ " AND CANAL  = NVL(#{canalVenta,jdbcType=NVARCHAR}, CANAL) ";

  final String DELETE_ULTIMA_CARGA = "delete from CONCI_MASTER_PRECIOS_SOAT " + " where cod_socio=#{codSocio,jdbcType=VARCHAR}" + " and fec_validez = "
      + " (select max(fec_validez) from CONCI_MASTER_PRECIOS_SOAT " + " where cod_socio=#{codSocio,jdbcType=VARCHAR})" + " and usu_creacion=#{usuCreacion,jdbcType=VARCHAR}";

  final String SELECT_MAX_VALIDEZ = "select max(fec_validez) from CONCI_MASTER_PRECIOS_SOAT where cod_socio= #{codSocio}";

  final String DELETE_X_FEC_VALIDEZ = "delete from CONCI_MASTER_PRECIOS_SOAT where cod_socio=#{codSocio} " + "and fec_validez = #{fecValidez}";

  final String SELECT_FECHA_CARGA = "select  fec_validez, count(*) as cantidad from dbo.CONCI_MASTER_PRECIOS_SOAT " + "where cod_socio=isnull(#{socio,jdbcType=VARCHAR},cod_socio) "
      + "group by fec_validez order by fec_validez desc";

  final String DELETE_MASTER = "delete CONCI_MASTER_PRECIOS_SOAT " + "where fec_validez = #{fecValidez} " + "and cod_socio=#{socio}";

  final String EXISTE_MASTER = "SELECT COUNT(*) FROM CONCI_MASTER_PRECIOS_SOAT " + "WHERE COD_SOCIO = #{socio,jdbcType=VARCHAR} " + "AND FEC_VALIDEZ < DATEADD(DAY, -1, DATEADD(MONTH, 1, "
      + "CONVERT(DATETIME, CAST(#{periodo,jdbcType=NUMERIC} AS VARCHAR(6)) + '01', 112)))";

  
  
  @Select(CONSULTA_MASTER)
  @ResultMap(value = "BaseDetalleMaster")
  List<ConsultaMasterPrecio> consultaDetalleMaster(@Param("socio") String socio,@Param("uso") String uso,@Param("departamento") String departamento,@Param("categoria")  String categoria, @Param("canalVenta") String canalVenta);


  @Select(SELECT_MAX_VALIDEZ)
  Date selectFechaMaxima(@Param("codSocio") String codSocio);

  @Delete(DELETE_X_FEC_VALIDEZ)
  int deleteByFecValidez(@Param("codSocio") String codSocio, @Param("fecValidez") Date fecValidez);

  @Delete(DELETE_ULTIMA_CARGA)
  int deleteByUsuFecValidez(@Param("codSocio") String codSocio, @Param("usuCreacion") String usuCreacion);

  @Select(SELECT_FECHA_CARGA)
  @ResultMap(value = "BaseResultMapFechaMaster")
  List<ConsultaFechaCargaMaster> selectFecCarga(@Param("socio") String socio);

  @Delete(DELETE_MASTER)
  int deleteMaster(@Param("socio") String socio, @Param("fecValidez") Date fecValidez);

  @Select(EXISTE_MASTER)
  int existeMaster(@Param("periodo") Integer periodo, @Param("socio") String socio);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CONCI_MASTER_PRECIOS_SOAT
   *
   * @mbggenerated Fri Jul 04 14:49:02 COT 2014
   */
  int countByExample(MasterPrecioSoatExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CONCI_MASTER_PRECIOS_SOAT
   *
   * @mbggenerated Fri Jul 04 14:49:02 COT 2014
   */
  int deleteByExample(MasterPrecioSoatExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CONCI_MASTER_PRECIOS_SOAT
   *
   * @mbggenerated Fri Jul 04 14:49:02 COT 2014
   */
  int insert(MasterPrecioSoat record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CONCI_MASTER_PRECIOS_SOAT
   *
   * @mbggenerated Fri Jul 04 14:49:02 COT 2014
   */
  int insertSelective(MasterPrecioSoat record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CONCI_MASTER_PRECIOS_SOAT
   *
   * @mbggenerated Fri Jul 04 14:49:02 COT 2014
   */
  List<MasterPrecioSoat> selectByExample(MasterPrecioSoatExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CONCI_MASTER_PRECIOS_SOAT
   *
   * @mbggenerated Fri Jul 04 14:49:02 COT 2014
   */
  int updateByExampleSelective(@Param("record") MasterPrecioSoat record, @Param("example") MasterPrecioSoatExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.CONCI_MASTER_PRECIOS_SOAT
   *
   * @mbggenerated Fri Jul 04 14:49:02 COT 2014
   */
  int updateByExample(@Param("record") MasterPrecioSoat record, @Param("example") MasterPrecioSoatExample example);


  
}