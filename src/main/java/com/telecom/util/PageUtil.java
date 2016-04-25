package com.telecom.util;

import java.util.List;

import com.telecom.model.WarningRecord;

public class PageUtil {

	private Integer pageSize = 10;	//每页数据数量默认为10
	private Integer pageNum = 1;	//页码默认为1
	private Integer totalPages;		//总页数
	private Integer totalCount;		//总数量
	private List<Object> list;//数据结果集
	
	public PageUtil(){}
	
	public PageUtil(Integer pageNum, Integer pageSize, Integer totalCount, List<Object> list){
		this.setPageSize(pageSize == null ? this.pageSize : pageSize);
		this.setPageNum(pageNum == null ? this.pageNum : pageNum);
		this.setTotalCount(totalCount);
		this.setTotalPages(this.calculationTotalPages(this.getTotalCount(), this.getPageSize()));
		this.setList(list);
	}

	/**
	 * 根据数据的总量和每页显示的数量计算出页码的数量
	 * @Integer
	 * @author lichenyi
	 * 2015年11月13日 上午2:28:27
	 */
	public Integer calculationTotalPages(Integer totalCount, Integer pageSize){
		if(totalCount%pageSize > 0){
			return (totalCount / pageSize)+1;
		}else{
			return totalCount/pageSize;
		}
	}
	
	/**
	 * 每页显示的数据量
	 * @Integer
	 * @author lichenyi
	 * 2015年11月13日 上午2:33:20
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	
	/**
	 * 当前页码
	 * @Integer
	 * @author lichenyi
	 * 2015年11月13日 上午2:32:44
	 */
	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	/**
	 * 总页码
	 * @Integer
	 * @author lichenyi
	 * 2015年11月13日 上午2:32:10
	 */
	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * 数据总数量
	 * @Integer
	 * @author lichenyi
	 * 2015年11月13日 上午2:31:45
	 */
	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 获取结果集
	 * @List<Object>
	 * @author lichenyi
	 * 2015年11月13日 上午2:31:18
	 */

	public List<Object> getList() {
		return list;
	}

	public void setList(List<Object> list) {
		this.list = list;
	}
	
}
