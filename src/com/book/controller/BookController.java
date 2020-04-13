package com.book.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.book.pojo.BookInfo;
import com.book.pojo.Pager;
import com.book.service.BookInfoService;
import com.book.service.BookInfoServiceImpl;
import com.mysql.jdbc.StringUtils;

/**
 * Servlet implementation class BookController
 */
@WebServlet("/BookController")
public class BookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private BookInfoService bis = new BookInfoServiceImpl();  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookController() {
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
			deleteBook(request,response);
		}
	}

	private void deleteBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 设置字符编码
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		//调用删除的方法
		boolean isOk = bis.deleteBook(id);
		if(isOk) {
			response.sendRedirect("admin/book-mgr.jsp");
		}else {
			response.sendRedirect("admin/book-mgr.jsp");
		}
	}

	private void addBook(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String bookName=null; 
			String author=null;
			String categoryId=null;
			String publisher=null; 
			String price=null;
			String photo=null;
			// 设置上传的文件路径
			String filePath = this.getServletContext().getRealPath("/static/images");
			// 验证表单是否是采用的Multipart/form-data的格式进行文件上传 enctype的值
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			// 判断是否是采用的二进制文件流的形式做文件上传
			if(isMultipart) {
				// 创建一个用于文件上传的工厂对象
				FileItemFactory fac = new DiskFileItemFactory();
				// 利用工厂对象创建一个用于解析文件上传的对象
				ServletFileUpload upload = new ServletFileUpload(fac);
				try {
					// 使用文件上传对象来获得表单中的所有请求
					List<FileItem> items = upload.parseRequest(request);
					// 遍历整个集合 Iterator
					Iterator<FileItem> it = items.iterator();
					// 遍历整个的items集合
					while (it.hasNext()) {
						// 集合中是否有元素
						// 获得表单中的元素
						FileItem item = it.next();// 取出集合中元素
						// getFieldName() 获得表单的name值
						//System.out.println(item.getFieldName());
						// 判断比表单中的元素是上传元素表单还是普通文本表单
						if(item.isFormField()) {
							// 它是一个普通表单
							String name = item.getFieldName();// 得到表单的name值
							// 根据name值，为上面的变量赋值
							switch (name) {
							case "bookName":
								bookName = item.getString("UTF-8");
								break;
							case "author":
								author = item.getString("UTF-8");
								break;
							case "categoryId":
								categoryId = item.getString("UTF-8");
								break;
							case "publisher":
								publisher = item.getString("UTF-8");
								break;
							case "price":
								price = item.getString("UTF-8");
								break;
							}
						}else {
							// 它是上传元素表单
							// 保存上传文件的名称
							photo = item.getName();
							// 生成一个随机的唯一标识值
							UUID rand = UUID.randomUUID();
							photo=rand+photo;
							// 创建一个文件对象，来保存这个要上传的文件内容
							File saveFile = new File(filePath, photo);
							// 做文件上传
							// 调用item对象的write方法，将文件写入到服务器
							item.write(saveFile);
							photo="static/images/"+photo;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				BookInfo infos = new BookInfo(bookName, author, Integer.parseInt(categoryId), publisher, Integer.parseInt(price), photo);
				boolean isOk = bis.addInfo(infos);
			    if(isOk) {
			    	//添加成功
			    	response.sendRedirect("BookController?op=showInfo");
			    }else {
			    	response.sendRedirect("admin/admin-home.jsp");
			    }
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void showInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		showPageList(request, response);
		response.sendRedirect("user/index.jsp");
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
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
