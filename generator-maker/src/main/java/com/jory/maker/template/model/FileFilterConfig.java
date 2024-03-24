package com.jory.maker.template.model;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: Jory Zhang
 * @Date: 2024/3/21 12 25
 * @Description: 文件过滤陪配置
 */
@Data
@Builder
public class FileFilterConfig {
    /**
     * 过滤范围
     */
    private String range;
    /**
     * 过滤规则
     */
    private String rule;

    /**
     * 过滤值
     */
    private String value;

}
