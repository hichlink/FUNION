package com.hichlink.funion.portal.common.dto;

import java.math.BigDecimal;

public class FlowProductDTO {
	/**
	 * 产品ID
	 */
	private Long productId;

	/**
	 * 产品名称
	 */
	private String productName;
	/**
	 * 结算价格
	 */
	private BigDecimal settlementPrice;
	
	/**
	 * 结算价格
	 */
	private BigDecimal totalPrice;
	/**
	 * 产品个数
	 */
	private int num;
	/**
	 * 定单Id
	 */
	private String prepayId;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getSettlementPrice() {
		return settlementPrice;
	}

	public void setSettlementPrice(BigDecimal settlementPrice) {
		this.settlementPrice = settlementPrice;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	
}
