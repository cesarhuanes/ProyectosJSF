
package com.proyecto.controller;

import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.proyecto.dao.UsuarioDao;
import com.proyecto.pojos.Usuario;

@ManagedBean(name="loginController")
@SessionScoped
public class LoginController{
	private static final Logger logger = Logger.getLogger(LoginController.class.getName());
	private String username;
	private String password;
	private String datosPersonales;
	UsuarioDao usuario = null;

	@PostConstruct
	public void init() {
		usuario = new UsuarioDao();
	}

	public String autenticar() {
		ResourceBundle rb = ResourceBundle.getBundle("com.proyecto.recursos.mensajes",
				FacesContext.getCurrentInstance().getViewRoot().getLocale());
		logger.info("Usuario =" + username);
		Usuario usu = usuario.obtenerUsuario(username);
		if (usu != null) {
			logger.info("Usuario 2=" + usu.getUsuario());
			logger.info("Password=" + usu.getPassword());
			if (usu.getUsuario().equals(username) && usu.getPassword().equals(password)) {
				FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("userName", usu.getUsuario());
				setDatosPersonales(usu.getNombres().concat(" " + usu.getApPaterno()));
				//return "principal";
				return "/plantillas/layout";
			} else {
				FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO,
						rb.getString("validacion_login_incorrecto"),
						rb.getString("validacion_login_incorrecto_detalle"));
				FacesContext.getCurrentInstance().addMessage(null, fm);
				return null;
			}
		} else {
			FacesMessage fm = new FacesMessage(FacesMessage.SEVERITY_INFO, rb.getString("validacion_login_incorrecto"),
					rb.getString("validacion_login_incorrecto_detalle"));
			FacesContext.getCurrentInstance().addMessage(null, fm);
			return null;
		}
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the datosPersonales
	 */
	public String getDatosPersonales() {
		return datosPersonales;
	}

	/**
	 * @param datosPersonales
	 *            the datosPersonales to set
	 */
	public void setDatosPersonales(String datosPersonales) {
		this.datosPersonales = datosPersonales;
	}
}
