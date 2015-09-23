package com.baiduMap.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baiduMap.service.ILoginService;

@Component
public class ExpireJobTask {

	@Autowired
	private ILoginService loginService;
	
	public void doBiz() {
//		loginService.selectAll();
		System.out.println("ExpireJobTask.doBiz() excuted");
	}
}
