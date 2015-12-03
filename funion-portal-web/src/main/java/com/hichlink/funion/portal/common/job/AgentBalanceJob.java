package com.hichlink.funion.portal.common.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import com.aspire.webbas.core.util.SpringContextHolder;
import com.hichlink.funion.portal.common.job.biz.AgentBalanceBiz;

public class AgentBalanceJob implements StatefulJob {
	private AgentBalanceBiz agentBalanceBiz;

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		agentBalanceBiz = SpringContextHolder.getBean(AgentBalanceBiz.class);
		agentBalanceBiz.batchBalance();
	}

}
