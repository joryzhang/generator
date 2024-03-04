package com.jory.maker.generator;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.jory.maker.generator.file.DynamicFileGenerator;
import com.jory.maker.generator.main.GenerateTemplate;
import com.jory.maker.meta.Meta;
import com.jory.maker.meta.MetaManager;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;


/**
 * @Author: Jory Zhang
 * @Date: 2024/2/28 14 17
 * @Description:
 */
public class MainGenerator extends GenerateTemplate {
    @Override
    protected void buildDist(String outputPath, String sourceCopyDestPath, String jarPath, String shellOutputFilePath) {
        super.buildDist(outputPath, sourceCopyDestPath, jarPath, shellOutputFilePath);//可以覆写 扩展性更强
    }
}
