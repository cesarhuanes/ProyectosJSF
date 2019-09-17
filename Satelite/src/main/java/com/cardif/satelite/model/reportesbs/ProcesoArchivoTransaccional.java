package com.cardif.satelite.model.reportesbs;

import java.math.BigDecimal;
import java.util.Date;

public class ProcesoArchivoTransaccional {
	private BigDecimal codProcesoArchivo;

	private String nomArchivoOriginal;

	private String nomArchivoProcesado;

	private int numRegistros;

	private BigDecimal sumImportePrimaPagar;

	private BigDecimal sumImporteSiniestroCobrar;

	private String estado;

	private String codEstadoSolicitud;

	private String usuProceso;

	private Date fecProceso;

	private String usuCreacion;

	private Date fecCreacion;

	private String usuModificacion;

	private Date fecModificacion;

	private BigDecimal sumImportePrimaCobrar;

	private BigDecimal sumImporteSiniestroPagar;

	public BigDecimal getCodProcesoArchivo() {
		return codProcesoArchivo;
	}

	public void setCodProcesoArchivo(BigDecimal codProcesoArchivo) {
		this.codProcesoArchivo = codProcesoArchivo;
	}

	public String getNomArchivoOriginal() {
		return nomArchivoOriginal;
	}

	public void setNomArchivoOriginal(String nomArchivoOriginal) {
		this.nomArchivoOriginal = nomArchivoOriginal;
	}

	public String getNomArchivoProcesado() {
		return nomArchivoProcesado;
	}

	public void setNomArchivoProcesado(String nomArchivoProcesado) {
		this.nomArchivoProcesado = nomArchivoProcesado;
	}

	public BigDecimal getSumImportePrimaPagar() {
		return sumImportePrimaPagar;
	}

	public void setSumImportePrimaPagar(BigDecimal sumImportePrimaPagar) {
		this.sumImportePrimaPagar = sumImportePrimaPagar;
	}

	public BigDecimal getSumImporteSiniestroCobrar() {
		return sumImporteSiniestroCobrar;
	}

	public void setSumImporteSiniestroCobrar(
			BigDecimal sumImporteSiniestroCobrar) {
		this.sumImporteSiniestroCobrar = sumImporteSiniestroCobrar;
	}

	public String getUsuProceso() {
		return usuProceso;
	}

	public void setUsuProceso(String usuProceso) {
		this.usuProceso = usuProceso;
	}

	public Date getFecProceso() {
		return fecProceso;
	}

	public void setFecProceso(Date fecProceso) {
		this.fecProceso = fecProceso;
	}

	public String getUsuCreacion() {
		return usuCreacion;
	}

	public void setUsuCreacion(String usuCreacion) {
		this.usuCreacion = usuCreacion;
	}

	public Date getFecCreacion() {
		return fecCreacion;
	}

	public void setFecCreacion(Date fecCreacion) {
		this.fecCreacion = fecCreacion;
	}

	public String getUsuModificacion() {
		return usuModificacion;
	}

	public void setUsuModificacion(String usuModificacion) {
		this.usuModificacion = usuModificacion;
	}

	public Date getFecModificacion() {
		return fecModificacion;
	}

	public void setFecModificacion(Date fecModificacion) {
		this.fecModificacion = fecModificacion;
	}

	public BigDecimal getSumImportePrimaCobrar() {
		return sumImportePrimaCobrar;
	}

	public void setSumImportePrimaCobrar(BigDecimal sumImportePrimaCobrar) {
		this.sumImportePrimaCobrar = sumImportePrimaCobrar;
	}

	public BigDecimal getSumImporteSiniestroPagar() {
		return sumImporteSiniestroPagar;
	}

	public void setSumImporteSiniestroPagar(BigDecimal sumImporteSiniestroPagar) {
		this.sumImporteSiniestroPagar = sumImporteSiniestroPagar;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCodEstadoSolicitud() {
		return codEstadoSolicitud;
	}

	public void setCodEstadoSolicitud(String codEstadoSolicitud) {
		this.codEstadoSolicitud = codEstadoSolicitud;
	}

	public int getNumRegistros() {
		return numRegistros;
	}

	public void setNumRegistros(int numRegistros) {
		this.numRegistros = numRegistros;
	}

}
