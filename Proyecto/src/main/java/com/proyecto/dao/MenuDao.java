
package com.proyecto.dao;

import com.proyecto.pojos.Menu;
import com.proyecto.util.MyBatisUtil;
import java.util.List;
import java.util.logging.Logger;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;


public class MenuDao {
 private static  final Logger logger=Logger.getLogger(MenuDao.class.getName());
SqlSessionFactory ssf= MyBatisUtil.getSQL_SESSION_FACTORY();
SqlSession session = null;  
public List<Menu> listaOpciones(){
    session=ssf.openSession();
   List<Menu> lista=null; 
   lista=session.selectList("MenuMapper.getMenus");
  
   return lista;
}
}
