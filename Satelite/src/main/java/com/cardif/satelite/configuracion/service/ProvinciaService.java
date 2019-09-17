package com.cardif.satelite.configuracion.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.Provincia;

public interface ProvinciaService
{
	
	public List<Provincia> buscarProvincias() throws SyncconException;
	
	public Provincia buscarByPK(String codProvincia) throws SyncconException;
	
	public List<Provincia> buscarByCodDepartamento(String codDepartamento) throws SyncconException;
	
} //ProvinciaService
