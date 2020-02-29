package com.zw.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class WebLogAspect {

	private final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

	@Pointcut("execution(* com.zw.controller.*.*(..))")
	public void logdown() {
	}

	// @Before("logdown()")
	public void logBefore(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		logger.info(args[0] + "准备注册了");
	}

	// @After("logdown()")
	public void logAfter(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		logger.info(args[0] + "注册成功，密码为：" + args[1]);
	}

	@Around("logdown()")
	public String logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] args = joinPoint.getArgs();
		long start = System.currentTimeMillis();
		Thread.sleep(3000);
		Object ret = joinPoint.proceed();
		long end = System.currentTimeMillis();
		System.out.println(args[0] + "注册花了" + (end - start) + "毫秒");
		// logger.info(args[0]+"注册花了"+(end-start)+"毫秒");
		return ret.toString();
	}

}
