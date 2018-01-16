
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
private int codigoEstado;
private String telefono;
private int idPerfil;
private String password;
private String usuarioCreacion;
private Date fechaCreacion;
private String usuarioModificacion;
private Date fechaModificacion;
private String numDocumento;
private String email;
private String usuario;

public Usuario() {

}

public int getIdUsuario() {
	return idUsuario;
}

public void setIdUsuario(int idUsuario) {
	this.idUsuario = idUsuario;
}

public String getApPaterno() {
	return apPaterno;
}

public void setApPaterno(String apPaterno) {
	this.apPaterno = apPaterno;
}

public String getApMaterno() {
	return apMaterno;
}

public void setApMaterno(String apMaterno) {
	this.apMaterno = apMaterno;
}

public String getNombres() {
	return nombres;
}

public void setNombres(String nombres) {
	this.nombres = nombres;
}

public int getIdTipoDocumento() {
	return idTipoDocumento;
}

public void setIdTipoDocumento(int idTipoDocumento) {
	this.idTipoDocumento = idTipoDocumento;
}

public int getCodigoEstado() {
	return codigoEstado;
}

public void setCodigoEstado(int codigoEstado) {
	this.codigoEstado = codigoEstado;
}

public String getTelefono() {
	return telefono;
}

public void setTelefono(String telefono) {
	this.telefono = telefono;
}

public int getIdPerfil() {
	return idPerfil;
}

public void setIdPerfil(int idPerfil) {
	this.idPerfil = idPerfil;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}


public Date getFechaCreacion() {
	return fechaCreacion;
}

public void setFechaCreacion(Date fechaCreacion) {
	this.fechaCreacion = fechaCreacion;
}

public Date getFechaModificacion() {
	return fechaModificacion;
}

public void setFechaModificacion(Date fechaModificacion) {
	this.fechaModificacion = fechaModificacion;
}

public String getNumDocumento() {
	return numDocumento;
}

public void setNumDocumento(String numDocumento) {
	this.numDocumento = numDocumento;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}

public String getUsuario() {
	return usuario;
}

public void setUsuario(String usuario) {
	this.usuario = usuario;
}

public String getUsuarioCreacion() {
	return usuarioCreacion;
}

public void setUsuarioCreacion(String usuarioCreacion) {
	this.usuarioCreacion = usuarioCreacion;
}

public String getUsuarioModificacion() {
	return usuarioModificacion;
}

public void setUsuarioModificacion(String usuarioModificacion) {
	this.usuarioModificacion = usuarioModificacion;
}

        
}
