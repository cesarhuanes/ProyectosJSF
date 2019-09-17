package com.cardif.satelite.suscripcion.service;

import java.io.File;
import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.satelite.ConciCabTramaMensual;
import com.cardif.satelite.suscripcion.bean.SocioProductoBean;

/**
 * @author torresgu2
 * @create 2014.07.11
 * @version 0.1
 * @description Carga de la Trama Mensual para Conciliacion SOAT
 */
public interface CargaTramaMensualService {
	
//  public void cargaTramaTXT(ConciTramaMensual conciTramaMensual) throws SyncconException;
//
//  public void cargaTramaXLS(File excelFile, Long secuencia, String producto) throws SyncconException;
//
//  public void insertarCabecera(ConciCabTramaMensual conciCabTramaMensual) throws SyncconException;
//  /**
//   * insertar Cabecera
//   * **/
//  public Long obtener(String producto) throws SyncconException;
//
//  public List<ConciCabTramaMensual> buscar(String producto) throws SyncconException;
//
//  public void eliminar(Long codSecuencia) throws SyncconException;
//
//  public void actualiza(ConciCabTramaMensual conciCabTramaMensual, Long codSecuencia) throws SyncconException;
//
//  public void actualizaTramaMensual(Long codSecuencia) throws SyncconException;
//
//  public Long consultar(Long codSecuencia) throws SyncconException;
//
//  public boolean consultaPeriodo(Integer periodo, String producto) throws SyncconException;
	
  //hacia abajo
  public int numeroRegistrosExcel(File archivoFinal) throws SyncconException;

  public boolean insertar(ConciCabTramaMensual conciCabTramaMensual) throws SyncconException;

  public List<ConciCabTramaMensual> buscarSocio(String producto) throws SyncconException;
  
  public void eliminar(Long codSecuencia) throws SyncconException;

  public List<SocioProductoBean> listaSocioProducto(Integer idcanal) throws SyncconException;

  public Boolean executePakage();

  
}
