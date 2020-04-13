package com.book.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.book.commons.MybatisUtils;
import com.book.dao.order.BookOrdersMapper;
import com.book.pojo.BookOrders;

public class OrderServiceImpl implements OrderService {
	private SqlSession sqlSession;
	@Override
	public boolean addOrders(BookOrders order) {
		sqlSession = MybatisUtils.createSqlSession();
		int result = sqlSession.getMapper(BookOrdersMapper.class).saveOrder(order);
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
	public List<BookOrders> findOrders(String userId) {
		sqlSession = MybatisUtils.createSqlSession();
		List<BookOrders> list = sqlSession.getMapper(BookOrdersMapper.class).getOrderList(userId);
		MybatisUtils.closeSqlSession(sqlSession);
		return list;
	}
	@Override
	public boolean updateOrders(String oid, int count, double curPrice) {
		sqlSession = MybatisUtils.createSqlSession();
		int result = sqlSession.getMapper(BookOrdersMapper.class).updateOrders(oid,count,curPrice);
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
