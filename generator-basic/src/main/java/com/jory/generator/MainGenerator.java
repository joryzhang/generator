package com.jory.generator;

import com.jory.model.MainTemplateConfig;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * @Author: Jory Zhang
 * @Date: 2024/2/22 16 50
 * @Description:静态文件和动态文件结合生成
 */
public class MainGenerator {
    public static void main(String[] args) throws TemplateException, IOException {

        //静态文件生成
        String projectPath = System.getProperty("user.dir");//获取当前项目的根目录
        System.out.println(projectPath);
        //
        String inputPath = projectPath + File.separator+"generator-demo-projects"+ File.separator+"acm-template";//separator可以根据操作系统的不通更换不同的
        System.out.println(inputPath);
        String outputPath = projectPath;
        //复制
        StaticGenerator.copyFilesByRecursive(inputPath,outputPath);

        String dynamicInputPath= projectPath + File.separator + "generator-basic/src/main/resources/template/MainTemplate.java.ftl";
        String dynamicOutputPath= projectPath + File.separator + "acm-template/src/com/jory/acm/MainTemplate.java";
        MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
        mainTemplateConfig.setAuthor("Jory");
        mainTemplateConfig.setOutputText("输出结果");
        mainTemplateConfig.setLoop(true);

        //动态文件生成
        DynamicGenerator.doGenerator(dynamicInputPath,dynamicOutputPath,mainTemplateConfig);

    }
}
