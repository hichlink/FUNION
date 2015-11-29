package com.hichlink.funion.portal.web;

import java.net.URLEncoder;
import java.util.UUID;

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
import com.hichlink.funion.common.weixin.WeixinApiBiz;
import com.hichlink.funion.common.weixin.entity.AccessToken;
import com.hichlink.funion.common.weixin.entity.OpenUserinfo;
import com.hichlink.funion.portal.common.config.SystemConfig;
import com.hichlink.funion.portal.common.util.SessionUtil;

@Controller
@RequestMapping("/register")
public class RegisterController extends BaseController {
	private static final Logger LOG = LoggerFactory.getLogger(RegisterController.class);
	@Autowired
	private WeixinApiBiz weixinApiBiz;

	@RequestMapping(value = "/enter.do")
	public ModelAndView enter(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(defaultValue = "") String code) {
		String appId = SystemConfig.getInstance().getAppId();
		if (StringUtils.isBlank(code)) {
			try {
				String redirectUri = URLEncoder.encode(
						SystemConfig.getInstance().getDomain() + request.getContextPath() + "/register/enter.do",
						"utf-8");
				response.sendRedirect(weixinApiBiz.getAuthUrlBySnsapiBase(appId, redirectUri));
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}

		} else {
			AccessToken accessToken = weixinApiBiz.getAccessToken(appId, code);
			if (null != accessToken) {
				LOG.debug("accessToken={}", accessToken.toString());
				OpenUserinfo openUserinfo = weixinApiBiz.getOpenUserinfo(accessToken.getAccessToken(),
						accessToken.getOpenId());
				if (null == openUserinfo) {
					LOG.error("获取不到微信用户信息");
					return null;
				}
				SessionUtil.setRegisterWxUserInfo(openUserinfo);
				return new ModelAndView("register", "userInfo", openUserinfo);
			} else {
				LOG.error("获取不到用户Token.");
			}
		}
		return null;
	}
	/*@RequestMapping(value = "/enter.do")
	public ModelAndView enter(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(defaultValue = "") String code) {
		OpenUserinfo openUserinfo = new OpenUserinfo();
		openUserinfo.setOpenid(UUID.randomUUID().toString().replaceAll("-", ""));
		openUserinfo.setHeadimgurl("");
		SessionUtil.setRegisterWxUserInfo(openUserinfo);
		return new ModelAndView("register", "userInfo", openUserinfo);
	}*/
}
