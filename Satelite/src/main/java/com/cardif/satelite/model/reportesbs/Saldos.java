package com.cardif.satelite.model.reportesbs;


public class Saldos {
	
    private Long codSaldos;

    private String anio;

    private Double primaPagarReaCedido;

    private Double primaCobrarReaAceptado;

    private Double siniestroPagarReaCedido;

    private Double siniestroCobrarReaAceptado;

    private Double otrasCobrarReaCedidos;

    private Double otrasCobrarReaAceptado;

    private Double descuentosComisiones;

    private String tipoSeguro;

    private String codEmpresa;

    private String tipoReaseguro;

    private String modeloMoneda;

    private String moneda;


    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getTipoSeguro() {
        return tipoSeguro;
    }

    public void setTipoSeguro(String tipoSeguro) {
        this.tipoSeguro = tipoSeguro;
    }

    public String getCodEmpresa() {
        return codEmpresa;
    }

    public void setCodEmpresa(String codEmpresa) {
        this.codEmpresa = codEmpresa;
    }

    public String getTipoReaseguro() {
        return tipoReaseguro;
    }

    public void setTipoReaseguro(String tipoReaseguro) {
        this.tipoReaseguro = tipoReaseguro;
    }

    public String getModeloMoneda() {
        return modeloMoneda;
    }

    public void setModeloMoneda(String modeloMoneda) {
        this.modeloMoneda = modeloMoneda;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }



	public Long getCodSaldos() {
		return codSaldos;
	}

	public void setCodSaldos(Long codSaldos) {
		this.codSaldos = codSaldos;
	}

	public Double getPrimaPagarReaCedido() {
		return primaPagarReaCedido;
	}

	public void setPrimaPagarReaCedido(Double primaPagarReaCedido) {
		this.primaPagarReaCedido = primaPagarReaCedido;
	}

	public Double getPrimaCobrarReaAceptado() {
		return primaCobrarReaAceptado;
	}

	public void setPrimaCobrarReaAceptado(Double primaCobrarReaAceptado) {
		this.primaCobrarReaAceptado = primaCobrarReaAceptado;
	}

	public Double getSiniestroPagarReaCedido() {
		return siniestroPagarReaCedido;
	}

	public void setSiniestroPagarReaCedido(Double siniestroPagarReaCedido) {
		this.siniestroPagarReaCedido = siniestroPagarReaCedido;
	}

	public Double getSiniestroCobrarReaAceptado() {
		return siniestroCobrarReaAceptado;
	}

	public void setSiniestroCobrarReaAceptado(Double siniestroCobrarReaAceptado) {
		this.siniestroCobrarReaAceptado = siniestroCobrarReaAceptado;
	}

	public Double getOtrasCobrarReaCedidos() {
		return otrasCobrarReaCedidos;
	}

	public void setOtrasCobrarReaCedidos(Double otrasCobrarReaCedidos) {
		this.otrasCobrarReaCedidos = otrasCobrarReaCedidos;
	}

	public Double getOtrasCobrarReaAceptado() {
		return otrasCobrarReaAceptado;
	}

	public void setOtrasCobrarReaAceptado(Double otrasCobrarReaAceptado) {
		this.otrasCobrarReaAceptado = otrasCobrarReaAceptado;
	}

	public Double getDescuentosComisiones() {
		return descuentosComisiones;
	}

	public void setDescuentosComisiones(Double descuentosComisiones) {
		this.descuentosComisiones = descuentosComisiones;
	}
}