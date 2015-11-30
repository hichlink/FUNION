package com.hichlink.funion.portal.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hichlink.funion.common.weixin.entity.OpenUserinfo;

public class SessionUtil {
	public static final String SESSION_REGISTER_WXUSERINFO = "registerWxUserInfo";
	public static final String SESSION_AGENT_UUID = "MyUUID";
	public static final String SESSION_PAYOPENID = "PayOpenId";

	public static HttpServletRequest getRequest() {
		if (null != RequestContextHolder.getRequestAttributes()) {
			return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		} else {
			return null;
		}
	}

	public static HttpSession getSession() {
		return getRequest().getSession();
	}

	public static void setSession(String key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static Object getSession(String key) {
		return getSession().getAttribute(key);
	}

	public static OpenUserinfo getRegisterWxUserInfo() {
		return (OpenUserinfo) getSession(SESSION_REGISTER_WXUSERINFO);
	}

	public static void setRegisterWxUserInfo(OpenUserinfo data) {
		setSession(SESSION_REGISTER_WXUSERINFO, data);
	}

	public static void setUUID(String uuid) {
		setSession(SESSION_AGENT_UUID, uuid);
	}

	public static String getUUID() {
		return (String) getSession(SESSION_AGENT_UUID);
	}

	public static void setPayOpenId(String openId) {
		setSession(SESSION_PAYOPENID, openId);
	}

	public static String getPayOpenId() {
		return (String) getSession(SESSION_PAYOPENID);
	}
}
