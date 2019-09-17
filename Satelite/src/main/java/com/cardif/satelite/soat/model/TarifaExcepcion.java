package com.cardif.satelite.soat.model;

public class TarifaExcepcion implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Long id = new Long(0L);
	private String plan;
	private Integer cobertura;
	private Double valor;
	private String departamento;
	private String uso;
	private String categoria;
	private String modelo;
	private String socio;
	private Integer version;
	
	
	public TarifaExcepcion() {}
	
	public TarifaExcepcion(String plan, Integer cobertura, Double valor, String departamento, String uso, String categoria, String modelo, String socio, Integer version)
	{
		this.plan = plan;
		this.cobertura = cobertura;
		this.valor = valor;
		this.departamento = departamento;
		this.uso = uso;
		this.categoria = categoria;
		this.modelo = modelo;
		this.socio = socio;
		this.version = version;
	} //TarifaExcepcion
	
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public String getPlan()
	{
		return plan;
	}
	
	public void setPlan(String plan)
	{
		this.plan = plan;
	}
	
	public Integer getCobertura()
	{
		return cobertura;
	}
	
	public void setCobertura(Integer cobertura)
	{
		this.cobertura = cobertura;
	}
	
	public Double getValor()
	{
		return valor;
	}
	
	public void setValor(Double valor)
	{
		this.valor = valor;
	}
	
	public String getDepartamento()
	{
		return departamento;
	}
	
	public void setDepartamento(String departamento)
	{
		this.departamento = departamento;
	}
	
	public String getUso()
	{
		return uso;
	}
	
	public void setUso(String uso)
	{
		this.uso = uso;
	}
	
	public String getCategoria()
	{
		return categoria;
	}
	
	public void setCategoria(String categoria)
	{
		this.categoria = categoria;
	}
	
	public String getModelo()
	{
		return modelo;
	}
	
	public void setModelo(String modelo)
	{
		this.modelo = modelo;
	}
	
	public String getSocio()
	{
		return socio;
	}
	
	public void setSocio(String socio)
	{
		this.socio = socio;
	}
	
	public Integer getVersion()
	{
		return version;
	}
	
	public void setVersion(Integer version)
	{
		this.version = version;
	}
	
} // TarifaExcepcion
