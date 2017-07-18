
package com.zxwl.pay.common.bean.outbuilder;

import com.alibaba.fastjson.JSONObject;
import com.zxwl.pay.common.bean.PayOutMessage;

/**
 * @author: chendawei
 *  <pre>
 *      email 1026022306@qq.com
 *      date 2017/1/13 14:30
 *   </pre>
 */
public class JsonBuilder  extends BaseBuilder<JsonBuilder, PayOutMessage>{
    JSONObject json = null;

    public JsonBuilder(JSONObject json) {
        this.json = json;
    }

    public JsonBuilder content(String key, Object content) {
        this.json.put(key, content);
        return this;
    }

    public JSONObject getJson() {
        return json;
    }

    @Override
    public PayOutMessage build() {
        PayJsonOutMessage message = new PayJsonOutMessage();
        setCommon(message);
        message.setContent(json.toJSONString());
        return message;
    }
}
