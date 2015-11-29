package com.hichlink.funion.common.weixin.service;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.hichlink.funion.common.util.HttpClientUtil;
import com.hichlink.funion.common.util.MediaTypes;
import com.hichlink.funion.common.util.XStreamHandle;
import com.hichlink.funion.common.weixin.entity.WxMchOrderInfo;
import com.hichlink.funion.common.weixin.entity.WxMchOrderInfoResp;
import com.hichlink.funion.common.weixin.entity.WxOrderInfoResp;

@Component("weixinMchPayService")
public class WeixinMchPayService implements WeixinMchPay {
	private static Logger log = LoggerFactory.getLogger(WeixinMchPayService.class);
	public static final String PAY_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

	public WxMchOrderInfoResp sendOrder(WxMchOrderInfo wxMchOrderInfo, String key) {
		String result = null;
		try {
			result = HttpClientUtil.postByBody(PAY_URL, wxMchOrderInfo.toPayXml(key), MediaTypes.APPLICATION_XML_UTF_8);
		} catch (HttpException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		if (StringUtils.isNotBlank(result)) {
			return XStreamHandle.toBean(result, WxMchOrderInfoResp.class);
		}
		return null;
	}
}
