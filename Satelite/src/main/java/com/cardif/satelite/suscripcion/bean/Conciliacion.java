package com.cardif.satelite.suscripcion.bean;

import java.io.Serializable;
import java.util.Date;

public class Conciliacion implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private int id;
  private String periodo;
  private int cantRegistros;
  private Date fecProceso;
  private String estado;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getPeriodo() {
    return periodo;
  }

  public void setPeriodo(String periodo) {
    this.periodo = periodo;
  }

  public int getCantRegistros() {
    return cantRegistros;
  }

  public void setCantRegistros(int cantRegistros) {
    this.cantRegistros = cantRegistros;
  }

  public Date getFecProceso() {
    return fecProceso;
  }

  public void setFecProceso(Date fecProceso) {
    this.fecProceso = fecProceso;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

}