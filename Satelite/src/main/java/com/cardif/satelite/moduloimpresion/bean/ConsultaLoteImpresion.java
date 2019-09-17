package com.cardif.satelite.moduloimpresion.bean;

import java.util.Date;

public class ConsultaLoteImpresion implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Long numLote;
	private String estado;
	private Date fecCreacion;
	private String usuario;
	private Boolean visibleP=false;
	private Boolean visibleI=false;
	private String visible;
	private String fechaCreacion;
	
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
	
	public String getUsuario()
	{
		return usuario;
	}
	
	public void setUsuario(String usuario)
	{
		this.usuario = usuario;
	}

	
	public void setVisibleP(Boolean visibleP) {
		this.visibleP=visibleP;
		//= this.estado.equals("PENDIENTE")?true:true;
		
	}
	
	public Boolean getVisibleP() {

		return visibleP;
	}

	public void setVisibleI(Boolean visibleI) {
		this.visibleI = visibleI;
		//this.estado.equals("IMPRESO")?true:false;
		
		
	}


	public Boolean getVisibleI() {		
		return visibleI;
	}

	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	
	


	
	
	
} //ConsultaLoteImpresion
