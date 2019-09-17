package com.cardif.satelite.model.reportesbs;

import java.util.Date;

public class LogErroresReportes {
	private Long codLogErrores;

	private Long codReporte;

	private String nomReporte;

	private String glosa;

	private String boPlanilla;

	private String descripcion;

	private String usuarioCreador;

	private Date fechaCreado;

	public Long getCodLogErrores() {
		return codLogErrores;
	}

	public void setCodLogErrores(Long codLogErrores) {
		this.codLogErrores = codLogErrores;
	}

	public Long getCodReporte() {
		return codReporte;
	}

	public void setCodReporte(Long codReporte) {
		this.codReporte = codReporte;
	}

	public String getNomReporte() {
		return nomReporte;
	}

	public void setNomReporte(String nomReporte) {
		this.nomReporte = nomReporte;
	}

	public String getGlosa() {
		return glosa;
	}

	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}

	public String getBoPlanilla() {
		return boPlanilla;
	}

	public void setBoPlanilla(String boPlanilla) {
		this.boPlanilla = boPlanilla;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUsuarioCreador() {
		return usuarioCreador;
	}

	public void setUsuarioCreador(String usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}

	public Date getFechaCreado() {
		return fechaCreado;
	}

	public void setFechaCreado(Date fechaCreado) {
		this.fechaCreado = fechaCreado;
	}
}