package com.jory.cli.pattern;

/**
 * @Author: Jory Zhang
 * @Date: 2024/2/23 19 25
 * @Description:调用者
 */
public class RemoteControl {
    private Command command;

    public void setCommand(Command command){
        this.command=command;
    }

    public void pressButton(){
        command.execute();
    }
}
