package com.cardif.satelite.tesoreria.model;

public class Certificado {
	private String fechaEmision;
	private String ruc;
	private String nroDiario;
	private String razonSolial;
	private String nroCertificado;
	private String monedaPago;
	private String importeRetenido;
	private String fechaDigitilacion;
	private String causa;
	private String motivo;
	
	
	
	
	public Certificado(String fechaEmision, String ruc, String nroDiario,
			String razonSolial, String nroCertificado, String monedaPago,
			String importeRetenido, String fechaDigitilacion, String causa,
			String motivo) {
		super();
		this.fechaEmision = fechaEmision;
		this.ruc = ruc;
		this.nroDiario = nroDiario;
		this.razonSolial = razonSolial;
		this.nroCertificado = nroCertificado;
		this.monedaPago = monedaPago;
		this.importeRetenido = importeRetenido;
		this.fechaDigitilacion = fechaDigitilacion;
		this.causa = causa;
		this.motivo = motivo;
	}
	public Certificado() {
		super();
	}
	public String getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getNroDiario() {
		return nroDiario;
	}
	public void setNroDiario(String nroDiario) {
		this.nroDiario = nroDiario;
	}
	public String getRazonSolial() {
		return razonSolial;
	}
	public void setRazonSolial(String razonSolial) {
		this.razonSolial = razonSolial;
	}
	public String getNroCertificado() {
		return nroCertificado;
	}
	public void setNroCertificado(String nroCertificado) {
		this.nroCertificado = nroCertificado;
	}
	public String getMonedaPago() {
		return monedaPago;
	}
	public void setMonedaPago(String monedaPago) {
		this.monedaPago = monedaPago;
	}
	public String getImporteRetenido() {
		return importeRetenido;
	}
	public void setImporteRetenido(String importeRetenido) {
		this.importeRetenido = importeRetenido;
	}
	public String getFechaDigitilacion() {
		return fechaDigitilacion;
	}
	public void setFechaDigitilacion(String fechaDigitilacion) {
		this.fechaDigitilacion = fechaDigitilacion;
	}
	public String getCausa() {
		return causa;
	}
	public void setCausa(String causa) {
		this.causa = causa;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	
	

}
