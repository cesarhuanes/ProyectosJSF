package com.cardif.satelite.model.soat;

import java.io.Serializable;

public class Departamento implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_SOAT.DEPARTAMENTO.COD_DEPARTAMENTO
   *
   * @mbggenerated Mon Mar 17 19:38:27 COT 2014
   */
  private String codDepartamento;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_SOAT.DEPARTAMENTO.NOMBRE_DEPARTAMENTO
   *
   * @mbggenerated Mon Mar 17 19:38:27 COT 2014
   */
  private String nombreDepartamento;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_SOAT.DEPARTAMENTO.VERSION
   *
   * @mbggenerated Mon Mar 17 19:38:27 COT 2014
   */
  private Long version;

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column USER_SOAT.DEPARTAMENTO.COD_DEPARTAMENTO
   *
   * @return the value of USER_SOAT.DEPARTAMENTO.COD_DEPARTAMENTO
   *
   * @mbggenerated Mon Mar 17 19:38:27 COT 2014
   */
  public String getCodDepartamento() {
    return codDepartamento;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_SOAT.DEPARTAMENTO.COD_DEPARTAMENTO
   *
   * @param codDepartamento
   *          the value for USER_SOAT.DEPARTAMENTO.COD_DEPARTAMENTO
   *
   * @mbggenerated Mon Mar 17 19:38:27 COT 2014
   */
  public void setCodDepartamento(String codDepartamento) {
    this.codDepartamento = codDepartamento == null ? null : codDepartamento.trim();
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column USER_SOAT.DEPARTAMENTO.NOMBRE_DEPARTAMENTO
   *
   * @return the value of USER_SOAT.DEPARTAMENTO.NOMBRE_DEPARTAMENTO
   *
   * @mbggenerated Mon Mar 17 19:38:27 COT 2014
   */
  public String getNombreDepartamento() {
    return nombreDepartamento;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_SOAT.DEPARTAMENTO.NOMBRE_DEPARTAMENTO
   *
   * @param nombreDepartamento
   *          the value for USER_SOAT.DEPARTAMENTO.NOMBRE_DEPARTAMENTO
   *
   * @mbggenerated Mon Mar 17 19:38:27 COT 2014
   */
  public void setNombreDepartamento(String nombreDepartamento) {
    this.nombreDepartamento = nombreDepartamento == null ? null : nombreDepartamento.trim();
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column USER_SOAT.DEPARTAMENTO.VERSION
   *
   * @return the value of USER_SOAT.DEPARTAMENTO.VERSION
   *
   * @mbggenerated Mon Mar 17 19:38:27 COT 2014
   */
  public Long getVersion() {
    return version;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_SOAT.DEPARTAMENTO.VERSION
   *
   * @param version
   *          the value for USER_SOAT.DEPARTAMENTO.VERSION
   *
   * @mbggenerated Mon Mar 17 19:38:27 COT 2014
   */
  public void setVersion(Long version) {
    this.version = version;
  }
}