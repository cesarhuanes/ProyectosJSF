package com.cardif.satelite.tesoreria.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.ws.soap.SOAPFaultException;

import org.xml.sax.SAXException;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.LoteCabecera;
import com.cardif.satelite.model.LoteFactura;
import com.cardif.satelite.model.LoteProveedor;
import com.cardif.satelite.tesoreria.bean.JournalBean;
import com.cardif.satelite.tesoreria.model.PagoComisionItf;
import com.cardif.sunsystems.bean.AsientoContableBean;

public interface ProcesoPagos {

  public HashMap<String, BigDecimal> traerTipoCambioActual(String fechaProceso) throws SOAPFaultException, ParserConfigurationException, SAXException, IOException, TransformerException;

  public List<AsientoContableBean> procesarPago(List<List<JournalBean>> listaProcesos, String tipoPago, Date fechaPago, float tasaEurosSoles, float tasaEurosDolares, float tasaDolaresSoles,
      String ctaBanco, String glosaPago, String referencia, String medioPagoAnalisisCode10, Date fecTransaccion) throws SyncconException;

  public List<SelectItem> traerListaTipoPagoItems() throws SyncconException;

  public List<SelectItem> traerListaMedioPagoItems() throws SyncconException;;

  public List<SelectItem> traerListaMarcadorItems() throws SyncconException;

  public List<SelectItem> traerListaCtaBancos() throws SyncconException;

  public List<List<JournalBean>> consultarRegistros(String idProveedorFrom, String idProveedorTo, String fecPeriodoInicio, String fecPeriodoFin) throws Exception;

  public List<String> enviarSun(List<AsientoContableBean> asientoContable, String metodoEjecutado, HashMap<Integer, Object> resultados, String tipoPago, String numCuentaBanco);

  public HashMap<Integer, String> procesarResultadoSun(String resultado);

  public HashMap<Integer, Integer> procesarResultadoSunExitoso(String resultado);

  public String marcarLineasPagados(JournalBean bean) throws Exception;

  public String getTipoDiario();

  public void setLoteAndOp(String nroLote, String opBanco, String opComision);

  public void registrarExcelComisiones(PagoComisionItf pagoComisionItf);

  public int contarExcelByName(String nom_excel);

  public int selectMaxLoteSecuencial(String tipoPago, String banco, String moneda);

  public void registrarLoteCabecera(LoteCabecera lotePago);

  public void registrarLoteProveedor(LoteProveedor lotePago);

  public void registrarLoteFactura(LoteFactura lotePago);

  public String getTipoDiarioTransferencia(String moneda) throws SyncconException;
}
