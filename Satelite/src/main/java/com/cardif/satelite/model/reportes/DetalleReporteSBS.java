package com.cardif.satelite.model.reportes;

import java.util.Date;

public class DetalleReporteSBS implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long idDetalleTramaMensual;
	private String clase;
	private String uso;
	private String reportado;
	private Date fechaReportado;
	private String estadoReportado;
	private Date fecCreacion;
	private String usuCreacion;
	
	public Long getIdDetalleTramaMensual() {
		return idDetalleTramaMensual;
	}

	public void setIdDetalleTramaMensual(Long idDetalleTramaMensual) {
		this.idDetalleTramaMensual = idDetalleTramaMensual;
	}
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getClase()
	{
		return clase;
	}
	
	public void setClase(String clase)
	{
		this.clase = clase;
	}
	
	public String getUso()
	{
		return uso;
	}
	
	public void setUso(String uso)
	{
		this.uso = uso;
	}
	
	public String getReportado()
	{
		return reportado;
	}
	
	public void setReportado(String reportado)
	{
		this.reportado = reportado;
	}
	
	public Date getFechaReportado()
	{
		return fechaReportado;
	}
	
	public void setFechaReportado(Date fechaReportado)
	{
		this.fechaReportado = fechaReportado;
	}
	
	public String getEstadoReportado()
	{
		return estadoReportado;
	}
	
	public void setEstadoReportado(String estadoReportado)
	{
		this.estadoReportado = estadoReportado;
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
	
} //DetalleReporteSBS
