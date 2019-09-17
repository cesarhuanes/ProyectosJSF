package com.cardif.satelite.actuarial.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ConsultaActReaseguro implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Long codReaseguro;
  private BigDecimal pctReaseguro;
  private Date fecIniReaseguro;
  private Date fecFinReaseguro;
  private Long codSistema;
  private String codSocio;
  private String socio;
  private Long codProducto;
  private String producto;
  private Date fecCreacion;
  private String usuCreacion;
  private Date fecModificacion;
  private String usuModificacion;

  public Long getCodReaseguro() {
    return codReaseguro;
  }

  public void setCodReaseguro(Long codReaseguro) {
    this.codReaseguro = codReaseguro;
  }

  public BigDecimal getPctReaseguro() {
    return pctReaseguro;
  }

  public void setPctReaseguro(BigDecimal pctReaseguro) {
    this.pctReaseguro = pctReaseguro;
  }

  public Date getFecIniReaseguro() {
    return fecIniReaseguro;
  }

  public void setFecIniReaseguro(Date fecIniReaseguro) {
    this.fecIniReaseguro = fecIniReaseguro;
  }

  public Date getFecFinReaseguro() {
    return fecFinReaseguro;
  }

  public void setFecFinReaseguro(Date fecFinReaseguro) {
    this.fecFinReaseguro = fecFinReaseguro;
  }

  public Long getCodSistema() {
    return codSistema;
  }

  public void setCodSistema(Long codSistema) {
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

  public Date getFecCreacion() {
    return fecCreacion;
  }

  public void setFecCreacion(Date fecCreacion) {
    this.fecCreacion = fecCreacion;
  }

  public String getUsuCreacion() {
    return usuCreacion;
  }

  public void setUsuCreacion(String usuCreacion) {
    this.usuCreacion = usuCreacion;
  }

  public Date getFecModificacion() {
    return fecModificacion;
  }

  public void setFecModificacion(Date fecModificacion) {
    this.fecModificacion = fecModificacion;
  }

  public String getUsuModificacion() {
    return usuModificacion;
  }

  public void setUsuModificacion(String usuModificacion) {
    this.usuModificacion = usuModificacion;
  }

}
