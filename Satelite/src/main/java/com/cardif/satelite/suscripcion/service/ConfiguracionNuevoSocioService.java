package com.cardif.satelite.suscripcion.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.CanalProducto;
import com.cardif.satelite.model.Layout;
import com.cardif.satelite.model.Producto;
import com.cardif.satelite.model.Socio;

public interface ConfiguracionNuevoSocioService {

	public List<Socio> ListaSocioByRucRazonSocial( String ruc , String razonSocial) throws SyncconException;
	public List<Producto> listaProductoByIdsocio (Integer idSocio, String producto ) throws SyncconException;
	public List<Layout> listaLayoutByIdproducto(Integer idproducto) throws SyncconException;
	public Boolean nuevoSocio(Socio nuevoSocio)throws SyncconException;
	public Boolean modificarSocio(Socio modificarSocio)throws SyncconException;
	public List<CanalProducto> listaCanal()throws SyncconException;
	public Boolean insertProducto(Producto nuevoProducto) throws SyncconException;
	public Boolean modificarProducto(Producto selectProducto) throws SyncconException;
	public Boolean insertLayout(Layout layout)throws SyncconException;
	public Boolean eliminarLayout(Long pk)throws SyncconException;
	public Boolean ListaSocioByRuc(String ruc) throws SyncconException;
	public Boolean modificarSocioNullDate(Socio modificarSocioNullDate)throws SyncconException;	
}
