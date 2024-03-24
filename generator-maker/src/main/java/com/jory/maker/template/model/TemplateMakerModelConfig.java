package com.jory.maker.template.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: Jory Zhang
 * @Date: 2024/3/21 12 31
 * @Description:
 */
@Data
public class TemplateMakerModelConfig {
    private List<ModelInfoConfig> models;


    private ModelGroupConfig ModelGroupConfig;

    @Data
    @NoArgsConstructor
    public static class ModelInfoConfig{
        private String fileName;
        private String type;
        private String description;
        private String defaultValue;
        private String abbr;
        //用于替换哪些文本
        private String replaceText;


    }

    @Data
    @NoArgsConstructor
    public static class ModelGroupConfig{
        private String condition;

        private String groupKey;

        private String groupName;
    }
}
