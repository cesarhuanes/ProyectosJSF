package com.cardif.satelite.model;

import java.util.Date;

public class LoteCabecera {
  private String nro_lote;

  private String tipo_pago;

  private String banco;

  private String moneda;

  private Integer secuencial;

  private String cta_banco;

  private Date fec_proceso;

  private String usu_creacion;

  private Date fec_creacion;

  private String usu_modificacion;

  private Date fec_modificacion;

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

  public Integer getSecuencial() {
    return secuencial;
  }

  public void setSecuencial(Integer secuencial) {
    this.secuencial = secuencial;
  }

  public Date getFec_proceso() {
    return fec_proceso;
  }

  public void setFec_proceso(Date fec_proceso) {
    this.fec_proceso = fec_proceso;
  }

  public String getUsu_creacion() {
    return usu_creacion;
  }

  public void setUsu_creacion(String usu_creacion) {
    this.usu_creacion = usu_creacion;
  }

  public Date getFec_creacion() {
    return fec_creacion;
  }

  public void setFec_creacion(Date fec_creacion) {
    this.fec_creacion = fec_creacion;
  }

  public String getUsu_modificacion() {
    return usu_modificacion;
  }

  public void setUsu_modificacion(String usu_modificacion) {
    this.usu_modificacion = usu_modificacion;
  }

  public Date getFec_modificacion() {
    return fec_modificacion;
  }

  public void setFec_modificacion(Date fec_modificacion) {
    this.fec_modificacion = fec_modificacion;
  }

  public String getCta_banco() {
    return cta_banco;
  }

  public void setCta_banco(String cta_banco) {
    this.cta_banco = cta_banco;
  }
}