package com.hichlink.funion.common.weixin.service;

import com.hichlink.funion.common.weixin.entity.WxOrderInfo;
import com.hichlink.funion.common.weixin.entity.WxOrderInfoResp;
import com.hichlink.funion.common.weixin.entity.WxRefundReq;
import com.hichlink.funion.common.weixin.entity.WxRefundResp;

public interface WeixinPay {
	WxOrderInfoResp sendOrder(WxOrderInfo wxOrderInfo, String key);

	WxRefundResp refund(WxRefundReq wxRefundReq, String key,String certPath);
}
