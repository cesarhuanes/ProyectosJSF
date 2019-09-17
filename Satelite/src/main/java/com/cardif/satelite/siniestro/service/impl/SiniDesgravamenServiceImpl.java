package com.cardif.satelite.siniestro.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_ACTUALIZAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_ELIMINAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_INSERTAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_OBTENER;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.SiniDesgravamen;
import com.cardif.satelite.siniestro.dao.SiniDesgravamenMapper;
import com.cardif.satelite.siniestro.service.SiniDesgravamenService;

@Service("siniDesgravamenService")
public class SiniDesgravamenServiceImpl implements SiniDesgravamenService {
  public static final Logger logger = Logger.getLogger(SiniDesgravamenServiceImpl.class);
  @Autowired
  SiniDesgravamenMapper siniDesgravamenMapper;

  @Override
  public SiniDesgravamen listar(String nroSiniestro) throws SyncconException {
    SiniDesgravamen siniDesgravamen;

    siniDesgravamen = siniDesgravamenMapper.listar(nroSiniestro);

    return siniDesgravamen;
  }

  @Override
  public List<SiniDesgravamen> listar() throws SyncconException {
    logger.info("Inicio");

    List<SiniDesgravamen> lista;
    try {
      lista = siniDesgravamenMapper.listarT();
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    logger.info("Fin");
    return lista;
  }

  @Override
  public int actualizarSiniDesgravamen(SiniDesgravamen siniDesgravamen) throws SyncconException {
    logger.info("Inicio");

    try {
      String vsubStringNumeroTarjeta = siniDesgravamen.getNroTarjeta();

      if (vsubStringNumeroTarjeta != null) {
        if (vsubStringNumeroTarjeta.length() >= 8) {
          vsubStringNumeroTarjeta = vsubStringNumeroTarjeta.substring(0, 8);
          logger.info("Inicio:vsubStringNumeroTarjeta:  " + vsubStringNumeroTarjeta);
          int valida = siniDesgravamenMapper.selectBinTarjeta(vsubStringNumeroTarjeta);
          logger.info("Inicio valida: " + valida);

          if (valida == 1) {
            siniDesgravamen.setTipTarjeta("Linea Paralela");

          } else {
            siniDesgravamen.setTipTarjeta("Linea No Paralela");

          }

        } else {
          siniDesgravamen.setTipTarjeta(null);
        }

        logger.info("Resultado: " + siniDesgravamen);

        siniDesgravamenMapper.updateByPrimaryKey(siniDesgravamen);

      }

    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_ACTUALIZAR);
    }

    logger.info("Fin");
    return 0;
  }

  @Override
  public int validaTipoTarjeta(String numeroTarjeta) throws SyncconException {
    logger.info("Inicio");
    int valida;
    try {

      String vsubStringNumeroTarjeta = numeroTarjeta.substring(0, 8);

      logger.info("Inicio:vsubStringNumeroTarjeta:  " + vsubStringNumeroTarjeta);
      valida = siniDesgravamenMapper.selectBinTarjeta(vsubStringNumeroTarjeta);
      logger.info("Inicio valida: " + valida);

    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException("Error al consultar");
    }
    logger.info("Fin");
    return valida;
  }

  @Override
  public int insertarSiniDesgravamen(SiniDesgravamen siniDesgravamen) throws SyncconException {
    logger.info("Inicio");
    try {

      int valida;
      String vsubStringNumeroTarjeta = siniDesgravamen.getNroTarjeta();

      if (vsubStringNumeroTarjeta != null) {
        if (vsubStringNumeroTarjeta.length() >= 8) {
          vsubStringNumeroTarjeta = vsubStringNumeroTarjeta.substring(0, 8);
          logger.info("Inicio:vsubStringNumeroTarjeta:  " + vsubStringNumeroTarjeta);
          valida = siniDesgravamenMapper.selectBinTarjeta(vsubStringNumeroTarjeta);
          logger.info("Inicio valida: " + valida);

          if (valida == 1) {
            siniDesgravamen.setTipTarjeta("Linea Paralela");

          } else {
            siniDesgravamen.setTipTarjeta("Linea No Paralela");

          }
        } else {
          siniDesgravamen.setTipTarjeta(null);

        }
      }

      siniDesgravamenMapper.insertSelective(siniDesgravamen);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }
    logger.info("Fin");
    return 0;
  }

  @Override
  public int eliminarSiniDesgravamen(String siniDesgravamen) throws SyncconException {
    logger.info("Inicio");

    try {
      siniDesgravamenMapper.deleteByPrimaryKey(siniDesgravamen);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_ELIMINAR);
    }
    logger.info("Fin");

    return 0;
  }

}
