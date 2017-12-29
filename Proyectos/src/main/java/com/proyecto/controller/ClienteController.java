
package com.proyecto.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import com.proyecto.dao.ClienteDao;
import com.proyecto.pojos.Cliente;
import com.proyecto.util.Constantes;

@ManagedBean(name="clienteController")
@ApplicationScoped
public class ClienteController {
	private static final Logger logger = Logger.getLogger(ClienteController.class.getName());
	private List<SelectItem> listaEstados;
	private Cliente cliente;
	private ClienteDao clienteDao;
	private int codigoCliente;
	private int tipoTransaccion = 0;// 0 insertar ,1 actualizar

	@PostConstruct
	public void init() {
		clienteDao = new ClienteDao();
		
		listaCliente();
		cantidadClientes();
		listaEstados = new ArrayList<SelectItem>();
		listaEstados.add(new SelectItem("0", "Seleccionar"));
		listaEstados.add(new SelectItem("1", "Activo"));
		listaEstados.add(new SelectItem("2", "Inactivo"));
	}

	public int cantidadClientes() {
		return clienteDao.listaClientes().size();
	}

	public List<Cliente> listaCliente() {
		return clienteDao.listaClientes();
	}

	public String editarCliente() {
		String resultado = "";
		setTipoTransaccion(Constantes.UNO);
		cliente = new Cliente();
		cliente = clienteDao.obtenerCliente(getCodigoCliente());
		resultado = "actualizaCliente";
		return resultado;
	}

	public void eliminarCliente() {
		boolean flag = false;
		flag = clienteDao.eliminaCliente(getCodigoCliente());
	}
	
	public String nuevoCliente(){
		cliente=new Cliente();
		setTipoTransaccion(Constantes.CERO);
		return "actualizaCliente";
	}

	public String saveCliente() {
		String resultado = "listaCliente";
		boolean flag = false;
		clienteDao = new ClienteDao();
		if (tipoTransaccion == Constantes.CERO) {
			flag = clienteDao.insertarClientes(cliente);
			if(flag){
				FacesMessage message = new FacesMessage("Transacción con éxito.");
			    FacesContext.getCurrentInstance().addMessage(null, message);
			}else{
				RequestContext.getCurrentInstance().showMessageInDialog(new
						FacesMessage(FacesMessage.SEVERITY_ERROR,
						"", "Error al registrar."));
			}
		} else {
			flag = clienteDao.actualizarCliente(cliente);
			if(flag){
				FacesMessage message = new FacesMessage("Registro actualizado.");
			    FacesContext.getCurrentInstance().addMessage(null, message);
			}else{
				RequestContext.getCurrentInstance().showMessageInDialog(new
						FacesMessage(FacesMessage.SEVERITY_ERROR,
						"", "Error al actualizar."));
			}
		}
		return resultado;
	}

	public String returnCliente() {
		return "listaCliente";
	}
	

	public ClienteDao getClienteDao() {
		return clienteDao;
	}

	public void setClienteDao(ClienteDao clienteDao) {
		this.clienteDao = clienteDao;
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
	 * @return the cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * @param cliente
	 *            the cliente to set
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public int getCodigoCliente() {
		FacesContext context = FacesContext.getCurrentInstance();
		String codigo = context.getExternalContext().getRequestParameterMap().get("codigo");
		if (codigo != null) {
			codigoCliente = Integer.parseInt(codigo);
		}
		return codigoCliente;
	}

	public void setCodigoCliente(int codigoCliente) {
		this.codigoCliente = codigoCliente;
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

}
