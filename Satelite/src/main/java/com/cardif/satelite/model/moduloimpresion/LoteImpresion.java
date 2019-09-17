package com.cardif.satelite.model.moduloimpresion;

import java.util.Date;

public class LoteImpresion implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Long numLote;
	private String estado;
	private Date fecCreacion;
	private String pdfCodificadoLote;
	private Long producto;
	private String usuario;
	private String visible;

	
	
	public Long getNumLote()
	{
		return numLote;
	}
	
	public void setNumLote(Long numLote)
	{
		this.numLote = numLote;
	}
	
	public String getEstado()
	{
		return estado;
	}
	
	public void setEstado(String estado)
	{
		this.estado = estado;
	}
	
	public Date getFecCreacion()
	{
		return fecCreacion;
	}
	
	public void setFecCreacion(Date fecCreacion)
	{
		this.fecCreacion = fecCreacion;
	}
	
	public String getPdfCodificadoLote()
	{
		return pdfCodificadoLote;
	}
	
	public void setPdfCodificadoLote(String pdfCodificadoLote)
	{
		this.pdfCodificadoLote = pdfCodificadoLote;
	}
	
	public Long getProducto()
	{
		return producto;
	}
	
	public void setProducto(Long producto)
	{
		this.producto = producto;
	}
	
	public String getUsuario()
	{
		return usuario;
	}
	
	public void setUsuario(String usuario)
	{
		this.usuario = usuario;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	
	
	
	
} //LoteImpresion
