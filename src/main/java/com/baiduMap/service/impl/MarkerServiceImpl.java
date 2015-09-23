package com.baiduMap.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baiduMap.IDao.IMarkerMapper;
import com.baiduMap.common.DataUtils;
import com.baiduMap.pojo.Marker;
import com.baiduMap.service.IMarkerService;

@Service("markerService")
public class MarkerServiceImpl implements IMarkerService {
	@Resource
	private IMarkerMapper imarkerMapper;
	public List<Marker> selectAll(Marker pojo) {
		return this.imarkerMapper.selectAll(pojo);
	}
	public Marker selectByPrimaryKey(Marker pojo) {
		return this.imarkerMapper.selectByPrimaryKey(pojo);
	}
	public void add(Marker pojo) {

		pojo.setMarkerId(Long.valueOf(DataUtils.generate9()));
		String strType = "";
		for(String s : pojo.getMarkerType().split(",")) {
			if(!"--".equals(s)) {
				strType += "," + s;
			}
		}
		if(!"".equals(strType)) {
			pojo.setMarkerType(strType.substring(1));
		}
		pojo.setDelsign("0");
		this.imarkerMapper.insertSelective(pojo);
	}
	public void del(Marker pojo) {
		this.imarkerMapper.deleteByPrimaryKey(pojo.getMarkerId());
	}
	public void upd(Marker pojo) {
		this.imarkerMapper.updateByPrimaryKey(pojo);
	}

}
