package com.cardif.satelite.model;

import java.io.Serializable;
import java.util.Date;

public class Layout implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long pk;
	private Long idcanal;
	private String columnaExcel;
	private String columnaTabla;
	private Integer estado;
	private String tipodata;
	private int obligatorio;
	private String obligatorio_desc;
	private String label;
	private String usuarioCreacion;
	private String usuarioModifica;
	private Date fechaCreacion;
	private Date fechaModificacion;
	private int posicion;
	private String descripcion;
	private String formato;
	private Long secuencia;
	


	public Long getSecuencia() {
		return secuencia;
	}


	






	public String getObligatorio_desc() {
		return obligatorio_desc;
	}









	public void setObligatorio_desc(String obligatorio_desc) {
		this.obligatorio_desc = obligatorio_desc;
	}









	public void setSecuencia(Long secuencia) {
		this.secuencia = secuencia;
	}







	public Layout(){
		
	}
	

	




	public Layout(Long idcanal, String columnaExcel, String columnaTabla,
			String label, String usuarioModifica, Date fechaModificacion) {
		super();
		this.idcanal = idcanal;
		this.columnaExcel = columnaExcel;
		this.columnaTabla = columnaTabla;
		this.label = label;
		this.usuarioModifica = usuarioModifica;
		this.fechaModificacion = fechaModificacion;
	}

	

	public Layout(Long idcanal2, String columnaExcel2, String columnaTabla2,String label,String descripcion) {
		super();
		this.idcanal = idcanal2;
		this.columnaExcel = columnaExcel2;
		this.columnaTabla = columnaTabla2;
		this.descripcion = descripcion;
		this.label = label;
				
	}







	/**
	 * @return the pk
	 */
	public Long getPk() {
		return pk;
	}
	/**
	 * @param pk the pk to set
	 */
	public void setPk(Long pk) {
		this.pk = pk;
	}
	/**
	 * @return the idcanal
	 */
	public Long getIdcanal() {
		return idcanal;
	}
	/**
	 * @param idcanal the idcanal to set
	 */
	public void setIdcanal(Long idcanal) {
		this.idcanal = idcanal;
	}
	/**
	 * @return the columnaExcel
	 */
	public String getColumnaExcel() {
		return columnaExcel;
	}
	/**
	 * @param columnaExcel the columnaExcel to set
	 */
	public void setColumnaExcel(String columnaExcel) {
		this.columnaExcel = columnaExcel;
	}
	/**
	 * @return the columnaTabla
	 */
	public String getColumnaTabla() {
		return columnaTabla;
	}
	/**
	 * @param columnaTabla the columnaTabla to set
	 */
	public void setColumnaTabla(String columnaTabla) {
		this.columnaTabla = columnaTabla;
	}
	/**
	 * @return the estado
	 */
	public Integer getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	/**
	 * @return the tipodata
	 */
	public String getTipodata() {
		return tipodata;
	}
	/**
	 * @param tipodata the tipodata to set
	 */
	public void setTipodata(String tipodata) {
		this.tipodata = tipodata;
	}
	/**
	 * @return the obligatorio
	 */
	public int getObligatorio() {
		return obligatorio;
	}
	/**
	 * @param obligatorio the obligatorio to set
	 */
	public void setObligatorio(int obligatorio) {
		this.obligatorio = obligatorio;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
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
	 * @return the usuarioModifica
	 */
	public String getUsuarioModifica() {
		return usuarioModifica;
	}

	/**
	 * @param usuarioModifica the usuarioModifica to set
	 */
	public void setUsuarioModifica(String usuarioModifica) {
		this.usuarioModifica = usuarioModifica;
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
	 * @return the posicion
	 */
	public int getPosicion() {
		return posicion;
	}

	/**
	 * @param posicion the posicion to set
	 */
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}







	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}







	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	
	public String getFormato() {
		return formato;
	}







	public void setFormato(String formato) {
		this.formato = formato;
	}
}
