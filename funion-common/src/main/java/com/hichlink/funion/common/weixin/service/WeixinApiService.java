package com.hichlink.funion.common.weixin.service;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hichlink.funion.common.util.HttpClientUtil;
import com.hichlink.funion.common.weixin.entity.AccessToken;
import com.hichlink.funion.common.weixin.entity.ApiToken;
import com.hichlink.funion.common.weixin.entity.ApiUserinfo;
import com.hichlink.funion.common.weixin.entity.JSTicket;
import com.hichlink.funion.common.weixin.entity.OpenUserinfo;

@Component("weixinApiService")
public class WeixinApiService implements WeixinApi {
	private static Logger log = LoggerFactory.getLogger(WeixinApiService.class);
	public static final String GET_API_TOKEN_URL = "{0}/cgi-bin/token?grant_type=client_credential&appid={1}&secret={2}";
	public static final String GET_API_USERINFO_URL = "{0}/cgi-bin/user/info?access_token={1}&openid={2}&lang=zh_CN";
	public static final String GET_API_JS_TICKET_URL = "{0}/cgi-bin/ticket/getticket?access_token={1}&type=jsapi";
	public static final String GET_ACCESS_TOKEN_URL = "{0}/sns/oauth2/access_token?appid={1}&secret={2}&code={3}&grant_type=authorization_code";
	public static final String GET_OPEN_USERINFO_URL = "{0}/sns/userinfo?access_token={1}&openid={2}&lang=zh_CN";
	public static final String AUTH_URL = "{0}/connect/oauth2/authorize?appid={1}&redirect_uri={2}&response_type=code&scope={3}&state=123#wechat_redirect";

	public static final String SNSAPI_BASE_SIGN = "snsapi_base";
	public static final String SNSAPI_USERINFO_SIGN = "snsapi_userinfo";
	private String openDomain = "https://open.weixin.qq.com";
	private String apiDomain = "https://api.weixin.qq.com";

	public WeixinApiService() {
	}

	public String getApiDomain() {
		return apiDomain;
	}

	public void setApiDomain(String apiDomain) {
		this.apiDomain = apiDomain;
	}

	public String getOpenDomain() {
		return openDomain;
	}

	public void setOpenDomain(String openDomain) {
		this.openDomain = openDomain;
	}

	public String getAuthUrlBySnsapiBase(String appId, String redirectUri) {
		return MessageFormat.format(AUTH_URL, new Object[] { getOpenDomain(), appId, redirectUri, SNSAPI_BASE_SIGN });
	}

	public String getAuthUrlBySnsapiUserInfo(String appId, String redirectUri) {
		return MessageFormat.format(AUTH_URL,
				new Object[] { getOpenDomain(), appId, redirectUri, SNSAPI_USERINFO_SIGN });
	}

	public AccessToken getAccessToken(String appId, String secret, String code) {
		String url = MessageFormat.format(GET_ACCESS_TOKEN_URL, new Object[] { getApiDomain(), appId, secret, code });
		String response = HttpClientUtil.sendDataHttpsViaGet(url);
		if (StringUtils.isBlank(response)) {
			return null;
		}
		JSONObject json = JSON.parseObject(response);
		if (null == json || null != json.get("errcode")) {
			return null;
		}
		return JSON.parseObject(response, AccessToken.class);
	}

	public ApiToken getToken(String appId, String secret) {
		String url = MessageFormat.format(GET_API_TOKEN_URL, new Object[] { getApiDomain(), appId, secret });
		String response = HttpClientUtil.sendDataHttpsViaGet(url);
		if (StringUtils.isBlank(response)) {
			return null;
		}
		JSONObject json = JSON.parseObject(response);
		if (null == json || null != json.get("errcode")) {
			return null;
		}
		return JSON.parseObject(response, ApiToken.class);
	}

	public JSTicket getJSTicket(String accessToken) {
		String url = MessageFormat.format(GET_API_JS_TICKET_URL, new Object[] { getApiDomain(), accessToken });
		String response = HttpClientUtil.sendDataHttpsViaGet(url);
		if (StringUtils.isBlank(response)) {
			return null;
		}
		JSONObject json = JSON.parseObject(response);
		if (null == json || json.getIntValue("errcode") != 0) {
			return null;
		}
		return JSON.parseObject(response, JSTicket.class);
	}

	public OpenUserinfo getOpenUserinfo(String token, String openId) {
		String url = MessageFormat.format(GET_OPEN_USERINFO_URL, new Object[] { getApiDomain(), token, openId });
		String response = HttpClientUtil.sendDataHttpsViaGet(url);
		if (StringUtils.isBlank(response)) {
			return null;
		}
		JSONObject json = JSON.parseObject(response);
		if (null == json || null != json.get("errcode")) {
			return null;
		}
		return JSON.parseObject(response, OpenUserinfo.class);
	}

	public ApiUserinfo getApiUserinfo(String token, String openId) {
		String url = MessageFormat.format(GET_API_USERINFO_URL, new Object[] { getApiDomain(), token, openId });
		String response = HttpClientUtil.sendDataHttpsViaGet(url);
		if (StringUtils.isBlank(response)) {
			return null;
		}
		JSONObject json = JSON.parseObject(response);
		if (null == json || null != json.get("errcode")) {
			return null;
		}
		return JSON.parseObject(response, ApiUserinfo.class);
	}
}
