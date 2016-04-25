<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@	taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<style type="text/css">
.openfont{
font-size: 14px;
font-weight: 600;
line-height: 23px;
}
</style>
<script type="text/javascript">
	
$(function(){
	loadtree();
	loadright('all','1');
});
var si;
	//加载左边树
	function loadtree() {
		
		var url ="monitor/IndexLoadController/toIndextree";
		$.post(url, {
		}, function(data) {
			$('.all').empty().append('<a onClick="loadright(&quot;all&quot;,&quot;1&quot;);">全部机房</a>');
			for ( var o in data) {
				var tag_id="tag"+o;
				 $('#li').append("<dl id="+tag_id+" ></dl>");
				 //区域匹配
				 $('#'+tag_id).append("<dt class='dt' ><a onclick=loadright('"+data[o].aareacode+"','"+0+"')>"+data[o].areaname+"</a></dt>");
					var json = data[o].tagvalue; 
					 for( var j in json){
						 if(typeof(json[j].macshineroom)!="undefined"){
							 //机房匹配
							 $('#'+tag_id).append("<dd id=dd"+json[j].macshineroomcode+"><a onclick=loadright('"+json[j].macshineroomcode+"','"+1+"')>"+json[j].macshineroom+"</a></dd>");
						 }
						}
			}

		}, 'json');
	}
	//加载右边所有机器  传入机房信息
	function loadright(code,type) {
		var url ="monitor/IndexLoadController/toIndexright";
		$.post(url,{"code":code,"type":type},function(data){
			$(".sideRt").empty();
			for(var ob in data){
			var	tagid="div"+ob;
			var typename="";
			var dtype="";
			if(data[ob].device_name=="1"){
				typename="温湿度传感器";
				dtype="_t";
			}else if(data[ob].device_name=="2"){
				typename="UPS监控器";
				dtype="_u";
			}else if(data[ob].device_name=="3"){
				typename="服务器";
				dtype="_s";
			}else if(data[ob].device_name=="4"){
				typename="交换机";
				dtype="_w";
			}
			$(".sideRt").append("<div  class='boxRt'><h3><span>"+typename+"</span></h3><ul id="+tagid+"></ul></div>");
			for(var o in data[ob].tagvalue){
				var imgurls=$('#_'+data[ob].device_name+"normalimg").attr('src');
				var ddname="";
				if(data[ob].tagvalue[o].dname!=null&&data[ob].tagvalue[o].dname !=""){
					ddname=data[ob].tagvalue[o].dname;
				}
				$("#"+tagid).append("<li  ><img id="+dtype+''+data[ob].tagvalue[o].device_code+" onClick='opendiv(&quot;"+data[ob].tagvalue[o].device_name+"&quot;,&quot;"+data[ob].tagvalue[o].device_code+"&quot;)' src='"+imgurls+"' />"+
				"<p ></p>"+
				ddname+
				"</li>");
			}
			}
			exceptioninfo();
			try {
				clearInterval(si);
			} catch (e) {
				
			}
			si=setInterval("exceptioninfo()",30000);
		},'json');
	}
	function opendiv(type,code){
		var url ="monitor/IndexLoadController/detailed";
		$.post(url,{'type':type,'code':code},function(data){
			//加载显示图表
			$('.popPic').empty();
			var ipvalue="";
			if(type!="2"){
				if(data.ip!=null && data.ip!="")
				ipvalue='<p style="padding-left:5%;">IP：'+data.ip+'</p>';
			}
			var areaname="";
			var macshineroom="";
			var deviceaddress="";
			var equipmentcode="";
			var receivetime="";
			var guarantee="";
			var dname=""
			if(data.areaname!=null&&data.areaname !=""){
				areaname=data.areaname;
			}
			if(data.macshineroom!=null&&data.macshineroom!=""){
				macshineroom=data.macshineroom;
			}
			if(data.deviceaddress!=null &&data.deviceaddress!=""){
				deviceaddress=data.deviceaddress;
			}
			if(data.equipmentcode!=null && data.equipmentcode!=""){
				equipmentcode=data.equipmentcode;
			}
			if(data.receivetime!=null &&data.receivetime!=""){
				receivetime=data.receivetime;
			}
			if(data.guarantee!=null && data.guarantee!=""){
				guarantee=data.guarantee;
			}
			if(data.dname!=null && data.dname!=""){
				dname=data.dname;
			}
			var newwork="";
			/* if(data.networkdelayrate !=null && data.networkdelayrate !=""){
				newwork='<p style="padding-left:5%;">网络延时：'+data.networkdelayrate+'s</p>';
			} */
			
			
			var titlettext='<p span style="padding-left:5%;">区域名称：'+areaname+'</p>'+
				'<p style="padding-left:5%;">机房名称：'+macshineroom+'</p>'+
				'<p style="padding-left:5%;">设备位置：'+deviceaddress+'</p>'+
				'<p style="padding-left:5%;">设备编号：'+equipmentcode+'</p>'+
				'<p style="padding-left:5%;">设备名称：'+dname+'</p>'+
				'<p style="padding-left:5%;">采集时间：'+receivetime+'</p>'+
				''+ipvalue+''+
				''+newwork+''+
				'<p id="wbinfo" style="padding-left:5%;">维保信息：'+guarantee+'</p>';
			if(type=="3"){
				$('.popPic').append('<div id="cpu"  style="width:33%;height:300px;"></div>');
				$('.popPic').append('<div id="memory"  style="width:33%;height:300px;margin-top: -300px; margin-left: 33%;"></div>');
				$('.popPic').append('<div id="desk"  style="width:33%;height:300px;margin-top: -300px; margin-left: 67%;"></div>');
				$('.popPic').append('<div id="info1" class="openfont"  style="width:100%;height:100px;">'+titlettext+'</div>');
				$('.mark,.popPic,.popCont,.pop').show();
				loadhightchar('cpu','cpu使用率','cpu',data.cpu,'%','cpu');
				loadhightchar('memory','内存使用率','内存',data.memory,'%','内存');
				loadhightchar2('desk','E统盘剩余空间','磁盘',data.desk,'G','磁盘'); 
			}
			if(type=="1"){
				$('.popPic').append('<div id="temperature"  style="width:50%;height:300px;"></div>');
				$('.popPic').append('<div id="humidity"  style="width:50%;height:300px;margin-top: -300px; margin-left: 50%;"></div>');
				$('.popPic').append('<div id="info3" class="openfont"  style="width:100%;height:100px;">'+titlettext+'</div>');
				$('.mark,.popPic,.popCont,.pop').show();
			 	
			 	loadhightchar('temperature','温度','温度',data.temperature,'℃','摄氏度');
				loadhightchar('humidity','湿度','rh',data.humidity,'%','湿度');
			}
			if(type=="4"){
				$('.popPic').append('<div id="switches"  style="width:50%;height:300px;"></div>');
				$('.popPic').append('<div id="networkdelayrate"  style="width:50%;height:300px;margin-top: -300px; margin-left: 50%;"></div>');
				$('.popPic').append('<div id="info4" class="openfont"  style="width:100%;height:100px;">'+titlettext+'</div>');
				$('.mark,.popPic,.popCont,.pop').show();
				loadhightchar('switches','丢包率','丢包率',data.packetsloss,'%','');
				var endv=180;
				 if(data.networkdelayrate!=null && typeof(data.networkdelayrate)!="undefined" && data.networkdelayrate  >endv){
					endv=parseFloat(data.networkdelayrate)+50;
				 }
				loadhightchar3('networkdelayrate','网络延时','延时',data.networkdelayrate,'s','',endv);
			}
			if(type=="2"){
				var imgurl="";
				if(data.ustatus=="0" ||data.ustatus=="0.0"){
					imgurl="img/dianyuan.jpg";
				}else{
					imgurl="img/dianyuan_error.jpg";
				}
				$('.popPic').append('<div id="info3"  style="width:100%;height:100px;">'+
						'<img style="height:300%;padding-top:6%;padding-left:25%;" src="'+imgurl+'"/>'+
						'</div>');
				$('.popPic').append('<div id="info4" class="openfont"  style="width:100%;height:100px;">'+titlettext+'</div>');
				$('#wbinfo').css('width','30%');
				$('.mark,.popPic,.popCont,.pop').show();
			}
		},'json');	
			
	}
	function RefreshDiv()
	{
		
		//刷新页面图
		$('[id^=_t]').attr('src',$('#_1normalimg').attr('src'));
		$('[id^=_u]').attr('src',$('#_2normalimg').attr('src'));
		$('[id^=_s]').attr('src',$('#_3normalimg').attr('src'));
		$('[id^=_w]').attr('src',$('#_4normalimg').attr('src'));
	}
	
	function exceptioninfo(){
		var url="monitor/IndexLoadController/exceptionInfo";
		RefreshDiv();		
		$.post(url,{},function(data){
			$('[id^=dd]').css("background-color","white");
			var dtype="";
			for(var o in data)
			{
				 if(data[o].equipmentType=="1"){
					dtype="_t";
				}else if(data[o].equipmentType=="2"){
					dtype="_u";
				}else if(data[o].equipmentType=="3"){
					dtype="_s";
				}else if(data[o].equipmentType=="4"){
					dtype="_w";
				}
				if(data[o].reserve3=="1"){
					$('#'+dtype+''+data[o].equipmentcode).attr('src',$('#_'+data[o].equipmentType+"exceptionimg").attr('src'));
					$('#dd'+data[o].devicemacshineroomcode).css("background-color","red"); 
				}
			}
			
		},'json');
	}
	function closediv(){
		$('.mark,.popPic,.popCont,.pop').hide();
	}
	function loadhightchar(id,type,Company,value,lable,ip){
			var values=0;
			 if(value!=null && typeof(value)!="undefined" && value!=0){
				 values=value; 
			 }
			values=parseFloat(values);
		 $('#'+id).highcharts({
	            chart: {
	                type: 'gauge',
	                plotBackgroundColor: null,
	                plotBackgroundImage: null,
	                plotBorderWidth: 0,
	                plotShadow: false
	            },

	            title: {
	                text: type
	            },

	            pane: {
	                startAngle: -150,
	                endAngle: 150,
	                background: [{
	                    backgroundColor: {
	                        linearGradient: {
	                            x1: 0,
	                            y1: 0,
	                            x2: 0,
	                            y2: 1
	                        },
	                        stops: [
	                            [0, '#FFF'],
	                            [1, '#333']
	                        ]
	                    },
	                    borderWidth: 0,
	                    outerRadius: '109%'
	                }, {
	                    backgroundColor: {
	                        linearGradient: {
	                            x1: 0,
	                            y1: 0,
	                            x2: 0,
	                            y2: 1
	                        },
	                        stops: [
	                            [0, '#333'],
	                            [1, '#FFF']
	                        ]
	                    },
	                    borderWidth: 1,
	                    outerRadius: '107%'
	                }, {
	                    // default background
	                }, {
	                    backgroundColor: '#DDD',
	                    borderWidth: 0,
	                    outerRadius: '105%',
	                    innerRadius: '103%'
	                }]
	            },

	            // the value axis
	            yAxis: {
	                min: 0,
	                max: 100,

	                minorTickInterval: 'auto',
	                minorTickWidth: 1,
	                minorTickLength: 10,
	                minorTickPosition: 'inside',
	                minorTickColor: '#666',

	                tickPixelInterval: 30,
	                tickWidth: 2,
	                tickPosition: 'inside',
	                tickLength: 10,
	                tickColor: '#666',
	                labels: {
	                    step: 2,
	                    rotation: 'auto'
	                },
	                title: {
	                    text: ip
	                },
	                plotBands: [{
	                    from: 0,
	                    to: 50,
	                    color: '#4FE829' // green
	                }, {
	                    from: 50,
	                    to: 80,
	                    color: '#EEC211' // yellow
	                }, {
	                    from: 80,
	                    to: 100,
	                    color: '#F70909' // red
	                }]
	            },

	            series: [{
	                name: Company,
	                data: [values],
	                tooltip: {
	                    valueSuffix:lable
	                }
	            }]

	        }); 
		 $('.highcharts-button').remove();
	}
	
	function loadhightchar2(id,type,Company,value,lable,ip){
		var values=0;
		 if(value!=null && typeof(value)!="undefined" && value!=0){
			 values=value; 
		 }
		values=parseFloat(values);
	 $('#'+id).highcharts({
            chart: {
                type: 'gauge',
                plotBackgroundColor: null,
                plotBackgroundImage: null,
                plotBorderWidth: 0,
                plotShadow: false
            },

            title: {
                text: type
            },

            pane: {
                startAngle: -150,
                endAngle: 150,
                background: [{
                    backgroundColor: {
                        linearGradient: {
                            x1: 0,
                            y1: 0,
                            x2: 0,
                            y2: 1
                        },
                        stops: [
                            [0, '#FFF'],
                            [1, '#333']
                        ]
                    },
                    borderWidth: 0,
                    outerRadius: '109%'
                }, {
                    backgroundColor: {
                        linearGradient: {
                            x1: 0,
                            y1: 0,
                            x2: 0,
                            y2: 1
                        },
                        stops: [
                            [0, '#333'],
                            [1, '#FFF']
                        ]
                    },
                    borderWidth: 1,
                    outerRadius: '107%'
                }, {
                    // default background
                }, {
                    backgroundColor: '#DDD',
                    borderWidth: 0,
                    outerRadius: '105%',
                    innerRadius: '103%'
                }]
            },

            // the value axis
            yAxis: {
                min: 0,
                max: 1000,

                minorTickInterval: 'auto',
                minorTickWidth: 1,
                minorTickLength: 10,
                minorTickPosition: 'inside',
                minorTickColor: '#666',

                tickPixelInterval: 30,
                tickWidth: 2,
                tickPosition: 'inside',
                tickLength: 10,
                tickColor: '#666',
                labels: {
                    step: 2,
                    rotation: 'auto'
                },
                title: {
                    text: ip
                },
                plotBands: [{
                    from: 0,
                    to: 100,
                    color: '#F70909' // red
                }, {
                    from: 100,
                    to: 200,
                    color: '#EEC211' // yellow
                }, {
                    from: 300,
                    to: 1000,
                    color: '#4FE829' // green
                }]
            },

            series: [{
                name: Company,
                data: [values],
                tooltip: {
                    valueSuffix:lable
                }
            }]

        });
	 
	 $('.highcharts-button').remove();
	}
	
	function loadhightchar3(id,type,Company,value,lable,ip,end){
		var values=0;
		 if(value!=null && typeof(value)!="undefined" && value!=0){
			 values=value; 
		 }
		values=parseFloat(values);
	 $('#'+id).highcharts({
            chart: {
                type: 'gauge',
                plotBackgroundColor: null,
                plotBackgroundImage: null,
                plotBorderWidth: 0,
                plotShadow: false
            },

            title: {
                text: type
            },

            pane: {
                startAngle: -150,
                endAngle: 150,
                background: [{
                    backgroundColor: {
                        linearGradient: {
                            x1: 0,
                            y1: 0,
                            x2: 0,
                            y2: 1
                        },
                        stops: [
                            [0, '#FFF'],
                            [1, '#333']
                        ]
                    },
                    borderWidth: 0,
                    outerRadius: '109%'
                }, {
                    backgroundColor: {
                        linearGradient: {
                            x1: 0,
                            y1: 0,
                            x2: 0,
                            y2: 1
                        },
                        stops: [
                            [0, '#333'],
                            [1, '#FFF']
                        ]
                    },
                    borderWidth: 1,
                    outerRadius: '107%'
                }, {
                    // default background
                }, {
                    backgroundColor: '#DDD',
                    borderWidth: 0,
                    outerRadius: '105%',
                    innerRadius: '103%'
                }]
            },

            // the value axis
            yAxis: {
                min: 0,
                max: end,

                minorTickInterval: 'auto',
                minorTickWidth: 1,
                minorTickLength: 10,
                minorTickPosition: 'inside',
                minorTickColor: '#666',

                tickPixelInterval: 30,
                tickWidth: 2,
                tickPosition: 'inside',
                tickLength: 10,
                tickColor: '#666',
                labels: {
                    step: 2,
                    rotation: 'auto'
                },
                title: {
                    text: ip
                },
                plotBands: [{
                    from: 0,
                    to: 50,
                    color: '#4FE829' // green
                }, {
                    from: 50,
                    to: end/2,
                    color: '#EEC211' // yellow
                }, {
                    from: end/2,
                    to: end,
                    color: '#F70909' // red
                }]
            },

            series: [{
                name: Company,
                data: [values],
                tooltip: {
                    valueSuffix:lable
                }
            }]

        }); 
	 $('.highcharts-button').remove();
}
</script>
</head>

<body>

	<!-- header -->
	<div class="header">
		<div style="width: 21%;float:left; text-align: center;background: #f7f7f7 none repeat scroll 0 0;height: 50px; border-bottom: 1px solid #ddddde;padding-top: 10px;">
		<img style="" src="img/logo.png">
		</div>
		<div class="menu" style="float:right;padding-left: 34%;width: 45%;">
			<a href="monitor/IndexLoadController/index" class="curr">首页</a> <a href="monitor/MonitorController/toRealTimeMonitor">实时监测</a> <a href="monitor/IndexLoadController/shownotice" >公告</a>
			<a href="monitor/MonitorController/getAlarmRecord">警告记录</a>  <a href="http://<%=request.getServerName() %>:9005/admin/login.jspx">系统操作</a>
		</div>
	</div>
	
		<div style='display: none;'>
				<c:forEach items="${imgInfo}" var="imgdata" varStatus="status">
				 	<img id="_${imgdata.type}normalimg" src="<%=basePath%>${imgdata.normalimg}" />
				 	<img id="_${imgdata.type}exceptionimg" src="<%=basePath%>${imgdata.exceptionimg}" />
				</c:forEach>
		</div>

	<!-- main -->
	<div class="main">

		<!-- left -->
		<div class="sideLt">
			<ul>
				<li id="li"><span class="all">全部机房</span>
				</li>
			</ul>
		</div>

		<!-- right -->
		<div class="sideRt">
		</div>
 	</div>

	<div class="pop">
		<div class="popCont">
			<a onclick="closediv();">X</a>
			<div id="popPic" class="popPic" >
			</div>
			
		</div>
	</div>
	<div class="mark"></div>
	<script src="js/index.js" type="text/javascript"></script>
	<script src="common/Highcharts-4.1.9/js/highcharts.js"></script>
	<script src="common/Highcharts-4.1.9/js/highcharts-more.js"></script>
	<script src="common/Highcharts-4.1.9/js/modules/exporting.js"></script>
</body>
</html>
