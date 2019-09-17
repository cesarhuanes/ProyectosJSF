package com.cardif.satelite.configuracion.dao;

import java.util.List;

import com.cardif.satelite.model.Distrito;

public interface DistritoMapper
{
	
	public List<Distrito> selectAllDistrito();
	
	public Distrito selectByPrimaryKey(String codProvincia);
	
	public List<Distrito> selectByCodProvincia(String codProvincia);
	
} //DistritoMapper
