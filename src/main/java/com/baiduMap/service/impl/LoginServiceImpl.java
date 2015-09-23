package com.baiduMap.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baiduMap.IDao.IUserMapper;
import com.baiduMap.pojo.User;
import com.baiduMap.service.ILoginService;

@Service("loginService")
public class LoginServiceImpl implements ILoginService {
	@Resource
	private IUserMapper iuserMapper;
	public List<User> selectAll() {
		return this.iuserMapper.selectAll();
	}
	public User selectByPrimaryKey(String id) {
		return this.iuserMapper.selectByPrimaryKey(Long.valueOf(id));
	}
	public User selectByConditions(User pojo) {
		return this.iuserMapper.selectByConditions(pojo);
	}
	public void add(User pojo) {
		this.iuserMapper.insert(pojo);
	}
	public void del(String id) {
		this.iuserMapper.deleteByPrimaryKey(Long.valueOf(id));
	}
	public void upd(User pojo) {
		this.iuserMapper.updateByPrimaryKeySelective(pojo);
	}
	public int updateTokenByPrimaryKey(User pojo) {
		return this.iuserMapper.updateTokenByPrimaryKey(pojo);
	}

}
