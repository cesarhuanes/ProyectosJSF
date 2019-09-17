package com.cardif.satelite.soat.model;

import java.io.Serializable;
import java.util.Date;

public class BitacoraSoat implements Serializable {

  private static final long serialVersionUID = 1L;
  private int codigoBitacora;
  private String nombreBaseDatos;
  private String nombreTabla;
  private String operacion;
  private String usuario;
  private Date fecha;
  private String valorInicial;
  private String valorFinal;

  public int getCodigoBitacora() {
    return codigoBitacora;
  }

  public void setCodigoBitacora(int codigoBitacora) {
    this.codigoBitacora = codigoBitacora;
  }

  public String getNombreBaseDatos() {
    return nombreBaseDatos;
  }

  public void setNombreBaseDatos(String nombreBaseDatos) {
    this.nombreBaseDatos = nombreBaseDatos;
  }

  public String getNombreTabla() {
    return nombreTabla;
  }

  public void setNombreTabla(String nombreTabla) {
    this.nombreTabla = nombreTabla;
  }

  public String getOperacion() {
    return operacion;
  }

  public void setOperacion(String operacion) {
    this.operacion = operacion;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public String getValorInicial() {
    return valorInicial;
  }

  public void setValorInicial(String valorInicial) {
    this.valorInicial = valorInicial;
  }

  public String getValorFinal() {
    return valorFinal;
  }

  public void setValorFinal(String valorFinal) {
    this.valorFinal = valorFinal;
  }

}
