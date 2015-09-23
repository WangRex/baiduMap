package com.baiduMap.service;

import java.util.List;

import com.baiduMap.pojo.Type;

public interface ITypeService {
	
	public List<Type> selectByIdOrLevel(Type pojo);

	public Type selectByPrimaryKey(String id);

	public void add(Type pojo);

	public void del(String id);

	public void upd(Type pojo);
}