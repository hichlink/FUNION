/*** Auto generator by codegenerator 2015-11-28 14:10:35*/
package com.hichlink.funion.common.entity;

import java.math.BigDecimal;
import java.util.Date;

public class FetchCashFlow {
    /**
    流水ID
     */
    private Long flowId;

    /**
    代理ID
     */
    private Long agentId;

    /**
    提现类型
     */
    private Integer type;

    /**
    提现金额
     */
    private BigDecimal cashAmount;

    /**
    录入时间
     */
    private Date inputTime;

    /**
    备注
     */
    private String remark;

    /**
    流水ID
     * @return the value of fetch_cash_flow.flow_id
     */
    public Long getFlowId() {
        return flowId;
    }

    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }

    /**
    代理ID
     * @return the value of fetch_cash_flow.agent_id
     */
    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    /**
    提现类型
     * @return the value of fetch_cash_flow.type
     */
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    /**
    提现金额
     * @return the value of fetch_cash_flow.cash_amount
     */
    public BigDecimal getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(BigDecimal cashAmount) {
        this.cashAmount = cashAmount;
    }

    /**
    录入时间
     * @return the value of fetch_cash_flow.input_time
     */
    public Date getInputTime() {
        return inputTime;
    }

    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }

    /**
    备注
     * @return the value of fetch_cash_flow.remark
     */
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}
