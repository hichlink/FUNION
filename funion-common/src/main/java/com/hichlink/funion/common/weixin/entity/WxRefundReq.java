package com.hichlink.funion.common.weixin.entity;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

public class WxRefundReq extends WxBaseReq {
	private String appId;
	private String mchId;
	private String deviceInfo;
	private String nonceStr;
	private String outTradeNo;
	private String outRefundNo;
	private int totalFee;
	private int refundFee;
	private String refundFeeType;
	private String opUserId;
	private String transactionId;
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getOutRefundNo() {
		return outRefundNo;
	}

	public void setOutRefundNo(String outRefundNo) {
		this.outRefundNo = outRefundNo;
	}

	public int getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(int totalFee) {
		this.totalFee = totalFee;
	}

	public int getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(int refundFee) {
		this.refundFee = refundFee;
	}

	public String getRefundFeeType() {
		return refundFeeType;
	}

	public void setRefundFeeType(String refundFeeType) {
		this.refundFeeType = refundFeeType;
	}

	public String getOpUserId() {
		return opUserId;
	}

	public void setOpUserId(String opUserId) {
		this.opUserId = opUserId;
	}
	
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Map<String, String> getFieldMap() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("appid", this.getAppId());
		map.put("mch_id", this.getMchId());
		map.put("nonce_str", this.getNonceStr());
		map.put("op_user_id", this.getOpUserId());
		map.put("out_refund_no", this.getOutRefundNo());
		map.put("refund_fee", "" + this.getRefundFee());
		map.put("total_fee", "" + this.getTotalFee());
		map.put("out_trade_no", this.getOutTradeNo());
		if (StringUtils.isNotBlank(this.getTransactionId())){
			map.put("transaction_id", this.getTransactionId());
		}
		if (StringUtils.isNotBlank(this.getDeviceInfo())) {
			map.put("device_info", this.getDeviceInfo());
		}
		if (StringUtils.isNotBlank(this.getRefundFeeType())) {
			map.put("refund_fee_type", this.getRefundFeeType());
		}
		return map;
	}
}
