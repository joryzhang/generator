package com.jory.maker.meta;

/**
 * @Author: Jory Zhang
 * @Date: 2024/3/4 00 17
 * @Description:
 */
public class MetaException extends RuntimeException {

    public MetaException(String message) {
        super(message);
    }


    public MetaException(String message, Throwable cause) {
        super(message, cause);
    }
}
