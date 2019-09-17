package com.cardif.satelite.tesoreria.service;

import java.util.Date;
import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.satelite.CargoCentralizado;

public interface CargoCentralizadoService {

  public List<CargoCentralizado> obtenerListaCargoCentralizado() throws SyncconException;

  public CargoCentralizado obtenerPorPK(Long pk) throws SyncconException;

  public List<CargoCentralizado> obtenerComprobantesAprobados(Date filAprobFechaRegistroContableDesde, Date filAprobFechaRegistroContableHasta, String filAprobSistema, String filAprobNroComprobante,
      String filAprobRucDni, Date filAprobFecEmision, String filAprobRazonSocial, String filAprobMoneda, Date filAprobFecVencimiento, Date filAprobFechaCargaDesde, Date filAprobFechaCargaHasta,
      Date filAprobFecPago, String filAprobEstado) throws SyncconException;

  public boolean updateCombrobantesAprobados(List<CargoCentralizado> listaComprobantesAporbados, Date updateFecEntregaTesoreria, Date updateFecRecepcionContable, Date fecModificacion,
      String usuarioModificacion) throws SyncconException;

  public boolean updateCombrobantesFechaEntregaTesoreria(List<CargoCentralizado> listaComprobantesAprobados, Date updateFecEntregaTesoreria, Date fecModificacion, String usuarioModificacion)
      throws SyncconException;

  public boolean updateCombrobantesFechaRecepcionContable(List<CargoCentralizado> listaComprobantesAprobados, Date updateFecRecepcionContable, Date fecModificacion, String usuarioModificacion)
      throws SyncconException;

  public boolean updateCombrobantesFechaNull(List<CargoCentralizado> listaComprobantesAprobados, Date fecModificacion, String usuarioModificacion) throws SyncconException;

  public List<CargoCentralizado> obtenerComprobantesRechazados(String filRechazadoSistema, String filRechazadoNroComprobante, String filRechazadoRucDni, Date filRechazadoFecEmision,
      String filRechazadoRazonSocial, Date filRechazadoFechaCargaDesde, Date filRechazadoFechaCargaHasta) throws SyncconException;

}
