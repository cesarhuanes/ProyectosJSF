package com.cardif.satelite.suscripcion.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_OBTENER;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.SusTramaCabeceraIB;
import com.cardif.satelite.suscripcion.dao.SusTramaCabeceraIBMapper;
import com.cardif.satelite.suscripcion.service.SusTramaCabeceraIBService;

@Service("susTramaCabeceraIBService")
public class SusTramaCabeceraIBServiceImpl implements SusTramaCabeceraIBService {
  public static final Logger logger = Logger.getLogger(SusTramaInterbankServiceImpl.class);
  @Autowired
  SusTramaCabeceraIBMapper susTramaCabeceraIBMapper;

  @Override
  public List<SusTramaCabeceraIB> listarAnulacion() throws SyncconException {
    logger.info("Inicio");
    List<SusTramaCabeceraIB> lista = null;
    try {
      lista = new ArrayList<SusTramaCabeceraIB>();
      lista = susTramaCabeceraIBMapper.listar();
      if (logger.isDebugEnabled())
        logger.debug("Output [Registros = " + lista.size() + "]");
    } catch (Exception e) {
      System.err.println(e.getMessage());
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    logger.info("Fin");
    return lista;

  }

  @Override
  public int insertarDatosTxt(SusTramaCabeceraIB susTramaCabeceraIB) throws SyncconException {
    logger.info("[ Inicio ]");
    int numFilasAfectadas = 0;
    try {
      numFilasAfectadas = susTramaCabeceraIBMapper.insert(susTramaCabeceraIB);
      logger.info("[ Numero de Filas afectadas = " + numFilasAfectadas + " ]");
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    logger.info("[ Fin ]");

    return numFilasAfectadas;
  }

  @Override
  public int actualizarCabecera(SusTramaCabeceraIB susTramaCabeceraIB) throws SyncconException {

    logger.info("Inicio");
    int result = 0;
    try {
      // logger.debug("Input: [" + BeanUtils.describe(susTramaCabeceraIB)
      // + "]");
      susTramaCabeceraIB.setFecModificacion(new Date(System.currentTimeMillis()));
      susTramaCabeceraIBMapper.actualizarCabecera(susTramaCabeceraIB.getPeriodo(), susTramaCabeceraIB.getCanRechazados(), susTramaCabeceraIB.getCanRegistros());
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_ACTUALIZAR + "CABECERA"); // FIXME:
                                                                                       // BORRAR
                                                                                       // EL
                                                                                       // CODIGO:
                                                                                       // +CABECERA
    }

    logger.info("Fin");
    return result;
  }

  @Override
  public SusTramaCabeceraIB obtener(Long codTrama) throws SyncconException {
    logger.info("Inicio");
    SusTramaCabeceraIB susTramaCabeceraIB = null;
    try {
      if (logger.isDebugEnabled())
        logger.debug("Input [" + codTrama + "]");

      susTramaCabeceraIB = susTramaCabeceraIBMapper.selectByPrimaryKey(codTrama);

      if (logger.isDebugEnabled()) {
        logger.debug("Output [" + BeanUtils.describe(susTramaCabeceraIB) + "]");
      }

    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    logger.info("Fin");
    return susTramaCabeceraIB;
  }

  @Override
  public SusTramaCabeceraIB obtener(String periodo) throws SyncconException {
    logger.info("Inicio");
    SusTramaCabeceraIB susTramaCabeceraIB = null;
    try {
      if (logger.isDebugEnabled())
        logger.debug("Input [" + periodo + "]");

      susTramaCabeceraIB = susTramaCabeceraIBMapper.obtenerCabecera(periodo);

      if (logger.isDebugEnabled()) {
        logger.debug("Output [" + BeanUtils.describe(susTramaCabeceraIB) + "]");
      }

    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    logger.info("Fin");
    return susTramaCabeceraIB;
  }

  @Override
  public int obtenerNroCabeceras() throws SyncconException {
    logger.info("Inicio");
    int nro = susTramaCabeceraIBMapper.obtenerNroCabeceras();
    logger.info("Fin ");
    return nro;
  }

  @Override
  public int procesarMes(String periodo) throws SyncconException {
    susTramaCabeceraIBMapper.procesarTrama(periodo);
    return 0;
  }

  /*
   * 
   * public int procesarMesObservados(String periodo) throws SyncconException { // TODO Auto-generated method stub
   * susTramaCabeceraIBMapper.procesarTramaDia2(periodo); return 0; }
   */

  @Override
  public int procesarMesCerrado(Long codTrama) throws SyncconException {
    susTramaCabeceraIBMapper.procesarTramaMesCerrado(codTrama);
    return 0;
  }

}
