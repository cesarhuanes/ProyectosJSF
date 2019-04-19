package com.proyecto.pojos;

import java.io.Serializable;

public class TipoMoneda implements Serializable{

	private static final long serialVersionUID = 1L;
	private int idTipoMoneda;
	private String descripcion;
	public TipoMoneda(){
		
	}
	/**
	 * @return the idTipoMoneda
	 */
	public int getIdTipoMoneda() {
		return idTipoMoneda;
	}

	/**
	 * @param idTipoMoneda the idTipoMoneda to set
	 */
	public void setIdTipoMoneda(int idTipoMoneda) {
		this.idTipoMoneda = idTipoMoneda;
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
