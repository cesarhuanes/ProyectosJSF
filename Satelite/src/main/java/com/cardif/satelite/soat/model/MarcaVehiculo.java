package com.cardif.satelite.soat.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class MarcaVehiculo implements Serializable {

  private static final long serialVersionUID = 1L;

  private BigDecimal id;

  private String nombreMarcavehiculo;

  private Long version;

  public MarcaVehiculo() {}
	
	public MarcaVehiculo(String nombreMarcavehiculo)
	{
		this.nombreMarcavehiculo = nombreMarcavehiculo;
	}
  
  public BigDecimal getId() {
    return id;
  }

  public void setId(BigDecimal id) {
    this.id = id;
  }

  public String getNombreMarcavehiculo() {
    return nombreMarcavehiculo;
  }

  public void setNombreMarcavehiculo(String nombreMarcavehiculo) {
    this.nombreMarcavehiculo = nombreMarcavehiculo == null ? null : nombreMarcavehiculo.trim();
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }
}