package com.hichlink.funion.portal.web;

import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.webbas.core.web.BaseController;
import com.hichlink.funion.common.util.Signature;
import com.hichlink.funion.common.weixin.WeixinApiBiz;
import com.hichlink.funion.common.weixin.entity.AccessToken;
import com.hichlink.funion.common.weixin.entity.ApiUserinfo;
import com.hichlink.funion.common.weixin.entity.OpenUserinfo;

@Controller
@RequestMapping("/weixin")
public class WeixinController extends BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(WeixinController.class);
	@Autowired
	private WeixinApiBiz weixinApiBiz;

	@RequestMapping(value = "/getJsConfig")
	@ResponseBody
	public Map<String, Object> getJsConfig(String url, String appId) {
		if (StringUtils.isBlank(url)) {
			LOG.info("url传递失败");
			return fail("传递失败");
		}

		String jsTicket = weixinApiBiz.getJsapiTicket(appId);
		LOG.debug("jsTicket:=" + jsTicket);
		Map<String, String> ret = Signature.sign(jsTicket, url);
		LOG.debug("ret:=" + ret);
		ret.put("appid", appId);
		return success(ret);
	}

	@RequestMapping(value = "/{actId}/access.do")
	public void access(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(defaultValue = "") String code, @PathVariable String actId) throws Exception {
		String projectName = request.getContextPath();
		if (StringUtils.isBlank(actId)) {
			LOG.debug("actId:=null");
			return;
		}
		String appId = "wxa7e557056c7d41bb";
		if (StringUtils.isBlank(code)) {
			String redirectUri = URLEncoder.encode("http://poc.szwisdom.cn/foss-portal/weixin/" + actId + "/access.do",
					"utf-8");
			response.sendRedirect(weixinApiBiz.getAuthUrlBySnsapiBase(appId, redirectUri));
		} else {
			AccessToken accessToken = weixinApiBiz.getAccessToken(appId, code);

			if (null != accessToken) {
				LOG.debug("accessToken={}", accessToken.toString());
				OpenUserinfo openUserinfo = weixinApiBiz.getOpenUserinfo(accessToken.getAccessToken(),
						accessToken.getOpenId());
				if (null != openUserinfo) {
					LOG.debug("openUserinfo={}", openUserinfo.toString());
				}
				String token = weixinApiBiz.getToken(appId);
				LOG.debug("token={}", token);
				if (StringUtils.isNotBlank(token)) {
					ApiUserinfo apiUserinfo = weixinApiBiz.getApiUserinfo(appId,openUserinfo.getOpenid());
					if (null != apiUserinfo) {
						LOG.debug("apiUserinfo={}", apiUserinfo.toString());
					}
				}
				response.sendRedirect(projectName + "/pages/index.html?openId=" + accessToken.getOpenId());
			} else {
				LOG.error("获取不到用户Token.");
			}
		}
	}
}
