
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

import com.proyecto.dao.ClienteDao;
import com.proyecto.pojos.Cliente;
import com.proyecto.pojos.Estado;
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
	public List<Cliente> listaCliente;

	@PostConstruct
	public void init() {
		clienteDao = new ClienteDao();

		lstCliente();
		cantidadClientes();
		listaEstados = new ArrayList<SelectItem>();
		listaEstados.add(new SelectItem("", "Seleccionar"));
		List<Estado> lstEstado=clienteDao.listaEstados();
		for(Estado item:lstEstado){
		listaEstados.add(new SelectItem(item.getIdEstado(), item.getDescripcion()));
		
		}
	}

	public int cantidadClientes() {
		return clienteDao.listaClientes().size();
	}

	public List<Cliente> lstCliente() {
		listaCliente = clienteDao.listaClientes();
		return listaCliente;
	}

	public String editarCliente() {
		String resultado = "";
		setTipoTransaccion(Constantes.UNO);
		cliente = new Cliente();
		cliente = clienteDao.obtenerCliente(getCodigoCliente());
		resultado = "actualizaCliente";
		return resultado;
	}

	public String eliminarCliente() {
		String resultado = "listaCliente";
		boolean eliminaCliente = false;
		eliminaCliente = clienteDao.eliminaCliente(getCodigoCliente());
		if (eliminaCliente) {
			FacesMessage message = new FacesMessage("Registro Eliminado.");
			FacesContext.getCurrentInstance().addMessage(null, message);
		} else {
			RequestContext.getCurrentInstance()
					.showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error al registrar."));
		}
		listaCliente= clienteDao.listaClientes();
		return resultado;
	}

	public String nuevoCliente() {
		cliente = new Cliente();
		setTipoTransaccion(Constantes.CERO);
		return "actualizaCliente";
	}

	public String saveCliente() {
		String resultado = "listaCliente";
		boolean flag = false;
		clienteDao = new ClienteDao();
		String useName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("userName");
		if (tipoTransaccion == Constantes.CERO) {
			cliente.setUsuarioCreador(useName);
			cliente.setFechaCreacion(new Date(System.currentTimeMillis()));
			flag = clienteDao.insertarClientes(cliente);
			if (flag) {
				FacesMessage message = new FacesMessage("Transacción con éxito.");
				FacesContext.getCurrentInstance().addMessage(null, message);
			} else {
				RequestContext.getCurrentInstance()
						.showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error al registrar."));
			}
		} else {
			cliente.setUsuarioModificador(useName);
			cliente.setFechaModificacion(new Date(System.currentTimeMillis()));
			flag = clienteDao.actualizarCliente(cliente);
			if (flag) {
				FacesMessage message = new FacesMessage("Registro actualizado.");
				FacesContext.getCurrentInstance().addMessage(null, message);
			} else {
				RequestContext.getCurrentInstance()
						.showMessageInDialog(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Error al actualizar."));
			}
		}
		listaCliente= clienteDao.listaClientes();
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

	/**
	 * @return the listaCliente
	 */
	public List<Cliente> getListaCliente() {
		return listaCliente;
	}

	/**
	 * @param listaCliente
	 *            the listaCliente to set
	 */
	public void setListaCliente(List<Cliente> listaCliente) {
		this.listaCliente = listaCliente;
	}
}
