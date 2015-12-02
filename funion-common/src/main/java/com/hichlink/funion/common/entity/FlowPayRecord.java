/*** Auto generator by codegenerator 2015-11-28 14:10:42*/
package com.hichlink.funion.common.entity;

import java.math.BigDecimal;
import java.util.Date;

public class FlowPayRecord {
	/**
	 * 记录ID
	 */
	private Long recordId;

	/**
	 * 微信OpenID
	 */
	private String openId;

	/**
	 * 微信UnionID
	 */
	private String unionId;

	/**
	 * 微信昵称
	 */
	private String nickName;

	/**
	 * 微信头像
	 */
	private String headImg;

	/**
	 * 手机号码
	 */
	private String mobile;

	/**
	 * 产品ID
	 */
	private Long productId;

	/**
	 * 流量包个数
	 */
	private Integer num;

	/**
	 * 原价格
	 */
	private BigDecimal costPrice;

	/**
	 * 结算价格
	 */
	private BigDecimal settlementPrice;

	/**
	 * 运营商价格
	 */
	private BigDecimal operatorPrice;

	/**
	 * 支付类型 1:微信支付 2:支付宝支付
	 */
	private Integer payType;
	
	public static final Integer PAY_TYPE_WX = 1;
	
	public static final Integer PAY_TYPE_ALIPAY = 2;

	/**
	 * 支付状态 0:未支付 1:支付成功 2:支付完成 3:支付失败
	 */
	private Integer payStatus;
	
	public static final Integer PAY_STATUS_INIT = 0;
	
	public static final Integer PAY_STATUS_SUCC = 1;
	
	public static final Integer PAY_STATUS_FINISH = 2;
	
	public static final Integer PAY_STATUS_FAIL = 3;
	/**
	 * 流量下发状态 0:网关收到成功 00:成功 1:拒绝收单 其它编码表失败
	 */
	private String sendStatus;
	
	public static final String SEND_STATUS_INIT = "01";
	
	public static final String SEND_STATUS_GATE_OK = "02";
	
	public static final String SEND_STATUS_SENDING = "03";
	
	public static final String SEND_STATUS_OK = "00";
	
	public static final String SEND_STATUS_FAIL = "99";

	/**
	 * 商户订单号
	 */
	private String outTradeNo;

	/**
	 * 购买时间
	 */
	private Date inputTime;

	/**
	 * 购买来源标识
	 */
	private String identityId;

	/**
	 * 结算标识 0:否 1:是
	 */
	private Integer isBalance;
	public static final Integer IS_BALANCE_NO = 0;
	public static final Integer IS_BALANCE_YES = 1;
	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 记录ID
	 * 
	 * @return the value of flow_pay_record.record_id
	 */
	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	/**
	 * 微信OpenID
	 * 
	 * @return the value of flow_pay_record.open_id
	 */
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId == null ? null : openId.trim();
	}

	/**
	 * 微信UnionID
	 * 
	 * @return the value of flow_pay_record.union_id
	 */
	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId == null ? null : unionId.trim();
	}

	/**
	 * 微信昵称
	 * 
	 * @return the value of flow_pay_record.nick_name
	 */
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName == null ? null : nickName.trim();
	}

	/**
	 * 微信头像
	 * 
	 * @return the value of flow_pay_record.head_img
	 */
	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg == null ? null : headImg.trim();
	}

	/**
	 * 手机号码
	 * 
	 * @return the value of flow_pay_record.mobile
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
	 * @return the value of flow_pay_record.product_id
	 */
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/**
	 * 流量包个数
	 * 
	 * @return the value of flow_pay_record.num
	 */
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	/**
	 * 原价格
	 * 
	 * @return the value of flow_pay_record.cost_price
	 */
	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	/**
	 * 结算价格
	 * 
	 * @return the value of flow_pay_record.settlement_price
	 */
	public BigDecimal getSettlementPrice() {
		return settlementPrice;
	}

	public void setSettlementPrice(BigDecimal settlementPrice) {
		this.settlementPrice = settlementPrice;
	}

	/**
	 * 运营商价格
	 * 
	 * @return the value of flow_pay_record.operator_price
	 */
	public BigDecimal getOperatorPrice() {
		return operatorPrice;
	}

	public void setOperatorPrice(BigDecimal operatorPrice) {
		this.operatorPrice = operatorPrice;
	}

	/**
	 * 支付类型 1:微信支付 2:支付宝支付
	 * 
	 * @return the value of flow_pay_record.pay_type
	 */
	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	/**
	 * 支付状态 0:未支付 1:支付成功 2:支付完成 3:支付失败
	 * 
	 * @return the value of flow_pay_record.pay_status
	 */
	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	/**
	 * 流量下发状态 0:网关收到成功 00:成功 1:拒绝收单 其它编码表失败
	 * 
	 * @return the value of flow_pay_record.send_status
	 */
	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus == null ? null : sendStatus.trim();
	}

	/**
	 * 商户订单号
	 * 
	 * @return the value of flow_pay_record.out_trade_no
	 */
	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
	}

	/**
	 * 购买时间
	 * 
	 * @return the value of flow_pay_record.input_time
	 */
	public Date getInputTime() {
		return inputTime;
	}

	public void setInputTime(Date inputTime) {
		this.inputTime = inputTime;
	}

	/**
	 * 购买来源标识
	 * 
	 * @return the value of flow_pay_record.identity_id
	 */
	public String getIdentityId() {
		return identityId;
	}

	public void setIdentityId(String identityId) {
		this.identityId = identityId == null ? null : identityId.trim();
	}

	/**
	 * 结算标识 0:否 1:是
	 * 
	 * @return the value of flow_pay_record.is_balance
	 */
	public Integer getIsBalance() {
		return isBalance;
	}

	public void setIsBalance(Integer isBalance) {
		this.isBalance = isBalance;
	}

	/**
	 * 备注
	 * 
	 * @return the value of flow_pay_record.remark
	 */
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}
}
