package com.cardif.satelite.suscripcion.bean;

import java.io.Serializable;

public class TramaDiariaBean  implements Serializable{


	private static final long serialVersionUID = 1L;

	public String nombreColumna;
	public Integer numeroCaracteres;
	public String estado;
	public String tipoDato;
	public String label;
	
	public String getNombreColumna() {
		return nombreColumna;
	}
	public void setNombreColumna(String nombreColumna) {
		this.nombreColumna = nombreColumna;
	}
	public Integer getNumeroCaracteres() {
		return numeroCaracteres;
	}
	public void setNumeroCaracteres(Integer numeroCaracteres) {
		this.numeroCaracteres = numeroCaracteres;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String obligatorio) {
		estado = obligatorio;
	}
	public String getTipoDato() {
		return tipoDato;
	}
	public void setTipoDato(String tipoDato) {
		this.tipoDato = tipoDato;
	}
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	
}
