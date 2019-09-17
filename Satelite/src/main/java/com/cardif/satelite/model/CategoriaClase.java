package com.cardif.satelite.model;

public class CategoriaClase implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String codCategoriaClase;
	private String descripcionCategoriaClase;
	
	
	public String getCodCategoriaClase()
	{
		return codCategoriaClase;
	}
	
	public void setCodCategoriaClase(String codCategoriaClase)
	{
		this.codCategoriaClase = codCategoriaClase;
	}
	
	public String getDescripcionCategoriaClase()
	{
		return descripcionCategoriaClase;
	}
	
	public void setDescripcionCategoriaClase(String descripcionCategoriaClase)
	{
		this.descripcionCategoriaClase = descripcionCategoriaClase;
	}
	
} //CategoriaClase
