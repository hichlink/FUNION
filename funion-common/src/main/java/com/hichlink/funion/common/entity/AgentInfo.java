/*** Auto generator by codegenerator 2015-11-28 14:10:24*/
package com.hichlink.funion.common.entity;

import java.math.BigDecimal;
import java.util.Date;

public class AgentInfo {
    /**
    代理ID
     */
    private Long agentId;

    /**
    身份标识,随机生成唯一
     */
    private String identityId;

    /**
    代理别名
     */
    private String agentName;

    /**
    微信OpenID
     */
    private String openId;

    /**
    微信UnionID
     */
    private String unionId;

    /**
    微信昵称
     */
    private String nickName;

    /**
    微信头像
     */
    private String headImg;

    /**
    真实姓名
     */
    private String realName;

    /**
    手机号码
     */
    private String mobile;

    /**
    邮箱地址
     */
    private String email;

    /**
    身份证号码
     */
    private String cardNo;

    /**
    录入时间
     */
    private Date inputTime;

    /**
    录入者
     */
    private String inputBy;

    /**
    修改时间
     */
    private Date modifyTime;

    /**
    修改者
     */
    private String modifier;

    /**
    父结点
     */
    private Long parentId;

    /**
    佣金比例 0-100
     */
    private Integer commisionRatio;

    /**
    帐户余额
     */
    private BigDecimal balance;

    /**
    代理ID
     * @return the value of agent_info.agent_id
     */
    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    /**
    身份标识,随机生成唯一
     * @return the value of agent_info.identity_id
     */
    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId == null ? null : identityId.trim();
    }

    /**
    代理别名
     * @return the value of agent_info.agent_name
     */
    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName == null ? null : agentName.trim();
    }

    /**
    微信OpenID
     * @return the value of agent_info.open_id
     */
    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    /**
    微信UnionID
     * @return the value of agent_info.union_id
     */
    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId == null ? null : unionId.trim();
    }

    /**
    微信昵称
     * @return the value of agent_info.nick_name
     */
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**
    微信头像
     * @return the value of agent_info.head_img
     */
    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg == null ? null : headImg.trim();
    }

    /**
    真实姓名
     * @return the value of agent_info.real_name
     */
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    /**
    手机号码
     * @return the value of agent_info.mobile
     */
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
    邮箱地址
     * @return the value of agent_info.email
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
    身份证号码
     * @return the value of agent_info.card_no
     */
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo == null ? null : cardNo.trim();
    }

    /**
    录入时间
     * @return the value of agent_info.input_time
     */
    public Date getInputTime() {
        return inputTime;
    }

    public void setInputTime(Date inputTime) {
        this.inputTime = inputTime;
    }

    /**
    录入者
     * @return the value of agent_info.input_by
     */
    public String getInputBy() {
        return inputBy;
    }

    public void setInputBy(String inputBy) {
        this.inputBy = inputBy == null ? null : inputBy.trim();
    }

    /**
    修改时间
     * @return the value of agent_info.modify_time
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
    修改者
     * @return the value of agent_info.modifier
     */
    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier == null ? null : modifier.trim();
    }

    /**
    父结点
     * @return the value of agent_info.parent_id
     */
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
    佣金比例 0-100
     * @return the value of agent_info.commision_ratio
     */
    public Integer getCommisionRatio() {
        return commisionRatio;
    }

    public void setCommisionRatio(Integer commisionRatio) {
        this.commisionRatio = commisionRatio;
    }

    /**
    帐户余额
     * @return the value of agent_info.balance
     */
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
