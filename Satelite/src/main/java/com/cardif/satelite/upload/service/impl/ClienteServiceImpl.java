package com.cardif.satelite.upload.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_BUSCAR;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.upload.Cliente;
import com.cardif.satelite.upload.dao.ClienteMapper;
import com.cardif.satelite.upload.service.ClienteService;

/**
 * @author 2ariasju
 *
 */

@Service("clienteService")
public class ClienteServiceImpl implements ClienteService {

  public static final Logger log = Logger.getLogger(ClienteServiceImpl.class);

  @Autowired
  private ClienteMapper clienteMapper;

  @Override
  public List<Cliente> buscar(Long socioId, Long sequence) throws SyncconException {
    log.info("Inicio");
    List<Cliente> lista;
    int size = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + socioId + "," + sequence + "]");
      BigDecimal seq = new BigDecimal(sequence);
      lista = clienteMapper.selectBase(socioId, seq);
      size = (lista != null) ? lista.size() : 0;
      if (log.isDebugEnabled())
        log.debug("Output [Registros:" + size + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_BUSCAR);
    }

    log.info("Fin");
    return lista;
  }

  @Override
  public List<Cliente> buscarParcial(Long socioId, Long sequence, int inicio, int cantidad) throws SyncconException {
    log.info("Inicio");
    List<Cliente> lista;
    int size = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + socioId + "," + sequence + "," + inicio + "," + cantidad + "]");
      BigDecimal seq = new BigDecimal(sequence);
      lista = clienteMapper.selectBaseParcial(socioId, seq, new RowBounds(inicio, cantidad));
      size = (lista != null) ? lista.size() : 0;
      if (log.isDebugEnabled())
        log.debug("Output [Registros:" + size + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_BUSCAR);
    }

    log.info("Fin");
    return lista;
  }

}
