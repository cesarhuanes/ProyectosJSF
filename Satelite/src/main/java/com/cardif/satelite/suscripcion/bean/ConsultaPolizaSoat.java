package com.cardif.satelite.suscripcion.bean;

import java.io.Serializable;
import java.util.Date;

public class ConsultaPolizaSoat implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String socio;
  private String placa;
  private String poliza;
  // private Long numDocumento;
  private String numDocumento;
  private Date iniVigencia;
  private String nomContratante;
  private String apePatContratante;
  private String apeMatContratante;
  /********************** export ******************************/
  private String direccion;
  private String numCertificado;
  private String telFijo;
  private String telMovil;
  private String usoVehiculo;
  private String marca;
  private String modImpresion;
  private String clase;
  private Integer anioFabricacion;
  private Integer numAsientos;
  private String serie;
  private Date fecVenta;
  private Date finVigencia;
  private String canalVenta;
  private String estado;

  public String getSocio() {
    return socio;
  }

  public void setSocio(String socio) {
    this.socio = socio;
  }

  public String getPlaca() {
    return placa;
  }

  public void setPlaca(String placa) {
    this.placa = placa;
  }

  public String getPoliza() {
    return poliza;
  }

  public void setPoliza(String poliza) {
    this.poliza = poliza;
  }

  public Date getIniVigencia() {
    return iniVigencia;
  }

  public void setIniVigencia(Date iniVigencia) {
    this.iniVigencia = iniVigencia;
  }

  public String getNomContratante() {
    return nomContratante;
  }

  public void setNomContratante(String nomContratante) {
    this.nomContratante = nomContratante;
  }

  public String getApePatContratante() {
    return apePatContratante;
  }

  public void setApePatContratante(String apePatContratante) {
    this.apePatContratante = apePatContratante;
  }

  public String getApeMatContratante() {
    return apeMatContratante;
  }

  public void setApeMatContratante(String apeMatContratante) {
    this.apeMatContratante = apeMatContratante;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public String getTelFijo() {
    return telFijo;
  }

  public void setTelFijo(String telFijo) {
    this.telFijo = telFijo;
  }

  public String getTelMovil() {
    return telMovil;
  }

  public void setTelMovil(String telMovil) {
    this.telMovil = telMovil;
  }

  public String getUsoVehiculo() {
    return usoVehiculo;
  }

  public void setUsoVehiculo(String usoVehiculo) {
    this.usoVehiculo = usoVehiculo;
  }

  public String getMarca() {
    return marca;
  }

  public void setMarca(String marca) {
    this.marca = marca;
  }

  public String getModImpresion() {
    return modImpresion;
  }

  public void setModImpresion(String modImpresion) {
    this.modImpresion = modImpresion;
  }

  public String getClase() {
    return clase;
  }

  public void setClase(String clase) {
    this.clase = clase;
  }

  public Integer getAnioFabricacion() {
    return anioFabricacion;
  }

  public void setAnioFabricacion(Integer anioFabricacion) {
    this.anioFabricacion = anioFabricacion;
  }

  public Integer getNumAsientos() {
    return numAsientos;
  }

  public void setNumAsientos(Integer numAsientos) {
    this.numAsientos = numAsientos;
  }

  public String getSerie() {
    return serie;
  }

  public void setSerie(String serie) {
    this.serie = serie;
  }

  public Date getFecVenta() {
    return fecVenta;
  }

  public void setFecVenta(Date fecVenta) {
    this.fecVenta = fecVenta;
  }

  public Date getFinVigencia() {
    return finVigencia;
  }

  public void setFinVigencia(Date finVigencia) {
    this.finVigencia = finVigencia;
  }

  public String getCanalVenta() {
    return canalVenta;
  }

  public void setCanalVenta(String canalVenta) {
    this.canalVenta = canalVenta;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getNumCertificado() {
    return numCertificado;
  }

  public void setNumCertificado(String numCertificado) {
    this.numCertificado = numCertificado;
  }

  public String getNumDocumento() {
    return numDocumento;
  }

  public void setNumDocumento(String numDocumento) {
    this.numDocumento = numDocumento;
  }

}
