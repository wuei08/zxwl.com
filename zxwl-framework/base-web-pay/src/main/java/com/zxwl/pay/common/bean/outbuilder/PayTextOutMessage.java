package com.zxwl.pay.common.bean.outbuilder;

import com.zxwl.pay.common.bean.MsgType;
import com.zxwl.pay.common.bean.PayOutMessage;

/**
 * @author chendawei
 *  <pre>
 *      email 1026022306@qq.com
 *      date 2016-6-1 11:40:30
 *  </pre>
 */
public class PayTextOutMessage extends PayOutMessage{

    public PayTextOutMessage() {
        this.msgType = MsgType.text.name();
    }

    @Override
    public String toMessage() {
        return getContent();
    }
}
