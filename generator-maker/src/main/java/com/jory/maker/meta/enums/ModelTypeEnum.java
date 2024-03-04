package com.jory.maker.meta.enums;

/**
 * @Author: Jory Zhang
 * @Date: 2024/3/5 01 06
 * @Description: 模型类型枚举
 */
public enum ModelTypeEnum {
    STRING("字符串", "String"),
    BOOLEAN("布尔", "boolean");

    private final String text;
    private final String value;

    ModelTypeEnum(String text, String value) {
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
