/*** Auto generator by codegenerator 2015-11-28 14:10:29*/
package com.hichlink.funion.common.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.hichlink.funion.common.util.DateUtil;

public class BalanceFlow {
	/**
	 * 流水ID
	 */
	private Long flowId;

	/**
	 * 代理ID
	 */
	private Long agentId;

	/**
	 * 记录ID
	 */
	private Long recordId;

	/**
	 * 结算类型 1:佣金
	 */
	private Integer type;
	
	public static final Integer TYPE_COMMISION = 1;

	/**
	 * 佣金
	 */
	private BigDecimal commisionAmount;

	/**
	 * 录入时间
	 */
	private Date inputTime;
	
	private String checkTime;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 流水ID
	 * 
	 * @return the value of balance_flow.flow_id
	 */
	public Long getFlowId() {
		return flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	/**
	 * 代理ID
	 * 
	 * @return the value of balance_flow.agent_id
	 */
	public Long getAgentId() {
		return agentId;
	}

	public void setAgentId(Long agentId) {
		this.agentId = agentId;
	}

	/**
	 * 记录ID
	 * 
	 * @return the value of balance_flow.record_id
	 */
	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	/**
	 * 结算类型 1:佣金
	 * 
	 * @return the value of balance_flow.type
	 */
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * 佣金
	 * 
	 * @return the value of balance_flow.commision_amount
	 */
	public BigDecimal getCommisionAmount() {
		return commisionAmount;
	}

	public void setCommisionAmount(BigDecimal commisionAmount) {
		this.commisionAmount = commisionAmount;
	}

	/**
	 * 录入时间
	 * 
	 * @return the value of balance_flow.input_time
	 */
	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	/**
	 * 备注
	 * 
	 * @return the value of balance_flow.remark
	 */
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getCheckTime() {
		if (null != this.getInputTime()){
			this.checkTime = DateUtil.dateToDateString(this.getInputTime(), DateUtil.MMYYYYDD_EN);
		}
		return checkTime;
	}

	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	
}
