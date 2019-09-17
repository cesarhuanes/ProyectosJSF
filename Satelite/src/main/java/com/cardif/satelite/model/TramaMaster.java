package com.cardif.satelite.model;

import java.io.Serializable;
import java.util.Date;

public class TramaMaster implements Serializable {
	private static final long serialVersionUID = -8910713709704830080L;

	private Integer idMaster;
	private Integer idSocio;
	private String labelSocio;
	private Date fechaInicio;
	private Date fechaFin;
	private String rutaArchivo;
	private String estadoMaster;
	private String usuarioCreacion;
	private String usuarioModificacion;
	private Date fechaCreacion;
	private Date fechaModificacion;
	private Integer estado;
	private Integer numRegistros;
	/**
	 * @return the idMaster
	 */
	public Integer getIdMaster() {
		return idMaster;
	}
	/**
	 * @param idMaster the idMaster to set
	 */
	public void setIdMaster(Integer idMaster) {
		this.idMaster = idMaster;
	}
	/**
	 * @return the idSocio
	 */
	public Integer getIdSocio() {
		return idSocio;
	}
	/**
	 * @param idSocio the idSocio to set
	 */
	public void setIdSocio(Integer idSocio) {
		this.idSocio = idSocio;
	}
	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}
	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}
	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	/**
	 * @return the rutaArchivo
	 */
	public String getRutaArchivo() {
		return rutaArchivo;
	}
	/**
	 * @param rutaArchivo the rutaArchivo to set
	 */
	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}
	/**
	 * @return the estadoMaster
	 */
	public String getEstadoMaster() {
		return estadoMaster;
	}
	/**
	 * @param estadoMaster the estadoMaster to set
	 */
	public void setEstadoMaster(String estadoMaster) {
		this.estadoMaster = estadoMaster;
	}
	/**
	 * @return the usuarioCreacion
	 */
	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}
	/**
	 * @param usuarioCreacion the usuarioCreacion to set
	 */
	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}
	/**
	 * @return the usuarioModificacion
	 */
	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}
	/**
	 * @param usuarioModificacion the usuarioModificacion to set
	 */
	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}
	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	/**
	 * @return the fechaModificacion
	 */
	public Date getFechaModificacion() {
		return fechaModificacion;
	}
	/**
	 * @param fechaModificacion the fechaModificacion to set
	 */
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	/**
	 * @return the estado
	 */
	public Integer getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	/**
	 * @return the numRegistros
	 */
	public Integer getNumRegistros() {
		return numRegistros;
	}
	/**
	 * @param numRegistros the numRegistros to set
	 */
	public void setNumRegistros(Integer numRegistros) {
		this.numRegistros = numRegistros;
	}
	/**
	 * @return the labelSocio
	 */
	public String getLabelSocio() {
		return labelSocio;
	}
	/**
	 * @param labelSocio the labelSocio to set
	 */
	public void setLabelSocio(String labelSocio) {
		this.labelSocio = labelSocio;
	}
	
}
