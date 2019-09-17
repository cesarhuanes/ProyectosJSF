package com.cardif.satelite.telemarketing.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_BUSCAR;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.TlmkLayoutSocioCardif;
import com.cardif.satelite.telemarketing.dao.TlmkLayoutSocioCardifMapper;
import com.cardif.satelite.telemarketing.service.TlmkLayoutCardifService;

@Service("tlmkLayoutCardifService")
public class TlmkLayoutCardifServiceImpl implements TlmkLayoutCardifService {

  public static final Logger log = Logger.getLogger(TlmkLayoutCardifServiceImpl.class);

  @Autowired
  private TlmkLayoutSocioCardifMapper TlmkLayoutSocioCardifMapper;

  @Override
  public List<TlmkLayoutSocioCardif> listarLayoutCardif(String codSocio) throws Exception {

    log.info("Inicio");

    List<TlmkLayoutSocioCardif> listaLayout = null;

    try {

      log.info("listaLayout vacia " + listaLayout);
      log.info("cod socio " + codSocio);
      listaLayout = TlmkLayoutSocioCardifMapper.selectLayout(codSocio);
      log.info("listaLayout con valores " + listaLayout);
      log.info("listaLayout tamaño " + listaLayout.size());

    } catch (Exception e) {

      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_BUSCAR);

    }

    log.info("Fin");

    return listaLayout;
  }

}
