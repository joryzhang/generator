package com.jory.maker.generator.file;

import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * @Author: Jory Zhang
 * @Date: 2024/2/22 16 50
 * @Description:静态文件和动态文件结合生成
 */
public class FileGenerator {
    public static void doGenerate(Object model) throws TemplateException, IOException {

        String inputRootPath="D:\\IntelliJ IDEA\\generator\\generator\\acm-template";//D:\IntelliJ IDEA\generator\generator\generator-demo-projects\acm-template
        String outputRootPath = "/generated";//generated

        //复制
        StaticFileGenerator.copyFileByHutool(inputRootPath,outputRootPath);
        //generator-basic/

        String dynamicInputPath= "";
        String dynamicOutputPath="";

        //动态文件生成
        DynamicFileGenerator.doGenerator(dynamicInputPath,dynamicOutputPath,model);

    }


}
