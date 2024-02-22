package com.jory.generator;

import com.jory.model.MainTemplateConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

/**
 * @Author: Jory Zhang
 * @Date: 2024/2/22 16 10
 * @Description:动态文件生成器
 */
public class DynamicGenerator {
    public static void main(String[] args) throws TemplateException, IOException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);

        //指定输出数字格式
        configuration.setNumberFormat("0.#####");
        // 指定模板文件所在的路径
        String projectPath = System.getProperty("user.dir")+File.separator+"generator-basic";    //generator\+generator-basic
        File parentPath = new File(projectPath);
        configuration.setDirectoryForTemplateLoading(new File(parentPath,"src/main/resources/template"));
        //设置模板文件使用的字符集
        configuration.setDefaultEncoding("utf-8");

        //创建模板对象,加载指定模板
        Template template = configuration.getTemplate("MainTemplate.java.ftl");

        //创建数据模型
        MainTemplateConfig mainTemlateConfig = new MainTemplateConfig();
        mainTemlateConfig.setAuthor("Jory");
        mainTemlateConfig.setOutputText("我的输出结果");
        mainTemlateConfig.setLoop(false);



        //指定生成的文件
        Writer out = new FileWriter("MainTemplate.java");
        //生成文件
        template.process(mainTemlateConfig,out);
        //关闭流
        out.close();
    }


    public static void doGenerator(String inputPath,String outputPath,Object model) throws IOException, TemplateException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);

        //指定输出数字格式
        configuration.setNumberFormat("0.#####");
        // 指定模板文件所在的路径
        File templateDir = new File(inputPath).getParentFile();
        configuration.setDirectoryForTemplateLoading(templateDir);
        //设置模板文件使用的字符集
        configuration.setDefaultEncoding("utf-8");

        //创建模板对象,加载指定模板
        String templateName = new File(inputPath).getName();
        Template template = configuration.getTemplate(templateName);
        //指定生成的文件
        Writer out = new FileWriter(outputPath);
        //生成文件
        template.process(model,out);
        //关闭流
        out.close();
    }
}
