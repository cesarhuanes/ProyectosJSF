
package com.proyecto.dao;

import java.util.logging.Logger;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

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

	public Usuario obtenerUsuario(String usu) {
		Usuario usuario = null;
		usuario = (Usuario) session.selectOne("UsuarioMapper.getUsuario", usu);
		return usuario;
	}
}
