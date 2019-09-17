package com.cardif.satelite.tesoreria.service;

import java.util.Date;
import java.util.List;

import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.tesoreria.model.Cheque;

public interface ReporteImpresionService {
  List<Cheque> buscarCheques(Date fecImpresion1, Date fecImpresion3, String estado, String banco, String moneda);

  List<Parametro> getEstados();

  List<Parametro> getBancos();

  List<Parametro> getMonedas();

  List<String> getNomColumnas();

  List<Cheque> selectChequeByNro(String nro_cheque);

}
