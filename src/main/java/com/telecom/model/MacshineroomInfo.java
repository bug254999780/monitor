package com.telecom.model;

public class MacshineroomInfo {

	private String  dataNo;				//数据编号
	private String  macshineRoomCode;	//机房代码
	private String  macshineRoom;		//机房名称
	private String  areaCode;			//所属区域
	private String  macshineRoomManager;//管理人
	private String  sort;				//排序值
	public String getDataNo() {
		return dataNo;
	}
	public void setDataNo(String dataNo) {
		this.dataNo = dataNo;
	}
	public String getMacshineRoomCode() {
		return macshineRoomCode;
	}
	public void setMacshineRoomCode(String macshineRoomCode) {
		this.macshineRoomCode = macshineRoomCode;
	}
	public String getMacshineRoom() {
		return macshineRoom;
	}
	public void setMacshineRoom(String macshineRoom) {
		this.macshineRoom = macshineRoom;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getMacshineRoomManager() {
		return macshineRoomManager;
	}
	public void setMacshineRoomManager(String macshineRoomManager) {
		this.macshineRoomManager = macshineRoomManager;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
}
