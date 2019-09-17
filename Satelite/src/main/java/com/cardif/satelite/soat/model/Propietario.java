package com.cardif.satelite.soat.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Propietario implements Serializable {

  private static final long serialVersionUID = 1L;

  private BigDecimal id;

  private String email;

  private String materno;

  private String nombres;

  private String paterno;

  private String rutPropietario;

  private Long version;

  private Date fechaNacimiento;

  private BigDecimal idTipoDcto;

  public BigDecimal getId() {
    return id;
  }

  public void setId(BigDecimal id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email == null ? null : email.trim();
  }

  public String getMaterno() {
    return materno;
  }

  public void setMaterno(String materno) {
    this.materno = materno == null ? null : materno.trim();
  }

  public String getNombres() {
    return nombres;
  }

  public void setNombres(String nombres) {
    this.nombres = nombres == null ? null : nombres.trim();
  }

  public String getPaterno() {
    return paterno;
  }

  public void setPaterno(String paterno) {
    this.paterno = paterno == null ? null : paterno.trim();
  }

  public String getRutPropietario() {
    return rutPropietario;
  }

  public void setRutPropietario(String rutPropietario) {
    this.rutPropietario = rutPropietario == null ? null : rutPropietario.trim();
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public Date getFechaNacimiento() {
    return fechaNacimiento;
  }

  public void setFechaNacimiento(Date fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
  }

  public BigDecimal getIdTipoDcto() {
    return idTipoDcto;
  }

  public void setIdTipoDcto(BigDecimal idTipoDcto) {
    this.idTipoDcto = idTipoDcto;
  }
}