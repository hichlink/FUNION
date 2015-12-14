package com.hichlink.funion.common.weixin.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class WxRefundResp extends WxBaseResp {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3270429403294923278L;
	@XStreamAlias("err_code")
	private String errCode;
	@XStreamAlias("err_code_des")
	private String errCodeDes;
	@XStreamAlias("appid")
	private String appId;
	@XStreamAlias("mch_id")
	private String mchId;
	@XStreamAlias("device_info")
	private String deviceInfo;
	@XStreamAlias("nonce_str")
	private String nonceStr;
	@XStreamAlias("sign")
	private String sign;
	@XStreamAlias("transaction_id")
	private String transactionId;
	@XStreamAlias("out_trade_no")
	private String outTradeNo;
	@XStreamAlias("out_refund_no")
	private String outRefundNo;
	@XStreamAlias("refund_id")
	private String refundId;
	@XStreamAlias("refund_channel")
	private String refundChannel;
	@XStreamAlias("refund_fee")
	private Integer refundFee;
	@XStreamAlias("total_fee")
	private Integer totalFee;
	@XStreamAlias("fee_type")
	private String feeType;
	@XStreamAlias("cash_fee")
	private Integer cashFee;
	@XStreamAlias("cash_refund_fee")
	private Integer cashRefundFee;
	@XStreamAlias("coupon_refund_fee")
	private Integer couponRefundFee;
	@XStreamAlias("coupon_refund_count")
	private Integer couponRefundCount;
	@XStreamAlias("coupon_refund_id")
	private String couponRefundId;
	
	@Override
	public String toString() {
		return "WxRefundResp [errCode=" + errCode + ", errCodeDes=" + errCodeDes + ", appId=" + appId + ", mchId="
				+ mchId + ", deviceInfo=" + deviceInfo + ", nonceStr=" + nonceStr + ", sign=" + sign
				+ ", transactionId=" + transactionId + ", outTradeNo=" + outTradeNo + ", outRefundNo=" + outRefundNo
				+ ", refundId=" + refundId + ", refundChannel=" + refundChannel + ", refundFee=" + refundFee
				+ ", totalFee=" + totalFee + ", feeType=" + feeType + ", cashFee=" + cashFee + ", cashRefundFee="
				+ cashRefundFee + ", couponRefundFee=" + couponRefundFee + ", couponRefundCount=" + couponRefundCount
				+ ", couponRefundId=" + couponRefundId + ", getErrCode()=" + getErrCode() + ", getErrCodeDes()="
				+ getErrCodeDes() + ", getAppId()=" + getAppId() + ", getMchId()=" + getMchId() + ", getDeviceInfo()="
				+ getDeviceInfo() + ", getNonceStr()=" + getNonceStr() + ", getSign()=" + getSign()
				+ ", getTransactionId()=" + getTransactionId() + ", getOutTradeNo()=" + getOutTradeNo()
				+ ", getOutRefundNo()=" + getOutRefundNo() + ", getRefundId()=" + getRefundId()
				+ ", getRefundChannel()=" + getRefundChannel() + ", getRefundFee()=" + getRefundFee()
				+ ", getTotalFee()=" + getTotalFee() + ", getFeeType()=" + getFeeType() + ", getCashFee()="
				+ getCashFee() + ", getCashRefundFee()=" + getCashRefundFee() + ", getCouponRefundFee()="
				+ getCouponRefundFee() + ", getCouponRefundCount()=" + getCouponRefundCount() + ", getCouponRefundId()="
				+ getCouponRefundId() + ", getReturnCode()=" + getReturnCode() + ", getReturnMsg()=" + getReturnMsg()
				+ ", getResultCode()=" + getResultCode() + ", isResultSuccess()=" + isResultSuccess()
				+ ", isReturnSuccess()=" + isReturnSuccess() + ", isSuccess()=" + isSuccess() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
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

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
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

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public String getRefundChannel() {
		return refundChannel;
	}

	public void setRefundChannel(String refundChannel) {
		this.refundChannel = refundChannel;
	}

	public Integer getRefundFee() {
		return refundFee;
	}

	public void setRefundFee(Integer refundFee) {
		this.refundFee = refundFee;
	}

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public Integer getCashFee() {
		return cashFee;
	}

	public void setCashFee(Integer cashFee) {
		this.cashFee = cashFee;
	}

	public Integer getCashRefundFee() {
		return cashRefundFee;
	}

	public void setCashRefundFee(Integer cashRefundFee) {
		this.cashRefundFee = cashRefundFee;
	}

	public Integer getCouponRefundFee() {
		return couponRefundFee;
	}

	public void setCouponRefundFee(Integer couponRefundFee) {
		this.couponRefundFee = couponRefundFee;
	}

	public Integer getCouponRefundCount() {
		return couponRefundCount;
	}

	public void setCouponRefundCount(Integer couponRefundCount) {
		this.couponRefundCount = couponRefundCount;
	}

	public String getCouponRefundId() {
		return couponRefundId;
	}

	public void setCouponRefundId(String couponRefundId) {
		this.couponRefundId = couponRefundId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
