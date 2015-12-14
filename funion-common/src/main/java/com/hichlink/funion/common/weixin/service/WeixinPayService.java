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
import com.hichlink.funion.common.weixin.entity.WxOrderInfo;
import com.hichlink.funion.common.weixin.entity.WxOrderInfoResp;
import com.hichlink.funion.common.weixin.entity.WxRefundReq;
import com.hichlink.funion.common.weixin.entity.WxRefundResp;

@Component("weixinPayService")
public class WeixinPayService implements WeixinPay {
	public static final String PAY_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	public static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	private static Logger log = LoggerFactory.getLogger(WeixinPayService.class);

	public WxOrderInfoResp sendOrder(WxOrderInfo wxOrderInfo, String key) {
		String result = null;
		try {
			result = HttpClientUtil.postByBody(PAY_ORDER_URL, wxOrderInfo.toPayXml(key),
					MediaTypes.APPLICATION_XML_UTF_8);
		} catch (HttpException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		if (StringUtils.isNotBlank(result)) {
			return XStreamHandle.toBean(result, WxOrderInfoResp.class);
		}
		return null;
	}

	@Override
	public WxRefundResp refund(WxRefundReq wxRefundReq, String key,String certPath) {
		String result = null;
		try {
			result = HttpClientUtil.postByBodyWithCert(REFUND_URL, wxRefundReq.toPayXml(key), MediaTypes.APPLICATION_XML_UTF_8,certPath);
		} catch (HttpException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		if (StringUtils.isNotBlank(result)) {
			return XStreamHandle.toBean(result, WxRefundResp.class);
		}
		return null;
	}

}
