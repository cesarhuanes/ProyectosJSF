package com.cardif.satelite.soat.model;

import java.io.Serializable;

public class Departamento implements Serializable {

  private static final long serialVersionUID = 1L;

  private String codDepartamento;
  private String nombreDepartamento;
  private Long version;

  public String getCodDepartamento() {
    return codDepartamento;
  }

  public void setCodDepartamento(String codDepartamento) {
    this.codDepartamento = codDepartamento == null ? null : codDepartamento.trim();
  }

  public String getNombreDepartamento() {
    return nombreDepartamento;
  }

  public void setNombreDepartamento(String nombreDepartamento) {
    this.nombreDepartamento = nombreDepartamento == null ? null : nombreDepartamento.trim();
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }
}
