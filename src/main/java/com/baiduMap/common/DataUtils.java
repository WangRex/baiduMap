package com.baiduMap.common;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DataUtils {
	
	public static Map<String, String> infoMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("role0", "系统管理员");
		map.put("role1", "VIP用户");
		map.put("role2", "普通用户");
		map.put("status0", "激活状态");
		map.put("status1", "未激活状态");
		map.put("marker0", "可标记");
		map.put("marker1", "不可标记");
		return map;
	}
	
	public synchronized static String generate9() {
		int n = (int) (Math.random() * 900000000) + 100000000;
		return n + "";
	}

	/**
	 * @param str
	 * @return
	 */
	public static String getNoNull(String str) {
		if(str == null) {
			return "";
		} else {
			return str;
		}
	}
	/**
	 * to db
	 * 
	 * @param s
	 * @return
	 */
	public static String toHtml(String s) {

		s = Replace(s, "&", "&amp;");
		s = Replace(s, "<", "&lt;");
		s = Replace(s, ">", "&gt;");
		s = Replace(s, "\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
		s = Replace(s, "\r\n", "\n");
		s = Replace(s, "<br>", "\n");
		s = Replace(s, "  ", "&nbsp;&nbsp;");
		s = Replace(s, "\"", "&#34;");
		s = Replace(s, "'", "&#39;");
		s = Replace(s, "\\", "&#92;");

		if (s == null)
			s = "";
		if (s != null && !s.equals(""))
			s = s.trim();
		try {
			// if (s != null && !s.equals("")) s = new
			// String(s.getBytes("iso-8859-1"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	/**
	 * to front
	 * 
	 * @param s
	 * @return
	 */
	public static String unHtml(String s) {
		s = Replace(s, "&amp;", "&");
		s = Replace(s, "&nbsp;", " ");
		s = Replace(s, "&#39;", "'");
		s = Replace(s, "&#34;", "\"");
		s = Replace(s, "&lt;", "<");
		s = Replace(s, "&gt;", ">");
		s = Replace(s, "\n", "<br>");
		s = Replace(s, "?D", "-");
		return s;
	}

	private static String Replace(String s, String s1, String s2) {
		if (s == null) {
			return null;
		}
		StringBuffer stringbuffer = new StringBuffer();
		int i = s.length();
		int j = s1.length();
		int k;
		int l;
		for (k = 0; (l = s.indexOf(s1, k)) >= 0; k = l + j) {
			stringbuffer.append(s.substring(k, l));
			stringbuffer.append(s2);
		}

		if (k < i) {
			stringbuffer.append(s.substring(k));
		}
		return stringbuffer.toString();
	}
	
	public static String getComputerName(String ip){
		String computerName = "";
		String str = "";
		try {
			Process    p = Runtime.getRuntime().exec("cmd /c C:\\Windows\\sysnative\\nbtstat.exe -a " + ip);
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (int i = 1; i < 100; i++) {
				try {
					str = input.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				if(str != null) {
					if (str.indexOf("UNIQUE") > 1) {
						computerName = str.substring(0, str.indexOf("<")).trim();
					break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return computerName;
	}
	public static String getNowDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
		return df.format(new Date());
	}
	public static String getRandomString(int length) { //length表示生成字符串的长度
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
	 }  
	
	public static void main(String args[]) {
		String str = "abc,def,g";
		int splitIndex = str.indexOf(",");
		System.out.println(str.substring(0,splitIndex));
		System.out.println(str.substring(splitIndex));
		System.out.println(DataUtils.getRandomString(9));
	}
	
}
