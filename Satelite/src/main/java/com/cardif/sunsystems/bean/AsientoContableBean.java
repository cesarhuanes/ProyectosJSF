package com.cardif.sunsystems.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author hurtadojo
 */
public class AsientoContableBean implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private String layout;
  private String cuentaContable;
  private String nombreLibro;
  private Date fecha;
  private String periodo;
  private String glosa;
  private String referencia;
  private BigDecimal importeSoles;
  private String marcadorDC;
  private String moneda;
  private BigDecimal importeTransaccion;
  private BigDecimal importeEuros;
  private String centroCosto;
  private String socioProducto;
  private String canal;
  private String proveedorEmpleado;
  private String rucDniCliente;
  private String polizaCliente;
  private String inversiones;
  private String nroSiniestro;
  private String tipoComprobanteSunat;
  private String tipoMedioPago;
  private Date fechaVencimiento;
  private String codActivoFijo;
  private String indicador;
  private String ctaProveedor;
  private String nroConstancia;
  private String marcador;
  private String refImpSoles;
  private String refImpTrans;
  private String refImpEuros;
  private String nomProveedor;

  /**
   * @return the layout
   */
  public String getLayout() {
    return layout;
  }

  /**
   * @param layout
   *          the layout to set
   */
  public void setLayout(String layout) {
    this.layout = layout;
  }

  /**
   * @return the cuentaContable
   */
  public String getCuentaContable() {
    return cuentaContable;
  }

  /**
   * @param cuentaContable
   *          the cuentaContable to set
   */
  public void setCuentaContable(String cuentaContable) {
    this.cuentaContable = cuentaContable;
  }

  /**
   * @return the nombreLibro
   */
  public String getNombreLibro() {
    return nombreLibro;
  }

  /**
   * @param nombreLibro
   *          the nombreLibro to set
   */
  public void setNombreLibro(String nombreLibro) {
    this.nombreLibro = nombreLibro;
  }

  /**
   * @return the fecha
   */
  public Date getFecha() {
    return fecha;
  }

  /**
   * @param fecha
   *          the fecha to set
   */
  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  /**
   * @return the periodo
   */
  public String getPeriodo() {
    return periodo;
  }

  /**
   * @param periodo
   *          the periodo to set
   */
  public void setPeriodo(String periodo) {
    this.periodo = periodo;
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
   * @return the referencia
   */
  public String getReferencia() {
    return referencia;
  }

  /**
   * @param referencia
   *          the referencia to set
   */
  public void setReferencia(String referencia) {
    this.referencia = referencia;
  }

  /**
   * @return the importeSoles
   */
  public BigDecimal getImporteSoles() {
    return importeSoles;
  }

  /**
   * @param importeSoles
   *          the importeSoles to set
   */
  public void setImporteSoles(BigDecimal importeSoles) {
    this.importeSoles = importeSoles;
  }

  /**
   * @return the marcadorDC
   */
  public String getMarcadorDC() {
    return marcadorDC;
  }

  /**
   * @param marcadorDC
   *          the marcadorDC to set
   */
  public void setMarcadorDC(String marcadorDC) {
    this.marcadorDC = marcadorDC;
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
   * @return the importeTransaccion
   */
  public BigDecimal getImporteTransaccion() {
    return importeTransaccion;
  }

  /**
   * @param importeTransaccion
   *          the importeTransaccion to set
   */
  public void setImporteTransaccion(BigDecimal importeTransaccion) {
    this.importeTransaccion = importeTransaccion;
  }

  /**
   * @return the importeEuros
   */
  public BigDecimal getImporteEuros() {
    return importeEuros;
  }

  /**
   * @param importeEuros
   *          the importeEuros to set
   */
  public void setImporteEuros(BigDecimal importeEuros) {
    this.importeEuros = importeEuros;
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
   * @return the inversiones
   */
  public String getInversiones() {
    return inversiones;
  }

  /**
   * @param inversiones
   *          the inversiones to set
   */
  public void setInversiones(String inversiones) {
    this.inversiones = inversiones;
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
   * @return the tipoComprobanteSunat
   */
  public String getTipoComprobanteSunat() {
    return tipoComprobanteSunat;
  }

  /**
   * @param tipoComprobanteSunat
   *          the tipoComprobanteSunat to set
   */
  public void setTipoComprobanteSunat(String tipoComprobanteSunat) {
    this.tipoComprobanteSunat = tipoComprobanteSunat;
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
   * @return the fechaVencimiento
   */
  public Date getFechaVencimiento() {
    return fechaVencimiento;
  }

  /**
   * @param fechaVencimiento
   *          the fechaVencimiento to set
   */
  public void setFechaVencimiento(Date fechaVencimiento) {
    this.fechaVencimiento = fechaVencimiento;
  }

  /**
   * @return the codActivoFijo
   */
  public String getCodActivoFijo() {
    return codActivoFijo;
  }

  /**
   * @param codActivoFijo
   *          the codActivoFijo to set
   */
  public void setCodActivoFijo(String codActivoFijo) {
    this.codActivoFijo = codActivoFijo;
  }

  /**
   * @return the indicador
   */
  public String getIndicador() {
    return indicador;
  }

  /**
   * @param indicador
   *          the indicador to set
   */
  public void setIndicador(String indicador) {
    this.indicador = indicador;
  }

  /**
   * @return the ctaProveedor
   */
  public String getCtaProveedor() {
    return ctaProveedor;
  }

  /**
   * @param ctaProveedor
   *          the ctaProveedor to set
   */
  public void setCtaProveedor(String ctaProveedor) {
    this.ctaProveedor = ctaProveedor;
  }

  /**
   * @return the nroConstancia
   */
  public String getNroConstancia() {
    return nroConstancia;
  }

  /**
   * @param nroConstancia
   *          the nroConstancia to set
   */
  public void setNroConstancia(String nroConstancia) {
    this.nroConstancia = nroConstancia;
  }

  /**
   * @return the marcador
   */
  public String getMarcador() {
    return marcador;
  }

  /**
   * @param marcador
   *          the marcador to set
   */
  public void setMarcador(String marcador) {
    this.marcador = marcador;
  }

  /**
   * @return the refImpSoles
   */
  public String getRefImpSoles() {
    return refImpSoles;
  }

  /**
   * @param refImpSoles
   *          the refImpSoles to set
   */
  public void setRefImpSoles(String refImpSoles) {
    this.refImpSoles = refImpSoles;
  }

  /**
   * @return the refImpTrans
   */
  public String getRefImpTrans() {
    return refImpTrans;
  }

  /**
   * @param refImpTrans
   *          the refImpTrans to set
   */
  public void setRefImpTrans(String refImpTrans) {
    this.refImpTrans = refImpTrans;
  }

  /**
   * @return the refImpEuros
   */
  public String getRefImpEuros() {
    return refImpEuros;
  }

  /**
   * @param refImpEuros
   *          the refImpEuros to set
   */
  public void setRefImpEuros(String refImpEuros) {
    this.refImpEuros = refImpEuros;
  }

  public String getNomProveedor() {
    return nomProveedor;
  }

  public void setNomProveedor(String nomProveedor) {
    this.nomProveedor = nomProveedor;
  }

}
