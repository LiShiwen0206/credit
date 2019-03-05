package com.starsgroupchina.credit.server.utils;

import org.springframework.context.ApplicationContext;

/**
 * 上下文获取工具类
 * 
 * @author zhongbaoluo
 *
 */
public class SpringContextUtils {
	private static ApplicationContext applicationContext;

	public static void setApplicationContext(ApplicationContext context) {
		applicationContext = context;
	}

	public static Object getBean(String beanId) {
		return applicationContext.getBean(beanId);
	}
	
	public static <T> T getBean(Class<T> t){
		return applicationContext.getBean(t);
	}
}
