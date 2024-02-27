package com.jory.maker.generator;

import cn.hutool.core.io.FileUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;

/**
 * @Author: Jory Zhang
 * @Date: 2024/2/22 16 10
 * @Description:动态文件生成器
 */
public class DynamicFileGenerator {


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

        //如果文件不存在 则创建目录
        if (!FileUtil.exist(outputPath)){
            FileUtil.touch(outputPath);//touch()新建具体的文件

        }

        //指定生成的文件
     //   Writer out = new FileWriter(outputPath);
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputPath), "UTF-8"));

        //生成文件
        template.process(model,out);
        //关闭流
        out.close();
    }
}
