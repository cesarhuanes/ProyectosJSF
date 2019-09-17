package com.cardif.satelite.contabilidad.controller;

import static com.cardif.satelite.constantes.Constantes.TIP_PARAM_DETALLE;
import static com.cardif.satelite.constantes.Constantes.COD_PARAM_TIPOS_LIBROS;
import static com.cardif.satelite.constantes.Constantes.COD_PARAM_COMPANIA;
import static com.cardif.satelite.constantes.Constantes.COD_CARDIF_SEGUROS;
import static com.cardif.satelite.constantes.Constantes.COD_LIBRO_DIARIO;
import static com.cardif.satelite.constantes.Constantes.COD_LIBRO_MAYOR;
import static com.cardif.satelite.constantes.Constantes.COD_MONEDA_SOLES;
import static com.cardif.satelite.constantes.Constantes.COD_REG_COMPRAS;
import static com.cardif.satelite.constantes.Constantes.COD_REG_VENTAS;
import static com.cardif.satelite.constantes.Constantes.CONTABILIDAD_JOB_REG_VENTAS;
import static com.cardif.satelite.constantes.Constantes.LIBRO_DIARIO;
import static com.cardif.satelite.constantes.Constantes.LIBRO_MAYOR;
import static com.cardif.satelite.constantes.Constantes.PREFIJO_ARCHIVO;
import static com.cardif.satelite.constantes.Constantes.REG_COMPRAS;
import static com.cardif.satelite.constantes.Constantes.REG_VENTAS;
import static com.cardif.satelite.constantes.Constantes.RUC_CARDIF_SEGUROS;
import static com.cardif.satelite.constantes.Constantes.RUC_CARDIF_SERVICIOS;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.configuracion.service.ParametroService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.contabilidad.service.LibroElectronicoService;
import com.cardif.satelite.model.CabLibro;
import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.ssis.service.SsisService;
import com.cardif.satelite.util.PropertiesUtil;

@Controller("cargarLibrosController")
@Scope("request")
public class CargarLibrosController {

  public static final Logger log = Logger.getLogger(CargarLibrosController.class);

  @Autowired
  private ParametroService parametroService;
  @Autowired
  private LibroElectronicoService libroElectronicoService;
  @Autowired
  private SsisService ssisService;

  private List<SelectItem> companias;
  private List<SelectItem> tiposLibro;

  private String periodo;
  private String codCia;
  private String tipoLibro;
  private boolean upload;

  private ArrayList<String> listaArchivos;

  @PostConstruct
  public String inicio() {
    log.info("Inicio");
    try {

      listaArchivos = new ArrayList<String>();

      List<Parametro> parametro = parametroService.buscar(COD_PARAM_COMPANIA, TIP_PARAM_DETALLE);
      List<Parametro> parametro1 = parametroService.buscar(COD_PARAM_TIPOS_LIBROS, TIP_PARAM_DETALLE);
      tiposLibro = new ArrayList<SelectItem>();
      companias = new ArrayList<SelectItem>();
      SelectItem item1 = new SelectItem("", "- Seleccionar -");
      companias.add(item1);
      SelectItem item2 = new SelectItem("", "- Seleccionar -");
      tiposLibro.add(item2);
      for (Parametro p : parametro) {
        SelectItem item = new SelectItem(p.getCodValor(), p.getNomValor());
        companias.add(item);

      }
      for (Parametro p : parametro1) {
        SelectItem item = new SelectItem(p.getCodValor(), p.getNomValor());
        tiposLibro.add(item);
      }

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    log.info("Fin");
    return null;
  }

  public void clear() {
    log.info("Inicio");
    listaArchivos = new ArrayList<String>();
    log.info("Fin");
  }

  public void limpiar() {
    log.info("Inicio");
    periodo = null;
    codCia = null;
    tipoLibro = null;

    FacesContext context = FacesContext.getCurrentInstance();
    Application application = context.getApplication();
    ViewHandler viewHandler = application.getViewHandler();
    UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
    context.setViewRoot(viewRoot);
    context.renderResponse(); // Optional

    log.info("Fin");
  }

  public void listener(UploadEvent event) {
    try {
      log.debug("Inicio");
      UploadItem item = event.getUploadItem();

      File archivo = item.getFile();
      String nombreArchivo = item.getFileName().substring(item.getFileName().lastIndexOf("\\") + 1);
      File archivoFinal = new File(PropertiesUtil.getProperty("CI") + nombreArchivo);
      FileUtils.copyFile(archivo, archivoFinal);
      archivo.delete();

      listaArchivos.add(nombreArchivo);

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    log.debug("Fin");
  }

  public String procesar() {
    log.info("Inicio");
    String respuesta = null;
    CabLibro cabLibro = null;

    final String rutaCompras = PropertiesUtil.getProperty("CO");
    final String rutaVentas = PropertiesUtil.getProperty("CO");
    try {
      if (CollectionUtils.isEmpty(listaArchivos) && !(tipoLibro.equals(REG_VENTAS) && codCia.equals(COD_CARDIF_SEGUROS))) {
        SyncconException ex = new SyncconException(ErrorConstants.COD_ERROR_ARCHIVO, FacesMessage.SEVERITY_INFO);
        FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        return respuesta;
      }

      String usuario = SecurityContextHolder.getContext().getAuthentication().getName();

      File txtFile = null;
      // CardifSeguros
      if (codCia.equals(COD_CARDIF_SEGUROS)) {
        if (tipoLibro.equals(REG_COMPRAS)) {
          txtFile = new File(rutaCompras + PREFIJO_ARCHIVO + RUC_CARDIF_SEGUROS + periodo + "00" + COD_REG_COMPRAS + "0011" + COD_MONEDA_SOLES + "1" + ".TXT");
        } else if (tipoLibro.equals(REG_VENTAS)) {
          /*
           * txtFile = new File(rutaVentas + Constantes.PREFIJO_ARCHIVO + Constantes.RUC_CARDIF_SEGUROS + periodo + "00" + Constantes.COD_REG_VENTAS +
           * "0011" + Constantes.COD_MONEDA_SOLES + "1" + ".TXT");
           */
        } else if (tipoLibro.equals(LIBRO_DIARIO)) {
          txtFile = new File(rutaVentas + PREFIJO_ARCHIVO + RUC_CARDIF_SEGUROS + periodo + "00" + COD_LIBRO_DIARIO + "0011" + COD_MONEDA_SOLES + "1" + ".TXT");
        } else if (tipoLibro.equals(LIBRO_MAYOR)) {
          txtFile = new File(rutaVentas + PREFIJO_ARCHIVO + RUC_CARDIF_SEGUROS + periodo + "00" + COD_LIBRO_MAYOR + "0011" + COD_MONEDA_SOLES + "1" + ".TXT");
        }
      } else {
        if (tipoLibro.equals(REG_COMPRAS)) {
          txtFile = new File(rutaCompras + PREFIJO_ARCHIVO + RUC_CARDIF_SERVICIOS + periodo + "00" + COD_REG_COMPRAS + "0011" + COD_MONEDA_SOLES + "1" + ".TXT");
        } // Servicios
        else if (tipoLibro.equals(REG_VENTAS)) {
          txtFile = new File(rutaVentas + PREFIJO_ARCHIVO + RUC_CARDIF_SERVICIOS + periodo + "00" + COD_REG_VENTAS + "0011" + COD_MONEDA_SOLES + "1" + ".TXT");
        } else if (tipoLibro.equals(LIBRO_DIARIO)) {
          txtFile = new File(rutaVentas + PREFIJO_ARCHIVO + RUC_CARDIF_SERVICIOS + periodo + "00" + COD_LIBRO_DIARIO + "0011" + COD_MONEDA_SOLES + "1" + ".TXT");
        } else if (tipoLibro.equals(LIBRO_MAYOR)) {
          txtFile = new File(rutaVentas + PREFIJO_ARCHIVO + RUC_CARDIF_SERVICIOS + periodo + "00" + COD_LIBRO_MAYOR + "0011" + COD_MONEDA_SOLES + "1" + ".TXT");
        }
      }

      // BORRAMOS EL ARCHIVO EN CASO EXISTA
      if (txtFile != null && txtFile.exists()) {
        txtFile.delete();
      }

      for (String arc : listaArchivos) {
        cabLibro = new CabLibro();
        cabLibro.setCodCia(codCia);
        cabLibro.setTipLibro(tipoLibro);
        cabLibro.setPeriodo(Integer.parseInt(periodo));
        cabLibro.setEstado("PROCESADO");
        cabLibro.setNomArchivo(arc);
        // FIXME: VER POSIBILIDAD DE CONTAR LOS REGISTROS
        // cabLibro.setNumRegistros(numRegistros);
        cabLibro.setUsuCreacion(usuario);
        libroElectronicoService.insertar(cabLibro);//
        // LECTURA DEL ARCHIVO
        libroElectronicoService.transformarExcel(cabLibro, txtFile, new File(PropertiesUtil.getProperty("CI") + arc));
      }

      // Para el caso del registro de ventas no buscar varios archivos,
      // sino busca en base de datos.
      if (codCia.equals(Constantes.COD_CARDIF_SEGUROS) && tipoLibro.equals(Constantes.REG_VENTAS)) {
        // REGISTRO VENTAS SEGUROS
        cabLibro = new CabLibro();
        cabLibro.setCodCia(codCia);
        cabLibro.setTipLibro(tipoLibro);
        cabLibro.setPeriodo(Integer.parseInt(periodo));
        cabLibro.setEstado("PENDIENTE");
        // FIXME: VER POSIBILIDAD DE CONTAR LOS REGISTROS
        // cabLibro.setNumRegistros(numRegistros);
        cabLibro.setUsuCreacion(usuario);
        libroElectronicoService.insertar(cabLibro);

        ssisService.lanzarJob(CONTABILIDAD_JOB_REG_VENTAS);
        respuesta = "procesoIniciado";
      } else {
        ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletResponse response = (HttpServletResponse) contexto.getResponse();

        FileInputStream in = new FileInputStream(txtFile);

        /* inicio zip */
        File zipFile = new File(txtFile.getAbsolutePath().replace("TXT", "zip"));
        ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(zipFile));
        zip.putNextEntry(new ZipEntry(txtFile.getName()));

        byte[] b = new byte[(int) txtFile.length()];
        int count;
        while ((count = in.read(b)) > 0) {
          zip.write(b, 0, count);

        }
        zip.close();
        in.close();
        /* fin zip */

        // borramos el archivo txt
        if (txtFile.exists()) {
          txtFile.delete();
        }

        FileInputStream zipIn = new FileInputStream(zipFile);
        byte[] loader = new byte[(int) zipFile.length()];
        ServletOutputStream out = response.getOutputStream();
        log.debug("attachment;filename=" + zipFile.getName());
        response.addHeader("Content-Disposition", "attachment;filename=" + zipFile.getName());
        response.setContentType("application/zip");

        while ((zipIn.read(loader)) > 0) {
          out.write(loader, 0, loader.length);
        }
        out.close();
        zipIn.close();
        FacesContext.getCurrentInstance().responseComplete();
      }

      listaArchivos = new ArrayList<String>();

    } catch (SyncconException ex) {
      log.error("ERROR SYNCCON: " + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = "error";
    }
    log.info("Fin");
    return respuesta;
  }

  public void cambioTipoLibro() {
    log.info("Inicio");
    boolean disabled = false;
    log.info("Compañia : " + codCia); // 1
    log.info("Libro    : " + tipoLibro); // 2

    if (Constantes.COD_CARDIF_SEGUROS.equals(codCia) && Constantes.REG_VENTAS.equals(tipoLibro)) {
      disabled = true;
    }

    setUpload(disabled);
    log.info("Fin");
  }

  public List<SelectItem> getCompanias() {
    return companias;
  }

  public void setCompanias(List<SelectItem> companias) {
    this.companias = companias;
  }

  public String getPeriodo() {
    return periodo;
  }

  public void setPeriodo(String periodo) {
    this.periodo = periodo;
  }

  /**
   * @return the codCia
   */
  public String getCodCia() {
    return codCia;
  }

  /**
   * @param codCia
   *          the codCia to set
   */
  public void setCodCia(String codCia) {
    this.codCia = codCia;
  }

  public List<SelectItem> getTiposLibro() {
    return tiposLibro;
  }

  public void setTiposLibro(List<SelectItem> tiposLibro) {
    this.tiposLibro = tiposLibro;
  }

  public String getTipoLibro() {
    return tipoLibro;
  }

  public void setTipoLibro(String tipoLibro) {
    this.tipoLibro = tipoLibro;
  }

  public boolean isUpload() {
    return upload;
  }

  public void setUpload(boolean upload) {
    this.upload = upload;
  }

}
