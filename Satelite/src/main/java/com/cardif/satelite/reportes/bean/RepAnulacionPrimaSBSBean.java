package com.cardif.satelite.reportes.bean;

import java.util.Date;

public class RepAnulacionPrimaSBSBean implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	private Integer codigoFila;
	private Long id;
	private Long idDetalleTramaMensual;
	private Date fecRegistroAnulacion;
	private Date fecAnulacion;
	private String nroPoliza;
	private String nroCertificado;
	private String placa;
	private Double primaComercialBruta;
	private Double primaComercial;
	private Double montoFondoSOAT;
	private Date fechaIniVigencia;
	
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

	public Date getFecRegistroAnulacion() {
		return fecRegistroAnulacion;
	}

	public void setFecRegistroAnulacion(Date fecRegistroAnulacion) {
		this.fecRegistroAnulacion = fecRegistroAnulacion;
	}

	public Date getFecAnulacion() {
		return fecAnulacion;
	}

	public void setFecAnulacion(Date fecAnulacion) {
		this.fecAnulacion = fecAnulacion;
	}

	public String getNroPoliza() {
		return nroPoliza;
	}

	public void setNroPoliza(String nroPoliza) {
		this.nroPoliza = nroPoliza;
	}

	public String getNroCertificado() {
		return nroCertificado;
	}

	public void setNroCertificado(String nroCertificado) {
		this.nroCertificado = nroCertificado;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Double getPrimaComercialBruta() {
		return primaComercialBruta;
	}

	public void setPrimaComercialBruta(Double primaComercialBruta) {
		this.primaComercialBruta = primaComercialBruta;
	}

	public Double getPrimaComercial() {
		return primaComercial;
	}

	public void setPrimaComercial(Double primaComercial) {
		this.primaComercial = primaComercial;
	}

	public Double getMontoFondoSOAT() {
		return montoFondoSOAT;
	}

	public void setMontoFondoSOAT(Double montoFondoSOAT) {
		this.montoFondoSOAT = montoFondoSOAT;
	}

	public Date getFechaIniVigencia() {
		return fechaIniVigencia;
	}

	public void setFechaIniVigencia(Date fechaIniVigencia) {
		this.fechaIniVigencia = fechaIniVigencia;
	}

} // RepAnulacionPrimaSBSBean
