package com.cardif.satelite.model;

public class Provincia implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String codProvincia;
	private String nombreProvincia;
	private String departamento;
	
	
	public String getCodProvincia()
	{
		return codProvincia;
	}
	
	public void setCodProvincia(String codProvincia)
	{
		this.codProvincia = codProvincia;
	}
	
	public String getNombreProvincia()
	{
		return nombreProvincia;
	}
	
	public void setNombreProvincia(String nombreProvincia)
	{
		this.nombreProvincia = nombreProvincia;
	}
	
	public String getDepartamento()
	{
		return departamento;
	}
	
	public void setDepartamento(String departamento)
	{
		this.departamento = departamento;
	}
	
} //Provincia
