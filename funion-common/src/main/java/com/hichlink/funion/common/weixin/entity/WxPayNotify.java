package com.hichlink.funion.common.weixin.entity;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.hichlink.funion.common.util.Signature;
import com.hichlink.funion.common.util.XStreamHandle;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class WxPayNotify extends WxBaseResp {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3824714284771733380L;
	/**
	 * 公众账号ID
	 */
	@XStreamAlias("appid")
	private String appId;
	/**
	 * 商家数据包
	 */
	@XStreamAlias("attach")
	private String attach;
	@XStreamAlias("device_info")
	private String deviceInfo;
	/**
	 * 付款银行
	 */
	@XStreamAlias("bank_type")
	private String bankType;
	/**
	 * 货币种类
	 */
	@XStreamAlias("fee_type")
	private String feeType;
	/**
	 * 是否关注公众账号
	 */
	@XStreamAlias("is_subscribe")
	private String iSubscribe;
	/**
	 * 商户号
	 */
	@XStreamAlias("mch_id")
	private String mchId;
	/**
	 * 随机字符串
	 */
	@XStreamAlias("nonce_str")
	private String nonceStr;
	/**
	 * 用户标识
	 */
	@XStreamAlias("openid")
	private String openId;
	/**
	 * 商户订单号
	 */
	@XStreamAlias("out_trade_no")
	private String outTradeNo;
	/**
	 * 签名
	 */
	@XStreamAlias("sign")
	private String sign;
	/**
	 * 
	 */
	@XStreamAlias("sub_mch_id")
	private String subMchId;
	/**
	 * 支付完成时间
	 */
	@XStreamAlias("time_end")
	private String timeEnd;
	/**
	 * 总金额
	 */
	@XStreamAlias("total_fee")
	private String totalFee;
	/**
	 * 交易类型
	 */
	@XStreamAlias("trade_type")
	private String tradeType;
	/**
	 * 微信支付订单号
	 */
	@XStreamAlias("transaction_id")
	private String transactionId;

	@XStreamAlias("cash_fee_type")
	private String cashFeeType;

	public WxPayNotify(String xml) {
		parseXml(xml);
	}

	public void parseXml(String xml) {
		if (StringUtils.isNotBlank(xml)) {
			XStreamHandle.toBean(xml, this.getClass());
		}
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getiSubscribe() {
		return iSubscribe;
	}

	public void setiSubscribe(String iSubscribe) {
		this.iSubscribe = iSubscribe;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getSubMchId() {
		return subMchId;
	}

	public void setSubMchId(String subMchId) {
		this.subMchId = subMchId;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getCashFeeType() {
		return cashFeeType;
	}

	public void setCashFeeType(String cashFeeType) {
		this.cashFeeType = cashFeeType;
	}

	public boolean validateSign(String apiKey) {
		Map<String, String> map = new TreeMap<String, String>();
		if (StringUtils.isNotBlank(getAppId())) {
			map.put("appid", getAppId());
		}
		if (StringUtils.isNotBlank(getAttach())) {
			map.put("attach", getAttach());
		}
		if (StringUtils.isNotBlank(getDeviceInfo())) {
			map.put("device_info", getDeviceInfo());
		}
		if (StringUtils.isNotBlank(getBankType())) {
			map.put("bank_type", getBankType());
		}
		if (StringUtils.isNotBlank(getFeeType())) {
			map.put("fee_type", getFeeType());
		}
		if (StringUtils.isNotBlank(getiSubscribe())) {
			map.put("is_subscribe", getiSubscribe());
		}
		if (StringUtils.isNotBlank(getMchId())) {
			map.put("mch_id", getMchId());
		}
		if (StringUtils.isNotBlank(getNonceStr())) {
			map.put("nonce_str", getNonceStr());
		}
		if (StringUtils.isNotBlank(getOpenId())) {
			map.put("openid", getOpenId());
		}
		if (StringUtils.isNotBlank(getOutTradeNo())) {
			map.put("out_trade_no", getOutTradeNo());
		}
		if (StringUtils.isNotBlank(getResultCode())) {
			map.put("result_code", getResultCode());
		}
		if (StringUtils.isNotBlank(getReturnCode())) {
			map.put("return_code", getReturnCode());
		}
		if (StringUtils.isNotBlank(getSubMchId())) {
			map.put("sub_mch_id", getSubMchId());
		}
		if (StringUtils.isNotBlank(getTimeEnd())) {
			map.put("time_end", getTimeEnd());
		}
		if (StringUtils.isNotBlank(getTotalFee())) {
			map.put("total_fee", getTotalFee());
		}

		if (StringUtils.isNotBlank(getTradeType())) {
			map.put("trade_type", getTradeType());
		}
		if (StringUtils.isNotBlank(getTransactionId())) {
			map.put("transaction_id", getTransactionId());
		}
		if (StringUtils.isNotBlank(getCashFeeType())) {
			map.put("cash_fee_type", getCashFeeType());
		}
		if (StringUtils.isBlank(getSign())) {
			return false;
		}
		String sign = Signature.getSign(map, apiKey);
		return sign.equals(getSign());
	}
}
