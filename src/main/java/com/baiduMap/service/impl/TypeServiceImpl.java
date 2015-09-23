package com.baiduMap.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baiduMap.IDao.ITypeMapper;
import com.baiduMap.pojo.Type;
import com.baiduMap.service.ITypeService;


@Service("typeService")
public class TypeServiceImpl implements ITypeService{
	@Resource
	private ITypeMapper itypeMapper;
	
	public List<Type> selectByIdOrLevel(Type pojo) {
		return this.itypeMapper.selectByIdOrLevel(pojo);
	}
	public Type selectByPrimaryKey(String id) {
		return this.itypeMapper.selectByPrimaryKey(Long.valueOf(id));
	}
	public void add(Type pojo) {
		this.itypeMapper.insert(pojo);
	}
	public void del(String id) {
		this.itypeMapper.deleteByPrimaryKey(Long.valueOf(id));
	}
	public void upd(Type pojo) {
		this.itypeMapper.updateByPrimaryKey(pojo);
	}

}
