package com.book.service;

import org.apache.ibatis.session.SqlSession;

import com.book.commons.MybatisUtils;
import com.book.dao.user.BookUserMapper;
import com.book.pojo.BookUser;

public class BookUserServiceImpl implements BookUserService {
	private SqlSession sqlSession;
	@Override
	public BookUser loginValidate(String userId, String userPsw) {
		sqlSession=MybatisUtils.createSqlSession();
		BookUserMapper mapper=sqlSession.getMapper(BookUserMapper.class);
		BookUser result=mapper.loginValidate(userId, userPsw);
		if(result!=null) {
			MybatisUtils.closeSqlSession(sqlSession);
			return result;
		}else {
			MybatisUtils.closeSqlSession(sqlSession);
			return null;
		}
	}

	@Override
	public boolean saveUser(BookUser user) {
		sqlSession=MybatisUtils.createSqlSession();
		BookUserMapper mapper=sqlSession.getMapper(BookUserMapper.class);
		int result=mapper.saveUser(user);
		if(result>0) {
			sqlSession.commit();
			MybatisUtils.closeSqlSession(sqlSession);
			return true;
		}else {
			sqlSession.rollback();
			MybatisUtils.closeSqlSession(sqlSession);
			return false;
		}
	}
	
}
