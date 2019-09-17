package com.cardif.satelite.acsele.bean;

import java.io.Serializable;
import java.util.Date;

public class ConsultaOpenItem implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private String numPoliza;
  private String tipo;
  private Date fecha;
  private Date fecVenc;
  private Double monto;
  private String moneda;

  public ConsultaOpenItem() {
    super();
  }

  public ConsultaOpenItem(String numPoliza, String tipo, Date fecha, Date fecVenc, Double monto, String moneda) {
    super();
    this.numPoliza = numPoliza;
    this.tipo = tipo;
    this.fecha = fecha;
    this.fecVenc = fecVenc;
    this.monto = monto;
    this.moneda = moneda;
  }

  public String getNumPoliza() {
    return numPoliza;
  }

  public void setNumPoliza(String numPoliza) {
    this.numPoliza = numPoliza;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public Date getFecVenc() {
    return fecVenc;
  }

  public void setFecVenc(Date fecVen) {
    this.fecVenc = fecVen;
  }

  public Double getMonto() {
    return monto;
  }

  public void setMonto(Double monto) {
    this.monto = monto;
  }

  public String getMoneda() {
    return moneda;
  }

  public void setMoneda(String moneda) {
    this.moneda = moneda;
  }

}
