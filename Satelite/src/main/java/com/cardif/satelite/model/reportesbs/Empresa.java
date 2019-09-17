package com.cardif.satelite.model.reportesbs;

public class Empresa {

	private Long codEmpresa;
	private String descripcion;
	private String codSbs;
	private Long estado;

	public Long getCodEmpresa() {
		return codEmpresa;
	}

	public void setCodEmpresa(Long codEmpresa) {
		this.codEmpresa = codEmpresa;

	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getCodSbs() {
		return codSbs;
	}

	public void setCodSbs(String codSbs) {
		this.codSbs = codSbs;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

}
