/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.controller;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.proyecto.pojos.Usuario;

@SuppressWarnings("serial")
@Named
@ViewScoped
public class PlantillaController implements Serializable{
	private static final Logger logger = Logger.getLogger(PlantillaController.class.getName());

	public void verificarSession() {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			Usuario us = (Usuario) context.getExternalContext().getSessionMap().get("usuario");
			if (us == null) {
				context.getExternalContext().redirect("permisos.xhtml");
			}

		} catch (Exception e) {

		}

	}
}
