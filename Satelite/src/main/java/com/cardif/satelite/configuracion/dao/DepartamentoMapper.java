package com.cardif.satelite.configuracion.dao;

import java.util.List;

import com.cardif.satelite.model.Departamento;

public interface DepartamentoMapper
{
	
	public List<Departamento> selectAllDepartamentos();
	
	public Departamento selectByPrimaryKey(String codDepartamento);
	
} //DepartamentoMapper
