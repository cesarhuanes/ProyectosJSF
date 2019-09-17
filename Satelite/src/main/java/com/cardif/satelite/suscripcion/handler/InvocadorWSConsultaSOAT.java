package com.cardif.satelite.suscripcion.handler;

//import com.cardif.satelite.suscripcion.wsSass.ConsultaSOATService;
//import com.cardif.satelite.suscripcion.wsSass.ConsultaSOATServiceLocator;
//import com.cardif.satelite.suscripcion.wsSass.ConsultaSOATSoapBindingStub;

public class InvocadorWSConsultaSOAT {
	
	public int consultaSOAT(String nroPlaca){
		
		int rpt = 0;
		String SERVICE_WSDL = null;
		try {
			
			/*Context env = (Context) new InitialContext().lookup("java:comp/env");			
			SERVICE_WSDL = (String) env.lookup("URL_CONSULTASOAT_POLIZA_WS");
			ConsultaSOATService ss = new ConsultaSOATServiceLocator();			
			ss.setConsultaSOATEndpointAddress(SERVICE_WSDL);			
			ConsultaSOATSoapBindingStub stub;	*/		
			//stub = new ConsultaSOATSoapBindingStub(new URL(ss.getConsultaSOATAddress()), ss);
			//rpt = stub.consultaSOATService(nroPlaca);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return rpt;
	}

}
