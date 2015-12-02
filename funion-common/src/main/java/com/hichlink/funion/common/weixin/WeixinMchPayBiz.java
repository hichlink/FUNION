package com.hichlink.funion.common.weixin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.aspire.webbas.core.exception.MyException;
import com.hichlink.funion.common.dao.WxAccessConfMapper;
import com.hichlink.funion.common.entity.WxAccessConf;
import com.hichlink.funion.common.weixin.entity.WxMchOrderInfo;
import com.hichlink.funion.common.weixin.entity.WxMchOrderInfoResp;
import com.hichlink.funion.common.weixin.service.WeixinMchPay;

@Component
public class WeixinMchPayBiz {
	private static Logger log = LoggerFactory.getLogger(WeixinMchPayBiz.class);
	@Autowired
	@Qualifier("weixinMchPayService")
	private WeixinMchPay weixinMchPay;
	@Autowired
	private WxAccessConfMapper wxAccessConfMapper;

	public WxMchOrderInfoResp sendOrder(WxMchOrderInfo wxMchOrderInfo,String certPath) {
		WxAccessConf wc = getWxAccessConf(wxMchOrderInfo.getMchAppid());
		wxMchOrderInfo.setMchid(wc.getWxMerchantNo());
		return weixinMchPay.sendOrder(wxMchOrderInfo, wc.getApiKey(),certPath);
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
}
