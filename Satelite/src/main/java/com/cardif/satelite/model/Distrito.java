package com.cardif.satelite.model;

public class Distrito implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String codDistrito;
	private String nombreDistrito;
	private String provincia;
	
	
	public String getCodDistrito()
	{
		return codDistrito;
	}
	
	public void setCodDistrito(String codDistrito)
	{
		this.codDistrito = codDistrito;
	}
	
	public String getNombreDistrito()
	{
		return nombreDistrito;
	}
	
	public void setNombreDistrito(String nombreDistrito)
	{
		this.nombreDistrito = nombreDistrito;
	}
	
	public String getProvincia()
	{
		return provincia;
	}
	
	public void setProvincia(String provincia)
	{
		this.provincia = provincia;
	}
	
} //Distrito
