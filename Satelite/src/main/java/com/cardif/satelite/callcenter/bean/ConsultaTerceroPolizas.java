package com.cardif.satelite.callcenter.bean;

import java.io.Serializable;
import java.util.Date;

public class ConsultaTerceroPolizas implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private String numdoc;// codeIdentifier
  private String asegurado;// name

  private String estado;// STATE_DESCRIPCION
  private String contratante;// Contratante: PARTNERNAME
  private String producto;// Producto: PRODUCTNAME
  private String numPoliza;// Num. de Poliza: policyNumber
  private Date fecInicio;// : initialDate
  private Date fecFin;// finalDate

  private String idPoliza;// id de Poliza: id

  public ConsultaTerceroPolizas() {
    super();
  }

  public ConsultaTerceroPolizas(String numdoc, String asegurado, String estado, String contratante, String producto, String numPoliza, Date fecInicio, Date fecFin, String idPoliza) {
    super();
    this.numdoc = numdoc;
    this.asegurado = asegurado;
    this.estado = estado;
    this.contratante = contratante;
    this.producto = producto;
    this.numPoliza = numPoliza;
    this.fecInicio = fecInicio;
    this.fecFin = fecFin;
    this.idPoliza = idPoliza;
  }

  public String getNumdoc() {
    return numdoc;
  }

  public void setNumdoc(String numdoc) {
    this.numdoc = numdoc;
  }

  public String getAsegurado() {
    return asegurado;
  }

  public void setAsegurado(String asegurado) {
    this.asegurado = asegurado;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getContratante() {
    return contratante;
  }

  public void setContratante(String contratante) {
    this.contratante = contratante;
  }

  public String getProducto() {
    return producto;
  }

  public void setProducto(String producto) {
    this.producto = producto;
  }

  public String getNumPoliza() {
    return numPoliza;
  }

  public void setNumPoliza(String numPoliza) {
    this.numPoliza = numPoliza;
  }

  public String getIdPoliza() {
    return idPoliza;
  }

  public void setIdPoliza(String idPoliza) {
    this.idPoliza = idPoliza;
  }

  public Date getFecInicio() {
    return fecInicio;
  }

  public void setFecInicio(Date feInicio) {
    this.fecInicio = feInicio;
  }

  public Date getFecFin() {
    return fecFin;
  }

  public void setFecFin(Date fecFin) {
    this.fecFin = fecFin;
  }

}
