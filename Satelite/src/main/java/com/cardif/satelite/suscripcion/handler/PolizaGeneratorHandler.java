package com.cardif.satelite.suscripcion.handler;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.namespace.QName;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import cl.cardif.soap.datos.cliente.generaPoliza.DocumentoPDF;
import cl.cardif.soap.datos.cliente.generaPoliza.GenerarPDFImplService;
import cl.cardif.soap.datos.cliente.generaPoliza.GenerarPDFSEI;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.constantes.ErrorConstants;

public class PolizaGeneratorHandler
{
	private final Logger logger = Logger.getLogger(PolizaGeneratorHandler.class);
	
	private GenerarPDFSEI generarPDFSEI;
	
	
	private PolizaGeneratorHandler() throws SyncconException
	{
		if (logger.isDebugEnabled()) {logger.debug("Inicio - constructor");}
		try
		{
			String SERVICE_WSDL = null;
			QName SERVICE_NAME = null;
			
			try
			{
				Context env = (Context) new InitialContext().lookup("java:comp/env");
				
				SERVICE_WSDL = (String) env.lookup("URL_GENERA_POLIZA_WS");
				SERVICE_NAME = new QName("http://service.negocio.soap.cardif.cl/", "GenerarPDFImplService");
			}
			catch (NamingException e)
			{
				logger.error("Error al obtener endpoint. Se configura por defecto a http://localhost:8081/WSGenerarPoliza/Servicio/Negocio/generaPolizaWS?wsdl", e);
		        SERVICE_WSDL = "http://localhost:8081/WSGenerarPoliza/Servicio/Negocio/generaPolizaWS?wsdl";
			}
			
		    URL wsdlURL = new URL(SERVICE_WSDL);
		    GenerarPDFImplService service = new GenerarPDFImplService(wsdlURL, SERVICE_NAME);
			
		    if (logger.isDebugEnabled()) {logger.debug("Se genera el objeto GenerarPDFSEI.");}
			this.generarPDFSEI = service.getGenerarPDFImplPort();
		}
		catch (MalformedURLException e)
		{
			logger.error("Error al momento de crear la conexion. ERROR: " + e.getMessage());
			throw new SyncconException(ErrorConstants.COD_ERROR_GENERAR_PDF_POLIZA);
		}
		if (logger.isDebugEnabled()) {logger.debug("Fin - constructor");}
	} //PolizaGeneratorHandler
	
	public static synchronized PolizaGeneratorHandler newInstance() throws SyncconException
	{
		return new PolizaGeneratorHandler();
	} //newInstance
	
	
	public DocumentoPDF generarPolizaPDF(String nroPoliza, String nroPlaca, String nroSerie, Integer nroAsientos, Integer anioFab, String nombreCategoriaClase, String nombreUsoVehiculo, String nombreMarcaVehiculo, String nombreModeloVehiculo,
			Double costoPlan, Date fechaEmision, String horaEmision, Date fecIniVigencia, Date fecFinVigencia, String docIdentidadPropietario, String nombrePropietario, String apePaternoPropietario, String apeMaternoPropietario, 
			String departamentoPropietario, String provinciaPropietario, String distritoPropietario, String direccionPropietario, String telefonoPropietario) throws SyncconException
	{
		if (logger.isInfoEnabled()) {logger.info("Inicio");}
		DocumentoPDF documentoPDF = null;
		
		try
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("########################### GENERAR POLIZA PDF ###########################" + 
						"\nNRO_POLIZA: " + nroPoliza + "\tNRO_PLACA: " + nroPlaca + "\tNRO_SERIE: " + nroSerie + "\tNRO_ASIENTOS: " + nroAsientos + "\tANIO_FAB: " + anioFab + 
						"\nCATEGORIA_CLASE: " + nombreCategoriaClase + "\tUSO_VEHICULO: " + nombreUsoVehiculo + "\tMARCA: " + nombreMarcaVehiculo + "\tMODELO: " + nombreModeloVehiculo + 
						"\nCOSTO_PLAN: " + costoPlan + "\tFEC_EMISION: " + fechaEmision + "\tHO_EMISION: " + horaEmision + "\tINI_VIGEN: " + fecIniVigencia + "\tFIN_VIGEN: " + fecFinVigencia +
						"\nDOC_ID: " + docIdentidadPropietario + "\tNOMBRE: " + nombrePropietario + "\tAPE_PAT: " + apePaternoPropietario + "\tAPE_MAT: " + apeMaternoPropietario +
						"\nDEPARTAMENTO: " + departamentoPropietario + "\tPROVINCIA: " + provinciaPropietario + "\tDISTRITO: " + distritoPropietario + "\tDIRECCION: " + direccionPropietario + "\tTELEFONO: " + telefonoPropietario);
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			
			String fecEmisionString = null;
			if (null != fechaEmision)
			{
				fecEmisionString = sdf.format(fechaEmision);
			}
			
			String fecIniVigenString = null;
			if (null != fecIniVigencia)
			{
				fecIniVigenString = sdf.format(fecIniVigencia);
			}
			
			String fecFinVigenString = null;
			if (null != fecFinVigencia)
			{
				fecFinVigenString = sdf.format(fecFinVigencia);
			}
			
			if(logger.isDebugEnabled()){
				logger.debug("\nfecEmisionString : " + fecEmisionString +
						"\nfecIniVigenString : " + fecIniVigenString + 
						"\nfecFinVigenString : " + fecFinVigenString);
			}
			
			String nombreCompletoPropietario = nombrePropietario + " " + apePaternoPropietario + " " + apeMaternoPropietario;
			
			documentoPDF= generarPDFSEI.generarPDF(nroPoliza, docIdentidadPropietario, nombreCompletoPropietario, apePaternoPropietario, apeMaternoPropietario, fecIniVigenString, fecFinVigenString, costoPlan,
					nroPlaca, nombreMarcaVehiculo, nombreModeloVehiculo, anioFab, nroSerie, nombreCategoriaClase, nombreUsoVehiculo, nroAsientos, fecEmisionString, distritoPropietario, 
					provinciaPropietario, departamentoPropietario, horaEmision, telefonoPropietario, direccionPropietario);
		}
		catch (Exception e)
		{
			logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
			logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
			throw new SyncconException(ErrorConstants.COD_ERROR_GENERAR_PDF_POLIZA);
		}
		if (logger.isInfoEnabled()) {logger.info("Fin");}
		return documentoPDF;
	} //generarPolizaPDF
	
} //PolizaGeneratorHandler
