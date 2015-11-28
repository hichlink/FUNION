/*** Auto generator by codegenerator 2015-11-28 14:10:51*/
package com.hichlink.funion.common.entity;

import java.util.Date;

public class WxAccessConf {
    /**
    自增序列
     */
    private Long seqId;

    /**
    微信名称
     */
    private String wxName;

    /**
    获取到的凭证
     */
    private String accessToken;

    /**
    凭证有效时间
     */
    private Date expiresTime;

    /**
    微信appid
     */
    private String appId;

    /**
    微信appsecret
     */
    private String appSecret;

    /**
    微信token
     */
    private String token;

    /**
    微信jsapiTicket
     */
    private String jsapiTicket;

    /**
    JS凭证有效时间
     */
    private Date jsapExpiresTime;

    /**
    录入者ip
     */
    private String ip;

    /**
    微信aeskey
     */
    private String aesKey;

    /**
    微信支付商户号
     */
    private String wxMerchantNo;

    /**
    支付回调URL
     */
    private String wxPayCallbackUrl;

    /**
    创建者
     */
    private String creator;

    /**
    创建时间
     */
    private Date createTime;

    /**
    修改者
     */
    private String updator;

    /**
    修改时间
     */
    private Date updateTime;

    /**
    微信支付签名key
     */
    private String apiKey;

    /**
    自增序列
     * @return the value of wx_access_conf.seq_id
     */
    public Long getSeqId() {
        return seqId;
    }

    public void setSeqId(Long seqId) {
        this.seqId = seqId;
    }

    /**
    微信名称
     * @return the value of wx_access_conf.wx_name
     */
    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName == null ? null : wxName.trim();
    }

    /**
    获取到的凭证
     * @return the value of wx_access_conf.access_token
     */
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken == null ? null : accessToken.trim();
    }

    /**
    凭证有效时间
     * @return the value of wx_access_conf.expires_time
     */
    public Date getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(Date expiresTime) {
        this.expiresTime = expiresTime;
    }

    /**
    微信appid
     * @return the value of wx_access_conf.app_id
     */
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    /**
    微信appsecret
     * @return the value of wx_access_conf.app_secret
     */
    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret == null ? null : appSecret.trim();
    }

    /**
    微信token
     * @return the value of wx_access_conf.token
     */
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    /**
    微信jsapiTicket
     * @return the value of wx_access_conf.jsapi_ticket
     */
    public String getJsapiTicket() {
        return jsapiTicket;
    }

    public void setJsapiTicket(String jsapiTicket) {
        this.jsapiTicket = jsapiTicket == null ? null : jsapiTicket.trim();
    }

    /**
    JS凭证有效时间
     * @return the value of wx_access_conf.jsap_expires_time
     */
    public Date getJsapExpiresTime() {
        return jsapExpiresTime;
    }

    public void setJsapExpiresTime(Date jsapExpiresTime) {
        this.jsapExpiresTime = jsapExpiresTime;
    }

    /**
    录入者ip
     * @return the value of wx_access_conf.ip
     */
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
    微信aeskey
     * @return the value of wx_access_conf.aes_key
     */
    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey == null ? null : aesKey.trim();
    }

    /**
    微信支付商户号
     * @return the value of wx_access_conf.wx_merchant_no
     */
    public String getWxMerchantNo() {
        return wxMerchantNo;
    }

    public void setWxMerchantNo(String wxMerchantNo) {
        this.wxMerchantNo = wxMerchantNo == null ? null : wxMerchantNo.trim();
    }

    /**
    支付回调URL
     * @return the value of wx_access_conf.wx_pay_callback_url
     */
    public String getWxPayCallbackUrl() {
        return wxPayCallbackUrl;
    }

    public void setWxPayCallbackUrl(String wxPayCallbackUrl) {
        this.wxPayCallbackUrl = wxPayCallbackUrl == null ? null : wxPayCallbackUrl.trim();
    }

    /**
    创建者
     * @return the value of wx_access_conf.creator
     */
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
    创建时间
     * @return the value of wx_access_conf.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
    修改者
     * @return the value of wx_access_conf.updator
     */
    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }

    /**
    修改时间
     * @return the value of wx_access_conf.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
    微信支付签名key
     * @return the value of wx_access_conf.api_key
     */
    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey == null ? null : apiKey.trim();
    }
}
