package com.cardif.satelite.model.reportes;

import java.util.Date;

public class ReporteSBS implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Long id = 0L;
	private String nombreArchivo;
	private String archivoAdjuntoExportar;
	private String tipoReporte;
	private Integer anioReporte;
	private Long periodoReporte;
	private Double tipoCambio;
	private Integer nroRegistros;
	private Date fechaProceso;
	private String estadoProceso;
	
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getNombreArchivo()
	{
		return nombreArchivo;
	}
	
	public void setNombreArchivo(String nombreArchivo)
	{
		this.nombreArchivo = nombreArchivo;
	}
	
	public String getArchivoAdjuntoExportar()
	{
		return archivoAdjuntoExportar;
	}
	
	public void setArchivoAdjuntoExportar(String archivoAdjuntoExportar)
	{
		this.archivoAdjuntoExportar = archivoAdjuntoExportar;
	}
	
	public String getTipoReporte()
	{
		return tipoReporte;
	}
	
	public void setTipoReporte(String tipoReporte)
	{
		this.tipoReporte = tipoReporte;
	}
	
	public Integer getAnioReporte()
	{
		return anioReporte;
	}
	
	public void setAnioReporte(Integer anioReporte)
	{
		this.anioReporte = anioReporte;
	}
	
	public Long getPeriodoReporte()
	{
		return periodoReporte;
	}
	
	public void setPeriodoReporte(Long periodoReporte)
	{
		this.periodoReporte = periodoReporte;
	}
	
	public Double getTipoCambio()
	{
		return tipoCambio;
	}
	
	public void setTipoCambio(Double tipoCambio)
	{
		this.tipoCambio = tipoCambio;
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
	
} //ReporteSBS
