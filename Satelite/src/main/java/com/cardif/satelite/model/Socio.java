package com.cardif.satelite.model;

import java.util.Date;

public class Socio implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String rucSocio;
	private String nombreSocio;
	private String abreviatura;
	private Date fechaContrato;	
	private String fechaContratos;
	private Integer grupoComercio;
	private Integer estado;
	private Date fechaCreacion;
	private Date fechaModificacion;
	private String usuarioCreacion;
	private String usuarioModificacion;
	
	private Long secuencial;
	
	public Socio() {}
	
	public Socio(Integer grupoComercio, Integer estado)
	{
		super();
		this.grupoComercio = grupoComercio;
		this.estado = estado;
	}
	
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getRucSocio()
	{
		return rucSocio;
	}
	
	public void setRucSocio(String rucSocio)
	{
		this.rucSocio = rucSocio;
	}
	
	public String getNombreSocio()
	{
		return nombreSocio;
	}
	
	public void setNombreSocio(String nombreSocio)
	{
		this.nombreSocio = nombreSocio;
	}
	
	public String getAbreviatura()
	{
		return abreviatura;
	}
	
	public void setAbreviatura(String abreviatura)
	{
		this.abreviatura = abreviatura;
	}
	
	public Date getFechaContrato()
	{
		return fechaContrato;
	}
	
	public void setFechaContrato(Date fechaContrato)
	{
		this.fechaContrato = fechaContrato;
	}
	
	public Integer getGrupoComercio()
	{
		return grupoComercio;
	}
	
	public void setGrupoComercio(Integer grupoComercio)
	{
		this.grupoComercio = grupoComercio;
	}
	
	public Integer getEstado()
	{
		return estado;
	}
	
	public void setEstado(Integer estado)
	{
		this.estado = estado;
	}
	
	public Date getFechaCreacion()
	{
		return fechaCreacion;
	}
	
	public void setFechaCreacion(Date fechaCreacion)
	{
		this.fechaCreacion = fechaCreacion;
	}
	
	public Date getFechaModificacion()
	{
		return fechaModificacion;
	}
	
	public void setFechaModificacion(Date fechaModificacion)
	{
		this.fechaModificacion = fechaModificacion;
	}
	
	public String getUsuarioCreacion()
	{
		return usuarioCreacion;
	}
	
	public void setUsuarioCreacion(String usuarioCreacion)
	{
		this.usuarioCreacion = usuarioCreacion;
	}
	
	public String getUsuarioModificacion()
	{
		return usuarioModificacion;
	}
	
	public void setUsuarioModificacion(String usuarioModificacion)
	{
		this.usuarioModificacion = usuarioModificacion;
	}
	
	public Long getSecuencial() {
		return secuencial;
	}

	public void setSecuencial(Long secuencial) {
		this.secuencial = secuencial;
	}

	public String getFechaContratos() {
		return fechaContratos;
	}

	public void setFechaContratos(String fechaContratos) {
		this.fechaContratos = fechaContratos;
	}
	
	
	
	
} //Socio
