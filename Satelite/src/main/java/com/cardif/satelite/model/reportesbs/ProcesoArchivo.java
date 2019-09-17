package com.cardif.satelite.model.reportesbs;

import java.util.Date;

public class ProcesoArchivo {
	private Long codProceso;
	private String nomArchivoUsuario;
	private String nomArchivoProceso;
	private int cantRegistros;
	private String codEstado;
	private String estadoDescripcion;
	private String usuarioProceso;
	private String fechaProceso;
	private Date fechaCreador;
	private String codEstadoSolicitud;

	public Long getCodProceso() {
		return codProceso;
	}

	public void setCodProceso(Long codProceso) {
		this.codProceso = codProceso;
	}

	public String getNomArchivoUsuario() {
		return nomArchivoUsuario;
	}

	public void setNomArchivoUsuario(String nomArchivoUsuario) {
		this.nomArchivoUsuario = nomArchivoUsuario;
	}

	public String getNomArchivoProceso() {
		return nomArchivoProceso;
	}

	public void setNomArchivoProceso(String nomArchivoProceso) {
		this.nomArchivoProceso = nomArchivoProceso;
	}

	public int getCantRegistros() {
		return cantRegistros;
	}

	public void setCantRegistros(int cantRegistros) {
		this.cantRegistros = cantRegistros;
	}

	public String getCodEstado() {
		return codEstado;
	}

	public void setCodEstado(String codEstado) {
		this.codEstado = codEstado;
	}

	public String getUsuarioProceso() {
		return usuarioProceso;
	}

	public void setUsuarioProceso(String usuarioProceso) {
		this.usuarioProceso = usuarioProceso;
	}

	public Date getFechaCreador() {
		return fechaCreador;
	}

	public void setFechaCreador(Date fechaCreador) {
		this.fechaCreador = fechaCreador;
	}

	public String getCodEstadoSolicitud() {
		return codEstadoSolicitud;
	}

	public void setCodEstadoSolicitud(String codEstadoSolicitud) {
		this.codEstadoSolicitud = codEstadoSolicitud;
	}

	public String getFechaProceso() {
		return fechaProceso;
	}

	public void setFechaProceso(String fechaProceso) {
		this.fechaProceso = fechaProceso;
	}

	public String getEstadoDescripcion() {
		return estadoDescripcion;
	}

	public void setEstadoDescripcion(String estadoDescripcion) {
		this.estadoDescripcion = estadoDescripcion;
	}

}
