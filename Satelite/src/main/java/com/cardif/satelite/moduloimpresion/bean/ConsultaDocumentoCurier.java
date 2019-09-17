package com.cardif.satelite.moduloimpresion.bean;

import java.util.Date;

public class ConsultaDocumentoCurier implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String nroDocCliente;
	private String nombreDestinatarioGuia;
	private String dirDestinatarioGuia;
	private String distDestinatarioGuia;
	private String ciudadDestinatarioGuia;
	private String piezas;
	private String tipoEnvio;
	private String valorDeclaradoGuia;
	private String idDestinatarioGuia;
	private String telDestinatarioGuia;
	private String observaGuia;
	private String nombreRemitenteGuia;
	private String poliza;
	private String placa;
	private Date fechaVenta;
	
	
	public String getNroDocCliente()
	{
		return nroDocCliente;
	}
	
	public void setNroDocCliente(String nroDocCliente)
	{
		this.nroDocCliente = nroDocCliente;
	}
	
	public String getNombreDestinatarioGuia()
	{
		return nombreDestinatarioGuia;
	}
	
	public void setNombreDestinatarioGuia(String nombreDestinatarioGuia)
	{
		this.nombreDestinatarioGuia = nombreDestinatarioGuia;
	}
	
	public String getDirDestinatarioGuia()
	{
		return dirDestinatarioGuia;
	}
	
	public void setDirDestinatarioGuia(String dirDestinatarioGuia)
	{
		this.dirDestinatarioGuia = dirDestinatarioGuia;
	}
	
	public String getDistDestinatarioGuia()
	{
		return distDestinatarioGuia;
	}
	
	public void setDistDestinatarioGuia(String distDestinatarioGuia)
	{
		this.distDestinatarioGuia = distDestinatarioGuia;
	}
	
	public String getCiudadDestinatarioGuia()
	{
		return ciudadDestinatarioGuia;
	}
	
	public void setCiudadDestinatarioGuia(String ciudadDestinatarioGuia)
	{
		this.ciudadDestinatarioGuia = ciudadDestinatarioGuia;
	}
	
	public String getPiezas()
	{
		return piezas;
	}
	
	public void setPiezas(String piezas)
	{
		this.piezas = piezas;
	}
	
	public String getTipoEnvio()
	{
		return tipoEnvio;
	}
	
	public void setTipoEnvio(String tipoEnvio)
	{
		this.tipoEnvio = tipoEnvio;
	}
	
	public String getValorDeclaradoGuia()
	{
		return valorDeclaradoGuia;
	}
	
	public void setValorDeclaradoGuia(String valorDeclaradoGuia)
	{
		this.valorDeclaradoGuia = valorDeclaradoGuia;
	}
	
	public String getIdDestinatarioGuia()
	{
		return idDestinatarioGuia;
	}
	
	public void setIdDestinatarioGuia(String idDestinatarioGuia)
	{
		this.idDestinatarioGuia = idDestinatarioGuia;
	}
	
	public String getTelDestinatarioGuia()
	{
		return telDestinatarioGuia;
	}
	
	public void setTelDestinatarioGuia(String telDestinatarioGuia)
	{
		this.telDestinatarioGuia = telDestinatarioGuia;
	}
	
	public String getObservaGuia()
	{
		return observaGuia;
	}
	
	public void setObservaGuia(String observaGuia)
	{
		this.observaGuia = observaGuia;
	}
	
	public String getNombreRemitenteGuia()
	{
		return nombreRemitenteGuia;
	}
	
	public void setNombreRemitenteGuia(String nombreRemitenteGuia)
	{
		this.nombreRemitenteGuia = nombreRemitenteGuia;
	}
	
	public String getPoliza()
	{
		return poliza;
	}
	
	public void setPoliza(String poliza)
	{
		this.poliza = poliza;
	}
	
	public String getPlaca()
	{
		return placa;
	}
	
	public void setPlaca(String placa)
	{
		this.placa = placa;
	}
	
	public Date getFechaVenta()
	{
		return fechaVenta;
	}
	public void setFechaVenta(Date fechaVenta)
	{
		this.fechaVenta = fechaVenta;
	}
	
} //ConsultaDocumentCurier
