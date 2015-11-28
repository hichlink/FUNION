package com.hichlink.funion.common.flow;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FOSSMakeOrderReqMesg {
	@JsonProperty("USER")
	private String user; // 用户标识(手机号码)
	@JsonProperty("PACKAGEID")
	private String packageId; // 流量包ID。
	@JsonProperty("ORDERTYPE")
	private String orderType; // 订单类型
	@JsonProperty("EXTORDER")
	private String extOrder; // 外部订单号
	@JsonProperty("APPID")
	private String appId;
	@JsonProperty("APPKEY")
	private String appKey;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getExtOrder() {
		return extOrder;
	}

	public void setExtOrder(String extOrder) {
		this.extOrder = extOrder;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

}
