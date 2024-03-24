package com.jory.cli.command;

import cn.hutool.core.bean.BeanUtil;
import com.jory.generator.FileGenerator;
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

       @CommandLine.Option(names = {"--needGit"},description = "是否生成.gitignore 文件",arity = "0..1",interactive = true,echo = true)
       private boolean needGit = true;
       @CommandLine.Option(names = {"-l","--loop"},description = "是否生成循环",arity = "0..1",interactive = true,echo = true)
       private boolean loop = false;
    static DataModel.MainTemplate mainTemplate = new DataModel.MainTemplate();
    @CommandLine.Command(name = "mainTemplate")
    @Data
    public static class MainTemplateCommand implements Runnable {
        @CommandLine.Option(names = {"-a","--author"},description = "作者注释",arity = "0..1",interactive = true,echo = true)
        private String author = "jory";
        @CommandLine.Option(names = {"-o","--outputText"},description = "输出信息",arity = "0..1",interactive = true,echo = true)
        private String outputText = "sum = ";
        @Override
        public void run() {
        mainTemplate.author = author;
        mainTemplate.outputText = outputText;
            }
        }
    @Override
    public Integer call() throws Exception {
            if(loop){
        System.out.println("输入核心模板配置:");
        CommandLine commandLine = new CommandLine(MainTemplateCommand.class);
        commandLine.execute("--author", "--outputText");
            }
        //传递参数 先创建一个动态模板
        DataModel dataModel = new DataModel();
        BeanUtil.copyProperties(this,dataModel);
        dataModel.mainTemplate = mainTemplate;
        FileGenerator.doGenerate(dataModel);
        return 0;
    }
}
