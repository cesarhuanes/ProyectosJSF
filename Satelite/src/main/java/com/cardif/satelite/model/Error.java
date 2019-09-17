package com.cardif.satelite.model;

import java.io.Serializable;

public class Error implements Serializable {

  private static final long serialVersionUID = 1L;

  private String nomValor;
  private Long codError;
  private String tipError;

  public String getNomValor() {
    return nomValor;
  }

  public void setNomValor(String nomValor) {
    this.nomValor = nomValor;
  }

  public Long getCodError() {
    return codError;
  }

  public void setCodError(Long codError) {
    this.codError = codError;
  }

  public String getTipError() {
    return tipError;
  }

  public void setTipError(String tipError) {
    this.tipError = tipError;
  }

}