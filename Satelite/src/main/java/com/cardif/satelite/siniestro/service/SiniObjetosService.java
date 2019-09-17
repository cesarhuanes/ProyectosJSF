package com.cardif.satelite.siniestro.service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.SiniObjetos;

public interface SiniObjetosService {

  SiniObjetos obtener(int codObjeto) throws SyncconException;
}
