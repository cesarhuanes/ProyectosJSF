package com.cardif.satelite.suscripcion.bean;

public class TramaEjecucion {

	private Boolean iniciando;
	private Boolean enProceso;
	private Boolean finalizo;
	private Integer nroRegistros;
	private Integer nroRegistrosObservados;
	private Integer nroRegistrosCorrectos;
	private String flagEstado;
	private String estadoProceso;
	private String mensaje;
	
	
	public TramaEjecucion (){
		
	}

	/**
	 * @return the iniciando
	 */
	public Boolean getIniciando() {
		return iniciando;
	}

	/**
	 * @param iniciando the iniciando to set
	 */
	public void setIniciando(Boolean iniciando) {
		this.iniciando = iniciando;
	}

	/**
	 * @return the enProceso
	 */
	public Boolean getEnProceso() {
		return enProceso;
	}

	/**
	 * @param enProceso the enProceso to set
	 */
	public void setEnProceso(Boolean enProceso) {
		this.enProceso = enProceso;
	}

	
	
	/**
	 * @return the finalizo
	 */
	public Boolean getFinalizo() {
		return finalizo;
	}

	/**
	 * @param finalizo the finalizo to set
	 */
	public void setFinalizo(Boolean finalizo) {
		this.finalizo = finalizo;
	}

	/**
	 * @return the nroRegistros
	 */
	public Integer getNroRegistros() {
		return nroRegistros;
	}

	/**
	 * @param nroRegistros the nroRegistros to set
	 */
	public void setNroRegistros(Integer nroRegistros) {
		this.nroRegistros = nroRegistros;
	}

	/**
	 * @return the nroRegistrosObservados
	 */
	public Integer getNroRegistrosObservados() {
		return nroRegistrosObservados;
	}

	/**
	 * @param nroRegistrosObservados the nroRegistrosObservados to set
	 */
	public void setNroRegistrosObservados(Integer nroRegistrosObservados) {
		this.nroRegistrosObservados = nroRegistrosObservados;
	}

	/**
	 * @return the nroRegistrosCorrectos
	 */
	public Integer getNroRegistrosCorrectos() {
		return nroRegistrosCorrectos;
	}

	/**
	 * @param nroRegistrosCorrectos the nroRegistrosCorrectos to set
	 */
	public void setNroRegistrosCorrectos(Integer nroRegistrosCorrectos) {
		this.nroRegistrosCorrectos = nroRegistrosCorrectos;
	}

	/**
	 * @return the flagEstado
	 */
	public String getFlagEstado() {
		return flagEstado;
	}

	/**
	 * @param flagEstado the flagEstado to set
	 */
	public void setFlagEstado(String flagEstado) {
		this.flagEstado = flagEstado;
	}

	public String getEstadoProceso() {
		return estadoProceso;
	}

	public void setEstadoProceso(String estadoProceso) {
		this.estadoProceso = estadoProceso;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
	
}
