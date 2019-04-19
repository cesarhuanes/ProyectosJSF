package com.proyecto.pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class HistorialContrato  implements Serializable{
	private static final long serialVersionUID = 1L;
	private int idHistorialContrato;
	 Usuario usuario;
	 TipoContrato tipoContrato;
	 Rol rol;
	 Estado estado;
	private BigDecimal sueldo ;
	private BigDecimal otrosCostos;
	private Date fechaInicio;
	private Date fechaFin;
	private Date fechaCreacion;
	private Date fechaModificacion;
	private String usuarioCreador;
	private String usuarioModificador;
	
	public HistorialContrato() {
		usuario = new Usuario();
		tipoContrato = new TipoContrato();
		rol = new Rol();
		estado = new Estado();
	}

	/**
	 * @return the idHistorialContrato
	 */
	public int getIdHistorialContrato() {
		return idHistorialContrato;
	}

	/**
	 * @param idHistorialContrato
	 *            the idHistorialContrato to set
	 */
	public void setIdHistorialContrato(int idHistorialContrato) {
		this.idHistorialContrato = idHistorialContrato;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the tipoContrato
	 */
	public TipoContrato getTipoContrato() {
		return tipoContrato;
	}

	/**
	 * @param tipoContrato
	 *            the tipoContrato to set
	 */
	public void setTipoContrato(TipoContrato tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	/**
	 * @return the rol
	 */
	public Rol getRol() {
		return rol;
	}

	/**
	 * @param rol
	 *            the rol to set
	 */
	public void setRol(Rol rol) {
		this.rol = rol;
	}

	/**
	 * @return the estado
	 */
	public Estado getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	
	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio
	 *            the fechaInicio to set
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
	 * @param fechaFin
	 *            the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	

	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion
	 *            the fechaCreacion to set
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
	 * @param fechaModificacion
	 *            the fechaModificacion to set
	 */
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	/**
	 * @return the usuarioCreador
	 */
	public String getUsuarioCreador() {
		return usuarioCreador;
	}

	/**
	 * @param usuarioCreador
	 *            the usuarioCreador to set
	 */
	public void setUsuarioCreador(String usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}

	/**
	 * @return the usuarioModificador
	 */
	public String getUsuarioModificador() {
		return usuarioModificador;
	}

	/**
	 * @param usuarioModificador
	 *            the usuarioModificador to set
	 */
	public void setUsuarioModificador(String usuarioModificador) {
		this.usuarioModificador = usuarioModificador;
	}

	/**
	 * @return the otrosCostos
	 */
	public BigDecimal getOtrosCostos() {
		return otrosCostos;
	}

	/**
	 * @param otrosCostos the otrosCostos to set
	 */
	public void setOtrosCostos(BigDecimal otrosCostos) {
		this.otrosCostos = otrosCostos;
	}

	/**
	 * @return the sueldo
	 */
	public BigDecimal getSueldo() {
		return sueldo;
	}

	/**
	 * @param sueldo the sueldo to set
	 */
	public void setSueldo(BigDecimal sueldo) {
		this.sueldo = sueldo;
	}

}
