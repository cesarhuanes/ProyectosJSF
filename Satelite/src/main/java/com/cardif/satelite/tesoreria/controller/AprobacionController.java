package com.cardif.satelite.tesoreria.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.satelite.tesoreria.model.Cheque;
import com.cardif.satelite.tesoreria.model.Firmante;
import com.cardif.satelite.tesoreria.service.ChequeService;
import com.cardif.satelite.util.SateliteUtil;

@Controller
@Scope("request")
public class AprobacionController extends BaseController implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final Logger LOGGER = Logger.getLogger(AprobacionController.class);
  private List<Cheque> chequesRechazados;
  private List<Cheque> chequesPendientes;
  private List<Cheque> chequesAprobar;
  private List<Cheque> chequesRechazar;
  private Boolean mostrar;
  private String textoAprobacion;
  private String textoRechazo;
  private Firmante firmante;
  private String motivoRechazo;

  @Autowired
  private ChequeService chequeService;

  @Override
  @PostConstruct
  public String inicio() {
    if (tieneAcceso()) {
      LOGGER.info("Inicio");

      String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
      firmante = chequeService.buscarFirmante(usuario);
      chequesPendientes = chequeService.buscarChequesPendientes(firmante);
      chequesRechazados = chequeService.buscarChequesRechazados(firmante);
      mostrar = Boolean.FALSE;
      LOGGER.info("Fin");
    }
    return null;
  }

  public String prepararAprobacion() {
    mostrar = Boolean.FALSE;
    chequesAprobar = chequeService.filtrarSeleccionado(chequesPendientes);
    int size = chequesAprobar.size();
    LOGGER.info("Número de cheques seleccionados: " + size);
    if (size == 0) {
      SateliteUtil.mostrarMensaje("Debe seleccionar al menos un cheque");
    } else if (size == 1) {
      mostrar = Boolean.TRUE;
      Cheque cheque = chequesAprobar.get(0);
      StringBuilder sb = new StringBuilder();
      sb.append("El cheque con N° ");
      sb.append(cheque.getNumero());
      sb.append(" para ");
      sb.append(cheque.getNombreBeneficiario());
      sb.append(" por el monto de ");
      sb.append(cheque.getTipoMoneda());
      sb.append(" ");
      sb.append(cheque.getImporte());
      sb.append(" será APROBADO. ¿Está seguro que desea continuar con la operación?");
      textoAprobacion = sb.toString();
    } else {
      mostrar = Boolean.TRUE;
      StringBuilder sb = new StringBuilder();
      sb.append("Usted va a aprobar ");
      sb.append(size);
      sb.append(" cheques. ¿Está seguro que desea continuar con la operación?");
      textoAprobacion = sb.toString();
    }
    return null;
  }

  public String prepararRechazo() {
    mostrar = Boolean.FALSE;
    chequesRechazar = chequeService.filtrarSeleccionado(chequesPendientes);
    int size = chequesRechazar.size();
    LOGGER.info("Número de cheques seleccionados: " + size);
    if (size != 1) {
      SateliteUtil.mostrarMensaje("Debe seleccionar un cheque");
    } else {
      mostrar = Boolean.TRUE;
      Cheque cheque = chequesRechazar.get(0);
      StringBuilder sb = new StringBuilder();
      sb.append("El cheque con N° ");
      sb.append(cheque.getNumero());
      sb.append(" para ");
      sb.append(cheque.getNombreBeneficiario());
      sb.append(" por el monto de ");
      sb.append(cheque.getTipoMoneda());
      sb.append(" ");
      sb.append(cheque.getImporte());
      sb.append(" será RECHAZADO.\nPara poder rechazar debe ingresar un motivo de rechazo. \n\n");
      textoRechazo = sb.toString();
    }
    return null;
  }

  public String aprobar() {
    String mensaje = chequeService.aprobarCheques(chequesAprobar, firmante);
    chequesPendientes = chequeService.buscarChequesPendientes(firmante);
    SateliteUtil.mostrarMensaje(mensaje);

    return null;

  }

  public String rechazar() {
    String usuario = SecurityContextHolder.getContext().getAuthentication().getName();

    StringBuilder sb = new StringBuilder();
    sb.append(usuario);
    sb.append(": ");
    sb.append(motivoRechazo);

    String mensaje = chequeService.rechazarCheques(chequesRechazar, firmante, sb.toString());
    chequesPendientes = chequeService.buscarChequesPendientes(firmante);
    chequesRechazados = chequeService.buscarChequesRechazados(firmante);
    SateliteUtil.mostrarMensaje(mensaje);
    motivoRechazo = null;
    return null;
  }

  public List<Cheque> getChequesRechazados() {
    return chequesRechazados;
  }

  public void setChequesRechazados(List<Cheque> chequesRechazados) {
    this.chequesRechazados = chequesRechazados;
  }

  public List<Cheque> getChequesPendientes() {
    return chequesPendientes;
  }

  public void setChequesPendientes(List<Cheque> chequesPendientes) {
    this.chequesPendientes = chequesPendientes;
  }

  public List<Cheque> getChequesAprobar() {
    return chequesAprobar;
  }

  public void setChequesAprobar(List<Cheque> chequesAprobar) {
    this.chequesAprobar = chequesAprobar;
  }

  public List<Cheque> getChequesRechazar() {
    return chequesRechazar;
  }

  public void setChequesRechazar(List<Cheque> chequesRechazar) {
    this.chequesRechazar = chequesRechazar;
  }

  public Boolean getMostrar() {
    return mostrar;
  }

  public void setMostrar(Boolean mostrar) {
    this.mostrar = mostrar;
  }

  public String getTextoAprobacion() {
    return textoAprobacion;
  }

  public void setTextoAprobacion(String textoAprobacion) {
    this.textoAprobacion = textoAprobacion;
  }

  public String getTextoRechazo() {
    return textoRechazo;
  }

  public void setTextoRechazo(String textoRechazo) {
    this.textoRechazo = textoRechazo;
  }

  public Firmante getFirmante() {
    return firmante;
  }

  public void setFirmante(Firmante firmante) {
    this.firmante = firmante;
  }

  public String getMotivoRechazo() {
    return motivoRechazo;
  }

  public void setMotivoRechazo(String motivoRechazo) {
    this.motivoRechazo = motivoRechazo;
  }

}
