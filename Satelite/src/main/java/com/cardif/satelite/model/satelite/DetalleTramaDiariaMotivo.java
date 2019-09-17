package com.cardif.satelite.model.satelite;

import java.util.Date;

public class DetalleTramaDiariaMotivo implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Long id = new Long(0L);
	private String motivoDsc;
	private String observacionDsc;
	private Integer tipoMotivo;
	private Long idDetalleTramaDiaria;
	private Date fecAnulacion;
	private Date fechaCreacion;
	private String usuCreacion;
	
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getMotivoDsc()
	{
		return motivoDsc;
	}
	
	public void setMotivoDsc(String motivoDsc)
	{
		this.motivoDsc = motivoDsc;
	}
	
	public String getObservacionDsc()
	{
		return observacionDsc;
	}
	
	public void setObservacionDsc(String observacionDsc)
	{
		this.observacionDsc = observacionDsc;
	}
	
	public Integer getTipoMotivo()
	{
		return tipoMotivo;
	}
	
	public void setTipoMotivo(Integer tipoMotivo)
	{
		this.tipoMotivo = tipoMotivo;
	}
	
	public Long getIdDetalleTramaDiaria()
	{
		return idDetalleTramaDiaria;
	}
	
	public void setIdDetalleTramaDiaria(Long idDetalleTramaDiaria)
	{
		this.idDetalleTramaDiaria = idDetalleTramaDiaria;
	}
	
	public Date getFecAnulacion()
	{
		return fecAnulacion;
	}
	
	public void setFecAnulacion(Date fecAnulacion)
	{
		this.fecAnulacion = fecAnulacion;
	}
	
	public Date getFechaCreacion()
	{
		return fechaCreacion;
	}
	
	public void setFechaCreacion(Date fechaCreacion)
	{
		this.fechaCreacion = fechaCreacion;
	}
	
	public String getUsuCreacion()
	{
		return usuCreacion;
	}
	
	public void setUsuCreacion(String usuCreacion)
	{
		this.usuCreacion = usuCreacion;
	}
	
} //DetalleTramaDiariaMotivo
