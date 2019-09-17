package com.cardif.satelite.configuracion.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.ModeloVehiculo;

public interface ModeloVehiculoService
{
	
	public ModeloVehiculo buscarPorPK(Long codUso) throws SyncconException;

	public List<ModeloVehiculo> buscarPorMarcaVehiculoCategoriaClase(Long pkMarcaVehiculo) throws SyncconException;
	
}
