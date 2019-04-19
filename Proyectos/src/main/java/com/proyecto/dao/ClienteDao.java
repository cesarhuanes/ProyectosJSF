package com.proyecto.dao;

import java.util.List;
import java.util.logging.Logger;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.proyecto.pojos.Cliente;
import com.proyecto.pojos.Estado;
import com.proyecto.util.MyBatisUtil;


public class ClienteDao {
	private static final Logger logger = Logger.getLogger(ClienteDao.class.getName());
	SqlSessionFactory ssf = null;
	SqlSession session = null;

	public ClienteDao() {
		ssf = MyBatisUtil.getSQL_SESSION_FACTORY();
		session = ssf.openSession();
	}
	public Cliente obtenerCliente(Integer codigo) {
		Cliente cli = new Cliente();
		cli = (Cliente) session.selectOne("ClienteMapper.selectCliente", codigo);
		logger.info("Obtener Cliente");
		return cli;
	}
	
	public List<Cliente> listaClientes() {
		List<Cliente> lista = null;
		lista = session.selectList("ClienteMapper.getClientes");
		logger.info("Lista de Clientes"+lista.size());
		return lista;
	}
	public List<Estado> listaEstados(){
		List<Estado> lista=null;
		lista = session.selectList("EstadoMapper.getAllEstados");
		logger.info("Lista de Estado"+lista.size());
		return lista;
		
	}

	public boolean insertarClientes(Cliente cliente) {
		boolean insertoCliente = false;
		try {
			session.insert("ClienteMapper.insertarCliente", cliente);
			session.commit();
			insertoCliente = true;
			if (insertoCliente) {
				logger.info("Se guardo el cliente satisfatoriamente");
			}
		} catch (Exception e) {
			logger.info(""+e);
			session.rollback();
		}
		
		return insertoCliente;
	}

	public boolean actualizarCliente(Cliente cliente) {
		boolean actualizaCliente=false;
		try{
		session.update("ClienteMapper.actualizarCliente", cliente);
		session.commit();
		actualizaCliente=true;
		if(actualizaCliente){
		logger.info("Cliente Actualizado");
		}
		}catch(Exception e){
			logger.info(""+e);
			session.rollback();
			session.close();
		}
		return actualizaCliente;
	}
	
	public boolean eliminaCliente(int codigoCliente){
		boolean deleteCliente=false;
		try{
		session.delete("ClienteMapper.deleteCliente", codigoCliente);
		session.commit();
		deleteCliente=true;
		if(deleteCliente){
			logger.info("Cliente Eliminado");
		}
		}catch(Exception e){
			logger.info(""+e);
			session.rollback();
			session.close();
		}
		return deleteCliente;
	}
	
}
