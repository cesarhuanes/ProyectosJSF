package com.proyecto.pojos;

import java.io.Serializable;

public class TipoContrato implements Serializable{
	private static final long serialVersionUID = 1L;
	private int idTipoContrato;
	private String descripcion;

	public TipoContrato() {

	}

	/**
	 * @return the idTipoContrato
	 */
	public int getIdTipoContrato() {
		return idTipoContrato;
	}

	/**
	 * @param idTipoContrato
	 *            the idTipoContrato to set
	 */
	public void setIdTipoContrato(int idTipoContrato) {
		this.idTipoContrato = idTipoContrato;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
