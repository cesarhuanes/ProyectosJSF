package com.cardif.satelite.model.soat;

import java.io.Serializable;

public class UsoVehiculo implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_SOAT.USO_VEHICULO.COD_USO
   *
   * @mbggenerated Mon Mar 17 19:38:27 COT 2014
   */
  private String codUso;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_SOAT.USO_VEHICULO.DESCRIPCION_USO
   *
   * @mbggenerated Mon Mar 17 19:38:27 COT 2014
   */
  private String descripcionUso;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_SOAT.USO_VEHICULO.VERSION
   *
   * @mbggenerated Mon Mar 17 19:38:27 COT 2014
   */
  private Long version;

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column USER_SOAT.USO_VEHICULO.COD_USO
   *
   * @return the value of USER_SOAT.USO_VEHICULO.COD_USO
   *
   * @mbggenerated Mon Mar 17 19:38:27 COT 2014
   */
  public String getCodUso() {
    return codUso;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_SOAT.USO_VEHICULO.COD_USO
   *
   * @param codUso
   *          the value for USER_SOAT.USO_VEHICULO.COD_USO
   *
   * @mbggenerated Mon Mar 17 19:38:27 COT 2014
   */
  public void setCodUso(String codUso) {
    this.codUso = codUso == null ? null : codUso.trim();
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column USER_SOAT.USO_VEHICULO.DESCRIPCION_USO
   *
   * @return the value of USER_SOAT.USO_VEHICULO.DESCRIPCION_USO
   *
   * @mbggenerated Mon Mar 17 19:38:27 COT 2014
   */
  public String getDescripcionUso() {
    return descripcionUso;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_SOAT.USO_VEHICULO.DESCRIPCION_USO
   *
   * @param descripcionUso
   *          the value for USER_SOAT.USO_VEHICULO.DESCRIPCION_USO
   *
   * @mbggenerated Mon Mar 17 19:38:27 COT 2014
   */
  public void setDescripcionUso(String descripcionUso) {
    this.descripcionUso = descripcionUso == null ? null : descripcionUso.trim();
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column USER_SOAT.USO_VEHICULO.VERSION
   *
   * @return the value of USER_SOAT.USO_VEHICULO.VERSION
   *
   * @mbggenerated Mon Mar 17 19:38:27 COT 2014
   */
  public Long getVersion() {
    return version;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_SOAT.USO_VEHICULO.VERSION
   *
   * @param version
   *          the value for USER_SOAT.USO_VEHICULO.VERSION
   *
   * @mbggenerated Mon Mar 17 19:38:27 COT 2014
   */
  public void setVersion(Long version) {
    this.version = version;
  }
}