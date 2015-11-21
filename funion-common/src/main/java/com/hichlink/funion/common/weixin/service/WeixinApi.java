package com.hichlink.funion.common.weixin.service;

import com.hichlink.funion.common.weixin.entity.AccessToken;
import com.hichlink.funion.common.weixin.entity.ApiToken;
import com.hichlink.funion.common.weixin.entity.ApiUserinfo;
import com.hichlink.funion.common.weixin.entity.JSTicket;
import com.hichlink.funion.common.weixin.entity.OpenUserinfo;

public interface WeixinApi {
	String getAuthUrlBySnsapiBase(String appId, String redirectUri);

	String getAuthUrlBySnsapiUserInfo(String appId, String redirectUri);

	OpenUserinfo getOpenUserinfo(String token, String openId);

	AccessToken getAccessToken(String appId, String secret, String code);

	ApiToken getToken(String appId, String secret);

	ApiUserinfo getApiUserinfo(String token, String appId);

	JSTicket getJSTicket(String accessToken);
}
