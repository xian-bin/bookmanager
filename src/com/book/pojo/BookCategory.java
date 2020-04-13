package com.book.pojo;

public class BookCategory {

	private Integer id;
	private String category;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public BookCategory(Integer id, String category) {
		super();
		this.id = id;
		this.category = category;
	}
	public BookCategory() {
		super();
	}
	
}
