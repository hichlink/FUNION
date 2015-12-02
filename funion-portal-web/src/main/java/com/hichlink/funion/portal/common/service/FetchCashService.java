package com.hichlink.funion.portal.common.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aspire.webbas.core.exception.MyException;
import com.hichlink.funion.common.entity.AgentInfo;
import com.hichlink.funion.common.entity.FetchCashFlow;
import com.hichlink.funion.common.service.AgentInfoService;
import com.hichlink.funion.common.service.FetchCashFlowService;
import com.hichlink.funion.common.util.OrderSeqGen;
import com.hichlink.funion.common.weixin.WeixinMchPayBiz;
import com.hichlink.funion.common.weixin.entity.OpenUserinfo;
import com.hichlink.funion.common.weixin.entity.WxMchOrderInfo;
import com.hichlink.funion.common.weixin.entity.WxMchOrderInfoResp;
import com.hichlink.funion.portal.common.config.SystemConfig;
import com.hichlink.funion.portal.common.util.SessionUtil;

@Service("fetchCashService")
public class FetchCashService {
	private static final Logger LOG = LoggerFactory.getLogger(FetchCashService.class);
	@Autowired
	private AgentInfoService agentInfoService;
	@Autowired
	private FetchCashFlowService fetchCashFlowService;
	@Autowired
	private WeixinMchPayBiz weixinMchPayBiz;
	private final static Byte[] synFlag = new Byte[0];

	@Transactional
	public void withdrawCash(int cash) {
		OpenUserinfo openUserinfo = SessionUtil.getRegisterWxUserInfo();
		if (null == openUserinfo) {
			throw new MyException("请在微信客户端打开");
		}
		if (cash > SystemConfig.getInstance().getCashMax()) {
			throw new MyException("提取金额超过最大限额");
		}
		synchronized (synFlag) {

			AgentInfo agentInfo = agentInfoService.selectByOpenId(openUserinfo.getOpenid());
			if (null == agentInfo) {
				throw new MyException("代理信息不存在!");
			}
			if (agentInfo.getBalance().multiply(new BigDecimal(100))
					.compareTo(new BigDecimal(SystemConfig.getInstance().getCashMin())) < 0) {
				throw new MyException("当前佣金总数不满足提现");
			}

			String appId = SystemConfig.getInstance().getAppId();
			WxMchOrderInfo wxMchOrderInfo = new WxMchOrderInfo();
			wxMchOrderInfo.setAmount(cash);
			wxMchOrderInfo.setCheckName("");
			wxMchOrderInfo.setDesc("test");
			wxMchOrderInfo.setMchAppid(appId);
			wxMchOrderInfo.setOpenId(openUserinfo.getOpenid());
			String orderId = OrderSeqGen.createApplyId();
			wxMchOrderInfo.setPartneTradeNo(orderId);
			wxMchOrderInfo.setReUserName("");
			wxMchOrderInfo.setCheckName("NO_CHECK");
			wxMchOrderInfo.setNonceStr(UUID.randomUUID().toString().replaceAll("-", ""));
			wxMchOrderInfo.setSpbillCreateIp("127.0.0.1");
			String certPath = SystemConfig.getInstance().getCertPath();
			WxMchOrderInfoResp resp = weixinMchPayBiz.sendOrder(wxMchOrderInfo,certPath);
			LOG.debug("resp={}", resp.toString());
			if (resp.isSuccess()) {
				FetchCashFlow fetchCashFlow = new FetchCashFlow();
				fetchCashFlow.setAgentId(agentInfo.getAgentId());
				fetchCashFlow.setCashAmount(new BigDecimal(cash).divide(new BigDecimal(100)));
				fetchCashFlow.setInputTime(new Date());
				fetchCashFlow.setType(1);
				fetchCashFlow.setRemark("佣金提现,cash=" + new BigDecimal(cash).divide(new BigDecimal(100)) + "分");
				fetchCashFlowService.insert(fetchCashFlow);
				agentInfoService.updateBalance(agentInfo.getAgentId(),
						new BigDecimal(0).subtract(new BigDecimal(cash).divide(new BigDecimal(100))));
			} else {
				throw new MyException(resp.getReturnMsg());
			}
		}
	}
}
