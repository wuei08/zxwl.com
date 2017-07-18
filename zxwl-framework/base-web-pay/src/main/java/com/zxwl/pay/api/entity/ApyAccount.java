

package com.zxwl.pay.api.entity;

import com.zxwl.pay.common.bean.MsgType;
import com.zxwl.pay.common.util.str.StringUtils;

import java.util.Date;


/**
 * 支付账户
 * @author: chendawei
 * @email 1026022306@qq.com
 * @date 2016/11/18 0:36
 */

public class ApyAccount {
    // 支付账号id
//    @Id
    private Integer payId;
    // 支付合作id,商户id，差不多是支付平台的账号或id
    private String partner;
    // 应用id
    private String appid;
    //  支付公钥，sign_type只有单一key时public_key与private_key相等，比如sign_type=MD5的情况
    private String publicKey;
    // 支付私钥
    private String privateKey;
    // 异步回调地址
    private String notifyUrl;
    // 同步回调地址
    private String returnUrl;
    // 收款账号
    private String seller;
    // 签名类型
    private String signType;
    // 编码类型 枚举值，字符编码 utf-8,gbk等等
    private String inputCharset;
    //支付类型,aliPay：支付宝，wxPay：微信
    private PayType payType;
    // 消息类型，text,xml,json
    private MsgType msgType;
    //是否为测试环境
    private boolean isTest = false;
    //创建人
    private  String createBy;
    //创建时间
    private Date createTime;

    public Integer getPayId() {
        return payId;
    }

    public void setPayId(Integer payId) {
        this.payId = payId;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }

    public String getInputCharset() {
        return inputCharset;
    }

    public void setInputCharset(String inputCharset) {
        this.inputCharset = inputCharset;
    }

    public boolean isTest() {
        return isTest;
    }

    public void setTest(boolean test) {
        isTest = test;
    }

    @Override
    public String toString() {
        return "ApyAccount{" +
                "payId=" + payId +
                ", partner='" + partner + '\'' +
                ", appid='" + appid + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", privateKey='" + privateKey + '\'' +
                ", notifyUrl='" + notifyUrl + '\'' +
                ", returnUrl='" + returnUrl + '\'' +
                ", seller='" + seller + '\'' +
                ", signType='" + signType + '\'' +
                ", inputCharset='" + inputCharset + '\'' +
                ", payType=" + payType +
                ", msgType=" + msgType +
                '}';
    }

    public String getCreateBy() {
        if (StringUtils.isEmpty ( createBy ))
            createBy = "david";
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        createTime = new Date (  );
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
