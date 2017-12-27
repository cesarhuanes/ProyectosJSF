
package com.proyecto.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.proyecto.dao.ClienteDao;
import com.proyecto.pojos.Cliente;

@ManagedBean(name="clienteController")
@ApplicationScoped
public class ClienteController {
	private static final Logger logger = Logger.getLogger(ClienteController.class.getName());
	private List<SelectItem> listaEstados;
	private Cliente cliente;
	private ClienteDao clienteDao;


	@PostConstruct
	public void init() {
		cliente = new Cliente();
		clienteDao=new ClienteDao();
		listaCliente();
		cantidadClientes();
	
		listaEstados = new ArrayList<SelectItem>();
		listaEstados.add(new SelectItem("0", "Seleccionar"));
		listaEstados.add(new SelectItem("1", "Activo"));
		listaEstados.add(new SelectItem("2", "Inactivo"));
	}
	public String  editarCliente(){
		String resultado = "";
		FacesContext context = FacesContext.getCurrentInstance();
		String codigo = context.getExternalContext().getRequestParameterMap().get("codigo");
		System.out.println("codigo==>" + codigo);
		if (codigo != null) {
			cliente = clienteDao.obtenerCliente(Integer.parseInt(codigo));
			resultado = "actualizaCliente";
		}
		return resultado;
	}
	public void eliminarCliente(){
		FacesContext context = FacesContext.getCurrentInstance();
		String codigo = context.getExternalContext().getRequestParameterMap().get("codigo");
		System.out.println("codigo==>" + codigo);
		if (codigo != null) {

		}
	}
	
	public int cantidadClientes(){
	return 	clienteDao.listaClientes().size();
	}

	public List<Cliente> listaCliente(){
		return clienteDao.listaClientes();
	}
	@SuppressWarnings("unused")
	public String saveCliente() {
		logger.info("ingreso a grabar");
		boolean flag = false;
		clienteDao = new ClienteDao();
		flag = clienteDao.insertarClientes(cliente);
		return "";
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

}
