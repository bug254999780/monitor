package com.telecom.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.telecom.model.WarningRecord;
import com.telecom.services.MonitorService;
import com.telecom.services.WarningRecordService;
import com.telecom.util.CacheUtil;
import com.telecom.util.DateUtil;
//import com.telecom.util.DateUtil;
import com.telecom.util.JsonUtils;

@Controller
@RequestMapping("IndexLoadController")
public class IndexLoadController {
	/**
	 * 创建时间：2015年11月3日 下午2:28:48 项目名称：monitor
	 * 
	 * @author zhangk
	 * @version 1.0
	 * @since JDK 1.6.0_21 文件名称：IndexLoadController.java 类说明：
	 */

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(IndexLoadController.class);
	@Autowired
	MonitorService ms;
	@Autowired
	WarningRecordService warningRecordService;
	@RequestMapping("index")
	public ModelAndView toindex(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("imgInfo", ms.getImgInfo());
		mav.setViewName("index");
		return mav;
	}


	@RequestMapping("toIndextree")
	public void toIndextree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Map> msdata = ms.getIntexTree();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Set set = new HashSet<>();
		for (Map<String, Object> map : msdata) {
			set.add(map.get("aareacode"));
		}
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			Map<String, Object> maps = new HashMap<String, Object>();
			List<Map<String, Object>> listdm = new ArrayList();
			String next = it.next();
			maps.put("aareacode", next);
			for (Map<String, Object> map : msdata) {
				if (next.equals(map.get("aareacode"))) {
					listdm.add(map);
					maps.put("areaname", map.get("areaname"));
				}
			}
			maps.put("tagvalue", listdm);
			list.add(maps);
		}

		String str = JsonUtils.bean2json(list);
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().print(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("toIndexright")
	public void indexright(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String strcode=request.getParameter("code");
		String strtype=request.getParameter("type");
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("code", strcode);
		paramsMap.put("type", strtype);
		
		List<Map> lists = new ArrayList<Map>();
		lists = ms.getDeviceInfo(paramsMap);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Set set = new HashSet<>();
		for (Map<String, Object> map : lists) {
			set.add(map.get("device_name"));
		}
		Iterator it = set.iterator();
		while (it.hasNext()) {
			Map<String, Object> maps = new HashMap<String, Object>();
			List<Map<String, Object>> listdm = new ArrayList();
			int next = (int) it.next();
			maps.put("device_name", next);
			for (Map<String, Object> map : lists) {
				if (next==Integer.parseInt(map.get("device_name").toString())) {
					listdm.add(map);
					maps.put("device_name", map.get("device_name"));
				}
			}
			maps.put("tagvalue", listdm);
			list.add(maps);
		}
		String str = JsonUtils.bean2json(list);
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().print(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@RequestMapping("shownotice")
	public ModelAndView showNotice(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("notice", ms.getNotice());
		mav.setViewName("notice");
		return mav;
	}

	@RequestMapping("detailed")
	public void deviceDetailed(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String strcode = request.getParameter("code");
		List<Map> list = new ArrayList();
		Map map = new HashMap();
		// begin缓存中读取活动信息
		if (null == CacheUtil.dataInforMap.get("deviceDetailed")) {
			list=ms.getDetailed();
			Map hchdmap = new HashMap();
			hchdmap.put("detailedTimeMillis", System.currentTimeMillis());// 当前毫秒
			hchdmap.put("detailed", list);
			CacheUtil.dataInforMap.put("deviceDetailed", hchdmap);
		} else {
			Long hcmapTime = Long.parseLong(((Map) CacheUtil.dataInforMap
					.get("deviceDetailed")).get("detailedTimeMillis").toString());
			Long dqTime = System.currentTimeMillis();
			if (dqTime - hcmapTime > 10000) {// 超过分钟清除活动缓存信息，并重新查询数据库获取活动信息重新保存到缓存MAP
				list=ms.getDetailed();
				Map hchdmap = new HashMap();
				hchdmap.put("detailedTimeMillis", System.currentTimeMillis());// 当前毫秒
				hchdmap.put("detailed", list);
				CacheUtil.dataInforMap.put("deviceDetailed", hchdmap);
			} else {
				list = (List) ((Map) CacheUtil.dataInforMap.get("deviceDetailed")).get("detailed");
			}
		}
		// end缓存中读取活动信息
		if (list != null) {
			for (Map maps : list) {
				if (maps.get("equipmentcode").toString().equals(strcode)) {
					map = maps;
				}
			}
		}
		String str = JsonUtils.map2json(map);
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().print(str);
		} catch (Exception e) {
			log.fatal(e);
		}
	}

	@RequestMapping("exceptionInfo")
	public void exceptionInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Map> list =ms.getExceptionData();
		/*	Map mapcode=new HashMap();
		mapcode.put("code","0");
		List<WarningRecord> list = warningRecordService.getWarningRecordList(mapcode);
		//List<WarningRecord> list =warningRecordService.getWarningRecordListNoLimit(mapcode);
		Map<String,Object> map=new HashMap<String,Object>();
		if (list != null) {
			for (WarningRecord warningRecord : list) {
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
								//warningRecord.setReserve3("1");//显示按钮
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
		}*/
		String str = JsonUtils.bean2json(list);
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().print(str);
		} catch (Exception e) {
			log.fatal(e);
		}
	}

}
