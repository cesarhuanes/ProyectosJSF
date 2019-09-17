package com.cardif.satelite.suscripcion.service;

import java.util.Date;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.suscripcion.bean.RepPolizasSOATBean;

import java.util.List;

/**
 * 
 * 
 * @author INFOPARQUEPERU
 * 		   Jose Manuel Lucas Barrera
 */
public interface RepConsultaSOATService
{
	
	/**
	 * Este metodo busca la lista de polizas de los diferentes SOCIOS, almacenadas en la tabla CONCI_TRAMA_DIARIA.
	 * 
	 * @param codSocio
	 * 		Codigo del socio.
	 * @param numPlaca
	 * 		Numero de placa del vehiculo.
	 * @param numCertificado
	 * 		Numero de certificado asignado a la poliza.
	 * @param numDocumentoID
	 * 		Numero de documento de identidad del contratante.
	 * @param nombreContratante
	 * 		Apellidos y nombres del contratante.
	 * @param fechaVentaDesde
	 * 		Fecha de venta de la poliza (Parametro inicial)
	 * @param fechaVentaHasta
	 * 		Fecha de venta de la poliza (Parametro final)
	 * @return
	 * 		Retorna la lista de objetos RepPolizasSOATBean, los cuales contienen la informacion de las diferentes
	 * 		polizas en base a los filtros seleccionados.
	 * @throws SyncconException
	 */
	public List<RepPolizasSOATBean> buscarPolizasSOAT(String codSocio, String numPlaca, String numCertificado, String numDocumentoID, String nombreContratante,
			Date fechaVentaDesde, Date fechaVentaHasta) throws SyncconException;
	
} //RepConsultaSOATService
