package com.cardif.satelite.model.satelite;

import java.io.Serializable;

public class TipoTrabajador implements Serializable {

  private static final long serialVersionUID = 1L;

  private String codTipoTrabajador;
  private String modalidadTrabajador;
  private String descripcion;

  public String getCodTipoTrabajador() {
    return codTipoTrabajador;
  }

  public void setCodTipoTrabajador(String codTipoTrabajador) {
    this.codTipoTrabajador = codTipoTrabajador == null ? null : codTipoTrabajador.trim();
    ;
  }

  public String getModalidadTrabajador() {
    return modalidadTrabajador;
  }

  public void setModalidadTrabajador(String modalidadTrabajador) {
    this.modalidadTrabajador = modalidadTrabajador == null ? null : modalidadTrabajador.trim();
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion == null ? null : descripcion.trim();
    ;

  }

}
