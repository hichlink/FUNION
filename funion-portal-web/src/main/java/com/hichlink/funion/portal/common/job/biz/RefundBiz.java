package com.hichlink.funion.portal.common.job.biz;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hichlink.funion.common.entity.FlowPayRecord;
import com.hichlink.funion.common.service.FlowPayRecordService;
import com.hichlink.funion.common.util.OrderSeqGen;
import com.hichlink.funion.common.weixin.WeixinPayBiz;
import com.hichlink.funion.common.weixin.entity.WxRefundReq;
import com.hichlink.funion.common.weixin.entity.WxRefundResp;
import com.hichlink.funion.portal.common.config.SystemConfig;

@Service("refundBiz")
public class RefundBiz {
	private static final Logger LOG = LoggerFactory.getLogger(RefundBiz.class);
	@Autowired
	private FlowPayRecordService flowPayRecordService;
	private final static BigDecimal HUNDRED = new BigDecimal(100);
	@Autowired
	private WeixinPayBiz weixinPayBiz;

	public void batchRefund() {
		List<FlowPayRecord> list = flowPayRecordService.findRefundList();
		for (FlowPayRecord data : list) {
			String appId = SystemConfig.getInstance().getAppId();
			/*
			 * WxMchOrderInfo wxMchOrderInfo = new WxMchOrderInfo();
			 * wxMchOrderInfo.setAmount(data.getCostPrice().multiply(HUNDRED).
			 * intValue()); wxMchOrderInfo.setCheckName("");
			 * wxMchOrderInfo.setDesc("流量向前冲退款");
			 * wxMchOrderInfo.setMchAppid(appId);
			 * wxMchOrderInfo.setOpenId(data.getOpenId()); String orderId =
			 * OrderSeqGen.createApplyId();
			 * wxMchOrderInfo.setPartneTradeNo(orderId);
			 * wxMchOrderInfo.setReUserName("");
			 * wxMchOrderInfo.setCheckName("NO_CHECK");
			 * wxMchOrderInfo.setNonceStr(UUID.randomUUID().toString().
			 * replaceAll("-", ""));
			 * wxMchOrderInfo.setSpbillCreateIp("127.0.0.1");
			 */
			WxRefundReq wxRefundReq = new WxRefundReq();
			wxRefundReq.setAppId(appId);
			wxRefundReq.setNonceStr(UUID.randomUUID().toString().replaceAll("-", ""));
			wxRefundReq.setOutRefundNo(OrderSeqGen.createApplyId());
			wxRefundReq.setRefundFee(data.getSettlementPrice().multiply(HUNDRED).intValue());
			wxRefundReq.setTotalFee(data.getSettlementPrice().multiply(HUNDRED).intValue());
			wxRefundReq.setOutTradeNo(data.getOutTradeNo());
			data.setIsRefund(FlowPayRecord.IS_REFUND_YES);
			flowPayRecordService.update(data);
			String certPath = SystemConfig.getInstance().getCertPath();
			WxRefundResp resp = weixinPayBiz.refund(wxRefundReq,certPath);
			LOG.debug("resp={}", null != resp ?resp.toString():"");
			if (resp.isSuccess()) {
				data.setRefundTime(new Date());
			} else {
				data.setIsRefund(FlowPayRecord.IS_REFUND_NO);
			}
			flowPayRecordService.update(data);
		}
	}
}
