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
	private MsgBody msgBody = new MsgBody();
	public static final String SUCC = "00";

	public boolean isSucc() {
		return (null != this.getMsgBody() && null != this.getMsgBody().getResp()
				&& SUCC.equals(this.getMsgBody().getResp().getCode()));
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
	
	@Override
	public String toString() {
		return "FlowOrderResp [flowHeader=" + flowHeader + ", msgBody=" + msgBody + ", isSucc()=" + isSucc()
				+ ", getFlowHeader()=" + getFlowHeader() + ", getMsgBody()=" + getMsgBody() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	public static class MsgBody {
		@JsonProperty("RESP")
		private Resp resp;
		@JsonProperty("CONTENT")
		private Content content = new Content();
		public MsgBody() {
			
		}
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

	public static class Resp {
		@JsonProperty("RCODE")
		private String code;
		@JsonProperty("RMSG")
		private String msg;
		public Resp(){
			
		}
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

	public static class Content {
		@JsonProperty("ORDERID")
		private String orderId;
		@JsonProperty("EXTORDER")
		private String extOrder;
		public Content(){
			super();
		}
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
