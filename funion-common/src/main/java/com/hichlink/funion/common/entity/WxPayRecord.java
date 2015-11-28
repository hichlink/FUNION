/*** Auto generator by codegenerator 2015-11-28 14:10:55*/
package com.hichlink.funion.common.entity;

import java.math.BigDecimal;
import java.util.Date;

public class WxPayRecord {
    /**
    支付自增序列
     */
    private Long paySeqId;

    /**
    支付流水号
     */
    private String outTradeNo;

    /**
    微信订单号
     */
    private String transactionId;

    /**
    设备号
     */
    private String deviceInfo;

    /**
    微信OpenID
     */
    private String openId;

    /**
    微信OpenID
     */
    private String nickName;

    /**
    单价
     */
    private BigDecimal price;

    /**
    数量
     */
    private Integer num;

    /**
    总金额
     */
    private BigDecimal totalFee;

    /**
    备注
     */
    private String remark;

    /**
    支付时间
     */
    private Date payTime;

    /**
    对账时间
     */
    private Date payCheckTime;

    /**
    支付IP
     */
    private String payIp;

    /**
    交易类型,取值如下：JSAPI，NATIVE，APP，WAP
     */
    private String tradeType;

    /**
    支付状态 0:提交待支付 1:支付成功 2:支付完成 3:支付失败
     */
    private Integer payStatus;

    /**
    支付类型 0:贺卡
     */
    private Integer payType;

    /**
    记录ID
     */
    private Long recordId;

    /**
    支付自增序列
     * @return the value of wx_pay_record.pay_seq_id
     */
    public Long getPaySeqId() {
        return paySeqId;
    }

    public void setPaySeqId(Long paySeqId) {
        this.paySeqId = paySeqId;
    }

    /**
    支付流水号
     * @return the value of wx_pay_record.out_trade_no
     */
    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo == null ? null : outTradeNo.trim();
    }

    /**
    微信订单号
     * @return the value of wx_pay_record.transaction_id
     */
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId == null ? null : transactionId.trim();
    }

    /**
    设备号
     * @return the value of wx_pay_record.device_info
     */
    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo == null ? null : deviceInfo.trim();
    }

    /**
    微信OpenID
     * @return the value of wx_pay_record.open_id
     */
    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    /**
    微信OpenID
     * @return the value of wx_pay_record.nick_name
     */
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**
    单价
     * @return the value of wx_pay_record.price
     */
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
    数量
     * @return the value of wx_pay_record.num
     */
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    /**
    总金额
     * @return the value of wx_pay_record.total_fee
     */
    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    /**
    备注
     * @return the value of wx_pay_record.remark
     */
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
    支付时间
     * @return the value of wx_pay_record.pay_time
     */
    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
    对账时间
     * @return the value of wx_pay_record.pay_check_time
     */
    public Date getPayCheckTime() {
        return payCheckTime;
    }

    public void setPayCheckTime(Date payCheckTime) {
        this.payCheckTime = payCheckTime;
    }

    /**
    支付IP
     * @return the value of wx_pay_record.pay_ip
     */
    public String getPayIp() {
        return payIp;
    }

    public void setPayIp(String payIp) {
        this.payIp = payIp == null ? null : payIp.trim();
    }

    /**
    交易类型,取值如下：JSAPI，NATIVE，APP，WAP
     * @return the value of wx_pay_record.trade_type
     */
    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType == null ? null : tradeType.trim();
    }

    /**
    支付状态 0:提交待支付 1:支付成功 2:支付完成 3:支付失败
     * @return the value of wx_pay_record.pay_status
     */
    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    /**
    支付类型 0:贺卡
     * @return the value of wx_pay_record.pay_type
     */
    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    /**
    记录ID
     * @return the value of wx_pay_record.record_id
     */
    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }
}
