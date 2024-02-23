package com.jory.cli.pattern;

/**
 * @Author: Jory Zhang
 * @Date: 2024/2/23 19 18
 * @Description:
 */
public class Device {

    private String name;

    public Device(String name) {
        this.name = name;
    }

    public void turnOn() {
        System.out.println(name + "设备打开");
    }

    public void turnOff() {
        System.out.println(name + "设备关闭");
    }
}
