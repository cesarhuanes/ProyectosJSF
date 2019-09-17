package com.cardif.satelite.reportes.bean;

import java.util.Date;

public class RepConsultaSBSBean {
	private Long id;
	private String nombreArchivo;
	private String tipoAnexo;
	private Integer nroRegistros;
	private Date fechaProceso;
	private String estadoProceso;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getTipoAnexo() {
		return tipoAnexo;
	}

	public void setTipoAnexo(String tipoAnexo) {
		this.tipoAnexo = tipoAnexo;
	}

	public Integer getNroRegistros() {
		return nroRegistros;
	}

	public void setNroRegistros(Integer nroRegistros) {
		this.nroRegistros = nroRegistros;
	}

	public Date getFechaProceso() {
		return fechaProceso;
	}

	public void setFechaProces(Date fechaProceso) {
		this.fechaProceso = fechaProceso;
	}

	public String getEstadoProceso() {
		return estadoProceso;
	}

	public void setEstadoProceso(String estadoProceso) {
		this.estadoProceso = estadoProceso;
	}

} // RepConsultaSBSBean
