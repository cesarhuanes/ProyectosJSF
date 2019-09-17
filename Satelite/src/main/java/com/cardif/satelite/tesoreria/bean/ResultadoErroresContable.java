package com.cardif.satelite.tesoreria.bean;

import java.io.Serializable;

public class ResultadoErroresContable implements Serializable {
  private static final long serialVersionUID = 1L;
  private String lineaAsientoError;
  private String mensajeAsientoError;
  private String procedenciaError;
  private String diario;
  private String lineaDiario;

  public String getLineaAsientoError() {
    return lineaAsientoError;
  }

  public void setLineaAsientoError(String lineaAsientoError) {
    this.lineaAsientoError = lineaAsientoError;
  }

  public String getMensajeAsientoError() {
    return mensajeAsientoError;
  }

  public void setMensajeAsientoError(String mensajeAsientoError) {
    this.mensajeAsientoError = mensajeAsientoError;
  }

  public String getProcedenciaError() {
    return procedenciaError;
  }

  public void setProcedenciaError(String procedenciaError) {
    this.procedenciaError = procedenciaError;
  }

  public String getLineaDiario() {
    return lineaDiario;
  }

  public void setLineaDiario(String lineaDiario) {
    this.lineaDiario = lineaDiario;
  }

  public String getDiario() {
    return diario;
  }

  public void setDiario(String diario) {
    this.diario = diario;
  }
}
