package com.cardif.satelite.soat.model;

import java.io.Serializable;

public class UsoVehiculo implements Serializable {

  private static final long serialVersionUID = 1L;

  private String codUso;

  private String descripcionUso;

  private Long version;

  public String getCodUso() {
    return codUso;
  }

  public void setCodUso(String codUso) {
    this.codUso = codUso == null ? null : codUso.trim();
  }

  public String getDescripcionUso() {
    return descripcionUso;
  }

  public void setDescripcionUso(String descripcionUso) {
    this.descripcionUso = descripcionUso == null ? null : descripcionUso.trim();
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }
}