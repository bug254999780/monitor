package com.telecom.model;

public class ThresholdManage {

	private String dataNo;		//
	private String EquipmentType;//设备类型(1：温湿度传感器；2：UPS监控器；3：服务器；4：交换机)
	private String TargetName;	//指标名称(001：温度；002：湿度；003：内存利用率；004：CPU使用率；005：硬盘使用率；006：网络延时率；007：丢包率 ;008ups状态)
	private String TargetCode;	//指标编码(001~008)
	private Double MaxValue;	//最大值
	private Double MinValue;	//最小值
	private String CycleTime;	//取值周期
	private String Reserve1;	//预留1
	private String Reserve2;	//预留2
	public String getDataNo() {
		return dataNo;
	}
	public void setDataNo(String dataNo) {
		this.dataNo = dataNo;
	}
	public String getEquipmentType() {
		return EquipmentType;
	}
	public void setEquipmentType(String equipmentType) {
		EquipmentType = equipmentType;
	}
	public String getTargetName() {
		return TargetName;
	}
	public void setTargetName(String targetName) {
		TargetName = targetName;
	}
	public String getTargetCode() {
		return TargetCode;
	}
	public void setTargetCode(String targetCode) {
		TargetCode = targetCode;
	}
	public Double getMaxValue() {
		return MaxValue;
	}
	public void setMaxValue(Double maxValue) {
		MaxValue = maxValue;
	}
	public Double getMinValue() {
		return MinValue;
	}
	public void setMinValue(Double minValue) {
		MinValue = minValue;
	}
	public String getCycleTime() {
		return CycleTime;
	}
	public void setCycleTime(String cycleTime) {
		CycleTime = cycleTime;
	}
	public String getReserve1() {
		return Reserve1;
	}
	public void setReserve1(String reserve1) {
		Reserve1 = reserve1;
	}
	public String getReserve2() {
		return Reserve2;
	}
	public void setReserve2(String reserve2) {
		Reserve2 = reserve2;
	}
	
}
