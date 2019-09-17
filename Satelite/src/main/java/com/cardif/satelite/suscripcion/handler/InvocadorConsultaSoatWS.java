package com.cardif.satelite.suscripcion.handler;

import javax.naming.Context;
import javax.naming.InitialContext;

import com.cardif.framework.excepcion.SyncconException;

public class InvocadorConsultaSoatWS {

	public int callWS(String nroPlaca) throws SyncconException, Exception{
		int rpt = 0;
		String SERVICE_WSDL = null;
		
			Context env = (Context) new InitialContext().lookup("java:comp/env");			
			SERVICE_WSDL = (String) env.lookup("URL_CONSULTASOAT_POLIZA_WS"); 
			
			com.cardif.satelite.suscripcion.wsSass.ServicioConsultaSoatStub stub = 
					new com.cardif.satelite.suscripcion.wsSass.ServicioConsultaSoatStub(SERVICE_WSDL);   
			
			com.cardif.satelite.suscripcion.wsSass.ServicioConsultaSoatStub.ConsultaSOATService request = 
					new com.cardif.satelite.suscripcion.wsSass.ServicioConsultaSoatStub.ConsultaSOATService();
			
			request.setNroPlaca(nroPlaca);
			
			com.cardif.satelite.suscripcion.wsSass.ServicioConsultaSoatStub.ConsultaSOATServiceResponse response = 
																			stub.consultaSOATService(request);
			
			rpt = response.get_return();
		
			return rpt;
		
	}

}
