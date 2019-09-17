package com.cardif.satelite.reportes.bean;

import java.util.Date;

public class RepProduccionPrimaSBSBean implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Integer codigoFila;
	private Long id;
	private Long idDetalleTramaMensual;
	private Date fechaEmision;
	private String nroPoliza;
	private String nroCertificado;
	private String placa;
	private String ubigeo;
	private String claseVehiculo;
	private String usoVehiculo;
	private Integer nroAsientos;
	private Double primaComercialBruta;
	private Double derechoEmision; /* Extraer del reporte de Liquidacion de Comisiones */
	private Double primaComercial;
	private Double primaComercialCobrada;
	private Double primaComercialCobrar;
	private Double comisiones; /* Extraer del reporte de Liquidacion de Comisiones */
	private Double gastoAdministracion;
	private Double recargoComercial;
	private Double otrosRecargos;
	private Date fechaIniVigencia;
	private Date fechaFinVigencia;
	private Double fondoCompensacion; /* Extraer del reporte de Liquidacion de Comisiones */

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

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
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

	public String getUbigeo() {
		return ubigeo;
	}

	public void setUbigeo(String ubigeo) {
		this.ubigeo = ubigeo;
	}

	public String getClaseVehiculo() {
		return claseVehiculo;
	}

	public void setClaseVehiculo(String claseVehiculo) {
		this.claseVehiculo = claseVehiculo;
	}

	public String getUsoVehiculo() {
		return usoVehiculo;
	}

	public void setUsoVehiculo(String usoVehiculo) {
		this.usoVehiculo = usoVehiculo;
	}

	public Integer getNroAsientos() {
		return nroAsientos;
	}

	public void setNroAsientos(Integer nroAsientos) {
		this.nroAsientos = nroAsientos;
	}

	public Double getPrimaComercialBruta() {
		return primaComercialBruta;
	}

	public void setPrimaComercialBruta(Double primaComercialBruta) {
		this.primaComercialBruta = primaComercialBruta;
	}

	public Double getDerechoEmision() {
		return derechoEmision;
	}

	public void setDerechoEmision(Double derechoEmision) {
		this.derechoEmision = derechoEmision;
	}

	public Double getPrimaComercial() {
		return primaComercial;
	}

	public void setPrimaComercial(Double primaComercial) {
		this.primaComercial = primaComercial;
	}

	public Double getPrimaComercialCobrada() {
		return primaComercialCobrada;
	}

	public void setPrimaComercialCobrada(Double primaComercialCobrada) {
		this.primaComercialCobrada = primaComercialCobrada;
	}

	public Double getPrimaComercialCobrar() {
		return primaComercialCobrar;
	}

	public void setPrimaComercialCobrar(Double primaComercialCobrar) {
		this.primaComercialCobrar = primaComercialCobrar;
	}

	public Double getComisiones() {
		return comisiones;
	}

	public void setComisiones(Double comisiones) {
		this.comisiones = comisiones;
	}

	public Double getGastoAdministracion() {
		return gastoAdministracion;
	}

	public void setGastoAdministracion(Double gastoAdministracion) {
		this.gastoAdministracion = gastoAdministracion;
	}

	public Double getRecargoComercial() {
		return recargoComercial;
	}

	public void setRecargoComercial(Double recargoComercial) {
		this.recargoComercial = recargoComercial;
	}

	public Double getOtrosRecargos() {
		return otrosRecargos;
	}

	public void setOtrosRecargos(Double otrosRecargos) {
		this.otrosRecargos = otrosRecargos;
	}

	public Date getFechaIniVigencia() {
		return fechaIniVigencia;
	}

	public void setFechaIniVigencia(Date fechaIniVigencia) {
		this.fechaIniVigencia = fechaIniVigencia;
	}

	public Date getFechaFinVigencia() {
		return fechaFinVigencia;
	}

	public void setFechaFinVigencia(Date fechaFinVigencia) {
		this.fechaFinVigencia = fechaFinVigencia;
	}

	public Double getFondoCompensacion() {
		return fondoCompensacion;
	}

	public void setFondoCompensacion(Double fondoCompensacion) {
		this.fondoCompensacion = fondoCompensacion;
	}
} // RepProduccionPrimaSBSBean
