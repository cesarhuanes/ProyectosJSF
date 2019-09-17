package com.cardif.satelite.soat.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class DireccionPropietarioVehiculo implements Serializable {

  private static final long serialVersionUID = 1L;

  private String placa;

  private Long version;

  private BigDecimal direccion;

  private BigDecimal propietario;

  public String getPlaca() {
    return placa;
  }

  public void setPlaca(String placa) {
    this.placa = placa == null ? null : placa.trim();
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public BigDecimal getDireccion() {
    return direccion;
  }

  public void setDireccion(BigDecimal direccion) {
    this.direccion = direccion;
  }

  public BigDecimal getPropietario() {
    return propietario;
  }

  public void setPropietario(BigDecimal propietario) {
    this.propietario = propietario;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("DireccionPropietarioVehiculo [placa=");
    builder.append(placa);
    builder.append(", version=");
    builder.append(version);
    builder.append(", direccion=");
    builder.append(direccion);
    builder.append(", propietario=");
    builder.append(propietario);
    builder.append("]");
    return builder.toString();
  }

  public boolean equals(DireccionPropietarioVehiculo direccionPropietarioVehiculo) {
    return placa.equals(direccionPropietarioVehiculo.placa) && version.equals(direccionPropietarioVehiculo.version) && propietario.equals(direccionPropietarioVehiculo.propietario)
        && direccion.equals(direccionPropietarioVehiculo.direccion);
  }

}