package com.telecom.services;

import java.util.List;
import java.util.Map;

import com.telecom.model.WarningRecord;
import com.telecom.util.PageUtil;

public  interface WarningRecordService {

	void updateWarningRecord(WarningRecord warningRecord);

	PageUtil getWarningRecordList(Integer pageNum, Integer pageSize, Map map);

	List<WarningRecord> getWarningRecordList(Map map);

	List getWarningRecordListNoLimit(Map map);
	
}
