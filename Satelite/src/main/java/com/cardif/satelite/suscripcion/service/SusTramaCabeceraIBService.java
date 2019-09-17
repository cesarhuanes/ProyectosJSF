package com.cardif.satelite.suscripcion.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.SusTramaCabeceraIB;

public interface SusTramaCabeceraIBService {
  public List<SusTramaCabeceraIB> listarAnulacion() throws SyncconException;

  public int insertarDatosTxt(SusTramaCabeceraIB susTramaCabeceraIB) throws SyncconException;

  public SusTramaCabeceraIB obtener(Long codTrama) throws SyncconException;

  public SusTramaCabeceraIB obtener(String periodo) throws SyncconException;

  public int obtenerNroCabeceras() throws SyncconException;

  public int procesarMes(String periodo) throws SyncconException;

  public int procesarMesCerrado(Long codTrama) throws SyncconException;

  public int actualizarCabecera(SusTramaCabeceraIB susTramaCabeceraIB) throws SyncconException;

}
