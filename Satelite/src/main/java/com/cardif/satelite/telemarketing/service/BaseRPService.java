package com.cardif.satelite.telemarketing.service;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.TlmkBaseRP;

public interface BaseRPService {

  public void insertar(TlmkBaseRP tlmkBaseRP) throws SyncconException;

  public void insertarDatosExcel(File excelFile, Long codBase, LinkedHashMap<String, String> mapExcel) throws SyncconException;

  public int codBaseMaxRP() throws SyncconException;

  public List<TlmkBaseRP> buscar(Long codBase, String estado) throws SyncconException;

  public int cantClientesProcesados(Long codBase, String estado) throws SyncconException;

}
