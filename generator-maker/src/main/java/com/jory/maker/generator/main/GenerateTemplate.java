package com.jory.maker.generator.main;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.jory.maker.generator.JarGenerator;
import com.jory.maker.generator.ScriptGenerator;
import com.jory.maker.generator.file.DynamicFileGenerator;
import com.jory.maker.meta.Meta;
import com.jory.maker.meta.MetaManager;
import freemarker.template.TemplateException;
import lombok.Data;

import java.io.File;
import java.io.IOException;

/**
 * @Author: Jory Zhang
 * @Date: 2024/3/5 01 24
 * @Description:���������
 */
public abstract class GenerateTemplate {

    public void doGenerate() throws TemplateException, IOException, InterruptedException {
        Meta meta = MetaManager.getMetaObject();
        System.out.println(meta);

        //����ĸ�·��
        String projectPath = System.getProperty("user.dir");
        String outputPath = projectPath + File.separator + "generated";
        if (!FileUtil.exist(outputPath)) {
            FileUtil.mkdir(outputPath);
        }
        //����ԭʼ�ļ�
        String sourceCopyDestPath = copySource(meta, outputPath);
        //���ɴ���
        generateCode(meta, projectPath, outputPath);
        //���� Jar ��
        String jarPath = buildJar(outputPath, meta);
        //��װ�ű�
        String shellOutputFilePath = buildScript(outputPath, jarPath);
        //���ɾ����ĳ���(�����)
        buildDist(outputPath, sourceCopyDestPath, jarPath, shellOutputFilePath);
    }

    protected void buildDist(String outputPath, String sourceCopyDestPath, String jarPath, String shellOutputFilePath) {
        String distOutputPath = outputPath + "-dist";
        //����Jar��
        String targetAbsolutePath = distOutputPath + File.separator + "target";
        FileUtil.mkdir(targetAbsolutePath);
        String jarAbsolutePath = outputPath + File.separator + jarPath;
        FileUtil.copy(jarAbsolutePath, targetAbsolutePath, true);
        //�����ű��ļ�
        FileUtil.copy(shellOutputFilePath, distOutputPath, true);
        FileUtil.copy(shellOutputFilePath + ".bat", distOutputPath, true);
        //������Դģ���ļ�
        FileUtil.copy(sourceCopyDestPath, distOutputPath, true);
    }

    protected String buildScript(String outputPath, String jarPath) {
        String shellOutputFilePath = outputPath + File.separator + "generator";
        ScriptGenerator.doGenerate(shellOutputFilePath, jarPath);
        return shellOutputFilePath;
    }

    protected String buildJar(String outputPath,Meta meta) throws IOException, InterruptedException {
        JarGenerator.doGenerate(outputPath);
        String jarName = String.format("%s-%s-jar-with-dependencies.jar", meta.getName(), meta.getVersion());
        String jarPath = "target/" + jarName;
        return jarPath;
    }

    protected void generateCode(Meta meta, String projectPath, String outputPath) throws IOException, TemplateException {
        //��ȡResource�ļ�
        String classPathResource = projectPath + File.separator + "src/main/resources";
        System.out.println(classPathResource);


        //Java ��������·��
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
    }

    protected String copySource(Meta meta, String outputPath) {
        String sourceRootPath = meta.getFileConfig().getSourceRootPath();
        String sourceCopyDestPath = outputPath + File.separator + ".source";
        FileUtil.copy(sourceRootPath, sourceCopyDestPath, false);
        return sourceCopyDestPath;
    }
}



