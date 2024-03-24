package com.jory.maker.template;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import com.jory.maker.template.enums.FileFilterRangeEnum;
import com.jory.maker.template.enums.FileFilterRuleEnum;
import com.jory.maker.template.model.FileFilterConfig;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Jory Zhang
 * @Date: 2024/3/21 14 11
 * @Description:
 */
public class FileFilter {

    /**
     * 对某个文件或目录进行过滤 返回文件目录
     * @param filePath
     * @param filterConfigList
     * @return
     */
    public static List<File> doFilter(String filePath,List<FileFilterConfig> filterConfigList){
        //根据路径获得所有文件
        List<File> fileList = FileUtil.loopFiles(filePath);
        return fileList.stream()
                .filter(file -> doSingleFileFilter(filterConfigList,file))
                .collect(Collectors.toList());
    }

    /**
     * 单个文件过滤
     *
     * @param filterConfigList
     * @param file
     * @return
     */
    public static boolean doSingleFileFilter(List<FileFilterConfig> filterConfigList, File file) {
        String fileName = file.getName();
        String fileContent = FileUtil.readUtf8String(file);

        //所有过滤器校验结束后的结果
        boolean result = true;
        if (CollUtil.isEmpty(filterConfigList)) {
            return true;
        }

        for (FileFilterConfig fileFilterConfig : filterConfigList) {
            String range = fileFilterConfig.getRange();
            String rule = fileFilterConfig.getRule();
            String value = fileFilterConfig.getValue();

            FileFilterRangeEnum filterRangeEnum = FileFilterRangeEnum.getEnumByValue(range);
            if (filterRangeEnum == null) {
                continue;
            }
            //要过滤的原内容
            String content = fileName;
            switch (filterRangeEnum) {

                case FILE_NAME:
                    content = fileName;
                    break;
                case FILE_CONTENT:
                    content = fileContent;
                    break;
                default:
            }

            FileFilterRuleEnum filterRuleEnum = FileFilterRuleEnum.getEnumByValue(rule);
            if (filterRuleEnum == null) {
                continue;
            }
            switch (filterRuleEnum){

                case CONTAINS:
                    result = content.contains(value);
                    break;
                case STARTS_WITH:
                    result = content.startsWith(value);
                    break;
                case ENDS_WITH:
                    result = content.endsWith(value);
                    break;
                case REGEX:
                    result = content.matches(value);
                    break;
                case EQUALS:
                    result = content.equals(value);
                    break;
                default:
            }
            //有一个不满足 就返回
            if (!result){
                return false;
            }

        }
        //都满足
        return true;
    }
}
