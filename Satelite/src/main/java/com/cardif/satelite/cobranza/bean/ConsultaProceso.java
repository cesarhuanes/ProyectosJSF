package com.cardif.satelite.cobranza.bean;

import java.io.Serializable;
import java.util.Date;

public class ConsultaProceso implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private int numero;
  private String tipo;
  private String nombre;
  private String estado;
  private Date fechaIni;
  private Date fechaFin;
  private String pathArchivos;
  private String nombreArchivo;
  private String descripcionError;

  public ConsultaProceso() {
    super();
  }

  public int getNumero() {
    return numero;
  }

  public void setNumero(int numero) {
    this.numero = numero;
  }

  public String getTipo() {
    return tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public Date getFechaIni() {
    return fechaIni;
  }

  public void setFechaIni(Date fechaIni) {
    this.fechaIni = fechaIni;
  }

  public Date getFechaFin() {
    return fechaFin;
  }

  public void setFechaFin(Date fechaFin) {
    this.fechaFin = fechaFin;
  }

  public String getPathArchivos() {
    return pathArchivos;
  }

  public void setPathArchivos(String pathArchivos) {
    this.pathArchivos = pathArchivos;
    if (pathArchivos != null) {
      setNombreArchivo("Descargar");
    }
  }

  public String getNombreArchivo() {
    return nombreArchivo;
  }

  public void setNombreArchivo(String nombreArchivo) {
    this.nombreArchivo = nombreArchivo;
  }

  public String getDescripcionError() {
    return descripcionError;
  }

  public void setDescripcionError(String descripcionError) {
    this.descripcionError = descripcionError;
  }

}