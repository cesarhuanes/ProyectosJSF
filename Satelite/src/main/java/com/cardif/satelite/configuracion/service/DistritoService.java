package com.cardif.satelite.configuracion.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.Distrito;

public interface DistritoService
{
	
	public List<Distrito> buscarDistritos() throws SyncconException;
	
	public Distrito buscarByPK(String codDistrito) throws SyncconException;
	
	public List<Distrito> buscarByCodProvincia(String codProvincia) throws SyncconException;
	
} //DistritoService
