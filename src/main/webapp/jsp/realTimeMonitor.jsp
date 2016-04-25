<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>实时监测</title>
<link rel="stylesheet" href="css/multiple-select.css" />
<link href="css/base.css" rel="stylesheet" type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/telecomMonitor.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/jquery.datetimepicker.css"/>
<script type="text/javascript">
		//点击选择区域
		function onChangeArea(){
			var array=$("#area").val();
			var areaCode = "";
			for(var areaValue in array){
				if(areaCode.length > 0){
					areaCode += ",";	
				}
				areaCode += array[areaValue].split("@")[0];
			} 
			requestRemoteData("monitor/MonitorController/getRoom", {"areaCode" : areaCode}, "get", function(data){
				var html = '';
				for(var i = 0;i < data.room.length;i++){
					html += '<option value="'+data.room[i].macshineRoomCode+'@'+data.room[i].macshineRoom+'">'+data.room[i].macshineRoom+'</option>';
				}
				$("#room").next().find('div').html("");
				$("#room").html(html);
				$('#room').change(function() {
		        }).multipleSelect({
		            width: '100%'
		        });
			    //修改样式
		        $("#room").next().find('div ul').css({"max-height":"200px"});
		        $("#room").next().find('div li').css({"float":"none"});
			});
		}
		//点击选择机房
		function onChangeRoom(){
			var array=$("#room").val();
			var roomCode = "";
			for(var roomValue in array){
				if(roomCode.length > 0){
					roomCode += ",";	
				}
				roomCode += array[roomValue].split("@")[0];
			} 
			requestRemoteData("monitor/MonitorController/getEquipment", {"devicemacshineroomcode" : roomCode}, "get", function(data){
				var html = '';
				for(var i = 0;i < data.equipment.length;i++){
					if(data.equipment[i].device_name == '2'){//UPS机房不展示
						continue;
					}
					html += '<option value="'+data.equipment[i].device_name+'@'+data.equipment[i].device_name+'">';
					if(data.equipment[i].device_name == '1'){
						html += '温湿度传感器';
					}
					if(data.equipment[i].device_name == '2'){
						html += 'UPS监控器';
					}
					if(data.equipment[i].device_name == '3'){
						html += '服务器';
					}
					if(data.equipment[i].device_name == '4'){
						html += '交换机';
					}
					//console.log(html);
					html += '</option>';
				}
				$("#equipment").html(html);
			});
		}
		//点击选择设备
		function onChangeEquipment(){ 
			requestRemoteData("monitor/MonitorController/getTarget", {"equipmentType" : $("#equipment").val().split("@")[0]}, "get", function(data){
				var html = '';
				for(var i = 0;i < data.target.length;i++){
					html += '<option value="'+data.target[i].targetCode+'@'+data.target[i].targetName+'">'+data.target[i].targetName+'</option>';
				}
				$("#target").html(html);
			});
		}
		
		function record(type){//0 初始化查询 、1首页、2上一页、3下一页、4尾页
			if(type == 0){
				param = {"reqtype": dmh, "pageSize":$("#recordTablePageSize").html(), "pageNum":1};
			}
			if(type == 1){
				param = {"reqtype": dmh, "pageSize":$("#recordTablePageSize").val(), "pageNum":1};
			}
			if(type == 2){
				var pNN = $("#recordTablePageNum").val();
				if($("#recordTablePageNum").val() > 1){
					pNN --;
				}
				param = {"reqtype": dmh, "pageSize":$("#recordTablePageSize").val(), "pageNum":pNN};
			}
			if(type == 3){
				var pNN = $("#recordTablePageNum").val();
				if(pNN < parseInt($("#recordTotalPage").html())){
					pNN ++;
				}
				param = {"reqtype": dmh, "pageSize":$("#recordTablePageSize").val(), "pageNum":pNN};
			}
			if(type == 4){
				param = {"reqtype": dmh, "pageSize":$("#recordTablePageSize").val(), "pageNum":$("#recordTotalPage").html()};
			}
			if(type == 5){//点击确认
				var skip = $("#recordTableSkipPage").val();
				if(skip < 1){
					skip = 1;
				}
				else{
					if(skip >= parseInt($("#recordTotalPage").html())){
						skip = $("#recordTotalPage").html();
					}
				}
				param = {"reqtype": dmh, "pageSize":$("#recordTablePageSize").html(), "pageNum":skip};
			}
			requestRemoteData("monitor/MonitorController/toGetChartRecord", param, "get", function(data){
				packageRecordTable(data.result);
			});
		}
		
		function packageRecordTable(tableData){
			var html = "";
			for(var i = 0;i < tableData.list.length;i++){
				var row = tableData.list[i];
				html += "<tr>";
				for(var j = 0;j < row.length;j++){
					var ceil = row[j];
					if(i == 0){
						html += "<th>";
						html += ceil;
						html += "</th>";
					}else{
						html += "<td>";
						html += ceil;
						html += "</td>";
					}
				}
				html += "</tr>";
			}
			$("#recordDangQian").html(tableData.pageNum);
			$("#recordTotalPage").html(tableData.totalPages);
			$("#recordTablePageNum").val(tableData.pageNum);
			$("#recordTablePageSize").val(tableData.pageSize);
			$("#stageTable").html(html);
		}
		//远程调用数据
		function requestRemoteData(url, data, type, callback){
			if(!data){
				$.param(data);
			}
			$.ajax({
	            type:type,
	            url: url,
	            data: data,
	            dataType: "json",
	            success:callback
	       });
		}
	</script>
</head>
<body class="">
	<!-- header -->
	<div class="header">
		<div style="width: 21%; float: left; text-align: center; background: #f7f7f7 none repeat scroll 0 0; height: 50px; border-bottom: 1px solid #ddddde; padding-top: 10px;">
			<img style="" src="img/logo.png">
		</div>
		<div class="menu" style="float: right; width: 45%;padding-left: 34%;">
			<a href="monitor/IndexLoadController/index">首页</a> 
			<a class="curr" href="monitor/MonitorController/toRealTimeMonitor">实时监测</a> 
			<a href="monitor/IndexLoadController/shownotice">公告</a> 
			<a href="monitor/MonitorController/getAlarmRecord">警告记录</a> 
			<a href="http://<%=request.getServerName() %>:9005/admin/login.jspx">系统操作</a>
		</div>
	</div>

	<!-- main -->
    <div class="main2">
    	<div class="inputList">
        	<ul>
            	<li>
                	<span>区域：</span>
			        <select id="area" multiple="multiple" class="inputText sel" style="width: 55%;">
                        <c:forEach items="${area}" var="a">
							<option value="${a.areacode}@${a.areaname}">${a.areaname}</option>
						</c:forEach>
			        </select>
                </li>
                <li>
                	<span>机房：</span>
                    <select id="room" multiple="multiple"  class="inputText sel" style="width: 55%;">

			        </select>
                </li>
                <li>
                	<span>设备：</span>
                    <select class="inputText sel" id="equipment" onClick="onChangeEquipment()">
                    	<option>请选择</option>
                    </select>
                </li>
                <li>
                	<span>监控项：</span>
                    <select class="inputText sel" id="target">
                    	<option>请选择</option>
                    </select>
                </li>
                <!-- 
                <li style="width:66.66%">
                	<span>时间：</span>
                    <input type="text" id="startTime" value="" class="inputText txt" />
                    <span class="z">至</span>
                    <input type="text" id="endTime" value="" class="inputText txt" />
                </li>
                 -->
            </ul>
            <p><a class="btnSearch">查询</a></p>
        </div>
        <div class="result">
        	<h3><a class="btnSearch curr">报表</a><a class="btnSearch">记录</a></h3>
            <div class="resultCont" style="margin-bottom: 5%;">
            	<div class="report cBox">
                	<div id="container" ></div>
                </div>
                <div class="record cBox" style="display:none;">
                	<a class="btnSearch" id="excelA">导出</a>
                	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tab" id="stageTable" >
                    </table>
                    <div class="paging">
                    	<a class="btnFirst" onclick="record(1);">第一页</a>
                        <a class="btnPrev" onclick="record(2);">上一页</a>
                        <a class="btnNext" onclick="record(3);">下一页</a>
                        <a class="btnLast" onclick="record(4);">最后页</a>
                        <span>当前第<i id="recordDangQian">1</i>页/共<i id="recordTotalPage">0</i>页</span>
                        <input type="text" value="1" class="pageCount" id="recordTableSkipPage"/>
                        <span>页</span>
                        <a class="btnQueding" onclick="record(5);">确定</a>
                        <input type="hidden" id="recordTablePageNum" value="1"/>
                        <input type="hidden" id="recordTablePageSize" value="10"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
<script type="text/javascript" src="common/Highcharts-4.1.9/js/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="js/highCharts.js"></script>
<script src="common/Highcharts-4.1.9/js/highcharts.js"></script>
<script src="common/Highcharts-4.1.9/js/modules/exporting.js"></script>
<script src="js/index.js" type="text/javascript"></script>
<script src="js/jquery.datetimepicker.js"></script>
<script type="text/javascript" src="js/jquery.multiple.select.js" ></script>
<script>
	var logic = function(currentDateTime ){
		if( currentDateTime.getDay()==6 ){
			this.setOptions({
				minTime:'0:00'
			});
		}else
		this.setOptions({
			minTime:'0:00'
		});
	};
	$('#startTime, #endTime').datetimepicker({
		onChangeDateTime:logic,
		onShow:logic
	});
	 $(function() {
        $('#area').change(function() {
            onChangeArea();
        }).multipleSelect({
            width: '100%'
        });
        $('#room').change(function() {
	        onChangeRoom();
        }).multipleSelect({
            width: '100%'
        });
        //修改样式
		$(".ms-parent").attr("style", "width: 55%;");
        $(".ms-choice").attr("style", "border:none;");
        $(".ms-parent div ul").css({"max-height":"200px"});
    	$('.ms-parent div li').css({"float":"none"});
    	$('#area').click(function(){
    		$('#area').find('div ul').css({"max-height":"200px"});
    		$('#area').find('div li').css({"float":"none"});
    	});
    });
</script>
</html>
