package com.jory.maker.meta;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.jory.maker.meta.enums.FileGnerateTypeEnum;
import com.jory.maker.meta.enums.FileTypeEnum;
import com.jory.maker.meta.enums.ModelTypeEnum;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Jory Zhang
 * @Date: 2024/3/4 00 26
 * @Description: 元信息校验
 */
public class MetaValidator {

    public static void dValidAndFile(Meta meta) {
        //基础信息校验和默认值
        validAndFileMetaRoot(meta);
        //fileConfig校验和默认值
        validAndFileConfig(meta);
        //modelConfig校验和默认值
        validAndModelConfig(meta);


    }

    private static void validAndModelConfig(Meta meta) {
        Meta.ModelConfigDTO modelConfig = meta.getModelConfig();
        if (modelConfig == null) {
            return;
        }
        List<Meta.ModelConfigDTO.ModelInfo> modelInfoList = modelConfig.getModels();
        if (CollUtil.isEmpty(modelInfoList)) {
            return;
        }
        for (Meta.ModelConfigDTO.ModelInfo modelInfo : modelInfoList) {
            //为group时，不校验
            String groupKey = modelInfo.getGroupKey();
            if (StrUtil.isNotEmpty(groupKey)){
                //生成中间参数“--author --outputText”
                List<Meta.ModelConfigDTO.ModelInfo> subModelInfoList = modelInfo.getModels();
                String allArgStr = subModelInfoList.stream()
                        .map(subModelInfo -> String.format("\"--%s\"", subModelInfo.getFieldName()))
                        .collect(Collectors.joining(", "));
                modelInfo.setAllArgStr(allArgStr);
                continue;
            }
            String fieldName = modelInfo.getFieldName();
            if (StrUtil.isBlank(fieldName)) {
                throw new MetaException("未填写 FieldName");
            }
            String modelInfoType = modelInfo.getType();
            if (StrUtil.isEmpty(modelInfoType)) {
                modelInfo.setType(ModelTypeEnum.STRING.getValue());
            }

        }
    }

    private static void validAndFileConfig(Meta meta) {
        Meta.FileConfigDTO fileConfig = meta.getFileConfig();
        if (fileConfig == null) {
            return;
        }
        //sourceRootPath 必填

        String sourceRootPath = fileConfig.getSourceRootPath();
        if (StrUtil.isBlank(sourceRootPath)) {
            throw new MetaException("未填写 sourceRootPath");
        }

        String inputRootPath = fileConfig.getInputRootPath();
        String defaultInputRootPath = ".source/" + FileUtil.getLastPathEle(Paths.get(sourceRootPath)).getFileName().toString();
        if (StrUtil.isEmpty(inputRootPath)) {
            fileConfig.setInputRootPath(defaultInputRootPath);
        }

        String outputRootPath = fileConfig.getOutputRootPath();
        String defaultOutRootPath = "generated";
        if (StrUtil.isEmpty(outputRootPath)) {
            fileConfig.setOutputRootPath(defaultOutRootPath);
        }
        String fileConfigType = fileConfig.getType();
        String defaultType = FileTypeEnum.DIR.getValue();
        if (StrUtil.isEmpty(fileConfigType)) {
            fileConfig.setType(defaultType);
        }
        List<Meta.FileConfigDTO.FileInfo> fileInfoList = fileConfig.getFiles();
        if (CollUtil.isEmpty(fileInfoList)) {
            return;
        }
        for (Meta.FileConfigDTO.FileInfo fileInfo : fileInfoList) {
            String type = fileInfo.getType();
            //如果是组 暂时先不校验
            if (FileTypeEnum.GROUP.getValue().equals(type)){
                continue;
            }


            //inputPath 必填
            String inputPath = fileInfo.getInputPath();
            if (StrUtil.isBlank(inputPath)) {
                throw new MetaException("未填写 inputPath");
            }
            String outputPath = fileInfo.getOutputPath();
            if (StrUtil.isEmpty(outputPath)) {
                fileInfo.setOutputPath(inputPath);
            }
            if (StrUtil.isEmpty(type)) {
                //无文件后缀
                if (StrUtil.isBlank(FileUtil.getSuffix(inputPath))) {
                    fileInfo.setType(FileTypeEnum.DIR.getValue());
                } else {
                    fileInfo.setType(FileTypeEnum.FILE.getValue());
                }
            }
            String generateType = fileInfo.getGenerateType();
            if (StrUtil.isBlank(generateType)) {
                if (inputPath.endsWith(".ftl")) {
                    fileInfo.setGenerateType(FileGnerateTypeEnum.DYNAMIC.getValue());
                } else {
                    fileInfo.setGenerateType(FileGnerateTypeEnum.STATIC.getValue());
                }
            }


        }

    }

    private static void validAndFileMetaRoot(Meta meta) {
        //校验并填充默认值
        String name = StrUtil.blankToDefault(meta.getName(), "my-generator");
        String description = StrUtil.blankToDefault(meta.getDescription(), "我的模板生成器");
        String basePackage = StrUtil.blankToDefault(meta.getBasePackage(), "com.jory");
        String version = StrUtil.blankToDefault(meta.getVersion(), "1.0");
        String createTime = StrUtil.blankToDefault(meta.getCreateTime(), DateUtil.now());
        meta.setName(name);
        meta.setDescription(description);
        meta.setBasePackage(basePackage);
        meta.setVersion(version);
        meta.setCreateTime(createTime);
    }


}
