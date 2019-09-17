package com.cardif.satelite.reportesbs.bean;


public class ListaCuentasDataSun {
	
	private String canal="";
	private String codigoCuenta ="";
	private String debitoCredito = "";
	private Integer Numerodiario;
	private String fechaTransaccion = "";
	private String glosa= "";
	private double tipoCambio;
	private double importeTransaccion; 
	private double importeSoles; 
	private double importeBase;
	private String liniaDiario;
	private String moneda = "";
	private String nombreProveedor ="";
	private String nombreProveedorCompleto = "";
	private String nroSiniestro = "";
	private String polizaCliente = "";
	private String ruc = "";
	private String socioProducto = "";
	private String tipoDiario = "";
	private String journalType = "";
	
	public String getCanal() {
		return canal;
	}
	public void setCanal(String canal) {
		this.canal = canal;
	}
	public String getCodigoCuenta() {
		return codigoCuenta;
	}
	public void setCodigoCuenta(String codigoCuenta) {
		this.codigoCuenta = codigoCuenta;
	}
	public String getDebitoCredito() {
		return debitoCredito;
	}
	public void setDebitoCredito(String debitoCredito) {
		this.debitoCredito = debitoCredito;
	}
	public Integer getNumerodiario() {
		return Numerodiario;
	}
	public void setNumerodiario(Integer numerodiario) {
		Numerodiario = numerodiario;
	}
	public String getFechaTransaccion() {
		return fechaTransaccion;
	}
	public void setFechaTransaccion(String fechaTransaccion) {
		this.fechaTransaccion = fechaTransaccion;
	}
	public String getGlosa() {
		return glosa;
	}
	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}


	public String getLiniaDiario() {
		return liniaDiario;
	}
	public void setLiniaDiario(String liniaDiario) {
		this.liniaDiario = liniaDiario;
	}
	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	public String getNombreProveedor() {
		return nombreProveedor;
	}
	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}
	public String getNombreProveedorCompleto() {
		return nombreProveedorCompleto;
	}
	public void setNombreProveedorCompleto(String nombreProveedorCompleto) {
		this.nombreProveedorCompleto = nombreProveedorCompleto;
	}
	public String getNroSiniestro() {
		return nroSiniestro;
	}
	public void setNroSiniestro(String nroSiniestro) {
		this.nroSiniestro = nroSiniestro;
	}
	public String getPolizaCliente() {
		return polizaCliente;
	}
	public void setPolizaCliente(String polizaCliente) {
		this.polizaCliente = polizaCliente;
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getSocioProducto() {
		return socioProducto;
	}
	public void setSocioProducto(String socioProducto) {
		this.socioProducto = socioProducto;
	}
	public double getImporteTransaccion() {
		return importeTransaccion;
	}
	public void setImporteTransaccion(double importeTransaccion) {
		this.importeTransaccion = importeTransaccion;
	}
	public double getImporteSoles() {
		return importeSoles;
	}
	public void setImporteSoles(double importeSoles) {
		this.importeSoles = importeSoles;
	}
	public double getImporteBase() {
		return importeBase;
	}
	public void setImporteBase(double importeBase) {
		this.importeBase = importeBase;
	}
	public String getTipoDiario() {
		return tipoDiario;
	}
	public void setTipoDiario(String tipoDiario) {
		this.tipoDiario = tipoDiario;
	}
	public String getJournalType() {
		return journalType;
	}
	public void setJournalType(String journalType) {
		this.journalType = journalType;
	}
	public double getTipoCambio() {
		return tipoCambio;
	}
	public void setTipoCambio(double tipoCambio) {
		this.tipoCambio = tipoCambio;
	}


}
