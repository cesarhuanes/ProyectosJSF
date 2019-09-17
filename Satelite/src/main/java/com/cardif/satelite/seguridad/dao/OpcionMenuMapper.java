package com.cardif.satelite.seguridad.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.OpcionMenu;
import com.cardif.satelite.model.OpcionMenuExample;

public interface OpcionMenuMapper {

  final String SELECT_OPCIONES = "SELECT * FROM OPCION_MENU ORDER BY COD_PADRE ASC";

  final String SELECT_OPC_PERFIL = "SELECT a.* " + "FROM OPCION_MENU a, OPCION_MENU_PERFIL b " + "WHERE a.COD_OPCION = b.COD_OPCION " + "AND b.cod_perfil = #{perfil} " + "AND a.habilitado = 1 "
      + "AND b.habilitado = 1 " + "ORDER BY a.nivel, a.orden";

  @Select(SELECT_OPCIONES)
  @ResultMap(value = "BaseResultMap")
  List<OpcionMenu> selectOpciones();

  @Select(SELECT_OPC_PERFIL)
  @ResultMap(value = "BaseResultMap")
  List<OpcionMenu> selectByPerfil(@Param("perfil") Long perfil);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.OPCION_MENU
   *
   * @mbggenerated Tue Jul 30 16:52:02 COT 2013
   */
  int countByExample(OpcionMenuExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.OPCION_MENU
   *
   * @mbggenerated Tue Jul 30 16:52:02 COT 2013
   */
  int deleteByExample(OpcionMenuExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.OPCION_MENU
   *
   * @mbggenerated Tue Jul 30 16:52:02 COT 2013
   */
  int deleteByPrimaryKey(Long codOpcion);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.OPCION_MENU
   *
   * @mbggenerated Tue Jul 30 16:52:02 COT 2013
   */
  int insert(OpcionMenu record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.OPCION_MENU
   *
   * @mbggenerated Tue Jul 30 16:52:02 COT 2013
   */
  int insertSelective(OpcionMenu record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.OPCION_MENU
   *
   * @mbggenerated Tue Jul 30 16:52:02 COT 2013
   */
  List<OpcionMenu> selectByExample(OpcionMenuExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.OPCION_MENU
   *
   * @mbggenerated Tue Jul 30 16:52:02 COT 2013
   */
  OpcionMenu selectByPrimaryKey(Long codOpcion);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.OPCION_MENU
   *
   * @mbggenerated Tue Jul 30 16:52:02 COT 2013
   */
  int updateByExampleSelective(@Param("record") OpcionMenu record, @Param("example") OpcionMenuExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.OPCION_MENU
   *
   * @mbggenerated Tue Jul 30 16:52:02 COT 2013
   */
  int updateByExample(@Param("record") OpcionMenu record, @Param("example") OpcionMenuExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.OPCION_MENU
   *
   * @mbggenerated Tue Jul 30 16:52:02 COT 2013
   */
  int updateByPrimaryKeySelective(OpcionMenu record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table dbo.OPCION_MENU
   *
   * @mbggenerated Tue Jul 30 16:52:02 COT 2013
   */
  int updateByPrimaryKey(OpcionMenu record);
}