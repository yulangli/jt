package com.jt.util;
//ThreadLocal是线程安全的

import com.jt.pojo.User;

public class UserThreadLocal {
	/**
	 * 如何存取多个数据??   Map集合
	 * ThreadLocal<Map<k,v>>
	 */
	private static ThreadLocal<User> thread = new ThreadLocal<>();
	
	//新增数据
	public static void set(User user) {
		
		thread.set(user);
	}
	
	//获取数据
	public static User get() {
		
		return thread.get();
	}
	
	//使用threadlocal切记关闭 防止内存泄漏
	public static void remove() {
		
		thread.remove();
	}
}
