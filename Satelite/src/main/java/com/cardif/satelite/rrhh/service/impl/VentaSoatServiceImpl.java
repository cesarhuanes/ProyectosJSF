/**
 * 
 */
package com.cardif.satelite.rrhh.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_BUSCAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_INSERTAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_OBTENER;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.VentaSoatEmpl;
import com.cardif.satelite.rrhh.dao.VentaSoatEmplMapper;
import com.cardif.satelite.rrhh.service.VentaSoatService;

/**
 * @author 2ariasju
 *
 */
@Service("ventaSoatService")
public class VentaSoatServiceImpl implements VentaSoatService {

  public static final Logger log = Logger.getLogger(VentaSoatServiceImpl.class);

  @Autowired
  private VentaSoatEmplMapper ventaSoatEmplMapper;

  @Override
  public void insertar(VentaSoatEmpl ventaSoatEmpl) throws SyncconException {
    log.info("Inicio");

    try {
      log.debug("Input: [" + BeanUtils.describe(ventaSoatEmpl) + "]");

      ventaSoatEmpl.setFecCreacion(new Date(System.currentTimeMillis()));
      ventaSoatEmplMapper.insert(ventaSoatEmpl);

      log.debug("Output: [Ok]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }
    log.info("Fin");

  }

  @Override
  public void actualizar(VentaSoatEmpl ventaSoatEmpl) throws SyncconException {
    log.info("Inicio");
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + BeanUtils.describe(ventaSoatEmpl).toString() + "]");

      Date fecActual;
      fecActual = new Date(System.currentTimeMillis());
      ventaSoatEmpl.setFecModificacion(fecActual);
      ventaSoatEmplMapper.updateByPrimaryKey(ventaSoatEmpl);
      if (log.isDebugEnabled())
        log.debug("Output [Ok]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_ACTUALIZAR);
    }
    log.info("Fin");
  }

  @Override
  public VentaSoatEmpl obtener(String nroPoliza) throws SyncconException {
    log.info("Inicio");
    VentaSoatEmpl resulVentaSoatEmpl = null;
    try {
      resulVentaSoatEmpl = ventaSoatEmplMapper.selectByPrimaryKey(nroPoliza);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return resulVentaSoatEmpl;
  }

  @Override
  public String obtenerMaxPoliza() throws SyncconException {
    log.info("Inicio");
    String nroPoliza = null;
    try {
      nroPoliza = ventaSoatEmplMapper.selectMaximo();
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return nroPoliza;
  }

  @Override
  public List<VentaSoatEmpl> listar() throws SyncconException {
    log.info("Inicio");
    List<VentaSoatEmpl> lista = null;
    int size = 0;
    try {
      // Invocan mapper
      log.debug("Input: [" + "]");

      // insertar set fecha de creacion
      lista = ventaSoatEmplMapper.selectPolizas();
      size = (lista != null) ? lista.size() : 0;
      log.debug("Output: [Registros:" + size + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }
    log.info("Fin");
    return lista;
  }

  @Override
  public List<VentaSoatEmpl> buscar(Date desde, Date hasta, String razonSocial) throws SyncconException {
    log.info("Inicio");
    List<VentaSoatEmpl> lista = null;
    int size = 0;
    try {
      // Invocan mapper
      if (log.isDebugEnabled())
        log.debug("Input [" + desde + "," + desde + "," + razonSocial + "]");
      razonSocial = (StringUtils.isEmpty(razonSocial)) ? null : razonSocial;
      // insertar set fecha de creacion
      lista = ventaSoatEmplMapper.buscarPoliza(desde, hasta, razonSocial);
      size = (lista != null) ? lista.size() : 0;
      log.debug("Output: [Registros:" + size + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_BUSCAR);
    }
    log.info("Fin");
    return lista;
  }

}
