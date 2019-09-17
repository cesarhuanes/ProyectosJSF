package com.cardif.satelite.configuracion.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.Socio;

public interface SocioService
{
	
	/**
	 * Este metodo inserta un registro en la tabla SOCIO.
	 * 
	 * @param socio
	 * @return
	 * @throws SyncconException
	 */
	public Socio insert(Socio socio) throws SyncconException;
	
	/**
	 * Este metodo obtiene el SOCIO en base al RUC.
	 * 
	 * @param rucSocio
	 * @return
	 * @throws SyncconException
	 */
	public Socio getSocioByRuc(String rucSocio) throws SyncconException;
	
	public List<Socio> buscarPorModSuscripcionEstadoProdEstadoSocio(Integer modSuscripcionOpcion, Integer estadoProducto, Integer estadoSocio) throws SyncconException;
	
	public List<Socio> buscarSocio(Integer estado)throws SyncconException;
	public List<Socio> buscarTodosSocios() throws SyncconException;

	public List<Socio> buscarSociosPorGrupoComercio(String grupoComercio) throws SyncconException;
	
} //SocioService
