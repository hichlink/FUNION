/*** Auto generator by codegenerator 2015-11-29 13:44:09*/
package com.hichlink.funion.common.entity;

import java.math.BigDecimal;

public class FlowProductInfo {
    /**
    产品ID
     */
    private Long productId;

    /**
    产品名称
     */
    private String productName;

    /**
    产品代码
     */
    private String productCode;

    /**
    流量包ID
     */
    private String packageId;

    /**
    适用区域
     */
    private String zone;

    /**
    对应运营商产品ID,YD:移动　LT:联通　DX:电信
     */
    private String operatorCode;

    /**
    原价格
     */
    private BigDecimal costPrice;

    /**
    结算价格
     */
    private BigDecimal settlementPrice;

    /**
    运营商价格
     */
    private BigDecimal operatorPrice;

    /**
    备注
     */
    private String remark;

    /**
    流量包大小
     */
    private Integer flowAmount;

    /**
    产品ID
     * @return the value of flow_product_info.product_id
     */
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    /**
    产品名称
     * @return the value of flow_product_info.product_name
     */
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    /**
    产品代码
     * @return the value of flow_product_info.product_code
     */
    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    /**
    流量包ID
     * @return the value of flow_product_info.package_id
     */
    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId == null ? null : packageId.trim();
    }

    /**
    适用区域
     * @return the value of flow_product_info.zone
     */
    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone == null ? null : zone.trim();
    }

    /**
    对应运营商产品ID,YD:移动　LT:联通　DX:电信
     * @return the value of flow_product_info.operator_code
     */
    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode == null ? null : operatorCode.trim();
    }

    /**
    原价格
     * @return the value of flow_product_info.cost_price
     */
    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    /**
    结算价格
     * @return the value of flow_product_info.settlement_price
     */
    public BigDecimal getSettlementPrice() {
        return settlementPrice;
    }

    public void setSettlementPrice(BigDecimal settlementPrice) {
        this.settlementPrice = settlementPrice;
    }

    /**
    运营商价格
     * @return the value of flow_product_info.operator_price
     */
    public BigDecimal getOperatorPrice() {
        return operatorPrice;
    }

    public void setOperatorPrice(BigDecimal operatorPrice) {
        this.operatorPrice = operatorPrice;
    }

    /**
    备注
     * @return the value of flow_product_info.remark
     */
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
    流量包大小
     * @return the value of flow_product_info.flow_amount
     */
    public Integer getFlowAmount() {
        return flowAmount;
    }

    public void setFlowAmount(Integer flowAmount) {
        this.flowAmount = flowAmount;
    }
}
