package com.cardif.satelite.acsele.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.acsele.bean.ConsultaOpenItem;
import com.cardif.satelite.acsele.dao.OpenItemMapper;
import com.cardif.satelite.acsele.service.OpenItemService;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.acsele.OpenItem;

@Service("openItemService")
public class OpenItemServiceImpl implements OpenItemService {

  public static final Logger log = Logger.getLogger(OpenItemServiceImpl.class);

  @Autowired
  private OpenItemMapper openItemMapper;

  @Override
  public List<ConsultaOpenItem> cargaDetallesPoliza(BigDecimal idPoliza, String numPoliza) throws SyncconException {
    log.info("Inicio");
    List<OpenItem> listaOpenItems = new ArrayList<OpenItem>();
    List<ConsultaOpenItem> listaDetallesPoliza = new ArrayList<ConsultaOpenItem>();
    int size = 0;

    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + idPoliza + "," + numPoliza + "]");
      listaDetallesPoliza = openItemMapper.selectListaOpenItems(idPoliza);
      size = (listaOpenItems != null) ? listaOpenItems.size() : 0;
      if (log.isDebugEnabled())
        log.debug("Output [Registros = " + size + "]");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return listaDetallesPoliza;
  }

  @Override
  public List<OpenItem> listarOpenItem(String nroCertificado) throws SyncconException {
    log.info("Inicio");
    List<OpenItem> listaOpems = new ArrayList<OpenItem>();

    int size = 0;

    try {
      listaOpems = openItemMapper.listar(nroCertificado);
      size = (listaOpems != null) ? listaOpems.size() : 0;
      if (log.isDebugEnabled())
        log.debug("Output [Registros = " + size + "]");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return listaOpems;
  }

}
