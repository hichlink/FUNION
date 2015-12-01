package com.hichlink.funion.portal.common.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import com.aspire.webbas.core.util.SpringContextHolder;
import com.hichlink.funion.portal.common.job.biz.FlowDispatchBiz;

public class FlowDispatchJob implements StatefulJob {
	private FlowDispatchBiz flowDispatchBiz;

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		flowDispatchBiz = SpringContextHolder.getBean(FlowDispatchBiz.class);
		flowDispatchBiz.batchDispatch();

	}

}
