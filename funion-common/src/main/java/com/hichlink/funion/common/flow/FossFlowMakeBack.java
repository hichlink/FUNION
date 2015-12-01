package com.hichlink.funion.common.flow;

public class FossFlowMakeBack {
	private String code; // 返回码

	private String orderId; // 返回CP订单号

	private String msg; // 返回具体信息
	public static final String OK = "00";
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
