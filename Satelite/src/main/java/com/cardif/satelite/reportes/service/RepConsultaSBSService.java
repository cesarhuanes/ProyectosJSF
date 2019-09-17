package com.cardif.satelite.reportes.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.reportes.ReporteSBS;
import com.cardif.satelite.model.reportes.SBSPeriodo;
import com.cardif.satelite.reportes.bean.RepAnulacionPrimaSBSBean;
import com.cardif.satelite.reportes.bean.RepConsultaSBSBean;
import com.cardif.satelite.reportes.bean.RepContratoSoatSBSBean;
import com.cardif.satelite.reportes.bean.RepProduccionPrimaSBSBean;

public interface RepConsultaSBSService
{
	
	public List<SBSPeriodo> buscarPorTipoAnexo(String tipoAnexo) throws SyncconException;
	
	public List<RepConsultaSBSBean> buscarReporteSBSCabecera(String codTipoAnexo, Integer anio, Long codPeriodo) throws SyncconException;
	
	public String obtenerArchivoAdjuntoSBS(Long pkReporteSBS) throws SyncconException;
	
	public boolean verificarExisteReporte(String codTipoAnexo, Integer anio, Long codPeriodo) throws SyncconException;
		
	public List<RepProduccionPrimaSBSBean> extraerSBSProduccionPrimas(Integer anio, Long codPeriodo, Double tipoCambioUSD) throws SyncconException;
	
	public List<RepAnulacionPrimaSBSBean> extraerSBSAnulacionPrimas(Integer anio, Long codPeriodo, Double tipoCambioUSD) throws SyncconException;
	
	public List<RepContratoSoatSBSBean> extraerSBSContratosComercioSOAT(Integer anio, Long codPeriodo, Double tipoCambioUSD) throws SyncconException;

	public ReporteSBS guardarDatosReporte(String codTipoAnexo, Integer anio, Long codPeriodo, Double tipoCambioUSD, byte[] reporteBytes, Object listaReporte) throws SyncconException;
	
	public void confirmarReporteSBS(Long pkReporteSBS) throws SyncconException;
	
} //RepConsultaSBSService
