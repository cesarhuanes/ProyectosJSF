package com.cardif.satelite.model.reportes;

import java.util.Date;

public class DetalleReporteAPESEG implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long idDetalleTramaDiaria;
	private String clase;
	private String uso;
	private String tipoTrx;
	private String tipoAnulacion;
	private Date fecActAnul;
	private String reportado;
	private Date fechaReportado;
	private Date fecCreacion;
	private String usuCreacion;
	
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public Long getIdDetalleTramaDiaria()
	{
		return idDetalleTramaDiaria;
	}
	
	public void setIdDetalleTramaDiaria(Long idDetalleTramaDiaria)
	{
		this.idDetalleTramaDiaria = idDetalleTramaDiaria;
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
	
	public String getTipoTrx()
	{
		return tipoTrx;
	}
	
	public void setTipoTrx(String tipoTrx)
	{
		this.tipoTrx = tipoTrx;
	}
	
	public String getTipoAnulacion()
	{
		return tipoAnulacion;
	}
	
	public void setTipoAnulacion(String tipoAnulacion)
	{
		this.tipoAnulacion = tipoAnulacion;
	}
	
	public Date getFecActAnul()
	{
		return fecActAnul;
	}
	
	public void setFecActAnul(Date fecActAnul)
	{
		this.fecActAnul = fecActAnul;
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
	
} //DetalleReporteAPESEG
