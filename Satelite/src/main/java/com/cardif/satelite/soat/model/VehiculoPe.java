package com.cardif.satelite.soat.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class VehiculoPe implements Serializable {

  private static final long serialVersionUID = 1L;

  private BigDecimal id;

  private Long anioVehiculo;

  private Long numeroAsiento;

  private String numeroSerie;

  private String placa;

  private Long version;

  private String categoriaClaseVehiculo;

  private BigDecimal marcaVehiculo;

  private BigDecimal modeloVehiculo;

  private String usoVehiculo;

  public BigDecimal getId() {
    return id;
  }

  public void setId(BigDecimal id) {
    this.id = id;
  }

  public Long getAnioVehiculo() {
    return anioVehiculo;
  }

  public void setAnioVehiculo(Long anioVehiculo) {
    this.anioVehiculo = anioVehiculo;
  }

  public Long getNumeroAsiento() {
    return numeroAsiento;
  }

  public void setNumeroAsiento(Long numeroAsiento) {
    this.numeroAsiento = numeroAsiento;
  }

  public String getNumeroSerie() {
    return numeroSerie;
  }

  public void setNumeroSerie(String numeroSerie) {
    this.numeroSerie = numeroSerie == null ? null : numeroSerie.trim();
  }

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

  public String getCategoriaClaseVehiculo() {
    return categoriaClaseVehiculo;
  }

  public void setCategoriaClaseVehiculo(String categoriaClaseVehiculo) {
    this.categoriaClaseVehiculo = categoriaClaseVehiculo == null ? null : categoriaClaseVehiculo.trim();
  }

  public BigDecimal getMarcaVehiculo() {
    return marcaVehiculo;
  }

  public void setMarcaVehiculo(BigDecimal marcaVehiculo) {
    this.marcaVehiculo = marcaVehiculo;
  }

  public BigDecimal getModeloVehiculo() {
    return modeloVehiculo;
  }

  public void setModeloVehiculo(BigDecimal modeloVehiculo) {
    this.modeloVehiculo = modeloVehiculo;
  }

  public String getUsoVehiculo() {
    return usoVehiculo;
  }

  public void setUsoVehiculo(String usoVehiculo) {
    this.usoVehiculo = usoVehiculo == null ? null : usoVehiculo.trim();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("VehiculoPe [id=");
    builder.append(id);
    builder.append(", anioVehiculo=");
    builder.append(anioVehiculo);
    builder.append(", numeroAsiento=");
    builder.append(numeroAsiento);
    builder.append(", numeroSerie=");
    builder.append(numeroSerie);
    builder.append(", placa=");
    builder.append(placa);
    builder.append(", version=");
    builder.append(version);
    builder.append(", categoriaClaseVehiculo=");
    builder.append(categoriaClaseVehiculo);
    builder.append(", marcaVehiculo=");
    builder.append(marcaVehiculo);
    builder.append(", modeloVehiculo=");
    builder.append(modeloVehiculo);
    builder.append(", usoVehiculo=");
    builder.append(usoVehiculo);
    builder.append("]");
    return builder.toString();
  }

  public boolean equals(VehiculoPe vehiculoPe) {
    return anioVehiculo.equals(vehiculoPe.anioVehiculo) && numeroAsiento.equals(vehiculoPe.numeroAsiento) && numeroSerie.equals(vehiculoPe.numeroSerie) && placa.equals(vehiculoPe.placa)
        && version.equals(vehiculoPe.version) && categoriaClaseVehiculo.equals(vehiculoPe.categoriaClaseVehiculo) && marcaVehiculo.equals(vehiculoPe.marcaVehiculo)
        && modeloVehiculo.equals(vehiculoPe.modeloVehiculo) && usoVehiculo.equals(vehiculoPe.usoVehiculo);
  }

}