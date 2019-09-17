package com.cardif.satelite.suscripcion.bean;

import java.io.Serializable;

public class SocioProductoBean implements Serializable {

private static final long serialVersionUID = 1L;

//data Socio 
 private Long idSocio;
 private String nombreSocio;
 private String estadoSocio;
 
// data Producto
 private Long idProducto;
 private String nombreProducto;
 private Integer tramaDiaria;
 private Integer tramaMensual;
 private String tipoArchivo;
 private String separador;
 
public Long getIdSocio() {
	return idSocio;
}
public void setIdSocio(Long idSocio) {
	this.idSocio = idSocio;
}
public String getNombreSocio() {
	return nombreSocio;
}
public void setNombreSocio(String nombreSocio) {
	this.nombreSocio = nombreSocio;
}
public String getEstadoSocio() {
	return estadoSocio;
}
public void setEstadoSocio(String estadoSocio) {
	this.estadoSocio = estadoSocio;
}
public Long getIdProducto() {
	return idProducto;
}
public void setIdProducto(Long idProducto) {
	this.idProducto = idProducto;
}
public String getNombreProducto() {
	return nombreProducto;
}
public void setNombreProducto(String nombreProducto) {
	this.nombreProducto = nombreProducto;
}
public Integer getTramaDiaria() {
	return tramaDiaria;
}
public void setTramaDiaria(Integer tramaDiaria) {
	this.tramaDiaria = tramaDiaria;
}
public Integer getTramaMensual() {
	return tramaMensual;
}
public void setTramaMensual(Integer tramaMensual) {
	this.tramaMensual = tramaMensual;
}
public String getTipoArchivo() {
	return tipoArchivo;
}
public void setTipoArchivo(String tipoArchivo) {
	this.tipoArchivo = tipoArchivo;
}
public String getSeparador() {
	return separador;
}
public void setSeparador(String separador) {
	this.separador = separador;
}


 
 
}
