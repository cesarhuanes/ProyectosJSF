package com.cardif.satelite.acsele.service;

import java.math.BigDecimal;
import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.acsele.bean.ConsultaOpenItem;
import com.cardif.satelite.model.acsele.OpenItem;

public interface OpenItemService {

  public List<ConsultaOpenItem> cargaDetallesPoliza(BigDecimal idPoliza, String numPoliza) throws SyncconException;

  public List<OpenItem> listarOpenItem(String nroCertificado) throws SyncconException;

}
