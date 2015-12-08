package com.hichlink.funion.portal.common.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import com.aspire.webbas.core.util.SpringContextHolder;
import com.hichlink.funion.portal.common.job.biz.RefundBiz;

public class RefundJob implements StatefulJob{
	private RefundBiz refundBiz;
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		refundBiz = SpringContextHolder.getBean(RefundBiz.class);
		refundBiz.batchRefund();
		
	}

}
