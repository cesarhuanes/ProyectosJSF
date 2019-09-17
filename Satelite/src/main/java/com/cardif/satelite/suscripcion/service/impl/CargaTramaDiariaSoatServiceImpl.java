package com.cardif.satelite.suscripcion.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_INSERTAR;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.dao.CanalProductoMapper;
import com.cardif.satelite.configuracion.dao.ProductoMapper;
import com.cardif.satelite.constantes.ErrorConstants;
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
import com.cardif.satelite.suscripcion.dao.ConciCabTramaDiariaMapper;
import com.cardif.satelite.suscripcion.dao.ConciTramaDiariaMapper;
import com.cardif.satelite.suscripcion.dao.ControlPaqueteMapper;
import com.cardif.satelite.suscripcion.dao.LayoutMapper;
import com.cardif.satelite.suscripcion.dao.SocioTramaMapper;
import com.cardif.satelite.suscripcion.service.CargTramaDiariaSoatService;
import com.cardif.satelite.util.SateliteConstants;
import com.cardif.satelite.util.SateliteUtil;

//import com.cardif.sunsystems.util.ConstantesSun;

/**
 * @author maronlu
 */
@Service("cargaTramaDiariaSoatService")
public class CargaTramaDiariaSoatServiceImpl implements CargTramaDiariaSoatService {
  public static final Logger log = Logger.getLogger(CargaTramaDiariaSoatServiceImpl.class);

  @Autowired
  private ConciCabTramaDiariaMapper conciCabTramaDiariaMapper  = null;

  @Autowired
  private ConciTramaDiariaMapper conciTramaDiariaMapper  = null;
  ConciTramaDiaria conciTramaDiaria;
 
  @Autowired
  private SocioTramaMapper socioTramaMapper  = null; 
  
  @Autowired
  private CanalProductoMapper canalProductoMapper  = null;
  
  @Autowired
  private ProductoMapper productoMapper = null;
  
  @Autowired
  private LayoutMapper layoutMapper = null;
   
  @Autowired
  private ControlPaqueteMapper controlPaqueteMapper = null;
  
 
  
  @Override
  public List<ConciCabTramaDiaria> buscar(String producto) throws SyncconException {
    List<ConciCabTramaDiaria> lista = null;
    int size = 0;

    log.info("Inicio");
    try {
      if (log.isDebugEnabled())
        log.debug("Input [1.-" + producto + "]");
      producto = StringUtils.isBlank(producto) ? null : producto;

      if (log.isDebugEnabled())
        log.debug("Input [1.-" + producto + "]");
      lista = conciCabTramaDiariaMapper.listaArchivos(producto);
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
  public List<SocioProductoBean> listaSocioProducto(Integer idcanal,String flagTrama)   throws SyncconException{
	  
	  List<SocioProductoBean> lista = null;
	  if (log.isInfoEnabled()) {log.info("Inicio");}
		
		try {
			log.debug("Input [ 1.-"+idcanal+" 2.- "+ flagTrama +" ]");
			
				if(flagTrama.equals(SateliteConstants.FLAG_TRAMA_DIARIA)){
			

					  lista =  productoMapper.listaSocioProductoDiarias(idcanal);
					
				}else if(flagTrama.equals(SateliteConstants.FLAG_TRAMA_MENSUAL)){
					

					  lista =  productoMapper.listaSocioProductoMensuales(idcanal);
					
				}
			
			  
			log.debug("Output [ 1.- ]");
		} catch (Exception e){
					log.error(e.getMessage(),e);
					throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		} 	
		if (log.isInfoEnabled()) {log.info("Fin");}
 
	  return lista;
  }
  
  
  @Override
  public List<CanalProducto> listaCanalesProducto(String idsocio)  throws SyncconException{
	  List<CanalProducto> lista = null;
	  	
	  log.info("Inicio");
	  	
	  	try {
	  		
	  	 // lista = canalProductoMapper.selectAll(idsocio);
	      log.debug("Output [" + "COUNT ListaCanalProducto: " + lista.size() + "]");
	      
		} catch (Exception e) {
			 log.error(e.getMessage(), e);
		       throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
		}
	  
	  log.info("Fin");
	  
	  return lista;
	  
  }
  
  @Override
  public boolean actualizarLayout(ConfLayout conflayout)throws SyncconException{
	
		if (log.isInfoEnabled()) {log.info("Inicio");}
				
		try {
				log.debug("Input [ 1.- " + BeanUtils.describe(conflayout)+ "]");
			
				Layout layout = new Layout();
				layout.setColumnaExcel(conflayout.getValueExcel());
				layout.setColumnaTabla(conflayout.getValueTabla());
				layout.setIdcanal(Long.valueOf(conflayout.getId()));
				layout.setUsuarioModifica(conflayout.getUsuario());
				layout.setFechaModificacion(Calendar.getInstance().getTime());				
				layoutMapper.UpdateByLayoutNombreColumna(layout);
				
				log.debug("Output [ 1.- ]");
		} catch (Exception e){
					log.error(e.getMessage(),e);
					throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
		} 	
		if (log.isInfoEnabled()) {log.info("Fin");}
	  
	  return true;
  }
  
  @Override
  public boolean insertLayout(Layout layout)throws SyncconException{
		if (log.isInfoEnabled()) {log.info("Inicio");}
		Boolean status = false;
		try {
		
			
				log.debug("Input [ 1.- "+ BeanUtils.describe(layout) +"]");
				
			
				layoutMapper.insertLayoutExcel(layout);
			
			status = true;
			log.debug("Output [ 1.- ]");
		} catch (Exception e){
					log.error(e.getMessage(),e);
					status = false;
					throw new SyncconException();
		} 	
		if (log.isInfoEnabled()) {log.info("Fin");}

	  return status;
  }
  
  @Override
  public List<Socio> listarSocio() throws SyncconException {
	  List<Socio> lista = null;
	  	
	  	log.info("Inicio");
	  
	    try {	    	
	    	
	    	lista = socioTramaMapper.listaSocios();

	        log.debug("Output [" + "COUNT listaSocios: " + lista.size() + "]");
	    } catch (Exception e) {
	        log.error(e.getMessage(), e);
	        throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
	    }
	    
	    log.info("Fin");  	  
	    
	    return lista;

  }
  
  @Override
  public ConciTramaDiaria insertar(ConciTramaDiaria base) throws SyncconException {
    log.info("Inicio");

    try {
      if (log.isDebugEnabled())
        log.debug("Output [" + BeanUtils.describe(base) + "]");
      conciTramaDiariaMapper.insert(base);
      log.debug("Output: [Ok]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_INSERTAR);
    }
    log.info("Fin");
    return null;
  }

  @Override
  public Boolean insertar(ConciCabTramaDiaria conciCabTramaDiaria) throws SyncconException {
    log.info("Inicio");
    Boolean flag = false;
    
    try {
    
      log.debug("Input: [" + BeanUtils.describe(conciCabTramaDiaria) + "]");
      conciCabTramaDiariaMapper.insert(conciCabTramaDiaria);
      flag = true;
      log.debug("Output: [Ok]");

    } catch (Exception e) {
      
      flag = false;
    	
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_INSERTAR);
    }
    log.info("Fin");

    return flag;
  }

  @Override
  public Long obtener(String producto) throws SyncconException {
    log.info("Inicio");
    Long secuencia = null;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + producto + "]");
      secuencia = conciCabTramaDiariaMapper.obtener(producto);
      if (log.isDebugEnabled())
        log.debug("Output [" + BeanUtils.describe(secuencia) + "]");
      log.debug("Output: [Ok]");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);

    }
    log.info("Fin");
    return secuencia;
  }

  @Override
  public void eliminar(Long codSecuencia) throws SyncconException {
    log.info("Inicio");
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + codSecuencia + "]");
      int result = -1;
      // Elimina tabla data de tabla baseUnificada
      conciCabTramaDiariaMapper.deletePorSecuencia(codSecuencia);
      log.debug("respueta ELIMINANDO DETALLE TRAMA DIARAIA" + result);
     	// Elimina cabecera base
      
      conciCabTramaDiariaMapper.deleteByPrimaryKey(codSecuencia);
            
         
      
      if (log.isDebugEnabled())
        log.debug("Output []");

    } catch (Exception e) {
    //  log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_INFO_ELIMINAR_TRAMAS,FacesMessage.SEVERITY_INFO);
    }
    log.info("Fin");

  }

  @Override
  public  List<Layout> validarExcelByBaseDatos(File archivoFinal, List<Layout> excel) throws SyncconException {
    
    if(log.isInfoEnabled()){log.debug("Inicio");}

    	List<Layout> lista = new ArrayList<Layout>();
		List<Layout> tmp = new ArrayList<Layout>();		
		Layout obj = null;
		String value = getFileExtension(archivoFinal);
	try {
    	
		int pk=0;
    	if(value.equalsIgnoreCase("xlsx")){
    		XSSFSheet xssfReader = null;
        	XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream (archivoFinal));
        	xssfReader = wb.getSheetAt(0);
        	XSSFRow headerRow = xssfReader.getRow(0);     	
        	
        	//OBTENER LISTA DE EXCEL
        	for (int col = 0; col < headerRow.getLastCellNum(); ++col) 
        	{ 
        		XSSFCell cell = headerRow.getCell(col);
        		
        		if (cell.getStringCellValue()!=null) {
        			obj = new Layout();
        			obj.setColumnaExcel(cell.getStringCellValue());
            		lista.add(obj);	
    			}			
        	}   	
    	}else if (value.equalsIgnoreCase("xls")) {
    		
    			HSSFSheet hxssfReader = null;
    			HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(archivoFinal));
    			hxssfReader = wb.getSheetAt(0);
    			HSSFRow HheaderRow  = hxssfReader.getRow(0);
    			//	obtener lista excel
    			for (int i = 0; i < HheaderRow.getLastCellNum(); i++) {
					HSSFCell cellx = HheaderRow.getCell(i);
					if(cellx.getStringCellValue()!=null){
						obj = new Layout();
	        			obj.setColumnaExcel(cellx.getStringCellValue());
	            		lista.add(obj);	
					}
				}    		
    		
		}
    	for (Layout items : excel) {
			for(Layout items2 : lista){
				if(items.getColumnaExcel().toString().equalsIgnoreCase(items2.getColumnaExcel().toString())){
					tmp.add(items2);
				}
			}
		}    	
    	
    	for (Layout items : tmp) {
    		lista.remove(items);
		}
    		
	    if (log.isDebugEnabled())
	        log.debug("Output [Ok] ");
	    
	    } catch (Exception e) {
	      log.error(e.getMessage(), e);
	      //throw new SyncconException(ErrorConstants.COD_ERROR_BD_INSERTAR);
	      SateliteUtil.mostrarMensaje(FacesMessage.SEVERITY_ERROR, "Error en el archivo.");
	    }
		
    if(log.isInfoEnabled()){log.debug("Fin");}
    
    return lista;

  }
  
  @Override
  public int numeroRegistrosExcel(File archivoFinal) throws SyncconException{
	int count = -1;
	String value =getFileExtension(archivoFinal);
	try {
		
		if(value.equalsIgnoreCase("xlsx")){
			XSSFSheet  xssfReader = null;
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream (archivoFinal));
		  	xssfReader = wb.getSheetAt(0);
		  	count = xssfReader.getLastRowNum();  
		  	
		}else if (value.equalsIgnoreCase("xls")) {
			HSSFSheet xssfReader = null;
			HSSFWorkbook wb =  new HSSFWorkbook(new FileInputStream(archivoFinal));
			xssfReader = wb.getSheetAt(0);
			count = xssfReader.getLastRowNum();
		}
		 
	} catch (FileNotFoundException e) {
	      log.error(e.getMessage(), e);
	} catch (IOException e) {
	      log.error(e.getMessage(), e);
	} 
	  return count;
}

  private static String getFileExtension(File file) {
      String fileName = file.getName();
      if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
      return fileName.substring(fileName.lastIndexOf(".")+1);
      else return "";
  }
  
 
  @Override
  public Long consultar(Long codSecuencia) throws SyncconException {
    log.info("Inicio");
    Long resultado = 0L;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + codSecuencia + "]");
      // consulta la cantidad de Registros por secuencia
      resultado = conciTramaDiariaMapper.consultaRegistros(codSecuencia);
      if (log.isDebugEnabled())
        log.debug("Output []");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
    }
    log.info("Fin");
    return resultado;
  }

  @Override
  public void actualiza(ConciCabTramaDiaria conciCabTramaDiaria, Long codSecuencia) throws SyncconException {
    log.info("Inicio");
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + "codSecuencia: " + codSecuencia + "Cant. Reg: " + conciCabTramaDiaria.getNumRegistros() + "]");
      conciCabTramaDiariaMapper.actualizaRegistros(codSecuencia, conciCabTramaDiaria.getNumRegistros());//
      if (log.isDebugEnabled())
        log.debug("Output []");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_ACTUALIZAR);
    }

    log.info("Fin");

  }

  @Override
  public void actualizaCabTramaDiaria(Long codSecuencia) throws SyncconException {
    log.info("Inicio");
    try {
      if (log.isDebugEnabled())
        log.debug("Input [" + "codSecuencia: " + codSecuencia + "]");
      conciCabTramaDiariaMapper.actualizaTramaDiaria(codSecuencia);//
      if (log.isDebugEnabled())
        log.debug("Output []");
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_ACTUALIZAR);
    }
    log.info("Fin");

  }

@Override
public Boolean insertarControlPaquete(ControlPaquete controlPaquete) throws SyncconException {
	if (log.isInfoEnabled()) {log.info("Inicio");}
	Boolean flag = false;
	
	try {
		log.debug("Input [ 1.-"+ BeanUtils.describe(controlPaquete)+ " ]");
			
		controlPaqueteMapper.insert(controlPaquete);
		
		flag = true;
		log.debug("Output [ 1.- ]");
	} catch (Exception e){
		
		flag = false;

		log.error(e.getMessage(),e);
		throw new SyncconException(ErrorConstants.COD_ERROR_BD_INSERTAR);
		
	} 	
	
	if (log.isInfoEnabled()) {log.info("Fin");}
	
	return flag;
}

@Override
public Boolean actualizarControlPaquete(ControlPaquete controlPaquete) throws SyncconException {
	if (log.isInfoEnabled()) {log.info("Inicio");}
	
	try {
		log.debug("Input [ 1.- " + BeanUtils.describe(controlPaquete)+ " ]");
	
		
		log.debug("Output [ 1.- ]");
	} catch (Exception e){
				log.error(e.getMessage(),e);
				throw new SyncconException(ErrorConstants.COD_ERROR_BD_ACTUALIZAR);
	} 	
	if (log.isInfoEnabled()) {log.info("Fin");}
	
	return null;
}

@Override
public ControlPaquete selectByIdPaquete(Long id) throws SyncconException {
	if (log.isInfoEnabled()) {log.info("Inicio");}
	
	try {
		log.debug("Input [ 1.- "+ id +" ]");
	
		
		log.debug("Output [ 1.- ]");
	} catch (Exception e){
				log.error(e.getMessage(),e);
				throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
	} 	
	if (log.isInfoEnabled()) {log.info("Fin");}
	
	return null;
}

@Override
public List<ControlPaquete> selectAllPaquete() throws SyncconException {
	// TODO Auto-generated method stub
if (log.isInfoEnabled()) {log.info("Inicio");}
	
	try {
		log.debug("Input [ 1.- ]");
	
			
		
		log.debug("Output [ 1.- ]");
	} catch (Exception e){
				log.error(e.getMessage(),e);
				throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
	} 	
	if (log.isInfoEnabled()) {log.info("Fin");}
	
	return null;
}

	@Override
	public synchronized TramaEjecucion ejecucionTrama() throws SyncconException{
	
	TramaEjecucion obj = new TramaEjecucion();
	
	if (log.isInfoEnabled()) {log.info("Inicio");}	
	
	try {
		
		//log.debug("Input [ 1.- ]");	
		
		ControlPaquete controlPaquete = new ControlPaquete();		
		controlPaquete = controlPaqueteMapper.selectByEstadoProceso(Long.valueOf(0));		
		
		log.debug("Input [ respuesta = "+ BeanUtils.describe(controlPaquete)+" ]");
		
		if(controlPaquete.getEstado()==0){obj.setIniciando(true);obj.setEnProceso(true);obj.setFinalizo(false);}
		if(controlPaquete.getEstado()==1){obj.setIniciando(true);obj.setEnProceso(true);obj.setFinalizo(false);}
		if(controlPaquete.getEstado()==2){obj.setIniciando(true);obj.setEnProceso(true);obj.setFinalizo(true);}
		if(controlPaquete.getEstado()==3){obj.setIniciando(true);obj.setEnProceso(true);obj.setFinalizo(true);}
		
		
		if(controlPaquete.getEstado() == 1){
			obj.setFlagEstado(SateliteConstants.FLAG_MENSAJE_PROCESO_02);	
		}
		else if (controlPaquete.getEstado()==2) {
			obj.setFlagEstado(SateliteConstants.FLAG_MENSAJE_PROCESO_01);	
		}
		else if(controlPaquete.getEstado()==4){			
			obj.setFlagEstado(SateliteConstants.FLAG_MENSAJE_PROCESO_04);
		}
		else if(controlPaquete.getEstado()==3 ){
			obj.setFlagEstado(SateliteConstants.FLAG_MENSAJE_PROCESO_03);
		}
		
		obj.setNroRegistros(controlPaquete.getTotalRegistro());
		obj.setNroRegistrosCorrectos(controlPaquete.getRegistroCorrecto());
		obj.setNroRegistrosObservados(controlPaquete.getRegistroObservado());
		obj.setEstadoProceso(controlPaquete.getEstadoProceso());
		obj.setMensaje(controlPaquete.getMensaje());
		
		log.debug(" obj TRAMA MENSUAL "+ BeanUtils.describe(obj)+" ]");

		
		
	} catch (Exception e){
				log.error(e.getMessage(),e);
				throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
	}
	
	if (log.isInfoEnabled()) {log.info("Fin");}

	return obj;
	}


	@Override
	public int selectCorrelativo(String socio) throws SyncconException{
		if (log.isInfoEnabled()) {log.info("Inicio");}
		int respint = -1;
		String resp ="";
		try {
			log.debug("Input [ 1.-" + socio + " ]");
			
			resp =  conciCabTramaDiariaMapper.selectCorrelativo(socio);
			
			if(StringUtils.isNotBlank(resp)){
				respint = Integer.parseInt(resp);

			}else{
				respint = 0;

			}
			
			log.debug("Output [ 1.- "+resp+"]");
		} catch (Exception e){
					log.error(e.getMessage(),e);
					throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
		} 	
		if (log.isInfoEnabled()) {log.info("Fin");}
		return respint;
	}

	@Override 
	public Producto selectProductoByID(int producto) throws SyncconException{
		
		if (log.isInfoEnabled()) {log.info("Inicio");}
		
		Producto obj= null;
		
		try {
			log.debug("Input [ 1.- "+ producto +" ]");
		
			obj = productoMapper.selectProductoByID(producto);
			
			log.debug("Output [ 1.-"+ BeanUtils.describe(obj) +" ]");
		} catch (Exception e){
					log.error(e.getMessage(),e);
					throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
		} 	
		if (log.isInfoEnabled()) {log.info("Fin");}	
		return obj;
	}
	
	@Override
	public String [] numeroRegistrosTXT(File archivoFinal,String sparacion,String codProducto) throws SyncconException {
		
		log.debug("Input [ 1.- "+ sparacion +" ]");
		 String [] columnas= null; 
		 String num="";		  
		 String [] numeroRegistros = new String[2];
		 Integer nroRegistros=0;		  
	     FileReader fr = null;
		 BufferedReader br = null;	
		 
		try {
			 
			 fr = new FileReader (archivoFinal);
			 br = new BufferedReader(fr);
			 Integer sumaColumn =0;
			 
		if(sparacion.trim().equals(SateliteConstants.TRAMA_SEPARADOR_04)){
			
			List<Layout> lista = layoutMapper.listaLayoutByIdproducto(Integer.valueOf(codProducto));
			
			for (Layout layout : lista) {				
				 sumaColumn = sumaColumn +layout.getPosicion();
			}		
			
			String linea;

			while((linea=br.readLine())!=null){
				if(linea.length() == sumaColumn){
					numeroRegistros[0] =  "true";
					nroRegistros = 1;
				}else{
					numeroRegistros[0] =  "false";
					nroRegistros = 1;
				}
			}	
			
		}else{
		
	    	 // Apertura del fichero y creacion de BufferedReader para poder
	         // hacer una lectura comoda (disponer del metodo readLine()).	   
	       
	         // Lectura del fichero	         	                
	         String linea;
	         
	         while((linea=br.readLine())!=null){		        	 
	        	 	num = linea;	        	 
	        	 	columnas = num.split(Pattern.quote(sparacion));     
	        	 	nroRegistros ++;
	        	 	log.info("NUMERO DE COLUMNAS :" + columnas.length );
	         }	         
	         
	         numeroRegistros[0] = String.valueOf(columnas.length);
	         
	      	}
        numeroRegistros[1] = String.valueOf(nroRegistros);

		 
		}
	      catch(Exception e){
	         e.printStackTrace();
	      }finally{
	         try{                    
	            if( null != fr ){   
	               fr.close();     
	            }                  
	         }catch (Exception e2){ 
	            e2.printStackTrace();
	         }
	      }
		
		return numeroRegistros;
	}

	@Override
	public Long selectIdTrama(String flag_estado ) throws SyncconException {
		Long tramaID = null;
		if (log.isInfoEnabled()) {log.info("Inicio");}
		
		try {
			log.debug("Input [ 1.- ]");
			
			tramaID = conciCabTramaDiariaMapper.selectIdTrama(flag_estado);
			
			log.debug("Output [ 1.-"+ tramaID+" ]");
		} catch (Exception e){
					log.error(e.getMessage(),e);
					throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
		} 	
		if (log.isInfoEnabled()) {log.info("Fin");}
		
		return tramaID;
	}

	@Override
	public List<HashMap<String, Object>> exportarObservado(String columns) throws SyncconException {
		
		List<HashMap<String, Object>> obj = new ArrayList<HashMap<String, Object>>();
		
		if(log.isInfoEnabled()){log.info("Inicio");}
			try {
	
			 	 obj=conciCabTramaDiariaMapper.selectTramaTemporal(columns);
										
			} catch (Exception e) {
				log.error(e.getMessage(),e);
				throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
			}
		
		if(log.isInfoEnabled()){log.info("Fin");}
		return obj;
	}
	
	

	@Override
	public List<Layout> listaLayoutByIdproducto(int idproducto)	throws SyncconException {
			
		List<Layout> lista = null;
		if(log.isInfoEnabled()){log.info("Inicio");}

			try {
				
				lista = layoutMapper.listaLayoutByIdproducto(idproducto);
								
			} catch (Exception e) {
				
				log.error(e.getMessage(),e);
				throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
				
			}
			
		if(log.isInfoEnabled()){log.info("Fin");}		
		return lista;
	}

	@Override
	public List<FormatoObservaciones> listaObservaciones()	throws SyncconException {
		
		List<FormatoObservaciones> lista = null;
		
		if(log.isInfoEnabled()){log.info("Inicio");}
			
			try {
				
				lista = conciCabTramaDiariaMapper.selectTramaObservaciones();
				
			} catch (Exception e) {
				
				log.error(e.getMessage(),e);
				throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
				
			}
		
		if(log.isInfoEnabled()){log.info("Inicio");}		
		return lista;
	}

	@Override
	public Boolean executePakage() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
