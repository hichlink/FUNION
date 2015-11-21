package com.hichlink.funion.common.weixin.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.alibaba.fastjson.annotation.JSONField;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AccessToken {
	@JSONField(name = "access_token")
	private String accessToken;
	@JSONField(name = "expires_in")
	private Integer expiresIn;
	@JSONField(name = "refresh_token")
	private String refreshToken;
	@JSONField(name = "openid")
	private String openId;
	@JSONField(name = "scope")
	private String scope;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Integer getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	@Override
	public String toString() {
		return "AccessToken [accessToken=" + accessToken + ", expiresIn=" + expiresIn + ", refreshToken=" + refreshToken
				+ ", openId=" + openId + ", scope=" + scope + "]";
	}
}
