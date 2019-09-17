package com.cardif.satelite.moduloimpresion.bean;

import java.util.Date;

public class ConsultaArmarLote implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nroPolizaPe;
	private String placa;
	private Date fecVenta;
	private Date fecDespacho;
	private Long codVehiculo;
	private String usoVehiculo;
	private String departamentoDesp;
	private String provinciaDesp;
	private String distritoDesp;
	private String franjaHorariaDesp;
	private Long idProducto;
	private String nombreProducto;
	private String estadoImpresion;
	
	private boolean selected;
	private boolean deleted;
	private String deletedCSSStyle;
	private boolean validated;
	private String validatedCSSStyle;
	private String numCertificado;
	
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
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
	
	public Date getFecVenta()
	{
		return fecVenta;
	}
	
	public void setFecVenta(Date fecVenta)
	{
		this.fecVenta = fecVenta;
	}
	
	public Date getFecDespacho()
	{
		return fecDespacho;
	}
	
	public void setFecDespacho(Date fecDespacho)
	{
		this.fecDespacho = fecDespacho;
	}
	
	public Long getCodVehiculo()
	{
		return codVehiculo;
	}
	
	public void setCodVehiculo(Long codVehiculo)
	{
		this.codVehiculo = codVehiculo;
	}
	
	public String getUsoVehiculo()
	{
		return usoVehiculo;
	}
	
	public void setUsoVehiculo(String usoVehiculo)
	{
		this.usoVehiculo = usoVehiculo;
	}
	
	public String getDepartamentoDesp()
	{
		return departamentoDesp;
	}
	
	public void setDepartamentoDesp(String departamentoDesp)
	{
		this.departamentoDesp = departamentoDesp;
	}
	
	public String getProvinciaDesp()
	{
		return provinciaDesp;
	}
	
	public void setProvinciaDesp(String provinciaDesp)
	{
		this.provinciaDesp = provinciaDesp;
	}
	
	public String getDistritoDesp()
	{
		return distritoDesp;
	}
	
	public void setDistritoDesp(String distritoDesp)
	{
		this.distritoDesp = distritoDesp;
	}
	
	public String getFranjaHorariaDesp()
	{
		return franjaHorariaDesp;
	}
	
	public void setFranjaHorariaDesp(String franjaHorariaDesp)
	{
		this.franjaHorariaDesp = franjaHorariaDesp;
	}
	
	public Long getIdProducto()
	{
		return idProducto;
	}
	
	public void setIdProducto(Long idProducto)
	{
		this.idProducto = idProducto;
	}
	
	public String getNombreProducto()
	{
		return nombreProducto;
	}
	
	public void setNombreProducto(String nombreProducto)
	{
		this.nombreProducto = nombreProducto;
	}
	
	public String getEstadoImpresion()
	{
		return estadoImpresion;
	}
	
	public void setEstadoImpresion(String estadoImpresion)
	{
		this.estadoImpresion = estadoImpresion;
	}
	
	public boolean isSelected()
	{
		return selected;
	}
	
	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}
	
	public boolean isDeleted()
	{
		return deleted;
	}
	
	public void setDeleted(boolean deleted)
	{
		this.deleted = deleted;
	}
	
	public String getDeletedCSSStyle()
	{
		return deletedCSSStyle;
	}
	
	public void setDeletedCSSStyle(String deletedCSSStyle)
	{
		this.deletedCSSStyle = deletedCSSStyle;
	}
	
	public boolean isValidated()
	{
		return validated;
	}
	
	public void setValidated(boolean validated)
	{
		this.validated = validated;
	}
	
	public String getValidatedCSSStyle()
	{
		return validatedCSSStyle;
	}
	
	public void setValidatedCSSStyle(String validatedCSSStyle)
	{
		this.validatedCSSStyle = validatedCSSStyle;
	}
	
	public String getNumCertificado()
	{
		return numCertificado;
	}
	
	public void setNumCertificado(String numCertificado)
	{
		this.numCertificado = numCertificado;
	}
	
} // ConsultaArmarLote
