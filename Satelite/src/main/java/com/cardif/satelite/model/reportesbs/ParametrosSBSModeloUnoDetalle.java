package com.cardif.satelite.model.reportesbs;

import java.math.BigDecimal;

public class ParametrosSBSModeloUnoDetalle implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long codProcesoParametro;
	private Long codSocio;
	private String codTipoContrato = "";
	private String codTipoSeguro = "";
	private String codTipoReaseguro = "";
	private Long codEmpresa;
	private Long codProducto;
	private String codTipoMovimiento = "";
	private String codTipoCuenta = "";
	private Long codSBS;
	private String riesgo;
	private String detalleCuenta = "";
	private BigDecimal cuentaSaldoSoles = new BigDecimal("0.00");
	private BigDecimal cuentaSaldoDolares = new BigDecimal("0.00");
	private BigDecimal cuentaDescuentoSoles = new BigDecimal("0.00");
	private BigDecimal cuentaDescuentoDolares = new BigDecimal("0.00");
	private BigDecimal cuentaPrimaSoles = new BigDecimal("0.00");
	private BigDecimal cuentaPrimaDolares = new BigDecimal("0.00");

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

	public Long getCodSBS() {
		return codSBS;
	}

	public void setCodSBS(Long codSBS) {
		this.codSBS = codSBS;
	}

	public String getRiesgo() {
		return riesgo;
	}

	public void setRiesgo(String riesgo) {
		this.riesgo = riesgo;
	}

	public String getDetalleCuenta() {
		return detalleCuenta;
	}

	public void setDetalleCuenta(String detalleCuenta) {
		this.detalleCuenta = detalleCuenta;
	}

	public BigDecimal getCuentaSaldoSoles() {
		return cuentaSaldoSoles;
	}

	public void setCuentaSaldoSoles(BigDecimal cuentaSaldoSoles) {
		this.cuentaSaldoSoles = cuentaSaldoSoles;
	}

	public BigDecimal getCuentaSaldoDolares() {
		return cuentaSaldoDolares;
	}

	public void setCuentaSaldoDolares(BigDecimal cuentaSaldoDolares) {
		this.cuentaSaldoDolares = cuentaSaldoDolares;
	}

	public BigDecimal getCuentaDescuentoSoles() {
		return cuentaDescuentoSoles;
	}

	public void setCuentaDescuentoSoles(BigDecimal cuentaDescuentoSoles) {
		this.cuentaDescuentoSoles = cuentaDescuentoSoles;
	}

	public BigDecimal getCuentaDescuentoDolares() {
		return cuentaDescuentoDolares;
	}

	public void setCuentaDescuentoDolares(BigDecimal cuentaDescuentoDolares) {
		this.cuentaDescuentoDolares = cuentaDescuentoDolares;
	}

	public BigDecimal getCuentaPrimaSoles() {
		return cuentaPrimaSoles;
	}

	public void setCuentaPrimaSoles(BigDecimal cuentaPrimaSoles) {
		this.cuentaPrimaSoles = cuentaPrimaSoles;
	}

	public BigDecimal getCuentaPrimaDolares() {
		return cuentaPrimaDolares;
	}

	public void setCuentaPrimaDolares(BigDecimal cuentaPrimaDolares) {
		this.cuentaPrimaDolares = cuentaPrimaDolares;
	}

}
