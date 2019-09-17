package com.cardif.satelite.configuracion.service.impl;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.dao.ProductoMapper;
import com.cardif.satelite.configuracion.service.ProductoService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.Producto;
import com.cardif.satelite.moduloimpresion.bean.ConsultaProductoSocio;
import com.cardif.satelite.suscripcion.bean.TipoArchivoBean;

@Service("productoConfService")
public class ProductoServiceImpl implements ProductoService
{
	private final Logger logger = Logger.getLogger(ProductoServiceImpl.class);
	
	@Autowired
	private ProductoMapper productoMapper;
	
	
	@Override
	public List<Producto> buscarProductosByEstado(Integer estadoProducto) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<Producto> listaProductos = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + estadoProducto + "]");}
			
			listaProductos = productoMapper.selectProductosByEstado(estadoProducto);
			int size = (null != listaProductos ? listaProductos.size() : 0);
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + size + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listaProductos;
	} //buscarProductosByEstado
	
	@Override
	public List<ConsultaProductoSocio> buscarParaModImpresion(Integer opcionModImpresion) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<ConsultaProductoSocio> listaProductoSocio = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + opcionModImpresion + "]");}
			
			listaProductoSocio = productoMapper.selectForModImpresion(opcionModImpresion);
			int size = (null != listaProductoSocio ? listaProductoSocio.size() : 0);
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + size + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listaProductoSocio;
	} //buscarParaModImpresion
	
	@Override
	public List<ConsultaProductoSocio> buscarPorModImpresionNombreCanal(Integer opcionModImpresion, String nombreCanal) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<ConsultaProductoSocio> listaProductoSocio = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + opcionModImpresion + ", 2.-" + nombreCanal + "]");}
			
			listaProductoSocio = productoMapper.selectForModImpresionAndCanal(opcionModImpresion, nombreCanal);
			int size = (null != listaProductoSocio ? listaProductoSocio.size() : 0);
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + size + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listaProductoSocio;
	} //buscarPorModImpresionNombreCanal
	
	@Override
	public List<TipoArchivoBean> buscarTipoArchivos() throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<TipoArchivoBean> listaTipoArchivos = null;
		
		try
		{
			listaTipoArchivos = productoMapper.selectTipoArchivos();
			int size = (null != listaTipoArchivos ? listaTipoArchivos.size() : 0);
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + size + "]");}
		}
		catch(Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listaTipoArchivos;
	} //buscarTipoArchivos
	
	@Override
	public Producto insert(Producto producto) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		try
		{
			int rows = productoMapper.insert(producto);
			if (logger.isDebugEnabled()) {logger.debug("El PRODUCTO[" + producto.getId() + "] fue registrado correctamente. Filas afectadas: " + rows);}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_INSERTAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return producto;
	} //insert

	@Override
	public List<ConsultaProductoSocio> buscarPorModuloEstadoCanal(String moduloSeleccionado, int estado, Long idCanal) throws SyncconException
	{
		if (logger.isDebugEnabled()) {logger.debug("Inicio");}
		List<ConsultaProductoSocio> listaProductoSocio = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + moduloSeleccionado + ", 2.-" + estado + ", 3.-" + idCanal + "]");}
			if (moduloSeleccionado.equalsIgnoreCase(Constantes.PRODUCTO_MOD_SUSCRIPCION))
			{
				if (logger.isDebugEnabled()) {logger.debug("Buscar productos con el MODULO DE SUSCRIPCION HABILITADO.");}
				listaProductoSocio = productoMapper.selectByModSuscripcionAndEstadoAndCanal(Constantes.PRODUCTO_MOD_SUSCRIPCION_SI, idCanal, estado);
			}
			else if (moduloSeleccionado.equalsIgnoreCase(Constantes.PRODUCTO_MOD_IMPRESION))
			{
				if (logger.isDebugEnabled()) {logger.debug("Buscar productos con el MODULO DE IMPRESION HABILITADO.");}
				listaProductoSocio = productoMapper.selectByModImpresionAndEstadoAndCanal(Constantes.PRODUCTO_MOD_IMPRESION_SI, idCanal, estado);
			}
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + (null != listaProductoSocio ? listaProductoSocio.size() : 0) + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isDebugEnabled()) {logger.debug("Fin");}
		return listaProductoSocio;
	} //buscarPorModuloEstadoCanal
	
	@Override
	public ConsultaProductoSocio buscarPorPkDetalleTramaDiaria(Long pkDetalleTramaDiaria) throws SyncconException
	{
		if (logger.isDebugEnabled()) {logger.debug("Inicio");}
		ConsultaProductoSocio consultaProductoSocio = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + pkDetalleTramaDiaria + "]");}
			
			consultaProductoSocio = productoMapper.selectByPkDetalleTramaDiaria(pkDetalleTramaDiaria);
			if (logger.isInfoEnabled()) {logger.info("Output [ConsultaProductoSocio: " + consultaProductoSocio + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
		}
		if (logger.isDebugEnabled()) {logger.debug("Fin");}
		return consultaProductoSocio;
	} //buscarPorPkDetalleTramaDiaria
	
	@Override
	public List<ConsultaProductoSocio> buscarPorEstado(int estado) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<ConsultaProductoSocio> listaProductoSocio = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + estado + "]");}
			
			listaProductoSocio = productoMapper.selectForEstado(estado);
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + (null != listaProductoSocio ? listaProductoSocio.size() : 0) + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listaProductoSocio;
	} //buscarPorEstado

	@Override
	public String obtenerRutaArchivo(String codProducto,String flag_canal) throws SyncconException {
		String urlArchivo="";
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + codProducto + " 2.- " + flag_canal+"]");}
			
			urlArchivo = productoMapper.selectArchivoUrl(codProducto,flag_canal);
		
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		return urlArchivo;
	}
	
} //ProductoServiceImpl
