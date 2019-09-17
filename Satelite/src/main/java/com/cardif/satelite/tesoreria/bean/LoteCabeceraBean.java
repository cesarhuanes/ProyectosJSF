package com.cardif.satelite.tesoreria.bean;

import java.util.Date;

public class LoteCabeceraBean {
  private String nro_lote;

  private String tipo_pago;

  private String banco;

  private String moneda;

  private Date fec_creacion;

  private boolean seleccionado;

  public String getNro_lote() {
    return nro_lote;
  }

  public void setNro_lote(String nro_lote) {
    this.nro_lote = nro_lote;
  }

  public String getTipo_pago() {
    return tipo_pago;
  }

  public void setTipo_pago(String tipo_pago) {
    this.tipo_pago = tipo_pago;
  }

  public String getBanco() {
    return banco;
  }

  public void setBanco(String banco) {
    this.banco = banco;
  }

  public String getMoneda() {
    return moneda;
  }

  public void setMoneda(String moneda) {
    this.moneda = moneda;
  }

  public Date getFec_creacion() {
    return fec_creacion;
  }

  public void setFec_creacion(Date fec_creacion) {
    this.fec_creacion = fec_creacion;
  }

  public boolean isSeleccionado() {
    return seleccionado;
  }

  public void setSeleccionado(boolean seleccionado) {
    this.seleccionado = seleccionado;
  }

}