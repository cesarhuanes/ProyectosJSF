package com.cardif.satelite.configuracion.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.CanalProducto;

public interface CanalProductoService
{
	
	public abstract List<CanalProducto> buscarPorEstado(int estado) throws SyncconException;
	
	public abstract List<CanalProducto> buscarPorCodSocio(Integer modSuscripcionOpcion, Long codSocio) throws SyncconException;
	
} //CanalProductoService
