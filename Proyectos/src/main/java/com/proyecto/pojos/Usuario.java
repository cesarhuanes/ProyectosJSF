
package com.proyecto.pojos;

import java.io.Serializable;
import java.util.Date;

public class Usuario implements Serializable{
	private static final long serialVersionUID = -1205586036289464017L;
	private int idUsuario;
	private String apPaterno;
	private String apMaterno;
	private String nombres;
	private int idTipoDocumento;
	private int idEstado;
	private String telefono;
	private int idPerfil;
	private String password;
	private String usuarioCreador;
	private Date fechaCreacion;
	private String usuarioModificador;
	private Date fechaModificacion;
	private String numDocumento;
	private String email;
	private String usuario;

	public Usuario() {

	}

	/**
	 * @return the idUsuario
	 */
	public int getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario
	 *            the idUsuario to set
	 */
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * @return the apPaterno
	 */
	public String getApPaterno() {
		return apPaterno;
	}

	/**
	 * @param apPaterno
	 *            the apPaterno to set
	 */
	public void setApPaterno(String apPaterno) {
		this.apPaterno = apPaterno;
	}

	/**
	 * @return the apMaterno
	 */
	public String getApMaterno() {
		return apMaterno;
	}

	/**
	 * @param apMaterno
	 *            the apMaterno to set
	 */
	public void setApMaterno(String apMaterno) {
		this.apMaterno = apMaterno;
	}

	/**
	 * @return the nombres
	 */
	public String getNombres() {
		return nombres;
	}

	/**
	 * @param nombres
	 *            the nombres to set
	 */
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	/**
	 * @return the idTipoDocumento
	 */
	public int getIdTipoDocumento() {
		return idTipoDocumento;
	}

	/**
	 * @param idTipoDocumento
	 *            the idTipoDocumento to set
	 */
	public void setIdTipoDocumento(int idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	/**
	 * @return the idEstado
	 */
	public int getIdEstado() {
		return idEstado;
	}

	/**
	 * @param idEstado
	 *            the idEstado to set
	 */
	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono
	 *            the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the idPerfil
	 */
	public int getIdPerfil() {
		return idPerfil;
	}

	/**
	 * @param idPerfil
	 *            the idPerfil to set
	 */
	public void setIdPerfil(int idPerfil) {
		this.idPerfil = idPerfil;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
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
	 * @return the numDocumento
	 */
	public String getNumDocumento() {
		return numDocumento;
	}

	/**
	 * @param numDocumento
	 *            the numDocumento to set
	 */
	public void setNumDocumento(String numDocumento) {
		this.numDocumento = numDocumento;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the usuarioModificador
	 */
	public String getUsuarioModificador() {
		return usuarioModificador;
	}

	/**
	 * @param usuarioModificador the usuarioModificador to set
	 */
	public void setUsuarioModificador(String usuarioModificador) {
		this.usuarioModificador = usuarioModificador;
	}

}
