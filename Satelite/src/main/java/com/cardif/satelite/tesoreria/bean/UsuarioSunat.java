package com.cardif.satelite.tesoreria.bean;

public class UsuarioSunat
{
	private String ruc;
	private String razonSocial;
	private String nombreComercial;
	private String direccionFiscal;
	private String usuarioSOL;
	private String claveSOL;
	
	
	public UsuarioSunat() {}
	
	public UsuarioSunat(String ruc, String razonSocial, String nombreComercial, String direccionFiscal, String usuarioSOL, String claveSOL)
	{
		this.ruc = ruc;
		this.razonSocial = razonSocial;
		this.nombreComercial = nombreComercial;
		this.direccionFiscal = direccionFiscal;
		this.usuarioSOL = usuarioSOL;
		this.claveSOL = claveSOL;
	} //UsuarioSunat
	
	
	public String getRUC()
	{
		return ruc;
	} //getRUC
	
	public void setRUC(String ruc)
	{
		this.ruc = ruc;
	} //setRUC
	
	public String getRazonSocial()
	{
		return razonSocial;
	} //getRazonSocial
	
	public void setRazonSocial(String razonSocial)
	{
		this.razonSocial = razonSocial;
	} //setRazonSocial
	
	public String getNombreComercial()
	{
		return nombreComercial;
	} //getNombreComercial
	
	public void setNombreComercial(String nombreComercial)
	{
		this.nombreComercial = nombreComercial;
	} //setNombreComercial
	
	public String getDireccionFiscal()
	{
		return direccionFiscal;
	} //getDireccionFiscal
	
	public void setDireccionFiscal(String direccionFiscal)
	{
		this.direccionFiscal = direccionFiscal;
	} //setDireccionFiscal
	
	public String getUsuarioSOL()
	{
		return this.usuarioSOL;
	} //getUsuario
	
	public void setUsuarioSOL(String usuarioSOL)
	{
		this.usuarioSOL = usuarioSOL;
	} //setUsuarioSOL
	
	public String getClaveSOL()
	{
		return this.claveSOL;
	} //getClaveSOL
	
	public void setClaveSOL(String claveSOL)
	{
		this.claveSOL = claveSOL;
	} //setClaveSOL
	
} //UsuarioSunat
