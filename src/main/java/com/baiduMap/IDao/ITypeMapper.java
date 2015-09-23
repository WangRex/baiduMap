package com.baiduMap.IDao;

import java.util.List;
import com.baiduMap.pojo.Type;

public interface ITypeMapper {
	
	List<Type> selectByIdOrLevel(Type record);
	
    int deleteByPrimaryKey(Long typeId);

    int insert(Type record);

    int insertSelective(Type record);

    Type selectByPrimaryKey(Long typeId);

    int updateByPrimaryKeySelective(Type record);

    int updateByPrimaryKey(Type record);
}