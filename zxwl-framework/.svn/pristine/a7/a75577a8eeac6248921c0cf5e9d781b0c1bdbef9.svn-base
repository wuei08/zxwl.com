package com.zxwl.pay.common.util;

import com.zxwl.pay.common.api.PayErrorExceptionHandler;
import com.zxwl.pay.common.exception.PayErrorException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * LogExceptionHandler 日志处理器
 * @author  chendawei
 * <pre>
 * email 1026022306@qq.com
 * date 2016-6-1 11:28:01
 *
 *
 * source chanjarster/weixin-java-tools
 * </pre>
 */
public class LogExceptionHandler implements PayErrorExceptionHandler {

    protected final Log log = LogFactory.getLog(PayErrorExceptionHandler.class);

    @Override
    public void handle(PayErrorException e) {

        log.error("Error happens", e);

    }

}
