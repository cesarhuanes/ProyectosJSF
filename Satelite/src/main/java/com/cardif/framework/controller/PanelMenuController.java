package com.cardif.framework.controller;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.ajax4jsf.component.html.Include;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.cardif.satelite.constantes.Constantes;
import com.cardif.satelite.seguridad.controller.LoginController;
import com.cardif.satelite.tesoreria.controller.LoginFirmanteController;
import com.cardif.satelite.tesoreria.controller.LoginSunController;
import com.cardif.satelite.tesoreria.model.Firmante;
import com.cardif.satelite.tesoreria.service.ChequeService;
import com.cardif.sunsystems.util.ConstantesSun;

@Controller("panelMenu")
@Scope("request")
public class PanelMenuController extends BaseController {
	 public static final Logger log = Logger.getLogger(PanelMenuController.class);
	  private String current = "/paginas/blank.xhtml";
	  private boolean singleMode;

	  HttpSession session;

	  private Include include;

	  @Autowired
	  private LoginController loginController;

	  @Autowired
	  private LoginSunController loginSunController;

	  @Autowired
	  private LoginFirmanteController loginFirmanteController;

	  @Autowired
	  private ChequeService chequeService;

	  @Override
	  public String inicio() {
	    log.info("[ Inicio ]");
	    String respuesta = null;
	    if (!tieneAcceso()) {
	      log.debug("[ No tiene los accesos suficientes ]");
	      return "accesoDenegado";
	    }
	    log.info("[ fin ]");
	    return respuesta;
	  }

	  public Include getInclude() {
	    return include;
	  }

	  public void setInclude(Include include) {
	    this.include = include;
	  }

	  public void reset() {
	    include.setViewId("/paginas/blank.xhtml");
	  }

	  public boolean isSingleMode() {
	    return singleMode;
	  }

	  public void setSingleMode(boolean singleMode) {
	    this.singleMode = singleMode;
	  }

	  public PanelMenuController() {
	  }

	  public String getCurrent() {
	    return this.current;
	  }

	  public void setCurrent(String current) {
	    this.current = current;
	  }

	  public String updateCurrent() {
	    FacesContext context = FacesContext.getCurrentInstance();
	    setCurrent(context.getExternalContext().getRequestParameterMap().get("current"));
	    String opcion = context.getExternalContext().getRequestParameterMap().get("opcion");
	    session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	    if (session != null) {
	      session.setAttribute("opcion", opcion);
	    }

	    log.info("### OPCION SELECCIONADA ###" + session.getAttribute("opcion"));

	    /* INICIO JARIASS SYNCCON 03/03/2015 */
	    
	    if (opcion.equals("915") || opcion.equals("903") || opcion.equals("904") || opcion.equals("905") || opcion.equals("911") || // AM
	        opcion.equals(ConstantesSun.TES_OPCION_MENU_PAGO_BANCARIO) || //AMER Modulo de pagos
	        opcion.equals(ConstantesSun.TES_OPCION_MENU_MODULO_PAGO) || 
	        opcion.equals(ConstantesSun.TES_OPCION_MENU_TRANSFER_ENTRE_CUENTAS) || 
	        opcion.equals(ConstantesSun.TES_OPCION_MENU_COMISION_E_ITF) || 
	        opcion.equals(ConstantesSun.TES_OPCION_MENU_ASIGNACION_FIRMANTES) || // Certificados de Retencion
	        opcion.equals(ConstantesSun.TES_OPCION_MENU_GENERAR_CERT_RETENCION) || 
	        opcion.equals(ConstantesSun.TES_OPCION_MENU_ANULAR_CERT_RETENCION) || 
	        opcion.equals(ConstantesSun.TES_OPCION_MENU_REVERSAR_CERT_RETENCION) ||
	        opcion.equals(ConstantesSun.TES_OPCION_MENU_GENERAR_REPORTE_SBS_MODELO_UNO) ||
	        opcion.equals(ConstantesSun.TES_OPCION_MENU_GENERAR_REPORTE_SBS_MODELO_UNO_LISTADO) 
	    ) {

	      if (!loginSunController.isLoged()) {
	        log.info("No está logueado en SUN");
	        // include.setViewId("accesoDenegado.xhtml");

	        include.setViewId("/paginas/tesoreria/loginSun.xhtml");

	        return null;
	      } else {
	        context.getExternalContext().getSessionMap().put(Constantes.MDP_AUTH_SUNSYSTEMS_TOKEN, loginSunController.getToken());
	      }
	    }

	    if (opcion.equals("912") || opcion.equals("913") || // AM
	        opcion.equals("413002") || opcion.equals("413004") // AMER
	    ) {

	      if (!loginFirmanteController.isLogged()) {

	        String usuario = SecurityContextHolder.getContext().getAuthentication().getName();
	        Firmante firmante = chequeService.buscarFirmante(usuario);
	        if (firmante != null) {
	          if (firmante.getClave() == null) {
	            log.info("El firmante no cuenta con clave, debe configurar una");

	            include.setViewId("/paginas/tesoreria/configurarClave.xhtml");
	          } else {
	            log.info("El firmante no esta logeado");

	            include.setViewId("/paginas/tesoreria/loginFirmante.xhtml");
	          }
	        } else {
	          include.setViewId("accesoDenegado.xhtml");
	        }

	        return null;
	      }
	    }

	    /* FIN JARIASS SYNCCON 03/03/2015 */

	    /* if (tieneAcceso()) { */
	    include.setViewId(current);
	    loginController.setCurrent(current);
	    /*
	     * } else { include.setViewId("accesoDenegado.xhtml"); }
	     */
	    return null;
	  }

	  public String redirigirAVista(String vista) {
	    include.setViewId(vista);
	    return null;
	  }
}
