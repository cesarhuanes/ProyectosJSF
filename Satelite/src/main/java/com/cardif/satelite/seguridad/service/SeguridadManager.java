package com.cardif.satelite.seguridad.service;

import com.cardif.framework.excepcion.SyncconException;

public interface SeguridadManager {

  public void validarUsuario(String usuario, String contrasena) throws SyncconException;

  public void cerrarSesion();

}