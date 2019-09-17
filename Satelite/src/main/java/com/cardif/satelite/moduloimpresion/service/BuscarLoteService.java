package com.cardif.satelite.moduloimpresion.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.moduloimpresion.LoteImpresion;
import com.cardif.satelite.moduloimpresion.bean.ConsultaArmarLote;
import com.cardif.satelite.moduloimpresion.bean.ConsultaConfirmacionImpresion;
import com.cardif.satelite.moduloimpresion.bean.ConsultaDocumentoCurier;
import com.cardif.satelite.moduloimpresion.bean.ConsultaLoteImpresion;

/**
 * Este clase contiene todos los metodos para manipular el proceso de 'Buscar Lote'.
 * 
 * @author INFOPARQUEPERU
 * 		   Jose Manuel Lucas Barrera
 */
public interface BuscarLoteService
{
	
	/**
	 * Este metodo implementara la busqueda de la lista de lotes segun los parametros de entrada.
	 * Busca en la tabla 'LOTE_IMPRESION'.
	 * 
	 * @param codProducto
	 * 		Codigo del producto.
	 * @param estadoLote
	 * 		codigo del estado del lote.
	 * @param numLote
	 * 		Identificador del lote.
	 * @param fecCreacionDesde
	 * 		Intervalo inicial de la fecha de creacion del lote.
	 * @param fecCreacionHasta
	 * 		Intervalo final de la fecha de creacion del lote.
	 * @return
	 * 		Retorna una lista con los objetos LoteImpresion.
	 * @throws SyncconException
	 */
	public List<ConsultaLoteImpresion> buscarLotesDeImpresion(Long codProducto, String estadoLote, Long numLote, Date fecCreacionDesde, Date fecCreacionHasta) throws SyncconException;
	
	/**
	 * Este metodo implementa la busqueda de un objeto LoteImpresion en base a su llave primaria.
	 * 
	 * @param pkLoteImpresion
	 * 		Llave primaria del objeto LoteImpresion a buscar.
	 * @return
	 * 		Retorna el objeto LoteImpresion.
	 * @throws SyncconException
	 */
	public LoteImpresion buscarLoteImpresionByPk(Long pkLoteImpresion) throws SyncconException;
	
	/**
	 * Este metodo implementa la busqueda de las polizas que seran exportadas para el CURIER en base a un
	 * numero de lote en especifico.
	 * 
	 * @param pkLoteImpresion
	 * 		Llave primaria del objeto LoteImpresion.
	 * @return
	 * 		Retorna una lista de las polizas que seran exportadas para el CURIER.
	 * @throws SyncconException
	 */
	public List<ConsultaDocumentoCurier> buscarPolizasCurierByPkLote(Long pkLoteImpresion) throws SyncconException;

	public List<ConsultaConfirmacionImpresion> buscarPolizasParaConfirmarImpresion(Long numLote) throws SyncconException;

	public boolean confirmarImpresionPorPoliza(ConsultaConfirmacionImpresion cConfirmacionImpresion, ConsultaConfirmacionImpresion cConfirmacionImpresionAux) throws SyncconException;

	public boolean actualizarEstadoLoteImpresion(Long numLote, String modImpresionEstadoImpreso) throws SyncconException;
	
	public Map<Long, Boolean> validarNumeroDoc(List<ConsultaArmarLote> listaValidarCertificadoLote) throws SyncconException;
	
	public Map<Long, Boolean> validarNumeroDocValorados(List<ConsultaArmarLote> listaValidarCertificadoLote) throws SyncconException;
	
} //BuscarLoteService
