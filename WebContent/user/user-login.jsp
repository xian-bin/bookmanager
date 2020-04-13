<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>智远图书网</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/css/styles.css"/>
  </head>  
  <body>
    <div id="container">
    	<div id="header">
    		<div id="tool-bar">&nbsp;&nbsp; 欢迎光临智远图书网， [<a href="user-login.html">请登录</a>]&nbsp;[<a href="user-regist.html">免费注册</a>]&nbsp;&nbsp;&nbsp;<a href="index.html">首页</a>&nbsp;|&nbsp;<a href="cart.html">购物车</a>&nbsp;|&nbsp;<a href="#">我的订单</a>&nbsp;|&nbsp;<a href="#">帮助</a></div>
    		<h1>智远图书网-<span style="font-size: 48px; font-family: Arial; font-weight: lighter;">Book</span></h1>
    	</div>
    	<div id="main">
    		<div class="box" id="register">
    			<div class="title">用户登录</div>
				<form action="${pageContext.request.contextPath}/UserBookController?op=login" method="post" style="margin: 10px;">
					<table cellspacing="0" class="no-border">
				    	<tr>
				    		<td style="text-align: right;">登录账号：</td>
				    		<td><input type="text" name="userId" class="txt" value="" /></td>
				    	</tr>
				    	<tr>
				    		<td style="text-align: right;">登录密码：</td>
				    		<td><input type="password" name="userPsw" class="txt" value="" /></td>
				    	</tr>
				    	<tr>
				    		<td>&nbsp;</td>
				    		<td><input type="submit" value=" 登  录 " class="btn" />&nbsp;&nbsp;</td>
				    	</tr>
				    </table>
				</form>
    		</div>
    	</div>  	
		<div id="footer"><p>版权所有&copy;智远教育</p></div>
	</div>
  </body>
</html>