package com.cardif.satelite.suscripcion.service.impl;

import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_BUSCAR;
import static com.cardif.satelite.constantes.ErrorConstants.COD_ERROR_BD_ELIMINAR;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.dao.CanalProductoMapper;
import com.cardif.satelite.configuracion.dao.CategoriaClaseMapper;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.CanalProducto;
import com.cardif.satelite.model.CategoriaClase;
import com.cardif.satelite.model.ControlPaquete;
import com.cardif.satelite.model.Socio;
import com.cardif.satelite.model.TramaMaster;
import com.cardif.satelite.model.satelite.MasterPrecioSoat;
import com.cardif.satelite.model.satelite.TramaDiaria;
import com.cardif.satelite.suscripcion.bean.ConsultaMasterPrecio;
import com.cardif.satelite.suscripcion.bean.TramaEjecucion;
import com.cardif.satelite.suscripcion.dao.CanalSubProductoMapper;
import com.cardif.satelite.suscripcion.dao.ConciCabTramaDiariaMapper;
import com.cardif.satelite.suscripcion.dao.ControlPaqueteMapper;
import com.cardif.satelite.suscripcion.dao.MasterPrecioSoatMapper;
import com.cardif.satelite.suscripcion.dao.SocioTramaMapper;
import com.cardif.satelite.suscripcion.dao.TramaMasterMapper;
import com.cardif.satelite.suscripcion.service.CargMasterSoatService;
import com.cardif.satelite.util.SateliteConstants;
import com.cardif.satelite.util.SateliteUtil;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * @author maronlu
 * 
 */
@Service("cargMasterSoatService")
public class CargMasterSoatServiceImpl implements CargMasterSoatService {
	
  public static final Logger log = Logger.getLogger(CargMasterSoatServiceImpl.class);
  MasterPrecioSoat masterPrecioSoat;
  String tempSocio = "";
  Date fechaValidez = null;
  boolean aux;
  @Autowired
  private MasterPrecioSoatMapper masterPrecioSoatMapper;

  @Autowired
  private CanalSubProductoMapper canalSubProductoMapper;

  @Autowired
  private SocioTramaMapper socioTramaMapper;
  
  @Autowired
  private CategoriaClaseMapper categoriaClaseMapper; 
  
  @Autowired
  private CanalProductoMapper canalProductoMapper;
  
  @Autowired TramaMasterMapper tramaMasterMapper;
  
  @Autowired
  private ControlPaqueteMapper controlPaqueteMapper = null;
  
  @Autowired
  private ConciCabTramaDiariaMapper conciCabTramaDiariaMapper  = null;
  
  static int fila = 1;
  static String campo = null;

//  @Override
//  public List<ConsultaMasterPrecio> buscar(String socio, String departamento, String uso, String categoria, String canalVenta) throws SyncconException {
//    log.info("Inicio");
//    List<ConsultaMasterPrecio> lista = null;
//    List<ConsultaMasterPrecio> listaRipley = null;
//    List<ConsultaMasterPrecio> listaFalabella = null;
//    int size = 0;
//    try {
//      if (log.isDebugEnabled())
//        log.debug("Input [1.-" + socio + ", 2.-" + departamento + ", 3.-" + uso + ", 4.-" + categoria + ", 5.- " + canalVenta + "]");
//      socio = StringUtils.isBlank(socio) ? null : socio;
//      departamento = StringUtils.isBlank(departamento) ? null : departamento;
//      uso = StringUtils.isBlank(uso) ? null : uso;
//      categoria = StringUtils.isBlank(categoria) ? null : categoria;
//      canalVenta = StringUtils.isBlank(canalVenta) ? null : canalVenta;
//      if (log.isDebugEnabled())
//        log.debug("Input [1.-" + socio + ", 2.-" + departamento + ", 3.-" + uso + ", 4.-" + categoria + ", 5.-" + canalVenta + "]");
//
//      if (socio == null && categoria == null) {
//        socio = "RP";
//        listaRipley = masterPrecioSoatMapper.selectMasterPorSocio(socio, departamento, uso, canalVenta);
//        socio = "SF";
//        listaFalabella = masterPrecioSoatMapper.selectMasterPorSocio(socio, departamento, uso, canalVenta);
//        if (listaRipley != null) {
//          listaRipley.addAll(listaFalabella);
//        } else if (listaFalabella != null && listaRipley == null) {
//          listaRipley = listaFalabella;
//        }
//        lista = listaRipley;
//
//      } else if (categoria != null) {
//        lista = masterPrecioSoatMapper.selectMasterPorCategoria(socio, departamento, uso, categoria, canalVenta);
//
//      } else if (categoria == null) {
//        lista = masterPrecioSoatMapper.selectMasterPorSocio(socio, departamento, uso, canalVenta);
//      }
//
//      size = (lista != null) ? lista.size() : 0;
//      if (log.isDebugEnabled())
//        log.debug("Output [Regitros:" + size + "]");
//      
//    } catch (Exception e) {
//      log.error(e.getMessage(), e);
//      throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
//    }
//    
//    log.info("Fin");
//    return lista;
//  }

  @Override
  public void guardarDataExcel(File excelFile, String socio, Date fecValidez) throws SyncconException {
    log.info("Inicio");
    try {
      if (log.isDebugEnabled())
        log.debug("excelFile: " + excelFile);
      OPCPackage container;
      container = OPCPackage.open(excelFile.getAbsolutePath());
      ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(container);
      XSSFReader xssfReader = new XSSFReader(container);
      StylesTable styles = xssfReader.getStylesTable();
      InputStream inputStream = xssfReader.getSheet("rId1");
      tempSocio = socio;
      fechaValidez = fecValidez;
      aux = false;
      log.info("fechaValidez: " + fechaValidez);
      procesarCargarMaster(styles, strings, inputStream);

      if (aux == true) {
        throw new SyncconException(ErrorConstants.COD_ERROR_SOCIO_MASTER);
      }
      
      log.info("Auxiliar: " + aux);
      
      inputStream.close();
      
      if (log.isDebugEnabled())
    	  
        log.debug("Ouput: ok");
      
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      if (aux == true) {
        throw new SyncconException(ErrorConstants.COD_ERROR_SOCIO_MASTER);
      } else {
        String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
        masterPrecioSoatMapper.deleteByUsuFecValidez(tempSocio, usuario);
        log.info("No se realizo ninguna insercion");
        throw new SyncconException(ErrorConstants.COD_ERROR_BD_INSERTAR);
      }
    }
    log.info("Fin");
  }

  private void procesarCargarMaster(StylesTable styles, ReadOnlySharedStringsTable strings, InputStream inputStream) throws IOException, SAXException, SyncconException {
    log.info("Inicio");
    InputSource sheetSource = new InputSource(inputStream);
    SAXParserFactory saxPFactory = SAXParserFactory.newInstance();
    try {
      SAXParser saxParser = saxPFactory.newSAXParser();
      XMLReader sheetParser = saxParser.getXMLReader();

      final NumberFormat nf = NumberFormat.getInstance(Locale.JAPAN); // FIXME:
      
      // CAMBIAR
      // A
      // JAPAN
      // EN
      // PROD
      // final DecimalFormat df = new DecimalFormat("0.00");
      
      DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
      simbolo.setDecimalSeparator('.');
      simbolo.setGroupingSeparator(',');
      // final DecimalFormat df = new DecimalFormat("0.00", simbolo);
      final DecimalFormat df = new DecimalFormat("#.##");

      	ContentHandler handler = new XSSFSheetXMLHandler(styles, strings, new SheetContentsHandler() {
        boolean procesar = false;
        boolean procCabecera = false;

        // ESPEZAR DESDE LA FILA
        @Override
        public void startRow(int rowNum) {
          // logger.info("[numero de fila : " + rowNum +
          // " ]");
          try {
            fila++;
            if (rowNum == 1) {
              procesar = true;
              procCabecera = true;
            }
            if (procesar) {
              masterPrecioSoat = new MasterPrecioSoat();
            }
          } catch (Exception e) {
            log.error(e.getMessage(), e);
          }
        }

        // AGREGAR AL FINAL
        @Override
        public void endRow() {
          // if (procesar && masterPrecioSoat.getFecValidez()
          // != null)
          if (procesar && masterPrecioSoat.getUso() != null) {
            String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
            masterPrecioSoat.setCodSocio(tempSocio);
            masterPrecioSoat.setFecValidez(fechaValidez);
            masterPrecioSoat.setFecCreacion(new Date(System.currentTimeMillis()));
            masterPrecioSoat.setUsuCreacion(usuario);

            masterPrecioSoatMapper.insert(masterPrecioSoat);
          }
        }

        @Override
        public void cell(String cellReference, String formattedValue) {
          try {
            if (procCabecera) {
              String celda = cellReference;
              if (celda.equals("A2")) {
                campo = "A2";
                log.info("formattedValue: " + formattedValue + " ---- tempSocio: " + tempSocio);
                if (!formattedValue.equals(tempSocio)) {
                  aux = true;
                } else {
                  procesar = true;
                  procCabecera = false;
                }
              } else {
                procesar = false;
              }
            }

            if (procesar) {
              String fila1 = cellReference.substring(0, 1);

              if (fila1.equals("A")) {
                campo = "A";
                masterPrecioSoat.setCodSocio(formattedValue);
                log.info("XXXXX Socio: " + formattedValue);
              }
              if (fila1.equals("B")) {
                campo = "B";
                masterPrecioSoat.setCodMaster(formattedValue);
              }
              if (fila1.equals("C")) {
                campo = "C";
                masterPrecioSoat.setCanal(formattedValue);
              }
              if (fila1.equals("D")) {
                campo = "D";
                masterPrecioSoat.setMedioPago(formattedValue);
              }
              if (fila1.equals("E")) {
                campo = "E";
                masterPrecioSoat.setUso(formattedValue);
              }
              if (fila1.equals("F")) {
                campo = "F";
                masterPrecioSoat.setCategoria(formattedValue);
              }
              if (fila1.equals("G")) {
                campo = "G";
                masterPrecioSoat.setDepartamento(formattedValue);
              }
              if (fila1.equals("H")) {
                campo = "H";
                masterPrecioSoat.setMarca(formattedValue);
              }
              if (fila1.equals("I")) {
                campo = "I";
                masterPrecioSoat.setModelo(formattedValue);
              }
              if (fila1.equals("J")) {
                campo = "J";
                masterPrecioSoat.setExcluido(Short.parseShort(formattedValue));
              }
              if (fila1.equals("K")) {
                campo = "K";
                masterPrecioSoat.setNroAsientos(Short.parseShort(formattedValue));
              }
              // if(fila1.equals("L")){masterPrecioSoat.setPrimaTecnica((BigDecimal)(nf.parse(formattedValue)));}
              if (fila1.equals("L")) {
                campo = "L";
                masterPrecioSoat.setPrimaTecnica(formatDecimal(formattedValue));
              }
              // if(fila1.equals("M")){masterPrecioSoat.setPrima((BigDecimal)(nf.parse(formattedValue)));}//Prima
              // Publica
              if (fila1.equals("M")) {
                campo = "M";
                masterPrecioSoat.setPrima(formatDecimal(formattedValue));
              }
              if (fila1.equals("N")) {
                campo = "N";
                masterPrecioSoat.setIgv(formatDecimal(formattedValue));
              } // IGV
              if (fila1.equals("O")) {
                campo = "O";
                masterPrecioSoat.setDe(formatDecimal(formattedValue));
              } // Derecho de emisi�n
              if (fila1.equals("P")) {
                campo = "P";
                masterPrecioSoat.setFndComp(formatDecimal(formattedValue));
              } // Fondo de compensaci�n
              if (fila1.equals("Q")) {
                campo = "Q";
                masterPrecioSoat.setComCobranza(formatDecimal(formattedValue));
              } // Comisi�n de cobranza
              if (fila1.equals("R")) {
                campo = "R";
                masterPrecioSoat.setComSocio(formatDecimal(formattedValue));
              } // Comisi�n Socio
              if (fila1.equals("S")) {
                campo = "S";
                masterPrecioSoat.setFdoTlmk(new BigDecimal(df.format(nf.parse(formattedValue))));
              } // Fondo Telemarketing
              if (fila1.equals("T")) {
                campo = "T";
                masterPrecioSoat.setComBroker(new BigDecimal(df.format(nf.parse(formattedValue))));
              } // Comisi�n Broker
            }
          } catch (Exception e) {
            log.error(e.getMessage(), e);
          }
        }

        private String formatString(String formattedValue) {
          int index = formattedValue.indexOf(",");
          if (index != -1) {
            formattedValue = formattedValue.substring(0, index) + "." + formattedValue.substring(index + 1, formattedValue.length());
          }
          return formattedValue;
        }

        private BigDecimal formatDecimal(String formattedValue) {
          String formatedNumber = formatString(formattedValue);
          BigDecimal importe = new BigDecimal(formatedNumber);
          importe = importe.setScale(2, RoundingMode.HALF_UP);
          return importe;
        }

        @Override
        public void headerFooter(String text, boolean isHeader, String tagName) {
        }

      }, false);

      sheetParser.setContentHandler(handler);
      sheetParser.parse(sheetSource);

    } catch (ParserConfigurationException e) {
      throw new RuntimeException("SAX parser appears to be broken - " + e.getMessage());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_INSERTAR);
    }
    log.info("Fin");

  }

  @Override
  public void eliminarUltimaCarga(String codSocio, String usuCreacion) {
    masterPrecioSoatMapper.deleteByUsuFecValidez(codSocio, usuCreacion);
  }

  @Override
  public List<ConsultaMasterPrecio> listar(String socio, String departamento, String uso, String categoria, String canalVenta) throws SyncconException {
   
	List<ConsultaMasterPrecio> listaMasterPrecios = null;  
    
    int size = 0;
    try {
      if (log.isDebugEnabled()){
        log.debug("Input [1.-" + socio + ", 2.-" + departamento + ", 3.-" + uso + ", 4.-" + categoria + ", 5.- " + canalVenta + "]");
      }
      
      socio = StringUtils.isBlank(socio) ? null : socio;
      departamento = StringUtils.isBlank(departamento) ? null : departamento;
      uso = StringUtils.isBlank(uso) ? null : uso;
      categoria = StringUtils.isBlank(categoria) ? null : categoria;
      canalVenta = StringUtils.isBlank(canalVenta) ? null : canalVenta;      
      
      listaMasterPrecios = masterPrecioSoatMapper.consultaDetalleMaster("","","","","");
      
      size = (listaMasterPrecios != null) ? listaMasterPrecios.size() : 0;
      
      if (log.isDebugEnabled()){ log.debug("Output [Regitros:" + size + "]");}

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
    }
    return listaMasterPrecios;
  }

  @Override
  public Date fecValidezMax(String codSocio) throws SyncconException {
    log.info("Inicio");
    Date fecha = null;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [1.-" + codSocio + "]");
      fecha = masterPrecioSoatMapper.selectFechaMaxima(codSocio);

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_BUSCAR);

    }
    log.info("Fin");
    return fecha;
  }

  @Override
  public int deletePorFechaValidez(String codSocio, Date fecValidez) throws SyncconException {
    log.info("Inicio");
    int filasAfectadas = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [1.-" + codSocio + "2.-" + fecValidez + "]");
      filasAfectadas = masterPrecioSoatMapper.deleteByFecValidez(codSocio, fecValidez);
      if (log.isDebugEnabled())
        log.debug("Output []");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_ELIMINAR);
    }
    log.info("Fin");

    return filasAfectadas;
  }

//  @Override
//  public List<ConsultaFechaCargaMaster> buscar(String socio) throws SyncconException {
//    log.info("Inicio");
//
//    List<ConsultaFechaCargaMaster> lista = null;
//    int size = 0;
//    try {
//
//      if (log.isDebugEnabled())
//        log.debug("Input [1.-" + socio + "]");
//      socio = StringUtils.isBlank(socio) ? null : socio;
//      lista = masterPrecioSoatMapper.selectFecCarga(socio);
//      size = (lista != null) ? lista.size() : 0;
//      if (log.isDebugEnabled())
//        log.debug("Output [Regitros:" + size + "]");
//    } catch (Exception e) {
//      log.error(e.getMessage(), e);
//      throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
//    }
//    log.info("Fin");
//    return lista;
//  }

  @Override
  public int deleteMasterPrecios(String socio, Date fecValidez) throws SyncconException {
    log.info("Inicio");
    int filasAfectadas = 0;
    try {
      if (log.isDebugEnabled())
        log.debug("Input [1.-" + socio + " 2.-" + fecValidez + "]");
      filasAfectadas = masterPrecioSoatMapper.deleteMaster(socio, fecValidez);
      if (log.isDebugEnabled())
        log.debug("Output [" + filasAfectadas + "]");

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(COD_ERROR_BD_ELIMINAR);
    }
    log.info("Fin");

    return filasAfectadas;
  }



@Override
public List<Socio> getListaSocios() throws SyncconException {
	if (log.isInfoEnabled()) {log.info("Inicio");}
	List<Socio> lstSocio = new ArrayList<Socio>();
	try {
		log.debug("Input [ 1.- ]");
	
		lstSocio = socioTramaMapper.listaSocios();
		
		log.debug("Output [ 1.- ]");
	} catch (Exception e){
				log.error(e.getMessage(),e);
				throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
	} 	
	if (log.isInfoEnabled()) {log.info("Fin");}	

	return lstSocio;
}

@Override
public List<CategoriaClase> getListCategoriaClase() throws SyncconException{
	
	if (log.isInfoEnabled()) {log.info("Inicio");}
	List<CategoriaClase> lstCategoria = new ArrayList<CategoriaClase>();

	try {
		log.debug("Input [ 1.- ]");
		
		lstCategoria = categoriaClaseMapper.selectAllCategoriaClases();
		
		log.debug("Output [ 1.- ]");
	} catch (Exception e){
				log.error(e.getMessage(),e);
				throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
	} 	
	if (log.isInfoEnabled()) {log.info("Fin");}
	
	return 	lstCategoria;
}

@Override 
public List<CanalProducto> getListCanales() throws SyncconException{
	
	if (log.isInfoEnabled()) {log.info("Inicio");}
	
	List<CanalProducto> lstCanales = new ArrayList<CanalProducto>();

	try {
		log.debug("Input [ 1.- ]");
	
		lstCanales = canalProductoMapper.selectAll();
		
		log.debug("Output [ 1.- ]");
	} catch (Exception e){
				log.error(e.getMessage(),e);
				throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
	} 	
	if (log.isInfoEnabled()) {log.info("Fin");}
	return lstCanales;
}

@Override
public Boolean registrarArchivoMaster(TramaMaster master) throws SyncconException{
	if(log.isInfoEnabled()){log.info("Inicio");}
		Boolean status = false;
		try {
			
			log.debug("Input [1.- "+BeanUtils.describe(master)+" ]");
			
					tramaMasterMapper.insert(master);
			
				status = true;
			log.debug("Output [1.- ]");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			status = false;
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_INSERTAR);
		}
	if(log.isInfoEnabled()){log.info("Fin");}
	
	return status;
}

@Override
public List<TramaDiaria> consultarArchivoBySocio(String socio) throws SyncconException {
	if(log.isInfoEnabled()){log.info("Inicio");}
		List<TramaDiaria> lista = new  ArrayList<TramaDiaria>();
		try {
			
			log.debug("Input [1.- "+  BeanUtils.describe(socio)+"]");
				
				
			
			log.debug("Output [1.- "+ lista.size() +"]");
			
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}		
	if(log.isInfoEnabled()){log.info("Fin");}
	return lista;
}

@Override
public List<MasterPrecioSoat> consultarCargaMaster(String socio,String canalVenta, String departamento, String uso, String categoria) throws SyncconException {
	
		if(log.isInfoEnabled()){log.info("Inicio");}
		List<MasterPrecioSoat> lista = new ArrayList<MasterPrecioSoat>();
		try {
			
				log.debug("Input [1.- "+ socio+" 2 .- "+canalVenta+" 3 .- "+departamento+" 4 .- "+uso+" 5 .- "+categoria+" ]");
			
				log.debug("Output [1.- "+ lista.size()+"]");
				
			} catch (Exception e) {
				log.error(e.getMessage(),e);
				throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
			}
		if(log.isInfoEnabled()){log.info("Fin");}
		
	return lista;
}

@Override
public Boolean eliminarArchivoMaster(String idMaster)	throws SyncconException {
	
		if(log.isInfoEnabled()){log.info("Inicio");}
		Boolean status = false;
		try {
			if(log.isInfoEnabled()){ log.debug("Input [1.-"+idMaster+"]");}
				
			tramaMasterMapper.eliminarMaster(idMaster);
			
				status = true;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			status = false;
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}	
		if(log.isInfoEnabled()){log.info("Fin");}
	return status;
}

@Override
public Boolean selectValidarMaster(String idsocio) throws SyncconException {
	
		if(log.isInfoEnabled()){log.info("Inicio");}		
			Boolean status = false;
			 try {
				
				 if(log.isInfoEnabled()){log.debug("Input [1.- "+ idsocio+"]");}				 
				TramaMaster obj= tramaMasterMapper.selectValidarMaster(idsocio);				
				log.info("resultado CargaMaster" + BeanUtils.describe(obj));				
				if(obj == null)
				{
					status = false;				
				}else{
					status = true;					
				}				
			} catch (Exception e) { 
				log.error(e.getMessage());
				status = false;
				throw new SyncconException( ErrorConstants.COD_ERROR_BD_BUSCAR );
			}			
		if(log.isInfoEnabled()){log.info("Fin");}	
	return status;
}


@Override
public int numeroRegistrosExcel(File archivoFinal) throws SyncconException{
	int count = -1;
	try {
		
		if(log.isInfoEnabled()){log.debug("Nombre archivo :" + archivoFinal.getName() );}
		
		XSSFSheet  xssfReader = null;
		XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream (archivoFinal));
	  	xssfReader = wb.getSheetAt(0);
	  	count = xssfReader.getLastRowNum();
	  	
	} catch (FileNotFoundException e) {
	      log.error(e.getMessage(), e);
	} catch (IOException e) {
	      log.error(e.getMessage(), e);
	} 
	  return count;
}

	@Override
	//public List<TramaMaster> getListaCargaMaster(String socio, Date fechaValidez, Date fechaCarga) throws SyncconException {
	public List<TramaMaster> getListaCargaMaster(String idsocio, Date fechaVigencia, Date fechaCarga) throws SyncconException {
		 log.debug("List<TramaMaster> getListaCargaMaster(String socio, Date fechaVigencia, Date fechaCarga)");
		 

		
		if(log.isInfoEnabled()){log.info("Inicio");}
			List<TramaMaster> lista = new ArrayList<TramaMaster>();
						
			Boolean status = false; 
			try {
				if(log.isDebugEnabled()){log.debug("Input [1.- " + idsocio+" 2.- "+fechaValidez+"] 3.- "+fechaCarga+"");}
				 log.debug("CargMasterSoatServiceImpl ::::::::::::: 1)");
				 
				if(idsocio==null && fechaValidez==null && fechaCarga==null){
					
					log.debug("CargMasterSoatServiceImpl ::::::::::::: 2)");
//					FacesMessage faces = new FacesMessage(FacesMessage.SEVERITY_INFO,"Debe seleccionar como mínimo un filtro para realizar la consulta.", null);
//			  		FacesContext.getCurrentInstance().addMessage(null, faces);				
					
				}else{
					 
				//lista= tramaMasterMapper.selectTramaMasterBySocioFecha(socio, fechaValidez, fechaCarga);anterior
				lista= tramaMasterMapper.selectTramaMasterBySocioFecha(idsocio, fechaVigencia, fechaCarga); 
				log.debug("CargMasterSoatServiceImpl ::::::::::::: 3)");
				log.debug("lista"+lista);
				}
				
				if(log.isDebugEnabled()){log.debug("Output");}
				
			} catch (Exception e) {
				log.error(e.getMessage());
				status = false;
				throw new SyncconException( ErrorConstants.COD_ERROR_BD_BUSCAR );	
			}
				
			if(log.isInfoEnabled()){log.info("Fin");}		
			
		return lista;
	}

	@Override
	public List<ConsultaMasterPrecio> consultaMasterDetalle(String idMaster)throws SyncconException {
		
		if(log.isInfoEnabled()){log.info("Inicio");}
		List<ConsultaMasterPrecio> lista = null;
		
			try {
				if(log.isDebugEnabled()){log.debug("Input [ 1.- "+idMaster+" ]");}
					
					lista = tramaMasterMapper.consultaMasterDetalle(idMaster);
							
				if(log.isDebugEnabled()){log.debug("Output [ 1.- "+idMaster+" ]");}
				
			} catch (Exception e) {
				log.error(e.getMessage());
				throw new SyncconException( ErrorConstants.COD_ERROR_BD_BUSCAR );	
			}
		
		
		if(log.isInfoEnabled()){log.info("Final");}
			
		return lista;
	}

	@Override
	public List<TramaMaster> buscarTramaMasterCabecera(Long codSocio, Date fechaIniVigencia, Date fechaCarga) throws SyncconException
	{
		if (log.isInfoEnabled()) {log.info("Inicio");}
		List<TramaMaster> listaCabTramaMaster = null;
		
		try
		{
			if (log.isDebugEnabled()) {log.debug("Input [1.-" + codSocio + ", 2.-" + fechaIniVigencia + ", 3.-" + fechaCarga +"]");}
			
			fechaIniVigencia = null != fechaIniVigencia ? fechaIniVigencia : SateliteUtil.getInitialCardifDate();
			fechaCarga = null != fechaCarga ? fechaCarga : SateliteUtil.getInitialCardifDate();
			
			listaCabTramaMaster = tramaMasterMapper.selectTramaMasterCabecera(codSocio, fechaIniVigencia, fechaCarga);
			if (log.isInfoEnabled()) {log.info("Output [Registros: " + (null != listaCabTramaMaster ? listaCabTramaMaster.size() : 0) + "]");}
		}
		catch (Exception e)
		{
			log.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			log.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_BD_BUSCAR);
		}
		if (log.isInfoEnabled()) {log.info("Fin");}
		return listaCabTramaMaster;
	} //buscarTramaMasterCabecera
	
	
	@Override
	public synchronized TramaEjecucion ejecucionTrama() throws SyncconException{
	
	TramaEjecucion obj = new TramaEjecucion();
	
	if (log.isInfoEnabled()) {log.info("Inicio");}	
	
	try {
		
		//log.debug("Input [ 1.- ]");	
		
		ControlPaquete controlPaquete = new ControlPaquete();		
		controlPaquete = controlPaqueteMapper.selectByEstadoProcesoMaster(Long.valueOf(0));		
		
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
		
		log.debug(" obj TRAMA MASTER "+ BeanUtils.describe(obj)+" ]");

		
		
	} catch (Exception e){
				log.error(e.getMessage(),e);
				throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
	}
	
	if (log.isInfoEnabled()) {log.info("Fin");}

	return obj;
	}
	
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
	public Long selectIdTrama(String flag_estado,Integer codSocio ) throws SyncconException {
		Long tramaID = null;
		if (log.isInfoEnabled()) {log.info("Inicio");}
		
		try {
			log.debug("Input [ 1.- ]");
			
			tramaID = tramaMasterMapper.selectIdTrama(flag_estado,codSocio);
			
			log.debug("Output [ 1.-"+ tramaID+" ]");
		} catch (Exception e){
					log.error(e.getMessage(),e);
					throw new SyncconException(ErrorConstants.COD_ERROR_BD_OBTENER);
		} 	
		if (log.isInfoEnabled()) {log.info("Fin");}
		
		return tramaID;
	}
}
