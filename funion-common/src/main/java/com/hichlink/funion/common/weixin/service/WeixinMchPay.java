package com.hichlink.funion.common.weixin.service;

import com.hichlink.funion.common.weixin.entity.WxMchOrderInfo;
import com.hichlink.funion.common.weixin.entity.WxMchOrderInfoResp;

public interface WeixinMchPay {
	WxMchOrderInfoResp sendOrder(WxMchOrderInfo wxMchOrderInfo,String key);
}
