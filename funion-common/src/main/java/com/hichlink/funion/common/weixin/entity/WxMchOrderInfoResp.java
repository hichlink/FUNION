package com.hichlink.funion.common.weixin.entity;

import org.apache.commons.lang3.StringUtils;

import com.funion.foss.common.util.XStreamHandle;
import com.thoughtworks.xstream.annotations.XStreamAlias;

public class WxMchOrderInfoResp extends WxBaseResp {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7670980683491484206L;
	@XStreamAlias("mch_appid")
	private String mchAppid;
	@XStreamAlias("mchid")
	private String mchId;
	@XStreamAlias("device_info")
	private String deviceInfo;
	@XStreamAlias("nonce_str")
	private String nonceStr;
	@XStreamAlias("partner_trade_no")
	private String partnerTradeNo;
	@XStreamAlias("payment_no")
	private String paymentNo;
	@XStreamAlias("payment_time")
	private String paymentTime;
	@XStreamAlias("err_code")
	private String errCode;
	@XStreamAlias("err_code_des")
	private String errCodeDes;

	public String getMchAppid() {
		return mchAppid;
	}

	public void setMchAppid(String mchAppid) {
		this.mchAppid = mchAppid;
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

	public String getPartnerTradeNo() {
		return partnerTradeNo;
	}

	public void setPartnerTradeNo(String partnerTradeNo) {
		this.partnerTradeNo = partnerTradeNo;
	}

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public String getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrCodeDes() {
		return errCodeDes;
	}

	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}

	public WxMchOrderInfoResp(String xml) {
		parseXml(xml);
	}

	public void parseXml(String xml) {
		if (StringUtils.isNotBlank(xml)) {
			XStreamHandle.toBean(xml, this.getClass());
		}
	}

}
