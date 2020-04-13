package com.book.service;

import com.book.pojo.BookUser;

public interface BookUserService {
	//验证登录
	BookUser loginValidate( String userId,String userPsw);
	//增加注册用户
	boolean saveUser(BookUser user);
}
