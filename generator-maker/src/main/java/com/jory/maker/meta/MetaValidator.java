package com.jory.maker.meta;

/**
 * @Author: Jory Zhang
 * @Date: 2024/3/4 00 26
 * @Description: Ԫ��ϢУ��
 */
public class MetaValidator {

    public static void dValidAndFile(Meta meta) {
        //������ϢУ���Ĭ��ֵ
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
