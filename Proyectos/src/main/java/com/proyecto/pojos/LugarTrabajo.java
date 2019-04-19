package com.proyecto.pojos;

import java.io.Serializable;

public class LugarTrabajo implements Serializable{
	
	private static final long serialVersionUID = -1330704433409398825L;
	private int idLugarTrabajo;
	private String descripcion;
	public LugarTrabajo() {

	}
	/**
	 * @return the idLugarTrabajo
	 */
	public int getIdLugarTrabajo() {
		return idLugarTrabajo;
	}
	/**
	 * @param idLugarTrabajo the idLugarTrabajo to set
	 */
	public void setIdLugarTrabajo(int idLugarTrabajo) {
		this.idLugarTrabajo = idLugarTrabajo;
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
