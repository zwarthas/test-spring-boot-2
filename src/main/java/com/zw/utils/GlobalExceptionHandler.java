package com.zw.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value=RuntimeException.class)
	@ResponseBody
	public Object defaultErrorHandler(HttpServletRequest req,Exception e)throws Exception{
		e.printStackTrace();
		return "出错拉";
	}
}
