package com.cardif.satelite.suscripcion.controller;

import static com.cardif.satelite.constantes.ErrorConstants.ERROR_SYNCCON;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR_GENERAL;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.richfaces.model.selection.SimpleSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.cardif.framework.controller.BaseController;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.acsele.service.ProductService;
import com.cardif.satelite.configuracion.service.ParametroService;
import com.cardif.satelite.configuracion.service.SocioService;
import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.model.Parametro;
import com.cardif.satelite.model.Socio;
import com.cardif.satelite.model.satelite.ConciCabReporte;
import com.cardif.satelite.model.satelite.ConciReporte;
import com.cardif.satelite.model.satelite.ConciTramaMensual;
import com.cardif.satelite.suscripcion.service.ProcesosETLsService;
import com.cardif.satelite.suscripcion.service.RepConciliacionService;
import com.cardif.satelite.util.PropertiesUtil;
import com.cardif.satelite.util.Utilitarios;

/**
 * @author torresgu2
 * @create 2014.07.21
 * @version 0.1
 * @description Reporte de Conciliaci�n SOAT
 */
@Controller("repConciliacionController")
@Scope("request")
public class RepConciliacionController extends BaseController
{
	private static final Logger logger = Logger.getLogger(RepConciliacionController.class);
	
	@Autowired
	private RepConciliacionService repConciliacionService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ParametroService parametroService;
	
	@Autowired
	private SocioService socioService;
	
	@Autowired
	private ProcesosETLsService procesosETLsService;
	
	private Integer periodo;
	private String socio;
	private String producto;
	private List<SelectItem> comboListaSocios;
	
	private boolean generado;
	
	private SimpleSelection selection;
	
	private ConciCabReporte conciCabReporte;
	
	private List<SelectItem> productoItems;
	private List<ConciCabReporte> listaRepConciliacion;
	private List<ConciReporte> listaReporteConciliacionExport;
	
	private File txtGenerado;
	
	
	@PostConstruct
	@Override
	public String inicio()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		String respuesta = null;
		
		if (!tieneAcceso())
		{
			if (logger.isDebugEnabled()) {logger.debug("No cuenta con los accesos necesarios");}
			return "accesoDenegado";
		}
		
		try
		{
			periodo = null;
			productoItems = new ArrayList<SelectItem>();
			listaRepConciliacion = new ArrayList<ConciCabReporte>();
			
			List<Socio> listaSocio = socioService.buscarSocio(Constantes.SOCIO_ESTADO_ACTIVO);
			this.productoItems.add(new SelectItem("", "- Seleccione -"));
			for (Socio socio : listaSocio)
			{
				if (logger.isDebugEnabled()) {logger.debug("SOCIO_ID: " + socio.getId() + " SOCIO_NOMBRE: " + socio.getNombreSocio() + " SOCIO_ABBREVIATURA: " + socio.getAbreviatura());}
				this.productoItems.add(new SelectItem(socio.getId(), socio.getNombreSocio()));
			}
			
			
			
			//listaProductos();
			generado = false;
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
			respuesta = MSJ_ERROR;
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return respuesta;
	} //inicio
	
	
	private void btnConciliacionDiariaMensual(){
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		try {
			procesosETLsService.procesosCargaConciliacionTrama();
		} catch (SyncconException e) {
			// TODO Auto-generated catch block
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}


	}
	
	private void btnConciliacionSOAT(){
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		try {
			procesosETLsService.procesosCargaConciliacion();
		} catch (SyncconException e) {
			// TODO Auto-generated catch block
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
	}
	
	
	private void listaProductos()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		try
		{
			// List<Product> listaProducto = productService.buscarSoat();
			// List<Product> listaProducto =
			// productService.buscarProductosConciliacion();
			List<Parametro> listaProducto = parametroService.buscar(Constantes.COD_PARAM_PRODUCTO_SOAT, Constantes.TIP_PARAM_DETALLE);
			
			productoItems.add(new SelectItem("", "- Seleccione -"));
			for (Parametro producto : listaProducto)
			{
				productoItems.add(new SelectItem(producto.getCodValor(), producto.getNomValor()));
			}
		}
		catch (SyncconException ex)
		{
			logger.error("SyncconException() - ERROR: " + ERROR_SYNCCON + ex.getMessageComplete());
			FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage(), e);
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
	} //listaProductos
	
	public String cargarReportes()
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
	    String respuesta = null;
	    
	    try
	    {
	    	listaRepConciliacion = repConciliacionService.listaResumenConciliacion(producto);
	    	selection = null;
	    }
	    catch (SyncconException ex)
	    {
	    	logger.error("ERROR SYNCCON: " + ex.getMessageComplete());
	    	FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
	    	FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	    }
	    catch (Exception e)
	    {
	    	logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage(), e);
	    	FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
	    	FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	    	respuesta = "error";
	    }
	    logger.info("Fin");
	    return respuesta;
	} //cargarReportes
	
  	public String procesar()
  	{
  		if (logger.isInfoEnabled()) {logger.info("Inicio");}
	  		generado = false;
	  		String respuesta = null;
	  		String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
	  		Date fecActual = new Date(System.currentTimeMillis());
  		
  		try
  		{
  			socio = producto.substring(0, 2);
  			
  			if (periodo == null || periodo < 200000 || periodo % 100 > 12 || periodo % 100 == 0)
  			{
  				throw new SyncconException(ErrorConstants.COD_ERROR_PERIODO_INVALIDO, FacesMessage.SEVERITY_INFO);
  			}
  			else if (!repConciliacionService.verificarMaster(periodo, socio))
  			{
  				throw new SyncconException(ErrorConstants.COD_ERROR_NO_MASTER, FacesMessage.SEVERITY_INFO);
  			}
  			else
  			{
  				conciCabReporte = new ConciCabReporte();
  				
//  				conciCabReporte.setPeriodo(periodo);
//  				conciCabReporte.setSocio(socio);
//  				conciCabReporte.setProducto(producto);
//  				conciCabReporte.setNumRegistros(0L);
//  				conciCabReporte.setRegObservados(0L);
//        		conciCabReporte.setRegAceptados(0L);
//        		conciCabReporte.setFecProceso(fecActual);
//        		conciCabReporte.setUsuProceso(usuario);
        		
        		if (logger.isInfoEnabled()) {logger.info("Procesando el reporte de conciliacion.");}
        		repConciliacionService.procesarConciliacion(conciCabReporte);
        		
        		if (logger.isInfoEnabled()) {logger.info("Cargando los reportes.");}
        		cargarReportes();
        		if (logger.isDebugEnabled()) {logger.debug("Se cargaron los reportes.");}
        		
        		if (logger.isInfoEnabled()) {logger.info("Generando trama TXT.");}
        		txtGenerado = generaTramaTXT();
        		if (logger.isDebugEnabled()) {logger.debug("Se genero la trama TXT.");}
        		
        		// descargarTXT();
        		generado = true;
  			}
  		}
  		catch (SyncconException ex)
  		{
  			logger.error("SyncconException() - ERROR: " + ErrorConstants.ERROR_SYNCCON + ex.getMessageComplete());
  			FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
  			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
  		}
  		catch (Exception e)
  		{
  			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage(), e);
  			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
  			FacesContext.getCurrentInstance().addMessage(null, facesMsg);
  			respuesta = "error";
  		}
  		if (logger.isInfoEnabled()) {logger.info("Fin");}
  		return respuesta;
  	} //procesar
  	
  public String exportar() {
	  logger.info("Inicio");
    String respuesta = null;
    Map<String, Object> beans = new HashMap<String, Object>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
    ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
    String FechaActual = sdf.format(new Date(System.currentTimeMillis()));
    XLSTransformer transformer = new XLSTransformer();
    try {

      Iterator<Object> iterator = getSelection().getKeys();

      if (listaRepConciliacion.isEmpty()) {
        throw new SyncconException(ErrorConstants.COD_ERROR_ELIMINAR_LISTA, FacesMessage.SEVERITY_INFO);// No
                                                                                                        // hay
                                                                                                        // elementos
                                                                                                        // en
                                                                                                        // la
        // lista
      } else if (!iterator.hasNext()) {
        throw new SyncconException(ErrorConstants.COD_ERROR_REGISTRO_NO_SELECCIONADO, FacesMessage.SEVERITY_INFO);//
      }
      if (iterator.hasNext()) {
        int key = (Integer) iterator.next();
//        Integer periodo = listaRepConciliacion.get(key).getPeriodo();
//        String producto = listaRepConciliacion.get(key).getProducto();

        listaReporteConciliacionExport = repConciliacionService.listaReporteConciliacion(periodo, producto);
        String rutaTemp = System.getProperty("java.io.tmpdir") + "REPORTE_CONCILIACION_" + System.currentTimeMillis() + ".xlsx";

        logger.debug("Ruta Archivo: " + rutaTemp);

        FacesContext fc = FacesContext.getCurrentInstance();
        ServletContext sc = (ServletContext) fc.getExternalContext().getContext();
        String rutaTemplate = sc.getRealPath(File.separator + "excel\\template_reporte_conciliacion_export.xlsx");

        logger.debug("Ruta Template: " + rutaTemplate);

        beans.put("exportar", listaReporteConciliacionExport);
        extracted(beans, transformer, rutaTemp, rutaTemplate);

        File archivoResp = new File(rutaTemp);
        FileInputStream in = new FileInputStream(archivoResp);

        HttpServletResponse response = (HttpServletResponse) contexto.getResponse();

        byte[] loader = new byte[(int) archivoResp.length()];

        response.addHeader("Content-Disposition", "attachment;filename=" + "REPORTE_CONCILIACION-" + FechaActual + ".xlsx");
        response.setContentType("application/vnd.ms-excel");
        ServletOutputStream out = response.getOutputStream();

        while ((in.read(loader)) > 0) {
          out.write(loader, 0, loader.length);
        }
        in.close();
        out.close();

        FacesContext.getCurrentInstance().responseComplete();
      }
    } catch (SyncconException ex) {
    	logger.error(ErrorConstants.ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
    	logger.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }
    logger.info("Fin");
    return respuesta;
  }

  private void extracted(Map<String, Object> beans, XLSTransformer transformer, String rutaTemp, String rutaTemplate) throws IOException, InvalidFormatException {
    transformer.transformXLS(rutaTemplate, beans, rutaTemp);
  }

  private File generaTramaTXT() {
	  logger.info("Inicio generaTramaTXT");
    File respuesta = null;
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");

    String FechaActual = sdf1.format(new Date(System.currentTimeMillis()));

    List<ConciTramaMensual> lista;

    final String rutaVentas = PropertiesUtil.getProperty("ST");
    // String s = File.separator;
    // String rutaVentas = "C:" + s + "temp" + s + "Conciliacion" + s + "SOAT" +
    // s;
    logger.info("Ruta ventas: " + rutaVentas);
    try {
      // File tramaGenerada = new
      // File(System.getProperty("java.io.tmpdir") + "TRAMA_GENERADA_" +
      // FechaActual + ".txt");
      File tramaGenerada = new File(rutaVentas + "TRAMA_GENERADA_" + FechaActual + ".TXT");

      FileWriter fw = new FileWriter(tramaGenerada);
      BufferedWriter bw = new BufferedWriter(fw);
      PrintWriter pw = new PrintWriter(bw);
      pw.write("");

      // lista = repConciliacionService.listaTramaMensual(periodo, producto);
      lista = repConciliacionService.listaTramaTXT(periodo, producto);

      String linea;
      int cont = 0;

      for (ConciTramaMensual registro : lista) {
        cont++;

        linea = "";
        linea = linea + Utilitarios.completaCadenaIzq(registro.getPoliza(), 20);
        String iniVigencia = registro.getIniVigencia() == null ? "" : sdf2.format(registro.getIniVigencia());
        linea = linea + Utilitarios.completaCadenaIzq(iniVigencia, 8);
        String finVigencia = registro.getFinVigencia() == null ? "" : sdf2.format(registro.getFinVigencia());
        linea = linea + Utilitarios.completaCadenaIzq(finVigencia, 8);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getMoneda(), 3);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getTipDocumento(), 2);
        String numDocumento = registro.getNumDocumento() == null ? "" : registro.getNumDocumento().toString();
        linea = linea + Utilitarios.completaCadenaIzq(numDocumento, 15);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getNomContratante(), 100);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getApePatContratante(), 50);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getApeMatContratante(), 50);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getSexo(), 1);
        String fecNacimiento = registro.getFecNacimiento() == null ? "" : sdf2.format(registro.getFecNacimiento());
        linea = linea + Utilitarios.completaCadenaIzq(fecNacimiento, 8);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getEstCivil(), 1);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getDepartamento(), 50);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getProvincia(), 50);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getDistrito(), 50);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getDireccion(), 100);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getTelFijo(), 20);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getTelMovil(), 20);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getEmail(), 50);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getNumTarjeta(), 30);
        String fecVencimiento = registro.getFecVencimientoTarj() == null ? "" : sdf2.format(registro.getFecVencimientoTarj());
        linea = linea + Utilitarios.completaCadenaIzq(fecVencimiento, 8);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getTipTarjeta(), 20);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getMarca(), 50);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getModelo(), 50);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getModImpresion(), 50);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getTipVehiculo(), 50);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getUsoVehiculo(), 50);
        String anioFabricacion = registro.getAnioFabricacion() == null ? "" : registro.getAnioFabricacion().toString();
        linea = linea + Utilitarios.completaCadenaIzq(anioFabricacion, 4);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getPlaca(), 20);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getSerie(), 20);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getMotor(), 20);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getColor(), 50);
        String numAsientos = registro.getNumAsientos() == null ? "" : registro.getNumAsientos().toString();
        linea = linea + Utilitarios.completaCadenaIzq(numAsientos, 2);
        String numCertificado = registro.getNumCertificado() == null ? "" : registro.getNumCertificado().replace(" ", "");
        linea = linea + Utilitarios.completaCadenaIzq(numCertificado, 12);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getCodMaster(), 50);
        String primaBruta = registro.getPrimaBruta() == null ? "" : registro.getPrimaBruta().toString();
        linea = linea + Utilitarios.completaCadenaIzq(primaBruta, 15);
        String fecVenta = registro.getFecVenta() == null ? "" : sdf2.format(registro.getFecVenta());
        linea = linea + Utilitarios.completaCadenaIzq(fecVenta, 8);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getCanalVenta(), 20);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getPuntoVenta(), 50);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getMedioPago(), 20);
        String codProducto = registro.getCodProducto() == null ? "" : registro.getCodProducto().toString();
        linea = linea + Utilitarios.completaCadenaIzq(codProducto, 5);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getCodVendedor(), 10);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getClase(), 80);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getNomVendedor(), 100);
        String fecCobro = registro.getFecCobro() == null ? "" : sdf2.format(registro.getFecCobro());
        linea = linea + Utilitarios.completaCadenaIzq(fecCobro, 8);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getSubProducto(), 10);
        String primaTecnica = registro.getPrimaTecnica() == null ? "" : registro.getPrimaTecnica().toString();
        linea = linea + Utilitarios.completaCadenaIzq(primaTecnica, 15);
        linea = linea + Utilitarios.completaCadenaIzq(registro.getCodTecnica(), 5);
        linea = linea + "\r\n";
        logger.debug("Linea: " + linea);
        pw.append(linea);

      }

      pw.close();
      // bw.close();

      respuesta = tramaGenerada;
    } catch (Exception e) {
    	logger.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }
    logger.info("Fin");
    return respuesta;
  }

  

  public String descargarTXT() {
	  logger.info("Inicio descargarTXT");
    String respuesta = null;
    try {
      ExternalContext contexto = FacesContext.getCurrentInstance().getExternalContext();
      HttpServletResponse response = (HttpServletResponse) contexto.getResponse();
      logger.info("Leyendo data");
      FileInputStream in = new FileInputStream(txtGenerado);

      /* inicio zip */
      File zipFile = new File(txtGenerado.getAbsolutePath().replace("TXT", "zip"));
      ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(zipFile));
      zip.putNextEntry(new ZipEntry(txtGenerado.getName()));

      byte[] b = new byte[(int) txtGenerado.length()];
      int count;
      while ((count = in.read(b)) > 0) {
        zip.write(b, 0, count);
      }
      zip.close();
      in.close();
      /* fin zip */

      // borramos el archivo txt
      if (txtGenerado.exists()) {
        txtGenerado.delete();
      }

      FileInputStream zipIn = new FileInputStream(zipFile);
      byte[] loader = new byte[(int) zipFile.length()];
      ServletOutputStream out = response.getOutputStream();
      logger.debug("attachment;filename=" + zipFile.getName());
      response.addHeader("Content-Disposition", "attachment;filename=" + zipFile.getName());
      response.setContentType("application/zip");

      while ((zipIn.read(loader)) > 0) {
        out.write(loader, 0, loader.length);
      }
      out.close();
      zipIn.close();
      FacesContext.getCurrentInstance().responseComplete();
      logger.info("Se descargó trama con éxito");
    } catch (Exception e) {
    	logger.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = null;
    }
    logger.info("Fin");
    return respuesta;
  }
  	
  	public Integer getPeriodo() {
  		return periodo;
  	}
  	
  	public void setPeriodo(Integer periodo) {
  		this.periodo = periodo;
  	}
  	
  	public String getSocio() {
  		return socio;
  	}
  	
  	public void setSocio(String socio) {
  		this.socio = socio;
  	}
  	
  	public String getProducto() {
  		return producto;
  	}
  	
  	public void setProducto(String producto) {
  		this.producto = producto;
  	}
  	
  	public List<SelectItem> getProductoItems() {
  		return productoItems;
  	}
  	
  	public void setProductoItems(List<SelectItem> productoItems) {
  		this.productoItems = productoItems;
  	}
  	
  	public List<ConciCabReporte> getListaRepConciliacion() {
  		return listaRepConciliacion;
  	}
  	
  	public void setListaRepConciliacion(List<ConciCabReporte> listaRepConciliacion) {
  		this.listaRepConciliacion = listaRepConciliacion;
  	}
  	
  	public SimpleSelection getSelection() {
  		return selection;
  	}
  	
  	public void setSelection(SimpleSelection selection) {
  		this.selection = selection;
  	}
  	
  	public ConciCabReporte getConciCabReporte() {
  		return conciCabReporte;
  	}
  	
  	public void setConciCabReporte(ConciCabReporte conciCabReporte) {
  		this.conciCabReporte = conciCabReporte;
  	}
  	
  	public List<ConciReporte> getListaReporteConciliacionExport() {
  		return listaReporteConciliacionExport;
  	}
  	
  	public void setListaReporteConciliacionExport(List<ConciReporte> listaReporteConciliacionExport) {
  		this.listaReporteConciliacionExport = listaReporteConciliacionExport;
  	}
  	
  	public File getTxtGenerado() {
  		return txtGenerado;
  	}
  	
  	public void setTxtGenerado(File txtGenerado) {
  		this.txtGenerado = txtGenerado;
  	}

  	public boolean isGenerado() {
  		return generado;
  	}
  	
  	public void setGenerado(boolean generado) {
  		this.generado = generado;
  	}
 
  	//TESTS
  	
  	private boolean buttonRendered = true;
    private boolean enabled=false;
    private Long startTime;
    

    
    public String startProcess() {
        setEnabled(true);
        setButtonRendered(false);
        setStartTime(new Date().getTime());
        return null;
    }

    public Long getCurrentValue(){
        if (isEnabled()){
            Long current = (new Date().getTime() - startTime)/10000;
            if (current>1000){
                setButtonRendered(true);
            }else if (current.equals(0)){
                return new Long(1);
            }
            return (new Date().getTime() - startTime)/10000;
        } if (startTime == null) {
            return Long.valueOf(-1);
        }
        else
            return Long.valueOf(1001);
    }
    
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public boolean isButtonRendered() {
        return buttonRendered;
    }

    public void setButtonRendered(boolean buttonRendered) {
        this.buttonRendered = buttonRendered;
    }
  	
  	
}
