package com.baiduMap.controller;

import javax.servlet.http.HttpServletResponse;
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
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private ILoginService loginService;
	
	@InitBinder("user")    
    public void initBinder2(WebDataBinder binder) {    
            binder.setFieldDefaultPrefix("user.");    
    } 
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public User login(
			HttpSession session,
			HttpServletResponse response,
			@ModelAttribute User user) {
		
		try {
			response.setCharacterEncoding("UTF-8");
			user = loginService.selectByConditions(user);
			if(ValueUtil.valNotNullAndEmpty(user)) {
				user.setToken(DataUtils.getRandomString(9));
				user.setUpdateName(String.valueOf(user.getId()));
				addUserToken(user);
				session.setAttribute("user", user);
				session.setMaxInactiveInterval(30*60);
			}
			return user;
		} catch (BeansException e) {
			e.printStackTrace();
			return null;
		}
	} 
	@ResponseBody
	@RequestMapping(value = "/getUser", method = RequestMethod.POST)
	public User getUser(@ModelAttribute User user) {
		try {
			user = loginService.selectByConditions(user);
			return user;
		} catch (BeansException e) {
			e.printStackTrace();
			return null;
		}
	} 
	@ResponseBody
	@RequestMapping(value = "/getSession", method = RequestMethod.POST)
	public User getUserSession(
			HttpSession session,
			@ModelAttribute User user) {
		
		try {
			return (User)session.getAttribute("user");
		} catch (BeansException e) {
			e.printStackTrace();
			return null;
		}
	}
	@ResponseBody
	@RequestMapping(value = "/removeUserSession", method = RequestMethod.POST)
	public void removeUserSession(
			HttpSession session,
			@ModelAttribute User user) {
		
		try {
			session.removeAttribute("user");
		} catch (BeansException e) {
			e.printStackTrace();
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/addUserToken", method = RequestMethod.POST)
	public void addUserToken(User user) {
		try {
			loginService.updateTokenByPrimaryKey(user);
		} catch (BeansException e) {
			e.printStackTrace();
		}
	}  

	@ResponseBody
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public User addUser(
			HttpSession session,
			@ModelAttribute User user) {
		try {
			user.setId(Long.valueOf(DataUtils.generate9()));
			user.setInputName(String.valueOf(user.getId()));
			if(user.getUserName() == null || "".equals(user.getUserName())) {
				user.setUserName("BDM_" + DataUtils.getRandomString(9));
			}
			loginService.add(user);
			user = loginService.selectByConditions(user);
			if(user != null && !"".equals(user.getLoginName())) {
				user.setToken(DataUtils.getRandomString(9));
			}
			user.setUpdateName(String.valueOf(user.getId()));
			addUserToken(user);
			session.setAttribute("user", user);
			session.setMaxInactiveInterval(30*60);
			return user;
		} catch (BeansException e) {
			e.printStackTrace();
			return null;
		}
	} 
	

	
}
