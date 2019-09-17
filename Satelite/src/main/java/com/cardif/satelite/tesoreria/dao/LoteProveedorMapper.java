package com.cardif.satelite.tesoreria.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import com.cardif.satelite.model.LoteProveedor;
import com.cardif.satelite.tesoreria.bean.ProveedorFacturaBean;

public interface LoteProveedorMapper {

  int insert(LoteProveedor record);

  final String SELECT_LOTES_PROVEEDOR = "SELECT * FROM LOTE_PROVEEDOR WHERE "

      + "nro_lote=ISNULL(#{nroLote,jdbcType=VARCHAR},nro_lote)";

  @Select(SELECT_LOTES_PROVEEDOR)
  @ResultMap("BaseResultMap")
  List<LoteProveedor> getLotesProveedor(@Param("nroLote") String nroLote);

  final String SELECT_JOIN_PROVEEDOR_FACTURA = "select p.nro_lote,p.tip_doc_proveedor,p.num_doc_proveedor,p.nom_proveedor,f.importe, "

      + "f.tip_cod_sunat, f.cuenta_contable, f.referencia "

      + "from lote_proveedor p INNER JOIN lote_factura f "

      + "on p.nro_lote=f.nro_Lote  AND p.num_doc_proveedor=f.num_doc_proveedor "

      + "WHERE p.nro_lote=ISNULL(#{nroLote,jdbcType=VARCHAR},p.nro_lote)";

  @Select(SELECT_JOIN_PROVEEDOR_FACTURA)
  @ResultMap("JoinResultMap")
  List<ProveedorFacturaBean> getJoinProveedorFactura(@Param("nroLote") String nroLote);

}