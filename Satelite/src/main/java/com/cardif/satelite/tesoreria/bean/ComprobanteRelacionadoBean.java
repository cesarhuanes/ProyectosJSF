package com.cardif.satelite.tesoreria.bean;

import java.io.Serializable;
import java.util.Date;

public class ComprobanteRelacionadoBean implements Serializable {
  private static final long serialVersionUID = 1L;

  String crTipoComprobante;
  String crNroComprobante;
  Date crFechaEmision;
  String crImporteTotal;
  String crTipoMoneda;
  Date pcFechaPagoCobro;
  String pcNroPagoCobro;
  Double pcImportePagoSinRetCob;
  Double pcImportePagoMonOrig;
  String pcTipoMoneda;
  Double rpImporteRetPer;
  String rpMonedaRetPer;
  Date rpFechaRetPer;
  Double rpImporteNetPagoCobro;
  String rpMonedaNetPagoCobro;
  String tcMonedaOrigen;
  String tcMonedaDestino;
  Double tcTasaCambio;
  Date tcFechaCambio;
  Boolean revisado;
  String importeBase;

  public String getCrTipoComprobante() {
    return crTipoComprobante;
  }

  public void setCrTipoComprobante(String crTipoComprobante) {
    this.crTipoComprobante = crTipoComprobante;
  }

  public String getCrNroComprobante() {
    return crNroComprobante;
  }

  public void setCrNroComprobante(String crNroComprobante) {
    this.crNroComprobante = crNroComprobante;
  }

  public Date getCrFechaEmision() {
    return crFechaEmision;
  }

  public void setCrFechaEmision(Date crFechaEmision) {
    this.crFechaEmision = crFechaEmision;
  }

  public String getCrImporteTotal() {
    return crImporteTotal;
  }

  public void setCrImporteTotal(String crImporteTotal) {
    this.crImporteTotal = crImporteTotal;
  }

  public String getCrTipoMoneda() {
    return crTipoMoneda;
  }

  public void setCrTipoMoneda(String crTipoMoneda) {
    this.crTipoMoneda = crTipoMoneda;
  }

  public Date getPcFechaPagoCobro() {
    return pcFechaPagoCobro;
  }

  public void setPcFechaPagoCobro(Date pcFechaPagoCobro) {
    this.pcFechaPagoCobro = pcFechaPagoCobro;
  }

  public String getPcNroPagoCobro() {
    return pcNroPagoCobro;
  }

  public void setPcNroPagoCobro(String pcNroPagoCobro) {
    this.pcNroPagoCobro = pcNroPagoCobro;
  }

  public Double getPcImportePagoSinRetCob() {
    return pcImportePagoSinRetCob;
  }

  public void setPcImportePagoSinRetCob(Double pcImportePagoSinRetCob) {
    this.pcImportePagoSinRetCob = pcImportePagoSinRetCob;
  }

  public Double getPcImportePagoMonOrig() {
    return pcImportePagoMonOrig;
  }

  public void setPcImportePagoMonOrig(Double pcImportePagoMonOrig) {
    this.pcImportePagoMonOrig = pcImportePagoMonOrig;
  }

  public String getPcTipoMoneda() {
    return pcTipoMoneda;
  }

  public void setPcTipoMoneda(String pcTipoMoneda) {
    this.pcTipoMoneda = pcTipoMoneda;
  }

  public Double getRpImporteRetPer() {
    return rpImporteRetPer;
  }

  public void setRpImporteRetPer(Double rpImporteRetPer) {
    this.rpImporteRetPer = rpImporteRetPer;
  }

  public String getRpMonedaRetPer() {
    return rpMonedaRetPer;
  }

  public void setRpMonedaRetPer(String rpMonedaRetPer) {
    this.rpMonedaRetPer = rpMonedaRetPer;
  }

  public Date getRpFechaRetPer() {
    return rpFechaRetPer;
  }

  public void setRpFechaRetPer(Date rpFechaRetPer) {
    this.rpFechaRetPer = rpFechaRetPer;
  }

  public Double getRpImporteNetPagoCobro() {
    return rpImporteNetPagoCobro;
  }

  public void setRpImporteNetPagoCobro(Double rpImporteNetPagoCobro) {
    this.rpImporteNetPagoCobro = rpImporteNetPagoCobro;
  }

  public String getRpMonedaNetPagoCobro() {
    return rpMonedaNetPagoCobro;
  }

  public void setRpMonedaNetPagoCobro(String rpMonedaNetPagoCobro) {
    this.rpMonedaNetPagoCobro = rpMonedaNetPagoCobro;
  }

  public String getTcMonedaOrigen() {
    return tcMonedaOrigen;
  }

  public void setTcMonedaOrigen(String tcMonedaOrigen) {
    this.tcMonedaOrigen = tcMonedaOrigen;
  }

  public String getTcMonedaDestino() {
    return tcMonedaDestino;
  }

  public void setTcMonedaDestino(String tcMonedaDestino) {
    this.tcMonedaDestino = tcMonedaDestino;
  }

  public Double getTcTasaCambio() {
    return tcTasaCambio;
  }

  public void setTcTasaCambio(Double tcTasaCambio) {
    this.tcTasaCambio = tcTasaCambio;
  }

  public Date getTcFechaCambio() {
    return tcFechaCambio;
  }

  public void setTcFechaCambio(Date tcFechaCambio) {
    this.tcFechaCambio = tcFechaCambio;
  }

  public Boolean getRevisado() {
    return revisado;
  }

  public void setRevisado(Boolean revisado) {
    this.revisado = revisado;
  }

  public String getImporteBase() {
    return importeBase;
  }

  public void setImporteBase(String importeBase) {
    this.importeBase = importeBase;
  }

}
