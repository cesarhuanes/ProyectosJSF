package com.cardif.satelite.telemarketing.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_ACTUALIZAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_BUSCAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_INSERTAR;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.Layout;
import com.cardif.satelite.model.TlmkBaseCabecera;
import com.cardif.satelite.suscripcion.bean.ConfLayout;
import com.cardif.satelite.suscripcion.bean.TramaDiariaBean;
import com.cardif.satelite.suscripcion.dao.LayoutMapper;
import com.cardif.satelite.telemarketing.dao.TlmkBaseCabeceraMapper;
import com.cardif.satelite.telemarketing.service.BaseCabeceraService;
import com.cardif.satelite.util.SateliteConstants;

/**
 * @author 2ariasju
 *
 */
@Service("baseCabeceraService")
public class BaseCabeceraServiceImpl implements BaseCabeceraService {

  public static final Logger log = Logger.getLogger(BaseCabeceraServiceImpl.class);

  @Autowired
  private TlmkBaseCabeceraMapper tlmkBaseCabeceraMapper;

  @Autowired
  private LayoutMapper layoutMapper = null; 
  
  @Override
  public List<TramaDiariaBean> obtenerTabla() throws SyncconException{	  
	  List<TramaDiariaBean> lista = null;
	  
	  lista = layoutMapper.SelectTramaDiaria();
	  
	  for (int i = 0; i < lista.size(); i++) {
		
		  
		  lista.get(i).setTipoDato(getTipoDeDataValue(lista.get(i).getTipoDato()));
		 	  
	  }
	    
	  return lista;
  }
  
  
  public String getTipoDeDataValue(String tipoData){
	  String respuesta="";
	 
	  if(tipoData.equalsIgnoreCase("VARCHAR2")){
		  respuesta = SateliteConstants.TIPO_DE_DATA2;
	  }else if (tipoData.equalsIgnoreCase("DATE")) {
		  respuesta = SateliteConstants.TIPO_DE_DATA3;
	  }else if (tipoData.equalsIgnoreCase("FLOAT") || tipoData.equalsIgnoreCase("NUMBER") ) {
		  respuesta = SateliteConstants.TIPO_DE_DATA1;
	  }	
	  
	  return respuesta;
  }
  
  
  @Override
  public List<ConfLayout> obtenerCabeceraExcel(Integer idproducto) throws SyncconException{
	 
	  log.debug("Input [1 .-"+idproducto + "]");
	  
	  List<Layout> lista = null;
	  List<TramaDiariaBean> listaBean = null;
	  
	  List<ConfLayout> lstLayout = new ArrayList<ConfLayout>();

	  listaBean = layoutMapper.SelectTramaDiaria();	  
	  lista = layoutMapper.listaLayoutByIdproducto(idproducto);
	  ConfLayout value = new ConfLayout();
	  
	  
		 for (int i = 0; i < lista.size(); i++) {		 
			 for (int j = 0; j < listaBean.size(); j++) {				 
				 if(lista.get(i).getColumnaTabla()!=null){		
					 if(lista.get(i).getColumnaTabla().contains(listaBean.get(j).getNombreColumna())){
						 	value= new  ConfLayout(lista.get(i).getIdcanal().toString(), lista.get(i).getDescripcion(), lista.get(i).getColumnaExcel(),  listaBean.get(j).getLabel(),listaBean.get(j).getNombreColumna(), "", "",lista.get(i).getTipodata(),getTipoDeDataValue(listaBean.get(j).getTipoDato()));
						 	break;
					 }
				 } else{
					 		value = new  ConfLayout(lista.get(i).getIdcanal().toString(), lista.get(i).getDescripcion(), lista.get(i).getColumnaExcel(),  "","", "", "", lista.get(i).getTipodata(),getTipoDeDataValue(listaBean.get(j).getTipoDato()) );						
				 }				 
			 }
			 
			 try {
				log.info("data -:" + BeanUtils.describe(value));
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			 lstLayout.add(value);
			 
		 }

	  return lstLayout;
	  
  }
  
  @Override
  public List<Layout> obtenerCabeceraExcelAll( Integer idproducto) throws SyncconException
  {  	  
	  List<Layout> lista = null;

		if (log.isInfoEnabled()) {log.info("Inicio");}
		
		try {
			
			log.debug("Input [ 1.-" + idproducto + " ]");
			
			lista = layoutMapper.listaLayoutByIdproducto(idproducto);			
			
		} catch (Exception e){
			log.error(e.getMessage(),e);
		} 	
		if (log.isInfoEnabled()) {log.info("Fin");}

	  
	  

	  return lista;
  }
  
  @Override
  public void insertar(TlmkBaseCabecera tlmkBaseCabecera) throws SyncconException {
    log.info("Inicio");

    try {
      log.debug("Input: [" + BeanUtils.describe(tlmkBaseCabecera) + "]");

      tlmkBaseCabecera.setFecCreacion(new Date(System.currentTimeMillis()));
      tlmkBaseCabeceraMapper.insert(tlmkBaseCabecera);

      log.debug("Output: [Ok]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }
    log.info("Fin");

  }

  @Override
  public void actualizar(TlmkBaseCabecera tlmkBaseCabecera) throws SyncconException {
    log.info("Inicio");

    try {
      log.debug("Input: [" + BeanUtils.describe(tlmkBaseCabecera) + "]");

      tlmkBaseCabecera.setFecModificacion(new Date(System.currentTimeMillis()));
      tlmkBaseCabeceraMapper.updateByPrimaryKey(tlmkBaseCabecera);

      log.debug("Output: [Ok]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_ACTUALIZAR);
    }
    log.info("Fin");

  }

  @Override
  public int codBaseMax(String codSocio) throws SyncconException {
    log.info("Inicio");
    int codBase = 0;

    try {
      log.debug("Input: [" + codSocio + "]");
      codBase = tlmkBaseCabeceraMapper.selectMaxCodBase(codSocio);
      log.debug("Output: [" + codBase + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_BUSCAR);
    }

    log.info("Fin");
    return codBase;
  }

  @Override
  public String buscarEstado(Long codBase) throws SyncconException {
    log.info("Inicio");
    String estado = null;
    try {
      estado = tlmkBaseCabeceraMapper.selectEstado(codBase);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_BUSCAR);
    }

    log.info("Fin");
    return estado;
  }

  @Override
  public List<TlmkBaseCabecera> buscar(String codSocio) throws SyncconException {
    log.info("Inicio");
    List<TlmkBaseCabecera> lista;
    try {
      lista = tlmkBaseCabeceraMapper.selectSocio(codSocio);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_BUSCAR);
    }

    log.info("Fin");
    return lista;
  }

  @Override
  public TlmkBaseCabecera obtener(Long codBase) throws SyncconException {
    log.info("Inicio");
    TlmkBaseCabecera base = null;
    try {
      log.debug("Input: [" + codBase + "]");
      base = tlmkBaseCabeceraMapper.selectByPrimaryKey(codBase);
      log.debug("Output: [" + BeanUtils.describe(base) + "]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }

    log.info("Fin");
    return base;
  }
  
  @Override
  public Boolean actualizarLayout(List<Layout> listaLayout) throws SyncconException{
	
	  Boolean taks = false;
	  if (log.isInfoEnabled()) {log.info("Inicio");}
		
		try {
			log.debug("Input [ 1.-"+ BeanUtils.describe(listaLayout) +" ]");
			
			for (Layout layout : listaLayout) {
				tlmkBaseCabeceraMapper.UpdateByLayoutNombreColumna(layout);
			}
			log.debug("Output [ 1.- ]");
		} catch (Exception e){
			log.error(e.getMessage(),e);
					
		} 	
		if (log.isInfoEnabled()) {log.info("Fin");}
		return taks;
  }

  
  @Override
  public List<TramaDiariaBean> selectTramaDiaraTemp() throws SyncconException{
	  List<TramaDiariaBean> lista = null;
	  if(log.isInfoEnabled()){log.info("Inicio");}
	  
	  	try {
	  		lista = layoutMapper.SelectTramaDiariaTemp();
	  		
			log.debug("Output [ 1.-" + lista.size()+ "]");
		} catch (Exception e) {
			// TODO: handle exception
		}
	  
	  if(log.isInfoEnabled()){log.info("Fin");}	  
	  return lista;
  }
}
