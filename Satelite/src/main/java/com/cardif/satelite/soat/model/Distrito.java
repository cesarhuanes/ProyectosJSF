package com.cardif.satelite.soat.model;

import java.io.Serializable;

public class Distrito implements Serializable {

  private static final long serialVersionUID = 1L;

  private String codDistrito;

  private String nombreDistrito;

  private Long version;

  private String provincia;

  public String getCodDistrito() {
    return codDistrito;
  }

  public void setCodDistrito(String codDistrito) {
    this.codDistrito = codDistrito == null ? null : codDistrito.trim();
  }

  public String getNombreDistrito() {
    return nombreDistrito;
  }

  public void setNombreDistrito(String nombreDistrito) {
    this.nombreDistrito = nombreDistrito == null ? null : nombreDistrito.trim();
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public String getProvincia() {
    return provincia;
  }

  public void setProvincia(String provincia) {
    this.provincia = provincia == null ? null : provincia.trim();
  }
}