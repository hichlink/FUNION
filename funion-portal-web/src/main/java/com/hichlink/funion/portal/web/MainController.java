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

import com.aspire.webbas.core.pagination.mybatis.pager.Page;
import com.aspire.webbas.core.web.BaseController;
import com.hichlink.funion.common.entity.AgentInfo;
import com.hichlink.funion.common.entity.BalanceFlow;
import com.hichlink.funion.common.service.AgentInfoService;
import com.hichlink.funion.common.service.BalanceFlowService;
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
	@Autowired
	private BalanceFlowService balanceFlowService;

	@RequestMapping(value = "/balanceFlow.do")
	@ResponseBody
	public Map<String, Object> balanceFlow(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Page<BalanceFlow> params = new Page<BalanceFlow>();
		OpenUserinfo openUserinfo = SessionUtil.getRegisterWxUserInfo();
		if (null == openUserinfo) {
			return fail("请从微信客户端进入");
		}
		AgentInfo agentInfo = agentInfoService.selectByOpenId(openUserinfo.getOpenid());
		if (null == agentInfo) {
			return fail("无效的用户");
		}
		params.getParams().put("agentId", agentInfo.getAgentId());
		params.setAssembleOrderBy(" input_time desc ");
		return super.page(balanceFlowService.pageQuery(params));
	}

	@RequestMapping(value = "/enter.do")
	public void enter(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(defaultValue = "") String code) throws Exception {
		String appId = SystemConfig.getInstance().getAppId();
		OpenUserinfo openUserinfo = SessionUtil.getRegisterWxUserInfo();
		String projectName = request.getContextPath();
		if (null != openUserinfo) {
			SessionUtil.setRegisterWxUserInfo(openUserinfo);
			AgentInfo agentInfo = agentInfoService.selectByOpenId(openUserinfo.getOpenid());
			if (null != agentInfo) {
				response.sendRedirect(projectName + "/main/index.do");
			}
			response.sendRedirect(projectName + "/register/enter.do");
		}
		if (StringUtils.isBlank(code)) {
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
					response.sendRedirect(projectName + "/main/index.do");
				}
				response.sendRedirect(projectName + "/register/enter.do");
			}
		}
	}

	@RequestMapping(value = "/index.do")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		OpenUserinfo openUserinfo = SessionUtil.getRegisterWxUserInfo();
		if (null != openUserinfo) {
			SessionUtil.setRegisterWxUserInfo(openUserinfo);
			AgentInfo agentInfo = agentInfoService.selectByOpenId(openUserinfo.getOpenid());
			if (null != agentInfo) {
				return new ModelAndView("main", "agentInfo", agentInfo);
			}
			return new ModelAndView("register", "userInfo", openUserinfo);
		}
		return null;
	}
}
