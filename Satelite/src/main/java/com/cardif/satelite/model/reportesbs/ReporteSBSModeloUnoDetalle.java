package com.cardif.satelite.model.reportesbs;

import java.math.BigDecimal;
import java.util.Date;

public class ReporteSBSModeloUnoDetalle implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigoReporte;
	private String modeloRepore;
	private String nomFirmanteUno;
	private String nomFirmanteDos;
	private String cargoFirmanteUno;
	private String cargoFirmanteDos;
	private String periodoAnio;
	private String periodoTrimestre;
	private BigDecimal tipoCambio;
	private String estado;
	private String usuarioCreador;
	private Date fechaCreado;

	public String getModeloRepore() {
		return modeloRepore;
	}

	public void setModeloRepore(String modeloRepore) {
		this.modeloRepore = modeloRepore;
	}

	public String getNomFirmanteUno() {
		return nomFirmanteUno;
	}

	public void setNomFirmanteUno(String nomFirmanteUno) {
		this.nomFirmanteUno = nomFirmanteUno;
	}

	public String getNomFirmanteDos() {
		return nomFirmanteDos;
	}

	public void setNomFirmanteDos(String nomFirmanteDos) {
		this.nomFirmanteDos = nomFirmanteDos;
	}

	public String getCargoFirmanteUno() {
		return cargoFirmanteUno;
	}

	public void setCargoFirmanteUno(String cargoFirmanteUno) {
		this.cargoFirmanteUno = cargoFirmanteUno;
	}

	public String getCargoFirmanteDos() {
		return cargoFirmanteDos;
	}

	public void setCargoFirmanteDos(String cargoFirmanteDos) {
		this.cargoFirmanteDos = cargoFirmanteDos;
	}

	public BigDecimal getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(BigDecimal tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Long getCodigoReporte() {
		return codigoReporte;
	}

	public void setCodigoReporte(Long codigoReporte) {
		this.codigoReporte = codigoReporte;
	}

	public String getPeriodoAnio() {
		return periodoAnio;
	}

	public void setPeriodoAnio(String periodoAnio) {
		this.periodoAnio = periodoAnio;
	}

	public String getPeriodoTrimestre() {
		return periodoTrimestre;
	}

	public void setPeriodoTrimestre(String periodoTrimestre) {
		this.periodoTrimestre = periodoTrimestre;
	}

	public String getUsuarioCreador() {
		return usuarioCreador;
	}

	public void setUsuarioCreador(String usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}

	public Date getFechaCreado() {
		return fechaCreado;
	}

	public void setFechaCreado(Date fechaCreado) {
		this.fechaCreado = fechaCreado;
	}

}
