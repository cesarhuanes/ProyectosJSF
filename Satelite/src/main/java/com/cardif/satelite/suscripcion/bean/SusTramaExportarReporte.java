package com.cardif.satelite.suscripcion.bean;

import java.io.Serializable;
import java.util.Date;

public class SusTramaExportarReporte implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  String nroPoliza;
  String producto;
  String evento;
  Date fecAnulacion;
  String motAnulacion;
  String observaciones;
  String fecRecepcion;

  public String getProducto() {
    return producto;
  }

  public String getNroPoliza() {
    return nroPoliza;
  }

  public String getEvento() {
    return evento;
  }

  public Date getFecAnulacion() {
    return fecAnulacion;
  }

  public String getMotAnulacion() {
    return motAnulacion;
  }

  public String getObservaciones() {
    return observaciones;
  }

  public String getFecRecepcion() {
    return fecRecepcion;
  }

  public void setProducto(String producto) {
    this.producto = producto;
  }

  public void setNroPoliza(String nroPoliza) {
    this.nroPoliza = nroPoliza;
  }

  public void setEvento(String evento) {
    this.evento = evento;
  }

  public void setFecAnulacion(Date fecAnulacion) {
    this.fecAnulacion = fecAnulacion;
  }

  public void setMotAnulacion(String motAnulacion) {
    this.motAnulacion = motAnulacion;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }

  public void setFecRecepcion(String fecRecepcion) {
    this.fecRecepcion = fecRecepcion;
  }

}
