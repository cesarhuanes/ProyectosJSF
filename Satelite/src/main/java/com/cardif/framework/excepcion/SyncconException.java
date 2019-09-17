package com.cardif.framework.excepcion;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;

public class SyncconException extends Exception {

  private static final long serialVersionUID = 1L;

  private ErrorBean errorBean = null;
  private Severity severidad = FacesMessage.SEVERITY_WARN;

  public Severity getSeveridad() {
    return severidad;
  }

  public void setSeveridad(Severity severidad) {
    this.severidad = severidad;
  }

  public SyncconException() {
    errorBean = new ErrorBean();
  }

  public SyncconException(String codigo) {
    super(codigo);
    errorBean = new ErrorBean();
    fillBean(codigo);
  }

  public SyncconException(String codigo, Severity severidad) {
    super(codigo);
    errorBean = new ErrorBean();
    this.severidad = severidad;
    fillBean(codigo);
  }

  public String getMessageComplete() {
    return errorBean.toString();
  }

  @Override
  public String getMessage() {
    return errorBean.getDescripcion();
  }

  private void fillBean(String codigo) {
    String descripcion = "";
    descripcion = PropertiesErrorUtil.getProperty(codigo);
    errorBean.setCodigo(codigo);
    errorBean.setDescripcion(descripcion);

  }

}
