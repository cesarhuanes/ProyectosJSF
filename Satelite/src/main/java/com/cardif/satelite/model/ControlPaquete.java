package com.cardif.satelite.model;

import java.util.Date;

public class ControlPaquete  implements java.io.Serializable {

	private static final long serialVersionUID = -3892434226499982721L;

	private Integer id;
	private Date fechaInicio;
	private Date fechaFin;
	private Integer flagEjecucion;
	private Integer estado;
	private String usuarioCreacion;
	private Date fechaCreacion;
	private Integer totalRegistro;
	private Integer registroCorrecto;
	private Integer registroObservado;
	private String proceso;
	private Integer idTrama;
	private String estadoProceso;
	private String mensaje;
	
	public ControlPaquete(){
		
	}
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
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
	 * @return the flagEjecucion
	 */
	public Integer getFlagEjecucion() {
		return flagEjecucion;
	}
	/**
	 * @param flagEjecucion the flagEjecucion to set
	 */
	public void setFlagEjecucion(Integer flagEjecucion) {
		this.flagEjecucion = flagEjecucion;
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
	 * @return the totalRegistro
	 */
	public Integer getTotalRegistro() {
		return totalRegistro;
	}
	/**
	 * @param totalRegistro the totalRegistro to set
	 */
	public void setTotalRegistro(Integer totalRegistro) {
		this.totalRegistro = totalRegistro;
	}
	/**
	 * @return the registroCorrecto
	 */
	public Integer getRegistroCorrecto() {
		return registroCorrecto;
	}
	/**
	 * @param registroCorrecto the registroCorrecto to set
	 */
	public void setRegistroCorrecto(Integer registroCorrecto) {
		this.registroCorrecto = registroCorrecto;
	}
	/**
	 * @return the registroObservado
	 */
	public Integer getRegistroObservado() {
		return registroObservado;
	}
	/**
	 * @param registroObservado the registroObservado to set
	 */
	public void setRegistroObservado(Integer registroObservado) {
		this.registroObservado = registroObservado;
	}

	

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public Integer getIdTrama() {
		return idTrama;
	}

	public void setIdTrama(Integer idTrama) {
		this.idTrama = idTrama;
	}

	public String getEstadoProceso() {
		return estadoProceso;
	}

	public void setEstadoProceso(String estadoProceso) {
		this.estadoProceso = estadoProceso;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}
