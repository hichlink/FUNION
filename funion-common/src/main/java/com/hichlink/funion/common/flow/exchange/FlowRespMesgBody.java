package com.hichlink.funion.common.flow.exchange;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import net.sf.json.JSONObject;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FlowRespMesgBody {

	@JsonProperty("CONTENT")
	private JSONObject content;

	@JsonProperty("RESP")
	private RespMsgBody resp;

	public FlowRespMesgBody() {

	}

	public JSONObject getContent() {
		return content;
	}

	public void setContent(JSONObject content) {
		this.content = content;
	}

	public RespMsgBody getResp() {
		return resp;
	}

	public void setResp(RespMsgBody resp) {
		this.resp = resp;
	}

}