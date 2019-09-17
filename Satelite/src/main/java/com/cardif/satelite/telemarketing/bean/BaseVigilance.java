package com.cardif.satelite.telemarketing.bean;

import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;

import com.cardif.satelite.ssis.service.impl.SsisServiceImpl;

public class BaseVigilance implements Serializable {

  private static final long serialVersionUID = 2852225547439351778L;
  public static final Logger LOGGER = Logger.getLogger(SsisServiceImpl.class);

  private Long numRegistros;

  private Date fecCreacion;

  private String estado;

  private String usuCreacion;

  public Long getNumRegistros() {
    return numRegistros;
  }

  public void setNumRegistros(Long numRegistros) {
    this.numRegistros = numRegistros;
  }

  public Date getFecCreacion() {
    return fecCreacion;
  }

  public void setFecCreacion(Date fecCreacion) {
    this.fecCreacion = fecCreacion;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getUsuCreacion() {
    return usuCreacion;
  }

  public void setUsuCreacion(String usuCreacion) {
    this.usuCreacion = usuCreacion;
  }

}
