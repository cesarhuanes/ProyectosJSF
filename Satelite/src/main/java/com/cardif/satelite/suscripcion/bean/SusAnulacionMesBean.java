package com.cardif.satelite.suscripcion.bean;

import java.io.Serializable;
import java.util.Date;

public class SusAnulacionMesBean implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  String nroTicket;
  Date fecIngreso;
  Date fecReclamo;
  int nroReclamo;
  Date fecAtencion;
  Date fecModificacion;
  int nroducto;
  String entidad;
  String imagen;
  String autonomia;
  int nroCertificado;
  int nroCertificadoBanco;
  String nomApeCliente;
  int dni;
  int nroCuenta;
  String motReclamo;
  String estado;
  int perInicioVigencia;
  int perFinVigencia;
  int nroCuotas;
  double totDevolver;
  String moneda;
  Date fecExtorno;
  String opeExtorno;
  int codRF;
  String nomRF;

  public String getNroTicket() {
    return nroTicket;
  }

  public Date getFecIngreso() {
    return fecIngreso;
  }

  public Date getFecReclamo() {
    return fecReclamo;
  }

  public int getNroReclamo() {
    return nroReclamo;
  }

  public Date getFecAtencion() {
    return fecAtencion;
  }

  public Date getFecModificacion() {
    return fecModificacion;
  }

  public int getNroducto() {
    return nroducto;
  }

  public String getEntidad() {
    return entidad;
  }

  public String getImagen() {
    return imagen;
  }

  public String getAutonomia() {
    return autonomia;
  }

  public int getNroCertificado() {
    return nroCertificado;
  }

  public int getNroCertificadoBanco() {
    return nroCertificadoBanco;
  }

  public String getNomApeCliente() {
    return nomApeCliente;
  }

  public int getDni() {
    return dni;
  }

  public int getNroCuenta() {
    return nroCuenta;
  }

  public String getMotReclamo() {
    return motReclamo;
  }

  public String getEstado() {
    return estado;
  }

  public int getPerInicioVigencia() {
    return perInicioVigencia;
  }

  public int getPerFinVigencia() {
    return perFinVigencia;
  }

  public int getNroCuotas() {
    return nroCuotas;
  }

  public double getTotDevolver() {
    return totDevolver;
  }

  public String getMoneda() {
    return moneda;
  }

  public Date getFecExtorno() {
    return fecExtorno;
  }

  public String getOpeExtorno() {
    return opeExtorno;
  }

  public int getCodRF() {
    return codRF;
  }

  public String getNomRF() {
    return nomRF;
  }

  public String getCodOficina() {
    return codOficina;
  }

  public String getNomOficina() {
    return nomOficina;
  }

  public String getNomTerritorio() {
    return nomTerritorio;
  }

  public Date getFecDigitacion() {
    return fecDigitacion;
  }

  public void setNroTicket(String nroTicket) {
    this.nroTicket = nroTicket;
  }

  public void setFecIngreso(Date fecIngreso) {
    this.fecIngreso = fecIngreso;
  }

  public void setFecReclamo(Date fecReclamo) {
    this.fecReclamo = fecReclamo;
  }

  public void setNroReclamo(int nroReclamo) {
    this.nroReclamo = nroReclamo;
  }

  public void setFecAtencion(Date fecAtencion) {
    this.fecAtencion = fecAtencion;
  }

  public void setFecModificacion(Date fecModificacion) {
    this.fecModificacion = fecModificacion;
  }

  public void setNroducto(int nroducto) {
    this.nroducto = nroducto;
  }

  public void setEntidad(String entidad) {
    this.entidad = entidad;
  }

  public void setImagen(String imagen) {
    this.imagen = imagen;
  }

  public void setAutonomia(String autonomia) {
    this.autonomia = autonomia;
  }

  public void setNroCertificado(int nroCertificado) {
    this.nroCertificado = nroCertificado;
  }

  public void setNroCertificadoBanco(int nroCertificadoBanco) {
    this.nroCertificadoBanco = nroCertificadoBanco;
  }

  public void setNomApeCliente(String nomApeCliente) {
    this.nomApeCliente = nomApeCliente;
  }

  public void setDni(int dni) {
    this.dni = dni;
  }

  public void setNroCuenta(int nroCuenta) {
    this.nroCuenta = nroCuenta;
  }

  public void setMotReclamo(String motReclamo) {
    this.motReclamo = motReclamo;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public void setPerInicioVigencia(int perInicioVigencia) {
    this.perInicioVigencia = perInicioVigencia;
  }

  public void setPerFinVigencia(int perFinVigencia) {
    this.perFinVigencia = perFinVigencia;
  }

  public void setNroCuotas(int nroCuotas) {
    this.nroCuotas = nroCuotas;
  }

  public void setTotDevolver(double totDevolver) {
    this.totDevolver = totDevolver;
  }

  public void setMoneda(String moneda) {
    this.moneda = moneda;
  }

  public void setFecExtorno(Date fecExtorno) {
    this.fecExtorno = fecExtorno;
  }

  public void setOpeExtorno(String opeExtorno) {
    this.opeExtorno = opeExtorno;
  }

  public void setCodRF(int codRF) {
    this.codRF = codRF;
  }

  public void setNomRF(String nomRF) {
    this.nomRF = nomRF;
  }

  public void setCodOficina(String codOficina) {
    this.codOficina = codOficina;
  }

  public void setNomOficina(String nomOficina) {
    this.nomOficina = nomOficina;
  }

  public void setNomTerritorio(String nomTerritorio) {
    this.nomTerritorio = nomTerritorio;
  }

  public void setFecDigitacion(Date fecDigitacion) {
    this.fecDigitacion = fecDigitacion;
  }

  String codOficina;
  String nomOficina;
  String nomTerritorio;
  Date fecDigitacion;

}
