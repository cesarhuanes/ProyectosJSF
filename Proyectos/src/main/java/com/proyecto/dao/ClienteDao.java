package com.proyecto.dao;

import java.util.List;
import java.util.logging.Logger;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.proyecto.pojos.Cliente;
import com.proyecto.util.MyBatisUtil;


public class ClienteDao {
	private static final Logger logger = Logger.getLogger(ClienteDao.class.getName());
	SqlSessionFactory ssf = null;
	SqlSession session = null;

	public ClienteDao() {
		ssf = MyBatisUtil.getSQL_SESSION_FACTORY();
		session = ssf.openSession();
	}

	public List<Cliente> listaClientes() {

		List<Cliente> lista = null;
		lista = session.selectList("ClienteMapper.getClientes");
		
		for (Cliente x : lista) {
			logger.info("Nombre CLiente" + x.getNombreCliente());
		}
		return lista;
	}

	public boolean insertarClientes(Cliente cliente) {
		boolean insertoCliente = false;
		try {
			session = ssf.openSession();
			session.insert("ClienteMapper.insertarCliente", cliente);
			session.commit();
			insertoCliente = true;
			if (insertoCliente) {
				logger.info("Se guardo el cliente satisfatoriamente");
			}
		} catch (Exception e) {
			session.rollback();
			session.close();

		}
		session.close();
		return insertoCliente;
	}

	public Cliente obtenerCliente(Integer codigo) {
		Cliente cliente = null;
		
		cliente = (Cliente) session.selectOne("ClienteMapper.selectCliente", codigo);
		return cliente;
	}

	public void actualizarCliente(Integer codigo) {
		Cliente cliente = obtenerCliente(codigo);
		session = ssf.openSession();
		session.update("ClienteMapper.actualizarCliente", cliente);
		session.commit();
		session.close();

	}
}
