package com.cardif.satelite.tesoreria.bean;

import java.io.Serializable;

public class ProveedorBean implements Serializable {
  private static final long serialVersionUID = -4369561524437343319L;
  private String codigoCtaProveedor;
  private String ruc;
  private String nomProveedor;
  private String glosa;
  private String referencia;
  private String pais;
  private String provincia;
  private String departamento;
  private String distrito;
  private String ciudad;
  private String ubigeo;
  private String urbanizacion;
  private String tipoDocIdentidad;
  public String glosaInicial;
  public String direccion;
  public String correoElectronico;
  
  public ProveedorBean() {

  }

  public ProveedorBean(String ruc, String nomProveedor) {
    this.nomProveedor = nomProveedor;
    this.ruc = ruc;
    this.glosa = nomProveedor + " ";
    this.glosaInicial = " " + nomProveedor;
  }

  public ProveedorBean(String nomProveedor, String glosa, String referencia) {
    this.nomProveedor = nomProveedor;
    this.glosa = glosa;
    this.glosaInicial = glosa;
    this.referencia = referencia;
  }

  public String getNomProveedor() {
    return nomProveedor;
  }

  public void setNomProveedor(String nomProveedor) {
    this.nomProveedor = nomProveedor;
  }

  public String getGlosa() {
    return glosa;
  }

  public void setGlosa(String glosa) {
    this.glosa = glosa;
  }

  public String getReferencia() {
    return referencia;
  }

  public void setReferencia(String referencia) {
    this.referencia = referencia;
  }

  public String getRuc() {
    return ruc;
  }

  public void setRuc(String ruc) {
    this.ruc = ruc;
  }

  public String getGlosaInicial() {
    return glosaInicial;
  }

  public void setGlosaInicial(String glosaInicial) {
    this.glosaInicial = glosaInicial;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public String getCorreoElectronico() {
    return correoElectronico;
  }

  public void setCorreoElectronico(String correoElectronico) {
    this.correoElectronico = correoElectronico;
  }

  public String getCodigoCtaProveedor() {
    return codigoCtaProveedor;
  }

  public void setCodigoCtaProveedor(String codigoCtaProveedor) {
    this.codigoCtaProveedor = codigoCtaProveedor;
  }

  public String getPais() {
    return pais;
  }

  public void setPais(String pais) {
    this.pais = pais;
  }

  public String getProvincia() {
    return provincia;
  }

  public void setProvincia(String provincia) {
    this.provincia = provincia;
  }

  public String getDepartamento() {
    return departamento;
  }

  public void setDepartamento(String departamento) {
    this.departamento = departamento;
  }

  public String getDistrito() {
    return distrito;
  }

  public void setDistrito(String distrito) {
    this.distrito = distrito;
  }

  public String getCiudad() {
    return ciudad;
  }

  public void setCiudad(String ciudad) {
    this.ciudad = ciudad;
  }

  public String getUbigeo() {
    return ubigeo;
  }

  public void setUbigeo(String ubigeo) {
    this.ubigeo = ubigeo;
  }

  public String getUrbanizacion() {
    return urbanizacion;
  }

  public void setUrbanizacion(String urbanizacion) {
    this.urbanizacion = urbanizacion;
  }

  public String getTipoDocIdentidad() {
    return tipoDocIdentidad;
  }

  public void setTipoDocIdentidad(String tipoDocIdentidad) {
    this.tipoDocIdentidad = tipoDocIdentidad;
  }
}
