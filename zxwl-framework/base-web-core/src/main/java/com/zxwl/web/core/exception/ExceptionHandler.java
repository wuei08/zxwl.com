package com.zxwl.web.core.exception;

import com.zxwl.web.core.message.ResponseMessage;

public interface ExceptionHandler {

   <T extends Throwable> boolean support(Class<T> e);

    ResponseMessage handle(Throwable e);
}