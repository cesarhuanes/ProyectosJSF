package com.cardif.satelite.model.reportes;

import java.util.Date;

public class DetalleReporteRegVenta implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	private Long id = 0L;
	private String tipoDocumento;
	private String nroDocumento;
	private Date fecEmision;
	private String nombreRazonsocial;
	private Double importeTotal;
	private String tipoComp;
	private Long idProducto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public Date getFecEmision() {
		return fecEmision;
	}

	public void setFecEmision(Date fecEmision) {
		this.fecEmision = fecEmision;
	}

	public String getNombreRazonsocial() {
		return nombreRazonsocial;
	}

	public void setNombreRazonsocial(String nombreRazonsocial) {
		this.nombreRazonsocial = nombreRazonsocial;
	}

	public Double getImporteTotal() {
		return importeTotal;
	}

	public void setImporteTotal(Double importeTotal) {
		this.importeTotal = importeTotal;
	}

	public String getTipoComp() {
		return tipoComp;
	}

	public void setTipoComp(String tipoComp) {
		this.tipoComp = tipoComp;
	}

	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

} // DetalleReporteRegVenta
