
package com.proyecto.util;

import java.io.Reader;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


public class MyBatisUtil {
	private static final SqlSessionFactory SQL_SESSION_FACTORY;
	static {
		try {
			Reader reader = Resources.getResourceAsReader("com/proyecto/recursos/SqlMapConfig.xml");
			SQL_SESSION_FACTORY = new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception e) {
			throw new RuntimeException("Error" + e);
		}

	}

	/**
	 * @return the SQL_SESSION_FACTORY
	 */
	public static SqlSessionFactory getSQL_SESSION_FACTORY() {
		return SQL_SESSION_FACTORY;
	}

}
