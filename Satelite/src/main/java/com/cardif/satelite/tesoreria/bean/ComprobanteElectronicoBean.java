package com.cardif.satelite.tesoreria.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ComprobanteElectronicoBean implements Serializable {
	private static final long serialVersionUID = 1L;

	String enTipoComprobante;
	String enNroComprobante;
	Date enFechaEmision;
	String enEmiNroDocIdent;
	String enEmiTipoDocIdent;
	String enEmiNombreComercial;
	String enEmiRazonSocial;
	String enEmiDomUbigeo;
	String enEmiDomDireccionCompleta;
	String enEmiDomUrbanizacion;
	String enEmiDomProvincia;
	String enEmiDomDepartamento;
	String enEmiDomDistrito;
	String enEmiDomPais;
	String enProTipoDocIdent;
	String enProNroDocIdent;
	String enProNombreComercial;
	String enProRazonSocial;
	String enProDomUbigeo;
	String enProDomDireccionCompleta;
	String enProDomUrbanizacion;
	String enProDomProvincia;
	String enProDomDepartamento;
	String enProDomDistrito;
	String enProDomPais;
	String enRetPerReg;
	Double enRetPerTasa;
	String enRetPerObservaciones;
	Double enRetPerImporteRetenidoPercibido;
	String enRetPerMoneda;
	Double enRetPerImportePagadoCobrado;
	String pePlantilla;
	String peCorreoCliente;
	String pePagWeb;
	String peTipoCambio;
	String peMontoLetras;
	String estadoComprobante;
	Date fechaCreacion;
	String creadoPor;
	Date fechaModificacion;
	String modificadoPor;
	String motivoAnulacion;
	String observacionDteRecovery;
	String codigoProveedor;
	String nroDiario;
	List<ComprobanteRelacionadoBean> comprobanteRelacionado;
	
	/* Para seleccionar un registro */
	private boolean seleccion;
	

	public String getEnTipoComprobante() {
		return enTipoComprobante;
	}

	public void setEnTipoComprobante(String enTipoComprobante) {
		this.enTipoComprobante = enTipoComprobante;
	}

	public Date getEnFechaEmision() {
		return enFechaEmision;
	}

	public void setEnFechaEmision(Date enFechaEmision) {
		this.enFechaEmision = enFechaEmision;
	}

	public String getEnEmiNroDocIdent() {
		return enEmiNroDocIdent;
	}

	public void setEnEmiNroDocIdent(String enEmiNroDocIdent) {
		this.enEmiNroDocIdent = enEmiNroDocIdent;
	}

	public String getEnEmiNombreComercial() {
		return enEmiNombreComercial;
	}

	public void setEnEmiNombreComercial(String enEmiNombreComercial) {
		this.enEmiNombreComercial = enEmiNombreComercial;
	}

	public String getEnEmiRazonSocial() {
		return enEmiRazonSocial;
	}

	public void setEnEmiRazonSocial(String enEmiRazonSocial) {
		this.enEmiRazonSocial = enEmiRazonSocial;
	}

	public String getEnEmiDomUbigeo() {
		return enEmiDomUbigeo;
	}

	public void setEnEmiDomUbigeo(String enEmiDomUbigeo) {
		this.enEmiDomUbigeo = enEmiDomUbigeo;
	}

	public String getEnEmiDomDireccionCompleta() {
		return enEmiDomDireccionCompleta;
	}

	public void setEnEmiDomDireccionCompleta(String enEmiDomDireccionCompleta) {
		this.enEmiDomDireccionCompleta = enEmiDomDireccionCompleta;
	}

	public String getEnEmiDomUrbanizacion() {
		return enEmiDomUrbanizacion;
	}

	public void setEnEmiDomUrbanizacion(String enEmiDomUrbanizacion) {
		this.enEmiDomUrbanizacion = enEmiDomUrbanizacion;
	}

	public String getEnEmiDomProvincia() {
		return enEmiDomProvincia;
	}

	public void setEnEmiDomProvincia(String enEmiDomProvincia) {
		this.enEmiDomProvincia = enEmiDomProvincia;
	}

	public String getEnEmiDomDepartamento() {
		return enEmiDomDepartamento;
	}

	public void setEnEmiDomDepartamento(String enEmiDomDepartamento) {
		this.enEmiDomDepartamento = enEmiDomDepartamento;
	}

	public String getEnEmiDomDistrito() {
		return enEmiDomDistrito;
	}

	public void setEnEmiDomDistrito(String enEmiDomDistrito) {
		this.enEmiDomDistrito = enEmiDomDistrito;
	}

	public String getEnEmiDomPais() {
		return enEmiDomPais;
	}

	public void setEnEmiDomPais(String enEmiDomPais) {
		this.enEmiDomPais = enEmiDomPais;
	}

	public String getEnProNroDocIdent() {
		return enProNroDocIdent;
	}

	public void setEnProNroDocIdent(String enProNroDocIdent) {
		this.enProNroDocIdent = enProNroDocIdent;
	}

	public String getEnProNombreComercial() {
		return enProNombreComercial;
	}

	public void setEnProNombreComercial(String enProNombreComercial) {
		this.enProNombreComercial = enProNombreComercial;
	}

	public String getEnProRazonSocial() {
		return enProRazonSocial;
	}

	public void setEnProRazonSocial(String enProRazonSocial) {
		this.enProRazonSocial = enProRazonSocial;
	}

	public String getEnProDomUbigeo() {
		return enProDomUbigeo;
	}

	public void setEnProDomUbigeo(String enProDomUbigeo) {
		this.enProDomUbigeo = enProDomUbigeo;
	}

	public String getEnProDomDireccionCompleta() {
		return enProDomDireccionCompleta;
	}

	public void setEnProDomDireccionCompleta(String enProDomDireccionCompleta) {
		this.enProDomDireccionCompleta = enProDomDireccionCompleta;
	}

	public String getEnProDomUrbanizacion() {
		return enProDomUrbanizacion;
	}

	public void setEnProDomUrbanizacion(String enProDomUrbanizacion) {
		this.enProDomUrbanizacion = enProDomUrbanizacion;
	}

	public String getEnProDomProvincia() {
		return enProDomProvincia;
	}

	public void setEnProDomProvincia(String enProDomProvincia) {
		this.enProDomProvincia = enProDomProvincia;
	}

	public String getEnProDomDepartamento() {
		return enProDomDepartamento;
	}

	public void setEnProDomDepartamento(String enProDomDepartamento) {
		this.enProDomDepartamento = enProDomDepartamento;
	}

	public String getEnProDomDistrito() {
		return enProDomDistrito;
	}

	public void setEnProDomDistrito(String enProDomDistrito) {
		this.enProDomDistrito = enProDomDistrito;
	}

	public String getEnProDomPais() {
		return enProDomPais;
	}

	public void setEnProDomPais(String enProDomPais) {
		this.enProDomPais = enProDomPais;
	}

	public String getEnRetPerReg() {
		return enRetPerReg;
	}

	public void setEnRetPerReg(String enRetPerReg) {
		this.enRetPerReg = enRetPerReg;
	}

	public Double getEnRetPerTasa() {
		return enRetPerTasa;
	}

	public void setEnRetPerTasa(Double enRetPerTasa) {
		this.enRetPerTasa = enRetPerTasa;
	}

	public String getEnRetPerObservaciones() {
		return enRetPerObservaciones;
	}

	public void setEnRetPerObservaciones(String enRetPerObservaciones) {
		this.enRetPerObservaciones = enRetPerObservaciones;
	}

	public Double getEnRetPerImporteRetenidoPercibido() {
		return enRetPerImporteRetenidoPercibido;
	}

	public void setEnRetPerImporteRetenidoPercibido(Double enRetPerImporteRetenidoPercibido) {
		this.enRetPerImporteRetenidoPercibido = enRetPerImporteRetenidoPercibido;
	}

	public String getEnRetPerMoneda() {
		return enRetPerMoneda;
	}

	public void setEnRetPerMoneda(String enRetPerMoneda) {
		this.enRetPerMoneda = enRetPerMoneda;
	}

	public Double getEnRetPerImportePagadoCobrado() {
		return enRetPerImportePagadoCobrado;
	}

	public void setEnRetPerImportePagadoCobrado(
			Double enRetPerImportePagadoCobrado) {
		this.enRetPerImportePagadoCobrado = enRetPerImportePagadoCobrado;
	}

	public String getPePlantilla() {
		return pePlantilla;
	}

	public void setPePlantilla(String pePlantilla) {
		this.pePlantilla = pePlantilla;
	}

	public String getPeCorreoCliente() {
		return peCorreoCliente;
	}

	public void setPeCorreoCliente(String peCorreoCliente) {
		this.peCorreoCliente = peCorreoCliente;
	}

	public String getPePagWeb() {
		return pePagWeb;
	}

	public void setPePagWeb(String pePagWeb) {
		this.pePagWeb = pePagWeb;
	}

	public String getPeTipoCambio() {
		return peTipoCambio;
	}

	public void setPeTipoCambio(String peTipoCambio) {
		this.peTipoCambio = peTipoCambio;
	}

	public String getPeMontoLetras() {
		return peMontoLetras;
	}

	public void setPeMontoLetras(String peMontoLetras) {
		this.peMontoLetras = peMontoLetras;
	}

	public List<ComprobanteRelacionadoBean> getComprobanteRelacionado() {
		return comprobanteRelacionado;
	}

	public void setComprobanteRelacionado(
			List<ComprobanteRelacionadoBean> comprobanteRelacionado) {
		this.comprobanteRelacionado = comprobanteRelacionado;
	}

	public String getEstadoComprobante() {
		return estadoComprobante;
	}

	public void setEstadoComprobante(String estadoComprobante) {
		this.estadoComprobante = estadoComprobante;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getCreadoPor() {
		return creadoPor;
	}

	public void setCreadoPor(String creadoPor) {
		this.creadoPor = creadoPor;
	}

	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	public String getModificadoPor() {
		return modificadoPor;
	}

	public void setModificadoPor(String modificadoPor) {
		this.modificadoPor = modificadoPor;
	}

	public String getMotivoAnulacion() {
		return motivoAnulacion;
	}

	public void setMotivoAnulacion(String motivoAnulacion) {
		this.motivoAnulacion = motivoAnulacion;
	}

	public String getObservacionDteRecovery() {
		return observacionDteRecovery;
	}

	public void setObservacionDteRecovery(String observacionDteRecovery) {
		this.observacionDteRecovery = observacionDteRecovery;
	}

	public boolean isSeleccion() {
		return seleccion;
	}

	public void setSeleccion(boolean seleccion) {
		this.seleccion = seleccion;
	}

  public String getCodigoProveedor() {
    return codigoProveedor;
  }

  public void setCodigoProveedor(String codigoProveedor) {
    this.codigoProveedor = codigoProveedor;
  }

  public String getNroDiario() {
    return nroDiario;
  }

  public void setNroDiario(String nroDiario) {
    this.nroDiario = nroDiario;
  }

  public String getEnEmiTipoDocIdent() {
    return enEmiTipoDocIdent;
  }

  public void setEnEmiTipoDocIdent(String enEmiTipoDocIdent) {
    this.enEmiTipoDocIdent = enEmiTipoDocIdent;
  }

  public String getEnProTipoDocIdent() {
    return enProTipoDocIdent;
  }

  public void setEnProTipoDocIdent(String enProTipoDocIdent) {
    this.enProTipoDocIdent = enProTipoDocIdent;
  }

  public String getEnNroComprobante() {
    return enNroComprobante;
  }

  public void setEnNroComprobante(String enNroComprobante) {
    this.enNroComprobante = enNroComprobante;
  }
	
}
