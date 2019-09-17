package com.cardif.satelite.cobranza.service;

import java.util.ArrayList;
import java.util.Date;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.cobranza.bean.ConsultaProceso;

public interface ProcesoService {

  public ArrayList<ConsultaProceso> consultar(String tipo, String estado, Date fecIni, Date fecFin) throws SyncconException;

}
