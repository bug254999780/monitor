package com.telecom.model;

public class AreaInfo {

	private String areaid;	//
	private String dataNo;	//数据编号
	private String areacode;//区域代码
	private String areaname;//区域名称
	private String geology;	//区域地质
	private String longitude;//经度
	private String dimensions;//纬度
	private String sort;	//排序值
	private String reserve1;//预留字段1
	private String reserve2;//预留字段2
	private String reserve3;//预留字段3
	
	public String getAreaid() {
		return areaid;
	}
	public void setAreaid(String areaid) {
		this.areaid = areaid;
	}
	public String getDataNo() {
		return dataNo;
	}
	public void setDataNo(String dataNo) {
		this.dataNo = dataNo;
	}
	public String getAreacode() {
		return areacode;
	}
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	public String getGeology() {
		return geology;
	}
	public void setGeology(String geology) {
		this.geology = geology;
	}
	public String getReserve1() {
		return reserve1;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getDimensions() {
		return dimensions;
	}
	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
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
	public String getReserve3() {
		return reserve3;
	}
	public void setReserve3(String reserve3) {
		this.reserve3 = reserve3;
	}
	
}
