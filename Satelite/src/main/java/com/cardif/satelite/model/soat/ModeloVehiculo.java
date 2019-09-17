package com.cardif.satelite.model.soat;

import java.io.Serializable;
import java.math.BigDecimal;

public class ModeloVehiculo implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_SOAT.MODELO_VEHICULO.ID
   *
   * @mbggenerated Tue Nov 05 11:46:17 COT 2013
   */
  private BigDecimal id;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_SOAT.MODELO_VEHICULO.NOMBRE_MODELOVEHICULO
   *
   * @mbggenerated Tue Nov 05 11:46:17 COT 2013
   */
  private String nombreModelovehiculo;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_SOAT.MODELO_VEHICULO.VERSION
   *
   * @mbggenerated Tue Nov 05 11:46:17 COT 2013
   */
  private Long version;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_SOAT.MODELO_VEHICULO.MARCA_VEHICULO
   *
   * @mbggenerated Tue Nov 05 11:46:17 COT 2013
   */
  private BigDecimal marcaVehiculo;

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column USER_SOAT.MODELO_VEHICULO.ID
   *
   * @return the value of USER_SOAT.MODELO_VEHICULO.ID
   *
   * @mbggenerated Tue Nov 05 11:46:17 COT 2013
   */
  public BigDecimal getId() {
    return id;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_SOAT.MODELO_VEHICULO.ID
   *
   * @param id
   *          the value for USER_SOAT.MODELO_VEHICULO.ID
   *
   * @mbggenerated Tue Nov 05 11:46:17 COT 2013
   */
  public void setId(BigDecimal id) {
    this.id = id;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column
   * USER_SOAT.MODELO_VEHICULO.NOMBRE_MODELOVEHICULO
   *
   * @return the value of USER_SOAT.MODELO_VEHICULO.NOMBRE_MODELOVEHICULO
   *
   * @mbggenerated Tue Nov 05 11:46:17 COT 2013
   */
  public String getNombreModelovehiculo() {
    return nombreModelovehiculo;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_SOAT.MODELO_VEHICULO.NOMBRE_MODELOVEHICULO
   *
   * @param nombreModelovehiculo
   *          the value for USER_SOAT.MODELO_VEHICULO.NOMBRE_MODELOVEHICULO
   *
   * @mbggenerated Tue Nov 05 11:46:17 COT 2013
   */
  public void setNombreModelovehiculo(String nombreModelovehiculo) {
    this.nombreModelovehiculo = nombreModelovehiculo == null ? null : nombreModelovehiculo.trim();
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column USER_SOAT.MODELO_VEHICULO.VERSION
   *
   * @return the value of USER_SOAT.MODELO_VEHICULO.VERSION
   *
   * @mbggenerated Tue Nov 05 11:46:17 COT 2013
   */
  public Long getVersion() {
    return version;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_SOAT.MODELO_VEHICULO.VERSION
   *
   * @param version
   *          the value for USER_SOAT.MODELO_VEHICULO.VERSION
   *
   * @mbggenerated Tue Nov 05 11:46:17 COT 2013
   */
  public void setVersion(Long version) {
    this.version = version;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column USER_SOAT.MODELO_VEHICULO.MARCA_VEHICULO
   *
   * @return the value of USER_SOAT.MODELO_VEHICULO.MARCA_VEHICULO
   *
   * @mbggenerated Tue Nov 05 11:46:17 COT 2013
   */
  public BigDecimal getMarcaVehiculo() {
    return marcaVehiculo;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_SOAT.MODELO_VEHICULO.MARCA_VEHICULO
   *
   * @param marcaVehiculo
   *          the value for USER_SOAT.MODELO_VEHICULO.MARCA_VEHICULO
   *
   * @mbggenerated Tue Nov 05 11:46:17 COT 2013
   */
  public void setMarcaVehiculo(BigDecimal marcaVehiculo) {
    this.marcaVehiculo = marcaVehiculo;
  }
}