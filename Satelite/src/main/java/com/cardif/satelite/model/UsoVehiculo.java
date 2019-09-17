package com.cardif.satelite.model;

public class UsoVehiculo implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String codUso;
	private String descripcionUso;
	
	
	public String getCodUso()
	{
		return codUso;
	}
	
	public void setCodUso(String codUso)
	{
		this.codUso = codUso;
	}
	
	public String getDescripcionUso()
	{
		return descripcionUso;
	}
	
	public void setDescripcionUso(String descripcionUso)
	{
		this.descripcionUso = descripcionUso;
	}
	
} //UsoVehiculo
