package com.hichlink.funion.common.flow.exchange;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import net.sf.json.JSONObject;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class RespMsgBody {

	@JsonProperty("RCODE")
	private String rcode;
	@JsonProperty("RMSG")
	private String rmsg;

	public RespMsgBody() {
		rcode = "-1";
		rmsg = "null";
	}

	public RespMsgBody(String jsonStr) {
		JSONObject rtnjb = JSONObject.fromObject(jsonStr);
		rcode = rtnjb.getString("RCODE");
		rmsg = rtnjb.getString("RMSG");
	}

	public RespMsgBody(String retCode, String retMsg) {
		rcode = retCode;
		rmsg = retMsg;
	}

	public String getRcode() {
		return rcode;
	}

	public void setRcode(String rcode) {
		this.rcode = rcode;
	}

	public String getRmsg() {
		return rmsg;
	}

	public void setRmsg(String rmsg) {
		this.rmsg = rmsg;
	}
	
}