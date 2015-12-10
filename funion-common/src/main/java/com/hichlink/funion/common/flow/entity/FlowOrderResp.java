package com.hichlink.funion.common.flow.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FlowOrderResp {
	@JsonProperty("HEADER")
	private FlowHeader flowHeader = new FlowHeader();
	@JsonProperty("MSGBODY")
	private MsgBody msgBody;
	public static final String SUCC = "00";

	public boolean isSucc() {
		return (null != this.getMsgBody() && null != this.getMsgBody().getResp()
				&& SUCC.equals(this.getMsgBody().getResp()));
	}

	public FlowHeader getFlowHeader() {
		return flowHeader;
	}

	public void setFlowHeader(FlowHeader flowHeader) {
		this.flowHeader = flowHeader;
	}

	public MsgBody getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(MsgBody msgBody) {
		this.msgBody = msgBody;
	}

	public class MsgBody {
		@JsonProperty("RESP")
		private Resp resp;
		@JsonProperty("CONTENT")
		private Content content;

		public Resp getResp() {
			return resp;
		}

		public void setResp(Resp resp) {
			this.resp = resp;
		}

		public Content getContent() {
			return content;
		}

		public void setContent(Content content) {
			this.content = content;
		}

	}

	public class Resp {
		@JsonProperty("RCODE")
		private String code;
		@JsonProperty("RMSG")
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

	}

	public class Content {
		@JsonProperty("ORDERID")
		private String orderId;
		@JsonProperty("EXTORDER")
		private String extOrder;

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

	}
}
