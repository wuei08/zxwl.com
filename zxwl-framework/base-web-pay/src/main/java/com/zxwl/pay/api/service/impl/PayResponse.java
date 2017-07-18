package com.zxwl.pay.api.service.impl;

import com.zxwl.pay.api.entity.ApyAccount;
import com.zxwl.pay.api.entity.PayType;
import com.zxwl.pay.api.handle.AliPayMessageHandler;
import com.zxwl.pay.api.handle.WxPayMessageHandler;
import com.zxwl.pay.api.interceptor.AliPayMessageInterceptor;
import com.zxwl.pay.common.api.PayConfigStorage;
import com.zxwl.pay.common.api.PayMessageHandler;
import com.zxwl.pay.common.api.PayMessageRouter;
import com.zxwl.pay.common.api.PayService;
import com.zxwl.pay.common.bean.MsgType;
import com.zxwl.pay.common.http.HttpConfigStorage;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import javax.annotation.Resource;

/**
 * 支付响应对象
 * @author: chendawei
 * @email 1026022306@qq.com
 * @date 2016/11/18 0:34
 */
public class PayResponse {

    @Resource
    private AutowireCapableBeanFactory spring;

    private PayConfigStorage storage;

    private PayService service;

    private PayMessageRouter router;

    public PayResponse() {

    }

    /**
     * 初始化支付配置
     * @param apyAccount 账户信息
     * @see ApyAccount 对应表结构详情--》 /pay-java-demo/resources/apy_account.sql
     */
    public void init(ApyAccount apyAccount) {
        //根据不同的账户类型 初始化支付配置
        this.service = apyAccount.getPayType().getPayService(apyAccount);
        this.storage = service.getPayConfigStorage();
        //这里设置代理配置
//        service.setRequestTemplateConfigStorage(getHttpConfigStorage());
        buildRouter(apyAccount.getPayId());
    }

    /**
     * 获取http配置，如果配置为null则为默认配置，无代理。
     * 此处非必需
     * @return
     */
    public HttpConfigStorage getHttpConfigStorage(){
        HttpConfigStorage httpConfigStorage = new HttpConfigStorage();
        //http代理地址
        httpConfigStorage.setHttpProxyHost("45.32.47.112");
        //代理端口
        httpConfigStorage.setHttpProxyPort(22);
        //代理用户名
        httpConfigStorage.setHttpProxyUsername("root");
        //代理密码
        httpConfigStorage.setHttpProxyPassword("yladmxaa");
        return httpConfigStorage;
    }


    /**
     * 配置路由
     * @param payId 指定账户id，用户多微信支付多支付宝支付
     */
    private void buildRouter(Integer payId) {
        router = new PayMessageRouter(this.service);
        router
                .rule()
                .async(false)
                .msgType(MsgType.text.name()) //消息类型
                .payType(PayType.aliPay.name()) //支付账户事件类型
                .interceptor(new AliPayMessageInterceptor()) //拦截器
                .handler(autowire(new AliPayMessageHandler(payId))) //处理器
                .end()
                .rule()
                .async(false)
                .msgType(MsgType.xml.name())
                .payType(PayType.wxPay.name())
                .handler(autowire(new WxPayMessageHandler(payId)))
                .end()


        ;
    }


    private PayMessageHandler autowire(PayMessageHandler handler) {
        spring.autowireBean(handler);
        return handler;
    }

    public PayConfigStorage getStorage() {
        return storage;
    }

    public PayService getService() {
        return service;
    }

    public PayMessageRouter getRouter() {
        return router;
    }
}
