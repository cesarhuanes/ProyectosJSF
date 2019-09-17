package com.cardif.satelite.telemarketing.service;

import java.util.List;

import com.cardif.satelite.model.TlmkLayoutSocioCardif;

public interface TlmkLayoutCardifService {

  public List<TlmkLayoutSocioCardif> listarLayoutCardif(String codSocio) throws Exception;

}
