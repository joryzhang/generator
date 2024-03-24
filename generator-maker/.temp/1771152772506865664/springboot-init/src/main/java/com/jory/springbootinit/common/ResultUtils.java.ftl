package com.yupi.springbootinit.common;

/**
 * 返回工具类
 */
public class ResultUtils {

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ${className}<T> success(T data) {
        return new ${className}<>(0, data, "ok");
    }

    /**
     * 失败
     *
     * @param errorCode
     * @return
     */
    public static ${className} error(ErrorCode errorCode) {
        return new ${className}<>(errorCode);
    }

    /**
     * 失败
     *
     * @param code
     * @param message
     * @return
     */
    public static ${className} error(int code, String message) {
        return new ${className}(code, null, message);
    }

    /**
     * 失败
     *
     * @param errorCode
     * @return
     */
    public static ${className} error(ErrorCode errorCode, String message) {
        return new ${className}(errorCode.getCode(), null, message);
    }
}
