package com.cardif.satelite.telemarketing.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.Layout;
import com.cardif.satelite.model.TlmkBaseCabecera;
import com.cardif.satelite.suscripcion.bean.ConfLayout;
import com.cardif.satelite.suscripcion.bean.TramaDiariaBean;

public interface BaseCabeceraService {

  public void insertar(TlmkBaseCabecera tlmkBaseCabecera) throws SyncconException;

  public void actualizar(TlmkBaseCabecera tlmkBaseCabecera) throws SyncconException;

  public int codBaseMax(String codSocio) throws SyncconException;

  public String buscarEstado(Long codBase) throws SyncconException;

  public List<TlmkBaseCabecera> buscar(String codSocio) throws SyncconException;

  public TlmkBaseCabecera obtener(Long codBase) throws SyncconException;

  public List<TramaDiariaBean> obtenerTabla() throws SyncconException;

  public List<ConfLayout> obtenerCabeceraExcel(Integer idproducto) throws SyncconException;

  public Boolean actualizarLayout(List<Layout> listaLayout) throws SyncconException;

  public List<Layout> obtenerCabeceraExcelAll(Integer idproducto) throws SyncconException;

  public List<TramaDiariaBean> selectTramaDiaraTemp() throws SyncconException;

}
