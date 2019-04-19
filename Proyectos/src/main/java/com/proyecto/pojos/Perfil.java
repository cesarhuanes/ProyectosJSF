package com.proyecto.pojos;

import java.io.Serializable;

public class Perfil implements Serializable {
	private static final long serialVersionUID = -1458737839282062559L;
	private int idPerfil;
	private String descripcion;
	public Perfil(){
		
	}
	/**
	 * @return the idPerfil
	 */
	public int getIdPerfil() {
		return idPerfil;
	}
	/**
	 * @param idPerfil the idPerfil to set
	 */
	public void setIdPerfil(int idPerfil) {
		this.idPerfil = idPerfil;
	}
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
