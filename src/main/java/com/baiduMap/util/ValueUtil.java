package com.baiduMap.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ValueUtil {
	
	public static boolean valNotNullAndEmpty(Object object) {
		boolean flag = true;
		if(object == null) {
			flag = false;
		}
		if(object instanceof Integer) {
			flag = ((Integer) object).intValue() == 0 ? false : true;
		}
		if(object instanceof String) {
			flag = object.equals("") ? false : true;
		}
		if(object instanceof Double) {
			flag = ((Double) object).doubleValue() == 0.00 ? false : true;
		}
		if(object instanceof Float) {
			flag = ((Float) object).floatValue() == 0.0 ? false : true;
		}
		if(object instanceof Long) {
			flag = ((Long) object).longValue() == 0L ? false : true;
		}
		if(object instanceof Boolean) {
			flag = ((Boolean) object).booleanValue() == false ? false : true;
		}
		if(object instanceof Date) {
			flag = ((Date) object).getTime() == 0 ? false : true;
		}
		if(object instanceof List<?>) {
			flag = ((List<?>) object).size() == 0 ? false : true;
		}
		if(object instanceof Map<?, ?>) {
			flag = ((Map<?, ?>) object).isEmpty() ? false : true;
		}
		return flag;
	}

}
