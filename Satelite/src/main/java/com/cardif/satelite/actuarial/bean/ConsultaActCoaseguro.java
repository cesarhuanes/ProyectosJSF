package com.cardif.satelite.actuarial.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ConsultaActCoaseguro implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Long codCoaseguro;
  private BigDecimal pctCoaseguro;
  private Date fecIniCoaseguro;
  private Date fecFinCoaseguro;

  private String codSistema;
  private String codSocio;
  private String socio;

  private Long codProducto;
  private String producto;

  public Long getCodCoaseguro() {
    return codCoaseguro;
  }

  public void setCodCoaseguro(Long codCoaseguro) {
    this.codCoaseguro = codCoaseguro;
  }

  public BigDecimal getPctCoaseguro() {
    return pctCoaseguro;
  }

  public void setPctCoaseguro(BigDecimal pctCoaseguro) {
    this.pctCoaseguro = pctCoaseguro;
  }

  public Date getFecIniCoaseguro() {
    return fecIniCoaseguro;
  }

  public void setFecIniCoaseguro(Date fecIniCoaseguro) {
    this.fecIniCoaseguro = fecIniCoaseguro;
  }

  public Date getFecFinCoaseguro() {
    return fecFinCoaseguro;
  }

  public void setFecFinCoaseguro(Date fecFinCoaseguro) {
    this.fecFinCoaseguro = fecFinCoaseguro;
  }

  public String getCodSistema() {
    return codSistema;
  }

  public void setCodSistema(String codSistema) {
    this.codSistema = codSistema;
  }

  public String getCodSocio() {
    return codSocio;
  }

  public void setCodSocio(String codSocio) {
    this.codSocio = codSocio;
  }

  public String getSocio() {
    return socio;
  }

  public void setSocio(String socio) {
    this.socio = socio;
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

}
