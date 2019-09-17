package com.cardif.satelite.suscripcion.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.SusTramaInterbank;
import com.cardif.satelite.model.SusTramaInterbankExample;
import com.cardif.satelite.suscripcion.bean.SusTramaExportarReporte;

public interface SusTramaInterbankService {

  public void ingresa(SusTramaInterbank susTramaInterbank) throws SyncconException;

  public List<SusTramaInterbank> listarAnulacion() throws SyncconException;

  public List<SusTramaInterbank> obtenerLista(long codTrama) throws SyncconException;

  public List<SusTramaExportarReporte> listaReporteEnviados(Long codTrama) throws SyncconException;

  public void actualizar(SusTramaInterbank susTramaInterbank, SusTramaInterbankExample example) throws SyncconException;

  public void actualizarRegistrosMultiMes(SusTramaInterbank susTramaInterbank) throws SyncconException;

}
