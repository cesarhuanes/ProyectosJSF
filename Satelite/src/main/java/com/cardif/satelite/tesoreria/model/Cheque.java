package com.cardif.satelite.tesoreria.model;

import java.math.BigDecimal;
import java.util.Date;

public class Cheque {
  private Long codigo;
  private Long codigoPar;
  private String banco;
  private String numero;
  private String nombreBeneficiario;
  private String tipoMoneda;
  private BigDecimal importe;
  private String estadoCheque;
  private Date fechaRegistro;
  private String usuarioAsignacion;
  private Date fechaAsignacion;
  private String estadoFirmante1;
  private String usuarioAprobacion1;
  private Date fechaAprobacion1;
  private String estadoFirmante2;
  private String usuarioAprobacion2;
  private Date fechaAprobacion2;
  private String usuarioImpresion;
  private Date fechaImpresion;
  private String motivoRechazo;
  private String usuarioReasignacion;
  private Date fechaReasignacion;
  private String motivoReasignacion;
  private String usuarioAnulacion;
  private Date fechaAnulacion;
  private String motivoAnulacion;
  private String usuarioCreacion;
  private Date fechaCreacion;
  private String usuarioModificacion;
  private Date fechaModificacion;
  private String nombreFirmante1;
  private String nombreFirmante2;
  private Boolean seleccionado;
  private Integer diario;
  private String lineaDiario;
  private Long codigoFirmante1;
  private Long codigoFirmante2;
  private String correo1;
  private String correo2;
  private int indicador;

  public Long getCodigoFirmante1() {
    return codigoFirmante1;
  }

  public void setCodigoFirmante1(Long codigoFirmante1) {
    this.codigoFirmante1 = codigoFirmante1;
  }

  public Long getCodigoFirmante2() {
    return codigoFirmante2;
  }

  public void setCodigoFirmante2(Long codigoFirmante2) {
    this.codigoFirmante2 = codigoFirmante2;
  }

  public Long getCodigo() {
    return codigo;
  }

  public void setCodigo(Long codigo) {
    this.codigo = codigo;
  }

  public Long getCodigoPar() {
    return codigoPar;
  }

  public void setCodigoPar(Long codigoPar) {
    this.codigoPar = codigoPar;
  }

  public String getBanco() {
    return banco;
  }

  public void setBanco(String banco) {
    this.banco = banco;
  }

  public String getNumero() {
    return numero;
  }

  public void setNumero(String numero) {
    this.numero = numero;
  }

  public String getNombreBeneficiario() {
    return nombreBeneficiario;
  }

  public void setNombreBeneficiario(String nombreBeneficiario) {
    this.nombreBeneficiario = nombreBeneficiario;
  }

  public String getTipoMoneda() {
    return tipoMoneda;
  }

  public void setTipoMoneda(String tipoMoneda) {
    this.tipoMoneda = tipoMoneda;
  }

  public BigDecimal getImporte() {
    return importe;
  }

  public void setImporte(BigDecimal importe) {
    this.importe = importe;
  }

  public String getEstadoCheque() {
    return estadoCheque;
  }

  public void setEstadoCheque(String estadoCheque) {
    this.estadoCheque = estadoCheque;
  }

  public Date getFechaRegistro() {
    return fechaRegistro;
  }

  public void setFechaRegistro(Date fechaRegistro) {
    this.fechaRegistro = fechaRegistro;
  }

  public String getUsuarioAsignacion() {
    return usuarioAsignacion;
  }

  public void setUsuarioAsignacion(String usuarioAsignacion) {
    this.usuarioAsignacion = usuarioAsignacion;
  }

  public Date getFechaAsignacion() {
    return fechaAsignacion;
  }

  public void setFechaAsignacion(Date fechaAsignacion) {
    this.fechaAsignacion = fechaAsignacion;
  }

  public String getEstadoFirmante1() {
    return estadoFirmante1;
  }

  public void setEstadoFirmante1(String estadoFirmante1) {
    this.estadoFirmante1 = estadoFirmante1;
  }

  public String getUsuarioAprobacion1() {
    return usuarioAprobacion1;
  }

  public void setUsuarioAprobacion1(String usuarioAprobacion1) {
    this.usuarioAprobacion1 = usuarioAprobacion1;
  }

  public Date getFechaAprobacion1() {
    return fechaAprobacion1;
  }

  public void setFechaAprobacion1(Date fechaAprobacion1) {
    this.fechaAprobacion1 = fechaAprobacion1;
  }

  public String getEstadoFirmante2() {
    return estadoFirmante2;
  }

  public void setEstadoFirmante2(String estadoFirmante2) {
    this.estadoFirmante2 = estadoFirmante2;
  }

  public String getUsuarioAprobacion2() {
    return usuarioAprobacion2;
  }

  public void setUsuarioAprobacion2(String usuarioAprobacion2) {
    this.usuarioAprobacion2 = usuarioAprobacion2;
  }

  public Date getFechaAprobacion2() {
    return fechaAprobacion2;
  }

  public void setFechaAprobacion2(Date fechaAprobacion2) {
    this.fechaAprobacion2 = fechaAprobacion2;
  }

  public String getUsuarioImpresion() {
    return usuarioImpresion;
  }

  public void setUsuarioImpresion(String usuarioImpresion) {
    this.usuarioImpresion = usuarioImpresion;
  }

  public Date getFechaImpresion() {
    return fechaImpresion;
  }

  public void setFechaImpresion(Date fechaImpresion) {
    this.fechaImpresion = fechaImpresion;
  }

  public String getMotivoRechazo() {
    return motivoRechazo;
  }

  public void setMotivoRechazo(String motivoRechazo) {
    this.motivoRechazo = motivoRechazo;
  }

  public String getUsuarioReasignacion() {
    return usuarioReasignacion;
  }

  public void setUsuarioReasignacion(String usuarioReasignacion) {
    this.usuarioReasignacion = usuarioReasignacion;
  }

  public Date getFechaReasignacion() {
    return fechaReasignacion;
  }

  public void setFechaReasignacion(Date fechaReasignacion) {
    this.fechaReasignacion = fechaReasignacion;
  }

  public String getMotivoReasignacion() {
    return motivoReasignacion;
  }

  public void setMotivoReasignacion(String motivoReasignacion) {
    this.motivoReasignacion = motivoReasignacion;
  }

  public String getUsuarioAnulacion() {
    return usuarioAnulacion;
  }

  public void setUsuarioAnulacion(String usuarioAnulacion) {
    this.usuarioAnulacion = usuarioAnulacion;
  }

  public Date getFechaAnulacion() {
    return fechaAnulacion;
  }

  public void setFechaAnulacion(Date fechaAnulacion) {
    this.fechaAnulacion = fechaAnulacion;
  }

  public String getMotivoAnulacion() {
    return motivoAnulacion;
  }

  public void setMotivoAnulacion(String motivoAnulacion) {
    this.motivoAnulacion = motivoAnulacion;
  }

  public String getUsuarioCreacion() {
    return usuarioCreacion;
  }

  public void setUsuarioCreacion(String usuarioCreacion) {
    this.usuarioCreacion = usuarioCreacion;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public String getUsuarioModificacion() {
    return usuarioModificacion;
  }

  public void setUsuarioModificacion(String usuarioModificacion) {
    this.usuarioModificacion = usuarioModificacion;
  }

  public Date getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

  public String getNombreFirmante1() {
    return nombreFirmante1;
  }

  public void setNombreFirmante1(String nombreFirmante1) {
    this.nombreFirmante1 = nombreFirmante1;
  }

  public String getNombreFirmante2() {
    return nombreFirmante2;
  }

  public void setNombreFirmante2(String nombreFirmante2) {
    this.nombreFirmante2 = nombreFirmante2;
  }

  public Boolean getSeleccionado() {
    return seleccionado;
  }

  public void setSeleccionado(Boolean seleccionado) {
    this.seleccionado = seleccionado;
  }

  public Integer getDiario() {
    return diario;
  }

  public void setDiario(Integer diario) {
    this.diario = diario;
  }

  public String getLineaDiario() {
    return lineaDiario;
  }

  public void setLineaDiario(String lineaDiario) {
    this.lineaDiario = lineaDiario;
  }

  public String getCorreo1() {
    return correo1;
  }

  public void setCorreo1(String correo1) {
    this.correo1 = correo1;
  }

  public String getCorreo2() {
    return correo2;
  }

  public void setCorreo2(String correo2) {
    this.correo2 = correo2;
  }

  public int getIndicador() {
    return indicador;
  }

  public void setIndicador(int indicador) {
    this.indicador = indicador;
  }

}