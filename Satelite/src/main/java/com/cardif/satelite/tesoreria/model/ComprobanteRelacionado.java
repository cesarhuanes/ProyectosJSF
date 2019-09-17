package com.cardif.satelite.tesoreria.model;

import java.util.Date;

public class ComprobanteRelacionado {
  private String nroComprobante;
  private String tipoComprobante;
  private Date fechaEmision;
  private Double importe;
  private String moneda;

  public String getNroComprobante() {
    return nroComprobante;
  }

  public void setNroComprobante(String nroComprobante) {
    this.nroComprobante = nroComprobante;
  }

  public String getTipoComprobante() {
    return tipoComprobante;
  }

  public void setTipoComprobante(String tipoComprobante) {
    this.tipoComprobante = tipoComprobante;
  }

  public Date getFechaEmision() {
    return fechaEmision;
  }

  public void setFechaEmision(Date fechaEmision) {
    this.fechaEmision = fechaEmision;
  }

  public Double getImporte() {
    return importe;
  }

  public void setImporte(Double importe) {
    this.importe = importe;
  }

  public String getMoneda() {
    return moneda;
  }

  public void setMoneda(String moneda) {
    this.moneda = moneda;
  }

}
