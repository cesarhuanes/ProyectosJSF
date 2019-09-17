package com.cardif.satelite.callcenter.bean;

import java.io.Serializable;

/*
 * @usuario lmaron
 * @fecha_creacion 30/01/2014
 * @Ticket:  
 */
public class ConsultaRegVehicular implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private Long id;
  private String placa;
  private String marca;
  private String modelo;
  private String serie;
  private Long anioFabricacion;
  private String uso;
  private String categoria;
  private Long nroAsiento;
  private String departamento;
  private String propietario;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPlaca() {
    return placa;
  }

  public void setPlaca(String placa) {
    this.placa = placa;
  }

  public String getMarca() {
    return marca;
  }

  public void setMarca(String marca) {
    this.marca = marca;
  }

  public String getModelo() {
    return modelo;
  }

  public void setModelo(String modelo) {
    this.modelo = modelo;
  }

  public String getSerie() {
    return serie;
  }

  public void setSerie(String serie) {
    this.serie = serie;
  }

  public Long getAnioFabricacion() {
    return anioFabricacion;
  }

  public void setAnioFabricacion(Long anioFabricacion) {
    this.anioFabricacion = anioFabricacion;
  }

  public String getUso() {
    return uso;
  }

  public void setUso(String uso) {
    this.uso = uso;
  }

  public String getCategoria() {
    return categoria;
  }

  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }

  public Long getNroAsiento() {
    return nroAsiento;
  }

  public void setNroAsiento(Long nroAsiento) {
    this.nroAsiento = nroAsiento;
  }

  public String getDepartamento() {
    return departamento;
  }

  public void setDepartamento(String departamento) {
    this.departamento = departamento;
  }

  public String getPropietario() {
    return propietario;
  }

  public void setPropietario(String propietario) {
    this.propietario = propietario;
  }

}
