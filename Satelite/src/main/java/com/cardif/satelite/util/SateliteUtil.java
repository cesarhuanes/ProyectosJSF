package com.cardif.satelite.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.richfaces.model.selection.SimpleSelection;

import com.cardif.sunsystems.util.ConstantesSun;

public final class SateliteUtil {

  public static final Logger logger = Logger.getLogger(SateliteUtil.class);
  private static final SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
  private static final SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

  private SateliteUtil() {

  }

  public static void clearCookies() {
    HttpServletResponse resp = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();

    HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

    for (Cookie cookie : req.getCookies()) {
      cookie.setValue("");
      cookie.setMaxAge(0);
      cookie.setPath("/");
      resp.addCookie(cookie);
    }
  }

  public static Object verSeleccionado(SimpleSelection selection, List lista) {

    Object objeto = null;
    Iterator<Object> iterator = selection.getKeys();
    while (iterator.hasNext()) {
      Integer key = (Integer) iterator.next();
      objeto = lista.get(key);
      break;
    }
    selection = null;
    return objeto;
  }

  public static void verSeleccionados(List objetos, SimpleSelection selection, List lista) {

    Iterator<Object> iterator = selection.getKeys();
    while (iterator.hasNext()) {
      Integer key = (Integer) iterator.next();
      Object objeto = lista.get(key);
      objetos.add(objeto);
    }

  }

  public static String resetString(String cadena) {
    if (cadena != null) {
      cadena = cadena.trim();
      if (cadena.equalsIgnoreCase("")) {
        cadena = null;
      }
    }
    return cadena;
  }

  public static String limpiarMensajes() {
    FacesContext facesContext = FacesContext.getCurrentInstance();
    facesContext.getMessages().remove();
    return null;
  }

  public static void mostrarMensaje(String mensaje) {
    FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, null);
    FacesContext.getCurrentInstance().addMessage(null, facesMsg);
  }

  public static void mostrarMensaje(Severity severity, String mensaje) {
    FacesMessage facesMsg = new FacesMessage(severity, mensaje, null);
    FacesContext.getCurrentInstance().addMessage(null, facesMsg);
  }

  public static String getPath(String ruta) {
    ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    return servletContext.getRealPath(ruta);
  }

  public static String getFormatDate(Date date) {
    if (date != null) {
      return sdf.format(date);
    } else {
      return "";
    }
  }

  public static Date getDateFromString(String fecha) {
    if (fecha == null)
      return null;
    else {

      try {
        return sdf.parse(fecha);
      } catch (ParseException e) {
        logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
        logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
        return null;
      }
    }
  }

  public static String getStringFromDate(Date date) {
    if (date != null) {
      return formato.format(date);
    } else {
      return "";
    }
  }

  public static String ajustarTextoCaracteres(String texto, int caracteres, String caracterVacio, String alineamiento) {
    String resultado = texto;
    int size = texto.length();

    if (size > caracteres) {
      return resultado.substring(0, caracteres);
    } else if (size < caracteres) {
      for (int i = 0; i < caracteres - size; i++) {
        if (alineamiento.equalsIgnoreCase(ConstantesSun.UTL_RELLENAR_IZQUIERDA)) {
          resultado = caracterVacio + resultado;
        } else if (alineamiento.equalsIgnoreCase(ConstantesSun.UTL_RELLENAR_DERECHA)) {
          resultado = resultado + caracterVacio;
        }

      }
    }
    return resultado;
  }

  public static String getTextoSinGuiones(String texto) {
    String result = "";
    String c = "";
    for (int i = 0; i < texto.length(); i++) {
      c = texto.substring(i, i + 1);
      if (!c.equals("-")) {
        result += c;
      }
    }
    return result;
  }

  public static String getEstado(String estado) {
    if (estado.equals(SateliteConstants.ESTADO_APROBADO)) {
      estado = "Aprobado";
    } else if (estado.equals(SateliteConstants.ESTADO_RECHAZADO)) {
      estado = "Rechazado";
    } else if (estado.equals(SateliteConstants.ESTADO_PENDIENTE)) {
      estado = "Pendiente";
    } else if (estado.equals(SateliteConstants.ESTADO_IMPRESO)) {
      estado = "Impreso";
    }
    return estado;
  }

  public static synchronized Date getInitialCardifDate() {
    Calendar initCalendar = Calendar.getInstance();

    initCalendar.set(Calendar.DAY_OF_MONTH, 1);
    initCalendar.set(Calendar.MONTH, 1);
    initCalendar.set(Calendar.YEAR, 2005);

    return initCalendar.getTime();
  } // getInitialCardifDate

  
  // PASE 
  
  public static synchronized Object cloneObject(Object oldObject)
	{
		if (logger.isDebugEnabled()) {logger.debug("Cloning object: " + oldObject);}
		Object clone = null;
		
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			
			oos.writeObject(oldObject);
			oos.flush();
			
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			
			clone = (Object) ois.readObject();
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
		}
		return clone;
	} //cloneObject
	
	public static String formatNumber(Long number, String pattern)
	{
		NumberFormat nf = new DecimalFormat(pattern);
		return nf.format(number);
	} //formatNumber
	
	public static List<Integer> obtenerUltimosPeriodos(int nroPeriodos) throws Exception
	{
		List<Integer> listaPeriodos = null;
		
		if (nroPeriodos > 0)
		{
	  		Integer anioActual = Calendar.getInstance().get(Calendar.YEAR);
	  		listaPeriodos = new ArrayList<Integer>(nroPeriodos);
	  		
	  		while (nroPeriodos >= 1)
	  		{
	  			listaPeriodos.add(anioActual--);
	  			nroPeriodos--;
	  		}
		}
		
		return listaPeriodos;
	} //obtenerUltimosPeriodos
	
	public static String formatNumber(Double number)
	{
		NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
		DecimalFormat df = (DecimalFormat) nf;
		df.applyPattern("###0.00");
		
		return df.format(number);
	} //formatNumber
	
  
}