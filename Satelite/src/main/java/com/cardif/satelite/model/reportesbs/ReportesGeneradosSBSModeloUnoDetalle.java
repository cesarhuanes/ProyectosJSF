package com.cardif.satelite.model.reportesbs;

import java.math.BigDecimal;
import java.util.Date;

public class ReportesGeneradosSBSModeloUnoDetalle {

	private BigDecimal codReporte;
	private BigDecimal estado;
	private String codTipoReporte;
	private String nomFirmante1;
	private String nomFirmante2;
	private String cargoFirmante1;
	private String cargoFirmante2;
	private String periodoAnio;
	private String periodoTrimestre;
	private BigDecimal tipoCambio;
	private String usuarioCreador;
	private String usuarioModificado;
	private Date fechaRegistro;

	public String getCodTipoReporte() {
		return codTipoReporte;
	}

	public void setCodTipoReporte(String codTipoReporte) {
		this.codTipoReporte = codTipoReporte;
	}

	public String getNomFirmante1() {
		return nomFirmante1;
	}

	public void setNomFirmante1(String nomFirmante1) {
		this.nomFirmante1 = nomFirmante1;
	}

	public String getNomFirmante2() {
		return nomFirmante2;
	}

	public void setNomFirmante2(String nomFirmante2) {
		this.nomFirmante2 = nomFirmante2;
	}

	public String getCargoFirmante1() {
		return cargoFirmante1;
	}

	public void setCargoFirmante1(String cargoFirmante1) {
		this.cargoFirmante1 = cargoFirmante1;
	}

	public String getCargoFirmante2() {
		return cargoFirmante2;
	}

	public void setCargoFirmante2(String cargoFirmante2) {
		this.cargoFirmante2 = cargoFirmante2;
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

	public BigDecimal getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(BigDecimal tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	public String getUsuarioCreador() {
		return usuarioCreador;
	}

	public void setUsuarioCreador(String usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}

	public String getUsuarioModificado() {
		return usuarioModificado;
	}

	public void setUsuarioModificado(String usuarioModificado) {
		this.usuarioModificado = usuarioModificado;
	}

	public BigDecimal getCodReporte() {
		return codReporte;
	}

	public void setCodReporte(BigDecimal codReporte) {
		this.codReporte = codReporte;
	}

	public BigDecimal getEstado() {
		return estado;
	}

	public void setEstado(BigDecimal estado) {
		this.estado = estado;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

}
