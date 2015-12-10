package com.hichlink.funion.common.flow.entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FlowNotifyReq {
	@JsonProperty("HEADER")
	private FlowHeader flowHeader = new FlowHeader();
	@JsonProperty("MSGBODY")
	private MsgBody msgBody;
	
	public static final String SUCC = "00";

	public boolean isSucc() {
		return (null != this.getMsgBody() && null != this.getMsgBody().getContent()
				&& SUCC.equals(this.getMsgBody().getContent().getCode()));
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
		@JsonProperty("CONTENT")
		private Content content;

		public Content getContent() {
			return content;
		}

		public void setContent(Content content) {
			this.content = content;
		}

	}

	public class Content {
		@JsonProperty("ORDERID")
		private String orderId;
		@JsonProperty("EXTORDER")
		private String extOrder;
		@JsonProperty("STATUS")
		private String status;
		@JsonProperty("CODE")
		private String code;

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

	}
}
