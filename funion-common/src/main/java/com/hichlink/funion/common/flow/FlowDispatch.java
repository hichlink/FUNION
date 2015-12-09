package com.hichlink.funion.common.flow;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hichlink.funion.common.flow.exchange.FlowMakeOrderReqMesg;
import com.hichlink.funion.common.flow.exchange.FlowMesgBody;
import com.hichlink.funion.common.flow.exchange.FlowMesgHeader;
import com.hichlink.funion.common.flow.exchange.FlowReqMesg;
import com.hichlink.funion.common.flow.exchange.FlowRespMesg;
import com.hichlink.funion.common.util.HttpClientUtil;
import com.hichlink.funion.common.util.MD5Util;

import net.sf.json.JSONObject;

public class FlowDispatch {
	private static Logger logger = LoggerFactory.getLogger(FlowDispatch.class);
	private static FlowDispatch instance = null;

	public synchronized static FlowDispatch getInstance() {
		if (null == instance) {
			instance = new FlowDispatch();
		}
		return instance;
	}

	private FlowDispatch() {

	}

	public FossFlowMakeBack dispatchFlow(FlowMakeOrderReqMesg s, String flowRequestAppId, String flowRequestAppSecret,
			String dispatchUrl) throws Exception {
		FlowReqMesg reqMsg = new FlowReqMesg();
		FlowMesgHeader header = new FlowMesgHeader();
		FossFlowMakeBack flowMackBack = new FossFlowMakeBack();

		header.setAppId(flowRequestAppId);
		header.setSeqNo(System.currentTimeMillis() + "");
		header.setTimestamp(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		header.setVersion("v1.0");
		String sECERTKEY = MD5Util
				.MD5(header.getTimestamp() + header.getSeqNo() + flowRequestAppId + flowRequestAppSecret);
		header.setSecertKey(sECERTKEY);

		String sign = MD5Util.MD5(flowRequestAppSecret + s.getUser() + s.getPackageId() + s.getOrderType());// MD5(APPSecret+USER+PACKEID+ORDERTYPE)
		s.setSign(sign);
		ObjectMapper objectMapper = new ObjectMapper();
		FlowMesgBody msgbody = new FlowMesgBody();
		msgbody.setContent(JSONObject.fromObject(objectMapper.writeValueAsString(s)));

		reqMsg.setHeader(header);
		reqMsg.setMsgbody(msgbody);

		logger.debug("****Mobile:" + s.getUser() + ",Package:" + s.getPackageId() + "flowRequestAppId:"
				+ flowRequestAppId + ",dispatch_url:" + dispatchUrl + ":" + reqMsg);

		String respJson = HttpClientUtil.sendData(objectMapper.writeValueAsString(reqMsg), dispatchUrl);
		logger.debug("****respJson---------------------:" + respJson);
		if (StringUtils.isNotBlank(respJson)) {
			FlowRespMesg respMesg = objectMapper.readValue(respJson, FlowRespMesg.class);
			if ("00".equals(respMesg.getMsgbody().getContent().getCode())) {
				flowMackBack.setCode(respMesg.getMsgbody().getContent().getCode());
				flowMackBack.setOrderId(respMesg.getMsgbody().getContent().getOrderId().toString());
				flowMackBack.setMsg(respMesg.getMsgbody().getContent().getStatus());
				return flowMackBack;
			} else {
				logger.error("兑换失败，网关返回：" + respMesg.getMsgbody().getContent().getStatus());
				flowMackBack.setCode(respMesg.getMsgbody().getContent().getCode());
				flowMackBack.setOrderId(null);
				flowMackBack.setMsg(respMesg.getMsgbody().getContent().getStatus());
				return flowMackBack;
			}
		}
		return flowMackBack;
	}
}
