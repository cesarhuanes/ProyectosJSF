package com.cardif.satelite.configuracion.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.CategoriaClase;

public interface CategoriaClaseService
{
	
	public List<CategoriaClase> buscarTodos() throws SyncconException;
	
	public CategoriaClase buscarByPK(String id) throws SyncconException;
	
	public CategoriaClase buscarByDescripcion(String descripcion) throws SyncconException;
	
} //CategoriaClaseService
