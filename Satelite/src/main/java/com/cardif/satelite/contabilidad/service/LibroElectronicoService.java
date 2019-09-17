package com.cardif.satelite.contabilidad.service;

import java.io.File;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.CabLibro;

public interface LibroElectronicoService {

  public void insertar(CabLibro cabLibro) throws SyncconException;

  public void transformarExcel(CabLibro cabLibro, File txtFile, File excelFile) throws SyncconException;

}
