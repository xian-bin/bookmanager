package com.book.dao.user;

import org.apache.ibatis.annotations.Param;

import com.book.pojo.BookUser;

public interface BookUserMapper {
	//验证登录的方法
	BookUser loginValidate(@Param("userId") String userId,@Param("userPsw") String userPsw);
	//注册用户的方法
	int saveUser(BookUser user);
}
