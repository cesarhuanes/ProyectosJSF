package com.cardif.satelite.reportes.bean;

import java.util.Date;

public class RepSunatCabeceraBean implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String nombreArchivo;
	private Integer nroRegistros;
	private Date fechaProceso;
	private String estadoProceso;
	
	
	public String getNombreArchivo()
	{
		return nombreArchivo;
	}
	
	public void setNombreArchivo(String nombreArchivo)
	{
		this.nombreArchivo = nombreArchivo;
	}
	
	public Integer getNroRegistros()
	{
		return nroRegistros;
	}
	
	public void setNroRegistros(Integer nroRegistros)
	{
		this.nroRegistros = nroRegistros;
	}
	
	public Date getFechaProceso()
	{
		return fechaProceso;
	}
	
	public void setFechaProceso(Date fechaProceso)
	{
		this.fechaProceso = fechaProceso;
	}
	
	public String getEstadoProceso()
	{
		return estadoProceso;
	}
	
	public void setEstadoProceso(String estadoProceso)
	{
		this.estadoProceso = estadoProceso;
	}
	
} // RepSunatCabeceraBean
