package com.jory.maker.template.enums;

import cn.hutool.core.util.ObjectUtil;

import java.io.File;

/**
 * @Author: Jory Zhang
 * @Date: 2024/3/5 01 06
 * @Description: 文件过滤规则枚举
 */
public enum FileFilterRuleEnum {
    CONTAINS("包含", "contains"),
    STARTS_WITH("前缀", "startsWith"),
    ENDS_WITH("后缀", "endsWith"),
    REGEX("正则", "regex"),
    EQUALS("相等", "equals");

    private final String text;
    private final String value;

    FileFilterRuleEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }


    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    public static FileFilterRuleEnum getEnumByValue(String value){
        if (ObjectUtil.isEmpty(value)){
            return null;
        }
        for (FileFilterRuleEnum anEnum : FileFilterRuleEnum.values()){
            if (anEnum.value.equals(value)){
                return anEnum;
            }
        }
        return null;
    }
}
