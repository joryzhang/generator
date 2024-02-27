package com.jory.maker.cli;

import com.jory.maker.cli.command.ConfigCommand;
import com.jory.maker.cli.command.GenerateCommand;
import com.jory.maker.cli.command.ListCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

/**
 * @Author: Jory Zhang
 * @Date: 2024/2/23 19 45
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
