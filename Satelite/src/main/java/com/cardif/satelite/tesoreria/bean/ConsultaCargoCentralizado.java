package com.cardif.satelite.tesoreria.bean;

import java.io.Serializable;
import java.util.Date;

public class ConsultaCargoCentralizado implements Serializable {
  private static final long serialVersionUID = 1L;

  private Date fechaDe;
  private Date fechaHasta;
  private String clase;
  private String nroDocumento;
  private String ruc;
  private Date fecEmision;
  private String razonSocial;
  private String moneda;
  private Date fecVencPago;

  public Date getFechaDe() {
    return fechaDe;
  }

  public void setFechaDe(Date fechaDe) {
    this.fechaDe = fechaDe;
  }

  public Date getFechaHasta() {
    return fechaHasta;
  }

  public void setFechaHasta(Date fechaHasta) {
    this.fechaHasta = fechaHasta;
  }

  public String getClase() {
    return clase;
  }

  public void setClase(String clase) {
    this.clase = clase;
  }

  public String getNroDocumento() {
    return nroDocumento;
  }

  public void setNroDocumento(String nroDocumento) {
    this.nroDocumento = nroDocumento;
  }

  public String getRuc() {
    return ruc;
  }

  public void setRuc(String ruc) {
    this.ruc = ruc;
  }

  public Date getFecEmision() {
    return fecEmision;
  }

  public void setFecEmision(Date fecEmision) {
    this.fecEmision = fecEmision;
  }

  public String getRazonSocial() {
    return razonSocial;
  }

  public void setRazonSocial(String razonSocial) {
    this.razonSocial = razonSocial;
  }

  public String getMoneda() {
    return moneda;
  }

  public void setMoneda(String moneda) {
    this.moneda = moneda;
  }

  public Date getFecVencPago() {
    return fecVencPago;
  }

  public void setFecVencPago(Date fecVencPago) {
    this.fecVencPago = fecVencPago;
  }

}
