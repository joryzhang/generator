package ${basePackage}.cli.command;

import cn.hutool.core.bean.BeanUtil;
import ${basePackage}.generator.file.FileGenerator;
import ${basePackage}.model.DataModel;
import lombok.Data;
import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * @Author: ${author}
 * @Date: ${createTime}
 * @Description:
 */
@CommandLine.Command(name = "generate",mixinStandardHelpOptions = true)
@Data
public class GenerateCommand implements Callable {

    <#list modelConfig.models as modelInfo>

        @CommandLine.Option(names = {<#if modelInfo.abbr??>"-${modelInfo.abbr}"</#if>,"--${modelInfo.fieldName}"},description = "${modelInfo.description}",arity = "0..1",interactive = true,echo = true)
        private ${modelInfo.type} ${modelInfo.fieldName} <#if modelInfo.defaultValue??>= ${modelInfo.defaultValue?c}</#if>;


    </#list>

    @Override
    public Object call() throws Exception {
        //传递参数 先创建一个动态模板
        DataModel mainTemplateConfig = new DataModel();
//        mainTemplateConfig.setAuthor();
        BeanUtil.copyProperties(this,mainTemplateConfig);
        FileGenerator.doGenerate(mainTemplateConfig);
        return 0;
    }
}
