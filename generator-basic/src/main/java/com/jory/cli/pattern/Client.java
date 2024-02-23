package com.jory.cli.pattern;

/**
 * @Author: Jory Zhang
 * @Date: 2024/2/23 19 30
 * @Description:客户端
 */
public class Client {

    public static void main(String[] args) {
        //创建接收者对象
        Device tv = new Device("TV");
        Device stereo = new Device("Stereo");

        //创建具体命令对象，可以绑定不同设备
        TurnOnCommand turnOn= new TurnOnCommand(tv);
        TurnoffCommand turnoff = new TurnoffCommand(stereo);

        //创建调用者
        RemoteControl remote = new RemoteControl();

        //执行命令
        remote.setCommand(turnOn);
        remote.pressButton();

        remote.setCommand(turnoff);
        remote.pressButton();
    }
}
