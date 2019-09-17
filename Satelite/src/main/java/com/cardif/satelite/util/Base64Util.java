package com.cardif.satelite.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.itextpdf.text.pdf.PdfCopyFields;
import com.itextpdf.text.pdf.PdfReader;

public final class Base64Util
{
	private final Logger logger = Logger.getLogger(Base64Util.class);
	
	private static Base64Util instance = null;
	
	
	/**
	 * Private constructor to avoid instances.
	 */
	private Base64Util() {}
	
	public static synchronized Base64Util getInstance()
	{
		if (null == instance)
		{
			instance = new Base64Util();
		}
		return instance;
	} //getInstance
	
	/** 
	 * Codifica un arreglo de bytes hacia un string en base 64.
	 * 
	 * @param bytes
	 * @return
	 */
	public static String encriptarABase64(byte[] bytes) {
		String encodedString = Base64.encodeBase64String(bytes);
		return encodedString;
	}
	
	/**
	 * Decodifica el contenido de una cadena en base 64.
	 * @param contenido
	 * @return
	 */
	public static byte[] desencriptarDeBase64(String contenido) {
		byte[] bytes = Base64.decodeBase64(contenido);
		return bytes;
	}
	
	
	public String convertBase64ToString(byte[] bytes)
	{
		String encodeString = Base64.encodeBase64String(bytes);
		
		return encodeString;
	} //convertBase64ToString
	
	public byte[] convertStringToBase64(String content)
	{
		byte[] bytes = Base64.decodeBase64(content);
		
		return bytes;
	} //convertStringToBase64
	
	public File saveFileToTemp(byte[] bytes, String fileName)
	{
		File fileTemp = new File(System.getProperty("java.io.tmpdir") + fileName);
		
		try
		{
			FileOutputStream fos = new FileOutputStream(fileTemp);
			
			fos.write(bytes);
			fos.flush();
			fos.close();
		}
		catch (FileNotFoundException e)
		{
			logger.error("FileNotFoundException() - ERROR: " + e.getMessage());
		}
		catch (IOException e)
		{
			logger.error("IOException() - ERROR: " + e.getMessage());
		}
		return fileTemp;
	} //saveFileToTemp
	
	public byte[] getBytesFromFile(File file)
	{
		byte[] bytes = null;
		FileInputStream fis = null;
		
		try
		{
			if (file.exists())
			{
				fis = new FileInputStream(file);
				
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buf = new byte[1024];
				
				for (int i; (i = fis.read(buf)) != -1;)
				{
					baos.write(buf, 0, i);
				}
				fis.close();
				
				bytes = baos.toByteArray();
			}
		}
		catch (FileNotFoundException e)
		{
			logger.error("FileNotFoundException() - ERROR: " + e.getMessage());
		}
		catch (IOException e)
		{
			logger.error("IOException() - ERROR: " + e.getMessage());
		}
		return bytes;
	} //getBytesFromFile
	
	public String concatenarPDF(Map<Long, String> mapPDFBase64)
	{
		if (logger.isDebugEnabled()) {logger.debug("Inicio");}
		
		if (null == mapPDFBase64 || mapPDFBase64.isEmpty())
		{
			logger.error("El objeto MAP es nulo o vacio.");
			return null;
		}
		
		String response = null;
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfCopyFields copy = new PdfCopyFields(baos);
			
			/* Sort the MAP for KEY */
			mapPDFBase64 = MapUtil.sortByKey(mapPDFBase64);
			
			Iterator<Long> it  = mapPDFBase64.keySet().iterator();
			while (it.hasNext())
			{
				Long pk = null;
				try
				{
					pk = it.next();
					
					String pdfBase64 = mapPDFBase64.get(pk);
					byte[] pdf = convertStringToBase64(pdfBase64);
					
					PdfReader reader = new PdfReader(pdf);
					copy.addDocument(reader);
					if (logger.isDebugEnabled()) {logger.debug("Se copio el PDF del objeto ConciTramaDiaria[" + pk + "] bytes: " + (null != pdf ? pdf.length : 0));}
				}
				catch (Exception e)
				{
					logger.error("Exception(" + e.getClass().getName() + ") ERROR: " + e.getMessage() + ". ERROR al copiar el PDF del objeto ConciTramaDiaria[" + pk + "].");
				}
			}
			
			copy.close();
			
			byte[] bytes = baos.toByteArray();
			response = convertBase64ToString(bytes);
			
			if (logger.isDebugEnabled()) {logger.debug("Se culmino el copiado de los PDF's a un solo LOTE.");}
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ")	ERROR al concatenar los PDF's. " + e.getMessage());
		}
		if (logger.isDebugEnabled()) {logger.debug("Fin");}
		return response;
	} //concatenarPDF
	
} //Base64Util
