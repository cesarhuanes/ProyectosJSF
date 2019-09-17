package com.cardif.satelite.siniestro.service;

import java.util.Date;
import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.SiniSiniestro;
import com.cardif.satelite.siniestro.bean.ConsultaSiniestro;
import com.cardif.satelite.siniestro.bean.ReporteSiniestro;

public interface SiniSiniestroService {

  public List<ConsultaSiniestro> buscar(String nroSiniestro_b, String socio_b, String producto_b, String nroPoliza_b, String tipoDoc_b, String nroDocumento_b, String cobAfectada_b, String nombres_b,
      String apePaterno_b, String apeMaterno_b, String estado_b, String estadoLegajo_b, Date fecUltDocumen_b, Date fecUltDocumenDesde_b, Date fecUltDocumenHasta_b, Date fecAprobRechDesde_b,
      Date fecAprobRechHasta_b, Date fecEntrgaOpcDesde_b, Date fecEntrgaOpcHasta_b, String pagoDesde_b, String pagoHasta_b, String nroPlanilla_b, String findEjeCafae_b, Date fecNotiDesde_b,
      Date fecNotiHasta_b, String tipoSeguro_b) throws SyncconException;

  public SiniSiniestro listar(String nroSiniestro) throws SyncconException;

  public int actualizarSiniSiniestro(SiniSiniestro siniSiniestro) throws SyncconException;

  public int insertarSiniSiniestro(SiniSiniestro siniSiniestro, Short codProducto) throws SyncconException;

  public int eliminarSiniSiniestro(String nroSiniestro) throws SyncconException;

  public String cantSiniestro(String nroDni, String tipDoc) throws SyncconException;

  /**
   * Metodo que obtiene el siguiente numero de planilla a utilizar por SOCIO por ejemplo SF2014-0001
   * 
   * @autor 2ariasju
   * @param codSocio
   * @return
   * @throws SyncconException
   */
  public String obtenerNroPlanilla(String codSocio) throws SyncconException;

  public int canDiasHabiles(Date fechaInicial, Date fechaFinal) throws SyncconException;

  public List<ReporteSiniestro> buscarReporte(String nroSiniestro_b, String socio_b, String producto_b, String nroPoliza_b, String tipoDoc_b, String nroDocumento_b, String cobAfectada_b,
      String nombres_b, String apePaterno_b, String apeMaterno_b, String estado_b, String estadoLegajo_b, Date fecUltDocumen_b, Date fecUltDocumenDesde_b, Date fecUltDocumenHasta_b,
      Date fecAprobRechDesde_b, Date fecAprobRechHasta_b, Date fecEntrgaOpcDesde_b, Date fecEntrgaOpcHasta_b, String pagoDesde_b, String pagoHasta_b, String nroPlanilla_b, String findEjeCafae_b,
      Date fecNotiDesde_b, Date fecNotiHasta_b, String tipoSeguro_b) throws SyncconException;

  /**
   * jarias Metodo que actualiza los registros del siniestro, pero sin considerar los campos nulos.
   * 
   * @param siniSiniestro
   * @return
   * @throws SyncconException
   */
  public int actualizarSelective(SiniSiniestro siniSiniestro) throws SyncconException;

  public ReporteSiniestro getReporteSiniestro(String ids) throws SyncconException;
}
