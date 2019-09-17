package com.cardif.satelite.soat.model;

import java.io.Serializable;

public class Provincia implements Serializable {

  private static final long serialVersionUID = 1L;

  private String codProvincia;

  private String nombreProvincia;

  private Long version;

  private String departamento;

  public String getCodProvincia() {
    return codProvincia;
  }

  public void setCodProvincia(String codProvincia) {
    this.codProvincia = codProvincia == null ? null : codProvincia.trim();
  }

  public String getNombreProvincia() {
    return nombreProvincia;
  }

  public void setNombreProvincia(String nombreProvincia) {
    this.nombreProvincia = nombreProvincia == null ? null : nombreProvincia.trim();
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public String getDepartamento() {
    return departamento;
  }

  public void setDepartamento(String departamento) {
    this.departamento = departamento == null ? null : departamento.trim();
  }
}