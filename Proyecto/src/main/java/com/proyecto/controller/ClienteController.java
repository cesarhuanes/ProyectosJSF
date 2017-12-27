
package com.proyecto.controller;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import com.proyecto.pojos.Cliente;
import java.io.Serializable;

@ManagedBean(name="clienteController")
@ApplicationScoped
public class ClienteController implements Serializable {
    private List<SelectItem> listaEstados;
    private Cliente cliente;
    
    public ClienteController(){
        cliente=new Cliente();
        listaEstados=new ArrayList<SelectItem>();
        listaEstados.add(new SelectItem("0","Seleccionar"));
        listaEstados.add(new SelectItem("1","Activo"));
        listaEstados.add(new SelectItem("2","Inactivo"));
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

    /**
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    

    
    
}
