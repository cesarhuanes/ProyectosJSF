package com.cardif.satelite.configuracion.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.Departamento;

public interface DepartamentoService
{
	
	public List<Departamento> buscarDepartamentos() throws SyncconException;
	
	public Departamento buscarByPK(String codDepartamento) throws SyncconException;
	
} //DepartamentoService
