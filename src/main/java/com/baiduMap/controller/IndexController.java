package com.baiduMap.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class IndexController {
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String loginPage(HttpServletResponse response) {
		
		try {
			response.setCharacterEncoding("UTF-8");
			return "index";
		} catch (BeansException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
