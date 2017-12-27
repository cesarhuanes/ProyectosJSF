package com.proyecto.test;

import com.proyecto.dao.ClienteDao;

public class Test {

	public static void main(String[] args) {
		  ClienteDao cliente=new ClienteDao();
	      /*Cliente clientes=new Cliente();
	      clientes.setNombreCliente("ABB");
	      clientes.setNombreContacto("CESAR HUANES");
	      clientes.setRuc("10417542148");
	      clientes.setTelefono("941383163");
	      clientes.setEmail("cesarhuanes@gmail.com");
	      clientes.setEstado(1);
	      cliente.insertarClientes(clientes);*/
	      cliente.listaClientes();
	}

}
