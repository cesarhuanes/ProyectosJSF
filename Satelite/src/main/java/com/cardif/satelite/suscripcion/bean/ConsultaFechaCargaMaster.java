package com.cardif.satelite.suscripcion.bean;

import java.io.Serializable;
import java.util.Date;

public class ConsultaFechaCargaMaster implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private int cantidad;
  private Date fecValidez;

  public int getCantidad() {
    return cantidad;
  }

  public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
  }

  public Date getFecValidez() {
    return fecValidez;
  }

  public void setFecValidez(Date fecValidez) {
    this.fecValidez = fecValidez;
  }

}
