package com.cardif.satelite.tesoreria.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.satelite.model.FirmantePar;
import com.cardif.satelite.tesoreria.dao.FirmanteMapper;
import com.cardif.satelite.tesoreria.dao.FirmanteParMapper;
import com.cardif.satelite.tesoreria.model.Firmante;
import com.cardif.satelite.tesoreria.service.FirmanteService;
import com.cardif.satelite.util.SateliteConstants;
import com.cardif.satelite.util.SateliteUtil;
import com.cardif.seguridad.AES;

@Service("firmanteService")
public class FirmanteServiceImpl implements FirmanteService, Serializable {

  private static final long serialVersionUID = 1L;
  public static final Logger LOGGER = Logger.getLogger(FirmanteServiceImpl.class);

  private List<String> estadoList;

  public FirmanteServiceImpl() {

    estadoList = new ArrayList<String>();
    estadoList.add(SateliteConstants.ESTADO_ACTIVO);
    estadoList.add(SateliteConstants.ESTADO_INACTIVO);
  }

  @Autowired
  private FirmanteMapper firmanteMapper;

  @Autowired
  private FirmanteParMapper firmanteParMapper;

  @Override
  public List<Firmante> buscarFirmantesActivos(String usuario) {
    try {
      usuario = SateliteUtil.resetString(usuario);
      List<Firmante> firmantes = firmanteMapper.buscarFirmantesActivos(usuario);
      LOGGER.info("Número de firmantes activos encontrados: " + firmantes.size());
      return firmantes;
    } catch (Exception e) {
      LOGGER.error(e);
      return new ArrayList<Firmante>();
    }
  }

  @Override
  public List<Firmante> buscarFirmantesTemp() {
    try {
      List<Firmante> firmantes = firmanteMapper.buscarFirmantesTemporales();
      LOGGER.info("Número de firmantes temporales encontrados: " + firmantes.size());
      return firmantes;
    } catch (Exception e) {
      LOGGER.error(e);
      return new ArrayList<Firmante>();
    }
  }

  @Override
  public String eliminarFirmanteTemporal(Firmante firmante) {
    try {
      int cont = firmanteMapper.eliminarFirmanteTemporal(firmante);
      LOGGER.info("Codigo temporal: " + firmante.getTemporal());
      LOGGER.info("Numero de registros eliminados: " + cont);

      return "El firmante temporal se eliminó con éxito";

    } catch (Exception e) {
      LOGGER.info(e);
      return "Ocurrio un error al eliminar";
    }
  }

  @Override
  public String insertarFirmanteTemporal(Firmante firmante) {
    try {
      if (null == firmante.getCodigo()) {
        int resultado = firmanteMapper.buscarFirmanteTemporal(firmante.getUsuario());
        if (resultado > 0) {
          return "No se registró el firmante porque el usuario ya esta registrado en temporales";
        }
        resultado = firmanteMapper.buscarFirmanteActivo(firmante.getUsuario());
        if (resultado > 0) {
          return "No se registró el firmante porque el usuario ya esta registrado en activos";
        }

        int cont = firmanteMapper.insertarFirmanteTemporal(firmante);
        LOGGER.info("Codigo temporal: " + firmante.getTemporal());
        LOGGER.info("Número de registros insertados: " + cont);
        return "El firmante temporal se registró con éxito";

      } else {
        List<Firmante> lista = firmanteMapper.buscarFirmantesTemporalesPorFirmante(firmante.getCodigo());
        if (lista.isEmpty()) {
          int cont = firmanteMapper.insertarFirmanteTemporal(firmante);
          LOGGER.info("Número de registros insertados: " + cont);
          return "El firmante temporal se registró con éxito";
        } else {
          return "No se puede registrar el cambio del firmante debido a que existe un pendiente";
        }
      }
    } catch (Exception e) {
      LOGGER.info(e);
      return "Ocurrio un error al insertar";
    }
  }

  @Override
  public List<FirmantePar> buscarParesDeFirmante(Firmante firmante) {
    try {
      if (firmante != null) {
        List<FirmantePar> pares = firmanteParMapper.buscarPares(firmante.getCodigo());
        return pares;
      }
    } catch (Exception e) {
      LOGGER.info(e);
    }
    return new ArrayList<FirmantePar>();
  }

  @Override
  public List<Firmante> buscarFirmantes() {
    try {
      List<Firmante> firmantes = firmanteMapper.buscarFirmantesActivos(null);
      LOGGER.info("Número de firmantes activos encontrados: " + firmantes.size());
      return firmantes;
    } catch (Exception e) {
      LOGGER.error(e);
      return new ArrayList<Firmante>();
    }
  }

  @Override
  public List<FirmantePar> buscarPares() {
    try {
      List<FirmantePar> pares = firmanteParMapper.buscarPares(null);
      return pares;
    } catch (Exception e) {
      LOGGER.info(e);
    }
    return new ArrayList<FirmantePar>();
  }

  @Override
  public String registrarPar(Long firmante1, Long firmante2) {
    try {

      if (firmante1 != null && firmante2 != null) {

        Long codigo1 = firmante1;
        Long codigo2 = firmante2;
        LOGGER.info("Codigo1: " + codigo1 + ", Codigo2:" + codigo2);
        if (!codigo1.equals(codigo2)) {
          FirmantePar firmantePar = new FirmantePar();
          firmantePar.setCodigoFirmante1(codigo1);
          firmantePar.setCodigoFirmante2(codigo2);
          firmantePar.setEstado(SateliteConstants.ESTADO_ACTIVO);
          List<FirmantePar> pares = firmanteParMapper.buscarPar(codigo1, codigo2);
          if (pares.size() == 0) {
            int rows = firmanteParMapper.insertarPar(firmantePar);
            LOGGER.info("Número de registros insertados: " + rows);
            return "El par de firmantes se registró con éxito";
          } else {
            return "El par de firmantes se encuentra registrado";
          }
        } else {
          return "Debe seleccionar 2 firmantes diferentes";
        }

      } else {
        return "Debe seleccionar 2 firmantes";
      }
    } catch (Exception e) {
      LOGGER.info(e);
      return "Ocurrió un error al regitrar el par";
    }
  }

  @Override
  public String eliminarPar(FirmantePar par) {
    try {

      par.setEstado(SateliteConstants.ESTADO_INACTIVO);
      firmanteParMapper.eliminarPar(par);
      return "El par de firmantes se eliminó con éxito";
    } catch (Exception e) {
      LOGGER.info(e);
      return "Ocurrió un error al eliminar el par";
    }
  }

  @Override
  public String aprobarFirmanteTemporal(Firmante firmante) {

    try {
      firmante.setEstadoAccion(SateliteConstants.ESTADO_APROBADO);
      firmanteMapper.aprobarFirmanteTemporal(firmante);
      if (SateliteConstants.REGISTRAR.equalsIgnoreCase(firmante.getNombreAccion())) {
        firmanteMapper.insertarFirmante(firmante);
        firmanteMapper.eliminarFirmanteTemporal(firmante);
        return "El nuevo firmante se registró con éxito";
      }
      if (SateliteConstants.MODIFICAR.equalsIgnoreCase(firmante.getNombreAccion())) {
        firmanteMapper.actualizarFirmante(firmante);
        firmanteMapper.eliminarFirmanteTemporal(firmante);

        return "El firmante se modificó con éxito";
      }
      if (SateliteConstants.ELIMINAR.equalsIgnoreCase(firmante.getNombreAccion())) {
        firmante.setEstado(SateliteConstants.ESTADO_INACTIVO);
        firmante.setMotivoEliminacion(firmante.getMotivoAccion());

        List<FirmantePar> pares = firmanteParMapper.buscarPares(firmante.getCodigo());
        for (FirmantePar par : pares) {
          par.setEstado(SateliteConstants.ESTADO_INACTIVO);
          firmanteParMapper.eliminarPar(par);
        }

        firmanteMapper.eliminarFirmante(firmante);
        firmanteMapper.eliminarFirmanteTemporal(firmante);

        return "El firmante se eliminó con éxito";
      }

      return "Se aprobó el firmante con éxito";
    } catch (Exception e) {
      LOGGER.info(e);
      return "Ocurrió un error al aprobar";
    }

  }

  @Override
  public String rechazarFirmanteTemporal(Firmante firmante) {
    try {
      firmante.setEstadoAccion(SateliteConstants.ESTADO_RECHAZADO);
      firmanteMapper.rechazarFirmanteTemporal(firmante);
      return "Se rechazó el firmante con éxito";
    } catch (Exception e) {
      LOGGER.info(e);
      return "Ocurrió un error al rechazar";
    }
  }

  @Override
  public Firmante obtenerFirmante(Long codigo) {
    try {
      Firmante firmante = firmanteMapper.buscarFirmante(codigo);
      LOGGER.info("Código de firmante: " + firmante.getCodigo());
      return firmante;
    } catch (Exception e) {
      LOGGER.info(e);
      return null;
    }
  }

  @Override
  public boolean validarFirmante(String usuario, String clave) {
    try {
      byte[] enc = AES.encrypt(clave.getBytes(), SateliteConstants.CLAVE_ENCRIPTACION.getBytes());
      List<Firmante> firmantes = firmanteMapper.validarFirmante(usuario, enc);
      LOGGER.info("Número de firmantes encontrados: " + firmantes.size());
      return firmantes.size() > 0;
    } catch (Exception e) {
      LOGGER.info(e);
      return false;
    }
  }

  @Override
  public void configurarClave(Firmante firmante, String clave) {
    try {
      byte[] enc = AES.encrypt(clave.getBytes(), SateliteConstants.CLAVE_ENCRIPTACION.getBytes());
      int cont = firmanteMapper.configurarClave(firmante.getCodigo(), enc);
      LOGGER.info("Número de firmantes actualizados: " + cont);
    } catch (Exception e) {
      LOGGER.error(e);
    }
  }

}
