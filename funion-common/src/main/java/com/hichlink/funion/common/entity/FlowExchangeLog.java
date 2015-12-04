/*** Auto generator by codegenerator 2015-11-28 14:10:38*/
package com.hichlink.funion.common.entity;

import java.util.Date;

public class FlowExchangeLog {
	/**
	 * 自增序列
	 */
	private Long logId;

	/**
	 * 本地流水号
	 */
	private String flowVoucherId;

	/**
	 * 网关流水号ID
	 */
	private String exchangeOrderId;

	/**
	 * 兑换类型 00-流量
	 */
	private String sourceType;

	/**
	 * 兑换手机号
	 */
	private String mobile;

	/**
	 * 产品ID
	 */
	private Long productId;

	/**
	 * 产品数量
	 */
	private Integer itemCount;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 状态成功 0:网关收到成功 00:成功 1:拒绝收单 其它编码表失败
	 */
	private String flag;
	
	
	/**
	 * 归属运营商
	 */
	private String mobileOperator;

	/**
	 * 归属地
	 */
	private String mobileHome;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 记录ID
	 */
	private Long recordId;

	/**
	 * 自增序列
	 * 
	 * @return the value of flow_exchange_log.log_id
	 */
	public Long getLogId() {
		return logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	/**
	 * 本地流水号
	 * 
	 * @return the value of flow_exchange_log.flow_voucher_id
	 */
	public String getFlowVoucherId() {
		return flowVoucherId;
	}

	public void setFlowVoucherId(String flowVoucherId) {
		this.flowVoucherId = flowVoucherId == null ? null : flowVoucherId.trim();
	}

	/**
	 * 网关流水号ID
	 * 
	 * @return the value of flow_exchange_log.exchange_order_id
	 */
	public String getExchangeOrderId() {
		return exchangeOrderId;
	}

	public void setExchangeOrderId(String exchangeOrderId) {
		this.exchangeOrderId = exchangeOrderId == null ? null : exchangeOrderId.trim();
	}

	/**
	 * 兑换类型 00-流量
	 * 
	 * @return the value of flow_exchange_log.source_type
	 */
	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType == null ? null : sourceType.trim();
	}

	/**
	 * 兑换手机号
	 * 
	 * @return the value of flow_exchange_log.mobile
	 */
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile == null ? null : mobile.trim();
	}

	/**
	 * 产品ID
	 * 
	 * @return the value of flow_exchange_log.product_id
	 */
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/**
	 * 产品数量
	 * 
	 * @return the value of flow_exchange_log.item_count
	 */
	public Integer getItemCount() {
		return itemCount;
	}

	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	/**
	 * 创建时间
	 * 
	 * @return the value of flow_exchange_log.create_time
	 */
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 状态成功 0:网关收到成功 00:成功 1:拒绝收单 其它编码表失败
	 * 
	 * @return the value of flow_exchange_log.flag
	 */
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag == null ? null : flag.trim();
	}

	/**
	 * 归属运营商
	 * 
	 * @return the value of flow_exchange_log.mobile_operator
	 */
	public String getMobileOperator() {
		return mobileOperator;
	}

	public void setMobileOperator(String mobileOperator) {
		this.mobileOperator = mobileOperator == null ? null : mobileOperator.trim();
	}

	/**
	 * 归属地
	 * 
	 * @return the value of flow_exchange_log.mobile_home
	 */
	public String getMobileHome() {
		return mobileHome;
	}

	public void setMobileHome(String mobileHome) {
		this.mobileHome = mobileHome == null ? null : mobileHome.trim();
	}

	/**
	 * 备注
	 * 
	 * @return the value of flow_exchange_log.remark
	 */
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	/**
	 * 记录ID
	 * 
	 * @return the value of flow_exchange_log.record_id
	 */
	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
}
