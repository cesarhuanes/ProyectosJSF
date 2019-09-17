package com.cardif.satelite.siniestro.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.SiniDesempleo;
import com.cardif.satelite.model.SiniDesempleoExample;

public interface SiniDesempleoMapper {
  final String LISTA_SINI_DESEMPLEO = "SELECT * FROM dbo.SINI_DESEMPLEO" + " WHERE NRO_SINIESTRO=#{nroSiniestro} ";

  @Select(LISTA_SINI_DESEMPLEO)
  @ResultMap(value = "BaseResultMap")
  SiniDesempleo listar(@Param("nroSiniestro") String nroSiniestro);

  final String LISTA_SINI_DESEMPLEO_TODOS = "SELECT * FROM dbo.SINI_DESEMPLEO";

  @Select(LISTA_SINI_DESEMPLEO_TODOS)
  @ResultMap(value = "BaseResultMap")
  List<SiniDesempleo> listarT();

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_DESEMPLEO
   *
   * @mbggenerated Mon Jul 21 17:21:12 COT 2014
   */
  int countByExample(SiniDesempleoExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_DESEMPLEO
   *
   * @mbggenerated Mon Jul 21 17:21:12 COT 2014
   */
  int deleteByExample(SiniDesempleoExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_DESEMPLEO
   *
   * @mbggenerated Mon Jul 21 17:21:12 COT 2014
   */
  int deleteByPrimaryKey(String nroSiniestro);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_DESEMPLEO
   *
   * @mbggenerated Mon Jul 21 17:21:12 COT 2014
   */
  int insert(SiniDesempleo record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_DESEMPLEO
   *
   * @mbggenerated Mon Jul 21 17:21:12 COT 2014
   */
  int insertSelective(SiniDesempleo record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_DESEMPLEO
   *
   * @mbggenerated Mon Jul 21 17:21:12 COT 2014
   */
  List<SiniDesempleo> selectByExample(SiniDesempleoExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_DESEMPLEO
   *
   * @mbggenerated Mon Jul 21 17:21:12 COT 2014
   */
  SiniDesempleo selectByPrimaryKey(String nroSiniestro);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_DESEMPLEO
   *
   * @mbggenerated Mon Jul 21 17:21:12 COT 2014
   */
  int updateByExampleSelective(@Param("record") SiniDesempleo record, @Param("example") SiniDesempleoExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_DESEMPLEO
   *
   * @mbggenerated Mon Jul 21 17:21:12 COT 2014
   */
  int updateByExample(@Param("record") SiniDesempleo record, @Param("example") SiniDesempleoExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_DESEMPLEO
   *
   * @mbggenerated Mon Jul 21 17:21:12 COT 2014
   */
  int updateByPrimaryKeySelective(SiniDesempleo record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.SINI_DESEMPLEO
   *
   * @mbggenerated Mon Jul 21 17:21:12 COT 2014
   */
  int updateByPrimaryKey(SiniDesempleo record);
}