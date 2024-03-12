package ${basePackage}.generator;
import ${basePackage}.model.DataModel;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
<#--定义一个宏-->
<#macro generateFile indent fileInfo >
${indent}inputPath = new File(inputRootPath, "${fileInfo.inputPath}").getAbsolutePath();
${indent}outputPath = new File(outputRootPath, "${fileInfo.outputPath}").getAbsolutePath();
<#if fileInfo.generateType == "static">
${indent}StaticFileGenerator.copyFileByHutool(inputPath,outputPath);
<#else >
${indent}DynamicFileGenerator.doGenerator(inputPath,outputPath,model);
</#if>
</#macro>
/**
* @Author: ${author}
* @Date: ${createTime}
* @Description:静态文件和动态文件结合生成
*/
public class FileGenerator {
public static void doGenerate(DataModel model) throws TemplateException, IOException {

        String inputRootPath = "${fileConfig.inputRootPath}";
        String outputRootPath = "${fileConfig.outputRootPath}";

        String inputPath;
        String outputPath;

<#--    boolean needGit = model.needGit -->
<#list modelConfig.models as modelInfo>
<#--    有分组-->
    <#if modelInfo.groupKey??>
    <#list modelInfo.models as subModelInfo>
        ${subModelInfo.type} ${subModelInfo.fieldName} = model.${modelInfo.groupKey}.${subModelInfo.fieldName};
    </#list>
    <#else >
        ${modelInfo.type} ${modelInfo.fieldName} = model.${modelInfo.fieldName};
    </#if>
</#list>
<#list fileConfig.files as fileInfo>
    <#if fileInfo.groupKey??>
        // groupKey = ${fileInfo.groupKey}
        <#if fileInfo.condition??>
        if (${fileInfo.condition}) {
            <#list fileInfo.files as fileInfo>
                <@generateFile indent="            " fileInfo=fileInfo/>
            </#list>
        }
        <#else>
            <#list fileInfo.files as fileInfo>
            <@generateFile indent="        " fileInfo=fileInfo/>
            </#list>
        </#if>
    <#else >
        <#if fileInfo.condition??>
            if(${fileInfo.condition}){
            <@generateFile indent="        " fileInfo=fileInfo/>
            }
        <#else>
            <@generateFile indent="        " fileInfo=fileInfo/>
        </#if>
    </#if>
</#list>
    }
}
