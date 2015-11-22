package com.hichlink.funion.common.weixin.entity;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.hichlink.funion.common.util.Signature;

public class WxMchOrderInfo {
	/**
	 * 微信分配的公众账号ID（企业号corpid即为此appId）
	 */
	private String mchAppid;
	/**
	 * 微信支付分配的商户号
	 */
	private String mchid;
	/**
	 * 微信支付分配的终端设备号
	 */
	private String deviceInfo;
	/**
	 * 随机字符串，不长于32位
	 */
	private String nonceStr;
	/**
	 * 商户订单号，需保持唯一性
	 */
	private String partneTradeNo;
	/**
	 * 商户appid下，某用户的openid
	 */
	private String openId;
	/**
	 * NO_CHECK：不校验真实姓名 FORCE_CHECK：强校验真实姓名（未实名认证的用户会校验失败，无法转账）
	 * OPTION_CHECK：针对已实名认证的用户才校验真实姓名（未实名认证用户不校验，可以转账成功）
	 */
	private String checkName;
	/**
	 * 收款用户真实姓名。 如果check_name设置为FORCE_CHECK或OPTION_CHECK，则必填用户真实姓名
	 */
	private String reUserName;
	/**
	 * 企业付款金额，单位为分
	 */
	private String amount;
	/**
	 * 企业付款操作说明信息。必填。
	 */
	private String desc;
	/**
	 * 调用接口的机器Ip地址
	 */
	private String spbillCreateIp;

	public String getMchAppid() {
		return mchAppid;
	}

	public void setMchAppid(String mchAppid) {
		this.mchAppid = mchAppid;
	}

	public String getMchid() {
		return mchid;
	}

	public void setMchid(String mchid) {
		this.mchid = mchid;
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

	public String getPartneTradeNo() {
		return partneTradeNo;
	}

	public void setPartneTradeNo(String partneTradeNo) {
		this.partneTradeNo = partneTradeNo;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getCheckName() {
		return checkName;
	}

	public void setCheckName(String checkName) {
		this.checkName = checkName;
	}

	public String getReUserName() {
		return reUserName;
	}

	public void setReUserName(String reUserName) {
		this.reUserName = reUserName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getSpbillCreateIp() {
		return spbillCreateIp;
	}

	public void setSpbillCreateIp(String spbillCreateIp) {
		this.spbillCreateIp = spbillCreateIp;
	}

	public Map<String, String> getFieldMap() {
		Map<String, String> map = new TreeMap<String, String>();
		if (StringUtils.isNotBlank(this.getMchAppid())) {
			map.put("mch_appid", this.getMchAppid());
		}
		if (StringUtils.isNotBlank(this.getMchid())) {
			map.put("mchid", this.getMchid());
		}
		if (StringUtils.isNotBlank(this.getDeviceInfo())) {
			map.put("device_info", this.getDeviceInfo());
		}
		if (StringUtils.isNotBlank(this.getNonceStr())) {
			map.put("nonce_str", this.getNonceStr());
		}
		if (StringUtils.isNotBlank(this.getPartneTradeNo())) {
			map.put("partner_trade_no", this.getPartneTradeNo());
		}
		if (StringUtils.isNotBlank(this.getOpenId())) {
			map.put("openid", this.getOpenId());
		}
		if (StringUtils.isNotBlank(this.getCheckName())) {
			map.put("check_name", this.getCheckName());
		}
		if (StringUtils.isNotBlank(this.getReUserName())) {
			map.put("re_user_name", this.getReUserName());
		}
		if (StringUtils.isNotBlank(this.getAmount())) {
			map.put("amount", this.getAmount());
		}
		if (StringUtils.isNotBlank(this.getDesc())) {
			map.put("desc", this.getDesc());
		}
		if (StringUtils.isNotBlank(this.getSpbillCreateIp())) {
			map.put("spbill_create_ip", this.getSpbillCreateIp());
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
