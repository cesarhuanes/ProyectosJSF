package com.cardif.satelite.suscripcion.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.satelite.ConciCabReporte;
import com.cardif.satelite.model.satelite.ConciReporte;
import com.cardif.satelite.model.satelite.ConciTramaMensual;

public interface RepConciliacionService {
  public void procesarConciliacion(ConciCabReporte conciCabReporte) throws SyncconException;

  public List<ConciCabReporte> listaResumenConciliacion(String producto) throws SyncconException;

  public List<ConciReporte> listaReporteConciliacion(Integer periodo, String producto) throws SyncconException;

  public List<ConciTramaMensual> listaTramaMensual(Integer periodo, String producto) throws SyncconException;

  public List<ConciTramaMensual> listaTramaTXT(Integer periodo, String producto) throws SyncconException;

  public boolean verificarMaster(Integer periodo, String socio) throws SyncconException;
}
