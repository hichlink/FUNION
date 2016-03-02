package com.hichlink.funion.portal.web;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.aspire.webbas.core.util.WebUtil;
import com.hichlink.funion.common.entity.WxAccessConf;
import com.hichlink.funion.common.util.Signature;
import com.hichlink.funion.common.weixin.WeixinApiBiz;
import com.hichlink.funion.common.weixin.entity.AccessToken;
import com.hichlink.funion.portal.common.config.SystemConfig;

import net.sf.json.JSONObject;

@Controller
public class WeixinProxyController extends BaseActionController {
	private static Logger log = LoggerFactory.getLogger(WeixinProxyController.class);
	@Autowired
	private WeixinApiBiz weixinApiBiz;
	private static final String SESSION_REDIRECT_URI = "redirect_uri";

	@RequestMapping(value = "/connect/oauth2/authorize")
	public void authorize(HttpServletRequest request, HttpServletResponse response, String appid, String redirect_uri,
			String response_type, String scope, String state) {
		request.getSession().setAttribute(SESSION_REDIRECT_URI, redirect_uri);
		String appId = SystemConfig.getInstance().getAppId();
		String redirectUri = SystemConfig.getInstance().getDomain() + request.getContextPath() + "/proxy/redirect";
		String jumpUrl = "";
		try {
			if ("snsapi_userinfo".equalsIgnoreCase(scope)) {
				jumpUrl = weixinApiBiz.getAuthUrlBySnsapiUserInfo(appId, URLEncoder.encode(redirectUri, "utf-8"));
			} else {
				jumpUrl = weixinApiBiz.getAuthUrlBySnsapiBase(appId, URLEncoder.encode(redirectUri, "utf-8"));
			}
			response.sendRedirect(jumpUrl);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	@RequestMapping(value = "/redirect")
	public void authorizeRedirect(HttpServletRequest request, HttpServletResponse response, String code, String state) {
		String redirectUri = (String) request.getSession().getAttribute(SESSION_REDIRECT_URI);
		if (isEmpty(redirectUri)) {
			return;
		}
		redirectUri += redirectUri.lastIndexOf("?") > -1 ? "&" : "?" + "code=" + code + "&state=" + state;
		try {
			response.sendRedirect(redirectUri);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	@RequestMapping(value = "/sns/oauth2/access_token")
	public void accessToken(HttpServletRequest request, HttpServletResponse response, String appid, String secret,
			String code, String grant_type) throws IOException {
		String appId = SystemConfig.getInstance().getAppId();
		AccessToken accessToken = weixinApiBiz.getAccessToken(appId, code);
		WebUtil.output(response, JSON.toJSONString(accessToken), "application/json");
	}

	@RequestMapping(value = "/cgi-bin/token")
	@ResponseBody
	public void token(HttpServletRequest request, HttpServletResponse response, String appid, String secret,
			String grant_type) throws IOException {
		String appId = SystemConfig.getInstance().getAppId();
		try {
			WxAccessConf wc = weixinApiBiz.getWxAccessConf(appId);
			if (null != wc) {
				int second = (int) ((wc.getExpiresTime().getTime() - (new Date()).getTime()) / 1000);
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("access_token", wc.getAccessToken());
				result.put("expires_in", second);
				WebUtil.output(response, JSON.toJSONString(result), "application/json");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			WebUtil.output(response, e.getMessage(), "application/json");
		}
	}

	private void outputJSONP(HttpServletRequest request, HttpServletResponse response, String content)
			throws IOException {
		String jsonp = request.getParameter("jsonpcallback");
		WebUtil.output(response, jsonp + "(" + content + ")", "application/javascript");
	}

	@RequestMapping(value = "/getJsConfig")
	public void getJsConfig(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
		if (StringUtils.isBlank(url)) {
			log.info("url传递失败");
			outputJSONP(request, response, "传递失败");
			return;
		}
		String appId = SystemConfig.getInstance().getAppId();
		String jsTicket = weixinApiBiz.getJsapiTicket(appId);
		log.debug("jsTicket:=" + jsTicket);
		Map<String, String> ret = Signature.sign(jsTicket, url);
		log.debug("ret:=" + ret);
		ret.put("appid", appId);
		outputJSONP(request, response, JSONObject.fromObject(ret).toString());
	}
}
