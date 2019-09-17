package com.cardif.satelite.model.reportesbs;

import java.util.Date;

public class Proceso {
	private Long codProcesoParametro;
	private Long codSocio;
	private String codTipoContrato;
	private String codTipoSeguro;
	private String codTipoReaseguro;
	private Long codEmpresa;
	private Long codProducto;
	private String codEstado;
	private String codTipoMovimiento;
	private String codTipoCuenta;
	private Long codSbs;
	private String riesgo;
	private String detalleCuenta;
	private String cuentaSaldoSoles;
	private String cuentaPrimaSoles;
	private String cuentaDescuentoSoles;
	private String cuentaSaldoDolares;
	private String cuentaPrimaDolares;
	private String cuentaDescuentoDolares;
	private String usuarioCreador;
	private Date fechaCreado;
	private String usuarioModificador;
	private Date fechaModificador;

	public Long getCodProcesoParametro() {
		return codProcesoParametro;
	}

	public void setCodProcesoParametro(Long codProcesoParametro) {
		this.codProcesoParametro = codProcesoParametro;
	}

	public Long getCodSocio() {
		return codSocio;
	}

	public void setCodSocio(Long codSocio) {
		this.codSocio = codSocio;
	}

	public String getCodTipoContrato() {
		return codTipoContrato;
	}

	public void setCodTipoContrato(String codTipoContrato) {
		this.codTipoContrato = codTipoContrato;
	}

	public String getCodTipoSeguro() {
		return codTipoSeguro;
	}

	public void setCodTipoSeguro(String codTipoSeguro) {
		this.codTipoSeguro = codTipoSeguro;
	}

	public String getCodTipoReaseguro() {
		return codTipoReaseguro;
	}

	public void setCodTipoReaseguro(String codTipoReaseguro) {
		this.codTipoReaseguro = codTipoReaseguro;
	}

	public Long getCodEmpresa() {
		return codEmpresa;
	}

	public void setCodEmpresa(Long codEmpresa) {
		this.codEmpresa = codEmpresa;
	}

	public Long getCodProducto() {
		return codProducto;
	}

	public void setCodProducto(Long codProducto) {
		this.codProducto = codProducto;
	}

	public String getCodEstado() {
		return codEstado;
	}

	public void setCodEstado(String codEstado) {
		this.codEstado = codEstado;
	}

	public String getCodTipoMovimiento() {
		return codTipoMovimiento;
	}

	public void setCodTipoMovimiento(String codTipoMovimiento) {
		this.codTipoMovimiento = codTipoMovimiento;
	}

	public String getCodTipoCuenta() {
		return codTipoCuenta;
	}

	public void setCodTipoCuenta(String codTipoCuenta) {
		this.codTipoCuenta = codTipoCuenta;
	}

	public Long getCodSbs() {
		return codSbs;
	}

	public void setCodSbs(Long codSbs) {
		this.codSbs = codSbs;
	}

	public String getDetalleCuenta() {
		return detalleCuenta;
	}

	public void setDetalleCuenta(String detalleCuenta) {
		this.detalleCuenta = detalleCuenta;
	}

	

	public String getUsuarioCreador() {
		return usuarioCreador;
	}

	public void setUsuarioCreador(String usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}

	public String getUsuarioModificador() {
		return usuarioModificador;
	}

	public void setUsuarioModificador(String usuarioModificador) {
		this.usuarioModificador = usuarioModificador;
	}

	public Date getFechaModificador() {
		return fechaModificador;
	}

	public void setFechaModificador(Date fechaModificador) {
		this.fechaModificador = fechaModificador;
	}

	public Date getFechaCreado() {
		return fechaCreado;
	}

	public void setFechaCreado(Date fechaCreado) {
		this.fechaCreado = fechaCreado;
	}

	public String getCuentaSaldoSoles() {
		return cuentaSaldoSoles;
	}

	public void setCuentaSaldoSoles(String cuentaSaldoSoles) {
		this.cuentaSaldoSoles = cuentaSaldoSoles;
	}

	public String getCuentaPrimaSoles() {
		return cuentaPrimaSoles;
	}

	public void setCuentaPrimaSoles(String cuentaPrimaSoles) {
		this.cuentaPrimaSoles = cuentaPrimaSoles;
	}

	public String getCuentaSaldoDolares() {
		return cuentaSaldoDolares;
	}

	public void setCuentaSaldoDolares(String cuentaSaldoDolares) {
		this.cuentaSaldoDolares = cuentaSaldoDolares;
	}

	public String getCuentaPrimaDolares() {
		return cuentaPrimaDolares;
	}

	public void setCuentaPrimaDolares(String cuentaPrimaDolares) {
		this.cuentaPrimaDolares = cuentaPrimaDolares;
	}

	public String getCuentaDescuentoDolares() {
		return cuentaDescuentoDolares;
	}

	public void setCuentaDescuentoDolares(String cuentaDescuentoDolares) {
		this.cuentaDescuentoDolares = cuentaDescuentoDolares;
	}

	public String getCuentaDescuentoSoles() {
		return cuentaDescuentoSoles;
	}

	public void setCuentaDescuentoSoles(String cuentaDescuentoSoles) {
		this.cuentaDescuentoSoles = cuentaDescuentoSoles;
	}

	public String getRiesgo() {
		return riesgo;
	}

	public void setRiesgo(String riesgo) {
		this.riesgo = riesgo;
	}

}
