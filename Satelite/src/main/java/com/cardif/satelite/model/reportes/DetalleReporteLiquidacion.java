package com.cardif.satelite.model.reportes;

public class DetalleReporteLiquidacion {
	private Long id;
	private Long idDetalleTramaDiaria;
	private Double derechoEmision;
	private Double comision;
	private Double fondoCompensacion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdDetalleTramaDiaria() {
		return idDetalleTramaDiaria;
	}

	public void setIdDetalleTramaDiaria(Long idDetalleTramaDiaria) {
		this.idDetalleTramaDiaria = idDetalleTramaDiaria;
	}

	public Double getDerechoEmision() {
		return derechoEmision;
	}

	public void setDerechoEmision(Double derechoEmision) {
		this.derechoEmision = derechoEmision;
	}

	public Double getComision() {
		return comision;
	}

	public void setComision(Double comision) {
		this.comision = comision;
	}

	public Double getFondoCompensacion() {
		return fondoCompensacion;
	}

	public void setFondoCompensacion(Double fondoCompensacion) {
		this.fondoCompensacion = fondoCompensacion;
	}

} // DetalleReporteLiquidaciones
