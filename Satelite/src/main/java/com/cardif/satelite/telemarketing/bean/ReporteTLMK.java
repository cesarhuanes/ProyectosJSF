package com.cardif.satelite.telemarketing.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ReporteTLMK implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private int codProducto;
  private BigDecimal codSocio;
  private String producto;
  private String numPoliza;
  private String codPersona;
  private String docIdentidad;
  private String nombreCliente;
  private String apellidoPaterno;
  private String apellidoMaterno;
  private Date fecIniVigencia;
  private Date fecFinVigencia;
  private String permanencia;
  private String dirPersona;
  private String descDistrito;
  private String descProvincia;
  private String descDepartamento;
  private Double importePrimaEmitida;
  private String telefono;
  private String otrosProductos;
  private BigDecimal codPoliza;
  private int cuotasImpagas;

  public int getCodProducto() {
    return codProducto;
  }

  public void setCodProducto(int codProducto) {
    this.codProducto = codProducto;
  }

  public BigDecimal getCodSocio() {
    return codSocio;
  }

  public void setCodSocio(BigDecimal codSocio) {
    this.codSocio = codSocio;
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

  public String getCodPersona() {
    return codPersona;
  }

  public void setCodPersona(String codPersona) {
    this.codPersona = codPersona;
  }

  public String getDocIdentidad() {
    return docIdentidad;
  }

  public void setDocIdentidad(String docIdentidad) {
    this.docIdentidad = docIdentidad;
  }

  public String getNombreCliente() {
    return nombreCliente;
  }

  public void setNombreCliente(String nombreCliente) {
    this.nombreCliente = nombreCliente;
  }

  public String getApellidoPaterno() {
    return apellidoPaterno;
  }

  public void setApellidoPaterno(String apellidoPaterno) {
    this.apellidoPaterno = apellidoPaterno;
  }

  public String getApellidoMaterno() {
    return apellidoMaterno;
  }

  public void setApellidoMaterno(String apellidoMaterno) {
    this.apellidoMaterno = apellidoMaterno;
  }

  public Date getFecIniVigencia() {
    return fecIniVigencia;
  }

  public void setFecIniVigencia(Date fecIniVigencia) {
    this.fecIniVigencia = fecIniVigencia;
  }

  public Date getFecFinVigencia() {
    return fecFinVigencia;
  }

  public void setFecFinVigencia(Date fecFinVigencia) {
    this.fecFinVigencia = fecFinVigencia;
  }

  public String getPermanencia() {
    return permanencia;
  }

  public void setPermanencia(String permanencia) {
    this.permanencia = permanencia;
  }

  public String getDirPersona() {
    return dirPersona;
  }

  public void setDirPersona(String dirPersona) {
    this.dirPersona = dirPersona;
  }

  public String getDescDistrito() {
    return descDistrito;
  }

  public void setDescDistrito(String descDistrito) {
    this.descDistrito = descDistrito;
  }

  public String getDescProvincia() {
    return descProvincia;
  }

  public void setDescProvincia(String descProvincia) {
    this.descProvincia = descProvincia;
  }

  public String getDescDepartamento() {
    return descDepartamento;
  }

  public void setDescDepartamento(String descDepartamento) {
    this.descDepartamento = descDepartamento;
  }

  public Double getImportePrimaEmitida() {
    return importePrimaEmitida;
  }

  public void setImportePrimaEmitida(Double importePrimaEmitida) {
    this.importePrimaEmitida = importePrimaEmitida;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getOtrosProductos() {
    return otrosProductos;
  }

  public void setOtrosProductos(String otrosProductos) {
    this.otrosProductos = otrosProductos;
  }

  public BigDecimal getCodPoliza() {
    return codPoliza;
  }

  public void setCodPoliza(BigDecimal codPoliza) {
    this.codPoliza = codPoliza;
  }

  public int getCuotasImpagas() {
    return cuotasImpagas;
  }

  public void setCuotasImpagas(int cuotasImpagas) {
    this.cuotasImpagas = cuotasImpagas;
  }

}
