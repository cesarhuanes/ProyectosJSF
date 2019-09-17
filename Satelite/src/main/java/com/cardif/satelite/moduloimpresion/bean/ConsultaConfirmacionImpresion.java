package com.cardif.satelite.moduloimpresion.bean;

import java.util.Date;

public class ConsultaConfirmacionImpresion implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nroPolizaPe;
	private String placa;
	private Date fecIniVigencia;
	private Date fecFinVigencia;
	private String nroCertificado;
	private Long numLote;
	private Long idProducto;
	private String impresoCorrectamente;
	private boolean validated;
	private String validatedCSSStyle;
	private boolean disabledText; 
	private String tmpNroCertificado;
	
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
	
	public Date getFecIniVigencia()
	{
		return fecIniVigencia;
	}
	
	public void setFecIniVigencia(Date fecIniVigencia)
	{
		this.fecIniVigencia = fecIniVigencia;
	}
	
	public Date getFecFinVigencia()
	{
		return fecFinVigencia;
	}
	
	public void setFecFinVigencia(Date fecFinVigencia)
	{
		this.fecFinVigencia = fecFinVigencia;
	}
	
	public String getNroCertificado()
	{
		return nroCertificado;
	}
	
	public void setNroCertificado(String nroCertificado)
	{
		this.nroCertificado = nroCertificado;
	}
	
	public Long getNumLote()
	{
		return numLote;
	}
	
	public void setNumLote(Long numLote)
	{
		this.numLote = numLote;
	}
	
	public Long getIdProducto()
	{
		return idProducto;
	}
	
	public void setIdProducto(Long idProducto)
	{
		this.idProducto = idProducto;
	}
	
	public String getImpresoCorrectamente()
	{
		return impresoCorrectamente;
	}
	
	public void setImpresoCorrectamente(String impresoCorrectamente)
	{
		this.impresoCorrectamente = impresoCorrectamente;
	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	public String getValidatedCSSStyle() {
		return validatedCSSStyle;
	}

	public void setValidatedCSSStyle(String validatedCSSStyle) {
		this.validatedCSSStyle = validatedCSSStyle;
	}

	public boolean isDisabledText() {
		return disabledText;
	}

	public void setDisabledText(boolean disabledText) {
		this.disabledText = disabledText;
	}

	public String getTmpNroCertificado() {
		return tmpNroCertificado;
	}

	public void setTmpNroCertificado(String tmpNroCertificado) {
		this.tmpNroCertificado = tmpNroCertificado;
	}	
	
	
	
} //ConsultaConfirmacionImpresion
