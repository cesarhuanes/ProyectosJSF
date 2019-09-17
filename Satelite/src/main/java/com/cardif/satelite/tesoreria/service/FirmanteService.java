package com.cardif.satelite.tesoreria.service;

import java.util.List;

import com.cardif.satelite.model.FirmantePar;
import com.cardif.satelite.tesoreria.model.Firmante;

public interface FirmanteService {

  List<Firmante> buscarFirmantesActivos(String usuario);

  List<Firmante> buscarFirmantesTemp();

  String eliminarFirmanteTemporal(Firmante firmanteTemp);

  String insertarFirmanteTemporal(Firmante firmanteNuevo);

  List<FirmantePar> buscarParesDeFirmante(Firmante firmante);

  List<Firmante> buscarFirmantes();

  List<FirmantePar> buscarPares();

  String registrarPar(Long firmante1, Long firmante2);

  String eliminarPar(FirmantePar par);

  String aprobarFirmanteTemporal(Firmante firmanteTemp);

  String rechazarFirmanteTemporal(Firmante firmanteTemp);

  Firmante obtenerFirmante(Long codigo);

  boolean validarFirmante(String usuario, String clave);

  void configurarClave(Firmante firmante, String clave);

}
