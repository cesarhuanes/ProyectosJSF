package com.cardif.satelite.tesoreria.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.satelite.model.FirmantePar;
import com.cardif.satelite.tesoreria.model.Cheque;
import com.cardif.satelite.tesoreria.service.ChequeService;
import com.cardif.satelite.tesoreria.service.FirmanteService;
import com.cardif.satelite.util.SateliteConstants;
import com.cardif.satelite.util.SateliteUtil;

@Controller
@Scope("request")
public class AsignacionController extends BaseController implements Serializable {
  private static final long serialVersionUID = 1L;
  public static final Logger LOGGER = Logger.getLogger(AsignacionController.class);

  private String banco;
  private List<SelectItem> bancos;
  private Date fechaDesde;
  private Date fechaHasta;
  private String numeroCheque;
  private List<Cheque> cheques;
  private Long par;
  private List<SelectItem> pares;
  private Boolean mostrar;

  @Autowired
  private ChequeService chequeService;

  @Autowired
  private FirmanteService firmanteService;

  @Override
  @PostConstruct
  public String inicio() {

    if (tieneAcceso()) {
      LOGGER.info("Inicio");
      this.bancos = new ArrayList<SelectItem>();
      this.bancos.add(new SelectItem(null, "Seleccionar banco"));
      List<String> bancos = chequeService.obtenerBancos();
      for (String banco : bancos) {
        this.bancos.add(new SelectItem(banco, banco));
      }

      this.pares = new ArrayList<SelectItem>();
      this.pares.add(new SelectItem(null, "Seleccionar"));
      List<FirmantePar> pares = firmanteService.buscarPares();
      for (FirmantePar par : pares) {
        this.pares.add(new SelectItem(par.getCodigo(), par.getFirmante1() + " - " + par.getFirmante2()));
      }
      mostrar = Boolean.FALSE;
      LOGGER.info("Fin");
    }
    return null;
  }

  public String buscar() {
    if (this.fechaHasta != null && this.fechaDesde != null) {
      if (!this.fechaHasta.after(this.fechaDesde) && !this.fechaDesde.equals(this.fechaHasta)) {

        SateliteUtil.mostrarMensaje("La fecha desde debe ser menor a la fecha hasta");
        return null;
      }
    }
    buscarCheques();
    LOGGER.info("Fin");

    return null;
  }

  private void buscarCheques() {
    String fechaDesde = SateliteUtil.getFormatDate(this.fechaDesde);
    String fechaHasta = SateliteUtil.getFormatDate(this.fechaHasta);
    LOGGER.info("Fecha desde: " + fechaDesde);
    LOGGER.info("Fecha hasta: " + fechaHasta);
    LOGGER.info("Banco: " + banco);
    LOGGER.info("Número de cheque: " + numeroCheque);

    cheques = chequeService.buscar(banco, numeroCheque, fechaDesde, fechaHasta);
  }

  public String limpiar() {
    fechaDesde = null;
    fechaHasta = null;
    numeroCheque = null;
    banco = null;
    cheques = new ArrayList<Cheque>();
    return null;
  }

  public void pintar(OutputStream os, Object obj) {
    String path = SateliteUtil.getPath("/templates");
    String rutaPDF = path + File.separator + "PDF_CHEQUE_MAQUETA.pdf";

    try {

      File archivo = new File(rutaPDF);

      byte[] bytes = new byte[(int) archivo.length()];

      FileInputStream input = new FileInputStream(archivo);
      input.read(bytes);
      input.close();
      os.write(bytes);
      // os.flush();

    } catch (IOException e) {
      log.error("Erreur lors de la generation du PDF", e);
    }
  }

  public String prepararVisualizacion() {
    mostrar = Boolean.FALSE;
    String mensaje = chequeService.generarPrevisualizacion(cheques, par, Boolean.FALSE);
    if (mensaje.equalsIgnoreCase(SateliteConstants.MENSAJE_PDF_EXITO)) {
      mostrar = Boolean.TRUE;
    } else {
      SateliteUtil.mostrarMensaje(mensaje);
    }
    return null;

  }

  public String asignar() {
    String usuario = SecurityContextHolder.getContext().getAuthentication().getName();

    String mensaje = chequeService.asignar(cheques, usuario, par);
    buscarCheques();
    SateliteUtil.mostrarMensaje(mensaje);
    return null;
  }

  // Getters & Setters
  public String getBanco() {
    return banco;
  }

  public void setBanco(String banco) {
    this.banco = banco;
  }

  public List<SelectItem> getBancos() {
    return bancos;
  }

  public void setBancos(List<SelectItem> bancos) {
    this.bancos = bancos;
  }

  public Date getFechaDesde() {
    return fechaDesde;
  }

  public void setFechaDesde(Date fechaDesde) {
    this.fechaDesde = fechaDesde;
  }

  public Date getFechaHasta() {
    return fechaHasta;
  }

  public void setFechaHasta(Date fechaHasta) {
    this.fechaHasta = fechaHasta;
  }

  public String getNumeroCheque() {
    return numeroCheque;
  }

  public void setNumeroCheque(String numeroCheque) {
    this.numeroCheque = numeroCheque;
  }

  public List<Cheque> getCheques() {
    return cheques;
  }

  public void setCheques(List<Cheque> cheques) {
    this.cheques = cheques;
  }

  public Long getPar() {
    return par;
  }

  public void setPar(Long par) {
    this.par = par;
  }

  public List<SelectItem> getPares() {
    return pares;
  }

  public void setPares(List<SelectItem> pares) {
    this.pares = pares;
  }

  public Boolean getMostrar() {
    return mostrar;
  }

  public void setMostrar(Boolean mostrar) {
    this.mostrar = mostrar;
  }

}
