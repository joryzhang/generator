package ${basePackage}.cli.command;

import cn.hutool.core.bean.BeanUtil;
import ${basePackage}.generator.FileGenerator;
import ${basePackage}.model.DataModel;
import lombok.Data;
import picocli.CommandLine;

import java.util.concurrent.Callable;
<#--生成选项-->
<#macro generateOption indent modelInfo>
${indent}@CommandLine.Option(names = {<#if modelInfo.abbr??>"-${modelInfo.abbr}",</#if>"--${modelInfo.fieldName}"},description = "${modelInfo.description}",arity = "0..1",interactive = true,echo = true)
${indent}private ${modelInfo.type} ${modelInfo.fieldName} <#if modelInfo.defaultValue??>= ${modelInfo.defaultValue?c}</#if>;
</#macro>

<#--生成命令调用-->
<#macro generateCommand indent modelInfo>
${indent}System.out.println("输入${modelInfo.groupName}配置:");
${indent}CommandLine commandLine = new CommandLine(${modelInfo.type}Command.class);
${indent}commandLine.execute(${modelInfo.allArgStr});
</#macro>
/**
 * @Author: ${author}
 * @Date: ${createTime}
 * @Description:
 */
@CommandLine.Command(name = "generate",mixinStandardHelpOptions = true)
@Data
public class GenerateCommand implements Callable {

    <#list modelConfig.models as modelInfo>
<#--    有分组-->
    <#if modelInfo.groupKey??>
    static DataModel.${modelInfo.type} ${modelInfo.groupKey} = new DataModel.${modelInfo.type}();
<#--根据分组生成命令类-->
    @CommandLine.Command(name = "${modelInfo.groupKey}")
    @Data
    public static class ${modelInfo.type}Command implements Runnable {
        <#list modelInfo.models as subModelInfo>
            <@generateOption indent="        " modelInfo = subModelInfo/>
        </#list>
        @Override
        public void run() {
        <#list modelInfo.models as subModelInfo>
        ${modelInfo.groupKey}.${subModelInfo.fieldName} = ${subModelInfo.fieldName};
        </#list>
            }
        }
    <#else>
        <@generateOption indent="       " modelInfo =modelInfo/>
    </#if>
    </#list>
<#--生成调用方法-->
    @Override
    public Integer call() throws Exception {
        <#list modelConfig.models as modelInfo>
        <#if modelInfo.groupKey??>
        <#if modelInfo.condition??>
            if(${modelInfo.condition}){
            <@generateCommand indent="        " modelInfo =modelInfo/>
            }
        <#else>
            <@generateCommand indent="    " modelInfo =modelInfo/>
        </#if>
        </#if>
        </#list>
        //传递参数 先创建一个动态模板
        DataModel dataModel = new DataModel();
        BeanUtil.copyProperties(this,dataModel);
        <#list modelConfig.models as modelInfo>
        <#if modelInfo.groupKey??>
        dataModel.${modelInfo.groupKey} = ${modelInfo.groupKey};
        </#if>
        </#list>
        FileGenerator.doGenerate(dataModel);
        return 0;
    }
}
