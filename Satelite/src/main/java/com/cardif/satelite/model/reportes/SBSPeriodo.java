package com.cardif.satelite.model.reportes;

public class SBSPeriodo implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nombrePeriodo;
	private String tipoAnexo;
	private String trimestre;
	private Integer numOrden;
	
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getNombrePeriodo()
	{
		return nombrePeriodo;
	}
	
	public void setNombrePeriodo(String nombrePeriodo)
	{
		this.nombrePeriodo = nombrePeriodo;
	}
	
	public String getTipoAnexo()
	{
		return tipoAnexo;
	}
	
	public void setTipoAnexo(String tipoAnexo)
	{
		this.tipoAnexo = tipoAnexo;
	}
	
	public String getTrimestre()
	{
		return trimestre;
	}
	
	public void setTrimestre(String trimestre)
	{
		this.trimestre = trimestre;
	}
	
	public Integer getNumOrden()
	{
		return numOrden;
	}
	
	public void setNumOrden(Integer numOrden)
	{
		this.numOrden = numOrden;
	} 
	
} //SBSPeriodo
