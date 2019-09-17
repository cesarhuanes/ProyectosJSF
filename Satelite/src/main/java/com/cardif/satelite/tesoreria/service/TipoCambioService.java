package com.cardif.satelite.tesoreria.service;

import com.cardif.satelite.tesoreria.bean.TipoCambioBean;

public interface TipoCambioService {

  public TipoCambioBean buscarTipoCambioDia(String unidadNegocio, String Fecha, String monedaDesde, String monedaHasta);

}
