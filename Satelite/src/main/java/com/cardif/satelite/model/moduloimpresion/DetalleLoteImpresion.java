package com.cardif.satelite.model.moduloimpresion;

public class DetalleLoteImpresion implements java.io.Serializable
{
	private static final long serialVersionUID = 5688887346305004697L;
	
	private Long id;
	private Long loteImpresion;
	private Long detalleTramaDiaria;
	private String impresoCorrectamente;
	
	
	public Long getId()
	{
		return id;
	}
	
	public void setId(Long id)
	{
		this.id = id;
	}
	
	public Long getLoteImpresion()
	{
		return loteImpresion;
	}
	
	public void setLoteImpresion(Long loteImpresion)
	{
		this.loteImpresion = loteImpresion;
	}
	
	public Long getDetalleTramaDiaria()
	{
		return detalleTramaDiaria;
	}
	
	public void setDetalleTramaDiaria(Long detalleTramaDiaria)
	{
		this.detalleTramaDiaria = detalleTramaDiaria;
	}
	
	public String getImpresoCorrectamente()
	{
		return impresoCorrectamente;
	}
	
	public void setImpresoCorrectamente(String impresoCorrectamente)
	{
		this.impresoCorrectamente = impresoCorrectamente;
	}
	
} //DetalleLoteImpresion
