package com.telecom.services;

import java.util.List;
import java.util.Map;

import com.telecom.model.AreaInfo;
import com.telecom.model.DeviceInfo;
import com.telecom.model.MacshineroomInfo;
import com.telecom.model.ThresholdManage;

/**
 * 创建时间：2015年11月5日 上午12:08:24
 * 项目名称：monitor
 * @author zhangk
 * @version 1.0
 * @since JDK 1.6.0_21
 * 文件名称：MonitorService.java
 * 类说明：
 */
public  interface MonitorService {
	@SuppressWarnings("rawtypes")
	List getNotice();
	@SuppressWarnings("rawtypes")
	List<Map> getIntexTree();
	@SuppressWarnings("rawtypes")
	List<Map> getImgInfo();
	List<AreaInfo> getAreaInfoList();
	@SuppressWarnings("rawtypes")
	List<Map> getDetailed();
	@SuppressWarnings("rawtypes")
	List<Map> getWarning();
	List<ThresholdManage> getThresholdManageList(String equipmentType);
	List<Object> getTemperatureRecordList(Map<String, Object> params);
	@SuppressWarnings("rawtypes")
	List<Map> getDeviceInfo(Map<String, Object> paramMap);
	List<Object> getTemperatureRecordListgb(Map<String, Object> params);
	List<Object> getServerRecordListgb(Map<String, Object> params);
	List<Object> getSwitchesrecordRecordListgb(Map<String, Object> params);
	@SuppressWarnings("rawtypes")
	List<Map> getExceptionData();
	List<MacshineroomInfo> getMacshineroomInfoList(List<String> areaCode);
	List<DeviceInfo> getDeviceInfoList(List<String> devicemacshineroomcode);
}
