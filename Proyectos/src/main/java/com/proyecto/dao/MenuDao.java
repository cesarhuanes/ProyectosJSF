
package com.proyecto.dao;

import java.util.List;
import java.util.logging.Logger;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.proyecto.pojos.Menu;
import com.proyecto.util.MyBatisUtil;


public class MenuDao {
	private static final Logger logger = Logger.getLogger(MenuDao.class.getName());
	SqlSessionFactory ssf = null;
	SqlSession session = null;

	public MenuDao() {
		ssf = MyBatisUtil.getSQL_SESSION_FACTORY();
		session = ssf.openSession();
	}

	public List<Menu> listaOpciones() {
		List<Menu> lista = session.selectList("MenuMapper.getMenus");
		session.close();
		return lista;
	}
}
