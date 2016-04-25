package com.telecom.controller;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.telecom.base.HighchartsBean;
import com.telecom.model.WarningRecord;
import com.telecom.services.MonitorService;
import com.telecom.services.WarningRecordService;
import com.telecom.util.CacheUtil;
import com.telecom.util.DateUtil;
import com.telecom.util.JsonUtils;
import com.telecom.util.PageUtil;


@Controller
@RequestMapping("MonitorController")
public class MonitorController {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(MonitorController.class);

	@Autowired
	MonitorService monitorService;

	@Autowired
	WarningRecordService warningRecordService;

	@RequestMapping("toRealTimeMonitor")
	public ModelAndView toRealTimeMonitor(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		// 区域
		mav.addObject("area", monitorService.getAreaInfoList());
		mav.setViewName("realTimeMonitor");
		return mav;
	}

	@RequestMapping("getRoom")
	public ModelAndView getRoom(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String areaCode = request.getParameter("areaCode");
		List<String> paramAreaCode = new ArrayList<String>();
		if(areaCode != null && areaCode.trim().length() > 0){
			for (String str : areaCode.split(",")) {
				paramAreaCode.add(str);
			}
		}
		// 机房
		mav.addObject("room", monitorService.getMacshineroomInfoList(paramAreaCode));
		mav.setViewName(null);
		return mav;
	}

	@RequestMapping("getEquipment")
	public ModelAndView getEquipment(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String devicemacshineroomcode = request.getParameter("devicemacshineroomcode");
		List<String> paramCode = new ArrayList<String>();
		if(devicemacshineroomcode != null && devicemacshineroomcode.trim().length() > 0){
			for (String str : devicemacshineroomcode.split(",")) {
				paramCode.add(str);
			}
		}
		// 设备
		mav.addObject("equipment", monitorService.getDeviceInfoList(paramCode));
		mav.setViewName(null);
		return mav;
	}

	@RequestMapping("getTarget")
	public ModelAndView getTarget(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String equipmentType = request.getParameter("equipmentType");
		// 指标
		mav.addObject("target", monitorService.getThresholdManageList(equipmentType));
		mav.setViewName(null);
		return mav;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("getChart")
	public ModelAndView toGetChart(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();

		String area = request.getParameter("area");
		String areaCode = "";
		String areaName = "";
		String room = request.getParameter("room");
		String roomCode = "";
		String roomName = "";
		String equipment = request.getParameter("equipment");
		String equipmentCode = "";
		String target = request.getParameter("target");// 001温度, 002湿度
		String targetCode = "";
		String targetName = "";
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		
		if(area != null && area.length() > 0){
			area = new String(area.getBytes("iso8859-1"),"UTF-8");
			if(!"请选择".equals(target)){
				areaCode = area.split("@")[0];
				areaName = area.split("@")[1];
			}
		}
		
		if(room != null && room.length() > 0){
			room = new String(room.getBytes("iso8859-1"),"UTF-8");
			if(!"请选择".equals(target)){
				roomCode = room.split("@")[0];
				roomName = room.split("@")[1];
			}
		}
		if(target != null && target.length() > 0){
			target = new String(target.getBytes("iso8859-1"),"UTF-8");
			if(!"请选择".equals(target)){
				targetCode = target.split("@")[0];
				targetName = target.split("@")[1];
			}else{
				targetCode = "001";//默认为001
				targetName = "温度";//默认为温度
			}
		}else{
			targetCode = "001";		//默认为001
			targetName = "温度";		//默认为温度
		}
		if(equipment != null && equipment.length() > 0){
			equipment = new String(equipment.getBytes("iso8859-1"),"UTF-8");
			if(!"请选择".equals(target)){
				equipmentCode = equipment.split("@")[0];
			}
		}
		if (startTime == null || startTime.trim().length() < 1) {
			startTime = null;
		}
		if (endTime == null || endTime.trim().length() < 1) {
			endTime = null;
		}

		Map<String, Object> params = new HashMap<String, Object>();
		createMap(params, "areacode", areaCode);
		createMap(params, "macshineroomcode", roomCode);
		createMap(params, "typecode", targetCode);
		createMap(params, "equipmentcode", equipmentCode);
		createMap(params, "startTime", startTime);
		createMap(params, "endTime", endTime);
		List<Object> tr = null;
		List<Object> trMonthList = new ArrayList<Object>();
		List<Object> trDayList = new ArrayList<Object>();
		List<Object> trHourList = new ArrayList<Object>();
		//温度 001-- 湿度002
		if("001".equals(targetCode) || "002".equals(targetCode)){
			tr = monitorService.getTemperatureRecordListgb(params);
			createMap(params, "type", "M");//月份数据
			//trMonthList = monitorService.getTemperatureRecordListgb(params);
			createMap(params, "type", "D");//天数据
			//trDayList = monitorService.getTemperatureRecordListgb(params);
			createMap(params, "type", "H");//小时的数据
			//trHourList = monitorService.getTemperatureRecordListgb(params);
		}
		//服务器数据  003 memory ，004 cpu, 005 desk
		if("003".equals(targetCode) || "004".equals(targetCode) || "005".equals(targetCode)){
			tr = monitorService.getServerRecordListgb(params);
			createMap(params, "type", "M");//月份数据
			//trMonthList = monitorService.getServerRecordListgb(params);
			createMap(params, "type", "D");//天数据
			//trDayList = monitorService.getServerRecordListgb(params);
			createMap(params, "type", "H");//小时的数据
			//trHourList = monitorService.getServerRecordListgb(params);
		}
		//交换机数据  006 networkdelayrate 网络延迟率, 007 packetsloss  丢包率
		if("006".equals(targetCode) || "007".equals(targetCode)){
			tr = monitorService.getSwitchesrecordRecordListgb(params);
			createMap(params, "type", "M");//月份数据
			//trMonthList = monitorService.getSwitchesrecordRecordListgb(params);
			createMap(params, "type", "D");//天数据
			//trDayList = monitorService.getSwitchesrecordRecordListgb(params);
			createMap(params, "type", "H");//小时的数据
			//trHourList = monitorService.getSwitchesrecordRecordListgb(params);
		}
		
		//封装HighchartsBean  温度、湿度
		HighchartsBean hb = new HighchartsBean();
		hb.setTitle(createMap("text", targetName));
		hb.setSubtitle(createMap("text", ""));
		List<Object> categories = new ArrayList<Object>();
		Map<String , List<Object>> seriesMap = new LinkedHashMap<String, List<Object>>();
		//日期数据
		for (Object object : tr) {
			Map<String, Object> dataMap = (Map<String, Object>) object;
			String roomNameData = "";
			if(dataMap.get("roomName")!=null){
				roomNameData = dataMap.get("roomName").toString();
			}
			//温度 001-- 湿度002
			if ("001".equals(targetCode)) {
				createSeries(seriesMap, roomNameData, Float.parseFloat(dataMap.get("temperatures")+""));
			}
			if ("002".equals(targetCode)) {
				createSeries(seriesMap, roomNameData, Float.parseFloat(dataMap.get("humidity")+""));
			}
			//服务器数据  003 memory ，004 cpu, 005 desk
			if ("003".equals(targetCode)) {
				createSeries(seriesMap, roomNameData, Float.parseFloat(dataMap.get("memory")+""));
			}
			if ("004".equals(targetCode)) {
				createSeries(seriesMap, roomNameData, Float.parseFloat(dataMap.get("cpu")+""));
			}
			if ("005".equals(targetCode)) {
				createSeries(seriesMap, roomNameData, Float.parseFloat(dataMap.get("desk")+""));
			}
			//交换机数据  006 networkdelayrate 网络延迟率, 007 packetsloss  丢包率
			if ("006".equals(targetCode)) {
				createSeries(seriesMap, roomNameData, Float.parseFloat(dataMap.get("networkdelayrate")+""));
			}
			if ("007".equals(targetCode)) {
				createSeries(seriesMap, roomNameData, Float.parseFloat(dataMap.get("packetsloss")+""));
			}
			// 横坐标
			categories.add(dataMap.get("receiveTimes"));
			//categories.add("");//TODO 取消X坐标
		}
		//createSeries(seriesMap, "详细", "");
		//TODO 去掉日期、月份、小时 2016-2-25 22:11:21 lichenyi 
		//createSeries(seriesMap, "日期", "");
		//createSeries(seriesMap, "月份", "");
		//createSeries(seriesMap, "小时", "");
		/*
		//月份数据
		List<Object> categoriesMonth = new ArrayList<Object>();
		Map<String , List<Object>> seriesMonthMap = new LinkedHashMap<String, List<Object>>();
		for (Object object : trMonthList) {
			Map<String, Object> dataMap = (Map<String, Object>) object;
			String roomNameData = "";
			if(dataMap.get("roomName")!=null){
				roomNameData = dataMap.get("roomName").toString();
			}
			//温度 001-- 湿度002
			if ("001".equals(targetCode)) {
				createSeries(seriesMonthMap, roomNameData, dataMap.get("temperatures"));
			}
			if ("002".equals(targetCode)) {
				createSeries(seriesMonthMap, roomNameData, dataMap.get("humidity"));
			}
			//服务器数据  003 memory ，004 cpu, 005 desk
			if ("003".equals(targetCode)) {
				createSeries(seriesMonthMap, roomNameData, dataMap.get("memory"));
			}
			if ("004".equals(targetCode)) {
				createSeries(seriesMonthMap, roomNameData, dataMap.get("cpu"));
			}
			if ("005".equals(targetCode)) {
				createSeries(seriesMonthMap, roomNameData, dataMap.get("desk"));
			}
			//交换机数据  006 networkdelayrate 网络延迟率, 007 packetsloss  丢包率
			if ("006".equals(targetCode)) {
				createSeries(seriesMonthMap, roomNameData, dataMap.get("networkdelayrate"));
			}
			if ("007".equals(targetCode)) {
				createSeries(seriesMonthMap, roomNameData, dataMap.get("packetsloss"));
			}
			// 横坐标
			categoriesMonth.add(dataMap.get("receiveTimes"));
		}
		createSeries(seriesMonthMap, "详细", "");
		//createSeries(seriesMonthMap, "日期", "");
		//createSeries(seriesMonthMap, "月份", "");
		//createSeries(seriesMonthMap, "小时", "");
		hb.setSeriesMonth(map2Series(seriesMonthMap));
		hb.setCategoriesMonth(categoriesMonth);
		//天数据
		List<Object> categoriesDay = new ArrayList<Object>();
		Map<String , List<Object>> seriesDayMap = new LinkedHashMap<String, List<Object>>();
		for (Object object : trDayList) {
			Map<String, Object> dataMap = (Map<String, Object>) object;
			String roomNameData = "";
			if(dataMap.get("roomName")!=null){
				roomNameData = dataMap.get("roomName").toString();
			}
			//温度 001-- 湿度002
			if ("001".equals(targetCode)) {
				createSeries(seriesDayMap, roomNameData, dataMap.get("temperatures"));
			}
			if ("002".equals(targetCode)) {
				createSeries(seriesDayMap, roomNameData, dataMap.get("humidity"));
			}
			//服务器数据  003 memory ，004 cpu, 005 desk
			if ("003".equals(targetCode)) {
				createSeries(seriesDayMap, roomNameData, dataMap.get("memory"));
			}
			if ("004".equals(targetCode)) {
				createSeries(seriesDayMap, roomNameData, dataMap.get("cpu"));
			}
			if ("005".equals(targetCode)) {
				createSeries(seriesDayMap, roomNameData, dataMap.get("desk"));
			}
			//交换机数据  006 networkdelayrate 网络延迟率, 007 packetsloss  丢包率
			if ("006".equals(targetCode)) {
				createSeries(seriesDayMap, roomNameData, dataMap.get("networkdelayrate"));
			}
			if ("007".equals(targetCode)) {
				createSeries(seriesDayMap, roomNameData, dataMap.get("packetsloss"));
			}
			// 横坐标
			categoriesDay.add(dataMap.get("receiveTimes"));
			
		}
		createSeries(seriesDayMap, "详细", "");
		createSeries(seriesDayMap, "日期", "");
		createSeries(seriesDayMap, "月份", "");
		createSeries(seriesDayMap, "小时", "");
		hb.setSeriesDay(map2Series(seriesDayMap));
		hb.setCategoriesDay(categoriesDay);
		//小时数据
		List<Object> categoriesHour = new ArrayList<Object>();
		Map<String , List<Object>> seriesHourMap = new LinkedHashMap<String, List<Object>>();
		for (Object object : trHourList) {
			Map<String, Object> dataMap = (Map<String, Object>) object;
			String roomNameData = "";
			if(dataMap.get("roomName")!=null){
				roomNameData = dataMap.get("roomName").toString();
			}
			//温度 001-- 湿度002
			if ("001".equals(targetCode)) {
				createSeries(seriesHourMap, roomNameData, dataMap.get("temperatures"));
			}
			if ("002".equals(targetCode)) {
				createSeries(seriesHourMap, roomNameData, dataMap.get("humidity"));
			}
			//服务器数据  003 memory ，004 cpu, 005 desk
			if ("003".equals(targetCode)) {
				createSeries(seriesHourMap, roomNameData, dataMap.get("memory"));
			}
			if ("004".equals(targetCode)) {
				createSeries(seriesHourMap, roomNameData, dataMap.get("cpu"));
			}
			if ("005".equals(targetCode)) {
				createSeries(seriesHourMap, roomNameData, dataMap.get("desk"));
			}
			//交换机数据  006 networkdelayrate 网络延迟率, 007 packetsloss  丢包率
			if ("006".equals(targetCode)) {
				createSeries(seriesHourMap, roomNameData, dataMap.get("networkdelayrate"));
			}
			if ("007".equals(targetCode)) {
				createSeries(seriesHourMap, roomNameData, dataMap.get("packetsloss"));
			}
			// 横坐标
			categoriesHour.add(dataMap.get("receiveTimes"));
		}
		createSeries(seriesHourMap, "详细", "");
		createSeries(seriesHourMap, "日期", "");
		createSeries(seriesHourMap, "月份", "");
		createSeries(seriesHourMap, "小时", "");
		hb.setSeriesHour(map2Series(seriesHourMap));
		hb.setCategoriesHour(categoriesHour);
		*/
		
		
		//*******************************************封装highcharts数据
		// X坐标
		Map<String, Object> xAxis = new HashMap<String, Object>();
		//TODO 去掉X轴
		createMap(xAxis, "categories", categories);  //TODO 2016-2-29 16:47:49  lichenyi
		createMap(xAxis, "gridLineWidth", "1");//变表格
		createMap(xAxis, "min", "0");//X轴最小值
		createMap(xAxis, "title", createMap("text", "日期"));
		List<Object> plotLinesX = new ArrayList<Object>();
		Map<String, Object> plotLinesMapX = new HashMap<String, Object>();
		plotLinesX.add(createMap(plotLinesMapX, "color", "red"));
		plotLinesX.add(createMap(plotLinesMapX, "dashStyle", "longdashdot"));
		plotLinesX.add(createMap(plotLinesMapX, "value", "3"));
		plotLinesX.add(createMap(plotLinesMapX, "width", "2"));
		// createMap(xAxis, "plotLines", plotLinesX);
		hb.setxAxis(xAxis);

		// Y坐标
		Map<String, Object> yAxis = new HashMap<String, Object>();
		if ("001".equals(targetCode)) {
			createMap(yAxis, "title", createMap("text", "温度"));
		}
		if ("002".equals(targetCode)) {
			createMap(yAxis, "title", createMap("text", "湿度"));
		}

		List<Object> plotLinesY = new ArrayList<Object>();
		Map<String, Object> plotLinesMapY = new HashMap<String, Object>();
		plotLinesY.add(createMap(plotLinesMapY, "color", "red"));
		plotLinesY.add(createMap(plotLinesMapY, "dashStyle", "longdashdot"));
		//plotLinesY.add(createMap(plotLinesMapY, "value", 0));//中线的值
		plotLinesY.add(createMap(plotLinesMapY, "width", "2"));
		//createMap(yAxis, "plotLines", plotLinesY);
		hb.setyAxis(yAxis);

//		hb.setSeries(seriesMap);
		hb.setSeries(map2Series(seriesMap));
		// hb.setSeriesName("CPU");//图例
		System.out.println(JsonUtils.bean2json(hb));
		mav.addObject(hb);

		
		//******************************** 封装table数据  *******************************************
		//日期数据
		List<Object> dateTime = new ArrayList<Object>();
		for (int i = 0; i < tr.size(); i++) {
			Object object = tr.get(i);
			Map<String, Object> dataMap = (Map<String, Object>) object;
			List<String> row = new ArrayList<String>();
			if (i == 0) {
				row.add("地区");
				row.add("房间");
				//温度 001-- 湿度002
				if ("001".equals(targetCode)) {
					row.add("温度");
				}
				if ("002".equals(targetCode)) {
					row.add("湿度");
				}
				//服务器数据  003 memory ，004 cpu, 005 desk
				if ("003".equals(targetCode)) {
					row.add("memory");
				}
				if ("004".equals(targetCode)) {
					row.add("cpu");
				}
				if ("005".equals(targetCode)) {
					row.add("desk");
				}
				//交换机数据  006 networkdelayrate 网络延迟率, 007 packetsloss  丢包率
				if ("006".equals(targetCode)) {
					row.add("网络延迟率");
				}
				if ("007".equals(targetCode)) {
					row.add("丢包率");
				}
				row.add("时间");
				dateTime.add(row);
			}
			row = new ArrayList<String>();
			row.add(dataMap.get("areaName").toString());
			row.add(dataMap.get("roomName").toString());
			//温度 001-- 湿度002
			if ("001".equals(targetCode)) {
				row.add(dataMap.get("temperatures").toString());
			}
			if ("002".equals(targetCode)) {
				row.add(dataMap.get("humidity").toString());
			}
			//服务器数据  003 memory ，004 cpu, 005 desk
			if ("003".equals(targetCode)) {
				row.add(dataMap.get("memory").toString());
			}
			if ("004".equals(targetCode)) {
				row.add(dataMap.get("cpu").toString());
			}
			if ("005".equals(targetCode)) {
				row.add(dataMap.get("desk").toString());
			}
			//交换机数据  006 networkdelayrate 网络延迟率, 007 packetsloss  丢包率
			if ("006".equals(targetCode)) {
				row.add(dataMap.get("networkdelayrate").toString());
			}
			if ("007".equals(targetCode)) {
				row.add(dataMap.get("packetsloss").toString());
			}
			row.add(dataMap.get("receiveTimes").toString());
			dateTime.add(row);
		}
		mav.addObject("dateTime", dateTime);
		
		//封装月份数据
		List<Object> monthTable = new ArrayList<Object>();
		for (int i = 0; i < trMonthList.size(); i++) {
			Object object = trMonthList.get(i);
			Map<String, Object> dataMap = (Map<String, Object>) object;
			List<String> row = new ArrayList<String>();
			if (i == 0) {
				row.add("地区");
				row.add("房间");
				//温度 001-- 湿度002
				if ("001".equals(targetCode)) {
					row.add("温度");
				}
				if ("002".equals(targetCode)) {
					row.add("湿度");
				}
				//服务器数据  003 memory ，004 cpu, 005 desk
				if ("003".equals(targetCode)) {
					row.add("memory");
				}
				if ("004".equals(targetCode)) {
					row.add("cpu");
				}
				if ("005".equals(targetCode)) {
					row.add("desk");
				}
				//交换机数据  006 networkdelayrate 网络延迟率, 007 packetsloss  丢包率
				if ("006".equals(targetCode)) {
					row.add("网络延迟率");
				}
				if ("007".equals(targetCode)) {
					row.add("丢包率");
				}
				row.add("时间");
				monthTable.add(row);
			}
			row = new ArrayList<String>();
			row.add(dataMap.get("areaName").toString());
			row.add(dataMap.get("roomName").toString());
			//温度 001-- 湿度002
			if ("001".equals(targetCode)) {
				row.add(dataMap.get("temperatures").toString());
			}
			if ("002".equals(targetCode)) {
				row.add(dataMap.get("humidity").toString());
			}
			//服务器数据  003 memory ，004 cpu, 005 desk
			if ("003".equals(targetCode)) {
				row.add(dataMap.get("memory").toString());
			}
			if ("004".equals(targetCode)) {
				row.add(dataMap.get("cpu").toString());
			}
			if ("005".equals(targetCode)) {
				row.add(dataMap.get("desk").toString());
			}
			//交换机数据  006 networkdelayrate 网络延迟率, 007 packetsloss  丢包率
			if ("006".equals(targetCode)) {
				row.add(dataMap.get("networkdelayrate").toString());
			}
			if ("007".equals(targetCode)) {
				row.add(dataMap.get("packetsloss").toString());
			}
			row.add(dataMap.get("receiveTimes").toString());
			monthTable.add(row);
		}
		mav.addObject("monthTable", monthTable);
		//封装天数据
		List<Object> dayTable = new ArrayList<Object>();
		for (int i = 0; i < trDayList.size(); i++) {
			Object object = trDayList.get(i);
			Map<String, Object> dataMap = (Map<String, Object>) object;
			List<String> row = new ArrayList<String>();
			if (i == 0) {
				row.add("地区");
				row.add("房间");
				//温度 001-- 湿度002
				if ("001".equals(targetCode)) {
					row.add("温度");
				}
				if ("002".equals(targetCode)) {
					row.add("湿度");
				}
				//服务器数据  003 memory ，004 cpu, 005 desk
				if ("003".equals(targetCode)) {
					row.add("memory");
				}
				if ("004".equals(targetCode)) {
					row.add("cpu");
				}
				if ("005".equals(targetCode)) {
					row.add("desk");
				}
				//交换机数据  006 networkdelayrate 网络延迟率, 007 packetsloss  丢包率
				if ("006".equals(targetCode)) {
					row.add("网络延迟率");
				}
				if ("007".equals(targetCode)) {
					row.add("丢包率");
				}
				row.add("时间");
				dayTable.add(row);
			}
			row = new ArrayList<String>();
			row.add(dataMap.get("areaName").toString());
			row.add(dataMap.get("roomName").toString());
			//温度 001-- 湿度002
			if ("001".equals(targetCode)) {
				row.add(dataMap.get("temperatures").toString());
			}
			if ("002".equals(targetCode)) {
				row.add(dataMap.get("humidity").toString());
			}
			//服务器数据  003 memory ，004 cpu, 005 desk
			if ("003".equals(targetCode)) {
				row.add(dataMap.get("memory").toString());
			}
			if ("004".equals(targetCode)) {
				row.add(dataMap.get("cpu").toString());
			}
			if ("005".equals(targetCode)) {
				row.add(dataMap.get("desk").toString());
			}
			//交换机数据  006 networkdelayrate 网络延迟率, 007 packetsloss  丢包率
			if ("006".equals(targetCode)) {
				row.add(dataMap.get("networkdelayrate").toString());
			}
			if ("007".equals(targetCode)) {
				row.add(dataMap.get("packetsloss").toString());
			}
			row.add(dataMap.get("receiveTimes").toString());
			dayTable.add(row);
		}
		mav.addObject("dayTable", dayTable);
		//封装小时数据
		List<Object> hourTable = new ArrayList<Object>();
		for (int i = 0; i < trHourList.size(); i++) {
			Object object = trHourList.get(i);
			Map<String, Object> dataMap = (Map<String, Object>) object;
			List<String> row = new ArrayList<String>();
			if (i == 0) {
				row.add("地区");
				row.add("房间");
				//温度 001-- 湿度002
				if ("001".equals(targetCode)) {
					row.add("温度");
				}
				if ("002".equals(targetCode)) {
					row.add("湿度");
				}
				//服务器数据  003 memory ，004 cpu, 005 desk
				if ("003".equals(targetCode)) {
					row.add("memory");
				}
				if ("004".equals(targetCode)) {
					row.add("cpu");
				}
				if ("005".equals(targetCode)) {
					row.add("desk");
				}
				//交换机数据  006 networkdelayrate 网络延迟率, 007 packetsloss  丢包率
				if ("006".equals(targetCode)) {
					row.add("网络延迟率");
				}
				if ("007".equals(targetCode)) {
					row.add("丢包率");
				}
				row.add("时间");
				hourTable.add(row);
			}
			row = new ArrayList<String>();
			row.add(dataMap.get("areaName").toString());
			row.add(dataMap.get("roomName").toString());
			//温度 001-- 湿度002
			if ("001".equals(targetCode)) {
				row.add(dataMap.get("temperatures").toString());
			}
			if ("002".equals(targetCode)) {
				row.add(dataMap.get("humidity").toString());
			}
			//服务器数据  003 memory ，004 cpu, 005 desk
			if ("003".equals(targetCode)) {
				row.add(dataMap.get("memory").toString());
			}
			if ("004".equals(targetCode)) {
				row.add(dataMap.get("cpu").toString());
			}
			if ("005".equals(targetCode)) {
				row.add(dataMap.get("desk").toString());
			}
			//交换机数据  006 networkdelayrate 网络延迟率, 007 packetsloss  丢包率
			if ("006".equals(targetCode)) {
				row.add(dataMap.get("networkdelayrate").toString());
			}
			if ("007".equals(targetCode)) {
				row.add(dataMap.get("packetsloss").toString());
			}
			row.add(dataMap.get("receiveTimes").toString());
			hourTable.add(row);
		}
		mav.addObject("hourTable", hourTable);
		CacheUtil.detailInfo.put("detaiInfo",dateTime);//详细缓存
		CacheUtil.detailInfo.put("hourInfo", hourTable); //小时缓存
		CacheUtil.detailInfo.put("dayiInfo", dayTable);  //天缓存
		CacheUtil.detailInfo.put("monthInfo", monthTable);//月缓存
		mav.setViewName(null);
		return mav;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("toGetChartRecord")
	public ModelAndView toGetChartRecord(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
		String reqtype = request.getParameter("reqtype");
		if(reqtype == null || reqtype.length() < 1){
			reqtype = "detaiInfo";
		}
		
		String pageSiz = request.getParameter("pageSize");
		String pageNu = request.getParameter("pageNum");
		Integer pageSize = 10;
		Integer pageNum = 1;
		if(pageSiz != null && pageSiz.length() > 0 && !"null".equals(pageSiz)){
			pageSize = Integer.valueOf(pageSiz);
		}
		if(pageNu != null && pageNu.length() > 0 && !"null".equals(pageNu)){
			pageNum = Integer.valueOf(pageNu);
		}
		Integer startValue = (pageNum-1)*pageSize;
		Integer sizeValue = pageSize;
		
		List<Object>  data = new ArrayList<>();
		List<Object> tableTitle = new ArrayList<>();
		if(CacheUtil.detailInfo!=null){
			switch (reqtype) {
			case "detaiInfo":
					data=(ArrayList<Object>) ((ArrayList<Object>) CacheUtil.detailInfo.get("detaiInfo")).clone();
					tableTitle = (List<Object>) data.get(0);
				break;
			case "H":
				data=(ArrayList<Object>) ((ArrayList<Object>) CacheUtil.detailInfo.get("hourInfo")).clone();
				tableTitle = (List<Object>) data.get(0);
				break;
			case "D":
				data=(ArrayList<Object>) ((ArrayList<Object>) CacheUtil.detailInfo.get("dayiInfo")).clone();
				tableTitle = (List<Object>) data.get(0);
				break;
			case "M":
				data=(ArrayList<Object>) ((ArrayList<Object>) CacheUtil.detailInfo.get("monthInfo")).clone();
				tableTitle = (List<Object>) data.get(0);
				break;
			default:
				break;
			}

		}
		
		List<Object> dataRecord= new ArrayList<>();
		data.remove(0);
		
		int pagecount=data.size();
		
		if(startValue+sizeValue>pagecount){
			dataRecord=data.subList(startValue, pagecount);
		}else{
			dataRecord=data.subList(startValue, startValue+sizeValue);
		}
		dataRecord.add(0, tableTitle);//加表头
		PageUtil pu = new PageUtil(pageNum, pageSize, data.size(), dataRecord);
		mav.addObject("result", pu);
		return mav;
	}
	/**
	 * 获取数据同时跳向告警记录页面
	 * 
	 * @ModelAndView
	 * @author lichenyi 2015年11月8日 上午4:43:02
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("getAlarmRecord")
	public ModelAndView toGetRecord(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String code = request.getParameter("code");
		String pageSiz = request.getParameter("pageSize");
		String pageNu = request.getParameter("pageNum");
		Integer pageSize = 10;
		Integer pageNum = 1;
		if(pageSiz != null && pageSiz.length() > 0 && !"null".equals(pageSiz)){
			pageSize = Integer.valueOf(pageSiz);
		}
		if(pageNu != null && pageNu.length() > 0 && !"null".equals(pageNu)){
			pageNum = Integer.valueOf(pageNu);
		}
		if(code == null){
			code = "1";
		}
		Map mapcode = new HashMap(); //参数map
		String area = request.getParameter("area");
		String areaCode = "";
		String room = request.getParameter("room");
		String roomCode = "";
		String equipment = request.getParameter("equipment");
		String equipmentName = "";
		if(area != null && area.length() > 0){
			area = new String(area.getBytes("iso8859-1"), "UTF-8");
			areaCode = area.split("@")[0];
			mapcode.put("type","1");
			mapcode.put("AreaCode",areaCode);
		}
		if(room != null && room.length() > 0){
			room = new String(room.getBytes("iso8859-1"), "UTF-8");
			roomCode = room.split("@")[0];
			mapcode.put("type","2");
			mapcode.put("MacshineRoomCode",roomCode);
		}
		if(equipment != null && equipment.length() > 0){
			equipment = new String(equipment.getBytes("iso8859-1"), "UTF-8");
			equipmentName = equipment.split("@")[0];
			mapcode.put("type","3");
			mapcode.put("Device_code",equipmentName);
		}
		mapcode.put("code",code);
		//【全部】的
		mapcode.put("minvalue", (pageNum-1)*pageSize);
		mapcode.put("maxvalue", pageSize);
		PageUtil list = warningRecordService.getWarningRecordList(pageNum, pageSize, mapcode);
		if(list != null && !list.getList().isEmpty()){
			//sortDescMapByValue(list.getList());
		}
		//处理结果接
		this.parseResultList(list.getList(), equipmentName);
		mav.addObject("pageResult", list);
		
		// 区域
		mav.addObject("area", monitorService.getAreaInfoList());
		if(mapcode.get("minvalue") != null){
			mapcode.remove("minvalue");
		}
		if(mapcode.get("maxvalue") != null){
			mapcode.remove("maxvalue");
		}
		//【过滤】的
		List listnoLimit = (List) warningRecordService.getWarningRecordListNoLimit(mapcode);
		//处理结果
		this.parseResultList(listnoLimit, equipmentName);
		List pageList = new ArrayList();
		for (Object object : listnoLimit) {
			WarningRecord warningRecord = (WarningRecord) object;
			if("1".equals(warningRecord.getReserve3())){
				pageList.add(warningRecord);
			}
		}
		if(pageList != null && !pageList.isEmpty()){
			//sortDescMapByValue(pageList);
			Collections.reverse(pageList);
		}
		//开始分页  【过滤】
		if("1".equals(code)){
			List<Object> dataRecord= new ArrayList<>();
			int pagecount=pageList.size();
			Integer startValue = (pageNum-1)*pageSize;
			Integer sizeValue = pageSize;
			if(startValue+sizeValue>pagecount){
				dataRecord=pageList.subList(startValue, pagecount);
			}else{
				dataRecord=pageList.subList(startValue, startValue+sizeValue);
			}
			PageUtil pu = new PageUtil(pageNum, pageSize, pageList.size(), dataRecord);
			mav.addObject("pageList", pu);
		}
		mav.setViewName("alarmRecord");
		return mav;
	}
	
	public void sortDescMapByValue(List<Object> results) {
		Collections.sort(results, new Comparator<Object>() {
			private final Collator collator = Collator.getInstance(java.util.Locale.CHINA);
			public int compare(Object o1,Object o2) {
				WarningRecord warningRecord1 = (WarningRecord) o1;
				WarningRecord warningRecord2 = (WarningRecord) o2;
				return collator.compare(warningRecord1.getReceiveTime(), warningRecord2.getReceiveTime()) > 0 ? -1 : 1 ;
			}
		});
	}
	public void sortAscMapByValue(List<Object> results) {
		Collections.sort(results, new Comparator<Object>() {
			private final Collator collator = Collator.getInstance(java.util.Locale.CHINA);
			public int compare(Object o1,Object o2) {
				WarningRecord warningRecord1 = (WarningRecord) o1;
				WarningRecord warningRecord2 = (WarningRecord) o2;
				return collator.compare(warningRecord1.getReceiveTime(), warningRecord2.getReceiveTime());
			}
		});
	}
	
	public void parseResultList(List list, String equipmentName){
		Map<String, Object> map = new HashMap<String, Object>();
		if (list != null) {
			for (Object object : list) {
				WarningRecord warningRecord = (WarningRecord) object;
				warningRecord.setArea(warningRecord.getReserve2());//区域
				warningRecord.setRoom(warningRecord.getReserve1());//机房
				warningRecord.setEquipmentName(equipmentName);
				//(1：温湿度传感器；2：UPS监控器；3：服务器；4：交换机)
				int equipmentType = warningRecord.getEquipmentType();
				if(equipmentType == 1){
					warningRecord.setEquipmentName("温湿度传感器");
				}
				if(equipmentType == 2){
					warningRecord.setEquipmentName("UPS监控器");
				}
				if(equipmentType == 3){
					warningRecord.setEquipmentName("服务器");
				}
				if(equipmentType == 4){
					warningRecord.setEquipmentName("交换机");
				}
				
				String targetCode = warningRecord.getTargetCode();
				if("001".equals(targetCode)){
					warningRecord.setTargetName("温度");
				}
				if("002".equals(targetCode)){
					warningRecord.setTargetName("湿度");
				}
				//服务器数据  003 memory ，004 cpu, 005 desk
				if ("003".equals(targetCode)) {
					warningRecord.setTargetName("memory");
				}
				if ("004".equals(targetCode)) {
					warningRecord.setTargetName("cpu");
				}
				if ("005".equals(targetCode)) {
					warningRecord.setTargetName("desk");
				}
				//交换机数据  006 networkdelayrate 网络延迟率, 007 packetsloss  丢包率
				if ("006".equals(targetCode)) {
					warningRecord.setTargetName("网络延迟率");
				}
				if ("007".equals(targetCode)) {
					warningRecord.setTargetName("丢包率");
				}
				if ("008".equals(targetCode)) {
					warningRecord.setTargetName("UPS状态");
				}
				
				warningRecord.setMonitorValue(warningRecord.getMonitorValue());
				
				if(map.get(warningRecord.getEquipmentCode())==null ){
					
					if(map.get(warningRecord.getEquipmentCode() + "_Status")==null &&warningRecord.getDispose() ==null){
						map.put(warningRecord.getEquipmentCode(),
								warningRecord.getEquipmentCode());
						map.put(warningRecord.getEquipmentCode() + "_",
								0);
						map.put(warningRecord.getEquipmentCode() + "_Status",true);
					}else{
						map.put(warningRecord.getEquipmentCode(),
								warningRecord.getEquipmentCode());
						map.put(warningRecord.getEquipmentCode() + "_",
								DateUtil.retTimeInMillis(warningRecord.getReceiveTime()));
						map.put(warningRecord.getEquipmentCode() + "_Status",false);
					}
				}else{
					map.put(warningRecord.getEquipmentCode() + "_Status",false);
				}
				if(warningRecord.getEquipmentCode().equals(map.get(warningRecord.getEquipmentCode()))){
						if(map.get(warningRecord.getEquipmentCode()+"_")!=null ){
							if((boolean)map.get(warningRecord.getEquipmentCode() + "_Status")==true||(DateUtil.retTimeInMillis(warningRecord.getReceiveTime())-(Long.parseLong(map.get(warningRecord.getEquipmentCode()+"_").toString())) > 7200000) ){
								if(warningRecord.getDispose()==null ){
									warningRecord.setReserve3("1");//显示按钮
								}else{
									warningRecord.setReserve3("0");//不显示按钮
								}
								map.put(warningRecord.getEquipmentCode() + "_",
											DateUtil.retTimeInMillis(warningRecord.getReceiveTime()));
							}else{
								warningRecord.setReserve3("0");//不显示按钮
							}
						}else{
							warningRecord.setReserve3("1");//显示按钮
						}
				}else{
					warningRecord.setReserve3("1");//显示按钮
				}
			}
		}
	}

	@RequestMapping("updateAlarmRecord")
	public ModelAndView updateAlarmRecord(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String dataNo = request.getParameter("dataNo");
		WarningRecord wr = new WarningRecord();
		wr.setDataNo(Integer.valueOf(dataNo));
		wr.setDispose(System.currentTimeMillis());
		warningRecordService.updateWarningRecord(wr);
		mav.addObject("result", "success");
		mav.setViewName(null);
		return mav;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@RequestMapping("/doDownloadModelFile")
	public void doDownloadModelFile(String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		//获取参数并获取数据
		String area = request.getParameter("area");
		String areaCode = ""; 
		String areaName = "";
		String room = request.getParameter("room");
		String roomCode = "";
		String roomName = "";
		String equipment = request.getParameter("equipment");
		String equipmentCode = "";
		String equipmentName = "";
		String target = request.getParameter("target");// 001温度, 002湿度
		String targetCode = "";
		String targetName = "";
		String type = request.getParameter("type");		//d--日期, m--月份, h--小时	
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		area = new String(area.getBytes("iso8859-1"),"UTF-8");
		room = new String(room.getBytes("iso8859-1"),"UTF-8");
		target = new String(target.getBytes("iso8859-1"),"UTF-8");
		equipment = new String(equipment.getBytes("iso8859-1"),"UTF-8");
		if(area != null && area.length() > 0){
			if(!"请选择".equals(target)){
				areaCode = area.split("@")[0];
				areaName = area.split("@")[1];
			}
		}
		
		if(room != null && room.length() > 0){
			if(!"请选择".equals(target)){
				roomCode = room.split("@")[0];
				roomName = room.split("@")[1];
			}
		}
		if(target != null && target.length() > 0){
			if(!"请选择".equals(target)){
				targetCode = target.split("@")[0];
				targetName = target.split("@")[1];
			}else{
				targetCode = "001";//默认为001
				targetName = "温度";//默认为温度
			}
		}else{
			targetCode = "001";		//默认为001
			targetName = "温度";		//默认为温度
		}
		if(equipment != null && equipment.length() > 0){
			if(!"请选择".equals(target)){
				equipmentCode = equipment.split("@")[0];
			}
		}
		if (startTime == null || startTime.trim().length() < 1) {
			startTime = null;
		}
		if (endTime == null || endTime.trim().length() < 1) {
			endTime = null;
		}
		if(area != null && area.length() > 0){
			areaCode = area.split("@")[0]==null?"":area.split("@")[0];
			areaName = area.split("@")[1]==null?"":area.split("@")[1];
		}
		if(room != null && room.length() > 0){
			roomCode = room.split("@")[0]==null?"":room.split("@")[0];
			roomName = room.split("@")[1]==null?"":room.split("@")[1];
		}
		if(equipment != null && equipment.length() > 0){
			equipmentCode = equipment.split("@")[0]==null?"":equipment.split("@")[0];
			if(equipmentCode!=null && "1".equals(equipmentCode)){
				equipmentName = "温湿度传感器";
			}else if(equipmentCode!=null && "2".equals(equipmentCode)){
				equipmentName = "UPS监控器";
			}else if(equipmentCode!=null && "3".equals(equipmentCode)){
				equipmentName = "服务器";
			}else if(equipmentCode!=null && "4".equals(equipmentCode)){
				equipmentName = "交换机";
			}else{
				equipmentName = "";
			}
		}
		if (startTime == null || startTime.trim().length() < 1) {
			startTime = null;
		}
		if (endTime == null || endTime.trim().length() < 1) {
			endTime = null;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		createMap(params, "equipmentcode", equipmentCode);
		createMap(params, "startTime", startTime);
		createMap(params, "endTime", endTime);
		createMap(params, "areacode", areaCode);
		createMap(params, "macshineroomcode", roomCode);
		createMap(params, "typecode", targetCode);
		createMap(params, "equipmentcode", equipmentCode);
		createMap(params, "type", type);
		createMap(params, "startTime", startTime);
		createMap(params, "endTime", endTime);
		List<Object> tr = null;
		List<Object> trMonthList = null;
		List<Object> trHourList = null;
		//温度 001-- 湿度002
		if("001".equals(targetCode) || "002".equals(targetCode)){
			tr = monitorService.getTemperatureRecordListgb(params);
		}
		//服务器数据  003 memory ，004 cpu, 005 desk
		if("003".equals(targetCode) || "004".equals(targetCode) || "005".equals(targetCode)){
			tr = monitorService.getServerRecordListgb(params);
		}
		//交换机数据  006 networkdelayrate 网络延迟率, 007 packetsloss  丢包率
		if("006".equals(targetCode) || "007".equals(targetCode)){
			tr = monitorService.getSwitchesrecordRecordListgb(params);
		}
		
		//封装表头数据
		Map<String, String> tableTitle = new LinkedHashMap<String, String>();
		tableTitle.put("areaName", "地区");//设备名称
		tableTitle.put("roomName", "房间");//设备名称
		tableTitle.put("equipmentName", "设备名称");//设备名称
		//tableTitle.put("equipmentCode", "设备代码");//设备代码
		//温度 001-- 湿度002
		if ("001".equals(targetCode) || "请选择".equals(targetCode)) {
			tableTitle.put("data", "温度");//数据名称
		}
		if ("002".equals(targetCode)) {
			tableTitle.put("data", "湿度");//数据名称
		}
		//服务器数据  003 memory ，004 cpu, 005 desk
		if ("003".equals(targetCode)) {
			tableTitle.put("data", "memory");//数据名称
		}
		if ("004".equals(targetCode)) {
			tableTitle.put("data", "cpu");//数据名称
		}
		if ("005".equals(targetCode)) {
			tableTitle.put("data", "desk");//数据名称
		}
		//交换机数据  006 networkdelayrate 网络延迟率, 007 packetsloss  丢包率
		if ("006".equals(targetCode)) {
			tableTitle.put("data", "网络延迟率");//数据名称
		}
		if ("007".equals(targetCode)) {
			tableTitle.put("data", "丢包率");//数据名称
		}
		
		tableTitle.put("time", "时间");
		
		//导出结果集 先从缓存拿
		if(CacheUtil.detailInfo!=null){
			
		}else {
			//数据库查询
		}
		//封装数据
		List<LinkedHashMap<String, Object>> tableData = new ArrayList<LinkedHashMap<String,Object>>();
		
		for (int i = 0; i < tr.size(); i++) {
			Object object = tr.get(i);
			Map<String, Object> dataMap = (Map<String, Object>) object;
			Map<String, Object> map = new LinkedHashMap<String, Object>();
//			map.put("areaName", areaName);
//			map.put("roomName", roomName);
			map.put("areaName", dataMap.get("areaName"));
			map.put("roomName", dataMap.get("roomName"));
			map.put("equipmentName", equipmentName);
			//map.put("equipmentCode", temperatureRecord.getEquipmentcode());
			//温度 001-- 湿度002
			if ("001".equals(targetCode) || "请选择".equals(targetName)) {
				map.put("data", dataMap.get("temperatures"));//数据名称
			}
			if ("002".equals(targetCode)) {
				map.put("data", dataMap.get("humidity"));//数据名称
			}
			//服务器数据  003 memory ，004 cpu, 005 desk
			if ("003".equals(targetCode)) {
				map.put("data", dataMap.get("memory"));//数据名称
			}
			if ("004".equals(targetCode)) {
				map.put("data", dataMap.get("cpu"));//数据名称
			}
			if ("005".equals(targetCode)) {
				map.put("data", dataMap.get("desk"));//数据名称
			}
			//交换机数据  006 networkdelayrate 网络延迟率, 007 packetsloss  丢包率
			if ("006".equals(targetCode)) {
				map.put("data", dataMap.get("networkdelayrate"));//数据名称
			}
			if ("007".equals(targetCode)) {
				map.put("data", dataMap.get("packetsloss"));//数据名称
			}
			map.put("time", dataMap.get("receiveTimes"));
			tableData.add((LinkedHashMap<String, Object>) map);
		}
		try {
			// 文件名字
			String filename = areaName+"@"+roomName+"数据记录"+DateUtil.getTimestamp()+".xlsx";
			// (创建工作薄)
			XSSFWorkbook workbook = new XSSFWorkbook();
			// (创建sheet并给他一个名字)
			XSSFSheet sheet = workbook.createSheet("user");
			// 创建第一行
			XSSFRow rowTitle = sheet.createRow(0);
			for (int j = 0; j < tableTitle.size(); j++) {
				// (创建单元格)
				XSSFCell cell = rowTitle.createCell((short) (j));
				// (给单元格赋值)
				if (j == 0) {
					cell.setCellValue(tableTitle.get("areaName"));
				}
				if (j == 1) {
					cell.setCellValue(tableTitle.get("roomName"));
				}
				if (j == 2) {
					cell.setCellValue(tableTitle.get("equipmentName"));
				}
//				if (j == 3) {
//					cell.setCellValue(tableTitle.get("equipmentCode"));
//				}
				if (j == 3) {
					cell.setCellValue(tableTitle.get("data"));
				}
				if (j == 4) {
					cell.setCellValue(tableTitle.get("time"));
				}
			}
			for (int i = 0; i < tableData.size(); i++) {
				// (创建行)
				XSSFRow rowData = sheet.createRow(i + 1);
				LinkedHashMap<String, Object> map = tableData.get(i);
				for (int j = 0; j < tableTitle.size(); j++) {
					// (创建单元格)
					XSSFCell cell = rowData.createCell((short) (j));
					// (给单元格赋值)
					if (j == 0) {
						if(map.get("areaName") != null){
							cell.setCellValue(map.get("areaName").toString());//
						}else{
							cell.setCellValue("");//
						}
					}
					if (j == 1) {
						if(map.get("roomName") != null){
							cell.setCellValue(map.get("roomName").toString());//
						}else{
							cell.setCellValue("");//
						}
					}
					if (j == 2) {
						if(map.get("equipmentName") != null){
							cell.setCellValue(map.get("equipmentName").toString());//
						}else{
							cell.setCellValue("");//
						}
					}
//					if (j == 3) {
//						if(map.get("equipmentCode") != null){
//							cell.setCellValue(map.get("equipmentCode").toString());//
//						}else{
//							cell.setCellValue("");//
//						}
//					}
					if (j == 3) {
						if(map.get("data") != null){
							cell.setCellValue(map.get("data").toString());//
						}else{
							cell.setCellValue("");//
						}
					}
					if (j == 4) {
						if(map.get("time") != null){
							cell.setCellValue(map.get("time").toString());//
						}else{
							cell.setCellValue("");//
						}
					}
				}
			}

			response.setContentType("application/octet-stream;charset=ISO-8859-1");
			response.setHeader("Content-Disposition", "attachment;filename=\""
					+ new String(filename.getBytes("UTF-8"), "ISO-8859-1")
					+ "\"");
			// 用excel专门的方式进行导出
			workbook.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	private Map<String, Object> createMap(String key, Object value) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(key, value);
		return map;
	}

	private Map<String, Object> createMap(Map<String, Object> map, String key,
			Object value) {
		map.put(key, value);
		return map;
	}
	
	//**********************************************************************************************
	//******************************** 封装series数据
	//**********************************************************************************************
	private List<Object> seriesList = null;
	private void createSeries(Map<String , List<Object>> seriesMap, String key, Object value){
		if(seriesMap == null || seriesMap.get(key) == null){
			seriesList = new ArrayList<Object>();
		}else{
			seriesList = seriesMap.get(key);
		}
		seriesList.add(value);
		seriesMap.put(key, seriesList);
	}
	private List<Object> map2Series(Map<String , List<Object>> seriesMap){
		List<Object> list = new ArrayList<Object>();
		for (Entry<String, List<Object>> entry : seriesMap.entrySet()) {
			Map<String, Object> map = new HashMap<String, Object>();
			createMap(map, "name", entry.getKey());
			list.add(createMap(map, "data", entry.getValue()));
		}
		return list;
	}
	
	public static void main(String[] args) throws Exception {
		MonitorController mc = new MonitorController();
		Map<String , List<Object>> seriesMap = new HashMap<String, List<Object>>();
		for (int i = 0; i < 15; i++) {
			if(i < 10){
				mc.createSeries(seriesMap, "机房", i);
			}else{
				mc.createSeries(seriesMap, "地区", i);
			}
		}
//		mc.map2Series(seriesMap);
		System.out.println(JsonUtils.bean2json(mc.map2Series(seriesMap)));
	}

}
