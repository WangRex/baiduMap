package com.baiduMap.IDao;

import java.util.List;

import com.baiduMap.pojo.User;

public interface IUserMapper {
	
	List<User> selectAll();
	
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    User selectByConditions(User record);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    int updateTokenByPrimaryKey(User record);
}