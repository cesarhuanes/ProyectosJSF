package com.cardif.satelite.tesoreria.service;

import java.util.Date;
import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.tesoreria.bean.ConsultaCertRetencionReversa;

public interface GeneracionReversaComprobantesService
{
	
	public abstract List<ConsultaCertRetencionReversa> buscarCertificadosRetencionReversar(String idUnidadNegocio, Date fecEmision, String nroCertificado, String rucProveedor) throws SyncconException;
	
	public abstract void actualizarEstadoCertRetencionReversa(List<ConsultaCertRetencionReversa> listaCertRetencionReversa, String estadoCertificadoRetencion, Integer plazoFechaSunat) throws SyncconException;
	
	public abstract boolean actualizarEstadoSunsystems(List<ConsultaCertRetencionReversa> listaCertRetencionReversa, String estadoCertificadoRetencion) throws SyncconException;
	
} //GeneracionReversaComprobantesService
