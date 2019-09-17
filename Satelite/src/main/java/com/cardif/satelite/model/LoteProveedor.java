package com.cardif.satelite.model;

public class LoteProveedor {
  private String nro_lote;

  private String tip_doc_proveedor;

  private String num_doc_proveedor;

  private String nom_proveedor;

  public String getNro_lote() {
    return nro_lote;
  }

  public void setNro_lote(String nro_lote) {
    this.nro_lote = nro_lote;
  }

  public String getTip_doc_proveedor() {
    return tip_doc_proveedor;
  }

  public void setTip_doc_proveedor(String tip_doc_proveedor) {
    this.tip_doc_proveedor = tip_doc_proveedor;
  }

  public String getNum_doc_proveedor() {
    return num_doc_proveedor;
  }

  public void setNum_doc_proveedor(String num_doc_proveedor) {
    this.num_doc_proveedor = num_doc_proveedor;
  }

  public String getNom_proveedor() {
    return nom_proveedor;
  }

  public void setNom_proveedor(String nom_proveedor) {
    this.nom_proveedor = nom_proveedor;
  }
}