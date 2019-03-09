package com.fleabay.database;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DB {
	static private SqlSessionFactory instance = null;
	private static SqlSessionFactory getSqlSessionFactory() {
		if(instance == null) {
			InputStream inputStream;
			try {
				inputStream = Resources.getResourceAsStream("com/fleabay/database/database-config.xml");
				instance = new SqlSessionFactoryBuilder().build(inputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}
	public static SqlSession getSqlSession() {
		return getSqlSessionFactory().openSession();
	}
}
