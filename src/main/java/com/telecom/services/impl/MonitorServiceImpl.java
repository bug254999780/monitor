package com.telecom.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.model.AreaInfo;
import com.telecom.model.DeviceInfo;
import com.telecom.model.MacshineroomInfo;
import com.telecom.model.TemperatureRecord;
import com.telecom.model.ThresholdManage;
import com.telecom.services.MonitorService;

/**
 * 创建时间：2015年11月5日 上午12:12:41
 * 项目名称：monitor
 * @author zhangk
 * @version 1.0
 * @since JDK 1.6.0_21
 * 文件名称：MonitorServiceImpl.java
 * 类说明：
 */
@Service
public class MonitorServiceImpl implements MonitorService {
	private static Log log = LogFactory.getLog(MonitorServiceImpl.class);
	@Autowired
	private SqlSession sqlSession;  
	
	@SuppressWarnings("rawtypes")
	@Override
	public List getNotice() {
		List List = new ArrayList();
		try {
			List = sqlSession.selectList("com.telecom.model.MonitorMapper.noticeinfo",null);
		} catch (Exception e) {
			log.info("getNotice 出现错误："+e.getMessage());
		}
		return List;
	
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map> getIntexTree() {
		List List = new ArrayList();
		try {
			List = sqlSession.selectList("com.telecom.model.MonitorMapper.treeinfo",null);
		} catch (Exception e) {
			log.info("gettree 出现错误："+e.getMessage());
		}
		return List;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map> getDeviceInfo(Map<String, Object> paramMap) {
		List List = new ArrayList();
		try {
			if("0".equals(paramMap.get("type"))){
				List = sqlSession.selectList("com.telecom.model.MonitorMapper.deviceforarea",paramMap);
			}
			if("1".equals(paramMap.get("type"))){
				List = sqlSession.selectList("com.telecom.model.MonitorMapper.deviceinfo",paramMap);
			}
		} catch (Exception e) {
			log.info("getDeviceInfo 出现错误："+e.getMessage());
		}
		return List;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map> getImgInfo() {
		List List = new ArrayList();
		try {
			List = sqlSession.selectList("com.telecom.model.MonitorMapper.imginfo",null);
		} catch (Exception e) {
			log.info("getImgInfo 出现错误："+e.getMessage());
		}
		return List;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List <Map> getDetailed() {
		List <Map> lists=new ArrayList<Map>();
		try {
			List listserverrecord = sqlSession.selectList("com.telecom.model.MonitorMapper.serverrecord",null);
			List upsrecord = sqlSession.selectList("com.telecom.model.MonitorMapper.upsrecord",null);
			List switchesrecord = sqlSession.selectList("com.telecom.model.MonitorMapper.switchesrecord",null);
			List temperaturerecord = sqlSession.selectList("com.telecom.model.MonitorMapper.temperaturerecord",null);
			lists.addAll(listserverrecord);
			lists.addAll(upsrecord);
			lists.addAll(switchesrecord);
			lists.addAll(temperaturerecord);
		} catch (Exception e) {
			log.info("getDetailed 出现错误："+e.getMessage());
		}
		return lists;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map> getWarning() {
		List List = new ArrayList();
		try {
			List = sqlSession.selectList("com.telecom.model.MonitorMapper.warningrecord",null);
		} catch (Exception e) {
			log.info("getImgInfo 出现错误："+e.getMessage());
		}
		return List;
	}
	
	
	
	//**********************************************************
	//************************ highcharts
	//**********************************************************
	/**
	 * 地区信息
	 * @List<Map>
	 * @author lichenyi
	 * 2015年11月7日 下午11:52:13
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<AreaInfo> getAreaInfoList() {
		List List = new ArrayList();
		try {
			List = sqlSession.selectList("com.telecom.model.MonitorMapper.areainfoList");
		} catch (Exception e) {
			log.error("getAreaInfoList 出现错误："+e.getMessage());
		}
		return List;
	}
	/**
	 * 机房信息
	 * 
	 * @author lichenyi
	 * 2015年11月8日 上午12:20:44
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<MacshineroomInfo> getMacshineroomInfoList(List<String> areaCode) {
		List List = new ArrayList();
		try {
			List = sqlSession.selectList("com.telecom.model.MonitorMapper.macshineroomInfoList", areaCode);
		} catch (Exception e) {
			log.error("getdMacshineroomInfoList 出现错误："+e.getMessage());
		}
		return List;
	}
	/**
	 * 设备信息
	 * 
	 * @author lichenyi
	 * 2015年11月8日 上午12:18:19
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<DeviceInfo> getDeviceInfoList(List<String> devicemacshineroomcode) {
		List List = new ArrayList();
		try {
			List = sqlSession.selectList("com.telecom.model.MonitorMapper.deviceInfoList", devicemacshineroomcode);
		} catch (Exception e) {
			log.error("getdDeviceInfoList 出现错误："+e.getMessage());
		}
		return List;
	}
	/**
	 * 指标信息
	 * 
	 * @author lichenyi
	 * 2015年11月8日 上午12:21:50
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ThresholdManage> getThresholdManageList(String equipmentType) {
		@SuppressWarnings("rawtypes")
		List List = new ArrayList();
		try {
			List = sqlSession.selectList("com.telecom.model.MonitorMapper.thresholdManageList", equipmentType);
		} catch (Exception e) {
			log.error("getdThresholdManageList 出现错误："+e.getMessage());
		}
		return List;
	}
	/**
	 * 报表数据
	 * 
	 * @author lichenyi
	 * 2015年11月8日 上午3:20:32
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Object> getTemperatureRecordList(Map<String, Object> params){
		List list = new ArrayList();
		try {
			list = sqlSession.selectList("com.telecom.model.MonitorMapper.temperatureRecordList", params);
		} catch (Exception e) {
			log.error("getTemperatureRecordList 出现错误："+e.getMessage());
		}
		return list;
	}

	/**
	 * 查询温度湿度报表数据
	 * 
	 * @author lichenyi
	 * 2015年11月14日 下午4:08:12
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Object> getTemperatureRecordListgb(Map<String, Object> params){
		List list = new ArrayList();
		try {
			list = sqlSession.selectList("com.telecom.model.MonitorMapper.temperatureRecordListgb", params);
		} catch (Exception e) {
			log.error("getTemperatureRecordListgb 出现错误："+e.getMessage());
		}
		return list;
	}
	/**
	 * 查询服务器报表数据
	 * 
	 * @author lichenyi
	 * 2015年11月14日 下午7:35:58
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Object> getServerRecordListgb(Map<String, Object> params){
		List list = new ArrayList();
		try {
			list = sqlSession.selectList("com.telecom.model.MonitorMapper.serverRecordListgb", params);
		} catch (Exception e) {
			log.error("getServerRecordListgb 出现错误："+e.getMessage());
		}
		return list;
	}
	/**
	 * 查询交换机数据
	 * @List<Object>
	 * @author lichenyi
	 * 2015年11月14日 下午10:30:24
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Object> getSwitchesrecordRecordListgb(Map<String, Object> params){
		List list = new ArrayList();
		try {
			list = sqlSession.selectList("com.telecom.model.MonitorMapper.switchesrecordRecordListgb", params);
		} catch (Exception e) {
			log.error("getSwitchesrecordRecordListgb 出现错误："+e.getMessage());
		}
		return list;
	}

	@Override
	public List<Map> getExceptionData() {
		List<Map> data=getDetailed_now();
		List<ThresholdManage> ltm =sqlSession.selectList("com.telecom.model.MonitorMapper.thresholdManageListex",null);
		Map mapU=LTM(ltm);
		List<Map> listresult=new ArrayList<Map>();
		try {
		if(data!=null){
			for (Map map : data) {
				boolean iserror=false;
				//boolean isnetworkdelayrate=false;
				switch (map.get("equipmentType").toString()) {
				case "1":
					if (map.get("temperature")==null || map.get("humidity") ==null || "".equals(map.get("temperature")) || "".equals(map.get("humidity")) ) {
						iserror=true;
						break;
					}
					if("0".equals(map.get("temperature").toString())|| Double.parseDouble(map.get("temperature").toString())>((ThresholdManage)mapU.get("001")).getMaxValue() || Double.parseDouble(map.get("temperature").toString())<((ThresholdManage)mapU.get("001")).getMinValue())
					{
						iserror=true;
					}
					if("0".equals(map.get("humidity").toString())|| Double.parseDouble(map.get("humidity").toString())>((ThresholdManage)mapU.get("002")).getMaxValue() || Double.parseDouble(map.get("humidity").toString())<((ThresholdManage)mapU.get("002")).getMinValue())
					{
						iserror=true;
					}
					break;
				case "2":
					if (map.get("ustatus")==null || "".equals(map.get("ustatus"))  ) {
						iserror=true;
						break;
					}
					if(!map.get("ustatus").toString().equals(((ThresholdManage)mapU.get("008")).getMinValue()) && !(Double.parseDouble(map.get("ustatus").toString()) == ((ThresholdManage)mapU.get("008")).getMinValue()))
					{
						iserror=true;
					}
					
					break;
				case "3":
					//a.ip,a.cpu,a.memory,a.desk
					if (map.get("cpu")==null || map.get("memory") ==null || map.get("desk")==null ||"".equals(map.get("memory") ) ||"".equals(map.get("cpu") ) ||"".equals(map.get("desk") )) {
						iserror=true;
						break;
					}
					if("0".equals(map.get("cpu").toString())|| Double.parseDouble(map.get("cpu").toString())>((ThresholdManage)mapU.get("004")).getMaxValue() || Double.parseDouble(map.get("cpu").toString())<((ThresholdManage)mapU.get("004")).getMinValue())
					{
						iserror=true;
					}
					if("0".equals(map.get("memory").toString())|| Double.parseDouble(map.get("memory").toString())>((ThresholdManage)mapU.get("003")).getMaxValue() || Double.parseDouble(map.get("memory").toString())<((ThresholdManage)mapU.get("003")).getMinValue())
					{
						iserror=true;
					}
					if("0".equals(map.get("desk").toString())|| Double.parseDouble(map.get("desk").toString())>((ThresholdManage)mapU.get("005")).getMaxValue() || Double.parseDouble(map.get("desk").toString())<((ThresholdManage)mapU.get("005")).getMinValue())
					{
						iserror=true;
					}
					break;
				case "4":
					if (map.get("packetsloss")==null || map.get("networkdelayrate") ==null || "".equals(map.get("packetsloss")) || "".equals(map.get("networkdelayrate"))) {
						iserror=true;
						break;
					}
					//packetsloss,a.networkdelayrate
					if( Double.parseDouble(map.get("packetsloss").toString())>((ThresholdManage)mapU.get("006")).getMaxValue() || Double.parseDouble(map.get("packetsloss").toString())<((ThresholdManage)mapU.get("006")).getMinValue())
					{
						iserror=true;
					}
					if( Double.parseDouble(map.get("networkdelayrate").toString())>((ThresholdManage)mapU.get("007")).getMaxValue() || Double.parseDouble(map.get("networkdelayrate").toString())<((ThresholdManage)mapU.get("007")).getMinValue())
					{
						iserror=true;
						//isnetworkdelayrate=true;
						
					}
					break;
				default:
					break;
				}
				if(iserror){
					map.put("reserve3", "1");
					//map.put("isnetworkdelayrate", isnetworkdelayrate);
					listresult.add(map);
				}
			}
			
		}
		
	} catch (Exception e) {
			log.error("exceptiondata",e);
			e.printStackTrace();
	}
		return listresult;
	}
	protected List<Map> getDetailed_now() {
		List <Map> lists=new ArrayList<Map>();
		try {
			List listserverrecord = sqlSession.selectList("com.telecom.model.MonitorMapper.serverrecord_now",null);
			List upsrecord = sqlSession.selectList("com.telecom.model.MonitorMapper.upsrecord_now",null);
			List switchesrecord = sqlSession.selectList("com.telecom.model.MonitorMapper.switchesrecord_now",null);
			List temperaturerecord = sqlSession.selectList("com.telecom.model.MonitorMapper.temperaturerecord_now",null);
			lists.addAll(listserverrecord);
			lists.addAll(upsrecord);
			lists.addAll(switchesrecord);
			lists.addAll(temperaturerecord);
		} catch (Exception e) {
			log.info("getDetailed_now 出现错误："+e.getMessage());
		}
		return lists;
	}
	protected Map<String,ThresholdManage> LTM(List<ThresholdManage> list) {
		Map<String,ThresholdManage> map=new HashMap<String,ThresholdManage>();
		if(list !=null && list.size()>0)
		{
			for (ThresholdManage tm : list) {
				map.put(tm.getTargetCode(), tm);
			}
			return map;
		}else{
			return null;
		}
	}
}
