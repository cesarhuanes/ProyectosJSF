package com.cardif.satelite.seguridad.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.Perfil;

public interface PerfilService {

  public List<Perfil> buscarPerfil(String nomPerfil, String descPerfil) throws SyncconException;

  public List<Perfil> buscarPerfil() throws SyncconException;

  public Perfil obtenerAuthority(String ldapGrupo) throws SyncconException;

  public Perfil obtener(Long codPerfil) throws SyncconException;

  public Perfil obtener(String authority) throws SyncconException;

  public void insertar(Perfil perfil) throws SyncconException;

  public void actualizar(Perfil perfil) throws SyncconException;

  public void eliminar(Long codPerfil) throws SyncconException;

}
