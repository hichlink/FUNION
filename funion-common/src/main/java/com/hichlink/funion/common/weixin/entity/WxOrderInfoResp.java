package com.hichlink.funion.common.weixin.entity;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.hichlink.funion.common.util.Signature;
import com.hichlink.funion.common.util.XStreamHandle;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class WxOrderInfoResp extends WxBaseResp{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7120896617035286310L;
	@XStreamAlias("appid")
	private String appId;
	@XStreamAlias("mch_id")
	private String mchId;
	@XStreamAlias("nonce_str")
	private String nonceStr;
	@XStreamAlias("sign")
	private String sign;
	
	@XStreamAlias("prepay_id")
	private String prepayId;
	@XStreamAlias("trade_type")
	private String tradeType;

	public WxOrderInfoResp(String xml) {
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

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}


	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public boolean validateSign(String apiKey) {
		Map<String, String> map = new TreeMap<String, String>();
		if (StringUtils.isNotBlank(getAppId())) {
			map.put("appid", getAppId());
		}
		if (StringUtils.isNotBlank(getMchId())) {
			map.put("mch_id", getMchId());
		}
		if (StringUtils.isNotBlank(getNonceStr())) {
			map.put("nonce_str", getNonceStr());
		}
		if (StringUtils.isNotBlank(getPrepayId())) {
			map.put("prepay_id", getPrepayId());
		}
		if (StringUtils.isNotBlank(getResultCode())) {
			map.put("result_code", getResultCode());
		}
		if (StringUtils.isNotBlank(getReturnCode())) {
			map.put("return_code", getReturnCode());
		}
		if (StringUtils.isNotBlank(getTradeType())) {
			map.put("trade_type", getTradeType());
		}
		if (StringUtils.isNotBlank(getReturnMsg())) {
			map.put("return_msg", getReturnMsg());
		}
		if (StringUtils.isBlank(getSign())) {
			return false;
		}
		String sign = Signature.getSign(map, apiKey);
		return sign.equals(getSign());
	}

}
