package com.jory.maker;

import com.jory.maker.cli.CommandExecutor;
import com.jory.maker.generator.MainGenerator;
import freemarker.template.TemplateException;

import java.io.IOException;


public class Main {
        //测试
//        args = new String[]{"generate","-l","-a","-o"};
//        args = new String[]{"config"};
//        args = new String[]{"list"};

//        CommandExecutor commandExecutor = new CommandExecutor();
//        commandExecutor.doExecute(args);
        public static void main(String[] args) throws TemplateException, IOException, InterruptedException {
            MainGenerator generateTemplate = new MainGenerator();
            generateTemplate.doGenerate();
        }

}