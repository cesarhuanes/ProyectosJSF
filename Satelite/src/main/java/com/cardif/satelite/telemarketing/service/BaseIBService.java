package com.cardif.satelite.telemarketing.service;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.TlmkBaseIB;

public interface BaseIBService {

  public void insertar(TlmkBaseIB tlmkBaseIB) throws SyncconException;

  public void insertarDatosExcel(File excelFile, Long codBase, LinkedHashMap<String, String> mapExcel) throws SyncconException;

  public int codBaseMax() throws SyncconException;

  public List<TlmkBaseIB> buscar(Long codBase, String estado) throws SyncconException;

  public List<TlmkBaseIB> buscarParcial(Long codBase, String estado, int inicio, int cantidad) throws SyncconException;

  public int cantClientesProcesados(Long codBase, String estado) throws SyncconException;

}
