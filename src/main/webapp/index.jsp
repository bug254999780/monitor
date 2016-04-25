<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<!-- saved from url=(0047)http://10.7.234.238:8080/monitor/realtime/list -->
<HTML>
<HEAD lang=zh-cn>
<TITLE>电信机房检测平台----实施检测</TITLE>
<META charset=utf-8>
<META content=IE=edge,chrome=1 http-equiv=X-UA-Compatible>
<META name=renderer content=webkit>
<LINK rel=stylesheet href="电信机房检测平台----实施检测_files/telecomMonitor.css">
<META name=GENERATOR content="MSHTML 8.00.6001.23588">
</HEAD>
<BODY>
	<!--#include file="_siteTop.shtml"-->
	<DIV class=site-top>
		<DIV class=site-top-bd>
			<DIV class=site-l>
				<IMG class=site-logo src="电信机房检测平台----实施检测_files/logo.png">
			</DIV>
			<DIV class=site-r>
				<DIV id=Site_Nav class=site-nav>
					<UL class=site-nav-bd>
						<LI><A class=j_SiteNav
							href="http://10.7.234.238:8080/monitor/" data-spm="0">首页</A></LI>
						<LI><A class=j_SiteNav
							href="http://10.7.234.238:8080/monitor/realtime/list"
							data-spm="1">实施检测</A></LI>
						<LI><A class=j_SiteNav
							href="http://10.7.234.238:8080/monitor/notice/list" data-spm="2">公告</A></LI>
						<LI><A class=j_SiteNav
							href="http://10.7.234.238:8080/monitor/warnRecord/list"
							data-spm="3">警告记录</A></LI>
						<LI><A class=j_SiteNav
							href="http://10.7.234.238:8080/monitor/stat/list" data-spm="4">统计报表</A></LI>
						<LI><A class=j_SiteNav
							href="http://10.7.234.238:8080/freeway/jsp/system/login.jsp"
							data-spm="5">系统操作</A></LI>
					</UL>
				</DIV>
			</DIV>
		</DIV>
	</DIV>
	<DIV id=Site_Wrap class="search detection" data-spm="1">
		<DIV class=view-option>
			<DIV class="tab-links view-option-bd site-wrap">
				<DIV class="tab-link active" data-href="#tab1">
					<I class="computer-icon icons"></I><SPAN class=tab-title>主机</SPAN>
					<I class=line></I>
				</DIV>
				<DIV class="tab-link last" data-href="#tab2">
					<I class="gyq-icon icons"></I><SPAN class=tab-title>传感器</SPAN> <I
						class=line></I>
				</DIV>
			</DIV>
		</DIV>
		
	</DIV>
	<SCRIPT src="电信机房检测平台----实施检测_files/jquery.1.11.3.min.js"></SCRIPT>

	<SCRIPT charset=utf-8 src="电信机房检测平台----实施检测_files/plugins.js"></SCRIPT>

	<SCRIPT charset=utf-8 src="电信机房检测平台----实施检测_files/WdatePicker.js"></SCRIPT>

	<SCRIPT src="电信机房检测平台----实施检测_files/echarts.js"></SCRIPT>

	<SCRIPT>
		$(document)
				.ready(
						function() {
							//更新机房下拉框
							$("#_area_id_select")
									.chosen()
									.change(
											function() {
												var areaId = $(this).val();
												if (areaId != ''
														&& areaId != null
														&& areaId != 'undefined') {
													$
															.ajax({
																type : "POST",
																url : "/monitor/srv/getRoomByAreaId",
																data : "areaId="
																		+ areaId,
																success : function(
																		data) {
																	var json = eval(data);
																	var _html = '<option value="">---------- 请选择 ----------</option>';
																	for (var i = 0; i < json.length; i++) {
																		_html += '<option value="'+json[i].labid+'">'
																				+ json[i].labname
																				+ '</option>';
																	}
																	$(
																			"#_room_id_select")
																			.html(
																					_html);
																	$(
																			"#_room_id_select")
																			.trigger(
																					"chosen:updated");
																}
															});
												}
											});

							//更新设备主机
							$("#_room_id_select")
									.chosen()
									.change(
											function() {
												var roomId = $(this).val();
												if (roomId != ''
														&& roomId != null
														&& roomId != 'undefined') {
													$
															.ajax({
																type : "POST",
																url : "/monitor/srv/getHostByRoomId",
																data : "roomId="
																		+ roomId,
																success : function(
																		data) {
																	var json = eval(data);
																	var _html = '<option value="">---------- 请选择 ----------</option>';
																	for (var i = 0; i < json.length; i++) {
																		_html += '<option value="'+json[i].id+'">'
																				+ json[i].name
																				+ '</option>';
																	}
																	$(
																			"#_host_id_select")
																			.html(
																					_html);
																	$(
																			"#_host_id_select")
																			.trigger(
																					"chosen:updated");
																}
															});
												}
											});

							//查询
							$("#_query")
									.click(
											function() {
												var areaId = $(
														"#_area_id_select")
														.val();
												var roomId = $(
														"#_room_id_select")
														.val();
												var hostId = $(
														"#_host_id_select")
														.val();
												var itemId = $(
														"#_item_id_select")
														.val();
												var bdate = $("#bdate").val();
												var edate = $("#edate").val();
												if (areaId == ''
														|| areaId == null
														|| areaId == 'undefined') {
													alert('请选择区域');
													return;
												}
												if (roomId == ''
														|| roomId == null
														|| roomId == 'undefined') {
													alert('请选择机房');
													return;
												}
												if (hostId == ''
														|| hostId == null
														|| hostId == 'undefined') {
													alert('请选择设备');
													return;
												}
												if (itemId == ''
														|| itemId == null
														|| itemId == 'undefined') {
													alert('请选择监控项');
													return;
												}
												if (bdate == ''
														|| bdate == null
														|| bdate == 'undefined') {
													alert('请选择开始时间');
													return;
												}
												if (edate == ''
														|| edate == null
														|| edate == 'undefined') {
													alert('请选择结束时间');
													return;
												}
												$
														.ajax({
															type : "POST",
															dataType : "text",
															url : "/monitor/realtime/ajax_realtime_data",
															data : "bdate="
																	+ $(
																			"#bdate")
																			.val()
																	+ "&edate="
																	+ $(
																			"#edate")
																			.val()
																	+ "&item="
																	+ $(
																			"#_item_id_select option:selected")
																			.text()
																	+ "&host="
																	+ $(
																			"#_host_id_select option:selected")
																			.text()
																	+ "&itemId="
																	+ $(
																			"#_item_id_select")
																			.val()
																	+ "&hostId="
																	+ $(
																			"#_host_id_select")
																			.val(),
															success : function(
																	json) {

																option = eval('('
																		+ json
																		+ ')');
																// 路径配置
																require
																		.config({
																			paths : {
																				echarts : '/monitor/styles/echarts/build/dist'
																			}
																		});
																//使用
																require(
																		[
																				'echarts',
																				'echarts/chart/line', // 使用柱状图就加载bar模块，按需加载
																				'echarts/chart/bar' ],
																		function(
																				ec) {
																			// 基于准备好的dom，初始化echarts图表
																			var myChart = ec
																					.init(document
																							.getElementById('main_line'));
																			// 为echarts对象加载数据 
																			myChart
																					.setOption(option);
																		});
															}
														});
											});

						});
	</SCRIPT>
</BODY>
</HTML>
