package com.cardif.satelite.model;

public class CanalProducto implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nombreCanal;
	private Integer conciliar;
	private Integer estado;
	
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getNombreCanal()
	{
		return nombreCanal;
	}
	
	public void setNombreCanal(String nombreCanal)
	{
		this.nombreCanal = nombreCanal;
	}
	
	public Integer getConciliar()
	{
		return conciliar;
	}
	
	public void setConciliar(Integer conciliar)
	{
		this.conciliar = conciliar;
	}
	
	public Integer getEstado()
	{
		return estado;
	}
	
	public void setEstado(Integer estado)
	{
		this.estado = estado;
	}
	
} //CanalProducto
