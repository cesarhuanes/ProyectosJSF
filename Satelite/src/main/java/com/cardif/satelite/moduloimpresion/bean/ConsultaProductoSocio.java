package com.cardif.satelite.moduloimpresion.bean;

public class ConsultaProductoSocio implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long idSocio;
	private String nombreProducto;
	private String nombreSocio;
	private Integer tramaDiaria;
	private Integer tramaMensual;
	private String tipoArchivo;
	private Integer modSuscripcion;
	private Integer modImpresion;
	private Integer estado;
	private String indicadorRegVenta;
	
	public String getIndicadorRegVenta() {
		return indicadorRegVenta;
	}

	public void setIndicadorRegVenta(String indicadorRegVenta) {
		this.indicadorRegVenta = indicadorRegVenta;
	}

	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public Long getIdSocio()
	{
		return idSocio;
	}
	
	public void setIdSocio(Long idSocio)
	{
		this.idSocio = idSocio;
	}
	
	public String getNombreProducto()
	{
		return nombreProducto;
	}
	
	public void setNombreProducto(String nombreProducto)
	{
		this.nombreProducto = nombreProducto;
	}
	
	public String getNombreSocio()
	{
		return nombreSocio;
	}
	
	public void setNombreSocio(String nombreSocio)
	{
		this.nombreSocio = nombreSocio;
	}
	
	public Integer getTramaDiaria()
	{
		return tramaDiaria;
	}
	
	public void setTramaDiaria(Integer tramaDiaria)
	{
		this.tramaDiaria = tramaDiaria;
	}
	
	public Integer getTramaMensual()
	{
		return tramaMensual;
	}
	
	public void setTramaMensual(Integer tramaMensual)
	{
		this.tramaMensual = tramaMensual;
	}
	
	public String getTipoArchivo()
	{
		return tipoArchivo;
	}
	
	public void setTipoArchivo(String tipoArchivo)
	{
		this.tipoArchivo = tipoArchivo;
	}
	
	public Integer getModSuscripcion()
	{
		return modSuscripcion;
	}
	
	public void setModSuscripcion(Integer modSuscripcion)
	{
		this.modSuscripcion = modSuscripcion;
	}
	
	public Integer getModImpresion()
	{
		return modImpresion;
	}
	
	public void setModImpresion(Integer modImpresion)
	{
		this.modImpresion = modImpresion;
	}
	
	public Integer getEstado()
	{
		return estado;
	}
	
	public void setEstado(Integer estado)
	{
		this.estado = estado;
	}
	
} //ConsultaProductoSocio
