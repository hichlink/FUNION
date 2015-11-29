package com.hichlink.funion.portal.web;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.aspire.webbas.core.web.BaseController;
import com.hichlink.funion.common.entity.AgentInfo;
import com.hichlink.funion.common.service.AgentInfoService;
import com.hichlink.funion.common.weixin.WeixinApiBiz;
import com.hichlink.funion.common.weixin.entity.AccessToken;
import com.hichlink.funion.common.weixin.entity.OpenUserinfo;
import com.hichlink.funion.portal.common.config.SystemConfig;
import com.hichlink.funion.portal.common.util.SessionUtil;

@Controller
@RequestMapping("/main")
public class MainController extends BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(MainController.class);
	@Autowired
	private AgentInfoService agentInfoService;
	@Autowired
	private WeixinApiBiz weixinApiBiz;

	@RequestMapping(value = "/enter.do")
	public ModelAndView enter(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(defaultValue = "") String code) throws Exception {
		String appId = SystemConfig.getInstance().getAppId();
		OpenUserinfo openUserinfo = SessionUtil.getRegisterWxUserInfo();
		if (null != openUserinfo) {
			SessionUtil.setRegisterWxUserInfo(openUserinfo);
			AgentInfo agentInfo = agentInfoService.selectByOpenId(openUserinfo.getOpenid());
			if (null != agentInfo) {
				return new ModelAndView("main", "agentInfo", agentInfo);
			}
			return new ModelAndView("register", "userInfo", openUserinfo);
		}
		if (StringUtils.isBlank(code)) {
			String projectName = request.getContextPath();
			String redirectUri = URLEncoder
					.encode(SystemConfig.getInstance().getDomain() + projectName + "/main/enter.do", "utf-8");

			response.sendRedirect(weixinApiBiz.getAuthUrlBySnsapiUserInfo(appId, redirectUri));

		}
		AccessToken accessToken = weixinApiBiz.getAccessToken(appId, code);
		if (null != accessToken) {
			LOG.debug("accessToken={}", accessToken.toString());
			openUserinfo = weixinApiBiz.getOpenUserinfo(accessToken.getAccessToken(), accessToken.getOpenId());
			LOG.debug("openUserinfo={}", openUserinfo.toString());
			if (null != openUserinfo) {
				SessionUtil.setRegisterWxUserInfo(openUserinfo);
				AgentInfo agentInfo = agentInfoService.selectByOpenId(openUserinfo.getOpenid());
				if (null != agentInfo) {
					return new ModelAndView("main", "agentInfo", agentInfo);
				}
				return new ModelAndView("register", "userInfo", openUserinfo);
			}
		} else {
			LOG.error("获取不到用户Token.");
		}
		return null;
	}
}
