/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mybatis;

import java.io.IOException;
import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 *
 * @author Cesar Huanes
 */
public class MybatisUtil {
  private String resource="com/mybatis/mybatis-config.xml";
  private SqlSession session=null;
  
  public SqlSession getSession(){
      try{
          Reader reader=Resources.getResourceAsReader(resource);
          SqlSessionFactory  sqlMapper=new SqlSessionFactoryBuilder().build(reader);
          session=sqlMapper.openSession();
      }catch(IOException ex){
          ex.printStackTrace();
      }
  return session;
  }
  
}
