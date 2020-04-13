<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>选择登录页面</title>
</head>
<body>
	<h1>请选择登录的角色</h1>
	<p><a href="${pageContext.request.contextPath }/user/user-login.jsp">前端登录</a></p>
	<p><a href="${pageContext.request.contextPath }/admin/admin-login.jsp">后台登录</a></p>
</body>
</html>