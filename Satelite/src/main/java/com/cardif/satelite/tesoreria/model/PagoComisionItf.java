package com.cardif.satelite.tesoreria.model;

import java.util.Date;

public class PagoComisionItf {
  private String nom_excel;

  private String usu_creacion;

  private Date fec_creacion;

  private String usu_modificacion;

  private Date fec_modificacion;

  public String getNom_excel() {
    return nom_excel;
  }

  public void setNom_excel(String nom_excel) {
    this.nom_excel = nom_excel;
  }

  public String getUsu_creacion() {
    return usu_creacion;
  }

  public void setUsu_creacion(String usu_creacion) {
    this.usu_creacion = usu_creacion;
  }

  public Date getFec_creacion() {
    return fec_creacion;
  }

  public void setFec_creacion(Date fec_creacion) {
    this.fec_creacion = fec_creacion;
  }

  public String getUsu_modificacion() {
    return usu_modificacion;
  }

  public void setUsu_modificacion(String usu_modificacion) {
    this.usu_modificacion = usu_modificacion;
  }

  public Date getFec_modificacion() {
    return fec_modificacion;
  }

  public void setFec_modificacion(Date fec_modificacion) {
    this.fec_modificacion = fec_modificacion;
  }
}