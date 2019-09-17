package com.cardif.satelite.model.reportesbs;

public class LogError {
	private Long codLogError;
	private Long codProcesoArchivo;
	private String socio;
	private String asegurado;
	private String producto;
	private String bo_planilla;
	private String numPolizaSiniestro;
	private String usuCreacion;
	private String fecCreacion;
	private String descripcionError;

	public Long getCodLogError() {
		return codLogError;
	}

	public void setCodLogError(Long codLogError) {
		this.codLogError = codLogError;
	}

	public Long getCodProcesoArchivo() {
		return codProcesoArchivo;
	}

	public void setCodProcesoArchivo(Long codProcesoArchivo) {
		this.codProcesoArchivo = codProcesoArchivo;
	}

	public String getSocio() {
		return socio;
	}

	public void setSocio(String socio) {
		this.socio = socio;
	}

	public String getAsegurado() {
		return asegurado;
	}

	public void setAsegurado(String asegurado) {
		this.asegurado = asegurado;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public String getBo_planilla() {
		return bo_planilla;
	}

	public void setBo_planilla(String bo_planilla) {
		this.bo_planilla = bo_planilla;
	}

	public String getNumPolizaSiniestro() {
		return numPolizaSiniestro;
	}

	public void setNumPolizaSiniestro(String numPolizaSiniestro) {
		this.numPolizaSiniestro = numPolizaSiniestro;
	}

	public String getUsuCreacion() {
		return usuCreacion;
	}

	public void setUsuCreacion(String usuCreacion) {
		this.usuCreacion = usuCreacion;
	}

	public String getDescripcionError() {
		return descripcionError;
	}

	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}

	public String getFecCreacion() {
		return fecCreacion;
	}

	public void setFecCreacion(String fecCreacion) {
		this.fecCreacion = fecCreacion;
	}

}
