package com.cardif.satelite.callcenter.bean;

import java.io.Serializable;
import java.math.BigDecimal;
/*
 * @usuario lmaron
 * 
 * @fecha_creacion 30/01/2014
 * 
 * @ticket
 */
import java.util.Date;

public class ConsultaRegPropietario implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /* jhurtado */
  private BigDecimal id;
  private String nombres;
  private String paterno;
  private String materno;
  private String email;
  private Date fechaNacimiento;
  private String rutPropietario;

  /* jhurtado */
  public BigDecimal getId() {
    return id;
  }

  public void setId(BigDecimal id) {
    this.id = id;
  }

  public String getNombres() {
    return nombres;
  }

  public void setNombres(String nombres) {
    this.nombres = nombres;
  }

  public String getPaterno() {
    return paterno;
  }

  public void setPaterno(String paterno) {
    this.paterno = paterno;
  }

  public String getMaterno() {
    return materno;
  }

  public void setMaterno(String materno) {
    this.materno = materno;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Date getFechaNacimiento() {
    return fechaNacimiento;
  }

  public void setFechaNacimiento(Date fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
  }

  public String getRutPropietario() {
    return rutPropietario;
  }

  public void setRutPropietario(String rutPropietario) {
    this.rutPropietario = rutPropietario;
  }

}
