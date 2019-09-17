package com.cardif.satelite.suscripcion.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.satelite.ConciCabReporte;
import com.cardif.satelite.model.satelite.ConciReporte;
import com.cardif.satelite.model.satelite.ConciTramaMensual;
import com.cardif.satelite.suscripcion.dao.ConciCabReporteMapper;
import com.cardif.satelite.suscripcion.dao.ConciCabTramaMensualMapper;
import com.cardif.satelite.suscripcion.dao.ConciReporteMapper;
import com.cardif.satelite.suscripcion.dao.ConciTramaMensualMapper;
import com.cardif.satelite.suscripcion.dao.MasterPrecioSoatMapper;
import com.cardif.satelite.suscripcion.service.RepConciliacionService;

@Service("repConciliacionService")
public class RepConciliacionServiceImpl implements RepConciliacionService {

  public static final Logger log = Logger.getLogger(RepConciliacionServiceImpl.class);

  @Autowired
  private ConciCabReporteMapper conciCabReporteMapper;
  @Autowired
  private ConciReporteMapper conciReporteMapper;
  @Autowired
  private ConciTramaMensualMapper conciTramaMensualMapper;
  @Autowired
  private ConciCabTramaMensualMapper conciCabTramaMensualMapper;
  @Autowired
  private MasterPrecioSoatMapper masterPrecioSoatMapper;

  ConciCabReporte conciCabReporte;

  @Override
  public void procesarConciliacion(ConciCabReporte conciCabReporte) throws SyncconException {
    log.info("Inicio");
    Long secuencia = null;
    Integer periodo;
    String producto;
    try {

//      periodo = conciCabReporte.getPeriodo();
//      producto = conciCabReporte.getProducto();
      
//      secuencia = conciCabReporteMapper.obtenerSecuencia(periodo, producto);
//      
//      if (secuencia != null) {
//        conciCabReporteMapper.deleteByPrimaryKey(secuencia);
//        conciReporteMapper.deleteBySecArchivo(secuencia);
//      }
//      
//      conciCabReporteMapper.insert(conciCabReporte);
//      secuencia = conciCabReporteMapper.obtenerSecuencia(periodo, producto);
//      log.info("Insertando Lista Reporte");
//      conciReporteMapper.insertaListaReporte(periodo, producto, secuencia);
//      log.info("Se insertó Lista Reporte");
//      Long numRegistros = conciCabReporteMapper.obtenerTotal(secuencia);
//      Long regAceptados = conciCabReporteMapper.obtenerOk(secuencia);
      
//      conciCabReporte.setSecArchivo(secuencia);
//      conciCabReporte.setNumRegistros(numRegistros);
//      conciCabReporte.setRegAceptados(regAceptados);
//      conciCabReporte.setRegObservados(numRegistros - regAceptados);
      
      
      conciCabReporteMapper.updateByPrimaryKeySelective(conciCabReporte);
      
//      conciCabTramaMensualMapper.actualizaEstado(periodo, producto);
      
      
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
    }
    log.info("Fin");
  }

  @Override
  public List<ConciCabReporte> listaResumenConciliacion(String producto) throws SyncconException {
    log.info("Inicio");
    List<ConciCabReporte> lista = null;
    try {
    	
    		lista = conciCabReporteMapper.listaResumenConciliacion(producto);
      
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
    }
    log.info("Fin");
    return lista;
  }

  @Override
  public List<ConciReporte> listaReporteConciliacion(Integer periodo, String producto) throws SyncconException {
    log.info("Inicio");
    List<ConciReporte> lista = null;
    int size = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [1.-" + periodo + ", 2.-" + producto + "]");
      lista = conciReporteMapper.listaReporteConciliacion(periodo, producto);
      size = (lista != null) ? lista.size() : 0;
      if (log.isDebugEnabled())
        log.debug("Output [Regitros:" + size + "]");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return lista;
  }

  @Override
  public List<ConciTramaMensual> listaTramaMensual(Integer periodo, String producto) throws SyncconException {
    log.info("Inicio");
    List<ConciTramaMensual> lista = null;
    int size = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [1.-" + periodo + ", 2.-" + producto + "]");
      lista = conciTramaMensualMapper.listaTramaMensual(periodo, producto);
      size = (lista != null) ? lista.size() : 0;
      if (log.isDebugEnabled())
        log.debug("Output [Regitros:" + size + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return lista;
  }

  @Override
  public boolean verificarMaster(Integer periodo, String socio) throws SyncconException {
    log.info("Inicio");
    boolean respuesta = false;
    if (masterPrecioSoatMapper.existeMaster(periodo, socio) > 0) {
      respuesta = true;
    } else {
      respuesta = false;
    }
    log.info("Fin");
    log.debug("respuesta: " + respuesta);
    return respuesta;
  }

  @Override
  public List<ConciTramaMensual> listaTramaTXT(Integer periodo, String producto) throws SyncconException {
    return conciTramaMensualMapper.listaTramaSalida(periodo, producto);
  }
}
