package com.telecom.model;

public class TemperatureRecord {

	private String id;				//
	private String equipmentcode;	//设备编号
	private String temperature;		//温度
	private String humidity;		//湿度
	private String receiveTime;		//采集时间（精确到s）
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEquipmentcode() {
		return equipmentcode;
	}
	public void setEquipmentcode(String equipmentcode) {
		this.equipmentcode = equipmentcode;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	public String getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(String receiveTime) {
		this.receiveTime = receiveTime;
	}
	
}
