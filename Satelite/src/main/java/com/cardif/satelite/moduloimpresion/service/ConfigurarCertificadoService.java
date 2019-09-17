package com.cardif.satelite.moduloimpresion.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.moduloimpresion.NumeroDocValorado;

public interface ConfigurarCertificadoService
{
	
	/**
	 * Este metodo busca los certificados publicos disponibles para un socio determinado.
	 * 
	 * @param codProducto
	 * 		Codigo del producto.
	 * @return
	 * 		Retorna la lista de certificados publicos disponibles.
	 * @throws SyncconException
	 */
	public abstract List<NumeroDocValorado> buscarCertificadosPublicos(Long codProducto) throws SyncconException;
	
	/**
	 * Este metodo busca los certificados privados disponibles para un socio determinado.
	 * 
	 * @param codProducto
	 * 		Codigo del producto.
	 * @return
	 * 		Retorna la lista de certificados privados disponibles.
	 * @throws SyncconException
	 */
	public abstract List<NumeroDocValorado> buscarCertificadosPrivados(Long codProducto) throws SyncconException;
	
	/**
	 * Este metodo inserta los numeros de documentos valorados PUBLICOS desde un rango inicial a final y en base al año.
	 * 
	 * @param rangoDesde
	 * 		Rango inicial de los documentos valorados a insertar.
	 * @param rangoHasta
	 * 		Rango final de los documentos valorados a insertar.
	 * @param anioCertificado
	 * 		Anio al que pertenece el rango de documentos valorados.
	 * @param codProducto
	 * 		Codigo del producto al cual se le asignan los numeros de documentos valorados.
	 * @return
	 * 		Retorna el número de documentos insertados.
	 * @throws SyncconException
	 */
	public abstract String[] insertarRangoCertificadosPublicos(Integer rangoDesde, Integer rangoHasta, String anioCertificado, Long codProducto) throws SyncconException;
	
	/**
	 * Este metodo inserta los numeros de documentos valorados PRIVADOS desde un rango inicial a final y en base al año.
	 * 
	 * @param rangoDesde
	 * 		Rango inicial de los documentos valorados a insertar.
	 * @param rangoHasta
	 * 		Rango final de los documentos valorados a insertar.
	 * @param anioCertificado
	 * 		Anio al que pertenece el rango de documentos valorados.
	 * @param codProducto
	 * 		Codigo del producto al cual se le asignan los numeros de documentos valorados.
	 * @return
	 * 		Retorna el número de documentos insertados.
	 * @throws SyncconException
	 */
	public abstract String[] insertarRangoCertificadosPrivados(Integer rangoDesde, Integer rangoHasta, String anioCertificado, Long codProducto) throws SyncconException;
	
	/**
	 * Este metodo elimina un numero de documento valorado de la tabla.
	 * 
	 * @param numCertificado
	 * 		Numero correlativo del documento valorado.
	 * @param tipoCertificado
	 * 		Tipo del documento valorado.
	 * @param anioCertificado
	 * 		Año del documento valorado.
	 * @param codProducto
	 * 		Producto a quien se le ha asignado el documento valorado.
	 * @return
	 * 		Retorna un texto indicando si la operacion fue exitosa.
	 * @throws SyncconException
	 */
	public abstract String eliminarCertificadoByParametros(String numCertificado, String tipoCertificado, Integer anioCertificado, Long codProducto) throws SyncconException;
	
} //ConfigurarCertificadoService
