package com.book.service;

import java.util.List;

import com.book.pojo.BookCategory;

public interface CategoryService {
	
	List<BookCategory> getCategoryList();

	boolean saveCategory(String category);

	boolean deletecategory(int id);
	
}
