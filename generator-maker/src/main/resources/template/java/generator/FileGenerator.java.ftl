package ${basePackage}.generator.file;

import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * @Author: ${author}
 * @Date: ${createTime}
 * @Description:静态文件和动态文件结合生成
 */
public class FileGenerator {
    public static void doGenerate(Object model) throws TemplateException, IOException {

        String inputRootPath = "${fileConfig.inputRootPath}";
        String outputRootPath = "${fileConfig.outputRootPath}";

        String inputPath;
        String outputPath;

    <#list fileConfig.files as fileInfo>

        <#if fileInfo.generateType == "static">
        inputPath = new File(inputRootPath, "${fileInfo.inputPath}").getAbsolutePath();
        outputPath = new File(outputRootPath, "${fileInfo.outputPath}").getAbsolutePath();
        StaticFileGenerator.copyFileByHutool(inputPath,outputPath);

        <#else >
         inputPath = new File(inputRootPath, "${fileInfo.inputPath}").getAbsolutePath();
         outputPath = new File(outputRootPath, "${fileInfo.outputPath}").getAbsolutePath();
         DynamicFileGenerator.doGenerator(inputPath,outputPath,model);
        </#if>

    </#list>

    }

}
