package com.proyecto.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.proyecto.dao.ProyectoDao;
import com.proyecto.pojos.Cliente;
import com.proyecto.pojos.Estado;
import com.proyecto.pojos.LugarTrabajo;
import com.proyecto.pojos.Proyecto;
import com.proyecto.pojos.TipoMoneda;
import com.proyecto.pojos.TipoTrabajo;
import com.proyecto.pojos.Usuario;
import com.proyecto.util.Constantes;

@ManagedBean(name="proyectoController")
@ApplicationScoped
public class ProyectoController {
	public Proyecto proyecto;
	private List<Proyecto> listaProyecto;
	private ProyectoDao proyectoDao;
	private List<SelectItem> listaCliente;
	private List<SelectItem> listaResponsable;
	private List<SelectItem> listaTipoMoneda;
	private List<SelectItem> listaTipoTrabajo;
	private List<SelectItem> listaLugarTrabajo;
	private List<SelectItem> listaEstado;
	private int codigoProyecto;
	private int tipoTransaccion = 0;// 0 insertar ,1 actualizar

	@PostConstruct
	public void init() {
		proyecto=new Proyecto();
		proyectoDao = new ProyectoDao();
		getLstProyecto();
		
	}

	private void getLstCliente() {
		listaCliente = new ArrayList<SelectItem>();
		listaCliente.add(new SelectItem("", "Seleccionar"));
		List<Cliente> lstCliente = proyectoDao.listaClientes();
		for (Cliente x : lstCliente) {
			listaCliente.add(new SelectItem(x.getCodigo(), x.getNombreCliente()));
		}
		
	}

	private void getLstResponsable() {
		listaResponsable = new ArrayList<SelectItem>();
		listaResponsable.add(new SelectItem("", "Seleccionar"));
		List<Usuario> lstUsuario = proyectoDao.listaResponsables();
		for (Usuario x : lstUsuario) {
			listaResponsable.add(
					new SelectItem(x.getIdUsuario(), x.getApPaterno() + " " + x.getApMaterno()+" "+ x.getNombres()  ));
		}
		
	}

	private void getLstTipoMoneda() {
		listaTipoMoneda = new ArrayList<SelectItem>();
		listaTipoMoneda.add(new SelectItem("", "Seleccionar"));
		List<TipoMoneda> lstTipoMoneda = proyectoDao.listaTipoMoneda();
		for (TipoMoneda x : lstTipoMoneda) {
			listaTipoMoneda.add(new SelectItem(x.getIdTipoMoneda(), x.getDescripcion()));
		}
		
	}

	private void  getLstTipoTrabajo() {
		listaTipoTrabajo = new ArrayList<SelectItem>();
		listaTipoTrabajo.add(new SelectItem("", "Seleccionar"));
		List<TipoTrabajo> lstTipoTrabajo = proyectoDao.listaTipoTrabajo();
		for (TipoTrabajo x : lstTipoTrabajo) {
			listaTipoTrabajo.add(new SelectItem(x.getIdTipoTrabajo(), x.getDescripcion()));
		}
	
	}

	private void getLstLugarTrabajo() {
		listaLugarTrabajo = new ArrayList<SelectItem>();
		listaLugarTrabajo.add(new SelectItem("", "Seleccionar"));
		List<LugarTrabajo> lstLugarTrabajo = proyectoDao.listaLugarTrabajo();
		for (LugarTrabajo x : lstLugarTrabajo) {
			listaLugarTrabajo.add(new SelectItem(x.getIdLugarTrabajo(), x.getDescripcion()));
		}
		
	}
	private void getLstEstado() {
		listaEstado = new ArrayList<SelectItem>();
		listaEstado.add(new SelectItem("", "Seleccionar"));
		List<Estado> lstEstado = proyectoDao.listaEstadosProyecto();
		for (Estado x : lstEstado) {
			listaEstado.add(new SelectItem(x.getIdEstado(), x.getDescripcion()));
		}
	
	}
	public String nuevoProyecto(){
		String resultado="mantProyectos";
		proyecto=new Proyecto();
		proyectoDao = new ProyectoDao();
		getLstCliente();
		getLstResponsable();
		getLstTipoMoneda();
		getLstTipoTrabajo();
		getLstLugarTrabajo();
		getLstEstado();
		return resultado;
	}
	
	private List<Proyecto> getLstProyecto(){
		listaProyecto=proyectoDao.listaProyecto();
		return listaProyecto;
	}
	/**
	 * @return the listaProyecto
	 */
	public List<Proyecto> getListaProyecto() {
		return listaProyecto;
	}
	/**
	 * @param listaProyecto the listaProyecto to set
	 */
	public void setListaProyecto(List<Proyecto> listaProyecto) {
		this.listaProyecto = listaProyecto;
	}
	/**
	 * @return the listaCliente
	 */
	public List<SelectItem> getListaCliente() {
		return listaCliente;
	}
	/**
	 * @param listaCliente the listaCliente to set
	 */
	public void setListaCliente(List<SelectItem> listaCliente) {
		this.listaCliente = listaCliente;
	}
	/**
	 * @return the listaResponsable
	 */
	public List<SelectItem> getListaResponsable() {
		return listaResponsable;
	}
	/**
	 * @param listaResponsable the listaResponsable to set
	 */
	public void setListaResponsable(List<SelectItem> listaResponsable) {
		this.listaResponsable = listaResponsable;
	}
	/**
	 * @return the listaTipoMoneda
	 */
	public List<SelectItem> getListaTipoMoneda() {
		return listaTipoMoneda;
	}
	/**
	 * @param listaTipoMoneda the listaTipoMoneda to set
	 */
	public void setListaTipoMoneda(List<SelectItem> listaTipoMoneda) {
		this.listaTipoMoneda = listaTipoMoneda;
	}
	/**
	 * @return the listaTipoTrabajo
	 */
	public List<SelectItem> getListaTipoTrabajo() {
		return listaTipoTrabajo;
	}
	/**
	 * @param listaTipoTrabajo the listaTipoTrabajo to set
	 */
	public void setListaTipoTrabajo(List<SelectItem> listaTipoTrabajo) {
		this.listaTipoTrabajo = listaTipoTrabajo;
	}
	/**
	 * @return the listaLugarTrabajo
	 */
	public List<SelectItem> getListaLugarTrabajo() {
		return listaLugarTrabajo;
	}
	/**
	 * @param listaLugarTrabajo the listaLugarTrabajo to set
	 */
	public void setListaLugarTrabajo(List<SelectItem> listaLugarTrabajo) {
		this.listaLugarTrabajo = listaLugarTrabajo;
	}

	/**
	 * @return the listaEstado
	 */
	public List<SelectItem> getListaEstado() {
		return listaEstado;
	}

	/**
	 * @param listaEstado the listaEstado to set
	 */
	public void setListaEstado(List<SelectItem> listaEstado) {
		this.listaEstado = listaEstado;
	}
	/**
	 * @return the proyecto
	 */
	public Proyecto getProyecto() {
		return proyecto;
	}

	/**
	 * @param proyecto the proyecto to set
	 */
	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	public String saveProyecto(){
		String resultado="listaProyecto";
		boolean flag=false;
		
		String useName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("userName");
		proyecto.setFechaCreacion(new Date(System.currentTimeMillis()));
		proyecto.setUsuarioCreador(useName);
		flag=proyectoDao.insertarProyecto(proyecto);
		if(flag){
			FacesMessage message = new FacesMessage("Transacción con éxito.");
		    FacesContext.getCurrentInstance().addMessage(null, message);
		}
		listaProyecto=proyectoDao.listaProyecto();
		return resultado;
	}

	public int getCodigoProyecto() {
		FacesContext context = FacesContext.getCurrentInstance();
		String codigo = context.getExternalContext().getRequestParameterMap().get("codigo");
		if (codigo != null) {
			codigoProyecto = Integer.parseInt(codigo);
		}
		return codigoProyecto;
	}
	public String editarProyecto(){
		String resultado = "";
		setTipoTransaccion(Constantes.UNO);
		proyecto = new Proyecto();
		proyecto = proyectoDao.obtenerProyecto(getCodigoProyecto());
		resultado = "mantProyectos";
		return resultado;
	}

	public void setCodigoProyecto(int codigoProyecto) {
		this.codigoProyecto = codigoProyecto;
	}

	public int getTipoTransaccion() {
		return tipoTransaccion;
	}

	public void setTipoTransaccion(int tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}
}
