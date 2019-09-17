package com.cardif.satelite.telemarketing.bean;

import java.io.Serializable;

public class AuditCargaTrama implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Long codTrama;
  private String codSocioBase;
  private String codSocioVali;
  private Long codProducto;
  private String producto;
  private String desSocio;
  private String desProducto;

  public Long getCodTrama() {
    return codTrama;
  }

  public void setCodTrama(Long codTrama) {
    this.codTrama = codTrama;
  }

  public String getCodSocioBase() {
    return codSocioBase;
  }

  public void setCodSocioBase(String codSocioBase) {
    this.codSocioBase = codSocioBase;
  }

  public String getCodSocioVali() {
    return codSocioVali;
  }

  public void setCodSocioVali(String codSocioVali) {
    this.codSocioVali = codSocioVali;
  }

  public Long getCodProducto() {
    return codProducto;
  }

  public void setCodProducto(Long codProducto) {
    this.codProducto = codProducto;
  }

  public String getProducto() {
    return producto;
  }

  public void setProducto(String producto) {
    this.producto = producto;
  }

  public String getDesSocio() {
    return desSocio;
  }

  public void setDesSocio(String desSocio) {
    this.desSocio = desSocio;
  }

  public String getDesProducto() {
    return desProducto;
  }

  public void setDesProducto(String desProducto) {
    this.desProducto = desProducto;
  }

}
