package com.cardif.satelite.cobranza.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.cobranza.bean.ConsultaProceso;
import com.cardif.satelite.cobranza.dao.ProcesoMapper;
import com.cardif.satelite.cobranza.service.ProcesoService;
import com.cardif.satelite.constantes.ErrorConstants;

@Service("procesoService")
public class ProcesoServiceImpl implements ProcesoService {

  public static final Logger log = Logger.getLogger(ProcesoService.class);
  @Autowired
  ProcesoMapper procesoMapper;

  @Override
  public ArrayList<ConsultaProceso> consultar(String tipo, String estado, Date fecIni, Date fecFin) throws SyncconException {
    log.info("Inicio");
    ArrayList<ConsultaProceso> lista = null;
    int size = 0;
    try {
      SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");
      String fechaInicial = ((fecIni != null) ? formateador.format(fecIni) : null);
      String fechaFinal = ((fecIni != null) ? formateador.format(fecFin) : null);

      if (log.isDebugEnabled())
        log.debug("Input [1.-" + tipo + ", 2.-" + estado + ", 3.-" + fechaInicial + ", 4.-" + fechaFinal + "]");
      Map<String, Object> mapa = new HashMap<String, Object>();
      mapa.put("p_tipo", tipo);
      mapa.put("p_estado", estado);
      mapa.put("p_fecIni", fechaInicial);
      mapa.put("p_fecFin", fechaFinal);
      lista = procesoMapper.selectByFilter(mapa);

      size = (lista != null) ? lista.size() : 0;
      if (log.isDebugEnabled())
        log.debug("Output [Registros = " + size + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
    }
    log.info("Fin");
    return lista;
  }

}
