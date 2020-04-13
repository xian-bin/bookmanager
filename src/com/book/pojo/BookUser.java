package com.book.pojo;

public class BookUser {

	private String userId;
	private String userPsw;
	private String userName;
	private Integer role;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPsw() {
		return userPsw;
	}
	public void setUserPsw(String userPsw) {
		this.userPsw = userPsw;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getRole() {
		return role;
	}
	public void setRole(Integer role) {
		this.role = role;
	}
	public BookUser(String userId, String userPsw, String userName, Integer role) {
		super();
		this.userId = userId;
		this.userPsw = userPsw;
		this.userName = userName;
		this.role = role;
	}
	public BookUser() {
		super();
	}
	
}
