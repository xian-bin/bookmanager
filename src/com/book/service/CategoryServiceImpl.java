package com.book.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.book.commons.MybatisUtils;
import com.book.dao.category.CategoryMapper;
import com.book.pojo.BookCategory;

public class CategoryServiceImpl implements CategoryService {
	private SqlSession sqlSession;
	@Override
	public List<BookCategory> getCategoryList() {
		sqlSession = MybatisUtils.createSqlSession();
		List<BookCategory> list = sqlSession.getMapper(CategoryMapper.class).getCategoryList();
		MybatisUtils.closeSqlSession(sqlSession);
		return list;
	}
	@Override
	public boolean saveCategory(String category) {
		sqlSession = MybatisUtils.createSqlSession();
		int result = sqlSession.getMapper(CategoryMapper.class).saveCategory(category);
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
	public boolean deletecategory(int id) {
		sqlSession = MybatisUtils.createSqlSession();
		int result = sqlSession.getMapper(CategoryMapper.class).deleteCategory(id);
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
