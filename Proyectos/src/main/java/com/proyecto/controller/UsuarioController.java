package com.proyecto.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.proyecto.dao.UsuarioDao;
import com.proyecto.pojos.Usuario;
import com.proyecto.util.Constantes;

@ManagedBean(name="usuarioController")
@ApplicationScoped
public class UsuarioController {
	private static final Logger logger = Logger.getLogger(UsuarioController.class.getName());
		private UsuarioDao usuarioDao;
		private List<Usuario> filterListUsuario;
		private int codigoUsuario;
		private int tipoTransaccion=0;
		private Usuario usuario;
		private List<SelectItem> listaTipoDocumento;
		private List<SelectItem> listaPerfil;
		private List<SelectItem> listaEstados;
		public boolean isDisableInputText;
	
		public boolean isDisableInputText() {
			return isDisableInputText;
		}
	
		public void setDisableInputText(boolean isDisableInputText) {
			this.isDisableInputText = isDisableInputText;
		}
	
		@PostConstruct
		public void init() {
			usuarioDao = new UsuarioDao();
			listaUsuario();
			filterListUsuario=new ArrayList<Usuario>();
	        listaTipoDocumento();
	        listaPerfil();
	        listaEstados();
	        setDisableInputText(false);
		}
		
		private void listaTipoDocumento(){
			listaTipoDocumento=new ArrayList<SelectItem>();
			listaTipoDocumento.add(new SelectItem("","Seleccionar"));
			listaTipoDocumento.add(new SelectItem("1","DNI"));
			listaTipoDocumento.add(new SelectItem("2","Carnet Ext"));
			listaTipoDocumento.add(new SelectItem("3","Passaporte"));
		}
		
		private void listaPerfil(){
			listaPerfil=new ArrayList<SelectItem>();
			listaPerfil.add(new SelectItem("","Seleccionar"));
			listaPerfil.add(new SelectItem("1","Super Administrador"));
			listaPerfil.add(new SelectItem("2","Administrador"));
			listaPerfil.add(new SelectItem("3","Operador"));
		}
		
		private void listaEstados(){
			listaEstados=new ArrayList<SelectItem>();
			listaEstados.add(new SelectItem("","Seleccionar"));
			listaEstados.add(new SelectItem("1","ACTIVO"));
			listaEstados.add(new SelectItem("2","INACTIVO"));
		}
		public String updateUsuario(){
			String resultado="";
			setTipoTransaccion(Constantes.UNO);
			usuario=new Usuario();
			usuario=usuarioDao.getUserByPrimaryKey(getCodigoUsuario());
			resultado="updateUsuario";
			return resultado;
		}
		public String verMasUsuario(){
			String resultado="";
			setTipoTransaccion(Constantes.DOS);
			usuario=new Usuario();
			usuario=usuarioDao.getUserByPrimaryKey(getCodigoUsuario());
			setDisableInputText(true);
			resultado="updateUsuario";
			return resultado;
		}
		public String nuevoUsuario(){
			usuario=new  Usuario();
			setTipoTransaccion(Constantes.CERO);
			return "updateUsuario";
		}
		public int getCodigoUsuario(){
			FacesContext context=FacesContext.getCurrentInstance();
			String codigo=(String) context.getExternalContext().getRequestMap().get("idUsuario");
			if(codigo!=null){
				codigoUsuario=Integer.parseInt(codigo);
			}
			return codigoUsuario;
		}
	
		public List<Usuario> listaUsuario() {
			return usuarioDao.listaUsuario();
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
		 * @param codigoUsuario the codigoUsuario to set
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
		 * @param listaTipoDocumento the listaTipoDocumento to set
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
		 * @param listaPerfil the listaPerfil to set
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
		 * @param listaEstados the listaEstados to set
		 */
		public void setListaEstados(List<SelectItem> listaEstados) {
			this.listaEstados = listaEstados;
		}
		
}

