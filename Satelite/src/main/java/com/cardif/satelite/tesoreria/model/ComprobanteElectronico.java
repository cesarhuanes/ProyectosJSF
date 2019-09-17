package com.cardif.satelite.tesoreria.model;

import java.util.Date;
import java.util.List;

public class ComprobanteElectronico {
	private String nroComprobanteElectronico;
	private String tipoComprobante;
	private Date fechaEmision;
	private String proTipoDocumento;
	private String proNroDocumento;
	private String proRazonSocial;
	private String monedaPago;
	private String tasaRetPer;
	private Double importeRetPer;
	private String monedaRetPer;
	private Double importePagCob;
	private String plantilla;
	private String correo;
	private Double tipoCambio;
	private String montoLetras;
	private String estado;
	private Date fechaCreacion;
	private String creadoPor;
	private Date fechaModificacion;
	private String modificadoPor;
	private String causaAnulacion;
	private String motivoAnulacion;
	private String respuestaDteRecovery;
	private String nroDiario;
	private Date fechaDigitalizacion;
	private String unidadNegocio;
	private Integer plazoFechaSunat;
	private List<ComprobanteRelacionado> comprobantes;
	
	
	public String getNroComprobanteElectronico() {
		return nroComprobanteElectronico;
	}

	public void setNroComprobanteElectronico(String nroComprobanteElectronico) {
		this.nroComprobanteElectronico = nroComprobanteElectronico;
	}

	public String getTipoComprobante() {
		return tipoComprobante;
	}

	public void setTipoComprobante(String tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}

	public Date getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public String getProTipoDocumento() {
		return proTipoDocumento;
	}

	public void setProTipoDocumento(String proTipoDocumento) {
		this.proTipoDocumento = proTipoDocumento;
	}

	public String getProNroDocumento() {
		return proNroDocumento;
	}

	public void setProNroDocumento(String proNroDocumento) {
		this.proNroDocumento = proNroDocumento;
	}

	public String getProRazonSocial() {
		return proRazonSocial;
	}

	public void setProRazonSocial(String proRazonSocial) {
		this.proRazonSocial = proRazonSocial;
	}

	public String getMonedaPago() {
		return monedaPago;
	}

	public void setMonedaPago(String monedaPago) {
		this.monedaPago = monedaPago;
	}

	public String getTasaRetPer() {
		return tasaRetPer;
	}

	public void setTasaRetPer(String tasaRetPer) {
		this.tasaRetPer = tasaRetPer;
	}

	public Double getImporteRetPer() {
		return importeRetPer;
	}

	public void setImporteRetPer(Double importeRetPer) {
		this.importeRetPer = importeRetPer;
	}

	public String getMonedaRetPer() {
		return monedaRetPer;
	}

	public void setMonedaRetPer(String monedaRetPer) {
		this.monedaRetPer = monedaRetPer;
	}

	public Double getImportePagCob() {
		return importePagCob;
	}

	public void setImportePagCob(Double importePagCob) {
		this.importePagCob = importePagCob;
	}

	public String getPlantilla() {
		return plantilla;
	}

	public void setPlantilla(String plantilla) {
		this.plantilla = plantilla;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Double getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(Double tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	public String getMontoLetras() {
		return montoLetras;
	}

	public void setMontoLetras(String montoLetras) {
		this.montoLetras = montoLetras;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getCreadoPor() {
		return creadoPor;
	}

	public void setCreadoPor(String creadoPor) {
		this.creadoPor = creadoPor;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getModificadoPor() {
		return modificadoPor;
	}

	public void setModificadoPor(String modificadoPor) {
		this.modificadoPor = modificadoPor;
	}

	public String getCausaAnulacion() {
		return causaAnulacion;
	}

	public void setCausaAnulacion(String causaAnulacion) {
		this.causaAnulacion = causaAnulacion;
	}

	public String getMotivoAnulacion() {
		return motivoAnulacion;
	}

	public void setMotivoAnulacion(String motivoAnulacion) {
		this.motivoAnulacion = motivoAnulacion;
	}

	public String getRespuestaDteRecovery() {
		return respuestaDteRecovery;
	}

	public void setRespuestaDteRecovery(String respuestaDteRecovery) {
		this.respuestaDteRecovery = respuestaDteRecovery;
	}
	
	public String getNroDiario() {
		return nroDiario;
	}
	
	public void setNroDiario(String nroDiario) {
		this.nroDiario = nroDiario;
	}
	
	public Date getFechaDigitalizacion() {
		return fechaDigitalizacion;
	}
	
	public void setFechaDigitalizacion(Date fechaDigitalizacion) {
		this.fechaDigitalizacion = fechaDigitalizacion;
	}
	
	public String getUnidadNegocio() {
		return unidadNegocio;
	}
	
	public void setUnidadNegocio(String unidadNegocio) {
		this.unidadNegocio = unidadNegocio;
	}
	
	public Integer getPlazoFechaSunat() {
		return plazoFechaSunat;
	}
	
	public void setPlazoFechaSunat(Integer plazoFechaSunat) {
		this.plazoFechaSunat = plazoFechaSunat;
	}
	
	public List<ComprobanteRelacionado> getComprobantes() {
		return comprobantes;
	}
	
	public void setComprobantes(List<ComprobanteRelacionado> comprobantes) {
		this.comprobantes = comprobantes;
	}
	
}
