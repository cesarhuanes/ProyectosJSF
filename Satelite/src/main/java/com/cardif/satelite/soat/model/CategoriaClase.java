package com.cardif.satelite.soat.model;

import java.io.Serializable;

public class CategoriaClase implements Serializable {

  private static final long serialVersionUID = 1L;
  private String codCategoriaClase;
  private String descripcionCategoriaClase;
  private Long version;

  public String getCodCategoriaClase() {
    return codCategoriaClase;
  }

  public void setCodCategoriaClase(String codCategoriaClase) {
    this.codCategoriaClase = codCategoriaClase == null ? null : codCategoriaClase.trim();
  }

  public String getDescripcionCategoriaClase() {
    return descripcionCategoriaClase;
  }

  public void setDescripcionCategoriaClase(String descripcionCategoriaClase) {
    this.descripcionCategoriaClase = descripcionCategoriaClase == null ? null : descripcionCategoriaClase.trim();
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }
}