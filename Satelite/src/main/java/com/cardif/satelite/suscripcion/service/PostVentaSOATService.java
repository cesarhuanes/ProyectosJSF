package com.cardif.satelite.suscripcion.service;

import java.util.Date;
import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.CategoriaClase;
import com.cardif.satelite.model.reportes.DetalleReporteAPESEG;
import com.cardif.satelite.model.reportes.DetalleReporteSBS;
import com.cardif.satelite.model.satelite.DetalleTramaDiaria;
import com.cardif.satelite.model.satelite.DetalleTramaDiariaMotivo;
import com.cardif.satelite.suscripcion.bean.ConsultaPostVentaSOAT;
import com.cardif.satelite.suscripcion.bean.RepPolizasSOATBean;
import com.cardif.satelite.suscripcion.bean.ReportePostVentaSOAT;

/**
 * 
 * 
 * @author INFOPARQUEPERU
 * 		   Jose Manuel Lucas Barrera (josemlucasb@gmail.com)
 */
public interface PostVentaSOATService
{
	
	/**
	 * Este metodo busca las polizas en la tabla DETALLE_TRAMA_DIARIA, para la pagina de CONSULTA POST VENTA SOAT.
	 * 
	 * @param codCanal
	 * @param codProducto
	 * @param numPlaca
	 * @param numCertificado
	 * @param numDocumentoID
	 * @param nomContratante
	 * @param fecVentaDesde
	 * @param fecVentaHasta
	 * @return
	 * @throws SyncconException
	 */
	public List<ConsultaPostVentaSOAT> buscarPolizasPostVentaSOAT(Long codSocio, Long codCanal, String numPlaca, String numCertificado, String numDocumentoID, String nomContratante, Date fecVentaDesde, Date fecVentaHasta) throws SyncconException;
	
	/**
	 * Este metodo busca las polizas segun los filtros ingresados para realizar el reporte.
	 * 
	 * @param codProducto
	 * @param numPlaca
	 * @param numCertificado
	 * @param numDocumentoID
	 * @param nomContratante
	 * @param fecVentaDesde
	 * @param fecVentaHasta
	 * @return
	 * @throws SyncconException
	 */
	public List<ReportePostVentaSOAT> buscarPolizasParaReportePostVentaSOAT(Long codSocio, Long codCanal, String numPlaca, String numCertificado, String numDocumentoID, String nomContratante, Date fecVentaDesde, Date fecVentaHasta) throws SyncconException;
	
	public boolean validarNumeroDocValorado(String nroCertificado) throws SyncconException;
	
	public Long reasignarNumeroDocValorado(String nroCertificado, Long pkDetalleTramaDiaria) throws SyncconException;
	
	public String buscarPDFByPoliza(Long pkDetalleTramaDiaria) throws SyncconException;
	
	public DetalleTramaDiaria buscarDetalleTramaDiariaPorPK(Long pkDetalleTramaDiaria) throws SyncconException;

	public CategoriaClase buscarCategoriaClase(String tipoVehiculo) throws SyncconException;
	
	public DetalleTramaDiariaMotivo buscarUltimoMotivoObservacion(Long idDetalleTramaDiaria, Integer tipoMotivo) throws SyncconException;

	public Long nuevoCertificadoPorReimpresion(String motivoReimpresion, String observacionReimpresion, Integer tipoMotivo, Long pkDetalleTramaDiaria, 
			String usuario, String nuevoNroCertificado,String canal) throws SyncconException;
	
	public boolean actualizarEstadoImpresionDePoliza(Long pkDetalleTramaDiaria, String estadoImpresion) throws SyncconException;
	
	public DetalleTramaDiaria actualizarPolizaPostVenta(DetalleTramaDiaria detalleTramaDiariaBean) throws SyncconException;

	public DetalleTramaDiaria anularPolizaPostVentaSOAT(String motivoAnulacion, String observacionAnulacion, Integer tipoMotivo, Long pkDetalleTramaDiaria, String usuario) throws SyncconException;

	public DetalleReporteAPESEG buscarUltimoReporteAPESEG(Long pkDetalleTramaDiaria) throws SyncconException;
	
	public DetalleReporteSBS buscarUltimoReporteSBS(Long pkDetalleTramaDiaria) throws SyncconException;

	public List<RepPolizasSOATBean> buscarPolizasSOAT(Long codSocio,Long codCanal, String numPlaca, String numCertificado,String numDocumentoID, String nombreContratante,Date fechaVentaDesde, Date fechaVentaHasta)  throws SyncconException;
	
	public boolean anulacionConsultaSoat(String nroPlaca) throws SyncconException;
} //PostVentaSOATService
