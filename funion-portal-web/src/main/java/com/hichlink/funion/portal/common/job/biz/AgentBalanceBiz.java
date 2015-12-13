package com.hichlink.funion.portal.common.job.biz;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import com.hichlink.funion.common.entity.AgentInfo;
import com.hichlink.funion.common.entity.BalanceFlow;
import com.hichlink.funion.common.entity.FlowPayRecord;
import com.hichlink.funion.common.entity.FlowProductInfo;
import com.hichlink.funion.common.service.AgentInfoService;
import com.hichlink.funion.common.service.BalanceFlowService;
import com.hichlink.funion.common.service.FlowPayRecordService;
import com.hichlink.funion.common.service.FlowProductInfoService;

@Service("agentBalanceBiz")
public class AgentBalanceBiz {
	private static Logger Logger = LoggerFactory.getLogger(AgentBalanceBiz.class);
	@Autowired
	private FlowPayRecordService flowPayRecordService;
	@Autowired
	private AgentInfoService agentInfoService;
	@Autowired
	private BalanceFlowService balanceFlowService;
	@Autowired
	private FlowProductInfoService flowProductInfoService;

	public void batchBalance() {
		Page<FlowPayRecord> params = new Page<FlowPayRecord>();
		params.getParams().put("sendStatus", FlowPayRecord.SEND_STATUS_OK);
		params.getParams().put("isBalance", FlowPayRecord.IS_BALANCE_NO);
		params.getParams().put("payStatus", FlowPayRecord.PAY_STATUS_SUCC);
		params.setRows(50);
		Page<FlowPayRecord> page = flowPayRecordService.pageQuery(params);
		List<FlowPayRecord> datas = page.getDatas();
		for (FlowPayRecord data : datas) {
			data.setIsBalance(FlowPayRecord.IS_BALANCE_YES);
			flowPayRecordService.update(data);
			String uuid = data.getIdentityId();
			if (StringUtils.isEmpty(uuid)) {
				Logger.error("uuid is null");
				continue;
			}
			AgentInfo agentInfo = agentInfoService.selectByUUId(uuid);
			if (null == agentInfo) {
				Logger.error("uuid={}找不到对应代理商", uuid);
				continue;
			}
			int ratio = agentInfo.getCommisionRatio();
			BigDecimal myCommisionAmount = BigDecimal.ZERO;
			FlowProductInfo flowProductInfo = flowProductInfoService.get(data.getProductId());
			if (null != flowProductInfo) {
				myCommisionAmount = flowProductInfo.getCommisionAmount();
			}

			BigDecimal commisionAmount = myCommisionAmount.multiply(new BigDecimal(ratio).divide(new BigDecimal(100)));
			BalanceFlow balanceFlow = new BalanceFlow();
			balanceFlow.setAgentId(agentInfo.getAgentId());
			balanceFlow.setCommisionAmount(commisionAmount);
			balanceFlow.setInputTime(new Date());
			balanceFlow.setRecordId(data.getRecordId());
			balanceFlow.setType(BalanceFlow.TYPE_COMMISION);
			balanceFlow.setRemark("佣金结算 +" + commisionAmount.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
			balanceFlowService.insert(balanceFlow);
			agentInfoService.updateBalance(agentInfo.getAgentId(), commisionAmount);
			agentInfoService.updateIncome(agentInfo.getAgentId(), commisionAmount);
		}
	}
}
