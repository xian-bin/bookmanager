package com.book.dao.order;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.book.pojo.BookOrders;
/**
 * 订单系统的操作层
 * @author xian
 * @创建时间 2020年4月13日下午4:29:41
 */
public interface BookOrdersMapper {
	
	int saveOrder(BookOrders order);
	
	List<BookOrders> getOrderList(@Param("uid") String uid);
	//购物车局部数据更新
	int updateOrders(@Param("oid") String oid,@Param("count") int count,@Param("curPrice") double curPrice);
}
