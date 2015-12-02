package com.hichlink.funion.portal.common.job.biz;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import com.hichlink.funion.common.entity.FlowExchangeLog;
import com.hichlink.funion.common.entity.FlowPayRecord;
import com.hichlink.funion.common.entity.FlowProductInfo;
import com.hichlink.funion.common.flow.FlowDispatch;
import com.hichlink.funion.common.flow.FossFlowMakeBack;
import com.hichlink.funion.common.flow.exchange.FlowMakeOrderReqMesg;
import com.hichlink.funion.common.service.FlowExchangeLogService;
import com.hichlink.funion.common.service.FlowPayRecordService;
import com.hichlink.funion.common.service.FlowProductInfoService;
import com.hichlink.funion.common.util.OrderSeqGen;
import com.hichlink.funion.portal.common.config.SystemConfig;

@Service("flowDispatchBiz")
public class FlowDispatchBiz {
	private static final Logger LOG = LoggerFactory.getLogger(FlowDispatchBiz.class);
	@Autowired
	private FlowPayRecordService flowPayRecordService;
	@Autowired
	private FlowProductInfoService flowProductInfoService;
	@Autowired
	private FlowExchangeLogService flowExchangeLogService;

	public void batchDispatch() {
		Page<FlowPayRecord> page = new Page<FlowPayRecord>();
		page.setRows(50);
		page.addParam("sendStatus", FlowPayRecord.SEND_STATUS_INIT);
		Page<FlowPayRecord> list;
		do {
			list = flowPayRecordService.pageQuery(page);
			List<FlowPayRecord> datas = list.getDatas();
			for (FlowPayRecord flowPayRecord : datas) {

				FlowProductInfo flowProductInfo = flowProductInfoService.get(flowPayRecord.getProductId());
				if (null == flowProductInfo) {
					LOG.error("productId={}找不到对应的流量产品", flowPayRecord.getProductId());
					flowPayRecord.setSendStatus(FlowPayRecord.SEND_STATUS_FAIL);
					flowPayRecord.setRemark("找不到对应的流量产品");
					flowPayRecordService.update(flowPayRecord);
					continue;
				}

				try {
					String extOrder = "F" + OrderSeqGen.createApplyId();
					FlowExchangeLog flowExchangeLog = new FlowExchangeLog();
					flowExchangeLog.setCreateTime(new Date());
					flowExchangeLog.setItemCount(1);
					flowExchangeLog.setMobile(flowPayRecord.getMobile());
					flowExchangeLog.setProductId(flowPayRecord.getProductId());
					flowExchangeLog.setSourceType("00");
					flowExchangeLog.setMobileHome("");
					flowExchangeLog.setMobileOperator("");
					flowExchangeLog.setFlowVoucherId(extOrder);
					flowPayRecord.setSendStatus(FlowPayRecord.SEND_STATUS_SENDING);
					flowPayRecordService.update(flowPayRecord);
					FossFlowMakeBack resp = dispatchFlow(flowPayRecord.getMobile(), flowProductInfo.getPackageId(),
							extOrder);
					if (FossFlowMakeBack.OK.equals(resp.getCode())) {
						flowPayRecord.setSendStatus(FlowPayRecord.SEND_STATUS_GATE_OK);
						flowExchangeLog.setFlag(FlowPayRecord.SEND_STATUS_GATE_OK);
						flowExchangeLog.setExchangeOrderId(resp.getOrderId());
					} else {
						flowPayRecord.setSendStatus(FlowPayRecord.SEND_STATUS_FAIL);
						flowExchangeLog.setFlag(FlowPayRecord.SEND_STATUS_FAIL);
					}
					flowExchangeLog.setRecordId(flowPayRecord.getRecordId());
					flowPayRecordService.update(flowPayRecord);
					flowExchangeLogService.update(flowExchangeLog);
				} catch (Exception e) {
					LOG.error(e.getMessage(), e);
				}
			}
		} while (list.getDatas().size() <= 0);
	}

	public FossFlowMakeBack dispatchFlow(String mobile, String packageId, String extOrder) throws Exception {
		FlowMakeOrderReqMesg reqMesg = new FlowMakeOrderReqMesg();
		reqMesg.setUser(mobile);
		reqMesg.setPackageId(packageId);
		reqMesg.setExtorder(extOrder);
		return FlowDispatch.getInstance().dispatchFlow(reqMesg, SystemConfig.getInstance().geFlowAppId(),
				SystemConfig.getInstance().getFlowAppkey(), SystemConfig.getInstance().getDispatchUrl());
	}
}
