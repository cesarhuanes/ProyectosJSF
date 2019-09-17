package com.cardif.satelite.suscripcion.bean;

import java.math.BigDecimal;
import java.util.Date;

public class RepPolizasSOATBean implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Long pk;
	private String nroPolizaPe;
	private Long idPoliza;  
	private String placa;
	private String marca;
	private String modelo;
	private String tipVehi;
	private String usoVehi;
	private Long nroAsientos;
	private BigDecimal importeCobroDsc;
	private String contratante;
	private String numDoc;
	private String numCertificado;
	private Date fecVenta;
	private String estado;
	
	private Long id;
	
	
	public Long getPk()
	{
		return pk;
	}
	
	public void setPk(Long pk)
	{
		this.pk = pk;
	}
	
	public String getNroPolizaPe()
	{
		return nroPolizaPe;
	}
	
	public void setNroPolizaPe(String nroPolizaPe)
	{
		this.nroPolizaPe = nroPolizaPe;
	}
	
	public String getPlaca()
	{
		return placa;
	}
	
	public void setPlaca(String placa)
	{
		this.placa = placa;
	}
	
	public String getMarca()
	{
		return marca;
	}
	
	public void setMarca(String marca)
	{
		this.marca = marca;
	}
	
	public String getModelo()
	{
		return modelo;
	}
	
	public void setModelo(String modelo)
	{
		this.modelo = modelo;
	}
	
	public String getTipVehi()
	{
		return tipVehi;
	}
	
	public void setTipVehi(String tipVehi)
	{
		this.tipVehi = tipVehi;
	}
	
	public String getUsoVehi()
	{
		return usoVehi;
	}
	
	public void setUsoVehi(String usoVehi)
	{
		this.usoVehi = usoVehi;
	}
	
	public Long getNroAsientos()
	{
		return nroAsientos;
	}
	
	public void setNroAsientos(Long nroAsientos)
	{
		this.nroAsientos = nroAsientos;
	}
	
	public BigDecimal getImporteCobroDsc()
	{
		return importeCobroDsc;
	}
	
	public void setImporteCobroDsc(BigDecimal importeCobroDsc)
	{
		this.importeCobroDsc = importeCobroDsc;
	}
	
	public String getContratante()
	{
		return contratante;
	}

	public void setContratante(String contratante)
	{
		this.contratante = contratante;
	}
	
	public String getNumDoc()
	{
		return numDoc;
	}
	
	public void setNumDoc(String numDoc)
	{
		this.numDoc = numDoc;
	}
	
	public String getNumCertificado()
	{
		return numCertificado;
	}
	
	public void setNumCertificado(String numCertificado)
	{
		this.numCertificado = numCertificado;
	}
	
	public Date getFecVenta()
	{
		return fecVenta;
	}
	
	public void setFecVenta(Date fecVenta)
	{
		this.fecVenta = fecVenta;
	}

	public Long getIdPoliza() {
		return idPoliza;
	}

	public void setIdPoliza(Long idPoliza) {
		this.idPoliza = idPoliza;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	
	
} //RepPolizasSOATBean
