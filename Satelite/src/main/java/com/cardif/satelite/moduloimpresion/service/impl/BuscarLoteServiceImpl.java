package com.cardif.satelite.moduloimpresion.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.dao.ProductoMapper;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.moduloimpresion.DetalleLoteImpresion;
import com.cardif.satelite.model.moduloimpresion.LoteImpresion;
import com.cardif.satelite.model.moduloimpresion.NumeroDocValorado;
import com.cardif.satelite.model.satelite.DetalleTramaDiaria;
import com.cardif.satelite.moduloimpresion.bean.ConsultaArmarLote;
import com.cardif.satelite.moduloimpresion.bean.ConsultaConfirmacionImpresion;
import com.cardif.satelite.moduloimpresion.bean.ConsultaDocumentoCurier;
import com.cardif.satelite.moduloimpresion.bean.ConsultaLoteImpresion;
import com.cardif.satelite.moduloimpresion.bean.ConsultaProductoSocio;
import com.cardif.satelite.moduloimpresion.dao.DetalleLoteImpresionMapper;
import com.cardif.satelite.moduloimpresion.dao.LoteImpresionMapper;
import com.cardif.satelite.moduloimpresion.dao.NumeroDocValoradoMapper;
import com.cardif.satelite.moduloimpresion.service.ActualizarSociosService;
import com.cardif.satelite.moduloimpresion.service.BuscarLoteService;
import com.cardif.satelite.soat.model.SocioDigitales;
import com.cardif.satelite.suscripcion.dao.DetalleTramaDiariaMapper;
import com.cardif.satelite.util.SateliteUtil;
import com.sun.org.apache.commons.beanutils.BeanUtils;

@Service("buscarLoteService")
public class BuscarLoteServiceImpl implements BuscarLoteService
{
	private final Logger logger = Logger.getLogger(BuscarLoteServiceImpl.class);
	
	@Autowired
	private LoteImpresionMapper loteImpresionMapper;
	
	@Autowired
	private DetalleLoteImpresionMapper detalleLoteImpresionMapper;
	
	@Autowired
	private DetalleTramaDiariaMapper detalleTramaDiariaMapper;
	
	@Autowired
	private NumeroDocValoradoMapper numeroDocValoradoMapper;
	
	@Autowired
	private ActualizarSociosService  actualizarSociosService = null;
	
	
	@Autowired
	private ProductoMapper productoMapper = null;
	/**
	 * Este metodo busca la lista de lotes segun los parametros de entrada. Busca en la tabla 
	 * 'LOTE_IMPRESION'.
	 */
	@Override
	public List<ConsultaLoteImpresion> buscarLotesDeImpresion(Long codProducto, String estadoLote, Long numLote, Date fecCreacionDesde, Date fecCreacionHasta) 
			throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<ConsultaLoteImpresion> listaLotesImpresion = null;
		
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + codProducto + ", 2.-" + estadoLote + ", 3.-" + numLote + ", 4.-" + fecCreacionDesde + ", 5.-" + fecCreacionHasta+ "]");}
			estadoLote = StringUtils.isNotBlank(estadoLote) ? estadoLote : null;
			String numLoteStr = (null != numLote && 0 < numLote) ? numLote.toString() : null;
			fecCreacionDesde = (null != fecCreacionDesde ? fecCreacionDesde : SateliteUtil.getInitialCardifDate());
			fecCreacionHasta = (null != fecCreacionHasta ? fecCreacionHasta : new Date());
			
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + codProducto + ", 2.-" + estadoLote + ", 3.-" + numLote + ", 4.-" + sdf.format(fecCreacionDesde).toString() + ", 5.-" + sdf.format(fecCreacionHasta).toString()+ "]");}
			
			
			listaLotesImpresion = loteImpresionMapper.selectLoteImpresionByParameters(codProducto, estadoLote, numLoteStr, sdf.format(fecCreacionDesde).toString(), sdf.format(fecCreacionHasta).toString());
			int size = (null != listaLotesImpresion ? listaLotesImpresion.size() : 0);
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + size + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listaLotesImpresion;
	} //buscarLotesDeImpresion
	
	/**
	 * Este metodo busca un objeto LoteImpresion en base a su llave primaria.
	 */
	@Override
	public LoteImpresion buscarLoteImpresionByPk(Long pkLoteImpresion) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		LoteImpresion loteImpresion = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + pkLoteImpresion + "]");}
			loteImpresion = loteImpresionMapper.selectByPrimaryKey(pkLoteImpresion);
			
			if (logger.isInfoEnabled()) {logger.info("Output [LoteImpresion: " + loteImpresion + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return loteImpresion;
	} //buscarLoteImpresionByPk
	
	@Override
	public List<ConsultaDocumentoCurier> buscarPolizasCurierByPkLote(Long pkLoteImpresion) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<ConsultaDocumentoCurier> listaDocumentoCurier = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + pkLoteImpresion + "]");}
			listaDocumentoCurier = detalleTramaDiariaMapper.selectPolizasCurierByPkLote(pkLoteImpresion);
			
			int size = (null != listaDocumentoCurier ? listaDocumentoCurier.size() : 0);
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + size + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listaDocumentoCurier;
	} //buscarPolizasCurierByPkLote
	
	@Override
	public List<ConsultaConfirmacionImpresion> buscarPolizasParaConfirmarImpresion(Long numLote) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<ConsultaConfirmacionImpresion> listaConfirmacionImpresion = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + numLote + "]");}
			listaConfirmacionImpresion = detalleTramaDiariaMapper.selectPolizasParaConfirmarImpresion(numLote);
			
			int size = (null != listaConfirmacionImpresion ? listaConfirmacionImpresion.size() : 0);
			if (logger.isInfoEnabled()) {logger.info("Output [Registros: " + size + "]");}
			for (int i = 0; i < listaConfirmacionImpresion.size(); i++) {
				listaConfirmacionImpresion.get(i).setImpresoCorrectamente("SI "+ listaConfirmacionImpresion.get(i).getId());
				listaConfirmacionImpresion.get(i).setDisabledText(true);
				listaConfirmacionImpresion.get(i).setTmpNroCertificado(listaConfirmacionImpresion.get(i).getNroCertificado());
			}	
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listaConfirmacionImpresion;
	} //buscarPolizasParaConfirmarImpresion
	
	@Override
	public boolean confirmarImpresionPorPoliza(ConsultaConfirmacionImpresion cConfirmacionImpresion, ConsultaConfirmacionImpresion cConfirmacionImpresionAux) 
			throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		boolean flag = false;
		
		try
		{
			if (logger.isInfoEnabled()) {logger.info("Opcion CONFIRMAR IMPRESION - " + cConfirmacionImpresion.getImpresoCorrectamente());}
			
			
			DetalleTramaDiaria detalleTramaDiaria = detalleTramaDiariaMapper.selectByPrimaryKey(cConfirmacionImpresion.getId());
			if (logger.isDebugEnabled()) {logger.debug("Se encontro el objeto DetalleTramaDiaria[" + cConfirmacionImpresion.getId() + "]: " + detalleTramaDiaria);}
			
			
			/*
			 * Actualizando la informacion en la tabla DETALLE_TRAMA_DIARIA
			 */
			{
				detalleTramaDiaria.setNroCertificado(cConfirmacionImpresion.getNroCertificado().trim());
				detalleTramaDiaria.setEstadoImpresion(Constantes.MOD_IMPRESION_ESTADO_IMPRESO);
				
				int rows = detalleTramaDiariaMapper.updateByPrimaryKeySelective(detalleTramaDiaria);
				if (logger.isInfoEnabled()) {logger.info("Se actualizo el objeto DetalleTramaDiaria[" + cConfirmacionImpresion.getId() + "]. Filas afectadas: " + rows);}
			}
			
			
			/*
			 * Actualizando el estado del NUMERO_DOC_VALORADO, colocandolo como USADO.
			 */
			{
				int text =cConfirmacionImpresion.getNroCertificado().length() ;
				
				if (logger.isDebugEnabled()) {logger.debug("Buscando el objeto NumeroDocValorado[" + cConfirmacionImpresion.getNroCertificado() + "]");}
				String numCertificado = cConfirmacionImpresion.getNroCertificado().substring(0, text-6);
				String tipoCertificado = cConfirmacionImpresion.getNroCertificado().substring(text -6 , text -4);
				String anioCertificado = cConfirmacionImpresion.getNroCertificado().substring(text -4 , text   );
				
				NumeroDocValorado numeroDocValorado = numeroDocValoradoMapper.selectCertificadoByParametros(numCertificado, anioCertificado, tipoCertificado);
				if (logger.isDebugEnabled()) {logger.debug("Se encontro el objeto NumeroDocValorado[" + numeroDocValorado.getId() + " - " + cConfirmacionImpresion.getNroCertificado() + "]");}
				
				numeroDocValorado.setDisponible(Constantes.COD_NUM_DOC_VALORADO_USADO);
				numeroDocValorado.setNroPolizaRel(cConfirmacionImpresion.getNroPolizaPe());
				
				int rows = numeroDocValoradoMapper.updateByPrimaryKeySelective(numeroDocValorado);
				if (logger.isInfoEnabled()) {logger.info("Se actualizo el objeto NumeroDocValorado[" + numeroDocValorado.getId() + " - " + cConfirmacionImpresion.getNroCertificado() + "]. Filas afectadas: " + rows);}
			}
			
			
			/*
			 * ANULANDO el NUMERO_DOC_VALORADO anterior (SI EXISTIERA)
			 */
			if (cConfirmacionImpresion.getNroCertificado().compareToIgnoreCase(cConfirmacionImpresionAux.getNroCertificado()) != 0)
			{
				int text2 =cConfirmacionImpresion.getNroCertificado().length() ;

				if (logger.isDebugEnabled()) {logger.debug("Buscando el Objeto NumeroDocValorado[" + cConfirmacionImpresionAux.getNroCertificado() + "] a ANULAR.");}
				String numCertificado = cConfirmacionImpresionAux.getNroCertificado().substring(0, text2-6);
				String tipoCertificado = cConfirmacionImpresionAux.getNroCertificado().substring(text2-6, text2-4);
				String anioCertificado = cConfirmacionImpresionAux.getNroCertificado().substring(text2 -4, text2);
				
				NumeroDocValorado numeroDocValorado = numeroDocValoradoMapper.selectCertificadoByParametros(numCertificado, anioCertificado, tipoCertificado);
				if (logger.isDebugEnabled()) {logger.debug("Se encontro el objeto NumeroDocValorado[" + numeroDocValorado.getId() + " - " + cConfirmacionImpresionAux.getNroCertificado() + "] a ANULAR.");}
				
				numeroDocValorado.setAnulado(Constantes.COD_NUM_DOC_VALORADO_EST_ANULADO);
				numeroDocValorado.setDisponible(Constantes.COD_NUM_DOC_VALORADO_USADO);
				numeroDocValorado.setNroPolizaRel(detalleTramaDiaria.getNroPolizaPe());
				
				int rows = numeroDocValoradoMapper.updateByPrimaryKeySelective(numeroDocValorado);
				if (logger.isInfoEnabled()) {logger.info("Se anulo el objeto NumeroDocValorado[" + numeroDocValorado.getId() + " - " + cConfirmacionImpresionAux.getNroCertificado() + "]. Filas afectadas: " + rows);}
			}
			else
			{
				if (logger.isInfoEnabled()) {logger.info("No fue necesario LIBERAR ningun NUMERO_DOC_VALORADO AUXILIAR. NroCertificado_AUX: " + cConfirmacionImpresionAux.getNroCertificado());}
			}
			
			
			/*
			 * Cambiando el valor de IMPRESO
			 */
			{
				if (logger.isDebugEnabled()) {logger.debug("Buscando el objeto DetalleLoteImpresion[" + cConfirmacionImpresion.getNumLote() + " - "+ detalleTramaDiaria.getId() + "]");}
				DetalleLoteImpresion detalleLoteImpresion = detalleLoteImpresionMapper.selectByLoteImpresionAndDetalleTramaDiaria(cConfirmacionImpresion.getNumLote(), detalleTramaDiaria.getId());
				
				detalleLoteImpresion.setImpresoCorrectamente(cConfirmacionImpresion.getImpresoCorrectamente());
				
				int rows = detalleLoteImpresionMapper.updateByPrimaryKeySelective(detalleLoteImpresion);
				if (logger.isInfoEnabled()) {logger.info("Se actualizo el objeto DetalleLoteImpresion[" + cConfirmacionImpresion.getNumLote() + " - " + detalleTramaDiaria.getId() + "]. Filas afectadas: " + rows);}
			}
			
			
			// Actualizacion de socios digitales poliza_pe, a modo impresion
			
						if(null != detalleTramaDiaria){
							
							SocioDigitales socioDigital = new SocioDigitales();				
							socioDigital.setIdPoliza(Long.valueOf(detalleTramaDiaria.getNroPolizaPe()));
							socioDigital.setEstadoPoliza(Constantes.MOD_IMPRESION_ESTADO_IMPRESA_DIG);
							socioDigital.setNumValorado(cConfirmacionImpresion.getNroCertificado().trim());
							
							if (logger.isInfoEnabled()) {logger.info("REPLICA EN SOCIOS DIGITALES [ "+ BeanUtils.describe(socioDigital)+" ]");}
							
							ConsultaProductoSocio producto = productoMapper.selectByPkDetalleTramaDiaria(detalleTramaDiaria.getId());

							if (logger.isInfoEnabled()) {logger.info("SELECCIONAR EL PRODUCTO DIGITAL [ "+ BeanUtils.describe(producto)+" ]");}

							
							if(producto.getNombreProducto().equalsIgnoreCase(Constantes.PRODUCTO_NOMBRE_CENCOSUD)){
							if (logger.isInfoEnabled()) {logger.info("REPLICA EN SOCIOS DIGITALES CENCOSUD");}				
							actualizarSociosService.actualizarSocioCencosud(socioDigital);
							}
							if(producto.getNombreProducto().equalsIgnoreCase(Constantes.PRODUCTO_NOMBRE_COMPARA_BIEN)){
							if (logger.isInfoEnabled()) {logger.info("REPLICA EN SOCIOS DIGITALES COMPARABIEN");}				
							actualizarSociosService.actualizarSocioComparaBien(socioDigital);
							}
							if(producto.getNombreProducto().equalsIgnoreCase(Constantes.PRODUCTO_NOMBRE_DIRECTO)){
							if (logger.isInfoEnabled()) {logger.info("REPLICA EN SOCIOS DIGITALES DIRECTO");}				
							actualizarSociosService.actualizarSocioDirecto(socioDigital);
							}
							if(producto.getNombreProducto().equalsIgnoreCase(Constantes.PRODUCTO_NOMBRE_FALABELLA)){
							if (logger.isInfoEnabled()) {logger.info("REPLICA EN SOCIOS DIGITALES FALABELLA");}				
							actualizarSociosService.actualizarSocioFalabella(socioDigital);
							}
							if(producto.getNombreProducto().equalsIgnoreCase(Constantes.PRODUCTO_NOMBRE_RIPLEY)){
							if (logger.isInfoEnabled()) {logger.info("REPLICA EN SOCIOS DIGITALES RIPLEY");}				
							actualizarSociosService.actualizarSocioRipley(socioDigital);	
							}
							
						
						}
			
			
			/*
			 * Cambiando el valor booleano del FLAG, cuando todo el proceso ha culminado.
			 */
			flag = true;
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_ARMAR_LOTE_CONF_IMPRESION_POLIZA);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return flag;
	} //confirmarImpresionPorPoliza
	
	@Override
	public boolean actualizarEstadoLoteImpresion(Long numLote, String modImpresionEstado) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		boolean flag = false;
		
		try
		{
			LoteImpresion loteImpresionObj = loteImpresionMapper.selectByPrimaryKey(numLote);
			if (logger.isDebugEnabled()) {logger.debug("Se encontro el LoteImpresion[" + numLote + "]: " + loteImpresionObj);}
			
			loteImpresionObj.setEstado(modImpresionEstado);
			
			int rows = loteImpresionMapper.updateByPrimaryKeySelective(loteImpresionObj);
			if (logger.isDebugEnabled()) {logger.debug("Se cambio el estado del LoteImpresion[" + numLote + "]. Filas afectadas: " + rows);}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_ARMAR_LOTE_ACTUA_LOTE_IMPRESION);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return flag;
	} //actualizaEstadoLoteImpresion
	
	@Override
	public Map<Long, Boolean> validarNumeroDoc(List<ConsultaArmarLote> listaValidarCertificadoLote) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		Map<Long, Boolean> mapCertValidos = null;
		
		try
		{
			mapCertValidos = new HashMap<Long, Boolean>();
			
			for (ConsultaArmarLote conVCertificadoLote : listaValidarCertificadoLote)
			{
				int text =conVCertificadoLote.getNumCertificado().length() ;

				String numCertificado = conVCertificadoLote.getNumCertificado().substring(0, text-6);
				String tipoCertificado = conVCertificadoLote.getNumCertificado().substring(text-6, text- 4);
				String anioCertificado = conVCertificadoLote.getNumCertificado().substring(text-4, text);
				
				NumeroDocValorado numDocValorado = numeroDocValoradoMapper.selectCertificadoByParametrosAndProducto(numCertificado, anioCertificado, tipoCertificado,
						conVCertificadoLote.getIdProducto());
				
				if (null != numDocValorado)
				{
					if (0 == numDocValorado.getDisponible()) /* Se encuentra disponible */
					{
						mapCertValidos.put(conVCertificadoLote.getId(), true);
					}
					else
					{
						mapCertValidos.put(conVCertificadoLote.getId(), false);
					}
				}
				else
				{
					mapCertValidos.put(conVCertificadoLote.getId(), false);
				}
			} //for
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return mapCertValidos;
	} //validarNumeroDocValorado
	
	@Override
	public Map<Long, Boolean> validarNumeroDocValorados(List<ConsultaArmarLote> listaValidarCertificadoLote) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		Map<Long, Boolean> mapCertValidos = null;
		
		try
		{
			mapCertValidos = new HashMap<Long, Boolean>();
			
			for (ConsultaArmarLote conVCertificadoLote : listaValidarCertificadoLote)
			{
				int text =conVCertificadoLote.getNumCertificado().length() ;

				String numCertificado = conVCertificadoLote.getNumCertificado().substring(0, text-6);
				String tipoCertificado = conVCertificadoLote.getNumCertificado().substring(text-6, text-4);
				String anioCertificado = conVCertificadoLote.getNumCertificado().substring(text-4, text);
				
				NumeroDocValorado numDocValorado = numeroDocValoradoMapper.selectCertificadoByParametrosAndProducto(numCertificado, anioCertificado, tipoCertificado,
						conVCertificadoLote.getIdProducto());
				
				if (null != numDocValorado && Constantes.COD_NUM_DOC_VALORADO_EST_ACTIVO == numDocValorado.getAnulado() && Constantes.COD_NUM_DOC_VALORADO_DISPONIBLE == numDocValorado.getDisponible())
				{
					/*
					 * Se encuentra disponible
					 */
					mapCertValidos.put(conVCertificadoLote.getId(), true);
				}
				else
				{
					mapCertValidos.put(conVCertificadoLote.getId(), false);
				}
			} //for
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return mapCertValidos;
	} //validarNumeroDocValorado
	
} //BuscarLoteServiceImpl
