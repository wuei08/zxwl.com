package com.zxwl.concurrent.lock.exception;

/**
 * Created by zhouhao on 16-5-14.
 */
public class LockException extends RuntimeException {
    public LockException(String message) {
        super(message);
    }

    public LockException(String message, Throwable cause) {
        super(message, cause);
    }
}
