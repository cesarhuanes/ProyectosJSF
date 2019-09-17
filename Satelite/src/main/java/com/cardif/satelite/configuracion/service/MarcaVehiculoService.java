package com.cardif.satelite.configuracion.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.MarcaVehiculo;

public interface MarcaVehiculoService
{
	
	public MarcaVehiculo buscarPorPK(Long id) throws SyncconException;
	
	public List<MarcaVehiculo> buscarPorPkCategoriaClase(String pkCategoriaClase) throws SyncconException;
	
	
	
	
}
