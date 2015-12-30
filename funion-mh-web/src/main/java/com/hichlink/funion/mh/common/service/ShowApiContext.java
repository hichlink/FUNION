package com.hichlink.funion.mh.common.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.aspire.webbas.core.util.MD5Util;
import com.hichlink.funion.mh.common.config.SystemConfig;
import com.hichlink.funion.mh.common.entity.MobileHomeInfo;
import com.hichlink.funion.mh.common.util.HttpClientUtil;

public class ShowApiContext {
	private static ShowApiContext context = null;
	private static Logger log = LoggerFactory.getLogger(ShowApiContext.class);

	private ShowApiContext() {

	}

	synchronized public static ShowApiContext getContext() {
		if (null == context) {
			context = new ShowApiContext();
		}
		return context;
	}

	private String sign(Map<String, String> params, String secret) {
		StringBuilder sb = new StringBuilder();
		for (String key : params.keySet()) {
			sb.append(key + params.get(key));
		}
		sb.append(secret);
		return MD5Util.md5Encode(sb.toString());
	}

	private String getTimestamp() {
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(new Date());
	}

	private String map2UrlParam(Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		for (String key : params.keySet()) {
			if (sb.toString().length() > 0){
				sb.append("&");
			}
			sb.append(key + "=" + params.get(key));
		}
		return sb.toString();
	}
	private static final Map<Integer,String> OPERATOR_CODE_MAP = new HashMap<Integer,String>();
	static{
		//1为移动 2为电信 3为联通
		OPERATOR_CODE_MAP.put(1, "LT");
		OPERATOR_CODE_MAP.put(3, "YD");
		OPERATOR_CODE_MAP.put(2, "DX");
	}
	private String getOperatorCode(Integer code){
		return OPERATOR_CODE_MAP.get(code);
	}
	public MobileHomeInfo getMobileHome(String mobile) {
		Map<String, String> params = new LinkedHashMap<String, String>();
		params.put("num", mobile);
		params.put("showapi_appid", SystemConfig.getInstance().getShowapiAppid());
		params.put("showapi_timestamp", getTimestamp());
		params.put("showapi_sign", sign(params, SystemConfig.getInstance().getShowapiAppkey()).toLowerCase());
		String resp = HttpClientUtil
				.sendDataHttpViaGet(SystemConfig.getInstance().getShowapiMhUrl() + map2UrlParam(params));
		if (StringUtils.isBlank(resp)) {
			return null;
		}
		JSONObject jsonObject = JSONObject.parseObject(resp);
		if (jsonObject.getInteger("showapi_res_code") == 0){
			JSONObject content = jsonObject.getJSONObject("showapi_res_body");
			MobileHomeInfo mobileHomeInfo = new MobileHomeInfo();
			mobileHomeInfo.setMobileNo(content.getString("num"));
			mobileHomeInfo.setCity(content.getString("city"));
			mobileHomeInfo.setCreateDate(new Date());
			mobileHomeInfo.setProvince(content.getString("prov"));
			mobileHomeInfo.setAreaCode(content.getString("provCode").substring(0, 2));
			mobileHomeInfo.setOperatorName(content.getString("name"));
			mobileHomeInfo.setOperatorCode(getOperatorCode(content.getInteger("type")));
			return mobileHomeInfo;
		}
		log.debug(resp);
		return null;
	}
}
