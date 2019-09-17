package com.cardif.satelite.actuarial.service;

import java.util.Date;
import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.actuarial.bean.ConsultaActReaseguro;
import com.cardif.satelite.model.ActReaseguro;

public interface ActReaseguroService {

  public void insertar(ActReaseguro actReaseguro) throws SyncconException;

  public List<ConsultaActReaseguro> buscar(Long codSistema, String codSocio, Long codProducto) throws SyncconException;

  public Date fechaMax(Long codSistema, String codSocio, Long codProducto) throws SyncconException;

  public Date fechaMaxAnt(Long codSistema, String codSocio, Long codProducto, Date fecFinReaseguro) throws SyncconException;

  public ActReaseguro obtener(Long codReaseguro) throws SyncconException;

  public void actualizar(ActReaseguro actReaseguro) throws SyncconException;

  public void eliminar(Long codReaseguro) throws SyncconException;

}
