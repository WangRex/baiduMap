package com.baiduMap.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baiduMap.pojo.Marker;
import com.baiduMap.pojo.User;
import com.baiduMap.service.IMarkerService;
import com.baiduMap.util.ValueUtil;

@Controller
@RequestMapping("/marker")
public class MarkerController {
	@Autowired
	private IMarkerService markerService;
	
	@InitBinder("marker")    
    public void initBinder2(WebDataBinder binder) {    
            binder.setFieldDefaultPrefix("marker.");    
    } 
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.POST)
	public List<Marker> getList(@ModelAttribute Marker marker) {
		
		try {
			List<Marker> list = markerService.selectAll(marker);
			return list;
		} catch (BeansException e) {
			e.printStackTrace();
			return null;
		}
	}
	@ResponseBody
	@RequestMapping(value = "/getMarker", method = RequestMethod.POST)
	public Marker selectByPrimaryKey(@ModelAttribute Marker marker) {
		
		try {
			Marker markerResult = markerService.selectByPrimaryKey(marker);
			if(markerResult.getMarkerType().indexOf(",") == -1) {
				markerResult.setMarkerType(markerResult.getMarkerType() + ",--");
			}
			return markerResult;
		} catch (BeansException e) {
			e.printStackTrace();
			return null;
		}
	}
	@ResponseBody
	@RequestMapping(value = "/updMarker", method = RequestMethod.POST)
	public Marker updateByPrimaryKey(@ModelAttribute Marker marker) {
		
		try {
			markerService.upd(marker);
			return marker;
		} catch (BeansException e) {
			e.printStackTrace();
			return null;
		}
	}
	@ResponseBody
	@RequestMapping(value = "/addMarker", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public Marker addMarker( @ModelAttribute Marker marker, HttpServletRequest request) {
		
		try {
			
			User user = new User();
			user = (User)request.getSession().getAttribute("user");
			
			Marker resultMarker = new Marker();
			
			if(ValueUtil.valNotNullAndEmpty(user)) {
				if(user.getMarkerFlag().equals("0")) {
					markerService.add(marker);
					resultMarker = marker;
				}
			}

			return resultMarker;
		} catch (BeansException e) {
			e.printStackTrace();
			return null;
		}
	}
	@ResponseBody
	@RequestMapping(value = "/delMarker", method = RequestMethod.POST)
	public void delIssues( @ModelAttribute Marker marker) {
		
		try {
			markerService.del(marker);
		} catch (BeansException e) {
			e.printStackTrace();
		}
	}
	
}
