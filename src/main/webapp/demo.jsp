<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.telecom.job.CookieJob"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath %>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>机房监控管理</title>
<link href="css/base.css" rel="stylesheet" type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link rel="icon" href="common/icon/telecom.png" type="image/x-icon">
<script src="js/jquery-1.7.1.min.js" type="text/javascript"></script>
<script src="js/index.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
		$.ajax({
			type : "post",
			url : "monitor/IndexController/toIndex",
			dataType:"json",
			data :{
				data:"{'name':'jack'}"
			},
			success : function(re) {
				console.log(re);
			}
		});
	});
</script>
</head>

<body>
	asdfasfdasf
</body>
</html>
