package com.cardif.satelite.suscripcion.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.model.CanalProducto;
import com.cardif.satelite.model.ControlPaquete;
import com.cardif.satelite.model.Layout;
import com.cardif.satelite.model.Producto;
import com.cardif.satelite.model.Socio;
import com.cardif.satelite.model.satelite.ConciCabTramaDiaria;
import com.cardif.satelite.model.satelite.ConciTramaDiaria;
import com.cardif.satelite.suscripcion.bean.ConfLayout;
import com.cardif.satelite.suscripcion.bean.FormatoObservaciones;
import com.cardif.satelite.suscripcion.bean.SocioProductoBean;
import com.cardif.satelite.suscripcion.bean.TramaEjecucion;

public interface CargTramaDiariaSoatService {
  public List<ConciCabTramaDiaria> buscar(String producto) throws SyncconException;

  public ConciTramaDiaria insertar(ConciTramaDiaria base) throws SyncconException;

  public Boolean insertar(ConciCabTramaDiaria conciCabTramaDiaria) throws SyncconException;

  public Long obtener(String producto) throws SyncconException;

  public void eliminar(Long codSecuencia) throws SyncconException;

  public Long consultar(Long codSecuencia) throws SyncconException;
  
  public List<Socio> listarSocio() throws SyncconException;
  
  public void actualiza(ConciCabTramaDiaria conciCabTramaDiaria, Long codSecuencia) throws SyncconException;

  public void actualizaCabTramaDiaria(Long codSecuencia) throws SyncconException;

  public List<SocioProductoBean> listaSocioProducto(Integer idcanal,String flagTrama) throws SyncconException;

  public int numeroRegistrosExcel(File archivoFinal) throws SyncconException;

  public List<CanalProducto> listaCanalesProducto(String idsocio) throws SyncconException;

  public boolean actualizarLayout(ConfLayout layout)throws SyncconException;

  public Boolean insertarControlPaquete(ControlPaquete controlPaquete) throws SyncconException; 
  
  public Boolean actualizarControlPaquete (ControlPaquete controlPaquete) throws SyncconException;
  
  public ControlPaquete selectByIdPaquete (Long id) throws SyncconException;
  
  public List<ControlPaquete> selectAllPaquete() throws SyncconException;

  public TramaEjecucion ejecucionTrama() throws SyncconException;

  public List<Layout> validarExcelByBaseDatos(File archivoFinal,  List<Layout>  excel)throws SyncconException;

  public boolean insertLayout(Layout layout) throws SyncconException;

  public String[] numeroRegistrosTXT(File archivoFinal,String sparacion,String codProducto) throws SyncconException;

  public int selectCorrelativo(String socio) throws SyncconException;

  public Producto selectProductoByID(int producto) throws SyncconException;

  public Long selectIdTrama(String flag_estado) throws SyncconException;

  public   Boolean executePakage();

  public List<HashMap<String, Object>> exportarObservado(String Columns)  throws SyncconException;

  public  List<Layout> listaLayoutByIdproducto(int idproducto)  throws SyncconException;

  public List<FormatoObservaciones> listaObservaciones() throws SyncconException;


}
