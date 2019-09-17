package com.cardif.satelite.telemarketing.service.impl;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.datamart.OdsPolizaHm;
import com.cardif.satelite.model.datamart.OdsProductoDe;
import com.cardif.satelite.model.datamart.OdsSocioMm;
import com.cardif.satelite.telemarketing.bean.ReporteTLMK;
import com.cardif.satelite.telemarketing.dao.OdsPolizaHmMapper;
import com.cardif.satelite.telemarketing.dao.OdsProductoDeMapper;
import com.cardif.satelite.telemarketing.dao.OdsSocioMmMapper;
import com.cardif.satelite.telemarketing.service.ConsultaTelemarketingService;

/**
 * @class ConsultaTelemarketingService
 * @usuario jhurtado
 * @fecha_creacion 31-01-2014
 * @ticket T022163
 * @descripcion: Metodos para la carga de combos
 * 
 */
@Service("consultaTelemarketingService")
public class ConsultaTelemarketingServiceImpl implements ConsultaTelemarketingService {

  public static final Logger logger = Logger.getLogger(ConsultaTelemarketingServiceImpl.class);

  @Autowired
  OdsSocioMmMapper odsSocioMmMapper;
  @Autowired
  OdsProductoDeMapper odsProductoDeMapper;
  @Autowired
  OdsPolizaHmMapper odsPolizaHmMapper;

  @Override
  public List<OdsSocioMm> obtenerSocios() throws SyncconException {
    logger.info("Inicio");
    List<OdsSocioMm> lista = null;
    int size = 0;
    try {
      if (logger.isDebugEnabled())
        lista = odsSocioMmMapper.selectSocio();
      size = (lista != null) ? lista.size() : 0;
      if (logger.isDebugEnabled())
        logger.debug("Output [Registros = " + size + "]");
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
    }
    logger.info("Fin");
    return lista;
  }

  @Override
  public List<OdsPolizaHm> obtenerCodigoProducto(OdsPolizaHm odsphma) throws SyncconException {
    logger.info("Inicio");

    List<OdsPolizaHm> listaOdsPolizaHm = null;
    try {
      logger.debug("Input [Codigo de Socio = " + odsphma.getCodSocio() + "]");
      if (logger.isDebugEnabled())
        listaOdsPolizaHm = odsPolizaHmMapper.selectCodProducto(odsphma.getCodSocio());
      if (logger.isDebugEnabled())
        logger.debug("Output [Tamaño de lista = " + listaOdsPolizaHm.size() + "]");
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
    }
    logger.info("Fin");
    return listaOdsPolizaHm;
  }

  @Override
  public List<OdsProductoDe> obtenerProductos(String codigoProducto) throws SyncconException {
    logger.info("Inicio");
    List<OdsProductoDe> lista = null;
    int size = 0;
    try {
      logger.debug("Input [Codigo de Producto = " + codigoProducto + " Registros = " + size + "]");
      if (logger.isDebugEnabled())
        lista = odsProductoDeMapper.selectProducto(codigoProducto);
      size = (lista != null) ? lista.size() : 0;
      if (logger.isDebugEnabled())
        logger.debug("Output [Registros = " + size + "]");
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
    }
    logger.info("Fin");
    return lista;
  }

  @Override
  public List<ReporteTLMK> obtenerReporteAD(Integer codProducto, BigDecimal codSocio, String fecInicial, String fecFinal, int cuotaDe, int cuotaA, String segmento) throws SyncconException {
    logger.info("Inicio");

    List<ReporteTLMK> listaReporteClientes = null;
    List<ReporteTLMK> listaOtrosProductos = null;
    String otrosProductos = "";
    // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    // String FechaActual = sdf.format(new
    // Date(System.currentTimeMillis()));
    // String anomes=FechaActual.substring(0,4)+FechaActual.substring(5,7);
    String anomes = odsProductoDeMapper.selectAnoMes();

    int size = 0;

    try {

      listaReporteClientes = odsProductoDeMapper.selectReporteTLMKAdquisision(codProducto, codSocio, fecInicial, fecFinal, cuotaDe, cuotaA, anomes);

      if (!listaReporteClientes.isEmpty()) {
        Iterator<ReporteTLMK> reporteTLMK = listaReporteClientes.iterator();

        while (reporteTLMK.hasNext()) {
          ReporteTLMK r = reporteTLMK.next();
          if (Float.parseFloat(r.getOtrosProductos()) > 0) {
            listaOtrosProductos = odsProductoDeMapper.selectReporteTLMKOtrosProductos(r.getCodPersona(), r.getCodPoliza(), r.getCodProducto(), anomes);
            for (ReporteTLMK op : listaOtrosProductos) {

              otrosProductos += op.getOtrosProductos() + ", ";
            }
            otrosProductos = otrosProductos.substring(0, otrosProductos.length() - 1);
            r.setOtrosProductos(otrosProductos);
            otrosProductos = "";

          } else
            r.setOtrosProductos("No tiene otros productos con CARDIF");

        }

      }

      if (segmento != null) {
        Iterator<ReporteTLMK> reporteTLMK = listaReporteClientes.iterator();
        while (reporteTLMK.hasNext()) {
          ReporteTLMK r = reporteTLMK.next(); // must be called before
          // you can call
          // i.remove()
          if (segmento.contains("AD")) {
            if (!r.getOtrosProductos().equals("No tiene otros productos con CARDIF")) {
              reporteTLMK.remove();
            }
          } else if (segmento.contains("CS")) {
            if (r.getOtrosProductos().equals("No tiene otros productos con CARDIF")) {
              reporteTLMK.remove();
            }
          }
        }
      }

      size = (listaReporteClientes != null) ? listaReporteClientes.size() : 0;
      if (logger.isDebugEnabled())
        logger.debug("Output [Registros = " + size + "]");

    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
    }
    logger.info("Fin");
    return listaReporteClientes;
  }

}
