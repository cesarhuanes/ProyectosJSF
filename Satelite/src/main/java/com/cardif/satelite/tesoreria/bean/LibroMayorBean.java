package com.cardif.satelite.tesoreria.bean;

import java.io.Serializable;
import java.util.Date;

public class LibroMayorBean implements Serializable {
  private static final long serialVersionUID = 1L;

  private String codOperacion;

  private Date fecOperacion;
  private String desOperacion;
  private String codLibro;

  private String numCorrelativo;
  /*************************************/
  private String numCorrAsientoCtble;
  private String codPlanCtas;
  private String numCorrelativoVentas;
  private String numCorrelativoCompras;
  private String numCorrelativoConsig;
  /*************************************/
  private String debe;
  private String haber;
  private String estadoOperacion;

  public String getCodOperacion() {
    return codOperacion;
  }

  public void setCodOperacion(String codOperacion) {
    this.codOperacion = codOperacion == null ? null : codOperacion.trim();
  }

  public Date getFecOperacion() {
    return fecOperacion;
  }

  public void setFecOperacion(Date fecOperacion) {
    this.fecOperacion = fecOperacion;
  }

  public String getDesOperacion() {
    return desOperacion;
  }

  public void setDesOperacion(String desOperacion) {
    this.desOperacion = desOperacion;
  }

  public String getCodLibro() {
    return codLibro;
  }

  public void setCodLibro(String codLibro) {
    this.codLibro = codLibro;
  }

  public String getNumCorrelativo() {
    return numCorrelativo;
  }

  public void setNumCorrelativo(String numCorrelativo) {
    this.numCorrelativo = numCorrelativo;
  }

  public String getDebe() {
    return debe;
  }

  public void setDebe(String debe) {
    this.debe = debe;
  }

  public String getHaber() {
    return haber;
  }

  public void setHaber(String haber) {
    this.haber = haber;
  }

  public String getEstadoOperacion() {
    return estadoOperacion;
  }

  public void setEstadoOperacion(String estadoOperacion) {
    this.estadoOperacion = estadoOperacion;
  }

  public String getCodPlanCtas() {
    return codPlanCtas;
  }

  public void setCodPlanCtas(String codPlanCtas) {
    this.codPlanCtas = codPlanCtas;
  }

  public String getNumCorrelativoVentas() {
    return numCorrelativoVentas;
  }

  public void setNumCorrelativoVentas(String numCorrelativoVentas) {
    this.numCorrelativoVentas = numCorrelativoVentas;
  }

  public String getNumCorrelativoCompras() {
    return numCorrelativoCompras;
  }

  public void setNumCorrelativoCompras(String numCorrelativoCompras) {
    this.numCorrelativoCompras = numCorrelativoCompras;
  }

  public String getNumCorrelativoConsig() {
    return numCorrelativoConsig;
  }

  public void setNumCorrelativoConsig(String numCorrelativoConsig) {
    this.numCorrelativoConsig = numCorrelativoConsig;
  }

  public String getNumCorrAsientoCtble() {
    return numCorrAsientoCtble;
  }

  public void setNumCorrAsientoCtble(String numCorrAsientoCtble) {
    this.numCorrAsientoCtble = numCorrAsientoCtble;
  }
}