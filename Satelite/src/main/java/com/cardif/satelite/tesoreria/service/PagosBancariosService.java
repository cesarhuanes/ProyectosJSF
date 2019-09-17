package com.cardif.satelite.tesoreria.service;

import java.util.Date;
import java.util.List;

import com.cardif.satelite.model.LoteCabecera;
import com.cardif.satelite.model.LoteFactura;
import com.cardif.satelite.model.LoteProveedor;
import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.tesoreria.bean.ProveedorFacturaBean;

public interface PagosBancariosService {

  public List<LoteCabecera> buscar(String nroLote, String tipoPago, Date fec1, Date fec2);

  public List<Parametro> getTiposPagosMasivos();

  public List<LoteProveedor> getLotesProveedor(String nroLote);

  public List<LoteFactura> getLotesFactura(String nroLote);

  public List<ProveedorFacturaBean> getJoinProveedorFactura(String nroLote);

  public void updateFechaProceso(String nroLote, Date fechaProceso);

  public com.cardif.sunsystems.mapeo.direccion.SSC.Payload.Address obtenerAddress(String addressCode) throws Exception;

  public com.cardif.sunsystems.mapeo.proveedor.SSC.Payload.Supplier obtenerProveedor(String lookupCode, String codBanco, String codMon) throws Exception;

  public com.cardif.sunsystems.mapeo.detalleBanco.SSC.Payload.BankDetails obtenerBankDetails(String bankDetailsCode) throws Exception;

  public String getTipDocProveedor(String tipCodSunat);

  public String getRutaTrama();

}
