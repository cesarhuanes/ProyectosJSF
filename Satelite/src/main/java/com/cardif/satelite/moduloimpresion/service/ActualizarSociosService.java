package com.cardif.satelite.moduloimpresion.service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.soat.model.SocioDigitales;

public interface ActualizarSociosService {

	public Boolean actualizarSocioFalabella(SocioDigitales socio) throws SyncconException;
	public Boolean actualizarSocioRipley(SocioDigitales socio) throws SyncconException;
	public Boolean actualizarSocioComparaBien(SocioDigitales socio) throws SyncconException;
	public Boolean actualizarSocioCencosud(SocioDigitales socio) throws SyncconException;
	public Boolean actualizarSocioDirecto(SocioDigitales socio) throws SyncconException;

	
	
}
