package com.cardif.satelite.suscripcion.service;

import com.cardif.framework.excepcion.SyncconException;

public interface ProcesosETLsService {
	
	
	public  boolean procesosMarcaModelo() throws SyncconException;
	public  boolean procesosTramaDiaria()throws SyncconException;
	public  boolean procesosTramaMensual() throws SyncconException;
	public boolean procesosCargaMaster() throws SyncconException; 
	public boolean procesosCargaConciliacionTrama() throws SyncconException; 
	public boolean procesosCargaConciliacion() throws SyncconException;
	
}
