package com.infrastructure.common;

import org.apache.commons.beanutils.ConvertUtils;

import java.lang.reflect.InvocationTargetException;


/**
 * 
 * 
 *                       
 * @Filename BeanUtils.java
 *
 * @Description  Bean转换 增加日期转换支持
 *
 * @Version 1.0
 *
 * @Author suyl
 *       
 * @History 
 *<li>Author: suyl</li>
 *<li>Date: 2016-5-11</li>
 *<li>Version: 1.0</li>
 *<li>Content: create</li>
 *
 */
public class BeanUtils {
	static {
		 ConvertUtils.register(new DateConvert(), java.util.Date.class);
		 ConvertUtils.register(new DateConvert(), java.sql.Date.class);
		}
	/**
	 * bean 转换
	 * @param obj 数据源
	 * @param objClass 目的bean
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static<T> T copyProperties(Object obj , Class<?> objClass){
		T t=null;
		try {
			Class<?> c =  Class.forName(objClass.getName());
			t = (T) c.newInstance();
			org.apache.commons.beanutils.BeanUtils.copyProperties(t	, obj);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return t;
	}
}
