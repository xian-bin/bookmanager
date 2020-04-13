package com.book.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.pojo.BookOrders;
import com.book.service.OrderService;
import com.book.service.OrderServiceImpl;

/**
 * Servlet implementation class OrdersController
 */
@WebServlet("/OrdersController")
public class OrdersController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private OrderService os = new OrderServiceImpl();   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrdersController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String op = request.getParameter("op");
		if("add".equals(op)) {
			addCar(request,response);
		}else if("showCar".equals(op)) {
			showOrderList(request,response);
		}else if("update".equals(op)) {
			updateOrder(request,response);
		}
	}

	private void updateOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//设置响应的字符格式
		response.setContentType("application/json;charset=UTF-8");
		String oid = request.getParameter("oid");
		int count = Integer.parseInt(request.getParameter("count"));
		double price = Double.parseDouble(request.getParameter("price"));
		double curPrice = price*count;
		// 调用业务层更新订单的方法
		boolean isOk = os.updateOrders(oid,count,curPrice);
		PrintWriter pw = response.getWriter();
		if(isOk) {
			pw.write("{\"result\":\"true\"}");
		}else {
			pw.write("{\"result\":\"false\"}");
		}
		pw.flush();
		pw.close();
	}

	private void showOrderList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 获得userId,,在session中取用户账号
		String userId = (String) request.getSession().getAttribute("showUser");
		List<BookOrders> list = os.findOrders(userId);
		request.getSession().setAttribute("list", list);
		response.sendRedirect("user/cart.jsp");
	}

	private void addCar(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 设置响应给客户端的格式
		response.setContentType("application/json;charset=UTF-8");
		// 获得id的值
		int id = Integer.parseInt(request.getParameter("id"));
		double price = Integer.parseInt(request.getParameter("price"));
		// 生成订单编号  当前时间+6位随机数
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmss");
		String orderId = sdf.format(date);
		int rand = (int)(Math.random()*(999999-100000+1)+100000);
		orderId += rand;
		System.out.println("生成的订单id是："+orderId);
		// 获得用户的账户
		String userId = (String) request.getSession().getAttribute("showUser");
		BookOrders order = new BookOrders(orderId, id, 1, price, date, userId);
		// 调用增加订单的一个业务层方法
		boolean isOk = os.addOrders(order);
		PrintWriter pw = response.getWriter();
		if(isOk) {
			pw.write("{\"result\":\"true\"}");
		}else {
			pw.write("{\"result\":\"false\"}");
		}
		pw.flush();
		pw.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
