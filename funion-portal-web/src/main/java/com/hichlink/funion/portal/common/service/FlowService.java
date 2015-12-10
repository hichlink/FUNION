package com.hichlink.funion.portal.common.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.aspire.webbas.core.exception.MyException;
import com.hichlink.funion.common.entity.FlowExchangeLog;
import com.hichlink.funion.common.entity.FlowPayRecord;
import com.hichlink.funion.common.entity.FlowProductInfo;
import com.hichlink.funion.common.entity.WxPayRecord;
import com.hichlink.funion.common.flow.entity.FlowNotifyReq;
import com.hichlink.funion.common.flow.entity.FlowNotifyResp;
import com.hichlink.funion.common.flow.exchange.FlowRespMesg;
import com.hichlink.funion.common.service.FlowExchangeLogService;
import com.hichlink.funion.common.service.FlowPayRecordService;
import com.hichlink.funion.common.service.FlowProductInfoService;
import com.hichlink.funion.common.service.WxPayRecordService;
import com.hichlink.funion.common.util.CommonUtil;
import com.hichlink.funion.common.weixin.WeixinPayBiz;
import com.hichlink.funion.common.weixin.entity.WxOrderInfo;
import com.hichlink.funion.common.weixin.entity.WxOrderInfoResp;
import com.hichlink.funion.portal.common.config.SystemConfig;
import com.hichlink.funion.portal.common.util.CheckPhone;
import com.hichlink.funion.portal.common.util.SessionUtil;

@Service("flowService")
public class FlowService {
	private static final Logger LOG = LoggerFactory.getLogger(FlowService.class);
	@Autowired
	private FlowProductInfoService flowProductInfoService;
	@Autowired
	private WeixinPayBiz weixinPayBiz;
	@Autowired
	private WxPayRecordService wxPayRecordService;
	@Autowired
	private FlowPayRecordService flowPayRecordService;
	@Autowired
	private FlowExchangeLogService flowExchangeLogService;
	public List<FlowProductInfo> getProductByMobile(String mobile) {
		String operatorCode = CheckPhone.getMobileOpr(mobile);
		if (StringUtils.isBlank(operatorCode)) {// 找不到对应运营商，返回空
			return new ArrayList<FlowProductInfo>();
		}
		return flowProductInfoService.findByOperatorCode(operatorCode);
	}

	@Transactional
	public String initPayRecord(HttpServletRequest request, HttpServletResponse response, String uuid, String mobile,
			Long productId) {
		FlowProductInfo flowProductInfo = flowProductInfoService.get(productId);
		if (null == flowProductInfo) {
			LOG.error("根据productId={}查找不到对应的流量包信息", productId);
			throw new MyException("查找不到对应的流量包信息");
		}
		if (StringUtils.isBlank(mobile) || !CheckPhone.isMobileNO(mobile)) {
			LOG.error("mobile={}无效", mobile);
			throw new MyException("手机号码无效!");
		}
		String appId = SystemConfig.getInstance().getAppId();
		String openId = SessionUtil.getPayOpenId();
		String ip = CommonUtil.getIp(request);
		WxOrderInfo wxOrderInfo = new WxOrderInfo();
		wxOrderInfo.setAppId(appId);
		wxOrderInfo.setBody(flowProductInfo.getProductName());
		wxOrderInfo.setDetail("");
		String outTradeNo = "HK_" + flowProductInfo.getProductId() + "_" + System.currentTimeMillis()
				+ (new Random().nextInt(90000) + 10000);
		wxOrderInfo.setOutTradeNo(outTradeNo);
		wxOrderInfo.setNonceStr(UUID.randomUUID().toString().replaceAll("-", ""));
		wxOrderInfo.setNotifyUrl(SystemConfig.getInstance().getDomain() + request.getContextPath() + "/flow/notify.do");
		wxOrderInfo.setOpenId(openId);
		BigDecimal price = flowProductInfo.getSettlementPrice();
		wxOrderInfo.setTotalFee(price.multiply(new BigDecimal(1).multiply(new BigDecimal(100))).intValue());
		wxOrderInfo.setTradeType("JSAPI");
		wxOrderInfo.setAttach("");
		wxOrderInfo.setSpbillCreateIp("192.168.6.20");
		WxOrderInfoResp resp = weixinPayBiz.sendOrder(wxOrderInfo);
		System.out.println(resp.toString());
		if (!resp.isSuccess()) {
			LOG.error("下单失败,原因:{}", resp.getReturnMsg());
			throw new MyException("下单失败,原因:" + resp.getReturnMsg());
		}
		FlowPayRecord flowPayRecord = new FlowPayRecord();
		flowPayRecord.setCostPrice(flowProductInfo.getCostPrice());
		flowPayRecord.setHeadImg("");
		flowPayRecord.setIdentityId(uuid);
		flowPayRecord.setInputTime(new Date());
		flowPayRecord.setIsBalance(FlowPayRecord.IS_BALANCE_NO);
		flowPayRecord.setMobile(mobile);
		flowPayRecord.setNickName("");
		flowPayRecord.setNum(1);
		flowPayRecord.setSendStatus(FlowPayRecord.SEND_STATUS_INIT);
		flowPayRecord.setOpenId(openId);
		flowPayRecord.setOperatorPrice(flowProductInfo.getOperatorPrice());
		flowPayRecord.setOutTradeNo(outTradeNo);
		flowPayRecord.setPayStatus(WxPayRecord.PAY_STATUS_INIT);
		flowPayRecord.setPayType(FlowPayRecord.PAY_TYPE_WX);
		flowPayRecord.setProductId(productId);
		flowPayRecord.setSettlementPrice(price);
		flowPayRecord.setUnionId("");
		flowPayRecordService.saveAndUpdate(flowPayRecord);
		WxPayRecord wxPayRecord = new WxPayRecord();
		wxPayRecord.setDeviceInfo("");
		wxPayRecord.setNickName("");
		wxPayRecord.setNum(1);
		wxPayRecord.setOpenId(openId);
		wxPayRecord.setOutTradeNo(outTradeNo);
		wxPayRecord.setPayIp(ip);
		wxPayRecord.setPayStatus(WxPayRecord.PAY_STATUS_INIT);
		wxPayRecord.setPayTime(new Date());
		wxPayRecord.setPayType(0);// 0:流量包
		wxPayRecord.setPrice(price);
		wxPayRecord.setTotalFee(price);
		wxPayRecord.setTradeType("JSAPI");
		wxPayRecord.setTransactionId("");
		wxPayRecord.setRecordId(flowPayRecord.getRecordId());
		wxPayRecordService.saveAndUpdate(wxPayRecord);

		return resp.getPrepayId();
	}
	public FlowNotifyResp exchangeCallback(String body){
		if (StringUtils.isBlank(body)){
			throw new MyException("内容为空");
		}
		FlowNotifyReq req = JSONObject.parseObject(body, FlowNotifyReq.class);
		String extOrderId = req.getMsgBody().getContent().getExtOrder();
		if (StringUtils.isBlank(extOrderId)){
			throw new MyException("extOrder为空");
		}
		FlowExchangeLog flowExchangeLog = flowExchangeLogService.getByFlowVoucherId(extOrderId);
		if (null == flowExchangeLog){
			throw new MyException("根据extOrder=" + extOrderId + "查找不到记录.");
		}
		FlowPayRecord flowPayRecord = flowPayRecordService.get(flowExchangeLog.getRecordId());
		if (null != req && req.isSucc()){
			flowExchangeLog.setFlag(FlowPayRecord.SEND_STATUS_OK);
			flowPayRecord.setSendStatus(FlowPayRecord.SEND_STATUS_OK);
		}else{
			flowExchangeLog.setFlag(FlowPayRecord.SEND_STATUS_FAIL);
			flowPayRecord.setSendStatus(FlowPayRecord.SEND_STATUS_FAIL);
		}
		flowPayRecord.setCheckTime(new Date());
		flowExchangeLog.setRemark(req.getMsgBody().getContent().getStatus());
		flowExchangeLogService.update(flowExchangeLog);
		flowPayRecordService.update(flowPayRecord);
		FlowNotifyResp flowNotifyResp = new FlowNotifyResp();
		FlowNotifyResp.MsgBody msgBody = flowNotifyResp.new MsgBody();
		FlowNotifyResp.Resp resp = flowNotifyResp.new Resp();
		resp.setCode(FlowNotifyReq.SUCC);
		msgBody.setResp(resp);
		flowNotifyResp.setFlowHeader(req.getFlowHeader());
		flowNotifyResp.setMsgBody(msgBody);
		return flowNotifyResp;
	}
}
