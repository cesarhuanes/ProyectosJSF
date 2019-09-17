package com.cardif.satelite.siniestro.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.SiniRentaHosp;

public interface SiniRentaHospService {
  SiniRentaHosp listar(String nroSiniestro) throws SyncconException;

  List<SiniRentaHosp> listar() throws SyncconException;

  public int actualizarRentaHosp(SiniRentaHosp siniRentaHosp) throws SyncconException;

  public int insertarSiniRentaHosp(SiniRentaHosp siniRentaHosp) throws SyncconException;

  public int eliminarSiniRentaHosp(String siniRentaHosp) throws SyncconException;
}
