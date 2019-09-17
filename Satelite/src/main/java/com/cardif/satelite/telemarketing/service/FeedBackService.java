package com.cardif.satelite.telemarketing.service;

import java.io.File;

import com.cardif.framework.excepcion.SyncconException;

/**
 * Clase para la generacion de Archivos FeedBack para el Socio <br>
 * Ticket T022163
 * 
 * @author 2ariasju
 * @since 07/02/2014
 */
public interface FeedBackService {

  public void generarArchivo(Long codBase, String codSocio, File archivo) throws SyncconException;

  public void cargarArchivo(Long codBase, String codSocio, File excelFile) throws SyncconException;
}
