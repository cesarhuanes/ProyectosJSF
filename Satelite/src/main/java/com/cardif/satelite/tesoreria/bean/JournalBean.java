package com.cardif.satelite.tesoreria.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class JournalBean implements Serializable {
  private static final long serialVersionUID = 1L;
  private String codigoCuenta;
  private String nombreProveedor;
  private String nombreProveedorCompleto;
  private String fechaTransaccion;
  private String referenciaTransaccion;
  private String moneda;
  private float importeBase;
  private float importeTransaccion;
  private String impPropinas = "0.0";
  private String FlagPago;
  private boolean bFlagPago;
  private float impSoles;
  private float impEuros;
  private String glosa;
  private short periodo;
  private String ruc;
  private String centroCosto;
  private String socioProducto;
  private String canal;
  private String proveedorEmpleado;
  private String rucDniCliente;
  private String polizaCliente;
  private String Inversiones;
  private String nroSiniestro;
  private String comprobanteSunat;
  private String tipoMedioPago;
  private int diario;
  private String lineaDiario;
  private String debitoCredito;
  private String tipoDiario;
  private String referenciaVinculada2;
  private String descripcionGeneral1;
  private String descripcionGeneral2;
  private String descripcionGeneral3;

  //Reportes
  private String journalType;
  
  // Pago Detracciones
  String numeroConstancia;
  String NroCheque;
  String RefCtaBancoProvision;
  String RefCtaBancoComision;

  // Diferencias
  BigDecimal difEuros;
  BigDecimal difSoles;

  /**
   * @return the codigoCuenta
   */
  public String getCodigoCuenta() {
    return codigoCuenta;
  }

  /**
   * @param codigoCuenta
   *          the codigoCuenta to set
   */
  public void setCodigoCuenta(String codigoCuenta) {
    this.codigoCuenta = codigoCuenta;
  }

  /**
   * @return the nombreProveedor
   */
  public String getNombreProveedor() {
    return nombreProveedor;
  }

  /**
   * @param nombreProveedor
   *          the nombreProveedor to set
   */
  public void setNombreProveedor(String nombreProveedor) {
    this.nombreProveedor = nombreProveedor;
  }

  /**
   * @return the nombreProveedorCompleto
   */
  public String getNombreProveedorCompleto() {
    return nombreProveedorCompleto;
  }

  /**
   * @param nombreProveedor
   *          the nombreProveedorCompleto to set
   */
  public void setNombreProveedorCompleto(String nombreProveedorCompleto) {
    this.nombreProveedorCompleto = nombreProveedorCompleto;
  }

  /**
   * @return the fechaTransaccion
   */
  public String getFechaTransaccion() {
    return fechaTransaccion;
  }

  /**
   * @param fechaTransaccion
   *          the fechaTransaccion to set
   */
  public void setFechaTransaccion(String fechaTransaccion) {
    this.fechaTransaccion = fechaTransaccion;
  }

  /**
   * @return the referencia
   */
  public String getReferencia() {
    return referenciaTransaccion;
  }

  /**
   * @param referencia
   *          the referencia to set
   */
  public void setReferencia(String referencia) {
    this.referenciaTransaccion = referencia;
  }

  /**
   * @return the moneda
   */
  public String getMoneda() {
    return moneda;
  }

  /**
   * @param moneda
   *          the moneda to set
   */
  public void setMoneda(String moneda) {
    this.moneda = moneda;
  }

  /**
   * @return the impTransaccion
   */
  public float getImpTransaccionAmount() {
    return importeTransaccion;
  }

  /**
   * @param impTransaccion
   *          the impTransaccion to set
   */
  public void setImpTransaccionAmount(float impTransaccionAmount) {
    this.importeTransaccion = impTransaccionAmount;
  }

  /**
   * @return the impPropinas
   */
  public String getImpPropinas() {
    return impPropinas;
  }

  /**
   * @param impPropinas
   *          the impPropinas to set
   */
  public void setImpPropinas(String impPropinas) {
    this.impPropinas = impPropinas;
  }

  /**
   * @return the FlagPago
   */
  public String getFlagPago() {
    return FlagPago;
  }

  /**
   * @param FlagPago
   *          the FlagPago to set
   */
  public void setFlagPago(String FlagPago) {
    this.FlagPago = FlagPago;
  }

  /**
   * @return the impSoles
   */
  public float getImpSoles() {
    return impSoles;
  }

  /**
   * @param impSoles
   *          the impSoles to set
   */
  public void setImpSoles(float impSoles) {
    this.impSoles = impSoles;
  }

  /**
   * @return the impEuros
   */
  public float getImpEuros() {
    return impEuros;
  }

  /**
   * @param impEuros
   *          the impEuros to set
   */
  public void setImpEuros(float impEuros) {
    this.impEuros = impEuros;
  }

  /**
   * @return the glosa
   */
  public String getGlosa() {
    return glosa;
  }

  /**
   * @param glosa
   *          the glosa to set
   */
  public void setGlosa(String glosa) {
    this.glosa = glosa;
  }

  /**
   * @return the periodo
   */
  public short getPeriodo() {
    return periodo;
  }

  /**
   * @param periodo
   *          the periodo to set
   */
  public void setPeriodo(short periodo) {
    this.periodo = periodo;
  }

  /**
   * @return the ruc
   */
  public String getRuc() {
    return ruc;
  }

  /**
   * @param ruc
   *          the ruc to set
   */
  public void setRuc(String ruc) {
    this.ruc = ruc;
  }

  /**
   * @return the centroCosto
   */
  public String getCentroCosto() {
    return centroCosto;
  }

  /**
   * @param centroCosto
   *          the centroCosto to set
   */
  public void setCentroCosto(String centroCosto) {
    this.centroCosto = centroCosto;
  }

  /**
   * @return the socioProducto
   */
  public String getSocioProducto() {
    return socioProducto;
  }

  /**
   * @param socioProducto
   *          the socioProducto to set
   */
  public void setSocioProducto(String socioProducto) {
    this.socioProducto = socioProducto;
  }

  /**
   * @return the canal
   */
  public String getCanal() {
    return canal;
  }

  /**
   * @param canal
   *          the canal to set
   */
  public void setCanal(String canal) {
    this.canal = canal;
  }

  /**
   * @return the proveedorEmpleado
   */
  public String getProveedorEmpleado() {
    return proveedorEmpleado;
  }

  /**
   * @param proveedorEmpleado
   *          the proveedorEmpleado to set
   */
  public void setProveedorEmpleado(String proveedorEmpleado) {
    this.proveedorEmpleado = proveedorEmpleado;
  }

  /**
   * @return the rucDniCliente
   */
  public String getRucDniCliente() {
    return rucDniCliente;
  }

  /**
   * @param rucDniCliente
   *          the rucDniCliente to set
   */
  public void setRucDniCliente(String rucDniCliente) {
    this.rucDniCliente = rucDniCliente;
  }

  /**
   * @return the polizaCliente
   */
  public String getPolizaCliente() {
    return polizaCliente;
  }

  /**
   * @param polizaCliente
   *          the polizaCliente to set
   */
  public void setPolizaCliente(String polizaCliente) {
    this.polizaCliente = polizaCliente;
  }

  /**
   * @return the Inversiones
   */
  public String getInversiones() {
    return Inversiones;
  }

  /**
   * @param Inversiones
   *          the Inversiones to set
   */
  public void setInversiones(String Inversiones) {
    this.Inversiones = Inversiones;
  }

  /**
   * @return the nroSiniestro
   */
  public String getNroSiniestro() {
    return nroSiniestro;
  }

  /**
   * @param nroSiniestro
   *          the nroSiniestro to set
   */
  public void setNroSiniestro(String nroSiniestro) {
    this.nroSiniestro = nroSiniestro;
  }

  /**
   * @return the comprobanteSunat
   */
  public String getComprobanteSunat() {
    return comprobanteSunat;
  }

  /**
   * @param comprobanteSunat
   *          the comprobanteSunat to set
   */
  public void setComprobanteSunat(String comprobanteSunat) {
    this.comprobanteSunat = comprobanteSunat;
  }

  /**
   * @return the tipoMedioPago
   */
  public String getTipoMedioPago() {
    return tipoMedioPago;
  }

  /**
   * @param tipoMedioPago
   *          the tipoMedioPago to set
   */
  public void setTipoMedioPago(String tipoMedioPago) {
    this.tipoMedioPago = tipoMedioPago;
  }

  /**
   * @return the diario
   */
  public int getDiario() {
    return diario;
  }

  /**
   * @param diario
   *          the diario to set
   */
  public void setDiario(int diario) {
    this.diario = diario;
  }

  /**
   * @return the numeroConstancia
   */
  public String getNumeroConstancia() {
    return numeroConstancia;
  }

  /**
   * @param numeroConstancia
   *          the numeroConstancia to set
   */
  public void setNumeroConstancia(String numeroConstancia) {
    this.numeroConstancia = numeroConstancia;
  }

  /**
   * @return the nroCheque
   */
  public String getNroCheque() {
    return NroCheque;
  }

  /**
   * @param nroCheque
   *          the nroCheque to set
   */
  public void setNroCheque(String nroCheque) {
    NroCheque = nroCheque;
  }

  /**
   * @return the refCtaBancoProvision
   */
  public String getRefCtaBancoProvision() {
    return RefCtaBancoProvision;
  }

  /**
   * @param refCtaBancoProvision
   *          the refCtaBancoProvision to set
   */
  public void setRefCtaBancoProvision(String refCtaBancoProvision) {
    RefCtaBancoProvision = refCtaBancoProvision;
  }

  /**
   * @return the refCtaBancoComision
   */
  public String getRefCtaBancoComision() {
    return RefCtaBancoComision;
  }

  /**
   * @param refCtaBancoComision
   *          the refCtaBancoComision to set
   */
  public void setRefCtaBancoComision(String refCtaBancoComision) {
    RefCtaBancoComision = refCtaBancoComision;
  }

  public String getLineaDiario() {
    return lineaDiario;
  }

  public void setLineaDiario(String lineaDiario) {
    this.lineaDiario = lineaDiario;
  }

  public BigDecimal getDifEuros() {
    return difEuros;
  }

  public void setDifEuros(BigDecimal difEuros) {
    this.difEuros = difEuros;
  }

  public BigDecimal getDifSoles() {
    return difSoles;
  }

  public void setDifSoles(BigDecimal difSoles) {
    this.difSoles = difSoles;
  }

  public boolean isbFlagPago() {
    return bFlagPago;
  }

  public void setbFlagPago(boolean bFlagPago) {
    this.bFlagPago = bFlagPago;
  }

  public String getDebitoCredito() {
    return debitoCredito;
  }

  public void setDebitoCredito(String debitoCredito) {
    this.debitoCredito = debitoCredito;
  }

  public String getReferenciaTransaccion() {
    return referenciaTransaccion;
  }

  public void setReferenciaTransaccion(String referenciaTransaccion) {
    this.referenciaTransaccion = referenciaTransaccion;
  }

  public float getImporteBase() {
    return importeBase;
  }

  public void setImporteBase(float importeBase) {
    this.importeBase = importeBase;
  }

  public float getImporteTransaccion() {
    return importeTransaccion;
  }

  public void setImporteTransaccion(float importeTransaccion) {
    this.importeTransaccion = importeTransaccion;
  }

  public String getTipoDiario() {
    return tipoDiario;
  }

  public void setTipoDiario(String tipoDiario) {
    this.tipoDiario = tipoDiario;
  }

  public String getReferenciaVinculada2() {
    return referenciaVinculada2;
  }

  public void setReferenciaVinculada2(String referenciaVinculada2) {
    this.referenciaVinculada2 = referenciaVinculada2;
  }

  public String getDescripcionGeneral1() {
    return descripcionGeneral1;
  }

  public void setDescripcionGeneral1(String descripcionGeneral1) {
    this.descripcionGeneral1 = descripcionGeneral1;
  }

  public String getDescripcionGeneral2() {
    return descripcionGeneral2;
  }

  public void setDescripcionGeneral2(String descripcionGeneral2) {
    this.descripcionGeneral2 = descripcionGeneral2;
  }

  public String getDescripcionGeneral3() {
    return descripcionGeneral3;
  }

  public void setDescripcionGeneral3(String descripcionGeneral3) {
    this.descripcionGeneral3 = descripcionGeneral3;
  }

public String getJournalType() {
	return journalType;
}

public void setJournalType(String journalType) {
	this.journalType = journalType;
}
}
