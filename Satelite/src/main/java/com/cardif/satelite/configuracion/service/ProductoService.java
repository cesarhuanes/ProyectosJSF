package com.cardif.satelite.configuracion.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.Producto;
import com.cardif.satelite.moduloimpresion.bean.ConsultaProductoSocio;
import com.cardif.satelite.suscripcion.bean.TipoArchivoBean;

public interface ProductoService
{
	
	/**
	 * Este metodo busca todos los productos segun el valor del estado.
	 * 
	 * @param estadoProducto
	 * 		Es el estado del producto.
	 * 		- 0	: ACTIVO
	 * 		- 1 : INACTIVO
	 * @return
	 * 		Retorna la lista con los productos segun el estado.
	 * @throws SyncconException
	 */
	public List<Producto> buscarProductosByEstado(Integer estadoProducto) throws SyncconException;
	
	/**
	 * Este metodo busca todos los productos segun la opcion del modulo de impresion, devuelve los productos que esten
	 * en estado ACTIVO.
	 * 
	 * @param opcionModImpresion
	 * 		Es la opcion que valida si el producto pertenece al modulo de impresion.
	 * 		- 1	: SI
	 * 		- 0	: NO
	 * @return
	 * 		Retorna la lista con los productos segun la opcion del modulo de impresion.
	 * @throws SyncconException
	 */
	public List<ConsultaProductoSocio> buscarParaModImpresion(Integer opcionModImpresion) throws SyncconException;
	
	/**
	 * Este metodo busca todos los productos seguna la opcion del modulo de impresion y el nombre del canal, devolviendo los
	 * productos que esten en estado ACTIVO.
	 * 
	 * @param opcionModImpresion
	 * 		Es la opcion que valida si el producto pertenece al modulo de impresion.
	 * 		- 1	: SI
	 * 		- 0	: NO
	 * @param nombreCanal
	 * 		El nombre del canal. Tabla CANAL_PRODUCTO.
	 * @return
	 * 		Retorna los productos que cumplan con los filtros de entrada y que esten en estado ACTIVO.
	 * @throws SyncconException
	 */
	public List<ConsultaProductoSocio> buscarPorModImpresionNombreCanal(Integer opcionModImpresion, String nombreCanal) throws SyncconException;
	
	/**
	 * Este metodo busca los tipos de archivos disponibles en la tabla PRODUCTO, restringiendo el tipo de
	 * archivo 'BD'.
	 * 
	 * @return
	 * 		Retorna la lista con los tipos de archivos disponibles.
	 * @throws SyncconException
	 */
	public List<TipoArchivoBean> buscarTipoArchivos() throws SyncconException;
	
	/**
	 * Este metodo inserta un registro en la tabla PRODUCTO.
	 * 
	 * @param producto
	 * @return
	 * @throws SyncconException
	 */
	public Producto insert(Producto producto) throws SyncconException;
	
	public List<ConsultaProductoSocio> buscarPorModuloEstadoCanal(String moduloSeleccionado, int estado, Long idCanal) throws SyncconException;
	
	public ConsultaProductoSocio buscarPorPkDetalleTramaDiaria(Long pkDetalleTramaDiaria) throws SyncconException;
	
	public List<ConsultaProductoSocio> buscarPorEstado(int estado) throws SyncconException;

	public String obtenerRutaArchivo(String codProductom,String flag_canal)  throws SyncconException;
	
} //ProductoService
