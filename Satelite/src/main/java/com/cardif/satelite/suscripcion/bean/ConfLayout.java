package com.cardif.satelite.suscripcion.bean;

public class ConfLayout {

	private String id;
	private String labelExcel;
	private String valueExcel;
	private String tipoDataExcel;
	private String labelTabla;
	private String valueTabla;
	private String tipoDataTabla;
	private String usuario;
	private String fecha;
	private String Tipodata;
	
	
	public ConfLayout(){
		
	}	

	public ConfLayout(String id, String labelExcel, String valueExcel,
			String labelTabla, String valueTabla, String usuario, String fecha,
			String tipoDataExcel, String tipoDataTabla ) {
		this.id = id;
		this.labelExcel = labelExcel;
		this.valueExcel = valueExcel;
		this.labelTabla = labelTabla;
		this.valueTabla = valueTabla;
		this.usuario = usuario;
		this.fecha = fecha;
		this.tipoDataTabla = tipoDataTabla;
		this.tipoDataExcel = tipoDataExcel;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabelExcel() {
		return labelExcel;
	}

	public void setLabelExcel(String labelExcel) {
		this.labelExcel = labelExcel;
	}

	public String getValueExcel() {
		return valueExcel;
	}

	public void setValueExcel(String valueExcel) {
		this.valueExcel = valueExcel;
	}

	public String getLabelTabla() {
		return labelTabla;
	}

	public void setLabelTabla(String labelTabla) {
		this.labelTabla = labelTabla;
	}

	public String getValueTabla() {
		return valueTabla;
	}

	public void setValueTabla(String valueTabla) {
		this.valueTabla = valueTabla;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getTipodata() {
		return Tipodata;
	}

	public void setTipodata(String tipodata) {
		Tipodata = tipodata;
	}

	public String getTipoDataExcel() {
		return tipoDataExcel;
	}

	public void setTipoDataExcel(String tipoDataExcel) {
		this.tipoDataExcel = tipoDataExcel;
	}

	public String getTipoDataTabla() {
		return tipoDataTabla;
	}

	public void setTipoDataTabla(String tipoDataTabla) {
		this.tipoDataTabla = tipoDataTabla;
	}
	
}
