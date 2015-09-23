package com.baiduMap.util;

public class StringCustomUtil {

	public static String castString(Object object) {
		String str = "";
		if(ValueUtil.valNotNullAndEmpty(object)) {
			str = object.toString();
		}
		return str;
	}
}
