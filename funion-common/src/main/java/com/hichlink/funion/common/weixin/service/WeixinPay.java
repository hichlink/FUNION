package com.hichlink.funion.common.weixin.service;

import com.hichlink.funion.common.weixin.entity.WxOrderInfo;
import com.hichlink.funion.common.weixin.entity.WxOrderInfoResp;

public interface WeixinPay {
	WxOrderInfoResp sendOrder(WxOrderInfo wxOrderInfo, String key);
}
