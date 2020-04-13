package com.book.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.pojo.BookCategory;
import com.book.service.CategoryService;
import com.book.service.CategoryServiceImpl;

/**
 * Servlet implementation class CategoryController
 */
@WebServlet("/CategoryController")
public class CategoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CategoryService cs = new CategoryServiceImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CategoryController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String op = request.getParameter("op");
		if ("show".equals(op)) {
			showInfo(request,response);
		}else if("add".equals(op)) {
			addBook(request,response);
		}else if("delete".equals(op)) {
			deletecategory(request,response);
		}
	}

	private void deletecategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 设置字符编码
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		//调用删除的方法
		boolean isOk = cs.deletecategory(id);
		if(isOk) {
			response.sendRedirect("admin/book-mgr.jsp");
		}else {
			response.sendRedirect("admin/book-mgr.jsp");
		}
	}

	private void addBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//设置字符编码格式
		request.setCharacterEncoding("UTF-8");
		//获取数据
		String category = request.getParameter("category");
		BookCategory ca = new BookCategory();
		boolean isOk = cs.saveCategory(category);
		if(isOk) {
			response.sendRedirect("admin/book-mgr.jsp");
		}else {
			response.sendRedirect("admin/book-mgr.jsp");
		}
	}

	private void showInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<BookCategory> list = cs.getCategoryList();
		request.getSession().setAttribute("list", list);
		response.sendRedirect("admin/category-mgr.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
