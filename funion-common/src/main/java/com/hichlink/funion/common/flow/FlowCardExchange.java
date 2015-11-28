package com.hichlink.funion.common.flow;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * 流量兑换返回
 * @author wml
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class FlowCardExchange {
	@JsonProperty("ORDERID")
	private String ORDERID;			//FMP订单批次流水号
	@JsonProperty("EXTORDER")
	private String EXTORDER;		//CP本地订单号
	@JsonProperty("CARDPASS")
	private String CARDNO;		//兑换的卡密
	@JsonProperty("CARDNO")
	private String CARDPASS;		//兑换的卡密
	@JsonProperty("MOBILE")
	private String MOBILE;			//兑换手机号码
	@JsonProperty("CARDSTATUS")
	private int CARDSTATUS;			//券状态	1-初始化 2-激活 3-已兑换 4-过期 5-锁定 6-作废
	@JsonProperty("PRODUCTID")
	private int PRODUCTID;			//产品ID
	@JsonProperty("RECEIVEDATE")
	private String RECEIVEDATE;		//券兑换时间	格式:yyyyMMddHHmmss
	@JsonProperty("VALIDTIME")
	private String VALIDTIME;		//劵失效时间
	
	@JsonIgnore
	public String getCARDNO() {
		return CARDNO;
	}
	@JsonIgnore
	public void setCARDNO(String cARDNO) {
		CARDNO = cARDNO;
	}
	@JsonIgnore
	public String getVALIDTIME() {
		return VALIDTIME;
	}
	@JsonIgnore
	public void setVALIDTIME(String vALIDTIME) {
		VALIDTIME = vALIDTIME;
	}
	@JsonIgnore
	public String getORDERID() {
		return ORDERID;
	}
	@JsonIgnore
	public void setORDERID(String oRDERID) {
		ORDERID = oRDERID;
	}
	@JsonIgnore
	public String getEXTORDER() {
		return EXTORDER;
	}
	@JsonIgnore
	public void setEXTORDER(String eXTORDER) {
		EXTORDER = eXTORDER;
	}
	@JsonIgnore
	public String getCARDPASS() {
		return CARDPASS;
	}
	@JsonIgnore
	public void setCARDPASS(String cARDPASS) {
		CARDPASS = cARDPASS;
	}
	@JsonIgnore
	public String getMOBILE() {
		return MOBILE;
	}
	@JsonIgnore
	public void setMOBILE(String mOBILE) {
		MOBILE = mOBILE;
	}
	@JsonIgnore
	public int getCARDSTATUS() {
		return CARDSTATUS;
	}
	@JsonIgnore
	public void setCARDSTATUS(int cARDSTATUS) {
		CARDSTATUS = cARDSTATUS;
	}
	@JsonIgnore
	public int getPRODUCTID() {
		return PRODUCTID;
	}
	@JsonIgnore
	public void setPRODUCTID(int pRODUCTID) {
		PRODUCTID = pRODUCTID;
	}
	@JsonIgnore
	public String getRECEIVEDATE() {
		return RECEIVEDATE;
	}
	@JsonIgnore
	public void setRECEIVEDATE(String rECEIVEDATE) {
		RECEIVEDATE = rECEIVEDATE;
	}
}
