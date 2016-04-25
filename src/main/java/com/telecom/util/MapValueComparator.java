package com.telecom.util;

import java.util.Comparator;

import com.telecom.model.WarningRecord;

public class MapValueComparator implements Comparator<Object> {
    public int compare(Object me1, Object me2) {
    	WarningRecord warningRecord1 = (WarningRecord) me1;
		WarningRecord warningRecord2 = (WarningRecord) me2;
        return warningRecord1.getReceiveTime().compareTo(warningRecord2.getReceiveTime());
    }
}
