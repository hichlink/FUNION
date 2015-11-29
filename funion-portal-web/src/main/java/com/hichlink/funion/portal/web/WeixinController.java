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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.aspire.webbas.core.web.BaseController;
import com.hichlink.funion.common.entity.AgentInfo;
import com.hichlink.funion.common.service.AgentInfoService;
import com.hichlink.funion.common.util.Signature;
import com.hichlink.funion.common.weixin.WeixinApiBiz;
import com.hichlink.funion.common.weixin.entity.AccessToken;
import com.hichlink.funion.common.weixin.entity.OpenUserinfo;
import com.hichlink.funion.portal.common.config.SystemConfig;
import com.hichlink.funion.portal.common.util.SessionUtil;

@Controller
@RequestMapping("/weixin")
public class WeixinController extends BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(WeixinController.class);
	@Autowired
	private WeixinApiBiz weixinApiBiz;
	@Autowired
	private AgentInfoService agentInfoService;

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

	@RequestMapping(value = "/register.do")
	public ModelAndView access(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(defaultValue = "") String code) throws Exception {

		String appId = SystemConfig.getInstance().getAppId();
		if (StringUtils.isBlank(code)) {
			String projectName = request.getContextPath();
			String redirectUri = URLEncoder
					.encode(SystemConfig.getInstance().getDomain() + projectName + "/weixin/register.do", "utf-8");
			response.sendRedirect(weixinApiBiz.getAuthUrlBySnsapiUserInfo(appId, redirectUri));
		} else {
			AccessToken accessToken = weixinApiBiz.getAccessToken(appId, code);
			if (null != accessToken) {
				LOG.debug("accessToken={}", accessToken.toString());
				OpenUserinfo openUserinfo = weixinApiBiz.getOpenUserinfo(accessToken.getAccessToken(),
						accessToken.getOpenId());
				LOG.debug("openUserinfo={}", openUserinfo.toString());
				if (null != openUserinfo) {
					SessionUtil.setRegisterWxUserInfo(openUserinfo);
					AgentInfo agentInfo = agentInfoService.selectByOpenId(openUserinfo.getOpenid());
					if (null != agentInfo) {
						request.getRequestDispatcher("/main/enter.do").forward(request, response);
					}
					return new ModelAndView("register", "userInfo", openUserinfo);
				}
			} else {
				LOG.error("获取不到用户Token.");
			}
		}
		return null;
	}
}
