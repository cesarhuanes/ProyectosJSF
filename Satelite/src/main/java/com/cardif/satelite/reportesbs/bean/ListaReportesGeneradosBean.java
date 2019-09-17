package com.cardif.satelite.reportesbs.bean;

import java.util.Date;

public class ListaReportesGeneradosBean {

	private long codigoProceso;
	private String tipoReporte;
	private String periodo;
	private String anio;
	private String tipoCambio;
	private Date fechaRegistro;
	private String usuarioProceso;
	private String resultado;
	
	public long getCodigoProceso() {
		return codigoProceso;
	}
	public void setCodigoProceso(long codigoProceso) {
		this.codigoProceso = codigoProceso;
	}
	public String getTipoReporte() {
		return tipoReporte;
	}
	public void setTipoReporte(String tipoReporte) {
		this.tipoReporte = tipoReporte;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public String getAnio() {
		return anio;
	}
	public void setAnio(String anio) {
		this.anio = anio;
	}
	public String getTipoCambio() {
		return tipoCambio;
	}
	public void setTipoCambio(String tipoCambio) {
		this.tipoCambio = tipoCambio;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public String getUsuarioProceso() {
		return usuarioProceso;
	}
	public void setUsuarioProceso(String usuarioProceso) {
		this.usuarioProceso = usuarioProceso;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	
}
