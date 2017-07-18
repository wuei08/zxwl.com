package com.zxwl.pay.common.exception;

import com.zxwl.pay.common.bean.result.PayError;

/**
 * @author  chendawei
 *  <pre>
 * email 1026022306@qq.com
 * date 2016-5-18 14:09:01
 *  </pre>
 */
public class PayErrorException extends RuntimeException  {

    private PayError error;

    public PayErrorException(PayError error) {
        super(error.getString());
        this.error = error;
    }


    public PayError getPayError() {
        return error;
    }
}
