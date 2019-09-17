package com.cardif.satelite.acsele.service;

import java.math.BigDecimal;
import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.acsele.Product;

public interface ProductService {
  public List<Product> buscar(String codSocio) throws SyncconException;

  public List<Product> buscarSoat() throws SyncconException;

  public Product obtener(BigDecimal codProducto) throws SyncconException;

  public String obtenerDescripcion(String codProducto) throws SyncconException;

  public String obtenerCodProducto(String producto) throws SyncconException;

}
