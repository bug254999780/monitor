<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公告</title>
<link href="<%=path%>/css/base.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/css/style.css" rel="stylesheet" type="text/css" />
<link rel="icon" href="<%=path%>/common/icon/telecom.png"
	type="image/x-icon">
<script src="<%=path%>/js/jquery-1.7.1.min.js" type="text/javascript"></script>
<script src="<%=path%>/js/index.js" type="text/javascript"></script>
</head>

<body>

	<!-- header -->
	<div class="header">
		<div
			style="width: 21%; float: left; text-align: center; background: #f7f7f7 none repeat scroll 0 0; height: 50px; border-bottom: 1px solid #ddddde; padding-top: 10px;">
			<img style="" src="img/logo.png">
		</div>
		<div class="menu" style="float: right; width: 45%; padding-left: 34%;">
			<a href="monitor/IndexLoadController/index">首页</a> <a
				href="monitor/MonitorController/toRealTimeMonitor">实时监测</a> <a
				class="curr" href="monitor/IndexLoadController/shownotice">公告</a> <a
				href="monitor/MonitorController/getAlarmRecord">警告记录</a> <a href="http://<%=request.getServerName() %>:9005/admin/login.jspx">系统操作</a>
		</div>
	</div>


	<!-- main -->
	<div style="padding: 5% 10%; width: 80%">
		<c:forEach items="${notice}" var="data" varStatus="status">
			<ul>
				<li
					style="background-color: white; border: 1.5px solid #c5c5c5; line-height: 50px; text-align: left; padding: 5px 2%;font-size: 20px; font-weight: 600;margin-top: 2%;">${data.title}
					<span
					style="float: right;  font-size: 15px; font-weight: 200;">${data.createdate}
						&nbsp;&nbsp;<a id="${status.index}_a"
						onclick="disclose('${status.index}')">展开</a>
				</span>
				</li>
				<li style="background-color: #F7F7F7;display: none;" id="${status.index}_lc">${data.content}
					<p style="text-align: right;"><img src="img/jiantou.png" width="25px" style="padding-right: 2%;"  onclick="disclose('${status.index}')"></p>
				</li>
			</ul>
		</c:forEach>
	</div>



</body>
<script type="text/javascript">
	//关闭或者显示公告
	function disclose(id) {
		var avalue = $('#' + id + '_a').text();
		if (avalue == "展开") {
			$('#' + id + '_a').text('收起');
			$('#'+id+'_lc').css('display','block');
		}else{
			$('#' + id + '_a').text('展开');
			$('#'+id+'_lc').css('display','none');
		}
		
	}
</script>
</html>
