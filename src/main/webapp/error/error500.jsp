<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="<%=request.getContextPath() %>/error/error.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function displayErrorMessage(obj){
	var errorMessage = document.getElementById("errorMessage");
	if(obj.innerHTML == '查看后台错误报告'){
		errorMessage.style.display = '';
		obj.innerHTML = '关闭后台错误报告';
	}else{
		errorMessage.style.display = 'none';
		obj.innerHTML = '查看后台错误报告';
	}
	
	
}


function copyErrorMessage(){
	var errorMessage = document.getElementById("errorMessage");
	errorMessage.select(); // 选择对象
	document.execCommand("Copy"); // 执行浏览器复制命令
}
</script>
</head>
<body>

<%-- 
<div class="sorry">
  <!-- <p class="sorrywz">sorry,页面打开报错<br />请检查报表工具部署配置...</p> -->
  <p class="sorrywz">很抱歉 <br />您请求的页面无法打开,请联系系统管理员...</p>

  <p class="sorryfz"><a href="javascript: void(0)" onclick="displayErrorMessage(this)">查看后台错误报告</a></p>
  	<!-- <a href="javascript: void(0)" onclick="copyErrorMessage()">复制后台错误报告</a>-->
</div>
<textarea id="errorMessage" style="display: none;width: 100%;height: 300px;"><%=exception.getMessage() %></textarea>
 --%>

</body>
</html>