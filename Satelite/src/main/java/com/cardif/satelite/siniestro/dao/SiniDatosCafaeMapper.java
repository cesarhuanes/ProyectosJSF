package com.cardif.satelite.siniestro.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.SiniDatosCafae;
import com.cardif.satelite.model.SiniDatosCafaeExample;

public interface SiniDatosCafaeMapper {
  final String LISTA_SINIDATOS_CAFAE = "SELECT * FROM dbo.SINI_DATOS_CAFAE" + " WHERE NRO_SINIESTRO=#{nroSiniestro} ";

  @Select(LISTA_SINIDATOS_CAFAE)
  @ResultMap(value = "BaseResultMap")
  SiniDatosCafae listar(@Param("nroSiniestro") String nroSiniestro);

  final String LISTA_SINIDATOS_CAFAE_TODOS = "SELECT * FROM dbo.SINI_DATOS_CAFAE";

  @Select(LISTA_SINIDATOS_CAFAE_TODOS)
  @ResultMap(value = "BaseResultMap")
  List<SiniDatosCafae> listarT();

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_DATOS_CAFAE
   *
   * @mbggenerated Thu Aug 07 18:15:19 COT 2014
   */
  int countByExample(SiniDatosCafaeExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_DATOS_CAFAE
   *
   * @mbggenerated Thu Aug 07 18:15:19 COT 2014
   */
  int deleteByExample(SiniDatosCafaeExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_DATOS_CAFAE
   *
   * @mbggenerated Thu Aug 07 18:15:19 COT 2014
   */
  int deleteByPrimaryKey(String nroSiniestro);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_DATOS_CAFAE
   *
   * @mbggenerated Thu Aug 07 18:15:19 COT 2014
   */
  int insert(SiniDatosCafae record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_DATOS_CAFAE
   *
   * @mbggenerated Thu Aug 07 18:15:19 COT 2014
   */
  int insertSelective(SiniDatosCafae record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_DATOS_CAFAE
   *
   * @mbggenerated Thu Aug 07 18:15:19 COT 2014
   */
  List<SiniDatosCafae> selectByExample(SiniDatosCafaeExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_DATOS_CAFAE
   *
   * @mbggenerated Thu Aug 07 18:15:19 COT 2014
   */
  SiniDatosCafae selectByPrimaryKey(String nroSiniestro);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_DATOS_CAFAE
   *
   * @mbggenerated Thu Aug 07 18:15:19 COT 2014
   */
  int updateByExampleSelective(@Param("record") SiniDatosCafae record, @Param("example") SiniDatosCafaeExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_DATOS_CAFAE
   *
   * @mbggenerated Thu Aug 07 18:15:19 COT 2014
   */
  int updateByExample(@Param("record") SiniDatosCafae record, @Param("example") SiniDatosCafaeExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_DATOS_CAFAE
   *
   * @mbggenerated Thu Aug 07 18:15:19 COT 2014
   */
  int updateByPrimaryKeySelective(SiniDatosCafae record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_DATOS_CAFAE
   *
   * @mbggenerated Thu Aug 07 18:15:19 COT 2014
   */
  int updateByPrimaryKey(SiniDatosCafae record);
}