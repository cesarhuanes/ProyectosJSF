package com.cardif.satelite.moduloimpresion.service.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.moduloimpresion.NumeroDocValorado;
import com.cardif.satelite.moduloimpresion.dao.NumeroDocValoradoMapper;
import com.cardif.satelite.moduloimpresion.service.ConfigurarCertificadoService;

@Service("configurarCertificadoService")
public class ConfigurarCertificadoServiceImpl implements ConfigurarCertificadoService
{
	private final Logger logger = Logger.getLogger(ConfigurarCertificadoServiceImpl.class);
	
	@Autowired
	private NumeroDocValoradoMapper numeroDocValoradoMapper;
	
	
	@Override
	public List<NumeroDocValorado> buscarCertificadosPublicos(Long codProducto) throws SyncconException
	{
		if (logger.isDebugEnabled()) {logger.debug("Inicio");}
		List<NumeroDocValorado> listaDocValoradoPublicos = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + codProducto + ", 2.-" + Constantes.COD_DOC_VALORADO_PUBLICO + "]");}
			listaDocValoradoPublicos = numeroDocValoradoMapper.selectNumeroDocValoradoByProductoAndTipo(codProducto, Constantes.COD_DOC_VALORADO_PUBLICO);
			
			int size = (null != listaDocValoradoPublicos ? listaDocValoradoPublicos.size() : 0);
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + size + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isDebugEnabled()) {logger.debug("Fin");}
		return listaDocValoradoPublicos;
	} //buscarCertificadosPublicos
	
	@Override
	public List<NumeroDocValorado> buscarCertificadosPrivados(Long codProducto) throws SyncconException
	{
		if (logger.isDebugEnabled()) {logger.debug("Inicio");}
		List<NumeroDocValorado> listaDocValoradoPrivados = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + codProducto + ", 2.-" + Constantes.COD_DOC_VALORADO_PRIVADO + "]");}
			listaDocValoradoPrivados = numeroDocValoradoMapper.selectNumeroDocValoradoByProductoAndTipo(codProducto, Constantes.COD_DOC_VALORADO_PRIVADO);
			
			int size = (null != listaDocValoradoPrivados ? listaDocValoradoPrivados.size() : 0);
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + size + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isDebugEnabled()) {logger.debug("Fin");}
		return listaDocValoradoPrivados;
	} //buscarCertificadosPrivados
	
	@Override
	public String[] insertarRangoCertificadosPublicos(Integer rangoDesde, Integer rangoHasta, String anioCertificado, Long codProducto) 
			throws SyncconException
	{
		if (logger.isDebugEnabled()) {logger.debug("Inicio");}
		int registros = 0;
		//String certificadosRepetidos = null;
		String[] certificadosRepetidos=new String[2];
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + rangoDesde + ", 2.-" + rangoHasta + ", 3.-" + anioCertificado + ", 4.-" + codProducto + "]");}
			
			NumberFormat nf = new DecimalFormat("000000");
			
			for (Integer nCertificado=rangoDesde; nCertificado<=rangoHasta; nCertificado++)
			{
				String numeroCertificado = nf.format(nCertificado);
				
				/*
				 * Verificando si el Numero de Certificado ya existe para algun SOCIO.
				 * 
				 * Nota:
				 * 		Este valor debe ser UNICO para todos los socios.
				 */
				NumeroDocValorado nDocValorado = numeroDocValoradoMapper.selectCertificadoByParametros(numeroCertificado, anioCertificado, Constantes.COD_DOC_VALORADO_PUBLICO);
				if (logger.isDebugEnabled()) {
					//logger.debug("Numero de documento valorado_PK: " + (null != nDocValorado ? nDocValorado.getId() : "No encontrado"));
					}
				
				if (null == nDocValorado)
				{
					if (logger.isInfoEnabled()) {
						//logger.info("Agregando numero de documento valorado. numeroCertificado: " + numeroCertificado);
						}
					nDocValorado = new NumeroDocValorado();
					nDocValorado.setTipoCertificado(Constantes.COD_DOC_VALORADO_PUBLICO);
					nDocValorado.setAnio(Integer.valueOf(anioCertificado));
					nDocValorado.setProducto(codProducto);
					nDocValorado.setNumCertificado(numeroCertificado);
					nDocValorado.setDisponible(Constantes.COD_NUM_DOC_VALORADO_DISPONIBLE);
					nDocValorado.setAnulado(Constantes.COD_NUM_DOC_VALORADO_EST_ACTIVO);
					
					numeroDocValoradoMapper.insert(nDocValorado);
					registros++;
					
					if (null == certificadosRepetidos[0]){
						certificadosRepetidos[0] = new String("");						
					}
					certificadosRepetidos[0] += nDocValorado.getNumCertificado() + nDocValorado.getTipoCertificado() + nDocValorado.getAnio() + " ";
				}else{
					if (logger.isDebugEnabled()) {logger.debug("Numero de documento valorado existente. numeroCertificado: " + nDocValorado.getNumCertificado() + nDocValorado.getTipoCertificado() + nDocValorado.getAnio() + " Producto: " + nDocValorado.getProducto());}

					if (null == certificadosRepetidos[1])
					{
						certificadosRepetidos[1] = new String("");
					}
					
					certificadosRepetidos[1] += nDocValorado.getNumCertificado() + nDocValorado.getTipoCertificado() + nDocValorado.getAnio() + " ";
			
				}
			} 
			
			/*
			 if (StringUtils.isNotBlank(certificadosRepetidos))
			{
				certificadosRepetidos = certificadosRepetidos.trim().replace(" ", ", ");
			}
			*/
			
			
			 if (StringUtils.isNotBlank(certificadosRepetidos[0])){
					
					certificadosRepetidos[0] = certificadosRepetidos[0].trim().replace(" ", ", ");
					
			}
			 
			 if (StringUtils.isNotBlank(certificadosRepetidos[1])){
					
					certificadosRepetidos[1] = certificadosRepetidos[1].trim().replace(" ", ", ");
					
			}
			 
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + registros + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_INSERTAR);
		}
		if (logger.isDebugEnabled()) {logger.debug("Fin");}
		return certificadosRepetidos;
	} //insertarRangoCertificadosPublicos
	
	@Override
	public String[] insertarRangoCertificadosPrivados(Integer rangoDesde, Integer rangoHasta, String anioCertificado, Long codProducto)
			throws SyncconException
	{
		if (logger.isDebugEnabled()) {logger.debug("Inicio");}
		int registros = 0;
		String[] certificadosRepetidos = new String[2];

		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + rangoDesde + ", 2.-" + rangoHasta + ", 3.-" + anioCertificado + ", 4.-" + codProducto + "]");}
			
			Integer rDesde = Integer.valueOf(rangoDesde);
			Integer rHasta = Integer.valueOf(rangoHasta);
			
			NumberFormat nf = new DecimalFormat("000000");
			
			for (Integer nCertificado=rDesde; nCertificado<=rHasta; nCertificado++)
			{
				String numeroCertificado = nf.format(nCertificado);
				
				/*
				 * Verificando si el Numero de Certificado ya existe para algun SOCIO.
				 * 
				 * Nota:
				 * 		Este valor debe ser UNICO para todos los socios.
				 */
				NumeroDocValorado nDocValorado = numeroDocValoradoMapper.selectCertificadoByParametros(numeroCertificado, anioCertificado, Constantes.COD_DOC_VALORADO_PRIVADO);
				if (logger.isDebugEnabled()) {logger.debug("Numero de documento valorado_PK: " + (null != nDocValorado ? nDocValorado.getId() : "No encontrado"));}
				
				if (null == nDocValorado)
				{
					if (logger.isInfoEnabled()) {logger.info("Agregando numero de documento valorado. numeroCertificado: " + numeroCertificado);}
					nDocValorado = new NumeroDocValorado();
					nDocValorado.setTipoCertificado(Constantes.COD_DOC_VALORADO_PRIVADO);
					nDocValorado.setAnio(Integer.valueOf(anioCertificado));
					nDocValorado.setProducto(codProducto);
					nDocValorado.setNumCertificado(numeroCertificado);
					nDocValorado.setDisponible(Constantes.COD_NUM_DOC_VALORADO_DISPONIBLE);
					nDocValorado.setAnulado(Constantes.COD_NUM_DOC_VALORADO_EST_ACTIVO);
					
					numeroDocValoradoMapper.insert(nDocValorado);
					registros++;
					
					if (null == certificadosRepetidos[0])
					{
						certificadosRepetidos[0] = new String("");
					}
					certificadosRepetidos[0]+= numeroCertificado + Constantes.COD_DOC_VALORADO_PRIVADO + nDocValorado.getAnio() + " ";
					
					/*if (StringUtils.isNotBlank(certificadosRepetidos))
					{
						certificadosRepetidos = certificadosRepetidos.trim().replace(" ", ", ");
					}
					*/
				}
				else
				{
					//if (logger.isDebugEnabled()) {logger.debug("Numero de documento valorado existente. numeroCertificado: " + nDocValorado.getNumCertificado() + nDocValorado.getTipoCertificado() + nDocValorado.getAnio() + " Producto: " + nDocValorado.getProducto());}
					
					/*if (null == certificadosRepetidos)
					{
						certificadosRepetidos = new String("");
					}
					*/
					
					//certificadosRepetidos += nDocValorado.getNumCertificado() + nDocValorado.getTipoCertificado() + nDocValorado.getAnio() + " ";
					if (logger.isDebugEnabled()) {logger.debug("Numero de documento valorado existente. numeroCertificado: " + nDocValorado.getNumCertificado() + nDocValorado.getTipoCertificado() + nDocValorado.getAnio() + " Producto: " + nDocValorado.getProducto());}
				
				
					
					if (null == certificadosRepetidos[1])
					{
						certificadosRepetidos[1] = new String("");
					}
					
					certificadosRepetidos[1] += nDocValorado.getNumCertificado() + nDocValorado.getTipoCertificado() + nDocValorado.getAnio() + " ";
				}
			}
			
			/*
			if (StringUtils.isNotBlank(certificadosRepetidos))
			{
				certificadosRepetidos = certificadosRepetidos.trim().replace(" ", ", ");
			}
			*/
			 if (StringUtils.isNotBlank(certificadosRepetidos[0])){
					
					certificadosRepetidos[0] = certificadosRepetidos[0].trim().replace(" ", ", ");
					
			}
			 
			 if (StringUtils.isNotBlank(certificadosRepetidos[1])){
					
					certificadosRepetidos[1] = certificadosRepetidos[1].trim().replace(" ", ", ");
					
			}

			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + registros + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_INSERTAR);
		}
		if (logger.isDebugEnabled()) {logger.debug("Fin");}
		return certificadosRepetidos;
	} //insertarRangoCertificadosPrivados
	
	@Override
	public String eliminarCertificadoByParametros(String numCertificado, String tipoCertificado, Integer anioCertificado, Long codProducto) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		try
		{
			NumeroDocValorado nDocValorado = numeroDocValoradoMapper.selectCertificadoByParametrosAndProducto(numCertificado, anioCertificado.toString(), tipoCertificado, codProducto);
			
			if (null != nDocValorado)
			{
				if (logger.isDebugEnabled()) {logger.debug("Se encontro el certificado[" + nDocValorado.getNumCertificado() + nDocValorado.getTipoCertificado() + nDocValorado.getAnio() + "] con ID: " + nDocValorado.getId());}
				numeroDocValoradoMapper.deleteByPrimaryKey(nDocValorado.getId());
				
				respuesta = "Se elimino el certificado: " + numCertificado + tipoCertificado + anioCertificado + " del PRODUCTO: " + codProducto;
			}
			else
			{
				respuesta = "No se pudo eliminar el certificado: " + numCertificado + tipoCertificado + anioCertificado + " del PRODUCTO: " + codProducto;
			}
			if (logger.isDebugEnabled()) {logger.debug(respuesta);}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_ELIMINAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //eliminarCertificadoByParametros
	
} //ConfigurarCertificadoServiceImpl
