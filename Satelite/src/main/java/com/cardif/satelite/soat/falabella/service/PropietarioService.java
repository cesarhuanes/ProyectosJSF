package com.cardif.satelite.soat.falabella.service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.callcenter.bean.ConsultaRegPropietario;
import com.cardif.satelite.soat.model.Propietario;

public interface PropietarioService {

  public Propietario obtenerPropietario(String dni) throws SyncconException;

  public ConsultaRegPropietario obtener(String dni) throws SyncconException;

  public int insertar(Propietario propietario) throws SyncconException;

  public boolean actualizar(Propietario propietario) throws SyncconException;
}
