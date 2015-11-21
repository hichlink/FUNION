package com.hichlink.funion.common.weixin.entity;

import com.alibaba.fastjson.annotation.JSONField;

public class ApiToken {
	@JSONField(name = "access_token")
	private String accessToken;
	@JSONField(name = "expires_in")
	private Integer expiresIn;

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

	@Override
	public String toString() {
		return "ApiToken [accessToken=" + accessToken + ", expiresIn=" + expiresIn + "]";
	}

}
