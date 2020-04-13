package com.book.pojo;

import java.util.Date;

public class BookOrders {

	private String oid;
	private Integer bid;
	private int count;
	private Double curPrice;
	private Date date;
	private String userId;
	private BookInfo bookInfo;
	public BookInfo getBookInfo() {
		return bookInfo;
	}
	public void setBookInfo(BookInfo bookInfo) {
		this.bookInfo = bookInfo;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Double getCurPrice() {
		return curPrice;
	}
	public void setCurPrice(Double curPrice) {
		this.curPrice = curPrice;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public BookOrders(String oid, Integer bid, int count, Double curPrice, Date date,String userId) {
		super();
		this.oid = oid;
		this.bid = bid;
		this.count = count;
		this.curPrice = curPrice;
		this.date = date;
		this.userId=userId;
	}
	public BookOrders() {
		super();
	}
	
}
