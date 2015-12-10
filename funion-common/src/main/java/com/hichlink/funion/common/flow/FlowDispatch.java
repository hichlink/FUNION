package com.hichlink.funion.common.flow;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hichlink.funion.common.flow.entity.FlowHeader;
import com.hichlink.funion.common.flow.entity.FlowOrderReq;
import com.hichlink.funion.common.flow.entity.FlowOrderResp;
import com.hichlink.funion.common.util.HttpClientUtil;
import com.hichlink.funion.common.util.MD5Util;

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

	public FlowOrderResp dispatchFlow(FlowOrderReq.Content s, String flowRequestAppId, String flowRequestAppSecret,
			String dispatchUrl) throws Exception {
		FlowOrderReq flowOrderReq = new FlowOrderReq();
		
		FlowHeader header = new FlowHeader();

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
		FlowOrderReq.FlowOrderMsgBody flowOrderMsgBody = flowOrderReq.new FlowOrderMsgBody();
		s.setSign(sign);
		flowOrderMsgBody.setContent(s);
		flowOrderReq.setFlowOrderMsgBody(flowOrderMsgBody);
		flowOrderReq.setFlowHeader(header);
		
		flowOrderReq.setFlowOrderMsgBody(flowOrderMsgBody);

		logger.debug("****Mobile:" + s.getUser() + ",Package:" + s.getPackageId() + "flowRequestAppId:"
				+ flowRequestAppId + ",dispatch_url:" + dispatchUrl + ":" + flowOrderReq);

		String respJson = HttpClientUtil.sendData(objectMapper.writeValueAsString(flowOrderReq), dispatchUrl);
		logger.debug("****respJson---------------------:" + respJson);
		if (StringUtils.isNotBlank(respJson)) {
			FlowOrderResp flowOrderResp = objectMapper.readValue(respJson, FlowOrderResp.class);
			return flowOrderResp;
		}
		return null;
	}
}
