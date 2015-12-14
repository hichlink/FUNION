package com.hichlink.funion.common.weixin;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.aspire.webbas.core.exception.MyException;
import com.hichlink.funion.common.dao.WxAccessConfMapper;
import com.hichlink.funion.common.entity.WxAccessConf;
import com.hichlink.funion.common.util.Signature;
import com.hichlink.funion.common.weixin.entity.WxOrderInfo;
import com.hichlink.funion.common.weixin.entity.WxOrderInfoResp;
import com.hichlink.funion.common.weixin.entity.WxRefundReq;
import com.hichlink.funion.common.weixin.entity.WxRefundResp;
import com.hichlink.funion.common.weixin.service.WeixinPay;

@Component
public class WeixinPayBiz {
	private static Logger log = LoggerFactory.getLogger(WeixinPayBiz.class);
	@Autowired
	@Qualifier("weixinPayService")
	private WeixinPay weixinPay;
	@Autowired
	private WxAccessConfMapper wxAccessConfMapper;

	public WxOrderInfoResp sendOrder(WxOrderInfo wxOrderInfo) {
		WxAccessConf wc = getWxAccessConf(wxOrderInfo.getAppId());
		wxOrderInfo.setMchId(wc.getWxMerchantNo());
		return weixinPay.sendOrder(wxOrderInfo, wc.getApiKey());
	}
	public WxRefundResp refund(WxRefundReq wxRefundReq){
		WxAccessConf wc = getWxAccessConf(wxRefundReq.getAppId());
		wxRefundReq.setMchId(wc.getWxMerchantNo());
		wxRefundReq.setOpUserId(wc.getWxMerchantNo());
		return weixinPay.refund(wxRefundReq, wc.getApiKey());
	}
	private WxAccessConf getWxAccessConf(String appId) {
		WxAccessConf bean = new WxAccessConf();
		bean.setAppId(appId);
		WxAccessConf wc = wxAccessConfMapper.getByWxConf(bean);
		if (null == wc) {
			throw new MyException("根据AppId[" + appId + "]取不到对应的微信公众号配置");
		}
		return wc;
	}

	public Map<String, String> getPayConfig(String appId, String orderId) {
		WxAccessConf wxAccessConf = getWxAccessConf(appId);
		SortedMap<String, String> json = new TreeMap<String, String>();
		json.put("appId", wxAccessConf.getAppId());
		json.put("timeStamp", System.currentTimeMillis() + "");
		json.put("nonceStr", UUID.randomUUID().toString().replaceAll("-", ""));
		json.put("package", "prepay_id=" + orderId);
		json.put("signType", "MD5");
		String sign = Signature.getSign(json, wxAccessConf.getApiKey());
		json.put("paySign", sign);
		log.debug("支付json:" + JSONObject.toJSONString(json));
		return json;
	}
}
