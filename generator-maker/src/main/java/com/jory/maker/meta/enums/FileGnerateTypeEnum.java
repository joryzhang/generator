package com.jory.maker.meta.enums;

/**
 * @Author: Jory Zhang
 * @Date: 2024/3/5 01 06
 * @Description: 文件生成类型枚举
 */
public enum FileGnerateTypeEnum {
    DYNAMIC("动态", "dynamic"),
    STATIC("静态", "static");

    private final String text;
    private final String value;

    FileGnerateTypeEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }


    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
