package com.cardif.satelite.telemarketing.service;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.TlmkBaseSB;

public interface BaseSBService {

  public void insertar(TlmkBaseSB tlmkBaseSB) throws SyncconException;

  public void insertarDatosExcel(File excelFile, Long codBase, LinkedHashMap<String, String> mapExcel) throws SyncconException;

  public int codBaseMaxSB() throws SyncconException;

  public List<TlmkBaseSB> buscar(Long codBase, String estado) throws SyncconException;

  public int cantClientesProcesados(Long codBase, String estado) throws SyncconException;

}
