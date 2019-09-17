package com.cardif.satelite.tesoreria.controller;

import static com.cardif.satelite.constantes.ErrorConstants.ERROR_SYNCCON;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.tesoreria.model.Cheque;
import com.cardif.satelite.tesoreria.service.ReporteImpresionService;

@Controller
@Scope("request")
public class ReporteImpresionController {

  @Autowired
  private ReporteImpresionService reporteImpresionService;
  private Date fecImpresion1;
  private Date fecImpresion2;
  private String moneda;
  private String estado;
  private String banco;
  private List<Cheque> listaCheques;
  private boolean desabilitarExport;
  private List<SelectItem> bancos;
  private List<SelectItem> estados;
  private List<SelectItem> monedas;
  public static final Logger LOGGER = Logger.getLogger(ReporteImpresionController.class);

  @PostConstruct
  public String inicio() {
    // ComboBox de Bancos:
    LOGGER.info("Inicio");
    listaCheques = new ArrayList<Cheque>();
    desabilitarExport = true;

    List<Parametro> listasBancos = reporteImpresionService.getBancos();
    bancos = new ArrayList<SelectItem>();
    bancos.add(new SelectItem("", "Seleccionar"));

    for (Parametro parametro : listasBancos) {
      bancos.add(new SelectItem(parametro.getNomValor(), parametro.getNomValor()));
    }

    // ComboBox de estados:
    List<Parametro> listaEstados = reporteImpresionService.getEstados();
    estados = new ArrayList<SelectItem>();
    estados.add(new SelectItem("", "Seleccionar"));

    for (Parametro parametro : listaEstados) {
      estados.add(new SelectItem(parametro.getCodValor(), parametro.getNomValor()));
    }

    // ComboBox de monedas:
    List<Parametro> listaMonedas = reporteImpresionService.getMonedas();
    monedas = new ArrayList<SelectItem>();
    monedas.add(new SelectItem("", "Seleccionar"));

    for (Parametro parametro : listaMonedas) {
      monedas.add(new SelectItem(parametro.getNomValor(), parametro.getNomValor()));
    }
    return null;
  }

  public String limpiar() {
    moneda = "";
    fecImpresion1 = null;
    fecImpresion2 = null;
    return null;
  }

  public String buscar() {
    listaCheques.clear();
    desabilitarExport = false;

    listaCheques = reporteImpresionService.buscarCheques(fecImpresion1, fecImpresion2, estado, banco, moneda);

    if (listaCheques.size() > 0) {
      desabilitarExport = false;
    } else {
      desabilitarExport = true;
    }

    return null;
  }

  public String exportar() {
    LOGGER.info("Inicio");
    String respuesta = "";

    Map<String, Object> beans = new HashMap<String, Object>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
    String FechaActual = sdf.format(new Date(System.currentTimeMillis()));

    try {

      if (listaCheques.size() > 0) {

        String rutaTemp = System.getProperty("java.io.tmpdir") + "Reporte_Impresion_" + System.currentTimeMillis() + ".xls";
        LOGGER.debug("Ruta Archivo: " + rutaTemp);
        FacesContext fc = FacesContext.getCurrentInstance();
        ServletContext sc = (ServletContext) fc.getExternalContext().getContext();
        String rutaTemplate = sc.getRealPath(File.separator + "excel" + File.separator + "template_exportar_reporte_impresion.xls");
        beans.put("exportar", listaCheques);
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(rutaTemplate, beans, rutaTemp);
        File archivoResp = new File(rutaTemp);
        FileInputStream in = new FileInputStream(archivoResp);
        HttpServletResponse response = (HttpServletResponse) contexto.getResponse();
        byte[] loader = new byte[(int) archivoResp.length()];
        response.addHeader("Content-Disposition", "attachment;filename=" + "Reporte_Impresion_" + FechaActual + ".xls");
        response.setContentType("application/vnd.ms-excel");

        ServletOutputStream out = response.getOutputStream();

        while ((in.read(loader)) > 0) {
          out.write(loader, 0, loader.length);
        }
        in.close();
        out.close();

        FacesContext.getCurrentInstance().responseComplete();

      } else {
        throw new SyncconException(ErrorConstants.COD_ERROR_BUSQUEDA_PENDIENTE, FacesMessage.SEVERITY_INFO);
      }

    } catch (SyncconException ex) {
      LOGGER.error(ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);

    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    LOGGER.info("Fin");
    return respuesta;
  }

  public void onSelectionChanged() {
    System.out.println("Cambiando de filas");
  }

  public String getMoneda() {
    return moneda;
  }

  public void setMoneda(String moneda) {
    this.moneda = moneda;
  }

  public boolean isDesabilitarExport() {
    return desabilitarExport;
  }

  public Date getFecImpresion1() {
    return fecImpresion1;
  }

  public void setFecImpresion1(Date fecImpresion1) {
    this.fecImpresion1 = fecImpresion1;
  }

  public Date getFecImpresion2() {
    return fecImpresion2;
  }

  public void setFecImpresion2(Date fecImpresion2) {
    this.fecImpresion2 = fecImpresion2;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public String getBanco() {
    return banco;
  }

  public void setBanco(String banco) {
    this.banco = banco;
  }

  public void setDesabilitarExport(boolean desabilitarExport) {
    this.desabilitarExport = desabilitarExport;
  }

  public List<Cheque> getListaCheques() {
    return listaCheques;
  }

  public void setListaCheques(List<Cheque> listaCheques) {
    this.listaCheques = listaCheques;
  }

  public List<SelectItem> getBancos() {
    return bancos;
  }

  public void setBancos(List<SelectItem> bancos) {
    this.bancos = bancos;
  }

  public List<SelectItem> getEstados() {
    return estados;
  }

  public void setEstados(List<SelectItem> estados) {
    this.estados = estados;
  }

  public List<SelectItem> getMonedas() {
    return monedas;
  }

  public void setMonedas(List<SelectItem> monedas) {
    this.monedas = monedas;
  }

}
