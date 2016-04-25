package com.telecom.base;

import java.util.List;

public class HighchartsBean {

	private Object title;				//图表标题
	private Object subtitle;			//短标题
	private Object xAxis;				//x坐标轴
	private Object yAxis;				//y坐标轴
	private List<Object> series;		//数据列 【日期】
	private List<Object> seriesMonth;	//数据列 【月】
	private List<Object> categoriesMonth;//横坐标【月】
	private List<Object> seriesDay;		//数据列 【天】
	private List<Object> categoriesDay;	//横坐标【天】
	private List<Object> seriesHour;	//数据列 【月】
	private List<Object> categoriesHour;//横坐标【月】
	private Object seriesName;			//图例名称
	private Object tooltip;				//数据提示框
	private Object credits;				//版权信息
	private Object legend;				//图例
	
	public Object getSeriesName() {
		return seriesName;
	}
	public void setSeriesName(Object seriesName) {
		this.seriesName = seriesName;
	}
	public Object getLegend() {
		return legend;
	}
	public void setLegend(Object legend) {
		this.legend = legend;
	}
	public Object getTitle() {
		return title;
	}
	public Object getSubtitle() {
		return subtitle;
	}
	public Object getxAxis() {
		return xAxis;
	}
	public Object getyAxis() {
		return yAxis;
	}
	public List<Object> getSeries() {
		return series;
	}
	public void setSeries(List<Object> series) {
		this.series = series;
	}
	public Object getTooltip() {
		return tooltip;
	}
	public Object getCredits() {
		return credits;
	}
	public void setTitle(Object title) {
		this.title = title;
	}
	public void setSubtitle(Object subtitle) {
		this.subtitle = subtitle;
	}
	public void setxAxis(Object xAxis) {
		this.xAxis = xAxis;
	}
	public void setyAxis(Object yAxis) {
		this.yAxis = yAxis;
	}
	public void setTooltip(Object tooltip) {
		this.tooltip = tooltip;
	}
	public void setCredits(Object credits) {
		this.credits = credits;
	}
	public List<Object> getSeriesMonth() {
		return seriesMonth;
	}
	public void setSeriesMonth(List<Object> seriesMonth) {
		this.seriesMonth = seriesMonth;
	}
	public List<Object> getSeriesHour() {
		return seriesHour;
	}
	public void setSeriesHour(List<Object> seriesHour) {
		this.seriesHour = seriesHour;
	}
	public List<Object> getCategoriesMonth() {
		return categoriesMonth;
	}
	public void setCategoriesMonth(List<Object> categoriesMonth) {
		this.categoriesMonth = categoriesMonth;
	}
	public List<Object> getCategoriesHour() {
		return categoriesHour;
	}
	public void setCategoriesHour(List<Object> categoriesHour) {
		this.categoriesHour = categoriesHour;
	}
	public List<Object> getSeriesDay() {
		return seriesDay;
	}
	public void setSeriesDay(List<Object> seriesDay) {
		this.seriesDay = seriesDay;
	}
	public List<Object> getCategoriesDay() {
		return categoriesDay;
	}
	public void setCategoriesDay(List<Object> categoriesDay) {
		this.categoriesDay = categoriesDay;
	}
	
}
