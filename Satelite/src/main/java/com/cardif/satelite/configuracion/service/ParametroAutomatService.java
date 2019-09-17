package com.cardif.satelite.configuracion.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.ParametroAutomat;

public interface ParametroAutomatService
{
	
	public List<ParametroAutomat> buscarPorCodParam(String codParam) throws SyncconException;
	
	public ParametroAutomat obtener(String codParam, String codValor) throws SyncconException;
	
	public void actualizar(ParametroAutomat parametro) throws SyncconException;
	
	public void actualizar(String codParam, String codValor, String nombreValor, Integer numOrden) throws SyncconException;
	
} //ParametroAutomatService
