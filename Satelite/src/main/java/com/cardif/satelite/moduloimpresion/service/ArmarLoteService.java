package com.cardif.satelite.moduloimpresion.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.moduloimpresion.LoteImpresion;
import com.cardif.satelite.model.moduloimpresion.NumeroDocValorado;
import com.cardif.satelite.model.satelite.DetalleTramaDiaria;
import com.cardif.satelite.moduloimpresion.bean.ConsultaArmarLote;
import com.cardif.satelite.moduloimpresion.bean.ConsultaConfirmacionImpresion;

/**
 * 
 * 
 * @author INFOPARQUEPERU
 * 		   Jose Manuel Lucas Barrera
 */
public interface ArmarLoteService 
{
	
	public List<ConsultaArmarLote> buscarPolizas(Long codProducto, String numPlaca, String codUsoVehiculo, String codDepartamento, String codProvincia, String codDistrito, Date fecCompraDesde, Date fecCompraHasta, Date fecEntregaHasta, String rangoHorario) throws SyncconException;
	
	public Map<Long, Boolean> validarNumeroDocValorados(List<ConsultaArmarLote> listaValidarCertificadoLote) throws SyncconException;
	
	public DetalleTramaDiaria actualizarPolizaImpresa(Long pkDetTramaDiaria, String numeroDocVal, String modImpresionEstadoImpreso) throws SyncconException;
	
	public LoteImpresion insertarLoteImpresion(String modImpresionEstadoPendiente, String pdfBase64, Long codProducto, String usuario, List<ConsultaArmarLote> listaLImpresionArmado) throws SyncconException;
	
	public NumeroDocValorado actualizarEstadoNumDocValorado(String numeroDocVal, int estadoNumDocValorado, String nroPolizaPe) throws SyncconException;
	
	public List<ConsultaConfirmacionImpresion> buscarPolizasParaConfirmarImpresion(Long numLote) throws SyncconException;
	
	public boolean confirmarImpresionPorPoliza(ConsultaConfirmacionImpresion cConfirmacionImpresion, ConsultaConfirmacionImpresion cConfirmacionImpresionAux) throws SyncconException;
	
	public boolean actualizarEstadoLoteImpresion(Long numLote, String modImpresionEstadoImpreso) throws SyncconException;
	
	public Map<Long, Boolean> validarNumeroDoc(List<ConsultaArmarLote> listaValidarCertificadoLote) throws SyncconException;
	
} //ArmarLoteService
