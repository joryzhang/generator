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
public class TemplateMakerFileConfig {
    private List<FileInfoConfig> files;


    private FileGroupConfig fileGroupConfig;

    @Data
    @NoArgsConstructor
    public static class FileInfoConfig{
        private String path;

        private List<FileFilterConfig> filterConfigList;
    }

    @Data
    @NoArgsConstructor
    public static class FileGroupConfig{
        private String condition;

        private String groupKey;

        private String groupName;
    }
}
