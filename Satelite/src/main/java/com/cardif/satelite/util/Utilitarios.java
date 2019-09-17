package com.cardif.satelite.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.time.DateUtils;

import com.cardif.satelite.model.Parametro;

public class Utilitarios {

  /**
   * @author lmaron
   * @fecha 15/03/2014
   * @param line
   * @return cadena con caracteres extranios reemplazados
   */
  public static String replaceCaracters(String line) {

    CharsetEncoder isoEncoder = Charset.forName("ISO-8859-1").newEncoder();
    CharsetEncoder asciEncoder = Charset.forName("ASCII").newEncoder();
    CharsetEncoder utf8Encoder = Charset.forName("UTF-8").newEncoder();

    Boolean isISO = isoEncoder.canEncode(line);
    Boolean isASCII = asciEncoder.canEncode(line);
    String datos = "";

    if (isISO == false && isASCII == false) {
      Charset utf8charset = Charset.forName("UTF-8");
      Charset iso88591charset = Charset.forName("ISO-8859-1");
      // Decode UTF-8
      ByteBuffer bb = ByteBuffer.wrap(line.getBytes());
      CharBuffer data = utf8charset.decode(bb);

      // Encode ISO-8559-1
      ByteBuffer outputBuffer = iso88591charset.encode(data);
      byte[] outputData = outputBuffer.array();

      datos = new String(outputData);

      // logger.info("Datos con caract. Reemplazados |: "+datos);
    } else {
      datos = line;
      // logger.info("Datos no modificados: "+datos);
    }
    /*
     * logger.info("isISO : "+isISO); logger.info("isASCII : "+isASCII); logger.info("isUTF8 : "+isUTF8);
     */
    return datos;
  }

  /***
   * 
   */
  public static String convertirFechaACadena(Date fecha, String formato) {
	  DateFormat df = null;
	  String respuesta = "";
	  try {
		if(fecha != null){
			 df = new SimpleDateFormat(formato, Locale.US);	
			 respuesta = df.format(fecha);
		}else{
			respuesta="";
		}
	    
	} catch (Exception e) {
		e.printStackTrace();
	}      
    return respuesta;
  }
  
  /**
   * Gutenberg Torres Brousset P.
   */
  public static String completaCadenaIzq(String cadena, int longitud) {
    String respuesta = cadena == null ? "" : cadena;
    respuesta = String.format("%1$-" + longitud + "s", respuesta);
    return respuesta;
  }
  	
  	
  	public static synchronized boolean verificarRangoDeFechas(Date fechaInicial, Date fechaFinal, int dias) throws Exception
  	{
  		/*
  		 * Truncar las fechas
  		 */
  		fechaInicial = DateUtils.truncate(fechaInicial, Calendar.DAY_OF_MONTH);
  		fechaFinal = DateUtils.truncate(fechaFinal, Calendar.DAY_OF_MONTH);
  		
  		Date fechaInicialDias = DateUtils.addDays(fechaInicial, dias);
  		
  		/*
  		 * Validar:
  		 * - fechaFinal >= fechaInicial
  		 * - (fechaInicial + dias) > fechaFinal
  		 */
  		boolean flag = false;
  		if ((fechaFinal.after(fechaInicial) || fechaInicial.equals(fechaFinal)) && fechaInicialDias.after(fechaFinal))
  		{
  			flag = true;
  		}
  		return flag;
  	} //verificarRangoDeFechas
  	
  	public static synchronized Parametro obtenerObjetoParametro(List<Parametro> listaParametros, String codValor)
  	{
  		Parametro paramRespuesta = null;
  		
  		if (null != listaParametros && 0 < listaParametros.size())
  		{
  			for (Parametro parametro : listaParametros)
  			{
  				if (parametro.getCodValor().equalsIgnoreCase(codValor))
  				{
  					paramRespuesta = parametro; break;
  				}
  			}
  		}
  		return paramRespuesta;
  	} //obtenerObjetoParametro
  	
	public static String verificarLongitudCampo(String str, int longitudMax){
		String campo="";
		campo = str.substring(0, longitudMax);
		return campo;
	}//verificarLongitudCampo
  	
	public static Boolean validateCharacter(String cadena){

		Boolean sw = false;
		if(null!=cadena){
			Matcher mat = null;
			Pattern pat = Pattern.compile("^([A-z\\s-ñáéíóúÑÁÉÍÓÚ.0-9]{1,255})$");
			mat = pat.matcher(cadena);	
			if(!mat.matches()){
				sw = true;
			}
			
			char[] ca = cadena.toCharArray();

			for (char c : ca) {			
				switch (c) {
				case '[' : sw = true; break;
				case ']' : sw = true; break;
				case '_' : sw = true; break;
				case '-' : sw = true; break;
				}					
			}
		}
		
		return sw;
	}
	
	public static Boolean validateCharacterDesCanal(String cadena){

		Boolean sw = false;
		if(null!=cadena){
			Matcher mat = null;
			Pattern pat = Pattern.compile("^([A-z\\s-ñáéíóúÑÁÉÍÓÚ.0-9]{1,255})$");
			mat = pat.matcher(cadena);	
			if(!mat.matches()){
				sw = true;
			}
			
			char[] ca = cadena.toCharArray();

			for (char c : ca) {			
				switch (c) {
				case '[' : sw = true; break;
				case ']' : sw = true; break;
				case '-' : sw = true; break;
				}					
			}
		}
		
		return sw;
	}
	
	
	
	public static boolean validarFechasInicioFin(Date fechaInicio, Date fechaFin){
		boolean rpt = false;
		int resultado = 0;
		
		resultado = fechaFin.compareTo(fechaInicio);
		if(resultado == 0 || resultado > 0){
			rpt = true;
		}
		
		return rpt;
	}
	
	 public static void copyFiles(File source , File destino)
			    throws IOException {
		 
		 
		 	InputStream inStream = null;
			OutputStream outStream = null;

		    	try{


		    	    inStream = new FileInputStream(source);
		    	    outStream = new FileOutputStream(destino);

		    	    byte[] buffer = new byte[1024];

		    	    int length;
		    	    //copy the file content in bytes
		    	    while ((length = inStream.read(buffer)) > 0){

		    	    	outStream.write(buffer, 0, length);

		    	    }

		    	    inStream.close();
		    	    outStream.close();

		    	    //delete the original file
		    	    //source.delete();

		    	    System.out.println("File is copied successful!");

		    	}catch(IOException e){
		    	    e.printStackTrace();
		    	}
		 
	 }
	 
		public static String  formateoMonto(Double monto){
			String montoStr = "";
			
			NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
			DecimalFormat df = (DecimalFormat)nf;
			df.applyPattern("#####.00");
			
			if(monto == null || monto == 0D || Double.isNaN(monto)){
				montoStr = "0.00";
			}else{
				montoStr = df.format(monto);
			}
			return montoStr;
		}
	
}
