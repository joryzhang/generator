package com.jory.maker.template.enums;

import cn.hutool.core.util.ObjectUtil;

/**
 * @Author: Jory Zhang
 * @Date: 2024/3/5 01 06
 * @Description: 文件过滤范围枚举
 */
public enum FileFilterRangeEnum {
    FILE_NAME("文件名称", "fileName"),
    FILE_CONTENT("文件内容", "fileContent");

    private final String text;
    private final String value;

    FileFilterRangeEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }


    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    public static FileFilterRangeEnum getEnumByValue(String value){
        if (ObjectUtil.isEmpty(value)){
            return null;
        }
        for (FileFilterRangeEnum anEnum : FileFilterRangeEnum.values()){
            if (anEnum.value.equals(value)){
                return anEnum;
            }
        }
        return null;
    }
}
