package com.cardif.satelite.soat.model;

import java.util.Date;

public class OrdenCompraPe implements java.io.Serializable
{
	private static final long serialVersionUID = 2067664647796459873L;
	
	private Long ordenCompra;
	private String codigoSocio;
	private String estadoPago;
	private Date fecha;
	private String glosaEstadoPago;
	private String hora;
	private Double monto;
	private String numeroTransaccion;
	private String version;
	
	
	public Long getOrdenCompra()
	{
		return ordenCompra;
	}
	
	public void setOrdenCompra(Long ordenCompra)
	{
		this.ordenCompra = ordenCompra;
	}
	
	public String getCodigoSocio()
	{
		return codigoSocio;
	}
	
	public void setCodigoSocio(String codigoSocio)
	{
		this.codigoSocio = codigoSocio;
	}
	
	public String getEstadoPago()
	{
		return estadoPago;
	}
	
	public void setEstadoPago(String estadoPago)
	{
		this.estadoPago = estadoPago;
	}
	
	public Date getFecha()
	{
		return fecha;
	}
	
	public void setFecha(Date fecha)
	{
		this.fecha = fecha;
	}
	
	public String getGlosaEstadoPago()
	{
		return glosaEstadoPago;
	}
	
	public void setGlosaEstadoPago(String glosaEstadoPago)
	{
		this.glosaEstadoPago = glosaEstadoPago;
	}
	
	public String getHora()
	{
		return hora;
	}
	
	public void setHora(String hora)
	{
		this.hora = hora;
	}
	
	public Double getMonto()
	{
		return monto;
	}
	
	public void setMonto(Double monto)
	{
		this.monto = monto;
	}
	
	public String getNumeroTransaccion()
	{
		return numeroTransaccion;
	}
	
	public void setNumeroTransaccion(String numeroTransaccion)
	{
		this.numeroTransaccion = numeroTransaccion;
	}
	
	public String getVersion()
	{
		return version;
	}
	
	public void setVersion(String version)
	{
		this.version = version;
	}
	
} //OrdenCompraPe
