package com.cardif.satelite.suscripcion.dao;

import com.cardif.satelite.model.satelite.TramaDiaria;

public interface TramaDiariaMapper
{
	
	public TramaDiaria selectByPrimaryKey(Long secArchivo);
	
	public int deleteByPrimaryKey(Long secArchivo);
	
	public int insert(TramaDiaria record);
	
	public int insertSelective(TramaDiaria record);
	
} //TramaDiariaMapper
