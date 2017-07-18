package com.zxwl.pay.common.bean.outbuilder;

import com.zxwl.pay.common.bean.PayOutMessage;

/**
 * source chanjarster/weixin-java-tools
 *
 * @author  chendawei
 * <pre>
 *     email 1026022306@qq.com
 *     date 2016-6-1 11:40:30
 *  </pre>
 */
public abstract class BaseBuilder<BuilderType, ValueType> {


    public abstract ValueType build();

    public void setCommon(PayOutMessage m) {

    }

}