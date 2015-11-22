package com.hichlink.funion.portal.common.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.aspire.webbas.core.util.WebUtil;

public class ControllerInterceptor extends HandlerInterceptorAdapter {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ControllerInterceptor.class);

	private String translateParamMap2Str(Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		String tmp = "";
		for (String key : map.keySet()) {
			Object[] objs = (Object[]) map.get(key);
			tmp = "";
			for (Object obj : objs) {
				tmp += obj + ",";
			}
			sb.append(key
					+ "="
					+ (!"".equals(tmp) ? tmp.substring(0, tmp.length() - 1)
							: "") + "&");
		}
		return sb.toString().length() > 0 ? sb.substring(0, sb.toString()
				.length() - 1) : sb.toString();
	}

	protected boolean isAjaxRequest(HttpServletRequest request) {
		String xRequestedWith = request.getHeader("x-requested-with");
		return null != xRequestedWith
				&& "XMLHttpRequest".equalsIgnoreCase(xRequestedWith) ? true
				: false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		try {
			LOGGER.debug("ip=" + WebUtil.getIpAddr(request) + ",进入URI="
					+ request.getRequestURI() + ",QueryString="
					+ translateParamMap2Str(request.getParameterMap()));
		} catch (Exception e) {
			LOGGER.error("ControllerInterceptor.preHandle日志记录出错", e);
		}
		return super.preHandle(request, response, handler);
	}

	protected Map<String, Object> fail(String message) {
		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		modelMap.put("message", message);
		modelMap.put("success", false);
		return modelMap;
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if (null != ex) {
			LOGGER.error("[ControllerInterceptor.afterCompletion]", ex);
			if (isAjaxRequest(request)) {
				WebUtil.output2JSONStr(response, fail(ex.getMessage()));
			} else {
				WebUtil.output2Text(response, ex.getMessage());
			}
			return;
		}
		LOGGER.debug("ip=" + WebUtil.getIpAddr(request) + ",离开URI="
				+ request.getRequestURI());
	}
}
