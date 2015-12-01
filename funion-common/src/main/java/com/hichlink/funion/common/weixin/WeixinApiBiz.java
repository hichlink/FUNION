package com.hichlink.funion.common.weixin;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.aspire.webbas.core.exception.MyException;
import com.hichlink.funion.common.dao.WxAccessConfMapper;
import com.hichlink.funion.common.entity.WxAccessConf;
import com.hichlink.funion.common.weixin.entity.AccessToken;
import com.hichlink.funion.common.weixin.entity.ApiToken;
import com.hichlink.funion.common.weixin.entity.ApiUserinfo;
import com.hichlink.funion.common.weixin.entity.JSTicket;
import com.hichlink.funion.common.weixin.entity.OpenUserinfo;
import com.hichlink.funion.common.weixin.service.WeixinApi;

@Component
public class WeixinApiBiz {
	@Autowired
	@Qualifier("weixinApiService")
	private WeixinApi weixinApi;
	@Autowired
	private WxAccessConfMapper wxAccessConfMapper;

	public WeixinApiBiz() {

	}

	public WeixinApiBiz(WeixinApi weixinApi) {
		this.weixinApi = weixinApi;
	}

	private WxAccessConf getWxAccessConf(String appId) {
		WxAccessConf bean = new WxAccessConf();
		bean.setAppId(appId);
		WxAccessConf wc = wxAccessConfMapper.getByWxConf(bean);
		if (null == wc) {
			throw new MyException("根据AppId[" + appId + "]取不到对应的微信公众号配置");
		}
		if (wc.getExpiresTime() == null || StringUtils.isBlank(wc.getAccessToken())
				|| new Date().getTime() > wc.getExpiresTime().getTime()) {
			ApiToken apiAccessToken = weixinApi.getToken(wc.getAppId(), wc.getAppSecret());
			if (null == apiAccessToken) {
				throw new MyException("根据AppId[" + appId + "]取不到对应的Token");
			}
			wc.setAccessToken(apiAccessToken.getAccessToken());
			wc.setExpiresTime(new Date(new Date().getTime() + apiAccessToken.getExpiresIn() * 1000));
			wxAccessConfMapper.updateByPrimaryKey(wc);
		}
		return wc;
	}

	public String getAuthUrlBySnsapiBase(String appId, String redirectUri) {
		return weixinApi.getAuthUrlBySnsapiBase(appId, redirectUri);
	}

	public String getAuthUrlBySnsapiUserInfo(String appId, String redirectUri) {
		return weixinApi.getAuthUrlBySnsapiUserInfo(appId, redirectUri);
	}

	public OpenUserinfo getOpenUserinfo(String accessToken, String openId) {
		return weixinApi.getOpenUserinfo(accessToken, openId);
	}

	public String getJsapiTicket(String appId) {
		WxAccessConf wc = getWxAccessConf(appId);
		if (wc.getJsapExpiresTime() == null || StringUtils.isBlank(wc.getJsapiTicket())
				|| new Date().getTime() > wc.getJsapExpiresTime().getTime()) {
			String accessToken = getToken(appId);
			JSTicket jSTicket = weixinApi.getJSTicket(accessToken);
			if (null == jSTicket) {
				throw new MyException("根据AppId[" + appId + "]取不到对应的jSTicket");
			}
			wc.setJsapiTicket(jSTicket.getTicket());
			wc.setJsapExpiresTime(new Date(new Date().getTime() + jSTicket.getExpiresIn() * 1000));
			wxAccessConfMapper.updateByPrimaryKey(wc);
		}
		return wc.getJsapiTicket();
	}

	public ApiUserinfo getApiUserinfo(String appId,String openId) {
		WxAccessConf wc = getWxAccessConf(appId);
		if (null == wc || StringUtils.isBlank(wc.getAccessToken())) {
			throw new MyException("根据AppId[" + appId + "]取不到对应的Token");
		}
		return weixinApi.getApiUserinfo(wc.getAccessToken(), openId);

	}

	public AccessToken getAccessToken(String appId, String code) {
		WxAccessConf wxAccessConf = getWxAccessConf(appId);
		return weixinApi.getAccessToken(appId, wxAccessConf.getAppSecret(), code);
	}

	public String getToken(String appId) {
		WxAccessConf wc = getWxAccessConf(appId);
		return wc.getAccessToken();
	}
}
