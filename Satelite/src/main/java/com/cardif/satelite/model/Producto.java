package com.cardif.satelite.model;

import java.util.Date;

public class Producto implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long idSocio;
	private String nombreProducto;
	private String serieProducto;
	private Integer tramaDiaria;
	private Integer tramaMensual;
	private String tipoArchivo;
	private Integer modSuscripcion;
	private Integer modImpresion;
	private String separador;
	private Integer estado;
	private String canal;
	private Integer idcanal;
	private Date fechaCreacion;
	private String usuarioCreacion;
	private Date fechaModificacion;
	private String usuarioModificacion;
	
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
	
	public String getSerieProducto()
	{
		return serieProducto;
	}
	
	public void setSerieProducto(String serieProducto)
	{
		this.serieProducto = serieProducto;
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

	public String getCanal() {
		return canal;
	}

	public void setCanal(String canal) {
		this.canal = canal;
	}

	/**
	 * @return the idcanal
	 */
	public Integer getIdcanal() {
		return idcanal;
	}

	/**
	 * @param idcanal the idcanal to set
	 */
	public void setIdcanal(Integer idcanal) {
		this.idcanal = idcanal;
	}

	/**
	 * @return the separador
	 */
	public String getSeparador() {
		return separador;
	}

	/**
	 * @param separador the separador to set
	 */
	public void setSeparador(String separador) {
		this.separador = separador;
	}

	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @return the usuarioCreacion
	 */
	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	/**
	 * @param usuarioCreacion the usuarioCreacion to set
	 */
	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	/**
	 * @return the fechaModificacion
	 */
	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	/**
	 * @param fechaModificacion the fechaModificacion to set
	 */
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	/**
	 * @return the usuarioModificacion
	 */
	public String getUsuarioModificacion() {
		return usuarioModificacion;
	}

	/**
	 * @param usuarioModificacion the usuarioModificacion to set
	 */
	public void setUsuarioModificacion(String usuarioModificacion) {
		this.usuarioModificacion = usuarioModificacion;
	}
	
	
	
} //Producto
