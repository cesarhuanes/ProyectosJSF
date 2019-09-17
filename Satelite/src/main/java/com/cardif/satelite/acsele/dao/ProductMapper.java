package com.cardif.satelite.acsele.dao;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.acsele.Product;
import com.cardif.satelite.model.acsele.ProductExample;

public interface ProductMapper {

  final String SELECT_PRODUCTO = "select * from product " + "where description LIKE CONCAT(#{codSocio,jdbcType=VARCHAR},'%') " + "and pro_hierarchystate=2 "
      + "and pro_stateid=0 AND pro_iscommercial=1 order by description";

  /***************************************************/
  // Consulta para Productos SOAT - usuario: lmaron
  // **************************************************/
  final String SELECT_PRODUCTO_SOAT = "select * from product " + "where description LIKE '%SOAT%' " + "and pro_hierarchystate=2 " + "and pro_stateid=0 and pro_iscommercial=1 "
      + "and pro_premiummanagement = '2' order by description";

  /***************************************************/
  @Select(SELECT_PRODUCTO)
  @ResultMap(value = "BaseResultMap")
  List<Product> selectProduct(@Param("codSocio") String codSocio);

  @Select(SELECT_PRODUCTO_SOAT)
  @ResultMap(value = "BaseResultMap")
  List<Product> selectProductSOAT();

  final String SELECT_DESCRIPTION_PRODUCTO = "select description from product where productid = #{descripcion,jdbcType=VARCHAR}";

  @Select(SELECT_DESCRIPTION_PRODUCTO)
  String consultarDescripcion(@Param("descripcion") String descripcion);

  final String SELECT_COD_PRODUCTO = "SELECT PRODUCTID FROM PRODUCT WHERE DESCRIPTION = #{descripcion,jdbcType=VARCHAR}";

  @Select(SELECT_COD_PRODUCTO)
  String selectCodProducto(@Param("descripcion") String descripcion);

  int countByExample(ProductExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table BRAXTS_CFG.PRODUCT
   *
   * @mbggenerated Thu Dec 12 09:28:06 COT 2013
   */

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table BRAXTS_CFG.PRODUCT
   *
   * @mbggenerated Thu Dec 12 09:28:06 COT 2013
   */
  int deleteByExample(ProductExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table BRAXTS_CFG.PRODUCT
   *
   * @mbggenerated Thu Dec 12 09:28:06 COT 2013
   */
  int deleteByPrimaryKey(BigDecimal productid);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table BRAXTS_CFG.PRODUCT
   *
   * @mbggenerated Thu Dec 12 09:28:06 COT 2013
   */
  int insert(Product record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table BRAXTS_CFG.PRODUCT
   *
   * @mbggenerated Thu Dec 12 09:28:06 COT 2013
   */
  int insertSelective(Product record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table BRAXTS_CFG.PRODUCT
   *
   * @mbggenerated Thu Dec 12 09:28:06 COT 2013
   */
  List<Product> selectByExample(ProductExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table BRAXTS_CFG.PRODUCT
   *
   * @mbggenerated Thu Dec 12 09:28:06 COT 2013
   */
  Product selectByPrimaryKey(BigDecimal productid);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table BRAXTS_CFG.PRODUCT
   *
   * @mbggenerated Thu Dec 12 09:28:06 COT 2013
   */
  int updateByExampleSelective(@Param("record") Product record, @Param("example") ProductExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table BRAXTS_CFG.PRODUCT
   *
   * @mbggenerated Thu Dec 12 09:28:06 COT 2013
   */
  int updateByExample(@Param("record") Product record, @Param("example") ProductExample example);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table BRAXTS_CFG.PRODUCT
   *
   * @mbggenerated Thu Dec 12 09:28:06 COT 2013
   */
  int updateByPrimaryKeySelective(Product record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table BRAXTS_CFG.PRODUCT
   *
   * @mbggenerated Thu Dec 12 09:28:06 COT 2013
   */
  int updateByPrimaryKey(Product record);
}