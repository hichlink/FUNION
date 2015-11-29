package com.hichlink.funion.common.weixin.entity;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.hichlink.funion.common.util.Signature;

public class WxOrderInfo {
	/**
	 * 公众账号ID
	 */
	private String appId;
	/**
	 * 商户号
	 */
	private String mchId;
	/**
	 * 设备号
	 */
	private String deviceInfo;
	/**
	 * 随机字符串
	 */
	private String nonceStr;
	/**
	 * 签名
	 */
	private String sign;
	/**
	 * 商品描述
	 */
	private String body;
	/**
	 * 商品详情
	 */
	private String detail;
	/**
	 * 附加数据
	 */
	private String attach;
	/**
	 * 商户订单号
	 */
	private String outTradeNo;
	/**
	 * 货币类型
	 */
	private String feeType;
	/**
	 * 总金额
	 */
	private Integer totalFee;
	/**
	 * 终端IP
	 */
	private String spbillCreateIp;
	/**
	 * 交易起始时间
	 */
	private String timeStart;
	/**
	 * 交易结束时间
	 */
	private String timeExpire;
	/**
	 * 商品标记
	 */
	private String goodsTag;
	/**
	 * 通知地址
	 */
	private String notifyUrl;
	/**
	 * 交易类型
	 */
	private String tradeType = "JSAPI";

	/**
	 * 商品ID
	 */
	private String productId;
	/**
	 * 指定支付方式
	 */
	private String limitPay;
	/**
	 * 用户标识
	 */
	private String openId;

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

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeExpire() {
		return timeExpire;
	}

	public void setTimeExpire(String timeExpire) {
		this.timeExpire = timeExpire;
	}

	public String getGoodsTag() {
		return goodsTag;
	}

	public void setGoodsTag(String goodsTag) {
		this.goodsTag = goodsTag;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getLimitPay() {
		return limitPay;
	}

	public void setLimitPay(String limitPay) {
		this.limitPay = limitPay;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Map<String, String> getFieldMap() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("appid", this.getAppId());
		if (StringUtils.isNotBlank(this.getAttach())) {
			map.put("attach", this.getAttach());
		}

		if (StringUtils.isNotBlank(this.getDeviceInfo())) {
			map.put("device_info", this.getDeviceInfo());
		}
		map.put("body", this.getBody());
		if (StringUtils.isNotBlank(this.getDetail())) {
			map.put("detail", this.getDetail());
		}
		if (StringUtils.isNotBlank(this.getAttach())) {
			map.put("attach", this.getAttach());
		}
		map.put("mch_id", this.getMchId());
		map.put("nonce_str", this.getNonceStr());
		map.put("notify_url", this.getNotifyUrl());
		map.put("openid", this.getOpenId());
		map.put("out_trade_no", this.getOutTradeNo());
		if (StringUtils.isNotBlank(this.getFeeType())) {
			map.put("fee_type", this.getFeeType());
		}
		map.put("spbill_create_ip", this.getSpbillCreateIp());
		if (StringUtils.isNotBlank(this.getTimeStart())) {
			map.put("time_start", this.getTimeStart());
		}
		if (StringUtils.isNotBlank(this.getTimeExpire())) {
			map.put("time_expire", this.getTimeExpire());
		}
		if (StringUtils.isNotBlank(this.getGoodsTag())) {
			map.put("goods_tag", this.getGoodsTag());
		}
		map.put("total_fee", "" + this.getTotalFee());
		map.put("trade_type", this.getTradeType());
		if (StringUtils.isNotBlank(this.getProductId())) {
			map.put("product_id", this.getProductId());
		}
		if (StringUtils.isNotBlank(this.getLimitPay())) {
			map.put("limit_pay", this.getLimitPay());
		}
		if (StringUtils.isNotBlank(this.getOpenId())) {
			map.put("openid", this.getOpenId());
		}
		return map;
	}

	public String toPayXml(String key) {
		Map<String, String> fileds = getFieldMap();
		String sign = Signature.getSign(fileds, key);
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for (String filed : fileds.keySet()) {
			sb.append("<" + filed + "><![CDATA[" + fileds.get(filed) + "]]></" + filed + ">");
		}
		sb.append("<sign><![CDATA[" + sign + "]]></sign>");
		sb.append("</xml>");
		return sb.toString();
	}
}
