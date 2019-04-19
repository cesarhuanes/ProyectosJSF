package com.proyecto.dao;

import java.util.List;
import java.util.logging.Logger;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.proyecto.pojos.Estado;
import com.proyecto.pojos.HistorialContrato;
import com.proyecto.pojos.Rol;
import com.proyecto.pojos.TipoContrato;
import com.proyecto.util.MyBatisUtil;

public class HistorialContratoDao {
	private static final Logger logger = Logger.getLogger(HistorialContratoDao.class.getName());
	SqlSessionFactory ssf = null;
	SqlSession session = null;

	public HistorialContratoDao() {
		ssf = MyBatisUtil.getSQL_SESSION_FACTORY();
		session = ssf.openSession();
	}

	public List<HistorialContrato> listaContratoByUser(Integer idUser) {
		List<HistorialContrato> lista = null;
		lista = session.selectList("HistorialContratoMapper.selectByIdUser", idUser);
		logger.info("Lista de Historial Contrato" + lista.size());
		return lista;
	}

	public HistorialContrato obtenerCliente(Integer codigoHc) {
		HistorialContrato hc = new HistorialContrato();
		hc = (HistorialContrato) session.selectOne("HistorialContratoMapper.selectByPkHc", codigoHc);
		logger.info("Obtener Cliente");
		return hc;
	}

	public List<Estado> listaEstados() {
		List<Estado> lista = null;
		lista = session.selectList("EstadoMapper.getAllEstados");
		logger.info("Lista de Estado" + lista.size());
		return lista;

	}

	public List<Rol> listaRol() {
		List<Rol> lista = null;
		lista = session.selectList("RolMapper.getAllRol");
		logger.info("Lista de rol" + lista.size());
		return lista;

	}

	public List<TipoContrato> listaTipoContrato() {
		List<TipoContrato> lista = null;
		lista = session.selectList("TipoContratoMapper.getAllTipoContrato");
		logger.info("Lista de Tipo contrato" + lista.size());
		return lista;

	}

	public boolean insertaHC(HistorialContrato hc) {
		boolean insertoHC = false;
		try {
			session.insert("HistorialContratoMapper.insertHC", hc);
			session.commit();
			insertoHC = true;
			if (insertoHC) {
				logger.info("Se guardo el HC satisfatoriamente");
			}
		} catch (Exception e) {
			logger.info("" + e);
			session.rollback();
		}

		return insertoHC;

	}
	
	public boolean actualizarHC(HistorialContrato hc) {
		boolean actualizarHC = false;
		try {
			session.insert("HistorialContratoMapper.actualizarHC", hc);
			session.commit();
			actualizarHC = true;
			if (actualizarHC) {
				logger.info("Se actualizo el HC satisfatoriamente");
			}
		} catch (Exception e) {
			logger.info("" + e);
			session.rollback();
		}
		return actualizarHC;

	}

}
