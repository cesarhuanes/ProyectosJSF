package com.proyecto.dao;

import java.util.List;
import java.util.logging.Logger;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.proyecto.pojos.Cliente;
import com.proyecto.pojos.Estado;
import com.proyecto.pojos.LugarTrabajo;
import com.proyecto.pojos.Proyecto;
import com.proyecto.pojos.TipoMoneda;
import com.proyecto.pojos.TipoTrabajo;
import com.proyecto.pojos.Usuario;
import com.proyecto.util.MyBatisUtil;

public class ProyectoDao {
	private static final Logger logger = Logger.getLogger(ProyectoDao.class.getName());
	SqlSessionFactory ssf = null;
	SqlSession session = null;

	public ProyectoDao() {
		ssf = MyBatisUtil.getSQL_SESSION_FACTORY();
		session = ssf.openSession();
	}
	public List<Proyecto> listaProyecto() {
		List<Proyecto> lista = null;
		lista = session.selectList("ProyectoMapper.getAllProyectos");
		logger.info("Lista de Proyecto"+lista.size());
		return lista;
	}
	public List<TipoTrabajo> listaTipoTrabajo(){
		List<TipoTrabajo> lista = null;
		lista = session.selectList("TipoTrabajoMapper.getAllTipoTrabajo");
		logger.info("Lista de tipo trabajo"+lista.size());
		return lista;
	}
	public List<LugarTrabajo>  listaLugarTrabajo(){
		List<LugarTrabajo> lista = null;
		lista = session.selectList("LugarTrabajoMapper.getAllLugarTrabajo");
		logger.info("Lista de Lugar Trabajo"+lista.size());
		return lista;	
		}
	public List<TipoMoneda> listaTipoMoneda(){
		List<TipoMoneda> lista = null;
		lista = session.selectList("TipoMonedaMapper.getAllTipoMoneda");
		logger.info("Lista de Tipo Moneda"+lista.size());
		return lista;	
		}
	public List<Cliente> listaClientes() {
		List<Cliente> lista = null;
		lista = session.selectList("ClienteMapper.getAllClientesActivos");
		logger.info("Lista de Clientes activos"+lista.size());
		return lista;
	}
	public List<Usuario> listaResponsables() {
		List<Usuario> lista = null;
		lista = session.selectList("UsuarioMapper.selectAllResponsable");
		logger.info("Lista de Clientes activos"+lista.size());
		return lista;
	}
   
	public List<Estado> listaEstadosProyecto(){
		List<Estado> lista=null;
		lista = session.selectList("EstadoMapper.getAllEstadosProyecto");
		logger.info("Lista de Estados proyectos"+lista.size());
		return lista;
		
	}
	public boolean insertarProyecto(Proyecto proyecto) {
		boolean insertoProyecto = false;
		try {
			session.insert("ProyectoMapper.insertarProyecto", proyecto);
			session.commit();
			insertoProyecto = true;
			if (insertoProyecto) {
				logger.info("Se guardo el Proyecto satisfatoriamente");
			}
		} catch (Exception e) {
			logger.info(""+e);
			session.rollback();
		}
		
		return insertoProyecto;
	}
	public boolean actualizaProyecto(Proyecto proyecto){
		boolean actualizaProyecto=false;
		try{
			session.update("ProyectoMapper.actualizarProyecto", proyecto);
			session.commit();
			actualizaProyecto=true;
			if(actualizaProyecto){
				logger.info("Se actualizo el Proyecto satisfatoriamente");
			}
			
		}catch(Exception e){
			logger.info(""+e);
			session.rollback();
		}
		return actualizaProyecto;
	}
	public boolean eliminarProyecto(int codigoProyecto){
		boolean eliminaProyecto=false;
		try{
			session.delete("ProyectoMapper.deleteProyecto", codigoProyecto);
			session.commit();
			eliminaProyecto=true;
			if(eliminaProyecto){
				logger.info("Se elimino el Proyecto satisfatoriamente");
			}
		}catch(Exception e){
			logger.info(""+e);
			session.rollback();
		}
		return eliminaProyecto;
	}
	
	public Proyecto obtenerProyecto(Integer codigo) {
		Proyecto proyecto = new Proyecto();
		proyecto = (Proyecto) session.selectOne("ProyectoMapper.getProyectosByPk", codigo);
		logger.info("Obtener Proyecto");
		return proyecto;
	}
}
