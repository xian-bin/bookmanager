package com.book.commons;
/**
 * 负责读取配置文件，创建执行对象，关闭执行对象
 * @author Lenovo
 *
 */

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisUtils {
	private static SqlSessionFactory fac;
	static {
		try {
			fac=new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("MyBatis-config.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//创建一个创建sqlsession对象的方法
	public static SqlSession createSqlSession() {
		return fac.openSession(false);
	}
	//创建一个关闭sqlsession对象的方法
	public static void closeSqlSession(SqlSession sqlSession) {
		if(sqlSession!=null) {
			sqlSession.close();
		}
	}
}
