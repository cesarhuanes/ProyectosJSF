package com.cardif.satelite.model;

public class MarcaVehiculo implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Long id = new Long(0L);
	private String nombreMarcavehiculo;
	private int status;
	
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getNombreMarcavehiculo()
	{
		return nombreMarcavehiculo;
	}
	
	public void setNombreMarcavehiculo(String nombreMarcavehiculo)
	{
		this.nombreMarcavehiculo = nombreMarcavehiculo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
} //MarcaVehiculo
