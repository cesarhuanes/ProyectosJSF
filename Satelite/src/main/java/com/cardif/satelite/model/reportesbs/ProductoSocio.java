package com.cardif.satelite.model.reportesbs;

import java.util.Date;

public class ProductoSocio {
	private Long codProducto;
	private Long codSocio;
	private int codEstado;
	private String usu_creador;
	private Date fec_creacion;
	public Long getCodProducto() {
		return codProducto;
	}
	public void setCodProducto(Long codProducto) {
		this.codProducto = codProducto;
	}
	public Long getCodSocio() {
		return codSocio;
	}
	public void setCodSocio(Long codSocio) {
		this.codSocio = codSocio;
	}
	
	public String getUsu_creador() {
		return usu_creador;
	}
	public void setUsu_creador(String usu_creador) {
		this.usu_creador = usu_creador;
	}
	public Date getFec_creacion() {
		return fec_creacion;
	}
	public void setFec_creacion(Date fec_creacion) {
		this.fec_creacion = fec_creacion;
	}
	public int getCodEstado() {
		return codEstado;
	}
	public void setCodEstado(int codEstado) {
		this.codEstado = codEstado;
	}
	
	

}
