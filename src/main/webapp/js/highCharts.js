// JavaScript Document

function HighCharts()
{
	this.init();
}

HighCharts.prototype={
	init:function()
	{
		var _this = this;
		//_this.showDate();
		_this.bandBtnClick();
	},
	showDate:function()
	{
		var options = {
	        chart: {
	        	renderTo: 'container', 
	        	borderColor: '#EBBA95',
	            borderRadius: 20,
	            borderWidth: 2,
	            type: 'line'
	        },
	        credits:{
	            enabled:false // 禁用版权信息
	        },
	        title: {
	            text: '天气预报'
	        },
	        subtitle: {
	            text: 'www.xiongdaojia.com'
	        },
	        xAxis: {
	        	title: {
	        		text : '月份' 
	        	},
	            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun',
	                'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
                plotLines:[{
                    color:'red',            //线的颜色，定义为红色
                    dashStyle:'longdashdot',//标示线的样式，默认是solid（实线），这里定义为长虚线
                    value:3,                //定义在那个值上显示标示线，这里是在x轴上刻度为3的值处垂直化一条线
                    width:2                 //标示线的宽度，2px
                }]
	        },
	        yAxis: {
	            title: {
	                text: '温度'
	            },
	            labels: {
	                formatter: function () {
	                    return this.value + '°';
	                }
	            },
	            plotLines:[{
	                color:'red',           //线的颜色，定义为红色
	                dashStyle:'solid',     //默认值，这里定义为实线
	                value:3,               //定义在那个值上显示标示线，这里是在x轴上刻度为3的值处垂直化一条线
	                width:2                //标示线的宽度，2px
	            }]
	        },
	        tooltip: {
	            crosshairs: true,
	            shared: true
	        },
	        plotOptions: {
	            spline: {
	                marker: {
	                    radius: 4,
	                    lineColor: '#666666',
	                    lineWidth: 1
	                }
	            },
	            line: {
	                dataLabels: {
	                    enabled: false
	                },
	            },
	            series: {
	            	events: {
	            		legendItemClick: function (event) {
	            			if(this.name == "Tokyo"){
	            				chart.series[0].setData([129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4],true);
		            			chart.xAxis[0].setCategories(['J', 'F', 'M', 'A', 'M', 'J', 'J', 'A', 'S'],true);
		            			chart.xAxis[0].setTitle({text: '字母'},true);
		            		}
	            			return false;
	            		}
	            	}
	            }
	        },
	        legend: {
	            layout: 'vertical',
	            align: 'right',
	            verticalAlign: 'top',
	            x: -40,
	            y: 00,
	            floating: true,
	            borderWidth: 1,
	            backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),
	            shadow: true
	        },
	        series: [{
	            name: 'Tokyo',
	            marker: {
	                symbol: 'square'
	            },
	            data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 36.5, 23.3, 18.3, 13.9, 9.6]
	        }, {
	            name: 'London',
	            marker: {
	                symbol: 'diamond'
	            },
	            data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
	        }]
	    };
		chart = new Highcharts.Chart(options);
	},
	bandBtnClick:function(){
		var _this = this;
		$(".inputList .btnSearch").click(function(){
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
			var target = $("#target").val();
			var startTime = $("#startTime").val();
			var endTime = $("#endTime").val();
		/*	if(area == "请选择"){
				alert("请选择区域");
				return;
			}*/
			var param = {"area":area, "room":room, "equipment":equipment, "target":target, "startTime":startTime, "endTime":endTime};
			var params = "monitor/MonitorController/doDownloadModelFile?equipment="+equipment+"&target="+target+"&startTime="+startTime+"&endTime="+endTime+"&area="+area+"&room="+room+"&type="+dmh;
			$("#excelA").attr("href", params);
			_this.requestRemoteData("monitor/MonitorController/getChart", param, "get", function(data){
				var options = {
				        chart: {
				        	renderTo: 'container', 
				            borderColor: '#EBBA95',
				            borderRadius: 20,
				            borderWidth: 2,
				            type: 'line'
				        },
				        title: data.highchartsBean.title,
				        //subtitle: data.highchartsBean.subtitle,
				        xAxis: data.highchartsBean.xAxis,
				        yAxis: data.highchartsBean.yAxis,
				        tooltip: {
				            crosshairs: true,
				            shared: true
				        },
				        plotOptions: {
				            spline: {
				                marker: {
				                    radius: 4,
				                    lineColor: '#666666',
				                    lineWidth: 1
				                }
				            },
				            line: {
				                dataLabels: {
				                    enabled: true
				                },
				            },
				            series: {
				            	events: {
				            		legendItemClick: function (event) {
				            			//_this.removeData(chart.series);
				            			if(this.name == "详细"){
				            				dmh = null;
				            				//chart.series[0].setData(data.highchartsBean.series,true);
				            				_this.setDataSeries(data.highchartsBean.series, chart.series);
				            				chart.xAxis[0].setCategories(data.highchartsBean.xAxis.categories,true);
				            				chart.xAxis[0].setTitle({text: '时间'},true);
				            				//填写日期记录数据
				            				_this.packageRecordTable(data.dateTime);
				            				var params = "monitor/MonitorController/doDownloadModelFile?equipment="+equipment+"&target="+target+"&startTime="+startTime+"&endTime="+endTime+"&area="+area+"&room="+room+"&type="+dmh;
				            				$("#excelA").attr("href", params);
				            				return false;
				            			}else if(this.name == "日期"){
				            				dmh = 'D';
				            				_this.setDataSeries(data.highchartsBean.seriesDay, chart.series);
				            				chart.xAxis[0].setCategories(data.highchartsBean.categoriesDay,true);
				            				chart.xAxis[0].setTitle({text: '日期'},true);
				            				//填写日期记录数据
				            				_this.packageRecordTable(data.dayTable);
				            				var params = "monitor/MonitorController/doDownloadModelFile?equipment="+equipment+"&target="+target+"&startTime="+startTime+"&endTime="+endTime+"&area="+area+"&room="+room+"&type="+dmh;
				            				$("#excelA").attr("href", params);
				            				return false;
				            			}else if(this.name == "月份"){
				            				dmh = 'M';
				            				_this.setDataSeries(data.highchartsBean.seriesMonth, chart.series);
				            				chart.xAxis[0].setCategories(data.highchartsBean.categoriesMonth,true);
				            				chart.xAxis[0].setTitle({text: '月份'},true);
				            				//填写月份记录数据
				            				_this.packageRecordTable(data.monthTable);
				            				var params = "monitor/MonitorController/doDownloadModelFile?equipment="+equipment+"&target="+target+"&startTime="+startTime+"&endTime="+endTime+"&area="+area+"&room="+room+"&type="+dmh;
				            				$("#excelA").attr("href", params);
				            				return false;
				            			}else if(this.name == "小时"){
				            				dmh = 'H';
				            				_this.setDataSeries(data.highchartsBean.seriesHour, chart.series);
				            				chart.xAxis[0].setCategories(data.highchartsBean.categoriesHour,true);
				            				chart.xAxis[0].setTitle({text: '小时'},true);
				            				//填写小时记录数据
				            				_this.packageRecordTable(data.hourTable);
				            				var params = "monitor/MonitorController/doDownloadModelFile?equipment="+equipment+"&target="+target+"&startTime="+startTime+"&endTime="+endTime+"&area="+area+"&room="+room+"&type="+dmh;
				            				$("#excelA").attr("href", params);
				            				return false;
				            			}else{
				            				//return false;
				            			}
				            		}
				            	}
				            }
				        },
		                legend: {
		     	            layout: 'horizontal',
		     	            align: 'center',
		     	            verticalAlign: 'bottom',
		     	            x: -40,
		     	            y: 10,
		     	            floating: true,
		     	            borderWidth: 1,
		     	            backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),
		     	            shadow: true
		     	        },
		     	        series: data.highchartsBean.series
				        /*series: [
			                 {name: '详细', data : data.highchartsBean.series },
			                 {name: '日期'},
			                 {name: '月份'},
			                 {name: '小时'},
			            ]*/
				    };
				chart = new Highcharts.Chart(options);
				//默认填写 日期的数据记录
				_this.packageRecordTable(data.dateTime);
			});
		});
	},
	packageRecordTable:function(tableData){
		var html = "";
		$("#recordTotalPage").html(Math.ceil((tableData.length-1)/10));
		for(var i = 0;i < tableData.length>10?11:tableData.length; i++){
			var row = tableData[i];
			if(row == undefined){
				break;
			}
			html += "<tr>";
			for(var j = 0;j < row.length; j++){
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
		$("#stageTable").html(html);
	},
	removeData:function(object){
		for(var i = 0;i < object.length; i++)
		{
			if(i == object.length-1){
				object[i].remove();			}
		}
	},
	setDataSeries:function(seriesNew, seriesOld){
		for(var i = 0;i < seriesNew.length; i++)
		{
			for(var j = 0;j < seriesOld.length; j++)
			{
				if(i == j){
					seriesOld[i].setData(seriesNew[i].data,true);
				}
			}
		}
	},
	requestRemoteData:function(url, data, type, callback){
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
};

$(function()
{
	var highCharts = new HighCharts();
	var char;//type默认为d
	dmh = null;
});
