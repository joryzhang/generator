package com.jory.maker.meta.enums;

/**
 * @Author: Jory Zhang
 * @Date: 2024/3/5 01 06
 * @Description: 文件类型枚举
 */
public enum FileTypeEnum {
    DIR("目录", "dir"),
    FILE("文件", "file"),
    GROUP("文件组", "group");

    private final String text;
    private final String value;

    FileTypeEnum(String text, String value) {
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
