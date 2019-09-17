package com.cardif.satelite.configuracion.dao;

import java.util.List;

import com.cardif.satelite.model.Provincia;

public interface ProvinciaMapper
{
	
	public List<Provincia> selectAllProvincias();
	
	public Provincia selectByPrimaryKey(String codProvincia);
	
	public List<Provincia> selectByCodDepartamento(String codDepartamento);
	
} //ProvinciaMapper
