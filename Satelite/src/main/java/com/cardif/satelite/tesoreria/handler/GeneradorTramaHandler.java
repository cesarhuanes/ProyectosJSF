package com.cardif.satelite.tesoreria.handler;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.tesoreria.bean.ConsultaCertRetencionReversa;
import com.cardif.satelite.util.Utilitarios;
import com.cardif.sunsystems.util.ConstantesSun;
import com.cardif.sunsystems.util.Utilidades;

public class GeneradorTramaHandler
{
	public final Logger logger = Logger.getLogger(GeneradorTramaHandler.class);
	
	
	private GeneradorTramaHandler() {}
	
	public static synchronized GeneradorTramaHandler newInstance()
	{
		return new GeneradorTramaHandler();
	}
	
	
	/**
	 * Este metodo genera la trama de REVERSION de los certificados de RETENCION anulados.
	 * 
	 * Estructura de la Trama de Reversion:
	 * 		RAZONSOCIAL|RUC|TIPO_DOC_ID|FECHA_EMISION_CRE|TIPO_DOC|SERIE|CORRELATIVO|MOTIVO|NUM_ITEM|FECHA_GENERACION
	 * 
	 * @param listaCertRetencionReversa
	 * 		Lista de certificados de RETENCION anulados.
	 * @return
	 * 		Retorna la trama de REVERSION en formato String.
	 * @throws Exception
	 */
	public String generaTramaReversion(List<ConsultaCertRetencionReversa> listaCertRetencionReversa) throws Exception
	{
		if (logger.isDebugEnabled()) {logger.debug("Inicio");}
		String tramaReversion = new String("");
		
		/* Fecha de la generacion de la trama */
		String fechaGeneracion = Utilitarios.convertirFechaACadena(Calendar.getInstance().getTime(), ConstantesSun.UTL_FORMATO_FECHA_PAPERLESS);
		
		if (null != listaCertRetencionReversa && !listaCertRetencionReversa.isEmpty())
		{
			int nroItem = 0;
			for (ConsultaCertRetencionReversa bean : listaCertRetencionReversa)
			{
				String[] nroComprobanteEle = bean.getNroComprobanteEle().split("-");
				
				String serieCertificado = nroComprobanteEle[0];
				String correlativoCertificado = Utilidades.completarCeros(nroComprobanteEle[1], "8");
				nroItem++;
				
				tramaReversion += (bean.getProRazonSocial() + "|" + bean.getProNroDocumento() + "|" + bean.getProTipoDocumento() + "|" + Utilitarios.convertirFechaACadena(bean.getFechaEmision(), ConstantesSun.UTL_FORMATO_FECHA_PAPERLESS) + "|" +
						bean.getTipoComprobante() + "|" + serieCertificado + "|" + correlativoCertificado + "|" + bean.getMotivoAnulacion() + "|" + nroItem + "|" + fechaGeneracion + "\n"); 
			}
		}
		else
		{
			logger.error("No existe lista de certificados de RETENCION.");
			throw new SyncconException(ErrorConstants.COD_ERROR_LISTA_TRAMA_REVERSION_VACIA);
		}
		if (logger.isDebugEnabled()) {logger.debug("Fin");}
		return tramaReversion;
	} //generaTramaReversion
	
//	/**
//	 * Este metodo genera la trama de RETENCION del CERTIFICADO DE RETENCION.
//	 * 
//	 * Estructura de la Trama de Retencion:
//	 * 		EN|TIPO_COMPROBANTE|NRO_COMPROBANTE|FECHA_EMISION|RUC_EMISOR
//	 * 
//	 * 
//	 * @param comprobanteElectronico
//	 * 		Objeto ComprobanteElectronicoBean utilizado para extraer la informacion y generar la trama.
//	 * @param usuarioSunat
//	 * 		Objeto UsuarioSunat que contiene la informacion del EMISOR electronico.
//	 * @return
//	 * 		Retorna la trama de RETENCION.
//	 * @throws SyncconException
//	 */
//	public String generaTramaRetencion(ComprobanteElectronicoBean comprobanteElectronico, UsuarioSunat usuarioSunat) 
//			throws SyncconException
//	{
//		if (logger.isInfoEnabled()) {logger.info("Inicio");}
//		String tramaRetencion = new String("");
//		
//		try
//		{
//			String nroCertificado = comprobanteElectronico.getEnNroComprobante();
//			
//			String enFechaEmision = null;
//			String enProTipoDocIdent = null;
//			String enProNombreComercial = null;
//			String enProDomUbigeo = null;
//			String urbanizacion = null;
//			String tasaRetencion = null;
//			String observaciones = null;
//			String peCorreoCliente = null;
//			String peTipoCambio = null;
//			String pePagoWeb = null;
//			
//			
//			if (logger.isDebugEnabled())
//			{
//				logger.debug("[" + nroCertificado + "] RUC: " + usuarioSunat.getRUC() + "\tRazonSocial: " + usuarioSunat.getRazonSocial() + 
//						"\tNombreComercial: " + usuarioSunat.getNombreComercial() + "\tDireccionFiscal: " + usuarioSunat.getDireccionFiscal());
//			}
//				
//			enFechaEmision = Utilitarios.convertirFechaACadena(comprobanteElectronico.getEnFechaEmision(), ConstantesSun.UTL_FORMATO_FECHA_PAPERLESS);
//			enProTipoDocIdent = StringUtils.isNotBlank(comprobanteElectronico.getEnProTipoDocIdent()) ? comprobanteElectronico.getEnProTipoDocIdent() : ConstantesSun.FEL_TIP_DOC_IDENT_RUC;
//			
//			if (StringUtils.isNotBlank(comprobanteElectronico.getEnProNombreComercial()))
//			{
//				enProNombreComercial = comprobanteElectronico.getEnProRazonSocial();
//			}
//			else if (StringUtils.isNotBlank(comprobanteElectronico.getEnProRazonSocial()))
//			{
//				enProNombreComercial = comprobanteElectronico.getEnProRazonSocial();
//			}
//			else
//			{
//				throw new SyncconException(ErrorConstants.COD_ERROR_TRAMA_RETENCION_ERROR);
//			}
//				
//			if (StringUtils.isNotBlank(comprobanteElectronico.getEnProDomUbigeo()))
//			{
//				enProDomUbigeo = comprobanteElectronico.getEnProDomUbigeo();
//			}
//			else
//			{
//				throw new SyncconException(ErrorConstants.COD_ERROR_TRAMA_RETENCION_ERROR);
//			}
//			
//			urbanizacion = ""; //No existe urbanizacion definida para el EMISOR
//			tasaRetencion = "3"; //Valor por defecto
//			observaciones = ""; //No existen observaciones definidas
//			
//			// Preparamos los datos el encabezado "EN"
//		    String encabezadoEN = "EN|" + ConstantesSun.FEL_TIP_COMP_RETENCION + "|" + comprobanteElectronico.getEnNroComprobante() + "|" + enFechaEmision + "|" + usuarioSunat.getRUC() + "|" + ConstantesSun.FEL_TIP_DOC_IDENT_RUC
//		        + "|" + usuarioSunat.getNombreComercial() + "|" + usuarioSunat.getRazonSocial() + "|" + ConstantesSun.UTL_UBIGEO_CARDIF_COD + "|" + usuarioSunat.getDireccionFiscal() + "|" + urbanizacion + "|" + ConstantesSun.UTL_UBIGEO_CARDIF_PROVINCIA + "|"
//		        + ConstantesSun.UTL_UBIGEO_CARDIF_DEPARTAMENTO + "|" + ConstantesSun.UTL_UBIGEO_CARDIF_DISTRITO + "|" + ConstantesSun.UTL_UBIGEO_CARDIF_PAIS + "|"
//		        + comprobanteElectronico.getEnProNroDocIdent() + "|" + enProTipoDocIdent + "|" + enProNombreComercial + "|"
//		        + comprobanteElectronico.getEnProRazonSocial() + "|" + enProDomUbigeo + "|" + comprobanteElectronico.getEnProDomDireccionCompleta() + "|"
//		        + comprobanteElectronico.getEnProDomUrbanizacion() + "|" + comprobanteElectronico.getEnProDomProvincia() + "|" + comprobanteElectronico.getEnProDomDepartamento() + "|"
//		        + comprobanteElectronico.getEnProDomDistrito() + "|" + ConstantesSun.UTL_UBIGEO_CARDIF_PAIS + "|" + ConstantesSun.FEL_COD_REG_RETENCION + "|" + tasaRetencion + "|" + observaciones + "|"
//		        + comprobanteElectronico.getEnRetPerImporteRetenidoPercibido() + "|" + ConstantesSun.UTL_MONEDA_SOLES + "|" + comprobanteElectronico.getEnRetPerImportePagadoCobrado() + "|"
//		        + ConstantesSun.UTL_MONEDA_SOLES;
//			
//			tramaRetencion = encabezadoEN + "\n";
//			if (logger.isDebugEnabled()) {logger.debug("Encabezado EN: " + encabezadoEN);}
//			
//			
//			List<ComprobanteRelacionadoBean> listaComprobanteRelacionados = comprobanteElectronico.getComprobanteRelacionado();
//			if (null != listaComprobanteRelacionados && 0 < listaComprobanteRelacionados.size())
//			{
//				for (ComprobanteRelacionadoBean comprobanteRelacionado : listaComprobanteRelacionados)
//				{
//					/* Preparamos los datos del Comprobante Relacionado CR */
//					String crFechaEmision = Utilitarios.convertirFechaACadena(comprobanteRelacionado.getCrFechaEmision(), ConstantesSun.UTL_FORMATO_FECHA_PAPERLESS);
//					
//					String[] crNroComprobanteSplit = comprobanteRelacionado.getCrNroComprobante().split("-");
//					String crSerieNroComprobanteRelacionado = "";
//					if (0 < crNroComprobanteSplit.length)
//					{
//						crNroComprobanteSplit[0] = Utilidades.completarCeros(crNroComprobanteSplit[0], "4");
//						crNroComprobanteSplit[1] = Utilidades.completarCeros(crNroComprobanteSplit[1], "8");
//					
//						crSerieNroComprobanteRelacionado = crNroComprobanteSplit[0] + "-" + crNroComprobanteSplit[1];
//					}
//					else
//					{
//						crSerieNroComprobanteRelacionado = comprobanteRelacionado.getCrNroComprobante();
//					}
//					
//					String comprobanteRelacionadoCR = "CR|" + comprobanteRelacionado.getCrTipoComprobante() + "|" + crSerieNroComprobanteRelacionado + "|" +
//							crFechaEmision + "|" + comprobanteRelacionado.getCrImporteTotal() + "|" + comprobanteRelacionado.getCrTipoMoneda();
//					
//					tramaRetencion += (comprobanteRelacionadoCR + "\n");
//					if (logger.isDebugEnabled()) {logger.debug("Comprobante Relacionado CR: " + comprobanteRelacionadoCR);}
//					
//					
//					/* Preparamos los datos del Pago (R) o Cobro (P) "PC" */
//					String pcFechaPago = Utilitarios.convertirFechaACadena(comprobanteRelacionado.getPcFechaPagoCobro(), ConstantesSun.UTL_FORMATO_FECHA_PAPERLESS);
//					
//					String datosPagoCobroPC = "PC|" + pcFechaPago + "|" + comprobanteRelacionado.getPcNroPagoCobro() + "|" +
//							comprobanteRelacionado.getPcImportePagoSinRetCob() + "|" + comprobanteRelacionado.getPcTipoMoneda();
//					
//					tramaRetencion += (datosPagoCobroPC + "\n");
//					if (logger.isDebugEnabled()) {logger.debug("Datos Pago Cobro PC: " + datosPagoCobroPC);}
//					
//					
//					/* Preparamos los datos de la Retencion o Percepcion "RP" */
//					String rpFechaRetencion = Utilitarios.convertirFechaACadena(comprobanteRelacionado.getRpFechaRetPer(), ConstantesSun.UTL_FORMATO_FECHA_PAPERLESS);
//					
//					String datosRetencionPercepcionRP = "RP|" + comprobanteRelacionado.getRpImporteRetPer() + "|" + comprobanteRelacionado.getRpMonedaRetPer() + "|" + rpFechaRetencion + "|" +
//							comprobanteRelacionado.getRpImporteNetPagoCobro() + "|" + comprobanteRelacionado.getRpMonedaNetPagoCobro();
//					
//					tramaRetencion += (datosRetencionPercepcionRP + "\n");
//					if (logger.isDebugEnabled()) {logger.debug("Datos Retencion Percepcio RP: " + datosRetencionPercepcionRP);}
//					
//					
//					/* Preparamos el Tipo de Cambio "TC" */
//					String tcFechaTipoCambio = Utilitarios.convertirFechaACadena(comprobanteRelacionado.getTcFechaCambio(), ConstantesSun.UTL_FORMATO_FECHA_PAPERLESS);
//					
//					String datosTipoCambioTC = "TC|" + comprobanteRelacionado.getTcMonedaOrigen() + "|" + comprobanteRelacionado.getTcMonedaDestino() + "|" +
//							comprobanteRelacionado.getTcTasaCambio() + "|" + tcFechaTipoCambio;
//					
//					tramaRetencion += (datosTipoCambioTC + "\n");
//					if (logger.isDebugEnabled()) {logger.debug("Datos Tipo Cambio TC: " + datosTipoCambioTC + "\n\n");}
//				} //for
//			}
//			else
//			{
//				throw new SyncconException(ErrorConstants.COD_ERROR_TRAMA_RETENCION_ERROR);
//			}
//			
//			
//			/* Preparamos los datos Personalizados "PE" */
//			if (StringUtils.isNotBlank(comprobanteElectronico.getPeCorreoCliente()))
//			{
//				peCorreoCliente = comprobanteElectronico.getPeCorreoCliente();
//			}
//			else
//			{
//				peCorreoCliente = "";
//			}
//			
//			if (StringUtils.isNotBlank(comprobanteElectronico.getPeTipoCambio()))
//			{
//				peTipoCambio = comprobanteElectronico.getPeTipoCambio();
//			}
//			else
//			{
//				peTipoCambio = "";
//			}
//			
//			if (StringUtils.isNotBlank(comprobanteElectronico.getPePagWeb()))
//			{
//				pePagoWeb = comprobanteElectronico.getPePagWeb();
//			}
//			else
//			{
//				pePagoWeb = "";
//			}
//			
//			String datosPersonalizadosPE = "PE|CorreoCliente|" + peCorreoCliente + "\nPE|TipoCambio|" + peTipoCambio +
//					"\nPE|MontoLetras|" + comprobanteElectronico.getPeMontoLetras() + "\nPE|PagWeb|" + pePagoWeb; 
//			
//			tramaRetencion += (datosPersonalizadosPE + "\n");
//			if (logger.isDebugEnabled()) {logger.debug("Datos Personalizados PE: " + datosPersonalizadosPE);}
//			
//			
//			if (logger.isInfoEnabled()){logger.info("##################################### TRAMA GENERADA FINAL #####################################\n" + tramaRetencion);}
//		}
//		catch (SyncconException e)
//		{
//			logger.error("SyncconException - ERROR: " + e.getMessage());
//			logger.error("SyncconException -->" + ExceptionUtils.getStackTrace(e));
//			throw e;
//		}
//		catch (Exception e)
//		{
//			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
//			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
//			throw new SyncconException(ErrorConstants.COD_ERROR_TRAMA_RETENCION_ERROR);
//		}
//		if (logger.isInfoEnabled()) {logger.info("Fin");}
//		return tramaRetencion;
//	} //generaTramaRetencion
	
} //GeneradorTramaHandler
