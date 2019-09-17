package com.cardif.satelite.suscripcion.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.dao.CategoriaClaseMapper;
import com.cardif.satelite.configuracion.dao.MarcaVehiculoMapper;
import com.cardif.satelite.configuracion.dao.ModeloVehiculoMapper;
import com.cardif.satelite.configuracion.dao.ParametroAutomatMapper;
import com.cardif.satelite.configuracion.dao.ProductoMapper;
import com.cardif.satelite.configuracion.dao.UsoVehiculoMapper;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.CategoriaClase;
import com.cardif.satelite.model.ConsultaSoat;
import com.cardif.satelite.model.ParametroAutomat;
import com.cardif.satelite.model.moduloimpresion.NumeroDocValorado;
import com.cardif.satelite.model.reportes.DetalleReporteAPESEG;
import com.cardif.satelite.model.reportes.DetalleReporteSBS;
import com.cardif.satelite.model.satelite.DetalleTramaDiaria;
import com.cardif.satelite.model.satelite.DetalleTramaDiariaMotivo;
import com.cardif.satelite.moduloimpresion.bean.ConsultaProductoSocio;
import com.cardif.satelite.moduloimpresion.dao.NumeroDocValoradoMapper;
import com.cardif.satelite.reportes.dao.DetalleReporteAPESEGMapper;
import com.cardif.satelite.reportes.dao.DetalleReporteSBSMapper;
import com.cardif.satelite.soat.model.OrdenCompraPe;
import com.cardif.satelite.suscripcion.bean.ConsultaPostVentaSOAT;
import com.cardif.satelite.suscripcion.bean.RepPolizasSOATBean;
import com.cardif.satelite.suscripcion.bean.ReportePostVentaSOAT;
import com.cardif.satelite.suscripcion.dao.ConsultaSoatMapper;
import com.cardif.satelite.suscripcion.dao.DetalleTramaDiariaMapper;
import com.cardif.satelite.suscripcion.dao.DetalleTramaDiariaMotivoMapper;
import com.cardif.satelite.suscripcion.service.PostVentaSOATService;
import com.cardif.satelite.util.SateliteUtil;

@Service("postVentaSOATService")
public class PostVentaSOATServiceImpl implements PostVentaSOATService
{
	private final Logger logger = Logger.getLogger(PostVentaSOATServiceImpl.class);
	
	@Autowired
	private DetalleTramaDiariaMapper detalleTramaDiariaMapper;
	
	@Autowired
	private NumeroDocValoradoMapper numeroDocValoradoMapper;
	
	@Autowired
	private ParametroAutomatMapper parametroAutomatMapper;
	
	@Autowired
	private CategoriaClaseMapper categoriaClaseMapper;
	
	@Autowired
	private UsoVehiculoMapper usoVehiculoMapper;
	
	@Autowired
	private MarcaVehiculoMapper marcaVehiculoMapper;
	
	@Autowired
	private ModeloVehiculoMapper modeloVehiculoMapper;
	
	@Autowired
	private DetalleTramaDiariaMotivoMapper detTramaDiariaMotivoMapper;
	
	@Autowired
	private ProductoMapper productoMapper;
	
	@Autowired
	private com.cardif.satelite.soat.falabella.dao.OrdenCompraPeMapper ordenCompraFalabellaMapper;
	
	@Autowired
	private com.cardif.satelite.soat.ripley.dao.OrdenCompraPeMapper ordenCompraRipleyMapper;
	
	@Autowired
	private com.cardif.satelite.soat.cencosud.dao.OrdenCompraPeMapper ordenCompraCencosudMapper;
	
	@Autowired
	private com.cardif.satelite.soat.directo.dao.OrdenCompraPeMapper ordenCompraDirectoMapper;
	
	@Autowired
	private com.cardif.satelite.soat.comparabien.dao.OrdenCompraPeMapper ordenCompraComparaBienMapper;
	
	@Autowired
	private com.cardif.satelite.soat.sucursal.dao.OrdenCompraPeMapper ordenCompraSucursalMapper;
	
	@Autowired
	private com.cardif.satelite.soat.multisocio.dao.OrdenCompraPeMapper ordenCompraMultisocioMapper;
	
	@Autowired
	private DetalleReporteAPESEGMapper detalleReporteAPESEMapper;
	
	@Autowired
	private DetalleReporteSBSMapper detalleReporteSBSMapper;
	
	@Autowired
	private ConsultaSoatMapper consultaSoatMapper;
	
	
	@Override
	public List<ConsultaPostVentaSOAT> buscarPolizasPostVentaSOAT(Long codSocio, Long codCanal, String numPlaca, String numCertificado, String numDocumentoID, String nomContratante, Date fecVentaDesde, Date fecVentaHasta) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<ConsultaPostVentaSOAT> listaPostVentaSOAT = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + codSocio + ",	2.-" + codCanal + ", 3.-" + numPlaca + ", 4.-" + numCertificado +", 5.-" + numDocumentoID + ", 6.-" + nomContratante + ", 7.-" + fecVentaDesde + ", 8.-" + fecVentaHasta + "]");}
			codSocio = (null != codSocio && 0 < codSocio) ? codSocio : null;
			codCanal = (null != codCanal && 0 < codCanal) ? codCanal : null;
			numPlaca = StringUtils.isNotBlank(numPlaca) ? numPlaca : null;
			numCertificado = StringUtils.isNotBlank(numCertificado) ? numCertificado : null;
			numDocumentoID = StringUtils.isNotBlank(numDocumentoID) ? numDocumentoID : null;
			nomContratante = StringUtils.isNotBlank(nomContratante) ? nomContratante : null;
			fecVentaDesde = null != fecVentaDesde ? fecVentaDesde : SateliteUtil.getInitialCardifDate();
			fecVentaHasta = null != fecVentaHasta ? fecVentaHasta : new Date();
			
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + codSocio + ",	2.-" + codCanal + ", 3.-" + numPlaca + ", 4.-" + numCertificado +", 5.-" + numDocumentoID + ", 6.-" + nomContratante + ", 7.-" + fecVentaDesde + ", 8.-" + fecVentaHasta + "]");}
			
			
			listaPostVentaSOAT = detalleTramaDiariaMapper.selectPolizasPostVentaSOAT(codSocio, codCanal, numPlaca, numCertificado, numDocumentoID, nomContratante, fecVentaDesde, fecVentaHasta);
			int size = (null != listaPostVentaSOAT ? listaPostVentaSOAT.size() : 0);
			if (logger.isDebugEnabled()) {logger.debug("Output [Registros: " + size + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listaPostVentaSOAT;
	} //buscarPolizasPostVentaSOAT
	
	@Override
	public List<ReportePostVentaSOAT> buscarPolizasParaReportePostVentaSOAT(Long codSocio, Long codCanal, String numPlaca, String numCertificado, String numDocumentoID, String nomContratante, 
			Date fecVentaDesde, Date fecVentaHasta) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<ReportePostVentaSOAT> listaPostVentaSOAT = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + codSocio + ", 2.-" + codCanal + ", 3.-" + numPlaca + ", 4.-" + numCertificado +", 5.-" + numDocumentoID + ", 6.-" + nomContratante + ", 7.-" + fecVentaDesde + ", 8.-" + fecVentaHasta + "]");}
			codSocio = (null != codSocio && 0 < codSocio) ? codSocio : null;
			codCanal = (null != codCanal && 0 < codCanal) ? codCanal : null;
			numPlaca = StringUtils.isNotBlank(numPlaca) ? numPlaca : null;
			numCertificado = StringUtils.isNotBlank(numCertificado) ? numCertificado : null;
			numDocumentoID = StringUtils.isNotBlank(numDocumentoID) ? numDocumentoID : null;
			nomContratante = StringUtils.isNotBlank(nomContratante) ? nomContratante : null;
			fecVentaDesde = null != fecVentaDesde ? fecVentaDesde : SateliteUtil.getInitialCardifDate();
			fecVentaHasta = null != fecVentaHasta ? fecVentaHasta : new Date();
			
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + codSocio + ", 2.-" + codCanal + ", 3.-" + numPlaca + ", 4.-" + numCertificado +", 5.-" + numDocumentoID + ", 6.-" + nomContratante + ", 7.-" + fecVentaDesde + ", 8.-" + fecVentaHasta + "]");}
			
			
			listaPostVentaSOAT = detalleTramaDiariaMapper.selectReportePostVentaSOAT(codSocio, codCanal, numPlaca, numCertificado, numDocumentoID, nomContratante, fecVentaDesde, fecVentaHasta);
			int size = (null != listaPostVentaSOAT ? listaPostVentaSOAT.size() : 0);
			if (logger.isDebugEnabled()) {logger.debug("Output [Registros: " + size + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listaPostVentaSOAT;
	} //buscarPolizasParaReportePostVentaSOAT
	
	@Override
	public boolean validarNumeroDocValorado(String nroCertificado) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		boolean flag = false;
		
		try
		{
			int numlength  = nroCertificado.length();
			
			String numCertificado = nroCertificado.substring(0, numlength-6);
			String tipoCertificado = nroCertificado.substring(numlength-6, numlength-4);
			String anioCertificado = nroCertificado.substring(numlength-4, numlength);
			
			NumeroDocValorado numeroDocValorado = numeroDocValoradoMapper.selectCertificadoByParametros(numCertificado, anioCertificado, tipoCertificado);
			if (logger.isDebugEnabled()) {logger.debug("Se encontro el NUM_DOC_VALORADO_id: " + (null != numeroDocValorado ? numeroDocValorado.getId() : "NULL"));}
			
			if (null != numeroDocValorado)
			{
				if (Constantes.COD_NUM_DOC_VALORADO_EST_ACTIVO == numeroDocValorado.getAnulado())
				{
					if (Constantes.COD_NUM_DOC_VALORADO_DISPONIBLE == numeroDocValorado.getDisponible())
					{
						if (logger.isDebugEnabled()) {logger.debug("El numero de certificado [" + nroCertificado + "] esta DISPONIBLE.");}
						flag = true;
					}
					else
					{
						if (logger.isDebugEnabled()) {logger.debug("El numero de certificado [" + nroCertificado + "] se encuentra USADO.");}
						flag = false;
					}
				}
				else
				{
					if (logger.isDebugEnabled()) {logger.debug("El numero de certificado [" + nroCertificado + "] se encuentra ANULADO.");}
					flag = false;
				}
			}
			else
			{
				if (logger.isDebugEnabled()) {logger.debug("El numero de certificado [" + nroCertificado + "] no EXISTE.");}
				flag = false;
			}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return flag;
	} //validarNumeroDocValorado
	
	@Override
	public Long reasignarNumeroDocValorado(String nroCertificado, Long pkDetalleTramaDiaria) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		Long pkNumeroDocValorado = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Buscando el objeto de DetalleTramaDiairia [" + pkDetalleTramaDiaria + "].");}
			DetalleTramaDiaria detTramaDiaria = detalleTramaDiariaMapper.selectByPrimaryKey(pkDetalleTramaDiaria);
			
			String nroCertificadoAux = detTramaDiaria.getNroCertificado();
			if (StringUtils.isNotBlank(nroCertificadoAux))
			{
				if (logger.isInfoEnabled()) {logger.info("Anulando el numero de certificado asignado anteriormente a la poliza.");}
				String numeroCert = nroCertificadoAux.substring(0, 6); 
				String tipoCert = nroCertificadoAux.substring(6, 8);
				String anioCert = nroCertificadoAux.substring(8, 12);
				
				NumeroDocValorado numeroDocValorado = numeroDocValoradoMapper.selectCertificadoByParametros(numeroCert, anioCert, tipoCert);
				numeroDocValorado.setAnulado(Constantes.COD_NUM_DOC_VALORADO_EST_ANULADO);
				
				int rows = numeroDocValoradoMapper.updateByPrimaryKeySelective(numeroDocValorado);
				if (logger.isDebugEnabled()) {logger.debug("Se anulo el numero de certificado con ID: " + numeroDocValorado.getId() + ". Registros afectados: " + rows);}
			}
			else
			{
				if (logger.isDebugEnabled()) {logger.debug("No existe un numero de certificado para liberar, que este asignado a la poliza.");}
			}
			
			
			if (logger.isInfoEnabled()) {logger.info("Actualizando la informacion del objeto DetalleTramaDiairia [" + pkDetalleTramaDiaria + "]");}
			detTramaDiaria.setNroCertificado(nroCertificado);
			detTramaDiaria.setEstadoImpresion(Constantes.MOD_IMPRESION_ESTADO_REIMPRESO);
			
			detalleTramaDiariaMapper.updateCertificadoAndEstadoImpresion(detTramaDiaria);
			if (logger.isDebugEnabled()) {logger.debug("Se actualizo la informacion del objeto DetalleTramaDiairia.");}
			
			
			if (logger.isInfoEnabled()) {logger.info("Actualizando el estado del nuevo certificado [" + nroCertificado + "]");}
			String numeroCert = nroCertificado.substring(0, 6); 
			String tipoCert = nroCertificado.substring(6, 8);
			String anioCert = nroCertificado.substring(8, 12);
			
			NumeroDocValorado numeroDocValorado = numeroDocValoradoMapper.selectCertificadoByParametros(numeroCert, anioCert, tipoCert);
			numeroDocValorado.setDisponible(Constantes.COD_NUM_DOC_VALORADO_USADO);
			numeroDocValorado.setNroPolizaRel(detTramaDiaria.getNroPolizaPe());
			
			int rows = numeroDocValoradoMapper.updateByPrimaryKeySelective(numeroDocValorado);
			if (logger.isDebugEnabled()) {logger.debug("Se actualizo el estado del nuevo certificado con ID: " + numeroDocValorado.getId() + ". Filas afectadas: " + rows);}
			
			pkNumeroDocValorado = numeroDocValorado.getId();
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_ACTUALIZAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return pkNumeroDocValorado;
	} //reasignarNumeroDocValorado
	
	@Override
	public String buscarPDFByPoliza(Long pkDetalleTramaDiaria) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String pdfPoliza = null;
		
		try
		{
			if (logger.isInfoEnabled()) {logger.info("Input [1.-" + pkDetalleTramaDiaria +"]");}
			
			DetalleTramaDiaria detTramaDiaria = detalleTramaDiariaMapper.selectByPrimaryKey(pkDetalleTramaDiaria);
			if (logger.isDebugEnabled()) {logger.debug("Se encontro el objeto DetalleTramaDiaria: " + detTramaDiaria);}
			
			if (null != detTramaDiaria)
			{
				pdfPoliza = detTramaDiaria.getPdfCodificadoSinFirmar();
			}
			
			if (logger.isDebugEnabled()) {logger.debug("Output [PDF_Poliza: " + pdfPoliza + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return pdfPoliza;
	} //buscarPDFByPoliza
	
	@Override
	public DetalleTramaDiaria buscarDetalleTramaDiariaPorPK(Long pkDetalleTramaDiaria) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		DetalleTramaDiaria detalleTramaDiariaObj = null;
		
		try
		{
			//(CASE OBLIGATORIO  WHEN '0' THEN 'NO' WHEN '1' THEN 'SI' END)
			if (logger.isInfoEnabled()) {logger.info("Input [1.-" + pkDetalleTramaDiaria +"]");}
			
			detalleTramaDiariaObj = detalleTramaDiariaMapper.selectByPrimaryKey(pkDetalleTramaDiaria);
			if (logger.isDebugEnabled()) {logger.debug("Output [DetalleTramaDiaria: " + detalleTramaDiariaObj + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return detalleTramaDiariaObj;
	} //buscarDetalleTramaDiariaPorPK
	
	@Override
	public CategoriaClase buscarCategoriaClase(String tipoVehiculo) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		CategoriaClase categoriaClase = null;
		
		try
		{
			
			
			
			
		}
		catch (Exception e)
		{
			
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return categoriaClase;
	} //buscarCategoriaClase
	
	@Override
	public DetalleTramaDiariaMotivo buscarUltimoMotivoObservacion(Long idDetalleTramaDiaria, Integer tipoMotivo) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		DetalleTramaDiariaMotivo detTramaDiariaMotivo = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + idDetalleTramaDiaria + ", 2.-" + tipoMotivo + "]");}
			List<DetalleTramaDiariaMotivo> list = detTramaDiariaMotivoMapper.selectRowLastByPkDetalleTramaDiariaAndTipoMotivo(idDetalleTramaDiaria, tipoMotivo);
			
			if (null != list && 0 < list.size())
			{
				detTramaDiariaMotivo = list.get(0);
			}
			if (logger.isInfoEnabled()) {logger.info("Output [DetalleTramaDiariaMotivo" + detTramaDiariaMotivo + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return detTramaDiariaMotivo;
	} //buscarUltimoMotivoObservacion
	
	@Override
	public Long nuevoCertificadoPorReimpresion(String motivoReimpresion, String observacionReimpresion, Integer tipoMotivo, Long pkDetalleTramaDiaria, String usuario, 
			String nuevoNroCertificado,String canal) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		Long pkNuevoNumDocValorado = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + motivoReimpresion + ", 2.-" + observacionReimpresion +", 3.-" + tipoMotivo +", 4.-" + pkDetalleTramaDiaria + ", 5.-" + usuario + ", 6.-" + nuevoNroCertificado + "]");}
			DetalleTramaDiaria detalleTramaDiaria = detalleTramaDiariaMapper.selectByPrimaryKey(pkDetalleTramaDiaria);
			if (logger.isDebugEnabled()) {logger.debug("Se encontro el objeto DetalleTramaDiaria[" + detalleTramaDiaria + "].");}
			
			String numDocValoradoOLD = detalleTramaDiaria.getNroCertificado(); // el ANT
			
			
			// VALIDAR TIPO DE CANAL			
		//	if(canal.endsWith(Constantes.SOAT_CANAL_DIGITAL)){  EL CAMBIO DE LOGICA, ESTO APLICA A TODO LOS CANALES  FECHA DE CAMBIO: 09/01/2017 CONSULTA CON HEBER
				
				if(logger.isInfoEnabled()){logger.info("CANAL DIGITAL");}
				
				NumeroDocValorado numeroDocValorado = numeroDocValoradoMapper.selectCertificadoByParametros(
						nuevoNroCertificado.substring(0,nuevoNroCertificado.length()-6), 
						nuevoNroCertificado.substring(nuevoNroCertificado.length()-4, nuevoNroCertificado.length()), 
						nuevoNroCertificado.substring(nuevoNroCertificado.length()-6,nuevoNroCertificado.length()-4));
				
				if(null != numeroDocValorado){				
				{
						if (logger.isDebugEnabled()) {logger.debug("Dar de BAJA el antiguo NRO. de CERTIFICADO.");}				
						
						numeroDocValorado.setAnulado(Constantes.COD_NUM_DOC_VALORADO_EST_ANULADO);
						numeroDocValorado.setDisponible(Constantes.COD_NUM_DOC_VALORADO_USADO);
						numeroDocValorado.setNroPolizaRel(detalleTramaDiaria.getNroPolizaPe());		
						numeroDocValorado.setNumCertificado(numDocValoradoOLD);
						int rows = numeroDocValoradoMapper.updateByPrimaryKeySelective(numeroDocValorado);				
						if (logger.isInfoEnabled()) {logger.info("Se dio de BAJA el NRO. de CERTIFICADO[" + numeroDocValorado.getId() + "]. Registros afectados: " + rows);}
						
						}
						{
						
						if (logger.isDebugEnabled()) {logger.debug("Dar de ALTA el nuevo NRO. de CERTIFICADO.");}				
						
						numeroDocValorado.setAnulado(Constantes.COD_NUM_DOC_VALORADO_EST_ACTIVO);
						numeroDocValorado.setDisponible(Constantes.COD_NUM_DOC_VALORADO_USADO);
						numeroDocValorado.setNroPolizaRel(detalleTramaDiaria.getNroPolizaPe());
						numeroDocValorado.setNumCertificado(nuevoNroCertificado);
	
						int rows = numeroDocValoradoMapper.updateByPrimaryKeySelective(numeroDocValorado);
						if (logger.isInfoEnabled()) {logger.info("Se dio de ALTA el NRO. de CERTIFICADO[" + numeroDocValorado.getId() + "]. Registros afectados: " + rows);}
				
						}
				}else{
					if (logger.isDebugEnabled()) {logger.debug("ERROR DE CARGA NO ES UN CANAL DIGITAL");}		
				}					
				
			//} else{
				
				if(logger.isInfoEnabled()){logger.info("OTROS CANALES");}
				
				detalleTramaDiaria.setNroCertificadoAnt(numDocValoradoOLD);
				detalleTramaDiaria.setNroCertificado(nuevoNroCertificado);
				
				if (logger.isDebugEnabled()) {logger.debug("Guardando registro en la tabla DETALLE_TRAMA_DIARIA_MOTIVO.");}
				DetalleTramaDiariaMotivo detTramaDiariaMotivo = new DetalleTramaDiariaMotivo();
				
				detTramaDiariaMotivo.setIdDetalleTramaDiaria(pkDetalleTramaDiaria);
				detTramaDiariaMotivo.setMotivoDsc(motivoReimpresion);
				detTramaDiariaMotivo.setObservacionDsc(observacionReimpresion);
				detTramaDiariaMotivo.setTipoMotivo(tipoMotivo);
				detTramaDiariaMotivo.setUsuCreacion(usuario);
				detTramaDiariaMotivo.setFechaCreacion(Calendar.getInstance().getTime());
				
				int rows = detTramaDiariaMotivoMapper.insertSelective(detTramaDiariaMotivo);
				if (logger.isInfoEnabled()) {logger.info("Se guardo el registro en la tabla DETALLE_TRAMA_DIARIA_MOTIVO[" + detTramaDiariaMotivo.getId() +"]. Registros afectados: " + rows);}		
			
				int rowsupd = detalleTramaDiariaMapper.updateAltaDeImpresion(detalleTramaDiaria);
				if (logger.isInfoEnabled()) {logger.info("Se actualizo la informacion de la tabla DETALLE_TRAMA_DIARIA [" + pkDetalleTramaDiaria + "]. Registros afectados: " + rowsupd);}
		
			//}
		
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_ALTA_DE_REIMPRESION);
		}
		
		
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return pkNuevoNumDocValorado;
	} //nuevoCertificadoPorReimpresion

	@Override
	public boolean actualizarEstadoImpresionDePoliza(Long pkDetalleTramaDiaria, String estadoImpresion) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		boolean isOk = false;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + pkDetalleTramaDiaria + ", 2.-" + estadoImpresion +"]");}
			
			DetalleTramaDiaria detalleTramaDiaria = detalleTramaDiariaMapper.selectByPrimaryKey(pkDetalleTramaDiaria);
			if (logger.isDebugEnabled()) {logger.debug("Se encontro el objeto DetalleTramaDiaria[" + detalleTramaDiaria + "].");}
			
			detalleTramaDiaria.setEstadoImpresion(estadoImpresion);
			
			int rows = detalleTramaDiariaMapper.updateEstadoImpresion(detalleTramaDiaria);
			
			isOk = true;
			if (logger.isInfoEnabled()) {logger.info("Se actualizo la informacion de la tabla DETALLE_TRAMA_DIARIA [" + pkDetalleTramaDiaria + "]. Registros afectadaos: " + rows);}			
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_ACTUALIZAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return isOk;
	} //actualizarEstadoImpresionDePoliza
	
	@Override
	public DetalleTramaDiaria actualizarPolizaPostVenta(DetalleTramaDiaria detalleTramaDiariaBean) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		DetalleTramaDiaria detalleTramaDiaria = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + detalleTramaDiariaBean + "]");}
			
			detalleTramaDiaria = detalleTramaDiariaMapper.selectByPrimaryKey(detalleTramaDiariaBean.getId());
			if (logger.isDebugEnabled()) {logger.debug("Se encontro el objeto DetalleTramaDiaria(" + (null != detalleTramaDiaria ? detalleTramaDiaria.getId() : 0) + "): " + detalleTramaDiaria);}
			
			detalleTramaDiaria.setPlaca(detalleTramaDiariaBean.getPlaca());
			detalleTramaDiaria.setSerie(detalleTramaDiariaBean.getSerie());
			detalleTramaDiaria.setNroAsientos(detalleTramaDiariaBean.getNroAsientos());
			detalleTramaDiaria.setAnioFab(detalleTramaDiariaBean.getAnioFab());
			
			detalleTramaDiaria.setCodCategoriaClase(detalleTramaDiariaBean.getCodCategoriaClase());
			detalleTramaDiaria.setCategoriaClase(detalleTramaDiariaBean.getCategoriaClase());
			detalleTramaDiaria.setCodMarca(detalleTramaDiariaBean.getCodMarca());
			detalleTramaDiaria.setMarca(detalleTramaDiariaBean.getMarca());
			detalleTramaDiaria.setCodModelo(detalleTramaDiariaBean.getCodModelo());
			detalleTramaDiaria.setModelo(detalleTramaDiariaBean.getModelo());
			detalleTramaDiaria.setCodUsoVehiculo(detalleTramaDiariaBean.getCodUsoVehiculo());
			detalleTramaDiaria.setUsoVehiculo(detalleTramaDiariaBean.getUsoVehiculo());
			
			detalleTramaDiaria.setTipDoc(detalleTramaDiariaBean.getTipDoc());
			detalleTramaDiaria.setNumDoc(detalleTramaDiariaBean.getNumDoc());
			detalleTramaDiaria.setFecNac(detalleTramaDiariaBean.getFecNac());
			detalleTramaDiaria.setSexo(detalleTramaDiariaBean.getSexo());
			
			detalleTramaDiaria.setApelPateContrat(detalleTramaDiariaBean.getApelPateContrat());
			detalleTramaDiaria.setApelMateContrat(detalleTramaDiariaBean.getApelMateContrat());
			detalleTramaDiaria.setNomContrat(detalleTramaDiariaBean.getNomContrat());
			
			detalleTramaDiaria.setFecIniVigencia(detalleTramaDiariaBean.getFecIniVigencia());
			detalleTramaDiaria.setFecFinVigencia(detalleTramaDiariaBean.getFecFinVigencia());
			
			detalleTramaDiaria.setPdfCodificadoSinFirmar(detalleTramaDiariaBean.getPdfCodificadoSinFirmar());
			
			if (logger.isDebugEnabled()) {logger.debug("Se termino de pasar los datos al objeto. Procedemos a actualizar el Objeto.");}
			int rows = detalleTramaDiariaMapper.updateByPrimaryKeySelective(detalleTramaDiaria);
			
			if (logger.isInfoEnabled()) {logger.info("Se actualizo el objeto DetalleTramaDiaria(" + detalleTramaDiaria.getId() + "). Registros afectados: " + rows);}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_ACTUALIZAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return detalleTramaDiaria;
	} //actualizarPolizaPostVenta

	@Override
	public DetalleTramaDiaria anularPolizaPostVentaSOAT(String motivoAnulacion, String observacionAnulacion, Integer tipoMotivo, Long pkDetalleTramaDiaria, String usuario)
			throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		DetalleTramaDiaria detalleTramaDiaria = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + motivoAnulacion + ", 2.-" + observacionAnulacion + ", 3.-" + tipoMotivo + ", 4.-" + pkDetalleTramaDiaria + ", 5.-" + usuario + "]");}
			
			detalleTramaDiaria = detalleTramaDiariaMapper.selectByPrimaryKey(pkDetalleTramaDiaria);
			if (logger.isDebugEnabled()) {logger.debug("Se encontro el objeto DetalleTramaDiaria[" + detalleTramaDiaria + "].");}
			
			
			/*
			 * Verificando el tipo de CANAL DE VENTA
			 */
			if (StringUtils.isNotBlank(detalleTramaDiaria.getCanalVenta()) && detalleTramaDiaria.getCanalVenta().trim().equalsIgnoreCase(Constantes.SOAT_CANAL_DIGITAL))
			{
				ConsultaProductoSocio producto = productoMapper.selectByPkDetalleTramaDiaria(pkDetalleTramaDiaria);
				if (logger.isInfoEnabled()) {logger.info("Se encontro el producto relacionado al PK: " + pkDetalleTramaDiaria);}
				
				OrdenCompraPe ordenCompraPe = null;
				if (producto.getNombreProducto().equalsIgnoreCase(Constantes.PRODUCTO_NOMBRE_FALABELLA)) /* SOAT DIGITAL Falabella */
				{
					ordenCompraPe = ordenCompraFalabellaMapper.selectByPkPolizaPe(detalleTramaDiaria.getIdPoliza());
					if (logger.isDebugEnabled()) {logger.debug("Se obtuvo el objeto OrdenCompraPe(" + ordenCompraPe.getOrdenCompra() + ") del PRODUCTO: " + Constantes.PRODUCTO_NOMBRE_FALABELLA);}
					
					ordenCompraPe.setEstadoPago(Constantes.POLIZA_ESTADO_ANULADO);
					ordenCompraPe.setGlosaEstadoPago("La Orden de Compra " + ordenCompraPe.getOrdenCompra() + " asociada a la Poliza " + detalleTramaDiaria.getIdPoliza() + " ha sido anulada por el usuario: " + usuario);
					
					int rows = ordenCompraFalabellaMapper.updateByPrimaryKeySelective(ordenCompraPe);
					if (logger.isInfoEnabled()) {logger.info("Se anulo la Orden de Compra: " + ordenCompraPe.getOrdenCompra() + " asociada a la Poliza: " + detalleTramaDiaria.getIdPoliza() + ". Registros afectados: " + rows);}
				}
				else if (producto.getNombreProducto().equalsIgnoreCase(Constantes.PRODUCTO_NOMBRE_RIPLEY)) /* SOAT DIGITAL Ripley */
				{
					ordenCompraPe = ordenCompraRipleyMapper.selectByPkPolizaPe(detalleTramaDiaria.getIdPoliza());
					if (logger.isDebugEnabled()) {logger.debug("Se obtuvo el objeto OrdenCompraPe(" + ordenCompraPe.getOrdenCompra() + ") del PRODUCTO: " + Constantes.PRODUCTO_NOMBRE_RIPLEY);}
					
					ordenCompraPe.setEstadoPago(Constantes.POLIZA_ESTADO_ANULADO);
					ordenCompraPe.setGlosaEstadoPago("La Orden de Compra " + ordenCompraPe.getOrdenCompra() + " asociada a la Poliza " + detalleTramaDiaria.getIdPoliza() + " ha sido anulada por el usuario: " + usuario);
					
					int rows = ordenCompraRipleyMapper.updateByPrimaryKeySelective(ordenCompraPe);
					if (logger.isInfoEnabled()) {logger.info("Se anulo la Orden de Compra: " + ordenCompraPe.getOrdenCompra() + " asociada a la Poliza: " + detalleTramaDiaria.getIdPoliza() + ". Registros afectados: " + rows);}
				}
				else if (producto.getNombreProducto().equalsIgnoreCase(Constantes.PRODUCTO_NOMBRE_CENCOSUD)) /* SOAT DIGITAL Cencosud */
				{
					ordenCompraPe = ordenCompraCencosudMapper.selectByPkPolizaPe(detalleTramaDiaria.getIdPoliza());
					if (logger.isDebugEnabled()) {logger.debug("Se obtuvo el objeto OrdenCompraPe(" + ordenCompraPe.getOrdenCompra() + ") del PRODUCTO: " + Constantes.PRODUCTO_NOMBRE_CENCOSUD);}
					
					ordenCompraPe.setEstadoPago(Constantes.POLIZA_ESTADO_ANULADO);
					ordenCompraPe.setGlosaEstadoPago("La Orden de Compra " + ordenCompraPe.getOrdenCompra() + " asociada a la Poliza " + detalleTramaDiaria.getIdPoliza() + " ha sido anulada por el usuario: " + usuario);
					
					int rows = ordenCompraCencosudMapper.updateByPrimaryKeySelective(ordenCompraPe);
					if (logger.isInfoEnabled()) {logger.info("Se anulo la Orden de Compra: " + ordenCompraPe.getOrdenCompra() + " asociada a la Poliza: " + detalleTramaDiaria.getIdPoliza() + ". Registros afectados: " + rows);}
				}
				else if (producto.getNombreProducto().equalsIgnoreCase(Constantes.PRODUCTO_NOMBRE_DIRECTO)) /* SOAT DIGITAL Directo */
				{
					ordenCompraPe = ordenCompraDirectoMapper.selectByPkPolizaPe(detalleTramaDiaria.getIdPoliza());
					if (logger.isDebugEnabled()) {logger.debug("Se obtuvo el objeto OrdenCompraPe(" + ordenCompraPe.getOrdenCompra() + ") del PRODUCTO: " + Constantes.PRODUCTO_NOMBRE_DIRECTO);}
					
					ordenCompraPe.setEstadoPago(Constantes.POLIZA_ESTADO_ANULADO);
					ordenCompraPe.setGlosaEstadoPago("La Orden de Compra " + ordenCompraPe.getOrdenCompra() + " asociada a la Poliza " + detalleTramaDiaria.getIdPoliza() + " ha sido anulada por el usuario: " + usuario);
					
					int rows = ordenCompraDirectoMapper.updateByPrimaryKeySelective(ordenCompraPe);
					if (logger.isInfoEnabled()) {logger.info("Se anulo la Orden de Compra: " + ordenCompraPe.getOrdenCompra() + " asociada a la Poliza: " + detalleTramaDiaria.getIdPoliza() + ". Registros afectados: " + rows);}
				}
				else if (producto.getNombreProducto().equalsIgnoreCase(Constantes.PRODUCTO_NOMBRE_COMPARA_BIEN)) /* SOAT DIGITAL ComparaBien */
				{
					ordenCompraPe = ordenCompraComparaBienMapper.selectByPkPolizaPe(detalleTramaDiaria.getIdPoliza());
					if (logger.isDebugEnabled()) {logger.debug("Se obtuvo el objeto OrdenCompraPe(" + ordenCompraPe.getOrdenCompra() + ") del PRODUCTO: " + Constantes.PRODUCTO_NOMBRE_COMPARA_BIEN);}
					
					ordenCompraPe.setEstadoPago(Constantes.POLIZA_ESTADO_ANULADO);
					ordenCompraPe.setGlosaEstadoPago("La Orden de Compra " + ordenCompraPe.getOrdenCompra() + " asociada a la Poliza " + detalleTramaDiaria.getIdPoliza() + " ha sido anulada por el usuario: " + usuario);
					
					int rows = ordenCompraComparaBienMapper.updateByPrimaryKeySelective(ordenCompraPe);
					if (logger.isInfoEnabled()) {logger.info("Se anulo la Orden de Compra: " + ordenCompraPe.getOrdenCompra() + " asociada a la Poliza: " + detalleTramaDiaria.getIdPoliza() + ". Registros afectados: " + rows);}
				}
				else if (producto.getNombreProducto().equalsIgnoreCase(Constantes.PRODUCTO_NOMBRE_SUCURSAL)) /* SOAT DIGITAL Sucursal */
				{
					ordenCompraPe = ordenCompraSucursalMapper.selectByPkPolizaPe(detalleTramaDiaria.getIdPoliza());
					if (logger.isDebugEnabled()) {logger.debug("Se obtuvo el objeto OrdenCompraPe(" + ordenCompraPe.getOrdenCompra() + ") del PRODUCTO: " + Constantes.PRODUCTO_NOMBRE_SUCURSAL);}
					
					ordenCompraPe.setEstadoPago(Constantes.POLIZA_ESTADO_ANULADO);
					ordenCompraPe.setGlosaEstadoPago("La Orden de Compra " + ordenCompraPe.getOrdenCompra() + " asociada a la Poliza " + detalleTramaDiaria.getIdPoliza() + " ha sido anulada por el usuario: " + usuario);
					
					int rows = ordenCompraSucursalMapper.updateByPrimaryKeySelective(ordenCompraPe);
					if (logger.isInfoEnabled()) {logger.info("Se anulo la Orden de Compra: " + ordenCompraPe.getOrdenCompra() + " asociada a la Poliza: " + detalleTramaDiaria.getIdPoliza() + ". Registros afectados: " + rows);}
				}
				else
				{
					logger.error("El nombre del producto no cuenta con opcion para ANULAR en los SOAT DIGITALES. Nombre: " + producto.getNombreProducto());
				}
				
				
				if (logger.isDebugEnabled()) {logger.debug("Verificar si existe anulacion en SOAT DIGITAL Multisocio.");}
				ParametroAutomat multisocioParam = parametroAutomatMapper.getParametro(Constantes.COD_PARAM_SOCIOS_MULTISOCIO, producto.getNombreProducto());
				
				if (null != multisocioParam)
				{
					if (logger.isDebugEnabled()) {logger.debug("Existe registro de la relacion entre el 'NOMBRE DEL PRODUCTO y el NOMBRE DEL SOCIO para SOAT MULTISOCIO' en la tabla PARAMETRO_AUTOMAT.");}
					
					ordenCompraPe = ordenCompraMultisocioMapper.selectByPkPolizaPeAndSocio(detalleTramaDiaria.getIdPoliza(), multisocioParam.getNombreValor());
					if (null != ordenCompraPe)
					{
						ordenCompraPe.setEstadoPago(Constantes.POLIZA_ESTADO_ANULADO);
						ordenCompraPe.setGlosaEstadoPago("La Orden de Compra " + ordenCompraPe.getOrdenCompra() + " asociada a la Poliza " + detalleTramaDiaria.getIdPoliza() + " ha sido anulada por el usuario: " + usuario);
						
						int rows = ordenCompraMultisocioMapper.updateByPrimaryKeySelective(ordenCompraPe);
						if (logger.isInfoEnabled()) {logger.info("Se anulo la Orden de Compra: " + ordenCompraPe.getOrdenCompra() + " asociada a la Poliza: " + detalleTramaDiaria.getIdPoliza() + ". Registros afectados: " + rows);}
					}
					else
					{
						if (logger.isDebugEnabled()) {logger.debug("No se encontro el registro de la ORDEN DE COMPRA en la opcion MULTISOCIO.");}
					}
				}
				else
				{
					if (logger.isDebugEnabled()) {logger.debug("No se encontro registro PARAMETRO_AUTOMAT para la opcion MULTISOCIO.");}
				}
			}
			else
			{
				if (logger.isInfoEnabled()) {logger.info("La poliza a anular no es de tipo de canal DIGITAL. No se anula a nivela del SOAT DIGITAL.");}
			}
			
			
			/*
			 * Guardando el registro en DETALLE_TRAMA_DIARIA_MOTIVO
			 */
			{
				if (logger.isDebugEnabled()) {logger.debug("Guardando el registro en la tabla DETALLE_TRAMA_DIARIA_MOTIVO.");}
				DetalleTramaDiariaMotivo detTramaDiariaMotivo = new DetalleTramaDiariaMotivo();
				
				detTramaDiariaMotivo.setIdDetalleTramaDiaria(pkDetalleTramaDiaria);
				detTramaDiariaMotivo.setMotivoDsc(motivoAnulacion);
				detTramaDiariaMotivo.setObservacionDsc(observacionAnulacion);
				detTramaDiariaMotivo.setTipoMotivo(tipoMotivo);
				detTramaDiariaMotivo.setUsuCreacion(usuario);
				detTramaDiariaMotivo.setFechaCreacion(Calendar.getInstance().getTime());
				
				int rows = detTramaDiariaMotivoMapper.insertSelective(detTramaDiariaMotivo);
				if (logger.isInfoEnabled()) {logger.info("Se guardo el registro en la tabla DETALLE_TRAMA_DIARIA_MOTIVO[" + detTramaDiariaMotivo.getId() + "]. Registros afectados: " + rows);}
			}
			
			
			/*
			 * Anulando registro DETALLE_TRAMA_DIARIA
			 */
			detalleTramaDiaria.setEstado(Constantes.POLIZA_ESTADO_ANULADO);
			
			int rows = detalleTramaDiariaMapper.updateByPrimaryKeySelective(detalleTramaDiaria);
			if (logger.isInfoEnabled()) {logger.info("Se anulo la POLIZA en el registro [" + detalleTramaDiaria.getId() + "] de la tabla DETALLE_TRAMA_DIARIA. Registros afectados: " + rows);}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return detalleTramaDiaria;
	} //anularPolizaPostVentaSOAT

	@Override
	public DetalleReporteAPESEG buscarUltimoReporteAPESEG(Long pkDetalleTramaDiaria) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		DetalleReporteAPESEG detalleReporteAPESEG = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + pkDetalleTramaDiaria + "]");}
			
			List<DetalleReporteAPESEG> list = detalleReporteAPESEMapper.selectRowLastByPkDetalleTramaDiaria(pkDetalleTramaDiaria);
			if (null != list && 0 < list.size())
			{
				detalleReporteAPESEG = list.get(0);
			}
			if (logger.isInfoEnabled()) {logger.info("Output [DetalleReporteAPESEG: " + detalleReporteAPESEG + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return detalleReporteAPESEG;
	} //buscarUltimoReporteAPESEG

	@Override
	public DetalleReporteSBS buscarUltimoReporteSBS(Long pkDetalleTramaDiaria) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		DetalleReporteSBS detalleReporteSBS = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + pkDetalleTramaDiaria + "]");}
			
			List<DetalleReporteSBS> list = detalleReporteSBSMapper.selectRowLastByPkDetalleTramaDiaria(pkDetalleTramaDiaria);
			if (null != list && 0 < list.size())
			{
				detalleReporteSBS = list.get(0);
			}
			if (logger.isInfoEnabled()) {logger.info("Output [DetalleReporteSBS: " + detalleReporteSBS + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return detalleReporteSBS;
	} //buscarUltimoReporteSBS

	@Override
	public List<RepPolizasSOATBean> buscarPolizasSOAT(Long codSocio,Long codCanal, String numPlaca, String numCertificado,String numDocumentoID, String nomContratante,	Date fecVentaDesde, Date fecVentaHasta)  throws SyncconException{
		
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<RepPolizasSOATBean> listaPostVentaSOAT = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + codSocio + ",	2.-" + codCanal + ", 3.-" + numPlaca + ", 4.-" + numCertificado +", 5.-" + numDocumentoID + ", 6.-" + nomContratante + ", 7.-" + fecVentaDesde + ", 8.-" + fecVentaHasta + "]");}
			codSocio = (null != codSocio && 0 < codSocio) ? codSocio : null;
			codCanal = (null != codCanal && 0 < codCanal) ? codCanal : null;
			numPlaca = StringUtils.isNotBlank(numPlaca) ? numPlaca : null;
			numCertificado = StringUtils.isNotBlank(numCertificado) ? numCertificado : null;
			numDocumentoID = StringUtils.isNotBlank(numDocumentoID) ? numDocumentoID : null;
			nomContratante = StringUtils.isNotBlank(nomContratante) ? nomContratante : null;
			fecVentaDesde = null != fecVentaDesde ? fecVentaDesde : SateliteUtil.getInitialCardifDate();
			fecVentaHasta = null != fecVentaHasta ? fecVentaHasta : new Date();
			
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + codSocio + ",	2.-" + codCanal + ", 3.-" + numPlaca + ", 4.-" + numCertificado +", 5.-" + numDocumentoID + ", 6.-" + nomContratante + ", 7.-" + fecVentaDesde + ", 8.-" + fecVentaHasta + "]");}
			
			
			//listaPostVentaSOAT = detalleTramaDiariaMapper.selectPolizasPostVentaSOAT(codSocio, codCanal, numPlaca, numCertificado, numDocumentoID, nomContratante, fecVentaDesde, fecVentaHasta);
			listaPostVentaSOAT = detalleTramaDiariaMapper.selectConsultaVentaSOAT(codSocio, codCanal, numPlaca, numCertificado, numDocumentoID, nomContratante, fecVentaDesde, fecVentaHasta);
			int size = (null != listaPostVentaSOAT ? listaPostVentaSOAT.size() : 0);
			if (logger.isDebugEnabled()) {logger.debug("Output [Registros: " + size + "]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listaPostVentaSOAT;
	}
	
	public boolean anulacionConsultaSoat(String nroPlaca) throws SyncconException{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		boolean seAnula = false;
		try
		{
			ConsultaSoat consultaSoat1 = consultaSoatMapper.consultaPoliza(nroPlaca);
			if(consultaSoat1.getCodigoVenta().intValue() > 0){
				ConsultaSoat consultaSoat2 = consultaSoatMapper.consultaCoberturaGastosMedicos(consultaSoat1.getCodigoVenta());
				ConsultaSoat consultaSoat3 = consultaSoatMapper.consultaOtrasCoberturas(consultaSoat1.getCodigoVenta());
				if(consultaSoat2.getCantCoberturas().intValue() > 0 || consultaSoat3.getCantOtrasCoberturas().intValue() > 0 ){
					seAnula = true;
				}
			}
			
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return seAnula;
	}
	
} //PostVentaSOATServiceImpl
