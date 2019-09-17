package com.cardif.satelite.suscripcion.service;

import java.util.Date;
import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.MigRegVentas;
import com.cardif.satelite.model.TstVentas;
import com.cardif.satelite.suscripcion.bean.ConsultaTstVentas;

public interface RegVentasService {

  public boolean existePeriodo(int periodo) throws SyncconException;

  public void insertar(MigRegVentas migRegVentas) throws SyncconException;

  public List<ConsultaTstVentas> buscar(String correlativo, String tipComprobante, String producto, String poliza, String nroSerie, String correlativoSerie, String tipDocumento, String numDocumento,
      Date fechaDesde, Date fechaHasta) throws SyncconException;

  public List<ConsultaTstVentas> buscarNoReferenciados(String correlativo, String tipComprobante, String producto, String poliza, String nroSerie, String correlativoSerie, String tipDocumento,
      String numDocumento, Date fechaDesde, Date fechaHasta) throws SyncconException;

  public List<ConsultaTstVentas> buscarPorCorrelativo(String correlativo) throws SyncconException;

  public TstVentas obtener(Long pk) throws SyncconException;

  public void actualizar(TstVentas tstVentas) throws SyncconException;
}
