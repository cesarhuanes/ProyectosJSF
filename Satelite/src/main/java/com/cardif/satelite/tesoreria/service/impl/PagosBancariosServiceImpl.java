package com.cardif.satelite.tesoreria.service.impl;

import static com.cardif.satelite.constantes.Constantes.TIP_PARAM_DETALLE;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.satelite.configuracion.dao.ParametroMapper;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.model.LoteCabecera;
import com.cardif.satelite.model.LoteFactura;
import com.cardif.satelite.model.LoteProveedor;
import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.tesoreria.bean.ProveedorFacturaBean;
import com.cardif.satelite.tesoreria.dao.LoteCabeceraMapper;
import com.cardif.satelite.tesoreria.dao.LoteFacturaMapper;
import com.cardif.satelite.tesoreria.dao.LoteProveedorMapper;
import com.cardif.satelite.tesoreria.service.PagosBancariosService;
import com.cardif.sunsystems.controller.SunsystemsController;
import com.cardif.sunsystems.mapeo.detalleBanco.SSC.Payload.BankDetails;
import com.cardif.sunsystems.mapeo.direccion.SSC.Payload.Address;
import com.cardif.sunsystems.mapeo.proveedor.SSC.Payload.Supplier;

@Service("pagosBancariosService")
public class PagosBancariosServiceImpl implements PagosBancariosService {

  @Autowired
  LoteCabeceraMapper loteCabeceraMapper;

  @Autowired
  LoteProveedorMapper loteProveedorMapper;

  @Autowired
  LoteFacturaMapper loteFacturaMapper;

  @Autowired
  ParametroMapper parametroMapper;

  SunsystemsController controlSun = SunsystemsController.getInstance();

  @Override
  public List<LoteCabecera> buscar(String nroLote, String tipoPago, Date fec1, Date fec2) {
    return loteCabeceraMapper.getLotesCabecera(nroLote, tipoPago, fec1, fec2);
  }

  @Override
  public List<LoteProveedor> getLotesProveedor(String nroLote) {
    return loteProveedorMapper.getLotesProveedor(nroLote);
  }

  @Override
  public List<LoteFactura> getLotesFactura(String nroLote) {
    return loteFacturaMapper.getLotesFactura(nroLote);
  }

  @Override
  public List<Parametro> getTiposPagosMasivos() {
    return parametroMapper.selectPagosMasivos(Constantes.MDP_TIPO_MARCADOR_PAGOS, TIP_PARAM_DETALLE);
  }

  @Override
  public List<ProveedorFacturaBean> getJoinProveedorFactura(String nroLote) {
    return loteProveedorMapper.getJoinProveedorFactura(nroLote);
  }

  @Override
  public Address obtenerAddress(String addressCode) throws Exception {
    return controlSun.extraerAddress(addressCode);
  }

  @Override
  public Supplier obtenerProveedor(String lookupCode, String codBanco, String codMon) throws Exception {
    List<Supplier> suppliers = controlSun.extraerProveedor(lookupCode);
    // Elijo el primer elemento, pues las cuentas de proveedor son iguales para
    // todos.
    Supplier aux = suppliers.get(0);

    return aux;
  }

  @Override
  public BankDetails obtenerBankDetails(String bankDetailsCode) throws Exception {
    BankDetails bankDetail = controlSun.extraerBankDetails(bankDetailsCode);
    return bankDetail;
  }

  @Override
  public void updateFechaProceso(String nroLote, Date fechaProceso) {
    loteCabeceraMapper.updateFechaProceso(nroLote, fechaProceso);
  }

  @Override
  public String getTipDocProveedor(String tipCodSunat) {
    String codValor = tipCodSunat;
    if (!tipCodSunat.equals("07") && !tipCodSunat.equals("08")) {
      codValor = "00";
    }
    return parametroMapper.obtenerParametro(Constantes.MDP_DOC_PROVEEDOR, Constantes.TIP_PARAM_DETALLE, codValor).getNomValor();
  }

  @Override
  public String getRutaTrama() {
    return parametroMapper.obtenerParametro(Constantes.MDP_RUTA_TRAMA, Constantes.TIP_PARAM_DETALLE, "1").getNomValor();
  }

}
