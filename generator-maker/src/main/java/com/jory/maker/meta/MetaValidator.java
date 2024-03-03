package com.jory.maker.meta;

/**
 * @Author: Jory Zhang
 * @Date: 2024/3/4 00 26
 * @Description: 元信息校验
 */
public class MetaValidator {

    public static void dValidAndFile(Meta meta) {
        //基础信息校验和默认值
        String name = meta.getName();
        String description = meta.getDescription();
        String basePackage = meta.getBasePackage();
        String version = meta.getVersion();
        String author = meta.getAuthor();
        String createTime = meta.getCreateTime();
        Meta.FileConfigDTO fileConfig = meta.getFileConfig();
        Meta.ModelConfigDTO modelConfig = meta.getModelConfig();


    }


    //

}
