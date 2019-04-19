package com.proyecto.pojos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Proyecto  implements Serializable{
	
	private static final long serialVersionUID = -1801136411676950181L;
	private Integer idProyecto;
	private Cliente cliente;
	private TipoTrabajo tipoTrabajo;
	private Usuario usuario;
	private LugarTrabajo lugarTrabajo;
	private String codigoProyecto;
	private TipoMoneda tipoMoneda;
	private Estado estado;
	private Date fechaInicPlan;
	private Date fechaFinPlan;
	private Date fechaInicReal;
	private Date fechaFinReal;
	private String nombreProyecto;
	private String descripcionProyecto;
	private BigDecimal totalCotizado;
	private Date fechaCreacion;
	private Date fechaModificacion;
	private String usuarioCreador;
	private String usuarioModificador;

	public Proyecto() {
		cliente=new Cliente();
		tipoTrabajo=new TipoTrabajo();
		usuario=new Usuario();
		lugarTrabajo=new LugarTrabajo();
		tipoMoneda=new TipoMoneda();
		estado=new Estado();
	}

	private Integer codigo;

	/**
	 * @return the codigo
	 */
	public Integer getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * @param cliente
	 *            the cliente to set
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return the tipoTrabajo
	 */
	public TipoTrabajo getTipoTrabajo() {
		return tipoTrabajo;
	}

	/**
	 * @param tipoTrabajo
	 *            the tipoTrabajo to set
	 */
	public void setTipoTrabajo(TipoTrabajo tipoTrabajo) {
		this.tipoTrabajo = tipoTrabajo;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the lugarTrabajo
	 */
	public LugarTrabajo getLugarTrabajo() {
		return lugarTrabajo;
	}

	/**
	 * @param lugarTrabajo
	 *            the lugarTrabajo to set
	 */
	public void setLugarTrabajo(LugarTrabajo lugarTrabajo) {
		this.lugarTrabajo = lugarTrabajo;
	}

	/**
	 * @return the codigoProyecto
	 */
	public String getCodigoProyecto() {
		return codigoProyecto;
	}

	/**
	 * @param codigoProyecto
	 *            the codigoProyecto to set
	 */
	public void setCodigoProyecto(String codigoProyecto) {
		this.codigoProyecto = codigoProyecto;
	}

	/**
	 * @return the tipoMoneda
	 */
	public TipoMoneda getTipoMoneda() {
		return tipoMoneda;
	}

	/**
	 * @param tipoMoneda
	 *            the tipoMoneda to set
	 */
	public void setTipoMoneda(TipoMoneda tipoMoneda) {
		this.tipoMoneda = tipoMoneda;
	}

	/**
	 * @return the estado
	 */
	public Estado getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	/**
	 * @return the fechaInicPlan
	 */
	public Date getFechaInicPlan() {
		return fechaInicPlan;
	}

	/**
	 * @param fechaInicPlan
	 *            the fechaInicPlan to set
	 */
	public void setFechaInicPlan(Date fechaInicPlan) {
		this.fechaInicPlan = fechaInicPlan;
	}

	/**
	 * @return the fechaFinPlan
	 */
	public Date getFechaFinPlan() {
		return fechaFinPlan;
	}

	/**
	 * @param fechaFinPlan
	 *            the fechaFinPlan to set
	 */
	public void setFechaFinPlan(Date fechaFinPlan) {
		this.fechaFinPlan = fechaFinPlan;
	}

	/**
	 * @return the fechaInicReal
	 */
	public Date getFechaInicReal() {
		return fechaInicReal;
	}

	/**
	 * @param fechaInicReal
	 *            the fechaInicReal to set
	 */
	public void setFechaInicReal(Date fechaInicReal) {
		this.fechaInicReal = fechaInicReal;
	}

	/**
	 * @return the fechaFinReal
	 */
	public Date getFechaFinReal() {
		return fechaFinReal;
	}

	/**
	 * @param fechaFinReal
	 *            the fechaFinReal to set
	 */
	public void setFechaFinReal(Date fechaFinReal) {
		this.fechaFinReal = fechaFinReal;
	}

	/**
	 * @return the nombreProyecto
	 */
	public String getNombreProyecto() {
		return nombreProyecto;
	}

	/**
	 * @param nombreProyecto
	 *            the nombreProyecto to set
	 */
	public void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}

	/**
	 * @return the descripcionProyecto
	 */
	public String getDescripcionProyecto() {
		return descripcionProyecto;
	}

	/**
	 * @param descripcionProyecto
	 *            the descripcionProyecto to set
	 */
	public void setDescripcionProyecto(String descripcionProyecto) {
		this.descripcionProyecto = descripcionProyecto;
	}

	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion
	 *            the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @return the fechaModificacion
	 */
	public Date getFechaModificacion() {
		return fechaModificacion;
	}

	/**
	 * @param fechaModificacion
	 *            the fechaModificacion to set
	 */
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}

	/**
	 * @return the usuarioCreador
	 */
	public String getUsuarioCreador() {
		return usuarioCreador;
	}

	/**
	 * @param usuarioCreador
	 *            the usuarioCreador to set
	 */
	public void setUsuarioCreador(String usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}

	/**
	 * @return the usuarioModificador
	 */
	public String getUsuarioModificador() {
		return usuarioModificador;
	}

	/**
	 * @param usuarioModificador
	 *            the usuarioModificador to set
	 */
	public void setUsuarioModificador(String usuarioModificador) {
		this.usuarioModificador = usuarioModificador;
	}

	/**
	 * @return the idProyecto
	 */
	public Integer getIdProyecto() {
		return idProyecto;
	}

	/**
	 * @param idProyecto the idProyecto to set
	 */
	public void setIdProyecto(Integer idProyecto) {
		this.idProyecto = idProyecto;
	}

	/**
	 * @return the totalCotizado
	 */
	public BigDecimal getTotalCotizado() {
		return totalCotizado;
	}

	/**
	 * @param totalCotizado the totalCotizado to set
	 */
	public void setTotalCotizado(BigDecimal totalCotizado) {
		this.totalCotizado = totalCotizado;
	}

}
