package com.cardif.satelite.model.satelite;

import java.io.Serializable;

public class GenPais implements Serializable {
  private static final long serialVersionUID = 1L;

  private String codPais;
  private String nombrePais;

  public String getCodPais() {
    return codPais;
  }

  public void setCodPais(String codPais) {
    this.codPais = codPais == null ? null : codPais.trim();
  }

  public String getNombrePais() {
    return nombrePais;
  }

  public void setNombrePais(String nombrePais) {
    this.nombrePais = nombrePais == null ? null : nombrePais.trim();
  }
}
