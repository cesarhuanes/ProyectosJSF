package com.cardif.satelite.reportes.service;

import java.util.Date;
import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.reportes.bean.RepFalabellaBean;

public interface RepFalabellaService
{
	
	public abstract List<RepFalabellaBean> buscarPolizasDespacho(Date fecVentaDesde, Date fecVentaHasta) throws SyncconException;
	
	public abstract List<RepFalabellaBean> buscarPolizasAnulacion(Date fecVentaDesde, Date fecVentaHasta) throws SyncconException;
	
} //RepFalabellaService
