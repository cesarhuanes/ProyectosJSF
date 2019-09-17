package com.cardif.satelite.suscripcion.service.impl;



import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_ELIMINAR;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.acsele.service.ProductService;
import com.cardif.satelite.configuracion.dao.ParametroMapper;
import com.cardif.satelite.configuracion.dao.ProductoMapper;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.satelite.ConciCabTramaMensual;
import com.cardif.satelite.suscripcion.bean.SocioProductoBean;
import com.cardif.satelite.suscripcion.dao.CanalSubProductoMapper;
import com.cardif.satelite.suscripcion.dao.ConciCabTramaMensualMapper;
import com.cardif.satelite.suscripcion.dao.ConciTramaMensualMapper;
import com.cardif.satelite.suscripcion.service.CargaTramaMensualService;

/**
 * @author torresgu2
 * @create 2014.07.11
 * @version 0.1
 * @description Carga de la Trama Mensual para Conciliacion SOAT
 */
@Service("cargaTramaMensualService")
public class CargaTramaMensualServiceImpl implements CargaTramaMensualService {

  public static final Logger log = Logger.getLogger(CargaTramaMensualServiceImpl.class);
 
  @Autowired
  private ConciTramaMensualMapper tramaMenConciliacionMapper;
  
  @Autowired
  private CanalSubProductoMapper canalSubProductoMapper;
  
  @Autowired
  private ConciCabTramaMensualMapper cabTramaMenConciliacionMapper;
  
  @Autowired
  private ProductoMapper productoMapper;
  
  @Autowired
  private ProductService productService;

  @Autowired
  private ParametroMapper parametroMapper;


  @Override
  public int numeroRegistrosExcel(File archivoFinal) throws SyncconException {
	  int count = -1;
		try {			
			XSSFSheet  xssfReader = null;
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream (archivoFinal));
		  	xssfReader = wb.getSheetAt(0);
		  	count = xssfReader.getRow(0).getLastCellNum();	  		  	
		} catch (FileNotFoundException e) {	
		} catch (IOException e) {		
		}	 
		return count;
  }

  @Override
  public boolean insertar(ConciCabTramaMensual conciCabTramaMensual)	throws SyncconException {
	  	log.info("Inicio");
	  	
	  	boolean flag = false;
	  	
	    try {
	      if (log.isDebugEnabled())
	    	  
	        log.debug("Output [" + BeanUtils.describe(conciCabTramaMensual) + "]");	      
	      		cabTramaMenConciliacionMapper.insert(conciCabTramaMensual);	      	
	      	flag = true;
	      	
	      	log.debug("Output: [Ok]");
	    } catch (Exception e) {
	      log.error(e.getMessage(), e);
	      flag = false;
	      throw new SyncconException(ErrorConstants.COD_ERROR_BD_INSERTAR);
	    }
	   
	    log.info("Fin");
	    
	    return flag;
  }

  @Override
  public List<ConciCabTramaMensual> buscarSocio(String producto) throws SyncconException {
  
	  List<ConciCabTramaMensual> lista = null;
	    int size = 0;

	    log.info("Inicio");
	    try {
	      if (log.isDebugEnabled())
	        log.debug("Input [1.-" + producto + "]");
	      producto = StringUtils.isBlank(producto) ? null : producto;

	      if (log.isDebugEnabled())
	        log.debug("Input [1.-" + producto + "]");
	      lista = cabTramaMenConciliacionMapper.listaArchivos(producto);
	      size = (lista != null) ? lista.size() : 0;
	      if (log.isDebugEnabled())
	        log.debug("Output [Regitros:" + size + "]");

	    } catch (Exception e) {
	      log.error(e.getMessage(), e);
	      throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
	    }
	    log.info("Fin");

	    return lista;
	  
  }

  @Override
  public void eliminar(Long codSecuencia) throws SyncconException {
	  log.info("Inicio");
	    try {
	      if (log.isDebugEnabled())
	        log.debug("Input [" + codSecuencia + "]");
	      
	      cabTramaMenConciliacionMapper.deleteByPrimaryKey(codSecuencia);
	      
	      if (log.isDebugEnabled())
	        log.debug("Output []");

	    } catch (Exception e) {
	      log.error(e.getMessage(), e);
	      throw new SyncconException(COD_ERROR_BD_ELIMINAR);
	    }
	    log.info("Fin");
  }
  
  @Override
  public List<SocioProductoBean> listaSocioProducto(Integer idcanal) throws SyncconException{
	  
	  List<SocioProductoBean> lista = null;
	  if (log.isInfoEnabled()) {log.info("Inicio");}
	  lista =  productoMapper.listaSocioProducto(idcanal);
	  if (log.isInfoEnabled()) {log.info("final");}
	  
	  return lista;
  }

@Override
public synchronized Boolean executePakage() {
	
	Boolean status = false;
	
	String ruta ="C:/Program Files (x86)/Microsoft SQL Server/100/DTS/Binn/";
	String PKG = "C:/Users/quispelu/Documents/Visual Studio 2008/Projects/PKG_SOAT_Trama_Mensual/PKG_SOAT_Trama_Mensual/SOAT_Trama_Mensual.dtsx";
	String filePKG = "cmd /c \" cd "+ruta +" &&  dtexec /f \""+ PKG +"\" \" ";
			
		try {
			Process executeProcess = Runtime.getRuntime().exec(filePKG);
			BufferedReader output = new BufferedReader(new InputStreamReader(new DataInputStream(executeProcess.getInputStream())));
			String readStr;
			while ((readStr = output.readLine()) != null) {
			if(log.isInfoEnabled()){log.info(readStr);}
			}
			
			output.close();		 
			executeProcess.waitFor();
			
			if (executeProcess.exitValue() == 0) {
				if(log.isInfoEnabled()){log.info("SSIS execution succeeded\n");}
				status = true;
			} else {
				if(log.isInfoEnabled()){log.info("SSIS execution failed\n");}
				status = false;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage(),e);

		} // ruta donde se encuentre el paquete
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage(),e);
		}
		
	return status;
}
  
  
}
