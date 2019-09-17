package com.cardif.satelite.tesoreria.service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.tesoreria.bean.ProveedorBean;

public interface ProveedorService {

  public ProveedorBean buscarProveedor(String unidadNegocio, String codProveedor, String rucProveedor) throws SyncconException;
}
