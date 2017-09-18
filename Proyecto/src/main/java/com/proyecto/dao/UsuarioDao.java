
package com.proyecto.dao;

import com.proyecto.pojos.Usuario;
import com.proyecto.util.MyBatisUtil;
import java.util.List;
import java.util.logging.Logger;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;


public class UsuarioDao {
    private static  final Logger logger=Logger.getLogger(UsuarioDao.class.getName());
SqlSessionFactory ssf= MyBatisUtil.getSQL_SESSION_FACTORY();
SqlSession session = null;  

public Usuario obtenerUsuario(String usu){
    session=ssf.openSession();
   Usuario usuario=null;
   usuario=(Usuario)session.selectOne("UsuarioMapper.getUsuario",usu);
   session.close();
   return usuario;
}
}
