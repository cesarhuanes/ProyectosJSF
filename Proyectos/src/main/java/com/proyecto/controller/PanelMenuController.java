
package com.proyecto.controller;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean(name="panelMenu")
@SessionScoped
public class PanelMenuController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(PanelMenuController.class.getName());
	private String url = "/comun/cuerpo.xhtml";
	
	@PostConstruct
	public void init(){
		
	}
	
 	public String updateCurrent(){
		FacesContext context =FacesContext.getCurrentInstance();
		setUrl(context.getExternalContext().getRequestParameterMap().get("url"));
		System.out.println("URL:  >>>"+url);
		return "/plantillas/layout.xhtml";
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

}
