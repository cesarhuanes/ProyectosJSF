package com.cardif.satelite.tesoreria.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.richfaces.model.selection.SimpleSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cardif.satelite.model.LoteCabecera;
import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.tesoreria.bean.LoteCabeceraBean;
import com.cardif.satelite.tesoreria.bean.ProveedorFacturaBean;
import com.cardif.satelite.tesoreria.service.PagosBancariosService;
import com.cardif.satelite.util.SateliteUtil;
import com.cardif.sunsystems.mapeo.detalleBanco.SSC.Payload.BankDetails;
import com.cardif.sunsystems.mapeo.direccion.SSC.Payload.Address;
import com.cardif.sunsystems.mapeo.proveedor.SSC.Payload.Supplier;
import com.cardif.sunsystems.util.ConstantesSun;

@Controller
@Scope("request")
public class PagosBancariosController implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = 1618355813618243169L;
  private Date fechaPago1 = new Date();
  private Date fechaPago2 = new Date();
  private String tipoPago;
  private String nroLote;

  private final String IZQUIERDA = ConstantesSun.UTL_RELLENAR_IZQUIERDA;
  private final String DERECHA = ConstantesSun.UTL_RELLENAR_DERECHA;

  private Date fecProceso = new Date();

  public static final Logger logger = Logger.getLogger(ConsultarPagosController.class);

  private List<SelectItem> tiposPago;

  @Autowired
  private PagosBancariosService pagosBancariosService;

  private List<LoteCabecera> listaCabecera;
  private List<ProveedorFacturaBean> listaJoinProveedorFactura;
  private List<LoteCabeceraBean> listaTabla;

  private LoteCabeceraBean loteActivo;

  private SimpleSelection selection;

  // Desabilitar:
  private boolean desabilitarVerDetalles = true;
  private boolean desabilitarExport = true;
  private boolean mostrarDetalles = false;

  @PostConstruct
  public String inicio() {

    tiposPago = new ArrayList<SelectItem>();
    tiposPago.add(new SelectItem("", "Seleccionar"));

    List<Parametro> listaMarcadorItems = pagosBancariosService.getTiposPagosMasivos();

    for (Parametro c : listaMarcadorItems) {
      tiposPago.add(new SelectItem(c.getNomValor(), c.getNomValor()));
    }

    return null;
  }

  private String getEmptyString(String x) {
    if (x.length() == 0) {
      return null;
    } else {
      return x;
    }
  }

  public String buscar() {
    LoteCabeceraBean bean = null;
    desabilitarVerDetalles = true;
    desabilitarExport = true;
    listaTabla = new ArrayList<LoteCabeceraBean>();

    if ((fechaPago1 == null && fechaPago2 != null) || (fechaPago1 != null && fechaPago2 == null)) {
      SateliteUtil.mostrarMensaje("Seleccione un rango de fechas (inicio y fin)");
      return null;
    }
    listaCabecera = pagosBancariosService.buscar(getEmptyString(nroLote), getEmptyString(tipoPago), fechaPago1, fechaPago2);

    if (listaCabecera.size() > 0) {
      for (LoteCabecera loteCabecera : listaCabecera) {
        bean = new LoteCabeceraBean();
        bean.setBanco(loteCabecera.getBanco());
        bean.setFec_creacion(loteCabecera.getFec_creacion());
        bean.setMoneda(loteCabecera.getMoneda());
        bean.setNro_lote(loteCabecera.getNro_lote());
        bean.setSeleccionado(false);
        bean.setTipo_pago(loteCabecera.getTipo_pago());

        listaTabla.add(bean);
      }
      desabilitarVerDetalles = false;
      desabilitarExport = false;
    }

    return null;
  }

  public String exportarTrama() {
    int contSeleccionados = 0;
    LoteCabeceraBean loteSelecc = null;
    PrintWriter out = null;

    String result = "";

    LoteCabecera lote = null;

    for (LoteCabeceraBean bean : listaTabla) {
      if (bean.isSeleccionado()) {
        loteSelecc = bean;
        contSeleccionados++;
      }

      if (contSeleccionados > 1) {
        SateliteUtil.mostrarMensaje("Una trama solo puede contener un lote");
        return null;
      }
    }

    // log.info("Fecha Proceso: " + fecProceso.toString());
    if (contSeleccionados == 0) {
      SateliteUtil.mostrarMensaje("Seleccione un lote para generar la trama");
      return null;
    } else if (fecProceso == null) {
      SateliteUtil.mostrarMensaje("Seleccione un fecha de proceso para generar la trama");
      return null;
    } else {
      lote = buscarLoteCabecera(loteSelecc.getNro_lote());

    }

    // Actualizar Fecha de Proceso:
    pagosBancariosService.updateFechaProceso(loteSelecc.getNro_lote(), fecProceso);

    // Extracción Address:
    Address direccion = extraerAddress();
    if (direccion == null) {
      SateliteUtil.mostrarMensaje("Problema al obtener la direccion (Address) de SUN");
      return null;
    }
    // Creando archivo trama:

    String ruta = pagosBancariosService.getRutaTrama() + lote.getNro_lote() + ".txt";

    File trama = new File(ruta);

    if (trama.exists()) {
      trama.delete();
    }

    try {
      // Escritura de la Trama:
      logger.info("INICIO ESCRITURA DE TRAMA");
      out = new PrintWriter(trama);

      logger.info("SECCION 3110");
      result = "";
      result += "3110";
      result += "R";
      result += SateliteUtil.ajustarTextoCaracteres(direccion.getLookupCode(), 12, " ", DERECHA);
      result += SateliteUtil.ajustarTextoCaracteres(SateliteUtil.getFormatDate(new Date()), 8, " ", DERECHA);
      result += SateliteUtil.ajustarTextoCaracteres(SateliteUtil.getFormatDate(fecProceso), 8, " ", DERECHA);
      result += SateliteUtil.ajustarTextoCaracteres(lote.getCta_banco(), 20, " ", DERECHA);
      result += SateliteUtil.ajustarTextoCaracteres(lote.getMoneda(), 3, " ", DERECHA);
      result += SateliteUtil.ajustarTextoCaracteres("", 12, " ", DERECHA);
      result += "1";
      result += "0";
      result += SateliteUtil.ajustarTextoCaracteres(lote.getNro_lote(), 20, " ", DERECHA);
      result += SateliteUtil.ajustarTextoCaracteres("", 3, " ", DERECHA);
      result += SateliteUtil.ajustarTextoCaracteres("", 162, " ", DERECHA);

      out.println(result);

      logger.info("SECCION 3120");
      result = "";
      result += "3120";
      result += "R";
      result += SateliteUtil.ajustarTextoCaracteres(direccion.getLookupCode(), 12, " ", DERECHA);
      result += SateliteUtil.ajustarTextoCaracteres(direccion.getAddressLine2(), 35, " ", DERECHA);
      result += SateliteUtil.ajustarTextoCaracteres("", 35, " ", DERECHA);
      result += SateliteUtil.ajustarTextoCaracteres("", 168, " ", DERECHA);

      out.println(result);

      String rucAnterior = null;

      String formaPago = null;
      String codBanco = null;
      String cuentaProv = null;
      String tipoCuenta = null;

      String resultProveedor3210 = "";
      String resultProveedor2 = "";
      String resultProveedor3220 = "";
      String resultProveedor3230 = "";
      String resultProveedor3810 = "";
      String resultProveedor3910 = "";
      int contFilasProveedor = 0;
      int contFilasTotales = 0;

      BigDecimal importeProveedor = new BigDecimal("0.00");
      List<String> textoFacturas = new ArrayList<String>();

      listaJoinProveedorFactura = pagosBancariosService.getJoinProveedorFactura(lote.getNro_lote());
      BigDecimal sumaTotal = getSumaTotal(listaJoinProveedorFactura);

      // int contadorPrueba = 0;
      for (ProveedorFacturaBean bean : listaJoinProveedorFactura) {

        // Saber cuándo cambia de proveedor:
        // Y si es la última factura?
        if (rucAnterior == null || !rucAnterior.equalsIgnoreCase(bean.getNumeroDocProveedor())) {
          if (resultProveedor3210.length() > 0) {
            // Cada vez que se cambie de proveedor:
            String auxImporte = getTextFromImporte(importeProveedor);

            resultProveedor3210 += auxImporte;
            resultProveedor3210 += resultProveedor2;

            // Imprimo al proveedor:
            out.println(resultProveedor3210);
            out.println(resultProveedor3220);
            out.println(resultProveedor3230);

            // Imprimo sus facturas:
            for (String fact : textoFacturas) {
              out.println(fact);
            }

            logger.info("SECCION 3810");
            // Son 3 líneas de proveedor + esta línea de total proveedor = 4
            contFilasProveedor = 4 + textoFacturas.size();
            contFilasTotales += contFilasProveedor;
            // Fila anterior, para obtener el Num Documento del proveedor
            // anterior, pues acaba de cambiar
            ProveedorFacturaBean auxAnterior = listaJoinProveedorFactura.get(listaJoinProveedorFactura.indexOf(bean) - 1);

            resultProveedor3810 = "";
            resultProveedor3810 += "3810";
            resultProveedor3810 += "R";
            resultProveedor3810 += SateliteUtil.ajustarTextoCaracteres(direccion.getLookupCode(), 12, " ", DERECHA);
            resultProveedor3810 += bean.getTipoDocProveedor();
            resultProveedor3810 += SateliteUtil.ajustarTextoCaracteres(auxAnterior.getNumeroDocProveedor(), 12, " ", DERECHA);
            resultProveedor3810 += SateliteUtil.ajustarTextoCaracteres(contFilasProveedor + "", 10, "0", IZQUIERDA);
            resultProveedor3810 += SateliteUtil.ajustarTextoCaracteres(textoFacturas.size() + "", 8, "0", IZQUIERDA);
            resultProveedor3810 += auxImporte;
            resultProveedor3810 += SateliteUtil.ajustarTextoCaracteres("", 193, " ", DERECHA);
            out.println(resultProveedor3810);

          }

          // Extracción Proveedor
          Supplier proveedor = extraerProveedor(bean.getNumeroDocProveedor(), ConstantesSun.COD_BANCO_BBVA, lote.getMoneda());
          String email = proveedor.getEMailAddress() == null ? "" : proveedor.getEMailAddress();
          /*
           * log.info("EMAIL DEL PROVEEDOR: " + email); if (contadorPrueba == 0) { email = "synchrony@hotmail.com"; contadorPrueba++; }
           */

          // Extracción BankDetail
          BankDetails bankDetail = extraerBankDetail(bean.getCuentaContable());

          if (bankDetail == null) {
            formaPago = "O";
            codBanco = ConstantesSun.COD_BANCO_BBVA;
            // CUENTA_PROV_ORDEN_PAGO = "00000000000000000000"
            cuentaProv = ConstantesSun.CUENTA_PROV_ORDEN_PAGO;
            tipoCuenta = "00";
          } else if (bankDetail.getBankSortCode() == null) {
            formaPago = "O";
            codBanco = ConstantesSun.COD_BANCO_BBVA;
            cuentaProv = ConstantesSun.CUENTA_PROV_ORDEN_PAGO;
            tipoCuenta = "00";
          } else if (bankDetail.getBankSortCode().equalsIgnoreCase(ConstantesSun.COD_BANCO_BBVA)) {
            formaPago = "P";
            codBanco = ConstantesSun.COD_BANCO_BBVA;
            cuentaProv = SateliteUtil.getTextoSinGuiones(bankDetail.getBankAccountNumber());
            tipoCuenta = "00";
          } else {
            formaPago = "I";
            codBanco = bankDetail.getBankSortCode();
            cuentaProv = SateliteUtil.getTextoSinGuiones(bankDetail.getBankBranch());
            tipoCuenta = "02";
          }

          logger.info("SECCION 3210");
          resultProveedor3210 = "";
          resultProveedor3210 += "3210";
          resultProveedor3210 += "R";
          resultProveedor3210 += SateliteUtil.ajustarTextoCaracteres(direccion.getLookupCode(), 12, " ", DERECHA);
          resultProveedor3210 += bean.getTipoDocProveedor();
          resultProveedor3210 += SateliteUtil.ajustarTextoCaracteres(bean.getNumeroDocProveedor(), 12, " ", DERECHA);
          resultProveedor3210 += SateliteUtil.ajustarTextoCaracteres(bean.getNombreProveedor(), 35, " ", DERECHA);

          resultProveedor2 = "";
          resultProveedor2 += lote.getMoneda();
          resultProveedor2 += SateliteUtil.ajustarTextoCaracteres("", 12, " ", DERECHA);
          resultProveedor2 += "0000";
          resultProveedor2 += SateliteUtil.ajustarTextoCaracteres("", 40, " ", DERECHA);
          resultProveedor2 += SateliteUtil.ajustarTextoCaracteres("", 117, " ", DERECHA);

          logger.info("SECCION 3220");
          resultProveedor3220 = "";
          resultProveedor3220 += "3220";
          resultProveedor3220 += "R";
          resultProveedor3220 += SateliteUtil.ajustarTextoCaracteres(direccion.getLookupCode(), 12, " ", DERECHA);
          resultProveedor3220 += bean.getTipoDocProveedor();
          resultProveedor3220 += SateliteUtil.ajustarTextoCaracteres(bean.getNumeroDocProveedor(), 12, " ", DERECHA);
          resultProveedor3220 += SateliteUtil.ajustarTextoCaracteres(codBanco, 4, "0", DERECHA);
          resultProveedor3220 += SateliteUtil.ajustarTextoCaracteres(cuentaProv, 20, " ", DERECHA);
          resultProveedor3220 += SateliteUtil.ajustarTextoCaracteres("", 35, " ", DERECHA);
          resultProveedor3220 += formaPago;
          resultProveedor3220 += SateliteUtil.ajustarTextoCaracteres("", 24, " ", DERECHA);
          resultProveedor3220 += tipoCuenta;
          resultProveedor3220 += SateliteUtil.ajustarTextoCaracteres("", 139, " ", DERECHA);

          logger.info("SECCION 3230");
          resultProveedor3230 = "";
          resultProveedor3230 += "3230";
          resultProveedor3230 += "R";
          resultProveedor3230 += SateliteUtil.ajustarTextoCaracteres(direccion.getLookupCode(), 12, " ", DERECHA);
          resultProveedor3230 += bean.getTipoDocProveedor();
          resultProveedor3230 += SateliteUtil.ajustarTextoCaracteres(bean.getNumeroDocProveedor(), 12, " ", DERECHA);
          resultProveedor3230 += SateliteUtil.ajustarTextoCaracteres("", 35, " ", DERECHA);
          resultProveedor3230 += SateliteUtil.ajustarTextoCaracteres("", 25, " ", DERECHA);
          resultProveedor3230 += SateliteUtil.ajustarTextoCaracteres("", 25, " ", DERECHA);

          // if (formaPago.equals("P")) {
          // email = "synchrony@hotmail.com";
          // } else {
          // email = "";
          // }
          logger.error("Forma Pago: " + formaPago);
          logger.error("Email: " + email);
          if (formaPago.equals("O")) {
            resultProveedor3230 += "N";
            email = "";
          } else {
            if (email.length() == 0) {
              resultProveedor3230 += "N";
            } else {
              resultProveedor3230 += "E";
            }
          }

          resultProveedor3230 += SateliteUtil.ajustarTextoCaracteres(email, 60, " ", DERECHA);
          resultProveedor3230 += SateliteUtil.ajustarTextoCaracteres("", 12, " ", DERECHA);
          resultProveedor3230 += SateliteUtil.ajustarTextoCaracteres("", 67, " ", DERECHA);

          importeProveedor = new BigDecimal("0.00");
          textoFacturas = new ArrayList<String>();
          rucAnterior = bean.getNumeroDocProveedor();
        }

        logger.info("SECCION 3310");
        String factura = "";
        factura += "3310";
        factura += "R";
        factura += SateliteUtil.ajustarTextoCaracteres(direccion.getLookupCode(), 12, " ", DERECHA);
        factura += bean.getTipoDocProveedor();
        factura += SateliteUtil.ajustarTextoCaracteres(bean.getNumeroDocProveedor(), 12, " ", DERECHA);

        // Tipo Doc Pagado al Proveedor y Num Doc Pagado al Proveedor
        String tipDocProveedor = pagosBancariosService.getTipDocProveedor(bean.getTipoCodSunat());
        factura += SateliteUtil.ajustarTextoCaracteres(tipDocProveedor, 1, " ", DERECHA);
        factura += SateliteUtil.ajustarTextoCaracteres(bean.getReferencia(), 12, " ", DERECHA);

        factura += SateliteUtil.ajustarTextoCaracteres(SateliteUtil.getFormatDate(fecProceso), 8, " ", DERECHA);
        factura += SateliteUtil.ajustarTextoCaracteres("", 8, " ", DERECHA);
        factura += lote.getMoneda();
        factura += getTextFromImporte(bean.getImporte());
        factura += getSigno(bean.getImporte());
        factura += SateliteUtil.ajustarTextoCaracteres("", 25, " ", DERECHA);
        factura += SateliteUtil.ajustarTextoCaracteres("", 153, " ", DERECHA);
        textoFacturas.add(factura);

        importeProveedor = importeProveedor.add(bean.getImporte());

      }

      // Para la última factura:
      // Cada vez que se cambie de proveedor:
      String auxImporte = getTextFromImporte(importeProveedor);

      resultProveedor3210 += auxImporte;
      resultProveedor3210 += resultProveedor2;

      // Imprimo al proveedor:
      out.println(resultProveedor3210);
      out.println(resultProveedor3220);
      out.println(resultProveedor3230);

      // Imprimo sus facturas:
      for (String fact : textoFacturas) {
        out.println(fact);
      }

      logger.info("SECCION 3810");
      // Son 3 líneas de proveedor + esta línea de total proveedor = 4
      contFilasProveedor = 4 + textoFacturas.size();
      contFilasTotales += contFilasProveedor;
      // Fila anterior, para obtener el Num Documento del proveedor anterior,
      // pues acaba de cambiar
      ProveedorFacturaBean auxAnterior = listaJoinProveedorFactura.get(listaJoinProveedorFactura.size() - 1);

      resultProveedor3810 = "";
      resultProveedor3810 += "3810";
      resultProveedor3810 += "R";
      resultProveedor3810 += SateliteUtil.ajustarTextoCaracteres(direccion.getLookupCode(), 12, " ", DERECHA);
      resultProveedor3810 += listaJoinProveedorFactura.get(0).getTipoDocProveedor();
      resultProveedor3810 += SateliteUtil.ajustarTextoCaracteres(auxAnterior.getNumeroDocProveedor(), 12, " ", DERECHA);
      resultProveedor3810 += SateliteUtil.ajustarTextoCaracteres(contFilasProveedor + "", 10, "0", IZQUIERDA);
      resultProveedor3810 += SateliteUtil.ajustarTextoCaracteres(textoFacturas.size() + "", 8, "0", IZQUIERDA);
      resultProveedor3810 += auxImporte;
      resultProveedor3810 += SateliteUtil.ajustarTextoCaracteres("", 193, " ", DERECHA);
      out.println(resultProveedor3810);

      logger.info("SECCION 3910");

      resultProveedor3910 = "";
      resultProveedor3910 += "3910";
      resultProveedor3910 += "R";
      resultProveedor3910 += SateliteUtil.ajustarTextoCaracteres(direccion.getLookupCode(), 12, " ", DERECHA);
      // contFilasTotales (filas proveedores) + 2 filas de cabecera + esta fila:
      resultProveedor3910 += SateliteUtil.ajustarTextoCaracteres((contFilasTotales + 3) + "", 10, "0", IZQUIERDA);
      resultProveedor3910 += SateliteUtil.ajustarTextoCaracteres(listaJoinProveedorFactura.size() + "", 8, "0", IZQUIERDA);
      resultProveedor3910 += getTextFromImporte(sumaTotal);
      resultProveedor3910 += SateliteUtil.ajustarTextoCaracteres("", 106, " ", DERECHA);
      out.println(resultProveedor3910);

      logger.info("FIN ESCRITURA DE TRAMA");
    } catch (FileNotFoundException e) {
      logger.error("ERROR ESCRITURA TRAMA");

    } finally {
      out.close();
    }

    if (trama.exists()) {
      trama.setExecutable(false);
      trama.setReadable(false);
      trama.setWritable(false);
      logger.info("NAME TRAMA: " + trama.getName());
      logger.info("Absolute TRAMA: " + trama.getAbsolutePath());
      SateliteUtil.mostrarMensaje("La trama '" + trama.getName() + "' ha sido generada exitosamente ");
    }
    return null;
  }

  private BigDecimal getSumaTotal(List<ProveedorFacturaBean> listaJoinProveedorFactura2) {
    BigDecimal suma = new BigDecimal("0.00");
    for (ProveedorFacturaBean bean : listaJoinProveedorFactura2) {
      suma = suma.add(bean.getImporte());
    }
    return suma.setScale(2, RoundingMode.HALF_UP);
  }

  private String getTextFromImporte(BigDecimal importe) {
    String entero = "";
    String decimal = "";

    importe = importe.abs();

    String importeText = importe.setScale(2, RoundingMode.HALF_UP) + "";

    entero = "" + Integer.parseInt((importeText).substring(0, importeText.indexOf(".")));
    decimal = "" + Integer.parseInt((importeText).substring(importeText.indexOf(".") + 1, importeText.length()));

    entero = SateliteUtil.ajustarTextoCaracteres(entero, 12, "0", IZQUIERDA);
    decimal = SateliteUtil.ajustarTextoCaracteres(decimal, 2, "0", DERECHA);

    return entero + decimal;
  }

  private String getSigno(BigDecimal importe) {
    String signo = "";

    if (importe.signum() == 0 || importe.signum() == 1) {
      signo = 1 + "";
    } else {
      signo = 2 + "";
    }
    return signo;
  }

  public Address extraerAddress() {
    Address direccion = null;

    logger.info("-----INICIO EXTRACCION ADDRESS-----");
    try {
      direccion = pagosBancariosService.obtenerAddress(ConstantesSun.CODIGO_BUSQUEDA_DIRECCIONES);
    } catch (Exception e) {
      logger.error("Problema al obtener la direccion (Address) de SUN: ");
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));

    }
    logger.info("-----FIN EXTRACCION ADDRESS-----");

    return direccion;
  }

  public Supplier extraerProveedor(String lookupCode, String codBanco, String codMon) {
    Supplier proveedor = null;

    logger.info("-----INICIO EXTRACCION PROVEEDOR-----");
    try {
      proveedor = pagosBancariosService.obtenerProveedor(lookupCode, codBanco, codMon);
    } catch (Exception e) {
      logger.error("Problema al obtener datos del proveedor (Supplier) de SUN");
    }
    logger.info("-----FIN EXTRACCION PROVEEDOR-----");

    return proveedor;
  }

  public BankDetails extraerBankDetail(String bankDetailCode) {
    BankDetails bankDetail = null;

    logger.info("-----INICIO EXTRACCION BankDetails-----");
    try {
      bankDetail = pagosBancariosService.obtenerBankDetails(bankDetailCode);
    } catch (Exception e) {
      logger.error("Problema al obtener los BankDetails de SUN");
      SateliteUtil.mostrarMensaje("Problema al obtener los BankDetails de SUN");
      return null;
    }
    logger.info("-----FIN EXTRACCION BankDetails-----");

    return bankDetail;
  }

  private LoteCabecera buscarLoteCabecera(String nroLote) {
    LoteCabecera lote = null;
    for (LoteCabecera loteCabecera : listaCabecera) {
      if (loteCabecera.getNro_lote().equalsIgnoreCase(nroLote)) {
        lote = loteCabecera;
        break;
      }
    }

    return lote;
  }

  public String seleccionarLoteActivo() {

    loteActivo = (LoteCabeceraBean) SateliteUtil.verSeleccionado(selection, listaTabla);
    logger.info("Lote seleccionado: " + loteActivo.getNro_lote());

    return null;
  }

  public String verDetalles() {

    if (loteActivo == null) {
      SateliteUtil.mostrarMensaje("Seleccione un lote para ver los detalles");
      mostrarDetalles = false;
      return null;
    } else {
      mostrarDetalles = true;
    }

    listaJoinProveedorFactura = pagosBancariosService.getJoinProveedorFactura(loteActivo.getNro_lote());

    return null;
  }

  public Date getFechaPago1() {
    return fechaPago1;
  }

  public void setFechaPago1(Date fechaPago1) {
    this.fechaPago1 = fechaPago1;
  }

  public Date getFechaPago2() {
    return fechaPago2;
  }

  public void setFechaPago2(Date fechaPago2) {
    this.fechaPago2 = fechaPago2;
  }

  public String getTipoPago() {
    return tipoPago;
  }

  public void setTipoPago(String tipoPago) {
    this.tipoPago = tipoPago;
  }

  public String getNroLote() {
    return nroLote;
  }

  public void setNroLote(String nroLote) {
    this.nroLote = nroLote;
  }

  public Date getFecProceso() {
    return fecProceso;
  }

  public void setFecProceso(Date fecProceso) {
    this.fecProceso = fecProceso;
  }

  public List<LoteCabeceraBean> getListaTabla() {
    return listaTabla;
  }

  public void setListaTabla(List<LoteCabeceraBean> listaTabla) {
    this.listaTabla = listaTabla;
  }

  public boolean isDesabilitarVerDetalles() {
    return desabilitarVerDetalles;
  }

  public void setDesabilitarVerDetalles(boolean desabilitarVerDetalles) {
    this.desabilitarVerDetalles = desabilitarVerDetalles;
  }

  public boolean isDesabilitarExport() {
    return desabilitarExport;
  }

  public void setDesabilitarExport(boolean desabilitarExport) {
    this.desabilitarExport = desabilitarExport;
  }

  public List<SelectItem> getTiposPago() {
    return tiposPago;
  }

  public void setTiposPago(List<SelectItem> tiposPago) {
    this.tiposPago = tiposPago;
  }

  public SimpleSelection getSelection() {
    return selection;
  }

  public void setSelection(SimpleSelection selection) {
    this.selection = selection;
  }

  public LoteCabeceraBean getLoteActivo() {
    return loteActivo;
  }

  public void setLoteActivo(LoteCabeceraBean loteActivo) {
    this.loteActivo = loteActivo;
  }

  public List<LoteCabecera> getListaCabecera() {
    return listaCabecera;
  }

  public void setListaCabecera(List<LoteCabecera> listaCabecera) {
    this.listaCabecera = listaCabecera;
  }

  public List<ProveedorFacturaBean> getListaJoinProveedorFactura() {
    return listaJoinProveedorFactura;
  }

  public void setListaJoinProveedorFactura(List<ProveedorFacturaBean> listaJoinProveedorFactura) {
    this.listaJoinProveedorFactura = listaJoinProveedorFactura;
  }

  public boolean isMostrarDetalles() {
    return mostrarDetalles;
  }

  public void setMostrarDetalles(boolean mostrarDetalles) {
    this.mostrarDetalles = mostrarDetalles;
  }

}
