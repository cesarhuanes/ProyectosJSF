package com.cardif.satelite.model;

public class Departamento implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String codDepartamento;
	private String nombreDepartamento;
	
	
	public String getCodDepartamento()
	{
		return codDepartamento;
	}
	
	public void setCodDepartamento(String codDepartamento)
	{
		this.codDepartamento = codDepartamento;
	}
	
	public String getNombreDepartamento()
	{
		return nombreDepartamento;
	}
	
	public void setNombreDepartamento(String nombreDepartamento)
	{
		this.nombreDepartamento = nombreDepartamento;
	}
	
} //Departamento
