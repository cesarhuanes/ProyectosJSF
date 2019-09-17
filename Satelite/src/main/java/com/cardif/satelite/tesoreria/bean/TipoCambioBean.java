package com.cardif.satelite.tesoreria.bean;

import java.io.Serializable;

public class TipoCambioBean implements Serializable {
  private static final long serialVersionUID = 1L;

  String cuentaDesde;
  String cuentaHasta;
  String tasaConversion;
  String nroDecimales;
  String monedaDesde;
  String monedaHasta;
  String descripcion;
  String fechaEfectivaDesde;
  String fechaEfectivaHasta;
  String codigoBusqueda;
  String multiplicaDivide;

  public String getCuentaDesde() {
    return cuentaDesde;
  }

  public void setCuentaDesde(String cuentaDesde) {
    this.cuentaDesde = cuentaDesde;
  }

  public String getCuentaHasta() {
    return cuentaHasta;
  }

  public void setCuentaHasta(String cuentaHasta) {
    this.cuentaHasta = cuentaHasta;
  }

  public String getTasaConversion() {
    return tasaConversion;
  }

  public void setTasaConversion(String tasaConversion) {
    this.tasaConversion = tasaConversion;
  }

  public String getNroDecimales() {
    return nroDecimales;
  }

  public void setNroDecimales(String nroDecimales) {
    this.nroDecimales = nroDecimales;
  }

  public String getMonedaDesde() {
    return monedaDesde;
  }

  public void setMonedaDesde(String monedaDesde) {
    this.monedaDesde = monedaDesde;
  }

  public String getMonedaHasta() {
    return monedaHasta;
  }

  public void setMonedaHasta(String monedaHasta) {
    this.monedaHasta = monedaHasta;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getFechaEfectivaDesde() {
    return fechaEfectivaDesde;
  }

  public void setFechaEfectivaDesde(String fechaEfectivaDesde) {
    this.fechaEfectivaDesde = fechaEfectivaDesde;
  }

  public String getFechaEfectivaHasta() {
    return fechaEfectivaHasta;
  }

  public void setFechaEfectivaHasta(String fechaEfectivaHasta) {
    this.fechaEfectivaHasta = fechaEfectivaHasta;
  }

  public String getCodigoBusqueda() {
    return codigoBusqueda;
  }

  public void setCodigoBusqueda(String codigoBusqueda) {
    this.codigoBusqueda = codigoBusqueda;
  }

  public String getMultiplicaDivide() {
    return multiplicaDivide;
  }

  public void setMultiplicaDivide(String multiplicaDivide) {
    this.multiplicaDivide = multiplicaDivide;
  }

}
