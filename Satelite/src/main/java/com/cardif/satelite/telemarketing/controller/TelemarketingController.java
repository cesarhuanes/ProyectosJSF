package com.cardif.satelite.telemarketing.controller;

import static com.cardif.satelite.constantes.Constantes.TIP_PARAM_DETALLE;
import static com.cardif.satelite.constantes.ErrorConstants.ERROR_SYNCCON;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR_GENERAL;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.richfaces.model.selection.SimpleSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.acsele.service.ProductService;
import com.cardif.satelite.configuracion.service.ParametroService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.model.TlmkBaseBC;
import com.cardif.satelite.model.TlmkBaseCabecera;
import com.cardif.satelite.model.TlmkBaseIB;
import com.cardif.satelite.model.TlmkBaseRP;
import com.cardif.satelite.model.TlmkBaseSB;
import com.cardif.satelite.model.TlmkLayoutSocioCardif;
import com.cardif.satelite.model.acsele.Product;
import com.cardif.satelite.model.upload.Cliente;
import com.cardif.satelite.segya.service.SocioService;
import com.cardif.satelite.ssis.service.SsisService;
import com.cardif.satelite.telemarketing.bean.AuditCargaTrama;
import com.cardif.satelite.telemarketing.bean.LayoutCardif;
import com.cardif.satelite.telemarketing.service.BaseBCService;
import com.cardif.satelite.telemarketing.service.BaseCabeceraService;
import com.cardif.satelite.telemarketing.service.BaseIBService;
import com.cardif.satelite.telemarketing.service.BaseRPService;
import com.cardif.satelite.telemarketing.service.BaseSBService;
import com.cardif.satelite.telemarketing.service.TlmkLayoutCardifService;
import com.cardif.satelite.upload.service.ClienteService;
import com.cardif.satelite.util.PropertiesUtil;

@Controller("telemarketingController")
@Scope("request")
public class TelemarketingController extends BaseController {

  public static final Logger log = Logger.getLogger(TelemarketingController.class);
  @Autowired
  private ParametroService parametroService;
  @Autowired
  private ClienteService clienteService;
  @Autowired
  private ProductService productoService;
  @Autowired
  private SocioService socioService;
  @Autowired
  private BaseCabeceraService baseCabeceraService;
  @Autowired
  private BaseIBService baseIBService;
  @Autowired
  private BaseBCService baseBCService;
  @Autowired
  private BaseRPService baseRPService;
  @Autowired
  private BaseSBService baseSBService;
  @Autowired
  private TlmkLayoutCardifService tlmkLayoutCardifService;
  @Autowired
  private SsisService ssisService;

  private Product product;
  private Parametro parametro;

  private String codSocio;
  private Integer numMesDescanso;
  private Integer numMesVencimiento;
  private Integer edadMaxima;

  private Long codProducto;
  public String socio;
  public String producto;
  private String codSocioVali;

  private AuditCargaTrama audit;
  private long codSocioBase;

  private Long codProductoArch;
  private long cantidad;
  private List<HashMap<String, Object>> listaProductoArch;

  private List<AuditCargaTrama> listaAudCargaTrama;
  private List<XSSFCell> listaCabecerasSocio;
  private List<TlmkLayoutSocioCardif> listaCabecerasCardif;
  private List<LayoutCardif> listaLayoutCardif;
  private List<String> listaLayout;
  private HashMap<String, Object> mapa;

  private TreeMap<Integer, String> map;
  private LinkedHashMap<String, String> mapExcel;

  private boolean esNuevo;

  private int tramaIndex = 0;

  private SimpleSelection selection;
  private SimpleSelection selectionLayout;
  private List<SelectItem> socioItems;
  private List<SelectItem> productoItems;
  private List<SelectItem> productoArchItems;

  int cantClientesReg;
  int cantClientesErr;
  long cantTotalClientes;
  long cantProductos;

  @Override
  @PostConstruct
  public String inicio() {
    log.info("Inicio");

    String respuesta = null;
    if (!tieneAcceso()) {
      log.debug("No cuenta con los accesos necesarios");
      return "accesoDenegado";
    }
    try {

      listaAudCargaTrama = new ArrayList<AuditCargaTrama>();

      selection = new SimpleSelection();
      esNuevo = false;
      product = new Product();

      /*** socio Base ***************/

      List<Parametro> listaSocio = parametroService.buscar(Constantes.COD_PARAM_SOCIO_TLMK, TIP_PARAM_DETALLE);

      socioItems = new ArrayList<SelectItem>();
      socioItems.add(new SelectItem(null, "- Seleccionar -"));

      for (Parametro p : listaSocio) {
        socioItems.add(new SelectItem(p.getCodValor(), p.getNomValor()));
      }

      /*** lista de productos ***************/

      productoItems = new ArrayList<SelectItem>();
      productoItems.add(new SelectItem(null, "- Seleccionar -"));

      // Inicializa lista, validacion por producto
      listaProductoArch = new ArrayList<HashMap<String, Object>>();
      cantidad = 0;

    } catch (SyncconException ex) {
      log.error(ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }

    log.info("Final");

    return respuesta;
  }

  public String siguienteUno() {
    log.info("Inicio");

    String respuesta = null;

    try {

      String productos = "";

      for (AuditCargaTrama audit : listaAudCargaTrama) {

        if (productos.equals("")) {

          productos = productos + audit.getCodProducto();

        } else {

          productos = productos + "," + audit.getCodProducto();
        }
      }

      /* LEE CABECERAS DE ARCHIVO EXCEL */
      File archivo = null;
      String nombreArchivoInput = "";

      if (codSocio.equals("IB")) {

        nombreArchivoInput = "input\\inputINTERBANK.xlsx";
        archivo = new File(PropertiesUtil.getProperty("IB") + nombreArchivoInput);

      } else if (codSocio.equals("SB")) {

        nombreArchivoInput = "input\\inputSCOTIABANK.xlsx";
        archivo = new File(PropertiesUtil.getProperty("SB") + nombreArchivoInput);

      } else if (codSocio.equals("BC")) {

        nombreArchivoInput = "input\\inputBBVA.xlsx";
        archivo = new File(PropertiesUtil.getProperty("BC") + nombreArchivoInput);

      } else if (codSocio.equals("RP")) {

        nombreArchivoInput = "input\\inputRIPLEY.xlsx";
        archivo = new File(PropertiesUtil.getProperty("RP") + nombreArchivoInput);
      }

      log.info("RUTA ARCHIVO*** " + archivo);

      if (!archivo.exists()) {
        throw new SyncconException(ErrorConstants.COD_ERROR_ARCHIVO_INPUT, FacesMessage.SEVERITY_INFO);
      }

      leerArchivoExcel(archivo);

      // Carga cabeceras de tabla Layout_Cardif
      listaCabecerasCardif = new ArrayList<TlmkLayoutSocioCardif>();
      listaCabecerasCardif = tlmkLayoutCardifService.listarLayoutCardif(codSocio);

      // lista que se muestra en el formulario
      listaLayout = new ArrayList<String>();

      map = new TreeMap<Integer, String>();

      int i = 0;
      for (TlmkLayoutSocioCardif layout : listaCabecerasCardif) {
        map.put(i++, layout.getNomCampo());
      }

      for (Map.Entry<Integer, String> entry : map.entrySet()) {
        listaLayout.add(entry.getValue());
      }

      log.debug("MAP ***" + map.values());
      log.debug("LISTA LAYOUT ***" + listaLayout);

      TlmkBaseCabecera tlmkBaseCabecera = new TlmkBaseCabecera();
      tlmkBaseCabecera.setCodSocio(codSocio);
      tlmkBaseCabecera.setProductos(productos);
      tlmkBaseCabecera.setEstado("PENDIENTE");
      tlmkBaseCabecera.setNumMesDescanso(this.numMesDescanso);
      tlmkBaseCabecera.setNumMesVencimiento(this.numMesVencimiento);
      tlmkBaseCabecera.setMaxEdad(this.edadMaxima);
      tlmkBaseCabecera.setUsuCreacion(SecurityContextHolder.getContext().getAuthentication().getName());

      baseCabeceraService.insertar(tlmkBaseCabecera);

      respuesta = "next";

    } catch (SyncconException ex) {
      log.error(ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }
    log.info("Fin");

    return respuesta;
  }

  // transforma el indice numérico de los mapas en letras, para las cabeceras
  // del excel
  private String transformarIndice(int i) {

    String letra = null;

    switch (i) {
      case 0:
        letra = "A";
        break;
      case 1:
        letra = "B";
        break;
      case 2:
        letra = "C";
        break;
      case 3:
        letra = "D";
        break;
      case 4:
        letra = "E";
        break;
      case 5:
        letra = "F";
        break;
      case 6:
        letra = "G";
        break;
      case 7:
        letra = "H";
        break;
      case 8:
        letra = "I";
        break;
      case 9:
        letra = "J";
        break;
      case 10:
        letra = "K";
        break;
      case 11:
        letra = "L";
        break;
      case 12:
        letra = "M";
        break;
      case 13:
        letra = "N";
        break;
      case 14:
        letra = "O";
        break;
      case 15:
        letra = "P";
        break;
      case 16:
        letra = "Q";
        break;
      case 17:
        letra = "R";
        break;
      case 18:
        letra = "S";
        break;
      case 19:
        letra = "T";
        break;
      case 20:
        letra = "U";
        break;
      case 21:
        letra = "V";
        break;
      case 22:
        letra = "W";
        break;
      case 23:
        letra = "X";
        break;
      case 24:
        letra = "Y";
        break;
      case 25:
        letra = "Z";
        break;
      case 26:
        letra = "AA";
        break;
      case 27:
        letra = "AB";
        break;
      case 28:
        letra = "AC";
        break;
      case 29:
        letra = "AD";
        break;
      case 30:
        letra = "AE";
        break;
      case 31:
        letra = "AF";
        break;
      case 32:
        letra = "AG";
        break;
      case 33:
        letra = "AH";
        break;
      case 34:
        letra = "AI";
        break;
      case 35:
        letra = "AJ";
        break;
      case 36:
        letra = "AK";
        break;
      case 37:
        letra = "AL";
        break;
      case 38:
        letra = "AM";
        break;
      case 39:
        letra = "AN";
        break;
      case 40:
        letra = "AO";
        break;
      default:
        break;
    }
    return letra;
  }

  private void leerArchivoExcel(File archivo) throws Exception {

    FileInputStream fileInputStream = new FileInputStream(archivo);
    XSSFWorkbook workBook = new XSSFWorkbook(fileInputStream);
    XSSFSheet hssfSheet = workBook.getSheetAt(0);
    Iterator rowIterator = hssfSheet.rowIterator();
    List<XSSFCell> cellTempList = null;

    // Recorre solo la primera fila
    for (int i = 0; i < 1; i++) {

      XSSFRow hssfRow = (XSSFRow) rowIterator.next();
      Iterator iterator = hssfRow.cellIterator();
      cellTempList = new ArrayList<XSSFCell>();

      while (iterator.hasNext()) {
        XSSFCell hssfCell = (XSSFCell) iterator.next();
        cellTempList.add(hssfCell);
      }
    }

    // for(int i=0 i < )

    // Carga cabeceras de los archivos excel en lista
    listaCabecerasSocio = new ArrayList();
    listaCabecerasSocio = cellTempList;

  }

  public String siguienteDos() throws SyncconException {
    log.info("Inicio");

    String respuesta = null;
    File archivo = null;

    try {

      mapExcel = new LinkedHashMap<String, String>();
      String letra = null;

      // llena el nuevo mapa
      for (int i = 0; i < listaLayout.size(); i++) {

        letra = transformarIndice(i);
        mapExcel.put(map.get(i), letra);

      }
      log.info("MAPA EXCEL *** " + mapExcel);

      // Creacion de archivos excel para procesar en el job
      codSocioBase = baseCabeceraService.codBaseMax(codSocio);

      // cantidad de clientes procesados
      cantClientesReg = 0;
      cantClientesErr = 0;
      cantTotalClientes = 0;

      if (codSocio.equals("IB")) {

        // archivo = new
        // File("C:\\TEMP\\INTERBANK\\input\\inputINTERBANK.xlsx");
        String nombreArchivoInput = "input\\inputINTERBANK.xlsx";
        archivo = new File(PropertiesUtil.getProperty("IB") + nombreArchivoInput);

        baseIBService.insertarDatosExcel(archivo, codSocioBase, mapExcel);

        // Ejecutar Job
        ssisService.lanzarJob(Constantes.TLMK_JOB_BASE_IB);

        Thread t = new Thread();

        log.info("ANTES**** " + new Date(System.currentTimeMillis()));
        while ("PENDIENTE".equals(baseCabeceraService.buscarEstado(codSocioBase))) {
          t.sleep(10000);
        }
        if (!(baseCabeceraService.buscarEstado(codSocioBase).equals("PROCESADO"))) {
          throw new SyncconException(ErrorConstants.COD_ERROR_JOB, FacesMessage.SEVERITY_INFO);
        }

        log.info("DESPUES**** " + new Date(System.currentTimeMillis()));

        // Cantidad de clientes procesados
        cantClientesReg = baseIBService.cantClientesProcesados(codSocioBase, Constantes.ESTADO_REGISTRADO);
        cantClientesErr = baseIBService.cantClientesProcesados(codSocioBase, Constantes.ESTADO_ERROR);
        cantTotalClientes = cantClientesReg + cantClientesErr;

      } else if (codSocio.equals("BC")) {
        String nombreArchivoInput = "input\\inputBBVA.xlsx";
        archivo = new File(PropertiesUtil.getProperty("BC") + nombreArchivoInput);
        // archivo = new File("C:\\TEMP\\BBVA\\input\\inputBBVA.xlsx");
        baseBCService.insertarDatosExcel(archivo, codSocioBase, mapExcel);

        // Ejecutar Job
        ssisService.lanzarJob(Constantes.TLMK_JOB_BASE_BC);

        Thread t = new Thread();

        log.info("ANTES**** " + new Date(System.currentTimeMillis()));
        while ("PENDIENTE".equals(baseCabeceraService.buscarEstado(codSocioBase))) {
          t.sleep(10000);
        }
        if (!(baseCabeceraService.buscarEstado(codSocioBase).equals("PROCESADO"))) {
          throw new SyncconException(ErrorConstants.COD_ERROR_JOB, FacesMessage.SEVERITY_INFO);
        }
        log.info("DESPUES**** " + new Date(System.currentTimeMillis()));

        // Cantidad de clientes procesados
        cantClientesReg = baseBCService.cantClientesProcesados(codSocioBase, Constantes.ESTADO_REGISTRADO);
        cantClientesErr = baseBCService.cantClientesProcesados(codSocioBase, Constantes.ESTADO_ERROR);
        cantTotalClientes = cantClientesReg + cantClientesErr;

      } else if (codSocio.equals("RP")) {

        String nombreArchivoInput = "input\\inputRIPLEY.xlsx";
        archivo = new File(PropertiesUtil.getProperty("RP") + nombreArchivoInput);
        // archivo = new
        // File("C:\\TEMP\\RIPLEY\\input\\inputRIPLEY.xlsx");
        baseRPService.insertarDatosExcel(archivo, codSocioBase, mapExcel);

        // Ejecutar Job
        ssisService.lanzarJob(Constantes.TLMK_JOB_BASE_RP);

        Thread t = new Thread();

        log.info("ANTES**** " + new Date(System.currentTimeMillis()));
        while ("PENDIENTE".equals(baseCabeceraService.buscarEstado(codSocioBase))) {
          t.sleep(10000);
        }
        if (!(baseCabeceraService.buscarEstado(codSocioBase).equals("PROCESADO"))) {
          throw new SyncconException(ErrorConstants.COD_ERROR_JOB, FacesMessage.SEVERITY_INFO);
        }
        log.info("DESPUES**** " + new Date(System.currentTimeMillis()));

        // Cantidad de clientes procesados
        cantClientesReg = baseRPService.cantClientesProcesados(codSocioBase, Constantes.ESTADO_REGISTRADO);
        cantClientesErr = baseRPService.cantClientesProcesados(codSocioBase, Constantes.ESTADO_ERROR);
        cantTotalClientes = cantClientesReg + cantClientesErr;

      } else if (codSocio.equals("SB")) {

        String nombreArchivoInput = "input\\inputSCOTIABANK.xlsx";
        archivo = new File(PropertiesUtil.getProperty("SB") + nombreArchivoInput);
        // archivo = new
        // File("C:\\TEMP\\SCOTIABANK\\input\\inputSCOTIABANK.xlsx");
        baseSBService.insertarDatosExcel(archivo, codSocioBase, mapExcel);

        // Ejecutar Job
        ssisService.lanzarJob(Constantes.TLMK_JOB_BASE_SB);

        Thread t = new Thread();

        log.info("ANTES**** " + new Date(System.currentTimeMillis()));
        while ("PENDIENTE".equals(baseCabeceraService.buscarEstado(codSocioBase))) {
          t.sleep(10000);
        }
        if (!(baseCabeceraService.buscarEstado(codSocioBase).equals("PROCESADO"))) {
          throw new SyncconException(ErrorConstants.COD_ERROR_JOB, FacesMessage.SEVERITY_INFO);
        }
        log.info("DESPUES**** " + new Date(System.currentTimeMillis()));

        // Cantidad de clientes procesados
        cantClientesReg = baseSBService.cantClientesProcesados(codSocioBase, Constantes.ESTADO_REGISTRADO);
        cantClientesErr = baseSBService.cantClientesProcesados(codSocioBase, Constantes.ESTADO_ERROR);
        cantTotalClientes = cantClientesReg + cantClientesErr;

      }

      TlmkBaseCabecera tlmkBaseCabecera = baseCabeceraService.obtener(codSocioBase);

      tlmkBaseCabecera.setNumRegistros(cantTotalClientes);
      tlmkBaseCabecera.setUsuModificacion(SecurityContextHolder.getContext().getAuthentication().getName());

      baseCabeceraService.actualizar(tlmkBaseCabecera);

      List<Product> lista = null;
      productoArchItems = new ArrayList<SelectItem>();
      productoArchItems.add(new SelectItem("", "- Seleccionar -"));

      lista = productoService.buscar(codSocio);

      for (Product pd : lista) {
        productoArchItems.add(new SelectItem(pd.getProductid().longValue(), pd.getDescription()));
      }

      respuesta = "next";

    } catch (SyncconException ex) {
      log.error(ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }

    log.info("Fin");

    return respuesta;
  }

  public String procesar() {

    log.info("Inicio");

    String respuesta = null;
    String rutaTemplate = null;

    try {
      FacesContext fc = FacesContext.getCurrentInstance();
      ServletContext sc = (ServletContext) fc.getExternalContext().getContext();

      Map<String, Object> beans = new HashMap<String, Object>();
      SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy_hhmm");
      List<TlmkBaseIB> listaIB = null;
      List<Cliente> listaCliente = null;
      List<TlmkBaseSB> listaSB = null;
      List<TlmkBaseBC> listaBC = null;
      List<TlmkBaseRP> listaRP = null;
      XLSTransformer transformer = null;
      String excelOutput = null;

      TlmkBaseCabecera tlmkBaseCabecera = baseCabeceraService.obtener(codSocioBase);

      int inicio = 0;
      if ("IB".equals(codSocio)) {

        if (listaProductoArch.size() == 0) {
          listaIB = baseIBService.buscar(codSocioBase, Constantes.ESTADO_REGISTRADO);

          rutaTemplate = sc.getRealPath(File.separator + "excel\\template_tlmk_IB_reg.xlsx");

          log.debug("Ruta Template: " + rutaTemplate);
          beans.put("base", listaIB);
          log.debug("Tamaño de mi lista de polizas: " + listaIB.size());

          transformer = new XLSTransformer();
          excelOutput = // "C:\\TEMP\\INTERBANK\\output\\TLMK_INTERBANK_"
              // +
              PropertiesUtil.getProperty("IB") + "output\\TLMK_INTERBANK_" + sdf.format(new Date(System.currentTimeMillis())) + "_Registrado.xlsx";
          transformer.transformXLS(rutaTemplate, beans, excelOutput);
        } else {
          for (Map<String, Object> ma : listaProductoArch) {
            // CREACION DEL EXCEL DE SALIDA, ERROR Y OK
            int cant = ((Long) ma.get("CANTIDAD")).intValue();
            String prod = (String) ma.get("DES_PRODUCTO");
            listaIB = baseIBService.buscarParcial(codSocioBase, Constantes.ESTADO_REGISTRADO, inicio, cant);

            rutaTemplate = sc.getRealPath(File.separator + "excel\\template_tlmk_IB_reg.xlsx");

            log.debug("Ruta Template: " + rutaTemplate);
            beans.put("base", listaIB);
            log.debug("Tamaño de mi lista de polizas: " + listaIB.size());

            transformer = new XLSTransformer();
            excelOutput = // "C:\\TEMP\\INTERBANK\\output\\TLMK_INTERBANK_"
                // +
                PropertiesUtil.getProperty("IB") + "output\\TLMK_INTERBANK_" + prod + sdf.format(new Date(System.currentTimeMillis())) + "_Registrado.xlsx";
            transformer.transformXLS(rutaTemplate, beans, excelOutput);

            inicio = inicio + cant;
          }

        }

        listaIB = baseIBService.buscar(codSocioBase, Constantes.ESTADO_ERROR);

        rutaTemplate = sc.getRealPath(File.separator + "excel\\template_tlmk_IB_err.xlsx");

        log.debug("Ruta Template: " + rutaTemplate);
        beans.put("base", listaIB);
        log.debug("Tamaño de mi lista de polizas: " + listaIB.size());

        transformer = new XLSTransformer();
        excelOutput = // "C:\\TEMP\\INTERBANK\\output\\TLMK_INTERBANK_"
            // +
            PropertiesUtil.getProperty("IB") + "output\\TLMK_INTERBANK_" + sdf.format(new Date(System.currentTimeMillis())) + "_Error.xlsx";
        transformer.transformXLS(rutaTemplate, beans, excelOutput);

        // Mover archivo input a caRPeta de procesados
        String nombreArchivoNuevo = "TLMK_INTERBANK_" + sdf.format(new Date(System.currentTimeMillis())) + ".xlsx";

        String nombreArchivoInput = "input\\inputINTERBANK.xlsx";
        File archivo = new File(PropertiesUtil.getProperty("IB") + nombreArchivoInput);

        File archivoNuevo = new File(PropertiesUtil.getProperty("IB") + "\\input\\procesados\\" + nombreArchivoNuevo);
        log.info("NOMBRE DEL ARCHIVO NUEVO ***" + archivoNuevo);
        FileUtils.moveFile(archivo, archivoNuevo); // mover archivo

      } else if ("SB".equals(codSocio)) {

        // CREACION DEL EXCEL DE SALIDA, ERROR Y OK

        if (listaProductoArch.size() == 0) {
          listaCliente = clienteService.buscar(Constantes.SOCIO_ID_SB, tlmkBaseCabecera.getSequenceFileNumber());
          rutaTemplate = sc.getRealPath(File.separator + "excel\\template_tlmk_CARDIF_reg.xlsx");
          log.debug("Ruta Template: " + rutaTemplate);
          beans.put("base", listaCliente);
          transformer = new XLSTransformer();
          excelOutput = // "C:\\TEMP\\SCOTIABANK\\output\\TLMK_SCOTIABANK_"
              // +
              PropertiesUtil.getProperty("SB") + "output\\TLMK_SCOTIABANK_" + sdf.format(new Date(System.currentTimeMillis())) + "_Registrado.xlsx";
          transformer.transformXLS(rutaTemplate, beans, excelOutput);

        } else {
          for (Map<String, Object> ma : listaProductoArch) {
            // CREACION DEL EXCEL DE SALIDA, ERROR Y OK
            int cant = ((Long) ma.get("CANTIDAD")).intValue();
            String prod = (String) ma.get("DES_PRODUCTO");
            listaCliente = clienteService.buscarParcial(Constantes.SOCIO_ID_SB, tlmkBaseCabecera.getSequenceFileNumber(), inicio, cant);
            rutaTemplate = sc.getRealPath(File.separator + "excel\\template_tlmk_CARDIF_reg.xlsx");
            log.debug("Ruta Template: " + rutaTemplate);
            beans.put("base", listaCliente);
            transformer = new XLSTransformer();
            excelOutput = // "C:\\TEMP\\SCOTIABANK\\output\\TLMK_SCOTIABANK_"
                // +
                PropertiesUtil.getProperty("SB") + "output\\TLMK_SCOTIABANK_" + prod + sdf.format(new Date(System.currentTimeMillis())) + "_Registrado.xlsx";
            transformer.transformXLS(rutaTemplate, beans, excelOutput);
            inicio = inicio + cant;
          }
        }

        listaSB = baseSBService.buscar(codSocioBase, Constantes.ESTADO_ERROR);

        rutaTemplate = sc.getRealPath(File.separator + "excel\\template_tlmk_SB_err.xlsx");

        log.debug("Ruta Template: " + rutaTemplate);
        beans.put("base", listaSB);
        log.debug("Tamaño de mi lista de polizas: " + listaSB.size());

        transformer = new XLSTransformer();
        excelOutput = // "C:\\TEMP\\SCOTIABANK\\output\\TLMK_SCOTIABANK_"
            // +
            PropertiesUtil.getProperty("SB") + "output\\TLMK_SCOTIABANK_" + sdf.format(new Date(System.currentTimeMillis())) + "_Error.xlsx";
        transformer.transformXLS(rutaTemplate, beans, excelOutput);

        // Mover archivo input a caRPeta de procesados
        String nombreArchivoInput = "input\\inputSCOTIABANK.xlsx";
        File archivo = new File(PropertiesUtil.getProperty("SB") + nombreArchivoInput);

        String nombreArchivoNuevo = "TLMK_SCOTIABANK_" + sdf.format(new Date(System.currentTimeMillis())) + ".xlsx";

        File archivoNuevo = new File(PropertiesUtil.getProperty("SB") + "\\input\\procesados\\" + nombreArchivoNuevo);
        log.info("NOMBRE DEL ARCHIVO NUEVO ***" + archivoNuevo);

        FileUtils.moveFile(archivo, archivoNuevo);

      } else if ("BC".equals(codSocio)) {

        // CREACION DEL EXCEL DE SALIDA, ERROR Y OK

        if (listaProductoArch.size() == 0) {
          listaCliente = clienteService.buscar(Constantes.SOCIO_ID_BC, tlmkBaseCabecera.getSequenceFileNumber());

          rutaTemplate = sc.getRealPath(File.separator + "excel\\template_tlmk_BC_reg.xlsx");

          log.debug("Ruta Template: " + rutaTemplate);
          beans.put("base", listaCliente);

          transformer = new XLSTransformer();
          excelOutput = // "C:\\TEMP\\SCOTIABANK\\output\\TLMK_SCOTIABANK_"
              // +
              PropertiesUtil.getProperty("BC") + "output\\TLMK_BBVA_" + sdf.format(new Date(System.currentTimeMillis())) + "_Registrado.xlsx";
          transformer.transformXLS(rutaTemplate, beans, excelOutput);

        } else {
          for (Map<String, Object> ma : listaProductoArch) {
            // CREACION DEL EXCEL DE SALIDA, ERROR Y OK
            int cant = ((Long) ma.get("CANTIDAD")).intValue();
            String prod = (String) ma.get("DES_PRODUCTO");
            listaCliente = clienteService.buscarParcial(Constantes.SOCIO_ID_BC, tlmkBaseCabecera.getSequenceFileNumber(), inicio, cant);

            rutaTemplate = sc.getRealPath(File.separator + "excel\\template_tlmk_BC_reg.xlsx");

            log.debug("Ruta Template: " + rutaTemplate);
            beans.put("base", listaCliente);

            transformer = new XLSTransformer();
            excelOutput = // "C:\\TEMP\\SCOTIABANK\\output\\TLMK_SCOTIABANK_"
                // +
                PropertiesUtil.getProperty("BC") + "output\\TLMK_BBVA_" + prod + sdf.format(new Date(System.currentTimeMillis())) + "_Registrado.xlsx";
            transformer.transformXLS(rutaTemplate, beans, excelOutput);

            inicio = inicio + cant;
          }
        }

        listaBC = baseBCService.buscar(codSocioBase, Constantes.ESTADO_ERROR);

        rutaTemplate = sc.getRealPath(File.separator + "excel\\template_tlmk_BC_err.xlsx");

        log.debug("Ruta Template: " + rutaTemplate);
        beans.put("base", listaBC);

        transformer = new XLSTransformer();
        excelOutput = // "C:\\TEMP\\BBVA\\output\\TLMK_BBVA_" +
            PropertiesUtil.getProperty("BC") + "output\\TLMK_BBVA_" + sdf.format(new Date(System.currentTimeMillis())) + "_Error.xlsx";
        transformer.transformXLS(rutaTemplate, beans, excelOutput);

        // Mover archivo input a caRPeta de procesados
        String nombreArchivoInput = "input\\inputBBVA.xlsx";
        File archivo = new File(PropertiesUtil.getProperty("BC") + nombreArchivoInput);

        String nombreArchivoNuevo = "TLMK_BBVA_" + sdf.format(new Date(System.currentTimeMillis())) + ".xlsx";

        File archivoNuevo = new File(PropertiesUtil.getProperty("BC") + "\\input\\procesados\\" + nombreArchivoNuevo);
        log.info("NOMBRE DEL ARCHIVO NUEVO ***" + archivoNuevo);

        FileUtils.moveFile(archivo, archivoNuevo);

      } else if ("RP".equals(codSocio)) {

        if (listaProductoArch.size() == 0) {
          listaCliente = clienteService.buscar(Constantes.SOCIO_ID_RP, tlmkBaseCabecera.getSequenceFileNumber());

          rutaTemplate = sc.getRealPath(File.separator + "excel\\template_tlmk_CARDIF_reg.xlsx");

          log.debug("Ruta Template: " + rutaTemplate);
          beans.put("base", listaCliente);

          transformer = new XLSTransformer();
          excelOutput = // "C:\\TEMP\\SCOTIABANK\\output\\TLMK_SCOTIABANK_"
              // +
              PropertiesUtil.getProperty("RP") + "output\\TLMK_RIPLEY_" + sdf.format(new Date(System.currentTimeMillis())) + "_Registrado.xlsx";
          transformer.transformXLS(rutaTemplate, beans, excelOutput);

        } else {
          for (Map<String, Object> ma : listaProductoArch) {
            // CREACION DEL EXCEL DE SALIDA, ERROR Y OK
            int cant = ((Long) ma.get("CANTIDAD")).intValue();
            String prod = (String) ma.get("DES_PRODUCTO");
            listaCliente = clienteService.buscarParcial(Constantes.SOCIO_ID_RP, tlmkBaseCabecera.getSequenceFileNumber(), inicio, cant);

            rutaTemplate = sc.getRealPath(File.separator + "excel\\template_tlmk_CARDIF_reg.xlsx");

            log.debug("Ruta Template: " + rutaTemplate);
            beans.put("base", listaCliente);

            transformer = new XLSTransformer();
            excelOutput = // "C:\\TEMP\\SCOTIABANK\\output\\TLMK_SCOTIABANK_"
                // +
                PropertiesUtil.getProperty("RP") + "output\\TLMK_RIPLEY_" + prod + sdf.format(new Date(System.currentTimeMillis())) + "_Registrado.xlsx";
            transformer.transformXLS(rutaTemplate, beans, excelOutput);

            inicio = inicio + cant;
          }
        }

        listaRP = baseRPService.buscar(codSocioBase, Constantes.ESTADO_ERROR);

        rutaTemplate = sc.getRealPath(File.separator + "excel\\template_tlmk_RP_err.xlsx");

        log.debug("Ruta Template: " + rutaTemplate);
        beans.put("base", listaRP);

        transformer = new XLSTransformer();
        excelOutput = // "C:\\TEMP\\RIPLEY\\output\\TLMK_RIPLEY_" +
            PropertiesUtil.getProperty("RP") + "output\\TLMK_RIPLEY_" + sdf.format(new Date(System.currentTimeMillis())) + "_Error.xlsx";
        transformer.transformXLS(rutaTemplate, beans, excelOutput);

        // Mover archivo input a caRPeta de procesados
        String nombreArchivoInput = "input\\inputRIPLEY.xlsx";
        File archivo = new File(PropertiesUtil.getProperty("RP") + nombreArchivoInput);

        String nombreArchivoNuevo = "TLMK_RIPLEY_" + sdf.format(new Date(System.currentTimeMillis())) + ".xlsx";

        File archivoNuevo = new File(PropertiesUtil.getProperty("RP") + "\\input\\procesados\\" + nombreArchivoNuevo);
        log.info("NOMBRE DEL ARCHIVO NUEVO ***" + archivoNuevo);

        FileUtils.moveFile(archivo, archivoNuevo);

      }

      // log.info("LISTA LAYOUT MODIFICADA***" + listaLayout);

      respuesta = "fin";
    } catch (SyncconException ex) {
      log.error(ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }

    log.info("Fin");

    return respuesta;
  }

  public String subir() {

    log.info("Inicio");
    String respuesta = null;
    try {
      listaLayout = new ArrayList<String>();
      String temp = null;

      Iterator<Object> iterator = getSelectionLayout().getKeys();

      if (iterator.hasNext()) {
        int key = (Integer) iterator.next();
        if (key != 0) {
          // int key = (Integer) iterator.next();
          temp = map.get(key);

          map.put(key, map.get(key - 1));
          map.put(key - 1, temp);
        }
      }

      // actualiza lista del formulario con los nuevos indices
      for (Map.Entry<Integer, String> entry : map.entrySet()) {
        listaLayout.add(entry.getValue());
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }
    log.info("Fin");

    return respuesta;
  }

  public String bajar() {

    log.info("Inicio");

    listaLayout = new ArrayList<String>();
    String temp = null;

    Iterator<Object> iterator = getSelectionLayout().getKeys();

    if (iterator.hasNext()) {

      int key = (Integer) iterator.next();
      if (key != (map.size() - 1)) {
        temp = map.get(key);

        map.put(key, map.get(key + 1));
        map.put(key + 1, temp);
      }
    }

    // actualiza lista del formulario con los nuevos indices
    for (Map.Entry<Integer, String> entry : map.entrySet()) {
      listaLayout.add(entry.getValue());
    }

    log.info("Fin");

    return null;
  }

  public void buscarProducto(ValueChangeEvent event) {

    log.info("Inicio");

    try {
      if (event.getNewValue() != null) {

        List<Product> lista = null;
        productoItems = new ArrayList<SelectItem>();
        productoItems.add(new SelectItem("", "- Seleccionar -"));
        log.debug("codSocio: " + (String) event.getNewValue());

        lista = productoService.buscar((String) event.getNewValue());

        for (Product pd : lista) {
          productoItems.add(new SelectItem(pd.getProductid().longValue(), pd.getDescription()));
        }
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    log.info("Fin");
  }

  public String agregar() {

    log.info("Inicio");
    String respuesta = null;

    try {
      esNuevo = true;
      socio = null;
      audit = new AuditCargaTrama();

      if (StringUtils.isNotEmpty(codSocioVali) && codProducto != 0) {

        socio = parametroService.obtener(Constantes.COD_PARAM_SOCIO, TIP_PARAM_DETALLE, codSocioVali).getNomValor();

        producto = productoService.obtener(BigDecimal.valueOf(codProducto)).getDescription();

        audit.setCodTrama((long) tramaIndex);
        audit.setCodSocioVali(codSocioVali);
        audit.setCodProducto(codProducto);
        audit.setDesSocio(socio);
        audit.setDesProducto(producto);

        listaAudCargaTrama.add(audit);
        tramaIndex++;
      } else {

      }

    } catch (SyncconException ex) {
      log.error(ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }
    log.info("Fin");

    return respuesta;
  }

  public String quitar() {

    String respuesta = null;
    log.info("Inicio");
    Iterator<Object> iterator = getSelection().getKeys();
    Object key = new Object();

    AuditCargaTrama auditCarga = null;

    try {
      while (iterator.hasNext()) {
        key = iterator.next();

        if (listaAudCargaTrama != null && listaAudCargaTrama.size() > 0) {
          auditCarga = listaAudCargaTrama.get((Integer) key);
        } else {
          throw new SyncconException(ErrorConstants.COD_ERROR_SELECCION, FacesMessage.SEVERITY_INFO);
        }
      }

      if (auditCarga == null) {
        throw new SyncconException(ErrorConstants.COD_ERROR_SELECCION, FacesMessage.SEVERITY_INFO);
      }

      listaAudCargaTrama.remove(Integer.parseInt(key.toString()));

    } catch (SyncconException ex) {
      log.error(ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }

    log.info("Fin");

    return respuesta;
  }

  public String agregarProducto() {

    log.info("Inicio");
    String respuesta = null;
    esNuevo = true;

    try {

      if (cantidad == 0) {
        throw new SyncconException(ErrorConstants.COD_ERROR_CANT_CERO, FacesMessage.SEVERITY_INFO);
      }

      cantProductos = cantProductos + cantidad;

      if (cantProductos <= cantClientesReg) {

        mapa = new HashMap<String, Object>();

        mapa.put("COD_PRODUCTO", codProductoArch);
        producto = productoService.obtener(BigDecimal.valueOf(codProductoArch)).getDescription();
        mapa.put("DES_PRODUCTO", producto);
        mapa.put("CANTIDAD", cantidad);

        listaProductoArch.add(mapa);
      } else {
        cantProductos = cantProductos - cantidad;
        throw new SyncconException(ErrorConstants.COD_ERROR_CANT_PROD, FacesMessage.SEVERITY_INFO);
      }

    } catch (SyncconException ex) {
      log.error(ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }

    return respuesta;
  }

  public String quitarProducto() {

    String respuesta = null;

    log.info("Inicio");
    HashMap<String, Object> mapProducto = null;
    try {

      Iterator<Object> iterator = getSelection().getKeys();
      Object key = new Object();

      while (iterator.hasNext()) {
        if (listaProductoArch != null && listaProductoArch.size() > 0) {
          key = iterator.next();
          mapProducto = listaProductoArch.get((Integer) key);
        } else {
          throw new SyncconException(ErrorConstants.COD_ERROR_SELECCION, FacesMessage.SEVERITY_INFO);
        }

      }
      if (mapProducto == null) {
        throw new SyncconException(ErrorConstants.COD_ERROR_SELECCION, FacesMessage.SEVERITY_INFO);
      }

      log.debug("A ELIMINAR***" + key.toString());
      cantidad = (Long) listaProductoArch.get(Integer.parseInt(key.toString())).get("CANTIDAD");
      listaProductoArch.remove(Integer.parseInt(key.toString()));
      cantProductos = cantProductos - cantidad;
      log.debug("LISTA ACTUALIZADA*** " + listaProductoArch.size());

    } catch (SyncconException ex) {
      log.error(ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }

    log.info("Fin");

    return respuesta;
  }

  public String volverInicio() {

    String respuesta = null;

    log.info("Inicio");

    try {
      listaAudCargaTrama = new ArrayList<AuditCargaTrama>();

      selection = new SimpleSelection();
      esNuevo = false;
      product = new Product();
      codSocio = null;
      codSocioVali = null;
      numMesDescanso = null;
      numMesVencimiento = null;
      edadMaxima = null;

      /*** socio Base ***************/

      List<Parametro> listaSocio = parametroService.buscar(Constantes.COD_PARAM_SOCIO_TLMK, TIP_PARAM_DETALLE);

      socioItems = new ArrayList<SelectItem>();
      socioItems.add(new SelectItem("", "- Seleccionar -"));

      for (Parametro p : listaSocio) {
        socioItems.add(new SelectItem(p.getCodValor(), p.getNomValor()));
      }

      /*** lista de productos ***************/

      productoItems = new ArrayList<SelectItem>();
      productoItems.add(new SelectItem("", "- Seleccionar -"));

      // Inicializa lista, validacion por producto
      listaProductoArch = new ArrayList<HashMap<String, Object>>();
      cantidad = 0;

      respuesta = "volver";

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }

    log.info("Fin");

    return respuesta;
  }

  // Métodos get y set

  public ParametroService getParametroService() {
    return parametroService;
  }

  public void setParametroService(ParametroService parametroService) {
    this.parametroService = parametroService;
  }

  public ProductService getProductoService() {
    return productoService;
  }

  public void setProductoService(ProductService productoService) {
    this.productoService = productoService;
  }

  public SocioService getSocioService() {
    return socioService;
  }

  public void setSocioService(SocioService socioService) {
    this.socioService = socioService;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public Parametro getParametro() {
    return parametro;
  }

  public void setParametro(Parametro parametro) {
    this.parametro = parametro;
  }

  public String getCodSocio() {
    return codSocio;
  }

  public void setCodSocio(String codSocio) {
    this.codSocio = codSocio;
  }

  public Long getCodProducto() {
    return codProducto;
  }

  public void setCodProducto(Long codProducto) {
    this.codProducto = codProducto;
  }

  public List<AuditCargaTrama> getListaAudCargaTrama() {
    return listaAudCargaTrama;
  }

  public void setListaAudCargaTrama(List<AuditCargaTrama> listaAudCargaTrama) {
    this.listaAudCargaTrama = listaAudCargaTrama;
  }

  public boolean isEsNuevo() {
    return esNuevo;
  }

  public void setEsNuevo(boolean esNuevo) {
    this.esNuevo = esNuevo;
  }

  public SimpleSelection getSelection() {
    return selection;
  }

  public void setSelection(SimpleSelection selection) {
    this.selection = selection;
  }

  public List<SelectItem> getSocioItems() {
    return socioItems;
  }

  public void setSocioItems(List<SelectItem> socioItems) {
    this.socioItems = socioItems;
  }

  public List<SelectItem> getProductoItems() {
    return productoItems;
  }

  public void setProductoItems(List<SelectItem> productoItems) {
    this.productoItems = productoItems;
  }

  public static Logger getLog() {
    return log;
  }

  public String getCodSocioVali() {
    return codSocioVali;
  }

  public void setCodSocioVali(String codSocioVali) {
    this.codSocioVali = codSocioVali;
  }

  public int getTramaIndex() {
    return tramaIndex;
  }

  public void setTramaIndex(int tramaIndex) {
    this.tramaIndex = tramaIndex;
  }

  public String getSocio() {
    return socio;
  }

  public void setSocio(String socio) {
    this.socio = socio;
  }

  public List<XSSFCell> getListaCabecerasSocio() {
    return listaCabecerasSocio;
  }

  public void setListaCabecerasSocio(List<XSSFCell> listaCabecerasSocio) {
    this.listaCabecerasSocio = listaCabecerasSocio;
  }

  public List<TlmkLayoutSocioCardif> getListaCabecerasCardif() {
    return listaCabecerasCardif;
  }

  public void setListaCabecerasCardif(List<TlmkLayoutSocioCardif> listaCabecerasCardif) {
    this.listaCabecerasCardif = listaCabecerasCardif;
  }

  public List<LayoutCardif> getListaLayoutCardif() {
    return listaLayoutCardif;
  }

  public void setListaLayoutCardif(List<LayoutCardif> listaLayoutCardif) {
    this.listaLayoutCardif = listaLayoutCardif;
  }

  public List<String> getListaLayout() {
    return listaLayout;
  }

  public void setListaLayout(List<String> listaLayout) {
    this.listaLayout = listaLayout;
  }

  public int getCantClientesReg() {
    return cantClientesReg;
  }

  public void setCantClientesReg(int cantClientesReg) {
    this.cantClientesReg = cantClientesReg;
  }

  public int getCantClientesErr() {
    return cantClientesErr;
  }

  public void setCantClientesErr(int cantClientesErr) {
    this.cantClientesErr = cantClientesErr;
  }

  public long getCantTotalClientes() {
    return cantTotalClientes;
  }

  public void setCantTotalClientes(long cantTotalClientes) {
    this.cantTotalClientes = cantTotalClientes;
  }

  public List<HashMap<String, Object>> getListaProductoArch() {
    return listaProductoArch;
  }

  public void setListaProductoArch(List<HashMap<String, Object>> listaProductoArch) {
    this.listaProductoArch = listaProductoArch;
  }

  public long getCantProductos() {
    return cantProductos;
  }

  public void setCantProductos(long cantProductos) {
    this.cantProductos = cantProductos;
  }

  public TreeMap<Integer, String> getMap() {
    return map;
  }

  public void setMap(TreeMap<Integer, String> map) {
    this.map = map;
  }

  public SimpleSelection getSelectionLayout() {
    return selectionLayout;
  }

  public void setSelectionLayout(SimpleSelection selectionLayout) {
    this.selectionLayout = selectionLayout;
  }

  public Long getCodProductoArch() {
    return codProductoArch;
  }

  public void setCodProductoArch(Long codProductoArch) {
    this.codProductoArch = codProductoArch;
  }

  public long getCantidad() {
    return cantidad;
  }

  public void setCantidad(long cantidad) {
    this.cantidad = cantidad;
  }

  public List<SelectItem> getProductoArchItems() {
    return productoArchItems;
  }

  public void setProductoArchItems(List<SelectItem> productoArchItems) {
    this.productoArchItems = productoArchItems;
  }

  public Integer getNumMesDescanso() {
    return numMesDescanso;
  }

  public void setNumMesDescanso(Integer numMesDescanso) {
    this.numMesDescanso = numMesDescanso;
  }

  public Integer getNumMesVencimiento() {
    return numMesVencimiento;
  }

  public void setNumMesVencimiento(Integer numMesVencimiento) {
    this.numMesVencimiento = numMesVencimiento;
  }

  public Integer getEdadMaxima() {
    return edadMaxima;
  }

  public void setEdadMaxima(Integer edadMaxima) {
    this.edadMaxima = edadMaxima;
  }
}
