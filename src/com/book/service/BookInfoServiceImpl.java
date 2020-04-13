package com.book.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.book.commons.MybatisUtils;
import com.book.dao.book.BookMapper;
import com.book.pojo.BookInfo;

public class BookInfoServiceImpl implements BookInfoService {
	private SqlSession sqlSession;
	@Override
	public int getcount(String bookName) {
		sqlSession = MybatisUtils.createSqlSession();
		int count = sqlSession.getMapper(BookMapper.class).getcount(bookName);
		MybatisUtils.closeSqlSession(sqlSession);
		return count;
	}

	@Override
	public List<BookInfo> getBookList(String bookName, int from, int pageSize) {
		sqlSession = MybatisUtils.createSqlSession();
		List<BookInfo> list = sqlSession.getMapper(BookMapper.class).getBookList(bookName, from, pageSize);
		MybatisUtils.closeSqlSession(sqlSession);
		return list;
	}

	@Override
	public boolean deleteBook(int id) {
		sqlSession = MybatisUtils.createSqlSession();
		int result = sqlSession.getMapper(BookMapper.class).deleteBook(id);
		if(result > 0) {
			sqlSession.commit();
			MybatisUtils.closeSqlSession(sqlSession);
			return true;
		}else {
			sqlSession.rollback();
			MybatisUtils.closeSqlSession(sqlSession);
			return false;
		}
	}

	@Override
	public boolean addInfo(BookInfo infos) {
		sqlSession = MybatisUtils.createSqlSession();
		int result = sqlSession.getMapper(BookMapper.class).saveBook(infos);
		if(result > 0) {
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
