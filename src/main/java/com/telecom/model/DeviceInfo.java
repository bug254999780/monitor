package com.telecom.model;

public class DeviceInfo {

	private String _id;						//
	private String device_name;				//设备名称(1：温湿度传感器；2：UPS监控器；3：服务器；4：交换机)
	private String _ip;						//ip
	private String device_code ;			//设备编号
	private String deviceMacshineRoomCode;	//所属机房
	private String sort;					//排序值
	private String deviceaddress;			//设备位置
	private String guarantee;				//维保信息
	private String reserve1;				//预留1
	private String reserve2;				//预留2
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String get_ip() {
		return _ip;
	}
	public void set_ip(String _ip) {
		this._ip = _ip;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getDevice_name() {
		return device_name;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	public String getDevice_code() {
		return device_code;
	}
	public void setDevice_code(String device_code) {
		this.device_code = device_code;
	}
	public String getDeviceMacshineRoomCode() {
		return deviceMacshineRoomCode;
	}
	public void setDeviceMacshineRoomCode(String deviceMacshineRoomCode) {
		this.deviceMacshineRoomCode = deviceMacshineRoomCode;
	}
	public String getDeviceaddress() {
		return deviceaddress;
	}
	public void setDeviceaddress(String deviceaddress) {
		this.deviceaddress = deviceaddress;
	}
	public String getGuarantee() {
		return guarantee;
	}
	public void setGuarantee(String guarantee) {
		this.guarantee = guarantee;
	}
	public String getReserve1() {
		return reserve1;
	}
	public void setReserve1(String reserve1) {
		this.reserve1 = reserve1;
	}
	public String getReserve2() {
		return reserve2;
	}
	public void setReserve2(String reserve2) {
		this.reserve2 = reserve2;
	}
	
}
