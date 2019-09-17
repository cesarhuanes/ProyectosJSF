package com.cardif.satelite.model.datamart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OdsProductoDe implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_DM_ODS.ODS_PRODUCTO_DE.DES_PRODUCTO
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  private String desProducto;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_DM_ODS.ODS_PRODUCTO_DE.COD_PRODUCTO
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  private BigDecimal codProducto;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_DM_ODS.ODS_PRODUCTO_DE.COD_RIESGOSBS
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  private Double codRiesgosbs;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_DM_ODS.ODS_PRODUCTO_DE.DES_RIESGOSBS
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  private String desRiesgosbs;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_DM_ODS.ODS_PRODUCTO_DE.COD_SBS
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  private String codSbs;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_DM_ODS.ODS_PRODUCTO_DE.FEC_LANZAMIENTO
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  private Date fecLanzamiento;

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column USER_DM_ODS.ODS_PRODUCTO_DE.DES_PRODUCTO
   *
   * @return the value of USER_DM_ODS.ODS_PRODUCTO_DE.DES_PRODUCTO
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  public String getDesProducto() {
    return desProducto;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_DM_ODS.ODS_PRODUCTO_DE.DES_PRODUCTO
   *
   * @param desProducto
   *          the value for USER_DM_ODS.ODS_PRODUCTO_DE.DES_PRODUCTO
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  public void setDesProducto(String desProducto) {
    this.desProducto = desProducto == null ? null : desProducto.trim();
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column USER_DM_ODS.ODS_PRODUCTO_DE.COD_PRODUCTO
   *
   * @return the value of USER_DM_ODS.ODS_PRODUCTO_DE.COD_PRODUCTO
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  public BigDecimal getCodProducto() {
    return codProducto;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_DM_ODS.ODS_PRODUCTO_DE.COD_PRODUCTO
   *
   * @param codProducto
   *          the value for USER_DM_ODS.ODS_PRODUCTO_DE.COD_PRODUCTO
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  public void setCodProducto(BigDecimal codProducto) {
    this.codProducto = codProducto;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column USER_DM_ODS.ODS_PRODUCTO_DE.COD_RIESGOSBS
   *
   * @return the value of USER_DM_ODS.ODS_PRODUCTO_DE.COD_RIESGOSBS
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  public Double getCodRiesgosbs() {
    return codRiesgosbs;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_DM_ODS.ODS_PRODUCTO_DE.COD_RIESGOSBS
   *
   * @param codRiesgosbs
   *          the value for USER_DM_ODS.ODS_PRODUCTO_DE.COD_RIESGOSBS
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  public void setCodRiesgosbs(Double codRiesgosbs) {
    this.codRiesgosbs = codRiesgosbs;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column USER_DM_ODS.ODS_PRODUCTO_DE.DES_RIESGOSBS
   *
   * @return the value of USER_DM_ODS.ODS_PRODUCTO_DE.DES_RIESGOSBS
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  public String getDesRiesgosbs() {
    return desRiesgosbs;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_DM_ODS.ODS_PRODUCTO_DE.DES_RIESGOSBS
   *
   * @param desRiesgosbs
   *          the value for USER_DM_ODS.ODS_PRODUCTO_DE.DES_RIESGOSBS
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  public void setDesRiesgosbs(String desRiesgosbs) {
    this.desRiesgosbs = desRiesgosbs == null ? null : desRiesgosbs.trim();
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column USER_DM_ODS.ODS_PRODUCTO_DE.COD_SBS
   *
   * @return the value of USER_DM_ODS.ODS_PRODUCTO_DE.COD_SBS
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  public String getCodSbs() {
    return codSbs;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_DM_ODS.ODS_PRODUCTO_DE.COD_SBS
   *
   * @param codSbs
   *          the value for USER_DM_ODS.ODS_PRODUCTO_DE.COD_SBS
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  public void setCodSbs(String codSbs) {
    this.codSbs = codSbs == null ? null : codSbs.trim();
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column USER_DM_ODS.ODS_PRODUCTO_DE.FEC_LANZAMIENTO
   *
   * @return the value of USER_DM_ODS.ODS_PRODUCTO_DE.FEC_LANZAMIENTO
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  public Date getFecLanzamiento() {
    return fecLanzamiento;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_DM_ODS.ODS_PRODUCTO_DE.FEC_LANZAMIENTO
   *
   * @param fecLanzamiento
   *          the value for USER_DM_ODS.ODS_PRODUCTO_DE.FEC_LANZAMIENTO
   *
   * @mbggenerated Tue Feb 04 14:23:56 COT 2014
   */
  public void setFecLanzamiento(Date fecLanzamiento) {
    this.fecLanzamiento = fecLanzamiento;
  }
}