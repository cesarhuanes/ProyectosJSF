package com.cardif.satelite.suscripcion.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ConsultaTstVentas implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private Long pk;

  private Long correlativoregistro;

  private Date fechaemision;

  private Long tipocomprobante;

  private String tipocomprobantedesc;

  private Long nroserie;

  private Long correlativoserie;

  private String tipodoccliente;

  private String tipodocclientedesc;

  private String numerodoccliente;

  private String nombresrazonsocial;

  private BigDecimal importetotal;

  private Date fechaemisionref;

  private Long tipocomprobanteref;

  private Long seriecomprobanteref;

  private Long correlativoserieorig;

  private String numeropoliza;

  private String producto;

  public Long getPk() {
    return pk;
  }

  public void setPk(Long pk) {
    this.pk = pk;
  }

  public Long getCorrelativoregistro() {
    return correlativoregistro;
  }

  public void setCorrelativoregistro(Long correlativoregistro) {
    this.correlativoregistro = correlativoregistro;
  }

  public Date getFechaemision() {
    return fechaemision;
  }

  public void setFechaemision(Date fechaemision) {
    this.fechaemision = fechaemision;
  }

  public Long getTipocomprobante() {
    return tipocomprobante;
  }

  public void setTipocomprobante(Long tipocomprobante) {
    this.tipocomprobante = tipocomprobante;
  }

  public String getTipocomprobantedesc() {
    return tipocomprobantedesc;
  }

  public void setTipocomprobantedesc(String tipocomprobantedesc) {
    this.tipocomprobantedesc = tipocomprobantedesc;
  }

  public Long getNroserie() {
    return nroserie;
  }

  public void setNroserie(Long nroserie) {
    this.nroserie = nroserie;
  }

  public Long getCorrelativoserie() {
    return correlativoserie;
  }

  public void setCorrelativoserie(Long correlativoserie) {
    this.correlativoserie = correlativoserie;
  }

  public String getTipodoccliente() {
    return tipodoccliente;
  }

  public void setTipodoccliente(String tipodoccliente) {
    this.tipodoccliente = tipodoccliente;
  }

  public String getTipodocclientedesc() {
    return tipodocclientedesc;
  }

  public void setTipodocclientedesc(String tipodocclientedesc) {
    this.tipodocclientedesc = tipodocclientedesc;
  }

  public String getNumerodoccliente() {
    return numerodoccliente;
  }

  public void setNumerodoccliente(String numerodoccliente) {
    this.numerodoccliente = numerodoccliente;
  }

  public String getNombresrazonsocial() {
    return nombresrazonsocial;
  }

  public void setNombresrazonsocial(String nombresrazonsocial) {
    this.nombresrazonsocial = nombresrazonsocial;
  }

  public BigDecimal getImportetotal() {
    return importetotal;
  }

  public void setImportetotal(BigDecimal importetotal) {
    this.importetotal = importetotal;
  }

  public Date getFechaemisionref() {
    return fechaemisionref;
  }

  public void setFechaemisionref(Date fechaemisionref) {
    this.fechaemisionref = fechaemisionref;
  }

  public Long getTipocomprobanteref() {
    return tipocomprobanteref;
  }

  public void setTipocomprobanteref(Long tipocomprobanteref) {
    this.tipocomprobanteref = tipocomprobanteref;
  }

  public Long getSeriecomprobanteref() {
    return seriecomprobanteref;
  }

  public void setSeriecomprobanteref(Long seriecomprobanteref) {
    this.seriecomprobanteref = seriecomprobanteref;
  }

  public Long getCorrelativoserieorig() {
    return correlativoserieorig;
  }

  public void setCorrelativoserieorig(Long correlativoserieorig) {
    this.correlativoserieorig = correlativoserieorig;
  }

  public String getNumeropoliza() {
    return numeropoliza;
  }

  public void setNumeropoliza(String numeropoliza) {
    this.numeropoliza = numeropoliza;
  }

  public String getProducto() {
    return producto;
  }

  public void setProducto(String producto) {
    this.producto = producto;
  }

}