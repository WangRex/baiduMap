package com.baiduMap.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baiduMap.common.DataUtils;
import com.baiduMap.pojo.User;
import com.baiduMap.service.ILoginService;
import com.baiduMap.util.ValueUtil;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private ILoginService loginService;
	
	@InitBinder("user")    
    public void initBinder2(WebDataBinder binder) {    
		binder.setFieldDefaultPrefix("user.");    
    } 
	@ResponseBody
	@RequestMapping(value = "/getUserList", method = RequestMethod.POST)
	public List<User> getUserList(@ModelAttribute User user) {
		
		try {
			return loginService.selectAll();
		} catch (BeansException e) {
			e.printStackTrace();
			return null;
		}
	}   

	@ResponseBody
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public void addUser(
			HttpSession session,
			@ModelAttribute User user) {
		try {
			user.setId(Long.valueOf(DataUtils.generate9()));
			user.setInputName(String.valueOf(user.getId()));
			if(user.getUserName() == null || "".equals(user.getUserName())) {
				user.setUserName("BDM_" + DataUtils.getRandomString(9));
			}
			if(!ValueUtil.valNotNullAndEmpty(user.getMarkerFlag())) {
				user.setMarkerFlag("1");
			}
			loginService.add(user);
		} catch (BeansException e) {
			e.printStackTrace();
		}
	} 
	@ResponseBody
	@RequestMapping(value = "/getUser", method = RequestMethod.POST)
	public User getUser(
			@ModelAttribute User user) {
		
		try {
			user = loginService.selectByConditions(user);
			return user;
		} catch (BeansException e) {
			e.printStackTrace();
			return null;
		}
	} 

	@ResponseBody
	@RequestMapping(value = "/updUser", method = RequestMethod.POST)
	public void UpdUser(@ModelAttribute User user) {
		
		try {
			loginService.upd(user);
		} catch (BeansException e) {
			e.printStackTrace();
		}
	} 
	
}
