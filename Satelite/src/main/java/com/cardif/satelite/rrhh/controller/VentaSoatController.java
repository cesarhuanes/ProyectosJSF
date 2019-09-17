package com.cardif.satelite.rrhh.controller;

import static com.cardif.satelite.constantes.Constantes.COD_PARAM_COMPANIA;
import static com.cardif.satelite.constantes.Constantes.TIP_PARAM_DETALLE;
import static com.cardif.satelite.constantes.ErrorConstants.ERROR_SYNCCON;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR_GENERAL;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.richfaces.model.selection.SimpleSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.service.ParametroService;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.MasterPreciosSoat;
import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.model.VentaSoatEmpl;
import com.cardif.satelite.rrhh.service.VentaSoatService;
import com.cardif.satelite.soat.directo.service.DepartamentoServiceDirecto;
import com.cardif.satelite.soat.falabella.service.DistritoService;
import com.cardif.satelite.soat.falabella.service.MarcaVehiculoService;
import com.cardif.satelite.soat.falabella.service.MasterSoatService;
import com.cardif.satelite.soat.falabella.service.ModeloVehiculoService;
import com.cardif.satelite.soat.falabella.service.ProvinciaService;
import com.cardif.satelite.soat.falabella.service.VehiculoPeService;
import com.cardif.satelite.soat.model.Departamento;
import com.cardif.satelite.soat.model.Distrito;
import com.cardif.satelite.soat.model.MarcaVehiculo;
import com.cardif.satelite.soat.model.ModeloVehiculo;
import com.cardif.satelite.soat.model.Provincia;
import com.cardif.satelite.soat.model.VehiculoPe;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

//import com.cardif.satelite.model.Parametro;
/**
 * @author lmaron
 *
 */

@Controller("ventaSoatController")
@Scope("request")
public class VentaSoatController extends BaseController {

  public static final Logger log = Logger.getLogger(VentaSoatController.class);

  @Autowired
  private VentaSoatService ventaSoatService;
  @Autowired
  private ParametroService parametroService;
  @Autowired
  private DepartamentoServiceDirecto departamentoService;
  @Autowired
  private ProvinciaService provinciaService;
  @Autowired
  private MarcaVehiculoService marcaVehiculoService;
  @Autowired
  private ModeloVehiculoService modeloVehiculoService;
  @Autowired
  private DistritoService distritoService;
  @Autowired
  private MasterSoatService masterSoatService;
  @Autowired
  private VehiculoPeService vehiculoService;
  private VentaSoatEmpl ventaSoatEmpl;
  private VehiculoPe vehiculoPe;
  private MarcaVehiculo marcaVehiculo;
  private ModeloVehiculo modeloVehiculo;
  private Departamento departamento;
  private Provincia provincia;
  private Distrito distrito;
  private List<SelectItem> tipDocumentoItems;
  private List<SelectItem> departamentoItems;
  private List<SelectItem> provinciaItems;
  private List<SelectItem> distritoItems;
  private List<SelectItem> marcaItems;
  private List<SelectItem> modeloItems;
  private List<SelectItem> listaRazonSocialItem;
  private SimpleSelection selection;

  private String hora;
  private String codMarca;
  private String codModelo;

  // private

  /*
   * (non-Javadoc)
   * 
   * @see com.cardif.framework.controller.BaseController#inicio()
   */
  @Override
  @PostConstruct
  public String inicio() {

    String respuesta = null;
    if (!tieneAcceso()) {
      log.debug("No cuenta con los accesos necesarios");
      return "accesoDenegado";
    }
    ventaSoatEmpl = new VentaSoatEmpl();
    vehiculoPe = new VehiculoPe();
    departamento = new Departamento();
    distrito = new Distrito();
    provincia = new Provincia();

    try {

      /*
       * List<Departamento> listaDepartamento=departamentoService.buscar();
       */
      List<Provincia> listaProvincia = provinciaService.buscar("15");
      List<MarcaVehiculo> listaMarca = marcaVehiculoService.buscar();

      /* Lista de tipos de documentos */
      /*
       * List<Parametro> listaTiposDocumento = parametroService.buscar(COD_PARAM_TIPOS_DOCUMENTO, TIP_PARAM_DETALLE); for (Parametro p :
       * listaTiposDocumento) { tipDocumentoItems.add(new SelectItem(p.getCodValor(), p.getNomValor())); }
       */
      tipDocumentoItems = new ArrayList<SelectItem>();
      tipDocumentoItems.add(new SelectItem("1", "DNI"));
      tipDocumentoItems.add(new SelectItem("4", "Carnet de Extranjería"));
      tipDocumentoItems.add(new SelectItem("6", "RUC"));

      /*** lista de Razon Social ***************/
      List<Parametro> listaCompanias = parametroService.buscar(COD_PARAM_COMPANIA, TIP_PARAM_DETALLE);
      listaRazonSocialItem = new ArrayList<SelectItem>();
      listaRazonSocialItem.add(new SelectItem("", "- Seleccionar -"));
      for (Parametro p : listaCompanias) {
        listaRazonSocialItem.add(new SelectItem(p.getCodValor(), p.getNomValor()));
      }

      // List<Departamento>
      // listaDepartamento=departamentoService.buscar();
      departamentoItems = new ArrayList<SelectItem>();
      departamentoItems.add(new SelectItem("", "-Seleccione-"));
      // for(Departamento d : listaDepartamento){
      // departamentoItems.add(new
      // SelectItem(d.getCodDepartamento(),d.getNombreDepartamento()));
      departamentoItems.add(new SelectItem("15", "LIMA"));
      // departamentoItems.add(new
      // SelectItem("07","PROV. CONST. DEL CALLAO"));
      departamentoItems.add(new SelectItem("07", "CALLAO"));
      // }
      /*
       * Lista de Departamentos
       */

      /*
       * departamentoItems = new ArrayList<SelectItem>(); //departamentoItems.add(new SelectItem("15","LIMA"));
       * 
       * departamentoItems.add(new SelectItem("","-Seleccione-")); for(Departamento d : listaDepartamento){ //departamentoItems.add(new
       * SelectItem(d.getCodDepartamento(),d.getNombreDepartamento())); departamentoItems.add(new SelectItem("15","LIMA")); departamentoItems.add(new
       * SelectItem("25","PROV. CONST. DEL CALLAO")); }
       */
      provinciaItems = new ArrayList<SelectItem>();
      provinciaItems.add(new SelectItem("", "- Seleccionar -"));
      for (Provincia provincia : listaProvincia) {
        provinciaItems.add(new SelectItem(provincia.getCodProvincia(), provincia.getNombreProvincia()));
      }
      distritoItems = new ArrayList<SelectItem>();
      distritoItems.add(new SelectItem("", "- Seleccionar -"));

      marcaItems = new ArrayList<SelectItem>();
      marcaItems.add(new SelectItem("", "- Seleccionar -"));
      for (MarcaVehiculo marca : listaMarca) {
        marcaItems.add(new SelectItem(String.valueOf(marca.getId().intValue()), marca.getNombreMarcavehiculo()));
      }

      modeloItems = new ArrayList<SelectItem>();
      modeloItems.add(new SelectItem("", "- Seleccionar -"));

      ventaSoatEmpl.setDepartamento("LIMA");
      // ventaSoatEmpl.setTipDocumentoempl("DNI");
      // ventaSoatEmpl.setTipDocumento("DNI");
      ventaSoatEmpl.setDepContrat("LIMA");

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

  public String guardarVenta() {
    log.info("Inicio");
    String respuesta = null;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Date fecActual = new Date(System.currentTimeMillis());
    try {// Invocar Servicios
         // Validaciones
      if (ventaSoatEmpl.getPrima() == null || ventaSoatEmpl.getPrima().intValue() == 0) {
        throw new SyncconException(ErrorConstants.COD_ERROR_NO_PRIMA, FacesMessage.SEVERITY_INFO);
      }
      if (ventaSoatEmpl.getFecIniVig().before(sdf.parse(sdf.format(fecActual)))) {
        throw new SyncconException(ErrorConstants.COD_ERROR_FEC_VIG_ANTERIOR, FacesMessage.SEVERITY_INFO);
      }
      Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(System.currentTimeMillis());
      cal.add(Calendar.DATE, 30);
      if (ventaSoatEmpl.getFecIniVig().after(cal.getTime())) {
        throw new SyncconException(ErrorConstants.COD_ERROR_FEC_VIG_POSTERIOR, FacesMessage.SEVERITY_INFO);
      }

      MarcaVehiculo marca = marcaVehiculoService.obtener(codMarca);
      ModeloVehiculo modelo = modeloVehiculoService.obtener(codModelo);

      Departamento departamento = departamentoService.obtener(ventaSoatEmpl.getDepContrat());

      Provincia provincia = provinciaService.obtener(ventaSoatEmpl.getProvContrat());
      Distrito distrito = distritoService.obtener(ventaSoatEmpl.getDistContrat());
      cal = Calendar.getInstance();
      cal.setTime(ventaSoatEmpl.getFecIniVig());
      cal.roll(Calendar.YEAR, 1);
      ventaSoatEmpl.setFecFinVig(cal.getTime());

      String polizaAux = ventaSoatService.obtenerMaxPoliza();
      Long poliza = Long.valueOf(polizaAux) + 1;

      polizaAux = (poliza == 1) ? "85" + StringUtils.leftPad(poliza.toString(), 8, '0') : StringUtils.leftPad(poliza.toString(), 8, '0');

      ventaSoatEmpl.setMarca(marca.getNombreMarcavehiculo());
      ventaSoatEmpl.setModelo(modelo.getNombreModelovehiculo());
      ventaSoatEmpl.setDepContrat(departamento.getNombreDepartamento());
      ventaSoatEmpl.setProvContrat(provincia.getNombreProvincia());
      ventaSoatEmpl.setDistContrat(distrito.getNombreDistrito());
      ventaSoatEmpl.setUsuCreacion(SecurityContextHolder.getContext().getAuthentication().getName());
      ventaSoatEmpl.setNroPoliza(polizaAux);

      ventaSoatService.insertar(ventaSoatEmpl);

      limpiar();

      respuesta = null;
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

  public String imprimirSolicitud() {
    log.info("Inicio");
    String respuesta = null;

    try {

      ventaSoatEmpl = ventaSoatService.obtener(ventaSoatService.obtenerMaxPoliza());

      /* Generacion de PDF */
      FacesContext fc = FacesContext.getCurrentInstance();
      ServletContext sc = (ServletContext) fc.getExternalContext().getContext();
      String rutaImagen = sc.getRealPath(File.separator + "imagenes\\logoCardif.jpg");

      Document document = new Document();
      document.setPageSize(PageSize.A4);
      document.setMargins(72, 72, 100, 100);
      OutputStream ost = new FileOutputStream(System.getProperty("java.io.tmpdir") + "AUTORIZACION_DESCUENTO_SOAT_" + ventaSoatEmpl.getNroPoliza() + ".pdf");
      PdfWriter writer = PdfWriter.getInstance(document, ost);
      log.debug("se creo el PdfWriter->filesize=" + writer.toString());
      document.open();

      Font fuenteTitulo = null;
      Font fuenteTabla = null;

      fuenteTitulo = new Font(Font.TIMES_ROMAN, 13, Font.BOLD);
      fuenteTabla = new Font(Font.TIMES_ROMAN, 12, Font.NORMAL);

      Image logo = Image.getInstance(rutaImagen);
      logo.setAlignment(Element.ALIGN_LEFT);
      document.add(logo);

      document.add(new Paragraph(" ", fuenteTabla));
      document.add(new Paragraph(" ", fuenteTabla));

      Paragraph pTitulo = new Paragraph("RECIBO DOCUMENTARIO", fuenteTitulo);

      pTitulo.setAlignment(Element.ALIGN_CENTER);
      document.add(pTitulo);

      document.add(new Paragraph(" ", fuenteTabla));
      document.add(new Paragraph(" ", fuenteTabla));
      document.add(new Paragraph(" ", fuenteTabla));

      String nombreCompleto = ventaSoatEmpl.getNomEmpl() + " " + ventaSoatEmpl.getApePatempl() + " " + ventaSoatEmpl.getApeMatempl();
      String numDocumento = ventaSoatEmpl.getNroDocumentoempl();
      String placa = ventaSoatEmpl.getPlaca();
      double prima = Math.round(ventaSoatEmpl.getPrima().doubleValue() * 100.0) / 100.0;
      String sPrima = String.valueOf(prima);

      Paragraph pTexto = new Paragraph("El/La que suscribe la presente, " + nombreCompleto + ", identificado(a) con DNI N° " + numDocumento
          + " confirma tomar conocimiento que el costo del SOAT para el vehiculo de placa " + placa + " tiene un costo de " + sPrima + " que será descontado por planilla.", fuenteTabla);
      pTexto.setAlignment(Element.ALIGN_JUSTIFIED);

      document.add(pTexto);

      document.add(new Paragraph(" ", fuenteTabla));
      document.add(new Paragraph(" ", fuenteTabla));
      document.add(new Paragraph(" ", fuenteTabla));

      Calendar fecha = Calendar.getInstance();
      fecha.setTimeInMillis(System.currentTimeMillis());

      Paragraph pFecha = new Paragraph("Lima, " + fecha.get(Calendar.DATE) + " de " + getMes(fecha.get(Calendar.MONTH)) + " de " + fecha.get(Calendar.YEAR), fuenteTabla);
      pFecha.setAlignment(Element.ALIGN_RIGHT);
      document.add(pFecha);

      document.add(new Paragraph(" ", fuenteTabla));
      document.add(new Paragraph(" ", fuenteTabla));
      document.add(new Paragraph(" ", fuenteTabla));
      document.add(new Paragraph(" ", fuenteTabla));

      Paragraph pLinea = new Paragraph(StringUtils.rightPad("", nombreCompleto.length(), "."), fuenteTabla);
      pLinea.setAlignment(Element.ALIGN_RIGHT);
      Paragraph pFirma = new Paragraph(nombreCompleto, fuenteTabla);
      pFirma.setAlignment(Element.ALIGN_RIGHT);
      Paragraph pDni = new Paragraph(StringUtils.rightPad("DNI N° " + numDocumento, nombreCompleto.length() - 15, " "), fuenteTabla);
      pDni.setAlignment(Element.ALIGN_RIGHT);

      document.add(pLinea);
      document.add(pFirma);
      document.add(pDni);

      document.close();

      File pdfFile = new File(System.getProperty("java.io.tmpdir") + "AUTORIZACION_DESCUENTO_SOAT_" + ventaSoatEmpl.getNroPoliza() + ".pdf");
      ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
      HttpServletResponse response = (HttpServletResponse) contexto.getResponse();
      FileInputStream pdfIn = new FileInputStream(pdfFile);

      log.debug("attachment;filename=" + pdfFile.getPath());

      IOUtils.copy(pdfIn, response.getOutputStream());
      response.addHeader("Content-Disposition", "attachment;filename=" + pdfFile.getName());
      response.setContentType("application/pdf");

      pdfIn.close();

      FacesContext.getCurrentInstance().responseComplete();

      respuesta = null;
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }
    log.info("Fin");
    return respuesta;
  }

  public String buscarPrecio() {
    log.info("Inicio");
    String respuesta = null;

    try {// Invocar Servicios

      MarcaVehiculo mar = marcaVehiculoService.obtener(codMarca);
      ModeloVehiculo mod = modeloVehiculoService.obtener(codModelo);

      MasterPreciosSoat precio = masterSoatService.obtener("CA", ventaSoatEmpl.getDepartamento(), ventaSoatEmpl.getUso(), ventaSoatEmpl.getCategoria(), ventaSoatEmpl.getNroAsientos().intValue(),
          mar.getNombreMarcavehiculo(), mod.getNombreModelovehiculo());

      if (precio != null) {
        if (precio.getExcluido() == 1) {
          throw new SyncconException(ErrorConstants.COD_ERROR_EXCLUIDO, FacesMessage.SEVERITY_INFO);
        }
        ventaSoatEmpl.setPrima(precio.getPrima());
        ventaSoatEmpl.setPrimaTecnica(precio.getPrimaTecnica());

      } else {
        ventaSoatEmpl.setPrima(null);
        ventaSoatEmpl.setPrimaTecnica(null);
        throw new SyncconException(ErrorConstants.COD_ERROR_NO_PRIMA, FacesMessage.SEVERITY_INFO);
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

  public void buscarProvincia(ValueChangeEvent event) {
    log.info("Inicio");
    List<Provincia> lista = null;
    provinciaItems = new ArrayList<SelectItem>();
    provinciaItems.add(new SelectItem("", "- Seleccionar -"));
    try {
      // seleccion de combo anidado en destiempo
      // lista = provinciaService.buscar(ventaSoatEmpl.getDepContrat());
      log.debug("test: " + (String) event.getNewValue());
      lista = provinciaService.buscar((String) event.getNewValue());
      for (Provincia provincia : lista) {
        provinciaItems.add(new SelectItem(provincia.getCodProvincia(), provincia.getNombreProvincia()));
      }
    }

    catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    log.info("Fin");

  }

  public void buscarDistrito(ValueChangeEvent event) {
    log.info("Inicio");
    List<Distrito> lista = null;
    distritoItems = new ArrayList<SelectItem>();
    distritoItems.add(new SelectItem("", "- Seleccionar -"));
    try {
      log.debug("test: " + (String) event.getNewValue());
      lista = distritoService.buscar((String) event.getNewValue());
      for (Distrito dist : lista) {
        distritoItems.add(new SelectItem(dist.getCodDistrito(), dist.getNombreDistrito()));
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    log.info("Fin");
  }

  public void buscarModelo(ValueChangeEvent event) {
    log.info("Inicio");
    List<ModeloVehiculo> lista = null;
    modeloItems = new ArrayList<SelectItem>();
    modeloItems.add(new SelectItem("", "- Seleccionar -"));
    try {

      lista = modeloVehiculoService.buscar((String) event.getNewValue());
      for (ModeloVehiculo modelo : lista) {
        modeloItems.add(new SelectItem(String.valueOf(modelo.getId().intValue()), modelo.getNombreModelovehiculo()));
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    log.info("Fin");
  }

  public String buscarPlaca() {
    log.info("Inicio");
    String result = null;

    try {
      String placa = ventaSoatEmpl.getPlaca();
      VehiculoPe vehiculo = vehiculoService.obtener(placa.toUpperCase());

      if (vehiculo != null) {
        vehiculo.getId();
        // ventaSoatEmpl.setMarca(vehiculo.getMarcaVehiculo().toString());
        // ventaSoatEmpl.setModelo(vehiculo.getModeloVehiculo().toString());
        codMarca = String.valueOf(vehiculo.getMarcaVehiculo().intValue());
        codModelo = String.valueOf(vehiculo.getModeloVehiculo().intValue());
        ventaSoatEmpl.setNroAsientos(vehiculo.getNumeroAsiento().shortValue());
        ventaSoatEmpl.setSerie(vehiculo.getNumeroSerie());
        ventaSoatEmpl.setUso(vehiculo.getUsoVehiculo());
        ventaSoatEmpl.setCategoria(vehiculo.getCategoriaClaseVehiculo());
        ventaSoatEmpl.setAnioFabricacion(vehiculo.getAnioVehiculo().shortValue());

        List<ModeloVehiculo> lista = modeloVehiculoService.buscar(codMarca);

        for (ModeloVehiculo modelo : lista) {
          modeloItems.add(new SelectItem(String.valueOf(modelo.getId().intValue()), modelo.getNombreModelovehiculo()));
        }

      } else {
        limpiar();
        throw new SyncconException(ErrorConstants.COD_ERROR_PLACA, FacesMessage.SEVERITY_INFO);
      }

    } catch (SyncconException ex) {
      log.error(ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      result = MSJ_ERROR;
    }
    log.info("Fin");
    return result;
  }

  public void limpiar() {
    log.info("Inicio");

    vehiculoPe = null;
    marcaVehiculo = null;
    modeloVehiculo = null;
    departamento = null;
    provincia = null;
    distrito = null;
    codMarca = null;
    codModelo = null;
    distritoItems = new ArrayList<SelectItem>();
    distritoItems.add(new SelectItem("", "- Seleccionar -"));
    ventaSoatEmpl = new VentaSoatEmpl();
    ventaSoatEmpl.setDepartamento("LIMA");
    ventaSoatEmpl.setTipDocumentoempl("DNI");
    ventaSoatEmpl.setTipDocumento("DNI");
    ventaSoatEmpl.setDepContrat("LIMA");
    FacesContext context = FacesContext.getCurrentInstance();
    Application application = context.getApplication();
    ViewHandler viewHandler = application.getViewHandler();
    UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
    context.setViewRoot(viewRoot);
    context.renderResponse(); // Optional

    log.info("Fin");
  }

  public String getMes(int month) {
    String text = "";
    switch (month) {
      case 0:
        text = "Enero";
        break;
      case 1:
        text = "Febrero";
        break;
      case 2:
        text = "Marzo";
        break;
      case 3:
        text = "Abril";
        break;
      case 4:
        text = "Mayo";
        break;
      case 5:
        text = "Junio";
        break;
      case 6:
        text = "Julio";
        break;
      case 7:
        text = "Agosto";
        break;
      case 8:
        text = "Setiembre";
        break;
      case 9:
        text = "Octubre";
        break;
      case 10:
        text = "Noviembre";
        break;
      case 11:
        text = "Diciembre";
        break;
    }
    return text;
  }

  public VentaSoatEmpl getVentaSoatEmpl() {
    return ventaSoatEmpl;
  }

  public void setVentaSoatEmpl(VentaSoatEmpl ventaSoatEmpl) {
    this.ventaSoatEmpl = ventaSoatEmpl;
  }

  public List<SelectItem> getTipDocumentoItems() {
    return tipDocumentoItems;
  }

  public void setTipDocumentoItems(List<SelectItem> tipDocumentoItems) {
    this.tipDocumentoItems = tipDocumentoItems;
  }

  public String getHora() {
    return hora;
  }

  public void setHora(String hora) {
    this.hora = hora;
  }

  public SimpleSelection getSelection() {
    return selection;
  }

  public void setSelection(SimpleSelection selection) {
    this.selection = selection;
  }

  public List<SelectItem> getDepartamentoItems() {
    return departamentoItems;
  }

  public void setDepartamentoItems(List<SelectItem> departamentoItems) {
    this.departamentoItems = departamentoItems;
  }

  public List<SelectItem> getProvinciaItems() {
    return provinciaItems;
  }

  public void setProvinciaItems(List<SelectItem> provinciaItems) {
    this.provinciaItems = provinciaItems;
  }

  public List<SelectItem> getDistritoItems() {
    return distritoItems;
  }

  public void setDistritoItems(List<SelectItem> distritoItems) {
    this.distritoItems = distritoItems;
  }

  public List<SelectItem> getMarcaItems() {
    return marcaItems;
  }

  public void setMarcaItems(List<SelectItem> marcaItems) {
    this.marcaItems = marcaItems;
  }

  public List<SelectItem> getModeloItems() {
    return modeloItems;
  }

  public void setModeloItems(List<SelectItem> modeloItems) {
    this.modeloItems = modeloItems;
  }

  public VehiculoPe getVehiculoPe() {
    return vehiculoPe;
  }

  public void setVehiculoPe(VehiculoPe vehiculoPe) {
    this.vehiculoPe = vehiculoPe;
  }

  public MarcaVehiculo getMarcaVehiculo() {
    return marcaVehiculo;
  }

  public void setMarcaVehiculo(MarcaVehiculo marcaVehiculo) {
    this.marcaVehiculo = marcaVehiculo;
  }

  public ModeloVehiculo getModeloVehiculo() {
    return modeloVehiculo;
  }

  public void setModeloVehiculo(ModeloVehiculo modeloVehiculo) {
    this.modeloVehiculo = modeloVehiculo;
  }

  public Departamento getDepartamento() {
    return departamento;
  }

  public void setDepartamento(Departamento departamento) {
    this.departamento = departamento;
  }

  public Provincia getProvincia() {
    return provincia;
  }

  public void setProvincia(Provincia provincia) {
    this.provincia = provincia;
  }

  public Distrito getDistrito() {
    return distrito;
  }

  public void setDistrito(Distrito distrito) {
    this.distrito = distrito;
  }

  public String getCodMarca() {
    return codMarca;
  }

  public void setCodMarca(String codMarca) {
    this.codMarca = codMarca;
  }

  public String getCodModelo() {
    return codModelo;
  }

  public void setCodModelo(String codModelo) {
    this.codModelo = codModelo;
  }

  public List<SelectItem> getListaRazonSocialItem() {
    return listaRazonSocialItem;
  }

  public void setListaRazonSocialItem(List<SelectItem> listaRazonSocialItem) {
    this.listaRazonSocialItem = listaRazonSocialItem;
  }

}
