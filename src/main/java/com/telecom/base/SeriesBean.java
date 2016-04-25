package com.telecom.base;

import java.util.List;
import java.util.Map;

public class SeriesBean {
	private String name;
	private Map<String, Object> marker;
	private List<Object> data;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, Object> getMarker() {
		return marker;
	}
	public void setMarker(Map<String, Object> marker) {
		this.marker = marker;
	}
	public List<Object> getData() {
		return data;
	}
	public void setData(List<Object> data) {
		this.data = data;
	}
}
