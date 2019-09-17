package com.cardif.satelite.model.satelite;

import java.util.Date;

public class TramaDiaria implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Long secArchivo;
	private Long producto;
	private String nombreArchivo;
	private Integer nroRegistros;
	private Date fecCarga;
	private String tipoCarga;
	private Date fecCreacion;
	private String usuCreacion;
	
	
	public Long getSecArchivo()
	{
		return secArchivo;
	}
	
	public void setSecArchivo(Long secArchivo)
	{
		this.secArchivo = secArchivo;
	}
	
	public Long getProducto()
	{
		return producto;
	}
	
	public void setProducto(Long producto)
	{
		this.producto = producto;
	}
	
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
	
	public Date getFecCarga()
	{
		return fecCarga;
	}
	
	public void setFecCarga(Date fecCarga)
	{
		this.fecCarga = fecCarga;
	}
	
	public String getTipoCarga()
	{
		return tipoCarga;
	}
	
	public void setTipoCarga(String tipoCarga)
	{
		this.tipoCarga = tipoCarga;
	}
	
	public Date getFecCreacion()
	{
		return fecCreacion;
	}
	
	public void setFecCreacion(Date fecCreacion)
	{
		this.fecCreacion = fecCreacion;
	}
	
	public String getUsuCreacion()
	{
		return usuCreacion;
	}
	
	public void setUsuCreacion(String usuCreacion)
	{
		this.usuCreacion = usuCreacion;
	}
	
} //TramaDiaria
