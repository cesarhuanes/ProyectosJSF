package com.cardif.satelite.reportes.bean;

import java.util.Date;

public class RepContratoSoatSBSBean implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	private Integer codigoFila;
	private Long id;
	private Long idDetalleTramaMensual;
	private Long idSocio;
	private String nombreEmpresa;
	private Date fecFirmaContrato;
	private Integer nroAsegurados;
	private Double montoPrimas;

	public Long getIdDetalleTramaMensual() {
		return idDetalleTramaMensual;
	}

	public void setIdDetalleTramaMensual(Long idDetalleTramaMensual) {
		this.idDetalleTramaMensual = idDetalleTramaMensual;
	}
	
	public Integer getCodigoFila() {
		return codigoFila;
	}

	public void setCodigoFila(Integer codigoFila) {
		this.codigoFila = codigoFila;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdSocio() {
		return idSocio;
	}

	public void setIdSocio(Long idSocio) {
		this.idSocio = idSocio;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public Date getFecFirmaContrato() {
		return fecFirmaContrato;
	}

	public void setFecFirmaContrato(Date fecFirmaContrato) {
		this.fecFirmaContrato = fecFirmaContrato;
	}

	public Integer getNroAsegurados() {
		return nroAsegurados;
	}

	public void setNroAsegurados(Integer nroAsegurados) {
		this.nroAsegurados = nroAsegurados;
	}

	public Double getMontoPrimas() {
		return montoPrimas;
	}

	public void setMontoPrimas(Double montoPrimas) {
		this.montoPrimas = montoPrimas;
	}

}
