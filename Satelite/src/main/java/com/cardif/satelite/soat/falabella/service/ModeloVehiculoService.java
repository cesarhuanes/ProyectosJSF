package com.cardif.satelite.soat.falabella.service;

import java.math.BigDecimal;
import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.soat.model.ModeloVehiculo;

public interface ModeloVehiculoService {

  public List<ModeloVehiculo> buscar(String codMarca) throws SyncconException;

  public ModeloVehiculo obtener(String codModelo) throws SyncconException;

  public List<ModeloVehiculo> buscarModelo(BigDecimal codMarca) throws SyncconException;

}
