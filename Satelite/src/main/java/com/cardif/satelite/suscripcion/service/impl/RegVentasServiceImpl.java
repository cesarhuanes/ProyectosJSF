package com.cardif.satelite.suscripcion.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_ACTUALIZAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_BUSCAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_INSERTAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_OBTENER;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.MigRegVentas;
import com.cardif.satelite.model.TstVentas;
import com.cardif.satelite.suscripcion.bean.ConsultaTstVentas;
import com.cardif.satelite.suscripcion.dao.MigRegVentasMapper;
import com.cardif.satelite.suscripcion.dao.TstVentasMapper;
import com.cardif.satelite.suscripcion.service.RegVentasService;

@Service("regVentasService")
public class RegVentasServiceImpl implements RegVentasService {

  public static final Logger log = Logger.getLogger(RegVentasServiceImpl.class);

  @Autowired
  private TstVentasMapper tstVentasMapper;
  @Autowired
  private MigRegVentasMapper migRegVentasMapper;

  @Override
  public boolean existePeriodo(int periodo) throws SyncconException {
    log.info("Inicio");
    boolean existe = false;
    try {
      if (migRegVentasMapper.verificarPeriodo(periodo) > 0)
        existe = true;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }
    log.info("Fin");
    return existe;
  }

  @Override
  public void insertar(MigRegVentas migRegVentas) throws SyncconException {
    log.info("Inicio");
    try {
      if (log.isDebugEnabled())
        log.debug("input [" + BeanUtils.describe(migRegVentas).toString() + "]");
      migRegVentas.setFecCreacion(new Date(System.currentTimeMillis()));
      migRegVentasMapper.insert(migRegVentas);
      if (log.isDebugEnabled())
        log.debug("Output [ok]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }
    log.info("Fin");
  }

  @Override
  public List<ConsultaTstVentas> buscar(String correlativo, String tipComprobante, String producto, String poliza, String nroSerie, String correlativoSerie, String tipDocumento, String numDocumento,
      Date fechaDesde, Date fechaHasta) throws SyncconException {
    log.info("Inicio");
    List<ConsultaTstVentas> lista = null;
    int size = 0;
    try {
      SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
      String fechaInicial = ((fechaDesde != null) ? formateador.format(fechaDesde) : "2000-01-01");
      String fechaFinal = ((fechaHasta != null) ? formateador.format(fechaHasta) : "2060-12-31");
      correlativo = StringUtils.isBlank(correlativo) ? null : correlativo;
      tipComprobante = StringUtils.isBlank(tipComprobante) ? null : tipComprobante;
      producto = StringUtils.isBlank(producto) ? null : producto;
      poliza = StringUtils.isBlank(poliza) ? null : poliza;
      nroSerie = StringUtils.isBlank(nroSerie) ? null : nroSerie;
      correlativoSerie = StringUtils.isBlank(correlativoSerie) ? null : correlativoSerie;
      tipDocumento = StringUtils.isBlank(tipDocumento) ? null : tipDocumento;
      numDocumento = StringUtils.isBlank(numDocumento) ? null : numDocumento;

      if (log.isDebugEnabled())
        log.debug("Input [1.-" + correlativo + ", 2.-" + tipComprobante + ", " + "3.-" + producto + ", 4.-" + poliza + " 5.-" + nroSerie + ", 6.-" + correlativoSerie + ", " + "7.-" + tipDocumento
            + ", 8.-" + numDocumento + ", 9.-" + fechaInicial + ", 10.-" + fechaFinal + "]");

      lista = tstVentasMapper.selectTstVentasDesc(correlativo, tipComprobante, producto, poliza, nroSerie, correlativoSerie, tipDocumento, numDocumento, fechaInicial, fechaFinal);

      size = (lista != null) ? lista.size() : 0;
      if (log.isDebugEnabled())
        log.debug("Output [Registros = " + size + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_BUSCAR);
    }
    log.info("Fin");
    return lista;
  }

  @Override
  public List<ConsultaTstVentas> buscarPorCorrelativo(String correlativo) throws SyncconException {
    log.info("Inicio");
    List<ConsultaTstVentas> lista = null;
    int size = 0;
    try {
      correlativo = StringUtils.isBlank(correlativo) ? null : correlativo;

      if (log.isDebugEnabled())
        log.debug("Input [" + correlativo + "]");

      lista = tstVentasMapper.selectTstVentasCorrela(correlativo);

      size = (lista != null) ? lista.size() : 0;
      if (log.isDebugEnabled())
        log.debug("Output [Registros = " + size + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_BUSCAR);
    }
    log.info("Fin");
    return lista;
  }

  @Override
  public List<ConsultaTstVentas> buscarNoReferenciados(String correlativo, String tipComprobante, String producto, String poliza, String nroSerie, String correlativoSerie, String tipDocumento,
      String numDocumento, Date fechaDesde, Date fechaHasta) throws SyncconException {
    log.info("Inicio");
    List<ConsultaTstVentas> lista = null;
    int size = 0;
    try {
      SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
      String fechaInicial = ((fechaDesde != null) ? formateador.format(fechaDesde) : "2000-01-01");
      String fechaFinal = ((fechaHasta != null) ? formateador.format(fechaHasta) : "2060-12-31");
      correlativo = StringUtils.isBlank(correlativo) ? null : correlativo;
      tipComprobante = StringUtils.isBlank(tipComprobante) ? null : tipComprobante;
      producto = StringUtils.isBlank(producto) ? null : producto;
      poliza = StringUtils.isBlank(poliza) ? null : poliza;
      nroSerie = StringUtils.isBlank(nroSerie) ? null : nroSerie;
      correlativoSerie = StringUtils.isBlank(correlativoSerie) ? null : correlativoSerie;
      tipDocumento = StringUtils.isBlank(tipDocumento) ? null : tipDocumento;
      numDocumento = StringUtils.isBlank(numDocumento) ? null : numDocumento;

      lista = tstVentasMapper.selectTstVentasNoRefDesc(correlativo, tipComprobante, producto, poliza, nroSerie, correlativoSerie, tipDocumento, numDocumento, fechaInicial, fechaFinal);
      size = (lista != null) ? lista.size() : 0;
      if (log.isDebugEnabled())
        log.debug("Output [Registros = " + size + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_BUSCAR);
    }
    log.info("Fin");
    return lista;
  }

  @Override
  public TstVentas obtener(Long pk) throws SyncconException {
    log.info("Inicio");
    TstVentas resulTstVentas = null;
    try {
      resulTstVentas = tstVentasMapper.selectByPrimaryKey(pk);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return resulTstVentas;
  }

  @Override
  public void actualizar(TstVentas tstVentas) throws SyncconException {
    log.info("Inicio");
    try {
      if (log.isDebugEnabled())
        log.debug("input [" + BeanUtils.describe(tstVentas).toString() + "]");

      tstVentas.setFecModificacion(new Date(System.currentTimeMillis()));
      tstVentasMapper.updateByPrimaryKeySelectiveInt(tstVentas);

      if (log.isDebugEnabled())
        log.debug("Output [Ok]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_ACTUALIZAR);
    }
    log.info("Fin");
  }

}
