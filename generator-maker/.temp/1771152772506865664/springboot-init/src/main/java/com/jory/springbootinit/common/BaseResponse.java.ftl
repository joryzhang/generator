package com.yupi.springbootinit.common;

import java.io.Serializable;
import lombok.Data;

/**
 * 通用返回类
 *
 * @param <T>
 */
@Data
public class ${className}<T> implements Serializable {

    private int code;

    private T data;

    private String message;

    public ${className}(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public ${className}(int code, T data) {
        this(code, data, "");
    }

    public ${className}(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}
