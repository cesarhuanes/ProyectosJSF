
package com.proyecto.dao;

import java.util.List;
import java.util.logging.Logger;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.proyecto.pojos.Estado;
import com.proyecto.pojos.Perfil;
import com.proyecto.pojos.TipoDocumento;
import com.proyecto.pojos.Usuario;
import com.proyecto.util.MyBatisUtil;


public class UsuarioDao {
	private static final Logger logger = Logger.getLogger(UsuarioDao.class.getName());
	SqlSessionFactory ssf = null;
	SqlSession session = null;

	public UsuarioDao() {
		ssf = MyBatisUtil.getSQL_SESSION_FACTORY();
		session = ssf.openSession();
	}

	public Usuario obtenerUsuario(String user) {
		Usuario usuario = new Usuario();
		usuario = (Usuario) session.selectOne("UsuarioMapper.selectByUsuario", user);
		return usuario;
	}

	public Usuario getUserByPrimaryKey(int pkUser) {
		Usuario usuario = null;
		usuario = (Usuario) session.selectOne("UsuarioMapper.selectByPkUsuario", pkUser);
		return usuario;
	}

	public List<Usuario> listaUsuario() {
		List<Usuario> lista = null;
		lista = session.selectList("UsuarioMapper.selectAllUsuarios");
		logger.info("Lista de Usuarios" + lista.size());
		return lista;
	}

	public boolean insertarUsuario(Usuario usuario) {
		boolean insertoUsuario = false;
		try {
			session.insert("UsuarioMapper.insertarUsuario", usuario);
			session.commit();
			insertoUsuario = true;
			if (insertoUsuario) {
				logger.info("Se guardo el Usuario satisfatoriamente");
			}
		} catch (Exception e) {
			logger.info("" + e);
			session.rollback();
		}

		return insertoUsuario;
	}

	public boolean actualizarUsuario(Usuario usuario) {
		boolean actualizarUsuario = false;
		try {
			session.update("UsuarioMapper.actualizarUsuario", usuario);
			session.commit();
			actualizarUsuario = true;
			if (actualizarUsuario) {
				logger.info("Usuario  Actualizado");
			}
		} catch (Exception e) {
			logger.info("" + e);
			session.rollback();
			session.close();
		}
		return actualizarUsuario;
	}

	public boolean eliminaUsuario(int codigoUsuario) {
		boolean deleteUsuario = false;
		try {
			session.delete("UsuarioMapper.deleteUsuario", codigoUsuario);
			session.commit();
			deleteUsuario = true;
			if (deleteUsuario) {
				logger.info("Usuario Eliminado");
			}
		} catch (Exception e) {
			logger.info("" + e);
			session.rollback();
			session.close();
		}
		return deleteUsuario;
	}

	public List<Estado> listaEstados() {
		List<Estado> lista = null;
		lista = session.selectList("EstadoMapper.getAllEstados");
		logger.info("Lista de Estado" + lista.size());
		return lista;

	}

	public List<TipoDocumento> listaTipoDocumento() {
		List<TipoDocumento> lista = null;
		lista = session.selectList("TipoDocumentoMapper.getAllTipoDocumento");
		logger.info("Lista de tipo Documento" + lista.size());
		return lista;

	}

	public List<Perfil> listaPerfil() {
		List<Perfil> lista = null;
		lista = session.selectList("PerfilMapper.getAllPerfil");
		logger.info("Lista de perfil" + lista.size());
		return lista;

	}
}
