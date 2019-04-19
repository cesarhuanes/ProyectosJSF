package com.proyecto.pojos;

import java.io.Serializable;

public class TipoTrabajo implements Serializable {
	private static final long serialVersionUID = 1245845987586353451L;
	private int idTipoTrabajo;
	private String descripcion;
	public TipoTrabajo(){
		
	}
	/**
	 * @return the idTipoTrabajo
	 */
	public int getIdTipoTrabajo() {
		return idTipoTrabajo;
	}
	/**
	 * @param idTipoTrabajo the idTipoTrabajo to set
	 */
	public void setIdTipoTrabajo(int idTipoTrabajo) {
		this.idTipoTrabajo = idTipoTrabajo;
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
