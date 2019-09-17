package com.cardif.satelite.model.reportesbs;

public class Parametro {
	private String codParametro;
	private String codValor;
	private String descripcion;
	private Long estado;
	private Long numOrden;

	public String getCodParametro() {
		return codParametro;
	}

	public void setCodParametro(String codParametro) {
		this.codParametro = codParametro;
	}

	public String getCodValor() {
		return codValor;
	}

	public void setCodValor(String codValor) {
		this.codValor = codValor;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

	public Long getNumOrden() {
		return numOrden;
	}

	public void setNumOrden(Long numOrden) {
		this.numOrden = numOrden;
	}

}
