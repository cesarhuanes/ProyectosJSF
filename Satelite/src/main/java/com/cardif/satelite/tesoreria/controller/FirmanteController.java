package com.cardif.satelite.tesoreria.controller;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;
import org.richfaces.model.selection.SimpleSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.satelite.model.FirmantePar;
import com.cardif.satelite.tesoreria.model.Firmante;
import com.cardif.satelite.tesoreria.service.FirmanteService;
import com.cardif.satelite.util.SateliteConstants;
import com.cardif.satelite.util.SateliteUtil;

@Controller
@Scope("request")
public class FirmanteController extends BaseController implements Serializable {

  private static final long serialVersionUID = 1L;

  public static final Logger LOGGER = Logger.getLogger(FirmanteController.class);

  private String usuario;
  private List<Firmante> firmantesActivos;
  private List<Firmante> firmantesTemporales;
  private SimpleSelection selectionActivo;
  private SimpleSelection selectionTemp;
  private Firmante firmanteActual;

  private Firmante firmanteActivo;
  private Firmante firmanteTemp;

  private Firmante firmanteNuevo;

  private List<FirmantePar> pares;

  @Autowired
  private FirmanteService firmanteService;

  private String alto;

  private int ancho;

  @Override
  @PostConstruct
  public String inicio() {
    if (tieneAcceso()) {
      LOGGER.info("Inicio");
      firmantesActivos = firmanteService.buscarFirmantesActivos(null);
      firmantesTemporales = firmanteService.buscarFirmantesTemp();
      LOGGER.info("Fin");
    }
    return null;
  }

  public void pintar(OutputStream out, Object objFirma) throws IOException {

    BufferedImage bufferedImage = null;
    try {
      if (null == objFirma) {
        LOGGER.info("Firma nula");
        bufferedImage = new BufferedImage(210, 90, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setComposite(AlphaComposite.Clear);

        g2d.dispose();

      } else {
        LOGGER.info("Firma no nula");
        byte[] firma = (byte[]) objFirma;
        InputStream in = new ByteArrayInputStream(firma);
        bufferedImage = ImageIO.read(in);
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        LOGGER.info("Ancho:" + width);
        LOGGER.info("Alto:" + height);

        Graphics2D graphics = bufferedImage.createGraphics();

        // set font for the watermark text
        graphics.setFont(new Font("Arial", Font.BOLD, 25));
        graphics.setColor(new Color(0, 0.4f, 0, 0.5f));
        // unicode characters for (c) is \u00a9
        String watermark = "SIN VALOR ";

        // add the watermark text
        graphics.drawString(watermark, 40, height / 2);
        graphics.dispose();

      }

      ImageIO.write(bufferedImage, "png", out);

    } catch (IOException e) {
      LOGGER.error(e);
    }
  }

  public String buscarFirmantes() {
    LOGGER.info("Inicio");
    firmantesActivos = firmanteService.buscarFirmantesActivos(usuario);
    LOGGER.info("Fin");
    return null;

  }

  public String limpiarFirma() {
    LOGGER.info("Inicio");
    firmanteNuevo.setFirma(null);
    firmanteNuevo.setFirmaDesprotegida(null);
    LOGGER.info("Fin");
    return null;
  }

  public String aprobar() {
    LOGGER.info("Inicio");
    String mensaje = firmanteService.aprobarFirmanteTemporal(firmanteTemp);
    if (null != mensaje) {
      SateliteUtil.mostrarMensaje(mensaje);
    }
    firmantesTemporales = firmanteService.buscarFirmantesTemp();
    firmantesActivos = firmanteService.buscarFirmantesActivos(null);
    limpiar();
    LOGGER.info("Fin");

    return null;
  }

  private void limpiar() {
    selectionTemp = null;
    selectionActivo = null;
    firmanteTemp = null;
    firmanteNuevo = null;
  }

  public String rechazar() {
    LOGGER.info("Inicio");
    String mensaje = firmanteService.rechazarFirmanteTemporal(firmanteTemp);
    if (null != mensaje) {
      SateliteUtil.mostrarMensaje(mensaje);
    }
    firmantesTemporales = firmanteService.buscarFirmantesTemp();
    firmantesActivos = firmanteService.buscarFirmantesActivos(null);
    limpiar();
    LOGGER.info("Fin");
    return null;
  }

  public void seleccionarFirmanteActivo() {
    LOGGER.info("Inicio");
    firmanteActivo = (Firmante) SateliteUtil.verSeleccionado(selectionActivo, firmantesActivos);
    LOGGER.info(firmanteActivo);
    LOGGER.info("Fin");
  }

  public void seleccionarFirmanteTemporal() {
    LOGGER.info("Inicio");
    firmanteTemp = (Firmante) SateliteUtil.verSeleccionado(selectionTemp, firmantesTemporales);
    LOGGER.info(firmanteTemp);
    LOGGER.info("Fin");
  }

  public void eliminarFirmanteTemporal(ActionEvent event) {
    LOGGER.info("Inicio");
    String mensaje = firmanteService.eliminarFirmanteTemporal(firmanteTemp);
    firmantesTemporales = firmanteService.buscarFirmantesTemp();
    selectionTemp = null;
    SateliteUtil.mostrarMensaje(mensaje);
    LOGGER.info("Fin");

  }

  public String insertarFirmanteTemporal() {
    if (null != firmanteNuevo.getFirma()) {
      LOGGER.info("Inicio");
      String mensaje = firmanteService.insertarFirmanteTemporal(firmanteNuevo);
      firmantesTemporales = firmanteService.buscarFirmantesTemp();
      selectionTemp = null;
      SateliteUtil.mostrarMensaje(mensaje);
      LOGGER.info("Fin");
    } else {
      LOGGER.info("Firma null");
    }
    return null;

  }

  private void buscarPares(Firmante firmante) {
    LOGGER.info("Inicio");
    pares = firmanteService.buscarParesDeFirmante(firmante);
    LOGGER.info("Fin");
  }

  public String prepararInsercion() {
    LOGGER.info("Inicio");
    firmanteNuevo = new Firmante();
    firmanteNuevo.setEstado(SateliteConstants.ESTADO_ACTIVO);
    firmanteNuevo.setNombreAccion(SateliteConstants.REGISTRAR);
    firmanteNuevo.setEstadoAccion(SateliteConstants.ESTADO_PENDIENTE);
    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firma", null);
    LOGGER.info("Fin");
    return null;
  }

  public String prepararActualizacion() {
    LOGGER.info("Inicio");
    firmanteNuevo = new Firmante(firmanteActivo.getCodigo(), firmanteActivo.getUsuario(), firmanteActivo.getNombres(), firmanteActivo.getApellidos(), firmanteActivo.getCorreo(),
        firmanteActivo.getClave(), firmanteActivo.getEstado(), firmanteActivo.getFirma());
    firmanteNuevo.setNombreAccion(SateliteConstants.MODIFICAR);
    firmanteNuevo.setEstadoAccion(SateliteConstants.ESTADO_PENDIENTE);
    firmanteNuevo.setFirmaDesprotegida(firmanteActivo.getFirmaDesprotegida());
    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firma", firmanteNuevo.getFirmaDesprotegida());
    LOGGER.info("Fin");
    return null;
  }

  public String prepararRechazo() {
    LOGGER.info("Inicio");
    firmanteTemp.setMotivoRechazo("");
    LOGGER.info("Fin");
    return null;
  }

  public String prepararEliminacion() {
    LOGGER.info("Inicio");
    firmanteNuevo = new Firmante(firmanteActivo.getCodigo(), firmanteActivo.getUsuario(), firmanteActivo.getNombres(), firmanteActivo.getApellidos(), firmanteActivo.getCorreo(),
        firmanteActivo.getClave(), firmanteActivo.getEstado(), firmanteActivo.getFirma());
    firmanteNuevo.setNombreAccion(SateliteConstants.ELIMINAR);
    firmanteNuevo.setEstadoAccion(SateliteConstants.ESTADO_PENDIENTE);
    buscarPares(firmanteNuevo);
    LOGGER.info("Fin");
    return null;
  }

  public String prepararVisualizacion() {
    LOGGER.info("Inicio");
    int resultado = calcularAlto();
    LOGGER.info(resultado);
    setAlto(String.valueOf(resultado));
    LOGGER.info("Fin");
    return null;
  }

  public String prepararVisualizacionTemp() {
    LOGGER.info("Inicio");
    int resultado = calcularAlto();
    LOGGER.info(resultado);

    ancho = 350;
    setAlto(String.valueOf(resultado));
    if (SateliteConstants.MODIFICAR.equalsIgnoreCase(firmanteTemp.getNombreAccion())) {
      firmanteActual = firmanteService.obtenerFirmante(firmanteTemp.getCodigo());
      ancho = 635;
    } else if (SateliteConstants.ELIMINAR.equalsIgnoreCase(firmanteTemp.getNombreAccion())) {
      buscarPares(firmanteTemp);
      ancho = 635;
    }

    LOGGER.info("Fin");
    return null;
  }

  private int calcularAlto() {
    int cont = 0;
    if (SateliteConstants.MODIFICAR.equalsIgnoreCase(firmanteTemp.getNombreAccion()) || SateliteConstants.ELIMINAR.equalsIgnoreCase(firmanteTemp.getNombreAccion())) {
      cont++;
    }
    if (SateliteConstants.ESTADO_RECHAZADO.equalsIgnoreCase(firmanteTemp.getEstadoAccion())) {
      cont++;
    }
    int resultado = 300 + cont * 50;
    return resultado;
  }

  public void listener(UploadEvent event) throws Exception {

    UploadItem item = event.getUploadItem();
    LOGGER.debug("Nombre: " + item.getFileName());
    LOGGER.debug("Tamaño: " + item.getFileSize());
    try {
      if (item.isTempFile()) {
        LOGGER.info("Es temporal");
        byte[] bytes = new byte[(int) item.getFile().length()];
        File archivo = item.getFile();
        FileInputStream input = new FileInputStream(archivo);
        input.read(bytes);
        input.close();
        firmanteNuevo.setFirma(bytes);
        firmanteNuevo.setFirmaDesprotegida(bytes);
      } else {
        LOGGER.info("No es temporal");
        firmanteNuevo.setFirma(item.getData());
        firmanteNuevo.setFirmaDesprotegida(item.getData());
      }
      double kilobytes = firmanteNuevo.getFirmaDesprotegida().length / 1024;
      LOGGER.info("kilobytes: " + kilobytes + "kb");
    } catch (Exception e) {
      LOGGER.error("ERROR:[listener()]: " + e);
    }

    InputStream input = new FileInputStream(item.getFile());
    BufferedImage bufferedImage = ImageIO.read(input);
    int width = bufferedImage.getWidth();
    int height = bufferedImage.getHeight();
    LOGGER.info("Ancho:" + width);
    LOGGER.info("Alto:" + height);
    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firma", firmanteNuevo.getFirmaDesprotegida());
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public List<Firmante> getFirmantesActivos() {
    return firmantesActivos;
  }

  public void setFirmantesActivos(List<Firmante> firmantesActivos) {
    this.firmantesActivos = firmantesActivos;
  }

  public List<Firmante> getFirmantesTemporales() {
    return firmantesTemporales;
  }

  public void setFirmantesTemporales(List<Firmante> firmantesTemporales) {
    this.firmantesTemporales = firmantesTemporales;
  }

  public SimpleSelection getSelectionActivo() {
    return selectionActivo;
  }

  public void setSelectionActivo(SimpleSelection selectionActivo) {
    this.selectionActivo = selectionActivo;
  }

  public SimpleSelection getSelectionTemp() {
    return selectionTemp;
  }

  public void setSelectionTemp(SimpleSelection selectionTemp) {
    this.selectionTemp = selectionTemp;
  }

  public Firmante getFirmanteActivo() {
    return firmanteActivo;
  }

  public void setFirmanteActivo(Firmante firmanteActivo) {
    this.firmanteActivo = firmanteActivo;
  }

  public Firmante getFirmanteTemp() {
    return firmanteTemp;
  }

  public void setFirmanteTemp(Firmante firmanteTemp) {
    this.firmanteTemp = firmanteTemp;
  }

  public Firmante getFirmanteNuevo() {
    return firmanteNuevo;
  }

  public void setFirmanteNuevo(Firmante firmanteNuevo) {
    this.firmanteNuevo = firmanteNuevo;
  }

  public List<FirmantePar> getPares() {
    return pares;
  }

  public void setPares(List<FirmantePar> pares) {
    this.pares = pares;
  }

  public FirmanteService getFirmanteService() {
    return firmanteService;
  }

  public void setFirmanteService(FirmanteService firmanteService) {
    this.firmanteService = firmanteService;
  }

  public String getAlto() {
    return alto;
  }

  public void setAlto(String alto) {
    this.alto = alto;
  }

  public Firmante getFirmanteActual() {
    return firmanteActual;
  }

  public void setFirmanteActual(Firmante firmanteActual) {
    this.firmanteActual = firmanteActual;
  }

  public int getAncho() {
    return ancho;
  }

  public void setAncho(int ancho) {
    this.ancho = ancho;
  }

}
