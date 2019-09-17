package com.cardif.satelite.telemarketing.service;

import java.math.BigDecimal;
import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.datamart.OdsPolizaHm;
import com.cardif.satelite.model.datamart.OdsProductoDe;
import com.cardif.satelite.model.datamart.OdsSocioMm;
import com.cardif.satelite.telemarketing.bean.ReporteTLMK;

/**
 * @class ConsultaTelemarketingService
 * @usuario jhurtado
 * @fecha_creacion 31-01-2014
 * @ticket T022163
 * @descripcion: interfaces para la carga de combos
 * 
 */
public interface ConsultaTelemarketingService {

  public List<OdsSocioMm> obtenerSocios() throws SyncconException;

  public List<OdsPolizaHm> obtenerCodigoProducto(OdsPolizaHm odsphm) throws SyncconException;

  public List<OdsProductoDe> obtenerProductos(String codigoProducto) throws SyncconException;

  public List<ReporteTLMK> obtenerReporteAD(Integer codProducto, BigDecimal codSocio, String fechaIni, String fechaFin, int cuotaDe, int cuotaA, String segmento) throws SyncconException;

}
