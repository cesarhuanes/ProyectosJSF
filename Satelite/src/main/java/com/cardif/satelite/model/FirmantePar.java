package com.cardif.satelite.model;

import java.io.Serializable;

public class FirmantePar implements Serializable {

  private static final long serialVersionUID = -8682210331498492568L;

  private Long codigo;

  private Long codigoFirmante1;

  private Long codigoFirmante2;

  private String estado;

  private String firmante1;
  private String firmante2;

  private String correo1;
  private String correo2;

  public FirmantePar() {
    super();
  }

  public FirmantePar(Long codigo, Long codigoFirmante1, Long codigoFirmante2, String estado) {
    super();
    this.codigo = codigo;
    this.codigoFirmante1 = codigoFirmante1;
    this.codigoFirmante2 = codigoFirmante2;
    this.estado = estado;
  }

  public Long getCodigo() {
    return codigo;
  }

  public void setCodigo(Long codigo) {
    this.codigo = codigo;
  }

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

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("FirmantePar [codigo=");
    builder.append(codigo);
    builder.append(", codigoFirmante1=");
    builder.append(codigoFirmante1);
    builder.append(", codigoFirmante2=");
    builder.append(codigoFirmante2);
    builder.append(", estado=");
    builder.append(estado);
    builder.append("]");
    return builder.toString();
  }

  public String getFirmante1() {
    return firmante1;
  }

  public void setFirmante1(String firmante1) {
    this.firmante1 = firmante1;
  }

  public String getFirmante2() {
    return firmante2;
  }

  public void setFirmante2(String firmante2) {
    this.firmante2 = firmante2;
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

}