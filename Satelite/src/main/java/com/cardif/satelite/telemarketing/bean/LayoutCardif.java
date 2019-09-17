package com.cardif.satelite.telemarketing.bean;

import java.io.Serializable;

public class LayoutCardif implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String codSocio;
  private String nomCampo;
  private int numSecu;

  public LayoutCardif(String nomCampo) {
    this.nomCampo = nomCampo;
  }

  public String getCodSocio() {
    return codSocio;
  }

  public void setCodSocio(String codSocio) {
    this.codSocio = codSocio;
  }

  public String getNomCampo() {
    return nomCampo;
  }

  public void setNomCampo(String nomCampo) {
    this.nomCampo = nomCampo;
  }

  public int getNumSecu() {
    return numSecu;
  }

  public void setNumSecu(int numSecu) {
    this.numSecu = numSecu;
  }

}
