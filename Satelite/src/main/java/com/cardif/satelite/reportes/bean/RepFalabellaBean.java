package com.cardif.satelite.reportes.bean;

import java.util.Date;

public class RepFalabellaBean
{
	private Long id;
	private String nroPoliza;
	private String propuesta;
	private Date fecha;
	private String motivo;
	private String valor;
	
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getNroPoliza()
	{
		return nroPoliza;
	}
	
	public void setNroPoliza(String nroPoliza)
	{
		this.nroPoliza = nroPoliza;
	}
	
	public String getPropuesta()
	{
		return propuesta;
	}
	
	public void setPropuesta(String propuesta)
	{
		this.propuesta = propuesta;
	}
	
	public Date getFecha()
	{
		return fecha;
	}
	
	public void setFecha(Date fecha)
	{
		this.fecha = fecha;
	}
	
	public String getMotivo()
	{
		return motivo;
	}
	
	public void setMotivo(String motivo)
	{
		this.motivo = motivo;
	}
	
	public String getValor()
	{
		return valor;
	}
	
	public void setValor(String valor)
	{
		this.valor = valor;
	}
	
} //RepFalabellaBean
