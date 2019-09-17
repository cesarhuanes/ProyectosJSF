package com.cardif.satelite.reportes.service;

import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.reportes.bean.RepSunatBean;
import com.cardif.satelite.reportes.bean.RepSunatCabeceraBean;

public interface RepSunatService
{
	
	public abstract List<RepSunatCabeceraBean> buscarReporteSunatCabecera(Integer anioPeriodo) throws SyncconException;
	
	public List<RepSunatBean> buscarPolizasReporteSunat(Integer anioPeriodo) throws SyncconException;
	
} //RepSunatService
