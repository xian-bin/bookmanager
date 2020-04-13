package com.book.service;

import java.util.List;

import com.book.pojo.BookOrders;

public interface OrderService {

	boolean addOrders(BookOrders order);

	List<BookOrders> findOrders(String userId);

	boolean updateOrders(String oid, int count, double curPrice);

}
