package com.cardif.satelite.model.reportesbs;

import java.util.Date;

public class ProcesoParametro {

	private Long codProcesoParametro;
	private Long codSocio;
	private String socio;
	private String codTipoSeguro;
	private String tipoSeguro;
	private String codTipoReaseguro;
	private String tipoReaseguro;
	private String codTipoContrato;
	private String tipoContrato;
	private Long codEmpresa;
	private String empresa;
	private Long codProducto;
	private String producto;
	private String codEstado;
	private String estadoDesc;
	private String codTipoMovimiento;
	private String tipoMovimiento;
	private String codTipoCuenta;
	private String tipoCuenta;
	private Long codSbs;
	private String riesgo;
	private String detalleCuenta;
	private Long cuentaSaldoSoles;
	private Long cuentaPrimaSoles;
	private Long cuentaDescuentoSoles;
	private Long cuentaSaldoDolares;
	private Long cuentaPrimaDolares;
	private Long cuentaDescuentoDolares;
	private String usuarioCreador;
	private Date fechaCreador;
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

	public String getSocio() {
		return socio;
	}

	public void setSocio(String socio) {
		this.socio = socio;
	}

	public String getCodTipoSeguro() {
		return codTipoSeguro;
	}

	public void setCodTipoSeguro(String codTipoSeguro) {
		this.codTipoSeguro = codTipoSeguro;
	}

	public String getTipoSeguro() {
		return tipoSeguro;
	}

	public void setTipoSeguro(String tipoSeguro) {
		this.tipoSeguro = tipoSeguro;
	}

	public String getCodTipoReaseguro() {
		return codTipoReaseguro;
	}

	public void setCodTipoReaseguro(String codTipoReaseguro) {
		this.codTipoReaseguro = codTipoReaseguro;
	}

	public String getTipoReaseguro() {
		return tipoReaseguro;
	}

	public void setTipoReaseguro(String tipoReaseguro) {
		this.tipoReaseguro = tipoReaseguro;
	}

	public Long getCodEmpresa() {
		return codEmpresa;
	}

	public void setCodEmpresa(Long codEmpresa) {
		this.codEmpresa = codEmpresa;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa == null ? null : empresa.trim();

	}

	public Long getCodProducto() {
		return codProducto;
	}

	public void setCodProducto(Long codProducto) {
		this.codProducto = codProducto;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public String getEstadoDesc() {
		return estadoDesc;
	}

	public void setEstadoDesc(String estadoDesc) {
		this.estadoDesc = estadoDesc;
	}

	public String getCodTipoMovimiento() {
		return codTipoMovimiento;
	}

	public void setCodTipoMovimiento(String codTipoMovimiento) {
		this.codTipoMovimiento = codTipoMovimiento;
	}

	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public String getCodTipoCuenta() {
		return codTipoCuenta;
	}

	public void setCodTipoCuenta(String codTipoCuenta) {
		this.codTipoCuenta = codTipoCuenta;
	}

	public String getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
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

	public Long getCuentaSaldoSoles() {
		return cuentaSaldoSoles;
	}

	public void setCuentaSaldoSoles(Long cuentaSaldoSoles) {
		this.cuentaSaldoSoles = cuentaSaldoSoles;
	}

	public Long getCuentaPrimaSoles() {
		return cuentaPrimaSoles;
	}

	public void setCuentaPrimaSoles(Long cuentaPrimaSoles) {
		this.cuentaPrimaSoles = cuentaPrimaSoles;
	}

	public Long getCuentaDescuentoSoles() {
		return cuentaDescuentoSoles;
	}

	public void setCuentaDescuentoSoles(Long cuentaDescuentoSoles) {
		this.cuentaDescuentoSoles = cuentaDescuentoSoles;
	}

	public Long getCuentaSaldoDolares() {
		return cuentaSaldoDolares;
	}

	public void setCuentaSaldoDolares(Long cuentaSaldoDolares) {
		this.cuentaSaldoDolares = cuentaSaldoDolares;
	}

	public Long getCuentaPrimaDolares() {
		return cuentaPrimaDolares;
	}

	public void setCuentaPrimaDolares(Long cuentaPrimaDolares) {
		this.cuentaPrimaDolares = cuentaPrimaDolares;
	}

	public Long getCuentaDescuentoDolares() {
		return cuentaDescuentoDolares;
	}

	public void setCuentaDescuentoDolares(Long cuentaDescuentoDolares) {
		this.cuentaDescuentoDolares = cuentaDescuentoDolares;
	}

	public String getUsuarioCreador() {
		return usuarioCreador;
	}

	public void setUsuarioCreador(String usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}

	public Date getFechaCreador() {
		return fechaCreador;
	}

	public void setFechaCreador(Date fechaCreador) {
		this.fechaCreador = fechaCreador;
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

	public String getCodTipoContrato() {
		return codTipoContrato;
	}

	public void setCodTipoContrato(String codTipoContrato) {
		this.codTipoContrato = codTipoContrato;
	}

	public String getTipoContrato() {
		return tipoContrato;
	}

	public void setTipoContrato(String tipoContrato) {
		this.tipoContrato = tipoContrato;
	}

	public String getCodEstado() {
		return codEstado;
	}

	public void setCodEstado(String codEstado) {
		this.codEstado = codEstado;
	}

	public String getRiesgo() {
		return riesgo;
	}

	public void setRiesgo(String riesgo) {
		this.riesgo = riesgo;
	}

}
