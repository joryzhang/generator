package com.jory.maker.generator;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.jory.maker.generator.file.DynamicFileGenerator;
import com.jory.maker.meta.Meta;
import com.jory.maker.meta.MetaManager;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * @Author: Jory Zhang
 * @Date: 2024/2/28 14 17
 * @Description:
 */
public class MainGenerator {
    public static void main(String[] args) throws TemplateException, IOException, InterruptedException {
        Meta meta = MetaManager.getMetaObject();
        System.out.println(meta);

        //输出的根路径
        String projectPath = System.getProperty("user.dir");
        String outputPath = projectPath + File.separator + "generated";
        if (!FileUtil.exist(outputPath)) {
            FileUtil.mkdir(outputPath);
        }

        //读取Resource文件
        String classPathResource = projectPath + File.separator + "src/main/resources";
        System.out.println(classPathResource);


        //Java 包的生成路径
        String outputBasePackage = meta.getBasePackage();//com.jory
        //com/jory
        String outputBasePackagePath = StrUtil.join("/", StrUtil.split(outputBasePackage, "."));
        // generated/src/main/java/com/jory/xxx
        String outputPathJavaPackagePath = outputPath + File.separator + "src/main/java/" + outputBasePackagePath;

        String inputFilePath;
        String outputFilePath;

        //java.Main
        inputFilePath = classPathResource + File.separator + "template/java/Main.java.ftl";
        outputFilePath = outputPathJavaPackagePath + File.separator + "Main.java";
        DynamicFileGenerator.doGenerator(inputFilePath, outputFilePath, meta);

        // model.DataModel
        inputFilePath = classPathResource + File.separator + "template/java/model/DataModel.java.ftl";
        outputFilePath = outputPathJavaPackagePath + File.separator + "model/DataModel.java";
        DynamicFileGenerator.doGenerator(inputFilePath, outputFilePath, meta);

        // command.ConfigCommand
        inputFilePath = classPathResource + File.separator + "template/java/cli/command/ConfigCommand.java.ftl";
        outputFilePath = outputPathJavaPackagePath + File.separator + "cli/command/ConfigCommand.java";
        DynamicFileGenerator.doGenerator(inputFilePath, outputFilePath, meta);

        // command.GenerateCommand
        inputFilePath = classPathResource + File.separator + "template/java/cli/command/GenerateCommand.java.ftl";
        outputFilePath = outputPathJavaPackagePath + File.separator + "cli/command/GenerateCommand.java";
        DynamicFileGenerator.doGenerator(inputFilePath, outputFilePath, meta);

        // command.ListCommand
        inputFilePath = classPathResource + File.separator + "template/java/cli/command/ListCommand.java.ftl";
        outputFilePath = outputPathJavaPackagePath + File.separator + "cli/command/ListCommand.java";
        DynamicFileGenerator.doGenerator(inputFilePath, outputFilePath, meta);

        // cli.CommandExecutor
        inputFilePath = classPathResource + File.separator + "template/java/cli/CommandExecutor.java.ftl";
        outputFilePath = outputPathJavaPackagePath + File.separator + "cli/CommandExecutor.java";
        DynamicFileGenerator.doGenerator(inputFilePath, outputFilePath, meta);

        //generator.DynamicFileGenerator
        inputFilePath = classPathResource + File.separator + "template/java/generator/DynamicFileGenerator.java.ftl";
        outputFilePath = outputPathJavaPackagePath + File.separator + "generator/DynamicFileGenerator.java";
        DynamicFileGenerator.doGenerator(inputFilePath, outputFilePath, meta);

        //generator.StaticFileGenerator
        inputFilePath = classPathResource + File.separator + "template/java/generator/StaticFileGenerator.java.ftl";
        outputFilePath = outputPathJavaPackagePath + File.separator + "generator/StaticFileGenerator.java";
        DynamicFileGenerator.doGenerator(inputFilePath, outputFilePath, meta);

        //generator.FileGenerator
        inputFilePath = classPathResource + File.separator + "template/java/generator/FileGenerator.java.ftl";
        outputFilePath = outputPathJavaPackagePath + File.separator + "generator/FileGenerator.java";
        DynamicFileGenerator.doGenerator(inputFilePath, outputFilePath, meta);

        //template.pom
        inputFilePath = classPathResource + File.separator + "template/pom.xml.ftl";
        outputFilePath = outputPath + File.separator + "pom.xml";
        DynamicFileGenerator.doGenerator(inputFilePath, outputFilePath, meta);

        //构建 Jar 包
        JarGenerator.doGenerate(outputPath);

        //封装脚本
        String shellOutputFilePath = outputPath + File.separator + "generator";
        String jarName = String.format("%s-%s-jar-with-dependencies.jar", meta.getName(), meta.getVersion());
        String jarPath = "target/" + jarName;
        ScriptGenerator.doGenerate(shellOutputFilePath,jarPath);
    }
}
