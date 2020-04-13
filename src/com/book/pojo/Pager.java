package com.book.pojo;
import java.util.List;
/**
 * 分页实体类
 * @author lindy
 * @创建时间 2020年4月12日下午10:48:12
 */
public class Pager {
	private int currPage = 1;// 当前页
	private int pageSize = 3;// 每页显示数据条数
	private int totalCount;// 总数据条数
	private int totalPages;// 总页数
	private List<Object> pageLists;// 装分页数据的集合
	public int getCurrPage() {
		return currPage;
	}
	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPages() {
		totalPages = totalCount % pageSize == 0 ? totalCount / pageSize :
			totalCount / pageSize + 1;
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public List<Object> getPageLists() {
		return pageLists;
	}
	public void setPageLists(List<Object> pageLists) {
		this.pageLists = pageLists;
	}
	
}
