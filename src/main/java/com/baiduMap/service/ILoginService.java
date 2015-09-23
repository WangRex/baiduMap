package com.baiduMap.service;

import java.util.List;

import com.baiduMap.pojo.User;

public interface ILoginService {
	
	public List<User> selectAll();

	public User selectByPrimaryKey(String id);

	public User selectByConditions(User pojo);

	public void add(User pojo);

	public void del(String id);

	public void upd(User pojo);

	public int updateTokenByPrimaryKey(User pojo);
}