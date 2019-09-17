package com.cardif.satelite.tesoreria.bean;

import java.util.Date;

public class ConsultaCertRetencionReversa implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String nroComprobanteEle;
	private String tipoComprobante;
	private String nroDiario;
	private Date fechaEmision;
	private String monedaPago;
	private Double importeRetper;
	private String proNroDocumento;
	private String proTipoDocumento;
	private String proRazonSocial;
	private String motivoAnulacion;
	private String unidadNegocio;
	
	
	public String getNroComprobanteEle()
	{
		return nroComprobanteEle;
	}
	
	public void setNroComprobanteEle(String nroComprobanteEle)
	{
		this.nroComprobanteEle = nroComprobanteEle;
	}
	
	public String getTipoComprobante()
	{
		return tipoComprobante;
	}
	
	public void setTipoComprobante(String tipoComprobante)
	{
		this.tipoComprobante = tipoComprobante;
	}
	
	public String getNroDiario()
	{
		return nroDiario;
	}
	
	public void setNroDiario(String nroDiario)
	{
		this.nroDiario = nroDiario;
	}
	
	public Date getFechaEmision()
	{
		return fechaEmision;
	}
	
	public void setFechaEmision(Date fechaEmision)
	{
		this.fechaEmision = fechaEmision;
	}
	
	public String getMonedaPago()
	{
		return monedaPago;
	}
	
	public void setMonedaPago(String monedaPago)
	{
		this.monedaPago = monedaPago;
	}
	
	public Double getImporteRetper()
	{
		return importeRetper;
	}
	
	public void setImporteRetper(Double importeRetper)
	{
		this.importeRetper = importeRetper;
	}
	
	public String getProNroDocumento()
	{
		return proNroDocumento;
	}
	
	public void setProNroDocumento(String proNroDocumento)
	{
		this.proNroDocumento = proNroDocumento;
	}
	
	public String getProTipoDocumento()
	{
		return proTipoDocumento;
	}
	
	public void setProTipoDocumento(String proTipoDocumento)
	{
		this.proTipoDocumento = proTipoDocumento;
	}
	
	public String getProRazonSocial()
	{
		return proRazonSocial;
	}
	
	public void setProRazonSocial(String proRazonSocial)
	{
		this.proRazonSocial = proRazonSocial;
	}
	
	public String getMotivoAnulacion()
	{
		return motivoAnulacion;
	}
	
	public void setMotivoAnulacion(String motivoAnulacion)
	{
		this.motivoAnulacion = motivoAnulacion;
	}
	
	public String getUnidadNegocio()
	{
		return unidadNegocio;
	}
	
	public void setUnidadNegocio(String unidadNegocio)
	{
		this.unidadNegocio = unidadNegocio;
	}
	
} //ConsultaCertRetencionReversa
