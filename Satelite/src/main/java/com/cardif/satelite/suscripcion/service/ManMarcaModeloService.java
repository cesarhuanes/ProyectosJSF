package com.cardif.satelite.suscripcion.service;

import java.util.Map;

import com.cardif.framework.excepcion.SyncconException;

public interface ManMarcaModeloService
{
	
	public abstract boolean verificarMarcaVehiculo(String nombreMarcaVehiculo) throws SyncconException;
	
	public abstract boolean verificarModeloVehiculo(Object marcaVehiculoObj, String nombreModeloVehiculo) throws SyncconException;
	
	public abstract Map<String, Long> registrarMarcaModeloVehiculo(String codCategoriaClase, String nomMarcaVehiculo, String nomModeloVehiculo) throws SyncconException;
	
	public abstract Long registrarModeloVehiculo(String codCategoriaClase, Long codMarcaVehiculo, String nomModeloVehiculo) throws SyncconException;
	
	public abstract Map<String, Boolean> validacionMarca(String codCategoriaClase, String codMarcaVehiculo);
	
	public abstract Map<String, Boolean> validacionModelo(String nombreModelo, String nombreMarca);

} //ManMarcaModeloService
