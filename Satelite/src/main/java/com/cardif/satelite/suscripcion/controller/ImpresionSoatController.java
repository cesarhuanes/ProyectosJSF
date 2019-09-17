package com.cardif.satelite.suscripcion.controller;

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
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.richfaces.model.selection.SimpleSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.service.ParametroService;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.model.VentaSoatEmpl;
import com.cardif.satelite.rrhh.service.VentaSoatService;
import com.cardif.satelite.soat.directo.service.DepartamentoServiceDirecto;
import com.cardif.satelite.soat.falabella.service.DistritoService;
import com.cardif.satelite.soat.falabella.service.ProvinciaService;
import com.cardif.satelite.soat.model.Departamento;
import com.cardif.satelite.soat.model.Distrito;
import com.cardif.satelite.soat.model.Provincia;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

import net.sf.jxls.transformer.XLSTransformer;

@Controller("impresionSoatController")
@Scope("request")
public class ImpresionSoatController {

  public static final Logger log = Logger.getLogger(ImpresionSoatController.class);
  final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
  @Autowired
  private VentaSoatService ventaSoatService;
  @Autowired
  private ParametroService parametroService;
  @Autowired
  private DepartamentoServiceDirecto departamentoService;
  @Autowired
  private ProvinciaService provinciaService;
  @Autowired
  private DistritoService distritoService;
  private List<VentaSoatEmpl> listaPolizas;
  private VentaSoatEmpl ventaSoatEmpl;
  private SimpleSelection selection;
  private Date fechaCompaDesde;
  private Date fechaCompaHasta;
  private String razonSocial;
  private int estado;
  private Provincia provincia;
  private Distrito distrito;
  private List<SelectItem> provinciaItems;
  private List<SelectItem> distritoItems;
  private List<SelectItem> departamentoItems;
  private List<SelectItem> tipDocumentoItems;
  private List<SelectItem> listaRazonSocialItem;

  @PostConstruct
  public String inicio() {
    log.info("Inicio");
    String respuesta = null;
    try {
      listaPolizas = null;
      selection = new SimpleSelection();

      setEstado(1);

      departamentoItems = new ArrayList<SelectItem>();
      departamentoItems.add(new SelectItem("", "-Seleccione-"));
      departamentoItems.add(new SelectItem("15", "LIMA"));
      departamentoItems.add(new SelectItem("07", "CALLAO"));

      /*** lista de Razon Social ***************/
      List<Parametro> listaCompanias = parametroService.buscar(COD_PARAM_COMPANIA, TIP_PARAM_DETALLE);
      listaRazonSocialItem = new ArrayList<SelectItem>();
      listaRazonSocialItem.add(new SelectItem("", "- Seleccionar -"));
      for (Parametro p : listaCompanias) {
        listaRazonSocialItem.add(new SelectItem(p.getCodValor(), p.getNomValor()));
      }

      tipDocumentoItems = new ArrayList<SelectItem>();
      tipDocumentoItems.add(new SelectItem("1", "DNI"));
      tipDocumentoItems.add(new SelectItem("4", "Carnet de Extranjería"));
      tipDocumentoItems.add(new SelectItem("6", "RUC"));
      provinciaItems = new ArrayList<SelectItem>();
      provinciaItems.add(new SelectItem("", "- Seleccionar -"));

      List<Provincia> listaProvincia = provinciaService.buscar("15");
      for (Provincia provincia : listaProvincia) {
        provinciaItems.add(new SelectItem(provincia.getCodProvincia(), provincia.getNombreProvincia()));
      }

      distrito = new Distrito();
      distritoItems = new ArrayList<SelectItem>();
      distritoItems.add(new SelectItem("", "- Seleccionar -"));
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

  public String imprimir() {
    log.info("Inicio");
    String respuesta = null;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss");
    VentaSoatEmpl ventaSoatEmpl = null;
    try {
      Iterator<Object> iterator = getSelection().getKeys();
      if (iterator.hasNext()) {
        int key = (Integer) iterator.next();
        ventaSoatEmpl = listaPolizas.get(key);
      }
      if (ventaSoatEmpl == null) {
        throw new SyncconException(ErrorConstants.COD_ERROR_SELECCION, FacesMessage.SEVERITY_INFO);
      }
      ventaSoatEmpl.setEstado("IMPRESO");
      ventaSoatService.actualizar(ventaSoatEmpl);

      Document document = new Document();
      // document = new Document();
      document.setPageSize(PageSize.A4.rotate());

      OutputStream ost = new FileOutputStream(System.getProperty("java.io.tmpdir") + "POLIZA_SOAT_" + ventaSoatEmpl.getNroPoliza() + ".pdf");

      PdfWriter writer = PdfWriter.getInstance(document, ost);
      document.open();

      String placa = ventaSoatEmpl.getPlaca();
      String categoria = ventaSoatEmpl.getCategoria();
      String anio = ventaSoatEmpl.getAnioFabricacion().toString();
      String marca = ventaSoatEmpl.getMarca();
      String modelo = ventaSoatEmpl.getModelo();
      String nroAsientos = ventaSoatEmpl.getNroAsientos().toString();
      String uso = ventaSoatEmpl.getUso();
      String serie = ventaSoatEmpl.getSerie();

      String poliza = ventaSoatEmpl.getNroPoliza();
      String fecIni = sdf.format(ventaSoatEmpl.getFecIniVig());
      String fecFin = sdf.format(ventaSoatEmpl.getFecFinVig());

      String fecCompra = sdf.format(ventaSoatEmpl.getFecCreacion());
      String horaCompra = sdf2.format(ventaSoatEmpl.getFecCreacion());

      String direccion = ventaSoatEmpl.getDireccion();
      String departamento = ventaSoatEmpl.getDepContrat();
      String provincia = ventaSoatEmpl.getProvContrat();
      String distrito = ventaSoatEmpl.getDistContrat();

      String nombreCompleto = ventaSoatEmpl.getNomContrat() + " " + ventaSoatEmpl.getApePatcontrat() + " " + ventaSoatEmpl.getApeMatcontrat();
      String nroDocum = ventaSoatEmpl.getNroDocumento();
      String telefono = ventaSoatEmpl.getTelefono();
      String prima = String.valueOf(ventaSoatEmpl.getPrima().intValue());

      placeChunck(placa, 270, 553, writer);
      placeChunck(categoria, 360, 553, writer);
      placeChunck(anio, 270, 528, writer);
      placeChunck(marca, 360, 528, writer);
      placeChunck(nroAsientos, 270, 505, writer);
      placeChunck(modelo, 360, 505, writer);
      placeChunck(uso, 270, 483, writer);
      placeChunck(serie, 360, 483, writer);

      placeChunck(poliza, 100, 465, writer);
      placeChunck(fecIni, 30, 439, writer);
      placeChunck(fecIni, 154, 439, writer);
      placeChunck(fecFin, 30, 427, writer);
      placeChunck(fecFin, 154, 427, writer);
      /*
       * placeChunck(poliza, 645, 430, writer); placeChunck(placa, 645, 410, writer); placeChunck(fecIni, 645, 400, writer); placeChunck(fecFin, 645,
       * 390, writer);
       */
      placeChunck(nombreCompleto, 8, 376, writer);
      placeChunck(nroDocum, 8, 352, writer);
      placeChunck(telefono, 147, 352, writer);
      placeChunck(fecCompra, 306, 345, writer);
      placeChunck(horaCompra, 378, 345, writer);
      placeChunck(prima, 451, 345, writer);
      placeChunck(direccion, 8, 327, writer);
      placeChunck(distrito, 8, 306, writer);

      placeChunck(provincia, 80, 306, writer);
      placeChunck(departamento, 170, 306, writer);

      placeChunck(placa, 230, 253, writer);
      placeChunck(categoria, 310, 253, writer);
      placeChunck(anio, 230, 229, writer);
      placeChunck(marca, 310, 229, writer);
      placeChunck(nroAsientos, 230, 205, writer);
      placeChunck(modelo, 310, 205, writer);
      placeChunck(uso, 230, 178, writer);
      placeChunck(serie, 310, 178, writer);

      placeChunck(placa, 670, 253, writer);
      placeChunck(categoria, 750, 253, writer);
      placeChunck(anio, 670, 229, writer);
      placeChunck(marca, 750, 229, writer);
      placeChunck(nroAsientos, 670, 205, writer);
      placeChunck(modelo, 750, 205, writer);
      placeChunck(uso, 670, 178, writer);
      placeChunck(serie, 750, 178, writer);

      placeChunck(poliza, 90, 170, writer);
      placeChunck(fecIni, 30, 144, writer);
      placeChunck(fecIni, 140, 144, writer);
      placeChunck(fecFin, 30, 132, writer);
      placeChunck(fecFin, 140, 132, writer);

      placeChunck(poliza, 515, 170, writer);
      placeChunck(fecIni, 462, 144, writer);
      placeChunck(fecIni, 580, 144, writer);
      placeChunck(fecFin, 462, 132, writer);
      placeChunck(fecFin, 580, 132, writer);

      placeChunck(nombreCompleto, 8, 80, writer);
      placeChunck(nroDocum, 8, 55, writer);
      placeChunck(telefono, 130, 55, writer);
      placeChunck(fecCompra, 225, 35, writer);
      placeChunck(horaCompra, 305, 35, writer);
      placeChunck(prima, 385, 35, writer);
      placeChunck(direccion, 8, 32, writer);
      placeChunck(distrito, 8, 6, writer);

      placeChunck(provincia, 69, 6, writer);
      placeChunck(departamento, 149, 6, writer);

      placeChunck(nombreCompleto, 440, 80, writer);
      placeChunck(nroDocum, 440, 55, writer);
      placeChunck(telefono, 571, 55, writer);
      placeChunck(fecCompra, 661, 35, writer);
      placeChunck(horaCompra, 741, 35, writer);
      placeChunck(prima, 821, 35, writer);
      placeChunck(direccion, 440, 32, writer);
      placeChunck(distrito, 440, 6, writer);

      log.info(" distrito : 4 : " + distrito);
      placeChunck(provincia, 510, 6, writer);
      placeChunck(departamento, 590, 6, writer);

      document.close();

      File pdfFile = new File(System.getProperty("java.io.tmpdir") + "POLIZA_SOAT_" + ventaSoatEmpl.getNroPoliza() + ".pdf");
      ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
      HttpServletResponse response = (HttpServletResponse) contexto.getResponse();
      FileInputStream pdfIn = new FileInputStream(pdfFile);

      log.debug("attachment;filename=" + pdfFile.getName());

      IOUtils.copy(pdfIn, response.getOutputStream());
      response.addHeader("Content-Disposition", "attachment;filename=" + pdfFile.getName());
      response.setContentType("application/");
      pdfIn.close();
      FacesContext.getCurrentInstance().responseComplete();

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

  public String exportar() {
    
	log.info("Inicio");
    
    Map<String, Object> beans = new HashMap<String, Object>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
    String FechaActual = sdf.format(new Date(System.currentTimeMillis()));
    
    try {

      String rutaTemp = System.getProperty("java.io.tmpdir") + "Archivo_Actividades_" + System.currentTimeMillis() + ".xls";
      log.debug("Ruta Archivo: " + rutaTemp);

      FacesContext fc = FacesContext.getCurrentInstance();
      ServletContext sc = (ServletContext) fc.getExternalContext().getContext();
      String rutaTemplate = sc.getRealPath(File.separator + "excel\\template_exportar.xls");
      beans.put("exportar", listaPolizas);
      
      XLSTransformer transformer = new XLSTransformer();
      transformer.transformXLS(rutaTemplate, beans, rutaTemp);

      File archivoResp = new File(rutaTemp);
      FileInputStream in = new FileInputStream(archivoResp);

      HttpServletResponse response = (HttpServletResponse) contexto.getResponse();

      byte[] loader = new byte[(int) archivoResp.length()];

      response.addHeader("Content-Disposition", "attachment;filename=" + "Archivo_Exportado_" + FechaActual + ".xls");
      response.setContentType("application/vnd.ms-excel");

      ServletOutputStream out = response.getOutputStream();

      while ((in.read(loader)) > 0) {
        out.write(loader, 0, loader.length);
      }
      
      in.close();
      out.close();

      FacesContext.getCurrentInstance().responseComplete();
      
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    log.info("Fin");
    return null;
  }

  public static java.sql.Date sumarFechasDias(Date date, int dias) {
    Calendar cal = new GregorianCalendar();
    cal.setTimeInMillis(date.getTime());
    cal.add(Calendar.DATE, dias);
    return new java.sql.Date(cal.getTimeInMillis());
  }

  public String buscar() {
    String respuesta = null;
    log.debug("inicio");
    try {
      listaPolizas = null;

      if ((getFechaCompaDesde() != null && getFechaCompaHasta() == null) || (getFechaCompaDesde() == null && getFechaCompaHasta() != null)) {
        throw new SyncconException(ErrorConstants.COD_ERROR_RANGO_INVALIDO, FacesMessage.SEVERITY_INFO);
      }
      if ((getFechaCompaDesde() != null && getFechaCompaHasta() != null)) {
        if (getFechaCompaDesde().after(fechaCompaHasta)) {
          throw new SyncconException(ErrorConstants.COD_ERROR_FEC_VIG_FIN, FacesMessage.SEVERITY_INFO);
        }
      }

      /*
       * if(getRazonSocial().trim()=="" && getFechaCompaDesde()==null && getFechaCompaHasta()==null ){ listaPolizas = ventaSoatService.listar();
       * setEstado(1); } else if(getRazonSocial().trim()!="" ){ {
       */
      listaPolizas = ventaSoatService.buscar(getFechaCompaDesde(), getFechaCompaHasta(), getRazonSocial());
      setEstado(2);
      /* } */
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

  public String mostrar() {
    String respuesta = null;
    log.info("inicio");
    ventaSoatEmpl = null;
    ventaSoatEmpl = new VentaSoatEmpl();

    try {

      Iterator<Object> iterator = getSelection().getKeys();
      if (iterator.hasNext()) {
        int key = (Integer) iterator.next();
        String pk = listaPolizas.get(key).getNroPoliza();
        ventaSoatEmpl = ventaSoatService.obtener(pk);
        Departamento departamento = departamentoService.obtenerPorNom(ventaSoatEmpl.getDepContrat());
        ventaSoatEmpl.setDepContrat(departamento.getCodDepartamento());
        Provincia provincia = provinciaService.obtenerPorNom(ventaSoatEmpl.getProvContrat(), departamento.getCodDepartamento());
        ventaSoatEmpl.setProvContrat(provincia.getCodProvincia());
        /*** lista distritos ***************/

        List<Distrito> listaDistrito = distritoService.buscar(ventaSoatEmpl.getProvContrat());
        distritoItems = new ArrayList<SelectItem>();
        distritoItems.add(new SelectItem("", "- Seleccionar -"));
        for (Distrito d : listaDistrito) {
          distritoItems.add(new SelectItem(d.getCodDistrito(), d.getNombreDistrito()));
        }

        provinciaItems = new ArrayList<SelectItem>();
        provinciaItems.add(new SelectItem("", "- Seleccionar -"));
        List<Provincia> listaProvincia = provinciaService.buscar(ventaSoatEmpl.getDepContrat());
        for (Provincia prov : listaProvincia) {
          provinciaItems.add(new SelectItem(prov.getCodProvincia(), prov.getNombreProvincia()));
        }
        Distrito distrito = distritoService.obtenerPorNom(ventaSoatEmpl.getDistContrat(), provincia.getCodProvincia());
        ventaSoatEmpl.setDistContrat(distrito.getCodDistrito());

      } else {
        throw new SyncconException(ErrorConstants.COD_ERROR_FILA_NO_SELECCIONADA, FacesMessage.SEVERITY_INFO);
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

  public String modificar() {
    String respuesta = null;
    log.info("inicio");
    try {

      departamentoItems = new ArrayList<SelectItem>();
      departamentoItems.add(new SelectItem("", "-Seleccione-"));
      departamentoItems.add(new SelectItem("15", "LIMA"));
      departamentoItems.add(new SelectItem("07", "CALLAO"));
      
      ventaSoatEmpl.setUsuModificacion(SecurityContextHolder.getContext().getAuthentication().getName());
      Departamento departamento = departamentoService.obtener(ventaSoatEmpl.getDepContrat());
      Provincia provincia = provinciaService.obtener(ventaSoatEmpl.getProvContrat());
      Distrito distrito = distritoService.obtener(ventaSoatEmpl.getDistContrat());
      ventaSoatEmpl.setDepContrat(departamento.getNombreDepartamento());
      ventaSoatEmpl.setProvContrat(provincia.getNombreProvincia());
      ventaSoatEmpl.setDistContrat(distrito.getNombreDistrito());
      ventaSoatService.actualizar(ventaSoatEmpl);

      listaPolizas = null;
      if (getEstado() == 1) {
        listaPolizas = ventaSoatService.listar();
      } else {
        listaPolizas = ventaSoatService.buscar(getFechaCompaDesde(), getFechaCompaHasta(), getRazonSocial());
      }

      throw new SyncconException(ErrorConstants.DATOS_GUARDADOS_CORRECTAMENTE, FacesMessage.SEVERITY_INFO);
    } catch (SyncconException ex) {
      log.error(ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }
    return respuesta;
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

  public void buscarProvincia(ValueChangeEvent event) {
    log.info("Inicio");
    List<Provincia> lista = null;
    provinciaItems = new ArrayList<SelectItem>();
    provinciaItems.add(new SelectItem("", "- Seleccionar -"));
    try {
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

  private void placeChunck(String text, int x, int y, PdfWriter writer) throws Exception {
    BaseFont base = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.WINANSI, true);

    PdfContentByte cb = writer.getDirectContent();

    cb.saveState();
    cb.beginText();
    cb.moveText(x, y);
    cb.setFontAndSize(base, 7);
    cb.showText(text);
    cb.endText();
    cb.restoreState();
  }

  public List<VentaSoatEmpl> getListaPolizas() {
    return listaPolizas;
  }

  public void setListaPolizas(List<VentaSoatEmpl> listaPolizas) {
    this.listaPolizas = listaPolizas;
  }

  public SimpleSelection getSelection() {
    return selection;
  }

  public void setSelection(SimpleSelection selection) {
    this.selection = selection;
  }

  public Date getFechaCompaDesde() {
    return fechaCompaDesde;
  }

  public void setFechaCompaDesde(Date fechaCompaDesde) {
    this.fechaCompaDesde = fechaCompaDesde;
  }

  public Date getFechaCompaHasta() {
    return fechaCompaHasta;
  }

  public void setFechaCompaHasta(Date fechaCompaHasta) {
    this.fechaCompaHasta = fechaCompaHasta;
  }

  public String getRazonSocial() {
    return razonSocial;
  }

  public void setRazonSocial(String razonSocial) {
    this.razonSocial = razonSocial;
  }

  public List<SelectItem> getListaRazonSocialItem() {
    return listaRazonSocialItem;
  }

  public void setListaRazonSocialItem(List<SelectItem> listaRazonSocialItem) {
    this.listaRazonSocialItem = listaRazonSocialItem;
  }

  public int getEstado() {
    return estado;
  }

  public void setEstado(int estado) {
    this.estado = estado;
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

  public List<SelectItem> getProvinciaItems() {
    return provinciaItems;
  }

  public List<SelectItem> getDistritoItems() {
    return distritoItems;
  }

  public void setProvinciaItems(List<SelectItem> provinciaItems) {
    this.provinciaItems = provinciaItems;
  }

  public void setDistritoItems(List<SelectItem> distritoItems) {
    this.distritoItems = distritoItems;
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

  public List<SelectItem> getDepartamentoItems() {
    return departamentoItems;
  }

  public void setDepartamentoItems(List<SelectItem> departamentoItems) {
    this.departamentoItems = departamentoItems;
  }

}
