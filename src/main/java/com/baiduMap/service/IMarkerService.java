package com.baiduMap.service;

import java.util.List;

import com.baiduMap.pojo.Marker;

public interface IMarkerService {
	
	public List<Marker> selectAll(Marker pojo);

	public Marker selectByPrimaryKey(Marker pojo);

	public void add(Marker pojo);

	public void del(Marker pojo);

	public void upd(Marker pojo);
}