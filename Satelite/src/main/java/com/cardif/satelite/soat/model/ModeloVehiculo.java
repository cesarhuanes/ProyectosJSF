package com.cardif.satelite.soat.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ModeloVehiculo implements Serializable {

  private static final long serialVersionUID = 1L;

  private BigDecimal id;

  private String nombreModelovehiculo;

  private Long version;

  private BigDecimal marcaVehiculo;
  
  private String categoriaClaseVehiculo;
	
	public ModeloVehiculo() {}
	
	public ModeloVehiculo(String nombreModelovehiculo, BigDecimal marcaVehiculo, String categoriaClaseVehiculo)
	{
		this.nombreModelovehiculo = nombreModelovehiculo;
		this.marcaVehiculo = marcaVehiculo;
		this.categoriaClaseVehiculo = categoriaClaseVehiculo;
	} //ModeloVehiculo
	
  
  public BigDecimal getId() {
    return id;
  }

  public void setId(BigDecimal id) {
    this.id = id;
  }

  public String getNombreModelovehiculo() {
    return nombreModelovehiculo;
  }

  public void setNombreModelovehiculo(String nombreModelovehiculo) {
    this.nombreModelovehiculo = nombreModelovehiculo == null ? null : nombreModelovehiculo.trim();
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

  public BigDecimal getMarcaVehiculo() {
    return marcaVehiculo;
  }

  public void setMarcaVehiculo(BigDecimal marcaVehiculo) {
    this.marcaVehiculo = marcaVehiculo;
  }

	public String getCategoriaClaseVehiculo() {
		return categoriaClaseVehiculo;
	}
	
	public void setCategoriaClaseVehiculo(String categoriaClaseVehiculo) {
		this.categoriaClaseVehiculo = categoriaClaseVehiculo;
	}
  
  
  
}