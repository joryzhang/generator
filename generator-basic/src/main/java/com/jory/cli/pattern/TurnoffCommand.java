package com.jory.cli.pattern;

/**
 * @Author: Jory Zhang
 * @Date: 2024/2/23 19 16
 * @Description:
 */
public class TurnoffCommand implements Command {

    private Device device;

    public TurnoffCommand(Device device) {
        this.device = device;
    }


    @Override
    public void execute() {
    device.turnOff();
    }
}
