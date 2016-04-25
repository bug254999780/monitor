<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath %>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理-警告记录</title>
<link rel="stylesheet" href="css/multiple-select.css" />
<link href="css/base.css" rel="stylesheet" type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" />

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
				var html = '<option value>请选择</option>';
				for(var i = 0;i < data.equipment.length;i++){
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
					html += '</option>';
				}
				$("#equipment").html(html);
			});
		}
		//点击选择设备
		function onChangeEquipment(){ 
			requestRemoteData("monitor/MonitorController/getTarget", {"equipmentType" : $("#equipment").val().split("@")[0]}, "get", function(data){
				var html = '<option value>请选择</option>';
				for(var i = 0;i < data.target.length;i++){
					html += '<option value="'+data.target[i].targetCode+'@'+data.target[i].targetName+'">'+data.target[i].targetName+'</option>';
				}
				$("#target").html(html);
			});
		}
		
		//过滤数据
		function guolv(type){//0 初始化查询 、1首页、2上一页、3下一页、4尾页
			var areaArray=$("#area").val();
			var area = "";
			for(var value in areaArray){
				if(value > 0){
					area += ",";	
				}
				if((parseInt(value)+1) == areaArray.length){
					area += "'"+areaArray[value].split("@")[0]+"'@"+areaArray[value].split("@")[1];
				}else{
					area += "'"+areaArray[value].split("@")[0]+"'";
				}
			} 
			var roomArray=$("#room").val();
			var room = "";
			for(var value in roomArray){
				if(room.length > 0){
					room += ",";	
				}
				if((parseInt(value)+1) == roomArray.length){
					room += "'"+roomArray[value].split("@")[0]+"'@"+roomArray[value].split("@")[1];
				}else{
					room += "'"+roomArray[value].split("@")[0]+"'";
				}
			} 
			
			var equipment = $("#equipment").val();
			var param = "";
			var code = "1";
			
			/*if(area == ""){
				alert("请选择区域");
				return ;
			}*/
			if(type == 0){
				param = {"area":area, "room":room, "equipment":equipment, "pageSize":$("#guolvPageSize").val(), "pageNum":1};
			}
			if(type == 1){
				param = {"area":area, "room":room, "equipment":equipment, "code":code, "pageSize":$("#guolvPageSize").val(), "pageNum":1};
			}
			if(type == 2){
				var pN = 1;
				var pNN = $("#guolvPageNum").val();
				if($("#guolvPageNum").val() > 1){
					pNN --;
				}
				param = {"area":area, "room":room, "equipment":equipment, "code":code, "pageSize":$("#guolvPageSize").val(), "pageNum":pNN};
			}
			if(type == 3){
				var pNN = $("#guolvPageNum").val();
				if(pNN < parseInt($("#guolvTotalPage").html())){
					pNN ++;
				}
				param = {"area":area, "room":room, "equipment":equipment, "code":code, "pageSize":$("#guolvPageSize").val(), "pageNum":pNN};
			}
			if(type == 4){
				param = {"area":area, "room":room, "equipment":equipment, "code":code, "pageSize":$("#guolvPageSize").val(), "pageNum":$("#guolvTotalPage").html()};
			}
			if(type == 5){//点击确认
				var skip = $("#guolvSkipPage").val();
				if(skip < 1){
					skip = 1;
				}
				if(skip >= parseInt($("#guolvTotalPage").html())){
					skip = $("#guolvTotalPage").html();
				}
				param = {"area":area, "room":room, "equipment":equipment, "code":code, "pageSize":$("#guolvPageSize").val(), "pageNum":skip};
			}
			requestRemoteData("monitor/MonitorController/getAlarmRecord", param, "get", function(data){
				html = '';
				html += '<tr><th>区域</th><th>机房</th><th>设备类型</th><th>设备名称</th><th>设备编号</th><th>采集时间</th><th>指标</th><th>异常值</th><th>操作</th></tr>';
				var hm='<tr><th>区域</th><th>机房</th><th>设备类型</th><th>设备名称</th><th>设备编号</th><th>采集时间</th><th>指标</th><th>异常值</th><th>操作</th></tr>'
				for(var i = 0; i < data.pageList.list.length;i++){
					var object = data.pageList.list[i];
					//if(object.reserve3 == "1" ||object.reserve3==1){
						html += '<tr>';
						if(object.area=="null" ||object.area==null)
							object.area="未知";
							
						if(object.room=="null" ||object.room==null)
							object.room="未知";
							
						html += 	'<td>'+object.area+'</td>';
						html += 	'<td>'+object.room+'</td>';
						html += 	'<td>'+object.equipmentName+'</td>';
						html += 	'<td>'+object.dname+'</td>';
						html += 	'<td>'+object.equipmentCode+'</td>';
						html += 	'<td>'+object.receiveTime+'</td>';
						html += 	'<td>'+object.targetName+'</td>';
						html += 	'<td>'+object.monitorValue+'</td>';
						html += 	'<td>';
						//if(object.reserve3 == "1"){
						html += '<a class="btnQr" data-id="'+object.dataNo+'" id="warningRecordBtn" onclick="warningRecordBtnClick();">确认</a>';
						//}
						html += 	'</td>';
						html += '</tr>';
					}
				//}
				if(html==hm){
					html="";
				}
				$("#tableGuoLv").html(html);
				
				$("#guolvDangQian").html(data.pageList.pageNum);
				$("#guolvTotalPage").html(data.pageList.totalPages);
				$("#guolvCount").html(data.pageList.totalCount);
				$("#guolvPageNum").val(data.pageList.pageNum);
				$("#guolvPageSize").val(data.pageList.pageSize);
				
			});
		}
		
		//全部数据
		function allTable(type){//0 初始化查询 、1首页、2上一页、3下一页、4尾页
			var areaArray=$("#area").val();
			var area = "";
			for(var value in areaArray){
				if(value > 0){
					area += ",";	
				}
				if((parseInt(value)+1) == areaArray.length){
					area += "'"+areaArray[value].split("@")[0]+"'@"+areaArray[value].split("@")[1];
				}else{
					area += "'"+areaArray[value].split("@")[0]+"'";
				}
			} 
			var roomArray=$("#room").val();
			var room = "";
			for(var value in roomArray){
				if(room.length > 0){
					room += ",";	
				}
				if((parseInt(value)+1) == roomArray.length){
					room += "'"+roomArray[value].split("@")[0]+"'@"+roomArray[value].split("@")[1];
				}else{
					room += "'"+roomArray[value].split("@")[0]+"'";
				}
			} 
			var equipment = $("#equipment").val();
			var param = "";
			var code = "0";
			/*if(area == ""){
				alert("请选择区域");
				return ;
			}*/
			if(type == 0){
				param = {"area":area, "room":room, "equipment":equipment, "code":code, "pageSize":$("#allTablePageSize").val(), "pageNum":1};
			}
			if(type == 1){
				param = {"area":area, "room":room, "equipment":equipment, "code":code, "pageSize":$("#allTablePageSize").val(), "pageNum":1};
			}
			if(type == 2){
				var pNN = $("#allTablePageNum").val();
				if($("#allTablePageNum").val() > 1){
					pNN --;
				}
				param = {"area":area, "room":room, "equipment":equipment, "code":code, "pageSize":$("#allTablePageSize").val(), "pageNum":pNN};
			}
			if(type == 3){
				var pNN = $("#allTablePageNum").val();
				if(pNN < parseInt($("#allTableTotalPage").html())){
					pNN ++;
				}
				param = {"area":area, "room":room, "equipment":equipment, "code":code, "pageSize":$("#allTablePageSize").val(), "pageNum":pNN};
			}
			if(type == 4){
				param = {"area":area, "room":room, "equipment":equipment, "code":code, "pageSize":$("#allTablePageSize").val(), "pageNum":$("#allTableTotalPage").html()};
			}
			if(type == 5){//点击确认
				var skip = $("#allTableSkipPage").val();
				if(skip < 1){
					skip = 1;
				}
				else{
					if(skip >= parseInt($("#allTableTotalPage").html())){
						skip = $("#allTableTotalPage").html();
					}
				}
				param = {"area":area, "room":room, "equipment":equipment, "code":code, "pageSize":$("#allTablePageSize").val(), "pageNum":skip};
			}
			requestRemoteData("monitor/MonitorController/getAlarmRecord", param, "get", function(data){
				html = '';
				html += '<tr><th>区域</th><th>机房</th><th>设备类型</th><th>设备名称</th><th>设备编号</th><th>采集时间</th><th>指标</th><th>异常值</th></tr>';
				var hm='<tr><th>区域</th><th>机房</th><th>设备类型</th><th>设备名称</th><th>设备编号</th><th>采集时间</th><th>指标</th><th>异常值</th></tr>'
				for(var i = 0; i < data.pageResult.list.length;i++){
					var object = data.pageResult.list[i];
					html += '<tr>';
					if(object.area=="null" ||object.area==null)
						object.area="未知";
						
					if(object.room=="null" ||object.room==null)
						object.room="未知";
						
					html += 	'<td>'+object.area+'</td>';
					html += 	'<td>'+object.room+'</td>';
					html += 	'<td>'+object.equipmentName+'</td>';
					html += 	'<td>'+object.dname+'</td>';
					html += 	'<td>'+object.equipmentCode+'</td>';
					html += 	'<td>'+object.receiveTime+'</td>';
					html += 	'<td>'+object.targetName+'</td>';
					html += 	'<td>'+object.monitorValue+'</td>';
					/*html += 	'<td>';
				 	if(object.reserve3 == "1"){
							html += '<a class="btnQr" data-id="'+object.dataNo+'" id="warningRecordBtn" onclick="warningRecordBtnClick();">确认</a>';
					} 
					//html += 	'</td>';*/
					html += '</tr>';
				}
				if(html==hm){
					html="";
				}
				$("#tableallTable").html(html);
				
				$("#allTableDangQian").html(data.pageResult.pageNum);
				$("#allTableTotalPage").html(data.pageResult.totalPages);
				$("#allTableCount").html(data.pageResult.totalCount);
				$("#allTablePageNum").val(data.pageResult.pageNum);
				$("#allTablePageSize").val(data.pageResult.pageSize);
				
			});
		}
		
		
		function warningRecordBtnClick(){
			var dataNo = $("#warningRecordBtn").attr("data-id");
			requestRemoteData("monitor/MonitorController/updateAlarmRecord", {"dataNo":dataNo}, "post", function(data){
				if(data.result == "success"){
					 location.reload();
				}
			});
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
<body>
	<!-- header -->
	<div class="header">
		<div style="width: 21%;float:left; text-align: center;background: #f7f7f7 none repeat scroll 0 0;height: 50px; border-bottom: 1px solid #ddddde;padding-top: 10px;">
		<img style="" src="img/logo.png">
		</div>
		<div class="menu" style="float:right;width: 45%;padding-left: 34%;">	<a href="monitor/IndexLoadController/index" >首页</a> <a  href="monitor/MonitorController/toRealTimeMonitor">实时监测</a> <a  href="monitor/IndexLoadController/shownotice" >公告</a>
			<a class="curr" href="monitor/MonitorController/getAlarmRecord">警告记录</a>  <a href="http://<%=request.getServerName() %>:9005/admin/login.jspx">系统操作</a>
		</div>

	</div>
	<!-- main -->
    <div class="main2">
    	<div class="inputList">
        	<ul>
            	<li>
                	<span>区域：</span>
                    <select id="area" multiple="multiple" class="inputText sel" style="width: 55%;">
                    <!--<option value>请选择</option> -->
                        <c:forEach items="${area}" var="a">
							<option value="${a.areacode}@${a.areaname}">${a.areaname}</option>
						</c:forEach>
                    </select>
                </li>
                <li>
                	<span>机房：</span>
                    <select id="room" multiple="multiple" class="inputText sel" style="width: 55%;">
                   <!-- <option value>请选择</option> -->
                    </select>
                </li>
                <li>
                	<span>设备：</span>
                    <select class="inputText sel" id="equipment" onClick="onChangeEquipment()">
                    	<option value>请选择</option>
                    </select>
                </li>
            </ul>
            <p><a class="btnSearch" id="alarmRecordBtnSearch">查询</a></p>
        </div>
        <div class="result warning">
        	<h3><a class="btnSearch curr" id="guolv">过滤的报警记录</a><a class="btnSearch" id="all">完全的报警记录</a></h3>
            <div class="resultCont" style="padding-bottom: 5%;">
            	<div class="record cBox">
                	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tab" id="tableGuoLv">
                    </table>
                    <div class="paging">
                    	<a class="btnFirst" onclick="guolv(1);">第一页</a>
                        <a class="btnPrev" onclick="guolv(2);">上一页</a>
                        <a class="btnNext" onclick="guolv(3);">下一页</a>
                        <a class="btnLast" onclick="guolv(4);">最后页</a>
                        <span>当前第<i id="guolvDangQian">1</i>页/共<i id="guolvTotalPage">0</i>页</span>
                        <input type="text" value="1" class="pageCount" id="guolvSkipPage"/>
                        <span>页</span>
                        <a class="btnQueding" onclick="guolv(5);">确定</a>
                        <input type="hidden" id="guolvPageNum" value="1"/>
                        <input type="hidden" id="guolvPageSize" value="10"/>
                    </div>
                </div>
                
                <div class="record cBox" style="display:none;">
                	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="tab" id="tableallTable">
                    </table>
                    <div class="paging">
                        <a class="btnFirst" onclick="allTable(1);">第一页</a>
                        <a class="btnPrev" onclick="allTable(2);">上一页</a>
                        <a class="btnNext" onclick="allTable(3);">下一页</a>
                        <a class="btnLast" onclick="allTable(4);">最后页</a>
                        <span>当前第<i id="allTableDangQian">1</i>页/共<i id="allTableTotalPage">0</i>页</span>
                        <input type="text" value="1" class="pageCount" id="allTableSkipPage"/>
                        <span>页</span>
                        <a class="btnQueding" onclick="allTable(5);">确定</a>
                        <input type="hidden" id="allTablePageNum" value="1"/>
                        <input type="hidden" id="allTablePageSize" value="10"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="js/jquery-1.7.1.min.js" type="text/javascript"></script>
	<script src="js/index.js" type="text/javascript"></script>
    <script type="text/javascript" src="js/jquery.multiple.select.js" ></script>
    <script type="text/javascript">
	    $(document).ready(function(){
			//点击查询，查询数据
			$("#alarmRecordBtnSearch").click(function(){
				if($("#guolv").hasClass("curr")){
					guolv(0);
				}else{
					allTable(0);
				}
			});
			guolv(0);
			allTable(0);
	    	//setTimeout("setLabel()",20000);
			try {
				clearInterval(sl);
			} catch (e) {
			}
			si=setInterval("setLabel()",30000);
			
			
		});
	    
	    $(function(){
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
	    
		function setLabel(){
			if($("#guolv").hasClass("curr")){
				guolv(0);
			}else{
				allTable(0);
			}
			//location.reload();
		}
    </script>
</body>
</html>