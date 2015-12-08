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
import com.hichlink.funion.common.weixin.WeixinMchPayBiz;
import com.hichlink.funion.common.weixin.entity.WxMchOrderInfo;
import com.hichlink.funion.common.weixin.entity.WxMchOrderInfoResp;
import com.hichlink.funion.portal.common.config.SystemConfig;

@Service("refundBiz")
public class RefundBiz {
	private static final Logger LOG = LoggerFactory.getLogger(RefundBiz.class);
	@Autowired
	private FlowPayRecordService flowPayRecordService;
	private final static BigDecimal HUNDRED = new BigDecimal(100);
	@Autowired
	private WeixinMchPayBiz weixinMchPayBiz;

	public void batchRefund() {
		List<FlowPayRecord> list = flowPayRecordService.findRefundList();
		for (FlowPayRecord data : list) {
			String appId = SystemConfig.getInstance().getAppId();
			WxMchOrderInfo wxMchOrderInfo = new WxMchOrderInfo();
			wxMchOrderInfo.setAmount(data.getCostPrice().multiply(HUNDRED).intValue());
			wxMchOrderInfo.setCheckName("");
			wxMchOrderInfo.setDesc("流量向前冲退款");
			wxMchOrderInfo.setMchAppid(appId);
			wxMchOrderInfo.setOpenId(data.getOpenId());
			String orderId = OrderSeqGen.createApplyId();
			wxMchOrderInfo.setPartneTradeNo(orderId);
			wxMchOrderInfo.setReUserName("");
			wxMchOrderInfo.setCheckName("NO_CHECK");
			wxMchOrderInfo.setNonceStr(UUID.randomUUID().toString().replaceAll("-", ""));
			wxMchOrderInfo.setSpbillCreateIp("127.0.0.1");
			String certPath = SystemConfig.getInstance().getCertPath();
			data.setIsRefund(FlowPayRecord.IS_REFUND_YES);
			flowPayRecordService.update(data);
			WxMchOrderInfoResp resp = weixinMchPayBiz.sendOrder(wxMchOrderInfo, certPath);
			LOG.debug("resp={}", resp.toString());
			if (resp.isSuccess()) {
				data.setRefundTime(new Date());
			} else {
				data.setIsRefund(FlowPayRecord.IS_REFUND_NO);
			}
			flowPayRecordService.update(data);
		}
	}
}
