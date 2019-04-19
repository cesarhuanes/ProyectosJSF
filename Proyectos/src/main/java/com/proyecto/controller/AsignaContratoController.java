package com.proyecto.controller;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;

import org.primefaces.context.RequestContext;

@ManagedBean(name="asignaContratoController")
public class AsignaContratoController {
	
	public void viewAsignaContrato(){
		 Map<String,Object> options = new HashMap<String, Object>();
	        options.put("draggable", false);
	        options.put("modal", true);
	        options.put("resizable", false);
	        options.put("closeOnEscape", true);
	        options.put("showEffect", "scale");
	        options.put("closable", true);
	        options.put("width", 870);
	        options.put("height", 500);
	       
	         
	        RequestContext.getCurrentInstance().openDialog("/paginas/usuarios/asignarContrato.xhtml", options, null);
		
	}
	
}
