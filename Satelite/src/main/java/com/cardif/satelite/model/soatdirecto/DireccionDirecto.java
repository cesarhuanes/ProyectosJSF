package com.cardif.satelite.model.soatdirecto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DireccionDirecto implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_SOAT_DIRECPROD.DIRECCION.ID_DIRECCION
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  private BigDecimal idDireccion;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_SOAT_DIRECPROD.DIRECCION.DIRECCION
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  private String direccion;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_SOAT_DIRECPROD.DIRECCION.EMAIL
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  private String email;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_SOAT_DIRECPROD.DIRECCION.FECHA
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  private Date fecha;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_SOAT_DIRECPROD.DIRECCION.FRANJA_HORARIA
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  private String franjaHoraria;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_SOAT_DIRECPROD.DIRECCION.REFERENCIA
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  private String referencia;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_SOAT_DIRECPROD.DIRECCION.TELEFONO
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  private String telefono;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_SOAT_DIRECPROD.DIRECCION.VERSION
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  private Long version;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_SOAT_DIRECPROD.DIRECCION.DEPARTAMENTO
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  private String departamento;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_SOAT_DIRECPROD.DIRECCION.DISTRITO
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  private String distrito;

  /**
   * This field was generated by MyBatis Generator. This field corresponds to the database column USER_SOAT_DIRECPROD.DIRECCION.PROVINCIA
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  private String provincia;

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column USER_SOAT_DIRECPROD.DIRECCION.ID_DIRECCION
   *
   * @return the value of USER_SOAT_DIRECPROD.DIRECCION.ID_DIRECCION
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  public BigDecimal getIdDireccion() {
    return idDireccion;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_SOAT_DIRECPROD.DIRECCION.ID_DIRECCION
   *
   * @param idDireccion
   *          the value for USER_SOAT_DIRECPROD.DIRECCION.ID_DIRECCION
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  public void setIdDireccion(BigDecimal idDireccion) {
    this.idDireccion = idDireccion;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column USER_SOAT_DIRECPROD.DIRECCION.DIRECCION
   *
   * @return the value of USER_SOAT_DIRECPROD.DIRECCION.DIRECCION
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  public String getDireccion() {
    return direccion;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_SOAT_DIRECPROD.DIRECCION.DIRECCION
   *
   * @param direccion
   *          the value for USER_SOAT_DIRECPROD.DIRECCION.DIRECCION
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  public void setDireccion(String direccion) {
    this.direccion = direccion == null ? null : direccion.trim();
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column USER_SOAT_DIRECPROD.DIRECCION.EMAIL
   *
   * @return the value of USER_SOAT_DIRECPROD.DIRECCION.EMAIL
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  public String getEmail() {
    return email;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_SOAT_DIRECPROD.DIRECCION.EMAIL
   *
   * @param email
   *          the value for USER_SOAT_DIRECPROD.DIRECCION.EMAIL
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  public void setEmail(String email) {
    this.email = email == null ? null : email.trim();
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column USER_SOAT_DIRECPROD.DIRECCION.FECHA
   *
   * @return the value of USER_SOAT_DIRECPROD.DIRECCION.FECHA
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  public Date getFecha() {
    return fecha;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_SOAT_DIRECPROD.DIRECCION.FECHA
   *
   * @param fecha
   *          the value for USER_SOAT_DIRECPROD.DIRECCION.FECHA
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column USER_SOAT_DIRECPROD.DIRECCION.FRANJA_HORARIA
   *
   * @return the value of USER_SOAT_DIRECPROD.DIRECCION.FRANJA_HORARIA
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  public String getFranjaHoraria() {
    return franjaHoraria;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_SOAT_DIRECPROD.DIRECCION.FRANJA_HORARIA
   *
   * @param franjaHoraria
   *          the value for USER_SOAT_DIRECPROD.DIRECCION.FRANJA_HORARIA
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  public void setFranjaHoraria(String franjaHoraria) {
    this.franjaHoraria = franjaHoraria == null ? null : franjaHoraria.trim();
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column USER_SOAT_DIRECPROD.DIRECCION.REFERENCIA
   *
   * @return the value of USER_SOAT_DIRECPROD.DIRECCION.REFERENCIA
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  public String getReferencia() {
    return referencia;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_SOAT_DIRECPROD.DIRECCION.REFERENCIA
   *
   * @param referencia
   *          the value for USER_SOAT_DIRECPROD.DIRECCION.REFERENCIA
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  public void setReferencia(String referencia) {
    this.referencia = referencia == null ? null : referencia.trim();
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column USER_SOAT_DIRECPROD.DIRECCION.TELEFONO
   *
   * @return the value of USER_SOAT_DIRECPROD.DIRECCION.TELEFONO
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  public String getTelefono() {
    return telefono;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_SOAT_DIRECPROD.DIRECCION.TELEFONO
   *
   * @param telefono
   *          the value for USER_SOAT_DIRECPROD.DIRECCION.TELEFONO
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  public void setTelefono(String telefono) {
    this.telefono = telefono == null ? null : telefono.trim();
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column USER_SOAT_DIRECPROD.DIRECCION.VERSION
   *
   * @return the value of USER_SOAT_DIRECPROD.DIRECCION.VERSION
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  public Long getVersion() {
    return version;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_SOAT_DIRECPROD.DIRECCION.VERSION
   *
   * @param version
   *          the value for USER_SOAT_DIRECPROD.DIRECCION.VERSION
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  public void setVersion(Long version) {
    this.version = version;
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column USER_SOAT_DIRECPROD.DIRECCION.DEPARTAMENTO
   *
   * @return the value of USER_SOAT_DIRECPROD.DIRECCION.DEPARTAMENTO
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  public String getDepartamento() {
    return departamento;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_SOAT_DIRECPROD.DIRECCION.DEPARTAMENTO
   *
   * @param departamento
   *          the value for USER_SOAT_DIRECPROD.DIRECCION.DEPARTAMENTO
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  public void setDepartamento(String departamento) {
    this.departamento = departamento == null ? null : departamento.trim();
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column USER_SOAT_DIRECPROD.DIRECCION.DISTRITO
   *
   * @return the value of USER_SOAT_DIRECPROD.DIRECCION.DISTRITO
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  public String getDistrito() {
    return distrito;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_SOAT_DIRECPROD.DIRECCION.DISTRITO
   *
   * @param distrito
   *          the value for USER_SOAT_DIRECPROD.DIRECCION.DISTRITO
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  public void setDistrito(String distrito) {
    this.distrito = distrito == null ? null : distrito.trim();
  }

  /**
   * This method was generated by MyBatis Generator. This method returns the value of the database column USER_SOAT_DIRECPROD.DIRECCION.PROVINCIA
   *
   * @return the value of USER_SOAT_DIRECPROD.DIRECCION.PROVINCIA
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  public String getProvincia() {
    return provincia;
  }

  /**
   * This method was generated by MyBatis Generator. This method sets the value of the database column USER_SOAT_DIRECPROD.DIRECCION.PROVINCIA
   *
   * @param provincia
   *          the value for USER_SOAT_DIRECPROD.DIRECCION.PROVINCIA
   *
   * @mbggenerated Wed Mar 12 16:22:33 COT 2014
   */
  public void setProvincia(String provincia) {
    this.provincia = provincia == null ? null : provincia.trim();
  }
}