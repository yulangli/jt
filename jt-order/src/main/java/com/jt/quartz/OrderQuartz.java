package com.jt.quartz;


import java.util.Calendar;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.OrderMapper;
import com.jt.pojo.Order;

//准备订单定时任务
@Component
public class OrderQuartz extends QuartzJobBean{

	@Autowired
	private OrderMapper orderMapper;

	/**
	 * 业务思想:
	 * 	用户30分钟内,没有支付则将状态改为6交易关闭
	 * sql:
	 * 	update tb_order set status = 6,
	 * 					updated=#{date}
	 *  where status=1 and created<now-30
	 * 
	 */
	//当程序执行时 执行该方法
	@Override
	@Transactional
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		Calendar calendar = Calendar.getInstance(); //获取当前时间
		calendar.add(Calendar.MINUTE, -30);
		Date timeOutDate = calendar.getTime();
		Order order = new Order();
		order.setStatus(6).setUpdated(new Date());
		UpdateWrapper<Order> updateWrapper = new UpdateWrapper<Order>();
		updateWrapper.eq("status", 1)
					 .lt("created", timeOutDate);
		orderMapper.update(order, updateWrapper);
		System.out.println("定时任务执行成功!!!!!");
	}
}
