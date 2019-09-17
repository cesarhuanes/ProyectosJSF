package com.cardif.satelite.tesoreria.controller;

import java.io.Serializable;
import java.util.ArrayList;
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
import com.cardif.satelite.tesoreria.dao.FirmanteParMapper;
import com.cardif.satelite.tesoreria.model.Cheque;
import com.cardif.satelite.tesoreria.service.ChequeService;
import com.cardif.satelite.util.SateliteConstants;
import com.cardif.satelite.util.SateliteUtil;

@Controller
@Scope("request")
public class ImpresionController extends BaseController implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final Logger LOGGER = Logger.getLogger(ImpresionController.class);

  @Autowired
  private ChequeService chequeService;

  @Autowired
  private FirmanteParMapper firmanteParMapper;

  private List<Cheque> cheques;

  private Cheque chequeSeleccionado;

  private Boolean puedeAnular;
  private Boolean puedeReactivar;
  private Boolean puedeReasignar;
  private Boolean puedeImprimir;

  private String numero;
  private Integer total;
  private Integer parAnulado;
  private List<Cheque> paresActivos;
  private List<SelectItem> pares;
  private List<Cheque> seleccionados;
  private Long codigoPar;
  private String estado;
  private List<SelectItem> estados;

  @Override
  @PostConstruct
  public String inicio() {

    LOGGER.info("Inicio");
    cheques = chequeService.buscarCheques(estado);
    LOGGER.info("Fin");
    puedeAnular = Boolean.FALSE;
    puedeReactivar = Boolean.FALSE;
    puedeReasignar = Boolean.FALSE;
    puedeImprimir = Boolean.FALSE;
    this.pares = new ArrayList<SelectItem>();
    this.estados = new ArrayList<SelectItem>();
    this.estados.add(new SelectItem(null, "Seleccionar estado"));
    this.estados.add(new SelectItem("A", "Aprobado"));
    this.estados.add(new SelectItem("P", "Pendiente"));
    this.estados.add(new SelectItem("R", "Rechazado"));

    return null;
  }

  public String buscarCheques() {
    cheques = chequeService.buscarCheques(estado);
    return null;
  }

  private List<Cheque> buscarSeleccionados() {
    List<Cheque> seleccionados = new ArrayList<Cheque>();
    for (Cheque cheque : cheques) {
      if (cheque.getSeleccionado() != null && cheque.getSeleccionado()) {
        seleccionados.add(cheque);
      }
    }
    return seleccionados;
  }

  private List<Cheque> buscarAprobados() {
    List<Cheque> seleccionados = new ArrayList<Cheque>();
    for (Cheque cheque : cheques) {
      if (cheque.getSeleccionado() != null && cheque.getSeleccionado()) {
        if (cheque.getEstadoCheque().equalsIgnoreCase(SateliteConstants.ESTADO_APROBADO)) {
          seleccionados.add(cheque);
        } else {
          return new ArrayList<Cheque>();
        }
      }
    }
    return seleccionados;
  }

  private String buscarParesActivos() {
    Boolean Inactivos = false;
    String mensajeInactivos = "";
    String mensajePrevio = "El cheque ";
    String mensajePost = " se encuentra aprobado por un par no Activo";
//    List<Cheque> paresActivos = new ArrayList<Cheque>();
    for (Cheque cheque : cheques) {
      if (cheque.getSeleccionado() != null && cheque.getSeleccionado()) {
        Long numPar = cheque.getCodigoPar();
        FirmantePar firmantePar = firmanteParMapper.obtenerPar(numPar);
        if (firmantePar == null)
          if (mensajeInactivos.length() == 0) {
            mensajeInactivos = cheque.getNumero().toString();
            Inactivos = true;
          } else {
            mensajeInactivos = mensajeInactivos + " , " + cheque.getNumero().toString();
            mensajePrevio = "Los cheques ";
            mensajePost = " se encuentran aprobados por pares no Activos";
          }
      }
    }
    if (Inactivos)
      return mensajePrevio + mensajeInactivos + mensajePost;
    else
      return "";
  }

  public String prepararReactivar() {
    puedeReactivar = Boolean.FALSE;
    List<Cheque> seleccionados = buscarSeleccionados();
    if (seleccionados.size() != 1) {
      SateliteUtil.mostrarMensaje("Debe seleccionar un cheque");
    } else {
      chequeSeleccionado = seleccionados.get(0);
      if (SateliteConstants.ESTADO_RECHAZADO.equals(chequeSeleccionado.getEstadoCheque())) {
        puedeReactivar = Boolean.TRUE;

      } else {
        SateliteUtil.mostrarMensaje("Debe seleccionar un cheque en estado RECHAZADO");

      }

    }
    return null;
  }

  public String prepararReasignar() {
    puedeReasignar = Boolean.FALSE;

    List<Cheque> seleccionados = buscarSeleccionados();
    if (seleccionados.size() != 1) {
      SateliteUtil.mostrarMensaje("Debe seleccionar un cheque");
    } else {
      chequeSeleccionado = seleccionados.get(0);
      if (SateliteConstants.ESTADO_PENDIENTE.equals(chequeSeleccionado.getEstadoCheque())) {
        puedeReasignar = Boolean.TRUE;

        List<FirmantePar> pares = chequeService.buscarPares(chequeSeleccionado);

        this.pares = new ArrayList<SelectItem>();
        for (FirmantePar par : pares) {
          this.pares.add(new SelectItem(par.getCodigo(), par.getFirmante1() + " - " + par.getFirmante2()));
        }

        chequeSeleccionado.setMotivoReasignacion("");

      } else {
        SateliteUtil.mostrarMensaje("Debe seleccionar un cheque en estado PENDIENTE");

      }
    }
    return null;
  }

  public String prepararAnular() {
    puedeAnular = Boolean.FALSE;

    List<Cheque> seleccionados = buscarSeleccionados();
    if (seleccionados.size() != 1) {
      SateliteUtil.mostrarMensaje("Debe seleccionar un cheque");
    } else {
      chequeSeleccionado = seleccionados.get(0);
      puedeAnular = Boolean.TRUE;

    }

    return null;
  }

  public String prepararImprimir() {
    puedeImprimir = Boolean.FALSE;
    String mensaje = "";

    seleccionados = buscarAprobados();
    total = seleccionados.size();

    if (total < 1) {
      SateliteUtil.mostrarMensaje("Debe seleccionar cheques en estado APROBADO");
    } else {
      // Validando que el Par que aprobó aún se encuentre activo
      mensaje = buscarParesActivos();
      if (mensaje != "") {
        SateliteUtil.mostrarMensaje(mensaje);
      } else {
        puedeImprimir = Boolean.TRUE;
      }
    }
    return null;
  }

  public String reactivar() {
    String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
    chequeService.reactivarCheque(chequeSeleccionado, usuario);

    SateliteUtil.mostrarMensaje("El cheque ha sido reactivado");
    cheques = chequeService.buscarCheques(estado);
    return null;
  }

  public String reasignar() {
    String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
    chequeSeleccionado.setCodigoPar(codigoPar);
    chequeService.reasignarCheque(chequeSeleccionado, usuario);

    SateliteUtil.mostrarMensaje("El cheque ha sido reasignado");

    cheques = chequeService.buscarCheques(estado);
    return null;
  }

  public String anular() {

    String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
    chequeService.anularCheque(chequeSeleccionado, usuario);
    SateliteUtil.mostrarMensaje("El cheque ha sido anulado");
    cheques = chequeService.buscarCheques(estado);

    return null;
  }

  public String imprimir() {

    String usuario = SecurityContextHolder.getContext().getAuthentication().getName();

    chequeService.generarPrevisualizacion(seleccionados, null, Boolean.TRUE);

    chequeService.imprimirCheques(seleccionados, usuario);

    SateliteUtil.mostrarMensaje("Los cheques se estan imprimiendo");

    cheques = chequeService.buscarCheques(estado);
    return null;
  }

  public List<Cheque> getCheques() {
    return cheques;
  }

  public void setCheques(List<Cheque> cheques) {
    this.cheques = cheques;
  }

  public ChequeService getChequeService() {
    return chequeService;
  }

  public void setChequeService(ChequeService chequeService) {
    this.chequeService = chequeService;
  }

  public Boolean getPuedeAnular() {
    return puedeAnular;
  }

  public void setPuedeAnular(Boolean puedeAnular) {
    this.puedeAnular = puedeAnular;
  }

  public Boolean getPuedeReactivar() {
    return puedeReactivar;
  }

  public void setPuedeReactivar(Boolean puedeReactivar) {
    this.puedeReactivar = puedeReactivar;
  }

  public Boolean getPuedeReasignar() {
    return puedeReasignar;
  }

  public void setPuedeReasignar(Boolean puedeReasignar) {
    this.puedeReasignar = puedeReasignar;
  }

  public String getNumero() {
    return numero;
  }

  public void setNumero(String numero) {
    this.numero = numero;
  }

  public Cheque getChequeSeleccionado() {
    return chequeSeleccionado;
  }

  public void setChequeSeleccionado(Cheque chequeSeleccionado) {
    this.chequeSeleccionado = chequeSeleccionado;
  }

  public List<SelectItem> getPares() {
    return pares;
  }

  public void setPares(List<SelectItem> pares) {
    this.pares = pares;
  }

  public Integer getTotal() {
    return total;
  }

  public void setTotal(Integer total) {
    this.total = total;
  }

  public Boolean getPuedeImprimir() {
    return puedeImprimir;
  }

  public void setPuedeImprimir(Boolean puedeImprimir) {
    this.puedeImprimir = puedeImprimir;
  }

  public List<Cheque> getSeleccionados() {
    return seleccionados;
  }

  public void setSeleccionados(List<Cheque> seleccionados) {
    this.seleccionados = seleccionados;
  }

  public Long getCodigoPar() {
    return codigoPar;
  }

  public void setCodigoPar(Long codigoPar) {
    this.codigoPar = codigoPar;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public List<SelectItem> getEstados() {
    return estados;
  }

  public void setEstados(List<SelectItem> estados) {
    this.estados = estados;
  }

  public Integer getParAnulado() {
    return parAnulado;
  }

  public void setParAnulado(Integer parAnulado) {
    this.parAnulado = parAnulado;
  }

  public List<Cheque> getParesActivos() {
    return paresActivos;
  }

  public void setParesActivos(List<Cheque> paresActivos) {
    this.paresActivos = paresActivos;
  }

}
