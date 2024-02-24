package com.jory;

import com.jory.cli.CommandExecutor;

import java.io.File;

/**
 * @Author: Jory Zhang
 * @Date: 2024/2/21 21 44
 * @Description:
 */// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        //测试
        args = new String[]{"generate","-l","-a","-o"};
//        args = new String[]{"config"};
//        args = new String[]{"list"};

        CommandExecutor commandExecutor = new CommandExecutor();
        commandExecutor.doExecute(args);
    }
}