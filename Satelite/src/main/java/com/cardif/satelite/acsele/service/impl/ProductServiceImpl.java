package com.cardif.satelite.acsele.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.acsele.dao.ProductMapper;
import com.cardif.satelite.acsele.service.ProductService;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.acsele.Product;

@Service("productService")
public class ProductServiceImpl implements ProductService {
  public static final Logger log = Logger.getLogger(ProductServiceImpl.class);
  @Autowired
  private ProductMapper productMapper;

  @Override
  public List<Product> buscar(String codSocio) throws SyncconException {
    log.info("inicio");
    List<Product> lista = null;
    int size = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + codSocio + "]");
      lista = productMapper.selectProduct(codSocio);
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

  @Override
  public Product obtener(BigDecimal codProducto) throws SyncconException {
    log.info("Inicio");
    Product producto = null;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + codProducto + "]");
      producto = productMapper.selectByPrimaryKey(codProducto);
      if (log.isDebugEnabled())
        log.debug("Output [" + BeanUtils.describe(producto) + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return producto;
  }

  @Override
  public String obtenerDescripcion(String codProducto) throws SyncconException {

    log.info("Inicio");
    String productoDescripcion = null;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + codProducto + "]");
      productoDescripcion = productMapper.consultarDescripcion(codProducto);
      if (log.isDebugEnabled())
        log.debug("Output [" + productoDescripcion + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return productoDescripcion;
  }

  @Override
  public List<Product> buscarSoat() throws SyncconException {
    log.info("Inicio");
    List<Product> lista = null;
    int size = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + "]");
      lista = productMapper.selectProductSOAT();
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

  @Override
  public String obtenerCodProducto(String producto) throws SyncconException {
    log.info("Inicio");
    String codProducto = null;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + producto + "]");
      codProducto = productMapper.selectCodProducto(producto);
      if (log.isDebugEnabled())
        log.debug("Output [" + codProducto + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    log.info("Fin");
    return codProducto;
  }

}
