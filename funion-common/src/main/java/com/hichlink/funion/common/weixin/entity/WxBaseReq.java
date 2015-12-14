package com.hichlink.funion.common.weixin.entity;

import java.util.Map;

import com.hichlink.funion.common.util.Signature;

public abstract class WxBaseReq {
	protected abstract Map<String, String> getFieldMap();

	public String toPayXml(String key) {
		Map<String, String> fileds = getFieldMap();
		String sign = Signature.getSign(fileds, key);
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (String filed : fileds.keySet()) {
			sb.append("<" + filed + "><![CDATA[" + fileds.get(filed) + "]]></" + filed + ">");
		}
		sb.append("<sign><![CDATA[" + sign + "]]></sign>");
		sb.append("</xml>");
		return sb.toString();
	}
}
