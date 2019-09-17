package com.cardif.satelite.siniestro.bean;

import java.io.Serializable;
import java.util.Date;

public class ConsultaSiniestro implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private boolean idCheck;
  private boolean idSelectCheck;
  private String nroSiniestro;
  private Date fecOcurrencia;
  private String nomSocio;
  private String nomCobertura;
  private String nomProducto;
  private String nroPoliza;
  private String tipDocum;
  private String nroDocumento;
  private String nomCompleto;
  private Date fecRecepSocio;
  private Double impPagos;
  private Date fecAprobRechazo;
  private String nomEstado;
  private String nroPlanilla;
  private String nomMoneda;
  private String codSocio;
  private Date fecUltDocumentacion;
  private Date fecUltDocumentacionSocio;
  private String tipRefCafae;
  private String refCafae;
  private String usuCreacion;
  private Date fecCreacion;

  public boolean isIdSelectCheck() {
    return idSelectCheck;
  }

  public void setIdSelectCheck(boolean idSelectCheck) {
    this.idSelectCheck = idSelectCheck;
  }

  public String getNroSiniestro() {
    return nroSiniestro;
  }
  
  public Date getFecOcurrencia() {
    return fecOcurrencia;
  }

  public String getNomSocio() {
    return nomSocio;
  }

  public String getNomCobertura() {
    return nomCobertura;
  }

  public String getNomProducto() {
    return nomProducto;
  }

  public String getNroPoliza() {
    return nroPoliza;
  }

  public String getTipDocum() {
    return tipDocum;
  }

  public String getNroDocumento() {
    return nroDocumento;
  }

  public String getNomCompleto() {
    return nomCompleto;
  }

  public Date getFecRecepSocio() {
    return fecRecepSocio;
  }

  public Double getImpPagos() {
    return impPagos;
  }

  public Date getFecAprobRechazo() {
    return fecAprobRechazo;
  }

  public String getNomEstado() {
    return nomEstado;
  }

  public String getNroPlanilla() {
    return nroPlanilla;
  }

  public String getNomMoneda() {
    return nomMoneda;
  }

  public String getCodSocio() {
    return codSocio;
  }

  public Date getFecUltDocumentacion() {
    return fecUltDocumentacion;
  }

  public String getTipRefCafae() {
    return tipRefCafae;
  }

  public String getRefCafae() {
    return refCafae;
  }

  public String getUsuCreacion() {
    return usuCreacion;
  }

  public Date getFecCreacion() {
    return fecCreacion;
  }

  public Date getFecUltDocumentacionSocio() {
	return fecUltDocumentacionSocio;
   }

  public void setNroSiniestro(String nroSiniestro) {
    this.nroSiniestro = nroSiniestro;
  }

  public void setFecOcurrencia(Date fecOcurrencia) {
    this.fecOcurrencia = fecOcurrencia;
  }

  public void setNomSocio(String nomSocio) {
    this.nomSocio = nomSocio;
  }

  public void setNomCobertura(String nomCobertura) {
    this.nomCobertura = nomCobertura;
  }

  public void setNomProducto(String nomProducto) {
    this.nomProducto = nomProducto;
  }

  public void setNroPoliza(String nroPoliza) {
    this.nroPoliza = nroPoliza;
  }

  public void setTipDocum(String tipDocum) {
    this.tipDocum = tipDocum;
  }

  public void setNroDocumento(String nroDocumento) {
    this.nroDocumento = nroDocumento;
  }

  public void setNomCompleto(String nomCompleto) {
    this.nomCompleto = nomCompleto;
  }

  public void setFecRecepSocio(Date fecRecepSocio) {
    this.fecRecepSocio = fecRecepSocio;
  }

  public void setImpPagos(Double impPagos) {
    this.impPagos = impPagos;
  }

  public void setFecAprobRechazo(Date fecAprobRechazo) {
    this.fecAprobRechazo = fecAprobRechazo;
  }

  public void setNomEstado(String nomEstado) {
    this.nomEstado = nomEstado;
  }

  public void setNroPlanilla(String nroPlanilla) {
    this.nroPlanilla = nroPlanilla;
  }

  public void setNomMoneda(String nomMoneda) {
    this.nomMoneda = nomMoneda;
  }

  public void setCodSocio(String codSocio) {
    this.codSocio = codSocio;
  }

  public void setFecUltDocumentacion(Date fecUltDocumentacion) {
    this.fecUltDocumentacion = fecUltDocumentacion;
  }

  public void setTipRefCafae(String tipRefCafae) {
    this.tipRefCafae = tipRefCafae;
  }

  public void setRefCafae(String refCafae) {
    this.refCafae = refCafae;
  }

  public void setUsuCreacion(String usuCreacion) {
    this.usuCreacion = usuCreacion;
  }

  public void setFecCreacion(Date fecCreacion) {
    this.fecCreacion = fecCreacion;
  }

  public boolean isIdCheck() {
    return idCheck;
  }

  public void setIdCheck(boolean idCheck) {
    this.idCheck = idCheck;
  }
  
  public void setFecUltDocumentacionSocio(Date fecUltDocumentacionSocio) {
	this.fecUltDocumentacionSocio = fecUltDocumentacionSocio;
  }


}
