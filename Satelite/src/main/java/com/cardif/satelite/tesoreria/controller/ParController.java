package com.cardif.satelite.tesoreria.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.richfaces.model.selection.SimpleSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.satelite.model.FirmantePar;
import com.cardif.satelite.tesoreria.model.Firmante;
import com.cardif.satelite.tesoreria.service.FirmanteService;
import com.cardif.satelite.util.SateliteUtil;

@Controller
@Scope("request")
public class ParController extends BaseController implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final Logger LOGGER = Logger.getLogger(ParController.class);
  private Long firmante1;
  private Long firmante2;
  private List<SelectItem> firmantes;
  private List<FirmantePar> pares;
  private SimpleSelection selection;
  private FirmantePar par;

  @Autowired
  private FirmanteService firmanteService;

  @Override
  @PostConstruct
  public String inicio() {

    LOGGER.info("Inicio");
    this.firmantes = new ArrayList<SelectItem>();
    this.firmantes.add(new SelectItem(null, "Seleccionar"));
    List<Firmante> firmantes = firmanteService.buscarFirmantes();

    for (Firmante firmante : firmantes) {
      this.firmantes.add(new SelectItem(firmante.getCodigo(), firmante.getNombres() + " " + firmante.getApellidos()));
    }
    pares = firmanteService.buscarPares();
    LOGGER.info("Fin");

    return null;
  }

  public void registrarPar(ActionEvent event) {

    String mensaje = firmanteService.registrarPar(firmante1, firmante2);
    pares = firmanteService.buscarPares();
    SateliteUtil.mostrarMensaje(mensaje);
    selection = null;

  }

  public void eliminarPar(ActionEvent event) {

    if (par != null && par.getCodigo() != null) {
      String mensaje = firmanteService.eliminarPar(par);
      pares = firmanteService.buscarPares();
      SateliteUtil.mostrarMensaje(mensaje);
      selection = null;
    }

  }

  public void seleccionarPar() {

    par = (FirmantePar) SateliteUtil.verSeleccionado(selection, pares);

  }

  public Long getFirmante1() {
    return firmante1;
  }

  public void setFirmante1(Long firmante1) {
    this.firmante1 = firmante1;
  }

  public Long getFirmante2() {
    return firmante2;
  }

  public void setFirmante2(Long firmante2) {
    this.firmante2 = firmante2;
  }

  public List<SelectItem> getFirmantes() {
    return firmantes;
  }

  public void setFirmantes(List<SelectItem> firmantes) {
    this.firmantes = firmantes;
  }

  public List<FirmantePar> getPares() {
    return pares;
  }

  public void setPares(List<FirmantePar> pares) {
    this.pares = pares;
  }

  public SimpleSelection getSelection() {
    return selection;
  }

  public void setSelection(SimpleSelection selection) {
    this.selection = selection;
  }

  public FirmantePar getPar() {
    return par;
  }

  public void setPar(FirmantePar par) {
    this.par = par;
  }

  public FirmanteService getFirmanteService() {
    return firmanteService;
  }

  public void setFirmanteService(FirmanteService firmanteService) {
    this.firmanteService = firmanteService;
  }

}
