<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE HTML>
<html>
  <head>
    <title>智远图书网</title>
    <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/static/css/styles.css"/>
  </head>  
  <body>
    <div id="container">
    	<div id="header">
    		<div id="tool-bar">&nbsp;&nbsp; 欢迎光临智远图书网，[
	    		<a href="user-login.html">请登录</a>]&nbsp;[
	    		<a href="user-regist.html">免费注册</a>]&nbsp;&nbsp;&nbsp;
	    		<a href="index.html">首页</a>&nbsp;|&nbsp;
	    		<a href="cart.html">购物车</a>&nbsp;|&nbsp;
	    		<a href="#">我的订单</a>&nbsp;|&nbsp;
	    		<a href="#">帮助</a>
    		</div>
    		<h1>智远图书网-<span style="font-size: 48px; font-family: Arial; font-weight: lighter;">Book</span></h1>
    	</div>   
    	<form id="search-bar" action="" method="post">
	    		书名：<input type="text" class="txt" name="condition" />
	    		<input id="search-btn" type="submit" value=" 搜索图书 " />
    	</form>   	
    	<div id="main">
    		<div class="section-left">
    			<div class="box-left">
    				<div class="box-title">分类畅销榜</div>
    				<div class="box-content">
    					<p>·<a href="#">文学</a></p>
    					<p>·<a href="#">小说</a></p>
    					<p>·<a href="#">青春文学</a></p>
    					<p>·<a href="#">旅游</a></p>
    					<p>·<a href="#">哲学</a></p>
    					<p>·<a href="#">百科</a></p>
    					<p>·<a href="#">恐怖小说</a></p>    		
    				</div>
    			</div>
    		</div>
    		<div class="section-right">
    			<h3 align="center" style="margin-bottom: 20px; ">您选购的商品如下：</h3>
    			<table  align="center"  cellpadding="0" cellspacing="0" style="width:700px">
    				<tr>
    					<td class="header" width="200">订单号</td>
    					<td class="header" width="100">书名</td>
    					<td class="header"  width="50">单价</td>
    					<td class="header"  width="100">购物数量</td>
    					<td class="header"  width="60">小计</td>
    				</tr>
    				<c:forEach items="${list }" var="o">
    					<tr class="calcTr">
    						<td>${o.oid }</td>
	    					<td>${o.bookInfo.bookName }</td>
	    					<td>￥${o.bookInfo.price }</td>
	    					<td>
		    					<button onclick="decrease(this)">-</button>
		    					<input type="text" value="${o.count}" style="width:40px"/>
		    					<button onclick="add(this)">+</button> 本</td>
	    					<td>￥${o.curPrice }</td>
	    				</tr>
    				</c:forEach>
    				<tr>
    					<td colspan="5" class="header" style="text-align: right;">总计：￥<span id="showTotal"></span></td>
    				</tr>
    			</table>	
    			<div style="text-align: center; font-size: 14px; line-height: 40px;">
    				<a href="#" style="text-decoration: underline;">去结账</a>
    			</div>
    		</div>
    		<div class="cf"></div>   	
    	</div>  	
		<div id="footer"><p>版权所有&copy;智远教育</p></div>
	</div>
  </body>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-3.4.1.js"></script>
<script type="text/javascript">
	$(function(){
		calc();
	});
	
	function calc() {
		$(".calTr").each(function(){
			totalPrice += parseFloat($(this).find("td").eq(4).text().substring(1));
		});
		$("#showTotal").text(totalPrice);
	}
	//商品数量的增加
	function add(obj) {
		// 获得需要修改的值
		var count = $(obj).prev().val();
		count++;
		$(obj).prev().val(count);
		// 获得更新的订单号
		var oid = $(obj).parents("tr").find("td").eq(0).text();
		var price = parseFloat($(obj).parents("tr").find("td").eq(2).text().substring(1));
		//alert(oid+","+price);
		// 使用ajax去实现数据的更新
		$.post("${pageContext.request.contextPath}/OrdersController?op=update",
				{oid:oid,count:count,price:price},function(res){
			if(res.result=="true"){
				alert("更新成功！");
				localtion.href="user/cart.jsp";
			}else{
				alert("更新失败！");
				location.href="${pageContext.request.contextPath}/OrdersController?op=showCar";
				//window.location.reload();
				calc();
			}
		});
	}
	//商品数量的减少
	function decrease(obj) {
		// 获得需要修改的值
		var count = $(obj).next().val();
		count--;
		if(count <= 0){
			alert("不能再减了！");
			return;
		}
		$(obj).next().val(count);
		// 获得更新的订单号
		var oid = $(obj).parents("tr").find("td").eq(0).text();
		var price = parseFloat($(obj).parents("tr").find("td").eq(2).text().substring(1));
		//alert(oid+","+price);
		// 使用ajax去实现数据的更新
		$.post("${pageContext.request.contextPath}/OrdersController?op=update",
				{oid:oid,count:count,price:price},function(res){
			if(res.result=="true"){
				alert("更新成功！");
				localtion.href="user/cart.jsp";
			}else{
				alert("更新失败！");
				location.href="${pageContext.request.contextPath}/OrdersController?op=showCar";
				//window.location.reload();
				calc();
			}
		});
	}
</script>
</html>