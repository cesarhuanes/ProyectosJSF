package com.cardif.satelite.suscripcion.service;

import java.io.File;
import java.util.Date;
import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.CanalProducto;
import com.cardif.satelite.model.CategoriaClase;
import com.cardif.satelite.model.ControlPaquete;
import com.cardif.satelite.model.Socio;
import com.cardif.satelite.model.TramaMaster;
import com.cardif.satelite.model.satelite.MasterPrecioSoat;
import com.cardif.satelite.model.satelite.TramaDiaria;
import com.cardif.satelite.suscripcion.bean.ConsultaMasterPrecio;
import com.cardif.satelite.suscripcion.bean.TramaEjecucion;

public interface CargMasterSoatService {

  //public List<ConsultaMasterPrecio> buscar(String socio, String departamento, String uso, String categoria, String canalVenta) throws SyncconException;

  // public MasterPrecioSoat buscar2(String socio, String localidad, String
  // uso, String categoria) throws SyncconException;
  public void guardarDataExcel(File excelFile, String socio, Date fecValidez) throws SyncconException;

  public List<ConsultaMasterPrecio> listar(String socio, String departamento, String uso, String categoria, String canalVenta) throws SyncconException;

  public Date fecValidezMax(String codSocio) throws SyncconException;

  public int deletePorFechaValidez(String codSocio, Date fecValidez) throws SyncconException;

  public void eliminarUltimaCarga(String codSocio, String usuCreacion);

 // public List<ConsultaFechaCargaMaster> buscar(String socio) throws SyncconException;

  public int deleteMasterPrecios(String socio, Date fecValidez) throws SyncconException;

  public List<Socio> getListaSocios() throws SyncconException;

  public List<CategoriaClase> getListCategoriaClase() throws SyncconException;

  public List<CanalProducto> getListCanales() throws SyncconException;
  
  // nuevos metodos

  public Boolean registrarArchivoMaster(TramaMaster master) throws SyncconException;

  public List<TramaDiaria> consultarArchivoBySocio(String socio) throws SyncconException;

  public List<MasterPrecioSoat> consultarCargaMaster(String socio,String canalVenta, String departamento, String uso, String categoria)  throws SyncconException;

  public Boolean eliminarArchivoMaster(String idMaster) throws SyncconException;

  public Boolean selectValidarMaster(String idsocio)  throws SyncconException;

  //public List<TramaMaster> getListaCargaMaster(String socio, Date fechaValidez, Date fechaCarga) throws SyncconException; --anterior funcionando
  public List<TramaMaster> getListaCargaMaster(String socio, Date fechaVigencia, Date fechaCarga) throws SyncconException;

  public int numeroRegistrosExcel(File archivoFinal) throws SyncconException;

  public List<ConsultaMasterPrecio> consultaMasterDetalle(String idMaster) throws SyncconException;
  	
  public List<TramaMaster> buscarTramaMasterCabecera(Long codSocio, Date fechaIniVigencia, Date fechaCarga) throws SyncconException;

  public TramaEjecucion ejecucionTrama() throws SyncconException;

  public Boolean insertarControlPaquete(ControlPaquete controlPaquete) throws SyncconException;

  public Long selectIdTrama(String flag_estado,Integer codSocio) throws SyncconException;
  	
}