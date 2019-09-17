package com.cardif.satelite.tesoreria.model;

import java.io.Serializable;

public class Firmante implements Serializable {

  private static final long serialVersionUID = 2221909030961526126L;

  private Long temporal;

  private Long codigo;

  private String usuario;

  private String nombres;

  private String apellidos;

  private String correo;

  private byte[] clave;

  private String estado;

  private String nombreAccion;

  private String motivoAccion;

  private String estadoAccion;

  private String motivoRechazo;

  private String motivoEliminacion;

  private byte[] firma;

  private byte[] firmaDesprotegida;

  public Firmante() {
    super();
  }

  public Firmante(Long codigo, String usuario, String nombres, String apellidos, String correo, byte[] clave, String estado, byte[] firma) {
    super();
    this.codigo = codigo;
    this.usuario = usuario;
    this.nombres = nombres;
    this.apellidos = apellidos;
    this.correo = correo;
    this.clave = clave;
    this.estado = estado;
    this.firma = firma;
  }

  public Long getCodigo() {
    return codigo;
  }

  public void setCodigo(Long codigo) {
    this.codigo = codigo;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getNombres() {
    return nombres;
  }

  public void setNombres(String nombres) {
    this.nombres = nombres;
  }

  public String getApellidos() {
    return apellidos;
  }

  public void setApellidos(String apellidos) {
    this.apellidos = apellidos;
  }

  public String getCorreo() {
    return correo;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  public byte[] getClave() {
    return clave;
  }

  public void setClave(byte[] clave) {
    this.clave = clave;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public byte[] getFirma() {
    return firma;
  }

  public void setFirma(byte[] firma) {
    this.firma = firma;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(nombres);
    builder.append(" ");
    builder.append(apellidos);
    return builder.toString();
  }

  public byte[] getFirmaDesprotegida() {
    return firmaDesprotegida;
  }

  public void setFirmaDesprotegida(byte[] firmaDesprotegida) {
    this.firmaDesprotegida = firmaDesprotegida;
  }

  public Long getTemporal() {
    return temporal;
  }

  public void setTemporal(Long temporal) {
    this.temporal = temporal;
  }

  public String getMotivoAccion() {
    return motivoAccion;
  }

  public void setMotivoAccion(String motivoAccion) {
    this.motivoAccion = motivoAccion;
  }

  public String getMotivoRechazo() {
    return motivoRechazo;
  }

  public void setMotivoRechazo(String motivoRechazo) {
    this.motivoRechazo = motivoRechazo;
  }

  public String getNombreAccion() {
    return nombreAccion;
  }

  public void setNombreAccion(String nombreAccion) {
    this.nombreAccion = nombreAccion;
  }

  public String getEstadoAccion() {
    return estadoAccion;
  }

  public void setEstadoAccion(String estadoAccion) {
    this.estadoAccion = estadoAccion;
  }

  public String getMotivoEliminacion() {
    return motivoEliminacion;
  }

  public void setMotivoEliminacion(String motivoEliminacion) {
    this.motivoEliminacion = motivoEliminacion;
  }

}