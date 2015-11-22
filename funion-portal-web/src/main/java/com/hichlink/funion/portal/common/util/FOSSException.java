package com.hichlink.funion.portal.common.util;

public class FOSSException extends Exception {

	private static final long serialVersionUID = 3891423944680813582L;
	
	private String code;
	
	private String msg;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public FOSSException(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public FOSSException() {
		super();
	}

}
