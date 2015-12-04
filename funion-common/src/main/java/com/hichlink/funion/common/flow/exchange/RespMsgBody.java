package com.hichlink.funion.common.flow.exchange;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RespMsgBody {

	@JsonProperty("ORDERID")
	private String orderId;
	@JsonProperty("EXTORDER")
	private String extOrder;
	@JsonProperty("STATUS")
	private String status;
	@JsonProperty("CODE")
	private String code;
	public static final String SUCC = "00";
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getExtOrder() {
		return extOrder;
	}
	public void setExtOrder(String extOrder) {
		this.extOrder = extOrder;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean isSuccess(){
		return SUCC.equals(this.getCode());
	}
	
}