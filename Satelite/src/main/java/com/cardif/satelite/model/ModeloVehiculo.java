package com.cardif.satelite.model;

public class ModeloVehiculo implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Long id = new Long(0L);
	private String nombreModelovehiculo;
	private Long marcaVehiculo;
	private String categoriaClaseVehiculo;
	private int status;
	
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getNombreModelovehiculo()
	{
		return nombreModelovehiculo;
	}
	
	public void setNombreModelovehiculo(String nombreModelovehiculo)
	{
		this.nombreModelovehiculo = nombreModelovehiculo;
	}
	
	public Long getMarcaVehiculo()
	{
		return marcaVehiculo;
	}
	
	public void setMarcaVehiculo(Long marcaVehiculo)
	{
		this.marcaVehiculo = marcaVehiculo;
	}
	
	public String getCategoriaClaseVehiculo()
	{
		return categoriaClaseVehiculo;
	}
	
	public void setCategoriaClaseVehiculo(String categoriaClaseVehiculo)
	{
		this.categoriaClaseVehiculo = categoriaClaseVehiculo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
} //ModeloVehiculo
