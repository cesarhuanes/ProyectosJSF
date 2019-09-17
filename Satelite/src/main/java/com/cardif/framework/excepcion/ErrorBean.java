package com.cardif.framework.excepcion;

import java.io.Serializable;

public class ErrorBean implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String codigo;
  private String descripcion;

  /**
   * @param codigo
   * @param descripcion
   */
  public ErrorBean(String codigo, String descripcion) {
    super();
    this.codigo = codigo;
    this.descripcion = descripcion;
  }

  public ErrorBean() {
    super();

  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer("");
    sb.append("[");
    sb.append(this.codigo);
    sb.append("] - ");
    sb.append(this.descripcion);
    return sb.toString();
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

}