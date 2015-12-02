package com.hichlink.funion.portal.web;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.webbas.core.web.BaseController;
import com.hichlink.funion.common.entity.AgentInfo;
import com.hichlink.funion.common.service.AgentInfoService;
import com.hichlink.funion.common.util.BaseUtil;
import com.hichlink.funion.common.weixin.entity.OpenUserinfo;
import com.hichlink.funion.portal.common.config.SystemConfig;
import com.hichlink.funion.portal.common.util.SessionUtil;

@Controller
@RequestMapping("/agentInfo")
public class AgentInfoController extends BaseController {
	@Autowired
	private AgentInfoService agentInfoService;

	@RequestMapping(value = "/register.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> register(HttpServletRequest request, HttpServletResponse response, AgentInfo agentInfo) {
		OpenUserinfo openUserinfo = SessionUtil.getRegisterWxUserInfo();
		if (null == openUserinfo) {
			return super.fail("请在微信客户端打开");
		}
		AgentInfo data = new AgentInfo();
		data.setAgentName(agentInfo.getRealName());
		data.setRealName(agentInfo.getRealName());
		data.setCardNo(agentInfo.getCardNo());
		data.setEmail(agentInfo.getEmail());
		data.setMobile(agentInfo.getMobile());
		data.setOpenId(openUserinfo.getOpenid());
		data.setNickName(BaseUtil.getBase64(openUserinfo.getNickname()));
		data.setUnionId(openUserinfo.getUnionid());
		data.setHeadImg(openUserinfo.getHeadimgurl());
		data.setCommisionRatio(SystemConfig.getInstance().getCommisionRatio());
		data.setIdentityId(UUID.randomUUID().toString().replaceAll("-", ""));
		data.setInputTime(new Date());
		data.setInputBy(openUserinfo.getOpenid());
		agentInfoService.saveAndUpdate(data);
		return super.success("注册成功");
	}
}
