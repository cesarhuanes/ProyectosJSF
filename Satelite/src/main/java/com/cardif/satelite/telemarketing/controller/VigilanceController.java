package com.cardif.satelite.telemarketing.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.ssis.service.SsisService;
import com.cardif.satelite.telemarketing.bean.BaseVigilance;
import com.cardif.satelite.telemarketing.service.VigilanceService;
import com.cardif.satelite.util.SateliteConstants;
import com.cardif.satelite.util.SateliteUtil;

@Controller("vigilanceController")
@Scope("request")
public class VigilanceController {
  public static final Logger LOGGER = Logger.getLogger(VigilanceController.class);
  private List<BaseVigilance> listaVigilance;
  @Autowired
  private VigilanceService vigilanceService;
  @Autowired
  private SsisService ssisService;

  @PostConstruct
  public String inicio() {
    LOGGER.info("Inicio");
    try {

      cargarDatos();

    } catch (SyncconException ex) {
      LOGGER.error("ERROR SYNCCON: " + ex.getMessageComplete());
      SateliteUtil.mostrarMensaje(ex.getSeveridad(), ex.getMessage());

    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      SateliteUtil.mostrarMensaje(FacesMessage.SEVERITY_WARN, ErrorConstants.MSJ_ERROR_GENERAL);
    }

    LOGGER.info("Fin");

    return null;
  }

  private void cargarDatos() throws SyncconException {
    listaVigilance = new ArrayList<BaseVigilance>();
    BaseVigilance baseVigilance = vigilanceService.getUltimoBaseVigilance();
    if (baseVigilance != null) {
      listaVigilance.add(baseVigilance);
    }
  }

  public String actualizar() {
    String respuesta = null;
    try {

      if (vigilanceService.algunaCargaVigilanceEstaPendiente()) {
        SateliteUtil.mostrarMensaje("No se pudo iniciar la carga porque otro usuario ya inicio el proceso");
        return respuesta;
      } else {

        vigilanceService.insertarBaseVigilance();
        ssisService.lanzarJob(Constantes.TLMK_JOB_VIGILANCE);
      }
      String codigoBase = vigilanceService.getCodigoBase();

      LOGGER.info("Inicio de carga de vigilance: " + new Date(System.currentTimeMillis()));
      while (SateliteConstants.PENDIENTE.equals(vigilanceService.buscarEstado(codigoBase))) {
        Thread.sleep(10000L);
      }
      LOGGER.info("Fin de carga de vigilance: " + new Date(System.currentTimeMillis()));

      vigilanceService.actualizarBaseVigilance(codigoBase);
      cargarDatos();

      SateliteUtil.mostrarMensaje("La carga de Vigilance ha finalizado");

    } catch (SyncconException ex) {
      LOGGER.error("ERROR SYNCCON: " + ex.getMessageComplete());
      SateliteUtil.mostrarMensaje(ex.getSeveridad(), ex.getMessage());

    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
      SateliteUtil.mostrarMensaje(FacesMessage.SEVERITY_WARN, ErrorConstants.MSJ_ERROR_GENERAL);
    }
    LOGGER.info("Fin");
    return respuesta;

  }

  public List<BaseVigilance> getListaVigilance() {
    return listaVigilance;
  }

  public void setListaVigilance(List<BaseVigilance> listaVigilance) {
    this.listaVigilance = listaVigilance;
  }

}
