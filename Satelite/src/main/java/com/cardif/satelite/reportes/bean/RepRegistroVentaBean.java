package com.cardif.satelite.reportes.bean;

import java.util.Date;

public class RepRegistroVentaBean implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long correlativoRegistro;
	private Date fechaEmision;
	private Date fechaVencimiento;
	private String tipoComprobante;
	private String nroSerie;
	private String correlativoSerie;
	private String tipoDocCliente;
	private String nroDocCliente;
	private String nombreRazonSocial;
	private Double valorFactExport;
	private Double baseImponible;
	private Double importeExonerado;
	private Double importeInafecto;
	private Double impuestoISC;
	private Double impuestoIGV;
	private Double otrosImportes;
	private Double importeTotal;
	private Double tipoCambio;
	private Date fechaEmisionRef;
	private String tipoComprobanteRef;
	private String serieComprobanteRef;
	private String correlativoSerieOrig;
	private String modificable;
	private String openItem;
	private String openItemRef;
	private String nroPoliza;
	private String producto;
	private String estado;
	private String observacion;
	
	private Date fechaCarga;
	private Date fechaVenta;
	
	private String tipoRegistroVenta;

	
	public String getTipoRegistroVenta() {
		return tipoRegistroVenta;
	}

	public void setTipoRegistroVenta(String tipoRegistroVenta) {
		this.tipoRegistroVenta = tipoRegistroVenta;
	}

	public Date getFechaCarga() {
		return fechaCarga;
	}

	public void setFechaCarga(Date fechaCarga) {
		this.fechaCarga = fechaCarga;
	}

	public Date getFechaVenta() {
		return fechaVenta;
	}

	public void setFechaVenta(Date fechaVenta) {
		this.fechaVenta = fechaVenta;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCorrelativoRegistro() {
		return correlativoRegistro;
	}

	public void setCorrelativoRegistro(Long correlativoRegistro) {
		this.correlativoRegistro = correlativoRegistro;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public String getTipoComprobante() {
		return tipoComprobante;
	}

	public void setTipoComprobante(String tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}

	public String getNroSerie() {
		return nroSerie;
	}

	public void setNroSerie(String nroSerie) {
		this.nroSerie = nroSerie;
	}

	public String getCorrelativoSerie() {
		return correlativoSerie;
	}

	public void setCorrelativoSerie(String correlativoSerie) {
		this.correlativoSerie = correlativoSerie;
	}

	public String getTipoDocCliente() {
		return tipoDocCliente;
	}

	public void setTipoDocCliente(String tipoDocCliente) {
		this.tipoDocCliente = tipoDocCliente;
	}

	public String getNroDocCliente() {
		return nroDocCliente;
	}

	public void setNroDocCliente(String nroDocCliente) {
		this.nroDocCliente = nroDocCliente;
	}

	public String getNombreRazonSocial() {
		return nombreRazonSocial;
	}

	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
	}

	public Double getValorFactExport() {
		return valorFactExport;
	}

	public void setValorFactExport(Double valorFactExport) {
		this.valorFactExport = valorFactExport;
	}

	public Double getBaseImponible() {
		return baseImponible;
	}

	public void setBaseImponible(Double baseImponible) {
		this.baseImponible = baseImponible;
	}

	public Double getImporteExonerado() {
		return importeExonerado;
	}

	public void setImporteExonerado(Double importeExonerado) {
		this.importeExonerado = importeExonerado;
	}

	public Double getImporteInafecto() {
		return importeInafecto;
	}

	public void setImporteInafecto(Double importeInafecto) {
		this.importeInafecto = importeInafecto;
	}

	public Double getImpuestoISC() {
		return impuestoISC;
	}

	public void setImpuestoISC(Double impuestoISC) {
		this.impuestoISC = impuestoISC;
	}

	public Double getImpuestoIGV() {
		return impuestoIGV;
	}

	public void setImpuestoIGV(Double impuestoIGV) {
		this.impuestoIGV = impuestoIGV;
	}

	public Double getOtrosImportes() {
		return otrosImportes;
	}

	public void setOtrosImportes(Double otrosImportes) {
		this.otrosImportes = otrosImportes;
	}

	public Double getImporteTotal() {
		return importeTotal;
	}

	public void setImporteTotal(Double importeTotal) {
		this.importeTotal = importeTotal;
	}

	public Double getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(Double tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	public Date getFechaEmisionRef() {
		return fechaEmisionRef;
	}

	public void setFechaEmisionRef(Date fechaEmisionRef) {
		this.fechaEmisionRef = fechaEmisionRef;
	}

	public String getTipoComprobanteRef() {
		return tipoComprobanteRef;
	}

	public void setTipoComprobanteRef(String tipoComprobanteRef) {
		this.tipoComprobanteRef = tipoComprobanteRef;
	}

	public String getSerieComprobanteRef() {
		return serieComprobanteRef;
	}

	public void setSerieComprobanteRef(String serieComprobanteRef) {
		this.serieComprobanteRef = serieComprobanteRef;
	}

	public String getCorrelativoSerieOrig() {
		return correlativoSerieOrig;
	}

	public void setCorrelativoSerieOrig(String correlativoSerieOrig) {
		this.correlativoSerieOrig = correlativoSerieOrig;
	}

	public String getModificable() {
		return modificable;
	}

	public void setModificable(String modificable) {
		this.modificable = modificable;
	}

	public String getOpenItem() {
		return openItem;
	}

	public void setOpenItem(String openItem) {
		this.openItem = openItem;
	}

	public String getOpenItemRef() {
		return openItemRef;
	}

	public void setOpenItemRef(String openItemRef) {
		this.openItemRef = openItemRef;
	}

	public String getNroPoliza() {
		return nroPoliza;
	}

	public void setNroPoliza(String nroPoliza) {
		this.nroPoliza = nroPoliza;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

} // RepRegistroVentaBean
