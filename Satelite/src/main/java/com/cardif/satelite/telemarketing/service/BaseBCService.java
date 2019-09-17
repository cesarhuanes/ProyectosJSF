package com.cardif.satelite.telemarketing.service;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.TlmkBaseBC;
import com.cardif.satelite.model.TlmkBaseBCExample;

public interface BaseBCService {

  public void insertar(TlmkBaseBC tlmkBaseBC) throws SyncconException;

  public void insertarDatosExcel(File excelFile, Long codBase, LinkedHashMap<String, String> mapExcel) throws SyncconException;

  public int codBaseMaxBC() throws SyncconException;

  public List<TlmkBaseBC> buscar(Long codBase, String estado) throws SyncconException;

  public int cantClientesProcesados(Long codBase, String estado) throws SyncconException;

  public void actualizar(TlmkBaseBC tlmkBaseBC, TlmkBaseBCExample example) throws SyncconException;

}
