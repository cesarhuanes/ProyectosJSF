package com.proyecto.pojos;

import java.io.Serializable;

public class TipoDocumento  implements Serializable{
	private static final long serialVersionUID = 2340304126749114075L;
	private int idTipoDocumento;
	private String descripcion;
	
	public TipoDocumento(){
		
	}

	/**
	 * @return the idTipoDocumento
	 */
	public int getIdTipoDocumento() {
		return idTipoDocumento;
	}

	/**
	 * @param idTipoDocumento the idTipoDocumento to set
	 */
	public void setIdTipoDocumento(int idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
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
