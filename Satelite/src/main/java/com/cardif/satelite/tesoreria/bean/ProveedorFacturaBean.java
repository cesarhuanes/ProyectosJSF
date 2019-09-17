package com.cardif.satelite.tesoreria.bean;

import java.math.BigDecimal;

public class ProveedorFacturaBean {

  private String nroLote;
  private String tipoDocProveedor;
  private String numeroDocProveedor;
  private String nombreProveedor;
  private BigDecimal importe;
  private String tipoCodSunat;
  private String cuentaContable;
  private String referencia;

  public String getNroLote() {
    return nroLote;
  }

  public void setNroLote(String nroLote) {
    this.nroLote = nroLote;
  }

  public String getTipoDocProveedor() {
    return tipoDocProveedor;
  }

  public void setTipoDocProveedor(String tipoDocProveedor) {
    this.tipoDocProveedor = tipoDocProveedor;
  }

  public String getNumeroDocProveedor() {
    return numeroDocProveedor;
  }

  public void setNumeroDocProveedor(String numeroDocProveedor) {
    this.numeroDocProveedor = numeroDocProveedor;
  }

  public String getNombreProveedor() {
    return nombreProveedor;
  }

  public void setNombreProveedor(String nombreProveedor) {
    this.nombreProveedor = nombreProveedor;
  }

  public BigDecimal getImporte() {
    return importe;
  }

  public void setImporte(BigDecimal importe) {
    this.importe = importe;
  }

  public String getTipoCodSunat() {
    return tipoCodSunat;
  }

  public void setTipoCodSunat(String tipoCodSunat) {
    this.tipoCodSunat = tipoCodSunat;
  }

  public String getCuentaContable() {
    return cuentaContable;
  }

  public void setCuentaContable(String cuentaContable) {
    this.cuentaContable = cuentaContable;
  }

  public String getReferencia() {
    return referencia;
  }

  public void setReferencia(String referencia) {
    this.referencia = referencia;
  }
}
