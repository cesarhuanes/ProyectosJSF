package com.cardif.satelite.callcenter.controller;

import static com.cardif.satelite.constantes.ErrorConstants.ERROR_SYNCCON;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR;
import static com.cardif.satelite.constantes.ErrorConstants.MSJ_ERROR_GENERAL;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.richfaces.model.selection.SimpleSelection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.bnpparibas.cardif.offline.ws.schema.offline.v2.Policy;
import com.bnpparibas.cardif.offline.ws.schema.offline.v2.PolicyArray;
import com.bnpparibas.cardif.offline.ws.schema.offline.v2.ThirdParty;
import com.bnpparibas.cardif.offline.ws.schema.offline.v2.ThirdPartyArray;
import com.bnpparibas.cardif.offline.ws.service.offline.v2.WebSocioPortType;
import com.bnpparibas.cardif.offline.ws.service.offline.v2.WebSocioService;
import com.cardif.framework.excepcion.SyncconException;
import com.cardif.satelite.acsele.bean.ConsultaOpenItem;
import com.cardif.satelite.acsele.service.OpenItemService;
import com.cardif.satelite.callcenter.bean.ConsultaTerceroPolizas;
import com.cardif.satelite.callcenter.service.ConsultaWebService;
import com.cardif.satelite.constantes.ErrorConstants;
import com.cardif.satelite.util.PropertiesUtil;

/**
 * @author huamanju
 * 
 */
@Controller("consultaPolizasController")
@Scope("request")
public class ConsultaPolizasController {

  public static final Logger log = Logger.getLogger(ConsultaPolizasController.class);
  @Autowired
  private ConsultaWebService consultaWebService;
  @Autowired
  private OpenItemService acseleOpenItemsService;

  private ThirdParty tercero;
  private String docu;
  private String numPoliza;
  private List<ConsultaTerceroPolizas> listaTerceroPolizas;
  private List<ConsultaOpenItem> listaDetallesPoliza;
  private SimpleSelection selection;
  private Policy policySelect;
  private ThirdPartyArray listaTercero;
  private PolicyArray listaPolizas;
  private String errorDocu;

  private WebSocioPortType port;
  protected URL wsdlUrl;
  protected QName serviceName = null;

  @PostConstruct
  public String inicio() {
    log.info("Inicio");
    listaTerceroPolizas = new ArrayList<ConsultaTerceroPolizas>();
    listaDetallesPoliza = new ArrayList<ConsultaOpenItem>();
    String respuesta = null;
    docu = "";
    setErrorDocu("");

    try {
      port = factoryWebSocioPortType();

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, ErrorConstants.MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = ErrorConstants.MSJ_ERROR;
    }
    log.info("Fin");
    return respuesta;
  }

  private WebSocioPortType factoryWebSocioPortType() throws SyncconException {
    log.info("Inicio");
    WebSocioService service = null;
    try {
      serviceName = new QName("http://offline.cardif.bnpparibas.com/ws/service/offline/v2", "webSocioService");

      // wsdlUrl = new URL("file:/C:/wsdl/websocio-service.wsdl");//1ra
      // forma: ruta PC

      // 2da forma: ruta en proyecto
      FacesContext fc = FacesContext.getCurrentInstance();
      ServletContext sc = (ServletContext) fc.getExternalContext().getContext();
      String ruta = sc.getRealPath(File.separator + "wsdl" + File.separator + "websocio-service-" + PropertiesUtil.getProperty("WS") + ".wsdl");

      // String wsdlRuta = ruta.replace('\'', '/');
      log.debug("Ruta Archivo: " + ruta);

      wsdlUrl = new File(ruta).toURI().toURL();
      log.debug("Ruta URL: " + wsdlUrl);

      service = new WebSocioService(wsdlUrl, serviceName);

    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new SyncconException(ErrorConstants.COD_ERROR_WS_ACSELE);
    }
    log.info("Fin");

    return service.getWebSocioPort();
  }

  public String buscarTerceroPolizas() {
    log.info("Inicio");
    String respuesta = null;
    try {
      validaDocu();
      if (getErrorDocu().trim().isEmpty()) {
        limpiarDetallesPoliza();

        port = factoryWebSocioPortType();

        listaTercero = consultaWebService.findThirdPartyByDocumentNumber(port, docu);

        if (listaTercero != null && listaTercero.getItem().size() > 0) {
          tercero = listaTercero.getItem().get(0);
          String numdoc = tercero.getCodeIdentifier();
          String asegurado = tercero.getName();

          long id = tercero.getId();

          listaPolizas = consultaWebService.findPolicyByThirdPartyTptId(port, id);
          String estado, contratante, producto, numPoliza, idPoliza = "";
          Date fec_inicio, fec_fin = null;

          if (listaPolizas.getItem().size() > 0) {

            for (int i = 0; i < listaPolizas.getItem().size(); i++) {
              estado = listaPolizas.getItem().get(i).getProperties().getProperty().get(0).getValue();
              contratante = listaPolizas.getItem().get(i).getProperties().getProperty().get(2).getValue();
              producto = listaPolizas.getItem().get(i).getProperties().getProperty().get(3).getValue();
              numPoliza = listaPolizas.getItem().get(i).getPolicyNumber();
              fec_inicio = listaPolizas.getItem().get(i).getInitialDate().toGregorianCalendar().getTime();
              fec_fin = listaPolizas.getItem().get(i).getFinalDate().toGregorianCalendar().getTime();
              idPoliza = listaPolizas.getItem().get(i).getID();
              listaTerceroPolizas.add(new ConsultaTerceroPolizas(numdoc, asegurado, estado, contratante, producto, numPoliza, fec_inicio, fec_fin, idPoliza));
            }

          } else {
            throw new SyncconException(ErrorConstants.COD_ERROR_DOCU_WS_ACSELE, FacesMessage.SEVERITY_INFO);
          }

        } else {
          throw new SyncconException(ErrorConstants.COD_ERROR_DOCU_WS_ACSELE, FacesMessage.SEVERITY_INFO);
        }

      }

    } catch (SyncconException ex) {
      log.error(ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }

    log.info("Fin");

    return respuesta;

  }

  public void validaDocu() {
    log.info("Inicio");
    setErrorDocu("");
    if (docu.trim().isEmpty()) {
      setErrorDocu("Obligatorio");
      limpiar();
    }
    log.info("Fin");
  }

  public String cargaDetallesPoliza() {
    log.info("Inicio");
    String respuesta = null;
    try {
      Iterator<Object> iterator = getSelection().getKeys();
      if (iterator.hasNext()) {
        int key = (Integer) iterator.next();
        String idPoliza1 = listaTerceroPolizas.get(key).getIdPoliza();
        BigDecimal idPoliza = new BigDecimal(idPoliza1);
        numPoliza = listaTerceroPolizas.get(key).getNumPoliza();
        listaDetallesPoliza = acseleOpenItemsService.cargaDetallesPoliza(idPoliza, numPoliza);

        // cargaDetallesPoliza_conWS(idPoliza1);

      }
      if (listaDetallesPoliza.size() == 0) {
        throw new SyncconException(ErrorConstants.COD_ERROR_POLIZA_WS_ACSELE, FacesMessage.SEVERITY_INFO);
      }

    } catch (SyncconException ex) {
      log.error(ERROR_SYNCCON + ex.getMessageComplete());
      FacesMessage facesMsg = new FacesMessage(ex.getSeveridad(), ex.getMessage(), null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, MSJ_ERROR_GENERAL, null);
      FacesContext.getCurrentInstance().addMessage(null, facesMsg);
      respuesta = MSJ_ERROR;
    }

    log.info("Fin");
    return respuesta;
  }

  private void limpiarDetallesPoliza() {
    log.info("Inicio");
    selection = new SimpleSelection();
    listaTerceroPolizas = new ArrayList<ConsultaTerceroPolizas>();
    listaDetallesPoliza = new ArrayList<ConsultaOpenItem>();

    FacesContext context = FacesContext.getCurrentInstance();
    Application application = context.getApplication();
    ViewHandler viewHandler = application.getViewHandler();
    UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
    context.setViewRoot(viewRoot);
    context.renderResponse(); // Optional

    log.info("Fin");
  }

  public void limpiar() {
    log.info("Inicio");
    limpiarDetallesPoliza();
    docu = "";
    log.info("Fin");
  }

  public SimpleSelection getSelection() {
    return selection;
  }

  public void setSelection(SimpleSelection selection) {
    this.selection = selection;
  }

  public ThirdParty getTercero() {
    return tercero;
  }

  public void setTercero(ThirdParty tercero) {
    this.tercero = tercero;
  }

  public List<ConsultaTerceroPolizas> getListaTerceroPolizas() {
    return listaTerceroPolizas;
  }

  public void setListaTerceroPolizas(List<ConsultaTerceroPolizas> listaTerceroPolizas) {
    this.listaTerceroPolizas = listaTerceroPolizas;
  }

  public String getDocu() {
    return docu;
  }

  public void setDocu(String docu) {
    this.docu = docu;
  }

  public Policy getPolicySelect() {
    return policySelect;
  }

  public void setPolicySelect(Policy policySelect) {
    this.policySelect = policySelect;
  }

  public List<ConsultaOpenItem> getListaDetallesPoliza() {
    return listaDetallesPoliza;
  }

  public void setListaDetallesPoliza(List<ConsultaOpenItem> listaDetallesPoliza) {
    this.listaDetallesPoliza = listaDetallesPoliza;
  }

  public String getNumPoliza() {
    return numPoliza;
  }

  public void setNumPoliza(String numPoliza) {
    this.numPoliza = numPoliza;
  }

  public String getErrorDocu() {
    return errorDocu;
  }

  public void setErrorDocu(String errorDocu) {
    this.errorDocu = errorDocu;
  }

}
