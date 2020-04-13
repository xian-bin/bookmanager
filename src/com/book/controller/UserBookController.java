package com.book.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;

import com.book.pojo.BookUser;
import com.book.pojo.Pager;
import com.book.service.BookInfoService;
import com.book.service.BookInfoServiceImpl;
import com.book.service.BookUserService;
import com.book.service.BookUserServiceImpl;
import com.mysql.jdbc.StringUtils;
/**
 * Servlet implementation class UserBookController
 */
@WebServlet("/UserBookController")
public class UserBookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 // 创建业务层接口对象
	private BookUserService bus = new BookUserServiceImpl();
	private BookInfoService bis = new BookInfoServiceImpl();  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserBookController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//设置字符编码
		request.setCharacterEncoding("UTF-8");
		String op=request.getParameter("op");
		if("add".equals(op)) {
			addBookUser(request,response);
		}else if("login".equals(op)) {
			login(request,response);
		}
	}

	private void login(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException{
		// 获得账户和密码的数据
		String userId=request.getParameter("userId");
		String userPsw=request.getParameter("userPsw");
		String showUser = userId;
		// 对账户和密码进行MD5的加密
		userId = DigestUtils.md5Hex(userId);
		userPsw = DigestUtils.md5Hex(userPsw);
		// 调用验证登录的方法
		BookUser isOk = bus.loginValidate(userId, userPsw);
		if (isOk!=null) {
			// 去登录
			request.getSession().setAttribute("showUser", showUser);
			request.getSession().setAttribute("bookUser", isOk);
			showPageList(request,response);
			if(isOk.getRole() == 2) {
				response.sendRedirect("admin/book-mgr.jsp");
			}else if(isOk.getRole()==1) {
				response.sendRedirect("user/index.jsp");
			}
		}else {
			response.sendRedirect("user/user-login.jsp");
		}
	}

	private void showPageList(HttpServletRequest request, HttpServletResponse response) {
		String pageIndex = request.getParameter("pageIndex");
		String bookName = request.getParameter("bookName");
		int currPage = 0;
		Pager pg = new Pager();
		int totalCount = bis.getcount(bookName);
		pg.setTotalCount(totalCount);
		if (StringUtils.isNullOrEmpty(pageIndex)) {
			currPage = 1;
		}else {
			if(Integer.parseInt(pageIndex)<=0) {
				currPage = 1;
			}else if(Integer.parseInt(pageIndex)>=pg.getTotalPages()) {
				currPage = pg.getTotalPages();
			}else {
				currPage = Integer.parseInt(pageIndex);
			}
		}
		pg.setCurrPage(currPage);
		// 计算from
		int from = (currPage-1)*pg.getPageSize();
		List list = bis.getBookList(bookName, from, pg.getPageSize());
		pg.setPageLists(list);
		// 将分页类实体放入到作用域中
		request.getSession().setAttribute("pg", pg);
	}

	private void addBookUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId=request.getParameter("userId");
		String userPsw=request.getParameter("userPsw");
		String reLoginPsw=request.getParameter("reLoginPsw");
		String userName=request.getParameter("userName");
		String code=request.getParameter("code");
		String yzm = (String) request.getSession().getAttribute("yzm");
		String message=null;
		// mysql数据库提供的非空验证的工具类
		if(StringUtils.isNullOrEmpty(userId) || StringUtils.isNullOrEmpty(reLoginPsw)
				|| !code.equals(yzm)) {
			message="请完善信息！！！";
			request.getSession().setAttribute("message", message);
			response.sendRedirect("user/user-regist.jsp");
			return;
		}
		// 对账户和密码进行MD5的加密
		userId = DigestUtils.md5Hex(userId);
		userPsw = DigestUtils.md5Hex(userPsw);
		// 将这些数据封装到一个JavaBean中
		BookUser user = new BookUser(userId, userPsw, userName, 1);
		boolean isOk = bus.saveUser(user);
		if (isOk) {
			// 去登录
			response.sendRedirect("user/user-login.jsp");
		}else {
			response.sendRedirect("user/user-regist.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
