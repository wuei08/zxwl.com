package com.zxwl.pay.common.api;


import com.zxwl.pay.common.exception.PayErrorException;

/**
 *   PayErrorExceptionHandler处理器
 *
 * @author  chendawei
 * <pre>
 *     email 1026022306@qq.com
 *     date 2016-6-1 11:33:01
 *  </pre>
 */
public interface PayErrorExceptionHandler {

    /**
     * 异常统一处理器
     * @param e 支付异常
     */
     void handle(PayErrorException e);

}
