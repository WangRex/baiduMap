package com.baiduMap.controller;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baiduMap.common.DataUtils;
import com.baiduMap.pojo.Type;
import com.baiduMap.service.ITypeService;

@Controller
@RequestMapping("/type")
public class TypeController {
	@Autowired
	private ITypeService typeService;
	
	@InitBinder("type")    
    public void initBinder2(WebDataBinder binder) {    
            binder.setFieldDefaultPrefix("type.");    
    }
	
	@ResponseBody
	@RequestMapping(value = "/getTypeList", method = RequestMethod.GET)
	public List<Type> getTypeList(@RequestParam(value = "type_level", required = true) String type_level
			) {
		
		try {
			Type type = new Type();
			type.setTypeLevel(type_level);
			List<Type> list = typeService.selectByIdOrLevel(type);
			return list;
		} catch (BeansException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/getList", method = RequestMethod.GET)
	public List<Type> getList(@RequestParam(value = "typeParentId", required = true) String typeParentId,
			@RequestParam(value = "type_level", required = true) String type_level
			) {
		
		try {
			Type type = new Type();
			type.setTypeParentId(typeParentId);
			type.setTypeLevel(type_level);
			List<Type> list = typeService.selectByIdOrLevel(type);
			return list;
		} catch (BeansException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/addType", method = RequestMethod.GET)
	public void addType(@RequestParam(value = "type_name", required = true) String type_name,
			@RequestParam(value = "type_level", required = true) String type_level,
			@RequestParam(value = "type_parent_id", required = true) String type_parent_id) {
		
		try {

			Type type = new Type();	
			type.setTypeId(Long.valueOf(DataUtils.generate9()));
			type.setTypeName(type_name);
			type.setTypeLevel(type_level);
			type.setTypeParentId(type_parent_id);
			typeService.add(type);
			
		} catch (BeansException e) {
			e.printStackTrace();
		}
	}
	
	
}
