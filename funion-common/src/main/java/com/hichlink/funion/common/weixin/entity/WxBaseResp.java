package com.hichlink.funion.common.weixin.entity;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;
public class WxBaseResp implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7607091247945224632L;
	@XStreamAlias("return_code")
	private String returnCode;
	@XStreamAlias("return_msg")
	private String returnMsg;
	@XStreamAlias("result_code")
	private String resultCode;

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public boolean isResultSuccess() {
		return Constants.SUCCESS.equalsIgnoreCase(this.getResultCode());
	}

	public boolean isReturnSuccess() {
		return Constants.SUCCESS.equalsIgnoreCase(this.getReturnCode());
	}

	public boolean isSuccess() {
		return this.isReturnSuccess() && this.isResultSuccess();
	}
}
