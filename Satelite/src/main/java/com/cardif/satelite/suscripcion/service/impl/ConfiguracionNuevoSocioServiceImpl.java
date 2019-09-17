package com.cardif.satelite.suscripcion.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_OBTENER;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.dao.CanalProductoMapper;
import com.cardif.satelite.configuracion.dao.ProductoMapper;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.CanalProducto;
import com.cardif.satelite.model.Layout;
import com.cardif.satelite.model.Producto;
import com.cardif.satelite.model.Socio;
import com.cardif.satelite.suscripcion.dao.LayoutMapper;
import com.cardif.satelite.suscripcion.dao.SocioTramaMapper;
import com.cardif.satelite.suscripcion.service.ConfiguracionNuevoSocioService;
import com.sun.org.apache.commons.beanutils.BeanUtils;

@Service("ConfiguracionNuevoSocioService")
public class ConfiguracionNuevoSocioServiceImpl implements ConfiguracionNuevoSocioService {
	public static final Logger logger = Logger.getLogger(ConfiguracionNuevoSocioServiceImpl.class);
	
	@Autowired
	SocioTramaMapper socioTramaMapper = null;
	
	@Autowired
	ProductoMapper productoMapper = null;
	
	@Autowired
	LayoutMapper  layoutMapper = null;
	
	@Autowired
	CanalProductoMapper canalProductoMapper= null;
	
	@Override
	public List<Socio> ListaSocioByRucRazonSocial( String ruc , String razonSocial) throws SyncconException{
		
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<Socio> lista = null;		
		try {
			
				logger.debug("Input [ 1.-" + ruc +", 2.-"+ razonSocial +" ]");
				
				lista = new ArrayList<Socio>();
				lista = socioTramaMapper.listaSocioByRucRazonSocial(ruc, razonSocial);
				Long secuencial = new Long(0);
				for(int i=0; i<lista.size(); i++){
					lista.get(i).setSecuencial(++secuencial);
				}
				
				logger.debug("Output [Registros = " + lista.size()+ "]");		
				
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SyncconException(COD_ERROR_BD_OBTENER);
		}		
		if (logger.isInfoEnabled()) {logger.info("Fin");}
	
		return lista;
	}
	
	public Boolean ListaSocioByRuc(String ruc) throws SyncconException{
		
		if(logger.isInfoEnabled()){logger.info("Inicio");}		
		Boolean status = false;
		 try {
			
			 if(logger.isInfoEnabled()){logger.debug("Input [1.- "+ ruc+"]");}				 
			Socio obj= socioTramaMapper.listaSocioByRuc(ruc);				
			//logger.info("resultado Socio" + BeanUtils.describe(obj));				
			if(obj == null)
			{
				status = false;				
			}else{
				status = true;					
			}				
		} catch (Exception e) { 
			logger.error(e.getMessage());
			status = false;
			throw new SyncconException( ErrorConstants.COD_ERROR_BD_BUSCAR );
		}			
			if(logger.isInfoEnabled()){logger.info("Fin");}	
		return status;
	}
	
	@Override
	public List<Producto> listaProductoByIdsocio (Integer idSocio, String producto) throws SyncconException{
		
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<Producto> lista = null;		
		try {
			logger.debug("Input [ 1.- ID SOCIO" + idSocio+" ] | [ 2.-" + producto+" ]");
		
				lista = new ArrayList<Producto>();
				lista = productoMapper.listaProductoByIdsocio(idSocio,producto);
			
			logger.debug("Output [ Registros = "+ lista.size()+" ]");
		} catch (Exception e) {
				logger.error(e.getMessage(),e);
				throw new SyncconException(COD_ERROR_BD_OBTENER);
		}		
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return lista;
	}
	
	@Override
	public List<Layout> listaLayoutByIdproducto(Integer idproducto) throws SyncconException{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<Layout> lista = null;
		try {
			logger.debug("Input [ 1.-"+idproducto+" ]");
			lista = new ArrayList<Layout>();
			lista = layoutMapper.listaLayoutByIdproducto(idproducto);
			for(int i=0; i<lista.size(); i++){
				lista.get(i).setSecuencia(new Long(i+1));
			}
			
			logger.debug("Output [ Registros= " + lista.size()+"]");
		} catch (Exception e) {
				logger.error(e.getMessage(),e);
				throw new SyncconException(COD_ERROR_BD_OBTENER);
		}		
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return lista;
	}
	
	@Override
	public Boolean nuevoSocio(Socio nuevoSocio)throws SyncconException{
		
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		
		try {
			logger.debug("Input [ 1.-"+ BeanUtils.describe(nuevoSocio)+" ]");
			
		
		
				socioTramaMapper.insert(nuevoSocio);
			
			logger.debug("Output [ 1.- ]");
		} catch (Exception e){
					logger.error(e.getMessage(),e);
					throw new SyncconException(COD_ERROR_BD_OBTENER);
		} 	
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		
		return null;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public Boolean modificarSocio(Socio modificarSocio)throws SyncconException{
		
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		
		try {
			logger.debug("Input [ 1.-"+ BeanUtils.describe(modificarSocio)+" ]");
				socioTramaMapper.updateByPrimaryKeySelective(modificarSocio);
			
			logger.debug("Output [ 1.- ]");
		} catch (Exception e){
					logger.error(e.getMessage(),e);
					throw new SyncconException(COD_ERROR_BD_OBTENER);
		} 	
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return null;		
	}
	
	@Override
	public List<CanalProducto> listaCanal()throws SyncconException{
		
		List<CanalProducto> lista = null;
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		try {
			
				lista = canalProductoMapper.selectAll();
			
			logger.debug("Output [ Registros = "+lista.size()+" ]");
		} catch (Exception e){
					logger.error(e.getMessage(),e);
					throw new SyncconException(COD_ERROR_BD_OBTENER);
		} 	
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		
		return lista;
		
	}
	@Override
	public Boolean insertProducto(Producto nuevoProducto) throws SyncconException{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		
		try {
			logger.debug("Input [ 1.-" + BeanUtils.describe(nuevoProducto)+" ]");
			
			productoMapper.insert(nuevoProducto);
			
			logger.debug("Output [ 1.- ]");
		} catch (Exception e){
					logger.error(e.getMessage(),e);
					throw new SyncconException(COD_ERROR_BD_OBTENER);
		} 	
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return null;
	}
	@Override
	public Boolean modificarProducto(Producto selectProducto) throws SyncconException{
		
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		
		try {
			logger.debug("Input [ 1.-" + BeanUtils.describe(selectProducto)+" ]");
		
			productoMapper.updateByPrimaryKeySelective(selectProducto);
			
			logger.debug("Output [ 1.- ]");
		} catch (Exception e){
					logger.error(e.getMessage(),e);
					throw new SyncconException(COD_ERROR_BD_OBTENER);
		} 	
		
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		
		return null;		
	}
	
	@Override
	public Boolean insertLayout(Layout layout)throws SyncconException{
		
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		
		try {
			logger.debug("Input [ 1.-" +BeanUtils.describe(layout) + " ]");
		
			layoutMapper.insertLayoutExcel(layout);
			
			logger.debug("Output [ 1.- ]");
		} catch (Exception e){
					logger.error(e.getMessage(),e);
					throw new SyncconException(COD_ERROR_BD_OBTENER);
		} 	
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		
		return null;		
	}
	
	@Override
	public Boolean eliminarLayout(Long pk)throws SyncconException{
	
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		
		try {
			logger.debug("Input [ 1.- "+ pk+"]");
			layoutMapper.Eliminar(pk);
			
			logger.debug("Output [ 1.- ]");
		} catch (Exception e){
					logger.error(e.getMessage(),e);
					throw new SyncconException(COD_ERROR_BD_OBTENER);
		} 	
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		
		return null;
	}

	@Override
	public Boolean modificarSocioNullDate(Socio modificarSocioNullDate) throws SyncconException {
		try {
			logger.debug("Input [ 1.- "+ BeanUtils.describe(modificarSocioNullDate)+"]");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
//	@Override
//	public Boolean modificarSocio(Socio modificarSocio)throws SyncconException{
//		
//		if (logger.isInfoEnabled()) {logger.info("Inicio");}
//		
//		try {
//		
//			logger.debug("Input [ 1.-"+ BeanUtils.describe(modificarSocio)+" ]");
//		
//				socioTramaMapper.updateByPrimaryKeySelective(modificarSocio);
//			
//			logger.debug("Output [ 1.- ]");
//		} catch (Exception e){
//					logger.error(e.getMessage(),e);
//					throw new SyncconException(COD_ERROR_BD_OBTENER);
//		} 	
//		if (logger.isInfoEnabled()) {logger.info("Fin");}
//		return null;		
//	}
	
	
	
}
