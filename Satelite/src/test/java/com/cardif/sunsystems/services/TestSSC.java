package com.cardif.sunsystems.services;

import com.cardif.satelite.tesoreria.service.ComprobanteRetencionService;
import com.cardif.sunsystems.util.Utilidades;

public class TestSSC {
//  @Autowired(required = true)
  private ComprobanteRetencionService comprobanteRetencionService;

  public static void main(String[] args) {
    
    try
    {
    /*  System.out.println("iniciando...");
      List<ComprobanteElectronicoBean> comprobantesRetencion;
      
      String unidadNegocio=ConstantesSun.SSC_BusinessUnit_E02;
      String fechaDesde="31032016";
      String fechaHasta="31032016";
      String proveedorDesde ="42220524445604";
      String proveedorHasta="42220524445604";
      String rucProveedor= "20524445604";
      String nroComprobanteRetencion = "";
      
      ComprobanteRetencionService comprobanteRetencionService = new ComprobanteRetencionServiceImpl();
      System.out.println("buscando....");
//      comprobantesRetencion = comprobanteRetencionService.buscarRetenciones(unidadNegocio, fechaDesde, fechaHasta, proveedorDesde, proveedorHasta, rucProveedor, nroComprobanteRetencion);
//      System.out.println("Tamaño de Lista: "+comprobantesRetencion.size());
      
      */

      String primeraLetraComprobSerie = "F1".substring(0,1);
      String TodoelComprobSeriedesde1 = "F1".substring(1);
      
   // crNroComprobanteSerie[0] = Utilidades.completarCeros(crNroComprobanteSerie[0], "4");      
      String mostrar = primeraLetraComprobSerie + Utilidades.completarCeros(TodoelComprobSeriedesde1, "3");
      System.out.println("varible : " + mostrar);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
   
    
  }

}
