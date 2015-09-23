package com.baiduMap.IDao;

import java.util.List;

import com.baiduMap.pojo.Marker;

public interface IMarkerMapper {
	
	List<Marker> selectAll(Marker record);
	
    int deleteByPrimaryKey(Long markerId);

    int insert(Marker record);

    int insertSelective(Marker record);

    Marker selectByPrimaryKey(Marker record);

    int updateByPrimaryKeySelective(Marker record);

    int updateByPrimaryKey(Marker record);
}