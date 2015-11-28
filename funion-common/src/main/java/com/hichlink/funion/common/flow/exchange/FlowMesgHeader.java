package com.hichlink.funion.common.flow.exchange;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FlowMesgHeader {
	@JsonProperty("VERSION")
	private String version;// 消息版本号
	@JsonProperty("TIMESTAMP")
	private String timestamp; // 时间戳 按格式：yyyMMHHmmssSS
	@JsonProperty("SEQNO")
	private String seqNo; // 请求序列号，需保持唯一。
	@JsonProperty("APPID")
	private String appId;
	@JsonProperty("SECERTKEY")
	private String secertKey; // 安全校验KEY MD5(TIMESTAMP+ SEQNO+ UserID)
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSecertKey() {
		return secertKey;
	}

	public void setSecertKey(String secertKey) {
		this.secertKey = secertKey;
	}

	/**
	 * 校验Secertkey
	 * 
	 * @return
	 */
	public boolean checkValidate() {
		// TODO Auto-generated method stub
		return false;
	}

	public FlowMesgHeader() {
		version = "V1.0";
	}

}