package com.cardif.satelite.model.moduloimpresion;

public class NumeroDocValorado implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Integer anio;
	private String numCertificado;
	private String tipoCertificado; 
	private Long producto;
	private Integer disponible;
	private String nroPolizaRel;
	private Integer anulado;
	
	private boolean deleted;
	
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public Integer getAnio()
	{
		return anio;
	}
	
	public void setAnio(Integer anio)
	{
		this.anio = anio;
	}
	
	public String getNumCertificado()
	{
		return numCertificado;
	}
	
	public void setNumCertificado(String numCertificado)
	{
		this.numCertificado = numCertificado;
	}
	
	public String getTipoCertificado()
	{
		return tipoCertificado;
	}
	
	public void setTipoCertificado(String tipoCertificado)
	{
		this.tipoCertificado = tipoCertificado;
	}
	
	public Long getProducto()
	{
		return producto;
	}
	
	public void setProducto(Long producto)
	{
		this.producto = producto;
	}
	
	public Integer getDisponible()
	{
		return disponible;
	}
	
	public void setDisponible(Integer disponible)
	{
		this.disponible = disponible;
	}
	
	public String getNroPolizaRel()
	{
		return nroPolizaRel;
	}
	
	public void setNroPolizaRel(String nroPolizaRel)
	{
		this.nroPolizaRel = nroPolizaRel;
	}
	
	public boolean isDeleted()
	{
		return deleted;
	}
	
	public void setDeleted(boolean deleted)
	{
		this.deleted = deleted;
	}
	
	public Integer getAnulado()
	{
		return anulado;
	}
	
	public void setAnulado(Integer anulado)
	{
		this.anulado = anulado;
	}
	
} //NumeroDocValorado
