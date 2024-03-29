package com.jory.generator;
import com.jory.model.DataModel;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
/**
* @Author: jory
* @Date: 2024-02-27
* @Description:静态文件和动态文件结合生成
*/
public class FileGenerator {
public static void doGenerate(DataModel model) throws TemplateException, IOException {

        String inputRootPath = ".source/acm-template-pro";
        String outputRootPath = "generated";

        String inputPath;
        String outputPath;

        boolean needGit = model.needGit;
        boolean loop = model.loop;
        String author = model.mainTemplate.author;
        String outputText = model.mainTemplate.outputText;
        inputPath = new File(inputRootPath, "src/com/jory/acm/MainTemplate.java.ftl").getAbsolutePath();
        outputPath = new File(outputRootPath, "src/com/jory/acm/MainTemplate.java").getAbsolutePath();
        DynamicFileGenerator.doGenerator(inputPath,outputPath,model);
        // groupKey = git
        inputPath = new File(inputRootPath, ".gitignore").getAbsolutePath();
        outputPath = new File(outputRootPath, ".gitignore").getAbsolutePath();
        StaticFileGenerator.copyFileByHutool(inputPath,outputPath);
        inputPath = new File(inputRootPath, "README.md").getAbsolutePath();
        outputPath = new File(outputRootPath, "README.md").getAbsolutePath();
        StaticFileGenerator.copyFileByHutool(inputPath,outputPath);
    }
}
