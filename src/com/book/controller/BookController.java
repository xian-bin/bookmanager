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
		// �����ַ�����
		request.setCharacterEncoding("UTF-8");
		int id = Integer.parseInt(request.getParameter("id"));
		//����ɾ���ķ���
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
			// �����ϴ����ļ�·��
			String filePath = this.getServletContext().getRealPath("/static/images");
			// ��֤���Ƿ��ǲ��õ�Multipart/form-data�ĸ�ʽ�����ļ��ϴ� enctype��ֵ
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			// �ж��Ƿ��ǲ��õĶ������ļ�������ʽ���ļ��ϴ�
			if(isMultipart) {
				// ����һ�������ļ��ϴ��Ĺ�������
				FileItemFactory fac = new DiskFileItemFactory();
				// ���ù������󴴽�һ�����ڽ����ļ��ϴ��Ķ���
				ServletFileUpload upload = new ServletFileUpload(fac);
				try {
					// ʹ���ļ��ϴ���������ñ��е���������
					List<FileItem> items = upload.parseRequest(request);
					// ������������ Iterator
					Iterator<FileItem> it = items.iterator();
					// ����������items����
					while (it.hasNext()) {
						// �������Ƿ���Ԫ��
						// ��ñ��е�Ԫ��
						FileItem item = it.next();// ȡ��������Ԫ��
						// getFieldName() ��ñ���nameֵ
						//System.out.println(item.getFieldName());
						// �жϱȱ��е�Ԫ�����ϴ�Ԫ�ر�������ͨ�ı���
						if(item.isFormField()) {
							// ����һ����ͨ��
							String name = item.getFieldName();// �õ�����nameֵ
							// ����nameֵ��Ϊ����ı�����ֵ
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
							// �����ϴ�Ԫ�ر�
							// �����ϴ��ļ�������
							photo = item.getName();
							// ����һ�������Ψһ��ʶֵ
							UUID rand = UUID.randomUUID();
							photo=rand+photo;
							// ����һ���ļ��������������Ҫ�ϴ����ļ�����
							File saveFile = new File(filePath, photo);
							// ���ļ��ϴ�
							// ����item�����write���������ļ�д�뵽������
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
			    	//��ӳɹ�
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
		// ����from
		int from = (currPage-1)*pg.getPageSize();
		List list = bis.getBookList(bookName, from, pg.getPageSize());
		pg.setPageLists(list);
		// ����ҳ��ʵ����뵽��������
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
