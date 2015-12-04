package com.hichlink.funion.common.flow.exchange;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FlowRespMesgBody {

	@JsonProperty("CONTENT")
	private RespMsgBody content;

	public FlowRespMesgBody() {

	}

	public RespMsgBody getContent() {
		return content;
	}

	public void setContent(RespMsgBody content) {
		this.content = content;
	}

}