package com.proyecto.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.proyecto.dao.UsuarioDao;
import com.proyecto.pojos.Estado;
import com.proyecto.pojos.Perfil;
import com.proyecto.pojos.TipoDocumento;
import com.proyecto.pojos.Usuario;
import com.proyecto.util.Constantes;

@ManagedBean(name="usuarioController")
@ApplicationScoped
public class UsuarioController {
	private static final Logger logger = Logger.getLogger(UsuarioController.class.getName());
	private UsuarioDao usuarioDao;
	private List<Usuario> filterListUsuario;
	private int codigoUsuario;
	private int tipoTransaccion = 0;
	private Usuario usuario;
	private List<SelectItem> listaTipoDocumento;
	private List<SelectItem> listaPerfil;
	private List<SelectItem> listaEstados;
	public boolean isDisableInputText;
	public List<Usuario> listaUsuario;
	
	
	@PostConstruct
	public void init() {
		usuarioDao = new UsuarioDao();
		lstUsuario();
		filterListUsuario = new ArrayList<Usuario>();
		listaTipoDocumento();
		listaPerfil();
		listaEstados();
		setDisableInputText(false);

	}

	private void listaTipoDocumento(){
		listaTipoDocumento=new ArrayList<SelectItem>();
		listaTipoDocumento.add(new SelectItem("","Seleccionar"));
		List<TipoDocumento> lstTipoDocumento=usuarioDao.listaTipoDocumento();
		for(TipoDocumento item:lstTipoDocumento){
			listaTipoDocumento.add(new SelectItem(item.getIdTipoDocumento(), item.getDescripcion()));
		}
	}
	
	private void listaPerfil(){
		listaPerfil = new ArrayList<SelectItem>();
		listaPerfil.add(new SelectItem("", "Seleccionar"));
		List<Perfil> lstPerfil = usuarioDao.listaPerfil();
		for (Perfil item : lstPerfil) {
			listaPerfil.add(new SelectItem(item.getIdPerfil(), item.getDescripcion()));

		}
	}
	
	private void listaEstados(){
		listaEstados = new ArrayList<SelectItem>();
		listaEstados.add(new SelectItem("", "Seleccionar"));
		List<Estado> lstEstado = usuarioDao.listaEstados();
		for (Estado item : lstEstado) {
			listaEstados.add(new SelectItem(item.getIdEstado(), item.getDescripcion()));
		}
	}

	public String updateUsuario() {
		String resultado = "";
		setTipoTransaccion(Constantes.UNO);
		usuario = new Usuario();
		setDisableInputText(false);
		usuario = usuarioDao.getUserByPrimaryKey(getCodigoUsuario());
		resultado = "updateUsuario";
		return resultado;
	}

	public String verMasUsuario() {
		String resultado = "";
		setTipoTransaccion(Constantes.DOS);
		usuario = new Usuario();
		usuario = usuarioDao.getUserByPrimaryKey(getCodigoUsuario());
		setDisableInputText(true);
		resultado = "updateUsuario";
		return resultado;
	}

	public String nuevoUsuario() {
		usuario = new Usuario();
		setTipoTransaccion(Constantes.CERO);
		setDisableInputText(false);
		return "updateUsuario";
	}

	public String saveUsuario() {
		String resultado = "listaUsuario";
		boolean flag = false;
		String userName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("userName");
		if (getTipoTransaccion() == Constantes.CERO) {
			usuario.setUsuarioCreador(userName);
			usuario.setFechaCreacion(new Date(System.currentTimeMillis()));
			flag = usuarioDao.insertarUsuario(usuario);

			if (flag) {
				FacesMessage message = new FacesMessage("Transacción con éxito.");
				FacesContext.getCurrentInstance().addMessage(null, message);
			} else {
				RequestContext.getCurrentInstance()
						.showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error al registrar."));
			}
		} else {
			usuario.setUsuarioModificador(userName);
			usuario.setFechaModificacion(new Date(System.currentTimeMillis()));
			flag = usuarioDao.actualizarUsuario(usuario);
			if (flag) {
				FacesMessage message = new FacesMessage("Registro actualizado.");
				FacesContext.getCurrentInstance().addMessage(null, message);
			} else {
				RequestContext.getCurrentInstance()
						.showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error al actualizar."));
			}
		}
		lstUsuario();
		return resultado;
	}
	

	public int getCodigoUsuario() {
		FacesContext context = FacesContext.getCurrentInstance();
		String codigo = (String) context.getExternalContext().getRequestParameterMap().get("idUsuario");
		if (codigo != null) {
			codigoUsuario = Integer.parseInt(codigo);
		}
		return codigoUsuario;
	}

	public String returnUsuario() {
		return "listaUsuario";
	}

	public List<Usuario> lstUsuario() {
		listaUsuario = usuarioDao.listaUsuario();
		return listaUsuario;
	}

	/**
	 * @return the filterListUsuario
	 */
	public List<Usuario> getFilterListUsuario() {
		return filterListUsuario;
	}

	/**
	 * @param filterListUsuario
	 *            the filterListUsuario to set
	 */
	public void setFilterListUsuario(List<Usuario> filterListUsuario) {
		this.filterListUsuario = filterListUsuario;
	}

	public UsuarioDao getUsuarioDao() {
		return usuarioDao;
	}

	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @param codigoUsuario
	 *            the codigoUsuario to set
	 */
	public void setCodigoUsuario(int codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public int getTipoTransaccion() {
		return tipoTransaccion;
	}

	public void setTipoTransaccion(int tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

	/**
	 * @return the listaTipoDocumento
	 */
	public List<SelectItem> getListaTipoDocumento() {
		return listaTipoDocumento;
	}

	/**
	 * @param listaTipoDocumento
	 *            the listaTipoDocumento to set
	 */
	public void setListaTipoDocumento(List<SelectItem> listaTipoDocumento) {
		this.listaTipoDocumento = listaTipoDocumento;
	}

	/**
	 * @return the listaPerfil
	 */
	public List<SelectItem> getListaPerfil() {
		return listaPerfil;
	}

	/**
	 * @param listaPerfil
	 *            the listaPerfil to set
	 */
	public void setListaPerfil(List<SelectItem> listaPerfil) {
		this.listaPerfil = listaPerfil;
	}

	/**
	 * @return the listaEstados
	 */
	public List<SelectItem> getListaEstados() {
		return listaEstados;
	}

	/**
	 * @param listaEstados
	 *            the listaEstados to set
	 */
	public void setListaEstados(List<SelectItem> listaEstados) {
		this.listaEstados = listaEstados;
	}

	/**
	 * @return the listaUsuario
	 */
	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	/**
	 * @param listaUsuario
	 *            the listaUsuario to set
	 */
	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public boolean isDisableInputText() {
		return isDisableInputText;
	}

	public void setDisableInputText(boolean isDisableInputText) {
		this.isDisableInputText = isDisableInputText;
	}

	
}
