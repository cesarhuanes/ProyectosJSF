package com.cardif.satelite.tesoreria.service.impl;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.tesoreria.bean.ProveedorBean;
import com.cardif.satelite.tesoreria.handler.PayloadHandler;
import com.cardif.satelite.tesoreria.service.ProveedorService;
import com.cardif.sunsystems.mapeo.supplier.SSC;
import com.cardif.sunsystems.services.SunComponenteService;
import com.cardif.sunsystems.util.ConstantesSun;

@Service("proveedorService")
public class ProveedorServiceImpl implements ProveedorService {
  public static final Logger logger = Logger.getLogger(ProveedorServiceImpl.class);

  @Override
  public ProveedorBean buscarProveedor(String unidadNegocio, String codProveedor, String rucProveedor) throws SyncconException {
    if (logger.isInfoEnabled()) {
      logger.info("Inicio");
    }
    ProveedorBean proveedorBean = null;

    try {
      SunComponenteService sunComponent = new SunComponenteService();
      PayloadHandler payloadHandler = PayloadHandler.newInstance();

      String payload = payloadHandler.generarPayloadProveedor(unidadNegocio, codProveedor, rucProveedor);
      if (logger.isDebugEnabled()) {
        logger.debug("Payload generado para consultar: " + payload);
      }

      String resultadoXML = sunComponent.ejecutaConsulta(ConstantesSun.SSC_ComponenteSupplier, ConstantesSun.SSC_MetodoQuery, payload);
      if (logger.isInfoEnabled()) {
        logger.info("Resultado en formato XML: \n" + resultadoXML);
      }

      // Realizando el UNMARSHALLER de la respuesta
      JAXBContext jc = JAXBContext.newInstance(com.cardif.sunsystems.mapeo.supplier.SSC.class);
      Unmarshaller unmarshaller = jc.createUnmarshaller();

      StringReader reader = new StringReader(resultadoXML);
      com.cardif.sunsystems.mapeo.supplier.SSC proveedor = (com.cardif.sunsystems.mapeo.supplier.SSC) unmarshaller.unmarshal(reader);
      if (logger.isDebugEnabled()) {
        logger.debug("La respuesta XML fue convertida a objeto: " + proveedor);
      }

      if (null != proveedor.getPayload() && null != proveedor.getPayload().getSupplier() && 0 < proveedor.getPayload().getSupplier().size()) {

        if (logger.isInfoEnabled()) {
          logger.info("Transformando la informacion a un bean.");
          logger.info("Cantidad del proveedores encontrados: " + proveedor.getPayload().getSupplier().size());
        }

        for (SSC.Payload.Supplier supplier : proveedor.getPayload().getSupplier()) {
          proveedorBean = new ProveedorBean();
          proveedorBean.setCodigoCtaProveedor(supplier.getAccountCode());
          proveedorBean.setNomProveedor(supplier.getSupplierName());
          proveedorBean.setRuc(supplier.getLookupCode());

          if (null != supplier.getSupplierAddress() && 0 < supplier.getSupplierAddress().size()) {
            proveedorBean.setDepartamento(supplier.getSupplierAddress().get(0).getArea());
            proveedorBean.setDireccion(supplier.getSupplierAddress().get(0).getAddressLine3());
            proveedorBean.setDistrito(supplier.getSupplierAddress().get(0).getAddressLine5());
            proveedorBean.setPais(supplier.getSupplierAddress().get(0).getCountry());
            proveedorBean.setProvincia(supplier.getSupplierAddress().get(0).getState());
            proveedorBean.setCiudad(supplier.getSupplierAddress().get(0).getTownCity());
            proveedorBean.setUbigeo(supplier.getSupplierAddress().get(0).getPostalCode());
            proveedorBean.setUrbanizacion("");
            proveedorBean.setTipoDocIdentidad("");

          }
          proveedorBean.setCorreoElectronico(supplier.getEMailAddress());
        }
      } else {
        logger.error("No se encontro informacion del proveedor.");
      }
    } catch (Exception e) {
      logger.error("Exception(" + e.getClass().getName() + ") - ERROR: " + e.getMessage());
      logger.error("Exception(" + e.getClass().getName() + ") -->" + ExceptionUtils.getStackTrace(e));
    }
    return proveedorBean;
  } // buscarProveedorRetencion

} // ProveedorServiceImpl
