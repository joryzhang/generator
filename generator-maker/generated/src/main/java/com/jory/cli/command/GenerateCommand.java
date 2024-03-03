package com.jory.cli.command;

import cn.hutool.core.bean.BeanUtil;
import com.jory.generator.file.FileGenerator;
import com.jory.model.DataModel;
import lombok.Data;
import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * @Author: jory
 * @Date: 2024-02-27
 * @Description:
 */
@CommandLine.Command(name = "generate",mixinStandardHelpOptions = true)
@Data
public class GenerateCommand implements Callable {


        @CommandLine.Option(names = {"-l","--loop"},description = "是否生成循环",arity = "0..1",interactive = true,echo = true)
        private boolean loop = false;



        @CommandLine.Option(names = {"-a","--author"},description = "作者注释",arity = "0..1",interactive = true,echo = true)
        private String author = "jory";



        @CommandLine.Option(names = {"-o","--outputText"},description = "输出信息",arity = "0..1",interactive = true,echo = true)
        private String outputText = "sum = ";



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
