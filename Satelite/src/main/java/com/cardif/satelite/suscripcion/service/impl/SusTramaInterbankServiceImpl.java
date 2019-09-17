package com.cardif.satelite.suscripcion.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_OBTENER;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.SusTramaInterbank;
import com.cardif.satelite.model.SusTramaInterbankExample;
import com.cardif.satelite.suscripcion.bean.SusTramaExportarReporte;
import com.cardif.satelite.suscripcion.dao.SusTramaInterbankMapper;
import com.cardif.satelite.suscripcion.service.SusTramaInterbankService;

@Service("susTramaInterbankService")
public class SusTramaInterbankServiceImpl implements SusTramaInterbankService {

  public static final Logger log = Logger.getLogger(SusTramaInterbankServiceImpl.class);
  final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
  @Autowired
  private SusTramaInterbankMapper susTramaInterbankMapper;

  @Override
  public void ingresa(SusTramaInterbank susTramaInterbank) throws SyncconException {
    log.info("[ Inicio ]");

    int numFilasAfectadas = 0;
    try {
      numFilasAfectadas = susTramaInterbankMapper.insert(susTramaInterbank);
      log.info(numFilasAfectadas);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_INSERTAR);
    }
    log.info("[ Fin ]");

  }

  /*
   * public List<SusTramaInterbank> listarAnulacion() throws SyncconException { logger.info("Inicio"); int size=0; List<SusTramaInterbank> lista =
   * null; // TODO Auto-generated method stub //susTramaInterbankMapper.selectByExample(); try { lista = new ArrayList<SusTramaInterbank>(); lista =
   * susTramaInterbankMapper.selectByExample(); if (logger.isDebugEnabled()) logger.debug("Output [Registros = " + size + "]"); } catch (Exception e)
   * { System.err.println(e.getMessage()); throw new SyncconException(COD_ERROR_BD_BUSCAR); } logger.info("Fin"); return lista; return lista; }
   */

  @Override
  public List<SusTramaInterbank> listarAnulacion() throws SyncconException {
    log.info("Inicio");
    List<SusTramaInterbank> lista = null;
    try {

      lista = new ArrayList<SusTramaInterbank>();
      lista = susTramaInterbankMapper.listar();
      if (log.isDebugEnabled())
        log.debug("Output [Registros = " + lista.size() + "]");

    } catch (Exception e) {
      log.error(e.getMessage());
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return lista;

  }

  @Override
  public List<SusTramaInterbank> obtenerLista(long codTrama) throws SyncconException {
    log.info("Inicio");
    List<SusTramaInterbank> lista = null;
    try {
      lista = susTramaInterbankMapper.listarPorPK(codTrama);
      if (log.isDebugEnabled())
        log.debug("Output [Registros = " + lista.size() + "]");
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return lista;

  }

  @Override
  public List<SusTramaExportarReporte> listaReporteEnviados(Long codTrama) throws SyncconException {

    log.info("Inicio");
    List<SusTramaInterbank> lista = null;
    List<SusTramaExportarReporte> listaSTER = null;
    SusTramaExportarReporte susTramaExportarReporte = null;
    String motivo = "";
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + codTrama + "]");

      lista = susTramaInterbankMapper.listaReporteAnul(codTrama);
      listaSTER = new ArrayList<SusTramaExportarReporte>();

      for (SusTramaInterbank susTramaInterbank : lista) {
        susTramaExportarReporte = new SusTramaExportarReporte();
        susTramaExportarReporte.setNroPoliza(susTramaInterbank.getNroCertificado());
        susTramaExportarReporte.setEvento("Rescind policy after in force");
        // if(!susTramaInterbank.getFecSolAnulacion().equals(null))
        susTramaExportarReporte.setFecAnulacion(susTramaInterbank.getFecSolAnulacion());
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        susTramaExportarReporte.setFecRecepcion(sdf.format(d));
        if (susTramaInterbank.getMovAnulacion() == 1) {
          motivo = "Rescinding due to non-recognised sale";
        } else if (susTramaInterbank.getMovAnulacion() == 2) {
          motivo = "Rescinding due to client decision";
        } else if (susTramaInterbank.getMovAnulacion() == 3) {
          motivo = "Rescinding due to no payment";
        } else {
          motivo = "Others";
        }
        susTramaExportarReporte.setMotAnulacion(motivo);

        susTramaExportarReporte.setNroPoliza(susTramaInterbank.getNroCertificado());
        susTramaExportarReporte.setObservaciones("Anulación Masiva");
        susTramaExportarReporte.setProducto("IB_Multiproteccion");
        listaSTER.add(susTramaExportarReporte);

      }
      if (log.isDebugEnabled())
        log.debug("Output [Registros = " + lista.size() + "]");
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");

    return listaSTER;
  }

  @Override
  public void actualizar(SusTramaInterbank susTramaInterbank, SusTramaInterbankExample example) throws SyncconException {

    log.info("Inicio");
    int result = 0;
    try {
      log.debug("Input: [" + BeanUtils.describe(susTramaInterbank) + "]");
      result = susTramaInterbankMapper.updateByExampleSelective(susTramaInterbank, example);
      log.debug("Output: [Registros actualizados: " + result + "]");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_ACTUALIZAR);
    }
    log.info("Fin");
  }

  @Override
  public void actualizarRegistrosMultiMes(SusTramaInterbank susTramaInterbank) throws SyncconException {
    log.info("Inicio");
    int result = 0;
    try {
      log.debug("Input: [" + BeanUtils.describe(susTramaInterbank) + "]");
      susTramaInterbankMapper.actualizarRegistrosMultiMes(susTramaInterbank.getCodTrama(), susTramaInterbank.getNroCertificado());
      log.debug("Output: [Registros actualizados: " + result + "]");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_ACTUALIZAR);
    }
    log.info("Fin");

  }

}
