package com.telecom.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.telecom.base.BaseBean;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Module extends BaseBean {

	private java.lang.Integer id;
	private java.lang.String module_name;
	private java.lang.String module_url;
	private java.lang.String remark;
	
	public java.lang.Integer getId() {
		return id;
	}
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	public java.lang.String getModule_name() {
		return module_name;
	}
	public void setModule_name(java.lang.String module_name) {
		this.module_name = module_name;
	}
	public java.lang.String getModule_url() {
		return module_url;
	}
	public void setModule_url(java.lang.String module_url) {
		this.module_url = module_url;
	}
	public java.lang.String getRemark() {
		return remark;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	

	
	
}
