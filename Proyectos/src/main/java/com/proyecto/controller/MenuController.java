/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.proyecto.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.proyecto.dao.MenuDao;
import com.proyecto.pojos.Menu;

@ManagedBean(name = "menu")
public class MenuController {
	private static final Logger logger = Logger.getLogger(MenuController.class.getName());
	private String valor = "";
	private List<Menu> listaOpciones;
	PanelMenuController panelMenuController;

	@PostConstruct
	void init() {
		panelMenuController = new PanelMenuController();
		panelMenuController.getUrl();
	}

	/**
	 * @return the listaOpciones
	 */
	public List<Menu> getListaOpciones() {
		List<Menu> lista = null;
		MenuDao menu = new MenuDao();
		lista = menu.listaOpciones();
		setListaOpciones(lista);
		return listaOpciones;
	}

	public void cerrarSesion() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}

	/**
	 * @param listaOpciones
	 *            the listaOpciones to set
	 */
	public void setListaOpciones(List<Menu> listaOpciones) {
		this.listaOpciones = listaOpciones;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

}
