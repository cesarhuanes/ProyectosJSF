
package com.cardif.satelite.suscripcion.bean;

import java.math.BigDecimal;
import java.util.Date;

public class FormatoObservaciones  {
	
	private String tipoTrama = "";
	private String nombreArchivo= "";
	private Date fechaCargaArchivo = null;
	private String socio = "";
	private String canal= "";
	private String placa= "";
	private String clase= "";
	private String marca= "";
	private Integer nroAsiente = 0;
	private String modelo = "";
	private String usoVehiculo = "";
	private BigDecimal importePrima = new BigDecimal("0.00");
	private Date fechaVenta = null;
	private String contratante = "";
	private String nroDocumento = "";
	private Integer linea = 0;
	private String obeservaciones = "";
	
	public FormatoObservaciones(){
		
	}

	public String getTipoTrama() {
		return tipoTrama;
	}

	public void setTipoTrama(String tipoTrama) {
		this.tipoTrama = tipoTrama;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public Date getFechaCargaArchivo() {
		return fechaCargaArchivo;
	}

	public void setFechaCargaArchivo(Date fechaCargaArchivo) {
		this.fechaCargaArchivo = fechaCargaArchivo;
	}

	public String getSocio() {
		return socio;
	}

	public void setSocio(String socio) {
		this.socio = socio;
	}

	public String getCanal() {
		return canal;
	}

	public void setCanal(String canal) {
		this.canal = canal;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Integer getNroAsiente() {
		return nroAsiente;
	}

	public void setNroAsiente(Integer nroAsiente) {
		this.nroAsiente = nroAsiente;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getUsoVehiculo() {
		return usoVehiculo;
	}

	public void setUsoVehiculo(String usoVehiculo) {
		this.usoVehiculo = usoVehiculo;
	}

	public BigDecimal getImportePrima() {
		return importePrima;
	}

	public void setImportePrima(BigDecimal importePrima) {
		this.importePrima = importePrima;
	}

	public Date getFechaVenta() {
		return fechaVenta;
	}

	public void setFechaVenta(Date fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	public String getContratante() {
		return contratante;
	}

	public void setContratante(String contratante) {
		this.contratante = contratante;
	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public Integer getLinea() {
		return linea;
	}

	public void setLinea(Integer linea) {
		this.linea = linea;
	}

	public String getObeservaciones() {
		return obeservaciones;
	}

	public void setObeservaciones(String obeservaciones) {
		this.obeservaciones = obeservaciones;
	}
	
}
