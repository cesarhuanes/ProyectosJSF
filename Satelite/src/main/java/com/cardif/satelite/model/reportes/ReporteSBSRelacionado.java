package com.cardif.satelite.model.reportes;

public class ReporteSBSRelacionado implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Long id = 0L;
	private Long idReporte;
	private Long idDetalleReporte;
	
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public Long getIdReporte()
	{
		return idReporte;
	}
	
	public void setIdReporte(Long idReporte)
	{
		this.idReporte = idReporte;
	}
	
	public Long getIdDetalleReporte()
	{
		return idDetalleReporte;
	}
	
	public void setIdDetalleReporte(Long idDetalleReporte)
	{
		this.idDetalleReporte = idDetalleReporte;
	}
	
} //ReporteSBSRelacionado
