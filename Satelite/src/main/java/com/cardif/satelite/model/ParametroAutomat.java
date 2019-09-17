package com.cardif.satelite.model;

public class ParametroAutomat implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String codParam;
	private String codValor;
	private String nombreValor;
	private Integer numOrden;
	
	
	public String getCodParam()
	{
		return codParam;
	}
	
	public void setCodParam(String codParam)
	{
		this.codParam = codParam;
	}
	
	public String getCodValor()
	{
		return codValor;
	}
	
	public void setCodValor(String codValor)
	{
		this.codValor = codValor;
	}
	
	public String getNombreValor()
	{
		return nombreValor;
	}
	
	public void setNombreValor(String nombreValor)
	{
		this.nombreValor = nombreValor;
	}
	
	public Integer getNumOrden()
	{
		return numOrden;
	}
	
	public void setNumOrden(Integer numOrden)
	{
		this.numOrden = numOrden;
	}
	
} //ParametroAutomat
