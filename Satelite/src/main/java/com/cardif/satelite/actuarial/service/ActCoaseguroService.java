package com.cardif.satelite.actuarial.service;

import java.util.Date;
import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.actuarial.bean.ConsultaActCoaseguro;
import com.cardif.satelite.model.ActCoaseguro;

public interface ActCoaseguroService {

  public void insertar(ActCoaseguro actCoaseguro) throws SyncconException;

  public List<ConsultaActCoaseguro> buscar(Long codSistema, String codSocio, Long codProducto) throws SyncconException;

  public Date fechaMax(Long codSistema, String codSocio, Long codProducto) throws SyncconException;

  public Date fechaMaxAnt(Long codSistema, String codSocio, Long codProducto, Date fecFinReaseguro) throws SyncconException;

  public ActCoaseguro obtener(Long codCoaseguro) throws SyncconException;

  public void actualizar(ActCoaseguro actCoaseguro) throws SyncconException;

  public void eliminar(Long codCoaseguro) throws SyncconException;

}
