package com.jory.cli.pattern;

/**
 * @Author: Jory Zhang
 * @Date: 2024/2/23 19 17
 * @Description:
 */
public class TurnOnCommand implements Command{


    private Device device;

    public TurnOnCommand(Device device) {
        this.device = device;
    }


    @Override
    public void execute() {
        device.turnOn();
    }
}
