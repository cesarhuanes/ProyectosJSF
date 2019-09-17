package com.cardif.satelite.siniestro.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_INSERTAR;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.SiniSiniestro;
import com.cardif.satelite.siniestro.bean.ConsultaSiniestro;
import com.cardif.satelite.siniestro.bean.ReporteSiniestro;
import com.cardif.satelite.siniestro.dao.SiniSiniestroMapper;
import com.cardif.satelite.siniestro.service.SiniSiniestroService;
import com.cardif.satelite.util.Utilitarios;

@Service("siniSiniestroService")
public class SiniSiniestroServiceImpl implements SiniSiniestroService {

  public static final Logger log = Logger.getLogger(SiniSiniestroServiceImpl.class);

  @Autowired
  SiniSiniestroMapper siniSiniestroMapper;

  @Override
  public List<ConsultaSiniestro> buscar(String nroSiniestro_b, String socio_b, String producto_b, String nroPoliza_b, String tipoDoc_b, String nroDocumento_b, String cobAfectada_b, String nombres_b,
      String apePaterno_b, String apeMaterno_b, String estado_b, String estadoLegajo_b, Date fecUltDocumen_b, Date fecUltDocumenDesde_b, Date fecUltDocumenHasta_b, Date fecAprobRechDesde_b,
      Date fecAprobRechHasta_b, Date fecEntrgaOpcDesde_b, Date fecEntrgaOpcHasta_b, String pagoDesde_b, String pagoHasta_b, String nroPlanilla_b, String findEjeCafae_b, Date fecNotiDesde_b,
      Date fecNotiHasta_b, String tipoSeguro_b) throws SyncconException {
    log.info("Inicio");
    List<ConsultaSiniestro> lista = null;
    try {
      String desde = "";
      String hasta = "";
      String ultnoti = "";
      String ultnotiDesde = "";
      String ultnotiHasta = "";
      String sFecAprobRechazoDesde = "";
      String sFecAprobRechazoHasta = "";
      String sFecEntrgaOpcDesde = "";
      String sFecEntrgaOpcHasta = "";

      if (fecNotiDesde_b == null || fecNotiHasta_b == null) {
        hasta = "";
        desde = "";
      } else {
        desde = Utilitarios.convertirFechaACadena(fecNotiDesde_b, "yyyy-dd-MM");
        hasta = Utilitarios.convertirFechaACadena(fecNotiHasta_b, "yyyy-dd-MM");
      }

      if (fecUltDocumen_b != null) {
        ultnoti = Utilitarios.convertirFechaACadena(fecUltDocumen_b, "yyyy-dd-MM");
      } else {
        ultnoti = "";
      }

      if (fecUltDocumenDesde_b != null) {
        ultnotiDesde = Utilitarios.convertirFechaACadena(fecUltDocumenDesde_b, "yyyy-dd-MM");
      } else {
        ultnotiDesde = "";
      }

      if (fecUltDocumenHasta_b != null) {
        ultnotiHasta = Utilitarios.convertirFechaACadena(fecUltDocumenHasta_b, "yyyy-dd-MM");
      } else {
        ultnotiHasta = "";
      }

      if (fecAprobRechDesde_b != null) {
        sFecAprobRechazoDesde = Utilitarios.convertirFechaACadena(fecAprobRechDesde_b, "yyyy-dd-MM");
      } else {
        sFecAprobRechazoDesde = "";
      }

      if (fecAprobRechHasta_b != null) {
        sFecAprobRechazoHasta = Utilitarios.convertirFechaACadena(fecAprobRechHasta_b, "yyyy-dd-MM");
      } else {
        sFecAprobRechazoHasta = "";
      }
      if (fecEntrgaOpcDesde_b != null) {
        sFecEntrgaOpcDesde = Utilitarios.convertirFechaACadena(fecEntrgaOpcDesde_b, "yyyy-dd-MM");
      } else {
        sFecEntrgaOpcDesde = "";
      }

      if (fecEntrgaOpcHasta_b != null) {
        sFecEntrgaOpcHasta = Utilitarios.convertirFechaACadena(fecEntrgaOpcHasta_b, "yyyy-dd-MM");
      } else {
        sFecEntrgaOpcHasta = "";
      }

      lista = siniSiniestroMapper.buscar(nroSiniestro_b, socio_b, producto_b, nroPoliza_b, tipoDoc_b, nroDocumento_b, cobAfectada_b, nombres_b, apePaterno_b, apeMaterno_b, estado_b, estadoLegajo_b,
          ultnoti, ultnotiDesde, ultnotiHasta, sFecAprobRechazoDesde, sFecAprobRechazoHasta, sFecEntrgaOpcDesde, sFecEntrgaOpcHasta, pagoDesde_b, pagoHasta_b, nroPlanilla_b, findEjeCafae_b, desde,
          hasta, tipoSeguro_b);

      log.info("Respuesta: Tamaño de lista: " + lista.size());

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
    }
    log.info("Fin");
    return lista;
  }

  @Override
  public int actualizarSiniSiniestro(SiniSiniestro siniSiniestro) throws SyncconException {
    log.info("Inicio");
    try {
      Date fecActual;
      fecActual = new Date(System.currentTimeMillis());
      siniSiniestro.setFecModificacion(fecActual);
      siniSiniestroMapper.updateByPrimaryKey(siniSiniestro);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }
    log.info("Fin");
    return 0;
  }

  @Override
  public int actualizarSelective(SiniSiniestro siniSiniestro) throws SyncconException {
    log.info("Inicio");
    try {
      siniSiniestroMapper.updateByPrimaryKeySelective(siniSiniestro);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }
    log.info("[ Fin ]");
    return 0;
  }

  @Override
  public int insertarSiniSiniestro(SiniSiniestro siniSiniestro, Short codProducto) throws SyncconException {
    log.info("Inicio");
    int numRowAfected = 0;
    try {
      Date fecActual;
      fecActual = new Date(System.currentTimeMillis());
      if (log.isDebugEnabled())
        log.debug("Input [ " + BeanUtils.describe(siniSiniestro).toString() + " ]");

      siniSiniestro.setNroSiniestro(obtenerNroSiniestro(codProducto));
      siniSiniestro.setFecCreacion(fecActual);
      numRowAfected = siniSiniestroMapper.insert(siniSiniestro);

      log.debug("Output [Numero de filas afectadas : " + numRowAfected + " ]");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }
    log.info("[ Fin ]");
    return numRowAfected;
  }

  @Override
  public int eliminarSiniSiniestro(String nroSiniestro) throws SyncconException {
    log.info("[ Inicio ]");
    try {
      siniSiniestroMapper.deleteByPrimaryKey(nroSiniestro);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }
    log.info("[ Fin ]");
    return 0;
  }

  @Override
  public SiniSiniestro listar(String nroSiniestro) throws SyncconException {
    log.info("Inicio");

    SiniSiniestro siniSiniestro = new SiniSiniestro();
    try {
      if (log.isDebugEnabled())
        log.debug("Input [ " + nroSiniestro + " ]");

      siniSiniestro = siniSiniestroMapper.listar(nroSiniestro);

      if (log.isDebugEnabled())
        log.debug("Output [ " + BeanUtils.describe(siniSiniestro) + " ]");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }
    log.info("Fin");
    return siniSiniestro;
  }

  @Override
  public String obtenerNroPlanilla(String codSocio) throws SyncconException {
    log.info("Inicio");
    String nroPlanilla = null;
    Integer maxSecuencia = null;
    int anio = 0;
    String completaFS = "";

    if (codSocio.equals("SF")) {
      completaFS = "D";
    }
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + codSocio + "]");

      maxSecuencia = siniSiniestroMapper.getMaxNroPlanilla(codSocio);

      Calendar cal = Calendar.getInstance();
      cal.setTime(new Date(System.currentTimeMillis()));
      anio = cal.get(Calendar.YEAR);
      if (maxSecuencia == null) {
        nroPlanilla = codSocio + completaFS + anio + "-" + StringUtils.leftPad("1", 4, '0');
      } else {
        nroPlanilla = codSocio + completaFS + anio + "-" + StringUtils.leftPad(String.valueOf(maxSecuencia + 1), 4, '0');
      }
      if (log.isDebugEnabled())
        log.debug("Output [" + nroPlanilla + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return nroPlanilla;
  }

  /**
   * Metodo que obtiene el siguiente numero de Siniestro a utilizar por Codigo de Producto por ejemplo 4101-14-0001
   * 
   * @param codProductoSini
   * @return
   * @throws SyncconException
   */
  private String obtenerNroSiniestro(Short codProductoSini) throws Exception {
    log.info("Inicio");
    String nroSiniestro = null;
    Integer maxSecuencia = null;
    int anio = 0;
    // try {
    if (log.isDebugEnabled())
      log.debug("Input  codProductoSini  [" + codProductoSini + "]");

    maxSecuencia = siniSiniestroMapper.getMaxNroSiniestro(codProductoSini);

    Calendar cal = Calendar.getInstance();
    cal.setTime(new Date(System.currentTimeMillis()));
    anio = cal.get(Calendar.YEAR);

    if (maxSecuencia == null) {

      if (codProductoSini.toString().equals("6407")) {
        nroSiniestro = codProductoSini + "-" + String.valueOf(anio).subSequence(2, 4) + "-" + StringUtils.leftPad("6", 4, '0');
      } else if (codProductoSini.toString().equals("6109")) {
        nroSiniestro = codProductoSini + "-" + String.valueOf(anio).subSequence(2, 4) + "-" + StringUtils.leftPad("5", 4, '0');
      } else {
        nroSiniestro = codProductoSini + "-" + String.valueOf(anio).subSequence(2, 4) + "-" + StringUtils.leftPad("1", 4, '0');
      }

    } else {
      nroSiniestro = codProductoSini + "-" + String.valueOf(anio).subSequence(2, 4) + "-" + StringUtils.leftPad(String.valueOf(maxSecuencia + 1), 4, '0');
    }

    if (log.isDebugEnabled())
      log.debug("Output [" + nroSiniestro + "]");

    /*
     * } catch (Exception e) { log.error(e.getMessage(), e); throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER); }
     */
    log.info("Fin");
    return nroSiniestro;

  }

  @Override
  public List<ReporteSiniestro> buscarReporte(String nroSiniestro_b, String socio_b, String producto_b, String nroPoliza_b, String tipoDoc_b, String nroDocumento_b, String cobAfectada_b,
      String nombres_b, String apePaterno_b, String apeMaterno_b, String estado_b, String estadoLegajo_b, Date fecUltDocumen_b, Date fecUltDocumenDesde_b, Date fecUltDocumenHasta_b,
      Date fecAprobRechDesde_b, Date fecAprobRechHasta_b, Date fecEntrgaOpcDesde_b, Date fecEntrgaOpcHasta_b, String pagoDesde_b, String pagoHasta_b, String nroPlanilla_b, String findEjeCafae_b,
      Date fecNotiDesde_b, Date fecNotiHasta_b, String tipoSeguro_b) throws SyncconException {
    log.info("Inicio");
    List<ReporteSiniestro> lista = null;
    try {
      String desde = "";
      String hasta = "";
      String ultnoti = "";
      String ultnotiDesde = "";
      String ultnotiHasta = "";
      String sFecAprobRechazoDesde = "";
      String sFecAprobRechazoHasta = "";
      String sFecEntrgaOpcDesde = "";
      String sFecEntrgaOpcHasta = "";

      if (fecNotiDesde_b == null || fecNotiHasta_b == null) {
        hasta = "";
        desde = "";
      } else {
        desde = Utilitarios.convertirFechaACadena(fecNotiDesde_b, "yyyy-dd-MM");
        hasta = Utilitarios.convertirFechaACadena(fecNotiHasta_b, "yyyy-dd-MM");

      }
      if (fecUltDocumen_b != null) {
        ultnoti = Utilitarios.convertirFechaACadena(fecUltDocumen_b, "yyyy-dd-MM");
      } else {
        ultnoti = "";
      }

      if (fecUltDocumenDesde_b != null) {
        ultnotiDesde = Utilitarios.convertirFechaACadena(fecUltDocumenDesde_b, "yyyy-dd-MM");
      } else {
        ultnotiDesde = "";
      }

      if (fecUltDocumenHasta_b != null) {
        ultnotiHasta = Utilitarios.convertirFechaACadena(fecUltDocumenHasta_b, "yyyy-dd-MM");
      } else {
        ultnotiHasta = "";
      }

      if (fecAprobRechDesde_b != null) {
        sFecAprobRechazoDesde = Utilitarios.convertirFechaACadena(fecAprobRechDesde_b, "yyyy-dd-MM");
      } else {
        sFecAprobRechazoDesde = "";
      }

      if (fecAprobRechHasta_b != null) {
        sFecAprobRechazoHasta = Utilitarios.convertirFechaACadena(fecAprobRechHasta_b, "yyyy-dd-MM");
      } else {
        sFecAprobRechazoHasta = "";
      }
      if (fecEntrgaOpcDesde_b != null) {
        sFecEntrgaOpcDesde = Utilitarios.convertirFechaACadena(fecEntrgaOpcDesde_b, "yyyy-dd-MM");
      } else {
        sFecEntrgaOpcDesde = "";
      }

      if (fecEntrgaOpcHasta_b != null) {
        sFecEntrgaOpcHasta = Utilitarios.convertirFechaACadena(fecEntrgaOpcHasta_b, "yyyy-dd-MM");
      } else {
        sFecEntrgaOpcHasta = "";
      }

      lista = siniSiniestroMapper.buscarReporte(nroSiniestro_b, socio_b, producto_b, nroPoliza_b, tipoDoc_b, nroDocumento_b, cobAfectada_b, nombres_b, apePaterno_b, apeMaterno_b, estado_b,
          estadoLegajo_b, ultnoti, ultnotiDesde, ultnotiHasta, sFecAprobRechazoDesde, sFecAprobRechazoHasta, sFecEntrgaOpcDesde, sFecEntrgaOpcHasta, pagoDesde_b, pagoHasta_b, nroPlanilla_b,
          findEjeCafae_b, desde, hasta, tipoSeguro_b);

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return lista;

  }

  @Override
  public String cantSiniestro(String nroDni, String tipDoc) throws SyncconException {
    log.info("Inicio");
    String cantSiniestro;

    try {
      cantSiniestro = siniSiniestroMapper.cantSiniestro(nroDni, tipDoc);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");

    return cantSiniestro;
  }

  @Override
  public int canDiasHabiles(Date fechaInicial, Date fechaFinal) throws SyncconException {
    log.info("Inicio");
    int cantDiasHabiles;
    try {

      cantDiasHabiles = siniSiniestroMapper.cantDiasHabiles(fechaInicial, fechaFinal);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return cantDiasHabiles;
  }

  @Override
  public ReporteSiniestro getReporteSiniestro(String ids) throws SyncconException {
    log.info("Inicio");
    ReporteSiniestro reporteSiniestros = new ReporteSiniestro();
    try {

      reporteSiniestros = siniSiniestroMapper.getReporteSiniestro(ids);
    } catch (Exception e) {
      log.error(e.getMessage(), e);

      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return reporteSiniestros;
  }

}
