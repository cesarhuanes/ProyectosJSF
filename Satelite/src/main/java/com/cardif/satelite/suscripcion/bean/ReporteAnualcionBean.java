package com.cardif.satelite.suscripcion.bean;

import java.io.Serializable;

public class ReporteAnualcionBean implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private Long codTrama;
  private String periodo;
  private String fecCarga;
  private String estado;
  private String fecCreacion;
  private String usuCreacion;
  private String fecModificacion;
  private String usuModificacion;

  public Long getCodTrama() {
    return codTrama;
  }

  public String getPeriodo() {
    return periodo;
  }

  public String getFecCarga() {
    return fecCarga;
  }

  public String getEstado() {
    return estado;
  }

  public String getFecCreacion() {
    return fecCreacion;
  }

  public String getUsuCreacion() {
    return usuCreacion;
  }

  public String getFecModificacion() {
    return fecModificacion;
  }

  public String getUsuModificacion() {
    return usuModificacion;
  }

  public void setCodTrama(Long codTrama) {
    this.codTrama = codTrama;
  }

  public void setPeriodo(String periodo) {
    this.periodo = periodo;
  }

  public void setFecCarga(String fecCarga) {
    this.fecCarga = fecCarga;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public void setFecCreacion(String fecCreacion) {
    this.fecCreacion = fecCreacion;
  }

  public void setUsuCreacion(String usuCreacion) {
    this.usuCreacion = usuCreacion;
  }

  public void setFecModificacion(String fecModificacion) {
    this.fecModificacion = fecModificacion;
  }

  public void setUsuModificacion(String usuModificacion) {
    this.usuModificacion = usuModificacion;
  }

  public ReporteAnualcionBean(Long codTrama, String periodo, String fecCarga, String estado, String fecCreacion, String usuCreacion, String fecModificacion, String usuModificacion) {
    super();
    this.codTrama = codTrama;
    this.periodo = periodo;
    this.fecCarga = fecCarga;
    this.estado = estado;
    this.fecCreacion = fecCreacion;
    this.usuCreacion = usuCreacion;
    this.fecModificacion = fecModificacion;
    this.usuModificacion = usuModificacion;
  }

}
