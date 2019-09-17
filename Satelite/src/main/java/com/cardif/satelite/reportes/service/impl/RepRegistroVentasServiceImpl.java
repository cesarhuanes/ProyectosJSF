package com.cardif.satelite.reportes.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.dao.ParametroAutomatMapper;
import com.cardif.satelite.configuracion.dao.ProductoMapper;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.ParametroAutomat;
import com.cardif.satelite.model.reportes.DetalleReporteRegVenta;
import com.cardif.satelite.moduloimpresion.bean.ConsultaProductoSocio;
import com.cardif.satelite.reportes.bean.RepRegistroVentaBean;
import com.cardif.satelite.reportes.dao.DetalleReporteRegVentaMapper;
import com.cardif.satelite.reportes.service.RepRegistroVentasService;
import com.cardif.satelite.suscripcion.dao.DetalleTramaDiariaMapper;

@Service("repRegistroVentasService")
public class RepRegistroVentasServiceImpl implements RepRegistroVentasService
{
	private final Logger logger = Logger.getLogger(RepRegistroVentasServiceImpl.class);
	
	@Autowired
	private DetalleTramaDiariaMapper detTramaDiariaMapper;
	
	@Autowired
	private DetalleReporteRegVentaMapper detReporteRegVentaMapper;
	
	@Autowired
	private ParametroAutomatMapper parametroMapper;
	
	@Autowired
	private ProductoMapper productoMapper;
	
	@Override
	public List<RepRegistroVentaBean> buscarPolizasRegistroVentas(Long codProducto, Date fecEmisionDesde, Date fecEmisionHasta)
			throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		List<RepRegistroVentaBean> listaRegistroVentas = null; 
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + codProducto + ", 2.-" + fecEmisionDesde + ", 3.-" + fecEmisionHasta + "]");}
			
			listaRegistroVentas = detTramaDiariaMapper.selectPolizasRegistroVentas(codProducto, fecEmisionDesde, fecEmisionHasta);
			if (logger.isInfoEnabled()) {logger.info("Output [Registros_DetalleTramaDiaria: " + (null != listaRegistroVentas ? listaRegistroVentas.size() : 0) + "]");}			
			
			List<RepRegistroVentaBean> listaAdicionalNuevaVta = detReporteRegVentaMapper.selectPolizasNuevaVenta(codProducto, fecEmisionDesde, fecEmisionHasta);
			if (logger.isInfoEnabled()) {logger.info("Output [Registros_RepRegistroVentaBean: " + (null != listaAdicionalNuevaVta ? listaAdicionalNuevaVta.size() : 0) + "]");}
			
			
			if (null != listaRegistroVentas)
			{
				listaRegistroVentas.addAll(null != listaAdicionalNuevaVta ? listaAdicionalNuevaVta : null);
			}
			else
			{
				listaRegistroVentas = new ArrayList<RepRegistroVentaBean>();
				listaRegistroVentas.addAll(null != listaAdicionalNuevaVta ? listaAdicionalNuevaVta : null);
			}
			
			//Validar si el producto/socio esta obligado a registrar la fecha de venta en trama diaria (Tabla PRODUCTO: Campo INDICADOR_REGVENTA)
			ConsultaProductoSocio producto = productoMapper.selectIndicadorRegFechaVentaByProducto(new Long(codProducto));
			
			if(producto.getIndicadorRegVenta().equals(Constantes.INDICADOR_REGVENTA_OBLIGATORIO_POR_PRODUCTO)){
				//1:Obligados: jalan de la cabecera (tabla: TRAMA_DIARIA / campo: FEC_CARGA) -- Jalar y poner para todos los registros en el reporte.
				for(int i=0; i<listaRegistroVentas.size(); i++){
					listaRegistroVentas.get(i).setFechaEmision(listaRegistroVentas.get(i).getFechaCarga());
				}
				
			}else{
				//0:No Obligados: jalan del campo (tabla: DETALLE_TRAMA_DIARIA / campo: FEC_VENTA)
				for(int i=0; i<listaRegistroVentas.size(); i++){
					listaRegistroVentas.get(i).setFechaEmision(listaRegistroVentas.get(i).getFechaVenta());
				}
			}
			
			/* Agregando CORRELATIVO DE REGISTRO */
			for (int i=0; i <listaRegistroVentas.size(); i++)
			{
				listaRegistroVentas.get(i).setCorrelativoRegistro(Long.valueOf(i+1));
			}
			
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return listaRegistroVentas;
	} //buscarPolizasRegistroVentas
	
	@Override
	public DetalleReporteRegVenta registrarNuevaVenta(Long codProducto, Date fecEmision, String tipoDocumento, String nroDocumento, String nomRazonSocial, Double importeTotal, String tipoComprobante)
			throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		DetalleReporteRegVenta detReporteRegVenta = null;
		
		try
		{
			if (logger.isDebugEnabled()) {logger.debug("Input [1.-" + codProducto + ", 2.-" + fecEmision + ", 3.-" + tipoDocumento + ", 4.-" + nroDocumento + ", 5.-" + nomRazonSocial + "]");}
			
			detReporteRegVenta = new DetalleReporteRegVenta();
			detReporteRegVenta.setTipoDocumento(obtenerTipoDocCliente(tipoDocumento));
			detReporteRegVenta.setNroDocumento(nroDocumento);
			detReporteRegVenta.setFecEmision(fecEmision);
			detReporteRegVenta.setNombreRazonsocial(nomRazonSocial);
			detReporteRegVenta.setImporteTotal(importeTotal);
			detReporteRegVenta.setTipoComp(tipoComprobante);
			detReporteRegVenta.setIdProducto(codProducto);
			
			int rows = detReporteRegVentaMapper.insertSelective(detReporteRegVenta);
			if (logger.isInfoEnabled()) {logger.info("Output [Se registro el objeto DetalleReporteRegVenta, filas afectadas: " + rows +"]");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_INSERTAR);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return detReporteRegVenta;
	} //registrarNuevaVenta
	
	private String obtenerTipoDocCliente(String tipoDocumento) throws Exception
	{
		String tipoDocCliente = null;
		try
		{
			ParametroAutomat parametro = parametroMapper.getParametro(Constantes.COD_PARAM_TIP_DOC_REG_VTA, tipoDocumento);
			
			if (null != parametro)
			{
				tipoDocCliente = parametro.getNombreValor();
			}
		}
		catch (Exception e)
		{
			throw e;
		}
		return tipoDocCliente;
	} //obtenerTipoDocCliente
	
} 
