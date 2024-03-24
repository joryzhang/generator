package com.jory.maker.template;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.jory.maker.meta.Meta;
import com.jory.maker.meta.enums.FileGnerateTypeEnum;
import com.jory.maker.meta.enums.FileTypeEnum;
import com.jory.maker.template.enums.FileFilterRangeEnum;
import com.jory.maker.template.enums.FileFilterRuleEnum;
import com.jory.maker.template.model.FileFilterConfig;
import com.jory.maker.template.model.TemplateMakerConfig;
import com.jory.maker.template.model.TemplateMakerFileConfig;
import com.jory.maker.template.model.TemplateMakerModelConfig;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: Jory Zhang
 * @Date: 2024/3/13 14 14
 * @Description: 模板制作工具
 */
public class TemplateMaker {
    /**
     * 制作模板
     *
     * @param templateMakerConfig
     * @return
     */
    public static long makeTemplate(TemplateMakerConfig templateMakerConfig) {
        Long id = templateMakerConfig.getId();
        Meta meta = templateMakerConfig.getMeta();
        String originProjectPath = templateMakerConfig.getOriginProjectPath();
        TemplateMakerModelConfig templateMakerModelConfig = templateMakerConfig.getModelConfig();
        TemplateMakerFileConfig templateMakerFileConfig = templateMakerConfig.getFileConfig();
        return makeTemplate(meta, originProjectPath, templateMakerFileConfig, templateMakerModelConfig, id);
    }

    /**
     * 制作模板
     *
     * @param meta
     * @param originFileRootPath
     * @param templateMakerFileConfig
     * @param templateMakerModelConfig
     * @param id
     * @return
     */
    public static long makeTemplate(Meta meta, String originFileRootPath, TemplateMakerFileConfig templateMakerFileConfig, TemplateMakerModelConfig templateMakerModelConfig, Long id) {
        //没有id 则生成
        if (id == null) {
            id = IdUtil.getSnowflakeNextId();
        }
        //业务逻辑
        String projectPath = System.getProperty("user.dir");
        //指定目标目录
        String temDirPath = projectPath + File.separator + ".temp";
        temDirPath.replace("\\", "/");
        String templatePath = temDirPath + File.separator + id;
        if (!FileUtil.exist(templatePath)) {
            FileUtil.mkdir(templatePath);
            FileUtil.copy(originFileRootPath, templatePath, true);
        }
        //一. 输入信息
        List<TemplateMakerModelConfig.ModelInfoConfig> models = templateMakerModelConfig.getModels();
        //转化为配置文件接受的modelinfo对象
        List<Meta.ModelConfigDTO.ModelInfo> inputModelInfoList = models.stream()
                .map(modelInfoConfig -> {
                    Meta.ModelConfigDTO.ModelInfo modelInfo = new Meta.ModelConfigDTO.ModelInfo();
                    BeanUtil.copyProperties(modelInfoConfig, modelInfo);//把原本的modelInfoConfig复制给modelInfo、
                    return modelInfo;
                }).collect(Collectors.toList());
        //本次新增的模型列表
        ArrayList<Meta.ModelConfigDTO.ModelInfo> newModelInfoList = new ArrayList<>();
        //如果是模型组
        TemplateMakerModelConfig.ModelGroupConfig modelGroupConfig = templateMakerModelConfig.getModelGroupConfig();
        if (modelGroupConfig != null) {
            String condition = modelGroupConfig.getCondition();
            String groupKey = modelGroupConfig.getGroupKey();
            String groupName = modelGroupConfig.getGroupName();
            Meta.ModelConfigDTO.ModelInfo groupModelInfo = new Meta.ModelConfigDTO.ModelInfo();

            groupModelInfo.setCondition(condition);
            groupModelInfo.setGroupKey(groupKey);
            groupModelInfo.setGroupName(groupName);
            //文件全放到一个分组内
            groupModelInfo.setModels(inputModelInfoList);
            newModelInfoList = new ArrayList<>();
            newModelInfoList.add(groupModelInfo);

        } else {
            //不分组 添加所有模型信息到列表
            newModelInfoList.addAll(inputModelInfoList);
        }

        //1.项目的基本信息
        String name = "acm-template-generator";
        String description = "ACM 示例模板生成器";
        //2.输入文件信息
        //要挖坑的根路径
        String sourceRootPath = templatePath + File.separator + FileUtil.getLastPathEle(Paths.get(originFileRootPath)).toString();
        sourceRootPath = sourceRootPath.replace("\\", "/");
        System.out.println(sourceRootPath);
        List<TemplateMakerFileConfig.FileInfoConfig> fileInfoConfigList = templateMakerFileConfig.getFiles();
        //遍历输入文件
        List<Meta.FileConfigDTO.FileInfo> newFileInfoList = new ArrayList<>();
        for (TemplateMakerFileConfig.FileInfoConfig fileInfoConfig : fileInfoConfigList) {
            String inputFilePath = fileInfoConfig.getPath();
            String inputFileAbsolutPath = sourceRootPath + File.separator + inputFilePath;
            //传入绝对路径
            //得到过滤后的文件列表
            List<File> fileList = FileFilter.doFilter(inputFileAbsolutPath, fileInfoConfig.getFilterConfigList());
            // 不处理已经生成的FTL模板文件
            fileList = fileList.stream()
                    .filter(file -> !file.getAbsolutePath().endsWith(".ftl"))
                    .collect(Collectors.toList());
            for (File file : fileList) {
                Meta.FileConfigDTO.FileInfo fileInfo = makeFileTemplate(templateMakerModelConfig, sourceRootPath, file);
                newFileInfoList.add(fileInfo);
            }
        }

        //如果是文件组
        TemplateMakerFileConfig.FileGroupConfig fileGroupConfig = templateMakerFileConfig.getFileGroupConfig();
        if (fileGroupConfig != null) {
            String condition = fileGroupConfig.getCondition();
            String groupKey = fileGroupConfig.getGroupKey();
            String groupName = fileGroupConfig.getGroupName();
            Meta.FileConfigDTO.FileInfo groupFileInfo = new Meta.FileConfigDTO.FileInfo();

            groupFileInfo.setCondition(condition);
            groupFileInfo.setGroupKey(groupKey);
            groupFileInfo.setGroupName(groupName);
            //文件全放到一个分组内
            groupFileInfo.setFiles(newFileInfoList);
            newFileInfoList = new ArrayList<>();
            newFileInfoList.add(groupFileInfo);

        }


        //三、生成配置文件
        String metaOutputPath = templatePath + File.separator + "meta.json";

        //已有meta文件
        if (FileUtil.exist(metaOutputPath)) {
            meta = JSONUtil.toBean(FileUtil.readUtf8String(metaOutputPath), Meta.class);
            List<Meta.FileConfigDTO.FileInfo> fileInfoList = meta.getFileConfig().getFiles();
            fileInfoList.addAll(newFileInfoList);

            List<Meta.ModelConfigDTO.ModelInfo> modelInfoList = meta.getModelConfig().getModels();
            modelInfoList.addAll(newModelInfoList);

            //配置去重
            meta.getFileConfig().setFiles(distinctFiles(fileInfoList));
            meta.getModelConfig().setModels(distinctModels(modelInfoList));

        } else {
            //1.构造配置参数对象
            Meta.FileConfigDTO fileConfigDTO = new Meta.FileConfigDTO();
            meta.setFileConfig(fileConfigDTO);
            fileConfigDTO.setSourceRootPath(sourceRootPath);
            List<Meta.FileConfigDTO.FileInfo> fileInfoList = new ArrayList<>();
            fileConfigDTO.setFiles(fileInfoList);

            fileInfoList.addAll(newFileInfoList);

            Meta.ModelConfigDTO modelConfigDTO = new Meta.ModelConfigDTO();
            meta.setModelConfig(modelConfigDTO);
            List<Meta.ModelConfigDTO.ModelInfo> modelInfoList = new ArrayList<>();
            modelConfigDTO.setModels(modelInfoList);
            modelInfoList.addAll(newModelInfoList);
        }
        //2.输出元信息文件 用JSONUtil.toJsonPrettyStr()可以生成格式化好的json文件
        FileUtil.writeUtf8String(JSONUtil.toJsonPrettyStr(meta), metaOutputPath);
        return id;
    }

    /**
     * 制作单个模板
     *
     * @param sourceRootPath
     * @param inputFile
     * @return
     */
    public static Meta.FileConfigDTO.FileInfo makeFileTemplate(TemplateMakerModelConfig templateMakerModelConfig, String sourceRootPath, File inputFile) {
        //要挖坑的文件(一定要是相对路径)
        String inputFilePath = inputFile.getAbsolutePath();
        inputFilePath = inputFilePath.replace("\\", "/");
        String fileInputPath = inputFilePath.replace(sourceRootPath + "/", "");
        //输出的文件
        String fileOutputPath = fileInputPath + ".ftl";

        String fileContent;
        //输出路径
        //二、使用字符串替换，生成模板文件
        String fileInputAbsolutPath = inputFile.getAbsolutePath();
        String fileOutputAbsolutPath = inputFile.getAbsolutePath() + ".ftl";
        boolean hasTemplateFile = FileUtil.exist(fileOutputAbsolutPath);
        if (hasTemplateFile) {
            fileContent = FileUtil.readUtf8String(fileOutputAbsolutPath);
        } else {
            fileContent = FileUtil.readUtf8String(fileInputAbsolutPath);
        }

        //支持多轮替换 对于同一个文件的内容，遍历模型 进行多轮替换
        String newFileContent = fileContent;

        TemplateMakerModelConfig.ModelGroupConfig modelGroupConfig = templateMakerModelConfig.getModelGroupConfig();
        String replacement;
        for (TemplateMakerModelConfig.ModelInfoConfig modelInfoConfig : templateMakerModelConfig.getModels()) {
            String fileName = modelInfoConfig.getFileName();
            //不是分组
            if (modelGroupConfig == null) {
                replacement = String.format("${%s}", fileName);
            } else {
                String groupKey = modelGroupConfig.getGroupKey();
                replacement = String.format("${%s.%s}", groupKey, fileName);
            }
            newFileContent = StrUtil.replace(newFileContent, modelInfoConfig.getReplaceText(), replacement);
        }


        //输出模板文件
        Meta.FileConfigDTO.FileInfo fileInfo = new Meta.FileConfigDTO.FileInfo();
        fileInfo.setInputPath(fileOutputPath);
        fileInfo.setOutputPath(fileInputPath);
        fileInfo.setType(FileTypeEnum.FILE.getValue());
        fileInfo.setGenerateType(FileGnerateTypeEnum.DYNAMIC.getValue());
        //是否更改了文件内容
        boolean contentEquals = newFileContent.equals(fileContent);
        //之前不存在模板，并且这次替换没有修改文件的内容，才是静态生成
        if (!hasTemplateFile) {
            if (contentEquals) {
                //输出路径=输入路径
                fileInfo.setInputPath(fileInputPath);
                fileInfo.setGenerateType(FileGnerateTypeEnum.STATIC.getValue());
            } else {
                FileUtil.writeUtf8String(newFileContent, fileOutputAbsolutPath);
            }
        } else if (!contentEquals) {
            //有模板文件，并且增加了文件内容
            FileUtil.writeUtf8String(newFileContent, fileOutputAbsolutPath);
        }
        return fileInfo;
    }

    /**
     * 文件去重
     *
     * @param fileInfoList
     * @return o->o 意思是以原对象为value (exist,replacement)->replacement 相同的key希望返回新值
     */
    public static List<Meta.FileConfigDTO.FileInfo> distinctFiles(List<Meta.FileConfigDTO.FileInfo> fileInfoList) {
        //策略： 同分组内文件 merge 不同分组保留

        //1.有分组，以组为单位划分  (lambda表达式用到groupingBy 根据给定的分类函数对流中的元素进行分组，并将它们放入一个Map中)
        Map<String, List<Meta.FileConfigDTO.FileInfo>> groupKeyFileList = fileInfoList.stream()
                .filter(fileInfo -> StrUtil.isNotBlank(fileInfo.getGroupKey()))
                .collect(
                        Collectors.groupingBy(Meta.FileConfigDTO.FileInfo::getGroupKey)
                );
        /**
         结果like {“groupKey": "a", files:[[1, 2],[2, 3]]}
         */
        //2. 同组内的文件配置合并 -->打平 [1 2 2 3] --> [1 2 3]
        //保存每个组对应的合并后的对象 map
        Map<String, Meta.FileConfigDTO.FileInfo> groupKeyMergedFileInfoMap = new HashMap<>();
        for (Map.Entry<String, List<Meta.FileConfigDTO.FileInfo>> entry : groupKeyFileList.entrySet()) {
            List<Meta.FileConfigDTO.FileInfo> tempFileInfoList = entry.getValue();//[[1, 2],[2, 3]] 是多组文件
            //对多组文件进行合并
            /**
             * lambda表达式用到flatMap方法 它用于将一个流中的每个元素映射为另一个流，然后将所有生成的流连接成一个流。及扁平化为一个层级
             */
            List<Meta.FileConfigDTO.FileInfo> newFileInfoList = new ArrayList<>(tempFileInfoList.stream().flatMap(fileInfo -> fileInfo.getFiles().stream())
                    //                    .collect(Collectors.toList());//[1 2 2 3]
                    .collect(Collectors.toMap(Meta.FileConfigDTO.FileInfo::getOutputPath, o -> o, (exist, replacement) -> replacement)).values());
            //去重并封装--> [ 1 2 3 ]

            //使用新的group配置  首先得到最新的配置 之前的逻辑 是 先取到老的 然后再添加新的 所以新的文件配置一定是在最后
            Meta.FileConfigDTO.FileInfo newFileInfo = CollUtil.getLast(tempFileInfoList);
            newFileInfo.setFiles(newFileInfoList);
            String groupKey = entry.getKey();
            groupKeyMergedFileInfoMap.put(groupKey, newFileInfo);
        }

        // 3.创建新的文件配置列表（结果列表），先将合并后的分组添加到结果列表
        ArrayList<Meta.FileConfigDTO.FileInfo> resultList = new ArrayList<>(groupKeyMergedFileInfoMap.values());

        // 4.再将无分组的文件配置列表添加到结果列表


        resultList.addAll(new ArrayList<>(fileInfoList.stream()
                .filter(fileInfo -> StrUtil.isBlank(fileInfo.getGroupKey()))
                .collect(
                        Collectors.toMap(Meta.FileConfigDTO.FileInfo::getOutputPath, o -> o, (exist, replacement) -> replacement)
                ).values()));//去完重就不需要key，直接拿到values

        return resultList;
    }

    /**
     * 模型去重
     *
     * @param modelInfoList
     * @return o->o 意思是以原对象为value (exist,replacement)->replacement 相同的key希望返回新值
     */
    public static List<Meta.ModelConfigDTO.ModelInfo> distinctModels(List<Meta.ModelConfigDTO.ModelInfo> modelInfoList) {
        //1.有分组，以组为单位划分  (lambda表达式用到groupingBy 根据给定的分类函数对流中的元素进行分组，并将它们放入一个Map中)
        Map<String, List<Meta.ModelConfigDTO.ModelInfo>> groupKeymodelList = modelInfoList.stream()
                .filter(modelInfo -> StrUtil.isNotBlank(modelInfo.getGroupKey()))
                .collect(
                        Collectors.groupingBy(Meta.ModelConfigDTO.ModelInfo::getGroupKey)
                );
        /**
         结果like {“groupKey": "a", models:[[1, 2],[2, 3]]}
         */
        //2. 同组内的模型配置合并 -->打平 [1 2 2 3] --> [1 2 3]
        //保存每个组对应的合并后的对象 map
        Map<String, Meta.ModelConfigDTO.ModelInfo> groupKeyMergedmodelInfoMap = new HashMap<>();
        for (Map.Entry<String, List<Meta.ModelConfigDTO.ModelInfo>> entry : groupKeymodelList.entrySet()) {
            List<Meta.ModelConfigDTO.ModelInfo> tempmodelInfoList = entry.getValue();//[[1, 2],[2, 3]] 是多组模型
            //对多组模型进行合并
            /**
             * lambda表达式用到flatMap方法 它用于将一个流中的每个元素映射为另一个流，然后将所有生成的流连接成一个流。及扁平化为一个层级
             */
            List<Meta.ModelConfigDTO.ModelInfo> newmodelInfoList = new ArrayList<>(tempmodelInfoList.stream().flatMap(modelInfo -> modelInfo.getModels().stream())
                    //                    .collect(Collectors.toList());//[1 2 2 3]
                    .collect(Collectors.toMap(Meta.ModelConfigDTO.ModelInfo::getFieldName, o -> o, (exist, replacement) -> replacement)).values());
            //去重并封装--> [ 1 2 3 ]

            //使用新的group配置  首先得到最新的配置 之前的逻辑 是 先取到老的 然后再添加新的 所以新的模型配置一定是在最后
            Meta.ModelConfigDTO.ModelInfo newmodelInfo = CollUtil.getLast(tempmodelInfoList);
            newmodelInfo.setModels(newmodelInfoList);
            String groupKey = entry.getKey();
            groupKeyMergedmodelInfoMap.put(groupKey, newmodelInfo);
        }

        // 3.创建新的模型配置列表（结果列表），先将合并后的分组添加到结果列表
        ArrayList<Meta.ModelConfigDTO.ModelInfo> resultList = new ArrayList<>(groupKeyMergedmodelInfoMap.values());

        // 4.再将无分组的模型配置列表添加到结果列表
        resultList.addAll(new ArrayList<>(modelInfoList.stream()
                .filter(modelInfo -> StrUtil.isBlank(modelInfo.getGroupKey()))
                .collect(
                        Collectors.toMap(Meta.ModelConfigDTO.ModelInfo::getFieldName, o -> o, (exist, replacement) -> replacement)
                ).values()));//去完重就不需要key，直接拿到values

        return resultList;
    }

    public static void main(String[] args) {
        Meta newMeta = new Meta();
        newMeta.setName("acm-template-generator");
        newMeta.setDescription("ACM 示例模板生成器");
        String projectPath = System.getProperty("user.dir");
        //工作空间隔离
        String originFileRootPath = new File(projectPath).getParent() + File.separator + "generator-demo-projects/springboot-init";
        originFileRootPath.replace("\\", "/");
        String fileInputPath1 = "src/main/java/com/jory/springbootinit/common";
        String fileInputPath2 = "src/main/resources/application.yml";
        String fileInputPath3 = "src/main/java/com/jory/springbootinit/common";
        List<String> inputFilePathList = Arrays.asList(fileInputPath1, fileInputPath2);
        //输入模型参数信息
//        Meta.ModelConfigDTO.ModelInfo modelInfo = new Meta.ModelConfigDTO.ModelInfo();
//        modelInfo.setFieldName("outputText");
//        modelInfo.setType("String");
//        modelInfo.setDefaultValue("sum = ");
//        String str = "Sum";
        //输入模型参数信息 第二次
        Meta.ModelConfigDTO.ModelInfo modelInfo = new Meta.ModelConfigDTO.ModelInfo();
        modelInfo.setFieldName("className");
        modelInfo.setType("String");

        //文件过滤配置
        TemplateMakerFileConfig.FileInfoConfig fileInfoConfig1 = new TemplateMakerFileConfig.FileInfoConfig();
        fileInfoConfig1.setPath(fileInputPath3);
        List<FileFilterConfig> fileFilterConfigList = new ArrayList<>();
        FileFilterConfig fileFilterConfig = FileFilterConfig.builder()
                .range(FileFilterRangeEnum.FILE_NAME.getValue())
                .rule(FileFilterRuleEnum.CONTAINS.getValue())
                .value("Base")
                .build();
        fileFilterConfigList.add(fileFilterConfig);
        fileInfoConfig1.setFilterConfigList(fileFilterConfigList);

        TemplateMakerFileConfig.FileInfoConfig fileInfoConfig2 = new TemplateMakerFileConfig.FileInfoConfig();
        fileInfoConfig2.setPath(fileInputPath2);
        List<TemplateMakerFileConfig.FileInfoConfig> templateMakerFileList = Arrays.asList(fileInfoConfig1, fileInfoConfig2);

        TemplateMakerFileConfig templateMakerFileConfig = new TemplateMakerFileConfig();
        templateMakerFileConfig.setFiles(templateMakerFileList);
        //文件分组配置
        TemplateMakerFileConfig.FileGroupConfig fileGroupConfig = new TemplateMakerFileConfig.FileGroupConfig();
        fileGroupConfig.setCondition("outputText1");
        fileGroupConfig.setGroupKey("test2");
        fileGroupConfig.setGroupName("测试分组1");
        templateMakerFileConfig.setFileGroupConfig(fileGroupConfig);

        //模型分组配置
        TemplateMakerModelConfig templateMakerModelConfig = new TemplateMakerModelConfig();
        TemplateMakerModelConfig.ModelGroupConfig modelGroupConfig = new TemplateMakerModelConfig.ModelGroupConfig();
        modelGroupConfig.setGroupKey(null);//"mysql"
        modelGroupConfig.setGroupName(null);//"数据库配置"
        templateMakerModelConfig.setModelGroupConfig(modelGroupConfig);

        //模型配置
        TemplateMakerModelConfig.ModelInfoConfig modelInfoConfig1 = new TemplateMakerModelConfig.ModelInfoConfig();
        modelInfoConfig1.setFileName("url");
        modelInfoConfig1.setType("String");
        modelInfoConfig1.setDefaultValue("jdbc:mysql://localhost:3306/my_db");
        modelInfoConfig1.setReplaceText("jdbc:mysql://localhost:3306/my_db");

        TemplateMakerModelConfig.ModelInfoConfig modelInfoConfig2 = new TemplateMakerModelConfig.ModelInfoConfig();
        modelInfoConfig2.setFileName("username");
        modelInfoConfig2.setType("String");
        modelInfoConfig2.setDefaultValue("root");
        modelInfoConfig2.setReplaceText("root");

        List<TemplateMakerModelConfig.ModelInfoConfig> modelInfoConfigList = Arrays.asList(modelInfoConfig1, modelInfoConfig2);
        templateMakerModelConfig.setModels(modelInfoConfigList);

        makeTemplate(newMeta, originFileRootPath, templateMakerFileConfig, templateMakerModelConfig, 1771152772506865664L);
    }
}

