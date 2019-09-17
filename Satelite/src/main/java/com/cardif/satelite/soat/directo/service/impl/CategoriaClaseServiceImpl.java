package com.cardif.satelite.soat.directo.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.soat.directo.dao.CategoriaClaseDirectoMapper;
import com.cardif.satelite.soat.directo.service.CategoriaClaseServiceDirecto;
import com.cardif.satelite.soat.model.CategoriaClase;

@Service("categoriaClaseServiceDirecto")
public class CategoriaClaseServiceImpl implements CategoriaClaseServiceDirecto {

  public static final Logger log = Logger.getLogger(DepartamentoServiceImpl.class);

  @Autowired
  private CategoriaClaseDirectoMapper categoriaClaseDirectoMapper;

  @Override
  public List<CategoriaClase> buscar() throws SyncconException {
    log.info("Inicio");
    int size = 0;
    List<CategoriaClase> lista = null;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + "]");
      lista = categoriaClaseDirectoMapper.selectCategoria();
      size = (lista != null) ? lista.size() : 0;
      if (log.isDebugEnabled())
        log.debug("Output [Regitros:" + size + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
    }

    log.info("Fin");
    return lista;
  }

}
