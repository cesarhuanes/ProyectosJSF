package com.cardif.satelite.tesoreria.service;

import java.util.Date;
import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.tesoreria.model.ComprobanteElectronico;

public interface ConsultaCertificadoRetencionService
{
	
	public abstract List<ComprobanteElectronico> buscarCertificadosRetencion(String idUnidadNegocio, Date fecEmisionDesde, Date fecEmisionHasta, 
			String nroCertificadoDesde, String nroCertificadoHasta, String codEstadoCertificado) throws SyncconException;
	
}
