package com.cardif.satelite.soat.service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.soat.model.BitacoraSoat;

public interface BitacoraSoatService {

  public int insertar(BitacoraSoat bitacoraSoat) throws SyncconException;
}
