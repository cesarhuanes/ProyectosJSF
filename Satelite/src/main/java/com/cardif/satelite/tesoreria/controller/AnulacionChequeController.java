package com.cardif.satelite.tesoreria.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.richfaces.model.selection.SimpleSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.cardif.satelite.tesoreria.model.Cheque;
import com.cardif.satelite.tesoreria.service.ChequeService;
import com.cardif.satelite.tesoreria.service.ReporteImpresionService;
import com.cardif.satelite.util.SateliteUtil;

@Controller("anulacionChequeController")
@Scope("request")
public class AnulacionChequeController {
  private String nro_cheque;

  @Autowired
  private ReporteImpresionService reporteImpresionService;

  @Autowired
  private ChequeService chequeService;

  private List<Cheque> listaCheques = new ArrayList<Cheque>();
  public static final Logger log = Logger.getLogger(AnulacionChequeController.class);

  private boolean desabilitarAnula = true;
  private SimpleSelection selection;
  private Cheque chequeActual;
  private boolean puedeAnular = false;
  private String motivo;

  @PostConstruct
  public void inicio() {
    log.info("INICIO");
    selection = new SimpleSelection();
  }

  public String buscar() {
    listaCheques.clear();
    desabilitarAnula = false;
    if (nro_cheque.length() == 0) {
      nro_cheque = null;
    }
    listaCheques = reporteImpresionService.selectChequeByNro(nro_cheque);
    nro_cheque = "";
    System.out.println("Lista size: " + listaCheques.size());

    return null;
  }

  public String anular() {

    String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
    chequeActual.setMotivoAnulacion(motivo);
    chequeService.anularCheque(chequeActual, usuario);

    puedeAnular = false;

    motivo = "";
    chequeActual = new Cheque();

    SateliteUtil.mostrarMensaje("El cheque ha sido anulado");
    buscar();

    selection = null;
    return null;
  }

  public void onSelectionChanged() {

    if (selection != null) {
      Iterator<Object> it = selection.getKeys();
      while (it.hasNext()) {
        int key = Integer.parseInt(it.next().toString());
        System.out.println("key: " + key);
        chequeActual = listaCheques.get(key);
      }
      puedeAnular = true;
      desabilitarAnula = false;
      System.out.println("Nuevo Cod Cheque: " + chequeActual.getCodigo());
    } else {
      System.out.println("No selection is set.");
    }
  }

  public String getNro_cheque() {
    return nro_cheque;
  }

  public void setNro_cheque(String nro_cheque) {
    this.nro_cheque = nro_cheque;
  }

  public Cheque getChequeActual() {
    return chequeActual;
  }

  public void setChequeActual(Cheque chequeActual) {
    this.chequeActual = chequeActual;
  }

  public SimpleSelection getSelection() {
    return selection;
  }

  public void setSelection(SimpleSelection selection) {
    this.selection = selection;
  }

  public boolean isDesabilitarAnula() {
    return desabilitarAnula;
  }

  public List<Cheque> getListaCheques() {
    return listaCheques;
  }

  public void setListaCheques(List<Cheque> listaCheques) {
    this.listaCheques = listaCheques;
  }

  public void setDesabilitarAnula(boolean desabilitarAnula) {
    this.desabilitarAnula = desabilitarAnula;
  }

  public boolean isPuedeAnular() {
    return puedeAnular;
  }

  public void setPuedeAnular(boolean puedeAnular) {
    this.puedeAnular = puedeAnular;
  }

  public String getMotivo() {
    return motivo;
  }

  public void setMotivo(String motivo) {
    this.motivo = motivo;
  }
}
