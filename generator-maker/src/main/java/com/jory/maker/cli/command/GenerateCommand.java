package com.jory.maker.cli.command;

import cn.hutool.core.bean.BeanUtil;
import com.jory.maker.generator.file.FileGenerator;
import com.jory.maker.model.DataModel;
import com.jory.maker.model.MainTemplate;
import lombok.Data;
import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * @Author: Jory Zhang
 * @Date: 2024/2/23 19 44
 * @Description:
 */
@CommandLine.Command(name = "generate",mixinStandardHelpOptions = true)
@Data
public class GenerateCommand implements Callable {
    /**
     * arity 限制输入0-1个值 interactive开启交互 echo显示用户输入
     */
    @CommandLine.Option(names = {"-a","--author"},description = "作者",arity = "0..1",interactive = true,echo = true)
    private String author = "jory";


    @CommandLine.Option(names = {"-o","--outputText"},description = "输出文本",arity = "0..1",interactive = true,echo = true)
    private String outputText = "sum=";

    /**
     * 是否循环 loop(开关)
     */
    @CommandLine.Option(names = {"-l","--loop"},description = "是否循环",arity = "0..1",interactive = true,echo = true)
    private boolean loop;


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
