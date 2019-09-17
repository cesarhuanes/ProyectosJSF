package com.cardif.satelite.model;

import java.math.BigDecimal;

public class LoteFactura {
  private String nro_lote;

  private String num_doc_proveedor;

  private BigDecimal importe;

  private String tip_cod_sunat;

  private String cuenta_contable;

  private String referencia;

  public String getNro_lote() {
    return nro_lote;
  }

  public void setNro_lote(String nro_lote) {
    this.nro_lote = nro_lote;
  }

  public String getNum_doc_proveedor() {
    return num_doc_proveedor;
  }

  public void setNum_doc_proveedor(String num_doc_proveedor) {
    this.num_doc_proveedor = num_doc_proveedor;
  }

  public BigDecimal getImporte() {
    return importe;
  }

  public void setImporte(BigDecimal importe) {
    this.importe = importe;
  }

  public String getTip_cod_sunat() {
    return tip_cod_sunat;
  }

  public void setTip_cod_sunat(String tip_cod_sunat) {
    this.tip_cod_sunat = tip_cod_sunat;
  }

  public String getCuenta_contable() {
    return cuenta_contable;
  }

  public void setCuenta_contable(String cuenta_contable) {
    this.cuenta_contable = cuenta_contable;
  }

  public String getReferencia() {
    return referencia;
  }

  public void setReferencia(String referencia) {
    this.referencia = referencia;
  }
}