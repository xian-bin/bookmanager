package com.book.service;

import com.book.pojo.BookUser;

public interface BookUserService {
	//��֤��¼
	BookUser loginValidate( String userId,String userPsw);
	//����ע���û�
	boolean saveUser(BookUser user);
}
