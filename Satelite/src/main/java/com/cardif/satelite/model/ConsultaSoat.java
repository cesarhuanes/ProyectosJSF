package com.cardif.satelite.model;

public class ConsultaSoat implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long codigoVenta;
	private Long cantCoberturas;
	private Long cantOtrasCoberturas;
	
	public Long getCodigoVenta() {
		return codigoVenta;
	}
	public void setCodigoVenta(Long codigoVenta) {
		this.codigoVenta = codigoVenta;
	}
	public Long getCantCoberturas() {
		return cantCoberturas;
	}
	public void setCantCoberturas(Long cantCoberturas) {
		this.cantCoberturas = cantCoberturas;
	}
	public Long getCantOtrasCoberturas() {
		return cantOtrasCoberturas;
	}
	public void setCantOtrasCoberturas(Long cantOtrasCoberturas) {
		this.cantOtrasCoberturas = cantOtrasCoberturas;
	}
}
