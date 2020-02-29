package com.zw.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

	private final Logger logger=LoggerFactory.getLogger(SimpleController.class);
	
	@RequestMapping("/hello")
	public String hello() {
		logger.debug("这是debug日志");
		logger.info("这是info日志");
		return "hello springboot";
	}
}
