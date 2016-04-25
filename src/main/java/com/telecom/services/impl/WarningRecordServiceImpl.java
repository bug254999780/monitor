package com.telecom.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.telecom.model.WarningRecord;
import com.telecom.services.WarningRecordService;
import com.telecom.util.PageUtil;
import com.telecom.util.PageParam;

/**
 * 创建时间：2015年11月5日 上午12:12:41
 * 项目名称：monitor
 * @author zhangk
 * @version 1.0
 * @since JDK 1.6.0_21
 * 文件名称：MonitorServiceImpl.java
 * 类说明：
 */
@Service
public class WarningRecordServiceImpl implements WarningRecordService {
	private static Log log = LogFactory.getLog(WarningRecordServiceImpl.class);
	@Autowired
	private SqlSession sqlSession;  
	
	@Override
	public List<WarningRecord> getWarningRecordList(Map map){
		List warningRecordList = new ArrayList();
		try {
			warningRecordList = sqlSession.selectList("com.telecom.model.WarningRecordMapper.warningRecordList",map);
		} catch (Exception e) {
			log.info("getWarningRecordList 出现错误："+e.getMessage());
			e.printStackTrace();
		}
		return warningRecordList;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public PageUtil getWarningRecordList(Integer pageNum, Integer pageSize,Map map){
		List warningRecordList = new ArrayList();
		PageUtil pageUtil = null;
		try {
//			map.put("code", "2");
//			map.put("type", "1");
			
			Integer count = sqlSession.selectOne("com.telecom.model.WarningRecordMapper.warningRecordCountLimit",map);
			map.put("pageNum", pageNum);
			map.put("pageSize", pageSize);
			warningRecordList = sqlSession.selectList("com.telecom.model.WarningRecordMapper.warningRecordListLimit",map);
			pageUtil = new PageUtil(pageNum, pageSize, count, warningRecordList);
		} catch (Exception e) {
			log.info("getWarningRecordList 出现错误："+e.getMessage());
			e.printStackTrace();
		}
		return pageUtil;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getWarningRecordListNoLimit(Map map){
		List warningRecordList = new ArrayList();
		try {
			warningRecordList = sqlSession.selectList("com.telecom.model.WarningRecordMapper.warningRecordListLimit",map);
		} catch (Exception e) {
			log.info("getWarningRecordList 出现错误："+e.getMessage());
		}
		return warningRecordList;
	}
	
	@Override
	public void updateWarningRecord(WarningRecord warningRecord){
		List warningRecordList = new ArrayList();
		try {
			warningRecordList = sqlSession.selectList("com.telecom.model.WarningRecordMapper.updateWarningRecord", warningRecord);
		} catch (Exception e) {
			log.info("updateWarningRecord 出现错误："+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 张奎
	 * @Map<String,Object>
	 * @author lichenyi
	 * 2015年11月13日 上午4:03:52
	 */
	public Map<String,Object> limitWarningRecord(Map param){
		Map mapdata=new HashMap<>();
		int pagecount=0;
		Map MapCount=(Map)(sqlSession.selectList("com.telecom.model.WarningRecordMapper.updateWarningRecord", param));
		if(MapCount!=null && MapCount.get("pagecount")!=null){
			pagecount =Integer.parseInt(MapCount.get("pagecount").toString());
		}
		param.put("minvalue",PageParam.minvalue(PageParam.pagesize, Integer.parseInt(param.get("pageNb").toString())));
		param.put("maxvalue",PageParam.maxvalue(PageParam.pagesize, Integer.parseInt(param.get("pageNb").toString())));
		List<Object> list=new ArrayList<Object>();	
		list = sqlSession.selectList("com.telecom.model.WarningRecordMapper.updateWarningRecord", param);		
		mapdata.put("pagecount",pagecount);
		mapdata.put("pageInfo",list);
		return mapdata;
	}
}
