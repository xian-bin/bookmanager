package com.book.dao.user;

import org.apache.ibatis.annotations.Param;

import com.book.pojo.BookUser;

public interface BookUserMapper {
	//��֤��¼�ķ���
	BookUser loginValidate(@Param("userId") String userId,@Param("userPsw") String userPsw);
	//ע���û��ķ���
	int saveUser(BookUser user);
}
