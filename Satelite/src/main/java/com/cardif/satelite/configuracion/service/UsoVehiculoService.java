package com.cardif.satelite.configuracion.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.UsoVehiculo;

public interface UsoVehiculoService
{
	
	public UsoVehiculo buscarByPK(String codUso) throws SyncconException;
	
	/**
	 * Este metodo busca todos los registros de la tabla USO_VEHICULO.
	 * 
	 * @return
	 * 		Retorna una lista con los registros de la table USO_VEHICULO.
	 * @throws SyncconException
	 */
	public List<UsoVehiculo> buscarUsoVehiculos() throws SyncconException;
	
} //UsoVehiculoService
