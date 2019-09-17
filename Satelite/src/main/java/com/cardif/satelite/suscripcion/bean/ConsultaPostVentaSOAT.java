package com.cardif.satelite.suscripcion.bean;

import java.util.Date;

public class ConsultaPostVentaSOAT implements java.io.Serializable
{
	private static final long serialVersionUID = 8225331560657526644L;
	
	private Long id;
	private Long idPoliza;
	private String nroPoliza;
	private String placa;
	private String nroCertificado;
	private String contratante;
	private String numDoc;
	private Date fecVenta;
	private String canal;
	private String socioProducto;
	private String estado;
	
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public Long getIdPoliza()
	{
		return idPoliza;
	}
	
	public void setIdPoliza(Long idPoliza)
	{
		this.idPoliza = idPoliza;
	}
	
	public String getNroPoliza()
	{
		return nroPoliza;
	}
	
	public void setNroPoliza(String nroPoliza)
	{
		this.nroPoliza = nroPoliza;
	}
	
	public String getPlaca()
	{
		return placa;
	}
	
	public void setPlaca(String placa)
	{
		this.placa = placa;
	}
	
	public String getNroCertificado()
	{
		return nroCertificado;
	}
	
	public void setNroCertificado(String nroCertificado)
	{
		this.nroCertificado = nroCertificado;
	}
	
	public String getContratante()
	{
		return contratante;
	}
	
	public void setContratante(String contratante)
	{
		this.contratante = contratante;
	}
	
	public String getNumDoc()
	{
		return numDoc;
	}
	
	public void setNumDoc(String numDoc)
	{
		this.numDoc = numDoc;
	}
	
	public Date getFecVenta()
	{
		return fecVenta;
	}
	
	public void setFecVenta(Date fecVenta)
	{
		this.fecVenta = fecVenta;
	}
	
	public String getCanal()
	{
		return canal;
	}
	
	public void setCanal(String canal)
	{
		this.canal = canal;
	}
	
	public String getSocioProducto()
	{
		return socioProducto;
	}
	
	public void setSocioProducto(String socioProducto)
	{
		this.socioProducto = socioProducto;
	}
	
	public String getEstado()
	{
		return estado;
	}
	
	public void setEstado(String estado)
	{
		this.estado = estado;
	}
	
} //ConsultaPostVentaSOAT
