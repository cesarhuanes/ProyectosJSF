package com.cardif.satelite.model.soat;

import java.io.Serializable;

public class Distrito implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_SOAT.DISTRITO.COD_DISTRITO
   *
   * @mbggenerated Tue Nov 05 11:46:17 COT 2013
   */
  private String codDistrito;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_SOAT.DISTRITO.NOMBRE_DISTRITO
   *
   * @mbggenerated Tue Nov 05 11:46:17 COT 2013
   */
  private String nombreDistrito;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_SOAT.DISTRITO.VERSION
   *
   * @mbggenerated Tue Nov 05 11:46:17 COT 2013
   */
  private Long version;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_SOAT.DISTRITO.PROVINCIA
   *
   * @mbggenerated Tue Nov 05 11:46:17 COT 2013
   */
  private String provincia;

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column USER_SOAT.DISTRITO.COD_DISTRITO
   *
   * @return the value of USER_SOAT.DISTRITO.COD_DISTRITO
   *
   * @mbggenerated Tue Nov 05 11:46:17 COT 2013
   */
  public String getCodDistrito() {
    return codDistrito;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_SOAT.DISTRITO.COD_DISTRITO
   *
   * @param codDistrito
   *          the value for USER_SOAT.DISTRITO.COD_DISTRITO
   *
   * @mbggenerated Tue Nov 05 11:46:17 COT 2013
   */
  public void setCodDistrito(String codDistrito) {
    this.codDistrito = codDistrito == null ? null : codDistrito.trim();
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column USER_SOAT.DISTRITO.NOMBRE_DISTRITO
   *
   * @return the value of USER_SOAT.DISTRITO.NOMBRE_DISTRITO
   *
   * @mbggenerated Tue Nov 05 11:46:17 COT 2013
   */
  public String getNombreDistrito() {
    return nombreDistrito;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_SOAT.DISTRITO.NOMBRE_DISTRITO
   *
   * @param nombreDistrito
   *          the value for USER_SOAT.DISTRITO.NOMBRE_DISTRITO
   *
   * @mbggenerated Tue Nov 05 11:46:17 COT 2013
   */
  public void setNombreDistrito(String nombreDistrito) {
    this.nombreDistrito = nombreDistrito == null ? null : nombreDistrito.trim();
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column USER_SOAT.DISTRITO.VERSION
   *
   * @return the value of USER_SOAT.DISTRITO.VERSION
   *
   * @mbggenerated Tue Nov 05 11:46:17 COT 2013
   */
  public Long getVersion() {
    return version;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_SOAT.DISTRITO.VERSION
   *
   * @param version
   *          the value for USER_SOAT.DISTRITO.VERSION
   *
   * @mbggenerated Tue Nov 05 11:46:17 COT 2013
   */
  public void setVersion(Long version) {
    this.version = version;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column USER_SOAT.DISTRITO.PROVINCIA
   *
   * @return the value of USER_SOAT.DISTRITO.PROVINCIA
   *
   * @mbggenerated Tue Nov 05 11:46:17 COT 2013
   */
  public String getProvincia() {
    return provincia;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_SOAT.DISTRITO.PROVINCIA
   *
   * @param provincia
   *          the value for USER_SOAT.DISTRITO.PROVINCIA
   *
   * @mbggenerated Tue Nov 05 11:46:17 COT 2013
   */
  public void setProvincia(String provincia) {
    this.provincia = provincia == null ? null : provincia.trim();
  }
}