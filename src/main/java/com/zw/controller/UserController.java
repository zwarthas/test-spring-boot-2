package com.zw.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zw.service.UserService;

@RestController
public class UserController {

	private Logger logger=LoggerFactory.getLogger(UserController.class); 
	
	@Resource
	UserService userService;

	@RequestMapping("/register")
	public String register(String userName, String password) {
		int ret = userService.register(userName, password);
		logger.info("UserCOntrol:注册成功");
		if (ret > 0)
			return "注册成功了";
		else
			return "注册失败";
	}

}
