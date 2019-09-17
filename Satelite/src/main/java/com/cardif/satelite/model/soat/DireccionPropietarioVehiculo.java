package com.cardif.satelite.model.soat;

import java.io.Serializable;
import java.math.BigDecimal;

public class DireccionPropietarioVehiculo implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_SOAT.DIRECCION_PROPIETARIO_VEHICULO.PLACA
   *
   * @mbggenerated Mon Mar 17 16:32:51 COT 2014
   */
  private String placa;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_SOAT.DIRECCION_PROPIETARIO_VEHICULO.VERSION
   *
   * @mbggenerated Mon Mar 17 16:32:51 COT 2014
   */
  private Long version;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_SOAT.DIRECCION_PROPIETARIO_VEHICULO.DIRECCION
   *
   * @mbggenerated Mon Mar 17 16:32:51 COT 2014
   */
  private BigDecimal direccion;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_SOAT.DIRECCION_PROPIETARIO_VEHICULO.PROPIETARIO
   *
   * @mbggenerated Mon Mar 17 16:32:51 COT 2014
   */
  private BigDecimal propietario;

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column
   * USER_SOAT.DIRECCION_PROPIETARIO_VEHICULO.PLACA
   *
   * @return the value of USER_SOAT.DIRECCION_PROPIETARIO_VEHICULO.PLACA
   *
   * @mbggenerated Mon Mar 17 16:32:51 COT 2014
   */
  public String getPlaca() {
    return placa;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_SOAT.DIRECCION_PROPIETARIO_VEHICULO.PLACA
   *
   * @param placa
   *          the value for USER_SOAT.DIRECCION_PROPIETARIO_VEHICULO.PLACA
   *
   * @mbggenerated Mon Mar 17 16:32:51 COT 2014
   */
  public void setPlaca(String placa) {
    this.placa = placa == null ? null : placa.trim();
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column
   * USER_SOAT.DIRECCION_PROPIETARIO_VEHICULO.VERSION
   *
   * @return the value of USER_SOAT.DIRECCION_PROPIETARIO_VEHICULO.VERSION
   *
   * @mbggenerated Mon Mar 17 16:32:51 COT 2014
   */
  public Long getVersion() {
    return version;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column
   * USER_SOAT.DIRECCION_PROPIETARIO_VEHICULO.VERSION
   *
   * @param version
   *          the value for USER_SOAT.DIRECCION_PROPIETARIO_VEHICULO.VERSION
   *
   * @mbggenerated Mon Mar 17 16:32:51 COT 2014
   */
  public void setVersion(Long version) {
    this.version = version;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column
   * USER_SOAT.DIRECCION_PROPIETARIO_VEHICULO.DIRECCION
   *
   * @return the value of USER_SOAT.DIRECCION_PROPIETARIO_VEHICULO.DIRECCION
   *
   * @mbggenerated Mon Mar 17 16:32:51 COT 2014
   */
  public BigDecimal getDireccion() {
    return direccion;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column
   * USER_SOAT.DIRECCION_PROPIETARIO_VEHICULO.DIRECCION
   *
   * @param direccion
   *          the value for USER_SOAT.DIRECCION_PROPIETARIO_VEHICULO.DIRECCION
   *
   * @mbggenerated Mon Mar 17 16:32:51 COT 2014
   */
  public void setDireccion(BigDecimal direccion) {
    this.direccion = direccion;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column
   * USER_SOAT.DIRECCION_PROPIETARIO_VEHICULO.PROPIETARIO
   *
   * @return the value of USER_SOAT.DIRECCION_PROPIETARIO_VEHICULO.PROPIETARIO
   *
   * @mbggenerated Mon Mar 17 16:32:51 COT 2014
   */
  public BigDecimal getPropietario() {
    return propietario;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column
   * USER_SOAT.DIRECCION_PROPIETARIO_VEHICULO.PROPIETARIO
   *
   * @param propietario
   *          the value for USER_SOAT.DIRECCION_PROPIETARIO_VEHICULO.PROPIETARIO
   *
   * @mbggenerated Mon Mar 17 16:32:51 COT 2014
   */
  public void setPropietario(BigDecimal propietario) {
    this.propietario = propietario;
  }
}