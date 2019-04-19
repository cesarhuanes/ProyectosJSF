package com.proyecto.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import com.proyecto.dao.HistorialContratoDao;
import com.proyecto.pojos.Estado;
import com.proyecto.pojos.HistorialContrato;
import com.proyecto.pojos.Rol;
import com.proyecto.pojos.TipoContrato;
import com.proyecto.pojos.Usuario;
import com.proyecto.util.Constantes;

@ManagedBean(name = "contratoController")
@ApplicationScoped
public class HistorialContratoController implements Serializable{
	private static final long serialVersionUID = -3716119311220681631L;
	private List<SelectItem> listaRol;
	private List<SelectItem> listaTipoContrato;
	private List<HistorialContrato> listaHistorial;
	private List<SelectItem> listaEstado;
	Usuario usuario;
	HistorialContrato historial;
	HistorialContratoDao historialDao;
	boolean disableInputs = true;
	private int tipoTransaccion = 0;
	private BigDecimal montoTotal;
	 

	@PostConstruct
	public void init() {
		historial = new HistorialContrato();
		historialDao = new HistorialContratoDao();
		getLstRol();
		getLstTipoContrato();
		getLstEstados();
		
	}

	private List<SelectItem> getLstRol() {
		listaRol = new ArrayList<SelectItem>();
		listaRol.add(new SelectItem("", "Seleccionar"));
		List<Rol> lstRol = historialDao.listaRol();
		for (Rol item : lstRol) {
			listaRol.add(new SelectItem(item.getIdRol(), item.getDescripcion()));
		}
		return listaRol;
	}

	private List<SelectItem> getLstTipoContrato() {
		listaTipoContrato = new ArrayList<SelectItem>();
		listaTipoContrato.add(new SelectItem("", "Seleccionar"));
		List<TipoContrato> lstTipoContrato = historialDao.listaTipoContrato();
		for (TipoContrato item : lstTipoContrato) {
			listaTipoContrato.add(new SelectItem(item.getIdTipoContrato(), item.getDescripcion()));
		}
		return listaTipoContrato;
	}

	private List<SelectItem> getLstEstados() {
		listaEstado = new ArrayList<SelectItem>();
		listaEstado.add(new SelectItem("", "Seleccionar"));
		List<Estado> lstEstado = historialDao.listaEstados();
		for (Estado item : lstEstado) {
			listaEstado.add(new SelectItem(item.getIdEstado(), item.getDescripcion()));

		}
		return listaEstado;
	}

	public List<HistorialContrato> getLstHistorial() {
		listaHistorial = historialDao.listaContratoByUser(usuario.getIdUsuario());
		return listaHistorial;
	}

	public String saveContrato(ActionEvent event) {
		Usuario usu = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
		boolean flag = false;
		if (tipoTransaccion == Constantes.CERO) {
			usu.setIdUsuario(usuario.getIdUsuario());
			this.setUsuario(usu);

			historial.setUsuario(this.getUsuario());
			historial.setFechaCreacion(new Date(System.currentTimeMillis()));
			historial.setUsuarioCreador(usu.getUsuario());
			flag = historialDao.insertaHC(historial);
			if (flag) {
				FacesMessage message = new FacesMessage("Transacción con éxito.");
				FacesContext.getCurrentInstance().addMessage(null, message);
			} else {
				RequestContext.getCurrentInstance()
						.showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error al registrar."));
			}
		} else {
			historial.setFechaModificacion(new Date(System.currentTimeMillis()));
			historial.setUsuarioModificador(usu.getUsuario());
			flag = historialDao.actualizarHC(historial);
			if (flag) {
				FacesMessage message = new FacesMessage("Registro actualizado.");
				FacesContext.getCurrentInstance().addMessage(null, message);
			} else {
				RequestContext.getCurrentInstance()
						.showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error al actualizar."));
			}
		}
		getLstHistorial();
		historial=new HistorialContrato();
		montoTotal = new BigDecimal(0).setScale(2);
		return "";
	}
	

	public void selectHC(SelectEvent event) {
		 setDisableInputs(true);
		historial = (HistorialContrato) event.getObject();
		montoTotal = calculaMontoTotal(historial);
	}

	public String editarHC(ActionEvent event) {
		setDisableInputs(false);
		setTipoTransaccion(Constantes.UNO);
		int codigoHc = (int) event.getComponent().getAttributes().get("codigoHC");
		historial = historialDao.obtenerCliente(codigoHc);
		montoTotal = calculaMontoTotal(historial);
		return "";
	}

	public String nuevoHC(ActionEvent event) {
		setDisableInputs(false);
		montoTotal=new BigDecimal(0).setScale(2);
		setTipoTransaccion(Constantes.CERO);
		historial = new HistorialContrato();
		historial.setOtrosCostos(new BigDecimal(0).setScale(2));
		return "";
	}

	
	private BigDecimal calculaMontoTotal(HistorialContrato hc) {
		BigDecimal mt = BigDecimal.ZERO;
		mt = mt.add(hc.getSueldo());
		mt = mt.add(hc.getOtrosCostos());
		return mt;
	}
	public void obtenerUsuario(ActionEvent event){
		setDisableInputs(true);
		usuario=(Usuario) event.getComponent().getAttributes().get("usuario");
		historial=new HistorialContrato();
		getLstHistorial();
	}

	/**
	 * @return the listaRol
	 */
	public List<SelectItem> getListaRol() {
		return listaRol;
	}

	/**
	 * @param listaRol
	 *            the listaRol to set
	 */
	public void setListaRol(List<SelectItem> listaRol) {
		this.listaRol = listaRol;
	}

	/**
	 * @return the listaTipoContrato
	 */
	public List<SelectItem> getListaTipoContrato() {
		return listaTipoContrato;
	}

	/**
	 * @return the listaHistorial
	 */
	public List<HistorialContrato> getListaHistorial() {
		return listaHistorial;
	}

	/**
	 * @param listaHistorial
	 *            the listaHistorial to set
	 */
	public void setListaHistorial(List<HistorialContrato> listaHistorial) {
		this.listaHistorial = listaHistorial;
	}

	/**
	 * @param listaTipoContrato
	 *            the listaTipoContrato to set
	 */
	public void setListaTipoContrato(List<SelectItem> listaTipoContrato) {
		this.listaTipoContrato = listaTipoContrato;
	}

	/**
	 * @return the listaEstado
	 */
	public List<SelectItem> getListaEstado() {
		return listaEstado;
	}

	/**
	 * @param listaEstado
	 *            the listaEstado to set
	 */
	public void setListaEstado(List<SelectItem> listaEstado) {
		this.listaEstado = listaEstado;
	}

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the historial
	 */
	public HistorialContrato getHistorial() {
		return historial;
	}

	/**
	 * @param historial
	 *            the historial to set
	 */
	public void setHistorial(HistorialContrato historial) {
		this.historial = historial;
	}

	/**
	 * @return the disableInputs
	 */
	public boolean isDisableInputs() {
		return disableInputs;
	}

	/**
	 * @param disableInputs
	 *            the disableInputs to set
	 */
	public void setDisableInputs(boolean disableInputs) {
		this.disableInputs = disableInputs;
	}

	/**
	 * @return the tipoTransaccion
	 */
	public int getTipoTransaccion() {
		return tipoTransaccion;
	}

	/**
	 * @param tipoTransaccion
	 *            the tipoTransaccion to set
	 */
	public void setTipoTransaccion(int tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}

	/**
	 * @return the montoTotal
	 */
	public BigDecimal getMontoTotal() {
		return montoTotal;
	}

	/**
	 * @param montoTotal
	 *            the montoTotal to set
	 */
	public void setMontoTotal(BigDecimal montoTotal) {
		this.montoTotal = montoTotal;
	}

	
}
