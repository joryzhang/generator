package com.jory.cli;

import com.jory.cli.command.ConfigCommand;
import com.jory.cli.command.GenerateCommand;
import com.jory.cli.command.ListCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

/**
 * @Author: jory
 * @Date: 2024-02-27
 * @Description:遥控器
 */

@Command(name = "jory", mixinStandardHelpOptions = true)
public class CommandExecutor implements Runnable {

    private final CommandLine commandLine;

    {
        commandLine = new CommandLine(this)
                .addSubcommand(new GenerateCommand())
                .addSubcommand(new ConfigCommand())
                .addSubcommand(new ListCommand());
    }

    @Override
    public void run() {
        //不输入子命令时，给出友好提示
        System.out.println("请输入具体命令,或者输入 -- help 查看命令提示");
    }

    /**\
     * 执行命令
     * @param args
     * @return
     */
    public Integer doExecute(String[] args){
        return commandLine.execute(args);
    }
}
