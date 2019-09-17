package com.cardif.satelite.tesoreria.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.satelite.configuracion.dao.ParametroMapper;
import com.cardif.satelite.configuracion.service.ParametroService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.tesoreria.dao.ChequeMapper;
import com.cardif.satelite.tesoreria.dao.FirmanteMapper;
import com.cardif.satelite.tesoreria.model.Cheque;
import com.cardif.satelite.tesoreria.service.ReporteImpresionService;
import com.cardif.satelite.util.SateliteUtil;

@Service("reporteImpresionService")
public class ReporteImpresionServiceImpl implements ReporteImpresionService {

  @Autowired
  private FirmanteMapper firmanteMapper;

  @Autowired
  private ChequeMapper chequeMapper;

  @Autowired
  private ParametroMapper parametroMapper;

  @Autowired
  private ParametroService parametroService;

  @Override
  public List<Cheque> buscarCheques(Date fecImpresion1, Date fecImpresion3, String estado, String banco, String moneda) {

    return chequeMapper.selectCheques(fecImpresion1, fecImpresion3, SateliteUtil.resetString(estado), SateliteUtil.resetString(banco), SateliteUtil.resetString(moneda));
  }

  @Override
  public List<String> getNomColumnas() {
    return chequeMapper.selectNomColumnas();
  }

  @Override
  public List<Parametro> getBancos() {
    return parametroMapper.getBancos();
  }

  @Override
  public List<Parametro> getEstados() {
    return parametroMapper.getChequesEstadoImpresion();
  }

  @Override
  public List<Parametro> getMonedas() {
    return parametroMapper.selectParametro(Constantes.MDP_MONEDAS, Constantes.TIP_PARAM_DETALLE);
  }

  @Override
  public List<Cheque> selectChequeByNro(String nro_cheque) {
    return chequeMapper.selectChequeByNro(nro_cheque);
  }

}
